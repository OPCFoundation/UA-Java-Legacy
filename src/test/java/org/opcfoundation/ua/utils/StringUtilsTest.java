package org.opcfoundation.ua.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void removeWhitespace() throws Exception {
		assertEquals("aa", StringUtils.removeWhitespace("\t\r\n    aa"));
	}
	
}
