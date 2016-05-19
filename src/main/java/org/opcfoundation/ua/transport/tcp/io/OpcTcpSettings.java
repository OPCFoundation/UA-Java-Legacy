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
package org.opcfoundation.ua.transport.tcp.io;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.EnumSet;

import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.PrivKey;

public class OpcTcpSettings {

	PrivKey privKey;
	Cert clientCertificate;
	CertificateValidator certificateValidator;
	EnumSet<Flag> flags = EnumSet.noneOf(Flag.class);	
	public enum Flag {
		/**
		 * In multithread mode, depending on implementation, channels 
		 * encrypt & decrypt messages simultaneously in multiple threads.
		 * 
		 * This allows higher throughput in secured data intensive applications with 
		 * large messages.
		 */
		MultiThread
	}

	
	
	public Cert getClientCertificate() {
		return clientCertificate;
	}
	public void setClientCertificate(X509Certificate clientCertificate) throws CertificateEncodingException {
		this.clientCertificate = new Cert(clientCertificate);
	}

	public CertificateValidator getCertificateValidator() {
		return certificateValidator;
	}
	public void setCertificateValidator(CertificateValidator certificateValidator) {
		this.certificateValidator = certificateValidator;
	}

	public PrivKey getPrivKey() {
		return privKey;
	}

	public void setPrivKey(PrivKey privKey) {
		this.privKey = privKey;
	}

	public void setClientCertificate(Cert clientCertificate) {
		this.clientCertificate = clientCertificate;
	}
	
	public EnumSet<Flag> getFlags() {
		return flags;
	}

	public void setFlags(EnumSet<Flag> flags) {
		this.flags = flags;
	}	
	
	
	public void readFrom(OpcTcpSettings tcs) {
		if (tcs.clientCertificate!=null) clientCertificate = tcs.clientCertificate;
		if (tcs.certificateValidator!=null) certificateValidator = tcs.certificateValidator;
		if (tcs.privKey!=null) privKey = tcs.privKey;
		flags = tcs.flags;
	}
	
	@Override
	public OpcTcpSettings clone() {
		OpcTcpSettings result = new OpcTcpSettings();

		result.setClientCertificate(clientCertificate);
		result.setCertificateValidator(certificateValidator);
		result.setPrivKey(privKey);
		
		result.flags = flags.clone();
		return result;
	}	


}
