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
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.RequestHeader;


public class ModifySubscriptionRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ModifySubscriptionRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ModifySubscriptionRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ModifySubscriptionRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected UnsignedInteger SubscriptionId;
    protected Double RequestedPublishingInterval;
    protected UnsignedInteger RequestedLifetimeCount;
    protected UnsignedInteger RequestedMaxKeepAliveCount;
    protected UnsignedInteger MaxNotificationsPerPublish;
    protected UnsignedByte Priority;
    
    public ModifySubscriptionRequest() {}
    
    public ModifySubscriptionRequest(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, Double RequestedPublishingInterval, UnsignedInteger RequestedLifetimeCount, UnsignedInteger RequestedMaxKeepAliveCount, UnsignedInteger MaxNotificationsPerPublish, UnsignedByte Priority)
    {
        this.RequestHeader = RequestHeader;
        this.SubscriptionId = SubscriptionId;
        this.RequestedPublishingInterval = RequestedPublishingInterval;
        this.RequestedLifetimeCount = RequestedLifetimeCount;
        this.RequestedMaxKeepAliveCount = RequestedMaxKeepAliveCount;
        this.MaxNotificationsPerPublish = MaxNotificationsPerPublish;
        this.Priority = Priority;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public UnsignedInteger getSubscriptionId()
    {
        return SubscriptionId;
    }
    
    public void setSubscriptionId(UnsignedInteger SubscriptionId)
    {
        this.SubscriptionId = SubscriptionId;
    }
    
    public Double getRequestedPublishingInterval()
    {
        return RequestedPublishingInterval;
    }
    
    public void setRequestedPublishingInterval(Double RequestedPublishingInterval)
    {
        this.RequestedPublishingInterval = RequestedPublishingInterval;
    }
    
    public UnsignedInteger getRequestedLifetimeCount()
    {
        return RequestedLifetimeCount;
    }
    
    public void setRequestedLifetimeCount(UnsignedInteger RequestedLifetimeCount)
    {
        this.RequestedLifetimeCount = RequestedLifetimeCount;
    }
    
    public UnsignedInteger getRequestedMaxKeepAliveCount()
    {
        return RequestedMaxKeepAliveCount;
    }
    
    public void setRequestedMaxKeepAliveCount(UnsignedInteger RequestedMaxKeepAliveCount)
    {
        this.RequestedMaxKeepAliveCount = RequestedMaxKeepAliveCount;
    }
    
    public UnsignedInteger getMaxNotificationsPerPublish()
    {
        return MaxNotificationsPerPublish;
    }
    
    public void setMaxNotificationsPerPublish(UnsignedInteger MaxNotificationsPerPublish)
    {
        this.MaxNotificationsPerPublish = MaxNotificationsPerPublish;
    }
    
    public UnsignedByte getPriority()
    {
        return Priority;
    }
    
    public void setPriority(UnsignedByte Priority)
    {
        this.Priority = Priority;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ModifySubscriptionRequest
      */
    public ModifySubscriptionRequest clone()
    {
        ModifySubscriptionRequest result = new ModifySubscriptionRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.SubscriptionId = SubscriptionId;
        result.RequestedPublishingInterval = RequestedPublishingInterval;
        result.RequestedLifetimeCount = RequestedLifetimeCount;
        result.RequestedMaxKeepAliveCount = RequestedMaxKeepAliveCount;
        result.MaxNotificationsPerPublish = MaxNotificationsPerPublish;
        result.Priority = Priority;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ModifySubscriptionRequest other = (ModifySubscriptionRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (SubscriptionId==null) {
            if (other.SubscriptionId != null) return false;
        } else if (!SubscriptionId.equals(other.SubscriptionId)) return false;
        if (RequestedPublishingInterval==null) {
            if (other.RequestedPublishingInterval != null) return false;
        } else if (!RequestedPublishingInterval.equals(other.RequestedPublishingInterval)) return false;
        if (RequestedLifetimeCount==null) {
            if (other.RequestedLifetimeCount != null) return false;
        } else if (!RequestedLifetimeCount.equals(other.RequestedLifetimeCount)) return false;
        if (RequestedMaxKeepAliveCount==null) {
            if (other.RequestedMaxKeepAliveCount != null) return false;
        } else if (!RequestedMaxKeepAliveCount.equals(other.RequestedMaxKeepAliveCount)) return false;
        if (MaxNotificationsPerPublish==null) {
            if (other.MaxNotificationsPerPublish != null) return false;
        } else if (!MaxNotificationsPerPublish.equals(other.MaxNotificationsPerPublish)) return false;
        if (Priority==null) {
            if (other.Priority != null) return false;
        } else if (!Priority.equals(other.Priority)) return false;
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
                + ((SubscriptionId == null) ? 0 : SubscriptionId.hashCode());
        result = prime * result
                + ((RequestedPublishingInterval == null) ? 0 : RequestedPublishingInterval.hashCode());
        result = prime * result
                + ((RequestedLifetimeCount == null) ? 0 : RequestedLifetimeCount.hashCode());
        result = prime * result
                + ((RequestedMaxKeepAliveCount == null) ? 0 : RequestedMaxKeepAliveCount.hashCode());
        result = prime * result
                + ((MaxNotificationsPerPublish == null) ? 0 : MaxNotificationsPerPublish.hashCode());
        result = prime * result
                + ((Priority == null) ? 0 : Priority.hashCode());
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
