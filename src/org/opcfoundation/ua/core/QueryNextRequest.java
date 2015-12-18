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

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.core.RequestHeader;


public class QueryNextRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.QueryNextRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.QueryNextRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.QueryNextRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected Boolean ReleaseContinuationPoint;
    protected byte[] ContinuationPoint;
    
    public QueryNextRequest() {}
    
    public QueryNextRequest(RequestHeader RequestHeader, Boolean ReleaseContinuationPoint, byte[] ContinuationPoint)
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
    
    public byte[] getContinuationPoint()
    {
        return ContinuationPoint;
    }
    
    public void setContinuationPoint(byte[] ContinuationPoint)
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
        QueryNextRequest result = new QueryNextRequest();
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
