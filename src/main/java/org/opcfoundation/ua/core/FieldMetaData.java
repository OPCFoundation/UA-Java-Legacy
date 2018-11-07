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
import org.opcfoundation.ua.common.NamespaceTable;

import java.util.Arrays;
import java.util.UUID;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.core.KeyValuePair;
import org.opcfoundation.ua.utils.AbstractStructure;



public class FieldMetaData extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.FieldMetaData.getValue());
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.FieldMetaData_Encoding_DefaultBinary.getValue());
	public static final ExpandedNodeId XML = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.FieldMetaData_Encoding_DefaultXml.getValue());
	
    protected String Name;
    protected LocalizedText Description;
    protected UnsignedShort FieldFlags;
    protected UnsignedByte BuiltInType;
    protected NodeId DataType;
    protected Integer ValueRank;
    protected UnsignedInteger[] ArrayDimensions;
    protected UnsignedInteger MaxStringLength;
    protected UUID DataSetFieldId;
    protected KeyValuePair[] Properties;
    
    public FieldMetaData() {}
    
    public FieldMetaData(String Name, LocalizedText Description, UnsignedShort FieldFlags, UnsignedByte BuiltInType, NodeId DataType, Integer ValueRank, UnsignedInteger[] ArrayDimensions, UnsignedInteger MaxStringLength, UUID DataSetFieldId, KeyValuePair[] Properties)
    {
        this.Name = Name;
        this.Description = Description;
        this.FieldFlags = FieldFlags;
        this.BuiltInType = BuiltInType;
        this.DataType = DataType;
        this.ValueRank = ValueRank;
        this.ArrayDimensions = ArrayDimensions;
        this.MaxStringLength = MaxStringLength;
        this.DataSetFieldId = DataSetFieldId;
        this.Properties = Properties;
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
    
    public UnsignedShort getFieldFlags()
    {
        return FieldFlags;
    }
    
    public void setFieldFlags(UnsignedShort FieldFlags)
    {
        this.FieldFlags = FieldFlags;
    }
    
    public UnsignedByte getBuiltInType()
    {
        return BuiltInType;
    }
    
    public void setBuiltInType(UnsignedByte BuiltInType)
    {
        this.BuiltInType = BuiltInType;
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
    
    public UUID getDataSetFieldId()
    {
        return DataSetFieldId;
    }
    
    public void setDataSetFieldId(UUID DataSetFieldId)
    {
        this.DataSetFieldId = DataSetFieldId;
    }
    
    public KeyValuePair[] getProperties()
    {
        return Properties;
    }
    
    public void setProperties(KeyValuePair[] Properties)
    {
        this.Properties = Properties;
    }
    
    /**
      * Deep clone
      *
      * @return cloned FieldMetaData
      */
    public FieldMetaData clone()
    {
        FieldMetaData result = (FieldMetaData) super.clone();
        result.Name = Name;
        result.Description = Description;
        result.FieldFlags = FieldFlags;
        result.BuiltInType = BuiltInType;
        result.DataType = DataType;
        result.ValueRank = ValueRank;
        result.ArrayDimensions = ArrayDimensions==null ? null : ArrayDimensions.clone();
        result.MaxStringLength = MaxStringLength;
        result.DataSetFieldId = DataSetFieldId;
        if (Properties!=null) {
            result.Properties = new KeyValuePair[Properties.length];
            for (int i=0; i<Properties.length; i++)
                result.Properties[i] = Properties[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FieldMetaData other = (FieldMetaData) obj;
        if (Name==null) {
            if (other.Name != null) return false;
        } else if (!Name.equals(other.Name)) return false;
        if (Description==null) {
            if (other.Description != null) return false;
        } else if (!Description.equals(other.Description)) return false;
        if (FieldFlags==null) {
            if (other.FieldFlags != null) return false;
        } else if (!FieldFlags.equals(other.FieldFlags)) return false;
        if (BuiltInType==null) {
            if (other.BuiltInType != null) return false;
        } else if (!BuiltInType.equals(other.BuiltInType)) return false;
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
        if (DataSetFieldId==null) {
            if (other.DataSetFieldId != null) return false;
        } else if (!DataSetFieldId.equals(other.DataSetFieldId)) return false;
        if (Properties==null) {
            if (other.Properties != null) return false;
        } else if (!Arrays.equals(Properties, other.Properties)) return false;
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
                + ((FieldFlags == null) ? 0 : FieldFlags.hashCode());
        result = prime * result
                + ((BuiltInType == null) ? 0 : BuiltInType.hashCode());
        result = prime * result
                + ((DataType == null) ? 0 : DataType.hashCode());
        result = prime * result
                + ((ValueRank == null) ? 0 : ValueRank.hashCode());
        result = prime * result
                + ((ArrayDimensions == null) ? 0 : Arrays.hashCode(ArrayDimensions));
        result = prime * result
                + ((MaxStringLength == null) ? 0 : MaxStringLength.hashCode());
        result = prime * result
                + ((DataSetFieldId == null) ? 0 : DataSetFieldId.hashCode());
        result = prime * result
                + ((Properties == null) ? 0 : Arrays.hashCode(Properties));
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
		return "FieldMetaData: "+ObjectUtils.printFieldsDeep(this);
	}

}
