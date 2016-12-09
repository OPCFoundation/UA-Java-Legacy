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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.HistoryUpdateDetails;



public class DeleteRawModifiedDetails extends HistoryUpdateDetails {
	
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
        DeleteRawModifiedDetails result = (DeleteRawModifiedDetails) super.clone();
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
