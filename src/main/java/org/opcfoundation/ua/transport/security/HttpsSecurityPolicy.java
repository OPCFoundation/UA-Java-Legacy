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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Definition of HTTPS Security Policies.
 */
public final class HttpsSecurityPolicy {
	/** Constant <code>URI_TLS_1_0="http://opcfoundation.org/UA/SecurityPol"{trunked}</code> */
	public static final String URI_TLS_1_0 = "http://opcfoundation.org/UA/SecurityPolicy#TLS-1-0";
	/** Constant <code>URI_TLS_1_1="http://opcfoundation.org/UA/SecurityPol"{trunked}</code> */
	public static final String URI_TLS_1_1 = "http://opcfoundation.org/UA/SecurityPolicy#TLS-1-1";
	/** Constant <code>URI_TLS_1_2="http://opcfoundation.org/UA/SecurityPol"{trunked}</code> */
	public static final String URI_TLS_1_2 = "http://opcfoundation.org/UA/SecurityPolicy#TLS-1-2";
	/** Constant <code>URI_TLS_1_2_PFS="http://opcfoundation.org/UA/SecurityPol"{trunked}</code> */
	public static final String URI_TLS_1_2_PFS = "http://opcfoundation.org/UA/SecurityPolicy#TLS-1-2-PFS";

	// TLS - No security
	/** Constant <code>SSL_NULL_WITH_NULL_NULL="SSL_NULL_WITH_NULL_NULL"</code> */
	public static final String SSL_NULL_WITH_NULL_NULL = "SSL_NULL_WITH_NULL_NULL";
	/** Constant <code>TLS_RSA_WITH_NULL_MD5="TLS_RSA_WITH_NULL_MD5"</code> */
	public static final String TLS_RSA_WITH_NULL_MD5 = "TLS_RSA_WITH_NULL_MD5";
	/** Constant <code>TLS_RSA_WITH_NULL_SHA="TLS_RSA_WITH_NULL_SHA"</code> */
	public static final String TLS_RSA_WITH_NULL_SHA = "TLS_RSA_WITH_NULL_SHA";
	
	// TLS - Cipher Suites - RSA
	/** Constant <code>SSL_RSA_WITH_RC4_128_SHA="SSL_RSA_WITH_RC4_128_SHA"</code> */
	public static final String SSL_RSA_WITH_RC4_128_SHA = "SSL_RSA_WITH_RC4_128_SHA";
	/** Constant <code>TLS_RSA_WITH_RC4_128_MD5="TLS_RSA_WITH_RC4_128_MD5"</code> */
	public static final String TLS_RSA_WITH_RC4_128_MD5 = "TLS_RSA_WITH_RC4_128_MD5";
	/** Constant <code>TLS_RSA_WITH_IDEA_CBC_SHA="TLS_RSA_WITH_IDEA_CBC_SHA"</code> */
	public static final String TLS_RSA_WITH_IDEA_CBC_SHA = "TLS_RSA_WITH_IDEA_CBC_SHA";
	/** Constant <code>TLS_RSA_WITH_DES_CBC_SHA="TLS_RSA_WITH_DES_CBC_SHA"</code> */
	public static final String TLS_RSA_WITH_DES_CBC_SHA = "TLS_RSA_WITH_DES_CBC_SHA";
	/** Constant <code>SSL_RSA_WITH_3DES_EDE_CBC_SHA="SSL_RSA_WITH_3DES_EDE_CBC_SHA"</code> */
	public static final String SSL_RSA_WITH_3DES_EDE_CBC_SHA = "SSL_RSA_WITH_3DES_EDE_CBC_SHA";
	/** Constant <code>TLS_RSA_WITH_AES_128_CBC_SHA="TLS_RSA_WITH_AES_128_CBC_SHA"</code> */
	public static final String TLS_RSA_WITH_AES_128_CBC_SHA = "TLS_RSA_WITH_AES_128_CBC_SHA";
	/** Constant <code>TLS_RSA_WITH_AES_256_CBC_SHA="TLS_RSA_WITH_AES_256_CBC_SHA"</code> */
	public static final String TLS_RSA_WITH_AES_256_CBC_SHA = "TLS_RSA_WITH_AES_256_CBC_SHA";
	/** Constant <code>TLS_RSA_WITH_AES_128_CBC_SHA256="TLS_RSA_WITH_AES_128_CBC_SHA256"</code> */
	public static final String TLS_RSA_WITH_AES_128_CBC_SHA256 = "TLS_RSA_WITH_AES_128_CBC_SHA256";
	/** Constant <code>TLS_RSA_WITH_AES_256_CBC_SHA256="TLS_RSA_WITH_AES_256_CBC_SHA256"</code> */
	public static final String TLS_RSA_WITH_AES_256_CBC_SHA256 = "TLS_RSA_WITH_AES_256_CBC_SHA256";
																  
	// TLS - Cipher Suites - Diffie-Hellman
	/** Constant <code>TLS_DH_DSS_WITH_DES_CBC_SHA="TLS_DH_DSS_WITH_DES_CBC_SHA"</code> */
	public static final String TLS_DH_DSS_WITH_DES_CBC_SHA = "TLS_DH_DSS_WITH_DES_CBC_SHA";
	/** Constant <code>TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA="TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA"</code> */
	public static final String TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA = "TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA";
	/** Constant <code>TLS_DH_RSA_WITH_DES_CBC_SHA="TLS_DH_RSA_WITH_DES_CBC_SHA"</code> */
	public static final String TLS_DH_RSA_WITH_DES_CBC_SHA = "TLS_DH_RSA_WITH_DES_CBC_SHA";
	/** Constant <code>TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA="TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA"</code> */
	public static final String TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA = "TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA";
	/** Constant <code>TLS_DHE_DSS_WITH_DES_CBC_SHA="TLS_DHE_DSS_WITH_DES_CBC_SHA"</code> */
	public static final String TLS_DHE_DSS_WITH_DES_CBC_SHA = "TLS_DHE_DSS_WITH_DES_CBC_SHA";
	/** Constant <code>TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA="TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA"</code> */
	public static final String TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA = "TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA";
	/** Constant <code>TLS_DHE_RSA_WITH_DES_CBC_SHA="TLS_DHE_RSA_WITH_DES_CBC_SHA"</code> */
	public static final String TLS_DHE_RSA_WITH_DES_CBC_SHA = "TLS_DHE_RSA_WITH_DES_CBC_SHA";
	/** Constant <code>TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA="TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA"</code> */
	public static final String TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA = "TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA";
	/** Constant <code>TLS_DH_anon_WITH_RC4_128_MD5="TLS_DH_anon_WITH_RC4_128_MD5"</code> */
	public static final String TLS_DH_anon_WITH_RC4_128_MD5 = "TLS_DH_anon_WITH_RC4_128_MD5";
	/** Constant <code>TLS_DH_anon_WITH_DES_CBC_SHA="TLS_DH_anon_WITH_DES_CBC_SHA"</code> */
	public static final String TLS_DH_anon_WITH_DES_CBC_SHA = "TLS_DH_anon_WITH_DES_CBC_SHA";
	/** Constant <code>TLS_DH_anon_WITH_3DES_EDE_CBC_SHA="TLS_DH_anon_WITH_3DES_EDE_CBC_SHA"</code> */
	public static final String TLS_DH_anon_WITH_3DES_EDE_CBC_SHA = "TLS_DH_anon_WITH_3DES_EDE_CBC_SHA";
	/** Constant <code>TLS_DHE_RSA_WITH_AES_128_CBC_SHA256="TLS_DHE_RSA_WITH_AES_128_CBC_SHA256"</code> */
	public static final String TLS_DHE_RSA_WITH_AES_128_CBC_SHA256 = "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256";
	/** Constant <code>TLS_DHE_RSA_WITH_AES_256_CBC_SHA256="TLS_DHE_RSA_WITH_AES_256_CBC_SHA256"</code> */
	public static final String TLS_DHE_RSA_WITH_AES_256_CBC_SHA256 = "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256";

	/** Constant <code>TLS_1_0</code> */
	public static final HttpsSecurityPolicy TLS_1_0 = new HttpsSecurityPolicy(
			URI_TLS_1_0, 
			1024,4096, 
			new String[] {
					"..._RSA_WITH_RC4_128_SHA"
					});
	
	/** Constant <code>TLS_1_1</code> */
	public static final HttpsSecurityPolicy TLS_1_1 = new HttpsSecurityPolicy(
			URI_TLS_1_1, 
			1024,4096, 
			new String[] {
					// RSA
					"..._RSA_WITH_3DES_EDE_CBC_SHA",
					TLS_RSA_WITH_RC4_128_MD5, 
					TLS_RSA_WITH_IDEA_CBC_SHA,
					TLS_RSA_WITH_DES_CBC_SHA,
					TLS_RSA_WITH_AES_256_CBC_SHA256,
					
					// DH
					TLS_DH_DSS_WITH_DES_CBC_SHA,
					TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA,
					TLS_DH_RSA_WITH_DES_CBC_SHA,
					TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA,
					TLS_DHE_DSS_WITH_DES_CBC_SHA,
					TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA,
					TLS_DHE_RSA_WITH_DES_CBC_SHA,
					TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA,
					TLS_DH_anon_WITH_RC4_128_MD5,
					TLS_DH_anon_WITH_DES_CBC_SHA,
					TLS_DH_anon_WITH_3DES_EDE_CBC_SHA
			});
	/** Constant <code>TLS_1_2</code> */
	public static final HttpsSecurityPolicy TLS_1_2 = new HttpsSecurityPolicy(
			URI_TLS_1_2, 
			2048,4096, 
			new String[] {
					TLS_RSA_WITH_AES_256_CBC_SHA256});

	/** Constant <code>TLS_1_2_PFS</code> */
	public static final HttpsSecurityPolicy TLS_1_2_PFS = new HttpsSecurityPolicy(
			URI_TLS_1_2_PFS, 
			2048,4096, 
			new String[] {
					TLS_DHE_RSA_WITH_AES_128_CBC_SHA256,
					TLS_DHE_RSA_WITH_AES_256_CBC_SHA256});

	/**
     * All usable HTTPS Security Policies. Includes {@link #TLS_1_1} and {@link #TLS_1_2_PFS}. These are the
     * ones that work in Java 8. Only {@link #TLS_1_0} and {@link #TLS_1_1} work in Java 6, but
     * TLS_1_0 is not considered safe any more. {@link #TLS_1_2} does not work with any Java implementation.
     */
	public static final HttpsSecurityPolicy[] ALL = new HttpsSecurityPolicy[] {TLS_1_1, TLS_1_2_PFS};  

    /**
     * All HTTPS Security Policies defined in OPC UA 1.02. Includes {@link #TLS_1_0} and {@link #TLS_1_1}. This works with Java 6-8.
     * {@link #TLS_1_2} is also included in the specification, but it does not work with any Java implementation.
     */
    public static final HttpsSecurityPolicy[] ALL_102 = new HttpsSecurityPolicy[] {TLS_1_0, TLS_1_1};  

    /**
     * All HTTPS Security Policies defined in OPC UA 1.03. Includes only {@link #TLS_1_2_PFS}. This requires Java 8.
     * {@link #TLS_1_2} is also included in the specification, but it does not work with any Java implementation.
     */
	public static final HttpsSecurityPolicy[] ALL_103 = new HttpsSecurityPolicy[] {TLS_1_2_PFS};  

	/** Security policy map */
	private static Map<String, HttpsSecurityPolicy> availablePolicies = new ConcurrentHashMap<String, HttpsSecurityPolicy>();

	/**
	 * <p>addAvailablePolicy.</p>
	 *
	 * @param policy a {@link org.opcfoundation.ua.transport.security.HttpsSecurityPolicy} object.
	 */
	public static void addAvailablePolicy(HttpsSecurityPolicy policy)
	{
		availablePolicies.put(policy.policyUri, policy);
	}

	static {
		addAvailablePolicy(TLS_1_0);
		addAvailablePolicy(TLS_1_1);
		addAvailablePolicy(TLS_1_2);
		addAvailablePolicy(TLS_1_2_PFS);
	}

	/** Cipher suites as regular expression patterns */
	private final String[] cipherSuites;

	private final int maxAsymmetricKeyLength;

	private final int minAsymmetricKeyLength;

	private final String policyUri;

	HttpsSecurityPolicy(
			String policyUri,
			int minAsymmetricKeyLength, int maxAsymmetricKeyLength,
			String cipherSuites[]
			) {
		this.policyUri = policyUri;
			    
	    this.minAsymmetricKeyLength = minAsymmetricKeyLength;
	    this.maxAsymmetricKeyLength = maxAsymmetricKeyLength;
	    this.cipherSuites = cipherSuites;
	}

	/**
	 * <p>Getter for the field <code>cipherSuites</code>.</p>
	 *
	 * @return an array of {@link java.lang.String} objects.
	 */
	public String[] getCipherSuites() {
		return cipherSuites;
	}

	/**
	 * <p>Getter for the field <code>maxAsymmetricKeyLength</code>.</p>
	 *
	 * @return the maxAsymmetricKeyLength
	 */
	public int getMaxAsymmetricKeyLength() {
		return maxAsymmetricKeyLength;
	}

	/**
	 * <p>Getter for the field <code>minAsymmetricKeyLength</code>.</p>
	 *
	 * @return the minAsymmetricKeyLength
	 */
	public int getMinAsymmetricKeyLength() {
		return minAsymmetricKeyLength;
	}

	/**
	 * <p>Getter for the field <code>policyUri</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPolicyUri() {
		return policyUri;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return policyUri;
	}

	/**
	 * <p>Getter for the field <code>availablePolicies</code>.</p>
	 *
	 * @return the policies
	 */
	public static Map<String, HttpsSecurityPolicy> getAvailablePolicies() {
		return availablePolicies;
	}

}
