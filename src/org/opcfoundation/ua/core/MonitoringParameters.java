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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class MonitoringParameters extends Object implements Structure, Cloneable {
	
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
        MonitoringParameters result = new MonitoringParameters();
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
