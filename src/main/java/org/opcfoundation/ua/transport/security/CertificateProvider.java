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
 * Interface for creating certificates. 
 * Designed for stack internal use to allow pluggable system of providers.
 *
 */
public interface CertificateProvider {

	public byte[] base64Decode(String string);

	public byte[] base64Decode(byte[] bytes);

	public String base64Encode(byte[] bytes);
	
	public X509Certificate generateCertificate(String domainName,
			PublicKey publicKey, PrivateKey privateKey, KeyPair issuerKeys,
			Date from, Date to, BigInteger serialNumber, String applicationUri,
			String... hostNames) throws GeneralSecurityException, IOException;

	public X509Certificate generateIssuerCert(PublicKey publicKey,
			PrivateKey privateKey, KeyPair issuerKeys, String domainName,
			BigInteger serialNumber, Date startDate, Date expiryDate)
					throws GeneralSecurityException, IOException;

	public Collection<List<?>> getSubjectAlternativeNames(X509Certificate cert)
			throws CertificateParsingException;

	public void writeToPem(X509Certificate key, File savePath, String password,
			String algorithm) throws IOException;

}
