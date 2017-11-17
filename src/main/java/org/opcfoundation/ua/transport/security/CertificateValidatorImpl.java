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
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.cert.DefaultCertificateValidator;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.StatusCodes;

/**
 * <p>CertificateValidatorImpl class.</p>
 * @deprecated use {@link DefaultCertificateValidator}
 */
@Deprecated
public class CertificateValidatorImpl implements CertificateValidator {

	/** List of explicitely trusted certificates */
	Set<Cert> trustedCertificates = new HashSet<Cert>();
	
	/** List of public keys of explicitely trusted signers */
	Set<PublicKey> trustedPublicKeys = new HashSet<PublicKey>();
	
	/**
	 * <p>Constructor for CertificateValidatorImpl.</p>
	 */
	public CertificateValidatorImpl()
	{		
	}
	
	/**
	 * <p>Constructor for CertificateValidatorImpl.</p>
	 *
	 * @param trustedCertificates a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 */
	public CertificateValidatorImpl(Cert ... trustedCertificates)
	{		
		for (Cert c : trustedCertificates)
			addTrustedCertificate(c);
	}
	
	/**
	 * <p>addTrustedCertificate.</p>
	 *
	 * @param certificate a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 */
	public void addTrustedCertificate(Cert certificate)
	{
		trustedCertificates.add(certificate);
	}
	
	/**
	 * <p>addTrustedSigner.</p>
	 *
	 * @param signer a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 */
	public void addTrustedSigner(Cert signer)
	{
		addTrustedSignerPublicKey(signer.getCertificate().getPublicKey());
	}
	
	/**
	 * <p>addTrustedSignerPublicKey.</p>
	 *
	 * @param signerPublicKey a {@link java.security.PublicKey} object.
	 */
	public void addTrustedSignerPublicKey(PublicKey signerPublicKey)
	{
		trustedPublicKeys.add(signerPublicKey);
	}
	
	/** {@inheritDoc} */
	@Override
	public StatusCode validateCertificate(Cert c) {
		for (Cert certs : trustedCertificates)
			if (certs.equals(c)) return null;
		for (PublicKey key : trustedPublicKeys)
		{	
			try {
				c.getCertificate().verify(key);
				return null;
			} catch (GeneralSecurityException e) {
				continue;
			}
		}
		return new StatusCode(StatusCodes.Bad_SecurityChecksFailed);
	}

	@Override
	public StatusCode validateCertificate(ApplicationDescription applicationDescription, Cert cert) {
		return validateCertificate(cert);
	}

}
