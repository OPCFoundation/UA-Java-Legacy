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

package org.opcfoundation.ua.transport;

import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.common.NamespaceTable;
import org.opcfoundation.ua.common.RuntimeServiceResultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.transport.https.HttpsSettings;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.PrivKey;
import org.opcfoundation.ua.transport.tcp.io.OpcTcpSettings;

/**
 * Transport channel settings for Client.
 */
public class TransportChannelSettings implements Cloneable {

	///// Endpoint Settings /////
	EndpointDescription description;
	EndpointConfiguration configuration;
	NamespaceTable namespaceUris;
	
	OpcTcpSettings opctcpSettings = new OpcTcpSettings();
	
	///// HTTPS Settings /////	
	HttpsSettings httpsSettings = new HttpsSettings();
	
	/**
	 * <p>Constructor for TransportChannelSettings.</p>
	 */
	public TransportChannelSettings() {}

	/**
	 * Create Transport channel settings for a opctcp connection
	 *
	 * @param description a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 * @param configuration a {@link org.opcfoundation.ua.core.EndpointConfiguration} object.
	 * @param clientCertificate a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 * @param privateKey a {@link org.opcfoundation.ua.transport.security.PrivKey} object.
	 * @param certificateValidator a {@link org.opcfoundation.ua.transport.security.CertificateValidator} object.
	 * @param namespaceUris (optional)
	 * @throws org.opcfoundation.ua.common.RuntimeServiceResultException if any.
	 */
	public TransportChannelSettings(
			EndpointDescription description,
			EndpointConfiguration configuration,
			Cert clientCertificate,
			PrivKey privateKey,
			CertificateValidator certificateValidator,
			NamespaceTable namespaceUris) 
	throws RuntimeServiceResultException {
		super();
		this.configuration = configuration;
		this.description = description;
		
		this.opctcpSettings.setClientCertificate(clientCertificate);
		this.opctcpSettings.setCertificateValidator(certificateValidator);
		this.opctcpSettings.setPrivKey(privateKey);
		if (namespaceUris!=null) this.namespaceUris = namespaceUris;
	}

	/**
	 * Create Transport channel settings for a https connection
	 *
	 * @param description a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 * @param configuration a {@link org.opcfoundation.ua.core.EndpointConfiguration} object.
	 * @throws org.opcfoundation.ua.common.RuntimeServiceResultException if any.
	 * @param keypair a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 * @param certValidator a {@link org.opcfoundation.ua.transport.security.CertificateValidator} object.
	 * @param hostnameVerifier a {@link org.apache.http.conn.ssl.X509HostnameVerifier} object.
	 */
	public TransportChannelSettings(
			EndpointDescription description,
			EndpointConfiguration configuration,
			KeyPair keypair, 
			CertificateValidator certValidator, 
			X509HostnameVerifier hostnameVerifier
			) 
	throws RuntimeServiceResultException {
		super();
		this.configuration = configuration;
		this.description = description;
		this.httpsSettings.setKeyPair(keypair);
		this.httpsSettings.setCertificateValidator(certValidator);
		this.httpsSettings.setHostnameVerifier( hostnameVerifier );
	}
	
	
	/**
	 * <p>getServerCertificate.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.Cert} object.
	 */
	public Cert getServerCertificate() {
		try {
			if(this.description!=null && this.description.getServerCertificate()!=null && this.description.getServerCertificate().getLength() > 0){
			  return new Cert(ByteString.asByteArray(this.description.getServerCertificate()));
			}else{
			  return null;
			}
		} catch (ServiceResultException e) {
			throw new RuntimeServiceResultException(e);
		} 			
	}
	
	
	
	/**
	 * <p>Getter for the field <code>description</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 */
	public EndpointDescription getDescription() {
		return description;
	}

	/**
	 * <p>Setter for the field <code>description</code>.</p>
	 *
	 * @param description a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 */
	public void setDescription(EndpointDescription description) {
		this.description = description;
	}

	/**
	 * <p>Getter for the field <code>configuration</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.EndpointConfiguration} object.
	 */
	public EndpointConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * <p>Setter for the field <code>configuration</code>.</p>
	 *
	 * @param configuration a {@link org.opcfoundation.ua.core.EndpointConfiguration} object.
	 */
	public void setConfiguration(EndpointConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * <p>Getter for the field <code>opctcpSettings</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.tcp.io.OpcTcpSettings} object.
	 */
	public OpcTcpSettings getOpctcpSettings() {
		return opctcpSettings;
	}

	/**
	 * <p>Setter for the field <code>opctcpSettings</code>.</p>
	 *
	 * @param opctcpSettings a {@link org.opcfoundation.ua.transport.tcp.io.OpcTcpSettings} object.
	 */
	public void setOpctcpSettings(OpcTcpSettings opctcpSettings) {
		this.opctcpSettings = opctcpSettings;
	}

	/**
	 * <p>Getter for the field <code>httpsSettings</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.https.HttpsSettings} object.
	 */
	public HttpsSettings getHttpsSettings() {
		return httpsSettings;
	}

	/**
	 * <p>Setter for the field <code>httpsSettings</code>.</p>
	 *
	 * @param httpsSettings a {@link org.opcfoundation.ua.transport.https.HttpsSettings} object.
	 */
	public void setHttpsSettings(HttpsSettings httpsSettings) {
		this.httpsSettings = httpsSettings;
	}

	/**
	 * <p>readFrom.</p>
	 *
	 * @param tcs a {@link org.opcfoundation.ua.transport.TransportChannelSettings} object.
	 */
	public void readFrom(TransportChannelSettings tcs) {
		if (tcs.description!=null) description = tcs.description.clone();
		if (tcs.configuration!=null) configuration = tcs.configuration.clone();
		if (tcs.opctcpSettings!=null) opctcpSettings.readFrom(tcs.opctcpSettings);
		if (tcs.httpsSettings!=null) httpsSettings.readFrom(tcs.httpsSettings);
		if (tcs.namespaceUris!=null) namespaceUris = tcs.namespaceUris;
	}
	
	/** {@inheritDoc} */
	@Override
	public TransportChannelSettings clone() {
		TransportChannelSettings result = new TransportChannelSettings();
		if (description!=null)
			result.setDescription(description.clone());
		if (configuration!=null)
			result.setConfiguration(configuration.clone());
		if (opctcpSettings!=null)
			result.opctcpSettings = opctcpSettings.clone();
		if (httpsSettings!=null)
			result.httpsSettings = httpsSettings.clone();
		if (namespaceUris!=null)
			result.setNamespaceUris(namespaceUris);
		
		return result;
	}	
	
	/**
	 * <p>Getter for the field <code>namespaceUris</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.common.NamespaceTable} object.
	 */
	public NamespaceTable getNamespaceUris() {
		return namespaceUris;
	}
	/**
	 * <p>Setter for the field <code>namespaceUris</code>.</p>
	 *
	 * @param namespaceUris a {@link org.opcfoundation.ua.common.NamespaceTable} object.
	 */
	public void setNamespaceUris(NamespaceTable namespaceUris) {
		this.namespaceUris = namespaceUris;
	}
	
	
}
