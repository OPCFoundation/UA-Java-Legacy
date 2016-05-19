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
import java.nio.ByteBuffer;

import org.opcfoundation.ua.utils.IncubationQueue;

import junit.framework.TestCase;



public class TestIncubationQueue extends TestCase {

	// Queue with identity compare
	IncubationQueue<Object> qi;
	
	// Queue with equals/hash compare
	IncubationQueue<Object> qe;
	
	Object a = "a";
	Object b = "b";
	Object c = "c";
	Object d = "d";	
	
	public void setUp() throws Exception {
		qi = new IncubationQueue<Object>(true);
		qe = new IncubationQueue<Object>(false);
	}
	
	public void tearDown() throws Exception {
		qi=null;
		qe=null;
	}
	
	public void testIncubate()
	{
		qi.incubate(a);
		try {
			qi.incubate(a);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
	
	public void testHatch()
	{
		qi.incubate(a);
		qi.incubate(b);
		qi.incubate(c);

		qi.hatch(c);
		qi.hatch(b);
		qi.hatch(a);
		
		try {
			qi.hatch(d);
			fail();
		} catch (IllegalArgumentException e) {
		}		
	}
	
	public void testIdentityQueue() 
	throws InterruptedException
	{
		ByteBuffer a = ByteBuffer.allocate(1);
		qi.incubate(a);
		a.put((byte)5);
		qi.hatch(a);
		ByteBuffer b = (ByteBuffer) qi.getNext();
		assertEquals(a, b);
		
		a = ByteBuffer.allocate(1);
		qe.incubate(a);
		a.put((byte)5);
		try {
			qe.hatch(a);
			throw new RuntimeException("Hatch fail expected");
		} catch (IllegalArgumentException e) {			
		}
	}
	
	public void testRemoveNextHatchedIfAvailable()
	{
		qi.incubate(a);
		qi.incubate(b);
		qi.incubate(c);
		assertNull(qi.removeNextHatchedIfAvailable());
		qi.hatch(c);
		assertNull(qi.removeNextHatchedIfAvailable());
		qi.hatch(b);
		assertNull(qi.removeNextHatchedIfAvailable());
		qi.hatch(a);
		assertEquals(a, qi.removeNextHatchedIfAvailable());		
		assertEquals(b, qi.removeNextHatchedIfAvailable());		
		assertEquals(c, qi.removeNextHatchedIfAvailable());		
	}
	
	public void testRemoveNextHatched() throws InterruptedException
	{
		qi.incubate(a);
		qi.incubate(b);
		qi.incubate(c);
		
		qi.hatch(c);
		qi.hatch(b);
		qi.hatch(a);
		
		assertEquals(a, qi.removeNextHatched());		
		assertEquals(b, qi.removeNextHatched());		
		assertEquals(c, qi.removeNextHatched());
		
		qi.incubate(d);
		new Thread() {
		public void run() {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			qi.hatch(d);			
		}}.run();
		assertEquals(d, qi.removeNextHatched());		
		
	}
	
	public void testRemoveNextHatchedUninterruptibly()
	{
		qi.incubate(a);
		qi.incubate(b);
		qi.incubate(c);
		
		qi.hatch(c);
		qi.hatch(b);
		qi.hatch(a);
		
		assertEquals(a, qi.removeNextHatchedUninterruptibly());		
		assertEquals(b, qi.removeNextHatchedUninterruptibly());		
		assertEquals(c, qi.removeNextHatchedUninterruptibly());
		
		qi.incubate(d);
		new Thread() {
		public void run() {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			qi.hatch(d);			
		}}.run();
		assertEquals(d, qi.removeNextHatchedUninterruptibly());
	}
	
	public void testNextIsHatched()
	{
		assertEquals(false, qi.nextIsHatched());
		qi.incubate(a);
		assertEquals(false, qi.nextIsHatched());
		qi.hatch(a);
		assertEquals(true, qi.nextIsHatched());
		qi.removeNextHatchedIfAvailable();
		assertEquals(false, qi.nextIsHatched());
	}
	
	public void testGetNextHatchedIfAvailable()
	{
		assertEquals(null, qi.getNextHatchedIfAvailable());
		qi.incubate(a);
		assertEquals(null, qi.getNextHatchedIfAvailable());		
		qi.hatch(a);
		assertEquals(a, qi.getNextHatchedIfAvailable());				
	}
	
	public void testGetNextHatched() throws InterruptedException
	{
		qi.incubate(a);
		new Thread() {
		public void run() {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			qi.hatch(a);
		}}.run();
		assertEquals(a, qi.getNextHatched());
	}
	
	public void testGetNext() throws InterruptedException
	{
		qi.incubate(a);
		assertEquals(a, qi.getNext());
	}
	
	public void testIsEmpty()
	{
		assertEquals(true, qi.isEmpty());
		qi.incubate(a);
		assertEquals(false, qi.isEmpty());
		qi.hatch(a);
		assertEquals(false, qi.isEmpty());
		qi.removeNextHatchedUninterruptibly();
		assertEquals(true, qi.isEmpty());
	}
	
	public void testClear()
	{
		assertEquals(0, qi.size());
		qi.incubate(a);
		qi.clear();
		assertEquals(0, qi.size());
	}
	
	public void testSize()
	{
		qi.incubate(a);
		assertEquals(1, qi.size());
		qi.incubate(b);
		assertEquals(2, qi.size());
		qi.hatch(b);
		assertEquals(2, qi.size());
	}
	
	public void testIterator()
	{
		
	}
	
	public void testContains()
	{
		
	}
	
	public void testIsHatched()
	{
		
	}
	
	public void testIsIncubating()
	{
		
	}
	
	public void testWaitUntilIncubated()
	{
		
	}
	
}
