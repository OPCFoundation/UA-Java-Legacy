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
package org.opcfoundation.ua.transport.https;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStore.TrustedCertificateEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;

import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.params.HttpParams;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.CertValidatorTrustManager;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.HttpsSecurityPolicy;
import org.opcfoundation.ua.transport.security.KeyPair;

public class HttpsSettings {
	
	/** Key Managers */
	X509KeyManager keyManager;
	/** Trust managers */
	TrustManager trustManager;
	/** Verifies whether the target hostname matches the names stored inside 
	 * the server's X.509 certificate, once the connection has been established. 
	 * This verification can provide additional guarantees of authenticity of 
	 * the server trust material. */
	X509HostnameVerifier hostnameVerifier;
	/** Authentication info */ 
	String username;
	String password;
	/** http params */
	HttpParams httpParams;
	private HttpsSecurityPolicy[] httpsSecurityPolicies;

	public HttpsSettings() {
		
	}

	public HttpsSettings(KeyPair keypair, CertificateValidator certValidator, X509HostnameVerifier hostnameVerifier) {
		super();
		setKeyPair(keypair);
		setCertificateValidator(certValidator);
		this.hostnameVerifier = hostnameVerifier;
	}
	
	public HttpsSettings(X509KeyManager keyManager, TrustManager trustManager, X509HostnameVerifier hostnameVerifier) {
		super();
		this.keyManager = keyManager;
		this.trustManager = trustManager;
		this.hostnameVerifier = hostnameVerifier;
	}

	public HttpsSettings(X509KeyManager keyManager, TrustManager trustManager, X509HostnameVerifier hostnameVerifier, String username, String password) {
		super();
		this.keyManager = keyManager;
		this.trustManager = trustManager;
		this.hostnameVerifier = hostnameVerifier;
		this.username = username;
		this.password = password;
	}


	/**
	 * Verifies whether the target hostname matches the names stored inside 
	 * the server's X.509 certificate, once the connection has been established. 
	 * This verification can provide additional guarantees of authenticity of 
	 * the server trust material. 
	 * 
	 * @param hostnameVerifier
	 */
	public void setHostnameVerifier(X509HostnameVerifier hostnameVerifier) {
		this.hostnameVerifier = hostnameVerifier;
	}

	public X509HostnameVerifier getHostnameVerifier() {
		return hostnameVerifier;
	}

	/**
	 * Set keypair of a https application. This replaces a keyManager.
	 * Additional CA certifications can be attached. 
	 * 
	 * @param keypair key pair
	 * @param caCerts ca certs
	 */
	public void setKeyPair(KeyPair keypair, Cert...caCerts) 
	{
		if (keypair != null)
			try {
		        KeyStore keystore = KeyStore.getInstance("jks");
		        Certificate[] certs = new Certificate[] { keypair.certificate.certificate };
		        PrivateKeyEntry entry = new PrivateKeyEntry(keypair.privateKey.getPrivateKey(), certs);
		        String password = "";
		        keystore.load( null );
		        keystore.setEntry("myentry-"+keypair.hashCode(), entry, new PasswordProtection(password.toCharArray()));
		        int count = caCerts.length;
		        for (int i=0; i<count; i++) {
		        	String id = "cacert-"+(i+1);
		        	keystore.setEntry(id, new TrustedCertificateEntry(caCerts[i].certificate), null);
		        }
		        setKeyStore( keystore, "" );        
			} catch (KeyStoreException e) {
				// Expected if JKS is not available (e.g. in Android)
				
			} catch (NoSuchAlgorithmException e) {
				// Unexpected
				throw new RuntimeException(e);
			} catch (CertificateException e) {
				// Unexpected
				throw new RuntimeException(e);
			} catch (IOException e) {
				// Unexpected
				throw new RuntimeException(e);
			} catch (ServiceResultException e) {
				// Unexpected
				throw new RuntimeException(e);
			}
	}

	/**
	 * Set keypairs to a https application. This replaces previous keyManager.
	 * Additional CA certifications can be attached. 
	 * 
	 * @param keypairs key paris
	 * @param caCerts ca certs
	 */
	public void setKeyPairs(KeyPair[] keypairs, Cert...caCerts) 
	{
		try {
	        KeyStore keystore = KeyStore.getInstance("jks");
	        String password = "";
	        PasswordProtection prot = new PasswordProtection( password.toCharArray() ); 
	        keystore.load( null );
	        for ( int i=0; i<keypairs.length; i++ ) {
		        Certificate[] certs = new Certificate[1 + caCerts.length];
	        	certs[0] = keypairs[i].certificate.certificate;
	        	for ( int j=0; j<caCerts.length; j++ ) {
	        		certs[j+1] = caCerts[j].certificate;
	        	}
	        	PrivateKeyEntry entry = new PrivateKeyEntry( keypairs[i].privateKey.privateKey, certs);
		        keystore.setEntry("my-key-pair-entry-"+(i+1), entry, prot );
	        }
	        int count = caCerts.length;
	        for (int i=0; i<count; i++) {
	        	String id = "cacert-"+(i+1);
	        	keystore.setEntry(id, new TrustedCertificateEntry(caCerts[i].certificate), null);
	        }
	        setKeyStore( keystore, "" );        
		} catch (KeyStoreException e) {
			// Unexpected
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			// Unexpected
			throw new RuntimeException(e);
		} catch (CertificateException e) {
			// Unexpected
			throw new RuntimeException(e);
		} catch (IOException e) {
			// Unexpected
			throw new RuntimeException(e);
		} catch (ServiceResultException e) {
			// Unexpected
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Set keystore as the key manager for a https application.   
	 *  
	 * @param keystore key store
	 * @param password the password for the key store
	 * @throws ServiceResultException if error
	 */
	public void setKeyStore(KeyStore keystore, String password) throws ServiceResultException
	{
		try {
	        KeyManagerFactory kmfactory = KeyManagerFactory.getInstance( KeyManagerFactory.getDefaultAlgorithm() );
	        kmfactory.init(keystore, password.toCharArray());
	        KeyManager kms[] = kmfactory.getKeyManagers();
	        keyManager = kms.length==0 ? null : (X509KeyManager) kms[0];
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceResultException( e );
		} catch (UnrecoverableKeyException e) {
			throw new ServiceResultException( e );
		} catch (KeyStoreException e) {
			throw new ServiceResultException( e );
		}
	}
	
	/**
	 * Set keymanager for a https application.
	 * 
	 * @param keyManager key manager
	 * @throws ServiceResultException if error 
	 */
	public void setKeyManager(X509KeyManager keyManager) throws ServiceResultException 
	{
		this.keyManager = keyManager;
	}
	
	/**
	 * Set the trust manager for a https application. 
	 * Trust manager validates peer's certificates and certificate issuers.
	 * 
	 * @param trustManager trustmanager
	 * @throws ServiceResultException if error
	 */
	public void setTrustManager(TrustManager trustManager) throws ServiceResultException 
	{
		this.trustManager = trustManager;
	}
	
	/**
	 * Set an implementation of CertificateValidator as TrustManager.
	 * Trust manager validates peer's certificates and certificate issuers.
	 * 
	 * @param certValidator certificate validator
	 */
	public void setCertificateValidator(CertificateValidator certValidator)
	{
		this.trustManager = new CertValidatorTrustManager( certValidator );
	}
	
	/**
	 * Set SSL Authentication information. Must be set before #initialization.
	 * 
	 * @param username user name
	 * @param password password
	 */
	public void setHttpsAuth(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public TrustManager[] getTrustManagers() {
		return trustManager == null ? new TrustManager[0] : new TrustManager[] { trustManager };
	}
	
	public KeyManager[] getKeyManagers() {
		return keyManager == null ? new KeyManager[0] : new KeyManager[] { keyManager };
	}

	public TrustManager getTrustManager() {
		return trustManager;
	}
	
	public X509KeyManager getKeyManager() {
		return keyManager;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	
	public HttpParams getHttpParams() {
		return httpParams;
	}

	public void setHttpParams(HttpParams httpParams) {
		this.httpParams = httpParams;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void readFrom(HttpsSettings src) {
		if (src.hostnameVerifier!=null) hostnameVerifier = src.hostnameVerifier;
		if (src.trustManager!=null) this.trustManager = src.trustManager;
		if (src.keyManager!=null) this.keyManager = src.keyManager;
		if (src.username!=null && src.password!=null) {
			username = src.username;
			password = src.password;
		}
		if ( src.httpParams != null ) this.httpParams = src.httpParams;
		if ( src.httpsSecurityPolicies != null ) this.httpsSecurityPolicies = src.httpsSecurityPolicies;
	}
	
	@Override
	public HttpsSettings clone() {
		HttpsSettings result = new HttpsSettings();
		
		result.hostnameVerifier = hostnameVerifier;
		result.trustManager = trustManager;
		result.keyManager = keyManager;
		result.username = username;
		result.password = password;
		result.httpParams = httpParams;
		result.httpsSecurityPolicies = httpsSecurityPolicies;
		
		return result;
	}

	public HttpsSecurityPolicy[] getHttpsSecurityPolicies() {
		return httpsSecurityPolicies;
	}

	public void setHttpsSecurityPolicies(HttpsSecurityPolicy... httpsSecurityPolicy) {
		this.httpsSecurityPolicies = httpsSecurityPolicy;
	}

	
}
