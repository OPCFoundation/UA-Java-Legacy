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

package org.opcfoundation.ua.cert;

import java.util.EnumSet;

/**
 * Certificate checks that the Validator performs. The values are used to
 * define which checks passed in the validator.
 *
 * @see DefaultCertificateStoreListener
 */
public enum CertificateCheck {
	/**
	 * is the certificate signed by the certificate itself (this can be OK)
	 */
	SelfSigned,
	
	/**
	 * is the certificate signed by a trusted signer (or self)
	 */
	Signature,
	
	/**
	 * is the certificate already defined as trusted, i.e. it is found from
	 * the TrustedDir or added to the validator using addTrustedCertificate
	 */
	
	Trusted,
	/**
	 * does the certificate contain the applicationUri, equal to the one in
	 * the ApplicationDescription.
	 */
	Uri,
	
	/**
	 * validity of the URI
	 */
	UriValid,
	
	/** is the certificate time valid */
	Validity;
	
	/**
	 * All tests that must pass, to accept the certificate. Defined as
	 * including EnumSet.of(Trusted, Validity, Signature, Uri)
	 */
	public static EnumSet<CertificateCheck> COMPULSORY = EnumSet.of(Trusted, Validity, Signature, Uri);

}