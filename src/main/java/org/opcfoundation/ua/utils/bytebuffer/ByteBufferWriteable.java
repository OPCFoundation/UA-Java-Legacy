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
 * IWriteable implementation with ByteBuffer as backend
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ByteBufferWriteable implements IBinaryWriteable {

	ByteBuffer buf;
	
	/**
	 * <p>Constructor for ByteBufferWriteable.</p>
	 *
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 */
	public ByteBufferWriteable(ByteBuffer buf)
	{
		if (buf == null)
			throw new IllegalArgumentException("null");
		this.buf = buf;
	}

	/** {@inheritDoc} */
	@Override
	public void put(byte b) {
		buf.put(b);
	}

	/** {@inheritDoc} */
	@Override
	public void put(ByteBuffer src) {
		buf.put(src);
	}

	/** {@inheritDoc} */
	@Override
	public void put(ByteBuffer src, int length) {
		if (src.hasArray()) {
			byte[] array = src.array();
			buf.put(array, src.arrayOffset() + src.position(), length);
		} else {
			for (int i=0; i<length; i++)
				buf.put(src.get());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void put(byte[] src, int offset, int length) {
		buf.put(src, offset, length);
	}

	/** {@inheritDoc} */
	@Override
	public void put(byte[] src) {
		buf.put(src);
	}

	/** {@inheritDoc} */
	@Override
	public void putDouble(double value) {
		buf.putDouble(value);
	}

	/** {@inheritDoc} */
	@Override
	public void putFloat(float value) {
		buf.putFloat(value);
	}

	/** {@inheritDoc} */
	@Override
	public void putInt(int value) {
		buf.putInt(value);
	}

	/** {@inheritDoc} */
	@Override
	public void putLong(long value) {
		buf.putLong(value);
	}

	/** {@inheritDoc} */
	@Override
	public void putShort(short value) {
		buf.putShort(value);
	}

	/** {@inheritDoc} */
	@Override
	public ByteOrder order() {
		return buf.order();
	}

	/** {@inheritDoc} */
	@Override
	public void order(ByteOrder order) {
		buf.order(order);
	}

	/** {@inheritDoc} */
	@Override
	public void flush() {
	}
	
	/**
	 * <p>position.</p>
	 *
	 * @return a long.
	 * @throws java.io.IOException if any.
	 */
	public long position() throws IOException {
		return buf.position();
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
	
}
