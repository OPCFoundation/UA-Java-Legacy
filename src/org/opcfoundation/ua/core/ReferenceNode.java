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



public class ReferenceNode extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ReferenceNode);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ReferenceNode_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ReferenceNode_Encoding_DefaultXml);
	
    protected NodeId ReferenceTypeId;
    protected Boolean IsInverse;
    protected ExpandedNodeId TargetId;
    
    public ReferenceNode() {}
    
    public ReferenceNode(NodeId ReferenceTypeId, Boolean IsInverse, ExpandedNodeId TargetId)
    {
        this.ReferenceTypeId = ReferenceTypeId;
        this.IsInverse = IsInverse;
        this.TargetId = TargetId;
    }
    
    public NodeId getReferenceTypeId()
    {
        return ReferenceTypeId;
    }
    
    public void setReferenceTypeId(NodeId ReferenceTypeId)
    {
        this.ReferenceTypeId = ReferenceTypeId;
    }
    
    public Boolean getIsInverse()
    {
        return IsInverse;
    }
    
    public void setIsInverse(Boolean IsInverse)
    {
        this.IsInverse = IsInverse;
    }
    
    public ExpandedNodeId getTargetId()
    {
        return TargetId;
    }
    
    public void setTargetId(ExpandedNodeId TargetId)
    {
        this.TargetId = TargetId;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ReferenceNode
      */
    public ReferenceNode clone()
    {
        ReferenceNode result = new ReferenceNode();
        result.ReferenceTypeId = ReferenceTypeId;
        result.IsInverse = IsInverse;
        result.TargetId = TargetId;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ReferenceNode other = (ReferenceNode) obj;
        if (ReferenceTypeId==null) {
            if (other.ReferenceTypeId != null) return false;
        } else if (!ReferenceTypeId.equals(other.ReferenceTypeId)) return false;
        if (IsInverse==null) {
            if (other.IsInverse != null) return false;
        } else if (!IsInverse.equals(other.IsInverse)) return false;
        if (TargetId==null) {
            if (other.TargetId != null) return false;
        } else if (!TargetId.equals(other.TargetId)) return false;
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
                + ((IsInverse == null) ? 0 : IsInverse.hashCode());
        result = prime * result
                + ((TargetId == null) ? 0 : TargetId.hashCode());
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
		return "ReferenceNode: "+ObjectUtils.printFieldsDeep(this);
	}

}
