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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import junit.framework.TestCase;

/**
 * Test bench with a loop-back server 
 * 
 */
public abstract class SocketTestBench extends TestCase {

	public int port;
	public ServerSocket serverSocket; // Loop-Back server
	public List<Socket> sockets = new CopyOnWriteArrayList<Socket>();
	public SocketAddress addr;
	
	protected void setUp() throws Exception {
		serverSocket = new ServerSocket(0);
		port = serverSocket.getLocalPort();
		addr = serverSocket.getLocalSocketAddress();
		new Thread() {
			public void run() {
				try {
					while (!serverSocket.isClosed())
					{
						final Socket s = serverSocket.accept();
						sockets.add(s);
						new Thread() {
							public void run() {
								try {
									InputStream in = s.getInputStream();
									OutputStream out = s.getOutputStream();
									byte data[] = new byte[1024];
									while (!s.isClosed()) {
										int bytesRead = in.read(data);
										if (bytesRead>0) {
											out.write(data, 0, bytesRead);
											out.flush();
										}
									}
								} catch (SocketException ce) {
								} catch (ClosedChannelException ce) {
								} catch (IOException e) {
									e.printStackTrace();
								}
								sockets.remove(s);
							};
						}.start();
					}
				} catch (SocketException ce) {
				} catch (ClosedChannelException ce) {
				} catch (IOException e) {
					e.printStackTrace();
				}
				serverSocket = null;
			}
		}.start();
	}

	protected void tearDown() throws Exception {
		serverSocket.close();
		for (Socket s : sockets)
			s.close();
	}

	// Fill array with debug data
	public static byte[] fill(byte[] data)
	{
		Random r = new Random(data.length);
		for (int i=0; i<data.length; i++)
			data[i] = (byte) (r.nextInt(256) - 128);
		return data;
	}
	
	// Verify array of debug data
	public static boolean verify(byte[] data)
	{
		Random r = new Random(data.length);
		for (int i=0; i<data.length; i++) 
			if (data[i] != (byte) (r.nextInt(256) - 128))
				return false;
		return true;
	}
	
}
