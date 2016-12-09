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

import java.net.Socket;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.http.impl.nio.NHttpConnectionBase;
import org.apache.http.nio.NHttpServerConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.transport.CloseableObjectState;
import org.opcfoundation.ua.transport.IConnectionListener;
import org.opcfoundation.ua.transport.ServerConnection;
import org.opcfoundation.ua.transport.ServerSecureChannel;
import org.opcfoundation.ua.utils.AbstractState;

/**
 * This class implements HTTP TSL/SSL conversation.
 *
 * The messages are serialized using binary scheme, the same as with tcp
 * conversation.
 *
 * Because HTTPS channel is already secure, a OPC secure channel is not opened.
 * All HTTPS communications via a URL shall be treated as a single
 * SecureChannel that is shared by multiple Clients. Stack shall provide a
 * unique identifier for the SecureChannel which allows Applications correlate
 * a request with a SecureChannel.This means that Sessions can only be
 * considered secure if the AuthenticationToken (see Part 4) is long (&gt;20 bytes)
 * and HTTPS encryption is enabled.
 */
public class HttpsServerConnection extends AbstractState<CloseableObjectState, ServiceResultException> implements ServerConnection {
	private static Logger logger = LoggerFactory.getLogger(HttpsServerConnection.class);
	
	HttpsServer server;
	NHttpServerConnection conn;
	Socket socket;
	
	/** List of secure channels open in this connection */
	Map<Integer, ServerSecureChannel> secureChannels = new ConcurrentHashMap<Integer, ServerSecureChannel>();
	/** List of secure channel listener */
	CopyOnWriteArrayList<SecureChannelListener> secureChannelListeners = new CopyOnWriteArrayList<SecureChannelListener>();	
	/** Listeners that follow this connection */ 
	CopyOnWriteArrayList<IConnectionListener> connectionListeners = new CopyOnWriteArrayList<IConnectionListener>();
	
	/**
	 * <p>Constructor for HttpsServerConnection.</p>
	 *
	 * @param server a {@link org.opcfoundation.ua.transport.https.HttpsServer} object.
	 * @param conn a {@link org.apache.http.nio.NHttpServerConnection} object.
	 */
	public HttpsServerConnection(HttpsServer server, NHttpServerConnection conn) {
		super(CloseableObjectState.Closed);
		this.server = server;
		this.conn = conn;
		this.socket = ( (NHttpConnectionBase) conn ).getSocket();
		this.conn.setSocketTimeout(60000);
	}

	/** {@inheritDoc} */
	@Override
	public SocketAddress getLocalAddress() {
		return socket.getLocalSocketAddress();
	}

	/** {@inheritDoc} */
	@Override
	public SocketAddress getRemoteAddress() {
		return socket.getRemoteSocketAddress();
	}
	
	/**
	 * <p>getNHttpServerConnection.</p>
	 *
	 * @return a {@link org.apache.http.nio.NHttpServerConnection} object.
	 */
	public NHttpServerConnection getNHttpServerConnection() {
		return conn;
	}

	/** {@inheritDoc} */
	@Override
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
	protected void onStateTransition(CloseableObjectState oldState,
			CloseableObjectState newState) {
		logger.debug("onStateTransition: {}->{}", oldState, newState);
		super.onStateTransition(oldState, newState);
		
		if (newState == CloseableObjectState.Open)
		{
			for (IConnectionListener l : connectionListeners)
				l.onOpen();
		}
		
		if (newState == CloseableObjectState.Closed) 
		{
			ServiceResultException sre = new ServiceResultException(StatusCodes.Bad_CommunicationError);
			for (IConnectionListener l : connectionListeners) {
				l.onClosed(sre);
			}

		}
	}	
}
