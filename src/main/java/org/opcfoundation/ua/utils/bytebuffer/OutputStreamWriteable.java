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
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * Output stream writer
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class OutputStreamWriteable implements IBinaryWriteable {

	ByteOrder order = ByteOrder.nativeOrder();
	OutputStream out;
		
	/**
	 * <p>Constructor for OutputStreamWriteable.</p>
	 *
	 * @param out a {@link java.io.OutputStream} object.
	 */
	public OutputStreamWriteable(OutputStream out)
	{
		if (out==null) throw new IllegalArgumentException("null arg");
		this.out = out;
	}
	
	/**
	 * <p>getStream.</p>
	 *
	 * @return a {@link java.io.OutputStream} object.
	 */
	public OutputStream getStream()
	{
		return out;
	}

	void _put(int value)
	throws IOException
	{
		out.write(value);
	}
	
	/** {@inheritDoc} */
	@Override
	public void put(byte b) throws IOException {
		_put(b);
	}

	/** {@inheritDoc} */
	@Override
	public void put(ByteBuffer src) throws IOException {
		if (src.hasArray()) {
			byte array[] = src.array();
			put(array, src.position(), src.remaining());
			src.position(src.limit()); 
		} else 
			for (;src.hasRemaining();)
				_put(src.get());
	}

	/** {@inheritDoc} */
	@Override
	public void put(ByteBuffer src, int length) throws IOException {		
		if (src.hasArray()) {
			byte array[] = src.array();
			put(array, src.position(), length);
			src.position(length); 
		} else {
			for (int i=0; i<length; i++)
				_put(src.get());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void put(byte[] src, int offset, int length) throws IOException {
		out.write(src, offset, length);
	}

	/** {@inheritDoc} */
	@Override
	public void put(byte[] src) throws IOException {
		out.write(src);
	}

	/** {@inheritDoc} */
	@Override
	public void putDouble(double value) throws IOException {
		putLong(Double.doubleToLongBits(value));
	}

	/** {@inheritDoc} */
	@Override
	public void putFloat(float value) throws IOException {
		putInt(Float.floatToIntBits(value));
	}

	/** {@inheritDoc} */
	@Override
	public void putInt(int value) throws IOException {
		if (order == ByteOrder.BIG_ENDIAN) {
			_put(value >> 24);
			_put(value >> 16);
			_put(value >> 8);
			_put(value);
		} else {
			_put(value);
			_put(value >> 8);
			_put(value >> 16);
			_put(value >> 24);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void putLong(long value) throws IOException {
		if (order == ByteOrder.BIG_ENDIAN) {
			_put((int) (value >> 56));
			_put((int) (value >> 48));
			_put((int) (value >> 40));
			_put((int) (value >> 32));
			_put((int) (value >> 24));
			_put((int) (value >> 16));
			_put((int) (value >> 8));
			_put((int) (value));
		} else {
			_put((int) (value));
			_put((int) (value >> 8));
			_put((int) (value >> 16));
			_put((int) (value >> 24));
			_put((int) (value >> 32));
			_put((int) (value >> 40));
			_put((int) (value >> 48));
			_put((int) (value >> 56));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void putShort(short value) throws IOException {
		if (order == ByteOrder.BIG_ENDIAN) {
			_put(value >> 8);
			_put(value);
		} else {
			_put(value);
			_put(value >> 8);
		}
	}
	
	/**
	 * <p>order.</p>
	 *
	 * @return a {@link java.nio.ByteOrder} object.
	 */
	public ByteOrder order() {
		return order;
	}

	/** {@inheritDoc} */
	public void order(ByteOrder order) {
		this.order = order;
	}

	/** {@inheritDoc} */
	@Override
	public void flush() throws IOException {
		out.flush();
	}
	

}
