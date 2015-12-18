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
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class MonitoredItemCreateResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.MonitoredItemCreateResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.MonitoredItemCreateResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.MonitoredItemCreateResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected UnsignedInteger MonitoredItemId;
    protected Double RevisedSamplingInterval;
    protected UnsignedInteger RevisedQueueSize;
    protected ExtensionObject FilterResult;
    
    public MonitoredItemCreateResult() {}
    
    public MonitoredItemCreateResult(StatusCode StatusCode, UnsignedInteger MonitoredItemId, Double RevisedSamplingInterval, UnsignedInteger RevisedQueueSize, ExtensionObject FilterResult)
    {
        this.StatusCode = StatusCode;
        this.MonitoredItemId = MonitoredItemId;
        this.RevisedSamplingInterval = RevisedSamplingInterval;
        this.RevisedQueueSize = RevisedQueueSize;
        this.FilterResult = FilterResult;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public UnsignedInteger getMonitoredItemId()
    {
        return MonitoredItemId;
    }
    
    public void setMonitoredItemId(UnsignedInteger MonitoredItemId)
    {
        this.MonitoredItemId = MonitoredItemId;
    }
    
    public Double getRevisedSamplingInterval()
    {
        return RevisedSamplingInterval;
    }
    
    public void setRevisedSamplingInterval(Double RevisedSamplingInterval)
    {
        this.RevisedSamplingInterval = RevisedSamplingInterval;
    }
    
    public UnsignedInteger getRevisedQueueSize()
    {
        return RevisedQueueSize;
    }
    
    public void setRevisedQueueSize(UnsignedInteger RevisedQueueSize)
    {
        this.RevisedQueueSize = RevisedQueueSize;
    }
    
    public ExtensionObject getFilterResult()
    {
        return FilterResult;
    }
    
    public void setFilterResult(ExtensionObject FilterResult)
    {
        this.FilterResult = FilterResult;
    }
    
    /**
      * Deep clone
      *
      * @return cloned MonitoredItemCreateResult
      */
    public MonitoredItemCreateResult clone()
    {
        MonitoredItemCreateResult result = new MonitoredItemCreateResult();
        result.StatusCode = StatusCode;
        result.MonitoredItemId = MonitoredItemId;
        result.RevisedSamplingInterval = RevisedSamplingInterval;
        result.RevisedQueueSize = RevisedQueueSize;
        result.FilterResult = FilterResult;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MonitoredItemCreateResult other = (MonitoredItemCreateResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (MonitoredItemId==null) {
            if (other.MonitoredItemId != null) return false;
        } else if (!MonitoredItemId.equals(other.MonitoredItemId)) return false;
        if (RevisedSamplingInterval==null) {
            if (other.RevisedSamplingInterval != null) return false;
        } else if (!RevisedSamplingInterval.equals(other.RevisedSamplingInterval)) return false;
        if (RevisedQueueSize==null) {
            if (other.RevisedQueueSize != null) return false;
        } else if (!RevisedQueueSize.equals(other.RevisedQueueSize)) return false;
        if (FilterResult==null) {
            if (other.FilterResult != null) return false;
        } else if (!FilterResult.equals(other.FilterResult)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((StatusCode == null) ? 0 : StatusCode.hashCode());
        result = prime * result
                + ((MonitoredItemId == null) ? 0 : MonitoredItemId.hashCode());
        result = prime * result
                + ((RevisedSamplingInterval == null) ? 0 : RevisedSamplingInterval.hashCode());
        result = prime * result
                + ((RevisedQueueSize == null) ? 0 : RevisedQueueSize.hashCode());
        result = prime * result
                + ((FilterResult == null) ? 0 : FilterResult.hashCode());
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
		return "MonitoredItemCreateResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
