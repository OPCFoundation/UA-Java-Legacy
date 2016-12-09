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
import java.util.Set;

import org.opcfoundation.ua.transport.security.Cert;

/**
 * An interface modeling a certificate store.
 * Note for implementers! The methods of this interface
 * may change in the future. If there is any error in 
 * implemented method, throw a RuntimeException or
 * subclass of it.
 */
public interface CertificateStore {
	
	/**
	 * Get all certificates that are stored as Trusted.
	 * In a directory based PKI store, this would mean
	 * that the set contains all certs in 'certs' or
	 * 'trusted' folder. It is possible and allowed to
	 * return certificates that are revoked in this set,
	 * i.e. the implementation of this method should not
	 * perform any validation, user of the CertificateStore
	 * should check the revocation against the {@link #getRevocationLists()}
	 * revocation lists.
	 * 
	 * @return the certificates or empty set if none. The
	 * 		returned set should be treated as immutable.
	 */
	Set<Cert> getTrustedCerts();
	
	/**
	 * Get all certificates that are stored as Rejected.
	 * Note! implementers may choose to not store rejected
	 * certificates, in this case return empty set.
	 * Note! If the implementor chooses to do so, this
	 * set may also contain certificates that were previously
	 * accepted once.
	 * 
	 * 
	 * @return the certificates that are rejected or empty
	 * 		set if none. The returned set should be treated as immutable.
	 */
	Set<Cert> getRejectedCerts();
	
	/**
	 * Get all revocation lists the store has.
	 * 
	 * @return revocation lists, or empty set if none.
	 * 		The returned set should be treated as immutable.
	 */
	Set<X509CRL> getRevocationLists();
	
	/**
	 * Add a certificate to the store. Note! it is
	 * implementation specific which types are stored,
	 * e.g. an implementation may choose to store only
	 * trusted certificates. Implementation should
	 * store the certificates in a persistent storage.
	 * AcceptOnce type certificates could be stored as rejected,
	 * if there is need to accept them later (using a mechanism
	 * outside of this interface). 
	 * 
	 * @param type trusted, rejected or accept once.
	 * @param certificate the certificate to add.
	 */
	void addCertificate(ValidationResult type, Cert certificate);
	
}
