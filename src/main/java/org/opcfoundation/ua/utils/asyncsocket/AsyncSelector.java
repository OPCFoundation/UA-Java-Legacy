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
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.utils.State;

/**
 * Create asyncronous selector. Selector has one selector thread for each cpu
 * core in the system. SelectionKeys are listened with register method.
 * <p>
 * To close async selector, close its selector (getSelector().close()).
 * <p>
 * AsyncSelector guarantees that selection event of a key is handled in one
 * thread at a time, and it the event handled accordingly, new selection events do not occur.
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class AsyncSelector implements Runnable {

	private enum SelectorState {Active, Disabled};
	private final static EnumSet<SelectorState> ENABLED_STATE = EnumSet.of(SelectorState.Active);
	private final ThreadGroup THREAD_GROUP = new ThreadGroup("Async Selector");

	/** Selector state */
	State<SelectorState>				state = new State<SelectorState>(SelectorState.Active);
	/** Selector */
	Selector							sel;
	/** Registered keys and their handlers */
	Map<SelectionKey, SelectListener>	map = new ConcurrentHashMap<SelectionKey, SelectListener>();
	/** Selector Thread */
	Thread								thread;

	Object registerLock = new Object();
	
	/**
	 * Construct AsyncSelector with brand new selector
	 *
	 * @throws java.io.IOException if any.
	 */
	public AsyncSelector() throws IOException {
		this(Selector.open());
	}
	
	/**
	 * Construct new AsyncSelector
	 *
	 * @param sel a {@link java.nio.channels.Selector} object.
	 * @throws java.io.IOException if any.
	 */
	public AsyncSelector(Selector sel) throws IOException {
		this.sel = sel;		
		thread = new Thread(THREAD_GROUP, this, "Selector");
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * <p>getSelector.</p>
	 *
	 * @return a {@link java.nio.channels.Selector} object.
	 */
	public Selector getSelector() {
		return sel;
	}

	/**
	 * Modify interest ops of a key.
	 *
	 * @param channel registered key
	 * @param interestOps new interest op set
	 * @throws java.nio.channels.CancelledKeyException if any.
	 */
	public void interestOps(SelectableChannel channel, int interestOps) 
			throws CancelledKeyException {
		if (channel != null) {
			SelectionKey key = channel.keyFor(sel);
			
			if (key == null)
				throw new IllegalArgumentException(
						"Key is not registered to channel");
			
			if (!key.isValid())
				return;			
			
			synchronized (channel) {
				int newOps = key.interestOps();
				switch (interestOps) {
				case ~SelectionKey.OP_READ:
				case ~SelectionKey.OP_WRITE:
				case ~SelectionKey.OP_CONNECT:
					newOps = newOps & interestOps;
					break;
				default:
					newOps = newOps | interestOps;
					break;
				}
										
				if (newOps != key.interestOps()) {
					key.interestOps(newOps);
					sel.wakeup();
				}
			}
			
		}
	}
	
	private void enable()
	{
		state.setState(SelectorState.Active);
	}
	
	/**
	 * Acquire select permission
	 */
	private void disable()
	{
		state.setState(SelectorState.Disabled);
		sel.wakeup();
	}

	/**
	 * Register a selection event handler to a selectable channel.
	 * <p>
	 * selectEventListener is invoked by one thread at a time.
	 * The rule of thumb is that the listener must not block.
	 *
	 * Note! If channel is registered and closed, select event is invoked
	 * until the channel is unregistered.
	 *
	 * @param channel a {@link java.nio.channels.SelectableChannel} object.
	 * @param ops initial interest ops See {@link SelectionKey}
	 * @param selectEventListener a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncSelector.SelectListener} object.
	 * @throws java.nio.channels.ClosedChannelException if any.
	 */
	public void register(SelectableChannel channel,
			int ops, SelectListener selectEventListener) throws ClosedChannelException {
		
		synchronized(registerLock) {
			disable();
			try {
				// register blocks if any thread select()s
				SelectionKey key = channel.register(sel, ops);
				map.put(key, selectEventListener);
			} finally {
				enable();		
			}	
		}			
	}

	/**
	 * <p>unregister.</p>
	 *
	 * @param channel a {@link java.nio.channels.SelectableChannel} object.
	 */
	public void unregister(SelectableChannel channel) {
		SelectionKey key = channel.keyFor(sel);		
		if (key == null || !map.containsKey(key)) return;
		
		synchronized(registerLock) {
			disable();
			try {
				key.cancel();
				map.remove(key);
			} finally {
				enable();		
			}
		}	
	}
	
	/**
	 * <p>close.</p>
	 *
	 * @throws java.io.IOException if any.
	 */
	public void close() 
	throws IOException {
		sel.close();
		try {
			state.setState(SelectorState.Active);
			sel.wakeup();
		} catch (Exception e) {}
	}
	
	public interface SelectListener {
		/**
		 * Event for selected key.
		 * 
		 * Note! InterestSet of key is set to 0, the implementor must return as
		 * a result of onSelection the new set of interest ops.
		 * 
		 * Selection of a key is handled in one thread.
		 * 
		 * @param sender sender
		 * @param channel channel that selected
		 * @param selectOps selected event operations (See {@link SelectionKey}) 
		 * @param interestOps previous interest ops
		 */
		public void onSelected(AsyncSelector sender, SelectableChannel channel, int selectOps, int interestOps);
	}
	
	String toStr(int i)
	{
		String res = "[";
		if ((i & SelectionKey.OP_READ) != 0)res += "read";
		if ((i & SelectionKey.OP_CONNECT) != 0)res += "connect";
		if ((i & SelectionKey.OP_WRITE) != 0)res += "write";
		if ((i & SelectionKey.OP_ACCEPT) != 0)res += "accept";
		return res+"]";
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		try {
			while (sel.isOpen()) {
				state.waitForStateUninterruptibly(ENABLED_STATE);
				sel.select(1000);
				
				Set<SelectionKey> selectedKeys = sel.selectedKeys();
				for (SelectionKey key : selectedKeys) 
					try {
						key.interestOps(0);
					} catch(CancelledKeyException e) {/*ignore*/}
				
				for (SelectionKey key : selectedKeys) 
					try {
						Integer iop = key.interestOps();
						if (iop==null) continue;
						int readyOps = key.readyOps() /*| (iop & SelectionKey.OP_WRITE)*/;
						SelectListener l = map.get(key);
						if (l != null) l.onSelected(AsyncSelector.this, key.channel(), readyOps, iop);						
					} catch(CancelledKeyException e) {/*ignore*/}
					
				
				selectedKeys.clear();
			}
			
		} catch (ClosedSelectorException cse) {
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

}
