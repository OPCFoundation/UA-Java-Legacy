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

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.common.StatusCodeDescriptions;

/**
 * This class adapts cert validator to trust manager.
 *
 * Validation is evaluated every time, validator must cache results if needed.
 */
public class CertValidatorTrustManager implements X509TrustManager {
	
	CertificateValidator validator;
	
	// X509Certificate convertion to Cert
	Map<X509Certificate, Cert> certMap = new HashMap<X509Certificate, Cert>();
	
	List<Cert> acceptedCertificates = new ArrayList<Cert>();
	List<Cert> acceptedIssuers = new ArrayList<Cert>();
	X509Certificate[] acceptedIssuersArray;

	/**
	 * <p>Constructor for CertValidatorTrustManager.</p>
	 *
	 * @param validator a {@link org.opcfoundation.ua.transport.security.CertificateValidator} object.
	 */
	public CertValidatorTrustManager(CertificateValidator validator) {
		this.validator = validator;
	}

	/**
	 * Encode X509Certificate to binary and cache 
	 * 
	 * @param c
	 * @return Cert or null if error occured
	 * @throws CertificateException 
	 */
	synchronized void validate(X509Certificate c) throws CertificateException {
		Cert cert = certMap.get( c );
		if ( cert == null ) {
			cert = new Cert(c);
			certMap.put(c, cert);
		}	
		
		// Check validity (only once)
		if (!acceptedCertificates.contains(cert)) {
			StatusCode code = validator.validateCertificate(cert);
			boolean valid = code == null || code.isGood();
			if (valid) {
				acceptedCertificates.add(cert);
			} else {
				throw new CertificateException("Certificate is not valid: " + code);
			}
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public synchronized void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
		for ( X509Certificate c : certs ) {
			validate(c);
		}		
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException 
	{	
		for ( X509Certificate c : certs ) {
			validate(c);
		}		
	}

	/** {@inheritDoc} */
	@Override
	public synchronized X509Certificate[] getAcceptedIssuers() {
		if ( acceptedIssuersArray == null ) {
			int count = acceptedIssuers.size();
			acceptedIssuersArray = new X509Certificate[ count ];
			for (int i=0; i<count; i++) {
				Cert issuerCert = acceptedIssuers.get(i);
				acceptedIssuersArray[i] = issuerCert.getCertificate();
			}
		}
		return acceptedIssuersArray;
	}
	
}
