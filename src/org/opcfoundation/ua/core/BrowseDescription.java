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

import java.util.EnumSet;

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.ObjectUtils;



public class BrowseDescription extends Object implements Structure, Cloneable {

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
        BrowseDescription result = new BrowseDescription();
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
