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
import org.opcfoundation.ua.core.ArrayTestType;
import org.opcfoundation.ua.core.ScalarTestType;



public class CompositeTestType extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.CompositeTestType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.CompositeTestType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.CompositeTestType_Encoding_DefaultXml);
	
    protected ScalarTestType Field1;
    protected ArrayTestType Field2;
    
    public CompositeTestType() {}
    
    public CompositeTestType(ScalarTestType Field1, ArrayTestType Field2)
    {
        this.Field1 = Field1;
        this.Field2 = Field2;
    }
    
    public ScalarTestType getField1()
    {
        return Field1;
    }
    
    public void setField1(ScalarTestType Field1)
    {
        this.Field1 = Field1;
    }
    
    public ArrayTestType getField2()
    {
        return Field2;
    }
    
    public void setField2(ArrayTestType Field2)
    {
        this.Field2 = Field2;
    }
    
    /**
      * Deep clone
      *
      * @return cloned CompositeTestType
      */
    public CompositeTestType clone()
    {
        CompositeTestType result = new CompositeTestType();
        result.Field1 = Field1==null ? null : Field1.clone();
        result.Field2 = Field2==null ? null : Field2.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CompositeTestType other = (CompositeTestType) obj;
        if (Field1==null) {
            if (other.Field1 != null) return false;
        } else if (!Field1.equals(other.Field1)) return false;
        if (Field2==null) {
            if (other.Field2 != null) return false;
        } else if (!Field2.equals(other.Field2)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Field1 == null) ? 0 : Field1.hashCode());
        result = prime * result
                + ((Field2 == null) ? 0 : Field2.hashCode());
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
		return "CompositeTestType: "+ObjectUtils.printFieldsDeep(this);
	}

}
