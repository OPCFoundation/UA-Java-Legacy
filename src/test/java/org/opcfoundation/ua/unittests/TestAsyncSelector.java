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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import org.opcfoundation.ua.utils.asyncsocket.AsyncSelector;
import org.opcfoundation.ua.utils.asyncsocket.AsyncSelector.SelectListener;

/**
 *
 * 
 */
public class TestAsyncSelector extends TestCase {

	AsyncSelector sel;
	protected void setUp() throws Exception {
		sel = new AsyncSelector();
	}

	protected void tearDown() throws Exception {
		sel = null;
	}
	
	public void testSelect() 
	throws IOException 
	{		
		final int n = 20;		
		ServerSocketChannel ss = ServerSocketChannel.open();		
		ss.configureBlocking(false);
		ss.socket().bind(new InetSocketAddress(0));
		final SocketAddress addr = ss.socket().getLocalSocketAddress();
		final Semaphore sem = new Semaphore(0);
		final AtomicInteger acceptCounter = new AtomicInteger();
		sel.register(ss, SelectionKey.OP_ACCEPT, new SelectListener() {
			@Override
			public void onSelected(AsyncSelector sender, SelectableChannel channel, int selectOps, int interestOps) {
				acceptCounter.incrementAndGet();
				try {
					SocketChannel c = ((ServerSocketChannel) channel).accept();
					c.close();
					sem.release();
				} catch (IOException e) {
					e.printStackTrace();
				}
				sender.interestOps(channel, SelectionKey.OP_ACCEPT);
			}});
		
		// Make n connections
		for (int i=0; i<n; i++)
		{
			new Thread() {
				public void run() {
					Socket s = new Socket();
					try {
						s.connect(addr);
					} catch (IOException e) {
						e.printStackTrace();
						assertTrue(false);
					}
				}
			}.start();
		}
		
		try {
			assertTrue( sem.tryAcquire(n, 1000, TimeUnit.SECONDS) );
		} catch (InterruptedException e) {
			fail();
		}
		sel.unregister(ss);
		ss.close();
		assertEquals(n, acceptCounter.get()); 			
	}
		
	
}
