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



public class TimeZoneDataType extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.TimeZoneDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.TimeZoneDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.TimeZoneDataType_Encoding_DefaultXml);
	
    protected Short Offset;
    protected Boolean DaylightSavingInOffset;
    
    public TimeZoneDataType() {}
    
    public TimeZoneDataType(Short Offset, Boolean DaylightSavingInOffset)
    {
        this.Offset = Offset;
        this.DaylightSavingInOffset = DaylightSavingInOffset;
    }
    
    public Short getOffset()
    {
        return Offset;
    }
    
    public void setOffset(Short Offset)
    {
        this.Offset = Offset;
    }
    
    public Boolean getDaylightSavingInOffset()
    {
        return DaylightSavingInOffset;
    }
    
    public void setDaylightSavingInOffset(Boolean DaylightSavingInOffset)
    {
        this.DaylightSavingInOffset = DaylightSavingInOffset;
    }
    
    /**
      * Deep clone
      *
      * @return cloned TimeZoneDataType
      */
    public TimeZoneDataType clone()
    {
        TimeZoneDataType result = new TimeZoneDataType();
        result.Offset = Offset;
        result.DaylightSavingInOffset = DaylightSavingInOffset;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TimeZoneDataType other = (TimeZoneDataType) obj;
        if (Offset==null) {
            if (other.Offset != null) return false;
        } else if (!Offset.equals(other.Offset)) return false;
        if (DaylightSavingInOffset==null) {
            if (other.DaylightSavingInOffset != null) return false;
        } else if (!DaylightSavingInOffset.equals(other.DaylightSavingInOffset)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Offset == null) ? 0 : Offset.hashCode());
        result = prime * result
                + ((DaylightSavingInOffset == null) ? 0 : DaylightSavingInOffset.hashCode());
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
		return "TimeZoneDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
