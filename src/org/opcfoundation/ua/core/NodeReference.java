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
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.NodeId;



public class NodeReference extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.NodeReference);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.NodeReference_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.NodeReference_Encoding_DefaultXml);
	
    protected NodeId NodeId;
    protected NodeId ReferenceTypeId;
    protected Boolean IsForward;
    protected NodeId[] ReferencedNodeIds;
    
    public NodeReference() {}
    
    public NodeReference(NodeId NodeId, NodeId ReferenceTypeId, Boolean IsForward, NodeId[] ReferencedNodeIds)
    {
        this.NodeId = NodeId;
        this.ReferenceTypeId = ReferenceTypeId;
        this.IsForward = IsForward;
        this.ReferencedNodeIds = ReferencedNodeIds;
    }
    
    public NodeId getNodeId()
    {
        return NodeId;
    }
    
    public void setNodeId(NodeId NodeId)
    {
        this.NodeId = NodeId;
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
    
    public NodeId[] getReferencedNodeIds()
    {
        return ReferencedNodeIds;
    }
    
    public void setReferencedNodeIds(NodeId[] ReferencedNodeIds)
    {
        this.ReferencedNodeIds = ReferencedNodeIds;
    }
    
    /**
      * Deep clone
      *
      * @return cloned NodeReference
      */
    public NodeReference clone()
    {
        NodeReference result = new NodeReference();
        result.NodeId = NodeId;
        result.ReferenceTypeId = ReferenceTypeId;
        result.IsForward = IsForward;
        result.ReferencedNodeIds = ReferencedNodeIds==null ? null : ReferencedNodeIds.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        NodeReference other = (NodeReference) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (ReferenceTypeId==null) {
            if (other.ReferenceTypeId != null) return false;
        } else if (!ReferenceTypeId.equals(other.ReferenceTypeId)) return false;
        if (IsForward==null) {
            if (other.IsForward != null) return false;
        } else if (!IsForward.equals(other.IsForward)) return false;
        if (ReferencedNodeIds==null) {
            if (other.ReferencedNodeIds != null) return false;
        } else if (!Arrays.equals(ReferencedNodeIds, other.ReferencedNodeIds)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((NodeId == null) ? 0 : NodeId.hashCode());
        result = prime * result
                + ((ReferenceTypeId == null) ? 0 : ReferenceTypeId.hashCode());
        result = prime * result
                + ((IsForward == null) ? 0 : IsForward.hashCode());
        result = prime * result
                + ((ReferencedNodeIds == null) ? 0 : Arrays.hashCode(ReferencedNodeIds));
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
		return "NodeReference: "+ObjectUtils.printFieldsDeep(this);
	}

}
