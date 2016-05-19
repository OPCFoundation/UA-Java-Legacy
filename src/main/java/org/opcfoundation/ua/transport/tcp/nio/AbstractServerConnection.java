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

package org.opcfoundation.ua.transport.tcp.nio;

import java.nio.ByteBuffer;
import java.security.cert.Certificate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.transport.AsyncWrite;
import org.opcfoundation.ua.transport.CloseableObject;
import org.opcfoundation.ua.transport.CloseableObjectState;
import org.opcfoundation.ua.transport.IConnectionListener;
import org.opcfoundation.ua.transport.ServerConnection;
import org.opcfoundation.ua.transport.ServerSecureChannel;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.SecurityConfiguration;
import org.opcfoundation.ua.transport.tcp.impl.SecurityToken;
import org.opcfoundation.ua.transport.tcp.impl.TcpConnectionParameters;
import org.opcfoundation.ua.transport.tcp.nio.Channel.ChannelListener;
import org.opcfoundation.ua.utils.AbstractState;
import org.opcfoundation.ua.utils.IncubationQueue;
import org.opcfoundation.ua.utils.StackUtils;
import org.opcfoundation.ua.utils.StateListener;
import org.opcfoundation.ua.utils.asyncsocket.SocketState;

public abstract class AbstractServerConnection extends AbstractState<CloseableObjectState, ServiceResultException> implements ServerConnection, CloseableObject {
	
	/** Logger */
	private final static Logger logger = LoggerFactory.getLogger(AbstractServerConnection.class);
	/** Protocol Version after hand shake */
	int agreedProtocolVersion = 0;
	/** Security settings for asymmetric encryption */
	SecurityConfiguration securityConfiguration;
	/** Socket connect/disconnect state change listener */
	StateListener<SocketState> socketListener;
	/** Connection parameters */
	TcpConnectionParameters ctx = new TcpConnectionParameters();
	/** Message builder, complies chunks into complete messages */
	SecureInputMessageBuilder secureMessageBuilder;
	/** List of secure channels open in this connection */
	Map<Integer, ServerSecureChannel> secureChannels = new ConcurrentHashMap<Integer, ServerSecureChannel>();
	/** List of secure channel listener */
	CopyOnWriteArrayList<SecureChannelListener> secureChannelListeners = new CopyOnWriteArrayList<SecureChannelListener>();	
	/** Chunk incubate (are encoded and signed) before sending to stream */
	IncubationQueue<ByteBuffer> chunkIncubator = new IncubationQueue<ByteBuffer>(true);

	CopyOnWriteArrayList<ChannelListener> channelListeners = new CopyOnWriteArrayList<ChannelListener>();
	
	CopyOnWriteArrayList<IConnectionListener> connectionListeners = new CopyOnWriteArrayList<IConnectionListener>();
	
	protected AbstractServerConnection()
	{
		super(CloseableObjectState.Closed);
	}
			
	/** Remote Certificate Validator, invoked upon connect */
	protected abstract CertificateValidator getRemoteCertificateValidator();	
	
	protected abstract int sendAsymmSecureMessage(
			final AsyncWrite msg, 
			final SecurityConfiguration securityConfiguration,
			int secureChannelId,
			int requestNumber,
			AtomicInteger sendSequenceNumber)
	throws ServiceResultException;
	
	protected abstract void sendSecureMessage(
			final AsyncWrite msg, 
			final SecurityToken token, 
			final int requestId,
			final int messageType,
			final AtomicInteger sendSequenceNumber
			);
	
	protected void setError(UnsignedInteger errorCode)
	{
		setError(new StatusCode(errorCode));
	}
	
	protected void setError(StatusCode sc) 
	{
		setError(new ServiceResultException(sc));
	}
	
	protected synchronized void setError(ServiceResultException e) 
	{
		if (hasError()) return;
		super.setError(e);
		close();
	}
		
	@Override
	protected void onStateTransition(CloseableObjectState oldState,
			CloseableObjectState newState) {
		super.onStateTransition(oldState, newState);
		
		if (newState == CloseableObjectState.Open)
		{
			for (IConnectionListener l : connectionListeners)
				l.onOpen();
		}
		
		if (newState == CloseableObjectState.Closed) 
		{
			ServiceResultException sre = new ServiceResultException(
					StatusCodes.Bad_CommunicationError);
			for (IConnectionListener l : connectionListeners) {
				l.onClosed(sre);
			}

		}
	}
	
	// Handle runtime exceptions that are thrown by state listeners  
	@Override
	protected void onListenerException(RuntimeException rte) {
		setError( StackUtils.toServiceResultException(rte) );
	}

	public void getSecureChannels(Collection<ServerSecureChannel> list) {
		list.addAll( secureChannels.values() );
	}

	@Override
	public void addSecureChannelListener(SecureChannelListener l) {
		secureChannelListeners.add(l);
	}
	
	@Override
	public void removeSecureChannelListener(SecureChannelListener l) {
		secureChannelListeners.remove(l);
	}
	
	/**
	 * Send a notification to listeners that a secure channel has been 
	 * attached to (opened in) the connection. 
	 *  
	 * @param c
	 */
	protected void fireSecureChannelAttached(ServerSecureChannel c) {
		for (SecureChannelListener l : secureChannelListeners)
			l.onSecureChannelAttached(this, c);
	}
	
	/**
	 * Send a notification the listeners that a secure channel has been
	 * detached from the connection.
	 * 
	 * @param c
	 */
	protected void fireSecureChannelDetached(ServerSecureChannel c) {
		for (SecureChannelListener l : secureChannelListeners)
			l.onSecureChannelDetached(this, c);
	}
	
	public String getConnectURL() {
		return ctx.endpointUrl;
	}
	
	public Certificate getRemoteCertificate() {
		return securityConfiguration.getReceiverCertificate();
	}
	
	public void addChannelListener(ChannelListener listener) {
		channelListeners.add(listener);
	}
	
	public void removeChannelListener(ChannelListener listener) {
		channelListeners.remove(listener);
	}
	
	@Override
	public void addConnectionListener(IConnectionListener listener) {
		connectionListeners.add(listener);
	}
	
	@Override
	public void removeConnectionListener(IConnectionListener listener) {
		connectionListeners.remove(listener);
	}
	
	@Override
	public String toString() {
		CloseableObjectState s = getState();
		return "Connection (state="+s+", addr="+getRemoteAddress()+")";
	}	

	public synchronized CloseableObject close() {
		try {
			setState(CloseableObjectState.Closing);
		} finally {
			setState(CloseableObjectState.Closed);
		}
		return this;
	}
		
}
