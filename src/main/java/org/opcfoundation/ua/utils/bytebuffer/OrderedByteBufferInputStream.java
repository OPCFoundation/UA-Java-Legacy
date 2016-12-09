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

package org.opcfoundation.ua.utils.bytebuffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.util.TreeMap;

/**
 * Input stream with a sequence of ByteBuffers as backend.
 * ByteBuffers can be submitted in random order.
 * Input stream sleeps until data becomes available.
 * Sequence number determines the order of how the data becomes visible
 * to the input stream.
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class OrderedByteBufferInputStream extends InputStream {

	/** Active byte buffer */
	ByteBuffer cur;
	/** Current sequence number */
	int next = 0;
	/** last sequence number */
	int last = -1;
	/** close sequence number */
	int close = -1;
	/** Sorted byte buffers */
	TreeMap<Integer, ByteBuffer> bufs = new TreeMap<Integer, ByteBuffer>();
	
	/**
	 * <p>Constructor for OrderedByteBufferInputStream.</p>
	 */
	public OrderedByteBufferInputStream() {
		super();
	}
	
	/**
	 * Submits a byte buffer to the use of input stream
	 *
	 * @param sequenceNumber a int.
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 */
	public synchronized void offer(int sequenceNumber, ByteBuffer buf)
	{			
		if (sequenceNumber<0 || bufs.containsKey(sequenceNumber) || sequenceNumber<next)
			throw new IllegalArgumentException("sequence number");
		if (close>0 && sequenceNumber>=close)
			throw new RuntimeException("Cannot put data at "+sequenceNumber+" because the stream was closed at "+close);
		if (sequenceNumber>last)
			last = sequenceNumber;
		bufs.put(sequenceNumber, buf);
		this.notify();
	}
	
	/**
	 * Submits a byte buffer for the input stream to use
	 *
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 */
	public void offer(ByteBuffer buf)
	{
		offer(last+1, buf);
	}

	/**
	 * <p>close.</p>
	 */
	public void close()
	{
		close(last+1);
	}
	
	/**
	 * <p>close.</p>
	 *
	 * @param sequenceNumber a int.
	 */
	public synchronized void close(int sequenceNumber)
	{
		if (close>=0) return; //throw new RuntimeException("Already closed");
		if (sequenceNumber<0 || sequenceNumber<=last)
			throw new IllegalArgumentException("sequence number illegal");
		close = sequenceNumber;
		notifyAll();
	}
	
	/**
	 * <p>forceClose.</p>
	 */
	public synchronized void forceClose()
	{
		close = next;
		notifyAll();
	}
	
	/**
	 * Returns a byte buffer with data or null if end of stream 
	 * @return byte buffer with data or null if end of stream
	 */
	private synchronized ByteBuffer getByteBuffer() 
	throws InterruptedIOException {
		if (cur!=null && cur.hasRemaining()) return cur;
		if (cur!=null && !cur.hasRemaining()) cur = null;
		while (cur==null) {
			if (next>=close && close>0) return null;
			cur = bufs.remove(next);
			if (cur==null)
				try {
					wait();
				} catch (InterruptedException e) {
					throw new InterruptedIOException();
				}
			else { 
				next++;
				if (!cur.hasRemaining()) cur = null;
			}
		}
		return cur;
	}
	
	/** {@inheritDoc} */
	@Override
	public synchronized int read() throws IOException {
		ByteBuffer b = getByteBuffer();
		if (b==null) return -1;		
		return b.get() & 0xff;
	}
	
	/** {@inheritDoc} */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int bytesRead = 0;
		while (len>0) {
			ByteBuffer buf = getByteBuffer();
			if (buf==null) return (bytesRead>0) ? bytesRead : -1;
			int n = Math.min(buf.remaining(), len);
			buf.put(b, off, n);
			off += n;
			bytesRead += n;
			len -= n;
		}
		return bytesRead;
	}
	
	/** {@inheritDoc} */
	@Override
	public synchronized int available() throws IOException {
		int result = 0;
		if (cur!=null) result += cur.remaining();
		for (int i = next; i<=last; i++) {
			ByteBuffer b = bufs.get(i);
			if (b==null) break;
			result += b.remaining();
		}
		return result;
	}
	
	/**
	 * Get all offered chunks. Available only after stream is closed 
	 * @return
	 */
/*
    [Ei toimi koska puskurit poistetaan getByteBuffer]	
	public synchronized ByteBuffer[] getChunks() {
		if (close<0) throw new RuntimeException("Not closed");
		ByteBuffer[] result = new ByteBuffer[close];
		for (int i=0; i<close; i++)
			result[i] = bufs.get(i);
		return result;
	}
*/
}
