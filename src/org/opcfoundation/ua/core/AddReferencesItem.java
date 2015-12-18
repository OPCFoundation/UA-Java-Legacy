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
import org.opcfoundation.ua.core.NodeClass;



public class AddReferencesItem extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.AddReferencesItem);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.AddReferencesItem_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.AddReferencesItem_Encoding_DefaultXml);
	
    protected NodeId SourceNodeId;
    protected NodeId ReferenceTypeId;
    protected Boolean IsForward;
    protected String TargetServerUri;
    protected ExpandedNodeId TargetNodeId;
    protected NodeClass TargetNodeClass;
    
    public AddReferencesItem() {}
    
    public AddReferencesItem(NodeId SourceNodeId, NodeId ReferenceTypeId, Boolean IsForward, String TargetServerUri, ExpandedNodeId TargetNodeId, NodeClass TargetNodeClass)
    {
        this.SourceNodeId = SourceNodeId;
        this.ReferenceTypeId = ReferenceTypeId;
        this.IsForward = IsForward;
        this.TargetServerUri = TargetServerUri;
        this.TargetNodeId = TargetNodeId;
        this.TargetNodeClass = TargetNodeClass;
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
    
    public String getTargetServerUri()
    {
        return TargetServerUri;
    }
    
    public void setTargetServerUri(String TargetServerUri)
    {
        this.TargetServerUri = TargetServerUri;
    }
    
    public ExpandedNodeId getTargetNodeId()
    {
        return TargetNodeId;
    }
    
    public void setTargetNodeId(ExpandedNodeId TargetNodeId)
    {
        this.TargetNodeId = TargetNodeId;
    }
    
    public NodeClass getTargetNodeClass()
    {
        return TargetNodeClass;
    }
    
    public void setTargetNodeClass(NodeClass TargetNodeClass)
    {
        this.TargetNodeClass = TargetNodeClass;
    }
    
    /**
      * Deep clone
      *
      * @return cloned AddReferencesItem
      */
    public AddReferencesItem clone()
    {
        AddReferencesItem result = new AddReferencesItem();
        result.SourceNodeId = SourceNodeId;
        result.ReferenceTypeId = ReferenceTypeId;
        result.IsForward = IsForward;
        result.TargetServerUri = TargetServerUri;
        result.TargetNodeId = TargetNodeId;
        result.TargetNodeClass = TargetNodeClass;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AddReferencesItem other = (AddReferencesItem) obj;
        if (SourceNodeId==null) {
            if (other.SourceNodeId != null) return false;
        } else if (!SourceNodeId.equals(other.SourceNodeId)) return false;
        if (ReferenceTypeId==null) {
            if (other.ReferenceTypeId != null) return false;
        } else if (!ReferenceTypeId.equals(other.ReferenceTypeId)) return false;
        if (IsForward==null) {
            if (other.IsForward != null) return false;
        } else if (!IsForward.equals(other.IsForward)) return false;
        if (TargetServerUri==null) {
            if (other.TargetServerUri != null) return false;
        } else if (!TargetServerUri.equals(other.TargetServerUri)) return false;
        if (TargetNodeId==null) {
            if (other.TargetNodeId != null) return false;
        } else if (!TargetNodeId.equals(other.TargetNodeId)) return false;
        if (TargetNodeClass==null) {
            if (other.TargetNodeClass != null) return false;
        } else if (!TargetNodeClass.equals(other.TargetNodeClass)) return false;
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
                + ((TargetServerUri == null) ? 0 : TargetServerUri.hashCode());
        result = prime * result
                + ((TargetNodeId == null) ? 0 : TargetNodeId.hashCode());
        result = prime * result
                + ((TargetNodeClass == null) ? 0 : TargetNodeClass.hashCode());
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
		return "AddReferencesItem: "+ObjectUtils.printFieldsDeep(this);
	}

}
