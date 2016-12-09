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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class MonitoringParameters extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.MonitoringParameters);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.MonitoringParameters_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.MonitoringParameters_Encoding_DefaultXml);
	
    protected UnsignedInteger ClientHandle;
    protected Double SamplingInterval;
    protected ExtensionObject Filter;
    protected UnsignedInteger QueueSize;
    protected Boolean DiscardOldest;
    
    public MonitoringParameters() {}
    
    public MonitoringParameters(UnsignedInteger ClientHandle, Double SamplingInterval, ExtensionObject Filter, UnsignedInteger QueueSize, Boolean DiscardOldest)
    {
        this.ClientHandle = ClientHandle;
        this.SamplingInterval = SamplingInterval;
        this.Filter = Filter;
        this.QueueSize = QueueSize;
        this.DiscardOldest = DiscardOldest;
    }
    
    public UnsignedInteger getClientHandle()
    {
        return ClientHandle;
    }
    
    public void setClientHandle(UnsignedInteger ClientHandle)
    {
        this.ClientHandle = ClientHandle;
    }
    
    public Double getSamplingInterval()
    {
        return SamplingInterval;
    }
    
    public void setSamplingInterval(Double SamplingInterval)
    {
        this.SamplingInterval = SamplingInterval;
    }
    
    public ExtensionObject getFilter()
    {
        return Filter;
    }
    
    public void setFilter(ExtensionObject Filter)
    {
        this.Filter = Filter;
    }
    
    public UnsignedInteger getQueueSize()
    {
        return QueueSize;
    }
    
    public void setQueueSize(UnsignedInteger QueueSize)
    {
        this.QueueSize = QueueSize;
    }
    
    public Boolean getDiscardOldest()
    {
        return DiscardOldest;
    }
    
    public void setDiscardOldest(Boolean DiscardOldest)
    {
        this.DiscardOldest = DiscardOldest;
    }
    
    /**
      * Deep clone
      *
      * @return cloned MonitoringParameters
      */
    public MonitoringParameters clone()
    {
        MonitoringParameters result = (MonitoringParameters) super.clone();
        result.ClientHandle = ClientHandle;
        result.SamplingInterval = SamplingInterval;
        result.Filter = Filter;
        result.QueueSize = QueueSize;
        result.DiscardOldest = DiscardOldest;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MonitoringParameters other = (MonitoringParameters) obj;
        if (ClientHandle==null) {
            if (other.ClientHandle != null) return false;
        } else if (!ClientHandle.equals(other.ClientHandle)) return false;
        if (SamplingInterval==null) {
            if (other.SamplingInterval != null) return false;
        } else if (!SamplingInterval.equals(other.SamplingInterval)) return false;
        if (Filter==null) {
            if (other.Filter != null) return false;
        } else if (!Filter.equals(other.Filter)) return false;
        if (QueueSize==null) {
            if (other.QueueSize != null) return false;
        } else if (!QueueSize.equals(other.QueueSize)) return false;
        if (DiscardOldest==null) {
            if (other.DiscardOldest != null) return false;
        } else if (!DiscardOldest.equals(other.DiscardOldest)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ClientHandle == null) ? 0 : ClientHandle.hashCode());
        result = prime * result
                + ((SamplingInterval == null) ? 0 : SamplingInterval.hashCode());
        result = prime * result
                + ((Filter == null) ? 0 : Filter.hashCode());
        result = prime * result
                + ((QueueSize == null) ? 0 : QueueSize.hashCode());
        result = prime * result
                + ((DiscardOldest == null) ? 0 : DiscardOldest.hashCode());
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
		return "MonitoringParameters: "+ObjectUtils.printFieldsDeep(this);
	}

}
