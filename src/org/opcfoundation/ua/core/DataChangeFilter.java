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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.DataChangeTrigger;
import org.opcfoundation.ua.core.MonitoringFilter;



public class DataChangeFilter extends MonitoringFilter implements Structure, Cloneable {
	
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
        DataChangeFilter result = new DataChangeFilter();
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
