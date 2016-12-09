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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.EndpointBinding;
import org.opcfoundation.ua.transport.ServerConnection;
import org.opcfoundation.ua.transport.endpoint.AbstractServerSecureChannel;
import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.transport.tcp.nio.PendingRequest;
import org.opcfoundation.ua.utils.StackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>HttpsServerSecureChannel class.</p>
 *
 */
public class HttpsServerSecureChannel extends AbstractServerSecureChannel {
	
	/**
	 * <p>getLocalCertificate.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public KeyPair getLocalCertificate() {
		return null;
	}
	
	/**
	 * <p>getRemoteCertificate.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 */
	public Cert getRemoteCertificate() {
		return null;
	}

	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(HttpsServerSecureChannel.class);
	/** Endpoint binding */
	private EndpointBinding endpointBinding;
	/** Https Endpoint Handler */
	private HttpsServerEndpointHandler httpsEndpointHandler;
	/** The connection object that delivered the last message for this secure channel */
	private volatile HttpsServerConnection lastConnection;
	
	/**
	 * List on pending requests. All reads and writes are done by synchronizing to the
	 * requests object. 
	 */
	Map<Integer, PendingRequest> requests = new ConcurrentHashMap<Integer, PendingRequest>();
	
	/**
	 * <p>Constructor for HttpsServerSecureChannel.</p>
	 *
	 * @param httpsEndpointHandler a {@link org.opcfoundation.ua.transport.https.HttpsServerEndpointHandler} object.
	 * @param secureChannelId a int.
	 */
	public HttpsServerSecureChannel(HttpsServerEndpointHandler httpsEndpointHandler, int secureChannelId) {
		super(secureChannelId);
		this.endpointBinding = httpsEndpointHandler.endpointBinding;
		this.httpsEndpointHandler = httpsEndpointHandler;
	}

	/**
	 * <p>getMessageSecurityMode.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.MessageSecurityMode} object.
	 */
	public MessageSecurityMode getMessageSecurityMode() {
		return MessageSecurityMode.None;
	}

	/**
	 * <p>getSecurityPolicy.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.SecurityPolicy} object.
	 */
	public SecurityPolicy getSecurityPolicy() {
		return SecurityPolicy.NONE;
	}	
		
	/** {@inheritDoc} */
	public synchronized void setError(ServiceResultException e) {
		super.setError( e );
	}

	/** {@inheritDoc} */
	@Override
	protected void onListenerException(RuntimeException rte) {
		setError( StackUtils.toServiceResultException(rte) );
	}

	/** {@inheritDoc} */
	@Override
	public ServerConnection getConnection() {
		return lastConnection;
	}
	/**
	 * <p>setConnection.</p>
	 *
	 * @param connection a {@link org.opcfoundation.ua.transport.https.HttpsServerConnection} object.
	 */
	public void setConnection(HttpsServerConnection connection) {
		lastConnection = connection;
	}

	/**
	 * <p>getConnectURL.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getConnectURL() {
		return endpointBinding.endpointAddress.getEndpointUrl();
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
	}

	/** {@inheritDoc} */
	@Override
	public void getPendingServiceRequests(Collection<EndpointServiceRequest<?, ?>> result) {
		Map<Integer, HttpsServerPendingRequest> snapshot = new HashMap<Integer, HttpsServerPendingRequest>( httpsEndpointHandler.pendingRequests );
		for (HttpsServerPendingRequest req : snapshot.values()) {
			if ( req.secureChannelId == getSecureChannelId() )
				result.add( req );
		}
	}

	/** {@inheritDoc} */
	@Override
	public Endpoint getEndpoint() {
		return endpointBinding.endpointAddress;
	}

	/** {@inheritDoc} */
	@Override
	public Server getServer() {
		return endpointBinding.serviceServer;
	}

	/** {@inheritDoc} */
	@Override
	public boolean needsCertificate() {
		return true;
	}

	
	// Removed - There are no proper secure channels in https //

	/*
	void handleOpenChannel(HttpsServerPendingRequest msgExchange, OpenSecureChannelRequest req) throws ServiceResultException {
		
		setState(CloseableObjectState.Opening);
		
		securityPolicy		= SecurityPolicy.getSecurityPolicy( msgExchange.securityPolicyUri );
		MessageSecurityMode messageMode		= req.getSecurityMode();
		SecurityMode securityMode			= new SecurityMode(securityPolicy, messageMode);
		if (!getEndpoint().supportsSecurityMode(securityMode)) {
			log.warn("The requested MessageSecurityMode("+messageMode+") is not supported by the endpoint");
			throw new ServiceResultException("The requested MessageSecurityMode("+messageMode+") is not supported by the endpoint");
		}
		
		securityConfiguration				= 
				new SecurityConfiguration(
					securityMode,
					endpointBinding.serviceServer.getApplicationInstanceCertificate(),
					null);
		
		SecurityToken token = createToken(msgExchange, req);		
		
		
		ChannelSecurityToken chanToken		= new ChannelSecurityToken();
		chanToken.setChannelId( UnsignedInteger.valueOf(secureChannelId) );
		chanToken.setCreatedAt( new DateTime() );
		chanToken.setRevisedLifetime(UnsignedInteger.valueOf(token.getLifeTime()));
		chanToken.setTokenId(UnsignedInteger.valueOf(token.getTokenId()));

		setState(CloseableObjectState.Open);	
		
		OpenSecureChannelResponse res = new OpenSecureChannelResponse();
		res.setResponseHeader(new ResponseHeader());
		res.setSecurityToken(chanToken);
		res.setServerNonce(token.getLocalNonce());
		res.setServerProtocolVersion( UnsignedInteger.valueOf( 0 ) );
		tokens.put( token.getTokenId(), token );
		setActiveSecurityToken(token);				
		msgExchange.sendResponse(res);
	}
	
	void handleRenewSecureChannelRequest(HttpsServerPendingRequest msgExchange, OpenSecureChannelRequest req) throws ServiceResultException {
		if ( !getState().isOpen() ) {
			msgExchange.sendException( new ServiceResultException( StatusCodes.Bad_SecureChannelClosed, "Failed to renew token, secure channel has already been closed." ) );
			return;
		}
		
		SecurityToken token = createToken(msgExchange, req);		
		ChannelSecurityToken chanToken		= new ChannelSecurityToken();
		chanToken.setChannelId( UnsignedInteger.valueOf(secureChannelId) );
		chanToken.setCreatedAt( new DateTime() );
		chanToken.setRevisedLifetime(UnsignedInteger.valueOf(token.getLifeTime()));
		chanToken.setTokenId(UnsignedInteger.valueOf(token.getTokenId()));
		
		
		OpenSecureChannelResponse res = new OpenSecureChannelResponse();
		res.setSecurityToken(chanToken);
		res.setServerNonce(token.getLocalNonce());
		res.setServerProtocolVersion( UnsignedInteger.valueOf( 0 ) );
		
		UnsignedInteger reqHandle = req.getRequestHeader() == null ? null : req.getRequestHeader().getRequestHandle();
		ResponseHeader header = new ResponseHeader();
		res.setResponseHeader( header );
		header.setRequestHandle( reqHandle );
		
		tokens.put( token.getTokenId(), token );
		msgExchange.sendResponse(res);
	}

	void handleCloseChannel(HttpsServerPendingRequest msgExchange, CloseSecureChannelRequest req) {
		close();	
		CloseSecureChannelResponse res = new CloseSecureChannelResponse();
		ResponseHeader header = new ResponseHeader();
		header.setRequestHandle( req.getRequestHeader().getRequestHandle() );
		res.setResponseHeader( header );
		msgExchange.sendResponse( res );
	}
	Map<Integer, SecurityToken> tokens = new ConcurrentHashMap<Integer, SecurityToken>();
	SecurityToken			activeToken;
	NodeId 				authenticationToken;

	public synchronized SecurityToken getSecurityToken(int tokenId) {
		if (log.isDebugEnabled())
			log.debug("tokens("+tokens.size()+")="+tokens.values());
		return tokens.get(tokenId);
	}
	
	private void pruneInvalidTokens()
	{	
		if (log.isDebugEnabled())
			log.debug("pruneInvalidTokens: tokens("+tokens.size()+")="+tokens.values());
		for (SecurityToken t : tokens.values())
			if (!t.isValid()) {
				if (log.isDebugEnabled())
					log.debug("pruneInvalidTokens: remove Id="+t.getTokenId());
				tokens.remove(t.getTokenId());
			}
	}	
	public synchronized SecurityToken getLatestNonExpiredToken()
	{
		SecurityToken result = null;
		for (SecurityToken t : tokens.values())
		{
			if (t.isExpired()) continue;
			if (result==null) result = t;
			if (t.getCreationTime() > result.getCreationTime()) result = t;
		}
		return result;
	}
	
	
	private SecurityToken createToken(HttpsServerPendingRequest msgExchange, OpenSecureChannelRequest req) throws ServiceResultException
	{
		byte[] clientNonce					= req.getClientNonce();
		int tokenId							= tokenIdCounter.incrementAndGet();				
		
		SecurityAlgorithm algo = securityPolicy.getAsymmetricEncryptionAlgorithm();
		int nonceLength = CryptoUtil.getNonceLength( algo );
		byte[] serverNonce = CryptoUtil.createNonce( nonceLength );
		
		final UnsignedInteger tokenLifetime = 
			req.getRequestedLifetime() != null && req.getRequestedLifetime().intValue() > 0 
				? req.getRequestedLifetime() 
				: StackUtils.SERVER_GIVEN_TOKEN_LIFETIME;
		log.debug("tokenLifetime: "+tokenLifetime);
		SecurityToken token = new SecurityToken(
				securityConfiguration, 
				secureChannelId,
				tokenId,
				System.currentTimeMillis(),
				tokenLifetime.longValue(),
				serverNonce,
				clientNonce
				);
		tokens.put(tokenId, token);

		return token;
	}
	
	public SecurityToken getActiveSecurityToken() {
		return activeToken;
	}
	
	public void setActiveSecurityToken(SecurityToken token) {
		if (token==null) 
			throw new IllegalArgumentException("null");
		if (log.isDebugEnabled())
			log.debug("Switching to new security token "+token.getTokenId());
		this.activeToken = token;
		pruneInvalidTokens();
	}
	public MessageSecurityMode getMessageSecurityMode() {
		SecurityToken token = getActiveSecurityToken();
		return token==null ? null : token.getMessageSecurityMode();
	}

	public SecurityPolicy getSecurityPolicy() {
		SecurityToken token = getActiveSecurityToken();
		return token==null ? null : token.getSecurityPolicy();
	}	
	public NodeId getAuthenticationToken() {
		return authenticationToken;
	}

	*/
}
