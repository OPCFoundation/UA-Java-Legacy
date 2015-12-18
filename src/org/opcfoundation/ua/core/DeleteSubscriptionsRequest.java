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


public class DeleteSubscriptionsRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DeleteSubscriptionsRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DeleteSubscriptionsRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DeleteSubscriptionsRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected UnsignedInteger[] SubscriptionIds;
    
    public DeleteSubscriptionsRequest() {}
    
    public DeleteSubscriptionsRequest(RequestHeader RequestHeader, UnsignedInteger[] SubscriptionIds)
    {
        this.RequestHeader = RequestHeader;
        this.SubscriptionIds = SubscriptionIds;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public UnsignedInteger[] getSubscriptionIds()
    {
        return SubscriptionIds;
    }
    
    public void setSubscriptionIds(UnsignedInteger[] SubscriptionIds)
    {
        this.SubscriptionIds = SubscriptionIds;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DeleteSubscriptionsRequest
      */
    public DeleteSubscriptionsRequest clone()
    {
        DeleteSubscriptionsRequest result = new DeleteSubscriptionsRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.SubscriptionIds = SubscriptionIds==null ? null : SubscriptionIds.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DeleteSubscriptionsRequest other = (DeleteSubscriptionsRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (SubscriptionIds==null) {
            if (other.SubscriptionIds != null) return false;
        } else if (!Arrays.equals(SubscriptionIds, other.SubscriptionIds)) return false;
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
                + ((SubscriptionIds == null) ? 0 : Arrays.hashCode(SubscriptionIds));
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
