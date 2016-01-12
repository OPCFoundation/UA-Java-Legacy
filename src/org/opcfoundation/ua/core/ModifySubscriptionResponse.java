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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.ResponseHeader;


public class ModifySubscriptionResponse extends Object implements ServiceResponse {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ModifySubscriptionResponse);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ModifySubscriptionResponse_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ModifySubscriptionResponse_Encoding_DefaultXml);
	
    protected ResponseHeader ResponseHeader;
    protected Double RevisedPublishingInterval;
    protected UnsignedInteger RevisedLifetimeCount;
    protected UnsignedInteger RevisedMaxKeepAliveCount;
    
    public ModifySubscriptionResponse() {}
    
    public ModifySubscriptionResponse(ResponseHeader ResponseHeader, Double RevisedPublishingInterval, UnsignedInteger RevisedLifetimeCount, UnsignedInteger RevisedMaxKeepAliveCount)
    {
        this.ResponseHeader = ResponseHeader;
        this.RevisedPublishingInterval = RevisedPublishingInterval;
        this.RevisedLifetimeCount = RevisedLifetimeCount;
        this.RevisedMaxKeepAliveCount = RevisedMaxKeepAliveCount;
    }
    
    public ResponseHeader getResponseHeader()
    {
        return ResponseHeader;
    }
    
    public void setResponseHeader(ResponseHeader ResponseHeader)
    {
        this.ResponseHeader = ResponseHeader;
    }
    
    public Double getRevisedPublishingInterval()
    {
        return RevisedPublishingInterval;
    }
    
    public void setRevisedPublishingInterval(Double RevisedPublishingInterval)
    {
        this.RevisedPublishingInterval = RevisedPublishingInterval;
    }
    
    public UnsignedInteger getRevisedLifetimeCount()
    {
        return RevisedLifetimeCount;
    }
    
    public void setRevisedLifetimeCount(UnsignedInteger RevisedLifetimeCount)
    {
        this.RevisedLifetimeCount = RevisedLifetimeCount;
    }
    
    public UnsignedInteger getRevisedMaxKeepAliveCount()
    {
        return RevisedMaxKeepAliveCount;
    }
    
    public void setRevisedMaxKeepAliveCount(UnsignedInteger RevisedMaxKeepAliveCount)
    {
        this.RevisedMaxKeepAliveCount = RevisedMaxKeepAliveCount;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ModifySubscriptionResponse
      */
    public ModifySubscriptionResponse clone()
    {
        ModifySubscriptionResponse result = new ModifySubscriptionResponse();
        result.ResponseHeader = ResponseHeader==null ? null : ResponseHeader.clone();
        result.RevisedPublishingInterval = RevisedPublishingInterval;
        result.RevisedLifetimeCount = RevisedLifetimeCount;
        result.RevisedMaxKeepAliveCount = RevisedMaxKeepAliveCount;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ModifySubscriptionResponse other = (ModifySubscriptionResponse) obj;
        if (ResponseHeader==null) {
            if (other.ResponseHeader != null) return false;
        } else if (!ResponseHeader.equals(other.ResponseHeader)) return false;
        if (RevisedPublishingInterval==null) {
            if (other.RevisedPublishingInterval != null) return false;
        } else if (!RevisedPublishingInterval.equals(other.RevisedPublishingInterval)) return false;
        if (RevisedLifetimeCount==null) {
            if (other.RevisedLifetimeCount != null) return false;
        } else if (!RevisedLifetimeCount.equals(other.RevisedLifetimeCount)) return false;
        if (RevisedMaxKeepAliveCount==null) {
            if (other.RevisedMaxKeepAliveCount != null) return false;
        } else if (!RevisedMaxKeepAliveCount.equals(other.RevisedMaxKeepAliveCount)) return false;
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
                + ((RevisedPublishingInterval == null) ? 0 : RevisedPublishingInterval.hashCode());
        result = prime * result
                + ((RevisedLifetimeCount == null) ? 0 : RevisedLifetimeCount.hashCode());
        result = prime * result
                + ((RevisedMaxKeepAliveCount == null) ? 0 : RevisedMaxKeepAliveCount.hashCode());
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
