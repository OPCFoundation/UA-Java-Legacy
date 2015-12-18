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
import org.opcfoundation.ua.builtintypes.NodeId;



public class DeleteNodesItem extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DeleteNodesItem);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DeleteNodesItem_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DeleteNodesItem_Encoding_DefaultXml);
	
    protected NodeId NodeId;
    protected Boolean DeleteTargetReferences;
    
    public DeleteNodesItem() {}
    
    public DeleteNodesItem(NodeId NodeId, Boolean DeleteTargetReferences)
    {
        this.NodeId = NodeId;
        this.DeleteTargetReferences = DeleteTargetReferences;
    }
    
    public NodeId getNodeId()
    {
        return NodeId;
    }
    
    public void setNodeId(NodeId NodeId)
    {
        this.NodeId = NodeId;
    }
    
    public Boolean getDeleteTargetReferences()
    {
        return DeleteTargetReferences;
    }
    
    public void setDeleteTargetReferences(Boolean DeleteTargetReferences)
    {
        this.DeleteTargetReferences = DeleteTargetReferences;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DeleteNodesItem
      */
    public DeleteNodesItem clone()
    {
        DeleteNodesItem result = new DeleteNodesItem();
        result.NodeId = NodeId;
        result.DeleteTargetReferences = DeleteTargetReferences;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DeleteNodesItem other = (DeleteNodesItem) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (DeleteTargetReferences==null) {
            if (other.DeleteTargetReferences != null) return false;
        } else if (!DeleteTargetReferences.equals(other.DeleteTargetReferences)) return false;
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
                + ((DeleteTargetReferences == null) ? 0 : DeleteTargetReferences.hashCode());
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
		return "DeleteNodesItem: "+ObjectUtils.printFieldsDeep(this);
	}

}
