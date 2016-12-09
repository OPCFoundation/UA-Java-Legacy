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
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.opcfoundation.ua.utils.CertificateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.BasicConstraintsExtension;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.DNSName;
import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralNames;
import sun.security.x509.IPAddressName;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.KeyUsageExtension;
import sun.security.x509.OIDName;
import sun.security.x509.RFC822Name;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.x509.URIName;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

/**
 * <p>SunJceCertificateProvider class.</p>
 *
 */
public class SunJceCertificateProvider implements CertificateProvider {

	private static Logger logger = LoggerFactory
			.getLogger(SunJceCertificateProvider.class);

	static private final String KUE_SERVER_AUTH = "1.3.6.1.5.5.7.3.1";
	static private final String KUE_CLIENT_AUTH = "1.3.6.1.5.5.7.3.2";

	private static final String SUBJECT_ALT_NAME_OID = "2.5.29.17";

	/**
	 * {@inheritDoc}
	 *
	 * Generates a new certificate using the Sun implementation.
	 * <p>
	 * The method is used from
	 * {@link CertificateUtils#createApplicationInstanceCertificate(String, String, String, int, String...)}
	 * and
	 * {@link CertificateUtils#renewApplicationInstanceCertificate(String, String, String, int, org.opcfoundation.ua.transport.security.KeyPair, String...)}
	 */
	@Override
	public X509Certificate generateCertificate(String domainName,
			PublicKey publicKey, PrivateKey privateKey, KeyPair issuerKeys,
			Date from, Date to, BigInteger serialNumber, String applicationUri,
			String... hostNames) throws GeneralSecurityException, IOException {

		/*
		 * Implementation modified from
		 * http://stackoverflow.com/questions/1615871
		 * /creating-an-x509-certificate-in-java-without-bouncycastle
		 */
		X509CertInfo info = new X509CertInfo();
		CertificateExtensions ext = new CertificateExtensions();
		CertificateValidity interval = new CertificateValidity(from, to);

		X500Name dn = new X500Name(domainName);
		KeyIdentifier keyIdentifier = new KeyIdentifier(publicKey);
		AuthorityKeyIdentifierExtension authorityKeyIdentifierExtension;
		PrivateKey signerKey;
		X500Name issuerName;
		if (issuerKeys == null) {
			issuerName = dn;
			authorityKeyIdentifierExtension = new AuthorityKeyIdentifierExtension(
					keyIdentifier, null, null);
			signerKey = privateKey;
		} else {
			X509Certificate issuerCert = issuerKeys.getCertificate()
					.getCertificate();
			issuerName = new X500Name(issuerCert.getSubjectX500Principal()
					.getName());
			authorityKeyIdentifierExtension = new AuthorityKeyIdentifierExtension(
					new KeyIdentifier(issuerCert.getPublicKey()), null, null);
			signerKey = issuerKeys.getPrivateKey().getPrivateKey();
		}

		info.set(X509CertInfo.VALIDITY, interval);
		info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(
				serialNumber));
		info.set(X509CertInfo.SUBJECT, dn);
		info.set(X509CertInfo.KEY, new CertificateX509Key(publicKey));
		info.set(X509CertInfo.VERSION, new CertificateVersion(
				CertificateVersion.V3));
		info.set(X509CertInfo.ISSUER, issuerName);
		AlgorithmId algo = AlgorithmId.get(CertificateUtils
				.getCertificateSignatureAlgorithm());
		info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

		// The extensions
		// http://www.docjar.com/html/api/sun/security/tools/KeyTool.java.html
		// helps here

		boolean isCritical = false;
		boolean isCA = false;
		int pathLen = 0;

		ext.set(SubjectKeyIdentifierExtension.NAME,
				new SubjectKeyIdentifierExtension(keyIdentifier.getIdentifier()));

		ext.set(AuthorityKeyIdentifierExtension.NAME,
				authorityKeyIdentifierExtension);

		ext.set(BasicConstraintsExtension.NAME, new BasicConstraintsExtension(
				isCritical, isCA, pathLen));
		boolean[] ok = { true, // "digitalSignature", // (0),
				true, // "nonRepudiation", // (1)
				true, // "keyEncipherment", // (2),
				true, // "dataEncipherment", // (3),
				false, // "keyAgreement", // (4),
				true, // "keyCertSign", // (5),
				false, // "cRLSign", // (6),
				false, // "encipherOnly", // (7),
				false, // "decipherOnly", // (8)
				false // "contentCommitment" // also (1)
		};
		KeyUsageExtension kue = new KeyUsageExtension(ok);
		ext.set(KeyUsageExtension.NAME, kue);

		Vector<ObjectIdentifier> extendedKeyUsages = new Vector<ObjectIdentifier>();
		extendedKeyUsages.add(new ObjectIdentifier(KUE_SERVER_AUTH));
		extendedKeyUsages.add(new ObjectIdentifier(KUE_CLIENT_AUTH));
		ext.set(ExtendedKeyUsageExtension.NAME, new ExtendedKeyUsageExtension(
				isCritical, extendedKeyUsages));

		GeneralNames gnames = createAlternativeNames(applicationUri, hostNames);

		ext.set(SubjectAlternativeNameExtension.NAME,
				new SubjectAlternativeNameExtension(isCritical, gnames));

		info.set(X509CertInfo.EXTENSIONS, ext);

		return signCert(info, signerKey);

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
			PrivateKey privateKey, KeyPair issuerKeys, String domainName,
			BigInteger serialNumber, Date startDate, Date expiryDate)
					throws GeneralSecurityException, IOException {

		X509CertInfo info = new X509CertInfo();
		CertificateExtensions ext = new CertificateExtensions();
		CertificateValidity interval = new CertificateValidity(startDate,
				expiryDate);

		X500Name dn = new X500Name(domainName);
		KeyIdentifier keyIdentifier = new KeyIdentifier(publicKey);
		AuthorityKeyIdentifierExtension authorityKeyIdentifierExtension;
		PrivateKey signerKey;
		X500Name issuerName;
		if (issuerKeys == null) {
			issuerName = dn;
			authorityKeyIdentifierExtension = new AuthorityKeyIdentifierExtension(
					keyIdentifier, null, null);
			signerKey = privateKey;
		} else {
			X509Certificate issuerCert = issuerKeys.getCertificate()
					.getCertificate();
			issuerName = new X500Name(issuerCert.getSubjectX500Principal()
					.getName());
			authorityKeyIdentifierExtension = new AuthorityKeyIdentifierExtension(
					new KeyIdentifier(issuerCert.getPublicKey()), null, null);
			signerKey = issuerKeys.getPrivateKey().getPrivateKey();
		}

		info.set(X509CertInfo.VALIDITY, interval);
		info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(
				serialNumber));
		info.set(X509CertInfo.SUBJECT, new CertificateSubjectName(dn));
		info.set(X509CertInfo.KEY, new CertificateX509Key(publicKey));
		info.set(X509CertInfo.VERSION, new CertificateVersion(
				CertificateVersion.V3));
		info.set(X509CertInfo.ISSUER, new CertificateIssuerName(issuerName));
		AlgorithmId algo = AlgorithmId.get(CertificateUtils
				.getCertificateSignatureAlgorithm());
		info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

		// The extensions
		// http://www.docjar.com/html/api/sun/security/tools/KeyTool.java.html
		// helps here

		boolean isCritical = false;
		boolean isCA = true;
		int pathLen = 0;

		ext.set(SubjectKeyIdentifierExtension.NAME,
				new SubjectKeyIdentifierExtension(keyIdentifier.getIdentifier()));

		ext.set(AuthorityKeyIdentifierExtension.NAME,
				authorityKeyIdentifierExtension);

		ext.set(BasicConstraintsExtension.NAME, new BasicConstraintsExtension(
				isCritical, isCA, pathLen));
		boolean[] ok = { true, // "digitalSignature", // (0),
				false, // "nonRepudiation", // (1)
				false, // "keyEncipherment", // (2),
				false, // "dataEncipherment", // (3),
				false, // "keyAgreement", // (4),
				true, // "keyCertSign", // (5),
				true, // "cRLSign", // (6),
				false, // "encipherOnly", // (7),
				false, // "decipherOnly", // (8)
				false // "contentCommitment" // also (1)
		};
		KeyUsageExtension kue = new KeyUsageExtension(ok);
		ext.set(KeyUsageExtension.NAME, kue);

		info.set(X509CertInfo.EXTENSIONS, ext);

		return signCert(info, signerKey);

	}

	/*
	 * This code is copied from the openJDK, which checks the AltNames correctly
	 * and raises exceptions. We try to enable reading the UriName field without
	 * raising exceptions, even if the format is invalid.
	 */
	/** {@inheritDoc} */
	@Override
	public Collection<List<?>> getSubjectAlternativeNames(X509Certificate cert)
			throws CertificateParsingException {

		try {
			byte[] ext = cert.getExtensionValue(SUBJECT_ALT_NAME_OID);
			if (ext == null)
				return null;
			DerValue val = new DerValue(ext);
			byte[] data = val.getOctetString();

			SubjectAlternativeNameExtension subjectAltNameExt = new SubjectAlternativeNameExtension(
					Boolean.FALSE, data);

			GeneralNames names;
			try {
				names = (GeneralNames) subjectAltNameExt
						.get(SubjectAlternativeNameExtension.SUBJECT_NAME);
			} catch (IOException ioe) {
				// should not occur
				return Collections.<List<?>> emptySet();
			}
			return makeAltNames(names);
		} catch (IOException ioe) {
			CertificateParsingException cpe = new CertificateParsingException();
			cpe.initCause(ioe);
			throw cpe;
		}

	}

	/** {@inheritDoc} */
	@Override
	public void writeToPem(X509Certificate key, File savePath, String password,
			String algorithm) throws IOException {
		throw new RuntimeException(
				"writeToPem functionality not supported with Sun crypto implementation");

		/*
		 * PEMWriter pemWrt = new PEMWriter(new OutputStreamWriter(new
		 * FileOutputStream(savePath.getCanonicalPath()) )); if (password ==
		 * null) pemWrt.writeObject(key); else pemWrt.writeObject(key,
		 * algorithm, password.toCharArray(), CryptoUtil.getRandom());
		 * pemWrt.close();
		 */
	}

	/**
	 * @param applicationUri
	 * @param hostNames
	 * @return
	 * @throws IOException
	 */
	private GeneralNames createAlternativeNames(String applicationUri,
			String... hostNames) throws IOException {
		// URI Name
		GeneralNames gnames = new GeneralNames();
		gnames.add(new GeneralName(new URIName(applicationUri)));

		// Add DNS name from ApplicationUri
		boolean hasDNSName = false;
		String uriHostName = null;
		try {
			String[] appUriParts = applicationUri.split("[:/]");
			if (appUriParts.length > 1) {
				uriHostName = appUriParts[1];
				GeneralName dnsName = new GeneralName(new DNSName(uriHostName));
				gnames.add(dnsName);
				if (!uriHostName.matches("^[0-9.]+$"))
					hasDNSName = true;
			}
		} catch (Exception e) {
			logger.warn(
					"Cannot initialize DNS Name to Certificate from ApplicationUri{}",
					applicationUri);
		}
		// Add other DNS Names
		GeneralNames ipAddressNames = new GeneralNames();
		if (hostNames != null)
			for (String hostName : hostNames) {
				boolean isIpAddress = hostName.matches("^[0-9.]+$");
				if (!hostName.equals(uriHostName)
						&& !hostName.toLowerCase().equals("localhost")) {
					GeneralName dnsName = new GeneralName(
							isIpAddress ? new IPAddressName(hostName)
							: new DNSName(hostName));
					if (isIpAddress)
						ipAddressNames.add(new GeneralName(new IPAddressName(
								hostName)));
					else {
						gnames.add(dnsName);
						hasDNSName = true;
					}
				}
			}
		// Add IP Addresses, if no host names are defined
		if (!hasDNSName)
			for (GeneralName n : ipAddressNames.names())
				gnames.add(n);
		return gnames;
	}

	private Collection<List<?>> makeAltNames(GeneralNames names) {
		if (names.isEmpty())
			return Collections.<List<?>> emptySet();
		Set<List<?>> newNames = new HashSet<List<?>>();
		for (GeneralName gname : names.names()) {
			GeneralNameInterface name = gname.getName();
			List<Object> nameEntry = new ArrayList<Object>(2);
			nameEntry.add(Integer.valueOf(name.getType()));
			switch (name.getType()) {
			case GeneralNameInterface.NAME_RFC822:
				nameEntry.add(((RFC822Name) name).getName());
				break;
			case GeneralNameInterface.NAME_DNS:
				nameEntry.add(((DNSName) name).getName());
				break;
			case GeneralNameInterface.NAME_DIRECTORY:
				nameEntry.add(((X500Name) name).getRFC2253Name());
				break;
			case GeneralNameInterface.NAME_URI:
				// nameEntry.add(((URIName) name).getName());
				nameEntry.add(((URIName) name).getName());
				break;
			case GeneralNameInterface.NAME_IP:
				try {
					nameEntry.add(((IPAddressName) name).getName());
				} catch (IOException ioe) {
					// IPAddressName in cert is bogus
					throw new RuntimeException("IPAddress cannot be parsed",
							ioe);
				}
				break;
			case GeneralNameInterface.NAME_OID:
				nameEntry.add(((OIDName) name).getOID().toString());
				break;
			default:
				// add DER encoded form
				DerOutputStream derOut = new DerOutputStream();
				try {
					name.encode(derOut);
				} catch (IOException ioe) {
					// should not occur since name has already been decoded
					// from cert (this would indicate a bug in our code)
					throw new RuntimeException("name cannot be encoded", ioe);
				}
				nameEntry.add(derOut.toByteArray());
				break;
			}
			newNames.add(Collections.unmodifiableList(nameEntry));
		}
		return Collections.unmodifiableCollection(newNames);
	}

	/**
	 * @param info
	 * @param signerKey
	 * @return
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchProviderException
	 * @throws SignatureException
	 * @throws CertificateParsingException
	 * @throws IOException
	 */
	private X509Certificate signCert(X509CertInfo info, PrivateKey signerKey)
			throws CertificateException, NoSuchAlgorithmException,
			InvalidKeyException, NoSuchProviderException, SignatureException,
			CertificateParsingException, IOException {
		// Sign the cert to identify the algorithm that's used.
		X509CertImpl cert = new X509CertImpl(info);
		cert.sign(signerKey,
				CertificateUtils.getCertificateSignatureAlgorithm());

		// // Update the algorithm, and resign.
		// AlgorithmId algo;
		// algo = (AlgorithmId) cert.get(X509CertImpl.SIG_ALG);
		// info.set(CertificateAlgorithmId.NAME + "."
		// + CertificateAlgorithmId.ALGORITHM, algo);
		// cert = new X509CertImpl(info);
		// cert.sign(signerKey,
		// CertificateUtils.getCertificateSignatureAlgorithm());
		return cert;
	}

}
