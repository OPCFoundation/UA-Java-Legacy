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



public class MonitoredItemModifyResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.MonitoredItemModifyResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.MonitoredItemModifyResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.MonitoredItemModifyResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected Double RevisedSamplingInterval;
    protected UnsignedInteger RevisedQueueSize;
    protected ExtensionObject FilterResult;
    
    public MonitoredItemModifyResult() {}
    
    public MonitoredItemModifyResult(StatusCode StatusCode, Double RevisedSamplingInterval, UnsignedInteger RevisedQueueSize, ExtensionObject FilterResult)
    {
        this.StatusCode = StatusCode;
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
      * @return cloned MonitoredItemModifyResult
      */
    public MonitoredItemModifyResult clone()
    {
        MonitoredItemModifyResult result = new MonitoredItemModifyResult();
        result.StatusCode = StatusCode;
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
        MonitoredItemModifyResult other = (MonitoredItemModifyResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
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
		return "MonitoredItemModifyResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
