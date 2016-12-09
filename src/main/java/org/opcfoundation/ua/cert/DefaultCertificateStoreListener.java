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

import java.security.cert.X509CRL;

import org.opcfoundation.ua.transport.security.Cert;

/**
 * Listener for Cert changes for {@link DefaultCertificateValidator}
 */
public interface DefaultCertificateStoreListener {

	/**
	 * Called after {@link DefaultCertificateValidator} adds a Certificate
	 * to Rejected certificates
	 * 
	 * @param cert
	 *            the added certificate
	 */
	public void onRejectedCertificateAdded(Cert cert);

	/**
	 * Called after {@link DefaultCertificateValidator} adds a Certificate
	 * Revoked List.
	 * 
	 * @param crl added revocation list
	 */
	public void onRevokedListAdded(X509CRL crl);

	/**
	 * Called after {@link DefaultCertificateValidator} adds a Certificate
	 * to Trusted certificates
	 * 
	 * @param cert
	 *            the added certificate
	 */
	public void onTrustedCertificateAdded(Cert cert);

}
