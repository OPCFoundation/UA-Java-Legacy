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

import java.nio.charset.Charset;

public class SecurityPolicyUri {

	public static final String URI_BINARY_NONE = "http://opcfoundation.org/UA/SecurityPolicy#None";
	public static final String URI_BINARY_BASIC128RSA15 = "http://opcfoundation.org/UA/SecurityPolicy#Basic128Rsa15";
	public static final String URI_BINARY_BASIC256 = "http://opcfoundation.org/UA/SecurityPolicy#Basic256";
	public static final String URI_BINARY_BASIC256SHA256 = "http://opcfoundation.org/UA/SecurityPolicy#Basic256Sha256";
	public static final String URI_BINARY_AES128_SHA256_RSAOAEP = "http://opcfoundation.org/UA/SecurityPolicy#Aes128_Sha256_RsaOaep";
	public static final String URI_BINARY_AES256_SHA256_RSAPSS = "http://opcfoundation.org/UA/SecurityPolicy#Aes256_Sha256_RsaPss";
	public static final String URI_BINARY_PUBSUB_AES128_CTR = "http://opcfoundation.org/UA/SecurityPolicy#PubSub_Aes128_CTR";
	public static final String URI_BINARY_PUBSUB_AES256_CTR = "http://opcfoundation.org/UA/SecurityPolicy#PubSub_Aes256_CTR";
	public static final String URI_XML_NONE = "http://opcfoundation.org/UA-Profile/Securitypolicy/None";
	public static final String URI_XML_BASIC128RSA15 = "http://opcfoundation.org/UA-Profile/Securitypolicy/Basic128Rsa15";
	public static final String URI_XML_BASIC256 = "http://opcfoundation.org/UA-Profile/Securitypolicy/Basic256";
	public static final String URI_XML_PUBSUBAES128CTR = "http://opcfoundation.org/UA-Profile/Securitypolicy/PubSubAes128Ctr";
	public static final String URI_XML_PUBSUBAES256CTR = "http://opcfoundation.org/UA-Profile/Securitypolicy/PubSubAes256Ctr";
	
	static final Charset UTF8 = Charset.forName("utf-8");
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

	private SecurityPolicyUri() {
		//Should not be instantiated as only contains constants.
	}
	
}
