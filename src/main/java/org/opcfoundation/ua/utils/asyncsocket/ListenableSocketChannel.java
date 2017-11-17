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
import java.net.ConnectException;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.utils.CurrentThreadExecutor;
import org.opcfoundation.ua.utils.asyncsocket.AsyncSelector.SelectListener;

// TODO finalize connect here
// Sync & Async connect
// Do not expose ConnectListener

/**
 * ListenableSocketChannel adds event listening convenience to the use of
 * async sockets.
 * <p>
 * Select events (read, write, connect) are handled in thread other than selector.
 * The thread is determined by an executor which is given as argument to the constructor.
 * ListenableSocketChannel guarantees that each event type (read/write/connect) is handled
 * at most by one thread.
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ListenableSocketChannel {
	static Logger logger = LoggerFactory.getLogger(ListenableSocketChannel.class);

	Executor				executor;
	SocketChannel			channel;
	volatile ConnectionListener		connectListener;
	volatile ReadableListener		readListener;
	volatile WriteableListener		writeListener;
	AsyncSelector			selector;
	boolean					closed = false;
	boolean					ownsSelector = false;
	AtomicBoolean			readHndLock = new AtomicBoolean(false);
	AtomicBoolean			writeHndLock = new AtomicBoolean(false);
	AtomicBoolean			connectHndLock = new AtomicBoolean(false);

	/**
	 * Create new async-socket with given event handling thread
	 *
	 * @param eventExecutor event handling thread or null for selector thread
	 * @throws java.io.IOException if any.
	 */
	public ListenableSocketChannel(Executor eventExecutor) 
	throws IOException {
		this(
			(SocketChannel) SocketChannel.open().configureBlocking(false), 
			eventExecutor
			);
	}	
	
	/**
	 * Create async-socket wrapper over given socket channel and given listener handling
	 * thread.
	 *
	 * @param channel a {@link java.nio.channels.SocketChannel} object.
	 * @param eventExecutor event worker thread or null for selector thread
	 * @throws java.io.IOException if any.
	 */
	public ListenableSocketChannel(SocketChannel channel, Executor eventExecutor) 
	throws IOException {
		this(channel, eventExecutor, new AsyncSelector());
		ownsSelector = true;
	}

	/**
	 * Create async-socket wrapper over given socket channel, given listener handling
	 * thread, and given selector thread.
	 *
	 * @param channel a {@link java.nio.channels.SocketChannel} object.
	 * @param executor a {@link java.util.concurrent.Executor} object.
	 * @param selectorThread a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncSelector} object.
	 * @throws java.io.IOException if any.
	 */
	public ListenableSocketChannel(SocketChannel channel, Executor executor, AsyncSelector selectorThread) 
	throws IOException {
		logger.debug("ListenableSocketChannel: channel={}", channel);
		if (channel==null || executor==null)
			throw new IllegalArgumentException();
		if (channel.isBlocking())
			throw new IllegalArgumentException("channel arg must be in non-blocking mode. (SocketChannel.configureBlocking(false))");
		this.channel = channel;
		this.executor = executor;
		this.selector = selectorThread;
		selector.register(channel, 0, selListener);
	}
	
	/**
	 * <p>close.</p>
	 */
	public /* synchronized */ void close() 
	{
		//These are used is logging and if-clauses; call methods only once for performance
		boolean isRegistered = channel.isRegistered();
		boolean isOpen = channel.isOpen();
			logger.debug("close: channel.isRegistered()={}", isRegistered);
		if (isRegistered)
			selector.unregister(channel);
		logger.debug("close: channel.isOpen()={}", isOpen);
		if (isOpen)
		try {
			try {
				channel.close();
				logger.debug("closed");
			} catch (IOException e) {
				logger.error("close", e);
			}
		} finally {
		}		
		logger.debug("close: ownsSelector={}", ownsSelector);
		if (ownsSelector)
			try {
				selector.close();
			} catch (IOException e) {
				logger.error("close: selector.close", e);
			}
	}
	
	/**
	 * <p>Getter for the field <code>channel</code>.</p>
	 *
	 * @return a {@link java.nio.channels.SocketChannel} object.
	 */
	public SocketChannel getChannel()
	{
		return channel;
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
	 * Updates interest ops if key is valid
	 */
	void attemptUpdateInterestOps(int key) {
		try {
			int ops = getInterestOps(key);
			selector.interestOps(channel, ops);
		} catch (CancelledKeyException e) {
			
		} catch (IllegalArgumentException ee) {
			// Key was no longer registered to selector
		}
	}
	
	/**
	 * Determines set of interest ops
	 * @return interest ops
	 */
	/* synchronized */ int getInterestOps() {
		if (!channel.isOpen() || !channel.isRegistered()) return 0;
		int ops = 0;
		if (readListener!=null && !readHndLock.get() && channel.isConnected()) ops |= SelectionKey.OP_READ;
		if (writeListener!=null && !writeHndLock.get() && channel.isConnected()) ops |= SelectionKey.OP_WRITE;
		if (connectListener!=null && !connectHndLock.get() && !channel.isConnected()) ops |= SelectionKey.OP_CONNECT;
		return ops;
	}
	
	/**
	 * Determines whether to enable or disable
	 * operation interest ops
	 * @param interest ops key
	 * @return
	 */
	int getInterestOps(int key) {
		switch (key) {
		case SelectionKey.OP_READ:
			if (readListener != null && !readHndLock.get() && channel.isConnected())
				return SelectionKey.OP_READ;
			else
				return ~SelectionKey.OP_READ;
		case SelectionKey.OP_WRITE:
			if (writeListener != null && !writeHndLock.get() && channel.isConnected())
				return SelectionKey.OP_WRITE;
			else
				return ~SelectionKey.OP_WRITE;
		case SelectionKey.OP_CONNECT:
			if (connectListener != null && !connectHndLock.get() && !channel.isConnected())
				return SelectionKey.OP_CONNECT;
			else
				return ~SelectionKey.OP_CONNECT;
		}
		return 0;
	}
		
	/**
	 * Connect to a remote socket.
	 * To monitor connect result, set ConnectListener first.
	 *
	 * @param addr a {@link java.net.SocketAddress} object.
	 * @throws java.io.IOException if any.
	 */
	public void connect(SocketAddress addr) 
	throws IOException
	{		
		attemptUpdateInterestOps(SelectionKey.OP_CONNECT);			
		channel.connect(addr);
	}
	
	/**
	 * <p>syncConnect.</p>
	 *
	 * @param addr a {@link java.net.SocketAddress} object.
	 * @param timeout timeout in milliseconds
	 * @return true if connected
	 * @throws java.io.IOException if any.
	 */
	public boolean syncConnect(SocketAddress addr, long timeout)
	throws IOException
	{		
		final Semaphore sem = new Semaphore(0);
		final IOException error[] = new IOException[1];
		setConnectListener(new ConnectionListener() {
			@Override
			public void onConnected(ListenableSocketChannel sender) {
				sem.release();
			}

			@Override
			public void onConnectFailed(ListenableSocketChannel sender, IOException err) {
				error[0] = err;
				sem.release();
			}});
		connect(addr);
		try {
			if (!sem.tryAcquire(timeout, TimeUnit.MILLISECONDS)) return false;
		} catch (InterruptedException e) {
			return false;
		}
		if (error[0]!=null) throw error[0];
		return true;
	}

	public interface ConnectionListener 
	{
		/**
		 * This event is triggered when an incoming connection has been 
		 * established, or when there was an error in connect attempt.
		 * The implementor must invoke getChannel().finishConnect()
		 * to determine which was the case.
		 * 
		 * @param sender sender
		 */
		void onConnected(ListenableSocketChannel sender);
		
		void onConnectFailed(ListenableSocketChannel sender, IOException error);
	}	
	
	public interface ReadableListener
	{
		/**
		 * This event is triggered either
		 *  (a) data can be read from the socket channel
		 *  (b) socket is disconnected (bytes read = 0) and channel must be closed
		 * @param sender sender
		 */
		void onDataReadable(ListenableSocketChannel sender);
	}
	
	public interface WriteableListener
	{
		/**
		 * Bytes can be written to the socket.
		 * @param sender sender
		 */
		void onDataWriteable(ListenableSocketChannel sender);		
	}	
	
	SelectListener selListener = new SelectListener() {
		@Override
		public void onSelected(AsyncSelector sender, SelectableChannel channel, int ops, int oldOps) {
			// NOTE We are on selector thread. 
			// Implementation here must not block
			try {
				if ((ops & SelectionKey.OP_CONNECT) != 0) {
					if (!connectHndLock.get()) {
						connectHndLock.set(true);
						executor.execute(connectRun);
					}
				}
				if ((ops & SelectionKey.OP_READ) != 0) {
					if (!readHndLock.get()) {
						readHndLock.set(true);
						executor.execute(readRun);
					}
				}
				if ((ops & SelectionKey.OP_WRITE) != 0) {
					if (!writeHndLock.get()) {
						writeHndLock.set(true);
						executor.execute(writeRun);
					}
				}
			} finally {
				sender.interestOps(channel, getInterestOps());
			}
		}
	};		
	
	/**
	 * Set connection listener (make its selector key interested on connect events)
	 *
	 * @param connectListener a {@link org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.ConnectionListener} object.
	 */
	public /* synchronized */  void setConnectListener(final ConnectionListener connectListener) {
		this.connectListener = connectListener;
		attemptUpdateInterestOps(SelectionKey.OP_CONNECT);
	}

	Runnable connectRun = new Runnable() {
		public void run() {
			assert(connectHndLock.get());
			ConnectionListener cl = getConnectListener();	
			try {
				if (cl!=null) {
					try {
						boolean ok = channel.finishConnect();
						if (cl!=null && ok) cl.onConnected(ListenableSocketChannel.this);
						if (cl!=null && !ok) cl.onConnectFailed(ListenableSocketChannel.this, new ConnectException());						
					} catch (IOException e) {
						if (cl!=null) cl.onConnectFailed(ListenableSocketChannel.this, e);						
					}
				}
			} finally {
				// synchronized(ListenableSocketChannel.this) {
					connectHndLock.set(false);
					if (executor != CurrentThreadExecutor.INSTANCE) {
						attemptUpdateInterestOps(SelectionKey.OP_CONNECT);
					}
						
				// }
			}
		}};
	
	/**
	 * Set Read listener (makes its selector key interested on read events)
	 *
	 * @param readListener a {@link org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.ReadableListener} object.
	 */
	public /*synchronized */ void setReadListener(final ReadableListener readListener) {
		this.readListener = readListener;
		attemptUpdateInterestOps(SelectionKey.OP_READ);
	}

	Runnable readRun = new Runnable() {
		public void run() {
			assert(readHndLock.get());
			ReadableListener rl = getReadListener();	
			try {
				if (rl!=null) rl.onDataReadable(ListenableSocketChannel.this);
			} finally {
//				synchronized(ListenableSocketChannel.this) {
					readHndLock.set(false);
					if (executor != CurrentThreadExecutor.INSTANCE) {
						attemptUpdateInterestOps(SelectionKey.OP_READ);
					}
						
//				}
			}
		}};
	
	/**
	 * Set write listener (makes its selector key interAsyncSocketImplested on write events)
	 *
	 * @param writeListener a {@link org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.WriteableListener} object.
	 */
	public /* synchronized */ void setWriteListener(final WriteableListener writeListener) {
		this.writeListener = writeListener;
		attemptUpdateInterestOps(SelectionKey.OP_WRITE);
	}
	
	Runnable writeRun = new Runnable() {
		public void run() {
			// $ In Executor Thread $
			assert(writeHndLock.get());
			WriteableListener wl = getWriteListener();	
			try {
				if (wl!=null) wl.onDataWriteable(ListenableSocketChannel.this);
			} finally {
//				synchronized(ListenableSocketChannel.this) {
					writeHndLock.set(false);
					if (executor != CurrentThreadExecutor.INSTANCE) {
						attemptUpdateInterestOps(SelectionKey.OP_WRITE);
					}
						
//				}
			}
		}};
	
	

	/**
	 * <p>Getter for the field <code>connectListener</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.ConnectionListener} object.
	 */
	public /* synchronized */ ConnectionListener getConnectListener() {
		return connectListener;
	}
	
	/**
	 * <p>Getter for the field <code>readListener</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.ReadableListener} object.
	 */
	public /* synchronized */  ReadableListener getReadListener() {
		return readListener;
	}
	
	/**
	 * <p>Getter for the field <code>writeListener</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.WriteableListener} object.
	 */
	public /* synchronized */  WriteableListener getWriteListener() {
		return writeListener;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void finalize() throws Throwable {
		try {
			close();
		} finally {
			super.finalize();
		}
	}
	
}
