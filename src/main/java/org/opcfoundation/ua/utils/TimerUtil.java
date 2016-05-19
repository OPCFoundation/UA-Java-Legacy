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
 *
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class TimerUtil {
	
	public static WeakReference<Timer> timer;
	
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
	 * 
	 */
	protected static void purge() {
		Timer t = timer!=null ? timer.get() : null;
		if (t!=null) 
			t.purge();
	}
	
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
