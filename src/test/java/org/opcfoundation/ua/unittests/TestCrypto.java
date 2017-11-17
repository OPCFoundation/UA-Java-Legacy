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

import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Assert;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.transport.security.BcCryptoProvider;
import org.opcfoundation.ua.transport.security.BcJceCryptoProvider;
import org.opcfoundation.ua.transport.security.CryptoProvider;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.ScCryptoProvider;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.transport.security.SecurityConfiguration;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCrypto extends TestCase {
	
	static Logger logger = LoggerFactory.getLogger(TestCrypto.class);

	SecurityConfiguration clientProfile;
	SecurityConfiguration serverProfile;
	
	CryptoProvider clientCryptoProvider;
	CryptoProvider serverCryptoProvider;
	
	public void testBase64Encode() {
      base64EncodeDecode(0);
      CryptoUtil.setSecurityProviderName("SunJCE");
      base64EncodeDecode(0);
	}
	
    public void testBase64EncodeMultiline() {
      int lineLength = 64;
      base64EncodeDecode(lineLength);
      CryptoUtil.setSecurityProviderName("SunJCE");
      base64EncodeDecode(lineLength);
    }

    /**
     * @param lineLength
     */
    protected void base64EncodeDecode(int lineLength) {
      byte[] expected = new byte[100];
      for (int i=0; i < expected.length; i++)
          expected[i] = (byte)i;
      String s = CryptoUtil.base64Encode(expected);
      s = StringUtils.addLineBreaks(s, lineLength);
      logger.info("testBase64Encode: " + s);
      byte[] actual = CryptoUtil.base64Decode(s);
      assertTrue("expected=" + CryptoUtil.toHex(expected) + " actual=" + CryptoUtil.toHex(actual),
              Arrays.equals(expected, actual));
    }
    
    public void testEncryptDecryptRsa15() throws Exception {
		try {
			clientCryptoProvider = new BcJceCryptoProvider();
			serverCryptoProvider = new BcCryptoProvider();
			_setupTest(SecurityMode.BASIC128RSA15_SIGN_ENCRYPT, 2048);
			encryptDecryptAsymm();
		} finally {
			_tearDown();
		}
	}

	public void testEncryptDecryptRsaOaep() throws Exception {
		try {
			clientCryptoProvider = new BcJceCryptoProvider();
			serverCryptoProvider = new BcCryptoProvider();
			_setupTest(SecurityMode.BASIC256_SIGN_ENCRYPT, 2048);
			encryptDecryptAsymm();
		} finally {
			_tearDown();
		}
	}
	
	public void testEncryptDecryptRsa15WithSc() throws Exception {
		try {
			clientCryptoProvider = new ScCryptoProvider();
			serverCryptoProvider = new BcCryptoProvider();
			_setupTest(SecurityMode.BASIC128RSA15_SIGN_ENCRYPT, 2048);
			encryptDecryptAsymm();
		} finally {
			_tearDown();
		}
	}

	public void testEncryptDecryptRsaOaepWithSc() throws Exception {
		try {
			clientCryptoProvider = new BcJceCryptoProvider();
			serverCryptoProvider = new ScCryptoProvider();
			_setupTest(SecurityMode.BASIC256_SIGN_ENCRYPT, 2048);
			encryptDecryptAsymm();
		} finally {
			_tearDown();
		}
	}

	public void encryptDecryptAsymm() throws ServiceResultException {
		
		Certificate serverCert = serverProfile.getLocalCertificate();
		RSAPrivateKey serverPrivateKey = serverProfile.getLocalPrivateKey();
		
		SecurityAlgorithm algorithm = clientProfile.getSecurityPolicy().getAsymmetricEncryptionAlgorithm();
		
		int inputBlockSize = CryptoUtil.getPlainTextBlockSize(algorithm,
				serverProfile.getRemoteCertificate().getPublicKey());
		int outputBlockSize = CryptoUtil.getCipherBlockSize(algorithm,
				serverProfile.getRemoteCertificate().getPublicKey());
		logger.info("encryptAsymm: inputBlockSize={}, outputBlockSize={}",
				inputBlockSize, outputBlockSize);
		
		// Encrypt.
		byte[] dataToEncrypt = new byte[inputBlockSize];
		byte[] encrypted = new byte[outputBlockSize];
		byte[] origData = "Hello, world".getBytes();
		
		System.arraycopy(origData, 0, dataToEncrypt, 0, origData.length);
		clientCryptoProvider.encryptAsymm(serverCert.getPublicKey(), clientProfile.getSecurityPolicy().getAsymmetricEncryptionAlgorithm(), dataToEncrypt, encrypted, 0);
		
		// Decrypt.
		byte[] dataToDecrypt = new byte[outputBlockSize];
		byte[] decrypted = new byte[outputBlockSize];
		System.arraycopy(encrypted, 0, dataToDecrypt, 0, encrypted.length);
		
		serverCryptoProvider.decryptAsymm(serverPrivateKey, serverProfile.getSecurityPolicy().getAsymmetricEncryptionAlgorithm(), dataToDecrypt, decrypted, 0);
		
		for (int i = 0; i < dataToEncrypt.length; i++) {
			if (dataToEncrypt[i] != decrypted[i]) {
				Assert.fail("Encrypted and decrypted data do not match.");
			}
		}
		
	}
	
	public void _setupTest(SecurityMode mode, int keySize) throws ServiceResultException {
				
		KeyPair clientKeyPair = UnitTestKeys.getKeyPair("client", keySize);
		KeyPair serverKeyPair = UnitTestKeys.getKeyPair("server", keySize);
		
		clientProfile = new SecurityConfiguration(mode, clientKeyPair, serverKeyPair.getCertificate());
		serverProfile = new SecurityConfiguration(mode, serverKeyPair, clientKeyPair.getCertificate());
		
		logger.info("\nAlgorithm: \nclient={}, \nserver={}",
				clientProfile.getSecurityPolicy().getAsymmetricEncryptionAlgorithm(),
				serverProfile.getSecurityPolicy().getAsymmetricEncryptionAlgorithm());
		
	}
	
	private void _tearDown() {
		// TODO Auto-generated method stub
		
	}
	
}
