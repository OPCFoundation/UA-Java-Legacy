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

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;

/**
 * <p>ChunkUtils class.</p>
 */
public class ChunkUtils {

	private static Charset UTF8 = Charset.forName("UTF8"); 

	/**
	 * <p>getMessageType.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @return a int.
	 */
	public static int getMessageType(ByteBuffer chunk)
	{
		chunk.position(0);
		return chunk.getInt();
	}
	
	/**
	 * <p>getSecureChannelId.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @return a int.
	 */
	public static int getSecureChannelId(ByteBuffer chunk)
	{
		chunk.position(8);
		return chunk.getInt();
	}
	
	/**
	 * <p>getTokenId.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @return a int.
	 */
	public static int getTokenId(ByteBuffer chunk)
	{
		chunk.position(12);
		return chunk.getInt();
	}
	
	/**
	 * Get sequence number of a symmetric message
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @return a int.
	 */
	public static int getSequenceNumber(ByteBuffer chunk)
	{
		chunk.position(16);
		return chunk.getInt();
	}
	
	/**
	 * <p>getRecvCertificateThumbprint.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @return an array of byte.
	 */
	public static byte[] getRecvCertificateThumbprint(ByteBuffer chunk)
	{
		chunk.position(12);
		int policyUriLength = chunk.getInt();
		if (policyUriLength>0)
			chunk.position( chunk.position()+policyUriLength );
		int senderCertLength = chunk.getInt();
		if (senderCertLength>0)
			chunk.position( chunk.position()+senderCertLength );
		return getByteString(chunk);
	}
		
	/**
	 * <p>getRequestId.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @return a int.
	 */
	public static int getRequestId(ByteBuffer chunk)
	{
		chunk.position(20);
		return chunk.getInt();
	}
	
	/**
	 * <p>getAbortMessage.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @return a {@link java.lang.String} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public static String getAbortMessage(ByteBuffer chunk)
	throws ServiceResultException
	{
		chunk.position(8);
		return getString(chunk);		
	}

	/**
	 * <p>getSecurityPolicyUri.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @return a {@link java.lang.String} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public static String getSecurityPolicyUri(ByteBuffer chunk)
	throws ServiceResultException
	{
		chunk.position(12);
		return getString(chunk);		
	}	
	
	/**
	 * <p>getString.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @return a {@link java.lang.String} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public static String getString(ByteBuffer chunk)
	throws ServiceResultException
	{
		byte dada[] = getByteString(chunk);
		String result = new String(dada, UTF8);
		return result;
	}	

	/**
	 * <p>getByteString.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @return an array of byte.
	 */
	public static byte[] getByteString(ByteBuffer chunk)
	{
		int length = chunk.getInt();
		if (length==-1) return null;
		if ( (length<-1) || (length > chunk.remaining()) ) 
			new ServiceResultException(StatusCodes.Bad_CommunicationError, "Unexpected length");
		byte dada[] = new byte[length];
		chunk.get(dada);
		return dada;
	}
	
	
	
}
