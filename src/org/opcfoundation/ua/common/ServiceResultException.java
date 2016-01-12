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

import java.util.Arrays;

import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.ServiceResult;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.StatusCodes;

/**
 * Generic Exception
 * 
 * 
 * @see StatusCodes
 */
public class ServiceResultException extends Exception {

    private static final long serialVersionUID = 988605552235028178L;

    final protected StatusCode statusCode;
    final protected String text;

    public ServiceResultException(String message)
    {
    	this(new StatusCode(StatusCodes.Bad_UnexpectedError),  message);
    }
    
    public ServiceResultException(int statusCode)
    {
        this(StatusCode.getFromBits(statusCode), StatusCodeDescriptions.getStatusCodeDescription(statusCode));
    }

    public ServiceResultException(int statusCode, String text)
    {
        this(StatusCode.getFromBits(statusCode), text);
    }
    
    public ServiceResultException(UnsignedInteger statusCode)
    {
        this(new StatusCode(statusCode), StatusCodeDescriptions.getStatusCodeDescription(statusCode.intValue()));
    }

    public ServiceResultException(UnsignedInteger statusCode, String text)
    {
        this(new StatusCode(statusCode), text);
    }    

    public ServiceResultException(UnsignedInteger statusCode, Throwable reason, String text)
    {
    	super(text, reason);
        if (statusCode==null)
            throw new IllegalArgumentException("statusCode is null");        
        this.statusCode = new StatusCode(statusCode);
        this.text = text;        
    }    
    
    public ServiceResultException(StatusCode statusCode)
    {
        this(statusCode, statusCode.getDescription()!=null ? statusCode.getDescription() : "");
    }

    public ServiceResultException(StatusCode statusCode, String text)
    {
        if (statusCode==null)
            throw new IllegalArgumentException("statusCode is null");
        this.statusCode = statusCode;
        this.text = text;
    }

    public ServiceResultException(StatusCode statusCode, Throwable reason, String text)
    {
    	super(text, reason);
        if (statusCode==null)
            throw new IllegalArgumentException("statusCode is null");        
        this.statusCode = statusCode;
        this.text = text;        
    }

    public ServiceResultException(UnsignedInteger statusCode, Throwable reason)
    {
    	super(reason.getMessage(), reason);
        if (statusCode==null)
            throw new IllegalArgumentException("statusCode is null");        
        this.statusCode = new StatusCode(statusCode);
        this.text = statusCode.toString() + ", " + reason.getMessage();        
    }
    
    public ServiceResultException(StatusCode statusCode, Throwable reason)
    {
    	super(reason.getMessage(), reason);
        if (statusCode==null)
            throw new IllegalArgumentException("statusCode is null");        
        this.statusCode = statusCode;
        this.text = statusCode.toString() + ", " + reason.getMessage();        
    }

    public ServiceResultException(Throwable reason)
    {
    	super(reason);
        this.statusCode = new StatusCode(StatusCodes.Bad_UnexpectedError);
        this.text = reason.getMessage();        
    }
    
    @Override
    public String getMessage() {
        if (text!=null)
            return String.format("%s (code=0x%08X, description=\"%s\")", statusCode.getName(), statusCode.getValueAsIntBits(), text);
        return statusCode.toString();
    }
    
    public StatusCode getStatusCode() {
        return statusCode;
    }
        
    public String getAdditionalTextField()
    {
        return text;
    }
    
    /**
     * Converts the error into a service result
     * 
     * @return a new service result object
     */
    public ServiceResult toServiceResult()
    {
    	ServiceResult res = new ServiceResult();
    	if (statusCode==null)
    		res.setCode(new StatusCode(StatusCodes.Bad_UnexpectedError));
    	else
    		res.setCode(statusCode);
    	res.setSymbolicId(statusCode.toString());
    	res.setLocalizedText(new LocalizedText(getMessage(), ""));
    	res.setAdditionalInfo(Arrays.toString(getStackTrace()));
    	return res;
    }
        
}
