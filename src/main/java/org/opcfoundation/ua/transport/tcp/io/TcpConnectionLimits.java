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

package org.opcfoundation.ua.transport.tcp.io;


/**
 * Negotiated connection limits.
 */
public class TcpConnectionLimits implements Cloneable {
		
	public int maxSendMessageSize; 
	public int maxSendBufferSize;
	public int maxSendChunkCount;
	
	public int maxRecvMessageSize;
	public int maxRecvBufferSize;
	public int maxRecvChunkCount;

	/** {@inheritDoc} */
	@Override
	//Git issue 245
	//Suggestion provided by CWE link (http://cwe.mitre.org/data/definitions/491.html)
	public final TcpConnectionLimits clone() {
		try {
			return (TcpConnectionLimits) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
