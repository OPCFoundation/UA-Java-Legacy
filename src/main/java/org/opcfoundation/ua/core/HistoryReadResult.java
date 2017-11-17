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
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.utils.AbstractStructure;



public class HistoryReadResult extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.HistoryReadResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.HistoryReadResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.HistoryReadResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected ByteString ContinuationPoint;
    protected ExtensionObject HistoryData;
    
    public HistoryReadResult() {}
    
    public HistoryReadResult(StatusCode StatusCode, ByteString ContinuationPoint, ExtensionObject HistoryData)
    {
        this.StatusCode = StatusCode;
        this.ContinuationPoint = ContinuationPoint;
        this.HistoryData = HistoryData;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public ByteString getContinuationPoint()
    {
        return ContinuationPoint;
    }
    
    public void setContinuationPoint(ByteString ContinuationPoint)
    {
        this.ContinuationPoint = ContinuationPoint;
    }
    
    public ExtensionObject getHistoryData()
    {
        return HistoryData;
    }
    
    public void setHistoryData(ExtensionObject HistoryData)
    {
        this.HistoryData = HistoryData;
    }
    
    /**
      * Deep clone
      *
      * @return cloned HistoryReadResult
      */
    public HistoryReadResult clone()
    {
        HistoryReadResult result = (HistoryReadResult) super.clone();
        result.StatusCode = StatusCode;
        result.ContinuationPoint = ContinuationPoint;
        result.HistoryData = HistoryData;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        HistoryReadResult other = (HistoryReadResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (ContinuationPoint==null) {
            if (other.ContinuationPoint != null) return false;
        } else if (!ContinuationPoint.equals(other.ContinuationPoint)) return false;
        if (HistoryData==null) {
            if (other.HistoryData != null) return false;
        } else if (!HistoryData.equals(other.HistoryData)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((StatusCode == null) ? 0 : StatusCode.hashCode());
        result = prime * result
                + ((ContinuationPoint == null) ? 0 : ContinuationPoint.hashCode());
        result = prime * result
                + ((HistoryData == null) ? 0 : HistoryData.hashCode());
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
		return "HistoryReadResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
