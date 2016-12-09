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

package org.opcfoundation.ua.common;

import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.core.ServiceFault;
import org.opcfoundation.ua.core.StatusCodes;

/**
 * ServiceFaultException is an error that occurs in execution an operation.
 * It wraps {@link ServiceFault} into an exception.
 */
public class ServiceFaultException extends ServiceResultException {

	private static final long serialVersionUID = 1L;
	
	ServiceFault serviceFault;
	DiagnosticInfo di;

	/**
	 * <p>Constructor for ServiceFaultException.</p>
	 *
	 * @param t a {@link java.lang.Throwable} object.
	 */
	public ServiceFaultException(Throwable t)
	{
		super(t);
		this.serviceFault = ServiceFault.toServiceFault(t);
	}
	
	/**
	 * <p>Constructor for ServiceFaultException.</p>
	 *
	 * @param serviceFault a {@link org.opcfoundation.ua.core.ServiceFault} object.
	 */
	public ServiceFaultException(ServiceFault serviceFault) 
	{
		super(serviceFault.getResponseHeader()==null?new StatusCode(StatusCodes.Bad_InternalError):
			serviceFault.getResponseHeader().getServiceDiagnostics()==null?new StatusCode(StatusCodes.Bad_InternalError):
			serviceFault.getResponseHeader().getServiceDiagnostics().getInnerStatusCode()==null?new StatusCode(StatusCodes.Bad_InternalError): serviceFault.getResponseHeader().getServiceDiagnostics().getInnerStatusCode()  );
		this.serviceFault = serviceFault;
	}

	/**
	 * <p>Getter for the field <code>serviceFault</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.ServiceFault} object.
	 */
	public ServiceFault getServiceFault()
	{
		return serviceFault;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return serviceFault.toString();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getMessage() {
		return serviceFault.toString();
	}
	
    /**
     * <p>getStatusCode.</p>
     *
     * @return a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
     */
    public StatusCode getStatusCode()
    {
    	if (serviceFault.getResponseHeader()==null || serviceFault.getResponseHeader().getServiceResult()==null)
    		return new StatusCode(StatusCodes.Bad_InternalError);
    	return serviceFault.getResponseHeader().getServiceResult();
    }

}
