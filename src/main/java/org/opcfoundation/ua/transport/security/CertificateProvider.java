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
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>CertificateProvider interface.</p>
 *
 */
public interface CertificateProvider {

	/**
	 * <p>generateCertificate.</p>
	 *
	 * @param domainName a {@link java.lang.String} object.
	 * @param publicKey a {@link java.security.PublicKey} object.
	 * @param privateKey a {@link java.security.PrivateKey} object.
	 * @param issuerKeys a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 * @param from a {@link java.util.Date} object.
	 * @param to a {@link java.util.Date} object.
	 * @param serialNumber a {@link java.math.BigInteger} object.
	 * @param applicationUri a {@link java.lang.String} object.
	 * @param hostNames a {@link java.lang.String} object.
	 * @return a {@link java.security.cert.X509Certificate} object.
	 * @throws java.security.GeneralSecurityException if any.
	 * @throws java.io.IOException if any.
	 */
	public X509Certificate generateCertificate(String domainName,
			PublicKey publicKey, PrivateKey privateKey, KeyPair issuerKeys,
			Date from, Date to, BigInteger serialNumber, String applicationUri,
			String... hostNames) throws GeneralSecurityException, IOException;

	/**
	 * <p>generateIssuerCert.</p>
	 *
	 * @param publicKey a {@link java.security.PublicKey} object.
	 * @param privateKey a {@link java.security.PrivateKey} object.
	 * @param issuerKeys a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 * @param domainName a {@link java.lang.String} object.
	 * @param serialNumber a {@link java.math.BigInteger} object.
	 * @param startDate a {@link java.util.Date} object.
	 * @param expiryDate a {@link java.util.Date} object.
	 * @return a {@link java.security.cert.X509Certificate} object.
	 * @throws java.security.GeneralSecurityException if any.
	 * @throws java.io.IOException if any.
	 */
	public X509Certificate generateIssuerCert(PublicKey publicKey,
			PrivateKey privateKey, KeyPair issuerKeys, String domainName,
			BigInteger serialNumber, Date startDate, Date expiryDate)
					throws GeneralSecurityException, IOException;

	/**
	 * <p>getSubjectAlternativeNames.</p>
	 *
	 * @param cert a {@link java.security.cert.X509Certificate} object.
	 * @return a {@link java.util.Collection} object.
	 * @throws java.security.cert.CertificateParsingException if any.
	 */
	public Collection<List<?>> getSubjectAlternativeNames(X509Certificate cert)
			throws CertificateParsingException;

	/**
	 * <p>writeToPem.</p>
	 *
	 * @param key a {@link java.security.cert.X509Certificate} object.
	 * @param savePath a {@link java.io.File} object.
	 * @param password a {@link java.lang.String} object.
	 * @param algorithm a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public void writeToPem(X509Certificate key, File savePath, String password,
			String algorithm) throws IOException;

}
