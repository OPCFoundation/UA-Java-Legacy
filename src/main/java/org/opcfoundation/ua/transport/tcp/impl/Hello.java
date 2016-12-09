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
	 * <p>Getter for the field <code>fields</code>.</p>
	 *
	 * @return the fields
	 */
	public static Field[] getFields() {
		return fields;
	}
	
	/**
	 * <p>Constructor for Hello.</p>
	 */
	public Hello() {}
	
	/**
	 * <p>Constructor for Hello.</p>
	 *
	 * @param protocolVersion a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @param receiveBufferSize a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @param sendBufferSize a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @param maxMessageSize a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @param maxChunkCount a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @param endpointUrl a {@link java.lang.String} object.
	 */
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

	/**
	 * <p>getProtocolVersion.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public UnsignedInteger getProtocolVersion() {
		return ProtocolVersion;
	}

	/**
	 * <p>setProtocolVersion.</p>
	 *
	 * @param protocolVersion a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public void setProtocolVersion(UnsignedInteger protocolVersion) {
		ProtocolVersion = protocolVersion;
	}

	/**
	 * <p>getReceiveBufferSize.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public UnsignedInteger getReceiveBufferSize() {
		return ReceiveBufferSize;
	}

	/**
	 * <p>setReceiveBufferSize.</p>
	 *
	 * @param receiveBufferSize a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public void setReceiveBufferSize(UnsignedInteger receiveBufferSize) {
		ReceiveBufferSize = receiveBufferSize;
	}

	/**
	 * <p>getSendBufferSize.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public UnsignedInteger getSendBufferSize() {
		return SendBufferSize;
	}

	/**
	 * <p>setSendBufferSize.</p>
	 *
	 * @param sendBufferSize a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public void setSendBufferSize(UnsignedInteger sendBufferSize) {
		SendBufferSize = sendBufferSize;
	}

	/**
	 * <p>getMaxChunkCount.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public UnsignedInteger getMaxChunkCount() {
		return MaxChunkCount;
	}

	/**
	 * <p>setMaxChunkCount.</p>
	 *
	 * @param maxChunkCount a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public void setMaxChunkCount(UnsignedInteger maxChunkCount) {
		MaxChunkCount = maxChunkCount;
	}

	/**
	 * <p>getEndpointUrl.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getEndpointUrl() {
		return EndpointUrl;
	}

	/**
	 * <p>setEndpointUrl.</p>
	 *
	 * @param endpointUrl a {@link java.lang.String} object.
	 */
	public void setEndpointUrl(String endpointUrl) {
		EndpointUrl = endpointUrl;
	}

	/**
	 * <p>getMaxMessageSize.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public UnsignedInteger getMaxMessageSize() {
		return MaxMessageSize;
	}

	/**
	 * <p>setMaxMessageSize.</p>
	 *
	 * @param maxMessageSize a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public void setMaxMessageSize(UnsignedInteger maxMessageSize) {
		MaxMessageSize = maxMessageSize;
	}
		
	
}
