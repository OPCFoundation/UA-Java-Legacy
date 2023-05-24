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

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;

import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.transport.security.CryptoProvider;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm.AlgorithmType;
import org.opcfoundation.ua.transport.security.SecurityConfiguration;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is class contains Cryptographic utilities
 *
 * http://www.ietf.org/rfc/rfc2437.txt
 */
public class CryptoUtil {

	/**
	 * Security failures are logged with INFO level.
	 * Security info are printed with DEBUG level. Unexpected errors are logged
	 * with ERROR level.
	 */
	static Logger LOGGER = LoggerFactory.getLogger(CryptoUtil.class);

	private final static SecureRandom random;

	static {
		// Load Bouncy Castle
		try {
			// Initialize the random number generator
			// If not done, it will self-seed, which in some Linux systems can
			// take a long while
			LOGGER.debug("CryptoUtil init");
			random = SecureRandom.getInstance("SHA1PRNG");
			LOGGER.debug("CryptoUtil init: random={}", random);
			random.setSeed(System.currentTimeMillis());
		} catch (NoSuchAlgorithmException e) {
			throw new Error(e);
		}
	}

	/** Hex chars for makeHexString-method **/
	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

	private static final String SC_PROVIDER_NAME = "org.opcfoundation.ua.transport.security.ScCryptoProvider";
	private static final String BC_PROVIDER_NAME = "org.opcfoundation.ua.transport.security.BcCryptoProvider";
	
	private volatile static CryptoProvider cryptoProvider;

	private volatile static String securityProviderName;

	/**
	 * Convenience method for {@link CryptoProvider#encryptAsymm}. Deprecated: Use
	 * {@link #encryptAsymm} instead.
	 *
	 * @param input an array of byte.
	 * @param key a {@link java.security.Key} object.
	 * @param algorithm a {@link org.opcfoundation.ua.transport.security.SecurityAlgorithm} object.
	 * @throws java.security.InvalidKeyException if any.
	 * @throws javax.crypto.IllegalBlockSizeException if any.
	 * @throws javax.crypto.BadPaddingException if any.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws javax.crypto.NoSuchPaddingException if any.
	 * @return an array of byte.
	 */
	@Deprecated
	public static byte[] asymmEncrypt(byte[] input, Key key,
			SecurityAlgorithm algorithm) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			ServiceResultException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		return encryptAsymm(input, (PublicKey) key, algorithm);
	}

	/**
	 * <p>base64Decode.</p>
	 *
	 * @param string a {@link java.lang.String} object.
	 * @return an array of byte.
	 */
	public static byte[] base64Decode(String string) {
		return getCryptoProvider().base64Decode(string);
	}
	
	/**
	 * Overloaded base64Decode Function to accept byte array
	 * @param bytes the bytes to decode
	 * @return an array of byte
	 */
	public static byte[] base64Decode(byte[] bytes) {
		return getCryptoProvider().base64Decode(bytes);
	}

	/**
	 * <p>base64Encode a byte array to string</p>
	 *
	 * @param bytes the array of byte to convert.
	 * @return a {@link java.lang.String} representing the byte array in base64 encoded string. 
	 */
	public static String base64Encode(byte[] bytes) {
		return getCryptoProvider().base64Encode(bytes);
	}
	
	/**
	 * Create Message Authentication Code (MAC)
	 *
	 * @param algorithm
	 *            encryption algorithm
	 * @param secret an array of byte.
	 * @return MAC
	 * @throws org.opcfoundation.ua.common.ServiceResultException
	 *             Bad_SecurityPolicyRejected algorithm not supported
	 */
	public static Mac createMac(SecurityAlgorithm algorithm, byte[] secret)
			throws ServiceResultException {
		return getCryptoProvider().createMac(algorithm, secret);
	}

	/**
	 * Create a non-repeatable set of bytes.
	 *
	 * @param bytes
	 *            number of byte
	 * @return nonce
	 */
	public static ByteString createNonce(int bytes) {
		LOGGER.debug("createNonce: bytes={}", bytes);
		byte[] nonce = new byte[bytes];
		random.nextBytes(nonce);
		return ByteString.valueOf(nonce);
	}

	/**
	 * <p>createNonce.</p>
	 *
	 * @param algorithm a {@link org.opcfoundation.ua.transport.security.SecurityAlgorithm} object.
	 * @return an array of byte.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 * @deprecated use {@link #createNonce(int)} and get the size from {@link SecurityPolicy#getSecureChannelNonceLength()} directly
	 */
	@Deprecated
	public static ByteString createNonce(SecurityAlgorithm algorithm)
			throws ServiceResultException {
		return createNonce(getNonceLength(algorithm));
	}

	/**
	 * Convenience method for
	 * {@link CryptoProvider#decryptAsymm(PrivateKey, SecurityAlgorithm, byte[], byte[], int)}
	 * Possible to use only SecurityConfiguration instead of specifying
	 * SecurityAlgorithm explicitly.
	 *
	 * @param decryptingKey a {@link java.security.PrivateKey} object.
	 * @param profile a {@link org.opcfoundation.ua.transport.security.SecurityConfiguration} object.
	 * @param dataToDecrypt an array of byte.
	 * @param output output
	 * @param outputOffset output offset
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public static void decryptAsymm(PrivateKey decryptingKey,
			SecurityConfiguration profile, byte[] dataToDecrypt, byte[] output,
			int outputOffset) throws ServiceResultException {
		
		CryptoUtil.getCryptoProvider().decryptAsymm(decryptingKey,
				profile.getSecurityPolicy().getAsymmetricEncryptionAlgorithm(),
				dataToDecrypt, output, outputOffset);
	}

	/**
	 * Convenience method for {@link CryptoProvider#encryptAsymm}.
	 *
	 * @param input an array of byte.
	 * @param key a {@link java.security.PublicKey} object.
	 * @param algorithm a {@link org.opcfoundation.ua.transport.security.SecurityAlgorithm} object.
	 * @throws java.security.InvalidKeyException if any.
	 * @throws javax.crypto.IllegalBlockSizeException if any.
	 * @throws javax.crypto.BadPaddingException if any.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws javax.crypto.NoSuchPaddingException if any.
	 * @return an array of byte.
	 */
	public static byte[] encryptAsymm(byte[] input, PublicKey key,
			SecurityAlgorithm algorithm) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			ServiceResultException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		int outputBlockSize = getCipherBlockSize(algorithm, key);
		byte[] output = new byte[outputBlockSize];
		CryptoUtil.getCryptoProvider().encryptAsymm(key, algorithm, input,
				output, 0);
		return output;
	}

	/**
	 * Convenience method for
	 * {@link CryptoProvider#encryptAsymm(PublicKey, SecurityAlgorithm, byte[], byte[], int)}
	 * Possible to use only Certificate and SecurityConfiguration instead of
	 * specifying PublicKey and SecurityAlgorithm explicitly.
	 *
	 * @param encryptingCertificate
	 *            Certificate which public key will be used during encryption.
	 * @param profile
	 *            Asymmetric encryption algorithm will be taken from this
	 *            SecurityConfiguration
	 * @param dataToEncrypt
	 *            Data to encrypt
	 * @param output output
	 * @param outputOffset output offset
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public static void encryptAsymm(Certificate encryptingCertificate,
			SecurityConfiguration profile, byte[] dataToEncrypt, byte[] output,
			int outputOffset) throws ServiceResultException {
		LOGGER.info("encryptAsymm called.");
		CryptoUtil.getCryptoProvider().encryptAsymm(
				encryptingCertificate.getPublicKey(),
				profile.getSecurityPolicy().getAsymmetricEncryptionAlgorithm(),
				dataToEncrypt, output, outputOffset);
	}

	/**
	 * <p>filterCipherSuiteList.</p>
	 *
	 * @param cipherSuiteSet an array of {@link java.lang.String} objects.
	 * @param cipherSuitePatterns an array of {@link java.lang.String} objects.
	 * @return an array of {@link java.lang.String} objects.
	 */
	public static String[] filterCipherSuiteList(String[] cipherSuiteSet,
			String[] cipherSuitePatterns) {
		List<String> result = new ArrayList<String>(cipherSuiteSet.length);
		Pattern[] patterns = new Pattern[cipherSuitePatterns.length];

		int c = cipherSuitePatterns.length;
		for (int i = 0; i < c; i++)
			patterns[i] = Pattern.compile(cipherSuitePatterns[i]);

		nextCipherSuite: for (String cipherSuite : cipherSuiteSet) {
			for (Pattern p : patterns) {
				Matcher m = p.matcher(cipherSuite);
				if (m.matches()) {
					result.add(cipherSuite);
					continue nextCipherSuite;
				}
			}
		}

		return result.toArray(new String[result.size()]);
	}

	/**
	 * Create signer instance using an algorithm uri.
	 * http://www.ietf.org/rfc/rfc2437.txt Ciphers are defined in PKCS #1: RSA
	 * Cryptography Specifications
	 *
	 * @param algorithm
	 *            UA Specified algorithm
	 * @return Cipher
	 * @throws org.opcfoundation.ua.common.ServiceResultException
	 *             if algorithm is not supported by the stack
	 */
	public static Cipher getAsymmetricCipher(SecurityAlgorithm algorithm)
			throws ServiceResultException {
		if (algorithm == null)
			throw new IllegalArgumentException();

		try {
			// http://www.w3.org/2001/04/xmlenc#rsa-1_5
			if (algorithm.equals(SecurityAlgorithm.Rsa15))
				return Cipher.getInstance("RSA");

			// http://www.w3.org/2001/04/xmlenc#rsa-oaep
			if (algorithm.equals(SecurityAlgorithm.RsaOaep))
				// return
				// Cipher.getInstance("RSA/NONE/OAEPWithSHA1AndMGF1Padding");
				return Cipher.getInstance(
						"RSA/NONE/OAEPWithSHA1AndMGF1Padding",
						CryptoUtil.getSecurityProviderName());

		} catch (NoSuchAlgorithmException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		} catch (NoSuchPaddingException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		} catch (NoSuchProviderException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}
		throw new ServiceResultException(
				StatusCodes.Bad_SecurityPolicyRejected,
				"Unsupported asymmetric signature algorithm: " + algorithm);
	}

	/**
	 * <p>getAsymmInputBlockSize.</p>
	 *
	 * @param algorithm a {@link org.opcfoundation.ua.transport.security.SecurityAlgorithm} object.
	 * @return a int.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public static int getAsymmInputBlockSize(SecurityAlgorithm algorithm)
			throws ServiceResultException {
		// http://www.w3.org/2001/04/xmlenc#rsa-1_5
		if (algorithm.equals(SecurityAlgorithm.Rsa15))
			return 117;

		// http://www.w3.org/2001/04/xmlenc#rsa-oaep
		if (algorithm.equals(SecurityAlgorithm.RsaOaep))
			return 86;

		throw new ServiceResultException(
				StatusCodes.Bad_SecurityPolicyRejected,
				"Unsupported asymmetric signature algorithm: {0}, " + algorithm);
	}

	/**
	 * Get cipher block (=output) size in bytes
	 *
	 * @param algorithm
	 *            algorithm
	 * @param key
	 *            Optional, required for asymmetric encryption algorithms
	 * @return cipher block size
	 * @throws org.opcfoundation.ua.common.ServiceResultException
	 *             Bad_SecurityPolicyRejected algorithm not supported
	 */
	public static int getCipherBlockSize(SecurityAlgorithm algorithm, Key key)
			throws ServiceResultException {
		// No security
		if (algorithm == null)
			return 1;

		// Symmetric encryption
		AlgorithmType type = algorithm.getType();
		if (type.equals(SecurityAlgorithm.AlgorithmType.SymmetricEncryption))
			return 16; // 128 bits fixed for AES independent of the key size
		if (type.equals(SecurityAlgorithm.AlgorithmType.AsymmetricSignature))
			return algorithm.getKeySize() / 8;

		// Asymmetric encryption
		if (type.equals(SecurityAlgorithm.AlgorithmType.AsymmetricEncryption)) {
			if (key instanceof RSAPublicKey)
				return ((RSAPublicKey) key).getModulus().bitLength() / 8;

			if (key instanceof RSAPrivateKey)
				return ((RSAPrivateKey) key).getModulus().bitLength() / 8;
		}

		// Asymmetric signature
		// if (algorithm.equals(SecurityAlgorithm.RsaSha1)) {
		// return 160/8;
		// }
		// if (algorithm.equals(SecurityAlgorithm.RsaSha256)) {
		// return 256/8;
		// }

		throw new ServiceResultException(
				StatusCodes.Bad_SecurityPolicyRejected, algorithm.getUri());
	}

	/**
	 * Create an intersection of two lists of cipher suite lists
	 *
	 * @param cipherSuiteSet1
	 *            enabled cipher suites
	 * @param cipherSuiteSet2
	 *            filter list
	 * @param omitProtocol
	 *            if true the first 3 characters are ignored in compare
	 * @return an intersection of suites
	 */
	public static String[] getCipherSuiteIntersection(String[] cipherSuiteSet1,
			String[] cipherSuiteSet2, boolean omitProtocol) {
		List<String> result = new ArrayList<String>(Math.max(
				cipherSuiteSet1.length, cipherSuiteSet2.length));
		Set<String> set = new TreeSet<String>();
		for (String cs : cipherSuiteSet2)
			set.add(omitProtocol ? cs.substring(3) : cs);
		for (String cs : cipherSuiteSet1) {
			if (set.contains(omitProtocol ? cs.substring(3) : cs))
				result.add(cs);
		}
		return result.toArray(new String[result.size()]);
	}

	/**
	 * Returns {@link CryptoProvider} previously set with {@link #setCryptoProvider(CryptoProvider)}. 
	 * If it is not set, tries to load either Bouncy or SpongyCastle based on the return value of {@link #getSecurityProviderName()}.
	 * Throws {@link RuntimeException} if cannot be loaded.
	 */
	public static CryptoProvider getCryptoProvider() {
		if (cryptoProvider == null) {
			if ("SC".equals(getSecurityProviderName())) {
				try {
					cryptoProvider = (CryptoProvider) Class.forName(SC_PROVIDER_NAME).newInstance();
				} catch (Exception e) {
					throw new RuntimeException("Cannot init "+SC_PROVIDER_NAME, e);
				}
			} else if ("BC".equals(getSecurityProviderName())) {
				try {
					cryptoProvider = (CryptoProvider) Class.forName(BC_PROVIDER_NAME).newInstance();
				} catch (Exception e) {
					throw new RuntimeException("Cannot init "+BC_PROVIDER_NAME, e);
				}
			} else {
				throw new RuntimeException("NO CRYPTO PROVIDER AVAILABLE!");
			}
		}
		return cryptoProvider;
	}

	/**
	 * Returns the length of the nonce to be used with an asymmetric or
	 * symmetric encryption algorithm.
	 * <p>
	 * For symmetric algorithms, returns the algorithm key size (in bytes). For
	 * asymmetric algorithms, returns 32.
	 *
	 * @param algorithm
	 *            encryption algorithm or null (=no encryption)
	 * @return the length of the nonce in bytes
	 * @throws org.opcfoundation.ua.common.ServiceResultException
	 *             Bad_SecurityPolicyRejected, if the algorithm is not supported
	 * @deprecated use {@link SecurityPolicy#getSecureChannelNonceLength()} directly instead
	 */
	@Deprecated
	public static int getNonceLength(SecurityAlgorithm algorithm)
			throws ServiceResultException {
		if (algorithm == null)
			return 0;
		if (algorithm.equals(SecurityAlgorithm.Rsa15))
			return 32;
		if (algorithm.equals(SecurityAlgorithm.RsaOaep))
			return 32;
		if (SecurityAlgorithm.AlgorithmType.SymmetricEncryption == algorithm
				.getType())
			return algorithm.getKeySize() / 8;
		LOGGER.error("getNonceLength: Unsupported algorithm={}", algorithm);
		throw new ServiceResultException(
				StatusCodes.Bad_SecurityPolicyRejected, algorithm.getUri());
	}

	/**
	 * Get plain text block (=input) size in bytes
	 *
	 * @param securityAlgorithm
	 *            algorithm
	 * @param key
	 *            Optional, required for asymmetric encryption algorithms
	 * @return cipher block size
	 * @throws org.opcfoundation.ua.common.ServiceResultException
	 *             Bad_SecurityPolicyRejected algorithm not supported
	 */
	public static int getPlainTextBlockSize(
			SecurityAlgorithm securityAlgorithm, Key key)
			throws ServiceResultException {
		// No security
		if (securityAlgorithm == null)
			return 1;

		//https://crypto.stackexchange.com/questions/42097/what-is-the-maximum-size-of-the-plaintext-message-for-rsa-oaep
		final int n;
		switch (securityAlgorithm) {
			case Rsa15 :
				n = 11;
				break;
			case RsaOaep:
				n = 42;
				break;
			case RsaOaep256:
				n = 66;
				break;
			default :
				throw new ServiceResultException(
						StatusCodes.Bad_SecurityPolicyRejected,
						securityAlgorithm.getUri());
		}
		
		try {
			return ((RSAPublicKey) key).getModulus().bitLength() / 8 - n;
		}catch(ClassCastException e) {
			LOGGER.error("key is not instance of RSAPublicKey", e);
			throw new ServiceResultException(
					StatusCodes.Bad_SecurityPolicyRejected,
					securityAlgorithm.getUri());
		}
	}

	/**
	 * <p>Getter for the field <code>random</code>.</p>
	 *
	 * @return the random
	 */
	public static SecureRandom getRandom() {
		return random;
	}

	/**
	 * The Preferred Security Provider name. If not set via {@link #setSecurityProviderName(String)}, 
	 * will return 'SC' on android and 'BC' otherwise. Otherwise returns the set String.
	 */
	public static String getSecurityProviderName() {
		if (securityProviderName == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Providers={}",
						Arrays.toString(Security.getProviders()));
			}
			boolean isAndroid = System.getProperty("java.vendor")
					.toLowerCase().contains("android");
			if (isAndroid) {
				securityProviderName = "SC";
			} else{
				securityProviderName = "BC";
			}
		}
		return securityProviderName;
	}

	/**
	 * Gets a suitable jce provider name for the given situation.
	 */
	public static String getSecurityProviderName(Class<?> clazz) {
		return getCryptoProvider().getSecurityProviderName(clazz);
	}

	/**
	 * Get signature size in bytes
	 *
	 * @param signatureAlgorithm a {@link org.opcfoundation.ua.transport.security.SecurityAlgorithm} object.
	 * @param key a {@link java.security.Key} object.
	 * @return signature size in bytes
	 * @throws org.opcfoundation.ua.common.ServiceResultException
	 *             Bad_SecurityPolicyRejected algorithm not supported
	 */
	public static int getSignatureSize(SecurityAlgorithm signatureAlgorithm,
			Key key) throws ServiceResultException {
		if (signatureAlgorithm == null)
			return 0;
		if (signatureAlgorithm.getType().equals(
				SecurityAlgorithm.AlgorithmType.SymmetricSignature))
			return signatureAlgorithm.getKeySize() / 8;
		else {
			if (key instanceof RSAPublicKey)
				return ((RSAPublicKey) key).getModulus().bitLength() / 8;
			if (key instanceof RSAPrivateKey)
				return ((RSAPrivateKey) key).getModulus().bitLength() / 8;
		}
		// if (signatureAlgorithm.equals(SecurityAlgorithm.HmacSha1))
		// return 160 / 8;
		// if (signatureAlgorithm.equals(SecurityAlgorithm.HmacSha256))
		// return 256/8;

		if (signatureAlgorithm.equals(SecurityAlgorithm.RsaSha1)) {
			if (key instanceof RSAPublicKey)
				return ((RSAPublicKey) key).getModulus().bitLength() / 8;
			if (key instanceof RSAPrivateKey)
				return ((RSAPrivateKey) key).getModulus().bitLength() / 8;
		}

		// TODO: OK?
		if (signatureAlgorithm.equals(SecurityAlgorithm.RsaSha256)) {
			if (key instanceof RSAPublicKey)
				return ((RSAPublicKey) key).getModulus().bitLength() / 8;
			if (key instanceof RSAPrivateKey)
				return ((RSAPrivateKey) key).getModulus().bitLength() / 8;
		}

		throw new ServiceResultException(
				StatusCodes.Bad_SecurityPolicyRejected,
				signatureAlgorithm.getUri());
	}

	/**
	 * <p>hexToBytes.</p>
	 *
	 * @param s a {@link java.lang.String} object.
	 * @return an array of byte.
	 */
	public static byte[] hexToBytes(String s) {
		if (s == null)
			return null;
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * Define the preferred CryptoProvider. Usually this is determined
	 * automatically, but you may define the provider that you wish to use
	 * yourself. NOTE! This method will set the {@link #setSecurityProviderName(String)} from the given provider.
	 *
	 * @param cryptoProvider the cryptoProvider to set, if null will clear the previous one.
	 */
	public static void setCryptoProvider(CryptoProvider cryptoProvider) {
		CryptoUtil.cryptoProvider = cryptoProvider;
		CryptoUtil.securityProviderName = cryptoProvider==null ? null : cryptoProvider.getSecurityProviderName(null);
	}

	/**
	 * Define the preferred SecurityProvider. Usually this is determined
	 * automatically if SpongyCastle (on Android) or BouncyCastle is found,
	 * but you may define the provider name that you wish to use yourself.
	 * 
	 * NOTE! Calling this with a different provider name than previously 
	 * set will reset possible calls made to {@link #setCryptoProvider(CryptoProvider)}.
	 * 
	 *
	 * @param securityProviderName
	 *            the securityProviderName to set, e.g. "BC" for
	 *            BouncyCastleProvider
	 */
	public static void setSecurityProviderName(String securityProviderName) {
	  if (!StringUtils.equals(securityProviderName, CryptoUtil.securityProviderName)) {
		CryptoUtil.securityProviderName = securityProviderName;
        CryptoUtil.cryptoProvider = null;
	  }
	}

	/**
	 * <p>signAsymm.</p>
	 *
	 * @param signerKey
	 *            the private key used to sign the data
	 * @param algorithm
	 *            asymmetric signer algorithm, See {@link SecurityAlgorithm}
	 * @param dataToSign
	 *            the data to sign
	 * @return SignatureData
	 * @throws org.opcfoundation.ua.common.ServiceResultException
	 *             if the signing fails. Read the StatusCode and cause for more
	 *             details
	 */
	public static SignatureData signAsymm(PrivateKey signerKey,
			SecurityAlgorithm algorithm, byte[] dataToSign)
			throws ServiceResultException {
		if (algorithm == null)
			return new SignatureData(null, null);
		return new SignatureData(algorithm.getUri(), ByteString.valueOf(getCryptoProvider()
				.signAsymm(signerKey, algorithm, dataToSign)));
	}

	/**
	 * Convenience method for "displaying" a hex-string of a given byte array.
	 * Calls {@link #toHex(byte[], int)} with bytesPerRow=0 (no line breaks)
	 *
	 * @param bytes
	 *            the byte array to "display"
	 * @return a {@link java.lang.String} object.
	 */
	public static String toHex(byte[] bytes) {
		return toHex(bytes, (bytes != null && bytes.length > 64) ? 64 : 0);
	}

	/**
	 * Convenience method for "displaying" a hex-string of a given byte array.
	 * Breaks the string to lines, if bytesPerRow &gt; 0.
	 *
	 * @param bytes
	 *            the byte array to "display"
	 * @param bytesPerRow
	 *            number of bytes to include on a text row. If it is 0, no line
	 *            breaks are added.
	 * @param bytesPerRow
	 *            number of bytes to include on a text row. If it is 0, no line
	 *            breaks are added.
	 * @return a {@link java.lang.String} object.
	 */
	public static String toHex(byte[] bytes, int bytesPerRow) {
		if (bytes == null)
			return "(null)";
		StringBuffer sb = new StringBuffer();
		sb.append("[" + bytes.length + "] 0x");
		int i = 0;
		while (i < bytes.length) {
			if (bytesPerRow > 0 && i % bytesPerRow == 0)
				sb.append(StringUtils.lineSeparator());
			sb.append(HEX_CHARS[(bytes[i] >> 4) & 0x0F]);
			sb.append(HEX_CHARS[bytes[i] & 0x0F]);
			i++;
		}
		return sb.toString();
	}

	/** 
	 * Convert Char Array to Byte Array without String to avoid leaving traces of the intermediate results in the memory.
	 * 
	 * If chars is null, returns an empty byte array.
	 */
	public static byte[] toBytes(char[] chars) {
		  if (chars == null)
		      return new byte[0];
		  CharBuffer charBuffer = CharBuffer.wrap(chars);
		  ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
		  byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
		            byteBuffer.position(), byteBuffer.limit());
		  Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
		  return bytes;
		}	
	
	/**
	 * Verify a signature.
	 *
	 * @param certificate
	 *            the certificate used to verify the signature
	 * @param algorithm
	 *            the signature algorithm
	 * @param data
	 *            data to verify
	 * @param signature
	 *            the signature to verify
	 * @return true if the signature is valid
	 * @throws org.opcfoundation.ua.common.ServiceResultException
	 *             if the verification fails
	 */
	public static boolean verifyAsymm(X509Certificate certificate,
			SecurityAlgorithm algorithm, byte[] data, byte[] signature)
			throws ServiceResultException {
		return getCryptoProvider().verifyAsymm(certificate.getPublicKey(),
				algorithm, data, signature);
	}
	
	/**
	 * Returns a loaded JCE {@link Provider} of the given class. 
	 * Tries to add the provider if it is not already loaded.
	 * Throws {@link IllegalArgumentException} if cannot be done.
	 */
	public static synchronized Provider loadOrInstallProvider(String jceName, String providerName) throws IllegalArgumentException{
		Provider provider = Security.getProvider(jceName);
		if(provider != null) {
			return provider;
		}
		try {
			provider = (Provider) Class.forName(providerName).newInstance();
			Security.addProvider(provider);
			return provider;
		}catch(Exception e) {
			throw new IllegalArgumentException("Cannot add Security Provider class: "+providerName, e);
		}
	}

}
