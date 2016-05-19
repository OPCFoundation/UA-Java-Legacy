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
import java.math.BigInteger;

import org.opcfoundation.ua.builtintypes.UnsignedLong;

import junit.framework.TestCase;


public class TestUnsignedLong extends TestCase {

	public void testBigInteger() {
		BigInteger bi = BigInteger.valueOf(Long.MAX_VALUE);
		UnsignedLong v1 = UnsignedLong.valueOf(Long.MAX_VALUE);
		UnsignedLong v2 = new UnsignedLong(bi);
		assertEquals(v1, v2);
		
		BigInteger bi2 = bi.add(BigInteger.valueOf(1));
		v1 = v1.add(1);
		v2 = new UnsignedLong(bi2);
		assertEquals(v1, v2);

		v1 = v1.subtract(1);
		v2 = new UnsignedLong(bi);
		assertEquals(v1, v2);
		
		v1 = UnsignedLong.MAX_VALUE.subtract(1);
		v2 = new UnsignedLong(Long.MAX_VALUE).add(Long.MAX_VALUE);
		assertEquals(v1, v2);

		v1 = UnsignedLong.MAX_VALUE.subtract(Long.MAX_VALUE);
		v2 = new UnsignedLong(Long.MAX_VALUE).add(1);
		assertEquals(v1, v2);
	}
	
}
