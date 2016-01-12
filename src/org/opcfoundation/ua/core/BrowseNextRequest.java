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
import java.util.Arrays;
import org.opcfoundation.ua.core.RequestHeader;


public class BrowseNextRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.BrowseNextRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.BrowseNextRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.BrowseNextRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected Boolean ReleaseContinuationPoints;
    protected byte[][] ContinuationPoints;
    
    public BrowseNextRequest() {}
    
    public BrowseNextRequest(RequestHeader RequestHeader, Boolean ReleaseContinuationPoints, byte[][] ContinuationPoints)
    {
        this.RequestHeader = RequestHeader;
        this.ReleaseContinuationPoints = ReleaseContinuationPoints;
        this.ContinuationPoints = ContinuationPoints;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public Boolean getReleaseContinuationPoints()
    {
        return ReleaseContinuationPoints;
    }
    
    public void setReleaseContinuationPoints(Boolean ReleaseContinuationPoints)
    {
        this.ReleaseContinuationPoints = ReleaseContinuationPoints;
    }
    
    public byte[][] getContinuationPoints()
    {
        return ContinuationPoints;
    }
    
    public void setContinuationPoints(byte[][] ContinuationPoints)
    {
        this.ContinuationPoints = ContinuationPoints;
    }
    
    /**
      * Deep clone
      *
      * @return cloned BrowseNextRequest
      */
    public BrowseNextRequest clone()
    {
        BrowseNextRequest result = new BrowseNextRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.ReleaseContinuationPoints = ReleaseContinuationPoints;
        result.ContinuationPoints = ContinuationPoints==null ? null : ContinuationPoints.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BrowseNextRequest other = (BrowseNextRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (ReleaseContinuationPoints==null) {
            if (other.ReleaseContinuationPoints != null) return false;
        } else if (!ReleaseContinuationPoints.equals(other.ReleaseContinuationPoints)) return false;
        if (ContinuationPoints==null) {
            if (other.ContinuationPoints != null) return false;
        } else if (!Arrays.equals(ContinuationPoints, other.ContinuationPoints)) return false;
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
                + ((ReleaseContinuationPoints == null) ? 0 : ReleaseContinuationPoints.hashCode());
        result = prime * result
                + ((ContinuationPoints == null) ? 0 : Arrays.hashCode(ContinuationPoints));
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
