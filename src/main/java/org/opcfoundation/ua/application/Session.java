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

import org.opcfoundation.ua.builtintypes.ByteString;
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
	ByteString clientNonce;
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
	 * @param client the client
	 * @return session channel
	 */
	public SessionChannel createSessionChannel(SecureChannel channel, Client client)
	{
		return new SessionChannel(client, this, channel);
	}

	/**
	 * <p>Getter for the field <code>endpoint</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 */
	public EndpointDescription getEndpoint() {
		return endpoint;
	}

	/**
	 * <p>Getter for the field <code>serverNonce</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getServerNonce() {
		return serverNonce;
	}

	/**
	 * <p>Getter for the field <code>diagnosticsInfo</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 */
	public NodeId getDiagnosticsInfo() {
		return diagnosticsInfo;
	}

	/**
	 * <p>Getter for the field <code>authenticationToken</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 */
	public NodeId getAuthenticationToken() {
		return authenticationToken;
	}

	/**
	 * <p>Getter for the field <code>sessionTimeout</code>.</p>
	 *
	 * @return a double.
	 */
	public double getSessionTimeout() {
		return sessionTimeout;
	}

	/**
	 * <p>Getter for the field <code>maxRequestMessageSize</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public UnsignedInteger getMaxRequestMessageSize() {
		return maxRequestMessageSize;
	}

	/**
	 * <p>Getter for the field <code>serverSoftwareCertificates</code>.</p>
	 *
	 * @return an array of {@link org.opcfoundation.ua.core.SignedSoftwareCertificate} objects.
	 */
	public SignedSoftwareCertificate[] getServerSoftwareCertificates() {
		return serverSoftwareCertificates;
	}

	/**
	 * <p>Getter for the field <code>serverCertificate</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 */
	public Cert getServerCertificate() {
		return serverCertificate;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Getter for the field <code>sessionId</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 */
	public NodeId getSessionId() {
		return sessionId;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>Getter for the field <code>clientNonce</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public ByteString getClientNonce() {
		return clientNonce;
	}

	/**
	 * <p>Getter for the field <code>clientCertificate</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 */
	public Cert getClientCertificate() {
		return clientCertificate;
	}

	/**
	 * <p>Getter for the field <code>clientPrivateKey</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.PrivKey} object.
	 */
	public PrivKey getClientPrivateKey() {
		return clientPrivateKey;
	}

	/**
	 * <p>Getter for the field <code>serverPrivateKey</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.PrivKey} object.
	 */
	public PrivKey getServerPrivateKey() {
		return serverPrivateKey;
	}
	
	

}
