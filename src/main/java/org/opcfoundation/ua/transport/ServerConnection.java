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

package org.opcfoundation.ua.transport;

import java.net.SocketAddress;
import java.util.Collection;


/**
 * UAConnection is a stateful object with four possible states {Closed,
 * Opening, Open, Closing}. A connection is transfers to open state
 * after handshake (Hello/Acknowledge). Closed state is final.
 */
public interface ServerConnection {

	/**
	 * Get local socket address
	 *
	 * @return socket address
	 */
	SocketAddress getLocalAddress();
	
	/**
	 * Get remote socket address
	 *
	 * @return remote socket address
	 */
	SocketAddress getRemoteAddress();
	
	/**
	 * Get all open and opening secure channels of this connection.
	 *
	 * @param list list to be filled
	 */
	void getSecureChannels(Collection<ServerSecureChannel> list);
	
	static interface SecureChannelListener {
		void onSecureChannelAttached(Object sender, ServerSecureChannel channel);
		void onSecureChannelDetached(Object sender, ServerSecureChannel channel);
	}

	/**
	 * <p>addSecureChannelListener.</p>
	 *
	 * @param l a {@link org.opcfoundation.ua.transport.ServerConnection.SecureChannelListener} object.
	 */
	void addSecureChannelListener(SecureChannelListener l);
	/**
	 * <p>removeSecureChannelListener.</p>
	 *
	 * @param l a {@link org.opcfoundation.ua.transport.ServerConnection.SecureChannelListener} object.
	 */
	void removeSecureChannelListener(SecureChannelListener l);

	/**
	 * Add response listener
	 *
	 * @param listener a {@link org.opcfoundation.ua.transport.IConnectionListener} object.
	 */
	public void addConnectionListener(IConnectionListener listener);
	
	/**
	 * Add response listener
	 *
	 * @param listener a {@link org.opcfoundation.ua.transport.IConnectionListener} object.
	 */
	public void removeConnectionListener(IConnectionListener listener);	
	
}
