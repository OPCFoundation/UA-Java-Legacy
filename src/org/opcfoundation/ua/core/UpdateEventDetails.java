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
import org.opcfoundation.ua.core.EventFilter;
import org.opcfoundation.ua.core.HistoryEventFieldList;
import org.opcfoundation.ua.core.HistoryUpdateDetails;
import org.opcfoundation.ua.core.PerformUpdateType;



public class UpdateEventDetails extends HistoryUpdateDetails implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.UpdateEventDetails);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.UpdateEventDetails_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.UpdateEventDetails_Encoding_DefaultXml);
	
    protected PerformUpdateType PerformInsertReplace;
    protected EventFilter Filter;
    protected HistoryEventFieldList[] EventData;
    
    public UpdateEventDetails() {}
    
    public UpdateEventDetails(NodeId NodeId, PerformUpdateType PerformInsertReplace, EventFilter Filter, HistoryEventFieldList[] EventData)
    {
        super(NodeId);
        this.PerformInsertReplace = PerformInsertReplace;
        this.Filter = Filter;
        this.EventData = EventData;
    }
    
    public PerformUpdateType getPerformInsertReplace()
    {
        return PerformInsertReplace;
    }
    
    public void setPerformInsertReplace(PerformUpdateType PerformInsertReplace)
    {
        this.PerformInsertReplace = PerformInsertReplace;
    }
    
    public EventFilter getFilter()
    {
        return Filter;
    }
    
    public void setFilter(EventFilter Filter)
    {
        this.Filter = Filter;
    }
    
    public HistoryEventFieldList[] getEventData()
    {
        return EventData;
    }
    
    public void setEventData(HistoryEventFieldList[] EventData)
    {
        this.EventData = EventData;
    }
    
    /**
      * Deep clone
      *
      * @return cloned UpdateEventDetails
      */
    public UpdateEventDetails clone()
    {
        UpdateEventDetails result = new UpdateEventDetails();
        result.NodeId = NodeId;
        result.PerformInsertReplace = PerformInsertReplace;
        result.Filter = Filter==null ? null : Filter.clone();
        if (EventData!=null) {
            result.EventData = new HistoryEventFieldList[EventData.length];
            for (int i=0; i<EventData.length; i++)
                result.EventData[i] = EventData[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UpdateEventDetails other = (UpdateEventDetails) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (PerformInsertReplace==null) {
            if (other.PerformInsertReplace != null) return false;
        } else if (!PerformInsertReplace.equals(other.PerformInsertReplace)) return false;
        if (Filter==null) {
            if (other.Filter != null) return false;
        } else if (!Filter.equals(other.Filter)) return false;
        if (EventData==null) {
            if (other.EventData != null) return false;
        } else if (!Arrays.equals(EventData, other.EventData)) return false;
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
                + ((PerformInsertReplace == null) ? 0 : PerformInsertReplace.hashCode());
        result = prime * result
                + ((Filter == null) ? 0 : Filter.hashCode());
        result = prime * result
                + ((EventData == null) ? 0 : Arrays.hashCode(EventData));
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
		return "UpdateEventDetails: "+ObjectUtils.printFieldsDeep(this);
	}

}
