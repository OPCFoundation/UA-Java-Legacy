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
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.utils.AbstractState;
import org.opcfoundation.ua.utils.asyncsocket.ListenableServerSocketChannel.ServerSocketAcceptable;

/**
 * ASyncoronous Server Socket
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class AsyncServerSocket extends AbstractState<ServerSocketState, IOException> {
	ServerSocketChannel c;
	ListenableServerSocketChannel ls;
	Executor executor; // Teloittaja
	CopyOnWriteArrayList<ConnectListener> listeners = new CopyOnWriteArrayList<ConnectListener>();
	ServerSocketAcceptable listener = new ServerSocketAcceptable() {
		@Override
		public void onConnectionAcceptable(ListenableServerSocketChannel socket) {
			try {
				SocketChannel chan = socket.getChannel().accept();
				chan.configureBlocking(false);				
				AsyncSocketImpl as = new AsyncSocketImpl(chan, executor, ls.getSelectorThread());
				fireConnected(as);
			} catch (ClosedChannelException e) {
				setState(ServerSocketState.Closed);
			} catch (IOException e) {
				setError(e);
			}
		}};
	static Logger logger = LoggerFactory.getLogger(AsyncServerSocket.class); 
	public AsyncServerSocket(ServerSocketChannel chan, Executor e, AsyncSelector sel) 
	throws ClosedChannelException
	{
		super(ServerSocketState.Ready, ServerSocketState.Error);
		ls = new ListenableServerSocketChannel(chan, e, sel);
		this.executor = e;
		this.c = chan;
	}

    public AsyncServerSocket(ServerSocketChannel chan, Executor e)
            throws IOException
    {
        super(ServerSocketState.Ready, ServerSocketState.Error);
        ls = new ListenableServerSocketChannel(chan, e);
        this.executor = e;
        this.c = chan;
    }

    public interface ConnectListener {
		public void onConnected(AsyncServerSocket sender, AsyncSocketImpl newConnection);
	}
	
   /**
    *
    * Binds the <code>ServerSocket</code> to a specific address
    * (IP address and port number).
    * <p>
    * If the address is <code>null</code>, then the system will pick up
    * an ephemeral port and a valid local address to bind the socket.
    * <P>
    * The <code>backlog</code> argument must be a positive
    * value greater than 0. If the value passed if equal or less
    * than 0, then the default value will be assumed.
    * @param	addr		The IP address & port number to bind to.
    * @param	backlog		The listen backlog length.
    * @throws	IOException if the bind operation fails, or if the socket
    *			   is already bound.
    * @throws	SecurityException	if a <code>SecurityManager</code> is present and
    * its <code>checkListen</code> method doesn't allow the operation.
    * @throws  IllegalArgumentException if endpoint is a
    *          SocketAddress subclass not supported by this socket
    * @return this object. This is used for chained invocation
    */
	public /*synchronized*/ AsyncServerSocket bind(SocketAddress addr, int backlog) 
	throws IOException
	{				
		ServerSocketState s = getState();
		if (s==ServerSocketState.Closed) throw new ClosedChannelException();
		assertNoError();
		//if (getState()!=ServerSocketState.Ready) return;
		ls.bind(addr, backlog);
		setState(ServerSocketState.Bound);
		ls.setAcceptableListener(listener);		
	
		return this;
	}
	
	public AsyncServerSocket close()
	{
		logger.debug("close");
		try {
			ls.close();
			setState(ServerSocketState.Closed);
		} catch (IOException e) {
			logger.error("close", e);
			setError(e);
		}
		return this;
	}

	public ServerSocketChannel channel()
	{
		return c;
	}
	
	public ServerSocket socket()
	{
		return c.socket();
	}
	
	public void addListener(ConnectListener listener)
	{
		listeners.add(listener);
	}

	
	public void removeListener(ConnectListener listener)
	{
		listeners.remove(listener);
	}


	void fireConnected(final AsyncSocketImpl socket)
	{
		for (final ConnectListener entry : listeners)
		{
			executor.execute(new Runnable() {
				@Override
				public void run() {
					entry.onConnected(AsyncServerSocket.this, socket);
				}
			});
		}
	}
	
	@Override
	public String toString() {
		if (!c.socket().isBound()) return "unbound";
		return c.socket().getLocalSocketAddress().toString();
	}
	
}
