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

import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.transport.security.Cert;

/**
 * An event handler interface for reacting to certificate validation handling
 * results.
 */
public interface DefaultCertificateValidatorListener {

	/**
	 * Handle certificate validation. You can use the event to define your
	 * custom handler to decide whether to trust or reject the certificate -
	 * permanently or just this time.
	 * <p>
	 * The method is called once the actual validator has already checked the
	 * certificate and provides the results of the checks in the parameters. If
	 * isTrusted, isSignVerified, isValid are all false, you should normally
	 * accept the certificate.
	 * 
	 * @param certificate
	 *            the certificate that is being validated
	 * @param passedChecks
	 *            the certification checks that failed
	 * @return validation result: accept or reject; once or permanently?
	 */
	ValidationResult onValidate(Cert certificate, ApplicationDescription applicationDescription,
			EnumSet<CertificateCheck> passedChecks);

}
