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
import java.util.concurrent.Executor;

import org.opcfoundation.ua.utils.AbstractState;

/**
 * BufferMonitor is a monitor that triggers when a specific position is reached.
 * The position to monitor in AsyncSocketInputStream is the number of bytes
 * received (buffered, not read) and in AsyncSocketOutputStream the number
 * of bytes flushed to TCP Stack.
 * <p>
 * User can set event listeners or wait for a state change.
 * E.g.
 *    // Block until stream has buffered 1000 bytes
 *    inputStream.
 *        createAlarm(inputStream.getPosition() + 1000, null).
 *        waitForState(AlarmState.FINAL_STATES);
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public abstract class BufferMonitor extends AbstractState<BufferMonitorState, IOException> implements Comparable<BufferMonitor> {

	long triggerPos;
	Executor eventExecutor;
		
	BufferMonitor(long triggerPos, Executor eventExecutor) {
		super(BufferMonitorState.Waiting, BufferMonitorState.Error);
		this.triggerPos = triggerPos;
		this.eventExecutor = eventExecutor;
	}
		
	/**
	 * <p>Getter for the field <code>triggerPos</code>.</p>
	 *
	 * @return a long.
	 */
	public long getTriggerPos()
	{
		return triggerPos;
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(BufferMonitor o) {
		long diff = triggerPos - o.triggerPos;			
		return diff==0 ? 0 : (diff<0 ? -1 : 1);
	}
		
	/**
	 * <p>cancel.</p>
	 */
	public synchronized void cancel()
	{
		if (getState() != BufferMonitorState.Waiting) return;
		setState(BufferMonitorState.Canceled, eventExecutor, null);
	}
	
	/**
	 * <p>setError.</p>
	 *
	 * @param e a {@link java.io.IOException} object.
	 */
	protected synchronized void setError(IOException e)
	{
		super.setError(e);
//		if (getState() != BufferMonitorState.Waiting) return;
//		this.error = e;
//		super.setState(BufferMonitorState.Error, eventExecutor, null);
	}
	
	synchronized void close()
	{
		if (getState() != BufferMonitorState.Waiting) return;
		super.setState(BufferMonitorState.Closed, eventExecutor, null);		
	}
	
	synchronized void trigger()
	{
		if (getState() != BufferMonitorState.Waiting) return;
		super.setState(BufferMonitorState.Triggered, eventExecutor, null);		
	}
	
	
}
