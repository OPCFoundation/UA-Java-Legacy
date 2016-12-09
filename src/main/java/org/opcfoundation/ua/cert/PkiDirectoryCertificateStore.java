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

package org.opcfoundation.ua.cert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opcfoundation.ua.transport.security.Cert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PkiDirectoryCertificateStore implements CertificateStore{

	private static final Logger logger = LoggerFactory.getLogger(PkiDirectoryCertificateStore.class);
	
	private static final String FILE_EXTENSION = ".der";

	
	private static final String HEXES = "0123456789ABCDEF";
	
	
	private final Map<String, Cert> rejectedCertificates = new ConcurrentHashMap<String, Cert>();
	private final Map<String, Cert> trustedCertificates = new ConcurrentHashMap<String, Cert>();
	private final Set<X509CRL> revocationLists = new CopyOnWriteArraySet<X509CRL>();
	
	private final File baseDir;
	private final File revocationDir;
	private final File rejectedDir;



	private final File trustedDir;

	private final CopyOnWriteArraySet<PublicKey> trustedPublicKeys = new CopyOnWriteArraySet<PublicKey>();
	
	private boolean storeAcceptOnceCertificates = true;

	private final List<DefaultCertificateStoreListener> listeners;

	/**
	 * Create a new validator using the default baseDir ("PKI\CA"), trustedDir
	 * ("certs"), rejectedDir ("rejected") and revokedDir ("crl").
	 */
	public PkiDirectoryCertificateStore() {
		this("PKI/CA", "certs", "rejected", "crl");
	}
	
	/**
	 * Create a new validator using a baseDir and default subdirs: trustedDir
	 * ("certs"), rejectedDir ("rejected") and revokedDir ("crl").
	 *
	 * @param baseDir
	 *            the path to the base directory where the certificate
	 *            directories are kept.
	 */
	public PkiDirectoryCertificateStore(String baseDir) {
		this(baseDir, "certs", "rejected", "crl");
	}
	
	/**
	 * Create a new validator using a baseDir and <b>subdirectory names</b>,
	 * trustedDir, rejectedDir and revocationDir.
	 *
	 * @param baseDir
	 *            the path to the base directory where the certificate
	 *            directories are kept. Use null, if you wish to define the
	 *            other directories with absolute paths.
	 * @param trustedDir
	 *            the name of the directory in which the trusted certificates
	 *            are kept. If baseDir is defined, this is a relative path to
	 *            that, e.g. a subdirectory name.
	 * @param rejectedDir
	 *            the name of the directory in which the rejected certificates
	 *            are kept. If baseDir is defined, this is a relative path to
	 *            that, e.g. a subdirectory name.
	 * @param revocationDir
	 *            the name of the directory in which revoked certificates are
	 *            kept. If baseDir is defined, this is a relative path to that,
	 *            e.g. a subdirectory name.
	 */
	public PkiDirectoryCertificateStore(String baseDir, String trustedDir, String rejectedDir,
			String revocationDir) {
		this.listeners = new ArrayList<DefaultCertificateStoreListener>();
		this.baseDir = new File(baseDir);
		// if (!this.baseDir.exists())
		// this.baseDir.mkdir();
		this.trustedDir = new File(baseDir, trustedDir);
		this.rejectedDir = new File(baseDir, rejectedDir);
		this.revocationDir = new File(baseDir, revocationDir);
		init();
	}
	
	public void addListener(DefaultCertificateStoreListener listener) {
		if ((listener != null) && !this.listeners.contains(listener))
			this.listeners.add(listener);
	}	
	
	
	
	/**
	 * Add a certificate to the rejected certificates.
	 *
	 * @param certificate
	 *            the certificate to add
	 */
	public void addRejectedCertificate(Cert certificate){
		listAdd(rejectedCertificates, rejectedDir, certificate);
		removeCertificate(trustedCertificates, trustedDir, certificate);
		logger.info("Certificate '{}' added to rejected certificates.", getCertKey(certificate));
		fireAddedRejected(certificate);
	}
	

	/**
	 * Add a certificate revocation list to the store.
	 * NOTE! currently it is NOT written to the disk. 
	 * 
	 * @param crl revocation list to add
	 */
	public void addRevocationList(X509CRL crl){
		revocationLists.add(crl);
		fireAddedRevocationList(crl);
	}
	
	/**
	 * Add a certificate to the trusted certificates.
	 *
	 * @param certificate
	 *            the certificate to add
	 */
	public void addTrustedCertificate(Cert certificate) {
		logger.debug("addTrustedCertificate");
		listAdd(trustedCertificates, trustedDir, certificate);
		removeCertificate(rejectedCertificates, rejectedDir, certificate);
		logger.info("Certificate '{}' added to trusted certificates.", getCertKey(certificate));
		fireAddedTrusted(certificate);
	}
	
	@Override
	public Set<Cert> getTrustedCerts() {
		init();
		HashSet<Cert> set = new HashSet<Cert>();
		for(Entry<String, Cert> e : trustedCertificates.entrySet()){
			set.add(e.getValue());
		}
		return Collections.unmodifiableSet(set);
	}
	
	@Override
	public Set<Cert> getRejectedCerts() {
		init();
		HashSet<Cert> set = new HashSet<Cert>();
		for(Entry<String, Cert> e : rejectedCertificates.entrySet()){
			set.add(e.getValue());
		}
		return Collections.unmodifiableSet(set);
	}

	@Override
	public Set<X509CRL> getRevocationLists() {
		init();
		HashSet<X509CRL> set = new HashSet<X509CRL>();
		for(X509CRL crl : revocationLists){
			set.add(crl);
		}
		return Collections.unmodifiableSet(set); 
	}

	@Override
	public void addCertificate(ValidationResult type, Cert certificate) {
		if(type == null){
			throw new IllegalArgumentException("type cannot be null");
		}
		if(certificate == null){
			throw new IllegalArgumentException("certificate cannot be null");
		}
		
		switch (type) {
		case AcceptPermanently:
			addTrustedCertificate(certificate);
			break;
		case AcceptOnce:
			if(storeAcceptOnceCertificates){
				addRejectedCertificate(certificate);
			}
			break;		
		case Reject:
			addRejectedCertificate(certificate);
			break;
		default:
			throw new IllegalArgumentException("encountered unknown type parameter: "+type);
		}		
	}	
	
	/**
	 * @return the trustedDir
	 */
	public File getTrustedDir() {
		return trustedDir;
	}
	
	
	/**
	 * @return the rejectedDir
	 */
	public File getRejectedDir() {
		return rejectedDir;
	}
	
	/**
	 * @return the revocationDir
	 */
	public File getRevocationDir() {
		return revocationDir;
	}
	
	/**
	 * @return the storeAcceptOnceCertificates
	 */
	public boolean isStoreAcceptOnceCertificates() {
		return storeAcceptOnceCertificates;
	}
	
	public void removeListener(DefaultCertificateStoreListener listener) {
		if (listener != null)
			this.listeners.remove(listener);
	}
	
	/**
	 * Returns File for the given Certificate.
	 *
	 * @param cert the certificate
	 * @return File for the given Cert or null if Cert is null or cannot be found
	 */
	public File getFileForCert(Cert cert) {
		if (cert == null)
			return null;

		if (trustedCertificates.containsKey(getCertKey(cert)))
			return getFileForCert(trustedDir, cert);

		if (rejectedCertificates.containsKey(getCertKey(cert)))
			return getFileForCert(rejectedDir, cert);

		return null;
	}
	
	private File getFileForCert(File dir, Cert certificate) {
		return new File(dir, getCertKey(certificate) + FILE_EXTENSION);
	}
	
	/**
	 * Defines whether certificates that are accepted with AcceptOnce status are
	 * stored in the certificate store or not. If they are stored, they are
	 * stored in the RejectedCertificates. You may want to store them to be able
	 * to validate the certificate contents later on from the file.
	 * <p>
	 * Default: true
	 *
	 * @param keepAcceptOnceCerts true if accept once certs should be stored
	 */
	public void setStoreAcceptOnceCertificates(boolean keepAcceptOnceCerts) {
		this.storeAcceptOnceCertificates = keepAcceptOnceCerts;
	}
	
	/**
	 * Refresh certificates. Call this method in case you change certificates
	 * outside of this instance (e.g. move files on disk) and want to refresh
	 * before a validateCertificate call happens (which does this
	 * automatically).
	 */
	public void refresh() {
		init();
	}
	
	
	/**
	 * @return the baseDir
	 */
	public File getBaseDir() {
		return baseDir;
	}
	
	
	/**
	 * Clears the certificate lists.
	 *
	 * @param removeFiles
	 *            if true, removes all certificate files as well. Be careful
	 *            with this!
	 */
	public void clear(boolean removeFiles) {
		if (removeFiles) {
			for (File f : trustedDir.listFiles())
				f.delete();

			for (File f : rejectedDir.listFiles())
				f.delete();

			for (File f : revocationDir.listFiles())
				f.delete();
		}
		trustedCertificates.clear();
		rejectedCertificates.clear();
	}
	
	/**
	 * Initialize the certificate lists by reading the certificates from the
	 * directories.
	 *
	 * Synchronized because we should not allow refresh and validateCertificate
	 * calls to init at the same time.
	 */
	private synchronized void init() {
		initCertificates(trustedCertificates, trustedDir, rejectedCertificates);
		for (Cert c : trustedCertificates.values())
			trustedPublicKeys.add(c.getCertificate().getPublicKey());
		initCertificates(rejectedCertificates, rejectedDir, trustedCertificates);
	}
	
	private void initCertificates(Map<String, Cert> certificates, File dir, Map<String, Cert> removeFromCertificateList) {
		if (!dir.exists())
			dir.mkdirs();
		if (dir.isDirectory())
			for (File file : dir.listFiles())
				if (dir.equals(revocationDir) && file.getName().endsWith(".crl"))
					initCRL(file);
				else
					try {
						final Cert certificate = Cert.load(file);
						listAdd(certificates, dir, certificate);
						logger.debug("Certificate from '{}' added to accepted certificates", file);
						if (removeFromCertificateList != null)
							removeFromCertificateList.remove(getCertKey(certificate));
					} catch (IOException e) {
						logger.info("File '{}' is not a certificate: {}", file, e.getMessage());
					} catch (CertificateException e) {
						logger.info("File '{}' is not a valid certificate: {}", file, e.getMessage());
					}
	}
	
	private void initCRL(File file) {
		try {
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			FileInputStream fis = new FileInputStream(file);
			X509CRL crl = (X509CRL) certFactory.generateCRL(fis);
			revocationLists.add(crl);
			logger.info("CRL initialized from " + file + ": " + (crl.getRevokedCertificates() == null
					? "no revoked certificates" : crl.getRevokedCertificates().size() + " certificates revoked"));
		} catch (Exception e) {
			logger.warn("Could not read CRL file {: {}", file, e.getMessage());
		}
	}
	
	private void listAdd(Map<String, Cert> certificates, File dir, Cert certificate) {
		logger.debug("listAdd: cert={}; dir={}", getCertKey(certificate), dir);
		if (!certificates.containsKey(certificate)) {
			try {
				final File fileForCert = getFileForCert(dir, certificate);
				if (!fileForCert.exists())
					certificate.save(fileForCert);
			} catch (IOException e) {
				logger.error("Cannot write to directory " + dir, e);
			}
			certificates.put(getCertKey(certificate), certificate);
		}
		logger.debug("certificates.size()={}", certificates.size());
	}

	private String getCertKey(Cert certificate) {
		return getHex(certificate.getEncodedThumbprint());
	}
	
	private String getHex(byte[] raw) {
		if (raw == null)
			return null;
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw)
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
		return hex.toString();
	}
	
	private void fireAddedRejected(Cert cert) {
		for (DefaultCertificateStoreListener l : this.listeners)
			l.onRejectedCertificateAdded(cert);
	}

	private void fireAddedTrusted(Cert cert) {
		for (DefaultCertificateStoreListener l : this.listeners)
			l.onTrustedCertificateAdded(cert);
	}
	
	private void fireAddedRevocationList(X509CRL crl) {
		for(DefaultCertificateStoreListener l : this.listeners){
			l.onRevokedListAdded(crl);
		}
	}
	
	private void removeCertificate(Map<String, Cert> certificates, File dir, Cert certificate) {
		logger.debug("removeCertificate: cert={} dir={}", getCertKey(certificate), dir);
		logger.debug("certificates.size()={}", certificates.size());

		File file = getFileForCert(dir, certificate);
		file.delete();
		Cert c = certificates.remove(getCertKey(certificate));
		if (logger.isDebugEnabled()) {
			logger.debug("c=" + (c == null ? "null" : c.getEncodedThumbprint()));
			logger.debug("certificates.size()={}", certificates.size());
		}
	}

}
