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

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.ObjectUtils;

/**
 * Security Policy determines which algorithms to use during asymmetric and
 * symmetric encryption.
 * 
 * @see CryptoUtil for instantiating cryptographics objects
 */
public final class SecurityPolicy {

	private static final Charset UTF8 = Charset.forName("utf-8");
	public static final String URI_BINARY_NONE = "http://opcfoundation.org/UA/SecurityPolicy#None";
	public static final String URI_BINARY_BASIC128RSA15 = "http://opcfoundation.org/UA/SecurityPolicy#Basic128Rsa15";
	public static final String URI_BINARY_BASIC256 = "http://opcfoundation.org/UA/SecurityPolicy#Basic256";
	public static final String URI_BINARY_BASIC256SHA256 = "http://opcfoundation.org/UA/SecurityPolicy#Basic256Sha256";
	public static final String URI_XML_NONE = "http://opcfoundation.org/UA-Profile/Securitypolicy/None";
	public static final String URI_XML_BASIC128RSA15 = "http://opcfoundation.org/UA-Profile/Securitypolicy/Basic128Rsa15";
	public static final String URI_XML_BASIC256 = "http://opcfoundation.org/UA-Profile/Securitypolicy/Basic256";

	// Global Well known Security policies //
	public static final SecurityPolicy NONE = new SecurityPolicy(
			SecurityPolicy.URI_BINARY_NONE,  null, null,
			null, null, null, null, 0, 0, 0, 1, 1024, 2048);

	public static final SecurityPolicy BASIC128RSA15 = new SecurityPolicy(
			SecurityPolicy.URI_BINARY_BASIC128RSA15, 
			SecurityAlgorithm.HmacSha1, // Symmetric signature
			SecurityAlgorithm.Aes128, // Symmetric encryption
			SecurityAlgorithm.RsaSha1, // Asymmetric signature
			SecurityAlgorithm.KwRsa15, // Asymmetric keywrap
			SecurityAlgorithm.Rsa15, // Asymmetric encryption
			SecurityAlgorithm.PSha1, // key derivation
			20, 16, 16, 16, 1024, 2048);

	public static final SecurityPolicy BASIC256 = new SecurityPolicy(
			SecurityPolicy.URI_BINARY_BASIC256, 
			SecurityAlgorithm.HmacSha1, // Symmetric signature
			SecurityAlgorithm.Aes256, // Symmetric encryption
			SecurityAlgorithm.RsaSha1, // Asymmetric signature
			SecurityAlgorithm.KwRsaOaep,// Asymmetric keywrap
			SecurityAlgorithm.RsaOaep, // Asymmetric encryption
			SecurityAlgorithm.PSha1, // key derivation
			20, 24, 32, 16, 1024, 2048);

	public static final SecurityPolicy BASIC256SHA256 = new SecurityPolicy(
			SecurityPolicy.URI_BINARY_BASIC256SHA256, 
			SecurityAlgorithm.HmacSha256, // Symmetric signature
			SecurityAlgorithm.Aes256, // Symmetric encryption
			SecurityAlgorithm.RsaSha256, // Asymmetric signature
			SecurityAlgorithm.KwRsaOaep,// Asymmetric keywrap
			SecurityAlgorithm.RsaOaep, // Asymmetric encryption
			SecurityAlgorithm.PSha256, // key derivation
			32, 32, 32, 16, 2048, 4096);

	/** Security policy map */
	private static Map<String, SecurityPolicy> policies = new ConcurrentHashMap<String, SecurityPolicy>();

	static {
		addSecurityPolicy(NONE);
		addSecurityPolicy(BASIC128RSA15);
		addSecurityPolicy(BASIC256);
		addSecurityPolicy(BASIC256SHA256);
	}

	/**
	 * Add new security policy to stack
	 * 
	 * @param policy
	 */
	public static void addSecurityPolicy(SecurityPolicy policy) {
		policies.put(policy.policyUri, policy);
	}

	/**
	 * Get all security policies supported by the stack
	 * 
	 * @return security policies
	 */
	public static SecurityPolicy[] getAllSecurityPolicies() {
		return policies.values().toArray(new SecurityPolicy[policies.size()]);
	}

	/**
	 * Get security policy by policy uri
	 * 
	 * @param securityPolicyUri
	 *            security policy uri
	 * @return security policy
	 * @throws ServiceResultException
	 *             Bad_SecurityPolicyRejected if policy is unknown
	 */
	public static SecurityPolicy getSecurityPolicy(String securityPolicyUri)
			throws ServiceResultException {
		if (securityPolicyUri == null)
			return NONE;
		SecurityPolicy result = policies.get(securityPolicyUri);
		if (result == null)
			throw new ServiceResultException(
					StatusCodes.Bad_SecurityPolicyRejected);
		return result;

	}

	private final SecurityAlgorithm asymmetricEncryptionAlgorithm;

	private final SecurityAlgorithm asymmetricKeyWrapAlgorithm;
	private final SecurityAlgorithm asymmetricSignatureAlgorithm;
	private final byte[] encodedPolicyUri;

	private final int encryptionBlockSize;

	private final int encryptionKeySize;

	private final SecurityAlgorithm keyDerivationAlgorithm;

	private final int maxAsymmetricKeyLength;

	private final int minAsymmetricKeyLength;

	private final String policyUri;

	private final int signatureKeySize;

	private final SecurityAlgorithm symmetricEncryptionAlgorithm;
	private final SecurityAlgorithm symmetricSignatureAlgorithm;
	private final int symmetricSignatureSize;
	SecurityPolicy(String policyUri, 
			SecurityAlgorithm symmetricSignatureAlgorithmUri,
			SecurityAlgorithm symmetricEncryptionAlgorithmUri,
			SecurityAlgorithm asymmetricSignatureAlgorithmUri,
			SecurityAlgorithm asymmetricKeyWrapAlgorithmUri,
			SecurityAlgorithm asymmetricEncryptionAlgorithmUri,
			SecurityAlgorithm keyDerivationAlgorithmUri,
			int hmacHashSize,
			int signatureKeySize, int encryptionKeySize,
			int encryptionBlockSize, int minAsymmetricKeyLength,
			int maxAsymmetricKeyLength) {
		this.asymmetricEncryptionAlgorithm = asymmetricEncryptionAlgorithmUri;
		this.asymmetricKeyWrapAlgorithm = asymmetricKeyWrapAlgorithmUri;
		this.asymmetricSignatureAlgorithm = asymmetricSignatureAlgorithmUri;
		this.keyDerivationAlgorithm = keyDerivationAlgorithmUri;
		this.policyUri = policyUri;
		this.symmetricEncryptionAlgorithm = symmetricEncryptionAlgorithmUri;
		this.symmetricSignatureAlgorithm = symmetricSignatureAlgorithmUri;
		this.encodedPolicyUri = policyUri.getBytes(UTF8);

		this.symmetricSignatureSize = hmacHashSize;
		this.signatureKeySize = signatureKeySize;
		this.encryptionKeySize = encryptionKeySize;
		this.encryptionBlockSize = encryptionBlockSize;

		this.minAsymmetricKeyLength = minAsymmetricKeyLength;
		this.maxAsymmetricKeyLength = maxAsymmetricKeyLength;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SecurityPolicy))
			return false;
		SecurityPolicy other = (SecurityPolicy) obj;

		if (!ObjectUtils.objectEquals(policyUri, other.policyUri))
			return false;
		if (!ObjectUtils.objectEquals(asymmetricEncryptionAlgorithm,
				other.asymmetricEncryptionAlgorithm))
			return false;
		if (!ObjectUtils.objectEquals(asymmetricKeyWrapAlgorithm,
				other.asymmetricKeyWrapAlgorithm))
			return false;
		if (!ObjectUtils.objectEquals(asymmetricSignatureAlgorithm,
				other.asymmetricSignatureAlgorithm))
			return false;
		if (!ObjectUtils.objectEquals(keyDerivationAlgorithm,
				other.keyDerivationAlgorithm))
			return false;
		if (!ObjectUtils.objectEquals(symmetricEncryptionAlgorithm,
				other.symmetricEncryptionAlgorithm))
			return false;
		if (!ObjectUtils.objectEquals(symmetricSignatureAlgorithm,
				other.symmetricSignatureAlgorithm))
			return false;

		return true;
	}
	public SecurityAlgorithm getAsymmetricEncryptionAlgorithm() {
		return asymmetricEncryptionAlgorithm;
	}
	public SecurityAlgorithm getAsymmetricKeyWrapAlgorithm() {
		return asymmetricKeyWrapAlgorithm;
	}
	public SecurityAlgorithm getAsymmetricSignatureAlgorithm() {
		return asymmetricSignatureAlgorithm;
	}
	public byte[] getEncodedPolicyUri() {
		return encodedPolicyUri;
	}

	/**
	 * @return the encryptionBlockSize
	 */
	public int getEncryptionBlockSize() {
		return encryptionBlockSize;
	}

	/**
	 * @return the encryptionKeySize
	 */
	public int getEncryptionKeySize() {
		return encryptionKeySize;
	}

	public SecurityAlgorithm getKeyDerivationAlgorithm() {
		return keyDerivationAlgorithm;
	}

	/**
	 * @return the maxAsymmetricKeyLength
	 */
	public int getMaxAsymmetricKeyLength() {
		return maxAsymmetricKeyLength;
	}

	/**
	 * @return the minAsymmetricKeyLength
	 */
	public int getMinAsymmetricKeyLength() {
		return minAsymmetricKeyLength;
	}

	public String getPolicyUri() {
		return policyUri;
	}

	/**
	 * @return the signatureKeySize
	 */
	public int getSignatureKeySize() {
		return signatureKeySize;
	}

	public SecurityAlgorithm getSymmetricEncryptionAlgorithm() {
		return symmetricEncryptionAlgorithm;
	}

	public SecurityAlgorithm getSymmetricSignatureAlgorithm() {
		return symmetricSignatureAlgorithm;
	}

	/**
	 * @return the hmacHashSize
	 */
	public int getSymmetricSignatureSize() {
		return symmetricSignatureSize;
	}

	@Override
	public int hashCode() {
		return policyUri.hashCode();
	}

	/**
	 * Checks if a certificate is useable for this security policy.
	 * 
	 * @param cert
	 * @return true if certificate is usable
	 */
	public boolean isUsableWith(Cert cert) {
		int keySize = cert.getKeySize();
		return keySize >= minAsymmetricKeyLength
				&& keySize <= maxAsymmetricKeyLength;
	}

	@Override
	public String toString() {
		return policyUri;
	}

	// /**
	// * @return the Java StandardName for the asymmetric signature algorithm
	// corresponding to {@link #getAsymmetricSignatureAlgorithmUri()}
	// * @throws ServiceResultException with
	// StatusCodes.Bad_SecurityPolicyRejected, if the uri is not mapped to any
	// StandardName
	// */
	// public String getAsymmetricSignatureAlgorithmName() throws
	// ServiceResultException {
	// String uri = getAsymmetricSignatureAlgorithmUri();
	// if (uri.equals(SecurityAlgorithm.RsaSha1))
	// return "SHA1withRSA";
	// if (uri.equals(SecurityAlgorithm.RsaSha256))
	// return "SHA256withRSA";
	// throw new ServiceResultException(StatusCodes.Bad_SecurityPolicyRejected,
	// "Undefined signature algorithm: " + uri);
	//
	// }

	// /**
	// * @return the Java StandardName for the asymmetric signature algorithm
	// corresponding to {@link #getAsymmetricSignatureAlgorithmUri()}
	// * @throws ServiceResultException with
	// StatusCodes.Bad_SecurityPolicyRejected, if the uri is not mapped to any
	// StandardName
	// */
	// public String getSymmetricSignatureAlgorithmName() throws
	// ServiceResultException {
	// String uri = getAsymmetricSignatureAlgorithmUri();
	// if (uri.equals(SecurityAlgorithm.HmacSha1))
	// return "HMAC-SHA1";
	// if (uri.equals(SecurityAlgorithm.HmacSha256))
	// return "HMAC-SHA256";
	// throw new ServiceResultException(StatusCodes.Bad_SecurityPolicyRejected,
	// "Undefined signature algorithm: " + uri);
	//
	// }
	//

}
