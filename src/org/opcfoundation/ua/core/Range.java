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



public class Range extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.Range);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.Range_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.Range_Encoding_DefaultXml);
	
    protected Double Low;
    protected Double High;
    
    public Range() {}
    
    public Range(Double Low, Double High)
    {
        this.Low = Low;
        this.High = High;
    }
    
    public Double getLow()
    {
        return Low;
    }
    
    public void setLow(Double Low)
    {
        this.Low = Low;
    }
    
    public Double getHigh()
    {
        return High;
    }
    
    public void setHigh(Double High)
    {
        this.High = High;
    }
    
    /**
      * Deep clone
      *
      * @return cloned Range
      */
    public Range clone()
    {
        Range result = new Range();
        result.Low = Low;
        result.High = High;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Range other = (Range) obj;
        if (Low==null) {
            if (other.Low != null) return false;
        } else if (!Low.equals(other.Low)) return false;
        if (High==null) {
            if (other.High != null) return false;
        } else if (!High.equals(other.High)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Low == null) ? 0 : Low.hashCode());
        result = prime * result
                + ((High == null) ? 0 : High.hashCode());
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
		return "Range: "+ObjectUtils.printFieldsDeep(this);
	}

}
