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
import org.opcfoundation.ua.utils.AbstractStructure;



public class NodeReference extends AbstractStructure {
	
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
        NodeReference result = (NodeReference) super.clone();
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
