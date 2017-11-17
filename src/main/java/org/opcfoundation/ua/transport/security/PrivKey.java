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

package org.opcfoundation.ua.transport.security;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.utils.BouncyCastleUtils;
import org.opcfoundation.ua.utils.CertificateUtils;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.FileUtil;
import org.opcfoundation.ua.utils.StringUtils;


/**
 * Valid and encodeable private key.
 * Wrapper to {@link java.security.PrivateKey}
 */
public class PrivKey {

	private static final String END_RSA_PRIVATE_KEY = StringUtils.lineSeparator() + "-----END RSA PRIVATE KEY-----";
	private static final String BEGIN_RSA_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----" + StringUtils.lineSeparator();
	private static final String END_PRIVATE_KEY_REGEX = "-----END .*PRIVATE KEY-----";
	private static final String BEGIN_PRIVATE_KEY_REGEX = "-----BEGIN .*PRIVATE KEY-----";

	public final RSAPrivateKey privateKey;
	private static Logger logger = LoggerFactory.getLogger(PrivKey.class);
	/**
	 * Load private key from a PKCS12 key store
	 *
	 * @param keystoreUrl url to key store
	 * @param password password to key store
	 * @return private key
	 * @throws java.io.IOException if any.
	 * @throws java.security.KeyStoreException if any.
	 * @throws java.security.cert.CertificateException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws java.security.UnrecoverableKeyException if any.
	 */
	public static PrivKey loadFromKeyStore(URL keystoreUrl, String password) throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, KeyStoreException
	{
		RSAPrivateKey key = CertificateUtils.loadFromKeyStore(keystoreUrl, password);
		return new PrivKey(key);
	}	

	/**
	 * Load private key from a PEM encoded file
	 *
	 * @param file a {@link java.io.File} object.
	 * @param password a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws java.security.spec.InvalidKeySpecException if any.
	 * @throws javax.crypto.NoSuchPaddingException if any.
	 * @throws java.security.InvalidAlgorithmParameterException if any.
	 * @throws java.security.InvalidKeyException if any.
	 * @throws javax.crypto.BadPaddingException if any.
	 * @throws javax.crypto.IllegalBlockSizeException if any.
	 * @throws java.security.spec.InvalidParameterSpecException if any.
	 * @return a {@link org.opcfoundation.ua.transport.security.PrivKey} object.
	 */
	public static PrivKey load(File file, final String password)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException  {
		if (file.length() < 3)
			throw new IllegalArgumentException("file is not a valid private key (too short file)");
		byte[] keyData = FileUtil.readFile(file);
		return load(keyData, password);
	}
		
	/**
	 * <p>load.</p>
	 *
	 * @param is a {@link java.io.InputStream} object.
	 * @param password a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.transport.security.PrivKey} object.
	 * @throws java.io.IOException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws java.security.spec.InvalidKeySpecException if any.
	 * @throws javax.crypto.NoSuchPaddingException if any.
	 * @throws java.security.InvalidKeyException if any.
	 * @throws java.security.InvalidAlgorithmParameterException if any.
	 * @throws javax.crypto.IllegalBlockSizeException if any.
	 * @throws javax.crypto.BadPaddingException if any.
	 * @throws java.security.spec.InvalidParameterSpecException if any.
	 */
	public static PrivKey load(InputStream is, final String password)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException  {
		byte[] keyData = FileUtil.readStream(is);
		return load(keyData, password);
	}
	
	/**
	 * Load private key from key data
	 *
	 * @param password a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws java.security.spec.InvalidKeySpecException if any.
	 * @throws javax.crypto.NoSuchPaddingException if any.
	 * @throws java.security.InvalidAlgorithmParameterException if any.
	 * @throws java.security.InvalidKeyException if any.
	 * @throws javax.crypto.BadPaddingException if any.
	 * @throws javax.crypto.IllegalBlockSizeException if any.
	 * @throws java.security.spec.InvalidParameterSpecException if any.
	 * @param keyBytes an array of byte.
	 * @return a {@link org.opcfoundation.ua.transport.security.PrivKey} object.
	 */
	public static PrivKey load(byte[] keyBytes, final String password)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException  {

		boolean isEncrypted = false;
		String dekAlgName = "";
		byte[] ivBytes = null;
			
		// this code is ...  
		if (
		((keyBytes[0] == (byte) '-' && keyBytes[1] == (byte) '-' && keyBytes[2] == (byte) '-')) ||
		((keyBytes[3] == (byte) '-' && keyBytes[4] == (byte) '-' && keyBytes[5] == (byte) '-')) ) {
			// BASE64 encoded
			String privKeyPEM = new String(keyBytes);
			String[] dekInfo;
			Scanner scanner = new Scanner(privKeyPEM);
			StringBuilder keyString;
			boolean isBase64Encoded;
			try {
				keyString = new StringBuilder();
				String ivString = "";
				isBase64Encoded = false;
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.matches(BEGIN_PRIVATE_KEY_REGEX))
						isBase64Encoded = true;
					else if (line.matches(END_PRIVATE_KEY_REGEX))
						break;
					else if (line.startsWith("Proc-Type: 4,ENCRYPTED"))
						isEncrypted = true;
					else if (line.startsWith("DEK-Info:")) {
						dekInfo = line.substring(10).split(",");
						dekAlgName = dekInfo[0];
						ivString = dekInfo[1];
						ivBytes = CryptoUtil.hexToBytes(ivString);
					} else
						keyString.append(line.trim());
				}
			} finally {
				scanner.close();
			}
			if (isBase64Encoded)
				keyBytes = CryptoUtil.base64Decode(keyString.toString());
		}

		if (isEncrypted) {
			if (password == null || password.isEmpty())
				throw new SecurityException("Encrypted private key requires a password.");
			// Decrypt the data first
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			String[] dekAlgParts = dekAlgName.split("-");
			String alg = dekAlgParts[0];
			int keyBits = dekAlgParts.length > 1 ? Integer.parseInt(dekAlgParts[1]) : 128;
			if (true) {
				byte[] salt = ivBytes;
				if (salt.length > 8) {
					salt = new byte[8];
					System.arraycopy(ivBytes, 0, salt, 0, 8);
				}

				byte[] pw = BouncyCastleUtils.PKCS5PasswordToBytes(password.toCharArray());
				byte[] sKey = generateDerivedKey(keyBits / 8,
						pw, salt);
				SecretKeySpec keySpec = new SecretKeySpec(sKey, alg);
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
				keyBytes = cipher.doFinal(keyBytes);
			}
		}
		return new PrivKey(keyBytes);
	}

	private static byte[] generateDerivedKey(
	        int bytesNeeded, byte[] password, byte[] salt) throws NoSuchAlgorithmException
	    {
	        MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[]  buf = new byte[digest.getDigestLength()];
	        byte[]  key = new byte[bytesNeeded];
	        int     offset = 0;
	        
	        for (;;)
	        {
	            digest.update(password, 0, password.length);
	            digest.update(salt, 0, salt.length);

	            buf = digest.digest();
	            
	            int len = (bytesNeeded > buf.length) ? buf.length : bytesNeeded;
	            System.arraycopy(buf, 0, key, offset, len);
	            offset += len;

	            // check if we need any more
	            bytesNeeded -= len;
	            if (bytesNeeded == 0)
	            {
	                break;
	            }

	            // do another round
	            digest.reset();
	            digest.update(buf, 0, buf.length);
	        }
	        
	        return key;
	    }
	
	/**
	 * Load private key from a file.
	 * <p>
	 *
	 * @deprecated Use {@link #load(File, String)} or {@link #loadFromKeyStore(File, String)} instead
	 * @param file a {@link java.io.File} object.
	 * @throws java.io.IOException if any.
	 * @throws java.security.spec.InvalidKeySpecException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @return a {@link org.opcfoundation.ua.transport.security.PrivKey} object.
	 */
	@Deprecated
	public static PrivKey load(File file) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException
	{
		byte[] encoded = FileUtil.readFile(file);
		return new PrivKey(encoded);
	}	
	
	/**
	 * Load private key from a key store (PKCS12) file
	 *
	 * @param file key store file
	 * @param password password to key store
	 * @return private key
	 * @throws java.io.IOException if any.
	 * @throws java.security.KeyStoreException if any.
	 * @throws java.security.cert.CertificateException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 * @throws java.security.UnrecoverableKeyException if any.
	 */
	public static PrivKey loadFromKeyStore(File file, String password) throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, KeyStoreException
	{
		return loadFromKeyStore( file.toURI().toURL(), password );
	}
	
	/**
	 * Save the key in a binary file. Note that the file is not secured by a password.
	 *
	 * @param file a {@link java.io.File} object.
	 * @throws java.io.IOException if any.
	 */
	public void save(File file)
	throws IOException
	{
		FileUtil.writeFile(file, getEncodedPrivateKey());
	}
	
	/**
	 * Save the private key to a PEM file
	 *
	 * @param file the file
	 * @param privateKeyPassword the password used to store the key
	 * @throws java.io.IOException if any.
	 */
	public void save(File file, String privateKeyPassword) throws IOException
	{
		if (privateKeyPassword == null || privateKeyPassword.length() == 0) {
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(getPrivateKey()
					.getEncoded());
			FileWriter fw = new FileWriter(file);
			try {
				fw.append(BEGIN_RSA_PRIVATE_KEY);
				fw.append(StringUtils.addLineBreaks(CryptoUtil.base64Encode(spec.getEncoded()), 72));
				fw.append(END_RSA_PRIVATE_KEY);
			} finally {
				fw.close();
			}
		}
		else
		{
			savePemWithBC(file, privateKeyPassword);
		}
	}

	/**
	 * @param file
	 * @param privateKeyPassword
	 * @throws IOException
	 */
	private void savePemWithBC(File file, String privateKeyPassword)
			throws IOException {
		BouncyCastleUtils.writeToPem(getPrivateKey(), file, privateKeyPassword, "AES-128-CBC");
	}

	/**
	 * <p>Constructor for PrivKey.</p>
	 *
	 * @param encodedPrivateKey an array of byte.
	 * @throws java.io.IOException if any.
	 * @throws java.security.spec.InvalidKeySpecException if any.
	 * @throws java.security.NoSuchAlgorithmException if any.
	 */
	public PrivKey(byte[] encodedPrivateKey) 
	throws IOException, InvalidKeySpecException, NoSuchAlgorithmException
	{
		if (encodedPrivateKey==null) throw new IllegalArgumentException("null arg");
		this.privateKey = decodeRSAPrivateKey(encodedPrivateKey);
	}

	private RSAPrivateKey decodeRSAPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		// Try to read the private key with the default provider first
		try
		{
			KeyFactory kf = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) kf.generatePrivate(spec);
		} catch (Exception e) {
			// For some reason the Sun provider cannot read all keys: try with Bouncy Castle if it fails
			try {
				KeyFactory kf = KeyFactory.getInstance("RSA", CryptoUtil.getSecurityProviderName());
				return (RSAPrivateKey) kf.generatePrivate(spec);
			} catch (NoSuchProviderException e1) {
				logger.error("Could not read private key with default Provider and Bouncy Castle not available");
				throw new RuntimeException("Could not read private key with default Provider and Bouncy Castle not available", e1); 
			}
		}
	}

	/**
	 * <p>Constructor for PrivKey.</p>
	 *
	 * @param privateKey a {@link java.security.interfaces.RSAPrivateKey} object.
	 */
	public PrivKey(RSAPrivateKey privateKey)
	{
		this.privateKey = privateKey;
	}
	
	/**
	 * <p>getEncodedPrivateKey.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getEncodedPrivateKey() 
	{
		return privateKey.getEncoded();
	}
	
	/**
	 * <p>Getter for the field <code>privateKey</code>.</p>
	 *
	 * @return a {@link java.security.interfaces.RSAPrivateKey} object.
	 */
	public RSAPrivateKey getPrivateKey()
	{
		return privateKey;
	}

	/**
	 * Save the identity to a password protected keystore.
	 *
	 * @param cert
	 *            the certificate used to chain the key
	 * @param file
	 *            the file used to store the key
	 * @param privateKeyPassword
	 *            the password to secure the private key, must not be null for
	 *            JKS
	 * @param keyStorePassword
	 *            the password to the key store, must not be null
	 * @param keyStoreType
	 *            key store type, either "PKCS12" or "JKS"
	 * @throws java.io.IOException
	 *             if the file cannot be written to
	 * @throws java.security.NoSuchProviderException
	 *             Bouncy Castle Provider not found
	 * @throws java.security.KeyStoreException
	 *             keystore failed
	 * @throws java.security.cert.CertificateException
	 *             certificate problem
	 * @throws java.security.NoSuchAlgorithmException
	 *             cryptographic algorithm not found
	 */
	public void saveToKeyStore(Cert cert, File file, 
			String privateKeyPassword, String keyStorePassword, String keyStoreType) throws IOException, KeyStoreException,
			NoSuchProviderException, NoSuchAlgorithmException,
			CertificateException {
		String alias = "key";
		CertificateUtils.saveToProtectedStore(getPrivateKey(), cert.getCertificate(),
				file, alias, privateKeyPassword, keyStorePassword,
				keyStoreType);
	
	}
	
}
