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

import java.util.UUID;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.OverrideValueHandling;
import org.opcfoundation.ua.utils.AbstractStructure;



public class FieldTargetDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.FieldTargetDataType.getValue());
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.FieldTargetDataType_Encoding_DefaultBinary.getValue());
	public static final ExpandedNodeId XML = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.FieldTargetDataType_Encoding_DefaultXml.getValue());
	
    protected UUID DataSetFieldId;
    protected String ReceiverIndexRange;
    protected NodeId TargetNodeId;
    protected UnsignedInteger AttributeId;
    protected String WriteIndexRange;
    protected OverrideValueHandling OverrideValueHandling;
    protected Variant OverrideValue;
    
    public FieldTargetDataType() {}
    
    public FieldTargetDataType(UUID DataSetFieldId, String ReceiverIndexRange, NodeId TargetNodeId, UnsignedInteger AttributeId, String WriteIndexRange, OverrideValueHandling OverrideValueHandling, Variant OverrideValue)
    {
        this.DataSetFieldId = DataSetFieldId;
        this.ReceiverIndexRange = ReceiverIndexRange;
        this.TargetNodeId = TargetNodeId;
        this.AttributeId = AttributeId;
        this.WriteIndexRange = WriteIndexRange;
        this.OverrideValueHandling = OverrideValueHandling;
        this.OverrideValue = OverrideValue;
    }
    
    public UUID getDataSetFieldId()
    {
        return DataSetFieldId;
    }
    
    public void setDataSetFieldId(UUID DataSetFieldId)
    {
        this.DataSetFieldId = DataSetFieldId;
    }
    
    public String getReceiverIndexRange()
    {
        return ReceiverIndexRange;
    }
    
    public void setReceiverIndexRange(String ReceiverIndexRange)
    {
        this.ReceiverIndexRange = ReceiverIndexRange;
    }
    
    public NodeId getTargetNodeId()
    {
        return TargetNodeId;
    }
    
    public void setTargetNodeId(NodeId TargetNodeId)
    {
        this.TargetNodeId = TargetNodeId;
    }
    
    public UnsignedInteger getAttributeId()
    {
        return AttributeId;
    }
    
    public void setAttributeId(UnsignedInteger AttributeId)
    {
        this.AttributeId = AttributeId;
    }
    
    public String getWriteIndexRange()
    {
        return WriteIndexRange;
    }
    
    public void setWriteIndexRange(String WriteIndexRange)
    {
        this.WriteIndexRange = WriteIndexRange;
    }
    
    public OverrideValueHandling getOverrideValueHandling()
    {
        return OverrideValueHandling;
    }
    
    public void setOverrideValueHandling(OverrideValueHandling OverrideValueHandling)
    {
        this.OverrideValueHandling = OverrideValueHandling;
    }
    
    public Variant getOverrideValue()
    {
        return OverrideValue;
    }
    
    public void setOverrideValue(Variant OverrideValue)
    {
        this.OverrideValue = OverrideValue;
    }
    
    /**
      * Deep clone
      *
      * @return cloned FieldTargetDataType
      */
    public FieldTargetDataType clone()
    {
        FieldTargetDataType result = (FieldTargetDataType) super.clone();
        result.DataSetFieldId = DataSetFieldId;
        result.ReceiverIndexRange = ReceiverIndexRange;
        result.TargetNodeId = TargetNodeId;
        result.AttributeId = AttributeId;
        result.WriteIndexRange = WriteIndexRange;
        result.OverrideValueHandling = OverrideValueHandling;
        result.OverrideValue = OverrideValue;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FieldTargetDataType other = (FieldTargetDataType) obj;
        if (DataSetFieldId==null) {
            if (other.DataSetFieldId != null) return false;
        } else if (!DataSetFieldId.equals(other.DataSetFieldId)) return false;
        if (ReceiverIndexRange==null) {
            if (other.ReceiverIndexRange != null) return false;
        } else if (!ReceiverIndexRange.equals(other.ReceiverIndexRange)) return false;
        if (TargetNodeId==null) {
            if (other.TargetNodeId != null) return false;
        } else if (!TargetNodeId.equals(other.TargetNodeId)) return false;
        if (AttributeId==null) {
            if (other.AttributeId != null) return false;
        } else if (!AttributeId.equals(other.AttributeId)) return false;
        if (WriteIndexRange==null) {
            if (other.WriteIndexRange != null) return false;
        } else if (!WriteIndexRange.equals(other.WriteIndexRange)) return false;
        if (OverrideValueHandling==null) {
            if (other.OverrideValueHandling != null) return false;
        } else if (!OverrideValueHandling.equals(other.OverrideValueHandling)) return false;
        if (OverrideValue==null) {
            if (other.OverrideValue != null) return false;
        } else if (!OverrideValue.equals(other.OverrideValue)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((DataSetFieldId == null) ? 0 : DataSetFieldId.hashCode());
        result = prime * result
                + ((ReceiverIndexRange == null) ? 0 : ReceiverIndexRange.hashCode());
        result = prime * result
                + ((TargetNodeId == null) ? 0 : TargetNodeId.hashCode());
        result = prime * result
                + ((AttributeId == null) ? 0 : AttributeId.hashCode());
        result = prime * result
                + ((WriteIndexRange == null) ? 0 : WriteIndexRange.hashCode());
        result = prime * result
                + ((OverrideValueHandling == null) ? 0 : OverrideValueHandling.hashCode());
        result = prime * result
                + ((OverrideValue == null) ? 0 : OverrideValue.hashCode());
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
		return "FieldTargetDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
