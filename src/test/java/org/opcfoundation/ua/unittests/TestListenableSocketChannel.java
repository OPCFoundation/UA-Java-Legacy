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
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel;
import org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.ReadableListener;
import org.opcfoundation.ua.utils.asyncsocket.ListenableSocketChannel.WriteableListener;

/**
 *
 * 
 */
public class TestListenableSocketChannel extends SocketTestBench {
	
	int n = 10000000;
	byte testData[] = fill( new byte[ n ] );		
	ListenableSocketChannel s;	
	byte[] dada = "1234567890".getBytes(Charset.forName("utf8"));
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Executor executor = new java.util.concurrent.ThreadPoolExecutor(0, 1,
				60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		s = new ListenableSocketChannel(executor);
	}
	
	@Override
	protected void tearDown() throws Exception {
		s.close();
		s = null;
		super.tearDown();
	}
	
	public void testConnect() 
	throws IOException, InterruptedException
	{
		boolean ok = s.syncConnect(addr, 10*1000);
		assertTrue( ok );
		s.close();
	}
	
	public void testRead() 
	throws IOException, InterruptedException
	{
		final Semaphore sem = new Semaphore(0);
		final byte[] result = new byte[dada.length];  
		s.syncConnect(addr, 10*1000);
		s.setReadListener(new ReadableListener() {
			@Override
			public void onDataReadable(ListenableSocketChannel sender) {
				System.out.println("data readable");
				ByteBuffer buf = ByteBuffer.allocate(1000);
				try {
					sender.getChannel().read(buf);
					buf.rewind();
					buf.get(result);
				} catch (IOException e) {					
					fail(e.getMessage());
				}
				sem.release();
			}});
		ByteBuffer data = ByteBuffer.allocate(dada.length);
		data.put(dada);
		data.rewind();
		s.getChannel().write(data);
		assertTrue( sem.tryAcquire(100, TimeUnit.SECONDS) );
		assertTrue( Arrays.equals(result, dada) );
	}
	
	public void testWrite()
	throws Exception
	{
		final Semaphore sem = new Semaphore(0);
		s.syncConnect(addr, 10*1000);
		final ByteBuffer buf = ByteBuffer.allocate(n);
		buf.put(testData);
		buf.rewind();
		
		// Read anything from loop-back
		s.setReadListener(new ReadableListener() {
			@Override
			public void onDataReadable(ListenableSocketChannel sender) {
				try {
					ByteBuffer buf = ByteBuffer.allocate(1000);
					buf.rewind();
					sender.getChannel().read(buf);
				} catch (IOException e) {					
					fail(e.getClass().getName()+" "+e.getMessage());
				}
			}});
		
		s.setWriteListener(new WriteableListener() {
			@Override
			public void onDataWriteable(ListenableSocketChannel sender) {
				assertEquals(this, sender.getWriteListener());
				try {
					sender.getChannel().write(buf);
				} catch (IOException e) {
					e.printStackTrace();
					fail(e.getMessage());
				}
				if (buf.remaining()==0) {
					System.out.println("Write done.");
					s.setReadListener(null);
					s.setWriteListener(null);
					sem.release();
				}
			}});
		assertTrue( sem.tryAcquire(100, TimeUnit.SECONDS) );
		System.out.println("semaphore acquired");
		buf.rewind();						

		assertTrue( Arrays.equals(buf.array(), testData) );	
		System.out.println("Closing.");
	}

	/**
	 * ListenableSocketChannel specifies that only one thread may read at the same time. 
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void testReadSynchronization() 
	throws IOException, InterruptedException
	{
		final Semaphore sem = new Semaphore(0);
		final AtomicInteger c = new AtomicInteger(0);
		final byte[] result = new byte[dada.length];  
		s.syncConnect(addr, 10*1000);
		s.setReadListener(new ReadableListener() {
			@Override
			public void onDataReadable(ListenableSocketChannel sender) {
				assertEquals( c.incrementAndGet(), 1 );
				try { Thread.sleep(2000); } catch (InterruptedException e) { }

				System.out.println("data readable");
				ByteBuffer buf = ByteBuffer.allocate(1000);
				try {
					sender.getChannel().read(buf);
					buf.rewind();
					buf.get(result);
				} catch (IOException e) {					
					fail(e.getMessage());
				}
				try { Thread.sleep(2000); } catch (InterruptedException e) { }
				
				assertEquals( c.decrementAndGet(), 0 );
				sem.release();
			}});
		ByteBuffer data = ByteBuffer.allocate(dada.length);
		data.put(dada);
		data.rewind();
		s.getChannel().write(data);
		assertTrue( sem.tryAcquire(100, TimeUnit.SECONDS) );
		assertTrue( Arrays.equals(result, dada) );
	}
	
	
	
}
