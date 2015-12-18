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

package org.opcfoundation.ua.transport;

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.transport.security.SecurityPolicy;

/**
 * This utility class envelopes Securechannel with client service methods.
 * See {@link ChannelService}.
 */
public class ServiceChannel extends ChannelService implements SecureChannel {

	SecureChannel channel;
	
	public ServiceChannel(SecureChannel channel) {
		super(channel);
		this.channel = channel;
	}
	
	@Override
	public void close() {
		channel.close();
	}

	@Override
	public AsyncResult<SecureChannel> closeAsync() {
		return channel.closeAsync();
	}

	@Override
	public void dispose() {
		channel.dispose();		
	}

	@Override
	public String getConnectURL() {
		return channel.getConnectURL();
	}

	@Override
	public ServerConnection getConnection() {
		return channel.getConnection();
	}

	@Override
	public EndpointConfiguration getEndpointConfiguration() {
		return channel.getEndpointConfiguration();
	}

	@Override
	public EndpointDescription getEndpointDescription() {
		return channel.getEndpointDescription();
	}

	@Override
	public EncoderContext getMessageContext() {
		return channel.getMessageContext();
	}

	@Override
	public MessageSecurityMode getMessageSecurityMode() {
		return channel.getMessageSecurityMode();
	}

	@Override
	public int getOperationTimeout() {
		return channel.getOperationTimeout();
	}

	@Override
	public int getSecureChannelId() {
		return channel.getSecureChannelId();
	}

	@Override
	public SecurityPolicy getSecurityPolicy() {
		return channel.getSecurityPolicy();
	}

	@Override
	public void initialize(String url, TransportChannelSettings settings, EncoderContext ctx)
			throws ServiceResultException {
		channel.initialize(url, settings, ctx);
	}

	@Override
	public boolean isOpen() {
		return channel.isOpen();
	}

	@Override
	public void open() throws ServiceResultException {
		channel.open();
	}

	@Override
	public AsyncResult<SecureChannel> openAsync() {
		return channel.openAsync();
	}

	@Override
	public ServiceResponse serviceRequest(ServiceRequest request)
			throws ServiceResultException {
		return channel.serviceRequest(request);
	}

	@Override
	public AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest request) {
		return channel.serviceRequestAsync(request);
	}

	@Override
	public void setOperationTimeout(int timeout) {
		channel.setOperationTimeout(timeout);
	}

	@Override
	public void initialize(TransportChannelSettings settings, EncoderContext ctx)
			throws ServiceResultException {
		channel.initialize(settings, ctx);
	}

}
