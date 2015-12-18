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
import org.opcfoundation.ua.core.MonitoredItemModifyRequest;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.TimestampsToReturn;


public class ModifyMonitoredItemsRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ModifyMonitoredItemsRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ModifyMonitoredItemsRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ModifyMonitoredItemsRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected UnsignedInteger SubscriptionId;
    protected TimestampsToReturn TimestampsToReturn;
    protected MonitoredItemModifyRequest[] ItemsToModify;
    
    public ModifyMonitoredItemsRequest() {}
    
    public ModifyMonitoredItemsRequest(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, TimestampsToReturn TimestampsToReturn, MonitoredItemModifyRequest[] ItemsToModify)
    {
        this.RequestHeader = RequestHeader;
        this.SubscriptionId = SubscriptionId;
        this.TimestampsToReturn = TimestampsToReturn;
        this.ItemsToModify = ItemsToModify;
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
    
    public TimestampsToReturn getTimestampsToReturn()
    {
        return TimestampsToReturn;
    }
    
    public void setTimestampsToReturn(TimestampsToReturn TimestampsToReturn)
    {
        this.TimestampsToReturn = TimestampsToReturn;
    }
    
    public MonitoredItemModifyRequest[] getItemsToModify()
    {
        return ItemsToModify;
    }
    
    public void setItemsToModify(MonitoredItemModifyRequest[] ItemsToModify)
    {
        this.ItemsToModify = ItemsToModify;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ModifyMonitoredItemsRequest
      */
    public ModifyMonitoredItemsRequest clone()
    {
        ModifyMonitoredItemsRequest result = new ModifyMonitoredItemsRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.SubscriptionId = SubscriptionId;
        result.TimestampsToReturn = TimestampsToReturn;
        if (ItemsToModify!=null) {
            result.ItemsToModify = new MonitoredItemModifyRequest[ItemsToModify.length];
            for (int i=0; i<ItemsToModify.length; i++)
                result.ItemsToModify[i] = ItemsToModify[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ModifyMonitoredItemsRequest other = (ModifyMonitoredItemsRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (SubscriptionId==null) {
            if (other.SubscriptionId != null) return false;
        } else if (!SubscriptionId.equals(other.SubscriptionId)) return false;
        if (TimestampsToReturn==null) {
            if (other.TimestampsToReturn != null) return false;
        } else if (!TimestampsToReturn.equals(other.TimestampsToReturn)) return false;
        if (ItemsToModify==null) {
            if (other.ItemsToModify != null) return false;
        } else if (!Arrays.equals(ItemsToModify, other.ItemsToModify)) return false;
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
                + ((TimestampsToReturn == null) ? 0 : TimestampsToReturn.hashCode());
        result = prime * result
                + ((ItemsToModify == null) ? 0 : Arrays.hashCode(ItemsToModify));
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
