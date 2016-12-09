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
import java.net.SocketAddress;
import java.nio.channels.*;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.utils.asyncsocket.AsyncSelector.SelectListener;

/**
 * ListenableServerSocketChannel adds convenient listening mechanism over
 * non-blocking ServerSocketChannel.
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ListenableServerSocketChannel {
    static Logger logger = LoggerFactory.getLogger(ListenableServerSocketChannel.class);

    AtomicReference<ServerSocketChannel>	channel = new AtomicReference<ServerSocketChannel>(null);
	AsyncSelector					selector;
	Executor						executor;
	volatile ServerSocketAcceptable	acceptableListener;
	boolean							acceptableHndPending = false;
    boolean					        ownsSelector = false;
    Runnable acceptRun;

	/**
	 * Wrap AsyncServerSocket over given ServerSocketChannel using given event worker thread and selector thread.
	 *
	 * @param channel a {@link java.nio.channels.ServerSocketChannel} object.
	 * @param eventExecutor event executor or null for selector thread
	 * @param t selector thread
	 * @throws java.nio.channels.ClosedChannelException if any.
	 */
	public ListenableServerSocketChannel(ServerSocketChannel channel, Executor eventExecutor, AsyncSelector t) 
	throws ClosedChannelException
	{
		if (channel.isBlocking())
			throw new IllegalArgumentException("channel arg must be in non-blocking mode. (SocketChannel.configureBlocking(false))");
		if (t==null)
			throw new IllegalArgumentException("null arg");
		this.executor = eventExecutor;
		this.channel.set(channel);
		this.selector = t;
		t.register(channel, 0, selectListener);		
	}

    /**
     * Wrap AsyncServerSocket over given ServerSocketChannel using given event worker thread and a new selector thread.
     *
     * @param channel a {@link java.nio.channels.ServerSocketChannel} object.
     * @param eventExecutor event executor or null for selector thread
     * @throws ClosedChannelException if any.
     * @throws java.io.IOException if any.
     */
    public ListenableServerSocketChannel(ServerSocketChannel channel, Executor eventExecutor)
            throws IOException
    {
        this(channel, eventExecutor, new AsyncSelector(Selector.open()));
        ownsSelector = true;
   }

	SelectListener selectListener = new SelectListener() {
		@Override
		public void onSelected(AsyncSelector sender, SelectableChannel channel, int ops, int oldOps) {
			if ((ops & SelectionKey.OP_ACCEPT)!=0) {
				Runnable r = acceptRun;
				if (r!=null) {
					if (executor==null) {
						acceptableHndPending = true;
						try {
							r.run();
						} catch(Throwable t) {
							t.printStackTrace();
						}
					} else {
						acceptableHndPending = true;
						executor.execute( r );
					}
				}
			}
			sender.interestOps(channel, getInterestOps());
		}
	};

	/**
	 * <p>Setter for the field <code>acceptableListener</code>.</p>
	 *
	 * @param listener a {@link org.opcfoundation.ua.utils.asyncsocket.ListenableServerSocketChannel.ServerSocketAcceptable} object.
	 */
	public void setAcceptableListener(final ServerSocketAcceptable listener)
	{
		/*synchronized(this)*/ {
			this.acceptableListener = listener;
			if (listener!=null) {
				acceptRun = new Runnable() {
					@Override
					public void run() {
						try {
							listener.onConnectionAcceptable(ListenableServerSocketChannel.this);
						} finally {
							acceptableHndPending = false;
							updateInterestOps();
						}
					}
				};
			} else {
				acceptRun = null;
			}
		}
		updateInterestOps();
	}
	
	/**
	 * <p>Getter for the field <code>acceptableListener</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.ListenableServerSocketChannel.ServerSocketAcceptable} object.
	 */
	public ServerSocketAcceptable getAcceptableListener()
	{
		return acceptableListener;
	}	
	
	/*synchronized*/ void updateInterestOps() {
		ServerSocketChannel c = channel.get();
		if (c != null)
			selector.interestOps(c, getInterestOps());
	}
	
	/*synchronized*/ int getInterestOps() {
		ServerSocketChannel c = channel.get();
		if (c == null || !c.isOpen() || !c.isRegistered()) return 0;
		int ops = 0;		
		if (acceptableListener!=null && !acceptableHndPending) ops |= SelectionKey.OP_ACCEPT;
		return ops;
	}			
	
	public interface ServerSocketAcceptable 
	{
		void onConnectionAcceptable(ListenableServerSocketChannel socket);
	}	
	
	/**
	 * <p>Getter for the field <code>channel</code>.</p>
	 *
	 * @return a {@link java.nio.channels.ServerSocketChannel} object.
	 */
	public ServerSocketChannel getChannel()
	{
		return channel.get();
	}
	
	/**
	 * <p>getSelectorThread.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncSelector} object.
	 */
	public AsyncSelector getSelectorThread()
	{
		return selector;
	}	
	
	/**
	 * <p>close.</p>
	 *
	 * @throws java.io.IOException if any.
	 */
	public /*synchronized*/ void close() throws IOException
	{
		ServerSocketChannel c = channel.getAndSet(null);
		if (c==null)
			return;
		selector.unregister(c);
        if (ownsSelector)
		    selector.close();
		try {
			c.close();
			logger.debug("closed");
		} finally {
		}
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
	 *
	 * @param addr address
	 * @param backlog backlog
	 * @throws  java.lang.IllegalArgumentException if endpoint is a
	 *          SocketAddress subclass not supported by this socket
	 * @throws IOException if error
	 */
	public /*synchronized*/ void bind(SocketAddress addr, int backlog) 
	throws IOException
	{				
		ServerSocketChannel c = getChannel();
		c.socket().bind( addr, backlog );
//		selector.unregister(channel);
		selector.register(c, SelectionKey.OP_ACCEPT, selectListener);		
	}
		
}
