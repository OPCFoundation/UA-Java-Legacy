package org.opcfoundation.ua.encoding.binary;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ByteUtilsTest {
	@Test
	public void reverseNullElement() throws Exception {
		byte[] actual = ByteUtils.reverse(null);
		assertNull(actual);
	}
	
	@Test
	public void reverse0Element() throws Exception {
		byte[] d = new byte[] {};
		byte[] expected = new  byte[] {};
		byte[] actual = ByteUtils.reverse(d);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void reverse1Element() throws Exception {
		byte[] d = new byte[] {1};
		byte[] expected = new  byte[] {1};
		byte[] actual = ByteUtils.reverse(d);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void reverse2Element() throws Exception {
		byte[] d = new byte[] {1,2};
		byte[] expected = new  byte[] {2,1};
		byte[] actual = ByteUtils.reverse(d);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void reverse3Element() throws Exception {
		byte[] d = new byte[] {1,2,3};
		byte[] expected = new  byte[] {3,2,1};
		byte[] actual = ByteUtils.reverse(d);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void reverse4Element() throws Exception {
		byte[] d = new byte[] {1,2,3,4};
		byte[] expected = new  byte[] {4,3,2,1};
		byte[] actual = ByteUtils.reverse(d);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void concat() throws Exception {
		byte[] f = new byte[] {1,2};
		byte[] s = new byte[] {3,4};
		byte[] expected = new byte[] {1,2,3,4};
		byte[] actual = ByteUtils.concat(f, s);
		assertArrayEquals(expected, actual);
	}
}
