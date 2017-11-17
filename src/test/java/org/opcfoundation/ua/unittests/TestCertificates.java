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

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import junit.framework.TestCase;

import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.cert.DefaultCertificateValidator;
import org.opcfoundation.ua.cert.PkiDirectoryCertificateStore;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.transport.security.BcCryptoProvider;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.CryptoProvider;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.PrivKey;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.utils.CertificateUtils;
import org.opcfoundation.ua.utils.CryptoUtil;


public class TestCertificates extends TestCase {

	private static final String ALIAS = "alias";
	private static final String KEY_PASSWORD = "pass";
	private static final String TEST_FILE = "test.key";
	private File file;
	private KeyPair keys;

	public void setUp() throws Exception {
		file = new File(TEST_FILE);
		keys = CertificateUtils.createApplicationInstanceCertificate(
				"Test", "Test", "urn:localhost:UA:Test", 365);
	}
	
	protected void tearDown() throws Exception {
		file.delete();
	}
	
	public void testPfx() throws GeneralSecurityException, IOException  {
		CertificateUtils.saveToProtectedStore(keys.getPrivateKey()
				.getPrivateKey(), keys.getCertificate().getCertificate(), file,
				ALIAS, null, KEY_PASSWORD, "PKCS12");
		RSAPrivateKey privKey = CertificateUtils.loadFromKeyStore(file.toURI()
				.toURL(), KEY_PASSWORD);
		assertEquals(keys.getPrivateKey().getPrivateKey(), privKey);
	}

	public void testCASigned() throws IllegalStateException,
			IOException,
			GeneralSecurityException {
		KeyPair caKeys = CertificateUtils.createIssuerCertificate("TestCA", 3650, null);
		File file = new File("TestCA.der");
		caKeys.getCertificate().save(file);
		Cert caCert = Cert.load(file);
		assertEquals(caKeys.getCertificate().getCertificate(), caCert.getCertificate());
		KeyPair keys = CertificateUtils.createApplicationInstanceCertificate(
				"Test", "Test", "urn:localhost:UA:Test", 365, caKeys);
		file = new File("TestCert.der");
		keys.getCertificate().save(file);
		Cert cert = Cert.load(file);
		assertEquals(keys.getCertificate().getCertificate(), cert.getCertificate());
		file = new File("TestKey.pem");
		keys.getPrivateKey().save(file, null);
		PrivKey privKey = PrivKey.load(file, null);
		assertEquals(keys.getPrivateKey().getPrivateKey(), privKey.getPrivateKey());
	}
	
	public void testCertificateChain() throws IllegalStateException,
			IOException, GeneralSecurityException {

		KeyPair rootCaKeys = CertificateUtils.createIssuerCertificate("RootCA",
				3650, null);
		File rootCaFile = new File("RootCA.der");
		rootCaKeys.getCertificate().save(rootCaFile);
		Cert rootCaCert = Cert.load(rootCaFile);

		int intermediateCaAmount = 10;

		KeyPair[] intermediateCaKeys = new KeyPair[intermediateCaAmount];
		File[] intermediateCaFiles = new File[intermediateCaAmount];
		Cert[] intermediateCaCerts = new Cert[intermediateCaAmount];
		for (int i = 0; i < intermediateCaAmount; i++) {
			intermediateCaKeys[i] = CertificateUtils.createIssuerCertificate(
					"IntermediateCA" + (i + 1), 3650,
					i == 0 ? rootCaKeys : intermediateCaKeys[i - 1]);
			intermediateCaFiles[i] = new File(
					"IntermediateCA" + (i + 1) + ".der");
			intermediateCaKeys[i].getCertificate().save(intermediateCaFiles[i]);
			intermediateCaCerts[i] = Cert.load(intermediateCaFiles[i]);
		}

		KeyPair leafKeys = CertificateUtils
				.createApplicationInstanceCertificate("chainTest", "chainTest",
						"urn:localhost:UA:chainTest", 365,
						intermediateCaKeys[intermediateCaKeys.length - 1]);
		File leafFile = new File("LeafCert.der");
		leafKeys.getCertificate().save(leafFile);
		Cert leafCert = Cert.load(leafFile);

		final PkiDirectoryCertificateStore myCertStore = new PkiDirectoryCertificateStore(
				"TestCertificatesPKI/CA");
		final DefaultCertificateValidator validator = new DefaultCertificateValidator(
				myCertStore);
		try {
			myCertStore.clear(true);
		} catch (Exception e) {
		}

		// Test that none of the certs below root CA can be validated without
		// the root CA
		assertEquals(true, validator.validateCertificate(leafCert)
				.isStatusCode(StatusCodes.Bad_CertificateChainIncomplete));
		for (int i = 0; i < intermediateCaAmount; i++)
			assertEquals(true, validator
					.validateCertificate(intermediateCaCerts[i])
					.isStatusCode(StatusCodes.Bad_CertificateChainIncomplete));

		// Test that the chain works
		myCertStore.addTrustedCertificate(rootCaCert);
		for (int i = 0; i < intermediateCaAmount; i++)
			assertEquals(StatusCode.GOOD,
					validator.validateCertificate(intermediateCaCerts[i]));
		assertEquals(StatusCode.GOOD, validator.validateCertificate(leafCert));

		// Test that rejecting the top intermediate cert makes lower level certs
		// invalid
		myCertStore.addRejectedCertificate(intermediateCaCerts[0]);
		assertEquals(true, validator.validateCertificate(leafCert)
				.isStatusCode(StatusCodes.Bad_CertificateChainIncomplete));
		assertEquals(true, validator
				.validateCertificate(
						intermediateCaCerts[intermediateCaCerts.length - 1])
				.isStatusCode(StatusCodes.Bad_CertificateChainIncomplete));

		// Test that previous statement rejected each cert in chain, thus
		// trusting intermediateCaCerts[0] doesn't validate the lowest certs
		assertEquals(StatusCode.GOOD,
				validator.validateCertificate(intermediateCaCerts[0]));
		assertEquals(true, validator
				.validateCertificate(
						intermediateCaCerts[intermediateCaCerts.length - 1])
				.isStatusCode(StatusCodes.Bad_CertificateChainIncomplete));
		assertEquals(true, validator.validateCertificate(leafCert)
				.isStatusCode(StatusCodes.Bad_CertificateChainIncomplete));

		for (int i = 1; i < intermediateCaAmount; i++)
			assertEquals(StatusCode.GOOD,
					validator.validateCertificate(intermediateCaCerts[i]));
		assertEquals(StatusCode.GOOD, validator.validateCertificate(leafCert));

		// Test that rejecting the lowest level intermediate cert makes leaf
		// cert invalid
		myCertStore.addRejectedCertificate(
				intermediateCaCerts[intermediateCaCerts.length - 1]);
		assertEquals(true, validator.validateCertificate(leafCert)
				.isStatusCode(StatusCodes.Bad_CertificateChainIncomplete));

		assertEquals(StatusCode.GOOD, validator.validateCertificate(
				intermediateCaCerts[intermediateCaCerts.length - 1]));
		assertEquals(StatusCode.GOOD, validator.validateCertificate(leafCert));

		// Test that rejecting the root CA makes every cert below it invalid
		myCertStore.addRejectedCertificate(rootCaCert);
		assertEquals(true, validator.validateCertificate(leafCert)
				.isStatusCode(StatusCodes.Bad_CertificateChainIncomplete));
		for (int i = 0; i < intermediateCaAmount; i++)
			assertEquals(true, validator
					.validateCertificate(intermediateCaCerts[i])
					.isStatusCode(StatusCodes.Bad_CertificateChainIncomplete));

		try {
			myCertStore.clear(true);
		} catch (Exception e) {
		}
	}

	public void testHttpsCert() throws IllegalStateException, IOException,
			GeneralSecurityException {
		KeyPair caKeys = CertificateUtils.createIssuerCertificate("TestCA",
				3650, null);
		File file = new File("TestCA_https.der");
		caKeys.getCertificate().save(file);
		Cert caCert = Cert.load(file);
		assertEquals(caKeys.getCertificate().getCertificate(),
				caCert.getCertificate());
		KeyPair keys = CertificateUtils.createHttpsCertificate(
				InetAddress.getLocalHost().getHostName(), "urn:localhost:UA:Test", 365, caKeys);
		file = new File("TestCert_https.der");
		keys.getCertificate().save(file);
		Cert cert = Cert.load(file);
		assertEquals(keys.getCertificate().getCertificate(),
				cert.getCertificate());
		file = new File("TestKey_https.pem");
		keys.getPrivateKey().save(file, null);
		PrivKey privKey = PrivKey.load(file, null);
		assertEquals(keys.getPrivateKey().getPrivateKey(),
				privKey.getPrivateKey());
	}
	
	public void testJks() throws IllegalStateException,
			IOException,
			GeneralSecurityException {
		CertificateUtils.saveKeyPairToProtectedStore(keys,
				file.getAbsolutePath(), ALIAS, KEY_PASSWORD, KEY_PASSWORD);
		KeyPair keys2 = CertificateUtils.createApplicationInstanceCertificate(
				"Test2", "Test2", "urn:localhost:UA:Test2", 365);
		String ALIAS2 = "Test2";
		CertificateUtils.saveKeyPairToProtectedStore(keys2,
				file.getAbsolutePath(), ALIAS2, KEY_PASSWORD, KEY_PASSWORD);

		KeyPair keyPair = CertificateUtils.loadKeyPairFromProtectedStore(TEST_FILE, ALIAS, KEY_PASSWORD, KEY_PASSWORD);
		assertEquals(keys.getPrivateKey().getPrivateKey(), keyPair.getPrivateKey().getPrivateKey());
		assertEquals(keys.getCertificate().getCertificate(), keyPair.getCertificate().getCertificate());
		KeyPair keyPair2 = CertificateUtils.loadKeyPairFromProtectedStore(TEST_FILE, ALIAS2, KEY_PASSWORD, KEY_PASSWORD);
		assertEquals(keys2.getPrivateKey().getPrivateKey(), keyPair2.getPrivateKey().getPrivateKey());
		assertEquals(keys2.getCertificate().getCertificate(), keyPair2.getCertificate().getCertificate());
	}

	public void testDerCert() throws IOException, CertificateException  {
		keys.getCertificate().save(file);
		Cert cert = Cert.load(file);
		assertEquals(keys.getCertificate().getCertificate(), cert.getCertificate());
	}

	public void testPemPrivKey() throws IOException, CertificateEncodingException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException  {
		keys.getPrivateKey().save(file, KEY_PASSWORD);
		PrivKey privKey = PrivKey.load(file, KEY_PASSWORD);
		assertEquals(keys.getPrivateKey().getPrivateKey(), privKey.getPrivateKey());
	}
	
	public void testPemPrivKeyNoPassword() throws IOException, GeneralSecurityException  {
		keys.getPrivateKey().save(file, null);
		PrivKey privKey = PrivKey.load(file, null);
		assertTrue(Arrays.equals(keys.getPrivateKey().getEncodedPrivateKey(), privKey.getEncodedPrivateKey()));
	}
	
	public void testSignVerify() throws Exception {
		setUp();
		
		PrivateKey privkey = keys.getPrivateKey().getPrivateKey();
		SecurityAlgorithm algorithm = SecurityAlgorithm.RsaSha1;
		byte[] dataToSign = new byte[100];
		@SuppressWarnings("deprecation")
		SignatureData signedData = CertificateUtils.sign(privkey, algorithm , dataToSign);
		byte[] signature = ByteString.asByteArray(signedData.getSignature());
		@SuppressWarnings("deprecation")
		boolean isCorrect = CertificateUtils.verify(keys.certificate.certificate, algorithm, dataToSign, signature);
		tearDown();
		
		assertEquals(true, isCorrect);
		
	}
	
	public void testSignVerifyWithExplicitCryptoProvider() throws Exception {
		CryptoProvider bcCryptoProvider = new BcCryptoProvider();
		CryptoUtil.setCryptoProvider(bcCryptoProvider);
		
		setUp();
		
		PrivateKey privkey = keys.getPrivateKey().getPrivateKey();
		SecurityAlgorithm algorithm = SecurityAlgorithm.RsaSha1;
		byte[] dataToSign = new byte[100];
		@SuppressWarnings("deprecation")
		SignatureData signedData = CertificateUtils.sign(privkey, algorithm , dataToSign);
		byte[] signature = ByteString.asByteArray(signedData.getSignature());
		@SuppressWarnings("deprecation")
		boolean isCorrect = CertificateUtils.verify(keys.certificate.certificate, algorithm, dataToSign, signature);
		
		CryptoUtil.setCryptoProvider(null);
		tearDown();
		
		assertEquals(true, isCorrect);
		
	}
	
	
	public void testSignVerifyWithIncorrectParameters() throws Exception {
		setUp();

		PrivateKey privkey = keys.getPrivateKey().getPrivateKey();
		SecurityAlgorithm algorithm = SecurityAlgorithm.RsaSha1;
		byte[] dataToSign = new byte[100];
		@SuppressWarnings("deprecation")
		SignatureData signedData = CertificateUtils.sign(privkey, algorithm , dataToSign);
		byte[] signature = ByteString.asByteArray(signedData.getSignature());
		
		algorithm = SecurityAlgorithm.RsaSha256;
		//now signature should not be verified
		try {
			@SuppressWarnings("deprecation")
			boolean isFalse = CertificateUtils.verify(keys.certificate.certificate, algorithm, dataToSign, signature);
			assertEquals(false, isFalse);
		} finally {
			tearDown();
		}
	}
	
	public void testSignVerifyWithIncorrectParametersAndExplicitCryptoProvider() throws Exception {
		setUp();
		CryptoProvider bcCryptoProvider = new BcCryptoProvider();
		CryptoUtil.setCryptoProvider(bcCryptoProvider);

		PrivateKey privkey = keys.getPrivateKey().getPrivateKey();
		SecurityAlgorithm algorithm = SecurityAlgorithm.RsaSha1;
		byte[] dataToSign = new byte[100];
		@SuppressWarnings("deprecation")
		SignatureData signedData = CertificateUtils.sign(privkey, algorithm , dataToSign);
		byte[] signature = ByteString.asByteArray(signedData.getSignature());
		
		algorithm = SecurityAlgorithm.RsaSha256;
		//now signature should not be verified
		try {
			@SuppressWarnings("deprecation")
			boolean isFalse = CertificateUtils.verify(keys.certificate.certificate, algorithm, dataToSign, signature);
			assertEquals(false, isFalse);
		} finally {
			CryptoUtil.setCryptoProvider(null);
			tearDown();
		}
		
	}
	
	/* Not deprecated way of signing and verifying is to use CryptoProviders methods. 
	 * These are the same tests as above but using CryptoUtil.getCryptoProvider() provided methods.
	 * */
	public void testSignVerifyWithCryptoUtil() throws Exception {
		setUp();
		
		RSAPrivateKey privkey = keys.getPrivateKey().getPrivateKey();
		SecurityAlgorithm algorithm = SecurityAlgorithm.RsaSha1;
		byte[] dataToSign = new byte[100];
		SignatureData signedData = new SignatureData(algorithm.getUri(), ByteString.valueOf(CryptoUtil.getCryptoProvider().signAsymm(privkey, algorithm, dataToSign)));
		byte[] signature = ByteString.asByteArray(signedData.getSignature());
		boolean isCorrect = CryptoUtil.getCryptoProvider().verifyAsymm(keys.certificate.certificate.getPublicKey(), algorithm, dataToSign, signature);
		tearDown();
		
		assertEquals(true, isCorrect);
		
	}
	
	public void testSignVerifyWithExplicitCryptoProviderWithCryptoUtil() throws Exception {
		CryptoProvider bcCryptoProvider = new BcCryptoProvider();
		CryptoUtil.setCryptoProvider(bcCryptoProvider);
		
		setUp();
		
		RSAPrivateKey privkey = keys.getPrivateKey().getPrivateKey();
		SecurityAlgorithm algorithm = SecurityAlgorithm.RsaSha1;
		byte[] dataToSign = new byte[100];
		SignatureData signedData = new SignatureData(algorithm.getUri(), ByteString.valueOf(CryptoUtil.getCryptoProvider().signAsymm(privkey, algorithm, dataToSign)));
		byte[] signature = ByteString.asByteArray(signedData.getSignature());
		boolean isCorrect = CryptoUtil.getCryptoProvider().verifyAsymm(keys.certificate.certificate.getPublicKey(), algorithm, dataToSign, signature);
		
		CryptoUtil.setCryptoProvider(null);
		tearDown();
		
		assertEquals(true, isCorrect);
		
	}
	
	
	public void testSignVerifyWithIncorrectParametersWithCryptoUtil() throws Exception {
		setUp();

		RSAPrivateKey privkey = keys.getPrivateKey().getPrivateKey();
		SecurityAlgorithm algorithm = SecurityAlgorithm.RsaSha1;
		byte[] dataToSign = new byte[100];
		SignatureData signedData = new SignatureData(algorithm.getUri(), ByteString.valueOf(CryptoUtil.getCryptoProvider().signAsymm(privkey, algorithm, dataToSign)));
		byte[] signature = ByteString.asByteArray(signedData.getSignature());
		
		algorithm = SecurityAlgorithm.RsaSha256;
		//now signature should not be verified
		try {
			boolean isFalse = CryptoUtil.getCryptoProvider().verifyAsymm(keys.certificate.certificate.getPublicKey(), algorithm, dataToSign, signature); 
			assertEquals(false, isFalse);
		} finally {
			tearDown();
		}
	}
	
	public void testSignVerifyWithIncorrectParametersAndExplicitCryptoProviderWithCryptoUtil() throws Exception {
		setUp();
		CryptoProvider bcCryptoProvider = new BcCryptoProvider();
		CryptoUtil.setCryptoProvider(bcCryptoProvider);

		RSAPrivateKey privkey = keys.getPrivateKey().getPrivateKey();
		SecurityAlgorithm algorithm = SecurityAlgorithm.RsaSha1;
		byte[] dataToSign = new byte[100];
		SignatureData signedData = new SignatureData(algorithm.getUri(), ByteString.valueOf(CryptoUtil.getCryptoProvider().signAsymm(privkey, algorithm, dataToSign)));
		byte[] signature = ByteString.asByteArray(signedData.getSignature());
		
		algorithm = SecurityAlgorithm.RsaSha256;
		//now signature should not be verified
		try {
			boolean isFalse = CryptoUtil.getCryptoProvider().verifyAsymm(keys.certificate.certificate.getPublicKey(), algorithm, dataToSign, signature); 
			assertEquals(false, isFalse);
		} finally {
			CryptoUtil.setCryptoProvider(null);
			tearDown();
		}
		
	}
	
}
