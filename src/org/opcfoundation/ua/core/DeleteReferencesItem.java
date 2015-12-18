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



public class DeleteReferencesItem extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DeleteReferencesItem);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DeleteReferencesItem_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DeleteReferencesItem_Encoding_DefaultXml);
	
    protected NodeId SourceNodeId;
    protected NodeId ReferenceTypeId;
    protected Boolean IsForward;
    protected ExpandedNodeId TargetNodeId;
    protected Boolean DeleteBidirectional;
    
    public DeleteReferencesItem() {}
    
    public DeleteReferencesItem(NodeId SourceNodeId, NodeId ReferenceTypeId, Boolean IsForward, ExpandedNodeId TargetNodeId, Boolean DeleteBidirectional)
    {
        this.SourceNodeId = SourceNodeId;
        this.ReferenceTypeId = ReferenceTypeId;
        this.IsForward = IsForward;
        this.TargetNodeId = TargetNodeId;
        this.DeleteBidirectional = DeleteBidirectional;
    }
    
    public NodeId getSourceNodeId()
    {
        return SourceNodeId;
    }
    
    public void setSourceNodeId(NodeId SourceNodeId)
    {
        this.SourceNodeId = SourceNodeId;
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
    
    public ExpandedNodeId getTargetNodeId()
    {
        return TargetNodeId;
    }
    
    public void setTargetNodeId(ExpandedNodeId TargetNodeId)
    {
        this.TargetNodeId = TargetNodeId;
    }
    
    public Boolean getDeleteBidirectional()
    {
        return DeleteBidirectional;
    }
    
    public void setDeleteBidirectional(Boolean DeleteBidirectional)
    {
        this.DeleteBidirectional = DeleteBidirectional;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DeleteReferencesItem
      */
    public DeleteReferencesItem clone()
    {
        DeleteReferencesItem result = new DeleteReferencesItem();
        result.SourceNodeId = SourceNodeId;
        result.ReferenceTypeId = ReferenceTypeId;
        result.IsForward = IsForward;
        result.TargetNodeId = TargetNodeId;
        result.DeleteBidirectional = DeleteBidirectional;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DeleteReferencesItem other = (DeleteReferencesItem) obj;
        if (SourceNodeId==null) {
            if (other.SourceNodeId != null) return false;
        } else if (!SourceNodeId.equals(other.SourceNodeId)) return false;
        if (ReferenceTypeId==null) {
            if (other.ReferenceTypeId != null) return false;
        } else if (!ReferenceTypeId.equals(other.ReferenceTypeId)) return false;
        if (IsForward==null) {
            if (other.IsForward != null) return false;
        } else if (!IsForward.equals(other.IsForward)) return false;
        if (TargetNodeId==null) {
            if (other.TargetNodeId != null) return false;
        } else if (!TargetNodeId.equals(other.TargetNodeId)) return false;
        if (DeleteBidirectional==null) {
            if (other.DeleteBidirectional != null) return false;
        } else if (!DeleteBidirectional.equals(other.DeleteBidirectional)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((SourceNodeId == null) ? 0 : SourceNodeId.hashCode());
        result = prime * result
                + ((ReferenceTypeId == null) ? 0 : ReferenceTypeId.hashCode());
        result = prime * result
                + ((IsForward == null) ? 0 : IsForward.hashCode());
        result = prime * result
                + ((TargetNodeId == null) ? 0 : TargetNodeId.hashCode());
        result = prime * result
                + ((DeleteBidirectional == null) ? 0 : DeleteBidirectional.hashCode());
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
		return "DeleteReferencesItem: "+ObjectUtils.printFieldsDeep(this);
	}

}
