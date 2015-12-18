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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class BrowsePathTarget extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.BrowsePathTarget);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.BrowsePathTarget_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.BrowsePathTarget_Encoding_DefaultXml);
	
    protected ExpandedNodeId TargetId;
    protected UnsignedInteger RemainingPathIndex;
    
    public BrowsePathTarget() {}
    
    public BrowsePathTarget(ExpandedNodeId TargetId, UnsignedInteger RemainingPathIndex)
    {
        this.TargetId = TargetId;
        this.RemainingPathIndex = RemainingPathIndex;
    }
    
    public ExpandedNodeId getTargetId()
    {
        return TargetId;
    }
    
    public void setTargetId(ExpandedNodeId TargetId)
    {
        this.TargetId = TargetId;
    }
    
    public UnsignedInteger getRemainingPathIndex()
    {
        return RemainingPathIndex;
    }
    
    public void setRemainingPathIndex(UnsignedInteger RemainingPathIndex)
    {
        this.RemainingPathIndex = RemainingPathIndex;
    }
    
    /**
      * Deep clone
      *
      * @return cloned BrowsePathTarget
      */
    public BrowsePathTarget clone()
    {
        BrowsePathTarget result = new BrowsePathTarget();
        result.TargetId = TargetId;
        result.RemainingPathIndex = RemainingPathIndex;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BrowsePathTarget other = (BrowsePathTarget) obj;
        if (TargetId==null) {
            if (other.TargetId != null) return false;
        } else if (!TargetId.equals(other.TargetId)) return false;
        if (RemainingPathIndex==null) {
            if (other.RemainingPathIndex != null) return false;
        } else if (!RemainingPathIndex.equals(other.RemainingPathIndex)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((TargetId == null) ? 0 : TargetId.hashCode());
        result = prime * result
                + ((RemainingPathIndex == null) ? 0 : RemainingPathIndex.hashCode());
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
		return "BrowsePathTarget: "+ObjectUtils.printFieldsDeep(this);
	}

}
