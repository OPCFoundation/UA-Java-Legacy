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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.HistoryUpdateDetails;



public class DeleteEventDetails extends HistoryUpdateDetails implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DeleteEventDetails);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DeleteEventDetails_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DeleteEventDetails_Encoding_DefaultXml);
	
    protected byte[][] EventIds;
    
    public DeleteEventDetails() {}
    
    public DeleteEventDetails(NodeId NodeId, byte[][] EventIds)
    {
        super(NodeId);
        this.EventIds = EventIds;
    }
    
    public byte[][] getEventIds()
    {
        return EventIds;
    }
    
    public void setEventIds(byte[][] EventIds)
    {
        this.EventIds = EventIds;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DeleteEventDetails
      */
    public DeleteEventDetails clone()
    {
        DeleteEventDetails result = new DeleteEventDetails();
        result.NodeId = NodeId;
        result.EventIds = EventIds==null ? null : EventIds.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DeleteEventDetails other = (DeleteEventDetails) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (EventIds==null) {
            if (other.EventIds != null) return false;
        } else if (!Arrays.equals(EventIds, other.EventIds)) return false;
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
                + ((EventIds == null) ? 0 : Arrays.hashCode(EventIds));
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
		return "DeleteEventDetails: "+ObjectUtils.printFieldsDeep(this);
	}

}
