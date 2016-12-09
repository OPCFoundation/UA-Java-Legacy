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

import java.util.EnumSet;

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.TransportChannelSettings;

/**
 * <p>ITransportChannel interface.</p>
 *
 */
public interface ITransportChannel {
	
	/**
	 * Initialize a secure channel with endpoint identified by the URL.
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param settings a {@link org.opcfoundation.ua.transport.TransportChannelSettings} object.
	 * @param ctx a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	void initialize(String url, TransportChannelSettings settings, EncoderContext ctx) throws ServiceResultException;
	
	/**
	 * Send a service request over the secure channel. <p>
	 *
	 * If the operation timeouts or the thread is interrupted a
	 * ServiceResultException is thrown with {@link StatusCodes#Bad_Timeout}.<p>
	 *
	 * @param request a {@link org.opcfoundation.ua.builtintypes.ServiceRequest} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 * @return a {@link org.opcfoundation.ua.builtintypes.ServiceResponse} object.
	 */
	ServiceResponse serviceRequest(ServiceRequest request) throws ServiceResultException;
	
	/**
	 * Asynchronous operation to send a request over the secure channel.
	 *
	 * @param request the request
	 * @return the result
	 */
	AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest request);

	/**
	 * Send a service request over the secure channel. <p>
	 *
	 * If the operation timeouts or the operation is interrupted and a
	 * ServiceResultException is thrown with {@link StatusCodes#Bad_Timeout}.<p>
	 *
	 * @param request a {@link org.opcfoundation.ua.builtintypes.ServiceRequest} object.
	 * @param operationTimeout timeout time in milliseconds
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 * @return a {@link org.opcfoundation.ua.builtintypes.ServiceResponse} object.
	 */
	ServiceResponse serviceRequest(ServiceRequest request, long operationTimeout) throws ServiceResultException;
	
	/**
	 * Asynchronous operation to send a request over the secure channel.
	 *
	 * @param request the request
	 * @param operationTimeout timeout time
	 * @return the result
	 */
	AsyncResult<ServiceResponse> serviceRequestAsync(ServiceRequest request, long operationTimeout);
	
	/**
	 * Get a list of features supported by the channel.
	 *
	 * @return a {@link java.util.EnumSet} object.
	 */
	EnumSet<TransportChannelFeature> getSupportedFeatures();
	public enum TransportChannelFeature { open, openAsync, reconnect, reconnectAsync, sendRequest, sendRequestAsync, close, closeAync }	

	/**
	 * <p>getEndpointDescription.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.EndpointDescription} object.
	 */
	EndpointDescription getEndpointDescription();
	
	/**
	 * <p>getEndpointConfiguration.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.EndpointConfiguration} object.
	 */
	EndpointConfiguration getEndpointConfiguration();
	
	/**
	 * <p>getMessageContext.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	EncoderContext getMessageContext();
	
	/**
	 * Set operation timeout
	 *
	 * @param timeout in milliseconds
	 */
	void setOperationTimeout(int timeout);
	
	/**
	 * Get operation timeout
	 *
	 * @return timeout in milliseconds
	 */
	int getOperationTimeout();
	
	/**
	 * <p>dispose.</p>
	 */
	void dispose();
	
}
