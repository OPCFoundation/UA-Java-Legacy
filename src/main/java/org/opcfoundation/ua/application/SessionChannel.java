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

package org.opcfoundation.ua.application;

import java.security.interfaces.RSAPrivateKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ActivateSessionRequest;
import org.opcfoundation.ua.core.ActivateSessionResponse;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.IssuedIdentityToken;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.core.UserIdentityToken;
import org.opcfoundation.ua.core.UserTokenPolicy;
import org.opcfoundation.ua.core.X509IdentityToken;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.ChannelService;
import org.opcfoundation.ua.transport.RequestChannel;
import org.opcfoundation.ua.transport.ResultListener;
import org.opcfoundation.ua.transport.SecureChannel;
import org.opcfoundation.ua.transport.impl.AsyncResultImpl;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.EndpointUtil;
import org.opcfoundation.ua.utils.bytebuffer.ByteBufferUtils;


/**
 * Session channel is a request channel of an active session.
 * <p>
 * It is fully safe to use session channel from different thread simultaneosly.
 * <p>
 * Session channel adds authentication token and time stamp to all
 * requests.
 *
 * TODO Keep-Alive
 */
public class SessionChannel extends ChannelService implements RequestChannel {

	/**
	 * Log4J Error logger. 
	 * Security settings are logged with DEBUG level.
	 * Unexpected errors are logged with ERROR level. 
	 */
	static Logger LOGGER = LoggerFactory.getLogger(SessionChannel.class);
	
	/** Client */
	Client client;
	/** Session */
	Session session;
	/** Service Channel */
	SecureChannel channel;

	/**
	 * <p>Constructor for SessionChannel.</p>
	 *
	 * @param client a {@link org.opcfoundation.ua.application.Client} object.
	 * @param session a {@link org.opcfoundation.ua.application.Session} object.
	 * @param channel a {@link org.opcfoundation.ua.transport.SecureChannel} object.
	 */
	public SessionChannel(Client client, Session session, SecureChannel channel) {
		super();
		this.session = session;
		this.client = client;
		this.channel = channel;
		setRequestChannel(this);
	}
	
	/**
	 * Activate session using anonymous access.
	 *
	 * @return response
	 * @throws org.opcfoundation.ua.common.ServiceResultException if error
	 */
	public ActivateSessionResponse activate()
	throws ServiceResultException
	{
		UserIdentityToken token = EndpointUtil.createAnonymousIdentityToken(session.getEndpoint());
		return activate(token, null);	
	}
	
	/**
	 * Activate session using user name and password.
	 *
	 * @param username user name
	 * @param password user password
	 * @return response
	 * @throws org.opcfoundation.ua.common.ServiceResultException if error
	 */
	public ActivateSessionResponse activate(String username, String password) throws ServiceResultException
	{
		UserIdentityToken token = EndpointUtil.createUserNameIdentityToken(session.getEndpoint(), session.getServerNonce(), username, password);
		return activate(token, null);	
	}

	/**
	 * Activate session using identity token.
	 *
	 * @param issuedIdentityToken token
	 * @return response
	 * @throws org.opcfoundation.ua.common.ServiceResultException if error
	 */
	public ActivateSessionResponse activate(byte[] issuedIdentityToken) throws ServiceResultException
	{
		UserIdentityToken token = EndpointUtil.createIssuedIdentityToken(session.getEndpoint(), session.getServerNonce(), issuedIdentityToken);
		return activate(token, null);	
	}
	
	
	/**
	 * Activate session.
	 *
	 * @param identity user identity, see {@link EndpointUtil#createIssuedIdentityToken(EndpointDescription, byte[], byte[])}
	 * @param identitySignature used with {@link X509IdentityToken} and {@link IssuedIdentityToken} ?
	 * @param identitySignature used with {@link X509IdentityToken} and {@link IssuedIdentityToken} ?
	 * @return response
	 * @throws org.opcfoundation.ua.common.ServiceResultException if error
	 */
	public ActivateSessionResponse activate(UserIdentityToken identity, SignatureData identitySignature) throws ServiceResultException
	{
		if (channel==null || identity==null)
			throw new IllegalArgumentException("null arg");
		
		// Select user token policy, ensure the server has the requested policy
		final String policyId = identity.getPolicyId();
		if (policyId != null) {
			UserTokenPolicy userTokenPolicy = session.getEndpoint()
					.findUserTokenPolicy(policyId);
			if (userTokenPolicy == null)
				throw new ServiceResultException("UserIdentityPolicy \""
						+ policyId
						+ "\" is not supported by the given endpoint");
		}
		// 1. Sign certificate + nonce
		SignatureData clientSignature = null;
		if (!MessageSecurityMode.None.equals(channel.getMessageSecurityMode())) {
			SecurityPolicy securityPolicy = channel.getSecurityPolicy();
			RSAPrivateKey signerKey = session.getClientPrivateKey()
					.getPrivateKey();
			SecurityAlgorithm algorithm = securityPolicy
					.getAsymmetricSignatureAlgorithm();
			byte[] dataToSign = session.getServerCertificate().getEncoded();
			if (session.getServerNonce() != null)
				dataToSign = ByteBufferUtils.concatenate(dataToSign,
						session.getServerNonce());
			
			clientSignature = new SignatureData(algorithm.getUri(), ByteString.valueOf(CryptoUtil.getCryptoProvider().signAsymm(signerKey, algorithm, dataToSign)));

		}
		// 2. Activate Session
		ActivateSessionRequest asreq = new ActivateSessionRequest();
		asreq.setLocaleIds( client.getApplication().getLocaleIds() );
		asreq.setClientSoftwareCertificates(client.getApplication().getSoftwareCertificates());
		asreq.setClientSignature( clientSignature );
		asreq.setUserIdentityToken( ExtensionObject.binaryEncode( identity, client.getEncoderContext() ) );
		asreq.setUserTokenSignature( identitySignature );				
		
		ActivateSessionResponse asres = ActivateSession(asreq);
		session.serverNonce = ByteString.asByteArray(asres.getServerNonce());
		return asres;
	}	
	
	/**
	 * Get the session.
	 *
	 * @return session
	 */
	public Session getSession()
	{
		return session;
	}
	
	/**
	 * Get secure channel.
	 *
	 * @return secure channel
	 */
	public SecureChannel getSecureChannel()
	{
		return channel;
	}
	
	/**
	 * Close the session and the secure channel.
	 * Subscriptions are deleted.
	 *
	 * This convenience method logs the errors to default Logger
	 * but doesn't throw exceptions. Rationale is that in typical
	 * case, the client cannot handle close errors.
	 *
	 * To capture errors use {@link #CloseSession(RequestHeader, Boolean)} and {@link #closeSecureChannel()}.
	 *
	 * @throws org.opcfoundation.ua.common.ServiceResultException if error
	 * @throws org.opcfoundation.ua.common.ServiceFaultException if error
	 */
	public void close() throws ServiceFaultException, ServiceResultException {		
		CloseSession(null, true);
		closeSecureChannel();
	}
	
	/**
	 * Close the session and the secure channel.
	 * Subscriptions are deleted.
	 *
	 * This convenience method logs the errors to default Logger
	 * but doesn't throw exceptions. Rationale is that in typical
	 * case, the client cannot handle close errors.
	 *
	 * To capture errors use {@link #close()}.
	 */
	public void closeUnsafe() {
		try {
			close();
		} catch (ServiceResultException e) {
			LOGGER.error("Failed to close session channel", e);
		}
	}
	
	/**
	 * Close the underlying secure channel.
	 */
	public void closeSecureChannel() {
		channel.close();
	}
	
	/**
	 * Close the session and the secure channel asynchronously.
	 * <p>
	 * Use {@link #close()} to close the session before
	 * closing the session.
	 *
	 * @return async result of the operation
	 */
	public AsyncResult<SecureChannel> closeAsync() {
		final AsyncResultImpl<SecureChannel> result = new AsyncResultImpl<SecureChannel>();
		AsyncResult<ServiceResponse> r = CloseSessionAsync(null, true);
		r.setListener(new ResultListener<ServiceResponse>() {
			@Override
			public void onCompleted(ServiceResponse x) {
				AsyncResult<SecureChannel> res = channel.closeAsync();
				result.setSource(res);
			}
			@Override
			public void onError(ServiceResultException error) {
				AsyncResult<SecureChannel> res = channel.closeAsync();
				result.setSource(res);
			}});
		return result;
	}

	/**
	 * <p>dispose.</p>
	 */
	public void dispose() {
//		close();		
		channel.close();
		channel.dispose();
		channel = null;
		session = null;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Invoke session service request.
	 * <p>
	 * AuthenticationToken and Timestamp is added to RequestHeader.
	 * </p>
	 *
	 * If the operation timeouts or the thread is interrupted a
	 * ServiceResultException is thrown with {@link StatusCodes#Bad_Timeout}
	 */
	public IEncodeable serviceRequest(ServiceRequest serviceRequest) throws ServiceResultException
	{
		ServiceRequest req = serviceRequest;		
		RequestHeader rh = req.getRequestHeader();
		if (rh==null)
			req.setRequestHeader( rh = new RequestHeader() );
		
		rh.setAuthenticationToken(session.getAuthenticationToken());
		rh.setTimestamp( new DateTime() );
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("serviceRequest: Request={} SecureChannelId={}", serviceRequest.getClass().getSimpleName(), channel.getSecureChannelId());
		return channel.serviceRequest(req);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Asynchronous operation to send a request over the secure channel.
	 *
	 * Invoke session service request.
	 * <p>
	 * AuthenticationToken and Timestamp is added to RequestHeader.
	 */
	public AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest request) {
		RequestHeader rh = request.getRequestHeader();
		if (rh==null)
			request.setRequestHeader( rh = new RequestHeader() );
		
		rh.setAuthenticationToken(session.getAuthenticationToken());
		rh.setTimestamp( new DateTime() );
		return channel.serviceRequestAsync(request);
	}

	
}
