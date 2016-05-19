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
	
	public ByteBufferWriteable(ByteBuffer buf)
	{
		if (buf == null)
			throw new IllegalArgumentException("null");
		this.buf = buf;
	}

	@Override
	public void put(byte b) {
		buf.put(b);
	}

	@Override
	public void put(ByteBuffer src) {
		buf.put(src);
	}

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

	@Override
	public void put(byte[] src, int offset, int length) {
		buf.put(src, offset, length);
	}

	@Override
	public void put(byte[] src) {
		buf.put(src);
	}

	@Override
	public void putDouble(double value) {
		buf.putDouble(value);
	}

	@Override
	public void putFloat(float value) {
		buf.putFloat(value);
	}

	@Override
	public void putInt(int value) {
		buf.putInt(value);
	}

	@Override
	public void putLong(long value) {
		buf.putLong(value);
	}

	@Override
	public void putShort(short value) {
		buf.putShort(value);
	}

	@Override
	public ByteOrder order() {
		return buf.order();
	}

	@Override
	public void order(ByteOrder order) {
		buf.order(order);
	}

	@Override
	public void flush() {
	}
	
	public long position() throws IOException {
		return buf.position();
	}
	
	public void position(long newPosition) throws IOException {
		if (newPosition>=Integer.MAX_VALUE || newPosition<0) throw new IllegalArgumentException("index out of range");
		buf.position((int) newPosition);		
	}
	
}
