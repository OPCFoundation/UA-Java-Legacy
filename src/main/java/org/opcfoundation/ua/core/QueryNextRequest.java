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

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.utils.AbstractStructure;


public class QueryNextRequest extends AbstractStructure implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.QueryNextRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.QueryNextRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.QueryNextRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected Boolean ReleaseContinuationPoint;
    protected ByteString ContinuationPoint;
    
    public QueryNextRequest() {}
    
    public QueryNextRequest(RequestHeader RequestHeader, Boolean ReleaseContinuationPoint, ByteString ContinuationPoint)
    {
        this.RequestHeader = RequestHeader;
        this.ReleaseContinuationPoint = ReleaseContinuationPoint;
        this.ContinuationPoint = ContinuationPoint;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public Boolean getReleaseContinuationPoint()
    {
        return ReleaseContinuationPoint;
    }
    
    public void setReleaseContinuationPoint(Boolean ReleaseContinuationPoint)
    {
        this.ReleaseContinuationPoint = ReleaseContinuationPoint;
    }
    
    public ByteString getContinuationPoint()
    {
        return ContinuationPoint;
    }
    
    public void setContinuationPoint(ByteString ContinuationPoint)
    {
        this.ContinuationPoint = ContinuationPoint;
    }
    
    /**
      * Deep clone
      *
      * @return cloned QueryNextRequest
      */
    public QueryNextRequest clone()
    {
        QueryNextRequest result = (QueryNextRequest) super.clone();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.ReleaseContinuationPoint = ReleaseContinuationPoint;
        result.ContinuationPoint = ContinuationPoint;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        QueryNextRequest other = (QueryNextRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (ReleaseContinuationPoint==null) {
            if (other.ReleaseContinuationPoint != null) return false;
        } else if (!ReleaseContinuationPoint.equals(other.ReleaseContinuationPoint)) return false;
        if (ContinuationPoint==null) {
            if (other.ContinuationPoint != null) return false;
        } else if (!ContinuationPoint.equals(other.ContinuationPoint)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((RequestHeader == null) ? 0 : RequestHeader.hashCode());
        result = prime * result
                + ((ReleaseContinuationPoint == null) ? 0 : ReleaseContinuationPoint.hashCode());
        result = prime * result
                + ((ContinuationPoint == null) ? 0 : ContinuationPoint.hashCode());
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
		return ObjectUtils.printFieldsDeep(this);
	}
	
}
