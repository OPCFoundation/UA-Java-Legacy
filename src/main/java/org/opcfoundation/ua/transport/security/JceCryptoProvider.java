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

import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.opcfoundation.ua.common.RuntimeServiceResultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.transport.tcp.impl.SecurityToken;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Abstract JceCryptoProvider class.</p>
 *
 */
public abstract class JceCryptoProvider implements CryptoProvider {

	// base implementations should use Cipher, Signature, Mac...

	static Logger logger = LoggerFactory.getLogger(JceCryptoProvider.class);
	protected Provider provider;

	/**
	 * <p>Constructor for JceCryptoProvider.</p>
	 */
	public JceCryptoProvider() {
		String securityProviderName = CryptoUtil.getSecurityProviderName();
		this.provider = Security.getProvider(securityProviderName);
	}

	/** {@inheritDoc} */
	@Override
	abstract public byte[] base64Decode(String string);

	/** {@inheritDoc} */
	@Override
	abstract public String base64Encode(byte[] bytes);

	/** {@inheritDoc} */
	@Override
	public Mac createMac(SecurityAlgorithm algorithm, byte[] secret)
			throws ServiceResultException {
		SecretKeySpec keySpec = new SecretKeySpec(secret,
				algorithm.getStandardName());
		Mac hmac;
		try {
			hmac = Mac.getInstance(algorithm.getStandardName(), provider);
			hmac.init(keySpec);
		} catch (InvalidKeyException e) {
			throw new ServiceResultException(
					StatusCodes.Bad_SecurityChecksFailed, e);
		} catch (GeneralSecurityException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}
		return hmac;
	}

	/** {@inheritDoc} */
	@Override
	public int decryptAsymm(PrivateKey decryptingKey,
			SecurityAlgorithm algorithm, byte[] dataToDecrypt, byte[] output,
			int outputOffset) throws ServiceResultException {

		RSAPrivateKey decryptingKeyRSA = (RSAPrivateKey) decryptingKey;
		int inputBlockSize = decryptingKeyRSA.getModulus().bitLength() / 8;

		Cipher cipher;
		try {
			cipher = getAsymmetricCipher(algorithm, decryptingKey);
		} catch (InvalidKeyException e) {
			logger.info("decrypt: The provided RSA key is invalid", e);
			throw new ServiceResultException(
					StatusCodes.Bad_SecurityChecksFailed, e);
		} catch (GeneralSecurityException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}

		// Verify block sizes
		if (dataToDecrypt.length % inputBlockSize != 0) {
			logger.error("decrypt: Wrong blockSize!!!");
			throw new ServiceResultException(
					StatusCodes.Bad_InternalError,
					"Error in asymmetric decrypt: Input data is not an even number of encryption blocks.");
		}

		try {

			logger.info("JceCipherDecrypt={}", cipher.toString());

			// int outputs = decrypter.getOutputSize(dataToDecrypt.length);
			//
			// int blocks = decrypter.getBlockSize();
			// int stop = 0;
			int maxIndex = outputOffset + dataToDecrypt.length;
			// initialize return value, value tells how bytes has been stored in
			// output
			int totalDecryptedBytes = 0;
			// this value tells how many bytes where added to buffer in each
			// iteration
			int amountOfDecryptedBytes = -1;
			int inputOffset = 0;
			for (int index = outputOffset; index < maxIndex; index += inputBlockSize) {
				amountOfDecryptedBytes = cipher.doFinal(dataToDecrypt,
						inputOffset, inputBlockSize, output, outputOffset);
				inputOffset += inputBlockSize;
				outputOffset += amountOfDecryptedBytes;
				// Update amount of total decrypted bytes
				totalDecryptedBytes += amountOfDecryptedBytes;
			}
			return totalDecryptedBytes;

		} catch (GeneralSecurityException e) {
			logger.error("decrypt: error", e);
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}

	}

	/** {@inheritDoc} */
	@Override
	public int decryptSymm(SecurityToken token, byte[] dataToDecrypt,
			int inputOffset, int inputLength, byte[] output, int outputOffset)
			throws ServiceResultException {

		// Decrypt
		SecurityAlgorithm algorithm = token.getSecurityPolicy()
				.getSymmetricEncryptionAlgorithm();
		// isTraceEnabled checked because potentially time consuming CryptoUtil
		// methods get evaluated otherwise every time.
		if (logger.isTraceEnabled()) {
			logger.trace("decrypt: token.getRemoteEncryptingKey()="
					+ CryptoUtil.toHex(token.getRemoteEncryptingKey()));
			logger.trace("decrypt: token.getRemoteInitializationVector()="
					+ CryptoUtil.toHex(token.getRemoteInitializationVector()));
			logger.trace("decrypt: dataToDecrypt="
					+ CryptoUtil.toHex(dataToDecrypt));
			logger.trace("decrypt: algorithm=" + algorithm);
		}

		Cipher cipher;
		int decryptedBytes = 0;

		SecretKeySpec spec = new SecretKeySpec(token.getRemoteEncryptingKey(),
				algorithm.getStandardName());

		try {
			cipher = Cipher.getInstance(algorithm.getTransformation());
			cipher.init(Cipher.DECRYPT_MODE, spec,
					new IvParameterSpec(token.getRemoteInitializationVector()));
			decryptedBytes = cipher.update(dataToDecrypt, inputOffset,
					inputLength, output, outputOffset);
			decryptedBytes += cipher.doFinal(output, outputOffset
					+ decryptedBytes);
		} catch (InvalidKeyException e) {
			throw new ServiceResultException(
					StatusCodes.Bad_SecurityChecksFailed, e);
		} catch (GeneralSecurityException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		} catch (IllegalStateException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}

		// isTraceEnabled checked because potentially time consuming CryptoUtil
		// method gets evaluated otherwise every time.
		if (logger.isTraceEnabled())
			logger.trace("decrypt: output=" + CryptoUtil.toHex(output));
		return decryptedBytes;

	}

	/** {@inheritDoc} */
	@Override
	public void encryptAsymm(PublicKey remotePublicKey,
			SecurityAlgorithm algorithm, byte[] dataToEncrypt, byte[] output,
			int outputOffset) throws ServiceResultException {

		// SecurityPolicy policy = profile.getSecurityPolicy();

		// RSAPublicKey serverPublic = (RSAPublicKey)
		// encryptingCertificate.getPublicKey();
		Cipher cipher = null;

		int inputBlockSize = 1;
		// Key key = profile.getRemoteCertificate().getPublicKey();
		// inputBlockSize =
		// CryptoUtil.getPlainTextBlockSize(policy.getAsymmetricEncryptionAlgorithm(),
		// key);
		inputBlockSize = CryptoUtil.getPlainTextBlockSize(algorithm,
				remotePublicKey);

		try {

			cipher = getAsymmetricCipher(algorithm, remotePublicKey);

			// encrypt one block at time
			int maxIndex = outputOffset + dataToEncrypt.length;

			int inputOffset = 0;
			for (int index = outputOffset; index < maxIndex; index += inputBlockSize) {
				int amountOfEncryptedBytes = cipher.doFinal(dataToEncrypt,
						inputOffset,
						Math.min(maxIndex - index, inputBlockSize), output,
						outputOffset);
				inputOffset += inputBlockSize;
				outputOffset += amountOfEncryptedBytes;
				logger.debug(
						"Asym encryption: amountOfEncryptedBytes={} inputOffset={} outputOffset={} index={}",
						amountOfEncryptedBytes, inputOffset, outputOffset,
						index);
			}

		} catch (GeneralSecurityException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}

	}

	/** {@inheritDoc} */
	@Override
	public int encryptSymm(SecurityToken token, byte[] dataToEncrypt,
			int inputOffset, int inputLength, byte[] output, int outputOffset)
			throws ServiceResultException {

		SecurityAlgorithm algorithm = token.getSecurityPolicy()
				.getSymmetricEncryptionAlgorithm();
		SecretKeySpec spec = new SecretKeySpec(token.getLocalEncryptingKey(),
				algorithm.getStandardName());

		try {
			Cipher cipher = Cipher.getInstance(algorithm.getTransformation());
			int blockSize = cipher.getBlockSize();

			// Check that input data is even with the encryption blocks
			if (dataToEncrypt.length % blockSize != 0) {
				// ERROR
				logger.error("Input data is not an even number of encryption blocks.");
				throw new ServiceResultException(
						StatusCodes.Bad_InternalError,
						"Error in symmetric decrypt: Input data is not an even number of encryption blocks.");
			}

			cipher.init(Cipher.ENCRYPT_MODE, spec,
					new IvParameterSpec(token.getLocalInitializationVector()));
			int encryptedBytes = cipher.update(dataToEncrypt, inputOffset,
					inputLength, output, outputOffset);
			encryptedBytes += cipher.doFinal(output, outputOffset
					+ encryptedBytes);
			return encryptedBytes;
		} catch (InvalidKeyException e) {
			throw new ServiceResultException(
					StatusCodes.Bad_SecurityChecksFailed, e);
		} catch (GeneralSecurityException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}

	}

	/** {@inheritDoc} */
	@Override
	public byte[] signAsymm(PrivateKey senderPrivate,
			SecurityAlgorithm algorithm, byte[] dataToSign)
			throws ServiceResultException {

		if (algorithm == null)
			return null;

		if (dataToSign == null || senderPrivate == null)
			throw new IllegalArgumentException("null arg");

		try {
			Signature signer = getSignature(algorithm);
			logger.debug("signer.getProvider(): {}", signer.getProvider());
			signer.initSign(senderPrivate);
			// compute hash of the message
			signer.update(dataToSign);
			return signer.sign();

		} catch (GeneralSecurityException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}

	}

	/** {@inheritDoc} */
	@Override
	public void signSymm(SecurityToken token, byte[] input, int verifyLen,
			byte[] output) throws ServiceResultException {
		Mac hmac = token.createLocalHmac();
		hmac.update(input, 0, verifyLen);
		try {
			hmac.doFinal(output, 0);
		} catch (GeneralSecurityException e) {
			throw new RuntimeServiceResultException(new ServiceResultException(
					StatusCodes.Bad_InternalError, e));
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean verifyAsymm(PublicKey signingCertificate,
			SecurityAlgorithm algorithm, byte[] dataToVerify, byte[] signature)
			throws ServiceResultException {
		if (algorithm == null)
			return true;
		if (signingCertificate == null || dataToVerify == null
				|| signature == null)
			throw new IllegalArgumentException("null arg");

		try {
			Signature verifier = getSignature(algorithm);
			verifier.initVerify(signingCertificate);
			verifier.update(dataToVerify);
			if (verifier.verify(signature)) {
				logger.debug("Asym Signature Verify : OK");
				return true;
			} else {
				logger.error("Asymmetric Signature Verification fails");
				return false;
			}
		} catch (GeneralSecurityException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}

	}

	/** {@inheritDoc} */
	@Override
	public void verifySymm(SecurityToken token, byte[] dataToVerify,
			byte[] signature) throws ServiceResultException {

		// Get right hmac
		Mac hmac = token.createRemoteHmac();
		byte[] computedSignature = hmac.doFinal(dataToVerify);

		// Compare signatures
		// First test that sizes are the same
		if (signature.length != computedSignature.length) {
			logger.warn("Signature lengths do not match: \n{} vs. \n{}",
					CryptoUtil.toHex(signature),
					CryptoUtil.toHex(computedSignature));
			throw new ServiceResultException(
					StatusCodes.Bad_SecurityChecksFailed, "Invalid signature");
		}
		// Compare byte by byte
		for (int index = 0; index < signature.length; index++) {
			if (signature[index] != computedSignature[index]) {
				logger.warn("Signatures do not match: \n{} vs. \n{}",
						CryptoUtil.toHex(signature),
						CryptoUtil.toHex(computedSignature));
				throw new ServiceResultException(
						StatusCodes.Bad_SecurityChecksFailed,
						"Invalid signature");
			}
		}

		// Everything went fine, signatures matched

	}

	private Cipher getAsymmetricCipher(SecurityAlgorithm algorithm,
			PrivateKey privateKey) throws NoSuchProviderException,
			NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, InvalidAlgorithmParameterException {

		Cipher cipher;
		// SecurityAlgorithm algorithm =
		// policy.getAsymmetricEncryptionAlgorithm();

		try {
			cipher = Cipher
					.getInstance(algorithm.getTransformation(), provider);
		} catch (NoSuchAlgorithmException e) {
			// SunJCE does not recognize RSA/NONE/PKCS1Padding, but will
			// return such with plain RSA
			cipher = Cipher.getInstance(algorithm.getStandardName(), provider);
		}

		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		logger.debug("decrypt: cipher.provider={}", cipher.getProvider());
		return cipher;

	}

	private Cipher getAsymmetricCipher(SecurityAlgorithm algorithm,
			PublicKey publicKey) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException,
			InvalidAlgorithmParameterException {

		Cipher cipher;

		try {
			cipher = Cipher
					.getInstance(algorithm.getTransformation(), provider);
		} catch (NoSuchAlgorithmException e) {
			// SunJCE does not recognize RSA/NONE/PKCS1Padding, but will
			// return such with plain RSA
			cipher = Cipher.getInstance(algorithm.getStandardName(), provider);
		}

		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		logger.trace("encrypt: cipher.provider={}", cipher.getProvider());
		return cipher;
	}

	private Signature getSignature(SecurityAlgorithm algorithm)
			throws NoSuchAlgorithmException {

		try {
			return Signature.getInstance(algorithm.getStandardName(), provider);
		} catch (NoSuchAlgorithmException e) {
			// Algorithm not found from explicitly defined security provider,
			// try to use any other provider found
			return Signature.getInstance(algorithm.getStandardName());
		}

	}

}
