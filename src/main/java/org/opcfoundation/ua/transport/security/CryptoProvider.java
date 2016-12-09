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

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Mac;

import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.transport.tcp.impl.SecurityToken;

/**
 * Crypto Provider interface for encrypting and decrypting services.
 */
public interface CryptoProvider {

	/**
	 * <p>base64Decode.</p>
	 *
	 * @param string a {@link java.lang.String} object.
	 * @return an array of byte.
	 */
	public byte[] base64Decode(String string);

	/**
	 * <p>base64Encode.</p>
	 *
	 * @param bytes an array of byte.
	 * @return a {@link java.lang.String} object.
	 */
	public String base64Encode(byte[] bytes);

	/**
	 * <p>createMac.</p>
	 *
	 * @param algorithm a {@link org.opcfoundation.ua.transport.security.SecurityAlgorithm} object.
	 * @param secret an array of byte.
	 * @return a {@link javax.crypto.Mac} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public Mac createMac(SecurityAlgorithm algorithm, byte[] secret)
			throws ServiceResultException;

	/**
	 * <p>decryptAsymm.</p>
	 *
	 * @param decryptingKey a {@link java.security.PrivateKey} object.
	 * @param algorithm a {@link org.opcfoundation.ua.transport.security.SecurityAlgorithm} object.
	 * @param dataToDecrypt an array of byte.
	 * @param output an array of byte.
	 * @param outputOffset a int.
	 * @return a int.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public int decryptAsymm(PrivateKey decryptingKey,
			SecurityAlgorithm algorithm, byte[] dataToDecrypt, byte[] output,
			int outputOffset) throws ServiceResultException;

	/**
	 * <p>decryptSymm.</p>
	 *
	 * @param token a {@link org.opcfoundation.ua.transport.tcp.impl.SecurityToken} object.
	 * @param dataToDecrypt an array of byte.
	 * @param inputOffset a int.
	 * @param inputLength a int.
	 * @param output an array of byte.
	 * @param outputOffset a int.
	 * @return a int.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public int decryptSymm(SecurityToken token, byte[] dataToDecrypt,
			int inputOffset, int inputLength, byte[] output, int outputOffset)
					throws ServiceResultException;

	/**
	 * <p>encryptAsymm.</p>
	 *
	 * @param encryptingCertificate a {@link java.security.PublicKey} object.
	 * @param algorithm a {@link org.opcfoundation.ua.transport.security.SecurityAlgorithm} object.
	 * @param dataToEncrypt an array of byte.
	 * @param output an array of byte.
	 * @param outputOffset a int.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public void encryptAsymm(PublicKey encryptingCertificate,
			SecurityAlgorithm algorithm, byte[] dataToEncrypt, byte[] output,
			int outputOffset) throws ServiceResultException;

	/**
	 * <p>encryptSymm.</p>
	 *
	 * @param token a {@link org.opcfoundation.ua.transport.tcp.impl.SecurityToken} object.
	 * @param dataToEncrypt an array of byte.
	 * @param inputOffset a int.
	 * @param inputLength a int.
	 * @param output an array of byte.
	 * @param outputOffset a int.
	 * @return a int.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public int encryptSymm(SecurityToken token, byte[] dataToEncrypt,
			int inputOffset, int inputLength, byte[] output, int outputOffset)
					throws ServiceResultException;

	/**
	 * <p>signAsymm.</p>
	 *
	 * @param senderPrivate a {@link java.security.PrivateKey} object.
	 * @param algorithm a {@link org.opcfoundation.ua.transport.security.SecurityAlgorithm} object.
	 * @param dataToSign an array of byte.
	 * @return an array of byte.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public byte[] signAsymm(PrivateKey senderPrivate,
			SecurityAlgorithm algorithm, byte[] dataToSign)
					throws ServiceResultException;

	/**
	 * <p>signSymm.</p>
	 *
	 * @param token a {@link org.opcfoundation.ua.transport.tcp.impl.SecurityToken} object.
	 * @param input an array of byte.
	 * @param verifyLen a int.
	 * @param output an array of byte.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public void signSymm(SecurityToken token, byte[] input, int verifyLen,
			byte[] output) throws ServiceResultException;

	/**
	 * <p>verifyAsymm.</p>
	 *
	 * @param signingCertificate a {@link java.security.PublicKey} object.
	 * @param algorithm a {@link org.opcfoundation.ua.transport.security.SecurityAlgorithm} object.
	 * @param dataToVerify an array of byte.
	 * @param signature an array of byte.
	 * @return a boolean.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public boolean verifyAsymm(PublicKey signingCertificate,
			SecurityAlgorithm algorithm, byte[] dataToVerify, byte[] signature)
					throws ServiceResultException;

	/**
	 * <p>verifySymm.</p>
	 *
	 * @param token a {@link org.opcfoundation.ua.transport.tcp.impl.SecurityToken} object.
	 * @param dataToVerify an array of byte.
	 * @param signature an array of byte.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public void verifySymm(SecurityToken token, byte[] dataToVerify,
			byte[] signature) throws ServiceResultException;

}
