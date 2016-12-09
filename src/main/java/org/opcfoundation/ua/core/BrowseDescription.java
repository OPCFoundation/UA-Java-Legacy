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

import java.util.EnumSet;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.BrowseResultMask;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.NodeClass;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.BrowseDirection;
import org.opcfoundation.ua.utils.AbstractStructure;



public class BrowseDescription extends AbstractStructure implements Structure, Cloneable {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.BrowseDescription);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.BrowseDescription_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.BrowseDescription_Encoding_DefaultXml);
	
    protected NodeId NodeId;
    protected BrowseDirection BrowseDirection;
    protected NodeId ReferenceTypeId;
    protected Boolean IncludeSubtypes;
    protected UnsignedInteger NodeClassMask;
    protected UnsignedInteger ResultMask;
    
    public BrowseDescription() {}
    
    public BrowseDescription(NodeId NodeId, BrowseDirection BrowseDirection, NodeId ReferenceTypeId, Boolean IncludeSubtypes, UnsignedInteger NodeClassMask, UnsignedInteger ResultMask)
    {
        this.NodeId = NodeId;
        this.BrowseDirection = BrowseDirection;
        this.ReferenceTypeId = ReferenceTypeId;
        this.IncludeSubtypes = IncludeSubtypes;
        this.NodeClassMask = NodeClassMask;
        this.ResultMask = ResultMask;
    }
    
    public NodeId getNodeId()
    {
        return NodeId;
    }
    
    public void setNodeId(NodeId NodeId)
    {
        this.NodeId = NodeId;
    }
    
    public BrowseDirection getBrowseDirection()
    {
        return BrowseDirection;
    }
    
    public void setBrowseDirection(BrowseDirection BrowseDirection)
    {
        this.BrowseDirection = BrowseDirection;
    }
    
    public NodeId getReferenceTypeId()
    {
        return ReferenceTypeId;
    }
    
    public void setReferenceTypeId(NodeId ReferenceTypeId)
    {
        this.ReferenceTypeId = ReferenceTypeId;
    }
    
    public Boolean getIncludeSubtypes()
    {
        return IncludeSubtypes;
    }
    
    public void setIncludeSubtypes(Boolean IncludeSubtypes)
    {
        this.IncludeSubtypes = IncludeSubtypes;
    }
    
    public UnsignedInteger getNodeClassMask()
    {
        return NodeClassMask;
    }
    
    public void setNodeClassMask(UnsignedInteger NodeClassMask)
    {
        this.NodeClassMask = NodeClassMask;
    }
    
    public UnsignedInteger getResultMask()
    {
        return ResultMask;
    }
    
    public void setResultMask(UnsignedInteger ResultMask)
    {
        this.ResultMask = ResultMask;
    }
    
    /**
      * Deep clone
      *
      * @return cloned BrowseDescription
      */
    public BrowseDescription clone()
    {
        BrowseDescription result = (BrowseDescription) super.clone();
        result.NodeId = NodeId;
        result.BrowseDirection = BrowseDirection;
        result.ReferenceTypeId = ReferenceTypeId;
        result.IncludeSubtypes = IncludeSubtypes;
        result.NodeClassMask = NodeClassMask;
        result.ResultMask = ResultMask;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BrowseDescription other = (BrowseDescription) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (BrowseDirection==null) {
            if (other.BrowseDirection != null) return false;
        } else if (!BrowseDirection.equals(other.BrowseDirection)) return false;
        if (ReferenceTypeId==null) {
            if (other.ReferenceTypeId != null) return false;
        } else if (!ReferenceTypeId.equals(other.ReferenceTypeId)) return false;
        if (IncludeSubtypes==null) {
            if (other.IncludeSubtypes != null) return false;
        } else if (!IncludeSubtypes.equals(other.IncludeSubtypes)) return false;
        if (NodeClassMask==null) {
            if (other.NodeClassMask != null) return false;
        } else if (!NodeClassMask.equals(other.NodeClassMask)) return false;
        if (ResultMask==null) {
            if (other.ResultMask != null) return false;
        } else if (!ResultMask.equals(other.ResultMask)) return false;
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
                + ((BrowseDirection == null) ? 0 : BrowseDirection.hashCode());
        result = prime * result
                + ((ReferenceTypeId == null) ? 0 : ReferenceTypeId.hashCode());
        result = prime * result
                + ((IncludeSubtypes == null) ? 0 : IncludeSubtypes.hashCode());
        result = prime * result
                + ((NodeClassMask == null) ? 0 : NodeClassMask.hashCode());
        result = prime * result
                + ((ResultMask == null) ? 0 : ResultMask.hashCode());
        return result;
    }
    


	public void setNodeClassMask(EnumSet<NodeClass> nodeClasses) {
		int result = 0;
		for (NodeClass c : nodeClasses) 
			result |= c.getValue();
		NodeClassMask = UnsignedInteger.valueOf(result);
	}

	public void setResultMask(EnumSet<BrowseResultMask> resultMask) {
		int result = 0;
		for (BrowseResultMask c : resultMask) 
			result |= c.getValue();
		ResultMask = UnsignedInteger.valueOf(result);
	}    

	public void setNodeClassMask(NodeClass ... nodeClasses) {
		int result = 0;
		for (NodeClass c : nodeClasses) 
			result |= c.getValue();
		NodeClassMask = UnsignedInteger.valueOf(result);
	}

	public void setResultMask(BrowseResultMask ... resultMask) {
		int result = 0;
		for (BrowseResultMask c : resultMask) 
			result |= c.getValue();
		ResultMask = UnsignedInteger.valueOf(result);
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
		return "BrowseDescription: "+ObjectUtils.printFieldsDeep(this);
	}

}
