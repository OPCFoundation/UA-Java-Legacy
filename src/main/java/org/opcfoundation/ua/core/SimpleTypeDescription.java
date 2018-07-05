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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.core.DataTypeDescription;



public class SimpleTypeDescription extends DataTypeDescription {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SimpleTypeDescription);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SimpleTypeDescription_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SimpleTypeDescription_Encoding_DefaultXml);
	
    protected NodeId BaseDataType;
    protected UnsignedByte BuiltInType;
    
    public SimpleTypeDescription() {}
    
    public SimpleTypeDescription(NodeId DataTypeId, QualifiedName Name, NodeId BaseDataType, UnsignedByte BuiltInType)
    {
        super(DataTypeId, Name);
        this.BaseDataType = BaseDataType;
        this.BuiltInType = BuiltInType;
    }
    
    public NodeId getBaseDataType()
    {
        return BaseDataType;
    }
    
    public void setBaseDataType(NodeId BaseDataType)
    {
        this.BaseDataType = BaseDataType;
    }
    
    public UnsignedByte getBuiltInType()
    {
        return BuiltInType;
    }
    
    public void setBuiltInType(UnsignedByte BuiltInType)
    {
        this.BuiltInType = BuiltInType;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SimpleTypeDescription
      */
    public SimpleTypeDescription clone()
    {
        SimpleTypeDescription result = (SimpleTypeDescription) super.clone();
        result.DataTypeId = DataTypeId;
        result.Name = Name;
        result.BaseDataType = BaseDataType;
        result.BuiltInType = BuiltInType;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SimpleTypeDescription other = (SimpleTypeDescription) obj;
        if (DataTypeId==null) {
            if (other.DataTypeId != null) return false;
        } else if (!DataTypeId.equals(other.DataTypeId)) return false;
        if (Name==null) {
            if (other.Name != null) return false;
        } else if (!Name.equals(other.Name)) return false;
        if (BaseDataType==null) {
            if (other.BaseDataType != null) return false;
        } else if (!BaseDataType.equals(other.BaseDataType)) return false;
        if (BuiltInType==null) {
            if (other.BuiltInType != null) return false;
        } else if (!BuiltInType.equals(other.BuiltInType)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((DataTypeId == null) ? 0 : DataTypeId.hashCode());
        result = prime * result
                + ((Name == null) ? 0 : Name.hashCode());
        result = prime * result
                + ((BaseDataType == null) ? 0 : BaseDataType.hashCode());
        result = prime * result
                + ((BuiltInType == null) ? 0 : BuiltInType.hashCode());
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
		return "SimpleTypeDescription: "+ObjectUtils.printFieldsDeep(this);
	}

}
