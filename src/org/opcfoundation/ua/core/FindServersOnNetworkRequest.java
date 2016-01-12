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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.RequestHeader;


public class FindServersOnNetworkRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.FindServersOnNetworkRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.FindServersOnNetworkRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.FindServersOnNetworkRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected UnsignedInteger StartingRecordId;
    protected UnsignedInteger MaxRecordsToReturn;
    protected String[] ServerCapabilityFilter;
    
    public FindServersOnNetworkRequest() {}
    
    public FindServersOnNetworkRequest(RequestHeader RequestHeader, UnsignedInteger StartingRecordId, UnsignedInteger MaxRecordsToReturn, String[] ServerCapabilityFilter)
    {
        this.RequestHeader = RequestHeader;
        this.StartingRecordId = StartingRecordId;
        this.MaxRecordsToReturn = MaxRecordsToReturn;
        this.ServerCapabilityFilter = ServerCapabilityFilter;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public UnsignedInteger getStartingRecordId()
    {
        return StartingRecordId;
    }
    
    public void setStartingRecordId(UnsignedInteger StartingRecordId)
    {
        this.StartingRecordId = StartingRecordId;
    }
    
    public UnsignedInteger getMaxRecordsToReturn()
    {
        return MaxRecordsToReturn;
    }
    
    public void setMaxRecordsToReturn(UnsignedInteger MaxRecordsToReturn)
    {
        this.MaxRecordsToReturn = MaxRecordsToReturn;
    }
    
    public String[] getServerCapabilityFilter()
    {
        return ServerCapabilityFilter;
    }
    
    public void setServerCapabilityFilter(String[] ServerCapabilityFilter)
    {
        this.ServerCapabilityFilter = ServerCapabilityFilter;
    }
    
    /**
      * Deep clone
      *
      * @return cloned FindServersOnNetworkRequest
      */
    public FindServersOnNetworkRequest clone()
    {
        FindServersOnNetworkRequest result = new FindServersOnNetworkRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.StartingRecordId = StartingRecordId;
        result.MaxRecordsToReturn = MaxRecordsToReturn;
        result.ServerCapabilityFilter = ServerCapabilityFilter==null ? null : ServerCapabilityFilter.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FindServersOnNetworkRequest other = (FindServersOnNetworkRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (StartingRecordId==null) {
            if (other.StartingRecordId != null) return false;
        } else if (!StartingRecordId.equals(other.StartingRecordId)) return false;
        if (MaxRecordsToReturn==null) {
            if (other.MaxRecordsToReturn != null) return false;
        } else if (!MaxRecordsToReturn.equals(other.MaxRecordsToReturn)) return false;
        if (ServerCapabilityFilter==null) {
            if (other.ServerCapabilityFilter != null) return false;
        } else if (!Arrays.equals(ServerCapabilityFilter, other.ServerCapabilityFilter)) return false;
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
                + ((StartingRecordId == null) ? 0 : StartingRecordId.hashCode());
        result = prime * result
                + ((MaxRecordsToReturn == null) ? 0 : MaxRecordsToReturn.hashCode());
        result = prime * result
                + ((ServerCapabilityFilter == null) ? 0 : Arrays.hashCode(ServerCapabilityFilter));
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
