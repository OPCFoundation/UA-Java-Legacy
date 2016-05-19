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

package org.opcfoundation.ua.application;

import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.SessionDiagnosticsDataType;
import org.opcfoundation.ua.core.SignedSoftwareCertificate;
import org.opcfoundation.ua.transport.SecureChannel;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.PrivKey;

/**
 * Session
 */
public class Session {
	
	/** Server Endpoint */
	EndpointDescription endpoint;
	/** Session Name */
	String name;
	/** Session ID */
	NodeId sessionId;
	/** Server nonce */
	byte[] serverNonce;
	/** Client nonce */
	byte[] clientNonce;
	/** NodeId that contains SessionDiagnosticsInfo, see {@link SessionDiagnosticsDataType} */
	NodeId diagnosticsInfo;
	/** Server assigned authentication token. It is passed in every request and response */
	NodeId authenticationToken;
	/** Inactivity timeout time */
	double sessionTimeout;
	/** Max request message size */
	UnsignedInteger maxRequestMessageSize;
	/** Server software Certificates */
	SignedSoftwareCertificate[] serverSoftwareCertificates;
	/** Server certificate */
	Cert serverCertificate;
	/** Client certificate */
	Cert clientCertificate;
	/** Client private key (optional) */
	PrivKey clientPrivateKey;
	/** Server private key (optional) */
	PrivKey serverPrivateKey;

	Session() {}

	/**
	 * Create new unactivated session channel.
	 * Session Channel will be wrapped over secure channel.
	 * 
	 * @param channel securechannel secure channel to wrap session channel over
	 * @return session channel
	 */
	public SessionChannel createSessionChannel(SecureChannel channel, Client client)
	{
		return new SessionChannel(client, this, channel);
	}

	public EndpointDescription getEndpoint() {
		return endpoint;
	}

	public byte[] getServerNonce() {
		return serverNonce;
	}

	public NodeId getDiagnosticsInfo() {
		return diagnosticsInfo;
	}

	public NodeId getAuthenticationToken() {
		return authenticationToken;
	}

	public double getSessionTimeout() {
		return sessionTimeout;
	}

	public UnsignedInteger getMaxRequestMessageSize() {
		return maxRequestMessageSize;
	}

	public SignedSoftwareCertificate[] getServerSoftwareCertificates() {
		return serverSoftwareCertificates;
	}

	public Cert getServerCertificate() {
		return serverCertificate;
	}

	public String getName() {
		return name;
	}

	public NodeId getSessionId() {
		return sessionId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getClientNonce() {
		return clientNonce;
	}

	public Cert getClientCertificate() {
		return clientCertificate;
	}

	public PrivKey getClientPrivateKey() {
		return clientPrivateKey;
	}

	public PrivKey getServerPrivateKey() {
		return serverPrivateKey;
	}
	
	

}
