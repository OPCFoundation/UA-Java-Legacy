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
	public static final String URI_TLS_1_0 = "http://opcfoundation.org/UA/SecurityPolicy#TLS-1-0";
	public static final String URI_TLS_1_1 = "http://opcfoundation.org/UA/SecurityPolicy#TLS-1-1";
	public static final String URI_TLS_1_2 = "http://opcfoundation.org/UA/SecurityPolicy#TLS-1-2";

	// TLS - No security
	public static final String SSL_NULL_WITH_NULL_NULL = "SSL_NULL_WITH_NULL_NULL";
	public static final String TLS_RSA_WITH_NULL_MD5 = "TLS_RSA_WITH_NULL_MD5";
	public static final String TLS_RSA_WITH_NULL_SHA = "TLS_RSA_WITH_NULL_SHA";
	
	// TLS - Cipher Suites - RSA
	public static final String SSL_RSA_WITH_RC4_128_SHA = "SSL_RSA_WITH_RC4_128_SHA";
	public static final String TLS_RSA_WITH_RC4_128_MD5 = "TLS_RSA_WITH_RC4_128_MD5";
	public static final String TLS_RSA_WITH_IDEA_CBC_SHA = "TLS_RSA_WITH_IDEA_CBC_SHA";
	public static final String TLS_RSA_WITH_DES_CBC_SHA = "TLS_RSA_WITH_DES_CBC_SHA";
	public static final String SSL_RSA_WITH_3DES_EDE_CBC_SHA = "SSL_RSA_WITH_3DES_EDE_CBC_SHA";
	public static final String TLS_RSA_WITH_AES_128_CBC_SHA = "TLS_RSA_WITH_AES_128_CBC_SHA";
	public static final String TLS_RSA_WITH_AES_256_CBC_SHA = "TLS_RSA_WITH_AES_256_CBC_SHA";
	public static final String TLS_RSA_WITH_AES_128_CBC_SHA256 = "TLS_RSA_WITH_AES_128_CBC_SHA256";
	public static final String TLS_RSA_WITH_AES_256_CBC_SHA256 = "TLS_RSA_WITH_AES_256_CBC_SHA256";
																  
	// TLS - Cipher Suites - Diffie-Hellman
	public static final String TLS_DH_DSS_WITH_DES_CBC_SHA = "TLS_DH_DSS_WITH_DES_CBC_SHA";
	public static final String TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA = "TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA";
	public static final String TLS_DH_RSA_WITH_DES_CBC_SHA = "TLS_DH_RSA_WITH_DES_CBC_SHA";
	public static final String TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA = "TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA";
	public static final String TLS_DHE_DSS_WITH_DES_CBC_SHA = "TLS_DHE_DSS_WITH_DES_CBC_SHA";
	public static final String TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA = "TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA";
	public static final String TLS_DHE_RSA_WITH_DES_CBC_SHA = "TLS_DHE_RSA_WITH_DES_CBC_SHA";
	public static final String TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA = "TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA";
	public static final String TLS_DH_anon_WITH_RC4_128_MD5 = "TLS_DH_anon_WITH_RC4_128_MD5";
	public static final String TLS_DH_anon_WITH_DES_CBC_SHA = "TLS_DH_anon_WITH_DES_CBC_SHA";
	public static final String TLS_DH_anon_WITH_3DES_EDE_CBC_SHA = "TLS_DH_anon_WITH_3DES_EDE_CBC_SHA";

	public static final HttpsSecurityPolicy TLS_1_0 = new HttpsSecurityPolicy(
			URI_TLS_1_0, 
			1024,4096, 
			new String[] {
					"..._RSA_WITH_RC4_128_SHA"
					});
	
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
	public static final HttpsSecurityPolicy TLS_1_2 = new HttpsSecurityPolicy(
			URI_TLS_1_2, 
			2048,4096, 
			new String[] {
					TLS_RSA_WITH_AES_256_CBC_SHA256});

	// TLS_1_2 does not work, so we will leave out of the default policies list
	public static final HttpsSecurityPolicy[] ALL = new HttpsSecurityPolicy[] {TLS_1_0, TLS_1_1};  

	/** Security policy map */
	private static Map<String, HttpsSecurityPolicy> availablePolicies = new ConcurrentHashMap<String, HttpsSecurityPolicy>();

	public static void addAvailablePolicy(HttpsSecurityPolicy policy)
	{
		availablePolicies.put(policy.policyUri, policy);
	}

	static {
		addAvailablePolicy(TLS_1_0);
		addAvailablePolicy(TLS_1_1);
		addAvailablePolicy(TLS_1_2);
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

	public String[] getCipherSuites() {
		return cipherSuites;
	}

	/**
	 * @return the maxAsymmetricKeyLength
	 */
	public int getMaxAsymmetricKeyLength() {
		return maxAsymmetricKeyLength;
	}

	/**
	 * @return the minAsymmetricKeyLength
	 */
	public int getMinAsymmetricKeyLength() {
		return minAsymmetricKeyLength;
	}

	public String getPolicyUri() {
		return policyUri;
	}

	@Override
	public String toString() {
		return policyUri;
	}

	/**
	 * @return the policies
	 */
	public static Map<String, HttpsSecurityPolicy> getAvailablePolicies() {
		return availablePolicies;
	}

}
