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

import org.opcfoundation.ua.core.MessageSecurityMode;

/**
 * Binding of {@link SecurityPolicy} and {@link MessageSecurityMode}.
 * <p>
 * Security Policy determines which algorithms to use during asymmetric and symmetric
 * encryption.
 * <p>
 * MessageSecurityMode determines whether to use encryption and whether to use signing
 * during _symmetric_ encryption, which is after hand-shake.
 */
public final class SecurityMode {
		
	// Secure Security Modes
	/** Constant <code>BASIC128RSA15_SIGN_ENCRYPT</code> */
	public final static SecurityMode BASIC128RSA15_SIGN_ENCRYPT = new SecurityMode(SecurityPolicy.BASIC128RSA15, MessageSecurityMode.SignAndEncrypt);
	/** Constant <code>BASIC128RSA15_SIGN</code> */
	public final static SecurityMode BASIC128RSA15_SIGN = new SecurityMode(SecurityPolicy.BASIC128RSA15, MessageSecurityMode.Sign);
	/** Constant <code>BASIC256_SIGN_ENCRYPT</code> */
	public final static SecurityMode BASIC256_SIGN_ENCRYPT = new SecurityMode(SecurityPolicy.BASIC256, MessageSecurityMode.SignAndEncrypt);
	/** Constant <code>BASIC256_SIGN</code> */
	public final static SecurityMode BASIC256_SIGN = new SecurityMode(SecurityPolicy.BASIC256, MessageSecurityMode.Sign);
	/** Constant <code>BASIC256SHA256_SIGN_ENCRYPT</code> */
	public final static SecurityMode BASIC256SHA256_SIGN_ENCRYPT = new SecurityMode(SecurityPolicy.BASIC256SHA256, MessageSecurityMode.SignAndEncrypt);
	/** Constant <code>BASIC256SHA256_SIGN</code> */
	public final static SecurityMode BASIC256SHA256_SIGN = new SecurityMode(SecurityPolicy.BASIC256SHA256, MessageSecurityMode.Sign);

	// Unsecure Security Mode
	/** Constant <code>NONE</code> */
	public final static SecurityMode NONE = new SecurityMode(SecurityPolicy.NONE, MessageSecurityMode.None);
	
	// Security Mode Sets
	// The 101-modes are the default for the time being, until all stacks add support for BASIC256SHA256
	/** Constant <code>ALL_102</code> */
	public final static SecurityMode[] ALL_102 = new SecurityMode[] {NONE, BASIC128RSA15_SIGN, BASIC128RSA15_SIGN_ENCRYPT, BASIC256_SIGN, BASIC256_SIGN_ENCRYPT, BASIC256SHA256_SIGN, BASIC256SHA256_SIGN_ENCRYPT}; 
	/** Constant <code>ALL_101</code> */
	public final static SecurityMode[] ALL_101 = new SecurityMode[] {NONE, BASIC128RSA15_SIGN, BASIC128RSA15_SIGN_ENCRYPT, BASIC256_SIGN, BASIC256_SIGN_ENCRYPT}; 
	/** Constant <code>ALL</code> */
	public final static SecurityMode[] ALL = ALL_101; 
	/** Constant <code>SECURE_102</code> */
	public final static SecurityMode[] SECURE_102 = new SecurityMode[] {BASIC128RSA15_SIGN, BASIC128RSA15_SIGN_ENCRYPT, BASIC256_SIGN, BASIC256_SIGN_ENCRYPT, BASIC256SHA256_SIGN, BASIC256SHA256_SIGN_ENCRYPT}; 
	/** Constant <code>SECURE_101</code> */
	public final static SecurityMode[] SECURE_101 = new SecurityMode[] {BASIC128RSA15_SIGN, BASIC128RSA15_SIGN_ENCRYPT, BASIC256_SIGN, BASIC256_SIGN_ENCRYPT}; 
	/** Constant <code>SECURE</code> */
	public final static SecurityMode[] SECURE = SECURE_101; 
	/** Constant <code>NON_SECURE</code> */
	public final static SecurityMode[] NON_SECURE = new SecurityMode[] {NONE}; 	

	private final SecurityPolicy securityPolicy;
	private final MessageSecurityMode messageSecurityMode;
	
	/**
	 * Create all permutations of security policies and message security modes.
	 *
	 * @param securityPolicies an array of {@link org.opcfoundation.ua.transport.security.SecurityPolicy} objects.
	 * @param messageSecurityModes an array of {@link org.opcfoundation.ua.core.MessageSecurityMode} objects.
	 * @return all permutations
	 */
	public static SecurityMode[] create(SecurityPolicy[] securityPolicies, MessageSecurityMode[] messageSecurityModes)
	{
		SecurityMode[] result = new SecurityMode[ securityPolicies.length * messageSecurityModes.length ];
		for (int i=0; i<securityPolicies.length; i++) {
			for (int j=0; j<messageSecurityModes.length; j++) {
				int x = i*messageSecurityModes.length + j;
				result[x] = new SecurityMode(securityPolicies[i], messageSecurityModes[j]);
			}
		}
		return result;
	}
		
	/**
	 * <p>Constructor for SecurityMode.</p>
	 *
	 * @param securityPolicy a {@link org.opcfoundation.ua.transport.security.SecurityPolicy} object.
	 * @param messageSecurityMode a {@link org.opcfoundation.ua.core.MessageSecurityMode} object.
	 */
	public SecurityMode(SecurityPolicy securityPolicy, MessageSecurityMode messageSecurityMode) {
		if (securityPolicy==null || messageSecurityMode==null) 
			throw new IllegalArgumentException("null arg");
		this.securityPolicy = securityPolicy;
		this.messageSecurityMode = messageSecurityMode;
	}
	
	/**
	 * <p>Getter for the field <code>securityPolicy</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.SecurityPolicy} object.
	 */
	public SecurityPolicy getSecurityPolicy() {
		return securityPolicy;
	}
	
	/**
	 * <p>Getter for the field <code>messageSecurityMode</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.MessageSecurityMode} object.
	 */
	public MessageSecurityMode getMessageSecurityMode() {
		return messageSecurityMode;
	}
	
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return securityPolicy.hashCode() ^ messageSecurityMode.hashCode();
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SecurityMode)) return false;
		SecurityMode other = (SecurityMode) obj;
		return other.securityPolicy == securityPolicy && other.messageSecurityMode == messageSecurityMode;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "["+securityPolicy.getPolicyUri()+","+messageSecurityMode+"]";
	}
	
	/**
	 * <p>join.</p>
	 *
	 * @param a an array of {@link org.opcfoundation.ua.transport.security.SecurityMode} objects.
	 * @param b an array of {@link org.opcfoundation.ua.transport.security.SecurityMode} objects.
	 * @return an array of {@link org.opcfoundation.ua.transport.security.SecurityMode} objects.
	 */
	public static SecurityMode[] join(SecurityMode[] a, SecurityMode[] b) {
		SecurityMode[] result = new SecurityMode[a.length + b.length];
		for ( int i=0; i<a.length; i++) result[i] = a[i];
		for ( int j=0; j<b.length; j++) result[j+a.length] = b[j];
		return result;
	}
	
}
