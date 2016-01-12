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
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.core.NodeClass;



public class AddNodesItem extends Object implements Structure, Cloneable {
	
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
        AddNodesItem result = new AddNodesItem();
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
