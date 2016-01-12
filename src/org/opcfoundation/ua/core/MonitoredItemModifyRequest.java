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

import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.MonitoringParameters;



public class MonitoredItemModifyRequest extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.MonitoredItemModifyRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.MonitoredItemModifyRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.MonitoredItemModifyRequest_Encoding_DefaultXml);
	
    protected UnsignedInteger MonitoredItemId;
    protected MonitoringParameters RequestedParameters;
    
    public MonitoredItemModifyRequest() {}
    
    public MonitoredItemModifyRequest(UnsignedInteger MonitoredItemId, MonitoringParameters RequestedParameters)
    {
        this.MonitoredItemId = MonitoredItemId;
        this.RequestedParameters = RequestedParameters;
    }
    
    public UnsignedInteger getMonitoredItemId()
    {
        return MonitoredItemId;
    }
    
    public void setMonitoredItemId(UnsignedInteger MonitoredItemId)
    {
        this.MonitoredItemId = MonitoredItemId;
    }
    
    public MonitoringParameters getRequestedParameters()
    {
        return RequestedParameters;
    }
    
    public void setRequestedParameters(MonitoringParameters RequestedParameters)
    {
        this.RequestedParameters = RequestedParameters;
    }
    
    /**
      * Deep clone
      *
      * @return cloned MonitoredItemModifyRequest
      */
    public MonitoredItemModifyRequest clone()
    {
        MonitoredItemModifyRequest result = new MonitoredItemModifyRequest();
        result.MonitoredItemId = MonitoredItemId;
        result.RequestedParameters = RequestedParameters==null ? null : RequestedParameters.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MonitoredItemModifyRequest other = (MonitoredItemModifyRequest) obj;
        if (MonitoredItemId==null) {
            if (other.MonitoredItemId != null) return false;
        } else if (!MonitoredItemId.equals(other.MonitoredItemId)) return false;
        if (RequestedParameters==null) {
            if (other.RequestedParameters != null) return false;
        } else if (!RequestedParameters.equals(other.RequestedParameters)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((MonitoredItemId == null) ? 0 : MonitoredItemId.hashCode());
        result = prime * result
                + ((RequestedParameters == null) ? 0 : RequestedParameters.hashCode());
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
		return "MonitoredItemModifyRequest: "+ObjectUtils.printFieldsDeep(this);
	}

}
