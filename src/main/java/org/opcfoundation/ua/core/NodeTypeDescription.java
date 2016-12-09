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
import org.opcfoundation.ua.core.QueryDataDescription;
import org.opcfoundation.ua.utils.AbstractStructure;



public class NodeTypeDescription extends AbstractStructure {
	
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
        NodeTypeDescription result = (NodeTypeDescription) super.clone();
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
