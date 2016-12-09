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
import org.opcfoundation.ua.core.HistoryEventFieldList;
import org.opcfoundation.ua.utils.AbstractStructure;



public class HistoryEvent extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.HistoryEvent);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.HistoryEvent_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.HistoryEvent_Encoding_DefaultXml);
	
    protected HistoryEventFieldList[] Events;
    
    public HistoryEvent() {}
    
    public HistoryEvent(HistoryEventFieldList[] Events)
    {
        this.Events = Events;
    }
    
    public HistoryEventFieldList[] getEvents()
    {
        return Events;
    }
    
    public void setEvents(HistoryEventFieldList[] Events)
    {
        this.Events = Events;
    }
    
    /**
      * Deep clone
      *
      * @return cloned HistoryEvent
      */
    public HistoryEvent clone()
    {
        HistoryEvent result = (HistoryEvent) super.clone();
        if (Events!=null) {
            result.Events = new HistoryEventFieldList[Events.length];
            for (int i=0; i<Events.length; i++)
                result.Events[i] = Events[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        HistoryEvent other = (HistoryEvent) obj;
        if (Events==null) {
            if (other.Events != null) return false;
        } else if (!Arrays.equals(Events, other.Events)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Events == null) ? 0 : Arrays.hashCode(Events));
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
		return "HistoryEvent: "+ObjectUtils.printFieldsDeep(this);
	}

}
