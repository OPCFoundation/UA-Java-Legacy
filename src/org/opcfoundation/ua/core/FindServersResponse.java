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

import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.ResponseHeader;


public class FindServersResponse extends Object implements ServiceResponse {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.FindServersResponse);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.FindServersResponse_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.FindServersResponse_Encoding_DefaultXml);
	
    protected ResponseHeader ResponseHeader;
    protected ApplicationDescription[] Servers;
    
    public FindServersResponse() {}
    
    public FindServersResponse(ResponseHeader ResponseHeader, ApplicationDescription[] Servers)
    {
        this.ResponseHeader = ResponseHeader;
        this.Servers = Servers;
    }
    
    public ResponseHeader getResponseHeader()
    {
        return ResponseHeader;
    }
    
    public void setResponseHeader(ResponseHeader ResponseHeader)
    {
        this.ResponseHeader = ResponseHeader;
    }
    
    public ApplicationDescription[] getServers()
    {
        return Servers;
    }
    
    public void setServers(ApplicationDescription[] Servers)
    {
        this.Servers = Servers;
    }
    
    /**
      * Deep clone
      *
      * @return cloned FindServersResponse
      */
    public FindServersResponse clone()
    {
        FindServersResponse result = new FindServersResponse();
        result.ResponseHeader = ResponseHeader==null ? null : ResponseHeader.clone();
        if (Servers!=null) {
            result.Servers = new ApplicationDescription[Servers.length];
            for (int i=0; i<Servers.length; i++)
                result.Servers[i] = Servers[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FindServersResponse other = (FindServersResponse) obj;
        if (ResponseHeader==null) {
            if (other.ResponseHeader != null) return false;
        } else if (!ResponseHeader.equals(other.ResponseHeader)) return false;
        if (Servers==null) {
            if (other.Servers != null) return false;
        } else if (!Arrays.equals(Servers, other.Servers)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ResponseHeader == null) ? 0 : ResponseHeader.hashCode());
        result = prime * result
                + ((Servers == null) ? 0 : Arrays.hashCode(Servers));
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
