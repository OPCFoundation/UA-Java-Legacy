package org.opcfoundation.ua.builtintypes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnsignedIntegerTest {

	@Test
	public void parseString() throws Exception {
	    assertEquals(new UnsignedInteger(0), UnsignedInteger.parseUnsignedInteger("0", 10));
	    assertEquals(new UnsignedInteger(473), UnsignedInteger.parseUnsignedInteger("473", 10));
	    assertEquals(new UnsignedInteger(0), UnsignedInteger.parseUnsignedInteger("-0", 10));
	    assertEquals(new UnsignedInteger(102), UnsignedInteger.parseUnsignedInteger("1100110", 2));
	    assertEquals(new UnsignedInteger(4294967295l), UnsignedInteger.parseUnsignedInteger("4294967295", 10));
	    assertEquals(new UnsignedInteger(0), UnsignedInteger.parseUnsignedInteger("0", 10));
	    assertEquals(new UnsignedInteger(411787), UnsignedInteger.parseUnsignedInteger("Kona", 27));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString() throws Exception {
	    UnsignedInteger.parseUnsignedInteger("-FF", 16);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString2() throws Exception {
		UnsignedInteger.parseUnsignedInteger("4294967296", 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void parseErrorString3() throws Exception {
		UnsignedInteger.parseUnsignedInteger("-1", 10);
	}
	
	@Test(expected = NumberFormatException.class)
	public void parseErrorString4() throws Exception {
		UnsignedInteger.parseUnsignedInteger("99", 8);
	}
	
	@Test(expected = NumberFormatException.class)
	public void parseErrorString5() throws Exception {
		UnsignedInteger.parseUnsignedInteger("Kona", 10);
	}
	
}
