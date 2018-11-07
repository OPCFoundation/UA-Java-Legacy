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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.utils.AbstractStructure;



public class PublishedVariableDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.PublishedVariableDataType.getValue());
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.PublishedVariableDataType_Encoding_DefaultBinary.getValue());
	public static final ExpandedNodeId XML = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.PublishedVariableDataType_Encoding_DefaultXml.getValue());
	
    protected NodeId PublishedVariable;
    protected UnsignedInteger AttributeId;
    protected Double SamplingIntervalHint;
    protected UnsignedInteger DeadbandType;
    protected Double DeadbandValue;
    protected String IndexRange;
    protected Variant SubstituteValue;
    protected QualifiedName[] MetaDataProperties;
    
    public PublishedVariableDataType() {}
    
    public PublishedVariableDataType(NodeId PublishedVariable, UnsignedInteger AttributeId, Double SamplingIntervalHint, UnsignedInteger DeadbandType, Double DeadbandValue, String IndexRange, Variant SubstituteValue, QualifiedName[] MetaDataProperties)
    {
        this.PublishedVariable = PublishedVariable;
        this.AttributeId = AttributeId;
        this.SamplingIntervalHint = SamplingIntervalHint;
        this.DeadbandType = DeadbandType;
        this.DeadbandValue = DeadbandValue;
        this.IndexRange = IndexRange;
        this.SubstituteValue = SubstituteValue;
        this.MetaDataProperties = MetaDataProperties;
    }
    
    public NodeId getPublishedVariable()
    {
        return PublishedVariable;
    }
    
    public void setPublishedVariable(NodeId PublishedVariable)
    {
        this.PublishedVariable = PublishedVariable;
    }
    
    public UnsignedInteger getAttributeId()
    {
        return AttributeId;
    }
    
    public void setAttributeId(UnsignedInteger AttributeId)
    {
        this.AttributeId = AttributeId;
    }
    
    public Double getSamplingIntervalHint()
    {
        return SamplingIntervalHint;
    }
    
    public void setSamplingIntervalHint(Double SamplingIntervalHint)
    {
        this.SamplingIntervalHint = SamplingIntervalHint;
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
    
    public String getIndexRange()
    {
        return IndexRange;
    }
    
    public void setIndexRange(String IndexRange)
    {
        this.IndexRange = IndexRange;
    }
    
    public Variant getSubstituteValue()
    {
        return SubstituteValue;
    }
    
    public void setSubstituteValue(Variant SubstituteValue)
    {
        this.SubstituteValue = SubstituteValue;
    }
    
    public QualifiedName[] getMetaDataProperties()
    {
        return MetaDataProperties;
    }
    
    public void setMetaDataProperties(QualifiedName[] MetaDataProperties)
    {
        this.MetaDataProperties = MetaDataProperties;
    }
    
    /**
      * Deep clone
      *
      * @return cloned PublishedVariableDataType
      */
    public PublishedVariableDataType clone()
    {
        PublishedVariableDataType result = (PublishedVariableDataType) super.clone();
        result.PublishedVariable = PublishedVariable;
        result.AttributeId = AttributeId;
        result.SamplingIntervalHint = SamplingIntervalHint;
        result.DeadbandType = DeadbandType;
        result.DeadbandValue = DeadbandValue;
        result.IndexRange = IndexRange;
        result.SubstituteValue = SubstituteValue;
        result.MetaDataProperties = MetaDataProperties==null ? null : MetaDataProperties.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PublishedVariableDataType other = (PublishedVariableDataType) obj;
        if (PublishedVariable==null) {
            if (other.PublishedVariable != null) return false;
        } else if (!PublishedVariable.equals(other.PublishedVariable)) return false;
        if (AttributeId==null) {
            if (other.AttributeId != null) return false;
        } else if (!AttributeId.equals(other.AttributeId)) return false;
        if (SamplingIntervalHint==null) {
            if (other.SamplingIntervalHint != null) return false;
        } else if (!SamplingIntervalHint.equals(other.SamplingIntervalHint)) return false;
        if (DeadbandType==null) {
            if (other.DeadbandType != null) return false;
        } else if (!DeadbandType.equals(other.DeadbandType)) return false;
        if (DeadbandValue==null) {
            if (other.DeadbandValue != null) return false;
        } else if (!DeadbandValue.equals(other.DeadbandValue)) return false;
        if (IndexRange==null) {
            if (other.IndexRange != null) return false;
        } else if (!IndexRange.equals(other.IndexRange)) return false;
        if (SubstituteValue==null) {
            if (other.SubstituteValue != null) return false;
        } else if (!SubstituteValue.equals(other.SubstituteValue)) return false;
        if (MetaDataProperties==null) {
            if (other.MetaDataProperties != null) return false;
        } else if (!Arrays.equals(MetaDataProperties, other.MetaDataProperties)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((PublishedVariable == null) ? 0 : PublishedVariable.hashCode());
        result = prime * result
                + ((AttributeId == null) ? 0 : AttributeId.hashCode());
        result = prime * result
                + ((SamplingIntervalHint == null) ? 0 : SamplingIntervalHint.hashCode());
        result = prime * result
                + ((DeadbandType == null) ? 0 : DeadbandType.hashCode());
        result = prime * result
                + ((DeadbandValue == null) ? 0 : DeadbandValue.hashCode());
        result = prime * result
                + ((IndexRange == null) ? 0 : IndexRange.hashCode());
        result = prime * result
                + ((SubstituteValue == null) ? 0 : SubstituteValue.hashCode());
        result = prime * result
                + ((MetaDataProperties == null) ? 0 : Arrays.hashCode(MetaDataProperties));
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
		return "PublishedVariableDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
