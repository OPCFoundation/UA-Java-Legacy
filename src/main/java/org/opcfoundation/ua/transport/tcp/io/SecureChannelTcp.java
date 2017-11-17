/* Copyright (c) 1996-2015, OPC Foundation. All rights reserved.
   The source code in this file is covered under a dual-license scenario:
     - RCL: for OPC Foundation members in good-standing
     - GPL V2: everybody else
   RCL license terms accompanied with this source code. See http://opcfoundation.org/License/RCL/1.00/
   GNU General Public License as published by the Free Software Foundation;
   version 2 of the License are accompanied with this source code. See http://opcfoundation.org/License/GPLv2
   This source code is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
*/

package org.opcfoundation.ua.transport.tcp.io;

import static org.opcfoundation.ua.core.StatusCodes.Bad_CommunicationError;
import static org.opcfoundation.ua.core.StatusCodes.Bad_InternalError;
import static org.opcfoundation.ua.core.StatusCodes.Bad_NotFound;
import static org.opcfoundation.ua.core.StatusCodes.Bad_SecureChannelClosed;
import static org.opcfoundation.ua.core.StatusCodes.Bad_SecureChannelTokenUnknown;
import static org.opcfoundation.ua.core.StatusCodes.Bad_ServerUriInvalid;
import static org.opcfoundation.ua.core.StatusCodes.Bad_TcpSecureChannelUnknown;
import static org.opcfoundation.ua.core.StatusCodes.Bad_Timeout;
import static org.opcfoundation.ua.core.StatusCodes.Bad_UnexpectedError;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ChannelSecurityToken;
import org.opcfoundation.ua.core.CloseSecureChannelRequest;
import org.opcfoundation.ua.core.EncodeableSerializer;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.OpenSecureChannelRequest;
import org.opcfoundation.ua.core.OpenSecureChannelResponse;
import org.opcfoundation.ua.core.ResponseHeader;
import org.opcfoundation.ua.core.SecurityTokenRequestType;
import org.opcfoundation.ua.core.ServiceFault;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.binary.IEncodeableSerializer;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.IConnectionListener;
import org.opcfoundation.ua.transport.SecureChannel;
import org.opcfoundation.ua.transport.ServerConnection;
import org.opcfoundation.ua.transport.TransportChannelSettings;
import org.opcfoundation.ua.transport.UriUtil;
import org.opcfoundation.ua.transport.impl.AsyncResultImpl;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.transport.tcp.io.IConnection.IMessageListener;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.utils.StackUtils;
import org.opcfoundation.ua.utils.TimerUtil;

/**
 * Client's Secure Channel connection to an endpoint. <p>
 *
 * Secure channel creates transport channel(s) as needed.
 *
 *
 * If the connection fails, and the transport channel is stateful (TCP), and
 * the secure channel has pending service requests, it attempts to reconnect the
 * transport channel. If the reconnect fails there is a timeout sequence
 * of the following wait periods { 0, 1, 2, 4, 8, 16, 32, 64, 120, 120, ... }.<p>
 *
 * If error recovery state fails to re-establish new security token before the old
 * expires, the secure channel will be closed.
 *
 * Despite the name SecureChannelTcp, the class is 99% implemented as transport
 * channel agnostic. The plan is to upgrade the class to support SOAP transport
 * and rename to SecureChannelImpl.
 */
public class SecureChannelTcp implements IMessageListener, IConnectionListener, ITransportChannel, org.opcfoundation.ua.transport.SecureChannel {

	/**
	 * Log4J Error logger. 
	 * Security settings are logged with DEBUG level.
	 * Unexpected errors are logged with ERROR level. 
	 */
	static Logger logger = LoggerFactory.getLogger(SecureChannelTcp.class);
	private EncoderContext ctx;

	Executor executor = StackUtils.getBlockingWorkExecutor(); 

	/**
	 * Secure channel id. If this value is -1 the secure channel is closed.
	 */
	int secureChannelId = -1;
	
	/**
	 * The time when token was issued in time system of {@link System#currentTimeMillis()}.
	 */
	long tokenIssueTime;
	
	/**
	 * Value in milliseconds that indicates the token life time.
	 * The secure channel expires after 125% of the token life time has elapsed.
	 */
	long tokenLifetime;
	
    IEncodeableSerializer serializer = EncodeableSerializer.getInstance();
	TransportChannelSettings settings;
	InetSocketAddress addr;
	AtomicInteger requestIdCounter = new AtomicInteger( 0 /*StackUtils.RANDOM.nextInt()*/ );
	
	// Use this interface with SOAP if possible, if not then restructure SecureChannel class
	AtomicReference<IConnection> transportChannel = new AtomicReference<IConnection>(null);
	
	/**
	 * List on pending requests. All reads and writes are done by synchronizing to the
	 * requests object. 
	 */
	Map<Integer, PendingRequest> requests = new ConcurrentHashMap<Integer, PendingRequest>();
	
	/**
	 * Pending request class. The result is written to error or response, after which 
	 * semaphore s is released and its listener awakes. 
	 */
	static class PendingRequest {
		// System time in milliseconds
		long startTime = System.currentTimeMillis();
		
		// The time (current time) when the request timeouts
		long timeoutTime;
		
		// Request identification
		int requestId;
		
		// Sync result objects
		AsyncResultImpl<ServiceResponse> result;
		
		// Used if the request message has not been sent
		IEncodeable requestToBeSent;
	}
		
	/** 
	 * reconnect index points to element in RECONNECT_WAIT_TIME. 
	 * The index is reseted once a secure channel is opened succesfully. 
	 */
	int errorRecoveryReconnectIndex = 0;
	
	/**
	 * Task attempts reconnection. 
	 * The task is canceled if the secure channel is closed or secure channel establised manually.
	 */
	TimerTask errorRecoveryReconnectTimer;
	
	/**
	 * If true, the secure channel is in error recover mode. In recovery mode, there is a
	 * reconnect timer that attempts to reconnect the socket. Once connected, secure channel
	 * is re-established. 
	 * 
	 * Error recover mode lasts, until connection is established and secure channel renewed,
	 * or until last security token has expired which means that the secure channel has
	 * been closed at the server.  
	 */
	boolean errorRecoveryState = false;
	
	/**
	 * This lock is used whenever manipulating errorRecovery* variables
	 */
	Object errorRecoveryLock = new Object();
	
	/**
	 * The wait times to use between reconnect attempts in error recover mode
	 */
	private static final int[] RECONNECT_WAIT_TIME = new int[] {0, 1, 2, 4, 8, 16, 32, 64, 120, 120, 120};
	/**
	 * Tolerance defines how much sooner the requests timeout compared to
	 * the TimeoutHint; 10% helps to ensure that the response will be
	 * delivered back to the client before it times out there
	 */
	private static final double TIMEOUT_TOLERANCE = 0.1;
	
	/**
	 * This task renews security token. The task is created after security token is created,
	 * and is canceled on close. 
	 */
	TimerTask renewSecurityTokenTask;
	
	/**
	 * This task timeouts pending requests. The task is created upon async service request.
	 * "requests" is synchronized when timeoutPendingRequests is modified.
	 */
	AtomicReference<TimerTask> timeoutPendingRequestsTask = new AtomicReference<TimerTask>(null);
	
	/**
	 * Timer that schedules future tasks 
	 */
	Timer timer;
	
	/**
	 * <p>Constructor for SecureChannelTcp.</p>
	 */
	public SecureChannelTcp()
	{		
	}

	/**
	 * {@inheritDoc}
	 *
	 * Configure the secure channel
	 */
	public void initialize(TransportChannelSettings settings, EncoderContext ctx)
	throws ServiceResultException 	
	{
		initialize(settings.getDescription().getEndpointUrl(), settings, ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Configure the secure channel
	 */
	public void initialize(String url, TransportChannelSettings settings, EncoderContext ctx)
	throws ServiceResultException
	{
		logger.debug("initialize: url={}", url);
		if (logger.isTraceEnabled())
			logger.trace("initialize: settings={}", ObjectUtils.printFields(settings));
		InetSocketAddress addr = UriUtil.getSocketAddress(url);
		initialize(addr, settings, ctx);
	}	
	
	/**
	 * {@inheritDoc}
	 *
	 * Configure the secure channel.
	 */
	public void initialize(InetSocketAddress addr, TransportChannelSettings settings, EncoderContext ctx)
	throws ServiceResultException 	
	{
		if (this.secureChannelId!=-1) {
			throw new ServiceResultException(Bad_InternalError, "Cannot reconfigure already opened secure channel");
		}
		
		this.settings = settings.clone();
		this.addr = addr;
		this.ctx = ctx;
		
		errorRecoveryReconnectTimer = null;
		errorRecoveryReconnectIndex = 0;
		
		timer = TimerUtil.getTimer();
		
		String url = settings.getDescription().getEndpointUrl();
		if ( url == null || url.isEmpty() || UriUtil.SCHEME_OPCTCP.equals( UriUtil.getTransportProtocol(url) )) {
			setTransportChannel(new TcpConnection());
			getTransportChannel().initialize(addr, settings, ctx);
			getTransportChannel().addConnectionListener( this );
			getTransportChannel().addMessageListener( this );			
		} else {
			throw new ServiceResultException(Bad_ServerUriInvalid, "The protocol is not supported by the this SecureChannelTcp");
		}
		 
	}
	
	/**
	 * <p>getRequestTimeout.</p>
	 *
	 * @param request a {@link org.opcfoundation.ua.builtintypes.ServiceRequest} object.
	 * @return a long.
	 */
	protected long getRequestTimeout(ServiceRequest request) {
		UnsignedInteger timeoutHint = request.getRequestHeader() != null ? request.getRequestHeader().getTimeoutHint() : null;
		long clientTimeout = timeoutHint != null ? timeoutHint.longValue() : getOperationTimeout();
		return clientTimeout;
	}	

	/**
	 * {@inheritDoc}
	 *
	 * Send service request to the server.
	 *
	 * If the secure channel is in error recovery state, the request is put to a send queue.
	 * Message is sent upon successful reconnection. <p>
	 *
	 * If the transport channel fails and cannot be restablished within operation
	 * timeout period, {@link StatusCodes#Bad_RequestTimeout} is set as error.
	 *
	 * If the secure channel is not open, is expired or closed
	 * {@link StatusCodes#Bad_SecureChannelClosed} is thrown.
	 *
	 * If the thread is interrupted with {@link Thread#interrupt()}, the operation aborts
	 * and ServiceResultException Bad_RequestCancelledByClient is thrown. <p>
	 */
	public ServiceResponse serviceRequest(ServiceRequest request)
	throws ServiceFaultException, ServiceResultException {
		long clientTimeout = getRequestTimeout(request);
		return serviceRequest(request, clientTimeout);
	}	
	
	/** {@inheritDoc} */
	public ServiceResponse serviceRequest(ServiceRequest request, long operationTimeout)
	throws ServiceFaultException, ServiceResultException {
		// When the secure channel is renewed, it may be that the channel is temporarily closed.
		// Let us try to wait a little bit, if that is the case.
		int n=0;
		while (!isOpen())
			try {
				if (n++ > 100)
					throw new ServiceResultException(Bad_SecureChannelClosed);
				Thread.sleep(10);
			} catch (InterruptedException e1) {
			}
		
				
		PendingRequest req = newPendingRequest(operationTimeout);
		req.result = new AsyncResultImpl<ServiceResponse>();
		
		requests.put(req.requestId, req);
		logger.debug("serviceRequest: requests.size={}", requests.size()); //keySet());
		try {
			try {
				IConnection channel = getTransportChannel();
				if (channel != null)
				  channel.sendRequest(request, secureChannelId, req.requestId);
				logger.debug("serviceRequest: Message sent, requestId={} secureChannelId={}", req.requestId, secureChannelId);
				logger.trace("serviceRequest: message={}", request);
			} catch (ServiceResultException e) {
				//Check isDebugEnabled() here for possible performance reasons.
				if (logger.isDebugEnabled())
					logger.debug("serviceRequest: While sending requestId=" + req.requestId
						+ ", secureChannelId=" + secureChannelId + ", message="
						+ request, e);
				
				// 	Put the message to queue
				if (e.getStatusCode().isStatusCode( Bad_CommunicationError ))
				{
					req.requestToBeSent = request;					
					// Sends the result in another thread. This thread can continue with minimal delay
					executor.execute( sendPendingMessagesRunnable );					
				} else {
					// Unexpected error
					throw e;
				}
			}		
			//Waiting for response after sending CloseSecureChannelRequest is unnecessary. 
			if(request instanceof CloseSecureChannelRequest) {
				return null;
			}
			else {
				// Wait for response
				final ServiceResponse res; 
				if (operationTimeout==0) {
					res = (ServiceResponse) req.result.waitForResult();
				} else {				
					long timeout = req.timeoutTime - System.currentTimeMillis();
					res = (ServiceResponse) req.result.waitForResult(
							timeout, TimeUnit.MILLISECONDS);
				}
				if (logger.isTraceEnabled())
					logger.trace("Response: {}", res);
				else
					logger.debug("Response: {}", res.getClass().getSimpleName());
				final ResponseHeader responseHeader = res.getResponseHeader();
				StatusCode statusCode = responseHeader.getServiceResult(); 
				if (statusCode.isBad()) {
					logger.debug("BAD response: {}", statusCode);
					throw new ServiceFaultException(new ServiceFault(responseHeader));
				}
				return res;	
			}
		} finally {
			requests.remove(req.requestId);
		}
		
	}

	/**
	 * @param operationTimeout
	 * @return
	 */
	private PendingRequest newPendingRequest(long operationTimeout) {
		PendingRequest req = new PendingRequest();
		req.requestId = requestIdCounter.incrementAndGet();
		req.startTime = System.currentTimeMillis();
		req.timeoutTime = operationTimeout == 0 ? Long.MAX_VALUE
				: (long) (req.startTime + operationTimeout * (1.0 + TIMEOUT_TOLERANCE));
		return req;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Send service request to the server.
	 *
	 * If the secure channel is in error recovery state, the request is put to a send queue.
	 * Message is sent upon successful reconnection. <p>
	 *
	 * If the transport channel fails and cannot be re-established within operation
	 * timeout period, {@link StatusCodes#Bad_RequestTimeout} is set as error.
	 *
	 * If the secure channel is not open, is expired or closed
	 * {@link StatusCodes#Bad_SecureChannelClosed} is thrown.
	 *
	 * Errors are written to the result object.
	 * ServiceFaultException There was a service fault in processing of the operation in the server.
	 * ServiceResultException There was an error while transferring the operation over network. <p>
	 */
	public AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest request)
	{
		long clientTimeout = getRequestTimeout(request);
		return serviceRequestAsync(request, clientTimeout);
	}
	/**
	 * {@inheritDoc}
	 *
	 * Send service request to the server.
	 *
	 * If the secure channel is in error recovery state, the request is put to a send queue.
	 * Message is sent upon successful reconnection. <p>
	 *
	 * If the transport channel fails and cannot be restablished within operation
	 * timeout period, {@link StatusCodes#Bad_RequestTimeout} is set as error.
	 *
	 * If the secure channel is not open, is expired or closed
	 * {@link StatusCodes#Bad_SecureChannelClosed} is thrown.
	 *
	 * Errors are written to the result object.
	 * ServiceFaultException There was a service fault in processing of the operation in the server.
	 * ServiceResultException There was an error while transferring the operation over network. <p>
	 */
	public AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest request, long operationTimeout)
	{
 		final AsyncResultImpl<ServiceResponse> result = new AsyncResultImpl<ServiceResponse>();

		if (!isOpen()) {
			result.setError( new ServiceResultException(Bad_SecureChannelClosed) );
			return result;
		}
				
//		final PendingRequest req = new PendingRequest();
//		req.requestId = requestIdCounter.incrementAndGet();
//		req.startTime = System.currentTimeMillis();
//		req.timeoutTime = operationTimeout==0 ? Long.MAX_VALUE : req.startTime + operationTimeout;
		final PendingRequest req = newPendingRequest(operationTimeout);
		req.result = result;
		req.requestToBeSent = request;
		
		requests.put(req.requestId, req);
		logger.debug("serviceRequestAsync: requests.size={}", requests.size()); //keySet());

		// Make sure the request timeouts at some time
		if (operationTimeout!=0) {
			scheduleTimeoutRequestsTimer();
		}
		// Sends the result in another thread. Current thread may continue with no further delay
//		logger.debug("scheduling async request to another thread: "+req.requestId);
		executor.execute( sendPendingMessagesRunnable );
		
		return result;
	}

	/**
	 * Sets new Timer Task that timeouts pending requests.
	 * If task already exists but is too far in the future, it is canceled and new task assigned
	 */
	private void scheduleTimeoutRequestsTimer()
	{
		PendingRequest nextRequest = getNextTimeoutingPendingRequest();

		// Cancel task
		if (nextRequest == null) {
			cancelTimeoutPendingRequestTask();
		} else {
			TimerTask task = timeoutPendingRequestsTask.get();
			// Task does not exists or is not ok
			if (task == null
					|| task.scheduledExecutionTime() > nextRequest.timeoutTime) {
				cancelTimeoutPendingRequestTask();
				// Create a new task
				task = TimerUtil.schedule(timer, timeoutRun, executor,
						nextRequest.timeoutTime);
				if (!timeoutPendingRequestsTask.compareAndSet(null, task))
					// it was already set
					task.cancel();
			}
		}
	}
		
	/**
	 * This runnable goes thru pending requests and sets Bad_Timeout error code to all 
	 * requests that have timeouted. 
	 */
	Runnable timeoutRun = new Runnable() {
		@Override
		public void run() {
			cancelTimeoutPendingRequestTask();
			long currentTime = System.currentTimeMillis();
			for (PendingRequest req : requests.values()) {
				if (currentTime >= req.timeoutTime) {
					logger.warn("Request id={} timeouted {}ms elapsed. timeout at {}ms", req.requestId, (System.currentTimeMillis()-req.startTime), (req.timeoutTime - req.startTime));
					req.result.setError(new ServiceResultException(Bad_Timeout));
					requests.remove(req.requestId);
					// logger.debug("requests: "+requests.keySet());
				}
			}
			// Schedule next timeout event
			scheduleTimeoutRequestsTimer();
		}};	
		
	/**
	 * Get the next request that is closest to timeout
	 * 
	 * @return null or request
	 */
	private PendingRequest getNextTimeoutingPendingRequest()
	{
		long next = Long.MAX_VALUE;
		PendingRequest result = null;
		logger.debug("getNextTimeoutingPendingRequest: requests.size={}", requests.size());
		for (PendingRequest req : requests.values()) {
			if (next > req.timeoutTime) {
				next = req.timeoutTime;
				result = req;
			}
		}
		return result;
	}

	/**
	 * Opens a secure channel. This method does nothing if the secure channel
	 * is already open.
	 *
	 * Sets up a connection, opens it, creates a secure channel. If unable to
	 * open connection an exception is thrown and the secure channel remains
	 * closed.
	 *
	 * If the operation timeouts or user interrupts the thread with
	 * {@link Thread#interrupt()} a Bad_Timeout is thrown.
	 *
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public void open() throws ServiceResultException 
	{
		logger.debug("open");
		// Create new secure channel
		if (secureChannelId==-1) {
			try {
				getTransportChannel().open();
			} catch (ServiceResultException e) {
				logger.warn("Connection failed: {}", e.getMessage());
				// Connection occasionally fails due to an EOFException, which
				// is mapped to a CommunicationError. If that occurs, retry
				// once.
				if (e.getStatusCode().getValue().equals(Bad_CommunicationError))
				{
					logger.warn("Bad_CommunicationError: Retrying");
					getTransportChannel().open();
				}else{
					throw e;
				}
			}
			createSecureChannel(false);
		}			
	}
	
	/**
	 * Asynchronous open channel.
	 *
	 * @return async result object
	 */
	public AsyncResult<SecureChannel> openAsync() 
	{
 		final AsyncResultImpl<SecureChannel> result = new AsyncResultImpl<SecureChannel>();
 		// already open
 		if (secureChannelId!=-1) {
 			result.setResult(this);
 			return result;
 		}
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					open();
					result.setResult(SecureChannelTcp.this);
				} catch (ServiceResultException sre) {
					result.setError(sre);
				}
			}});
		return result;
	}
	
	/**
	 * Create or renew secure channel.
	 *  
	 * If the operation timeouts or user interrupts the thread with 
	 * {@link Thread#interrupt()} a Bad_Timeout is thrown.
	 * 
	 * @param renew false to create new secure channel, true to renew 
	 */
	private void createSecureChannel(boolean renew)
	throws ServiceResultException
	{
		final IConnection channel = getTransportChannel();
		logger.debug("createSecureChannel: renew={} channel={}", renew, channel);
		if (channel == null)
			throw new ServiceResultException(StatusCodes.Bad_SecureChannelClosed);
		final long startTime = System.currentTimeMillis();
		int requestId = requestIdCounter.incrementAndGet();
		logger.debug("createSecureChannel: requestId={}", requestId);
		
		OpenSecureChannelRequest req = new OpenSecureChannelRequest();								
		
		SecurityPolicy policy = SecurityPolicy.getSecurityPolicy( settings.getDescription().getSecurityPolicyUri() );
		SecurityAlgorithm algorithm = policy.getSymmetricEncryptionAlgorithm();
		ByteString nonce = CryptoUtil.createNonce(algorithm);
		
		Integer tokenLifetime = settings.getConfiguration().getSecurityTokenLifetime();
		if (tokenLifetime==null) tokenLifetime = 3600000;
		logger.debug("tokenLifetime: {}", tokenLifetime);
		
		req.setClientNonce(nonce);
		req.setClientProtocolVersion( UnsignedInteger.valueOf(0) );
		req.setRequestedLifetime( UnsignedInteger.valueOf( tokenLifetime ) );
		req.setRequestType( renew ? SecurityTokenRequestType.Renew : SecurityTokenRequestType.Issue );
		req.setSecurityMode( settings.getDescription().getSecurityMode() );		

		int chanId = renew ? this.secureChannelId : 0;
		
		final Semaphore s = new Semaphore(0);
		final ServiceResultException[] errors = new ServiceResultException[1];
		final IEncodeable[] result = new IEncodeable[1];
		final int[] secureChannelId = new int[1];
		final int __requestId = requestId;
		IMessageListener opnListener = new IMessageListener() {
			public void onMessage(int requestId_, int secureChannelId_, IEncodeable message) {						
				if (requestId_!=__requestId) return;
				result[0] = message;
				secureChannelId[0] = secureChannelId_;
				s.release(Integer.MAX_VALUE);
			}
		};
		IConnectionListener cloListener = new IConnectionListener() {
			public void onClosed(ServiceResultException closeError) {
				if (closeError == null) closeError = new ServiceResultException(Bad_CommunicationError, "Connection Closed");					
				errors[0] = closeError;
				s.release(Integer.MAX_VALUE);
			}
			@Override
			public void onOpen() {
			}
		};	
		
		channel.addConnectionListener( cloListener );
		channel.addMessageListener( opnListener );			
		try {
			channel.sendRequest(req, chanId, requestId);
		
			// Wait for result
			try {												
				long operationTimeout = getOperationTimeout();
				if (operationTimeout>0) {
					long elapsedTime = (System.currentTimeMillis()-startTime)/1000;
					long waitTime = operationTimeout-elapsedTime;				
					s.tryAcquire(1, waitTime, TimeUnit.MILLISECONDS);
				} else {
					s.acquire();
				}
			} catch (InterruptedException e) {
				// The user aborted the operation
			}
			
			// Throw error
			if (errors[0] != null) {
				throw errors[0];
			}				
			
			IEncodeable res = result[0];
			if (res==null) {
				throw new ServiceResultException(Bad_Timeout);
			}			

			if (res instanceof ServiceFault) {
				ServiceFaultException e = new ServiceFaultException((ServiceFault)res); 
				logger.error(secureChannelId+": CreateSecureChannel Fault", e);
				throw e;
			}
			
			if (res instanceof OpenSecureChannelResponse==false) {
				throw new ServiceResultException(Bad_UnexpectedError, "Unexpected result "+res.getClass().getName()+" OpenSecureChannelResponse expected");
			}
			
			
			// Successful Open secure channel
			OpenSecureChannelResponse opn = (OpenSecureChannelResponse) res;
			final ChannelSecurityToken token = opn.getSecurityToken();
			this.secureChannelId = token.getChannelId().intValue();
			
			if (renew) {
				logger.debug("{} Secure channel renewed, SecureChannelId={}, TokenId={}", this.secureChannelId, this.secureChannelId, token.getTokenId().longValue());
			} else {
				logger.debug("{} Secure channel opened, SecureChannelId={}, TokenId={}", this.secureChannelId, this.secureChannelId, token.getTokenId().longValue());
			}
			
			// HAX! In Reconnect to secure channel -situation, the C# Server implementation sends
			// two conflicting secure channel id's. 
			// The old channel (correct) in message header and a new channel id in the message body. 
			// 
			if (renew) this.secureChannelId = chanId;
			
			long currentTime = System.currentTimeMillis();
			this.tokenIssueTime = startTime/2 + currentTime/2;
			this.tokenLifetime = token.getRevisedLifetime().longValue();
			
			// Cancel token renewal
			{
				TimerTask t = renewSecurityTokenTask;
				renewSecurityTokenTask = null;
				if (t!=null) t.cancel();
			}
			// Setup new token renewal
			{
				long renewTime = token.getRevisedLifetime().longValue();
				logger.debug("RevisedLifetime: {}", renewTime);
				renewSecurityTokenTask = TimerUtil.schedule(timer, renewSecurityTokenRunnable, executor, 
						currentTime + (long)(renewTime* TcpMessageLimits.TokenRenewalPeriod ));
			}
			
		} catch (ServiceResultException e) {
			// Open failed
			throw e;
		} finally {
			channel.removeConnectionListener( cloListener );
			channel.removeMessageListener( opnListener );
		}
		
	}

	/**
	 * Send all pending request messages.
	 * Requests messages are gathered in requests map.
	 * 
	 */
	private void sendPendingRequestMessages() 
	throws ServiceResultException {
		if (!isOpen()) return;
		IEncodeable messageToSend;
		PendingRequest req = null;
		
		for (;;) {
			
			req = getNextUnsentRequest();
			if (req == null)
				break;
			messageToSend = req.requestToBeSent;
			req.requestToBeSent = null;
			// Request message has not been sent

			// The request has timeouted
			long currentTime = System.currentTimeMillis();
			long elapsedTime = currentTime - req.startTime;
			if (currentTime > req.timeoutTime) {
				logger.debug("Request id={} timeouted {}ms elapsed. timeout at {} ms", req.requestId, elapsedTime, (req.timeoutTime - req.startTime));
				requests.remove(req.requestId);
				req.result.setError(new ServiceResultException(Bad_Timeout));
				continue;
			}
			// Send request message
			if (messageToSend != null)
				try {
					logger.debug("sendPendingRequestMessages: requestId={}", req.requestId);
					IConnection channel = getTransportChannel();
					if (channel != null)
					  channel.sendRequest((ServiceRequest)messageToSend, secureChannelId, req.requestId);
				} catch (EncodingException e) {
					// Encoding problem
					requests.remove(req.requestId);
					req.result.setError(e);
				} catch (ServiceResultException e) {				
					// Put message back to the send queue
					StatusCode code = e.getStatusCode();
					if (code.isStatusCode(Bad_CommunicationError))
					{
						req.requestToBeSent = messageToSend;
					} else {
						// Unexpected error while sending a message
						req.result.setError(e);
					}
				}
		}
	}
	
	private Runnable sendPendingMessagesRunnable = new Runnable() {
		@Override
		public void run() {
			// synchronized to prevent simultaneous running
			// XXX this probably could be improved
			synchronized(sendPendingMessagesRunnable){
				try {
					sendPendingRequestMessages();
				} catch (ServiceResultException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	/**
	 * Get the next unsent message from request queue 
	 * 
	 * @return unsent pending message or null
	 */
	private PendingRequest getNextUnsentRequest() {
		for (PendingRequest r : requests.values()) {
			if (r.requestToBeSent != null) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Close the secure channel. This method does nothing if the channel is
	 * already closed or has never been opened. <p>
	 *
	 * This method sends CloseSecureChannelRequest to the server and
	 * closes the socket connection. If sending of the message fails and thus
	 * the servers never receives notification about closed secure channel, then
	 * there is no resend attempt, instead the secure channel will eventually
	 * time out in the server. <p>
	 *
	 * All pending requests will fault with Bad_SecureChannelClosed <p>
	 */
	public void close() {
		// Cancel Error recover mode
		setErrorRecoveryState(false);
		
		// Cancel token renewal
		{
			TimerTask t = renewSecurityTokenTask;
			renewSecurityTokenTask = null;
			if (t!=null) t.cancel();
		}
		
		// Send CloseSecureChannel if possible, close, and dispose socket 
		final IConnection transport_channel = getTransportChannel();
		if (transport_channel!=null) {
			CloseSecureChannelRequest req = new CloseSecureChannelRequest();
			try {
				serviceRequest(req);
			} catch (ServiceResultException e) {
			}
			
			if (secureChannelId!=-1) {
				logger.info("{} Closed", secureChannelId);
			}
			secureChannelId = -1;	
			
			transport_channel.close();
			transport_channel.removeMessageListener(this);
			transport_channel.removeConnectionListener(this);
			transport_channel.dispose();
			setTransportChannel(null);
		}
		
		// Cancel all pending requests
		{
			Collection<PendingRequest> copy;
			
				
			// Cancel timeout task
			cancelTimeoutPendingRequestTask();

			// TODO: Is this thread safe? Does it have to be? Should requests be a BlockingQueue?
			
//			if (requests.isEmpty())
//				copy = Collections.emptyList();
//			else
			copy = new ArrayList<PendingRequest>(requests.values());
			logger.debug("requests.clear()");
			requests.clear();

			if (!copy.isEmpty()) {
				ServiceResultException sre = new ServiceResultException(Bad_SecureChannelClosed);		
				for (PendingRequest pr : copy) {
					pr.result.setError(sre);
				}
			}
		}
	}

	/**
	 * 
	 */
	private void cancelTimeoutPendingRequestTask() {
		TimerTask task = timeoutPendingRequestsTask.getAndSet(null);
		if (task !=null) {
			task.cancel();
		}
	}

	/**
	 * @return
	 */
	IConnection getTransportChannel() {
		return transportChannel.get();
	}

	/**
	 * <p>Setter for the field <code>transportChannel</code>.</p>
	 *
	 * @param transportChannel the transportChannel to set
	 */
	protected void setTransportChannel(IConnection transportChannel) {
		this.transportChannel.set(transportChannel);
	}

	/**
	 * <p>closeAsync.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.AsyncResult} object.
	 */
	public AsyncResult<SecureChannel> closeAsync() {
 		final AsyncResultImpl<SecureChannel> result = new AsyncResultImpl<SecureChannel>();
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					close();
				} finally {
					result.setResult(SecureChannelTcp.this);
				}
			}});
		return result;
	}

	/**
	 * <p>dispose.</p>
	 */
	public void dispose() {
		close();
		transportChannel = null;
		serializer = null;
		settings = null;
		addr = null;
		requests = null;
		timer = null;
	}

	/**
	 * <p>getEndpointConfiguration.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.EndpointConfiguration} object.
	 */
	public EndpointConfiguration getEndpointConfiguration() {
		if (settings==null) return null;
		return settings.getConfiguration();
	}

	/**
	 * <p>getEndpointDescription.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 */
	public EndpointDescription getEndpointDescription() {
		if (settings==null) return null;
		return settings.getDescription();
	}
	
	/**
	 * <p>getMessageContext.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public EncoderContext getMessageContext() {
		return ctx;
	}

	/** {@inheritDoc} */
	public void setOperationTimeout(int timeout) {
		settings.getConfiguration().setOperationTimeout(timeout);
	}
	
	/**
	 * <p>getOperationTimeout.</p>
	 *
	 * @return a int.
	 */
	public int getOperationTimeout() {
		Integer i = settings.getConfiguration().getOperationTimeout();		
		return i == null ? 0 : i;
	}
		
	/**
	 * Get secure channel id.
	 *
	 * @return secure channel id or -1 if channel is closed.
	 */
	public int getSecureChannelId()
	{
		return secureChannelId;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Implementation to IMessageListener.
	 * Listens to messages incoming from TcpConnection.
	 */
	@Override
	public void onMessage(int requestId, int secureChannelId, IEncodeable message) {
		if (secureChannelId!=this.secureChannelId) return;
		

		// logger.debug("requests: "+requests.keySet());
		PendingRequest req = requests.remove(requestId);

		if (req==null) {
			if (message instanceof OpenSecureChannelResponse == false) {
				 {
					ServiceFault serviceFault = (message instanceof ServiceFault) ? (ServiceFault) message
							: null;
					if (serviceFault != null
							&& serviceFault
									.getResponseHeader()
									.getServiceResult()
									.equals(
											StatusCodes.Bad_TooManyPublishRequests))
						logger.info("ServiceFault={}", serviceFault);
					else {
						logger.warn("{} Unidentified message, RequestId={}, type={}!", secureChannelId, requestId, message.getClass().getSimpleName());
						if (serviceFault != null)
							logger.warn("ServiceFault={}", serviceFault);
					}
				}
			}
			return; // not for us
		}
		if (message instanceof ServiceFault)
			req.result.setError( new ServiceFaultException( (ServiceFault) message ) );
		else
			try {
				req.result.setResult((ServiceResponse) message);
			} catch (ClassCastException e) {
				logger.error("onMessage: Cannot set result", e);
			}
	}

	/**
	 * Return true if the secure channel has been opened and is not (hopefully) closed
	 * on the server.
	 *
	 * Secure channel is open as long as it as security token that is alive,
	 * even if its transport layer connection is disconnected.
	 *
	 * @return a boolean.
	 */
	public boolean isOpen() {
		if (secureChannelId==-1) return false;
		long expireTime = ((long)(tokenLifetime*1.25)) + tokenIssueTime;
		long currentTime = System.currentTimeMillis();
		return expireTime > currentTime; 
	}

	/**
	 * {@inheritDoc}
	 *
	 * Transport channel has been closed.
	 *
	 * Implementation to IConnectionListener
	 */
	@Override
	public void onClosed(ServiceResultException closeError) {
		// The socket connection has been closed
		
		// If secure channel is also closed, do nothing
		if (secureChannelId==-1) return;
		
		// Secure channel is not closed, we need to re-open the socket connection
		// Put secure channel into recovery state
		
		StatusCode errorCode = closeError==null?null:closeError.getStatusCode();
		// Reconnect error
		if ( errorCode!=null && errorCode.isStatusCode( StatusCodes.Bad_ConnectionClosed ) ) {
			setErrorRecoveryState(true);
		} else {
			if ( closeError == null ) closeError = new ServiceResultException( StatusCodes.Bad_UnexpectedError );
			// Cancel requests
			while ( !requests.isEmpty() ) {
				List<PendingRequest> list = new ArrayList<PendingRequest>( requests.values() );
				for ( PendingRequest req : list ) {
					req.result.setError( closeError );
				}
				requests.values().removeAll(list);
			}
			setErrorRecoveryState(false);			
		}
		
		if ( errorCode!=null && errorCode.isStatusCode( StatusCodes.Bad_ConnectionClosed ) ) {
			setErrorRecoveryState(true);
		}
		
		boolean renew = closeError.getStatusCode().isStatusCode( StatusCodes.Bad_ConnectionClosed );
		setErrorRecoveryState( renew );
	}
	
	/**
	 * <p>onOpen.</p>
	 */
	public void onOpen() {
	};

	/**
	 * Error recover reconnect runnable
	 */
	Runnable reconnectRunnable = new Runnable() {
		public void run() {
			// Check that recover mode is still on
			synchronized(errorRecoveryLock) {
				if (!errorRecoveryState) return;
			}
			
			// Verify security channel not closed
			if (!isOpen()) {
				// Cancel recovery mode if enabled
				setErrorRecoveryState(false);
				logger.info("{}: Error recovery failed, security token has expired", secureChannelId);				
				close();
				return;
			}
			
			try {
				logger.debug("{}: Error recovery reconnect", secureChannelId);				
				getTransportChannel().open();
				createSecureChannel(true);
				
				setErrorRecoveryState(false);
				
				executor.execute(sendPendingMessagesRunnable);
				
			} catch (ServiceResultException e) {
				// C# server sends  Bad_TcpSecureChannelUnknown when it has closed the secure channel
				// C  server sends  Bad_NotFound when it has closed the secure channel
				if ( e.getStatusCode().isStatusCode( Bad_TcpSecureChannelUnknown ) ||
					 e.getStatusCode().isStatusCode( Bad_SecureChannelTokenUnknown ) || 
					 e.getStatusCode().isStatusCode( Bad_NotFound) )
				{
					logger.info(secureChannelId+": The secure channel has been closed by the server", e);
					close();		
					return;
				}
				
				// Recover attempt failed
				
				// Attempt reconnect again later
				synchronized(errorRecoveryLock) {
					errorRecoveryReconnectIndex++;
					long currentTime = System.currentTimeMillis();
					long waitTime = errorRecoveryReconnectIndex >= RECONNECT_WAIT_TIME.length ? RECONNECT_WAIT_TIME[RECONNECT_WAIT_TIME.length-1]*1000 : RECONNECT_WAIT_TIME[errorRecoveryReconnectIndex]*1000;
					long expireTime = ((long)(tokenLifetime*1.25)) + tokenIssueTime;
					if (currentTime + waitTime > expireTime) {
						logger.info("{}: Error recovery failed, security token has expired", secureChannelId);
						close();
						return;						
					}
					errorRecoveryReconnectTimer = TimerUtil.schedule(timer, reconnectRunnable, executor, currentTime + waitTime);				
				}
			}
			
		};
	};
	
	private void setErrorRecoveryState(boolean newState)
	{
		// Cancel recovery mode if enabled
		synchronized(errorRecoveryLock) {
			if (errorRecoveryState == newState) return;
			if (newState) {
				logger.info("{}: Error recovery = true", secureChannelId);
				errorRecoveryState = true;
				errorRecoveryReconnectIndex = 0;
				long currentTime = System.currentTimeMillis();
				errorRecoveryReconnectTimer = TimerUtil.schedule(timer, reconnectRunnable, executor, currentTime + RECONNECT_WAIT_TIME[0]);				
			} else {
				logger.info("{}: Error recovery = false", secureChannelId);
				errorRecoveryState = false;
				errorRecoveryReconnectIndex = 0;
				errorRecoveryReconnectTimer.cancel();
				errorRecoveryReconnectTimer = null;
			}
		}		
	}
	
	private Runnable renewSecurityTokenRunnable = new Runnable() {
		public void run() {
			try {
				logger.debug("{} Renewing security token", secureChannelId);
				createSecureChannel(true);
			} catch (ServiceResultException e) {
				logger.error(secureChannelId+" Failed to renew security token. ", e);
			}
		};
	};

	/** {@inheritDoc} */
	@Override
	public EnumSet<TransportChannelFeature> getSupportedFeatures() {
		return EnumSet.of(TransportChannelFeature.open, TransportChannelFeature.openAsync, TransportChannelFeature.close, TransportChannelFeature.closeAync, TransportChannelFeature.sendRequest, TransportChannelFeature.sendRequestAsync);
	}

	/** {@inheritDoc} */
	@Override
	public String getConnectURL() {
		return getEndpointDescription().getEndpointUrl();
	}

	/** {@inheritDoc} */
	@Override
	public ServerConnection getConnection() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public MessageSecurityMode getMessageSecurityMode() {
		return getEndpointDescription().getSecurityMode();
	}

	/** {@inheritDoc} */
	@Override
	public SecurityPolicy getSecurityPolicy() {
		try {
			return SecurityPolicy.getSecurityPolicy( getEndpointDescription().getSecurityPolicyUri() );
		} catch (ServiceResultException e) {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "SecureChannel "+secureChannelId+" "+ (isOpen()?"open":"closed");
	}
	
}
