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

/**
 * <p>Abstract AbstractServerConnection class.</p>
 *
 */
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
	
	/**
	 * <p>Constructor for AbstractServerConnection.</p>
	 */
	protected AbstractServerConnection()
	{
		super(CloseableObjectState.Closed);
	}
			
	/**
	 * Remote Certificate Validator, invoked upon connect
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.CertificateValidator} object.
	 */
	protected abstract CertificateValidator getRemoteCertificateValidator();	
	
	/**
	 * <p>sendAsymmSecureMessage.</p>
	 *
	 * @param msg a {@link org.opcfoundation.ua.transport.AsyncWrite} object.
	 * @param securityConfiguration a {@link org.opcfoundation.ua.transport.security.SecurityConfiguration} object.
	 * @param secureChannelId a int.
	 * @param requestNumber a int.
	 * @param sendSequenceNumber a {@link java.util.concurrent.atomic.AtomicInteger} object.
	 * @return a int.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	protected abstract int sendAsymmSecureMessage(
			final AsyncWrite msg, 
			final SecurityConfiguration securityConfiguration,
			int secureChannelId,
			int requestNumber,
			AtomicInteger sendSequenceNumber)
	throws ServiceResultException;
	
	/**
	 * <p>sendSecureMessage.</p>
	 *
	 * @param msg a {@link org.opcfoundation.ua.transport.AsyncWrite} object.
	 * @param token a {@link org.opcfoundation.ua.transport.tcp.impl.SecurityToken} object.
	 * @param requestId a int.
	 * @param messageType a int.
	 * @param sendSequenceNumber a {@link java.util.concurrent.atomic.AtomicInteger} object.
	 */
	protected abstract void sendSecureMessage(
			final AsyncWrite msg, 
			final SecurityToken token, 
			final int requestId,
			final int messageType,
			final AtomicInteger sendSequenceNumber
			);
	
	/**
	 * <p>setError.</p>
	 *
	 * @param errorCode a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	protected void setError(UnsignedInteger errorCode)
	{
		setError(new StatusCode(errorCode));
	}
	
	/**
	 * <p>setError.</p>
	 *
	 * @param sc a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 */
	protected void setError(StatusCode sc) 
	{
		setError(new ServiceResultException(sc));
	}
	
	/**
	 * <p>setError.</p>
	 *
	 * @param e a {@link org.opcfoundation.ua.common.ServiceResultException} object.
	 */
	protected synchronized void setError(ServiceResultException e) 
	{
		if (hasError()) return;
		super.setError(e);
		close();
	}
		
	/** {@inheritDoc} */
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
	/** {@inheritDoc} */
	@Override
	protected void onListenerException(RuntimeException rte) {
		setError( StackUtils.toServiceResultException(rte) );
	}

	/** {@inheritDoc} */
	public void getSecureChannels(Collection<ServerSecureChannel> list) {
		list.addAll( secureChannels.values() );
	}

	/** {@inheritDoc} */
	@Override
	public void addSecureChannelListener(SecureChannelListener l) {
		secureChannelListeners.add(l);
	}
	
	/** {@inheritDoc} */
	@Override
	public void removeSecureChannelListener(SecureChannelListener l) {
		secureChannelListeners.remove(l);
	}
	
	/**
	 * Send a notification to listeners that a secure channel has been
	 * attached to (opened in) the connection.
	 *
	 * @param c a {@link org.opcfoundation.ua.transport.ServerSecureChannel} object.
	 */
	protected void fireSecureChannelAttached(ServerSecureChannel c) {
		for (SecureChannelListener l : secureChannelListeners)
			l.onSecureChannelAttached(this, c);
	}
	
	/**
	 * Send a notification the listeners that a secure channel has been
	 * detached from the connection.
	 *
	 * @param c a {@link org.opcfoundation.ua.transport.ServerSecureChannel} object.
	 */
	protected void fireSecureChannelDetached(ServerSecureChannel c) {
		for (SecureChannelListener l : secureChannelListeners)
			l.onSecureChannelDetached(this, c);
	}
	
	/**
	 * <p>getConnectURL.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getConnectURL() {
		return ctx.endpointUrl;
	}
	
	/**
	 * <p>getRemoteCertificate.</p>
	 *
	 * @return a {@link java.security.cert.Certificate} object.
	 */
	public Certificate getRemoteCertificate() {
		return securityConfiguration.getReceiverCertificate();
	}
	
	/**
	 * <p>addChannelListener.</p>
	 *
	 * @param listener a {@link org.opcfoundation.ua.transport.tcp.nio.Channel.ChannelListener} object.
	 */
	public void addChannelListener(ChannelListener listener) {
		channelListeners.add(listener);
	}
	
	/**
	 * <p>removeChannelListener.</p>
	 *
	 * @param listener a {@link org.opcfoundation.ua.transport.tcp.nio.Channel.ChannelListener} object.
	 */
	public void removeChannelListener(ChannelListener listener) {
		channelListeners.remove(listener);
	}
	
	/** {@inheritDoc} */
	@Override
	public void addConnectionListener(IConnectionListener listener) {
		connectionListeners.add(listener);
	}
	
	/** {@inheritDoc} */
	@Override
	public void removeConnectionListener(IConnectionListener listener) {
		connectionListeners.remove(listener);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		CloseableObjectState s = getState();
		return "Connection (state="+s+", addr="+getRemoteAddress()+")";
	}	

	/**
	 * <p>close.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.CloseableObject} object.
	 */
	public synchronized CloseableObject close() {
		try {
			setState(CloseableObjectState.Closing);
		} finally {
			setState(CloseableObjectState.Closed);
		}
		return this;
	}
		
}
