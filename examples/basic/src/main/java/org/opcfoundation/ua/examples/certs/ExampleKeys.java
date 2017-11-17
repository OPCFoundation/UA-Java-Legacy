/* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/
package org.opcfoundation.ua.examples.certs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.PrivKey;
import org.opcfoundation.ua.utils.CertificateUtils;

/**
 * Keys for examples
 * Keystore.p12 contains 20 RSA keypairs with the following aliases
 * 
 * alias               dname
 * 
 * server_8192         CN=server 
 * server_4096         CN=server 
 * server_2048         CN=server
 * server_1024         CN=server
 * server_512          CN=server
 * 
 * client_8192         CN=client
 * client_4096         CN=client
 * client_2048         CN=client
 * client_1024         CN=client
 * client_512          CN=client
 * 
 * https_server_8192   CN=https_server
 * https_server_4096   CN=https_server
 * https_server_2048   CN=https_server
 * https_server_1024   CN=https_server
 * https_server_512    CN=https_server
 * 
 * https_client_8192   CN=https_client
 * https_client_4096   CN=https_client
 * https_client_2048   CN=https_client
 * https_client_1024   CN=https_client
 * https_client_512    CN=https_client
 * 
 * Keystore password is "password".
 * Private key passwords are "password".
 *
 */
public class ExampleKeys {
	
	private static final String PRIVKEY_PASSWORD = "Opc.Ua";

	/**
	 * Load file certificate and private key from applicationName.der & .pfx - or create ones if they do not exist
	 * @return the KeyPair composed of the certificate and private key
	 * @throws ServiceResultException
	 */
	public static KeyPair getCert(String applicationName)
	throws ServiceResultException
	{
		File certFile = new File(applicationName + ".der");
		File privKeyFile =  new File(applicationName+ ".pem");
		try {
			Cert myCertificate = Cert.load( certFile );
			PrivKey myPrivateKey = PrivKey.load( privKeyFile, PRIVKEY_PASSWORD );
			return new KeyPair(myCertificate, myPrivateKey); 
		} catch (CertificateException e) {
			throw new ServiceResultException( e );
		} catch (IOException e) {		
			try {
				String hostName = InetAddress.getLocalHost().getHostName();
				String applicationUri = "urn:"+hostName+":"+applicationName;
				KeyPair keys = CertificateUtils.createApplicationInstanceCertificate(applicationName, null, applicationUri, 3650, hostName);
				keys.getCertificate().save(certFile);
				keys.getPrivateKey().save(privKeyFile);
				return keys;
			} catch (Exception e1) {
				throw new ServiceResultException( e1 );
			}
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceResultException( e );
		} catch (InvalidKeyException e) {
			throw new ServiceResultException( e );
		} catch (InvalidKeySpecException e) {
			throw new ServiceResultException( e );
		} catch (NoSuchPaddingException e) {
			throw new ServiceResultException( e );
		} catch (InvalidAlgorithmParameterException e) {
			throw new ServiceResultException( e );
		} catch (IllegalBlockSizeException e) {
			throw new ServiceResultException( e );
		} catch (BadPaddingException e) {
			throw new ServiceResultException( e );
		} catch (InvalidParameterSpecException e) {
			throw new ServiceResultException( e );
		}
	}

	/**
	 * Load CA certificate and private key from SampleCA.der & .pfx - or create ones if they do not exist
	 * @return the KeyPair composed of the certificate and private key
	 * @throws ServiceResultException
	 */
	public static KeyPair getCACert()
	throws ServiceResultException
	{
		File certFile = new File("SampleCA.der");
		File privKeyFile =  new File("SampleCA.pem");
		try {
			Cert myCertificate = Cert.load( certFile );
			PrivKey myPrivateKey = PrivKey.load( privKeyFile, PRIVKEY_PASSWORD );
			return new KeyPair(myCertificate, myPrivateKey); 
		} catch (CertificateException e) {
			throw new ServiceResultException( e );
		} catch (IOException e) {		
			try {
				KeyPair keys = CertificateUtils.createIssuerCertificate("SampleCA", 3650, null);
				keys.getCertificate().save(certFile);
				keys.getPrivateKey().save(privKeyFile, PRIVKEY_PASSWORD);
				return keys;
			} catch (Exception e1) {
				throw new ServiceResultException( e1 );
			}
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceResultException( e );
		} catch (InvalidKeyException e) {
			throw new ServiceResultException( e );
		} catch (InvalidKeySpecException e) {
			throw new ServiceResultException( e );
		} catch (NoSuchPaddingException e) {
			throw new ServiceResultException( e );
		} catch (InvalidAlgorithmParameterException e) {
			throw new ServiceResultException( e );
		} catch (IllegalBlockSizeException e) {
			throw new ServiceResultException( e );
		} catch (BadPaddingException e) {
			throw new ServiceResultException( e );
		} catch (InvalidParameterSpecException e) {
			throw new ServiceResultException( e );
		}
	}
	/**
	 * Load file certificate and private key from applicationName.der & .pfx - or create ones if they do not exist
	 * @param applicationName
	 * @param caKey 
	 * @return the KeyPair composed of the certificate and private key
	 * @throws ServiceResultException
	 */
	public static KeyPair getHttpsCert(String applicationName)
	throws ServiceResultException
	{
		File certFile = new File(applicationName + "_https.der");
		File privKeyFile =  new File(applicationName+ "_https.pem");
		try {
			Cert myCertificate = Cert.load( certFile );
			PrivKey myPrivateKey = PrivKey.load( privKeyFile, PRIVKEY_PASSWORD );
			return new KeyPair(myCertificate, myPrivateKey); 
		} catch (CertificateException e) {
			throw new ServiceResultException( e );
		} catch (IOException e) {		
			try {
				KeyPair caCert = getCACert();
				String hostName = InetAddress.getLocalHost().getHostName();
				String applicationUri = "urn:"+hostName+":"+applicationName;
				KeyPair keys = CertificateUtils.createHttpsCertificate(hostName, applicationUri, 3650, caCert);
				keys.getCertificate().save(certFile);
				keys.getPrivateKey().save(privKeyFile, PRIVKEY_PASSWORD);
				return keys;
			} catch (Exception e1) {
				throw new ServiceResultException( e1 );
			}
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceResultException( e );
		} catch (InvalidKeyException e) {
			throw new ServiceResultException( e );
		} catch (InvalidKeySpecException e) {
			throw new ServiceResultException( e );
		} catch (NoSuchPaddingException e) {
			throw new ServiceResultException( e );
		} catch (InvalidAlgorithmParameterException e) {
			throw new ServiceResultException( e );
		} catch (IllegalBlockSizeException e) {
			throw new ServiceResultException( e );
		} catch (BadPaddingException e) {
			throw new ServiceResultException( e );
		} catch (InvalidParameterSpecException e) {
			throw new ServiceResultException( e );
		}
	}
	/**
	 * Open keypair from keystore.p12 used in some of these examples.
	 * 
	 * Usable aliases are : "server", "client", "https_server", "https_client"
	 * Usable keysizes are : 8192, 4096, 2048, 1024
	 * 
	 * @param alias 
	 * @param keysize 
	 * @return
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnrecoverableKeyException 
	 */
	public static KeyPair getKeyPair(String alias, int keysize) throws ServiceResultException {
		try {
			Certificate cert = ks.getCertificate(alias+"_"+keysize);
			Key key = ks.getKey(alias+"_"+keysize, "password".toCharArray());			
			KeyPair pair = new KeyPair( new Cert( (X509Certificate) cert ), new PrivKey( (RSAPrivateKey) key ) );
			return pair;
		} catch (KeyStoreException e) {
			throw new ServiceResultException( e );
		} catch (UnrecoverableKeyException e) {
			throw new ServiceResultException( e );
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceResultException( e );
		} catch (CertificateEncodingException e) {
			throw new ServiceResultException( e );
		}			
	}	
	
	static KeyStore ks;
	
	static {
		try {
			ks = KeyStore.getInstance("pkcs12");
			InputStream is = ExampleKeys.class.getResourceAsStream("keystore.p12");
			try {
				ks.load( is, "password".toCharArray() );
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			} catch (CertificateException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
