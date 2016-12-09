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

package org.opcfoundation.ua.transport.endpoint;

import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.ServiceResult;
import org.opcfoundation.ua.core.ServiceFault;
import org.opcfoundation.ua.transport.AsyncWrite;
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.ServerSecureChannel;

/**
 * Asynchronous message exchange.
 *
 * A service request to be processed by a server (as opposed to service request queried by a client).
 * <p>
 * To send service error use sendResponse(new ServiceFault());
 */
public abstract class EndpointServiceRequest<Request extends ServiceRequest, Response extends ServiceResponse> {

	protected Server server;
	protected Endpoint endpoint;
	protected Request request;

	/**
	 * <p>Constructor for EndpointServiceRequest.</p>
	 *
	 * @param request a Request object.
	 * @param server a {@link org.opcfoundation.ua.application.Server} object.
	 * @param endpoint a {@link org.opcfoundation.ua.transport.Endpoint} object.
	 */
	public EndpointServiceRequest(Request request, Server server, Endpoint endpoint)
	{
		this.request = request;
		this.server = server;
		this.endpoint = endpoint;
	}
	
	/**
	 * Get Request. The request is in Complete state.
	 *
	 * @return read request
	 */
	public Request getRequest() {
		return request;
	}
	
	/**
	 * Get server
	 *
	 * @return server
	 */
	public Server getServer() {
		return server;
	}
	
	/**
	 * Get endpoint
	 *
	 * @return endpoint
	 */
	public Endpoint getEndpoint() {
		return endpoint;
	}
	
	/**
	 * Send response.
	 *
	 * @param response async write wrapping response or {@link ServiceFault}
	 */
	public abstract void sendResponse(AsyncWrite response);

	/**
	 * Send a response.
	 *
	 * @param response to send, either {@link ServiceFault} or {@link ServiceResult}
	 * @return monitor for write status
	 */
	public abstract AsyncWrite sendResponse(Response response);

	/**
	 * Send a service fault
	 *
	 * @param fault error
	 */
	public void sendFault(ServiceFault fault) {
		sendResponse( new AsyncWrite( fault ) );
	}	
	
	/**
	 * Convert Throwable into an Service fault and send that to the client.
	 * NOTE! This is a convenience method that exposes stack trace to the client.
	 * Use with care!
	 *
	 * @param e ServiceResultException or other
	 */
	public void sendException(Throwable e) {
		sendResponse( new AsyncWrite( ServiceFault.toServiceFault(e) ) );
	}
	
	/**
	 * <p>getChannel.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.ServerSecureChannel} object.
	 */
	public abstract ServerSecureChannel getChannel();
	
}
