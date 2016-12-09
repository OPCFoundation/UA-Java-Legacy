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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * <p>ByteBufferArrayReadable class.</p>
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ByteBufferArrayReadable implements IBinaryReadable {

	ByteQueue q;
	
	/**
	 * <p>Constructor for ByteBufferArrayReadable.</p>
	 *
	 * @param bufs an array of {@link java.nio.ByteBuffer} objects.
	 */
	public ByteBufferArrayReadable(ByteBuffer[] bufs) {		
		if (bufs == null)
			throw new IllegalArgumentException("null");
		q = new ByteQueue();
		for (ByteBuffer buf : bufs)
			q.offer(buf);
	}	
	
	/**
	 * <p>Constructor for ByteBufferArrayReadable.</p>
	 *
	 * @param q a {@link org.opcfoundation.ua.utils.bytebuffer.ByteQueue} object.
	 */
	public ByteBufferArrayReadable(ByteQueue q) {		
		if (q == null)
			throw new IllegalArgumentException("null");
		this.q = q;
	}		
	
	/** {@inheritDoc} */
	@Override
	public ByteOrder order() {
		return q.order();
	}

	/** {@inheritDoc} */
	@Override
	public void order(ByteOrder order) {
		q.order(order);
	}
	
	/** {@inheritDoc} */
	@Override
	public byte get() throws IOException {
		return q.getReadChunk().get();
	}

	/** {@inheritDoc} */
	@Override
	public void get(byte[] dst, int offset, int length) throws IOException {
		q.get(dst, offset, length);		
	}

	/** {@inheritDoc} */
	@Override
	public void get(byte[] dst) throws IOException {
		q.get(dst);
	}

	/** {@inheritDoc} */
	@Override
	public void get(ByteBuffer buf) throws IOException {
		q.get(buf);
	}

	/** {@inheritDoc} */
	@Override
	public void get(ByteBuffer buf, int length) throws IOException {
		q.get(buf, length);
	}

	/** {@inheritDoc} */
	@Override
	public double getDouble() throws IOException {
		if (q.getReadChunk().remaining()>=8)
			return q.getReadChunk().getDouble();
		return q.get(8).getDouble();
	}

	/** {@inheritDoc} */
	@Override
	public float getFloat() throws IOException {
		if (q.getReadChunk().remaining()>=4)
			return q.getReadChunk().getFloat();
		return q.get(4).getFloat();
	}

	/** {@inheritDoc} */
	@Override
	public int getInt() throws IOException {
		if (q.getReadChunk().remaining()>=4)
			return q.getReadChunk().getInt();
		return q.get(4).getInt();
	}

	/** {@inheritDoc} */
	@Override
	public long getLong() throws IOException {
		if (q.getReadChunk().remaining()>=8)
			return q.getReadChunk().getLong();
		return q.get(8).getLong();
	}

	/** {@inheritDoc} */
	@Override
	public short getShort() throws IOException {
		if (q.getReadChunk().remaining()>=2)
			return q.getReadChunk().getShort();
		return q.get(2).getShort();
	}


	/** {@inheritDoc} */
	@Override
	public long limit() {
		return q.getBytesWritten();
	}

	/** {@inheritDoc} */
	@Override
	public long position() {
		return q.getBytesRead();
	}


}
