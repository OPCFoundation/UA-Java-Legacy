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

import org.opcfoundation.ua.utils.StackUtils;

/**
 * TCP Connection parameters
 */
public class TcpConnectionParameters implements Cloneable {
	
	public int maxSendMessageSize = 0; // 0 = no limit
	public int maxSendChunkSize = (StackUtils.cores() > 1) ? 8196 : 65536;
	public int maxSendChunkCount = 0; // 0 = no limit
	
	public int maxRecvMessageSize = 0; // 0 = no limit
	public int maxRecvChunkSize = (StackUtils.cores() > 1) ? 8196 : 65536;
	public int maxRecvChunkCount = 0; // 0 = no limit
	
	public String endpointUrl;

	/** {@inheritDoc} */
	@Override
	public TcpConnectionParameters clone() {
		try {
			return (TcpConnectionParameters) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
