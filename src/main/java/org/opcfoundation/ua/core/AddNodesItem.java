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
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.core.NodeClass;
import org.opcfoundation.ua.utils.AbstractStructure;



public class AddNodesItem extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.AddNodesItem);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.AddNodesItem_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.AddNodesItem_Encoding_DefaultXml);
	
    protected ExpandedNodeId ParentNodeId;
    protected NodeId ReferenceTypeId;
    protected ExpandedNodeId RequestedNewNodeId;
    protected QualifiedName BrowseName;
    protected NodeClass NodeClass;
    protected ExtensionObject NodeAttributes;
    protected ExpandedNodeId TypeDefinition;
    
    public AddNodesItem() {}
    
    public AddNodesItem(ExpandedNodeId ParentNodeId, NodeId ReferenceTypeId, ExpandedNodeId RequestedNewNodeId, QualifiedName BrowseName, NodeClass NodeClass, ExtensionObject NodeAttributes, ExpandedNodeId TypeDefinition)
    {
        this.ParentNodeId = ParentNodeId;
        this.ReferenceTypeId = ReferenceTypeId;
        this.RequestedNewNodeId = RequestedNewNodeId;
        this.BrowseName = BrowseName;
        this.NodeClass = NodeClass;
        this.NodeAttributes = NodeAttributes;
        this.TypeDefinition = TypeDefinition;
    }
    
    public ExpandedNodeId getParentNodeId()
    {
        return ParentNodeId;
    }
    
    public void setParentNodeId(ExpandedNodeId ParentNodeId)
    {
        this.ParentNodeId = ParentNodeId;
    }
    
    public NodeId getReferenceTypeId()
    {
        return ReferenceTypeId;
    }
    
    public void setReferenceTypeId(NodeId ReferenceTypeId)
    {
        this.ReferenceTypeId = ReferenceTypeId;
    }
    
    public ExpandedNodeId getRequestedNewNodeId()
    {
        return RequestedNewNodeId;
    }
    
    public void setRequestedNewNodeId(ExpandedNodeId RequestedNewNodeId)
    {
        this.RequestedNewNodeId = RequestedNewNodeId;
    }
    
    public QualifiedName getBrowseName()
    {
        return BrowseName;
    }
    
    public void setBrowseName(QualifiedName BrowseName)
    {
        this.BrowseName = BrowseName;
    }
    
    public NodeClass getNodeClass()
    {
        return NodeClass;
    }
    
    public void setNodeClass(NodeClass NodeClass)
    {
        this.NodeClass = NodeClass;
    }
    
    public ExtensionObject getNodeAttributes()
    {
        return NodeAttributes;
    }
    
    public void setNodeAttributes(ExtensionObject NodeAttributes)
    {
        this.NodeAttributes = NodeAttributes;
    }
    
    public ExpandedNodeId getTypeDefinition()
    {
        return TypeDefinition;
    }
    
    public void setTypeDefinition(ExpandedNodeId TypeDefinition)
    {
        this.TypeDefinition = TypeDefinition;
    }
    
    /**
      * Deep clone
      *
      * @return cloned AddNodesItem
      */
    public AddNodesItem clone()
    {
        AddNodesItem result = (AddNodesItem) super.clone();
        result.ParentNodeId = ParentNodeId;
        result.ReferenceTypeId = ReferenceTypeId;
        result.RequestedNewNodeId = RequestedNewNodeId;
        result.BrowseName = BrowseName;
        result.NodeClass = NodeClass;
        result.NodeAttributes = NodeAttributes;
        result.TypeDefinition = TypeDefinition;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AddNodesItem other = (AddNodesItem) obj;
        if (ParentNodeId==null) {
            if (other.ParentNodeId != null) return false;
        } else if (!ParentNodeId.equals(other.ParentNodeId)) return false;
        if (ReferenceTypeId==null) {
            if (other.ReferenceTypeId != null) return false;
        } else if (!ReferenceTypeId.equals(other.ReferenceTypeId)) return false;
        if (RequestedNewNodeId==null) {
            if (other.RequestedNewNodeId != null) return false;
        } else if (!RequestedNewNodeId.equals(other.RequestedNewNodeId)) return false;
        if (BrowseName==null) {
            if (other.BrowseName != null) return false;
        } else if (!BrowseName.equals(other.BrowseName)) return false;
        if (NodeClass==null) {
            if (other.NodeClass != null) return false;
        } else if (!NodeClass.equals(other.NodeClass)) return false;
        if (NodeAttributes==null) {
            if (other.NodeAttributes != null) return false;
        } else if (!NodeAttributes.equals(other.NodeAttributes)) return false;
        if (TypeDefinition==null) {
            if (other.TypeDefinition != null) return false;
        } else if (!TypeDefinition.equals(other.TypeDefinition)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ParentNodeId == null) ? 0 : ParentNodeId.hashCode());
        result = prime * result
                + ((ReferenceTypeId == null) ? 0 : ReferenceTypeId.hashCode());
        result = prime * result
                + ((RequestedNewNodeId == null) ? 0 : RequestedNewNodeId.hashCode());
        result = prime * result
                + ((BrowseName == null) ? 0 : BrowseName.hashCode());
        result = prime * result
                + ((NodeClass == null) ? 0 : NodeClass.hashCode());
        result = prime * result
                + ((NodeAttributes == null) ? 0 : NodeAttributes.hashCode());
        result = prime * result
                + ((TypeDefinition == null) ? 0 : TypeDefinition.hashCode());
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
		return "AddNodesItem: "+ObjectUtils.printFieldsDeep(this);
	}

}
