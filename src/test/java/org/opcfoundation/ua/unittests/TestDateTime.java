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
import java.text.ParseException;
import java.util.Calendar;

import junit.framework.TestCase;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.binary.BinaryDecoder;
import org.opcfoundation.ua.encoding.binary.BinaryEncoder;


public class TestDateTime extends TestCase {

	public static void testMain() {
        DateTime test1 = new DateTime(0);
        DateTime test2 = new DateTime(test1.getUtcCalendar());
        assertEquals(test1, test2);
        
		System.out.format("OffsetToGregorianCalendarZero: %d%n", DateTime.OffsetToGregorianCalendarZero);
    	DateTime d;
    	d = DateTime.MIN_VALUE;
        System.out.format("MIN_VALUE: %d = %s%n",  d.getValue(), d.toString());
	    	
    	d = DateTime.MAX_VALUE;
        System.out.format("MAX_VALUE: %d = %s%n",  d.getValue(), d.toString());

        d = new DateTime();
        System.out.format("UTC Now: %d = %s%n", d.getValue(), d.toString());
	        
        System.out.format("Local Now: %d = %Tc%n", d.getValue(), d.getLocalCalendar());
	}
	
	public static void testEncodeDecode() throws EncodingException, DecodingException, InterruptedException {
		DateTime d;
		final String fieldName = null;
		byte[] buffer = new byte[10240];
		BinaryEncoder encoder = new BinaryEncoder(buffer);
		BinaryDecoder decoder = new BinaryDecoder(buffer);
		for (int i=0; i<100;i++) {
			d = DateTime.currentTime();
			encoder.putDateTime(fieldName, d);
			assertEquals(d, decoder.getDateTime(fieldName));
			DataValue dv = new DataValue(new Variant(i));
			encoder.putDataValue(fieldName, dv);
			assertEquals(dv, decoder.getDataValue(fieldName));
			Thread.sleep(5);
		}
	}
	
	public static void testParse() throws ParseException {
		DateTime d = new DateTime(2011, Calendar.APRIL, 13, 11, 47, 12, 0);
		String s = "2011-04-13T11:47:12Z";
		assertEquals(d, DateTime.parseDateTime(s));
		s = "2011-04-13T11:47:12+00:00";
		assertEquals(d, DateTime.parseDateTime(s));
		s = "2011-04-13T12:47:12+01:00";
		assertEquals(d, DateTime.parseDateTime(s));
		s = "2011-04-13T10:47:12-01:00";
		assertEquals(d, DateTime.parseDateTime(s));
	}
}
