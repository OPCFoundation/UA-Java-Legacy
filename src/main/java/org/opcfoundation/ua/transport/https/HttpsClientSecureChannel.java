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
package org.opcfoundation.ua.transport.https;

import java.util.HashMap;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.ResponseHeader;
import org.opcfoundation.ua.core.ServiceFault;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.SecureChannel;
import org.opcfoundation.ua.transport.ServerConnection;
import org.opcfoundation.ua.transport.TransportChannelSettings;
import org.opcfoundation.ua.transport.impl.AsyncResultImpl;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.StackUtils;

/**
 * <p>HttpsClientSecureChannel class.</p>
 *
 */
public class HttpsClientSecureChannel implements SecureChannel {

	static Logger logger = LoggerFactory.getLogger(HttpsClientSecureChannel.class);
	private EncoderContext ctx;

	/** SecureChannelId */
	int secureChannelId = -1;
	
	HttpsClient client;
	
	Executor executor = StackUtils.getBlockingWorkExecutor();
	
	/**
	 * <p>Constructor for HttpsClientSecureChannel.</p>
	 *
	 * @param client a {@link org.opcfoundation.ua.transport.https.HttpsClient} object.
	 */
	public HttpsClientSecureChannel(HttpsClient client) {
		this.client = client;
	}

	/** {@inheritDoc} */
	@Override
	public void initialize(String url, TransportChannelSettings tcs, EncoderContext ctx) throws ServiceResultException {
		this.ctx = ctx;
		client.initialize(url, tcs, ctx);
	}

	/** {@inheritDoc} */
	@Override
	public void initialize(TransportChannelSettings tcs, EncoderContext ctx) throws ServiceResultException {
		this.ctx = ctx;
		client.initialize(tcs.getDescription().getEndpointUrl(), tcs, ctx);
	}

	/** {@inheritDoc} */
	@Override
	public void open() throws ServiceResultException {
		logger.debug("open");
		// Create new secure channel
		if (secureChannelId==-1) {
//			try {
//				client.open();
//			} catch (ServiceResultException e) {
//				logger.warn("Connection failed: " + e.getMessage());
//				// Connection occasionally fails due to an EOFException, which
//				// is mapped to a CommunicationError. If that occurs, retry
//				// once.
//				if (e.getStatusCode().getValue().equals(Bad_CommunicationError))
//				{
//					logger.warn("Bad_CommunicationError: Retrying");
//					client.open();
//				}
//			}
			
			this.secureChannelId = client.secureChannelIdCounter.incrementAndGet();
//			createSecureChannel(false);
		}					
	}

	/** {@inheritDoc} */
	@Override
	public AsyncResult<SecureChannel> openAsync() {
 		final AsyncResultImpl<SecureChannel> result = new AsyncResultImpl<SecureChannel>();
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					open();
					result.setResult(HttpsClientSecureChannel.this);
				} catch (ServiceResultException sre) {
					result.setError(sre);
				}
			}});
		return result;
	}
	
	/** {@inheritDoc} */
	@Override
	public ServiceResponse serviceRequest(ServiceRequest request) throws ServiceResultException {
		AsyncResult<ServiceResponse> asyncResult = client.serviceRequestAsync(request, client.getTimeout(request), secureChannelId);
//		if (operationTimeout==0) {
//		res = (ServiceResponse) req.result.waitForResult();
//	} else {				
//		res = (ServiceResponse) req.result.waitForResult(req.timeoutTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
//	}
		Object result = asyncResult.waitForResult();
		if (result instanceof ServiceFault)
			throw new ServiceFaultException((ServiceFault)result);
		
		final ServiceResponse response = (ServiceResponse) result;
		logger.trace("Response: {}", response);
		logger.debug("Response: {}", response.getClass().getSimpleName());
		final ResponseHeader responseHeader = response.getResponseHeader();
		if (responseHeader.getServiceResult().isBad()) {
			logger.debug("BAD response: {}", responseHeader.getServiceResult());
			throw new ServiceFaultException(new ServiceFault(responseHeader));
		}

		return response;
	}

	/** {@inheritDoc} */
	@Override
	public AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest request) {
		return client.serviceRequestAsync(request, client.getTimeout(request), secureChannelId);
	}

	/** {@inheritDoc} */
	@Override
	public int getSecureChannelId() {
		return secureChannelId;
	}

	/** {@inheritDoc} */
	@Override
	public ServerConnection getConnection() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public MessageSecurityMode getMessageSecurityMode() {
		return client.transportChannelSettings.getDescription().getSecurityMode();
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
	public String getConnectURL() {
		return client.connectUrl;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isOpen() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void close()
	{
		if ( secureChannelId == -1 ) return;
		int scid = secureChannelId;
		secureChannelId = -1;
		
		// Close pending requests
		HashMap<Integer, HttpsClientPendingRequest> copy = new HashMap<Integer, HttpsClientPendingRequest>(client.requests);
		for (HttpsClientPendingRequest pm : copy.values() ) {
			if (pm.secureChannelId == scid) {
				pm.cancel();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public AsyncResult<SecureChannel> closeAsync() {
 		final AsyncResultImpl<SecureChannel> result = new AsyncResultImpl<SecureChannel>();
		executor.execute(new Runnable() {
			@Override
			public void run() {
				close();
				result.setResult(HttpsClientSecureChannel.this);
			}});
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		close();
		executor = null;
		client = null;
	}

	/** {@inheritDoc} */
	@Override
	public EndpointDescription getEndpointDescription() {
		return client.transportChannelSettings.getDescription();
	}

	/** {@inheritDoc} */
	@Override
	public EndpointConfiguration getEndpointConfiguration() {
		return client.transportChannelSettings.getConfiguration();
	}

	/** {@inheritDoc} */
	@Override
	public EncoderContext getMessageContext() {
		return ctx;
	}

	/** {@inheritDoc} */
	@Override
	public void setOperationTimeout(int timeout) {
		EndpointConfiguration ec = client.transportChannelSettings.getConfiguration();
		if ( ec == null ) {
			ec = new EndpointConfiguration();
			client.transportChannelSettings.setConfiguration(ec);
		}
		ec.setOperationTimeout(timeout);
	}

	/** {@inheritDoc} */
	@Override
	public int getOperationTimeout() {
		Integer i = client.transportChannelSettings.getConfiguration().getOperationTimeout();		
		return i == null ? 0 : i;
	}

	
	
	
	

	/**
	 * The time when token was issued in time system of {@link System#currentTimeMillis()}.
	 */
	//long tokenIssueTime;
	
	/**
	 * Value in milliseconds that indicates the token life time.
	 * The secure channel expires after 125% of the token life time has elapsed.
	 */
	//long tokenLifetime;

	/**
	 * This task renews security token. The task is created after security token is created,
	 * and is canceled on close. 
	 */
	//TimerTask renewSecurityTokenTask;
	
	/**
	 * This task timeouts pending requests. The task is created upon async service request.
	 * "requests" is synchronized when timeoutPendingRequests is modified.
	 */
	//AtomicReference<TimerTask> timeoutPendingRequestsTask = new AtomicReference<TimerTask>(null);
	
	/**
	 * Create or renew secure channel.
	 *  
	 * If the operation timeouts or user interrupts the thread with 
	 * {@link Thread#interrupt()} a Bad_Timeout is thrown.
	 * 
	 * @param renew false to create new secure channel, true to renew 
	 */
	/*
	private void createSecureChannel(boolean renew)
	throws ServiceResultException
	{
		if (logger.isDebugEnabled())
			logger.debug("createSecureChannel: renew="+renew);
		final long startTime = System.currentTimeMillis();
		int requestId = client.requestIdCounter.incrementAndGet();
		if (logger.isDebugEnabled())
			logger.debug("createSecureChannel: requestId="+requestId);
		
		OpenSecureChannelRequest req = new OpenSecureChannelRequest();
		
		SecurityPolicy policy = SecurityPolicy.getSecurityPolicy( client.transportChannelSettings.getDescription().getSecurityPolicyUri() );
		SecurityAlgorithm algo = policy.getAsymmetricEncryptionAlgorithm();
		int nonceLength = CryptoUtil.getNonceLength( algo );
		byte[] nonce = CryptoUtil.createNonce( nonceLength );
		
		Integer tokenLifetime = client.transportChannelSettings.getConfiguration().getSecurityTokenLifetime();
		if (tokenLifetime==null) tokenLifetime = 3600000;
		if (logger.isDebugEnabled())
			logger.debug("tokenLifetime: " + tokenLifetime);
		
		req.setClientNonce( nonce );
		req.setClientProtocolVersion( UnsignedInteger.valueOf(0) );
		req.setRequestedLifetime( UnsignedInteger.valueOf( tokenLifetime ) );
		req.setRequestType( renew ? SecurityTokenRequestType.Renew : SecurityTokenRequestType.Issue );
		req.setSecurityMode( client.transportChannelSettings.getDescription().getSecurityMode() );
		
		int chanId = renew ? this.secureChannelId : 0;

		IEncodeable msg = client.serviceRequest( req );
		if ( msg instanceof OpenSecureChannelResponse == false ) {
			if ( msg instanceof ServiceFault ) {
				throw new ServiceFaultException( (ServiceFault) msg );
			} else {
				throw new ServiceResultException( "Unexpected response "+msg );
			}
		}
		OpenSecureChannelResponse res = (OpenSecureChannelResponse) msg;
		ChannelSecurityToken token = res.getSecurityToken();
		this.secureChannelId = token.getChannelId().intValue();
		
		if (logger.isDebugEnabled())
			if (renew) {
				logger.debug(this.secureChannelId+" Secure channel renewed, SecureChannelId="+this.secureChannelId+", TokenId="+token.getTokenId().longValue());
			} else {
				logger.debug(this.secureChannelId+" Secure channel opened, SecureChannelId="+this.secureChannelId+", TokenId="+token.getTokenId().longValue());
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
			if (logger.isDebugEnabled())
				logger.debug("RevisedLifetime: " + renewTime);
			renewSecurityTokenTask = TimerUtil.schedule(client.timer, renewSecurityTokenRunnable, executor, 
					currentTime + (long)(renewTime* TcpMessageLimits.TokenRenewalPeriod ));
		}
		
	}	

	private Runnable renewSecurityTokenRunnable = new Runnable() {
		public void run() {
			try {
				if (logger.isDebugEnabled())
					logger.debug(secureChannelId+" Renewing security token");
				createSecureChannel(true);
			} catch (ServiceResultException e) {
				logger.error(secureChannelId+" Failed to renew security token. ", e);
			}
		};
	};*/
	
}
