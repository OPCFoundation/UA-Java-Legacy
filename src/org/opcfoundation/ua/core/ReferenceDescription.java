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
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.core.NodeClass;



public class ReferenceDescription extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ReferenceDescription);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ReferenceDescription_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ReferenceDescription_Encoding_DefaultXml);
	
    protected NodeId ReferenceTypeId;
    protected Boolean IsForward;
    protected ExpandedNodeId NodeId;
    protected QualifiedName BrowseName;
    protected LocalizedText DisplayName;
    protected NodeClass NodeClass;
    protected ExpandedNodeId TypeDefinition;
    
    public ReferenceDescription() {}
    
    public ReferenceDescription(NodeId ReferenceTypeId, Boolean IsForward, ExpandedNodeId NodeId, QualifiedName BrowseName, LocalizedText DisplayName, NodeClass NodeClass, ExpandedNodeId TypeDefinition)
    {
        this.ReferenceTypeId = ReferenceTypeId;
        this.IsForward = IsForward;
        this.NodeId = NodeId;
        this.BrowseName = BrowseName;
        this.DisplayName = DisplayName;
        this.NodeClass = NodeClass;
        this.TypeDefinition = TypeDefinition;
    }
    
    public NodeId getReferenceTypeId()
    {
        return ReferenceTypeId;
    }
    
    public void setReferenceTypeId(NodeId ReferenceTypeId)
    {
        this.ReferenceTypeId = ReferenceTypeId;
    }
    
    public Boolean getIsForward()
    {
        return IsForward;
    }
    
    public void setIsForward(Boolean IsForward)
    {
        this.IsForward = IsForward;
    }
    
    public ExpandedNodeId getNodeId()
    {
        return NodeId;
    }
    
    public void setNodeId(ExpandedNodeId NodeId)
    {
        this.NodeId = NodeId;
    }
    
    public QualifiedName getBrowseName()
    {
        return BrowseName;
    }
    
    public void setBrowseName(QualifiedName BrowseName)
    {
        this.BrowseName = BrowseName;
    }
    
    public LocalizedText getDisplayName()
    {
        return DisplayName;
    }
    
    public void setDisplayName(LocalizedText DisplayName)
    {
        this.DisplayName = DisplayName;
    }
    
    public NodeClass getNodeClass()
    {
        return NodeClass;
    }
    
    public void setNodeClass(NodeClass NodeClass)
    {
        this.NodeClass = NodeClass;
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
      * @return cloned ReferenceDescription
      */
    public ReferenceDescription clone()
    {
        ReferenceDescription result = new ReferenceDescription();
        result.ReferenceTypeId = ReferenceTypeId;
        result.IsForward = IsForward;
        result.NodeId = NodeId;
        result.BrowseName = BrowseName;
        result.DisplayName = DisplayName;
        result.NodeClass = NodeClass;
        result.TypeDefinition = TypeDefinition;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ReferenceDescription other = (ReferenceDescription) obj;
        if (ReferenceTypeId==null) {
            if (other.ReferenceTypeId != null) return false;
        } else if (!ReferenceTypeId.equals(other.ReferenceTypeId)) return false;
        if (IsForward==null) {
            if (other.IsForward != null) return false;
        } else if (!IsForward.equals(other.IsForward)) return false;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (BrowseName==null) {
            if (other.BrowseName != null) return false;
        } else if (!BrowseName.equals(other.BrowseName)) return false;
        if (DisplayName==null) {
            if (other.DisplayName != null) return false;
        } else if (!DisplayName.equals(other.DisplayName)) return false;
        if (NodeClass==null) {
            if (other.NodeClass != null) return false;
        } else if (!NodeClass.equals(other.NodeClass)) return false;
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
                + ((ReferenceTypeId == null) ? 0 : ReferenceTypeId.hashCode());
        result = prime * result
                + ((IsForward == null) ? 0 : IsForward.hashCode());
        result = prime * result
                + ((NodeId == null) ? 0 : NodeId.hashCode());
        result = prime * result
                + ((BrowseName == null) ? 0 : BrowseName.hashCode());
        result = prime * result
                + ((DisplayName == null) ? 0 : DisplayName.hashCode());
        result = prime * result
                + ((NodeClass == null) ? 0 : NodeClass.hashCode());
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
		return "ReferenceDescription: "+ObjectUtils.printFieldsDeep(this);
	}

}
