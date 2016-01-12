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
import org.opcfoundation.ua.builtintypes.Variant;



public class QueryDataSet extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.QueryDataSet);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.QueryDataSet_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.QueryDataSet_Encoding_DefaultXml);
	
    protected ExpandedNodeId NodeId;
    protected ExpandedNodeId TypeDefinitionNode;
    protected Variant[] Values;
    
    public QueryDataSet() {}
    
    public QueryDataSet(ExpandedNodeId NodeId, ExpandedNodeId TypeDefinitionNode, Variant[] Values)
    {
        this.NodeId = NodeId;
        this.TypeDefinitionNode = TypeDefinitionNode;
        this.Values = Values;
    }
    
    public ExpandedNodeId getNodeId()
    {
        return NodeId;
    }
    
    public void setNodeId(ExpandedNodeId NodeId)
    {
        this.NodeId = NodeId;
    }
    
    public ExpandedNodeId getTypeDefinitionNode()
    {
        return TypeDefinitionNode;
    }
    
    public void setTypeDefinitionNode(ExpandedNodeId TypeDefinitionNode)
    {
        this.TypeDefinitionNode = TypeDefinitionNode;
    }
    
    public Variant[] getValues()
    {
        return Values;
    }
    
    public void setValues(Variant[] Values)
    {
        this.Values = Values;
    }
    
    /**
      * Deep clone
      *
      * @return cloned QueryDataSet
      */
    public QueryDataSet clone()
    {
        QueryDataSet result = new QueryDataSet();
        result.NodeId = NodeId;
        result.TypeDefinitionNode = TypeDefinitionNode;
        result.Values = Values==null ? null : Values.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        QueryDataSet other = (QueryDataSet) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (TypeDefinitionNode==null) {
            if (other.TypeDefinitionNode != null) return false;
        } else if (!TypeDefinitionNode.equals(other.TypeDefinitionNode)) return false;
        if (Values==null) {
            if (other.Values != null) return false;
        } else if (!Arrays.equals(Values, other.Values)) return false;
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
                + ((TypeDefinitionNode == null) ? 0 : TypeDefinitionNode.hashCode());
        result = prime * result
                + ((Values == null) ? 0 : Arrays.hashCode(Values));
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
		return "QueryDataSet: "+ObjectUtils.printFieldsDeep(this);
	}

}
