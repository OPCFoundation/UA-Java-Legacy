package org.opcfoundation.ua.builtintypes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnsignedByteTest {

	@Test
	public void parseString() throws Exception {
	    assertEquals(new UnsignedByte(0), UnsignedByte.parseUnsignedByte("0", 10));
	    assertEquals(new UnsignedByte(0), UnsignedByte.parseUnsignedByte("-0", 10));
	    assertEquals(new UnsignedByte(102), UnsignedByte.parseUnsignedByte("1100110", 2));
	    assertEquals(new UnsignedByte(255), UnsignedByte.parseUnsignedByte("255", 10));
	    assertEquals(new UnsignedByte(0), UnsignedByte.parseUnsignedByte("0", 10));
	    assertEquals(new UnsignedByte(20), UnsignedByte.parseUnsignedByte("K", 27));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString() throws Exception {
		UnsignedByte.parseUnsignedByte("-FF", 16);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString2() throws Exception {
		UnsignedByte.parseUnsignedByte("256", 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString3() throws Exception {
		UnsignedByte.parseUnsignedByte("-1", 10);
	}
	
	@Test(expected = NumberFormatException.class)
	public void parseErrorString4() throws Exception {
		UnsignedByte.parseUnsignedByte("99", 8);
	}
	
	@Test(expected = NumberFormatException.class)
	public void parseErrorString5() throws Exception {
		UnsignedByte.parseUnsignedByte("Kona", 10);
	}
	
}
