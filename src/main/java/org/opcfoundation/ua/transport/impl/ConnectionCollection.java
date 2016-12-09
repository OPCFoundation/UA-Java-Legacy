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

package org.opcfoundation.ua.transport.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.opcfoundation.ua.transport.ConnectionMonitor;
import org.opcfoundation.ua.transport.ServerConnection;

/**
 * <p>ConnectionCollection class.</p>
 *
 */
public class ConnectionCollection implements ConnectionMonitor {

	Set<ServerConnection> connections = new HashSet<ServerConnection>(); 
	CopyOnWriteArrayList<ConnectListener> listeners = new CopyOnWriteArrayList<ConnectListener>();
	Object sender;	

	/**
	 * <p>addConnection.</p>
	 *
	 * @param c a {@link org.opcfoundation.ua.transport.ServerConnection} object.
	 */
	public void addConnection(ServerConnection c) {
		if (!connections.add(c)) return;
		for (ConnectListener cl : listeners)
			cl.onConnect(sender, c);
	}
	
	/**
	 * <p>removeConnection.</p>
	 *
	 * @param c a {@link org.opcfoundation.ua.transport.ServerConnection} object.
	 */
	public void removeConnection(ServerConnection c) {
		connections.remove(c);
		for (ConnectListener cl : listeners)
			cl.onClose(sender, c);
	}

	/**
	 * <p>getConnectionListeners.</p>
	 *
	 * @return a {@link java.util.Iterator} object.
	 */
	public Iterator<ConnectListener> getConnectionListeners() {
		return listeners.iterator();
	}
	
	/**
	 * <p>Constructor for ConnectionCollection.</p>
	 *
	 * @param sender a {@link java.lang.Object} object.
	 */
	public ConnectionCollection(Object sender) {
		this.sender = sender;
	}
		
	/** {@inheritDoc} */
	@Override
	public void addConnectionListener(ConnectListener l) {
		listeners.add(l);
	}

	/** {@inheritDoc} */
	@Override
	public void removeConnectionListener(ConnectListener l) {
		listeners.remove(l);
	}
	
	/** {@inheritDoc} */
	@Override
	public synchronized void getConnections(Collection<ServerConnection> result) {
		result.addAll(connections);
	}
	
}
