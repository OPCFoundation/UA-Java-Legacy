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

package org.opcfoundation.ua.transport.tcp.io;

import java.net.InetSocketAddress;

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.transport.IConnectionListener;
import org.opcfoundation.ua.transport.TransportChannelSettings;

/**
 * <p>IConnection interface.</p>
 *
 */
public interface IConnection {	
	
	/**
	 * Set connection configuration parameters.
	 *
	 * @param addr a {@link java.net.InetSocketAddress} object.
	 * @param settings a {@link org.opcfoundation.ua.transport.TransportChannelSettings} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 * @param ctx a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public void initialize(InetSocketAddress addr, TransportChannelSettings settings, EncoderContext ctx)
	throws ServiceResultException; 

	/**
	 * Send request to the connection.
	 *
	 *
	 * If the connection is closed Bad_NotConnected is thrown
	 *
	 * @param request
	 * @param requestId
	 * @param secureChannelId a int.
	 * @param requestId a int.
	 * @throws org.opcfoundation.ua.common.ServiceResultException varies. Bad_NotConnected if connection is not established
	 */
	public void sendRequest(ServiceRequest request, int secureChannelId, int requestId)
	throws ServiceResultException; 

	/**
	 * Add response listener
	 *
	 * @param listener a {@link org.opcfoundation.ua.transport.tcp.io.IConnection.IMessageListener} object.
	 */
	public void addMessageListener(IMessageListener listener);
	
	/**
	 * Add response listener
	 *
	 * @param listener a {@link org.opcfoundation.ua.transport.tcp.io.IConnection.IMessageListener} object.
	 */
	public void removeMessageListener(IMessageListener listener);	

	/**
	 * Message listener interface
	 */
	public interface IMessageListener {
		/**
		 * New message arrived to the connection.
		 *  
		 * Incoming message listeners. All incoming messages are notified to all listeners.
		 * It is up to the listener to find the interesting messages.
		 * 
		 * @param requestId
		 * @param secureChannelId
		 * @param message
		 */
		void onMessage(int requestId, int secureChannelId, IEncodeable message);
	}

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
	
	/**
	 * Attempt to open the connection.
	 * There is no error if the connection is already open.
	 *
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public void open() throws ServiceResultException;
	
	/**
	 * Close the connection.
	 * There is no error if the connection is already closed.
	 */
	public void close();	

	/**
	 * Open if the connection is not open.
	 *
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public void reconnect() throws ServiceResultException; 
	
	/**
	 * Dispose the object making it unusable.
	 */
	public void dispose();		
	
	
}
