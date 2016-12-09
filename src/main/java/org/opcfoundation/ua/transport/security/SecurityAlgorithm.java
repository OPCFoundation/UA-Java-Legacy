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

import java.util.EnumSet;

/**
 * OPC UA specific security algorithm URIs and the respective Java StandardNames
 */
public enum SecurityAlgorithm {
	// Symmetric signature	
	HmacSha1(AlgorithmType.SymmetricSignature, "http://www.w3.org/2000/09/xmldsig#hmac-sha1", "HmacSHA1", 160),
	HmacSha256(AlgorithmType.SymmetricSignature, "http://www.w3.org/2000/09/xmldsig#hmac-sha256", "HmacSHA256", 256),
	// Symmetric encryption
	Aes128(AlgorithmType.SymmetricEncryption, "http://www.w3.org/2001/04/xmlenc#aes128-cbc", "AES/CBC/NoPadding", 128),
	Aes256(AlgorithmType.SymmetricEncryption, "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "AES/CBC/NoPadding", 256),
	// Asymmetric signature
	RsaSha1(AlgorithmType.AsymmetricSignature, "http://www.w3.org/2000/09/xmldsig#rsa-sha1", "SHA1withRSA", 160),
	RsaSha256(AlgorithmType.AsymmetricSignature, "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", "SHA256withRSA", 256),
	// Asymmetric encryption
	Rsa15(AlgorithmType.AsymmetricEncryption, "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "RSA/NONE/PKCS1Padding", 0),
	RsaOaep(AlgorithmType.AsymmetricEncryption, "http://www.w3.org/2001/04/xmlenc#rsa-oaep", "RSA/NONE/OAEPWithSHA1AndMGF1Padding", 0),
	// Asymmetric keywrap
	KwRsaOaep(AlgorithmType.AsymmetricKeywrap, "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p","", 0),
	KwRsa15(AlgorithmType.AsymmetricKeywrap, "http://www.w3.org/2001/04/xmlenc#rsa-1_5","", 0),
	// key derivation
	PSha1(AlgorithmType.KeyDerivation, "http://www.w3.org/2001/04/xmlenc#aes128-cbc","HmacSHA1", 0),
	PSha256(AlgorithmType.KeyDerivation, "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk/p_sha256","HmacSHA256", 0);
	
	
	public enum AlgorithmType {
		SymmetricSignature,
		SymmetricEncryption,
		AsymmetricSignature,
		AsymmetricEncryption,
		AsymmetricKeywrap,
		KeyDerivation
	}

	private AlgorithmType type;
	/**
	 * <p>Getter for the field <code>type</code>.</p>
	 *
	 * @return the type
	 */
	public AlgorithmType getType() {
		return type;
	}
	private final String uri;
	private final String standardName;
	private final String transformation;
	private final int keySize;
	private final String mode;
	private final String padding;
	/**
	 * <p>Getter for the field <code>mode</code>.</p>
	 *
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * <p>Getter for the field <code>padding</code>.</p>
	 *
	 * @return the padding
	 */
	public String getPadding() {
		return padding;
	}
	/**
	 * <p>Getter for the field <code>keySize</code>.</p>
	 *
	 * @return the keySize
	 */
	public int getKeySize() {
		return keySize;
	}
	/**
	 * <p>Getter for the field <code>uri</code>.</p>
	 *
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}
	/**
	 * <p>Getter for the field <code>standardName</code>.</p>
	 *
	 * @return the standardName
	 */
	public String getStandardName() {
		return standardName;
	}
	private SecurityAlgorithm(AlgorithmType type, String uri, String transformation, int keySize) {
		this.type = type;
		this.uri = uri;
		this.transformation = transformation;
		String[] parts = transformation.split("/");
		this.standardName = parts[0];
		this.mode = parts.length > 1 ? parts[1] : "EBC";
		this.padding = parts.length > 2 ? parts[2] : "PKCS5Padding";
		this.keySize = keySize;
		
	}
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Algorithm URI=" + uri + " StandardName=" + standardName
				+ " Transformation=" + transformation;
	}
	/**
	 * Find the SecurityAlgorithm with URI.
	 *
	 * @param algorithmUri the Uri to look for
	 * @return the respective SecurityAlgorithm or null if none is found.
	 */
	public static SecurityAlgorithm valueOfUri(String algorithmUri) {
		for (SecurityAlgorithm a: EnumSet.allOf(SecurityAlgorithm.class))
			if (a.getUri().equals(algorithmUri))
				return a;
		return null;
	}
	/**
	 * <p>Getter for the field <code>transformation</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTransformation() {
		return this.transformation;
	}

}
