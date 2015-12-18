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



public class ServiceCounterDataType extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ServiceCounterDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ServiceCounterDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ServiceCounterDataType_Encoding_DefaultXml);
	
    protected UnsignedInteger TotalCount;
    protected UnsignedInteger ErrorCount;
    
    public ServiceCounterDataType() {}
    
    public ServiceCounterDataType(UnsignedInteger TotalCount, UnsignedInteger ErrorCount)
    {
        this.TotalCount = TotalCount;
        this.ErrorCount = ErrorCount;
    }
    
    public UnsignedInteger getTotalCount()
    {
        return TotalCount;
    }
    
    public void setTotalCount(UnsignedInteger TotalCount)
    {
        this.TotalCount = TotalCount;
    }
    
    public UnsignedInteger getErrorCount()
    {
        return ErrorCount;
    }
    
    public void setErrorCount(UnsignedInteger ErrorCount)
    {
        this.ErrorCount = ErrorCount;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ServiceCounterDataType
      */
    public ServiceCounterDataType clone()
    {
        ServiceCounterDataType result = new ServiceCounterDataType();
        result.TotalCount = TotalCount;
        result.ErrorCount = ErrorCount;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ServiceCounterDataType other = (ServiceCounterDataType) obj;
        if (TotalCount==null) {
            if (other.TotalCount != null) return false;
        } else if (!TotalCount.equals(other.TotalCount)) return false;
        if (ErrorCount==null) {
            if (other.ErrorCount != null) return false;
        } else if (!ErrorCount.equals(other.ErrorCount)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((TotalCount == null) ? 0 : TotalCount.hashCode());
        result = prime * result
                + ((ErrorCount == null) ? 0 : ErrorCount.hashCode());
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
		return "ServiceCounterDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
