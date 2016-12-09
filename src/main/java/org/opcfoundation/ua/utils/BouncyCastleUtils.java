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
package org.opcfoundation.ua.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.openssl.PEMEncryptor;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcePEMEncryptorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.x509.extension.X509ExtensionUtil;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.opcfoundation.ua.transport.security.KeyPair;




/**
 * BouncyCastle specific implementations of certain Crypto Utilities.
 * Called normally from the {@link CryptoUtil} or {@link CertificateUtils} class,
 * so use those methods instead.
 */
public class BouncyCastleUtils {
	static Logger logger = LoggerFactory.getLogger(BouncyCastleUtils.class);

	//	/**
//	 * Build a V1 certificate to use as a CA root certificate
//	 * @param commonName 
//	 * @param days 
//	 */
//	public static X509Certificate buildV1RootCert(java.security.KeyPair keyPair,
//			String commonName, long days) throws Exception {
//		X500Name dn = new X500Name("CN=" + commonName);
//		Date startDate = new Date(System.currentTimeMillis());
//		Date expiryDate = new Date(
//				System.currentTimeMillis() + days*24*60*60*1000);
//		BigInteger serialNr = BigInteger.valueOf(System.currentTimeMillis());
//		JcaX509v1CertificateBuilder certBldr = new JcaX509v1CertificateBuilder(
//				dn, serialNr,
//				startDate, expiryDate, dn,
//				keyPair.getPublic());
//		ContentSigner signer = new JcaContentSignerBuilder("SHA1withRSA")
//				.setProvider("BC").build(keyPair.getPrivate());
//		return new JcaX509CertificateConverter().setProvider("BC")
//				.getCertificate(certBldr.build(signer));
//	}
	 
	/**
	 * Build a X509 V3 certificate to use as an issuer (CA) certificate. The
	 * certificate does not define OPC UA specific fields, so it cannot be used
	 * for an application instance certificate.
	 *
	 * @param publicKey
	 *            the public key to use for the certificate
	 * @param privateKey
	 *            the private key corresponding to the publicKey
	 * @param issuerKeys
	 *            the certificate and private key of the certificate issuer: if
	 *            null a self-signed certificate is created.
	 * @param commonName
	 *            the CommonName to use for the subject of the certificate.
	 * @param serialNr a {@link java.math.BigInteger} object.
	 * @param startDate a {@link java.util.Date} object.
	 * @param expiryDate a {@link java.util.Date} object.
	 * @return a {@link java.security.cert.X509Certificate} object.
	 * @throws java.security.GeneralSecurityException if any.
	 * @throws java.io.IOException if any.
	 */
	public static X509Certificate generateIssuerCert(PublicKey publicKey,
			PrivateKey privateKey, KeyPair issuerKeys, String commonName, BigInteger serialNr, Date startDate, Date expiryDate)
			throws GeneralSecurityException, IOException {
		JcaX509v3CertificateBuilder certBldr;
		JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
		AuthorityKeyIdentifier authorityKeyIdentifier;
		if (issuerKeys == null) {
			X500Name dn = new X500Name(commonName);
			 certBldr = new JcaX509v3CertificateBuilder(
					dn, serialNr,
					startDate,
					expiryDate, dn,
					publicKey);
				 authorityKeyIdentifier = extUtils.createAuthorityKeyIdentifier(publicKey);
		}
		else {
			 X509Certificate caCert = issuerKeys.getCertificate().getCertificate();
			 certBldr = new JcaX509v3CertificateBuilder(
					caCert, serialNr,
					startDate,
					expiryDate, new X500Principal(commonName),
					publicKey);
				authorityKeyIdentifier = extUtils.createAuthorityKeyIdentifier(caCert);
		}		
		
		certBldr.addExtension(Extension.authorityKeyIdentifier, false,
				authorityKeyIdentifier)
				.addExtension(Extension.subjectKeyIdentifier, false,
						extUtils.createSubjectKeyIdentifier(publicKey))
				.addExtension(Extension.basicConstraints, true,
						new BasicConstraints(0))
				.addExtension(
						Extension.keyUsage,
						true,
						new KeyUsage(KeyUsage.digitalSignature
								| KeyUsage.keyCertSign | KeyUsage.cRLSign));
		ContentSigner signer;
		try {
			signer = new JcaContentSignerBuilder(CertificateUtils.getCertificateSignatureAlgorithm())
					.setProvider("BC").build(privateKey);
		} catch (OperatorCreationException e) {
			throw new GeneralSecurityException("Failed to sign the certificate", e);
		}
		return new JcaX509CertificateConverter().setProvider("BC")
				.getCertificate(certBldr.build(signer));
	}
	 
	/**
	 * Generates a new certificate using the Bouncy Castle implementation.
	 * <p>
	 * The method is used from
	 * {@link CertificateUtils#createApplicationInstanceCertificate(String, String, String, int, String...)}
	 * and
	 * {@link CertificateUtils#renewApplicationInstanceCertificate(String, String, String, int, org.opcfoundation.ua.transport.security.KeyPair, String...)}
	 *
	 * @param domainName
	 *            the X500 domain name for the certificate
	 * @param publicKey
	 *            the public key of the cert
	 * @param privateKey
	 *            the private key of the cert
	 * @param issuerKeys
	 *            the certificate and private key of the issuer
	 * @param from
	 *            validity start time
	 * @param to
	 *            validity end time
	 * @param applicationUri
	 *            the OPC UA ApplicationUri of the application - added to
	 *            SubjectAlternativeName
	 * @param hostNames
	 *            the additional host names to ass to SubjectAlternativeName
	 * @return the generated certificate
	 * @throws java.security.GeneralSecurityException
	 *             if the generation fails
	 * @throws java.io.IOException
	 *             if the generation fails due to an IO exception
	 * @param serial a {@link java.math.BigInteger} object.
	 */
	public static X509Certificate generateCertificate(String domainName, PublicKey publicKey, PrivateKey privateKey, KeyPair issuerKeys,
			Date from, Date to, BigInteger serial, 
			String applicationUri, String... hostNames) throws IOException, GeneralSecurityException {

		JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();

		X509v3CertificateBuilder certBldr;
		AuthorityKeyIdentifier authorityKeyIdentifier;
		PrivateKey signerKey;
		if (issuerKeys == null) {
			X500Name dn = new X500Name(domainName);
			certBldr = new JcaX509v3CertificateBuilder(
				dn, serial, from, to, dn, publicKey);
			authorityKeyIdentifier = extUtils.createAuthorityKeyIdentifier(publicKey);
			signerKey = privateKey;
		} else {
			 X509Certificate caCert = issuerKeys.getCertificate().getCertificate();
			certBldr = new JcaX509v3CertificateBuilder(
				caCert, serial, from, to, new X500Principal(domainName), publicKey);
			 authorityKeyIdentifier = extUtils.createAuthorityKeyIdentifier(caCert);
			 signerKey = issuerKeys.getPrivateKey().getPrivateKey();
		}
		certBldr.addExtension(Extension.authorityKeyIdentifier, false,
				authorityKeyIdentifier)
				.addExtension(Extension.subjectKeyIdentifier, false,
						extUtils.createSubjectKeyIdentifier(publicKey))
				.addExtension(Extension.basicConstraints, false,
						new BasicConstraints(false))
				.addExtension(
						Extension.keyUsage,
						false,
						new KeyUsage(KeyUsage.digitalSignature
								| KeyUsage.keyEncipherment
								| KeyUsage.nonRepudiation
								| KeyUsage.dataEncipherment
								| KeyUsage.keyCertSign));
		
		certBldr.addExtension(Extension.extendedKeyUsage, false,
				new ExtendedKeyUsage(
						new KeyPurposeId[] {
								KeyPurposeId.id_kp_serverAuth, 
								KeyPurposeId.id_kp_clientAuth}));

//		Vector<KeyPurposeId> extendedKeyUsages = new Vector<KeyPurposeId>();
//		extendedKeyUsages.add(KeyPurposeId.id_kp_serverAuth);
//		extendedKeyUsages.add(KeyPurposeId.id_kp_clientAuth);
//		certBldr.addExtension(Extension.extendedKeyUsage, false,
//				new ExtendedKeyUsage(extendedKeyUsages));
				
		// BC 1.49:
//		certBldr.addExtension(X509Extension.extendedKeyUsage, false,
//				new ExtendedKeyUsage(new KeyPurposeId[] {
//						KeyPurposeId.id_kp_serverAuth,
//						KeyPurposeId.id_kp_clientAuth }));
		// create the extension value

		// URI Name
		List<GeneralName> names = new ArrayList<GeneralName>();
		names.add(new GeneralName(GeneralName.uniformResourceIdentifier,
				applicationUri));

		// Add DNS name from ApplicationUri
		boolean hasDNSName = false;
		String uriHostName = null;
		try {
			String[] appUriParts = applicationUri.split("[:/]");
			if (appUriParts.length > 1) {
				uriHostName = appUriParts[1];
				if (!uriHostName.toLowerCase().equals("localhost")) {
					GeneralName dnsName = new GeneralName(GeneralName.dNSName,
							uriHostName);
					names.add(dnsName);
					hasDNSName = true;
				}
			}
		} catch (Exception e) {
			logger.warn("Cannot initialize DNS Name to Certificate from ApplicationUri {}", applicationUri);
		}

		// Add other DNS Names
		List<GeneralName> ipAddressNames = new ArrayList<GeneralName>();
		if (hostNames != null)
			for (String hostName : hostNames) {
				boolean isIpAddress = hostName.matches("^[0-9.]+$");
				if (!hostName.equals(uriHostName)
						&& !hostName.toLowerCase().equals("localhost")) {
					GeneralName dnsName = new GeneralName(
							hostName.matches("^[0-9.]+$") ? GeneralName.iPAddress
									: GeneralName.dNSName, hostName);
					if (isIpAddress)
						ipAddressNames.add(dnsName);
					else {
						names.add(dnsName);
						hasDNSName = true;
					}
				}
			}
		// Add IP Addresses, if no host names are defined
		if (!hasDNSName)
			for (GeneralName n : ipAddressNames)
				names.add(n);

		final GeneralNames subjectAltNames = new GeneralNames(
				names.toArray(new GeneralName[0]));
		certBldr.addExtension(Extension.subjectAlternativeName, false,
				subjectAltNames);
				
		//***** generate certificate ***********/
		try {
			ContentSigner signer = new JcaContentSignerBuilder(
					CertificateUtils.getCertificateSignatureAlgorithm()).setProvider("BC")
					.build(signerKey);
			return new JcaX509CertificateConverter().setProvider("BC")
					.getCertificate(certBldr.build(signer));
		} catch (OperatorCreationException e) {
			throw new GeneralSecurityException(e);
		}
	}

	/**
	 * <p>writeToPem.</p>
	 *
	 * @param key certificate of private key
	 * @param savePath a {@link java.io.File} object.
	 * @param password a {@link java.lang.String} object.
	 * @param  algorithm a {@link java.lang.String} object.
	 * @throws FileNotFoundException if any.
	 * @throws java.io.IOException if any.
	 */
	public static void writeToPem(Object key, File savePath, String password, String  algorithm)
			throws IOException {
		CryptoUtil.getSecurityProviderName();
		JcaPEMWriter        pemWrt = new JcaPEMWriter(new OutputStreamWriter(new FileOutputStream(savePath.getCanonicalPath()) ));
		if (password == null)
			pemWrt.writeObject(key);
		else {
			char[] pw = password.toCharArray();
			PEMEncryptor encryptor = new JcePEMEncryptorBuilder(algorithm).setSecureRandom(CryptoUtil.getRandom()).build(pw);
			
			pemWrt.writeObject(key, encryptor);
		}
		pemWrt.close();
	}

	/**
	 * <p>base64Decode.</p>
	 *
	 * @param string a {@link java.lang.String} object.
	 * @return an array of byte.
	 */
	public static byte[] base64Decode(String string) {
		return Base64.decode(string);
	}

	/**
	 * <p>base64Encode.</p>
	 *
	 * @param bytes an array of byte.
	 * @return a {@link java.lang.String} object.
	 */
	public static String base64Encode(byte[] bytes) {
		try {
			return new String(Base64.encode(bytes), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>getSubjectAlternativeNames.</p>
	 *
	 * @param cert a {@link java.security.cert.X509Certificate} object.
	 * @return a {@link java.util.Collection} object.
	 * @throws java.security.cert.CertificateParsingException if any.
	 */
	@SuppressWarnings("unchecked")
	public static Collection<List<?>> getSubjectAlternativeNames(
			X509Certificate cert) throws CertificateParsingException {
		return X509ExtensionUtil.getSubjectAlternativeNames(cert);
//		try {
//			byte[] ext = cert.getExtensionValue(SUBJECT_ALT_NAME_OID);
//			if (ext == null)
//				return null;
//			DerValue val = new DerValue(ext);
//			byte[] data = val.getOctetString();
//	
//			SubjectAlternativeNameExtension subjectAltNameExt = new SubjectAlternativeNameExtension(
//					Boolean.FALSE, data);
//	
//			GeneralNames names;
//			try {
//				names = (GeneralNames) subjectAltNameExt
//						.get(SubjectAlternativeNameExtension.SUBJECT_NAME);
//			} catch (IOException ioe) {
//				// should not occur
//				return Collections.<List<?>> emptySet();
//			}
//			return makeAltNames(names);
//		} catch (IOException ioe) {
//			CertificateParsingException cpe = new CertificateParsingException();
//			cpe.initCause(ioe);
//			throw cpe;
//		}
	}

	/**
	 * Converts a password to a byte array according to the scheme in PKCS5 (ascii, no padding)
	 *
	 * @param password a character array representing the password.
	 * @return a byte array representing the password.
	 */
	public static byte[] PKCS5PasswordToBytes(char[] password) {
		return PBEParametersGenerator.PKCS5PasswordToBytes(password);
	}


}
