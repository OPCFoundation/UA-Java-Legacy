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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.utils.AbstractStructure;



public class DeleteReferencesItem extends AbstractStructure {
	
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
        DeleteReferencesItem result = (DeleteReferencesItem) super.clone();
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
