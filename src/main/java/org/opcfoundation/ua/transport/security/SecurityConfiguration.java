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

import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.core.MessageSecurityMode;

/**
 * SecurityConfiguration is binding of local application instance certificate,
 * local private key, remote application instance certificate, security policy
 * and message security mode.
 *
 * @author Mikko Salonen
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class SecurityConfiguration {
	
	/** Constant <code>NO_SECURITY</code> */
	public static final SecurityConfiguration NO_SECURITY;
	
	/** Logger */
	static Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
	
	/** Security mode */
	SecurityMode mode;
	/** Local Application Instance Certificate */
	KeyPair localCertificate = null;
	/** Remote Application Instance Certificate */
	Cert remoteCertificate = null;
	
	/**
	 * <p>Constructor for SecurityConfiguration.</p>
	 *
	 * @param mode a {@link org.opcfoundation.ua.transport.security.SecurityMode} object.
	 * @param localApplicationInstanceCertificate a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 * @param remoteCertificate a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 */
	public SecurityConfiguration(
			SecurityMode mode, 
			KeyPair localApplicationInstanceCertificate,
			Cert remoteCertificate)
	{
		if (mode==null)
			throw new NullPointerException("SecurityMode mode is null");
		this.mode = mode;

		// Spec. Part 6:
		//
		// SenderCertificate
		// This field SHALL be null if the message is not signed.
		//
		// ReceiverCertificateThumbprint
		// This field SHALL be null if the message is not encrypted.

		if ( mode.getMessageSecurityMode().hasSigning() )
		{
			if (localApplicationInstanceCertificate==null)
			  throw new NullPointerException("localApplicationInstanceCertificate is null");
			this.localCertificate = localApplicationInstanceCertificate;
			
//			If the mode is Sign (and not Sign&Encrypt), remote certificate is required for 
//			open secure channel. 
			if (remoteCertificate==null)
				throw new NullPointerException("remoteCertificate is null");
			this.remoteCertificate = remoteCertificate;
		}
	}

	/**
	 * <p>getReceiverCertificate.</p>
	 *
	 * @return a {@link java.security.cert.Certificate} object.
	 */
	public Certificate getReceiverCertificate(){
		if (remoteCertificate==null) return null;
		return remoteCertificate.getCertificate();
	}

	/**
	 * <p>getSecurityPolicy.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.SecurityPolicy} object.
	 */
	public SecurityPolicy getSecurityPolicy() {
		return mode.getSecurityPolicy();
	}

	/**
	 * <p>getEncodedLocalPrivateKey.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getEncodedLocalPrivateKey() {
		if (localCertificate==null) return null;
		return localCertificate.getPrivateKey().getPrivateKey().getEncoded();
	}

	/**
	 * <p>getEncodedLocalCertificate.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getEncodedLocalCertificate() {
		if (localCertificate==null) return null;
		return localCertificate.getCertificate().getEncoded();
	}

	/**
	 * <p>getEncodedRemoteCertificateThumbprint.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getEncodedRemoteCertificateThumbprint() {
		if (remoteCertificate==null) return null;
		return remoteCertificate.getEncodedThumbprint();
	}

	/**
	 * <p>getEncodedRemoteCertificate.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getEncodedRemoteCertificate() {
		if (remoteCertificate==null) return null;
		return remoteCertificate.getEncoded();
	}
	
	/**
	 * <p>getEncodedLocalCertificateThumbprint.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getEncodedLocalCertificateThumbprint() {
		if (localCertificate==null) return null;
		return localCertificate.getCertificate().getEncodedThumbprint();
	}

	/**
	 * <p>getLocalPrivateKey.</p>
	 *
	 * @return a {@link java.security.interfaces.RSAPrivateKey} object.
	 */
	public RSAPrivateKey getLocalPrivateKey() {
		if (localCertificate==null) return null;
		return (RSAPrivateKey) localCertificate.getPrivateKey().getPrivateKey();
	}

	/**
	 * <p>Getter for the field <code>localCertificate</code>.</p>
	 *
	 * @return a {@link java.security.cert.Certificate} object.
	 */
	public Certificate getLocalCertificate() {
		if (localCertificate==null) return null;
		return localCertificate.getCertificate().getCertificate();
	}
		
	/**
	 * <p>Getter for the field <code>remoteCertificate</code>.</p>
	 *
	 * @return a {@link java.security.cert.Certificate} object.
	 */
	public Certificate getRemoteCertificate() {
		if (remoteCertificate==null) return null;
		return remoteCertificate.getCertificate();
	}

	/**
	 * <p>getRemoteCertificate2.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 */
	public org.opcfoundation.ua.transport.security.Cert getRemoteCertificate2() {
		return remoteCertificate;
	}

	/**
	 * <p>getLocalCertificate2.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public KeyPair getLocalCertificate2() {
		return localCertificate;
	}
	
	/**
	 * <p>getSecurityMode.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.SecurityMode} object.
	 */
	public SecurityMode getSecurityMode() {
		return mode;
	}
	
	/**
	 * <p>getMessageSecurityMode.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.MessageSecurityMode} object.
	 */
	public MessageSecurityMode getMessageSecurityMode() {
		return mode.getMessageSecurityMode();
	}	
	
	static {		
		NO_SECURITY = new SecurityConfiguration(SecurityMode.NONE, null, null);
	}
	
}
