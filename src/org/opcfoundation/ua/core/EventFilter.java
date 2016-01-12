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
import org.opcfoundation.ua.core.ContentFilter;
import org.opcfoundation.ua.core.MonitoringFilter;
import org.opcfoundation.ua.core.SimpleAttributeOperand;



public class EventFilter extends MonitoringFilter implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.EventFilter);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.EventFilter_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.EventFilter_Encoding_DefaultXml);
	
    protected SimpleAttributeOperand[] SelectClauses;
    protected ContentFilter WhereClause;
    
    public EventFilter() {}
    
    public EventFilter(SimpleAttributeOperand[] SelectClauses, ContentFilter WhereClause)
    {
        this.SelectClauses = SelectClauses;
        this.WhereClause = WhereClause;
    }
    
    public SimpleAttributeOperand[] getSelectClauses()
    {
        return SelectClauses;
    }
    
    public void setSelectClauses(SimpleAttributeOperand[] SelectClauses)
    {
        this.SelectClauses = SelectClauses;
    }
    
    public ContentFilter getWhereClause()
    {
        return WhereClause;
    }
    
    public void setWhereClause(ContentFilter WhereClause)
    {
        this.WhereClause = WhereClause;
    }
    
    /**
      * Deep clone
      *
      * @return cloned EventFilter
      */
    public EventFilter clone()
    {
        EventFilter result = new EventFilter();
        if (SelectClauses!=null) {
            result.SelectClauses = new SimpleAttributeOperand[SelectClauses.length];
            for (int i=0; i<SelectClauses.length; i++)
                result.SelectClauses[i] = SelectClauses[i].clone();
        }
        result.WhereClause = WhereClause==null ? null : WhereClause.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EventFilter other = (EventFilter) obj;
        if (SelectClauses==null) {
            if (other.SelectClauses != null) return false;
        } else if (!Arrays.equals(SelectClauses, other.SelectClauses)) return false;
        if (WhereClause==null) {
            if (other.WhereClause != null) return false;
        } else if (!WhereClause.equals(other.WhereClause)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((SelectClauses == null) ? 0 : Arrays.hashCode(SelectClauses));
        result = prime * result
                + ((WhereClause == null) ? 0 : WhereClause.hashCode());
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
		return "EventFilter: "+ObjectUtils.printFieldsDeep(this);
	}

}
