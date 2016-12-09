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
 * <p>ByteBufferReadable class.</p>
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ByteBufferReadable implements IBinaryReadable {
	
	ByteBuffer buf;
	
	/**
	 * <p>Constructor for ByteBufferReadable.</p>
	 *
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 */
	public ByteBufferReadable(ByteBuffer buf) {		
		if (buf == null)
			throw new IllegalArgumentException("null");
		this.buf = buf;
	}

	/**
	 * <p>Constructor for ByteBufferReadable.</p>
	 *
	 * @param buf an array of byte.
	 */
	public ByteBufferReadable(byte[] buf) {		
		if (buf == null)
			throw new IllegalArgumentException("null");
		this.buf = ByteBuffer.wrap(buf);		
	}

	
	/** {@inheritDoc} */
	@Override
	public byte get() {
		return buf.get();
	}

	/** {@inheritDoc} */
	@Override
	public void get(byte[] dst, int offset, int length) {
		buf.get(dst, offset, length);
	}

	/** {@inheritDoc} */
	@Override
	public void get(byte[] dst) {
		buf.get(dst);
	}

	/** {@inheritDoc} */
	@Override
	public void get(ByteBuffer buf) {		
		if (buf.hasArray()) {
			this.buf.get(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
			buf.position(buf.capacity());
		} else {
			buf.put(buf);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void get(ByteBuffer buf, int length) {
		if (buf.hasArray()) {
			this.buf.get(buf.array(), buf.arrayOffset() + buf.position(), length);
			buf.position(buf.position() + length);
		} else {
//			int len = Math.min( Math.min( buf.remaining(), this.buf.remaining() ), length);
			int len = length;
			int origLimit = this.buf.limit();
			try {
				this.buf.limit(this.buf.position()+len);
				buf.put(this.buf);
			} finally {
				this.buf.limit(origLimit);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public double getDouble() {
		return buf.getDouble();
	}

	/** {@inheritDoc} */
	@Override
	public float getFloat() {
		return buf.getFloat();
	}

	/** {@inheritDoc} */
	@Override
	public int getInt() {
		return buf.getInt();
	}

	/** {@inheritDoc} */
	@Override
	public long getLong() {
		return buf.getLong();
	}

	/** {@inheritDoc} */
	@Override
	public short getShort() {
		return buf.getShort();
	}

	/** {@inheritDoc} */
	@Override
	public long limit() {
		return buf.limit();
	}
	
	/** {@inheritDoc} */
	@Override
	public long position() {
		return buf.position();
	}
	
	/**
	 * <p>order.</p>
	 *
	 * @return a {@link java.nio.ByteOrder} object.
	 */
	public ByteOrder order() {
		return buf.order();
	}

	/** {@inheritDoc} */
	public void order(ByteOrder order) {
		buf.order(order);
	}

	/**
	 * <p>position.</p>
	 *
	 * @param newPosition a int.
	 * @throws java.io.IOException if any.
	 */
	public void position(int newPosition) throws IOException {
		buf.position(newPosition);
	}

	/**
	 * <p>position.</p>
	 *
	 * @param newPosition a long.
	 * @throws java.io.IOException if any.
	 */
	public void position(long newPosition) throws IOException {
		if (newPosition>=Integer.MAX_VALUE || newPosition<0) throw new IllegalArgumentException("index out of range");
		buf.position((int) newPosition);
	}

	/**
	 * <p>skip.</p>
	 *
	 * @param bytes a long.
	 * @throws java.io.IOException if any.
	 */
	public void skip(long bytes) throws IOException {
		long newPosition = bytes + position();
		position( newPosition );
	}
	
}
