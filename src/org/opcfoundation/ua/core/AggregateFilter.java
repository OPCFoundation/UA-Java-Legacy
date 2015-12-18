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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.AggregateConfiguration;
import org.opcfoundation.ua.core.MonitoringFilter;



public class AggregateFilter extends MonitoringFilter implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.AggregateFilter);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.AggregateFilter_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.AggregateFilter_Encoding_DefaultXml);
	
    protected DateTime StartTime;
    protected NodeId AggregateType;
    protected Double ProcessingInterval;
    protected AggregateConfiguration AggregateConfiguration;
    
    public AggregateFilter() {}
    
    public AggregateFilter(DateTime StartTime, NodeId AggregateType, Double ProcessingInterval, AggregateConfiguration AggregateConfiguration)
    {
        this.StartTime = StartTime;
        this.AggregateType = AggregateType;
        this.ProcessingInterval = ProcessingInterval;
        this.AggregateConfiguration = AggregateConfiguration;
    }
    
    public DateTime getStartTime()
    {
        return StartTime;
    }
    
    public void setStartTime(DateTime StartTime)
    {
        this.StartTime = StartTime;
    }
    
    public NodeId getAggregateType()
    {
        return AggregateType;
    }
    
    public void setAggregateType(NodeId AggregateType)
    {
        this.AggregateType = AggregateType;
    }
    
    public Double getProcessingInterval()
    {
        return ProcessingInterval;
    }
    
    public void setProcessingInterval(Double ProcessingInterval)
    {
        this.ProcessingInterval = ProcessingInterval;
    }
    
    public AggregateConfiguration getAggregateConfiguration()
    {
        return AggregateConfiguration;
    }
    
    public void setAggregateConfiguration(AggregateConfiguration AggregateConfiguration)
    {
        this.AggregateConfiguration = AggregateConfiguration;
    }
    
    /**
      * Deep clone
      *
      * @return cloned AggregateFilter
      */
    public AggregateFilter clone()
    {
        AggregateFilter result = new AggregateFilter();
        result.StartTime = StartTime;
        result.AggregateType = AggregateType;
        result.ProcessingInterval = ProcessingInterval;
        result.AggregateConfiguration = AggregateConfiguration==null ? null : AggregateConfiguration.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AggregateFilter other = (AggregateFilter) obj;
        if (StartTime==null) {
            if (other.StartTime != null) return false;
        } else if (!StartTime.equals(other.StartTime)) return false;
        if (AggregateType==null) {
            if (other.AggregateType != null) return false;
        } else if (!AggregateType.equals(other.AggregateType)) return false;
        if (ProcessingInterval==null) {
            if (other.ProcessingInterval != null) return false;
        } else if (!ProcessingInterval.equals(other.ProcessingInterval)) return false;
        if (AggregateConfiguration==null) {
            if (other.AggregateConfiguration != null) return false;
        } else if (!AggregateConfiguration.equals(other.AggregateConfiguration)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((StartTime == null) ? 0 : StartTime.hashCode());
        result = prime * result
                + ((AggregateType == null) ? 0 : AggregateType.hashCode());
        result = prime * result
                + ((ProcessingInterval == null) ? 0 : ProcessingInterval.hashCode());
        result = prime * result
                + ((AggregateConfiguration == null) ? 0 : AggregateConfiguration.hashCode());
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
		return "AggregateFilter: "+ObjectUtils.printFieldsDeep(this);
	}

}
