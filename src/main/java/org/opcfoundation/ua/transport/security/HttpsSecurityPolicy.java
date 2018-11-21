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

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Definition of HTTPS Security Policies.
 */
public enum HttpsSecurityPolicy {
	
	/**
	 * TLS 1.0, not considered safe in UA 1.04
	 */
	TLS_1_0(
			SecurityPolicyUri.URI_TLS_1_0, 
			1024,4096, 
			new String[] {
					"..._RSA_WITH_RC4_128_SHA"
					}),
	
	/**
	 * TLS 1.1, not considered safe in UA 1.04
	 */
	TLS_1_1(
			SecurityPolicyUri.URI_TLS_1_1, 
			1024,4096, 
			new String[] {
					// RSA
					"..._RSA_WITH_3DES_EDE_CBC_SHA",
					SecurityPolicyUri.TLS_RSA_WITH_RC4_128_MD5, 
					SecurityPolicyUri.TLS_RSA_WITH_IDEA_CBC_SHA,
					SecurityPolicyUri.TLS_RSA_WITH_DES_CBC_SHA,
					SecurityPolicyUri.TLS_RSA_WITH_AES_256_CBC_SHA256,
					
					SecurityPolicyUri.TLS_DH_DSS_WITH_DES_CBC_SHA,
					SecurityPolicyUri.TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA,
					SecurityPolicyUri.TLS_DH_RSA_WITH_DES_CBC_SHA,
					SecurityPolicyUri.TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA,
					SecurityPolicyUri.TLS_DHE_DSS_WITH_DES_CBC_SHA,
					SecurityPolicyUri.TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA,
					SecurityPolicyUri.TLS_DHE_RSA_WITH_DES_CBC_SHA,
					SecurityPolicyUri.TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA,
					SecurityPolicyUri.TLS_DH_anon_WITH_RC4_128_MD5,
					SecurityPolicyUri.TLS_DH_anon_WITH_DES_CBC_SHA,
					SecurityPolicyUri.TLS_DH_anon_WITH_3DES_EDE_CBC_SHA
			}),
	/**
	 * TLS 1.2. Wont work at all or easily in java due to incompatible cipher suites, use {@link #TLS_1_2_PFS} instead.
	 */
	TLS_1_2(
			SecurityPolicyUri.URI_TLS_1_2, 
			2048,4096, 
			new String[] {
					SecurityPolicyUri.TLS_RSA_WITH_AES_256_CBC_SHA256}),
	
	/**
	 * TLS 1.2 with PFS. Only this policy should be used if possible.
	 */
	TLS_1_2_PFS(
			SecurityPolicyUri.URI_TLS_1_2_PFS, 
			2048,4096, 
			new String[] {
					SecurityPolicyUri.TLS_DHE_RSA_WITH_AES_128_CBC_SHA256,
					SecurityPolicyUri.TLS_DHE_RSA_WITH_AES_256_CBC_SHA256});

    /**
     * All HTTPS Security Policies defined in OPC UA 1.02. Includes {@link #TLS_1_0} and {@link #TLS_1_1}. This works with Java 6-8.
     * {@link #TLS_1_2} is also included in the specification, but it does not work with any Java implementation. This set in unmodifiable.
     */
	public static final Set<HttpsSecurityPolicy> ALL_102 = Collections.unmodifiableSet(EnumSet.of(TLS_1_0, TLS_1_1));
	
    /**
     * All HTTPS Security Policies defined in OPC UA 1.03. Includes only {@link #TLS_1_2_PFS}. This requires Java 8.
     * {@link #TLS_1_2} is also included in the specification, but it does not work with any Java implementation. This set in unmodifiable.
     */
	public static final Set<HttpsSecurityPolicy> ALL_103 = Collections.unmodifiableSet(EnumSet.of(TLS_1_2_PFS));
	
    /**
     * All HTTPS Security Policies defined in OPC UA 1.04. Includes only {@link #TLS_1_2_PFS}. This requires Java 8.
     * {@link #TLS_1_2} is also included in the specification, but it does not work with any Java implementation. This set in unmodifiable.
     */
	public static final Set<HttpsSecurityPolicy> ALL_104 = Collections.unmodifiableSet(EnumSet.of(TLS_1_2_PFS));

	
	/**
	 * And empty array constant. Can be used e.g. for converting the Set constants to an array with {@link Set#toArray(Object[])} more easily.
	 */
	public static final HttpsSecurityPolicy[] EMPTY_ARRAY = new HttpsSecurityPolicy[0];
	
	/** Security policy map */
	private static Map<String, HttpsSecurityPolicy> availablePolicies = new ConcurrentHashMap<String, HttpsSecurityPolicy>();

	private static void addAvailablePolicy(HttpsSecurityPolicy policy) {
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
