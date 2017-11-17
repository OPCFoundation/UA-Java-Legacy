package org.opcfoundation.ua.builtintypes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnsignedLongTest {

	@Test
	public void parseString() throws Exception {
	    assertEquals(new UnsignedLong(0), UnsignedLong.parseUnsignedLong("0", 10));
	    assertEquals(new UnsignedLong(473), UnsignedLong.parseUnsignedLong("473", 10));
	    assertEquals(new UnsignedLong(0), UnsignedLong.parseUnsignedLong("-0", 10));
	    assertEquals(new UnsignedLong(102), UnsignedLong.parseUnsignedLong("1100110", 2));
	    assertEquals(UnsignedLong.MAX_VALUE, UnsignedLong.parseUnsignedLong("18446744073709551615", 10));
	    assertEquals(new UnsignedLong(0), UnsignedLong.parseUnsignedLong("0", 10));
	    assertEquals(new UnsignedLong(411787), UnsignedLong.parseUnsignedLong("Kona", 27));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString() throws Exception {
		UnsignedLong.parseUnsignedLong("-FF", 16);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString2() throws Exception {
		UnsignedLong.parseUnsignedLong("18446744073709551616", 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString3() throws Exception {
		UnsignedLong.parseUnsignedLong("-1", 10);
	}
	
	@Test(expected = NumberFormatException.class)
	public void parseErrorString4() throws Exception {
		UnsignedLong.parseUnsignedLong("99", 8);
	}
	
	@Test(expected = NumberFormatException.class)
	public void parseErrorString5() throws Exception {
		UnsignedLong.parseUnsignedLong("Kona", 10);
	}
	
}
