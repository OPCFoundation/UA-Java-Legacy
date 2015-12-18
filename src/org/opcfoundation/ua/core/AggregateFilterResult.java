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
import org.opcfoundation.ua.core.AggregateConfiguration;
import org.opcfoundation.ua.core.MonitoringFilterResult;



public class AggregateFilterResult extends MonitoringFilterResult implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.AggregateFilterResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.AggregateFilterResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.AggregateFilterResult_Encoding_DefaultXml);
	
    protected DateTime RevisedStartTime;
    protected Double RevisedProcessingInterval;
    protected AggregateConfiguration RevisedAggregateConfiguration;
    
    public AggregateFilterResult() {}
    
    public AggregateFilterResult(DateTime RevisedStartTime, Double RevisedProcessingInterval, AggregateConfiguration RevisedAggregateConfiguration)
    {
        this.RevisedStartTime = RevisedStartTime;
        this.RevisedProcessingInterval = RevisedProcessingInterval;
        this.RevisedAggregateConfiguration = RevisedAggregateConfiguration;
    }
    
    public DateTime getRevisedStartTime()
    {
        return RevisedStartTime;
    }
    
    public void setRevisedStartTime(DateTime RevisedStartTime)
    {
        this.RevisedStartTime = RevisedStartTime;
    }
    
    public Double getRevisedProcessingInterval()
    {
        return RevisedProcessingInterval;
    }
    
    public void setRevisedProcessingInterval(Double RevisedProcessingInterval)
    {
        this.RevisedProcessingInterval = RevisedProcessingInterval;
    }
    
    public AggregateConfiguration getRevisedAggregateConfiguration()
    {
        return RevisedAggregateConfiguration;
    }
    
    public void setRevisedAggregateConfiguration(AggregateConfiguration RevisedAggregateConfiguration)
    {
        this.RevisedAggregateConfiguration = RevisedAggregateConfiguration;
    }
    
    /**
      * Deep clone
      *
      * @return cloned AggregateFilterResult
      */
    public AggregateFilterResult clone()
    {
        AggregateFilterResult result = new AggregateFilterResult();
        result.RevisedStartTime = RevisedStartTime;
        result.RevisedProcessingInterval = RevisedProcessingInterval;
        result.RevisedAggregateConfiguration = RevisedAggregateConfiguration==null ? null : RevisedAggregateConfiguration.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AggregateFilterResult other = (AggregateFilterResult) obj;
        if (RevisedStartTime==null) {
            if (other.RevisedStartTime != null) return false;
        } else if (!RevisedStartTime.equals(other.RevisedStartTime)) return false;
        if (RevisedProcessingInterval==null) {
            if (other.RevisedProcessingInterval != null) return false;
        } else if (!RevisedProcessingInterval.equals(other.RevisedProcessingInterval)) return false;
        if (RevisedAggregateConfiguration==null) {
            if (other.RevisedAggregateConfiguration != null) return false;
        } else if (!RevisedAggregateConfiguration.equals(other.RevisedAggregateConfiguration)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((RevisedStartTime == null) ? 0 : RevisedStartTime.hashCode());
        result = prime * result
                + ((RevisedProcessingInterval == null) ? 0 : RevisedProcessingInterval.hashCode());
        result = prime * result
                + ((RevisedAggregateConfiguration == null) ? 0 : RevisedAggregateConfiguration.hashCode());
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
		return "AggregateFilterResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
