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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.utils.NumericRange;

public class TestNumericRange {

	int[] testArrayOneDim = {0,1,2};
	int[][] testArrayTwoDim = {{0,1,2},{3,4,5}};
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testOneDim() throws ServiceResultException {
		NumericRange nr = new NumericRange(0, 2);
		Assert.assertEquals(0, nr.getBegin());
		Assert.assertEquals(2, nr.getEnd());
		Assert.assertEquals("0:2", nr.toString());
		
		NumericRange nr2 = NumericRange.parse("0:2");
		Assert.assertEquals(0, nr2.getBegin());
		Assert.assertEquals(2, nr2.getEnd());
	}

	@Test
	public void testParseOneDim() throws ServiceResultException {
		NumericRange nr2 = NumericRange.parse("0:2");
		Assert.assertEquals(0, nr2.getBegin());
		Assert.assertEquals(2, nr2.getEnd());
	}
	
	@Test
	public void testOneDimBegin() {
		NumericRange nr = new NumericRange(0);
		Assert.assertEquals(0, nr.getBegin());
		Assert.assertEquals(0, nr.getEnd());
		Assert.assertEquals("0", nr.toString());
	}
	
	@Test
	public void testParseOneDimBegin() throws ServiceResultException {
		NumericRange nr2 = NumericRange.parse("0");
		Assert.assertEquals(0, nr2.getBegin());
		Assert.assertEquals(0, nr2.getEnd());
	}
	
	@Test
	public void testOneDimEmpty() {
		NumericRange nr = new NumericRange();
		Assert.assertTrue(nr.isEmpty());
		Assert.assertEquals(-1, nr.getBegin());
		Assert.assertEquals(-1, nr.getEnd());
		Assert.assertEquals("", nr.toString());
	}

	@Test
	public void testParseOneDimEmpty() throws ServiceResultException {
		NumericRange nr = NumericRange.parse("");
		Assert.assertTrue(nr.isEmpty());
		Assert.assertEquals(-1, nr.getBegin());
		Assert.assertEquals(-1, nr.getEnd());
	}
	
	@Test
	public void testTwoDim() {
		NumericRange nr = new NumericRange();
		nr.setDimensions(2);
		nr.setBegin(0,0);
		nr.setEnd(0,2);
		nr.setBegin(1,1);
		nr.setEnd(1,3);
		Assert.assertEquals(0, nr.getBegin(0));
		Assert.assertEquals(2, nr.getEnd(0));
		Assert.assertEquals(1, nr.getBegin(1));
		Assert.assertEquals(3, nr.getEnd(1));
		Assert.assertEquals("0:2,1:3", nr.toString());
	}
	
	@Test
	public void testTwoDim2() {
		NumericRange nr = new NumericRange(new int[]{0, 2}, new int[]{1, 3});
		Assert.assertEquals(0, nr.getBegin(0));
		Assert.assertEquals(2, nr.getEnd(0));
		Assert.assertEquals(1, nr.getBegin(1));
		Assert.assertEquals(3, nr.getEnd(1));
		Assert.assertEquals("0:2,1:3", nr.toString());
	}

	@Test
	public void testParseTwoDim() throws ServiceResultException {
		NumericRange nr = NumericRange.parse("0:2,1:3");
		Assert.assertEquals(0, nr.getBegin(0));
		Assert.assertEquals(2, nr.getEnd(0));
		Assert.assertEquals(1, nr.getBegin(1));
		Assert.assertEquals(3, nr.getEnd(1));
	}
	
	@Test
	public void testThreeDim() {
		NumericRange nr = new NumericRange();
		nr.setDimensions(3);
		nr.setBegin(0,0);
		nr.setEnd(0,2);
		nr.setBegin(1,1);
		nr.setEnd(1,3);
		Assert.assertEquals(0, nr.getBegin(0));
		Assert.assertEquals(2, nr.getEnd(0));
		Assert.assertEquals(1, nr.getBegin(1));
		Assert.assertEquals(3, nr.getEnd(1));
		Assert.assertEquals(-1, nr.getBegin(2));
		Assert.assertEquals(-1, nr.getEnd(2));
		Assert.assertEquals("0:2,1:3,", nr.toString());
		nr.setBegin(2,4);
		Assert.assertEquals(4, nr.getBegin(2));
		Assert.assertEquals(4, nr.getEnd(2));
		Assert.assertEquals("0:2,1:3,4", nr.toString());
		nr.setEnd(2,6);
		Assert.assertEquals(4, nr.getBegin(2));
		Assert.assertEquals(6, nr.getEnd(2));
		Assert.assertEquals("0:2,1:3,4:6", nr.toString());
	}
	
	@Test
	public void testThreeDim2() {
		NumericRange nr = new NumericRange(new int[]{0, 2}, new int[]{1, 3}, new int[]{4, 6});
		Assert.assertEquals(0, nr.getBegin(0));
		Assert.assertEquals(2, nr.getEnd(0));
		Assert.assertEquals(1, nr.getBegin(1));
		Assert.assertEquals(3, nr.getEnd(1));
		Assert.assertEquals(4, nr.getBegin(2));
		Assert.assertEquals(6, nr.getEnd(2));
		Assert.assertEquals("0:2,1:3,4:6", nr.toString());
	}

	@Test
	public void testParseThreeDimBegin() throws ServiceResultException {
		NumericRange nr = NumericRange.parse("0:2,1:3,4");
		Assert.assertEquals(0, nr.getBegin(0));
		Assert.assertEquals(2, nr.getEnd(0));
		Assert.assertEquals(1, nr.getBegin(1));
		Assert.assertEquals(3, nr.getEnd(1));
		Assert.assertEquals(4, nr.getBegin(2));
		Assert.assertEquals(4, nr.getEnd(2));
	}
	@Test
	public void testParseThreeDim() throws ServiceResultException {
		NumericRange nr = NumericRange.parse("0:2,1:3,4:6");
		Assert.assertEquals(0, nr.getBegin(0));
		Assert.assertEquals(2, nr.getEnd(0));
		Assert.assertEquals(1, nr.getBegin(1));
		Assert.assertEquals(3, nr.getEnd(1));
		Assert.assertEquals(4, nr.getBegin(2));
		Assert.assertEquals(6, nr.getEnd(2));
	}
}
