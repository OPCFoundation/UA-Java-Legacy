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
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class MonitoredItemCreateResult extends AbstractStructure {
	
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
        MonitoredItemCreateResult result = (MonitoredItemCreateResult) super.clone();
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
