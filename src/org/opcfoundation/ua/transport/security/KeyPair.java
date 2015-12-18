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
import java.net.URL;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Valid and encodeable certificate, including signed public key and private key
 * This class aggregates private and public keys.
 * 
 * TODO Use {@link KeyPair} Instead?
 * 
 * @author Toni Kalajainen (toni.kalajainen@iki.fi)
 * @author Mikko Salonen
 */
public final class KeyPair {

	public final Cert certificate;
	public final PrivKey privateKey;

	/** 
	 * Load Certificate & Private key pair from X.509 and keystore file
	 * 
	 * @param certificateFile
	 * @param privateKeyFile
	 * @param privateKeyPassword
	 * @return a new keypair instance
	 * @throws IOException 
	 * @throws KeyStoreException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnrecoverableKeyException 
	 */
	public static KeyPair load(URL certificateFile, URL privateKeyFile, String privateKeyPassword) 
	throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, KeyStoreException
	{
		Cert cert = Cert.load(certificateFile);
		PrivKey privKey = PrivKey.loadFromKeyStore(privateKeyFile, privateKeyPassword);
		return new KeyPair(cert, privKey);
	}
	
	/** 
	 * Load Certificate & Private key pair from X.509 and keystore file
	 * 
	 * @param certificateFile
	 * @param privateKeyFile
	 * @param privateKeyPassword
	 * @return a new keypair instance
	 * @throws IOException 
	 * @throws KeyStoreException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnrecoverableKeyException 
	 */
	public static KeyPair load(File certificateFile, File privateKeyFile, String privateKeyPassword) 
	throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, KeyStoreException
	{
		Cert cert = Cert.load(certificateFile);
		PrivKey privKey = PrivKey.loadFromKeyStore(privateKeyFile, privateKeyPassword);
		return new KeyPair(cert, privKey);
	}
	
	public void save(File certificateFile, File privateKeyFile) 
	throws IOException
	{
		certificate.save(certificateFile);
		privateKey.save(privateKeyFile);
	}
	
	public void save(File certificateFile, File privateKeyFile, String privateKeyPassword) 
	throws IOException
	{
		certificate.save(certificateFile);
		privateKey.save(privateKeyFile, privateKeyPassword);
	}

	public KeyPair(Cert certificate, PrivKey privateKey)
	{
		if (certificate==null || privateKey==null)
			throw new IllegalArgumentException("null arg");
		this.certificate = certificate;
		this.privateKey = privateKey;
	}

	public Cert getCertificate() {
		return certificate;
	}

	public PrivKey getPrivateKey() {
		return privateKey;
	}

	@Override
	public String toString() {
		return certificate.toString();
	}
	
}
