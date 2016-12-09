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

package org.opcfoundation.ua.utils.asyncsocket;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import org.opcfoundation.ua.utils.IStatefulObject;

/**
 * <p>AsyncSocket interface.</p>
 *
 * @see AsyncSocketImpl
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public interface AsyncSocket {

	/**
	 * <p>getInputStream.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncInputStream} object.
	 */
	AsyncInputStream getInputStream();
	/**
	 * <p>getOutputStream.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncOutputStream} object.
	 */
	AsyncOutputStream getOutputStream();
	/**
	 * <p>close.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncSocketImpl} object.
	 * @throws java.io.IOException if any.
	 */
	AsyncSocketImpl close() throws IOException;
	/**
	 * <p>socketChannel.</p>
	 *
	 * @return a {@link java.nio.channels.SocketChannel} object.
	 */
	SocketChannel socketChannel();
	/**
	 * <p>socket.</p>
	 *
	 * @return a {@link java.net.Socket} object.
	 */
	Socket socket();
	/**
	 * <p>connect.</p>
	 *
	 * @param addr a {@link java.net.SocketAddress} object.
	 * @throws java.io.IOException if any.
	 */
	void connect(SocketAddress addr) throws IOException;
	/**
	 * <p>getStateMonitor.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.IStatefulObject} object.
	 */
	IStatefulObject<SocketState, IOException> getStateMonitor();
	
}
