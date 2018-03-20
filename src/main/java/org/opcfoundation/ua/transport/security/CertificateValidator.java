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

import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.core.ApplicationDescription;

/**
 * Certificate Validator estimates the validity of a certificate.
 *
 * @see AllowAllCertificatesValidator
 * @see DefaultCertificateValidator 
 */
public interface CertificateValidator {
	
	public static final CertificateValidator ALLOW_ALL = new AllowAllCertificatesValidator(); 

	/**
	 * Validate (peer's) certificate
	 *
	 * @param cert the certificate
	 * @return Bad statuscode to reject the certificate or Good to accept.
	 */
	StatusCode validateCertificate(Cert cert);
	
	/**
	 * Validate the certificate against the ApplicationDescription.
	 * 
	 * @param applicationDescription the application description
	 * @param cert the certificate
	 * @return Bad statuscode to reject the certificate or Good to accept.
	 */
	StatusCode validateCertificate(ApplicationDescription applicationDescription, Cert cert);
	
}
