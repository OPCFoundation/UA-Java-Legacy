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
import java.util.HashSet;
import java.util.Set;

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
	public final static SecurityMode BASIC128RSA15_SIGN_ENCRYPT = new SecurityMode(SecurityPolicy.BASIC128RSA15, MessageSecurityMode.SignAndEncrypt);
	public final static SecurityMode BASIC128RSA15_SIGN = new SecurityMode(SecurityPolicy.BASIC128RSA15, MessageSecurityMode.Sign);
	public final static SecurityMode BASIC256_SIGN_ENCRYPT = new SecurityMode(SecurityPolicy.BASIC256, MessageSecurityMode.SignAndEncrypt);
	public final static SecurityMode BASIC256_SIGN = new SecurityMode(SecurityPolicy.BASIC256, MessageSecurityMode.Sign);
	public final static SecurityMode BASIC256SHA256_SIGN_ENCRYPT = new SecurityMode(SecurityPolicy.BASIC256SHA256, MessageSecurityMode.SignAndEncrypt);
	public final static SecurityMode BASIC256SHA256_SIGN = new SecurityMode(SecurityPolicy.BASIC256SHA256, MessageSecurityMode.Sign);
	public final static SecurityMode AES128_SIGN_ENCRYPT = new SecurityMode(SecurityPolicy.AES128_SHA256_RSAOAEP, MessageSecurityMode.SignAndEncrypt);
	public final static SecurityMode AES128_SIGN = new SecurityMode(SecurityPolicy.AES128_SHA256_RSAOAEP, MessageSecurityMode.Sign);
	public final static SecurityMode AES256_SIGN_ENCRYPT = new SecurityMode(SecurityPolicy.AES256_SHA256_RSAPSS, MessageSecurityMode.SignAndEncrypt);
	public final static SecurityMode AES256_SIGN = new SecurityMode(SecurityPolicy.AES256_SHA256_RSAPSS, MessageSecurityMode.Sign);
	
//	public final static SecurityMode PUBSUB_AES128CTR_SIGN = new SecurityMode(SecurityPolicy.PUBSUB_AES128_CTR, MessageSecurityMode.Sign);
//	public final static SecurityMode PUBSUB_AES256CTR_SIGN = new SecurityMode(SecurityPolicy.PUBSUB_AES256_CTR, MessageSecurityMode.Sign);
//	public final static SecurityMode PUBSUB_AES128CTR_SIGN_ENCRYPT = new SecurityMode(SecurityPolicy.PUBSUB_AES128_CTR, MessageSecurityMode.SignAndEncrypt);
//	public final static SecurityMode PUBSUB_AES256CTR_SIGN_ENCRYPT = new SecurityMode(SecurityPolicy.PUBSUB_AES256_CTR, MessageSecurityMode.SignAndEncrypt);

	//Helper for collections 
	public final static SecurityMode[] EMPTY_ARRAY = new SecurityMode[0];
	
	// Unsecure Security Mode
	public final static SecurityMode NONE = new SecurityMode(SecurityPolicy.NONE, MessageSecurityMode.None);

	private final SecurityPolicy securityPolicy;
	private final MessageSecurityMode messageSecurityMode;
	
	/**
	 * Creates all sensible combinations of the given {@link MessageSecurityMode} and {@link SecurityPolicy} sets. 
	 * Both {@link MessageSecurityMode#None} and {@link SecurityPolicy#NONE} will one by used if the other is present, and wont be combined with any others,
	 * i.e. a {@link SecurityMode#NONE} is only present in the list, if both {@link MessageSecurityMode#None} and {@link SecurityPolicy#NONE} are present in the respective lists.
	 * If either set is null or empty, an empty set is returned. The {@link MessageSecurityMode#Invalid} is ignored.
	 * 
	 * @param modes set of modes
	 * @param policies set of policies
	 * @return set of {@link SecurityMode}s, containing all permutations (with the None mode/policy pairing only with the other None).
	 */
	public static Set<SecurityMode> combinations(Set<MessageSecurityMode> modes, Set<SecurityPolicy> policies){
		if(modes == null || policies == null || modes.isEmpty() || policies.isEmpty()) {
			return Collections.emptySet();
		}
		Set<SecurityMode> r = new HashSet<SecurityMode>();
		if(modes.contains(MessageSecurityMode.None) && policies.contains(SecurityPolicy.NONE)) {
			r.add(SecurityMode.NONE);
		}
		for(MessageSecurityMode mode : modes) {
			if(MessageSecurityMode.None == mode || MessageSecurityMode.Invalid == mode) {
				continue;
			}
			for(SecurityPolicy policy : policies) {
				if(SecurityPolicy.NONE == policy) {
					continue;
				}
				r.add(new SecurityMode(policy, mode));
			}
		}
		return r;
	}
	

	/**
	 * Constructs a new SecurityMode combination of the given {@link SecurityPolicy} and {@link MessageSecurityMode}. Note! it is recommended to use the existing constants 
	 * or the {@link #combinations(Set, Set)} methods using the constants of the given classes instead of calling this directly.
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
	
}
