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
import java.util.Arrays;
import org.opcfoundation.ua.core.RequestHeader;


public class FindServersRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.FindServersRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.FindServersRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.FindServersRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected String EndpointUrl;
    protected String[] LocaleIds;
    protected String[] ServerUris;
    
    public FindServersRequest() {}
    
    public FindServersRequest(RequestHeader RequestHeader, String EndpointUrl, String[] LocaleIds, String[] ServerUris)
    {
        this.RequestHeader = RequestHeader;
        this.EndpointUrl = EndpointUrl;
        this.LocaleIds = LocaleIds;
        this.ServerUris = ServerUris;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public String getEndpointUrl()
    {
        return EndpointUrl;
    }
    
    public void setEndpointUrl(String EndpointUrl)
    {
        this.EndpointUrl = EndpointUrl;
    }
    
    public String[] getLocaleIds()
    {
        return LocaleIds;
    }
    
    public void setLocaleIds(String[] LocaleIds)
    {
        this.LocaleIds = LocaleIds;
    }
    
    public String[] getServerUris()
    {
        return ServerUris;
    }
    
    public void setServerUris(String[] ServerUris)
    {
        this.ServerUris = ServerUris;
    }
    
    /**
      * Deep clone
      *
      * @return cloned FindServersRequest
      */
    public FindServersRequest clone()
    {
        FindServersRequest result = new FindServersRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.EndpointUrl = EndpointUrl;
        result.LocaleIds = LocaleIds==null ? null : LocaleIds.clone();
        result.ServerUris = ServerUris==null ? null : ServerUris.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FindServersRequest other = (FindServersRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (EndpointUrl==null) {
            if (other.EndpointUrl != null) return false;
        } else if (!EndpointUrl.equals(other.EndpointUrl)) return false;
        if (LocaleIds==null) {
            if (other.LocaleIds != null) return false;
        } else if (!Arrays.equals(LocaleIds, other.LocaleIds)) return false;
        if (ServerUris==null) {
            if (other.ServerUris != null) return false;
        } else if (!Arrays.equals(ServerUris, other.ServerUris)) return false;
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
                + ((EndpointUrl == null) ? 0 : EndpointUrl.hashCode());
        result = prime * result
                + ((LocaleIds == null) ? 0 : Arrays.hashCode(LocaleIds));
        result = prime * result
                + ((ServerUris == null) ? 0 : Arrays.hashCode(ServerUris));
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
