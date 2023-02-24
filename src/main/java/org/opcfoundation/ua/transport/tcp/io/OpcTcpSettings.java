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

import java.net.Socket;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.EnumSet;

import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.PrivKey;

/**
 * Settings used when making opc.tcp connections.
 * 
 */
public class OpcTcpSettings implements Cloneable{

	PrivKey privKey;
	Cert clientCertificate;
	CertificateValidator certificateValidator;
	EnumSet<Flag> flags = EnumSet.noneOf(Flag.class);
	int handshakeTimeout = -1;
	int connectTimeout = -1;
	int reverseHelloAcceptTimeout = -1;
	int maxConnections = 100;
	int maxSecureChannelsPerConnection = 1;
	
	public enum Flag {
		/**
		 * In multithread mode, depending on implementation, channels 
		 * encrypt and decrypt messages simultaneously in multiple threads.
		 * 
		 * This allows higher throughput in secured data intensive applications with 
		 * large messages.
		 */
		MultiThread
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
	 * <p>Setter for the field <code>clientCertificate</code>.</p>
	 *
	 * @param clientCertificate a {@link java.security.cert.X509Certificate} object.
	 * @throws java.security.cert.CertificateEncodingException if any.
	 */
	public void setClientCertificate(X509Certificate clientCertificate) throws CertificateEncodingException {
		this.clientCertificate = new Cert(clientCertificate);
	}

	/**
	 * <p>Getter for the field <code>certificateValidator</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.CertificateValidator} object.
	 */
	public CertificateValidator getCertificateValidator() {
		return certificateValidator;
	}
	/**
	 * <p>Setter for the field <code>certificateValidator</code>.</p>
	 *
	 * @param certificateValidator a {@link org.opcfoundation.ua.transport.security.CertificateValidator} object.
	 */
	public void setCertificateValidator(CertificateValidator certificateValidator) {
		this.certificateValidator = certificateValidator;
	}

	/**
	 * <p>Getter for the field <code>privKey</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.PrivKey} object.
	 */
	public PrivKey getPrivKey() {
		return privKey;
	}

	/**
	 * <p>Setter for the field <code>privKey</code>.</p>
	 *
	 * @param privKey a {@link org.opcfoundation.ua.transport.security.PrivKey} object.
	 */
	public void setPrivKey(PrivKey privKey) {
		this.privKey = privKey;
	}

	/**
	 * <p>Setter for the field <code>clientCertificate</code>.</p>
	 *
	 * @param clientCertificate a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 */
	public void setClientCertificate(Cert clientCertificate) {
		this.clientCertificate = clientCertificate;
	}
	
	/**
	 * <p>Getter for the field <code>flags</code>.</p>
	 *
	 * @return a {@link java.util.EnumSet} object.
	 */
	public EnumSet<Flag> getFlags() {
		return flags;
	}

	/**
	 * <p>Setter for the field <code>flags</code>.</p>
	 *
	 * @param flags a {@link java.util.EnumSet} object.
	 */
	public void setFlags(EnumSet<Flag> flags) {
		this.flags = flags;
	}	
	
	/**
	 * Timeout for the initial handshake (Hello/Acknowledge exchange) after a socket connection is opened.
	 */
	public int getHandshakeTimeout() {
		return handshakeTimeout;
	}
	
	/**
	 * See {@link #getHandshakeTimeout()}.
	 */
	public void setHandshakeTimeout(int handshakeTimeout) {
		this.handshakeTimeout = handshakeTimeout;
	}
	
	/**
	 * Get the timeout used to listen on reverse connections, in milliseconds. 
	 * Use -1 to use the default of {@link TcpConnection#getDefaultReverseHelloAcceptTimeout()}.
	 * 0 is infinite.
	 */
	public int getReverseHelloAcceptTimeout() {
		return reverseHelloAcceptTimeout;
	}
	
	/**
	 * See {@link #getReverseHelloAcceptTimeout()}.
	 */
	public void setReverseHelloAcceptTimeout(int reverseHelloListenTimeout) {
		this.reverseHelloAcceptTimeout = reverseHelloListenTimeout;
	}
	
	/**
	 * Timeout for the initial socket connect operation, in milliseconds. NOTE! this value is passed to {@link Socket#connect(java.net.SocketAddress, int)}.
	 * However in practice the maximum timeout is determined by the Operating System. 
	 * If this is -1, then {@link TcpConnection#getDefaultHandshakeTimeout()} is used.
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}
	
	/**
	 * See {@link #getConnectTimeout()}.
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	/**
	 * <p>readFrom.</p>
	 *
	 * @param tcs a {@link org.opcfoundation.ua.transport.tcp.io.OpcTcpSettings} object.
	 */
	public void readFrom(OpcTcpSettings tcs) {
		if (tcs.clientCertificate!=null) clientCertificate = tcs.clientCertificate;
		if (tcs.certificateValidator!=null) certificateValidator = tcs.certificateValidator;
		if (tcs.privKey!=null) privKey = tcs.privKey;
		flags = tcs.flags;
		this.reverseHelloAcceptTimeout = tcs.reverseHelloAcceptTimeout;
		this.handshakeTimeout = tcs.handshakeTimeout;
		this.connectTimeout = tcs.connectTimeout;
	}
	
	/** {@inheritDoc} */
	@Override
	public OpcTcpSettings clone() {
		OpcTcpSettings result = new OpcTcpSettings();

		result.setClientCertificate(clientCertificate);
		result.setCertificateValidator(certificateValidator);
		result.setPrivKey(privKey);
		
		result.flags = flags.clone();
		
		result.setConnectTimeout(connectTimeout);
		result.setHandshakeTimeout(handshakeTimeout);
		result.setReverseHelloAcceptTimeout(reverseHelloAcceptTimeout);
		return result;
	}
	public int getMaxConnections() {
		return maxConnections;
	}
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}
	public int getMaxSecureChannelsPerConnection() {
		return maxSecureChannelsPerConnection;
	}
	public void setMaxSecureChannelsPerConnection(
			int maxSecureChannelsPerConnection) {
		this.maxSecureChannelsPerConnection = maxSecureChannelsPerConnection;
	}	


}
