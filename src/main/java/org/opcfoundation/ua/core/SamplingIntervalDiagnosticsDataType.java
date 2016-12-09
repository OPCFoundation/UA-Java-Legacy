/* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.core;

import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class SamplingIntervalDiagnosticsDataType extends AbstractStructure {
	
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
        SamplingIntervalDiagnosticsDataType result = (SamplingIntervalDiagnosticsDataType) super.clone();
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
