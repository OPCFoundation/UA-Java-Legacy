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
	
	public TransportChannelSettings() {}

	/**
	 * Create Transport channel settings for a opctcp connection
	 * 
	 * @param description
	 * @param configuration
	 * @param clientCertificate
	 * @param privateKey
	 * @param certificateValidator
	 * @param namespaceUris (optional)
	 * @throws RuntimeServiceResultException
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
	 * @param description
	 * @param configuration
	 * @param clientCertificate
	 * @param privateKey
	 * @param certificateValidator
	 * @param namespaceUris
	 * @throws RuntimeServiceResultException
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
	
	
	public Cert getServerCertificate() {
		try {
			return (this.description!=null && this.description.getServerCertificate()!=null && this.description.getServerCertificate().length > 0) 
					? new Cert(this.description.getServerCertificate()) : null;
		} catch (ServiceResultException e) {
			throw new RuntimeServiceResultException(e);
		} 			
	}
	
	
	
	public EndpointDescription getDescription() {
		return description;
	}

	public void setDescription(EndpointDescription description) {
		this.description = description;
	}

	public EndpointConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(EndpointConfiguration configuration) {
		this.configuration = configuration;
	}

	public OpcTcpSettings getOpctcpSettings() {
		return opctcpSettings;
	}

	public void setOpctcpSettings(OpcTcpSettings opctcpSettings) {
		this.opctcpSettings = opctcpSettings;
	}

	public HttpsSettings getHttpsSettings() {
		return httpsSettings;
	}

	public void setHttpsSettings(HttpsSettings httpsSettings) {
		this.httpsSettings = httpsSettings;
	}

	public void readFrom(TransportChannelSettings tcs) {
		if (tcs.description!=null) description = tcs.description.clone();
		if (tcs.configuration!=null) configuration = tcs.configuration.clone();
		if (tcs.opctcpSettings!=null) opctcpSettings.readFrom(tcs.opctcpSettings);
		if (tcs.httpsSettings!=null) httpsSettings.readFrom(tcs.httpsSettings);
		if (tcs.namespaceUris!=null) namespaceUris = tcs.namespaceUris;
	}
	
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
	
	public NamespaceTable getNamespaceUris() {
		return namespaceUris;
	}
	public void setNamespaceUris(NamespaceTable namespaceUris) {
		this.namespaceUris = namespaceUris;
	}
	
	
}
