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

import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.core.TestServiceSetHandler;
import org.opcfoundation.ua.core.TestStackExRequest;
import org.opcfoundation.ua.core.TestStackExResponse;
import org.opcfoundation.ua.core.TestStackRequest;
import org.opcfoundation.ua.core.TestStackResponse;
import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;

/**
 * Service handler that implements stack test
 * 
 */
public class TestStackService implements TestServiceSetHandler {

	public void onTestStack(EndpointServiceRequest<TestStackRequest, TestStackResponse> req) throws ServiceFaultException {
		req.sendResponse( new TestStackResponse(null, req.getRequest().getInput() ) );
	}

	public void onTestStackEx(EndpointServiceRequest<TestStackExRequest, TestStackExResponse> req) throws ServiceFaultException {
		TestStackExResponse res = new TestStackExResponse();
		res.setOutput( req.getRequest().getInput() );		
		req.sendResponse( res );
	}

}
