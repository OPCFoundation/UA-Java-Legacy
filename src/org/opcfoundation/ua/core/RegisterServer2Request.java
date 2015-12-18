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

package org.opcfoundation.ua.core;

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.core.RegisteredServer2;
import org.opcfoundation.ua.core.RequestHeader;


public class RegisterServer2Request extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.RegisterServer2Request);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.RegisterServer2Request_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.RegisterServer2Request_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected RegisteredServer2 Server;
    
    public RegisterServer2Request() {}
    
    public RegisterServer2Request(RequestHeader RequestHeader, RegisteredServer2 Server)
    {
        this.RequestHeader = RequestHeader;
        this.Server = Server;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public RegisteredServer2 getServer()
    {
        return Server;
    }
    
    public void setServer(RegisteredServer2 Server)
    {
        this.Server = Server;
    }
    
    /**
      * Deep clone
      *
      * @return cloned RegisterServer2Request
      */
    public RegisterServer2Request clone()
    {
        RegisterServer2Request result = new RegisterServer2Request();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.Server = Server==null ? null : Server.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RegisterServer2Request other = (RegisterServer2Request) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (Server==null) {
            if (other.Server != null) return false;
        } else if (!Server.equals(other.Server)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((RequestHeader == null) ? 0 : RequestHeader.hashCode());
        result = prime * result
                + ((Server == null) ? 0 : Server.hashCode());
        return result;
    }
    
 

	public ExpandedNodeId getTypeId() {
		return ID;
	}

	public ExpandedNodeId getXmlEncodeId() {
		return XML;
	}
	
	public ExpandedNodeId getBinaryEncodeId() {
		return BINARY;
	}
	
	public String toString() {
		return ObjectUtils.printFieldsDeep(this);
	}
	
}
