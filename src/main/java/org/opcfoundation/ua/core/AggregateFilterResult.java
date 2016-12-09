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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.core.AggregateConfiguration;
import org.opcfoundation.ua.core.MonitoringFilterResult;



public class AggregateFilterResult extends MonitoringFilterResult {
	
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
        AggregateFilterResult result = (AggregateFilterResult) super.clone();
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
