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
 * <p>ByteBufferArrayWriteable class.</p>
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ByteBufferArrayWriteable implements IBinaryWriteable {

	ByteQueue q;
	ByteBuffer tmp = ByteBuffer.allocate(8);
	
	/**
	 * <p>Constructor for ByteBufferArrayWriteable.</p>
	 *
	 * @param bufs an array of {@link java.nio.ByteBuffer} objects.
	 */
	public ByteBufferArrayWriteable(ByteBuffer[] bufs) {		
		if (bufs == null)
			throw new IllegalArgumentException("null");
		q = new ByteQueue();
		tmp.order(q.order());
		for (ByteBuffer buf : bufs)
			q.offer(buf);
	}	
	
	/**
	 * <p>Constructor for ByteBufferArrayWriteable.</p>
	 *
	 * @param q a {@link org.opcfoundation.ua.utils.bytebuffer.ByteQueue} object.
	 */
	public ByteBufferArrayWriteable(ByteQueue q) {		
		if (q == null)
			throw new IllegalArgumentException("null");
		this.q = q;
		tmp.order(q.order());
	}		
	
	/** {@inheritDoc} */
	@Override
	public ByteOrder order() {
		return q.order();
	}

	/** {@inheritDoc} */
	@Override
	public void order(ByteOrder order) {
		tmp.order( order );
		q.order(order);
	}

	/** {@inheritDoc} */
	@Override
	public void put(byte b) throws IOException {
		q.getWriteChunk().put(b);
	}

	/** {@inheritDoc} */
	@Override
	public void put(ByteBuffer src) throws IOException {
		q.put(src);
	}

	/** {@inheritDoc} */
	@Override
	public void put(ByteBuffer src, int length) throws IOException {
		q.put(src, length);
	}

	/** {@inheritDoc} */
	@Override
	public void put(byte[] src, int offset, int length) throws IOException {
		q.put(src, offset, length);
	}

	/** {@inheritDoc} */
	@Override
	public void put(byte[] src) throws IOException {
		q.put(src);
	}

	/** {@inheritDoc} */
	@Override
	public void putDouble(double value) throws IOException {
		if (q.getWriteChunk().remaining()>8)
			q.getWriteChunk().putDouble(value);
		else {
			tmp.rewind();
			tmp.putDouble(value);
			tmp.rewind();
			q.put(tmp, 8);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void putFloat(float value) throws IOException {
		if (q.getWriteChunk().remaining()>4)
			q.getWriteChunk().putFloat(value);
		else {
			tmp.rewind();
			tmp.putFloat(value);
			tmp.rewind();
			q.put(tmp, 4);		
		}
	}

	/** {@inheritDoc} */
	@Override
	public void putInt(int value) throws IOException {
		if (q.getWriteChunk().remaining()>4)
			q.getWriteChunk().putInt(value);
		else {
			tmp.rewind();
			tmp.putInt(value);
			tmp.rewind();
			q.put(tmp, 4);		
		}
	}

	/** {@inheritDoc} */
	@Override
	public void putLong(long value) throws IOException {
		if (q.getWriteChunk().remaining()>8)
			q.getWriteChunk().putLong(value);
		else {
			tmp.rewind();
			tmp.putLong(value);
			tmp.rewind();
			q.put(tmp, 8);		
		}
	}

	/** {@inheritDoc} */
	@Override
	public void putShort(short value) throws IOException {
		if (q.getWriteChunk().remaining()>2)
			q.getWriteChunk().putShort(value);
		else {
			tmp.rewind();
			tmp.putShort(value);
			tmp.rewind();
			q.put(tmp, 2);		
		}
	}

	/** {@inheritDoc} */
	@Override
	public void flush() {		
	}

}
