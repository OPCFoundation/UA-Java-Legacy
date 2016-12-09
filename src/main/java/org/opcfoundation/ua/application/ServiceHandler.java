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

package org.opcfoundation.ua.application;

import java.util.Collection;

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;

/**
 * Service Handler reads {@link ServiceRequest} from client, processes it, and returns
 * a {@link ServiceResponse}.
 *
 * @see ServiceHandlerComposition
 * @see AbstractServiceHandler
 */
public interface ServiceHandler {

	/**
	 * Serve a service request.
	 * <p>
	 * The implementation is allowed to may submit the response
	 * later and from another thread.
	 *
	 * @param request the service request
	 * @throws org.opcfoundation.ua.common.ServiceResultException if error
	 */
	void serve(EndpointServiceRequest<?, ?> request) throws ServiceResultException;

	/**
	 * Queries whether this handler supports a given request class.
	 *
	 * @param requestMessageClass class
	 * @return true if this service handler can handle given class
	 */
	boolean supportsService(Class<? extends IEncodeable> requestMessageClass);
	
	/**
	 * Get supported services. Result will be filled with the request class of
	 * the supported services.
	 *
	 * @param result to be filled with request classes of supported services.
	 */
	void getSupportedServices(Collection<Class<? extends IEncodeable>> result);
	
	
}
