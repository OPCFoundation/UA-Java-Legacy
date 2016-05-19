 /* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.unittests;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.opcfoundation.ua.utils.IStatefulObject;
import org.opcfoundation.ua.utils.asyncsocket.AsyncInputStream;
import org.opcfoundation.ua.utils.asyncsocket.AsyncOutputStream;
import org.opcfoundation.ua.utils.asyncsocket.AsyncSocketImpl;
import org.opcfoundation.ua.utils.asyncsocket.BufferMonitorState;
import org.opcfoundation.ua.utils.asyncsocket.MonitorListener;

/**
 *
 * 
 */
public class TestAsyncSocket extends SocketTestBench {

	int n = 1024*1024*1;
	byte testData[] = fill( new byte[ n ] );		
	
	AsyncSocketImpl s;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		s = new AsyncSocketImpl();
	}
	
	@Override
	protected void tearDown() throws Exception {
		s.close();
		s = null;
		super.tearDown();		
	}

	public void testWrite() 
	throws Exception
	{		
		final Semaphore sem = new Semaphore(0);
		s.syncConnect(addr);
		
		final AsyncInputStream in = s.getInputStream();
		in.createMonitor(n, new MonitorListener() {
			@Override
			public void onStateTransition(IStatefulObject<BufferMonitorState, ?> sender, BufferMonitorState oldState, BufferMonitorState newState) {
				if (newState != BufferMonitorState.Triggered) fail();
				System.out.println("Read");
				byte data[] = new byte[ n ];
				in.read(data);				
				if (!Arrays.equals(testData, data)) fail("Data corrupted.");
				System.out.println("Verified");
				sem.release();
			}});				System.out.println("connecting1");

		
		AsyncOutputStream out = s.getOutputStream();
		out.write(testData);
		out.createMonitor(n, new MonitorListener() {
			@Override
			public void onStateTransition(IStatefulObject<BufferMonitorState, ?> sender, BufferMonitorState oldState, BufferMonitorState newState) {
				System.out.println("Written");
				sem.release();
			}});

		try {
			assertTrue( sem.tryAcquire(2, 1000, TimeUnit.SECONDS) );
		} catch (InterruptedException e) {
			fail();
		}
		
	}
	

	public void testAlarm() 
	throws Exception
	{
		s.syncConnect(addr);
		final Semaphore sem = new Semaphore(0);
		final AsyncInputStream in = s.getInputStream();
		final AsyncOutputStream out = s.getOutputStream();

		in.createMonitor(5, new MonitorListener() {
			@Override
			public void onStateTransition(IStatefulObject<BufferMonitorState, ?> sender, BufferMonitorState oldState, BufferMonitorState newState) {
				if (newState==BufferMonitorState.Triggered)
				{
					System.out.println("past 5");
					sem.release();
				}
			}});

		in.createMonitor(3, new MonitorListener() {
			@Override
			public void onStateTransition(IStatefulObject<BufferMonitorState, ?> sender, BufferMonitorState oldState, BufferMonitorState newState) {
				if (newState==BufferMonitorState.Triggered)
				{
					System.out.println("past 3");
					sem.release();
				}
			}});
		
		in.createMonitor(10, new MonitorListener() {
			@Override
			public void onStateTransition(IStatefulObject<BufferMonitorState, ?> sender, BufferMonitorState oldState, BufferMonitorState newState) {
				if (newState==BufferMonitorState.Triggered)
				{
					System.out.println("past 10");
					sem.release();
				}
			}});
		
		in.createMonitor(testData.length*5, new MonitorListener() {
			@Override
			public void onStateTransition(IStatefulObject<BufferMonitorState, ?> sender, BufferMonitorState oldState, BufferMonitorState newState) {
				if (newState==BufferMonitorState.Closed)
				{
					System.out.println(testData.length*5+" never triggered");
					sem.release();
				}
			}});
		
		
		out.write(testData, 0, 2);  out.flush();
		out.write(testData, 2, 2);  out.flush();
		out.write(testData, 4, 2);  out.flush();
		out.write(testData, 6, 2);  out.flush();
		out.write(testData, 8, 2);  out.flush();
		out.write(testData, 10, 2); out.flush();		
		Thread.sleep(1000L);
		s.close();
		
		try {
			assertTrue( sem.tryAcquire(4, 10, TimeUnit.SECONDS) );
		} catch (InterruptedException e) {
			fail();
		}
		
	}
	
	
}
