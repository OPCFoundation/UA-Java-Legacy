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

/**
 * 
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class SecurityConstants {
	
	public static final String SECURITY_POLICY_URI_BINARY_NONE = "http://opcfoundation.org/UA/SecurityPolicy#None";
	public static final String SECURITY_POLICY_URI_BINARY_BASIC128RSA15 = "http://opcfoundation.org/UA/SecurityPolicy#Basic128Rsa15";		
	public static final String SECURITY_POLICY_URI_BINARY_BASIC256 = "http://opcfoundation.org/UA/SecurityPolicy#Basic256";	

	public static final String SECURITY_POLICY_URI_XML_NONE = "http://opcfoundation.org/UA-Profile/Securitypolicy/None";
	public static final String SECURITY_POLICY_URI_XML_BASIC128RSA15 = "http://opcfoundation.org/UA-Profile/Securitypolicy/Basic128Rsa15";		
	public static final String SECURITY_POLICY_URI_XML_BASIC256 = "http://opcfoundation.org/UA-Profile/Securitypolicy/Basic256";

	// Symmetric signature	
	public static final String HmacSha1 = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
	public static final String HmacSha256 = "http://www.w3.org/2000/09/xmldsig#hmac-sha256";

	// Asymmetric encryption
	public static final String Rsa15 = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
	public static final String RsaOaep = "http://www.w3.org/2001/04/xmlenc#rsa-oaep";
	
	// Asymmetric signature
	public static final String RsaSha1 = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
	
	// Asymmetric keywrap
	public static final String KwRsaOaep = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
	public static final String KwRsa15 = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
		
	// Symmetric encryption
	public static final String Aes128 = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
	public static final String Aes256 = "http://www.w3.org/2001/04/xmlenc#aes256-cbc";
	
	// key derivation
	public static final String PSha1 = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";	
	
}
