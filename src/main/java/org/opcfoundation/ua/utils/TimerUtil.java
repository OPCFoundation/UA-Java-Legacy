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

package org.opcfoundation.ua.utils;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

/**
 * <p>TimerUtil class.</p>
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class TimerUtil {
	
	/** Constant <code>timer</code> */
	public static WeakReference<Timer> timer;
	
	/**
	 * <p>Getter for the field <code>timer</code>.</p>
	 *
	 * @return a {@link java.util.Timer} object.
	 */
	public synchronized static Timer getTimer()
	{
		Timer t = timer!=null ? timer.get() : null;
		if (t==null)
		{
			t = new Timer("UA Timer", true);
			timer = new WeakReference<Timer>(t);
		}
		return t;
	}
	
	/**
	 * <p>schedule.</p>
	 *
	 * @param run a {@link java.lang.Runnable} object.
	 * @param executor a {@link java.util.concurrent.Executor} object.
	 * @param systemTime a long.
	 * @return a {@link java.util.TimerTask} object.
	 */
	public static TimerTask schedule(final Runnable run, final Executor executor, long systemTime)
	{
		if (run==null || executor==null)
			throw new IllegalArgumentException("null arg");
		long delay = systemTime - System.currentTimeMillis();
		if (delay<1) delay = 1;
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				executor.execute(run);
			}
			
			@Override
			public boolean cancel() {
				boolean result = super.cancel();				
				purge();	
				return result;
			}

		};
		getTimer().schedule(task, delay);
		return task;
	}
	
	/**
	 * <p>purge.</p>
	 */
	protected static void purge() {
		Timer t = timer!=null ? timer.get() : null;
		if (t!=null) 
			t.purge();
	}
	
	/**
	 * <p>schedule.</p>
	 *
	 * @param timer a {@link java.util.Timer} object.
	 * @param run a {@link java.lang.Runnable} object.
	 * @param executor a {@link java.util.concurrent.Executor} object.
	 * @param systemTime a long.
	 * @return a {@link java.util.TimerTask} object.
	 */
	public static TimerTask schedule(Timer timer, final Runnable run, final Executor executor, long systemTime)
	{
		if (run==null || executor==null)
			throw new IllegalArgumentException("null arg");
		long delay = systemTime - System.currentTimeMillis();
		if (delay<1) delay = 1;
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				executor.execute(run);
			}
			
			@Override
			public boolean cancel() {
				boolean result = super.cancel();				
				purge();	
				return result;
			}
		};
		timer.schedule(task, delay);
		return task;
	}	
}
