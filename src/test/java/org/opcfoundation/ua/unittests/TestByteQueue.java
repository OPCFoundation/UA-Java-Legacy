 /* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.unittests;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static org.junit.Assert.* ;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opcfoundation.ua.utils.bytebuffer.ByteBufferFactory;
import org.opcfoundation.ua.utils.bytebuffer.ByteQueue;


/**
 *
 * 
 */
public class TestByteQueue {

	byte[] testData = new byte[] {0,1,2,3,4,5,6,7,8,9};
	int len = testData.length;
	ByteQueue q;
	
	@Before
	public void setUp() throws Exception {
		q = new ByteQueue();
		q.setChunkSize(3);
	}
	 
	@After
	public void tearDown() throws Exception {
		q = null;
	}
	
	@Test
	public void testPut1() {
		q.put(testData);
		byte[] dada = new byte[len];
		q.get(dada);
		assertTrue(Arrays.equals(dada, testData));
	}		
	
	@Test
	public void testPut2() {
		q.put(testData, 0, len);
		byte[] dada = new byte[len];
		q.get(dada);
		assertTrue(Arrays.equals(dada, testData));
	}	

	@Test
	public void testPut3() {
		ByteBuffer data = ByteBuffer.allocate(testData.length);
		data.put(testData);
		data.rewind();
		q.put(data, 5);
		q.put(data, len-5);
		byte[] dada = new byte[len];
		q.get(dada);
		assertTrue(Arrays.equals(dada, testData));
	}	

	@Test
	public void testPut4() {
		ByteBuffer data = ByteBuffer.allocate(testData.length);
		data.put(testData);
		data.rewind();
		q.put(data);
		byte[] dada = new byte[len];
		q.get(dada);
		assertTrue(Arrays.equals(dada, testData));		
	}	

	@Test
	public void testOffer() {
		ByteBuffer data = ByteBuffer.allocate(len);
		data.put(testData);
		data.rewind();
		q.offer(data);
		byte[] dada = new byte[len];
		q.get(dada);
		assertTrue(Arrays.equals(dada, testData));
	}	

	@Test
	public void testGet0() {
		byte[] data = new byte[len]; 
		boolean underflowed;
		try {
			q.get(data);
			underflowed = false;			
		} catch (BufferUnderflowException e) {
			underflowed = true;
		}
		assertTrue(underflowed);
		
		q.put(testData);
		q.get(data);
		assertEquals(0, q.remaining());
		for (int i=0; i<len; i++) 
			assertEquals(testData[i], data[i]);		
	}

	@Test
	public void testGet1() {
		byte[] data = new byte[len]; 
		boolean underflowed;
		try {
			q.get(data, 0, len);
			underflowed = false;			
		} catch (BufferUnderflowException e) {
			underflowed = true;
		}
		assertTrue(underflowed);
		
		q.put(testData);
		q.get(data, 0, len);
		assertEquals(0, q.remaining());
		for (int i=0; i<len; i++) 
			assertEquals(testData[i], data[i]);		
	}

	@Test
	public void testGet2() {
		ByteBuffer data = ByteBuffer.allocate(len);
		boolean underflowed;
		try {
			q.get(data);
			underflowed = false;			
		} catch (BufferUnderflowException e) {
			underflowed = true;
		}
		assertTrue(underflowed);
		
		q.put(testData);
		q.get(data);
		assertEquals(0, q.remaining());
		data.rewind();
		for (int i=0; i<len; i++) 
			assertEquals(testData[i], data.get());		
	}

	@Test
	public void testGet3() {
		ByteBuffer data = ByteBuffer.allocate(len);
		boolean underflowed;
		try {
			q.get(data, 1);
			underflowed = false;			
		} catch (BufferUnderflowException e) {
			underflowed = true;
		}
		assertTrue(underflowed);
		
		q.put(testData);
		q.get(data, 5);
		q.get(data, len-5);
		assertEquals(0, q.remaining());
		data.rewind();
		for (int i=0; i<len; i++) 
			assertEquals(testData[i], data.get());		
	}

	@Test
	public void testGet4() {
		boolean underflowed;
		try {
			q.get(1);
			underflowed = false;			
		} catch (BufferUnderflowException e) {
			underflowed = true;
		}
		assertTrue(underflowed);
		
		q.put(testData);
		ByteBuffer data = q.get(len);		
		data.rewind();
		assertEquals(0, q.remaining());
		for (int i=0; i<len; i++) 
			assertEquals(testData[i], data.get());		
	}
	
	@Test
	public void testGetAvail() {
		ByteBuffer data2 = ByteBuffer.allocate(len);
		q.getAvailable(data2); 
		assertEquals(len, data2.remaining());
		q.put(testData);		
		q.getAvailable(data2); 
		assertEquals(0, data2.remaining());
		data2.rewind(); 
		assertEquals(len, data2.remaining());
		assertEquals(0, q.remaining());
		for (int i=0; i<len; i++) 
			assertEquals(testData[i], data2.get());		
	}

	@Test
	public void testGetChunks() {
		q.setChunkSize(3);
		q.put(testData);
		ByteBuffer[] chunks = q.getChunks(len);
		int chunkPointer = 0;
		for (int j=0; j<len; j++)
		{
			if (!chunks[chunkPointer].hasRemaining())
				chunkPointer++;				
			assertEquals(testData[j], chunks[chunkPointer].get());
		}	
		assertEquals(0, q.remaining());
	}

	@Test
	public void testPeek1() {
		byte data[] = new byte[len];
		boolean underflowed;
		try {
			q.peek(data);
			underflowed = false;			
		} catch (BufferUnderflowException e) {
			underflowed = true;
		}
		assertTrue(underflowed);
		
		q.put(testData);
		q.peek(data);
		assertTrue(Arrays.equals(data, testData));
		assertEquals(len, q.remaining());		
	}

	@Test
	public void testPeek2() {	
		byte data[] = new byte[len];
		byte data2[] = new byte[len*2];
		boolean underflowed;
		try {
			q.peek(data);
			underflowed = false;			
		} catch (BufferUnderflowException e) {
			underflowed = true;
		}
		assertTrue(underflowed);
		
		q.put(testData);
		q.peek(data, 0, 5);
		q.peek(data2, 0, len);
		assertEquals(len, q.remaining());
		for (int i=0; i<5; i++) 
			assertEquals(testData[i], data[i]);
		for (int i=0; i<len; i++) 
			assertEquals(testData[i], data2[i]);
	}

	@Test
	public void testPeek3() {
		ByteBuffer data = ByteBuffer.allocate(5);
		ByteBuffer data2 = ByteBuffer.allocate(len);
		boolean underflowed;
		try {
			q.peek(data);
			underflowed = false;			
		} catch (BufferUnderflowException e) {
			underflowed = true;
		}
		assertTrue(underflowed);
		
		q.put(testData);
		q.peek(data); data.rewind();
		q.peek(data2);data2.rewind();
		
		assertEquals(len, q.remaining());
		for (int i=0; i<5; i++) 
			assertEquals(testData[i], data.get());
		for (int i=0; i<len; i++) 
			assertEquals(testData[i], data2.get());
	}

	@Test
	public void testPeek4() {
		boolean underflowed;
		try {
			q.peek(1);
			underflowed = false;			
		} catch (BufferUnderflowException e) {
			underflowed = true;
		}
		assertTrue(underflowed);		
		q.put(testData);
		ByteBuffer data = q.peek(5);
		ByteBuffer data2 = q.peek(len);		
		q.peek(data);
		q.peek(data2);
		data.rewind();
		data2.rewind();
		assertEquals(len, q.remaining());
		for (int i=0; i<5; i++) 
			assertEquals(testData[i], data.get());
		for (int i=0; i<len; i++) 
			assertEquals(testData[i], data2.get());		
	}
	
	@Test
	public void testPeekAvail() {	
		ByteBuffer data = ByteBuffer.allocate(5);
		ByteBuffer data1 = ByteBuffer.allocate(len*2);
		ByteBuffer data2 = ByteBuffer.allocate(len);
		q.peekAvailable(data); 
		assertEquals(5, data.remaining());
		q.put(testData);		
		q.peekAvailable(data); 
		q.peekAvailable(data1);
		q.peekAvailable(data2);
		assertEquals(0, data.remaining());
		data.rewind(); data2.rewind();
		assertEquals(5, data.remaining());
		assertEquals(len, q.remaining());
		assertEquals(testData[0], data1.get());		
		for (int i=0; i<5; i++) 
			assertEquals(testData[i], data.get());
		for (int i=0; i<len; i++) 
			assertEquals(testData[i], data2.get());		
	}

	@Test
	public void testPeekChunks() {
		q.setChunkSize(3);
		q.put(testData);
		
		ByteBuffer chunks[] = q.peekChunks(len);
		int chunkPointer = 0;
		for (int j=0; j<len; j++)
		{
			if (!chunks[chunkPointer].hasRemaining())
				chunkPointer++;				
			assertEquals(testData[j], chunks[chunkPointer].get());
		}
		
		chunks = q.getChunks(len);
		chunkPointer = 0;
		for (int j=0; j<len; j++)
		{
			if (!chunks[chunkPointer].hasRemaining())
				chunkPointer++;				
			assertEquals(testData[j], chunks[chunkPointer].get());
		}		
		
	}

	@Test
	public void testRemaining() {
		assertEquals(0, q.remaining());
		q.put(testData);
		assertEquals(len, q.remaining());
		q.get(len);
		assertEquals(0, q.remaining());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(q.isEmpty());
		q.put(testData);
		assertFalse(q.isEmpty());
		q.get(len);
		assertTrue(q.isEmpty());
		q.put(testData);
		assertFalse(q.isEmpty());
		q.clear();
		assertTrue(q.isEmpty());
	}

	@Test
	public void testGetBytesRead() {
		q.put(testData);
		assertEquals(q.getBytesRead(), 0);
		q.get(5);
		assertEquals(q.getBytesRead(), 5L);
	}

	@Test
	public void testGetBytesWritten() {
		assertEquals(0, q.getBytesWritten());
		q.put(testData);
		assertEquals(len, q.getBytesWritten());
		q.put(testData);
		assertEquals(len*2, q.getBytesWritten());
		q.offer( ByteBuffer.allocate(5) );
		assertEquals(len*2+5, q.getBytesWritten());
		
	}

	@Test
	public void testOmitAll() {		
		q.put(testData);
		assertEquals(0, q.getBytesRead());
		assertEquals(len, q.getBytesWritten());
		q.omitAll();
		assertEquals(0, q.remaining());
		assertEquals(len, q.getBytesRead());
		assertEquals(len, q.getBytesWritten());
	}

	@Test
	public void testClear() {
		q.put(testData);
		q.clear();
		assertEquals(0, q.remaining());
		assertEquals(0, q.getBytesRead());
		assertEquals(0, q.getBytesWritten());
	}

	@Test
	public void testSkip() {
		q.put(testData);
		q.skip(5);
		for (int i=5; i<len; i++)
			assertEquals(q.getReadChunk().get(), testData[i]);
	}

	@Test
	public void testGetReadChunk() {
		q.setChunkSize(3);
		q.put(testData);
		ByteBuffer readChunk = q.getReadChunk();
		for (int i=0; i<len; i++)
		{
			if (!readChunk.hasRemaining())
				readChunk = q.getReadChunk();
			assertEquals(testData[i], readChunk.get());
		}
	}

	@Test
	public void testGetWriteChunk() {
		q.setChunkSize(3);
		ByteBuffer writeChunk = q.getWriteChunk();
		for (int i=0; i<len; i++)
		{
			if (!writeChunk.hasRemaining())
				writeChunk = q.getWriteChunk();
			writeChunk.put(testData[i]);
		}		
		byte dada[] = new byte[len];
		q.get(dada);		
		assertTrue(Arrays.equals(testData, dada));
	}

	@Test
	public void testGetWriteableBytesRemaining() {
		q.setWriteLimit(10);
		assertEquals(10, q.getWriteableBytesRemaining());
		q.put(testData);
		assertEquals(0, q.getWriteableBytesRemaining());
		q.setWriteLimit(20);
		assertEquals(10, q.getWriteableBytesRemaining());
	}

	@Test
	public void testOrder() {		
		q.order(ByteOrder.BIG_ENDIAN);
		assertEquals(ByteOrder.BIG_ENDIAN, q.order());
		q.put(testData);
		for (ByteBuffer buf : q.getChunks(len))
			assertEquals(ByteOrder.BIG_ENDIAN, buf.order());
		
		q.order(ByteOrder.LITTLE_ENDIAN);
		assertEquals(ByteOrder.LITTLE_ENDIAN, q.order());
		q.put(testData);
		for (ByteBuffer buf : q.getChunks(len))
			assertEquals(ByteOrder.LITTLE_ENDIAN, buf.order());
	}

	@Test
	public void testByteBufferFactory() {
		ByteBufferFactory bb = new ByteBufferFactory() {
			@Override
			public ByteBuffer allocate(int capacity) {
				ByteBuffer result = ByteBuffer.allocate(capacity+1);
				result.limit(1);
				return result;
			}
		};
		q.setByteBufferFactory(bb);
		q.setChunkSize(1);
		q.put(testData);
		for (ByteBuffer buf : q.getChunks(len))
			assertEquals(2, buf.array().length);
	}

	@Test
	public void testChunkSize() {
		q.setChunkSize(1);
		assertEquals(q.getChunkSize(), 1);
		q.put(testData);
		int chunkCount = q.getChunks(len).length;
		assertEquals(chunkCount, len);
		
	}

	@Test
	public void testWriteLimit() {		
		q.setWriteLimit(10);
		boolean limitHit = false;
		try {
			q.put(testData);
			limitHit = false;
		} catch (BufferOverflowException e) {
			limitHit = true;
		}		
		assertFalse(limitHit);
		
		q.clear();
		
		q.setWriteLimit(9);
		limitHit = false;
		try {
			q.put(testData);
			limitHit = false;
		} catch (BufferOverflowException e) {
			limitHit = true;
		}		
		assertTrue(limitHit);			
	}
	
}
