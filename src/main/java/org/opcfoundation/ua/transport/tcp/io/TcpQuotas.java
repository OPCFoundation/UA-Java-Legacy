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
 * Tcp connection quotas. Quota values are negotiated between client and server.
 * The negotiated values are stored in {@link TcpConnectionLimits}.
 */
public class TcpQuotas {

	/** Constant <code>DEFAULT_CLIENT_QUOTA</code> */
	public static final TcpQuotas DEFAULT_CLIENT_QUOTA = new TcpQuotas(Integer.MAX_VALUE, TcpMessageLimits.MaxBufferSize, TcpMessageLimits.DefaultChannelLifetime, TcpMessageLimits.DefaultSecurityTokenLifeTime); 
	/** Constant <code>DEFAULT_SERVER_QUOTA</code> */
	public static final TcpQuotas DEFAULT_SERVER_QUOTA = new TcpQuotas(Integer.MAX_VALUE, TcpMessageLimits.MaxBufferSize, TcpMessageLimits.DefaultChannelLifetime, TcpMessageLimits.DefaultSecurityTokenLifeTime); 
	
	public final int maxMessageSize;
	public final int maxBufferSize;
	public final int channelLifetime;
	public final int securityTokenLifetime;
	
	/**
	 * <p>Constructor for TcpQuotas.</p>
	 *
	 * @param maxMessageSize a int.
	 * @param maxBufferSize a int.
	 * @param channelLifetime a int.
	 * @param securityTokenLifetime a int.
	 */
	public TcpQuotas(int maxMessageSize, int maxBufferSize,
			int channelLifetime, int securityTokenLifetime) {
		
		if (maxBufferSize < TcpMessageLimits.MinBufferSize)
			throw new IllegalArgumentException();
		
		this.maxMessageSize = maxMessageSize;
		this.maxBufferSize = maxBufferSize;
		this.channelLifetime = channelLifetime;
		this.securityTokenLifetime = securityTokenLifetime;
	}
	
}
