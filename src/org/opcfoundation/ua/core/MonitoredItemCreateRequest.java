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
import org.opcfoundation.ua.core.MonitoringMode;
import org.opcfoundation.ua.core.MonitoringParameters;
import org.opcfoundation.ua.core.ReadValueId;



public class MonitoredItemCreateRequest extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.MonitoredItemCreateRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.MonitoredItemCreateRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.MonitoredItemCreateRequest_Encoding_DefaultXml);
	
    protected ReadValueId ItemToMonitor;
    protected MonitoringMode MonitoringMode;
    protected MonitoringParameters RequestedParameters;
    
    public MonitoredItemCreateRequest() {}
    
    public MonitoredItemCreateRequest(ReadValueId ItemToMonitor, MonitoringMode MonitoringMode, MonitoringParameters RequestedParameters)
    {
        this.ItemToMonitor = ItemToMonitor;
        this.MonitoringMode = MonitoringMode;
        this.RequestedParameters = RequestedParameters;
    }
    
    public ReadValueId getItemToMonitor()
    {
        return ItemToMonitor;
    }
    
    public void setItemToMonitor(ReadValueId ItemToMonitor)
    {
        this.ItemToMonitor = ItemToMonitor;
    }
    
    public MonitoringMode getMonitoringMode()
    {
        return MonitoringMode;
    }
    
    public void setMonitoringMode(MonitoringMode MonitoringMode)
    {
        this.MonitoringMode = MonitoringMode;
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
      * @return cloned MonitoredItemCreateRequest
      */
    public MonitoredItemCreateRequest clone()
    {
        MonitoredItemCreateRequest result = new MonitoredItemCreateRequest();
        result.ItemToMonitor = ItemToMonitor==null ? null : ItemToMonitor.clone();
        result.MonitoringMode = MonitoringMode;
        result.RequestedParameters = RequestedParameters==null ? null : RequestedParameters.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MonitoredItemCreateRequest other = (MonitoredItemCreateRequest) obj;
        if (ItemToMonitor==null) {
            if (other.ItemToMonitor != null) return false;
        } else if (!ItemToMonitor.equals(other.ItemToMonitor)) return false;
        if (MonitoringMode==null) {
            if (other.MonitoringMode != null) return false;
        } else if (!MonitoringMode.equals(other.MonitoringMode)) return false;
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
                + ((ItemToMonitor == null) ? 0 : ItemToMonitor.hashCode());
        result = prime * result
                + ((MonitoringMode == null) ? 0 : MonitoringMode.hashCode());
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
		return "MonitoredItemCreateRequest: "+ObjectUtils.printFieldsDeep(this);
	}

}
