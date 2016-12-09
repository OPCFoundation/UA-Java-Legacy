/* Copyright (c) 1996-2015, OPC Foundation. All rights reserved.
   The source code in this file is covered under a dual-license scenario:
     - RCL: for OPC Foundation members in good-standing
     - GPL V2: everybody else
   RCL license terms accompanied with this source code. See http://opcfoundation.org/License/RCL/1.00/
   GNU General Public License as published by the Free Software Foundation;
   version 2 of the License are accompanied with this source code. See http://opcfoundation.org/License/GPLv2
   This source code is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
*/

package org.opcfoundation.ua.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.transport.security.BcCertificateProvider;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.CertificateProvider;
import org.opcfoundation.ua.transport.security.PrivKey;
import org.opcfoundation.ua.transport.security.ScCertificateProvider;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.transport.security.SunJceCertificateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A utility class for generating self-signed certificates for UA clients and
 * servers and for using them.
 */
public class CertificateUtils {

	private static final int NAME_URI = 6;
	private static Logger logger = LoggerFactory.getLogger(CertificateUtils.class);

	/**
	 * Sign data
	 *
	 * @param signerKey a {@link java.security.PrivateKey} object.
	 * @param algorithm
	 *            asymmetric signer algorithm, See {@link SecurityAlgorithm}
	 * @param dataToSign an array of byte.
	 * @return signature data
	 * @deprecated Use {@link CryptoUtil#signAsymm(PrivateKey, SecurityAlgorithm, byte[])} instead.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws java.security.SignatureException if any.
	 * @throws java.security.InvalidKeyException if any.
	 */
	public static SignatureData sign(PrivateKey signerKey,
			SecurityAlgorithm algorithm, byte[] dataToSign)
			throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

		try {
			return CryptoUtil.signAsymm(signerKey, algorithm, dataToSign);
		} catch (ServiceResultException e) {
			throw new SignatureException(e);
		}
	}

	/**
	 * Verify a signature.
	 *
	 * @param certificate a {@link java.security.cert.X509Certificate} object.
	 * @param algorithm
	 *            asymmetric signer algorithm, See {@link SecurityAlgorithm}
	 * @param data an array of byte.
	 * @param signature an array of byte.
	 * @return true if verified
	 * @deprecated Use {@link CryptoUtil#verifyAsymm(X509Certificate, SecurityAlgorithm, byte[], byte[])} instead.
	 * @throws java.security.SignatureException if any.
	 * @throws java.security.InvalidKeyException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 */
	public static boolean verify(X509Certificate certificate,
			SecurityAlgorithm algorithm, byte[] data, byte[] signature)
					throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {

		try {
			return CryptoUtil.verifyAsymm(certificate, algorithm, data, signature);
		} catch (ServiceResultException e) {
			throw new SignatureException(e);
		}
	}

	/**
	 * Load X.509 Certificate from an url
	 *
	 * @param url a {@link java.net.URL} object.
	 * @return Certificate
	 * @throws java.io.IOException if any.
	 * @throws java.security.cert.CertificateException
	 *             In case the certificate is not valid
	 */
	public static X509Certificate readX509Certificate(URL url)
			throws IOException, CertificateException {
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();
		try {
			CertificateFactory servercf = CertificateFactory
					.getInstance("X.509");
			return (X509Certificate) servercf.generateCertificate(is);
		} finally {
			is.close();
		}
	}

	/**
	 * Load X.509 Certificate from a file
	 *
	 * @param file a {@link java.io.File} object.
	 * @return Certificate
	 * @throws java.io.IOException if any.
	 * @throws java.security.cert.CertificateException
	 *             In case the certificate is not valid
	 */
	public static X509Certificate readX509Certificate(File file)
			throws IOException, CertificateException {
		return readX509Certificate(file.toURI().toURL());
	}

	/**
	 * Create SHA-1 Thumbprint
	 *
	 * @param data an array of byte.
	 * @return thumbprint
	 */
	public static byte[] createThumbprint(byte[] data) {
		try {
			MessageDigest shadigest = MessageDigest.getInstance("SHA1");
			return shadigest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new Error(e);
		}
	}

	/**
	 * Decode X509 Certificate
	 *
	 * @param encodedCertificate an array of byte.
	 * @return X509 certificate
	 * @throws java.security.cert.CertificateException if any.
	 */
	public static X509Certificate decodeX509Certificate(
			byte[] encodedCertificate) throws CertificateException {
		try {
			if (encodedCertificate == null)
				throw new IllegalArgumentException("null arg");
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			ByteArrayInputStream bais = new ByteArrayInputStream(
					encodedCertificate);
			X509Certificate result = (X509Certificate) cf
					.generateCertificate(bais);
			bais.close();
			return result;
		} catch (IOException e) {
			// ByteArrayInputStream will not throw this
			throw new RuntimeException(e);
		}
	}

	/**
	 * Load private key from a key store
	 *
	 * @param keystoreUrl
	 *            url to key store
	 * @param password
	 *            password to key store
	 * @return private key
	 * @throws java.io.IOException if any.
	 * @throws java.security.cert.CertificateException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws java.security.KeyStoreException if any.
	 * @throws java.security.UnrecoverableKeyException if any.
	 */
	public static RSAPrivateKey loadFromKeyStore(URL keystoreUrl,
			String password) throws IOException, NoSuchAlgorithmException,
			CertificateException, KeyStoreException, UnrecoverableKeyException {
		logger.debug("loadFromKeyStore: keystoreUrl={}", keystoreUrl);
		// Open pfx-certificate
		URLConnection connection = keystoreUrl.openConnection();
		InputStream is = connection.getInputStream();
		try {
			// Open key store and load the key
			if (logger.isDebugEnabled())
				logger.debug("getproviders={}", Arrays.toString(Security.getProviders()));
			KeyStore keyStore;
			try {
				try {
					// Prefer the Sun KeyStore implementation!
					// TODO Check if the new BC works better nowadays
					keyStore = KeyStore.getInstance("PKCS12", "SunJSSE");
				} catch (NoSuchProviderException e) {
					keyStore = KeyStore.getInstance("PKCS12", CryptoUtil.getSecurityProviderName(KeyStore.class));
				}
			} catch (NoSuchProviderException e) {
				keyStore = KeyStore.getInstance("PKCS12");
			}
			logger.debug("loadFromKeyStore: keyStore Provider={}", keyStore.getProvider());
			keyStore.load(is, password == null ? null : password.toCharArray());
			Enumeration<String> aliases = keyStore.aliases();

			Key key = null;
			while (aliases.hasMoreElements()) {
				String a = (String) aliases.nextElement();
				key = keyStore.getKey(a, password == null ? null : password.toCharArray());
			}

			return (RSAPrivateKey) key;
		} finally {
			is.close();
		}
	}

	/**
	 * Save the KeyPair to a Java Key Store.
	 *
	 * @param keyPairToSave a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 * @param storeLocation a {@link java.lang.String} object.
	 * @param alias a {@link java.lang.String} object.
	 * @param storePW a {@link java.lang.String} object.
	 * @param privatePW a {@link java.lang.String} object.
	 * @throws java.security.KeyStoreException if any.
	 * @throws java.io.IOException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws java.security.cert.CertificateException if any.
	 * @return a boolean.
	 */
	public static boolean saveKeyPairToProtectedStore(
			org.opcfoundation.ua.transport.security.KeyPair keyPairToSave,
			String storeLocation, String alias, String storePW, String privatePW)
			throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException {
		KeyStore store = null;

		// initialize and open keystore
		store = KeyStore.getInstance("JKS");
		File keystoreFile = new File(storeLocation);
		FileInputStream in;
		try {
			in = new FileInputStream(keystoreFile);
			try {
				store.load(in, storePW.toCharArray());
			} finally {
				in.close();
			}
		} catch (FileNotFoundException e) {
			store.load(null, null);
		}

		// create certificate chain containing only 1 certificate
		Certificate[] chain = new Certificate[1];
		chain[0] = keyPairToSave.certificate.getCertificate();
		store.setKeyEntry(alias, keyPairToSave.privateKey.getPrivateKey(),
				privatePW.toCharArray(), chain);

		FileOutputStream fOut = new FileOutputStream(storeLocation);
		try {
			store.store(fOut, storePW.toCharArray());
		} finally {
			fOut.close();
		}
		return true;
	}

	/**
	 * Load a KeyPair from a Java Key Store.
	 *
	 * @param storeLocation a {@link java.lang.String} object.
	 * @param alias a {@link java.lang.String} object.
	 * @param storePW a {@link java.lang.String} object.
	 * @param privatePW a {@link java.lang.String} object.
	 * @throws java.security.KeyStoreException if any.
	 * @throws java.io.IOException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws java.security.cert.CertificateException if any.
	 * @throws java.security.UnrecoverableKeyException if any.
	 * @return a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public static org.opcfoundation.ua.transport.security.KeyPair loadKeyPairFromProtectedStore(
			String storeLocation, String alias, String storePW, String privatePW)
			throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, UnrecoverableKeyException {

		KeyStore store = null;

		// initialize and open keystore
		store = KeyStore.getInstance("JKS");
		File keystoreFile = new File(storeLocation);
		FileInputStream in = new FileInputStream(keystoreFile);
		store.load(in, storePW.toCharArray());
		in.close();

		// try to load certificate from store
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);

		// Try to load private key from keystore
		RSAPrivateKey key = (RSAPrivateKey) store.getKey(alias,
				privatePW.toCharArray());

		return new org.opcfoundation.ua.transport.security.KeyPair(new Cert(
				cert), new PrivKey(key));
	}

	/**
	 * Renew a certificate KeyPair using the old keys.
	 *
	 * @param commonName
	 *            - Common Name (CN) for generated certificate
	 * @param organisation
	 *            - Organisation (O) for generated certificate
	 * @param applicationUri
	 *            - Alternative name (one of x509 extensiontype) for generated
	 *            certificate. Must not be null
	 * @param validityTime
	 *            - the time that the certificate is valid (in days)
	 * @param oldKeys
	 *            the old keys to renew
	 * @param issuerKeys
	 *            the optional issuer certificate and private key to use for
	 *            signing the certificate
	 * @param hostNames a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 * @throws java.lang.IllegalStateException if any.
	 * @throws java.security.GeneralSecurityException if any.
	 * @return a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public static org.opcfoundation.ua.transport.security.KeyPair renewApplicationInstanceCertificate(
			String commonName, String organisation, String applicationUri,
			int validityTime,
			org.opcfoundation.ua.transport.security.KeyPair oldKeys,
			org.opcfoundation.ua.transport.security.KeyPair issuerKeys, String... hostNames) throws IOException, IllegalStateException,
			GeneralSecurityException {
		if (applicationUri == null)
			throw new NullPointerException("applicationUri must not be null");

		// use the same keypair for the new certificate
		PublicKey certPubKey = oldKeys.getCertificate().getCertificate()
				.getPublicKey();
		RSAPrivateKey certPrivKey = oldKeys.getPrivateKey().getPrivateKey();

		X509Certificate cert = generateCertificate(
				"CN=" + commonName
						+ (organisation == null ? "" : ", O=" + organisation), // +", C="+System.getProperty("user.country"),
				validityTime, applicationUri, new KeyPair(certPubKey, certPrivKey),
				issuerKeys, hostNames);


		// Encapsulate Certificate and private key to CertificateKeyPair
		Cert certificate = new Cert(cert);
		org.opcfoundation.ua.transport.security.PrivKey UAkey = new org.opcfoundation.ua.transport.security.PrivKey(
				(RSAPrivateKey) certPrivKey);
		return new org.opcfoundation.ua.transport.security.KeyPair(certificate,
				UAkey);
	}

	/**
	 * Renew a certificate KeyPair. Sign with the own key.
	 *
	 * @param commonName
	 *            - Common Name (CN) for generated certificate
	 * @param organisation
	 *            - Organisation (O) for generated certificate
	 * @param applicationUri
	 *            - Alternative name (one of x509 extensiontype) for generated
	 *            certificate. Must not be null
	 * @param validityTime
	 *            - the time that the certificate is valid (in days)
	 * @param oldKeys
	 *            the old keys to renew
	 * @param hostNames a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 * @throws java.lang.IllegalStateException if any.
	 * @throws java.security.GeneralSecurityException if any.
	 * @return a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public static org.opcfoundation.ua.transport.security.KeyPair renewApplicationInstanceCertificate(
			String commonName, String organisation, String applicationUri,
			int validityTime,
			org.opcfoundation.ua.transport.security.KeyPair oldKeys,
			String... hostNames) throws IOException, IllegalStateException,
			GeneralSecurityException {
		return renewApplicationInstanceCertificate(commonName, organisation, applicationUri, validityTime, oldKeys, null, hostNames);
	}

	private static int keySize = 2048;
	private static String certificateSignatureAlgorithm = "SHA256WithRSA";

	/**
	 * Define the algorithm to use for certificate signatures.
	 * <p>
	 * The OPC UA specification defines that the algorithm should be (at least)
	 * "SHA1WithRSA" for application instance certificates used for security
	 * policies Basic128Rsa15 and Basic256. For Basic256Sha256 it should be
	 * "SHA256WithRSA".
	 * <p>
	 * Default: "SHA256WithRSA"
	 *
	 * @param certificateSignatureAlgorithm
	 *            the certificateSignatureAlgorithm to set
	 */
	public static void setCertificateSignatureAlgorithm(
			String certificateSignatureAlgorithm) {
		CertificateUtils.certificateSignatureAlgorithm = certificateSignatureAlgorithm;
	}

	/**
	 * <p>Getter for the field <code>keySize</code>.</p>
	 *
	 * @return the key size for new certificates
	 */
	public static int getKeySize() {
		return keySize;
	}

	/**
	 * Define the key size for the certificates.
	 *
	 * Default: 2048
	 *
	 * @param keySize
	 *            size of the certificates. Good values are multiples of 1024,2048(,3072) and 4096
	 * @throws java.lang.IllegalArgumentException
	 *             if the value is not accepted
	 */
	public static void setKeySize(int keySize) {
		if (keySize < 1024 || keySize % 1024 != 0 || keySize > 4096)
			throw new IllegalArgumentException(
					"KeySize must be 1024, 2048, 3072 or 4096");
		CertificateUtils.keySize = keySize;
	}

	/**
	 * Create a X.509 V3 Certificate.
	 * 
	 * @param dn
	 *            the X.509 Distinguished Name, eg "CN=Test, L=London, C=GB"
	 * @param days
	 *            how many days from now the Certificate is valid for
	 * @param applicationUri
	 *            the application URI as defined in the ApplicationDescription
	 * @param keys
	 *            the public and private key to sign
	 * @param issuerKeys
	 *            the optional issuer certificate and private key to use for
	 *            signing the certificate. If null a self-signed certificate is generated
	 * @param hostNames
	 *            host names to add to the Subject Alternative Names field
	 * @param algorithm
	 *            the signing algorithm, eg "SHA1withRSA"
	 * @return the generated certificate
	 * @throws IOException
	 * 
	 * @throws GeneralSecurityException
	 */
	private static X509Certificate generateCertificate(String dn, int days,
			String applicationUri, KeyPair keys,
			org.opcfoundation.ua.transport.security.KeyPair issuerKeys, String... hostNames) throws GeneralSecurityException, IOException {

		PrivateKey privkey = keys.getPrivate();
		PublicKey publicKey = keys.getPublic();

		return generateCertificate(dn, days, applicationUri, publicKey, privkey,
				issuerKeys, hostNames);
	}

	/**
	 * Create a X.509 Certificate signed with the provided private key.
	 * @param dn
	 *            the X.509 Distinguished Name, eg "CN=Test, L=London, C=GB"
	 * @param days
	 *            how many days from now the Certificate is valid for
	 * @param applicationUri
	 *            the application URI as defined in the ApplicationDescription
	 * @param publicKey
	 * @param privateKey
	 * @param issuerKeys
	 *            the optional issuer certificate and private key to use for
	 *            signing the certificate. If null a self-signed certificate is generated
	 * @param hostNames
	 *            host names to add to the Subject Alternative Names field
	 * @return the generated certificate
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private static X509Certificate generateCertificate(String dn, int days,
			String applicationUri, PublicKey publicKey, PrivateKey privateKey,
			org.opcfoundation.ua.transport.security.KeyPair issuerKeys, String... hostNames) throws IOException,
			GeneralSecurityException {
		Date startDate = getCertificateStartDate();
		Date endDate = getCertificateEndDate(days);
		BigInteger sn = BigInteger.valueOf(System.currentTimeMillis());

		return getCertificateProvider().generateCertificate(dn, publicKey, privateKey, issuerKeys,
				startDate, endDate, sn, applicationUri, hostNames);
	}

	private static CertificateProvider certificateProvider;
	
	/**
	 * <p>Getter for the field <code>certificateProvider</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.CertificateProvider} object.
	 */
	public static CertificateProvider getCertificateProvider() {
		if (certificateProvider == null) {
			if ("SC".equals(CryptoUtil.getSecurityProviderName())) {
				certificateProvider = new ScCertificateProvider();
			} else if ("BC".equals(CryptoUtil.getSecurityProviderName())) {
				certificateProvider = new BcCertificateProvider();
			} else if ("SunJCE".equals(CryptoUtil.getSecurityProviderName())) {
				certificateProvider = new SunJceCertificateProvider();
			} else {
				throw new RuntimeException("NO CRYPTO PROVIDER AVAILABLE!");
			}
		}
		return certificateProvider;
	}
	
	/**
	 * Define the preferred CertificateProvider. Usually this is determined
	 * automatically, but you may define the provider that you wish to use
	 * yourself.
	 *
	 * @param certificateProvider
	 *            the certificateProvider to set
	 */
	public static void setCertificateProvider(CertificateProvider certificateProvider) {
		CertificateUtils.certificateProvider = certificateProvider;
	}

	private static Date getCertificateStartDate() {
		return new Date(System.currentTimeMillis() - 1000 * 60 * 60);
	}

	private static Date getCertificateEndDate(int days) {
		Calendar expiryTime = Calendar.getInstance();
		expiryTime.add(Calendar.DAY_OF_YEAR, days);
		return expiryTime.getTime();
	}


	/**
	 * <p>createApplicationInstanceCertificate.</p>
	 *
	 * @param commonName
	 *            - Common Name (CN) for generated certificate
	 * @param organisation
	 *            - Organisation (O) for generated certificate
	 * @param applicationUri
	 *            - Alternative name (one of x509 extensiontype) for generated
	 *            certificate. Must not be null
	 * @param validityTime
	 *            - the time that the certificate is valid (in days)
	 * @param hostNames
	 *            - alternate host names or IP addresses to add to
	 *            SubjectAlternativeNames
	 * @throws java.io.IOException if any.
	 * @throws java.security.GeneralSecurityException if any.
	 * @return a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public static org.opcfoundation.ua.transport.security.KeyPair createApplicationInstanceCertificate(
			String commonName, String organisation, String applicationUri,
			int validityTime, String... hostNames) throws IOException,
			GeneralSecurityException {
		return createApplicationInstanceCertificate(commonName, organisation,
				applicationUri, validityTime, null, hostNames);
	}
	
	/**
	 * <p>createApplicationInstanceCertificate.</p>
	 *
	 * @param commonName
	 *            - Common Name (CN) for the generated certificate
	 * @param organisation
	 *            - Organisation (O) for the generated certificate
	 * @param applicationUri
	 *            - Alternative name (one of x509 extensiontype) for generated
	 *            certificate. Must not be null
	 * @param validityTime
	 *            - the time that the certificate is valid (in days)
	 * @param issuerKeys
	 *            the optional issuer certificate and private key to use for
	 *            signing the certificate. If null a self-signed certificate is generated
	 * @param hostNames
	 *            - alternate host names or IP addresses to add to
	 *            SubjectAlternativeNames
	 * @throws java.io.IOException if any.
	 * @throws java.security.GeneralSecurityException if any.
	 * @return a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public static org.opcfoundation.ua.transport.security.KeyPair createApplicationInstanceCertificate(
			String commonName, String organisation, String applicationUri,
			int validityTime, org.opcfoundation.ua.transport.security.KeyPair issuerKeys, String... hostNames) throws IOException,
			GeneralSecurityException {
		if (applicationUri == null)
			throw new NullPointerException("applicationUri must not be null");
		// Add provider for generator
		if (logger.isDebugEnabled())
			logger.debug("createApplicationInstanceCertificate: getProviders={}", Arrays.toString(Security.getProviders()));

		KeyPair keyPair = generateKeyPair();
		
		// The fields appear in reverse order in the final certificate!
		String name = 
				"CN=" + commonName
				+ (organisation == null ? "" : ", O=" + organisation)
				+ ((hostNames == null || hostNames.length == 0) ? "" : ", DC=" + hostNames[0]); 

		X509Certificate cert;
		cert = generateCertificate(
					name,
					validityTime, applicationUri, keyPair, issuerKeys,
					hostNames);

		
		// Encapsulate Certificate and private key to CertificateKeyPair
		return toKeyPair(cert, keyPair.getPrivate());
	}

	/**
	 * <p>toKeyPair.</p>
	 *
	 * @param cert a {@link java.security.cert.X509Certificate} object.
	 * @param privateKey a {@link java.security.PrivateKey} object.
	 * @return a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 * @throws java.security.cert.CertificateEncodingException if any.
	 */
	public static org.opcfoundation.ua.transport.security.KeyPair toKeyPair(
			X509Certificate cert, PrivateKey privateKey)
			throws CertificateEncodingException {
		Cert certificate = new Cert(cert);
		org.opcfoundation.ua.transport.security.PrivKey UAkey = new org.opcfoundation.ua.transport.security.PrivKey(
				(RSAPrivateKey) privateKey);
		return new org.opcfoundation.ua.transport.security.KeyPair(certificate,
				UAkey);
	}

	/**
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(getKeySize());
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return keyPair;
	}

	/**
	 * Create a new issuer certificate that can be used to issue certificates built
	 * with
	 * {@link #createApplicationInstanceCertificate(String, String, String, int, String...)}
	 * or
	 * {@link #createHttpsCertificate(String, String, int, org.opcfoundation.ua.transport.security.KeyPair)}
	 *
	 * @param commonName
	 *            The common name to use for the Subject of the certificate (the
	 *            name will be prepended with "CN=" if it does not start with it
	 *            already)
	 * @param days
	 *            - the time that the certificate is valid (in days)
	 * @param issuerCert
	 *            - The certificate of the issuer that should sign the
	 *            certificate. If null, a self-signed certificate is created
	 * @return the new certificate and private key
	 * @throws java.security.GeneralSecurityException if any.
	 * @throws java.io.IOException if any.
	 */
	public static org.opcfoundation.ua.transport.security.KeyPair createIssuerCertificate(
			String commonName, int days,
			org.opcfoundation.ua.transport.security.KeyPair issuerCert) throws GeneralSecurityException, IOException {
		KeyPair keyPair = generateKeyPair();
		Date startDate = getCertificateStartDate();
		Date endDate = getCertificateEndDate(days);
		BigInteger serialNr = BigInteger.valueOf(System.currentTimeMillis());
		if (!commonName.startsWith("CN="))
			commonName = "CN="+commonName;
		X509Certificate cert = getCertificateProvider().generateIssuerCert(keyPair.getPublic(), keyPair.getPrivate(),
				issuerCert, commonName, serialNr, startDate, endDate);
		return toKeyPair(cert, keyPair.getPrivate());
	}
	
	/**
	 * Create a new certificate that can be used with the HTTPS protocol. The
	 * certificate should be issued with a CA certificate, especially for the
	 * server applications, to ensure interoperability with other client
	 * applications.
	 *
	 * @param hostName
	 *            - HostName of the computer in which the application is
	 *            running: used to initialize the Subject field of the
	 *            certificate. The client applications may validate this field
	 *            of the server certificate, so it should match the hostName
	 *            used in the ApplicationUri of the application.
	 * @param applicationUri
	 *            - The ApplicationUri corresponding to the respective field of
	 *            the ApplicationDescription of the application for which the
	 *            certificate is created
	 * @param days
	 *            - the time that the certificate is valid (in days)
	 * @param issuerCert
	 *            - The certificate of the issuer that should sign the
	 *            certificate. If null, a self-signed certificate is created
	 * @return the new certificate and private key
	 * @throws java.io.IOException if any.
	 * @throws java.security.GeneralSecurityException if any.
	 */
	public static org.opcfoundation.ua.transport.security.KeyPair createHttpsCertificate(
			String hostName, String applicationUri,
			int days,  org.opcfoundation.ua.transport.security.KeyPair issuerCert) throws IOException,
			GeneralSecurityException {
		if (applicationUri == null)
			throw new NullPointerException("applicationUri must not be null");
		// Add provider for generator
		if (logger.isDebugEnabled())
			logger.debug("createApplicationInstanceCertificate: getProviders={}", Arrays.toString(Security.getProviders()));

		KeyPair keyPair = generateKeyPair();
		
		X509Certificate cert;
		cert = generateCertificate("CN="+hostName, days, applicationUri,
				keyPair.getPublic(), keyPair.getPrivate(),
				issuerCert);
		
		return toKeyPair(cert, keyPair.getPrivate());
	}

	/**
	 * <p>writeToPem.</p>
	 *
	 * @param key a {@link java.security.cert.X509Certificate} object.
	 * @param file a {@link java.io.File} object.
	 * @throws java.io.IOException if any.
	 */
	public static void writeToPem(X509Certificate key, File file)
			throws IOException {
		getCertificateProvider().writeToPem(key, file, null, null);
	}

	/**
	 * Save the private key to a jks or pfx (PKCS12)-keystore.
	 *
	 * @param storeLocation
	 *            save location of the keystore
	 * @param alias
	 *            alias used for the keypair
	 * @param privateKeyPassword
	 *            password to secure the private key, cannot be null for
	 *            keyStoreType "JKS"
	 * @param keyStorePassword
	 *            password to secure the key store
	 * @param keyStoreType
	 *            type of the key store, "JKS" and "PKCS12" supported
	 * @param privateKeyPassword
	 *            password to secure the private key, cannot be null for
	 *            keyStoreType "JKS"
	 * @param privateKey a {@link java.security.PrivateKey} object.
	 * @throws java.io.IOException
	 *             if storeLocation is not available
	 * @throws java.security.NoSuchProviderException
	 *             The required security Provider not found
	 * @throws java.security.KeyStoreException
	 *             keystore failed
	 * @throws java.security.cert.CertificateException
	 *             certificate problem
	 * @throws java.security.NoSuchAlgorithmException
	 *             cryptographic algorithm not found
	 * @param certificate a {@link java.security.cert.Certificate} object.
	 */
	public static void saveToProtectedStore(PrivateKey privateKey,
			Certificate certificate, File storeLocation, String alias,
			String privateKeyPassword, String keyStorePassword,
			String keyStoreType) throws IOException, KeyStoreException,
			NoSuchProviderException, NoSuchAlgorithmException,
			CertificateException {
		KeyStore store = null;
		// initialize and open keystore

		if (keyStoreType.equals("PKCS12")) {
			// For some reason, this does not work with the SunJSSE provider
			store = KeyStore.getInstance(keyStoreType,
					CryptoUtil.getSecurityProviderName(KeyStore.class));
		} else
			store = KeyStore.getInstance(keyStoreType);

		store.load(null, null);

		// create certificate chain containing only 1 certificate
		Certificate[] chain = new Certificate[1];
		chain[0] = certificate;
		if (privateKeyPassword != null)
			store.setKeyEntry(alias, privateKey,
					privateKeyPassword.toCharArray(), chain);
		else
			store.setKeyEntry(alias, privateKey, null, chain);

		FileOutputStream fOut = new FileOutputStream(storeLocation);
		try {
			store.store(fOut, keyStorePassword == null ? null
					: keyStorePassword.toCharArray());
		} finally {
			fOut.close();
		}
	}

	/**
	 * <p>Getter for the field <code>certificateSignatureAlgorithm</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String getCertificateSignatureAlgorithm() {
		return certificateSignatureAlgorithm;
	}
    
	/*
	 * This code is copied from the openJDK, which checks the AltNames correctly
	 * and raises exceptions. We try to enable reading the UriName field without
	 * raising exceptions, even if the format is invalid.
	 */
	/**
	 * <p>getSubjectAlternativeNames.</p>
	 *
	 * @param cert a {@link java.security.cert.X509Certificate} object.
	 * @return a {@link java.util.Collection} object.
	 * @throws java.security.cert.CertificateParsingException if any.
	 */
	protected static Collection<List<?>> getSubjectAlternativeNames(
			X509Certificate cert) throws CertificateParsingException {
		return getCertificateProvider().getSubjectAlternativeNames(cert);
	}

	
	/**
	 * <p>getApplicationUriOfCertificate.</p>
	 *
	 * @param certificate a {@link java.security.cert.X509Certificate} object.
	 * @throws java.security.cert.CertificateParsingException if any.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getApplicationUriOfCertificate(
			final X509Certificate certificate)
			throws CertificateParsingException {
		final Collection<List<?>> subjectAlternativeNames = getSubjectAlternativeNames(certificate);
		if (subjectAlternativeNames != null)
			for (List<?> altNames : subjectAlternativeNames) {
				int tagNo = (Integer) altNames.get(0);
				if (tagNo == NAME_URI) {
					String name = (String) altNames.get(1);
					return name;
				}
			}
		return "";
	}
	
	/**
	 * <p>getApplicationUriOfCertificate.</p>
	 *
	 * @param certificate a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 * @throws java.security.cert.CertificateParsingException if any.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getApplicationUriOfCertificate(Cert certificate)
			throws CertificateParsingException {
		return getApplicationUriOfCertificate(certificate.getCertificate());
	}	


}
