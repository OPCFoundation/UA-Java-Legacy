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
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.FilterOperand;



public class SimpleAttributeOperand extends FilterOperand {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SimpleAttributeOperand);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SimpleAttributeOperand_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SimpleAttributeOperand_Encoding_DefaultXml);
	
    protected NodeId TypeDefinitionId;
    protected QualifiedName[] BrowsePath;
    protected UnsignedInteger AttributeId;
    protected String IndexRange;
    
    public SimpleAttributeOperand() {}
    
    public SimpleAttributeOperand(NodeId TypeDefinitionId, QualifiedName[] BrowsePath, UnsignedInteger AttributeId, String IndexRange)
    {
        this.TypeDefinitionId = TypeDefinitionId;
        this.BrowsePath = BrowsePath;
        this.AttributeId = AttributeId;
        this.IndexRange = IndexRange;
    }
    
    public NodeId getTypeDefinitionId()
    {
        return TypeDefinitionId;
    }
    
    public void setTypeDefinitionId(NodeId TypeDefinitionId)
    {
        this.TypeDefinitionId = TypeDefinitionId;
    }
    
    public QualifiedName[] getBrowsePath()
    {
        return BrowsePath;
    }
    
    public void setBrowsePath(QualifiedName[] BrowsePath)
    {
        this.BrowsePath = BrowsePath;
    }
    
    public UnsignedInteger getAttributeId()
    {
        return AttributeId;
    }
    
    public void setAttributeId(UnsignedInteger AttributeId)
    {
        this.AttributeId = AttributeId;
    }
    
    public String getIndexRange()
    {
        return IndexRange;
    }
    
    public void setIndexRange(String IndexRange)
    {
        this.IndexRange = IndexRange;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SimpleAttributeOperand
      */
    public SimpleAttributeOperand clone()
    {
        SimpleAttributeOperand result = (SimpleAttributeOperand) super.clone();
        result.TypeDefinitionId = TypeDefinitionId;
        result.BrowsePath = BrowsePath==null ? null : BrowsePath.clone();
        result.AttributeId = AttributeId;
        result.IndexRange = IndexRange;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SimpleAttributeOperand other = (SimpleAttributeOperand) obj;
        if (TypeDefinitionId==null) {
            if (other.TypeDefinitionId != null) return false;
        } else if (!TypeDefinitionId.equals(other.TypeDefinitionId)) return false;
        if (BrowsePath==null) {
            if (other.BrowsePath != null) return false;
        } else if (!Arrays.equals(BrowsePath, other.BrowsePath)) return false;
        if (AttributeId==null) {
            if (other.AttributeId != null) return false;
        } else if (!AttributeId.equals(other.AttributeId)) return false;
        if (IndexRange==null) {
            if (other.IndexRange != null) return false;
        } else if (!IndexRange.equals(other.IndexRange)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((TypeDefinitionId == null) ? 0 : TypeDefinitionId.hashCode());
        result = prime * result
                + ((BrowsePath == null) ? 0 : Arrays.hashCode(BrowsePath));
        result = prime * result
                + ((AttributeId == null) ? 0 : AttributeId.hashCode());
        result = prime * result
                + ((IndexRange == null) ? 0 : IndexRange.hashCode());
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
		return "SimpleAttributeOperand: "+ObjectUtils.printFieldsDeep(this);
	}

}
