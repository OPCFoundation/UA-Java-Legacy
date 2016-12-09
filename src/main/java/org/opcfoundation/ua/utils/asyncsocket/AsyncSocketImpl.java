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
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.utils.IStatefulObject;
import org.opcfoundation.ua.utils.CurrentThreadExecutor;
import org.opcfoundation.ua.utils.StateListener;
import org.opcfoundation.ua.utils.AbstractState;
import org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.ConnectionListener;
import org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.ReadableListener;
import org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.WriteableListener;
import org.opcfoundation.ua.utils.bytebuffer.ByteQueue;

/**
 * Async socket.
 * <p>
 * The object has monitorable internal state {@link SocketState}.
 *
 * @see IStatefulObject Methods for monitoring the state
 * @see SocketState State of the socket
 * @see AsyncInputStream Async input stream
 * @see AsyncOutputStream Async output stream
 * @see BufferMonitor Stream alarms
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class AsyncSocketImpl extends AbstractState<SocketState, IOException> implements AsyncSocket, IStatefulObject<SocketState, IOException> {

	// Input buffer size (if alarms are not used)
	private final static int BUF_SIZE = 65536; 
	
	ListenableSocketChannel ls;
	SocketChannel chan;	 
	AsyncSocketInputStream is;
	AsyncSocketOutputStream os;
	Executor triggerExecutor;
	static Logger log = LoggerFactory.getLogger(AsyncSocketImpl.class);
			
	/**
	 * <p>Constructor for AsyncSocketImpl.</p>
	 *
	 * @throws java.io.IOException if any.
	 */
	public AsyncSocketImpl() 
	throws IOException 
	{
		this((SocketChannel) SocketChannel.open().configureBlocking(false), CurrentThreadExecutor.INSTANCE, new AsyncSelector());		
	}

	/**
	 * <p>Constructor for AsyncSocketImpl.</p>
	 *
	 * @param chan a {@link java.nio.channels.SocketChannel} object.
	 * @throws java.io.IOException if any.
	 */
	public AsyncSocketImpl(SocketChannel chan) 
	throws IOException 
	{
		this(chan, CurrentThreadExecutor.INSTANCE, new AsyncSelector());		
	}
	
	/**
	 * <p>Constructor for AsyncSocketImpl.</p>
	 *
	 * @param channel a {@link java.nio.channels.SocketChannel} object.
	 * @param triggerExecutor a {@link java.util.concurrent.Executor} object.
	 * @param selectorThread a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncSelector} object.
	 * @throws java.io.IOException if any.
	 */
	public AsyncSocketImpl(
			SocketChannel channel, 
			Executor triggerExecutor, 
			AsyncSelector selectorThread) 
	throws IOException 
	{
		super(channel.isConnected() ? SocketState.Connected : SocketState.Ready, SocketState.Error);
		this.triggerExecutor = triggerExecutor;
		ls = new ListenableSocketChannel(channel, CurrentThreadExecutor.INSTANCE, selectorThread);
		chan = ls.getChannel();
		is = new AsyncSocketInputStream();
		os = new AsyncSocketOutputStream();
		addStateListener(new StateListener<SocketState>() {
			@Override
			public void onStateTransition(
					IStatefulObject<SocketState, ?> monitor, SocketState oldState,
					SocketState newState) {
				if (SocketState.FINAL_STATES.contains(newState))
				{
					is.close();
					os.close();
				}
			}});
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onStateTransition(SocketState oldState, SocketState newState) {
		if (SocketState.FINAL_STATES.contains(newState))
		{
			is.close();
			os.close();
		}
	}
	
	/**
	 * <p>getInputStream.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncInputStream} object.
	 */
	public AsyncInputStream getInputStream()
	{
		return is;
	}
	
	/**
	 * <p>getOutputStream.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncOutputStream} object.
	 */
	public AsyncOutputStream getOutputStream()
	{
		return os;
	}
	
	/**
	 * <p>close.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncSocketImpl} object.
	 * @throws java.io.IOException if any.
	 */
	public AsyncSocketImpl close() 
	throws IOException 
	{
		ls.close();
		attemptSetState(SocketState.NON_FINAL_STATES, SocketState.Closed);
		return this;
	}
	
	/**
	 * <p>socketChannel.</p>
	 *
	 * @return a {@link java.nio.channels.SocketChannel} object.
	 */
	public SocketChannel socketChannel() {
		return chan;
	}
	
	/**
	 * <p>socket.</p>
	 *
	 * @return a {@link java.net.Socket} object.
	 */
	public Socket socket() {
		return chan.socket();
	}
	
	/**
	 * <p>setState.</p>
	 *
	 * @param state a {@link org.opcfoundation.ua.utils.asyncsocket.SocketState} object.
	 * @return a boolean.
	 */
	protected boolean setState(SocketState state) {
		// We are already in correct thread
		return super.setState(state, CurrentThreadExecutor.INSTANCE, null) == state;
	}
	
	/** {@inheritDoc} */
	@Override
	protected boolean isStateTransitionAllowed(SocketState oldState,
			SocketState newState) {
		return ! SocketState.FINAL_STATES.contains(getState()); 
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Async connect. The state is set to Connecting upon successful attempt
	 * (method returns without exeception).
	 * Once the connection attempt is finished the object state will shift
	 * either to Error or to Connected (See getState()).
	 * State changes can be monitored by attaching
	 * listeners (See addStateListener()).
	 */
	public void connect(SocketAddress addr)
	throws IOException 
	{
		assertNoError();
		SocketState state = getState();
		if (state!=SocketState.Ready)  {
			throw new IOException("Socket not ready");
		}
		synchronized(this) {
			try {
				ls.connect(addr);
				ls.setConnectListener(cl);
				setState(SocketState.Connecting);
			} catch (IOException e) {
				ls.setConnectListener(null);
				throw e;
			}
		}
	}
	
	/**
	 * <p>syncConnect.</p>
	 *
	 * @param addr a {@link java.net.SocketAddress} object.
	 * @return a boolean.
	 * @throws java.io.IOException if any.
	 */
	public boolean syncConnect(SocketAddress addr)
	throws IOException
	{
		connect(addr);
		try {
			waitForState(SocketState.CONNECTING_TRANSITION_STATES);
		} catch (InterruptedException e) {
			// should not happen
			return false;
		}
		if (getError()!=null) throw getError();
		return chan.isConnected();
	}
	
	/**
	 * <p>getStateMonitor.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.IStatefulObject} object.
	 */
	public IStatefulObject<SocketState, IOException> getStateMonitor()
	{
		return this;
	}
	
	/**
	 * Convenience method. Closes socket when all data is flushed
	 */
	public void closeOnFlush()
	{
		getOutputStream().createMonitor(
				getOutputStream().getPosition(),
				new MonitorListener() {
					@Override
					public void onStateTransition(
							IStatefulObject<BufferMonitorState, ?> monitor,
							BufferMonitorState oldState,
							BufferMonitorState newState) {
						try {
							close();
						} catch (Exception e) {
						}
					}});				
	}

	ConnectionListener cl = new ConnectionListener() {
		@Override
		public void onConnected(ListenableSocketChannel sender) {
			ls.setConnectListener(null);
			setState(SocketState.Connected);
		}
		@Override
		public void onConnectFailed(ListenableSocketChannel sender, IOException error) {
			ls.setConnectListener(null);
			setState(SocketState.Closed);
		}};  
	ReadableListener rl = new ReadableListener() {
		@Override
		public void onDataReadable(ListenableSocketChannel sender) {
			if (is.closed) {
				try {
					close();
				} catch (IOException e) {
				}
			} else {
				is.readChannel();
				is.prepareToReadMore();
			}
		}
	};
	WriteableListener wl = new WriteableListener() {
		@Override
		public void onDataWriteable(ListenableSocketChannel sender) {
			os.writeToChannel();
			os.checkWriteMore();			
		}		
	};
	
	class AsyncSocketInputStream extends AsyncInputStream {

		// Position sorted collection of alarms (All of which are in waiting state)
		TreeSet<BufferMonitor>		alarms = new TreeSet<BufferMonitor>(); 
		ByteQueue					q = new ByteQueue(16384);
		long						recvTargetPos;	// Position where to stop reading more data
		boolean						closed;
		long						bufSize = BUF_SIZE;
		
		@Override
		public synchronized int read() throws IOException {
			// Immediate read and return
			if (!q.isEmpty()) {
				int result = q.getReadChunk().get();
				prepareToReadMore();
				return result;
			}
				
			if (closed) {
				if (getState()==SocketState.Error) throw getError();
				return -1;
			}
			
			// Blocking read in async socket?! GAH. This will be painfully heavy.			
			while(true) {
				BufferMonitor a = createMonitor(getPosition()+1, null);
				try {
					BufferMonitorState as = a.waitForState(BufferMonitorState.FINAL_STATES);
					if (as == BufferMonitorState.Triggered) 
						synchronized(this) {
							if (q.isEmpty()) continue;
							int result = q.getReadChunk().get();
							prepareToReadMore();
							return result;				
						} 
					if (closed) {
						if (getState()==SocketState.Error) throw getError();
						return -1;
					}
				} catch (InterruptedException e) {
					throw new InterruptedIOException(e.getMessage());
				}
			}
		}
		

		@Override
		public int read(byte[] b) {
			return read(b, 0, b.length);
		}		
		
		@Override
		public synchronized int read(byte[] b, int off, int len) {
			if (b == null) {
			    throw new NullPointerException();
			} else if (off < 0 || len < 0 || len > b.length - off) {
			    throw new IndexOutOfBoundsException();
			} 
			
			int bytesToRead = Math.min(available(), len);
			if (bytesToRead>0) {
				q.get(b, off, bytesToRead);
				prepareToReadMore();
				return bytesToRead;
			}
			
			// EOF reached in the stream
			//SocketState state = getState();
			if (closed) {
				//if (state==SocketState.Error) throw getError();
				return -1;
			}
			return 0;			
		}		

		@Override
		public synchronized void read(ByteBuffer dst) {
			q.get(dst);
			prepareToReadMore();
		}

		@Override
		public synchronized void read(ByteBuffer dst, int length) {
			q.get(dst, length);
			prepareToReadMore();
		}

		@Override
		public synchronized ByteBuffer read(int len) {
			ByteBuffer result = q.get(len);
			prepareToReadMore();
			return result;
		}

		@Override
		public synchronized ByteBuffer[] readChunks(int len) {
			ByteBuffer[] result = q.getChunks(len);
			prepareToReadMore();
			return result;			
		}		
		
		@Override
		public synchronized void peek(byte[] buf) {
			q.peek(buf);
		}

		@Override
		public synchronized void peek(byte[] buf, int off, int len) {
			q.peek(buf, off, len);
		}

		@Override
		public synchronized ByteBuffer peek(int len) {
			return q.peek(len);
		}

		@Override
		public synchronized ByteBuffer[] peekChunks(int len) {
			return q.peekChunks(len);
		}

		
		
		@Override
		public synchronized int available() {
			long r = q.remaining();
			return r>Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)r; 
		}
		
		@Override
		public synchronized void close() {
			if (closed) return;
			closed = true;
			upRecvTarget(q.getBytesWritten()); // nulls readable listener
			// Trigger remaining alarms
			long maxPos = getMaxRecvSize();
			Iterator<BufferMonitor> i = alarms.iterator();
			while (i.hasNext()) {
				BufferMonitor a = i.next();
				if (a.triggerPos>maxPos)
				{
					if (AsyncSocketImpl.this.getState() == SocketState.Error)
						a.setError(getError());
					else
						a.close();
				}
				else {
					log.info("AsyncSocketInputStream.close(): unexpected untriggered monitor");
					a.trigger();
				}
			}
			alarms.clear();
		}
		
		/**
		 * Get the number of bytes received to this stream
		 * @return bytes received
		 */
		public synchronized long getReceivedBytes()
		{
			return q.getBytesWritten();
		}
		
		/**
		 * Get stream position.
		 * @return number of bytes read 
		 */
		public synchronized long getPosition()
		{
			return q.getBytesRead();
		}
		
		
		/**
		 * Invoked from SocketReadableListener
		 */
		private synchronized void readChannel()
		{
			// Only one thread goes here at a time thanks to the impl in ListenableSocketChannel.
			// Channel is readable
			
			// Create buffers
			try {
				int n = 0;
				do {
					n = chan.read( q.getWriteChunk() );
					if (n==-1)
					{
						setState(SocketState.Closed);
						return;
					}
					// XXX Could be a problem due to odd bugs in SocketChannel (never read 0)
				} while (n>0);			
			} catch (ClosedChannelException e) {
				setState(SocketState.Closed);
			} catch (IOException e) {
				setError(e);
			}

			// Set next buffer download target pos 
			prepareToReadMore();			
			
			// Trigger alarms
			if (!alarms.isEmpty()) {
				Iterator<BufferMonitor> i = alarms.iterator();
				while (i.hasNext()) {
					final BufferMonitor a = i.next();
					if (a.getTriggerPos()<=q.getBytesWritten())
					{
						i.remove();
						triggerExecutor.execute(new Runnable() {
							@Override
							public void run() {
								a.trigger();
							}});
//						a.trigger();
					} else break;
				}
			}
		}
				
		/**
		 * Create alarm for 
		 * @param position position to trigger
		 * @param listener alarm listener
		 * @return alarm
		 */
		public synchronized BufferMonitor createMonitor(long position, MonitorListener listener)
		{				
			BufferMonitor result = new BufferMonitor(position, triggerExecutor) {
				@Override
				public void cancel() {
					synchronized(AsyncSocketInputStream.this) {
						if (getState()!=BufferMonitorState.Waiting) return;
						alarms.remove(this);
						setState(BufferMonitorState.Canceled, eventExecutor, null);
					}
				}
				};
			if (listener!=null)
				result.addStateListener(listener);
			synchronized(this) {
				if (position<=q.getBytesWritten()) {
					// Trigger already reached
					result.trigger();
				} else if (position>getMaxRecvSize()) {
					// Trigger is unreachable
					if (AsyncSocketImpl.this.getState()==SocketState.Error) {
						result.setError(getError());
					} else {
						result.close();
					}
				}
				else {
					// Add alarm to the tree
					alarms.add(result);
					upRecvTarget(position);
				}
			}
			return result;				
		}
		
		
		synchronized void prepareToReadMore()
		{
			upRecvTarget(q.getBytesRead() + bufSize);
		}
		
		synchronized void upRecvTarget(long recvTargetPos)
		{
			if (closed) {
				this.recvTargetPos = q.getBytesWritten();
				ls.setReadListener( null );
				return;
			}
			this.recvTargetPos = Math.max(recvTargetPos, this.recvTargetPos);
			ls.setReadListener(q.getBytesWritten()<this.recvTargetPos ? rl : null);
		}
		
		long getMaxRecvSize() {
			if (closed || SocketState.FINAL_STATES.contains(getState()))
				return getReceivedBytes(); // End of stream
			return Long.MAX_VALUE;
		}


		@Override
		public int getBufferSize() {
			return (int) bufSize;
		}


		@Override
		public void setBufferSize(int size) {
			if (size<1) throw new IllegalArgumentException("buf size must be over 0");
			this.bufSize = size;
			prepareToReadMore();
		}

	}
	
	class AsyncSocketOutputStream extends AsyncOutputStream {

		// Position sorted collection of alarms (All of which are in waiting state)
		TreeSet<BufferMonitor> alarms = new TreeSet<BufferMonitor>(); 
		
		ByteQueue q = new ByteQueue(16384);		
		boolean closed;
		
		@Override
		public synchronized void write(int b) throws IOException {
			q.put((byte)b);
			writeToChannel();
			checkWriteMore();
		}
		
		@Override
		public synchronized void write(byte[] b, int off, int len) throws IOException {
			q.put(b, off, len);
			writeToChannel();
			checkWriteMore();
		}
		
		@Override
		public synchronized void offer(ByteBuffer buf) {
			q.offer(buf);
			writeToChannel();
			checkWriteMore();
		}

		@Override
		public synchronized void write(ByteBuffer src) {
			q.put(src);
			writeToChannel();
			checkWriteMore();
		}

		@Override
		public synchronized void write(ByteBuffer src, int length) {
			q.put(src, length);
			writeToChannel();
			checkWriteMore();
		}		

		private synchronized void checkWriteMore()
		{
			if (closed) {
				ls.setWriteListener(null);
				return;
			}
			boolean needToWrite = !q.isEmpty() && getState()==SocketState.Connected;
			ls.setWriteListener( needToWrite ? wl : null );
		}
		
		@Override
		public BufferMonitor createMonitor(long position,
				MonitorListener listener) {
			BufferMonitor result = new BufferMonitor(position, triggerExecutor) {
				@Override
				public void cancel() {
					synchronized(AsyncSocketOutputStream.this) {
						if (getState()!=BufferMonitorState.Waiting) return;
						alarms.remove(this);
						setState(BufferMonitorState.Canceled, eventExecutor, null);
					}
				}
			};
			if (listener!=null)
				result.addStateListener(listener);
			synchronized(this) {
				if (position>=q.getBytesRead()) {
					// Trigger already reached
					result.trigger();
				} else if (closed) {
					// Trigger is unreachable
					if (AsyncSocketImpl.this.getState()==SocketState.Error) {
						result.setError(getError());
					} else {
						result.close();
					}
				}
				else {
					// Add alarm to the tree
					alarms.add(result);
				}
			}
			return result;	
		}

		@Override
		public synchronized long getFlushPosition() {
			return q.getBytesRead();
		}

		@Override
		public synchronized long getPosition() {
			return q.getBytesWritten();
		}
		
		@Override
		public void flush() throws IOException {
			BufferMonitor a = createMonitor(getPosition(), null);
			try {
				a.waitForState(BufferMonitorState.FINAL_STATES);
			} catch (InterruptedException e) {
				throw new InterruptedIOException(e.getMessage());
			}
		}
		
		@Override
		public synchronized void close() {
			if (closed) return;
			closed = true;
			
			// Trigger Alarms
			Iterator<BufferMonitor> i = alarms.iterator();
			while (i.hasNext()) {
				BufferMonitor a = i.next();
				if (a.triggerPos>q.getBytesRead())
				{
					if (AsyncSocketImpl.this.getState() == SocketState.Error)
						a.setError(getError());
					else
						a.close();
				}
				else {
					log.error("AsyncSocketOutputStream.close(): unexpected untriggered monitor");
					a.trigger();
				}
			}
			alarms.clear();			
		}			
		
		private synchronized void writeToChannel()
		{
			// Only one thread goes here at a time due to the impl in ListenableSocketChannel.
			// Channel is writable		
			while (!q.isEmpty())
			{				
				try {				
					int bytesWritten = chan.write(q.getReadChunk());
					if (bytesWritten==0) break;
					if (bytesWritten==-1) {
						// EOF ??
						break;
					}
				} catch (IOException e) {
					setError(e);
					break;
				}
			}
			
			// Trigger alarms
			if (!alarms.isEmpty()) {
				Iterator<BufferMonitor> i = alarms.iterator();
				while (i.hasNext()) {
					final BufferMonitor a = i.next();
					if (a.getTriggerPos()<=q.getBytesRead())
					{
						i.remove();
						triggerExecutor.execute(new Runnable() {
							@Override
							public void run() {
								a.trigger();
							}});
//						a.trigger();
					} else break;
				}
			}
		}

		@Override
		public synchronized long getUnflushedBytes() {
			return q.remaining();
		}

		
	}
	
}
