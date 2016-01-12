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
import org.opcfoundation.ua.builtintypes.StatusCode;



public class AddNodesResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.AddNodesResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.AddNodesResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.AddNodesResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected NodeId AddedNodeId;
    
    public AddNodesResult() {}
    
    public AddNodesResult(StatusCode StatusCode, NodeId AddedNodeId)
    {
        this.StatusCode = StatusCode;
        this.AddedNodeId = AddedNodeId;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public NodeId getAddedNodeId()
    {
        return AddedNodeId;
    }
    
    public void setAddedNodeId(NodeId AddedNodeId)
    {
        this.AddedNodeId = AddedNodeId;
    }
    
    /**
      * Deep clone
      *
      * @return cloned AddNodesResult
      */
    public AddNodesResult clone()
    {
        AddNodesResult result = new AddNodesResult();
        result.StatusCode = StatusCode;
        result.AddedNodeId = AddedNodeId;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AddNodesResult other = (AddNodesResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (AddedNodeId==null) {
            if (other.AddedNodeId != null) return false;
        } else if (!AddedNodeId.equals(other.AddedNodeId)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((StatusCode == null) ? 0 : StatusCode.hashCode());
        result = prime * result
                + ((AddedNodeId == null) ? 0 : AddedNodeId.hashCode());
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
		return "AddNodesResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
