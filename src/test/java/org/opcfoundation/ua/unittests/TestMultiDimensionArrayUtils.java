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
import java.util.Arrays;

import junit.framework.TestCase;

import org.opcfoundation.ua.utils.MultiDimensionArrayUtils;
import org.opcfoundation.ua.utils.MultiDimensionArrayUtils.ArrayIterator;


public class TestMultiDimensionArrayUtils extends TestCase {

	Integer[][][] md = new Integer[][][] {{{5, 6, 7, 10}, {2, 4, 6, 9}}};
	Integer[] sd = new Integer[] {5, 6, 7, 10, 2, 4, 6, 9};
	
	public void setUp() {
	}
	
	public void tearDown() throws Exception {
	}
	
	public void testPrintArrayDeep()
	{
		MultiDimensionArrayUtils.printArrayDeep(md, System.out);
	}
	
	public void testGetArrayLengths()
	{
		int lens[] = MultiDimensionArrayUtils.getArrayLengths(md);
		assertTrue(Arrays.equals(new int[]{1, 2, 4}, lens));
	}
	
	public void testGetDimension()
	{
		assertEquals(3, MultiDimensionArrayUtils.getDimension(md));
	}
	
	public void testCreateMultiDimArray()
	{
		boolean[][][] a = (boolean[][][]) MultiDimensionArrayUtils.createMultiDimArray(boolean.class, new int[] {2, 2, 2});
		assertTrue(Arrays.deepEquals( new boolean[2][2][2], a));
	}
	
	public void testGetLength()
	{
		assertEquals( 8, MultiDimensionArrayUtils.getLength( new int[] {2, 2, 2} ) );		
	}
	
	public void testGetComponentType()
	{
		assertEquals( Integer.class, MultiDimensionArrayUtils.getComponentType(md.getClass()) );
	}
	
	public void testDemuxArray()
	{
		Integer[][][] mdD = (Integer[][][]) MultiDimensionArrayUtils.demuxArray(sd, new int[]{1, 2, 4}, Integer.class);
		assertTrue( Arrays.deepEquals( md, mdD) );		
	}
	
	public void testMuxArray()
	{
		Integer[] sdD = (Integer[]) MultiDimensionArrayUtils.muxArray(md);
		assertTrue( Arrays.equals( sd, sdD) );
	}
	
	public void testArrayIterator()
	{
		ArrayIterator<Integer> iter = MultiDimensionArrayUtils.arrayIterator(md, MultiDimensionArrayUtils.getArrayLengths(md));
		assertEquals( 5, (int)iter.next());
		assertEquals( 6, (int)iter.next());
		assertEquals( 7, (int)iter.next());
		assertEquals( 10,(int)iter.next());
		assertEquals( 2, (int)iter.next());
		assertEquals( 4, (int)iter.next());
		assertEquals( 6, (int)iter.next());		
		assertEquals( 9, (int)iter.next());		
	}
	
	public void testMuxArray2() {
		int sd[] = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24 };
		int[][][][] md = new int[][][][] { { { { 1, 2 }, { 3, 4 } }, { { 5, 6 }, { 7, 8 } } },
				{ { { 9, 10 }, { 11, 12 } }, { { 13, 14 }, { 15, 16 } } },
				{ { { 17, 18 }, { 19, 20 } }, { { 21, 22 }, { 23, 24 } } } };
		assertTrue(Arrays.equals(sd, (int[]) MultiDimensionArrayUtils.muxArray(md)));
	}
	
	

}
