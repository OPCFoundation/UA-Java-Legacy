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

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * Input stream reader
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class InputStreamReadable implements IBinaryReadable {

	ByteOrder order = ByteOrder.nativeOrder();
	InputStream is;
	long limit, position;
	
	/**
	 * <p>Constructor for InputStreamReadable.</p>
	 *
	 * @param is a {@link java.io.InputStream} object.
	 * @param limit a long.
	 */
	public InputStreamReadable(InputStream is, long limit)
	{
		this.is = is;
		this.limit = limit;
	}

	/**
	 * Get next byte
	 * @return 0..255
	 * @throws IOException
	 */
	int _get()
	throws IOException
	{
		int value = is.read();
		if (value==-1)
			throw new EOFException();
		position++;
		return value & 0xff;
	}
	
	/** {@inheritDoc} */
	@Override
	public byte get() 
    throws IOException	
	{
		return (byte) _get();
	}

	/** {@inheritDoc} */
	@Override
	public void get(byte[] dst, int offset, int length) 
    throws IOException	
	{
		while (length>0) {
			int bytesRead = is.read(dst, offset, length);
			if (bytesRead==-1) throw new EOFException();
			position+=bytesRead;			
			offset += bytesRead;
			length -= bytesRead;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void get(byte[] dst) 
    throws IOException	
	{
		get(dst, 0, dst.length);
	}

	/** {@inheritDoc} */
	@Override
	public void get(ByteBuffer buf) 
    throws IOException	
	{		
		for (;buf.hasRemaining();)
			buf.put((byte)_get());		
	}

	/** {@inheritDoc} */
	@Override
	public void get(ByteBuffer buf, int length) 
    throws IOException	
	{
		if (length<256) {
			for (int i=0; i<length; i++)
				buf.put((byte)_get());
		} else {
			byte[] b = new byte[length];
			get(b, 0, length);
			buf.put(b);
		}
	}

	/** {@inheritDoc} */
	@Override
	public double getDouble() 
    throws IOException	
	{
		return Double.longBitsToDouble(getLong());
	}

	/** {@inheritDoc} */
	@Override
	public float getFloat() 
    throws IOException	
	{
		return Float.intBitsToFloat(getInt());
	}

	/** {@inheritDoc} */
	@Override
	public int getInt() 
    throws IOException	
	{
		if (order == ByteOrder.BIG_ENDIAN)
		{		
			return 
				( _get() << 24) |
				( _get() << 16) | 
				( _get() << 8) |
				( _get() );
		} else {
			return 
				( _get() ) |
				( _get() << 8) |
				( _get() << 16) | 
				( _get() << 24);
		}
	}

	/** {@inheritDoc} */
	@Override
	public long getLong() 
    throws IOException	
	{
		if (order == ByteOrder.BIG_ENDIAN)
		{		
			return
			( ((long)_get()) << 56) |
			( ((long)_get()) << 48) | 
			( ((long)_get()) << 40) |
			( ((long)_get()) << 32) |		
			( ((long)_get()) << 24) |
			( ((long)_get()) << 16) | 
			( ((long)_get()) << 8) |
			( ((long)_get()) );		
		} else {		
			return 
			( ((long)_get())) |
			( ((long)_get()) << 8) |
			( ((long)_get()) << 16) | 
			( ((long)_get()) << 24) |
			( ((long)_get()) << 32) |		
			( ((long)_get()) << 40) |
			( ((long)_get()) << 48) | 
			( ((long)_get()) << 56);
		}
	}

	/** {@inheritDoc} */
	@Override
	public short getShort() 
    throws IOException	
	{
		if (order == ByteOrder.BIG_ENDIAN)
		{
			return (short) ( (_get() << 8) |  _get() ) ;
		} else {
			return (short) ( _get() | (_get() << 8) ) ;
		}
	}

	/** {@inheritDoc} */
	@Override
	public long limit() 
	{
		return limit;
	}
	
	/** {@inheritDoc} */
	@Override
	public long position() {
		return position;
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

	/**
	 * <p>skip.</p>
	 *
	 * @param bytes a long.
	 * @throws java.io.IOException if any.
	 */
	public void skip(long bytes) throws IOException {
		is.skip(bytes);	
	}
	
}
