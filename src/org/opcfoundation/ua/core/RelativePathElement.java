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
import org.opcfoundation.ua.builtintypes.QualifiedName;



public class RelativePathElement extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.RelativePathElement);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.RelativePathElement_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.RelativePathElement_Encoding_DefaultXml);
	
    protected NodeId ReferenceTypeId;
    protected Boolean IsInverse;
    protected Boolean IncludeSubtypes;
    protected QualifiedName TargetName;
    
    public RelativePathElement() {}
    
    public RelativePathElement(NodeId ReferenceTypeId, Boolean IsInverse, Boolean IncludeSubtypes, QualifiedName TargetName)
    {
        this.ReferenceTypeId = ReferenceTypeId;
        this.IsInverse = IsInverse;
        this.IncludeSubtypes = IncludeSubtypes;
        this.TargetName = TargetName;
    }
    
    public NodeId getReferenceTypeId()
    {
        return ReferenceTypeId;
    }
    
    public void setReferenceTypeId(NodeId ReferenceTypeId)
    {
        this.ReferenceTypeId = ReferenceTypeId;
    }
    
    public Boolean getIsInverse()
    {
        return IsInverse;
    }
    
    public void setIsInverse(Boolean IsInverse)
    {
        this.IsInverse = IsInverse;
    }
    
    public Boolean getIncludeSubtypes()
    {
        return IncludeSubtypes;
    }
    
    public void setIncludeSubtypes(Boolean IncludeSubtypes)
    {
        this.IncludeSubtypes = IncludeSubtypes;
    }
    
    public QualifiedName getTargetName()
    {
        return TargetName;
    }
    
    public void setTargetName(QualifiedName TargetName)
    {
        this.TargetName = TargetName;
    }
    
    /**
      * Deep clone
      *
      * @return cloned RelativePathElement
      */
    public RelativePathElement clone()
    {
        RelativePathElement result = new RelativePathElement();
        result.ReferenceTypeId = ReferenceTypeId;
        result.IsInverse = IsInverse;
        result.IncludeSubtypes = IncludeSubtypes;
        result.TargetName = TargetName;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RelativePathElement other = (RelativePathElement) obj;
        if (ReferenceTypeId==null) {
            if (other.ReferenceTypeId != null) return false;
        } else if (!ReferenceTypeId.equals(other.ReferenceTypeId)) return false;
        if (IsInverse==null) {
            if (other.IsInverse != null) return false;
        } else if (!IsInverse.equals(other.IsInverse)) return false;
        if (IncludeSubtypes==null) {
            if (other.IncludeSubtypes != null) return false;
        } else if (!IncludeSubtypes.equals(other.IncludeSubtypes)) return false;
        if (TargetName==null) {
            if (other.TargetName != null) return false;
        } else if (!TargetName.equals(other.TargetName)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ReferenceTypeId == null) ? 0 : ReferenceTypeId.hashCode());
        result = prime * result
                + ((IsInverse == null) ? 0 : IsInverse.hashCode());
        result = prime * result
                + ((IncludeSubtypes == null) ? 0 : IncludeSubtypes.hashCode());
        result = prime * result
                + ((TargetName == null) ? 0 : TargetName.hashCode());
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
		return "RelativePathElement: "+ObjectUtils.printFieldsDeep(this);
	}

}
