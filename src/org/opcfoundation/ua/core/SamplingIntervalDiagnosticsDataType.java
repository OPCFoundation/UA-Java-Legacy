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



public class SamplingIntervalDiagnosticsDataType extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SamplingIntervalDiagnosticsDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SamplingIntervalDiagnosticsDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SamplingIntervalDiagnosticsDataType_Encoding_DefaultXml);
	
    protected Double SamplingInterval;
    protected UnsignedInteger MonitoredItemCount;
    protected UnsignedInteger MaxMonitoredItemCount;
    protected UnsignedInteger DisabledMonitoredItemCount;
    
    public SamplingIntervalDiagnosticsDataType() {}
    
    public SamplingIntervalDiagnosticsDataType(Double SamplingInterval, UnsignedInteger MonitoredItemCount, UnsignedInteger MaxMonitoredItemCount, UnsignedInteger DisabledMonitoredItemCount)
    {
        this.SamplingInterval = SamplingInterval;
        this.MonitoredItemCount = MonitoredItemCount;
        this.MaxMonitoredItemCount = MaxMonitoredItemCount;
        this.DisabledMonitoredItemCount = DisabledMonitoredItemCount;
    }
    
    public Double getSamplingInterval()
    {
        return SamplingInterval;
    }
    
    public void setSamplingInterval(Double SamplingInterval)
    {
        this.SamplingInterval = SamplingInterval;
    }
    
    public UnsignedInteger getMonitoredItemCount()
    {
        return MonitoredItemCount;
    }
    
    public void setMonitoredItemCount(UnsignedInteger MonitoredItemCount)
    {
        this.MonitoredItemCount = MonitoredItemCount;
    }
    
    public UnsignedInteger getMaxMonitoredItemCount()
    {
        return MaxMonitoredItemCount;
    }
    
    public void setMaxMonitoredItemCount(UnsignedInteger MaxMonitoredItemCount)
    {
        this.MaxMonitoredItemCount = MaxMonitoredItemCount;
    }
    
    public UnsignedInteger getDisabledMonitoredItemCount()
    {
        return DisabledMonitoredItemCount;
    }
    
    public void setDisabledMonitoredItemCount(UnsignedInteger DisabledMonitoredItemCount)
    {
        this.DisabledMonitoredItemCount = DisabledMonitoredItemCount;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SamplingIntervalDiagnosticsDataType
      */
    public SamplingIntervalDiagnosticsDataType clone()
    {
        SamplingIntervalDiagnosticsDataType result = new SamplingIntervalDiagnosticsDataType();
        result.SamplingInterval = SamplingInterval;
        result.MonitoredItemCount = MonitoredItemCount;
        result.MaxMonitoredItemCount = MaxMonitoredItemCount;
        result.DisabledMonitoredItemCount = DisabledMonitoredItemCount;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SamplingIntervalDiagnosticsDataType other = (SamplingIntervalDiagnosticsDataType) obj;
        if (SamplingInterval==null) {
            if (other.SamplingInterval != null) return false;
        } else if (!SamplingInterval.equals(other.SamplingInterval)) return false;
        if (MonitoredItemCount==null) {
            if (other.MonitoredItemCount != null) return false;
        } else if (!MonitoredItemCount.equals(other.MonitoredItemCount)) return false;
        if (MaxMonitoredItemCount==null) {
            if (other.MaxMonitoredItemCount != null) return false;
        } else if (!MaxMonitoredItemCount.equals(other.MaxMonitoredItemCount)) return false;
        if (DisabledMonitoredItemCount==null) {
            if (other.DisabledMonitoredItemCount != null) return false;
        } else if (!DisabledMonitoredItemCount.equals(other.DisabledMonitoredItemCount)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((SamplingInterval == null) ? 0 : SamplingInterval.hashCode());
        result = prime * result
                + ((MonitoredItemCount == null) ? 0 : MonitoredItemCount.hashCode());
        result = prime * result
                + ((MaxMonitoredItemCount == null) ? 0 : MaxMonitoredItemCount.hashCode());
        result = prime * result
                + ((DisabledMonitoredItemCount == null) ? 0 : DisabledMonitoredItemCount.hashCode());
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
		return "SamplingIntervalDiagnosticsDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
