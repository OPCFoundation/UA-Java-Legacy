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

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Byte Queue is a LIFO queue of bytes. It uses {@link ByteBuffer}s
 * as back-end for data. There are two incremental memory pointers, read
 * and write position. Write position returns the number of bytes written
 * to the queue, and read bytes read.
 *
 * ByteQueue allocates new memory as required in chunks.
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ByteQueue {
	
	// Memory chunk size
	private final static ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);
	private final static ByteBuffer[] EMPTY_BUFFERS = new ByteBuffer[0];
	
	ByteBufferFactory factory = ByteBufferFactory.LITTLE_ENDIAN_HEAP_BYTEBUFFER_FACTORY;
	int chunkSize = 4096;	
	long bytesRead;
	long bytesWritten;
	long writeLimit = Long.MAX_VALUE;
	ByteOrder order = ByteOrder.nativeOrder();
	
	// List of readable buffers between readChunk and writeChunk 
	LinkedList<ByteBuffer> list = new LinkedList<ByteBuffer>();
	// Active write chunk
	ByteBuffer writeChunk;
	// Active read chunk
	ByteBuffer readChunk;
	
	// The structure of the buffer is the following:
	// [readChunk or null] [list = ByteBuffer, ByteBuffer, ByteBuffer, ...] [writeChunk or null]
	// Each buffer in list is rewound to its readable position.
	
	/**
	 * Construct new byte queue usign default allocation size.
	 */
	public ByteQueue() {		
	}
	
	/**
	 * Construct new byte queue.
	 *
	 * @param chunkSize allocation size for new memory chunks
	 */
	public ByteQueue(int chunkSize) {
		this.chunkSize = chunkSize;
	}
	
	/**
	 * Write bytes
	 *
	 * @param buf bytes to write
	 * @throws java.nio.BufferOverflowException write limit exeeded
	 */
	public void put(byte[] buf)
	throws BufferOverflowException 
	{
		put(buf, 0, buf.length);
	}
	
	/**
	 * Write bytes
	 *
	 * @param buf bytes
	 * @param off offset
	 * @param len length
	 * @throws java.nio.BufferOverflowException if any.
	 */
	public void put(byte[] buf, int off, int len)
	throws BufferOverflowException
	{
		if (buf == null) {
		    throw new NullPointerException();
		} else if ((off < 0) || (off > buf.length) || (len < 0) ||
			   ((off + len) > buf.length) || ((off + len) < 0)) {
		    throw new IndexOutOfBoundsException();
		} else if (len == 0) {
		    return;
		}
		
		while (len>0)
		{
			ByteBuffer dst = getWriteChunk();
			int n = Math.min(dst.remaining(), len);
			dst.put(buf, off, n);
			off += n;			
			len -= n;
		}
	}
	
	/**
	 * <p>put.</p>
	 *
	 * @param value a byte.
	 */
	public void put(byte value)
	{
		getWriteChunk().put(value);
	}
	
	/**
	 * Write remaining bytes of src
	 *
	 * @param src a {@link java.nio.ByteBuffer} object.
	 * @throws BufferOverflowException write limit exeeded
	 */
	public void put(ByteBuffer src)
	{
		put(src, src.remaining());
	}
	
	/**
	 * Write remaining bytes of src
	 *
	 * @param src a {@link java.nio.ByteBuffer} object.
	 * @param length a int.
	 * @throws BufferOverflowException write limit exeeded
	 */
	public void put(ByteBuffer src, int length)
	{
		while (length>0) {
			ByteBuffer dst = getWriteChunk();
			int n = Math.min(dst.remaining(), length);
			copy(src, dst, n);
			length -= n;
		}
	}
	
	/**
	 * Offers a byte buffer object for the queue.
	 * The remaining bytes of buf are added to
	 * the queue. ByteQueue takes the ownership of buf.
	 *
	 * @param buf buffer to write
	 * @throws java.nio.BufferOverflowException write limit exeeded
	 */
	public void offer(ByteBuffer buf)
			throws BufferOverflowException
	{
		if (getWriteableBytesRemaining()<buf.remaining())
			throw new BufferOverflowException();
		if (!buf.hasRemaining()) return;
		
		// Flush written part of active writeChunk 
		flushWriteChunk();
		bytesWritten += buf.remaining();
		list.addLast(buf);
	}
	
	/**
	 * Read from buf
	 *
	 * @param buf buf to be filled
	 * @throws java.nio.BufferUnderflowException if any.
	 */
	public void get(byte[] buf)
			throws BufferUnderflowException
	{
		get(buf, 0, buf.length);
	}
	
	/**
	 * Read from buf
	 *
	 * @param buf an array of byte.
	 * @param off a int.
	 * @param len a int.
	 * @throws BufferUnderflowException if any.
	 */
	public void get(byte[] buf, int off, int len)
	{		
		if (buf == null) {
		    throw new NullPointerException();
		} else if (off < 0 || len < 0 || len > buf.length - off) {
		    throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return;
		} else if (len > remaining())
		    throw new BufferUnderflowException();
		
		while (len>0) 
		{
			ByteBuffer src = getReadChunk();
			if (src==null) throw new BufferUnderflowException();
			int n = Math.min(src.remaining(), len);
			src.get(buf, off, n);
			off += n;
			len -= n;
		}
	}
	
	/**
	 * Reads to dst remaining bytes
	 *
	 * @param dst a {@link java.nio.ByteBuffer} object.
	 * @throws java.nio.BufferUnderflowException if any.
	 */
	public void get(ByteBuffer dst)
	throws BufferUnderflowException
	{
		if (dst.remaining()>remaining())
			throw new BufferUnderflowException();
		get(dst, dst.remaining());
	}
	
	/**
	 * Reads to dst
	 *
	 * @param dst a {@link java.nio.ByteBuffer} object.
	 * @throws java.nio.BufferUnderflowException if any.
	 * @param length a int.
	 */
	public void get(ByteBuffer dst, int length)
	throws BufferUnderflowException
	{
		if (length>dst.remaining())
			throw new BufferUnderflowException();
		if (length>remaining())
			throw new BufferUnderflowException();
		while (length>0) {
			ByteBuffer src = getReadChunk();
			int n = Math.min(src.remaining(), length);			
			copy(src, dst, n);
			length -= n;
		}
	}
	
	/**
	 * Read bytes. (Efficient)
	 *
	 * This implementation copies memory only if the
	 * requested data is span over several ByteBuffers in the
	 * back-end.
	 *
	 * @param len a int.
	 * @return ByteBuffer containing read data
	 * @throws java.nio.BufferUnderflowException if any.
	 */
	public ByteBuffer get(int len)
	throws BufferUnderflowException
	{
		if (len<0)
			throw new IllegalArgumentException();
		if (len > remaining())
	    throw new BufferUnderflowException();
		if (len==0) return EMPTY_BUFFER;
		
		int chunks = countChunks(len);
		if (chunks == 1) {
			ByteBuffer result = getChunks(len)[0];
			return result;
		}
		
		ByteBuffer result = ByteBuffer.allocate(len); 
		result.order(order);
		get(result);
		result.rewind();
		return result;
	}

	/**
	 * Reads as much as there is available and dst has
	 * remaining allocation
	 *
	 * @param dst a {@link java.nio.ByteBuffer} object.
	 */
	public void getAvailable(ByteBuffer dst)
	{
		get(dst, Math.min(dst.remaining(), (int) remaining()));		
	}
	
	/**
	 * Reads bytes.
	 *
	 * This implementation does not copy memory.
	 *
	 * @param len number of bytes to read
	 * @return byte buffer array
	 * @throws java.nio.BufferUnderflowException if any.
	 */
	public ByteBuffer[] getChunks(int len)
	throws BufferUnderflowException
	{
		if (len<0) throw new IllegalArgumentException();
		if (len > remaining()) throw new BufferUnderflowException();
		if (len==0) return EMPTY_BUFFERS;
		
		ByteBuffer[] result = new ByteBuffer[countChunks(len)];
		int index = 0;
		ByteBuffer readChunk_ = getReadChunk();
		
		while (readChunk_!=null && len>0) {
			if (len<readChunk_.remaining()) {
				ByteBuffer buf = readChunk.slice();
				buf.order(order);
				readChunk.position(readChunk.position() + len);				
				buf.limit(len);				
				result[index++] = buf;
				len = 0;
			} else {
				// Extract the whole readChunk
				bytesRead += readChunk_.remaining() + readChunk_.position();
				len -= readChunk_.remaining();
				readChunk = null;
				result[index++] = readChunk_.slice().order(order);
			}
			readChunk_ = getReadChunk();
		}
		
		return result;
	}
	
	/**
	 * Peek data, read without moving read pointer.
	 *
	 * @param buf an array of byte.
	 * @throws java.nio.BufferUnderflowException if any.
	 */
	public void peek(byte[] buf)
	throws BufferUnderflowException
	{
		peek(buf, 0, buf.length);		
	}	

	/**
	 * Peek data, read without moving read pointer
	 *
	 * @param buf an array of byte.
	 * @param off a int.
	 * @param len a int.
	 * @throws java.nio.BufferUnderflowException if any.
	 */
	public void peek(byte[] buf, int off, int len)
	throws BufferUnderflowException
	{
		if (buf == null) {
		    throw new NullPointerException();
		} else if (off < 0 || len < 0 || len > buf.length - off) {
		    throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return;
		} else if (len > remaining())
		    throw new BufferUnderflowException();

		
		// Peek from head (active read buffer)
		if (readChunk!=null) {
			int n = Math.min(readChunk.remaining(), len);
			readChunk.mark();
			readChunk.get(buf, off, n);
			readChunk.reset();
			off += n;
			len -= n;		
			if (len==0) return;
		}
		
		// Peek from queued buffers (sleeping buffers)
		if (!list.isEmpty()) {
			Iterator<ByteBuffer> i = list.iterator();
			while (len>0 && i.hasNext()) 
			{
				ByteBuffer src = i.next();
				int n = Math.min(src.remaining(), len);
				src.mark();
				src.get(buf, off, n);
				src.reset();
				off += n;
				len -= n;
			}
		}
		
		// Peek from tail (active write buffer)
		if (writeChunk!=null) {
			ByteBuffer src = (ByteBuffer) writeChunk.duplicate().flip();			
			src.order(order);
			int n = Math.min(src.remaining(), len);			
			src.get(buf, off, n);
			off += n;
			len -= n;		
		}		
	}	

	/**
	 * Peeks to dst.
	 *
	 * @param dst a {@link java.nio.ByteBuffer} object.
	 * @throws java.nio.BufferUnderflowException if any.
	 */
	public void peek(ByteBuffer dst)
	throws BufferUnderflowException
	{
		if (dst.remaining()>remaining())
			throw new BufferUnderflowException();
		peekAvailable(dst);
	}
	
	/**
	 * Peek, read without moving read pointer.
	 *
	 * @param len a int.
	 * @return a byte buffer containing the peeked bytes
	 * @throws java.nio.BufferUnderflowException if any.
	 */
	public ByteBuffer peek(int len)
	throws BufferUnderflowException
	{
		if (len<0)
			throw new IllegalArgumentException();
		if (len > remaining())
	    throw new BufferUnderflowException();
		if (len==0) return EMPTY_BUFFER;
		
		ByteBuffer chunks[] = peekChunks(len);
		if (chunks.length==1) return chunks[0];				
		ByteBuffer result = ByteBuffer.allocate(len); 
		result.order(order);
		peek(result);
		result.rewind();
		return result;		
	}
	
	/**
	 * Peek available data.
	 *
	 * @param dst a {@link java.nio.ByteBuffer} object.
	 */
	public void peekAvailable(ByteBuffer dst)
	{
		if (!dst.hasRemaining()) return;		
		// Read from header
		if (readChunk!=null) {
			readChunk.mark();
			copyRemaining(readChunk, dst);
			readChunk.reset();
		}
		
		if (!dst.hasRemaining()) return;
		if (!list.isEmpty()) {
			Iterator<ByteBuffer> i = list.iterator();
			while (dst.hasRemaining() && i.hasNext()) 
			{
				ByteBuffer src = i.next();
				src.mark();
				copyRemaining(src, dst);
				src.reset();
			}
		}
		
		if (!dst.hasRemaining()) return;		
		if (writeChunk!=null) {
			ByteBuffer src = (ByteBuffer) writeChunk.duplicate().flip();
			src.order(order);			
			copyRemaining(src, dst);
		}
	}	
	
	/**
	 * Peeks given number of bytes.
	 *
	 * This method does not copy memory.
	 *
	 * @param len a int.
	 * @return array of buffers.
	 * @throws java.nio.BufferUnderflowException if any.
	 */
	public ByteBuffer[] peekChunks(int len)
	throws BufferUnderflowException
	{
		if (len < 0)
			throw new IllegalArgumentException();
		if (len > remaining())
	    throw new BufferUnderflowException();
		if (len == 0) return EMPTY_BUFFERS;
		
		int remaining = len;
		
		ByteBuffer[] result = new ByteBuffer[countChunks(len)];
		int index = 0;
		
		if (readChunk!=null) {
			ByteBuffer buf = readChunk.slice();
			assert(buf.position()==0);
			buf.order(order);
			if (remaining<buf.remaining()) {
				buf.limit(remaining);
				remaining = 0;
			} else {
				remaining -= buf.remaining();
			}
			result[index++] = buf;			
		}
		
		if (!list.isEmpty() && remaining>0) {
			Iterator<ByteBuffer> i = list.iterator();
			while(remaining>0 && i.hasNext()) {
				ByteBuffer buf = i.next().slice();
				assert(buf.position()==0);
				buf.order(order);
				if (remaining<buf.remaining()) {
					buf.limit(remaining);
					remaining = 0;
				} else {
					remaining -= buf.remaining();
				}
				result[index++] = buf;				
			}
		}
		
		if (remaining>0 && writeChunk!=null) {
			ByteBuffer buf = (ByteBuffer) writeChunk.duplicate().flip();
			assert(buf.position()==0);
			buf.order(order);
			if (remaining<buf.remaining()) {
				buf.limit(remaining);
				remaining = 0;
			} else {
				remaining -= buf.remaining();
			}
			result[index++] = buf;			
		}
		assert(remaining==0);
		return result;
	}
	
	/**
	 * Count the number of chunks the given amount of bytes is spawn starting
	 * from the read pointer.   
	 * @param len number of bytes
	 * @return number of chunks
	 */
	private int countChunks(int len)
	{
		int result = 0;
		if (len==0) return result;
		if (readChunk!=null) {
			len -= Math.min(readChunk.remaining(), len);
			result++;
		}
				
		if (len==0) return result;
		if (!list.isEmpty()) {
			Iterator<ByteBuffer> i = list.iterator();
			while (len>0 && i.hasNext()) 
			{
				ByteBuffer src = i.next();
				len -= Math.min(src.remaining(), len);
				result ++;
			}
		}
		
		if (len==0) return result;
		if (writeChunk!=null) {
			result++;
		}		
		return result;
	}
	
	/**
	 * Get the number of readable bytes.
	 *
	 * @return number of readable bytes.
	 */
	public long remaining()
	{
		return getBytesWritten() - getBytesRead();
	}
	
	/**
	 * Is buffer empty
	 *
	 * @return true if buffer is empty
	 */
	public boolean isEmpty()
	{
		return remaining() == 0L;
	}
	
	/**
	 * <p>hasRemaining.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasRemaining()
	{
		return remaining() > 0L;
	}
	
	/**
	 * Get the total number of bytes read from the queue.
	 *
	 * @return the total number of bytes read from the queue
	 */
	public long getBytesRead()
	{
		return bytesRead + (readChunk==null ? 0 : readChunk.position());
	}
	
	/**
	 * Get the total number of bytes written to the queue.
	 *
	 * @return the total number of bytes written to the queue
	 */
	public long getBytesWritten()
	{
		return bytesWritten + (writeChunk==null ? 0 : writeChunk.position());
	}
		
	/**
	 * Clears queue and moves read pointer to written pointer.
	 */
	public void omitAll()
	{
//		skip((int) remaining());
		if (writeChunk!=null)
			bytesWritten += writeChunk.position();
		bytesRead = bytesWritten;
		list.clear();
		writeChunk = null;
		readChunk = null;
	}
	
	/**
	 * Clears bytes and resets all pointers.
	 */
	public void clear()
	{
		bytesRead = bytesWritten = 0;
		list.clear();
		writeChunk = null;
		readChunk = null;		
	}
	
	/**
	 * Omit data by moving read pointer forward.
	 *
	 * @param bytes to omit
	 * @throws java.nio.BufferOverflowException if any.
	 */
	public void skip(int bytes)
	throws BufferOverflowException
	{
		if (bytes>remaining()) 
			throw new BufferOverflowException();
		while (bytes>0) {
			ByteBuffer bb = getReadChunk();
			int n = Math.min(bb.remaining(), bytes);
			bb.position(bb.position()+n);
			bytes -= n;
		}
	}	
	
	/**
	 * Expose the a byte buffer of backend.
	 * Moving the position of this byte buffer, moves the byte queue aswell.
	 *
	 * @return active read byte buffer or null
	 */
	public ByteBuffer getReadChunk()
	{
		// Dump exhausted read chunk
		if (readChunk!=null && !readChunk.hasRemaining()) {
			bytesRead += readChunk.position();
			readChunk = null;
		}
		
		// Return active read chunk
		if (readChunk!=null) return readChunk;
		
		// Slice active write chunk by flushing the already written part 
		if (list.isEmpty()) flushWriteChunk();
		
		// Pull new read chunk from chunk queue		
		if (!list.isEmpty()) {
			readChunk = list.removeFirst();
			bytesRead -= readChunk.position();
			return readChunk;
		}
		
		return null;
	}
	
	/**
	 * Exposes internal bytebuffer of this object. Writing to this bytebuffer
	 * moves the pointer in this bytequeue aswell.
	 *
	 * @return returns a chunk with atleast 1 writeable byte
	 */
	public ByteBuffer getWriteChunk()
	{
		// Flush write chunk 
		if (writeChunk!=null && !writeChunk.hasRemaining()) 
			flushWriteChunk();				
		// Return active write chunk
		if (writeChunk!=null) return writeChunk;
		
		// Create new write chunk
		int n = Math.min(getWriteableBytesRemaining(), chunkSize);
		if (n==0) throw new BufferOverflowException();
		writeChunk = factory.allocate(n);
		writeChunk.order(order);
		return writeChunk;
	}
	
	/**
	 * Flushes write chunk to internal buffer.
	 * Empties writeChunk (of bytes to write).
	 */
	private void flushWriteChunk()	
	{
		// There is no write chunk
		if (writeChunk == null) return;
		// Nothing is written to write chunk
		if (writeChunk.position()==0) return;
		// Flush full writeChunk
		if (!writeChunk.hasRemaining()) {
			bytesWritten += writeChunk.position();
			writeChunk.flip();
			list.addLast(writeChunk);
			writeChunk = null;
			return;
		}
		// Slice write chunk
		bytesWritten += writeChunk.position();		
		ByteBuffer flushChunk = writeChunk;
		writeChunk = writeChunk.slice();
		writeChunk.order(order);
		flushChunk.flip();
		list.addLast(flushChunk);
		return;
		
	}
	
	/**
	 * Get the number of bytes remaining for write
	 *
	 * @return bytes remaining
	 */
	public int getWriteableBytesRemaining()
	{
		return (int) Math.min(Integer.MAX_VALUE, writeLimit - getBytesWritten());		
	}
	
	/**
	 * Get byte order
	 *
	 * @return the byte order
	 */
	public ByteOrder order() {
		return order;
	}

	/**
	 * Set byte order
	 *
	 * @param order a {@link java.nio.ByteOrder} object.
	 */
	public void order(ByteOrder order) {
		this.order = order;
	}	
			
	
	/**
	 * Copies as much as possible
	 * @param src
	 * @param dst
	 */
	private static void copyRemaining(ByteBuffer src, ByteBuffer dst)
	{
		int n = Math.min(src.remaining(), dst.remaining());
		copy(src, dst, n);
	}
	
	private static void copy(ByteBuffer src, ByteBuffer dst, int length)
	{
		int srcLimit = src.limit();
		int dstLimit = dst.limit();
		src.limit(src.position() + length);
		dst.limit(dst.position() + length);
		dst.put(src);
		src.limit(srcLimit);
		dst.limit(dstLimit);		
	}		
	
	
	
	/**
	 * Set byte buffer constructor. The constructed buffers
	 * may be larger than requested but remaining bytes must
	 * be the requested amount.
	 *
	 * @param factory a {@link org.opcfoundation.ua.utils.bytebuffer.ByteBufferFactory} object.
	 */
	public void setByteBufferFactory(ByteBufferFactory factory)
	{
		this.factory = factory;
	}
	
	/**
	 * Get byte buffer factory
	 *
	 * @return byte buffer factory.
	 */
	public ByteBufferFactory getByteBufferFactory() 
	{
		return factory;
	}
	
	/**
	 * Get the block size of back-end chunks
	 *
	 * @return chunk size
	 */
	public int getChunkSize() {
		return chunkSize;
	}

	/**
	 * Set the block size of back-end chunks
	 *
	 * @param chunkSize a int.
	 */
	public void setChunkSize(int chunkSize) {
		if (chunkSize<1)
			throw new IllegalArgumentException("chunk size < 1");
		this.chunkSize = chunkSize;
	}
	
	/**
	 * Get writable bytes limit
	 *
	 * @return writeable bytes constraint or Long.MAX_VALUE
	 */
	public long getWriteLimit() {
		return writeLimit;
	}

	/**
	 * Set writable bytes limit.
	 *
	 * @param writeLimit new writeable bytes constraint
	 */
	public void setWriteLimit(long writeLimit) {
		this.writeLimit = writeLimit;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "ByteQueue (read="+getBytesRead()+", written="+getBytesWritten()+")";
	}
	
}
