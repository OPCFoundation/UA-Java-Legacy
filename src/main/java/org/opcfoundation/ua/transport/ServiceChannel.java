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
	
	/**
	 * <p>Constructor for ServiceChannel.</p>
	 *
	 * @param channel a {@link org.opcfoundation.ua.transport.SecureChannel} object.
	 */
	public ServiceChannel(SecureChannel channel) {
		super(channel);
		this.channel = channel;
	}
	
	/** {@inheritDoc} */
	@Override
	public void close() {
		channel.close();
	}

	/** {@inheritDoc} */
	@Override
	public AsyncResult<SecureChannel> closeAsync() {
		return channel.closeAsync();
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		channel.dispose();		
	}

	/** {@inheritDoc} */
	@Override
	public String getConnectURL() {
		return channel.getConnectURL();
	}

	/** {@inheritDoc} */
	@Override
	public ServerConnection getConnection() {
		return channel.getConnection();
	}

	/** {@inheritDoc} */
	@Override
	public EndpointConfiguration getEndpointConfiguration() {
		return channel.getEndpointConfiguration();
	}

	/** {@inheritDoc} */
	@Override
	public EndpointDescription getEndpointDescription() {
		return channel.getEndpointDescription();
	}

	/** {@inheritDoc} */
	@Override
	public EncoderContext getMessageContext() {
		return channel.getMessageContext();
	}

	/** {@inheritDoc} */
	@Override
	public MessageSecurityMode getMessageSecurityMode() {
		return channel.getMessageSecurityMode();
	}

	/** {@inheritDoc} */
	@Override
	public int getOperationTimeout() {
		return channel.getOperationTimeout();
	}

	/** {@inheritDoc} */
	@Override
	public int getSecureChannelId() {
		return channel.getSecureChannelId();
	}

	/** {@inheritDoc} */
	@Override
	public SecurityPolicy getSecurityPolicy() {
		return channel.getSecurityPolicy();
	}

	/** {@inheritDoc} */
	@Override
	public void initialize(String url, TransportChannelSettings settings, EncoderContext ctx)
			throws ServiceResultException {
		channel.initialize(url, settings, ctx);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isOpen() {
		return channel.isOpen();
	}

	/** {@inheritDoc} */
	@Override
	public void open() throws ServiceResultException {
		channel.open();
	}

	/** {@inheritDoc} */
	@Override
	public AsyncResult<SecureChannel> openAsync() {
		return channel.openAsync();
	}

	/** {@inheritDoc} */
	@Override
	public ServiceResponse serviceRequest(ServiceRequest request)
			throws ServiceResultException {
		return channel.serviceRequest(request);
	}

	/** {@inheritDoc} */
	@Override
	public AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest request) {
		return channel.serviceRequestAsync(request);
	}

	/** {@inheritDoc} */
	@Override
	public void setOperationTimeout(int timeout) {
		channel.setOperationTimeout(timeout);
	}

	/** {@inheritDoc} */
	@Override
	public void initialize(TransportChannelSettings settings, EncoderContext ctx)
			throws ServiceResultException {
		channel.initialize(settings, ctx);
	}

}
