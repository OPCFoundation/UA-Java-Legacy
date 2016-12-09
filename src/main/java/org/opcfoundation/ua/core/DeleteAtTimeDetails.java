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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.HistoryUpdateDetails;



public class DeleteAtTimeDetails extends HistoryUpdateDetails {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DeleteAtTimeDetails);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DeleteAtTimeDetails_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DeleteAtTimeDetails_Encoding_DefaultXml);
	
    protected DateTime[] ReqTimes;
    
    public DeleteAtTimeDetails() {}
    
    public DeleteAtTimeDetails(NodeId NodeId, DateTime[] ReqTimes)
    {
        super(NodeId);
        this.ReqTimes = ReqTimes;
    }
    
    public DateTime[] getReqTimes()
    {
        return ReqTimes;
    }
    
    public void setReqTimes(DateTime[] ReqTimes)
    {
        this.ReqTimes = ReqTimes;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DeleteAtTimeDetails
      */
    public DeleteAtTimeDetails clone()
    {
        DeleteAtTimeDetails result = (DeleteAtTimeDetails) super.clone();
        result.NodeId = NodeId;
        result.ReqTimes = ReqTimes==null ? null : ReqTimes.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DeleteAtTimeDetails other = (DeleteAtTimeDetails) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (ReqTimes==null) {
            if (other.ReqTimes != null) return false;
        } else if (!Arrays.equals(ReqTimes, other.ReqTimes)) return false;
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
                + ((ReqTimes == null) ? 0 : Arrays.hashCode(ReqTimes));
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
		return "DeleteAtTimeDetails: "+ObjectUtils.printFieldsDeep(this);
	}

}
