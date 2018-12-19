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

package org.opcfoundation.ua.transport.tcp.impl;

import java.lang.reflect.Field;

import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.utils.ObjectUtils;

/**
 * ReverseHello is a message used in "reverse" TCP Handshake. Please see 1.04
 * Part 6 section 7.1.2.6 and 7.1.3 for more information.
 *
 */
public class ReverseHello implements IEncodeable{

	String ServerUri; // ApplicationUri of the server
	String EndpointUrl; // URL Endpoint the client should send Hello

	private static Field[] fields;

	static {
		try {
			fields = new Field[]{
					ReverseHello.class.getDeclaredField("ServerUri"),
					ReverseHello.class.getDeclaredField("EndpointUrl"),};
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public static Field[] getFields() {
		return fields;
	}
	
	public ReverseHello() {

	}

	public ReverseHello(String serverUri, String endpointUrl) {
		this.ServerUri = serverUri;
		this.EndpointUrl = endpointUrl;
	}

	public String getServerUri() {
		return ServerUri;
	}

	public void setServerUri(String serverUri) {
		ServerUri = serverUri;
	}

	public String getEndpointUrl() {
		return EndpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		EndpointUrl = endpointUrl;
	}
	
	@Override
	public String toString() {
		return "ReverseHello: "+ObjectUtils.printFieldsDeep(this);
	}
	
}
