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
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class StructureField extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.StructureField);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.StructureField_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.StructureField_Encoding_DefaultXml);
	
    protected String Name;
    protected LocalizedText Description;
    protected NodeId DataType;
    protected Integer ValueRank;
    protected UnsignedInteger[] ArrayDimensions;
    protected UnsignedInteger MaxStringLength;
    protected Boolean IsOptional;
    
    public StructureField() {}
    
    public StructureField(String Name, LocalizedText Description, NodeId DataType, Integer ValueRank, UnsignedInteger[] ArrayDimensions, UnsignedInteger MaxStringLength, Boolean IsOptional)
    {
        this.Name = Name;
        this.Description = Description;
        this.DataType = DataType;
        this.ValueRank = ValueRank;
        this.ArrayDimensions = ArrayDimensions;
        this.MaxStringLength = MaxStringLength;
        this.IsOptional = IsOptional;
    }
    
    public String getName()
    {
        return Name;
    }
    
    public void setName(String Name)
    {
        this.Name = Name;
    }
    
    public LocalizedText getDescription()
    {
        return Description;
    }
    
    public void setDescription(LocalizedText Description)
    {
        this.Description = Description;
    }
    
    public NodeId getDataType()
    {
        return DataType;
    }
    
    public void setDataType(NodeId DataType)
    {
        this.DataType = DataType;
    }
    
    public Integer getValueRank()
    {
        return ValueRank;
    }
    
    public void setValueRank(Integer ValueRank)
    {
        this.ValueRank = ValueRank;
    }
    
    public UnsignedInteger[] getArrayDimensions()
    {
        return ArrayDimensions;
    }
    
    public void setArrayDimensions(UnsignedInteger[] ArrayDimensions)
    {
        this.ArrayDimensions = ArrayDimensions;
    }
    
    public UnsignedInteger getMaxStringLength()
    {
        return MaxStringLength;
    }
    
    public void setMaxStringLength(UnsignedInteger MaxStringLength)
    {
        this.MaxStringLength = MaxStringLength;
    }
    
    public Boolean getIsOptional()
    {
        return IsOptional;
    }
    
    public void setIsOptional(Boolean IsOptional)
    {
        this.IsOptional = IsOptional;
    }
    
    /**
      * Deep clone
      *
      * @return cloned StructureField
      */
    public StructureField clone()
    {
        StructureField result = (StructureField) super.clone();
        result.Name = Name;
        result.Description = Description;
        result.DataType = DataType;
        result.ValueRank = ValueRank;
        result.ArrayDimensions = ArrayDimensions==null ? null : ArrayDimensions.clone();
        result.MaxStringLength = MaxStringLength;
        result.IsOptional = IsOptional;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StructureField other = (StructureField) obj;
        if (Name==null) {
            if (other.Name != null) return false;
        } else if (!Name.equals(other.Name)) return false;
        if (Description==null) {
            if (other.Description != null) return false;
        } else if (!Description.equals(other.Description)) return false;
        if (DataType==null) {
            if (other.DataType != null) return false;
        } else if (!DataType.equals(other.DataType)) return false;
        if (ValueRank==null) {
            if (other.ValueRank != null) return false;
        } else if (!ValueRank.equals(other.ValueRank)) return false;
        if (ArrayDimensions==null) {
            if (other.ArrayDimensions != null) return false;
        } else if (!Arrays.equals(ArrayDimensions, other.ArrayDimensions)) return false;
        if (MaxStringLength==null) {
            if (other.MaxStringLength != null) return false;
        } else if (!MaxStringLength.equals(other.MaxStringLength)) return false;
        if (IsOptional==null) {
            if (other.IsOptional != null) return false;
        } else if (!IsOptional.equals(other.IsOptional)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Name == null) ? 0 : Name.hashCode());
        result = prime * result
                + ((Description == null) ? 0 : Description.hashCode());
        result = prime * result
                + ((DataType == null) ? 0 : DataType.hashCode());
        result = prime * result
                + ((ValueRank == null) ? 0 : ValueRank.hashCode());
        result = prime * result
                + ((ArrayDimensions == null) ? 0 : Arrays.hashCode(ArrayDimensions));
        result = prime * result
                + ((MaxStringLength == null) ? 0 : MaxStringLength.hashCode());
        result = prime * result
                + ((IsOptional == null) ? 0 : IsOptional.hashCode());
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
		return "StructureField: "+ObjectUtils.printFieldsDeep(this);
	}

}
