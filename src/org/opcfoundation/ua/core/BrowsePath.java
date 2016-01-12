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
import org.opcfoundation.ua.core.RelativePath;



public class BrowsePath extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.BrowsePath);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.BrowsePath_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.BrowsePath_Encoding_DefaultXml);
	
    protected NodeId StartingNode;
    protected RelativePath RelativePath;
    
    public BrowsePath() {}
    
    public BrowsePath(NodeId StartingNode, RelativePath RelativePath)
    {
        this.StartingNode = StartingNode;
        this.RelativePath = RelativePath;
    }
    
    public NodeId getStartingNode()
    {
        return StartingNode;
    }
    
    public void setStartingNode(NodeId StartingNode)
    {
        this.StartingNode = StartingNode;
    }
    
    public RelativePath getRelativePath()
    {
        return RelativePath;
    }
    
    public void setRelativePath(RelativePath RelativePath)
    {
        this.RelativePath = RelativePath;
    }
    
    /**
      * Deep clone
      *
      * @return cloned BrowsePath
      */
    public BrowsePath clone()
    {
        BrowsePath result = new BrowsePath();
        result.StartingNode = StartingNode;
        result.RelativePath = RelativePath==null ? null : RelativePath.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BrowsePath other = (BrowsePath) obj;
        if (StartingNode==null) {
            if (other.StartingNode != null) return false;
        } else if (!StartingNode.equals(other.StartingNode)) return false;
        if (RelativePath==null) {
            if (other.RelativePath != null) return false;
        } else if (!RelativePath.equals(other.RelativePath)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((StartingNode == null) ? 0 : StartingNode.hashCode());
        result = prime * result
                + ((RelativePath == null) ? 0 : RelativePath.hashCode());
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
		return "BrowsePath: "+ObjectUtils.printFieldsDeep(this);
	}

}
