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
import org.opcfoundation.ua.common.NamespaceTable;

import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.ContentFilter;
import org.opcfoundation.ua.core.PublishedDataSetSourceDataType;
import org.opcfoundation.ua.core.SimpleAttributeOperand;



public class PublishedEventsDataType extends PublishedDataSetSourceDataType {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.PublishedEventsDataType.getValue());
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.PublishedEventsDataType_Encoding_DefaultBinary.getValue());
	public static final ExpandedNodeId XML = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.PublishedEventsDataType_Encoding_DefaultXml.getValue());
	
    protected NodeId EventNotifier;
    protected SimpleAttributeOperand[] SelectedFields;
    protected ContentFilter Filter;
    
    public PublishedEventsDataType() {}
    
    public PublishedEventsDataType(NodeId EventNotifier, SimpleAttributeOperand[] SelectedFields, ContentFilter Filter)
    {
        this.EventNotifier = EventNotifier;
        this.SelectedFields = SelectedFields;
        this.Filter = Filter;
    }
    
    public NodeId getEventNotifier()
    {
        return EventNotifier;
    }
    
    public void setEventNotifier(NodeId EventNotifier)
    {
        this.EventNotifier = EventNotifier;
    }
    
    public SimpleAttributeOperand[] getSelectedFields()
    {
        return SelectedFields;
    }
    
    public void setSelectedFields(SimpleAttributeOperand[] SelectedFields)
    {
        this.SelectedFields = SelectedFields;
    }
    
    public ContentFilter getFilter()
    {
        return Filter;
    }
    
    public void setFilter(ContentFilter Filter)
    {
        this.Filter = Filter;
    }
    
    /**
      * Deep clone
      *
      * @return cloned PublishedEventsDataType
      */
    public PublishedEventsDataType clone()
    {
        PublishedEventsDataType result = (PublishedEventsDataType) super.clone();
        result.EventNotifier = EventNotifier;
        if (SelectedFields!=null) {
            result.SelectedFields = new SimpleAttributeOperand[SelectedFields.length];
            for (int i=0; i<SelectedFields.length; i++)
                result.SelectedFields[i] = SelectedFields[i].clone();
        }
        result.Filter = Filter==null ? null : Filter.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PublishedEventsDataType other = (PublishedEventsDataType) obj;
        if (EventNotifier==null) {
            if (other.EventNotifier != null) return false;
        } else if (!EventNotifier.equals(other.EventNotifier)) return false;
        if (SelectedFields==null) {
            if (other.SelectedFields != null) return false;
        } else if (!Arrays.equals(SelectedFields, other.SelectedFields)) return false;
        if (Filter==null) {
            if (other.Filter != null) return false;
        } else if (!Filter.equals(other.Filter)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((EventNotifier == null) ? 0 : EventNotifier.hashCode());
        result = prime * result
                + ((SelectedFields == null) ? 0 : Arrays.hashCode(SelectedFields));
        result = prime * result
                + ((Filter == null) ? 0 : Filter.hashCode());
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
		return "PublishedEventsDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
