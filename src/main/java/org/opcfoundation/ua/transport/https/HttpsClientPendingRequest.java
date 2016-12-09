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

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.nio.entity.NByteArrayEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncoderMode;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.binary.BinaryDecoder;
import org.opcfoundation.ua.encoding.binary.BinaryEncoder;
import org.opcfoundation.ua.encoding.binary.EncoderCalc;
import org.opcfoundation.ua.transport.UriUtil;
import org.opcfoundation.ua.transport.impl.AsyncResultImpl;
import org.opcfoundation.ua.transport.tcp.impl.ErrorMessage;

class HttpsClientPendingRequest implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(HttpsClientPendingRequest.class);
	
	// Client
	HttpsClient httpsClient;
	
	// System time in milliseconds
	long startTime = System.currentTimeMillis();
	
	// The time (current time) when the request timeouts
	long timeoutTime = 0;
	
	// Request identification
	int requestId;
	
	// Sync result objects
	AsyncResultImpl<ServiceResponse> result;
	
	// Request Message
	ServiceRequest requestMessage;
	
	// Post
	HttpPost httpPost;
	
	// Operation aborted
	UnsignedInteger abortCode = null;

	int secureChannelId;

	String securityPolicy;
	
	/**
	 * <p>Constructor for HttpsClientPendingRequest.</p>
	 *
	 * @param httpsClient a {@link org.opcfoundation.ua.transport.https.HttpsClient} object.
	 * @param requestMessage a {@link org.opcfoundation.ua.builtintypes.ServiceRequest} object.
	 */
	public HttpsClientPendingRequest(HttpsClient httpsClient, ServiceRequest requestMessage) {
		this.httpsClient = httpsClient;
		this.requestMessage = requestMessage;
		this.result = new AsyncResultImpl<ServiceResponse>();
		
		// Read time-out time
		UnsignedInteger timeoutHint = requestMessage.getRequestHeader() != null ? requestMessage.getRequestHeader().getTimeoutHint() : null;
		long clientTimeout = timeoutHint != null ? timeoutHint.longValue() : httpsClient.getOperationTimeout();
		if ( clientTimeout != 0 ) {
			// The time when the result should be completed
			timeoutTime = startTime + clientTimeout;
		}
		
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		try {
			// Abort exit branch
			if ( abortCode != null ) {
				result.setError( new ServiceResultException( abortCode ) );
				return;
			}
			
			// Http Post
			InetSocketAddress inetAddress = UriUtil.getSocketAddress( httpsClient.connectUrl ); 
			String host = inetAddress.getHostName();
			int port = inetAddress.getPort();
			String scheme = UriUtil.getTransportProtocol( httpsClient.connectUrl );
			HttpHost httpHost = new HttpHost(host, port, scheme);
			String url = httpsClient.transportChannelSettings.getDescription().getEndpointUrl();
			String endpointId = url == null ? "" : url; //UriUtil.getEndpointName(url);
	    	httpPost = new HttpPost( endpointId );
	    	httpPost.addHeader("OPCUA-SecurityPolicy", httpsClient.securityPolicyUri);
	    	httpPost.addHeader("Content-Type", "application/octet-stream");
	    	
			// Calculate message length
       		EncoderCalc calc = new EncoderCalc();
       		calc.setEncoderContext( httpsClient.encoderCtx );
			calc.putMessage( requestMessage );
    		int len = calc.getLength();

    		// Assert max size is not exceeded
    		int maxLen = httpsClient.encoderCtx.getMaxMessageSize();
    		if ( maxLen != 0 && len > maxLen ) {
    			final EncodingException encodingException = new EncodingException(StatusCodes.Bad_EncodingLimitsExceeded, "MaxStringLength "+maxLen+" < "+len);
    			logger.warn("run: failed", encodingException);
    			throw encodingException;
    		}
			    		
	    	// Encode message
    		byte[] data = new byte[ len ];
    		BinaryEncoder enc = new BinaryEncoder( data );
    		enc.setEncoderContext( httpsClient.encoderCtx );
    		enc.setEncoderMode( EncoderMode.NonStrict );
    		enc.putMessage( requestMessage );
    		httpPost.setEntity( new NByteArrayEntity(data) );
    		
			// Abort exit branch
			if ( abortCode != null ) {
				result.setError( new ServiceResultException( abortCode ) );
				return;
			}
			
    		// Execute Post
			
	        HttpResponse httpResponse;
			try {
				httpResponse = httpsClient.httpclient.execute( httpHost, httpPost );
			} catch (SSLPeerUnverifiedException e) {
				// Currently, TLS_1_2 is not supported by JSSE implementations, for some odd reason
				// and it will give this exception when used.
				// Also, if the server certificate is rejected, we will get this error
				result.setError( new ServiceResultException(StatusCodes.Bad_SecurityPolicyRejected, e, 
						"Could not negotiate a TLS security cipher or the server did not provide a valid certificate."));
				return;
			}
        	HttpEntity entity = httpResponse.getEntity();        	
			
	        // Error response
	        int statusCode = httpResponse.getStatusLine().getStatusCode(); 
	        if ( statusCode != 200 ) {
	        	UnsignedInteger uacode = StatusCodes.Bad_UnknownResponse;
	        	if ( statusCode == 501 ) uacode = StatusCodes.Bad_ServiceUnsupported;	        	
	        	String msg = EntityUtils.toString( entity );
	        	result.setError( new ServiceResultException( uacode, statusCode+": "+msg ) );
	        	return;
	        }
	        
			// Abort exit branch
			if ( abortCode != null ) {
				result.setError( new ServiceResultException( abortCode ) );
				return;
			}

	        // Decode Message
	        data = EntityUtils.toByteArray(entity);	        
						
			BinaryDecoder dec = new BinaryDecoder( data );			
			dec.setEncoderContext( httpsClient.encoderCtx );
			IEncodeable response = dec.getMessage();
			
			// Client sent an error
			if ( response instanceof ErrorMessage ) {
				ErrorMessage error = (ErrorMessage) response;
				ServiceResultException errorResult = new ServiceResultException(new StatusCode(error.getError()), error.getReason());
				result.setError(errorResult);
				return;
			}
		
			try {
				// Client sent a valid message
				result.setResult((ServiceResponse) response);
			} catch (ClassCastException e) {
				result.setError(new ServiceResultException(e));
				logger.error(
						"Cannot cast response to ServiceResponse, response="
								+ response.getClass(), e);
			}
		} catch (EncodingException e) {
			// Internal Error
			result.setError( new ServiceResultException( StatusCodes.Bad_EncodingError, e ) );
		} catch (ClientProtocolException e) {
			result.setError( new ServiceResultException( StatusCodes.Bad_CommunicationError, e) );
		} catch (IOException e) {
			if ( abortCode != null ) {
				result.setError( new ServiceResultException( abortCode, e ) );
			} else {
				result.setError( new ServiceResultException( StatusCodes.Bad_CommunicationError, e ) );
			}
		} catch (DecodingException e) {
			result.setError( new ServiceResultException( StatusCodes.Bad_DecodingError, e ) );
		} catch (ServiceResultException e) {
			result.setError( e );
		} catch (RuntimeException rte) {
			// http-client seems to be throwing these, IllegalArgumentException for one
			result.setError( new ServiceResultException( rte ) );
		} finally {
			httpsClient.requests.remove( requestId );
		}
	}

	/**
	 * <p>cancel.</p>
	 */
	public void cancel() {
		if ( httpsClient.requests.remove(this) == null ) return;
		abortCode = StatusCodes.Bad_RequestCancelledByRequest;
		HttpPost post = httpPost;
		if ( post != null ) post.abort();
	}
	
	/**
	 * <p>timeout.</p>
	 */
	public void timeout() {
		if ( httpsClient.requests.remove( requestId ) == null ) return;
		abortCode = StatusCodes.Bad_Timeout;
		HttpPost post = httpPost;
		if ( post != null ) post.abort();
		result.setError( new ServiceResultException( abortCode ) );
	}
	
}
