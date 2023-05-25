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
package org.opcfoundation.ua.transport.security;

import java.io.File;
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

import org.opcfoundation.ua.utils.CertificateUtils;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AuthorityKeyIdentifier;
import org.spongycastle.asn1.x509.BasicConstraints;
import org.spongycastle.asn1.x509.ExtendedKeyUsage;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.KeyPurposeId;
import org.spongycastle.asn1.x509.KeyUsage;
import org.spongycastle.cert.jcajce.JcaX509CertificateConverter;
import org.spongycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.spongycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.spongycastle.openssl.PEMEncryptor;
import org.spongycastle.openssl.jcajce.JcaPEMWriter;
import org.spongycastle.openssl.jcajce.JcePEMEncryptorBuilder;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.jcajce.JcaContentSignerBuilder;
import org.spongycastle.util.encoders.Base64;
import org.spongycastle.x509.extension.X509ExtensionUtil;

/**
 * <p>ScCertificateProvider class.</p>
 *
 */
public class ScCertificateProvider implements CertificateProvider {

	private static final Logger logger = LoggerFactory.getLogger(ScCertificateProvider.class);

	public ScCertificateProvider() {
		//Ensure SC is loaded
		CryptoUtil.loadOrInstallProvider("SC", "org.spongycastle.jce.provider.BouncyCastleProvider");
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Generates a new certificate using the Spongy Castle implementation.
	 * <p>
	 * The method is used from
	 * {@link CertificateUtils#createApplicationInstanceCertificate(String, String, String, int, String...)}
	 * and
	 * {@link CertificateUtils#renewApplicationInstanceCertificate(String, String, String, int, org.opcfoundation.ua.transport.security.KeyPair, String...)}
	 */
	@Override
	public X509Certificate generateCertificate(String domainName,
			PublicKey publicKey, PrivateKey privateKey, KeyPair issuerKeys,
			Date from, Date to, BigInteger serial, String applicationUri,
			String... hostNames) throws IOException, GeneralSecurityException {

		JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();

		JcaX509v3CertificateBuilder certBldr;
		AuthorityKeyIdentifier authorityKeyIdentifier;
		PrivateKey signerKey;
		if (issuerKeys == null) {
			X500Name dn = new X500Name(domainName);
			certBldr = new JcaX509v3CertificateBuilder(dn, serial, from, to,
					dn, publicKey);
			authorityKeyIdentifier = extUtils
					.createAuthorityKeyIdentifier(publicKey);
			signerKey = privateKey;
		} else {
			X509Certificate caCert = issuerKeys.getCertificate()
					.getCertificate();
			certBldr = new JcaX509v3CertificateBuilder(caCert, serial, from,
					to, new X500Principal(domainName), publicKey);
			authorityKeyIdentifier = extUtils
					.createAuthorityKeyIdentifier(caCert);
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

		// BC/SC 1.49:
		certBldr.addExtension(Extension.extendedKeyUsage, false,
				new ExtendedKeyUsage(new KeyPurposeId[] {
						KeyPurposeId.id_kp_serverAuth,
						KeyPurposeId.id_kp_clientAuth }));
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
			logger.warn(
					"Cannot initialize DNS Name to Certificate from ApplicationUri{}",
					applicationUri);
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

		// ***** generate certificate ***********/
		try {
			ContentSigner signer = new JcaContentSignerBuilder(
					CertificateUtils.getCertificateSignatureAlgorithm())
			.setProvider("SC").build(signerKey);
			return new JcaX509CertificateConverter().setProvider("SC")
					.getCertificate(certBldr.build(signer));
		} catch (OperatorCreationException e) {
			throw new GeneralSecurityException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Build a X509 V3 certificate to use as an issuer (CA) certificate. The
	 * certificate does not define OPC UA specific fields, so it cannot be used
	 * for an application instance certificate.
	 */
	@Override
	public X509Certificate generateIssuerCert(PublicKey publicKey,
			PrivateKey privateKey, KeyPair issuerKeys, String commonName,
			BigInteger serialNr, Date startDate, Date expiryDate)
					throws GeneralSecurityException, IOException {
		JcaX509v3CertificateBuilder certBldr;
		JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
		AuthorityKeyIdentifier authorityKeyIdentifier;
		if (issuerKeys == null) {
			X500Name dn = new X500Name(commonName);
			certBldr = new JcaX509v3CertificateBuilder(dn, serialNr, startDate,
					expiryDate, dn, publicKey);
			authorityKeyIdentifier = extUtils
					.createAuthorityKeyIdentifier(publicKey);
		} else {
			X509Certificate caCert = issuerKeys.getCertificate()
					.getCertificate();
			certBldr = new JcaX509v3CertificateBuilder(caCert, serialNr,
					startDate, expiryDate, new X500Principal(commonName),
					publicKey);
			authorityKeyIdentifier = extUtils
					.createAuthorityKeyIdentifier(caCert);
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
			signer = new JcaContentSignerBuilder(
					CertificateUtils.getCertificateSignatureAlgorithm())
			.setProvider("SC").build(privateKey);
		} catch (OperatorCreationException e) {
			throw new GeneralSecurityException(
					"Failed to sign the certificate", e);
		}
		return new JcaX509CertificateConverter().setProvider("SC")
				.getCertificate(certBldr.build(signer));
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<List<?>> getSubjectAlternativeNames(X509Certificate cert)
			throws CertificateParsingException {
		return X509ExtensionUtil.getSubjectAlternativeNames(cert);
	}

	/** {@inheritDoc} */
	@Override
	public void writeToPem(X509Certificate key, File savePath, String password,
			String algorithm) throws IOException {

		JcaPEMWriter pemWrt = new JcaPEMWriter(new OutputStreamWriter(
				new FileOutputStream(savePath.getCanonicalPath())));
		if (password == null)
			pemWrt.writeObject(key);
		else {
			PEMEncryptor encryptor = new JcePEMEncryptorBuilder(algorithm)
			.setSecureRandom(CryptoUtil.getRandom()).build(
					password.toCharArray());

			pemWrt.writeObject(key, encryptor);
		}
		pemWrt.close();
	}

	/** {@inheritDoc} */
	@Override
	public byte[] base64Decode(String string) {
		return Base64.decode(StringUtils.removeWhitespace(string));
	}

	/** {@inheritDoc} */
	@Override
	public String base64Encode(byte[] bytes) {
		try {
			return new String(Base64.encode(bytes), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
