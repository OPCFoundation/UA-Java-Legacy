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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.DataTypeDefinition;
import org.opcfoundation.ua.core.StructureField;
import org.opcfoundation.ua.core.StructureType;



public class StructureDefinition extends DataTypeDefinition {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.StructureDefinition);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.StructureDefinition_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.StructureDefinition_Encoding_DefaultXml);
	
    protected NodeId DefaultEncodingId;
    protected NodeId BaseDataType;
    protected StructureType StructureType;
    protected StructureField[] Fields;
    
    public StructureDefinition() {}
    
    public StructureDefinition(NodeId DefaultEncodingId, NodeId BaseDataType, StructureType StructureType, StructureField[] Fields)
    {
        this.DefaultEncodingId = DefaultEncodingId;
        this.BaseDataType = BaseDataType;
        this.StructureType = StructureType;
        this.Fields = Fields;
    }
    
    public NodeId getDefaultEncodingId()
    {
        return DefaultEncodingId;
    }
    
    public void setDefaultEncodingId(NodeId DefaultEncodingId)
    {
        this.DefaultEncodingId = DefaultEncodingId;
    }
    
    public NodeId getBaseDataType()
    {
        return BaseDataType;
    }
    
    public void setBaseDataType(NodeId BaseDataType)
    {
        this.BaseDataType = BaseDataType;
    }
    
    public StructureType getStructureType()
    {
        return StructureType;
    }
    
    public void setStructureType(StructureType StructureType)
    {
        this.StructureType = StructureType;
    }
    
    public StructureField[] getFields()
    {
        return Fields;
    }
    
    public void setFields(StructureField[] Fields)
    {
        this.Fields = Fields;
    }
    
    /**
      * Deep clone
      *
      * @return cloned StructureDefinition
      */
    public StructureDefinition clone()
    {
        StructureDefinition result = (StructureDefinition) super.clone();
        result.DefaultEncodingId = DefaultEncodingId;
        result.BaseDataType = BaseDataType;
        result.StructureType = StructureType;
        if (Fields!=null) {
            result.Fields = new StructureField[Fields.length];
            for (int i=0; i<Fields.length; i++)
                result.Fields[i] = Fields[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StructureDefinition other = (StructureDefinition) obj;
        if (DefaultEncodingId==null) {
            if (other.DefaultEncodingId != null) return false;
        } else if (!DefaultEncodingId.equals(other.DefaultEncodingId)) return false;
        if (BaseDataType==null) {
            if (other.BaseDataType != null) return false;
        } else if (!BaseDataType.equals(other.BaseDataType)) return false;
        if (StructureType==null) {
            if (other.StructureType != null) return false;
        } else if (!StructureType.equals(other.StructureType)) return false;
        if (Fields==null) {
            if (other.Fields != null) return false;
        } else if (!Arrays.equals(Fields, other.Fields)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((DefaultEncodingId == null) ? 0 : DefaultEncodingId.hashCode());
        result = prime * result
                + ((BaseDataType == null) ? 0 : BaseDataType.hashCode());
        result = prime * result
                + ((StructureType == null) ? 0 : StructureType.hashCode());
        result = prime * result
                + ((Fields == null) ? 0 : Arrays.hashCode(Fields));
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
		return "StructureDefinition: "+ObjectUtils.printFieldsDeep(this);
	}

}
