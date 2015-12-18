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

import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.transport.AsyncWrite;
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.ServerSecureChannel;
import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;
import org.opcfoundation.ua.transport.tcp.impl.TcpMessageType;

public class PendingRequest extends EndpointServiceRequest<ServiceRequest, ServiceResponse> {
	
	OpcTcpServerSecureChannel channel;
	IEncodeable requestMessage;
	int requestId;
	AsyncWrite write;
	
	public PendingRequest(OpcTcpServerSecureChannel channel, Endpoint endpoint, Server server, int requestId, ServiceRequest requestMessage) {
		super(requestMessage, server, endpoint);
		this.channel = channel;
		this.requestId = requestId;
		this.requestMessage = requestMessage;
	}
	
	@Override
	public ServerSecureChannel getChannel() {
		return channel;
	}
	
	@Override
	public void sendResponse(AsyncWrite response) {
		channel.connection.pendingRequests.remove(requestId);
		channel.connection.sendSecureMessage(response, channel.getActiveSecurityToken(), requestId, TcpMessageType.MESSAGE, channel.sendSequenceNumber);
	}
	
	@Override
	public AsyncWrite sendResponse(ServiceResponse response) {
		write = new AsyncWrite(response);
		sendResponse(write);
		return write;
	}
	
}