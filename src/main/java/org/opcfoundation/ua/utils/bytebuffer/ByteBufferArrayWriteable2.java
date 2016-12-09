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
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * Sends events when chunks are complete
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ByteBufferArrayWriteable2 implements IBinaryWriteable {

	ByteBuffer[] bufs;
	int i = 0;
	ByteBuffer cur;
	ByteOrder order;
	ChunkListener listener;

	public interface ChunkListener {
		void onChunkComplete(ByteBuffer[] chunks, int index);
	}	
	
	/**
	 * <p>Constructor for ByteBufferArrayWriteable2.</p>
	 *
	 * @param bufs an array of {@link java.nio.ByteBuffer} objects.
	 * @param listener a {@link org.opcfoundation.ua.utils.bytebuffer.ByteBufferArrayWriteable2.ChunkListener} object.
	 */
	public ByteBufferArrayWriteable2(ByteBuffer[] bufs, ChunkListener listener) {
		if (bufs==null || listener==null)
			throw new IllegalArgumentException("null arg");
		this.bufs = bufs;
		this.listener = listener;
		cur = bufs[0];		
	}
	
	/**
	 * <p>fireChunkComplete.</p>
	 *
	 * @param index a int.
	 */
	protected void fireChunkComplete(int index)
	{
		if (listener!=null)
			listener.onChunkComplete(bufs, index);
	}

	/** {@inheritDoc} */
	@Override
	public ByteOrder order() {
		return order;
	}

	/** {@inheritDoc} */
	@Override
	public void order(ByteOrder order) {
		this.order = order;
		cur.order(order);
	}

	private void checkChunk()
	{
		if (!cur.hasRemaining())
			fireChunkComplete(i);
	}

	private void prepareNextChunk()
	{
		while (!cur.hasRemaining()) {
			i++;
			if (i>=bufs.length)
				throw new BufferOverflowException();
			cur = bufs[i];
			cur.order(order); 
		}
	}
	
	void _put(int value)
	throws IOException
	{
		prepareNextChunk();
		cur.put((byte)value);
		checkChunk();
	}
	
	/** {@inheritDoc} */
	@Override
	public void put(byte b) throws IOException {
		prepareNextChunk();
		cur.put(b);
		checkChunk();		
	}

	/** {@inheritDoc} */
	@Override
	public void put(ByteBuffer src) throws IOException {
		while (src.hasRemaining()) {
			prepareNextChunk();
			ByteBufferUtils.copyRemaining(src, cur);
			checkChunk();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void put(ByteBuffer src, int length) throws IOException {
		while (length>0) {
			prepareNextChunk();
			int n = Math.min(length, Math.min(src.remaining(), cur.remaining()));
			ByteBufferUtils.copy(src, cur, n);
			length -= n;
			checkChunk();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void put(byte[] src, int offset, int length) throws IOException {		
		while (length>0) {
			prepareNextChunk();
			int n = Math.min(length, cur.remaining());
			cur.put(src, offset, n);
			offset += n;
			length -= n;
			checkChunk();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void put(byte[] src) throws IOException {
		put(src, 0, src.length);
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

	/** {@inheritDoc} */
	@Override
	public void flush() {
	}
	
}
