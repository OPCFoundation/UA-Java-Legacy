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

package org.opcfoundation.ua.transport.tcp.nio;

import java.util.List;

import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.transport.security.SecurityConfiguration;
import org.opcfoundation.ua.transport.tcp.impl.ErrorMessage;
import org.opcfoundation.ua.transport.tcp.impl.SecurityToken;
import org.opcfoundation.ua.transport.tcp.impl.TcpMessageType;

/**
 * <p>InputMessage interface.</p>
 *
 */
public interface InputMessage {

	/**
	 * Get message if available. If message is not available, then error is.
	 *
	 * @return message or null
	 */
	IEncodeable getMessage();
	
	/**
	 * Get error if avaiable.
	 *
	 * @return error or null
	 */
	Exception getError();
		
	/**
	 * Get message type. One of the following:
	 *  {@link TcpMessageType#OPEN} Open Channel async message
	 *  {@link TcpMessageType#CLOSE} Close Channel async message
	 *  {@link TcpMessageType#MESSAGE} Service Request, or {@link ErrorMessage}
	 *
	 * @return message type
	 */
	int getMessageType();
	
	/**
	 * Get secure channel Id. Secure channel is 0 when opening a new secure channel.
	 *
	 * @return a int.
	 */
	int getSecureChannelId();	
	
	/**
	 * Get request id. Identifier is secure channel specific.
	 *
	 * @return a int.
	 */
	int getRequestId();	
	
	
	/**
	 * Return sequence number of each chunk
	 *
	 * @return list of sequence numbers
	 */
	List<Integer> getSequenceNumbers();
	
	/**
	 * Get security token
	 *
	 * @return {@link SecurityConfiguration} if async message, {@link SecurityToken} is sync message
	 */
	Object getToken();

	/**
	 * Security policy uri for async message
	 * 
	 * @return
	 */
//	String getSecurityPolicyUri();

	/**
	 * 
	 * 
	 * @return
	 */
//	byte[] getSenderCertificate();

//	byte[] getReceiverCertificateThumbprint();
	
	
}
