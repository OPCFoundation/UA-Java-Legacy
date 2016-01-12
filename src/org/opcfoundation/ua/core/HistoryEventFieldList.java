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
import org.opcfoundation.ua.builtintypes.Variant;



public class HistoryEventFieldList extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.HistoryEventFieldList);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.HistoryEventFieldList_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.HistoryEventFieldList_Encoding_DefaultXml);
	
    protected Variant[] EventFields;
    
    public HistoryEventFieldList() {}
    
    public HistoryEventFieldList(Variant[] EventFields)
    {
        this.EventFields = EventFields;
    }
    
    public Variant[] getEventFields()
    {
        return EventFields;
    }
    
    public void setEventFields(Variant[] EventFields)
    {
        this.EventFields = EventFields;
    }
    
    /**
      * Deep clone
      *
      * @return cloned HistoryEventFieldList
      */
    public HistoryEventFieldList clone()
    {
        HistoryEventFieldList result = new HistoryEventFieldList();
        result.EventFields = EventFields==null ? null : EventFields.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        HistoryEventFieldList other = (HistoryEventFieldList) obj;
        if (EventFields==null) {
            if (other.EventFields != null) return false;
        } else if (!Arrays.equals(EventFields, other.EventFields)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((EventFields == null) ? 0 : Arrays.hashCode(EventFields));
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
		return "HistoryEventFieldList: "+ObjectUtils.printFieldsDeep(this);
	}

}
