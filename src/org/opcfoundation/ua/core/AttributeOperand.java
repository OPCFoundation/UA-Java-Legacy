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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.FilterOperand;
import org.opcfoundation.ua.core.RelativePath;



public class AttributeOperand extends FilterOperand implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.AttributeOperand);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.AttributeOperand_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.AttributeOperand_Encoding_DefaultXml);
	
    protected NodeId NodeId;
    protected String Alias;
    protected RelativePath BrowsePath;
    protected UnsignedInteger AttributeId;
    protected String IndexRange;
    
    public AttributeOperand() {}
    
    public AttributeOperand(NodeId NodeId, String Alias, RelativePath BrowsePath, UnsignedInteger AttributeId, String IndexRange)
    {
        this.NodeId = NodeId;
        this.Alias = Alias;
        this.BrowsePath = BrowsePath;
        this.AttributeId = AttributeId;
        this.IndexRange = IndexRange;
    }
    
    public NodeId getNodeId()
    {
        return NodeId;
    }
    
    public void setNodeId(NodeId NodeId)
    {
        this.NodeId = NodeId;
    }
    
    public String getAlias()
    {
        return Alias;
    }
    
    public void setAlias(String Alias)
    {
        this.Alias = Alias;
    }
    
    public RelativePath getBrowsePath()
    {
        return BrowsePath;
    }
    
    public void setBrowsePath(RelativePath BrowsePath)
    {
        this.BrowsePath = BrowsePath;
    }
    
    public UnsignedInteger getAttributeId()
    {
        return AttributeId;
    }
    
    public void setAttributeId(UnsignedInteger AttributeId)
    {
        this.AttributeId = AttributeId;
    }
    
    public String getIndexRange()
    {
        return IndexRange;
    }
    
    public void setIndexRange(String IndexRange)
    {
        this.IndexRange = IndexRange;
    }
    
    /**
      * Deep clone
      *
      * @return cloned AttributeOperand
      */
    public AttributeOperand clone()
    {
        AttributeOperand result = new AttributeOperand();
        result.NodeId = NodeId;
        result.Alias = Alias;
        result.BrowsePath = BrowsePath==null ? null : BrowsePath.clone();
        result.AttributeId = AttributeId;
        result.IndexRange = IndexRange;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AttributeOperand other = (AttributeOperand) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (Alias==null) {
            if (other.Alias != null) return false;
        } else if (!Alias.equals(other.Alias)) return false;
        if (BrowsePath==null) {
            if (other.BrowsePath != null) return false;
        } else if (!BrowsePath.equals(other.BrowsePath)) return false;
        if (AttributeId==null) {
            if (other.AttributeId != null) return false;
        } else if (!AttributeId.equals(other.AttributeId)) return false;
        if (IndexRange==null) {
            if (other.IndexRange != null) return false;
        } else if (!IndexRange.equals(other.IndexRange)) return false;
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
                + ((Alias == null) ? 0 : Alias.hashCode());
        result = prime * result
                + ((BrowsePath == null) ? 0 : BrowsePath.hashCode());
        result = prime * result
                + ((AttributeId == null) ? 0 : AttributeId.hashCode());
        result = prime * result
                + ((IndexRange == null) ? 0 : IndexRange.hashCode());
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
		return "AttributeOperand: "+ObjectUtils.printFieldsDeep(this);
	}

}
