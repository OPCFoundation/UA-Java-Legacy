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

import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.encoding.IEncodeable;

/**
 * Hello is a message used in TCP Handshake.
 * 
 */
public class Hello implements IEncodeable {

	UnsignedInteger ProtocolVersion;
	UnsignedInteger ReceiveBufferSize;	// The largest message that the sender can receive
	UnsignedInteger SendBufferSize;		// The largest message that the sender will send
	UnsignedInteger MaxMessageSize;		// Max size for any response message
	UnsignedInteger MaxChunkCount;		// Max number of chunks in any response message
	String EndpointUrl;
	
	static
	{
		try {
			fields = new Field[]{
					Hello.class.getDeclaredField("ProtocolVersion"),
			Hello.class.getDeclaredField("ReceiveBufferSize"),
			Hello.class.getDeclaredField("SendBufferSize"),
			Hello.class.getDeclaredField("MaxMessageSize"),
			Hello.class.getDeclaredField("MaxChunkCount"),
			Hello.class.getDeclaredField("EndpointUrl")
			};
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
	}
	
	private static Field[] fields;
	
	/**
	 * @return the fields
	 */
	public static Field[] getFields() {
		return fields;
	}
	
	public Hello() {}
	
	public Hello(
			UnsignedInteger protocolVersion, 
			UnsignedInteger receiveBufferSize,
			UnsignedInteger sendBufferSize,
			UnsignedInteger maxMessageSize,
			UnsignedInteger maxChunkCount,
			String endpointUrl
			) {
		EndpointUrl = endpointUrl;
		MaxChunkCount = maxChunkCount;
		MaxMessageSize = maxMessageSize;
		ProtocolVersion = protocolVersion;
		ReceiveBufferSize = receiveBufferSize;
		SendBufferSize = sendBufferSize;
	}

	public UnsignedInteger getProtocolVersion() {
		return ProtocolVersion;
	}

	public void setProtocolVersion(UnsignedInteger protocolVersion) {
		ProtocolVersion = protocolVersion;
	}

	public UnsignedInteger getReceiveBufferSize() {
		return ReceiveBufferSize;
	}

	public void setReceiveBufferSize(UnsignedInteger receiveBufferSize) {
		ReceiveBufferSize = receiveBufferSize;
	}

	public UnsignedInteger getSendBufferSize() {
		return SendBufferSize;
	}

	public void setSendBufferSize(UnsignedInteger sendBufferSize) {
		SendBufferSize = sendBufferSize;
	}

	public UnsignedInteger getMaxChunkCount() {
		return MaxChunkCount;
	}

	public void setMaxChunkCount(UnsignedInteger maxChunkCount) {
		MaxChunkCount = maxChunkCount;
	}

	public String getEndpointUrl() {
		return EndpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		EndpointUrl = endpointUrl;
	}

	public UnsignedInteger getMaxMessageSize() {
		return MaxMessageSize;
	}

	public void setMaxMessageSize(UnsignedInteger maxMessageSize) {
		MaxMessageSize = maxMessageSize;
	}
		
	
}
