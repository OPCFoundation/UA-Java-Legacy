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
package org.opcfoundation.ua.transport.endpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.CloseableObjectState;
import org.opcfoundation.ua.transport.ServerSecureChannel;
import org.opcfoundation.ua.transport.impl.AsyncResultImpl;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.transport.tcp.impl.SecurityToken;
import org.opcfoundation.ua.utils.AbstractState;
import org.opcfoundation.ua.utils.StackUtils;

/**
 * Super class for endpoint secure channels.
 * 
 * Common mechanism:
 *  - Secure channel id
 *  - Security tokens
 *  - State & Error State
 */
public abstract class AbstractServerSecureChannel extends AbstractState<CloseableObjectState, ServiceResultException> implements ServerSecureChannel {

	/** Globally Unique Secure Channel ID */
	private int					secureChannelId;
	/** Collection of all Security Tokens */
	protected Map<Integer, SecurityToken> tokens = new ConcurrentHashMap<Integer, SecurityToken>();
	/** The active token, This token is used in write operations */
	protected SecurityToken			activeToken;
	
	/** Logger */
	static Logger logger = LoggerFactory.getLogger(AbstractServerSecureChannel.class);
	
	protected AbstractServerSecureChannel(int secureChannelId) {
		super(CloseableObjectState.Closed);
		this.secureChannelId = secureChannelId;
	}

	public int getSecureChannelId() {
		return secureChannelId;
	}

	public SecurityToken getActiveSecurityToken() {
		return activeToken;
	}
	
	public void setActiveSecurityToken(SecurityToken token) {
		if (token==null) 
			throw new IllegalArgumentException("null");
		logger.debug("Switching to new security token {}", token.getTokenId());
		this.activeToken = token;
		pruneInvalidTokens();
	}
	
	public synchronized SecurityToken getSecurityToken(int tokenId) {
		logger.debug("tokens({})={}", tokens.size(), tokens.values());
		return tokens.get(tokenId);
	}
	
	private void pruneInvalidTokens()
	{	
		if (logger.isDebugEnabled())
			logger.debug("pruneInvalidTokens: tokens({})={}", tokens.size(), tokens.values());
		for (SecurityToken t : tokens.values())
			if (!t.isValid()) {
				logger.debug("pruneInvalidTokens: remove Id={}", t.getTokenId());
				tokens.remove(t.getTokenId());
			}
	}

	public MessageSecurityMode getMessageSecurityMode() {
		SecurityToken token = getActiveSecurityToken();
		return token==null ? null : token.getMessageSecurityMode();
	}

	public SecurityPolicy getSecurityPolicy() {
		SecurityToken token = getActiveSecurityToken();
		return token==null ? null : token.getSecurityPolicy();
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
	
	public synchronized void setError(ServiceResultException e) {
		super.setError( e );
	}

	@Override
	protected void onListenerException(RuntimeException rte) {
		setError( StackUtils.toServiceResultException(rte) );
	}

	@Override
	public String toString() {
		return String.format("SecureChannelId=%d State=%s URL=%s SecurityPolicy=%s RemoteAddress=%s",
				getSecureChannelId(), getState(), getConnectURL(), getSecurityPolicy(), getRemoteAddress());
	}

	protected String getRemoteAddress() {
		if (getConnection() == null)
			return "(no connection)";
		return "" + getConnection().getRemoteAddress();
	}

	@Override
	public boolean isOpen() {
		return getState().isOpen();
	}

	@Override
	public void close() {
		if (getState()!=CloseableObjectState.Open) return;				
		setState(CloseableObjectState.Closing);
		setState(CloseableObjectState.Closed);
		logger.info("Channel closed: Id={}", getSecureChannelId());
		return;
	}

	@Override
	public AsyncResult<ServerSecureChannel> closeAsync() {
		AsyncResultImpl<ServerSecureChannel> result = new AsyncResultImpl<ServerSecureChannel>(); 
		if (getState()!=CloseableObjectState.Open) {
			result.setResult(this);
			return result;				
		}
		setState(CloseableObjectState.Closing);
		setState(CloseableObjectState.Closed);
		result.setResult(this);
		return result;
	}
	
}
