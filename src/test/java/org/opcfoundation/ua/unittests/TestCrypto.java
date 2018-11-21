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
import org.opcfoundation.ua.transport.security.SecurityPolicy;
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

    public void testEncryptDecryptAes128WithBc() throws Exception {
        try {
          // Sample values from: http://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38a.pdf
          byte[] key = javax.xml.bind.DatatypeConverter.parseHexBinary("2b7e151628aed2a6abf7158809cf4f3c");
          byte[] iv = javax.xml.bind.DatatypeConverter.parseHexBinary("000102030405060708090a0b0c0d0e0f");
          byte[] input = javax.xml.bind.DatatypeConverter.parseHexBinary("6bc1bee22e409f96e93d7e117393172a");
          byte[] output = javax.xml.bind.DatatypeConverter.parseHexBinary("7649abac8119b246cee98e9b12e9197d");
          encryptDecryptSymm(SecurityPolicy.AES128_SHA256_RSAOAEP, key, iv, input, output);
        } finally {
            _tearDown();
        }
      }
    

    public void testEncryptDecryptAes256WithBc() throws Exception {
      try {
        // Sample values from: http://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38a.pdf
        byte[] key = javax.xml.bind.DatatypeConverter.parseHexBinary("603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4");
        byte[] iv = javax.xml.bind.DatatypeConverter.parseHexBinary("000102030405060708090a0b0c0d0e0f");
        byte[] input = javax.xml.bind.DatatypeConverter.parseHexBinary("6bc1bee22e409f96e93d7e117393172a");
        byte[] output = javax.xml.bind.DatatypeConverter.parseHexBinary("f58c4c04d6e5f1ba779eabfb5f7bfbd6");
        encryptDecryptSymm(SecurityPolicy.AES256_SHA256_RSAPSS, key, iv, input, output);
      } finally {
          _tearDown();
      }
    }
    
//    public void testEncryptDecryptPubSub128WithBc() throws Exception {
//      try {
//        // Sample values from: https://tools.ietf.org/html/rfc3686#section-6
//        byte[] key = javax.xml.bind.DatatypeConverter.parseHexBinary("7691BE035E5020A8AC6E618529F9A0DC");
//        byte[] iv = javax.xml.bind.DatatypeConverter.parseHexBinary("00E0017B27777F3F4A1786F000000001");
//        byte[] input = javax.xml.bind.DatatypeConverter.parseHexBinary("000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F20212223");
//        byte[] output = javax.xml.bind.DatatypeConverter.parseHexBinary("C1CF48A89F2FFDD9CF4652E9EFDB72D74540A42BDE6D7836D59A5CEAAEF3105325B2072F");
//        encryptDecryptSymm(SecurityPolicy.PUBSUB_AES128_CTR, key, iv, input, output);
//      } finally {
//          _tearDown();
//      }
//    }
//    
//    public void testEncryptDecryptPubSub256WithBc() throws Exception {
//      try {
//        // Sample values from: https://tools.ietf.org/html/rfc3686#section-6
//        byte[] key = javax.xml.bind.DatatypeConverter.parseHexBinary("FF7A617CE69148E4F1726E2F43581DE2AA62D9F805532EDFF1EED687FB54153D");
//        byte[] iv = javax.xml.bind.DatatypeConverter.parseHexBinary("001CC5B751A51D70A1C1114800000001");
//        byte[] input = javax.xml.bind.DatatypeConverter.parseHexBinary("000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F20212223");
//        byte[] output = javax.xml.bind.DatatypeConverter.parseHexBinary("EB6C52821D0BBBF7CE7594462ACA4FAAB407DF866569FD07F48CC0B583D6071F1EC0E6B8");
//        encryptDecryptSymm(SecurityPolicy.PUBSUB_AES256_CTR, key, iv, input, output);
//      } finally {
//          _tearDown();
//      }
//
//    }
	
    public void encryptDecryptSymm(SecurityPolicy policy, byte[] key, byte[] iv, byte[] input, byte[] output) throws ServiceResultException {
      
      byte[] encrypted = new byte[input.length];
      
      BcCryptoProvider cryptoProvider = new BcCryptoProvider();      
      cryptoProvider.encryptSymm(policy, key, iv, input, 0, input.length, encrypted, 0);
      
      logger.info("Encrypted bytes:        " + javax.xml.bind.DatatypeConverter.printHexBinary(encrypted));
      logger.info("Target encrypted bytes: " + javax.xml.bind.DatatypeConverter.printHexBinary(output));
      
      for (int i = 0; i < output.length; i++) {
        if (output[i] != encrypted[i]) {
          Assert.fail("Encrypted and target output data do not match.");
        }
      }

      byte[] decrypted = new byte[input.length];
      cryptoProvider.decryptSymm(policy, key, iv, output, 0, output.length, decrypted, 0);
      
      logger.info("Decrypted bytes:        " + javax.xml.bind.DatatypeConverter.printHexBinary(decrypted));
      logger.info("Target decrypted bytes: " + javax.xml.bind.DatatypeConverter.printHexBinary(input));
      
      for (int i = 0; i < input.length; i++) {
        if (input[i] != decrypted[i]) {
          Assert.fail("Decrypted and target output data do not match.");
        }
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
