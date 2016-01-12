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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.HistoryUpdateDetails;



public class DeleteRawModifiedDetails extends HistoryUpdateDetails implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DeleteRawModifiedDetails);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DeleteRawModifiedDetails_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DeleteRawModifiedDetails_Encoding_DefaultXml);
	
    protected Boolean IsDeleteModified;
    protected DateTime StartTime;
    protected DateTime EndTime;
    
    public DeleteRawModifiedDetails() {}
    
    public DeleteRawModifiedDetails(NodeId NodeId, Boolean IsDeleteModified, DateTime StartTime, DateTime EndTime)
    {
        super(NodeId);
        this.IsDeleteModified = IsDeleteModified;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
    }
    
    public Boolean getIsDeleteModified()
    {
        return IsDeleteModified;
    }
    
    public void setIsDeleteModified(Boolean IsDeleteModified)
    {
        this.IsDeleteModified = IsDeleteModified;
    }
    
    public DateTime getStartTime()
    {
        return StartTime;
    }
    
    public void setStartTime(DateTime StartTime)
    {
        this.StartTime = StartTime;
    }
    
    public DateTime getEndTime()
    {
        return EndTime;
    }
    
    public void setEndTime(DateTime EndTime)
    {
        this.EndTime = EndTime;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DeleteRawModifiedDetails
      */
    public DeleteRawModifiedDetails clone()
    {
        DeleteRawModifiedDetails result = new DeleteRawModifiedDetails();
        result.NodeId = NodeId;
        result.IsDeleteModified = IsDeleteModified;
        result.StartTime = StartTime;
        result.EndTime = EndTime;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DeleteRawModifiedDetails other = (DeleteRawModifiedDetails) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (IsDeleteModified==null) {
            if (other.IsDeleteModified != null) return false;
        } else if (!IsDeleteModified.equals(other.IsDeleteModified)) return false;
        if (StartTime==null) {
            if (other.StartTime != null) return false;
        } else if (!StartTime.equals(other.StartTime)) return false;
        if (EndTime==null) {
            if (other.EndTime != null) return false;
        } else if (!EndTime.equals(other.EndTime)) return false;
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
                + ((IsDeleteModified == null) ? 0 : IsDeleteModified.hashCode());
        result = prime * result
                + ((StartTime == null) ? 0 : StartTime.hashCode());
        result = prime * result
                + ((EndTime == null) ? 0 : EndTime.hashCode());
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
		return "DeleteRawModifiedDetails: "+ObjectUtils.printFieldsDeep(this);
	}

}
