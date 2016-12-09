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

package org.opcfoundation.ua.common;

import org.opcfoundation.ua.transport.CloseableObject;
import org.opcfoundation.ua.transport.CloseableObjectState;
import org.opcfoundation.ua.transport.ConnectionMonitor.ConnectListener;
import org.opcfoundation.ua.transport.ServerConnection;
import org.opcfoundation.ua.transport.ServerConnection.SecureChannelListener;
import org.opcfoundation.ua.transport.ServerSecureChannel;
import org.opcfoundation.ua.utils.IStatefulObject;
import org.opcfoundation.ua.utils.StateListener;
import org.slf4j.Logger;

/**
 * Connect monitor prints to logger server's connect and secure channel events.
 *
 * Example:
 * 		UABinding binding;
 *		binding.addConnectionListener(new ConnectMonitor());
 *
 * @author Toni Kalajainen (toni.kalajainen@iki.fi)
 */
public class DebugLogger implements ConnectListener, SecureChannelListener, StateListener<CloseableObjectState> {
	Logger logger;
	/**
	 * <p>Constructor for DebugLogger.</p>
	 *
	 * @param logger a {@link org.slf4j.Logger} object.
	 */
	public DebugLogger(Logger logger) {
		this.logger = logger;
	}
	/** {@inheritDoc} */
	public void onConnect(Object sender, ServerConnection connection) {
		logger.info("{}: {}", sender, connection);
		if (connection instanceof CloseableObject) {
			((CloseableObject)connection).addStateListener( this);
		
		}
		connection.addSecureChannelListener(this);		
		
	}
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public void onSecureChannelAttached(Object sender, ServerSecureChannel channel) {
		logger.info("{}: {}", sender, channel);
		if (channel instanceof IStatefulObject<?, ?>)
		{
			IStatefulObject<CloseableObjectState, ServiceResultException> so = (IStatefulObject<CloseableObjectState, ServiceResultException>) channel;
			so.addStateListener(this);
		}
	}
	/**
	 * <p>onStateTransition.</p>
	 *
	 * @param sender a {@link org.opcfoundation.ua.utils.IStatefulObject} object.
	 * @param oldState a {@link org.opcfoundation.ua.transport.CloseableObjectState} object.
	 * @param newState a {@link org.opcfoundation.ua.transport.CloseableObjectState} object.
	 */
	public void onStateTransition(IStatefulObject<CloseableObjectState, ?> sender, CloseableObjectState oldState, CloseableObjectState newState) {
		logger.info("{}: {}", sender, sender);
		if (sender.getError()!=null) {
			Throwable e = sender.getError();
			logger.debug("onStateTransition: failed", e);
		}			
	}
	
	/** {@inheritDoc} */
	@Override
	public void onSecureChannelDetached(Object sender, ServerSecureChannel channel) {
	}
	/** {@inheritDoc} */
	@Override
	public void onClose(Object sender, ServerConnection connection) {
		// TODO Auto-generated method stub
		
	}
}
