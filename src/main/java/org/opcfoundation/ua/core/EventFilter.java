/* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.core;

import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.core.ContentFilter;
import org.opcfoundation.ua.core.MonitoringFilter;
import org.opcfoundation.ua.core.SimpleAttributeOperand;



public class EventFilter extends MonitoringFilter {
	
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
        EventFilter result = (EventFilter) super.clone();
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
