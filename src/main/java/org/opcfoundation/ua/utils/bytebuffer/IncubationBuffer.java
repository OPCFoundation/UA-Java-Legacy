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
import java.util.Iterator;

import org.opcfoundation.ua.utils.IncubationQueue;

/**
 * Input stream with a sequence of ByteBuffers as backend.
 * The data in ByteBuffers in read in the order they are "incubated"
 * The data becomes available when the ByteBuffers are "hatched"
 * Input stream blocks until data becomes available.
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class IncubationBuffer extends InputStream {

	/** Constant <code>CLOSED_MARKER</code> */
	protected final static ByteBuffer CLOSED_MARKER = ByteBuffer.allocate(0);
	protected IncubationQueue<ByteBuffer> queue = new IncubationQueue<ByteBuffer>(true);
	protected ByteBuffer cur;
	
	/**
	 * <p>Constructor for IncubationBuffer.</p>
	 */
	public IncubationBuffer() {
		super();
	}
	
	/**
	 * Submits a byte buffer to the use of input stream
	 *
	 * @param buf byte buffer to offer for use
	 */
	public void incubate(ByteBuffer buf)
	{
		synchronized(queue) {
		queue.incubate(buf);
		}
	}
	
	/**
	 * Makes the byte buffer available to input stream reader
	 *
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 */
	public void hatch(ByteBuffer buf)
	{
		synchronized(queue) {
		queue.hatch(buf);
		}
	}
	
	/**
	 * <p>close.</p>
	 */
	public void close()
	{
		synchronized(queue) {
		queue.incubate(CLOSED_MARKER);
		queue.hatch(CLOSED_MARKER);
		}
	}
	
	/**
	 * <p>forceClose.</p>
	 */
	public void forceClose()
	{
		synchronized(queue) {
		queue.clear();
		queue.incubate(CLOSED_MARKER);
		queue.hatch(CLOSED_MARKER);
		}
	}
	
	/**
	 * Returns a byte buffer with data or null if end of stream 
	 * @return byte buffer with data or null if end of stream
	 */
	private ByteBuffer getByteBuffer() 
	throws InterruptedIOException {
		synchronized(queue) {
		if (cur==CLOSED_MARKER) return null;
		if (cur!=null && cur.hasRemaining()) return cur;
		if (cur!=null && !cur.hasRemaining()) cur = null;
		try {
			cur = queue.removeNextHatched();
		} catch (InterruptedException e) {
			throw new InterruptedIOException();
		}
		if (cur==CLOSED_MARKER) return null;
		return cur;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public int read() throws IOException {
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
			buf.get(b, off, n);
			off += n;
			bytesRead += n;
			len -= n;
		}
		return bytesRead;
	}

	/** {@inheritDoc} */
	@Override
	public int available() throws IOException {
		synchronized(queue) {
		
		int result = 0;
		if (cur!=null) result += cur.remaining();
		Iterator<ByteBuffer> i = queue.iterator();
		while (i.hasNext()) {
			ByteBuffer o = i.next();
			if (!queue.isHatched(o)) break;
			result += o.remaining();
		}
		return result;
		
		}
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
