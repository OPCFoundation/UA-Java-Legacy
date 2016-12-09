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
import org.opcfoundation.ua.core.DataChangeTrigger;
import org.opcfoundation.ua.core.MonitoringFilter;



public class DataChangeFilter extends MonitoringFilter {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DataChangeFilter);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DataChangeFilter_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DataChangeFilter_Encoding_DefaultXml);
	
    protected DataChangeTrigger Trigger;
    protected UnsignedInteger DeadbandType;
    protected Double DeadbandValue;
    
    public DataChangeFilter() {}
    
    public DataChangeFilter(DataChangeTrigger Trigger, UnsignedInteger DeadbandType, Double DeadbandValue)
    {
        this.Trigger = Trigger;
        this.DeadbandType = DeadbandType;
        this.DeadbandValue = DeadbandValue;
    }
    
    public DataChangeTrigger getTrigger()
    {
        return Trigger;
    }
    
    public void setTrigger(DataChangeTrigger Trigger)
    {
        this.Trigger = Trigger;
    }
    
    public UnsignedInteger getDeadbandType()
    {
        return DeadbandType;
    }
    
    public void setDeadbandType(UnsignedInteger DeadbandType)
    {
        this.DeadbandType = DeadbandType;
    }
    
    public Double getDeadbandValue()
    {
        return DeadbandValue;
    }
    
    public void setDeadbandValue(Double DeadbandValue)
    {
        this.DeadbandValue = DeadbandValue;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DataChangeFilter
      */
    public DataChangeFilter clone()
    {
        DataChangeFilter result = (DataChangeFilter) super.clone();
        result.Trigger = Trigger;
        result.DeadbandType = DeadbandType;
        result.DeadbandValue = DeadbandValue;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DataChangeFilter other = (DataChangeFilter) obj;
        if (Trigger==null) {
            if (other.Trigger != null) return false;
        } else if (!Trigger.equals(other.Trigger)) return false;
        if (DeadbandType==null) {
            if (other.DeadbandType != null) return false;
        } else if (!DeadbandType.equals(other.DeadbandType)) return false;
        if (DeadbandValue==null) {
            if (other.DeadbandValue != null) return false;
        } else if (!DeadbandValue.equals(other.DeadbandValue)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Trigger == null) ? 0 : Trigger.hashCode());
        result = prime * result
                + ((DeadbandType == null) ? 0 : DeadbandType.hashCode());
        result = prime * result
                + ((DeadbandValue == null) ? 0 : DeadbandValue.hashCode());
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
		return "DataChangeFilter: "+ObjectUtils.printFieldsDeep(this);
	}

}
