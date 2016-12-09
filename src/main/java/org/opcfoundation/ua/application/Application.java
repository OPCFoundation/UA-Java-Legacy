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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.common.NamespaceTable;
import org.opcfoundation.ua.common.ServerTable;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.SignedSoftwareCertificate;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.transport.EndpointServer;
import org.opcfoundation.ua.transport.UriUtil;
import org.opcfoundation.ua.transport.https.HttpsServer;
import org.opcfoundation.ua.transport.https.HttpsSettings;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.tcp.io.OpcTcpSettings;
import org.opcfoundation.ua.transport.tcp.nio.OpcTcpServer;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.StackUtils;

/**
 * This class contains the mechanisms that are common for both client and server
 * application.
 *
 * @see Client OPC UA Client Application
 * @see Server OPC UA Server Application
 */
public class Application {
	private final static Logger logger = LoggerFactory.getLogger(Application.class);		

	/** Application description */
	ApplicationDescription applicationDescription = new ApplicationDescription();
	/** Application Instance Certificates */
	List<KeyPair> applicationInstanceCertificates = new CopyOnWriteArrayList<KeyPair>();
	/** Software Certificates */
	List<SignedSoftwareCertificate> softwareCertificates = new CopyOnWriteArrayList<SignedSoftwareCertificate>();
	/** Locales */
	List<Locale> locales = new CopyOnWriteArrayList<Locale>();
	/** Https Settings for Https Endpoint Servers */
	HttpsSettings httpsSettings = new HttpsSettings();
	/** OpcTcp Settings for OpcTcp Endpoint Servers */
	OpcTcpSettings opctcpSettings = new OpcTcpSettings();
	/** Https Server */
	HttpsServer httpsServer;
	/** OpcTcp Server */
	OpcTcpServer opctcpServer;

	private EncoderContext encoderContext = new EncoderContext(new NamespaceTable(), new ServerTable(), StackUtils.getDefaultSerializer());
	
	/**
	 * <p>Constructor for Application.</p>
	 */
	public Application()
	{
		// Create application name
		String publicHostname = "";
		try {
			publicHostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
		}
		
		applicationDescription.setApplicationUri( "urn:"+publicHostname+":"+UUID.randomUUID() );
		
		getOpctcpSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
		getHttpsSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
	}

	/**
	 * <p>Getter for the field <code>encoderContext</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public EncoderContext getEncoderContext() {
		return encoderContext;
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
	 * <p>getOrCreateEndpointServer.</p>
	 *
	 * @param scheme a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.transport.EndpointServer} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public synchronized EndpointServer getOrCreateEndpointServer(String scheme) throws ServiceResultException {
		if ( scheme.equals( UriUtil.SCHEME_OPCTCP ) ) {
			return getOrCreateOpcTcpServer();
		} else 
		if ( scheme.equals( UriUtil.SCHEME_HTTP ) || scheme.equals( UriUtil.SCHEME_HTTPS )) {
			return getOrCreateHttpsServer();
		} else throw new ServiceResultException(StatusCodes.Bad_UnexpectedError, "Cannot find EndpointServer for scheme "+scheme);
	}
	
	/**
	 * <p>getOrCreateHttpsServer.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.https.HttpsServer} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public synchronized HttpsServer getOrCreateHttpsServer() throws ServiceResultException {
		if ( httpsServer == null ) {
			httpsServer = new HttpsServer(this);
		}
		return httpsServer;
	}
	
	/**
	 * <p>getOrCreateOpcTcpServer.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.tcp.nio.OpcTcpServer} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public synchronized OpcTcpServer getOrCreateOpcTcpServer() throws ServiceResultException {
		if ( opctcpServer == null ) {
			opctcpServer = new OpcTcpServer( this );
		}
		return opctcpServer;
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
	 * <p>Getter for the field <code>applicationDescription</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.ApplicationDescription} object.
	 */
	public ApplicationDescription getApplicationDescription()
	{
		return applicationDescription;
	}
	
	/**
	 * <p>Getter for the field <code>softwareCertificates</code>.</p>
	 *
	 * @return an array of {@link org.opcfoundation.ua.core.SignedSoftwareCertificate} objects.
	 */
	public SignedSoftwareCertificate[] getSoftwareCertificates()
	{
		return softwareCertificates.toArray( new SignedSoftwareCertificate[softwareCertificates.size()] );
	}
	
	/**
	 * <p>addSoftwareCertificate.</p>
	 *
	 * @param cert a {@link org.opcfoundation.ua.core.SignedSoftwareCertificate} object.
	 */
	public void addSoftwareCertificate(SignedSoftwareCertificate cert)
	{
		if (cert==null) throw new IllegalArgumentException("null arg");
		softwareCertificates.add(cert);
	}
	
	/**
	 * <p>Getter for the field <code>applicationInstanceCertificates</code>.</p>
	 *
	 * @return an array of {@link org.opcfoundation.ua.transport.security.KeyPair} objects.
	 */
	public KeyPair[] getApplicationInstanceCertificates()
	{
		return applicationInstanceCertificates.toArray( new KeyPair[applicationInstanceCertificates.size()] );
	}
	
	/**
	 * <p>addApplicationInstanceCertificate.</p>
	 *
	 * @param cert a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public void addApplicationInstanceCertificate(KeyPair cert)
	{
		if (cert==null) throw new IllegalArgumentException("null arg");
		applicationInstanceCertificates.add(cert);
	}

	/**
	 * <p>removeApplicationInstanceCertificate.</p>
	 *
	 * @param applicationInstanceCertificate a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public void removeApplicationInstanceCertificate(KeyPair applicationInstanceCertificate)
	{
		applicationInstanceCertificates.remove( applicationInstanceCertificate );
	}

	/**
	 * <p>getApplicationInstanceCertificate.</p>
	 *
	 * @param thumb an array of byte.
	 * @return a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public KeyPair getApplicationInstanceCertificate(byte[] thumb) 
	{
		logger.debug("getApplicationInstanceCertificate: expected={}", CryptoUtil.toHex(thumb));
		if (thumb != null) {
			int i = 0;
			for (KeyPair cert : applicationInstanceCertificates) {
				byte[] encodedThumbprint = cert.getCertificate()
						.getEncodedThumbprint();
				logger.debug("getApplicationInstanceCertificate: cert[{}]={}", i++, CryptoUtil.toHex(encodedThumbprint));
				if (Arrays.equals(encodedThumbprint, thumb))
					return cert;
			}
		}
		return null;
	}
		
	/**
	 * <p>getApplicationInstanceCertificate.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.KeyPair} object.
	 */
	public KeyPair getApplicationInstanceCertificate()
	{
		final int index = applicationInstanceCertificates.size()-1;
		if (index < 0)
			return null;
		return applicationInstanceCertificates.get(index);
	}
	
	/**
	 * <p>getApplicationUri.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getApplicationUri()
	{
		return applicationDescription.getApplicationUri();
	}
	
	/**
	 * <p>setApplicationUri.</p>
	 *
	 * @param applicationUri a {@link java.lang.String} object.
	 */
	public void setApplicationUri(String applicationUri)
	{
		applicationDescription.setApplicationUri(applicationUri);
	}
	
	/**
	 * <p>setApplicationName.</p>
	 *
	 * @param applicationName a {@link org.opcfoundation.ua.builtintypes.LocalizedText} object.
	 */
	public void setApplicationName(LocalizedText applicationName)
	{
		applicationDescription.setApplicationName(applicationName);
	}

	/**
	 * <p>getProductUri.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getProductUri() 
	{
		return applicationDescription.getProductUri();
	}

	/**
	 * <p>setProductUri.</p>
	 *
	 * @param productUri a {@link java.lang.String} object.
	 */
	public void setProductUri(String productUri) 
	{
		applicationDescription.setProductUri( productUri );
	}
	
	/**
	 * <p>addLocale.</p>
	 *
	 * @param locale a {@link java.util.Locale} object.
	 */
	public void addLocale(Locale locale)
	{
		if (locale==null)
			throw new IllegalArgumentException("null arg");
		locales.add(locale);
	}
	
	/**
	 * <p>removeLocale.</p>
	 *
	 * @param locale a {@link java.util.Locale} object.
	 */
	public void removeLocale(Locale locale)
	{
		locales.remove(locale);
	}
	
	/**
	 * <p>Getter for the field <code>locales</code>.</p>
	 *
	 * @return an array of {@link java.util.Locale} objects.
	 */
	public Locale[] getLocales()
	{
		return locales.toArray( new Locale[0] );
	}
	
	/**
	 * <p>getLocaleIds.</p>
	 *
	 * @return an array of {@link java.lang.String} objects.
	 */
	public String[] getLocaleIds()
	{
		ArrayList<String> result = new ArrayList<String>(locales.size());
		for (Locale l : locales)
			result.add( LocalizedText.toLocaleId(l) );
		return result.toArray( new String[ result.size() ] );
	}
	
	/**
	 * <p>close.</p>
	 */
	public void close() {
		if ( httpsServer !=null ) {
			httpsServer.close();
			httpsServer = null;
		}
		if ( opctcpServer != null ) {
			opctcpServer.close();
			opctcpServer = null;
		}
	}
	
}
