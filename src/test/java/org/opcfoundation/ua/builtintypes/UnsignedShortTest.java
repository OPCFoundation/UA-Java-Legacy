package org.opcfoundation.ua.builtintypes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnsignedShortTest {

	@Test
	public void parseString() throws Exception {
	    assertEquals(new UnsignedShort(0), UnsignedShort.parseUnsignedShort("0", 10));
	    assertEquals(new UnsignedShort(473), UnsignedShort.parseUnsignedShort("473", 10));
	    assertEquals(new UnsignedShort(0), UnsignedShort.parseUnsignedShort("-0", 10));
	    assertEquals(new UnsignedShort(102), UnsignedShort.parseUnsignedShort("1100110", 2));
	    assertEquals(new UnsignedShort(65535), UnsignedShort.parseUnsignedShort("65535", 10));
	    assertEquals(new UnsignedShort(0), UnsignedShort.parseUnsignedShort("0", 10));
	    assertEquals(new UnsignedShort(280), UnsignedShort.parseUnsignedShort("aa", 27));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString() throws Exception {
		UnsignedShort.parseUnsignedShort("-FF", 16);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString2() throws Exception {
		UnsignedShort.parseUnsignedShort("65536", 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString3() throws Exception {
		UnsignedShort.parseUnsignedShort("-1", 10);
	}
	
	@Test(expected = NumberFormatException.class)
	public void parseErrorString4() throws Exception {
		UnsignedShort.parseUnsignedShort("99", 8);
	}
	
	@Test(expected = NumberFormatException.class)
	public void parseErrorString5() throws Exception {
		UnsignedShort.parseUnsignedShort("Kona", 10);
	}
	
}
