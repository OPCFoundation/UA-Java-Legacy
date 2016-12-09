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

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collection;

import javax.net.ssl.X509KeyManager;


/**
 * This class adapts a collection of key pair classes into a X509KeyManager.
 *
 * @author toni.kalajainen@semantum.fi
 */
public class KeyPairsKeyManager implements X509KeyManager {

	Collection<KeyPair> keypairs;
	
	/**
	 * <p>Constructor for KeyPairsKeyManager.</p>
	 *
	 * @param keypairs a {@link java.util.Collection} object.
	 */
	public KeyPairsKeyManager(Collection<KeyPair> keypairs) {
		this.keypairs = keypairs;
	}

	/** {@inheritDoc} */
	@Override
	public String chooseClientAlias(String[] arg0, Principal[] arg1, Socket arg2) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public X509Certificate[] getCertificateChain(String arg0) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String[] getClientAliases(String arg0, Principal[] arg1) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public PrivateKey getPrivateKey(String arg0) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String[] getServerAliases(String arg0, Principal[] arg1) {
		return null;
	}

	/**
	 * <p>getKeyPairs.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<KeyPair> getKeyPairs() {
		return keypairs;
	}
}
