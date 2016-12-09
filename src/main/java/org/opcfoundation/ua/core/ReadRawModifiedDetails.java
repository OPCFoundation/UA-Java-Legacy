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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.HistoryReadDetails;



public class ReadRawModifiedDetails extends HistoryReadDetails {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ReadRawModifiedDetails);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ReadRawModifiedDetails_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ReadRawModifiedDetails_Encoding_DefaultXml);
	
    protected Boolean IsReadModified;
    protected DateTime StartTime;
    protected DateTime EndTime;
    protected UnsignedInteger NumValuesPerNode;
    protected Boolean ReturnBounds;
    
    public ReadRawModifiedDetails() {}
    
    public ReadRawModifiedDetails(Boolean IsReadModified, DateTime StartTime, DateTime EndTime, UnsignedInteger NumValuesPerNode, Boolean ReturnBounds)
    {
        this.IsReadModified = IsReadModified;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.NumValuesPerNode = NumValuesPerNode;
        this.ReturnBounds = ReturnBounds;
    }
    
    public Boolean getIsReadModified()
    {
        return IsReadModified;
    }
    
    public void setIsReadModified(Boolean IsReadModified)
    {
        this.IsReadModified = IsReadModified;
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
    
    public UnsignedInteger getNumValuesPerNode()
    {
        return NumValuesPerNode;
    }
    
    public void setNumValuesPerNode(UnsignedInteger NumValuesPerNode)
    {
        this.NumValuesPerNode = NumValuesPerNode;
    }
    
    public Boolean getReturnBounds()
    {
        return ReturnBounds;
    }
    
    public void setReturnBounds(Boolean ReturnBounds)
    {
        this.ReturnBounds = ReturnBounds;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ReadRawModifiedDetails
      */
    public ReadRawModifiedDetails clone()
    {
        ReadRawModifiedDetails result = (ReadRawModifiedDetails) super.clone();
        result.IsReadModified = IsReadModified;
        result.StartTime = StartTime;
        result.EndTime = EndTime;
        result.NumValuesPerNode = NumValuesPerNode;
        result.ReturnBounds = ReturnBounds;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ReadRawModifiedDetails other = (ReadRawModifiedDetails) obj;
        if (IsReadModified==null) {
            if (other.IsReadModified != null) return false;
        } else if (!IsReadModified.equals(other.IsReadModified)) return false;
        if (StartTime==null) {
            if (other.StartTime != null) return false;
        } else if (!StartTime.equals(other.StartTime)) return false;
        if (EndTime==null) {
            if (other.EndTime != null) return false;
        } else if (!EndTime.equals(other.EndTime)) return false;
        if (NumValuesPerNode==null) {
            if (other.NumValuesPerNode != null) return false;
        } else if (!NumValuesPerNode.equals(other.NumValuesPerNode)) return false;
        if (ReturnBounds==null) {
            if (other.ReturnBounds != null) return false;
        } else if (!ReturnBounds.equals(other.ReturnBounds)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((IsReadModified == null) ? 0 : IsReadModified.hashCode());
        result = prime * result
                + ((StartTime == null) ? 0 : StartTime.hashCode());
        result = prime * result
                + ((EndTime == null) ? 0 : EndTime.hashCode());
        result = prime * result
                + ((NumValuesPerNode == null) ? 0 : NumValuesPerNode.hashCode());
        result = prime * result
                + ((ReturnBounds == null) ? 0 : ReturnBounds.hashCode());
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
		return "ReadRawModifiedDetails: "+ObjectUtils.printFieldsDeep(this);
	}

}
