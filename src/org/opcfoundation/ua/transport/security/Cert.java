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
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.utils.CertificateUtils;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.FileUtil;

import sun.security.provider.X509Factory;

/**
 * Cert is a X509 certificate that contains a public key.
 * The instance is valid and encodedable.
 * Wrapper to {@link java.security.cert.Certificate}.
 * <p>
 * To Create a new certificate See {@link CertificateUtils#generateKeyPair(String)}
 * 
 * @author Mikko Salonen
 * @author Toni Kalajainen (toni.kalajainen@iki.fi)
 */
public class Cert {

	public final X509Certificate certificate;
	public final byte[] encodedCertificate; 
	public final byte[] encodedCertificateThumbprint;
	
	/** 
	 * Load X.509 Certificate from an url
	 * 
	 * @param url
	 * @return Certificate
	 * @throws IOException 
	 * @throws CertificateException In case the certificate is not valid
	 */
	public static Cert load(URL url) 
	throws IOException, CertificateException
	{
		X509Certificate cert = CertificateUtils.readX509Certificate(url);
		return new Cert(cert);
	}
	
	/** 
	 * Load X.509 Certificate from a file
	 * 
	 * @param file
	 * @return Certificate
	 * @throws IOException 
	 * @throws CertificateException In case the certificate is not valid
	 */
	public static Cert load(File file) 
	throws IOException, CertificateException
	{
		return load(file.toURI().toURL());
	}
	
	
	public void save(File file)
	throws IOException
	{
		FileUtil.writeFile(file, encodedCertificate);
	}
	
	public void saveToPem(File file) throws IOException
	{
		FileWriter fw = new FileWriter(file);
		try {
			fw.append(X509Factory.BEGIN_CERT + "\n");
			fw.append(CryptoUtil.base64Encode(getEncoded()));
			fw.append(X509Factory.END_CERT + "\n");
		} finally {
			fw.close();
		}		
		//CertificateUtils.writeToPem(certificate, file);
	}
	
	public int getKeySize() {
		PublicKey key = certificate.getPublicKey();
		if ( key instanceof RSAPublicKey == false ) return -1;
		RSAPublicKey rsaKey = (RSAPublicKey) key;
		return rsaKey.getModulus().bitLength();
	}
	
	/**
	 * Create Certificate 
	 * 
	 * @param data encoded Certificate
	 * @throws ServiceResultException
	 */
	public Cert(byte[] data) 
	throws ServiceResultException
	{
		try {
			encodedCertificate = data;
			certificate = CertificateUtils.decodeX509Certificate(data);
			encodedCertificateThumbprint = CertificateUtils.createThumbprint(encodedCertificate);
		} catch (CertificateNotYetValidException ce) {
			throw new ServiceResultException(StatusCodes.Bad_CertificateTimeInvalid, ce);
		} catch (CertificateExpiredException ce) {
			throw new ServiceResultException(StatusCodes.Bad_CertificateTimeInvalid, ce);
		} catch (CertificateParsingException ce) {
			throw new ServiceResultException(StatusCodes.Bad_CertificateInvalid, ce);
		} catch (CertificateException ce) {
			throw new ServiceResultException(StatusCodes.Bad_CertificateInvalid, ce);
		}
	}
	
	public Cert(X509Certificate certificate) throws CertificateEncodingException
	{
		encodedCertificate = certificate.getEncoded();
		this.certificate = certificate;
		encodedCertificateThumbprint = CertificateUtils.createThumbprint(encodedCertificate);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(encodedCertificate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cert other = (Cert) obj;
		if (!Arrays.equals(encodedCertificate, other.encodedCertificate))
			return false;
		return true;
	}

	public X509Certificate getCertificate() {
		return certificate;
	}

	public byte[] getEncoded() {
		return encodedCertificate;
	}
	
	public byte[] getEncodedThumbprint() {
		return encodedCertificateThumbprint;
	}
	
	@Override
	public String toString() {
		return certificate.toString();
	}
	
}
