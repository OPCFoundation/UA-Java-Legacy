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

import static org.opcfoundation.ua.core.StatusCodes.Bad_Timeout;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.binary.IEncodeableSerializer;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.TransportChannelSettings;
import org.opcfoundation.ua.transport.UriUtil;
import org.opcfoundation.ua.transport.security.HttpsSecurityPolicy;
import org.opcfoundation.ua.transport.tcp.io.ITransportChannel;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.utils.StackUtils;
import org.opcfoundation.ua.utils.TimerUtil;

/**
 * Https Opc-Ua Client connection to an endpoint.
 */
public class HttpsClient implements ITransportChannel {

	static final ServiceResultException BAD_TIMEOUT = new ServiceResultException( Bad_Timeout );
	static final Charset UTF8 = Charset.forName("UTF-8");
	
	/**
	 * Log4J Error logger. 
	 * Security settings are logged with DEBUG level.
	 * Unexpected errors are logged with ERROR level. 
	 */
	static final Logger logger = LoggerFactory.getLogger(HttpsClient.class);

	/** Request Id Counter */
	AtomicInteger requestIdCounter = new AtomicInteger( 0 /*StackUtils.RANDOM.nextInt()*/ );
	
	/** Transport channel settings */
	TransportChannelSettings transportChannelSettings;
	/** Connect Url */
	String connectUrl;
	/** Security policy */
	HttpsSecurityPolicy securityPolicy;
	
	/** Executor */
	Executor executor = StackUtils.getBlockingWorkExecutor();
	
	/** http-code scheme registry */
	SchemeRegistry sr;
	/** Client connection manager */
	ClientConnectionManager ccm;
	/** Max connections */
	int maxConnections = 20;
	/** HttpClient */
	DefaultHttpClient httpclient;		
	
    /** Protocol */
    String protocol;
    
	/** Serializer */
	IEncodeableSerializer serializer;

	/** Security Policy */
	String securityPolicyUri;	
	
	/**
	 * List on pending requests. All reads and writes are done by synchronizing to the
	 * requests object. 
	 */
	Map<Integer, HttpsClientPendingRequest> requests = new ConcurrentHashMap<Integer, HttpsClientPendingRequest>();

	/**
	 * Timer that schedules future tasks 
	 */
	Timer timer;
	
	/**
	 * This task timeouts pending requests. The task is created upon async service request.
	 * "requests" is synchronized when timeoutPendingRequests is modified.
	 */
	AtomicReference<TimerTask> timeoutPendingRequestsTask = new AtomicReference<TimerTask>(null);
	
	/** Encoder Context */
	EncoderContext encoderCtx;
	
	AtomicInteger secureChannelIdCounter = new AtomicInteger(); 

	/** Selection of cipher suites, an intersecion of available and the suites in the algorithm */ 
	String[] cipherSuites;
	
	/**
	 * <p>Constructor for HttpsClient.</p>
	 *
	 * @param protocol a {@link java.lang.String} object.
	 */
	public HttpsClient(String protocol) {
		if ( !protocol.equals( UriUtil.SCHEME_HTTP ) && !protocol.equals( UriUtil.SCHEME_HTTPS ) ) throw new IllegalArgumentException();
		this.protocol = protocol;
	}
	
	/**
	 * Set client connection manager. Call before #initialize.
	 * If ClientConnectionManager is not set, a default implementation is used
	 *
	 * @param ccm a {@link org.apache.http.conn.ClientConnectionManager} object.
	 */
	public void setClientConnectionManager(ClientConnectionManager ccm)
	{
		this.ccm = ccm;
	}
	
	/**
	 * Set the number of concurrent maximum connections. Call this before calling #initialize.
	 * This value applies only if ClientConnectionManager has not been overridden.
	 *
	 * @param maxConnections a int.
	 */
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Initialize HttpsClient.
	 */
	public void initialize(String connectUrl, TransportChannelSettings tcs, EncoderContext ctx) throws ServiceResultException {
		
		this.connectUrl = connectUrl;
		this.securityPolicyUri = tcs.getDescription().getSecurityPolicyUri();
		this.transportChannelSettings = tcs;
		HttpsSettings httpsSettings = tcs.getHttpsSettings();
		HttpsSecurityPolicy[] policies = httpsSettings.getHttpsSecurityPolicies();
		if (policies != null && policies.length > 0)
			securityPolicy = policies[policies.length-1];
		else
			securityPolicy = HttpsSecurityPolicy.TLS_1_1;
		// securityPolicy = SecurityPolicy.getSecurityPolicy( this.securityPolicyUri );
		if ( securityPolicy != HttpsSecurityPolicy.TLS_1_0 &&
				securityPolicy != HttpsSecurityPolicy.TLS_1_1 &&
				securityPolicy != HttpsSecurityPolicy.TLS_1_2  &&
				securityPolicy != HttpsSecurityPolicy.TLS_1_2_PFS)
				throw new ServiceResultException( StatusCodes.Bad_SecurityChecksFailed, "Https Client doesn't support securityPolicy "+securityPolicy );
		if (logger.isDebugEnabled()) {
			logger.debug("initialize: url={}; settings={}", tcs.getDescription().getEndpointUrl(), ObjectUtils.printFields(tcs));
		}
			
    	// Setup Encoder
		EndpointConfiguration endpointConfiguration = tcs.getConfiguration();
		encoderCtx = ctx;
		encoderCtx.setMaxArrayLength( endpointConfiguration.getMaxArrayLength() != null ? endpointConfiguration.getMaxArrayLength() : 0 );
		encoderCtx.setMaxStringLength( endpointConfiguration.getMaxStringLength() != null ? endpointConfiguration.getMaxStringLength() : 0 );
		encoderCtx.setMaxByteStringLength( endpointConfiguration.getMaxByteStringLength() != null ? endpointConfiguration.getMaxByteStringLength() : 0 );
		encoderCtx.setMaxMessageSize( endpointConfiguration.getMaxMessageSize()!=null ? endpointConfiguration.getMaxMessageSize() : 0 );
		
		timer = TimerUtil.getTimer();
		try {
			SchemeRegistry sr = new SchemeRegistry();
			if ( protocol.equals( UriUtil.SCHEME_HTTPS ) ) {
		        
			  SSLContext sslcontext;
			  
			  /*
			   * Try first create tls 1.2 supporting context.
			   * This should work at least on java 8 out of the box.
			   * Might work on java 7 if tls 1.2 is enabled.
			   */
			  try{
			    sslcontext = SSLContext.getInstance("TLSv1.2");
			  }catch(NoSuchAlgorithmException noSuchAlgorithmException){
			    //fallback option
			    logger.debug("No TLSv1.2 implementation found, trying TLS");
			    sslcontext = SSLContext.getInstance("TLS");
			  }
		       
		        
		        sslcontext.init( httpsSettings.getKeyManagers(), httpsSettings.getTrustManagers(), null );
				X509HostnameVerifier hostnameVerifier = httpsSettings.getHostnameVerifier() != null ? 
						httpsSettings.getHostnameVerifier() : SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
				SSLSocketFactory sf = new SSLSocketFactory( sslcontext,	hostnameVerifier) {
					protected void prepareSocket(javax.net.ssl.SSLSocket socket) throws IOException {
						socket.setEnabledCipherSuites( cipherSuites );
					};
				};
		        
				SSLEngine sslEngine = sslcontext.createSSLEngine();
				String[] enabledCipherSuites = sslEngine.getEnabledCipherSuites();
				cipherSuites = CryptoUtil.filterCipherSuiteList(enabledCipherSuites, securityPolicy.getCipherSuites());		    
				
				logger.info( "Enabled protocols in SSL Engine are {}", Arrays.toString( sslEngine.getEnabledProtocols()));
				logger.info( "Enabled CipherSuites in SSL Engine are {}", Arrays.toString( enabledCipherSuites ) );
				logger.info( "Client CipherSuite selection for {} is {}", securityPolicy.getPolicyUri(), Arrays.toString( cipherSuites ));

				Scheme https = new Scheme("https", 443, sf);
				sr.register(https);
			}
			
			if ( protocol.equals( UriUtil.SCHEME_HTTP ) ) {
				Scheme http = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
				sr.register(http);
			}

			if ( ccm == null ) {
				PoolingClientConnectionManager pccm = new PoolingClientConnectionManager(sr);
				ccm = pccm;
				pccm.setMaxTotal( maxConnections );
				pccm.setDefaultMaxPerRoute( maxConnections );
			}
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, transportChannelSettings.getConfiguration().getOperationTimeout());
			HttpConnectionParams.setSoTimeout(httpParams, 0);
			httpclient = new DefaultHttpClient(ccm, httpParams);		
			
			// Set username and password authentication
			if ( httpsSettings.getUsername()!=null && httpsSettings.getPassword()!=null ) {
				BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
	        	credsProvider.setCredentials(
	        	    new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
	        	    new UsernamePasswordCredentials(httpsSettings.getUsername(), httpsSettings.getPassword()));
	        	httpclient.setCredentialsProvider( credsProvider );
			}

		} catch (NoSuchAlgorithmException e) {
			new ServiceResultException( e );
		} catch (KeyManagementException e) {
			new ServiceResultException( e );
		}
		
	}
	
	long getTimeout(ServiceRequest serviceRequest) {
		UnsignedInteger timeoutHint = serviceRequest.getRequestHeader() != null ? serviceRequest.getRequestHeader().getTimeoutHint() : null;
		long clientTimeout = timeoutHint != null ? timeoutHint.longValue() : getOperationTimeout();
		if ( clientTimeout == 0 ) clientTimeout = 100000L;
		return clientTimeout;
	}
	
	/** {@inheritDoc} */
	@Override
	public ServiceResponse serviceRequest(ServiceRequest request) throws ServiceResultException {
		return serviceRequest(request, getTimeout(request));
	}
	
	/** {@inheritDoc} */
	@Override
	public ServiceResponse serviceRequest(ServiceRequest request, long operationTimeout) throws ServiceResultException {
		AsyncResult<ServiceResponse> result = serviceRequestAsync( request );
		return (ServiceResponse) result.waitForResult(operationTimeout, TimeUnit.MILLISECONDS);
	}

	/** {@inheritDoc} */
	@Override
	public AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest serviceRequest) {
		return serviceRequestAsync(serviceRequest, getTimeout(serviceRequest));
	}
	
	/** {@inheritDoc} */
	@Override
	public AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest serviceRequest, long operationTimeout) {
		return serviceRequestAsync( serviceRequest, operationTimeout, -1);
	}

	/**
	 * <p>serviceRequestAsync.</p>
	 *
	 * @param serviceRequest a {@link org.opcfoundation.ua.builtintypes.ServiceRequest} object.
	 * @param operationTimeout a long.
	 * @param secureChannelId a int.
	 * @return a {@link org.opcfoundation.ua.transport.AsyncResult} object.
	 */
	public AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest serviceRequest, long operationTimeout, int secureChannelId) {
		HttpsClientPendingRequest pendingRequest = new HttpsClientPendingRequest(this, serviceRequest);
		pendingRequest.secureChannelId = secureChannelId;
		pendingRequest.securityPolicy = securityPolicyUri;
		pendingRequest.requestId = requestIdCounter.getAndIncrement();
		
		logger.debug("serviceRequestAsync: Sending message, requestId={} message={} operationTimeout={}", pendingRequest.requestId, serviceRequest.getClass().getSimpleName(), operationTimeout);
		
		logger.trace("serviceRequestAsync: message={}", serviceRequest);
		
		requests.put( pendingRequest.requestId, pendingRequest );
		if (pendingRequest.startTime!=0) scheduleTimeoutRequestsTimer();
		executor.execute( pendingRequest );
		return pendingRequest.result;
	}
	
	/**
	 * <p>close.</p>
	 */
	public void close() {
		ccm.shutdown();
				
		// Cancel all pending requests
		{
			Collection<HttpsClientPendingRequest> copy;
				
			// Cancel timeout task
			cancelTimeoutPendingRequestTask();

			// TODO: Is this thread safe? Does it have to be? Should requests be a BlockingQueue?
			
//			if (requests.isEmpty())
//				copy = Collections.emptyList();
//			else
			synchronized(requests) {
				copy = new ArrayList<HttpsClientPendingRequest>(requests.values());
				logger.debug("requests.clear()");
				requests.clear();
			}

			if (!copy.isEmpty()) {
				for (HttpsClientPendingRequest pr : copy) {
					pr.cancel();
				}
			}
		}		
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		close();
		ccm = null;
		sr = null;
		httpclient = null;
		serializer = null;
		transportChannelSettings = null;
	}

	/** {@inheritDoc} */
	@Override
	public EnumSet<TransportChannelFeature> getSupportedFeatures() {
		return EnumSet.of(
				TransportChannelFeature.open, 
				TransportChannelFeature.openAsync, 
				TransportChannelFeature.close, 
				TransportChannelFeature.closeAync, 
				TransportChannelFeature.sendRequest, 
				TransportChannelFeature.sendRequestAsync);
	}

	/** {@inheritDoc} */
	@Override
	public EndpointDescription getEndpointDescription() {
		return transportChannelSettings.getDescription();
	}

	/** {@inheritDoc} */
	@Override
	public EndpointConfiguration getEndpointConfiguration() {
		return transportChannelSettings.getConfiguration();
	}

	/** {@inheritDoc} */
	@Override
	public EncoderContext getMessageContext() {
		return encoderCtx;
	}

	/** {@inheritDoc} */
	@Override
	public void setOperationTimeout(int timeout) {
		transportChannelSettings.getConfiguration().setOperationTimeout(timeout);
	}

	/** {@inheritDoc} */
	@Override
	public int getOperationTimeout() {
		Integer i = transportChannelSettings.getConfiguration().getOperationTimeout();		
		return i == null ? 0 : i;
	}

	
	
	
	

	/**
	 * Sets new Timer Task that timeouts pending requests.
	 * If task already exists but is too far in the future, it is canceled and new task assigned
	 */
	private void scheduleTimeoutRequestsTimer()
	{
		HttpsClientPendingRequest nextRequest = _getNextTimeoutingPendingRequest();
		
		// Cancel task
		if (nextRequest == null) {
			cancelTimeoutPendingRequestTask();
		} else {
			TimerTask task = timeoutPendingRequestsTask.get();
			// Task does not exists or is not ok
			if (task == null || task.scheduledExecutionTime() > nextRequest.timeoutTime) {
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
			synchronized(requests) {
				long currentTime = System.currentTimeMillis();
				for (HttpsClientPendingRequest req : requests.values()) {
					if (req.timeoutTime!=0 && currentTime >= req.timeoutTime) {
						long elapsed = System.currentTimeMillis()-req.startTime;
						long timeOutAt = req.timeoutTime - req.startTime;
						logger.warn("Request id={} msg={} timeouted {} ms elapsed. timeout at {} ms", req.requestId, req.requestMessage.getClass(), elapsed, timeOutAt);
						req.timeout();
					}
				}
			}
			// Schedule next timeout event
			scheduleTimeoutRequestsTimer();
		}};

	private void cancelTimeoutPendingRequestTask() {
		TimerTask task = timeoutPendingRequestsTask.getAndSet(null);
		if (task !=null) {
			task.cancel();
		}
	}
		
		
	/**
	 * Get the next request that is closest to timeout
	 * 
	 * @return null or request
	 */
	private HttpsClientPendingRequest _getNextTimeoutingPendingRequest()
	{
		long next = Long.MAX_VALUE;
		HttpsClientPendingRequest result = null;
		synchronized (requests) {
			for (HttpsClientPendingRequest req : requests.values()) {
				if (next > req.timeoutTime) {
					next = req.timeoutTime;
					result = req;
					break;
				}
			}
		}
		return result;
	}
	
	/** Constant <code>ALLOW_ALL_HOSTNAME_VERIFIER</code> */
	public final static X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = 
			new X509HostnameVerifier() {
		        public boolean verify(String arg0, SSLSession arg1) { return true; }
		        public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
		        public void verify(String arg0, X509Certificate arg1) throws SSLException {}
		        public void verify(String arg0, SSLSocket arg1) throws IOException {}
    		};		
	
}
