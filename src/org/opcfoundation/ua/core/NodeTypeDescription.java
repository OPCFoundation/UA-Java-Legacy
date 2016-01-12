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
import org.opcfoundation.ua.core.QueryDataDescription;



public class NodeTypeDescription extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.NodeTypeDescription);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.NodeTypeDescription_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.NodeTypeDescription_Encoding_DefaultXml);
	
    protected ExpandedNodeId TypeDefinitionNode;
    protected Boolean IncludeSubTypes;
    protected QueryDataDescription[] DataToReturn;
    
    public NodeTypeDescription() {}
    
    public NodeTypeDescription(ExpandedNodeId TypeDefinitionNode, Boolean IncludeSubTypes, QueryDataDescription[] DataToReturn)
    {
        this.TypeDefinitionNode = TypeDefinitionNode;
        this.IncludeSubTypes = IncludeSubTypes;
        this.DataToReturn = DataToReturn;
    }
    
    public ExpandedNodeId getTypeDefinitionNode()
    {
        return TypeDefinitionNode;
    }
    
    public void setTypeDefinitionNode(ExpandedNodeId TypeDefinitionNode)
    {
        this.TypeDefinitionNode = TypeDefinitionNode;
    }
    
    public Boolean getIncludeSubTypes()
    {
        return IncludeSubTypes;
    }
    
    public void setIncludeSubTypes(Boolean IncludeSubTypes)
    {
        this.IncludeSubTypes = IncludeSubTypes;
    }
    
    public QueryDataDescription[] getDataToReturn()
    {
        return DataToReturn;
    }
    
    public void setDataToReturn(QueryDataDescription[] DataToReturn)
    {
        this.DataToReturn = DataToReturn;
    }
    
    /**
      * Deep clone
      *
      * @return cloned NodeTypeDescription
      */
    public NodeTypeDescription clone()
    {
        NodeTypeDescription result = new NodeTypeDescription();
        result.TypeDefinitionNode = TypeDefinitionNode;
        result.IncludeSubTypes = IncludeSubTypes;
        if (DataToReturn!=null) {
            result.DataToReturn = new QueryDataDescription[DataToReturn.length];
            for (int i=0; i<DataToReturn.length; i++)
                result.DataToReturn[i] = DataToReturn[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        NodeTypeDescription other = (NodeTypeDescription) obj;
        if (TypeDefinitionNode==null) {
            if (other.TypeDefinitionNode != null) return false;
        } else if (!TypeDefinitionNode.equals(other.TypeDefinitionNode)) return false;
        if (IncludeSubTypes==null) {
            if (other.IncludeSubTypes != null) return false;
        } else if (!IncludeSubTypes.equals(other.IncludeSubTypes)) return false;
        if (DataToReturn==null) {
            if (other.DataToReturn != null) return false;
        } else if (!Arrays.equals(DataToReturn, other.DataToReturn)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((TypeDefinitionNode == null) ? 0 : TypeDefinitionNode.hashCode());
        result = prime * result
                + ((IncludeSubTypes == null) ? 0 : IncludeSubTypes.hashCode());
        result = prime * result
                + ((DataToReturn == null) ? 0 : Arrays.hashCode(DataToReturn));
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
		return "NodeTypeDescription: "+ObjectUtils.printFieldsDeep(this);
	}

}
