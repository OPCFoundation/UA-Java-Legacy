package org.opcfoundation.ua.transport.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BcCryptoProviderTest {

	@Test
	public void base64decode() throws Exception {
		// "teststring" encoded as Base64
		String data = "dGVzdHN0cmluZw==";
		BcCryptoProvider sut = new BcCryptoProvider();
		String out = new String(sut.base64Decode(data));
		assertEquals("teststring", out);
	}
	
	@Test
	public void base64decodeWithWhitespace() throws Exception {
		// "teststring" encoded as Base64
		String data = "dGVzdHN0cmluZ" + "\r\n" + "               " + "w==";
		BcCryptoProvider sut = new BcCryptoProvider();
		String out = new String(sut.base64Decode(data));
		assertEquals("teststring", out);
	}
	
}
