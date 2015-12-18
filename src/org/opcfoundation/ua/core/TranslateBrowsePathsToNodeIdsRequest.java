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
import org.opcfoundation.ua.core.BrowsePath;
import org.opcfoundation.ua.core.RequestHeader;


public class TranslateBrowsePathsToNodeIdsRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.TranslateBrowsePathsToNodeIdsRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.TranslateBrowsePathsToNodeIdsRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.TranslateBrowsePathsToNodeIdsRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected BrowsePath[] BrowsePaths;
    
    public TranslateBrowsePathsToNodeIdsRequest() {}
    
    public TranslateBrowsePathsToNodeIdsRequest(RequestHeader RequestHeader, BrowsePath[] BrowsePaths)
    {
        this.RequestHeader = RequestHeader;
        this.BrowsePaths = BrowsePaths;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public BrowsePath[] getBrowsePaths()
    {
        return BrowsePaths;
    }
    
    public void setBrowsePaths(BrowsePath[] BrowsePaths)
    {
        this.BrowsePaths = BrowsePaths;
    }
    
    /**
      * Deep clone
      *
      * @return cloned TranslateBrowsePathsToNodeIdsRequest
      */
    public TranslateBrowsePathsToNodeIdsRequest clone()
    {
        TranslateBrowsePathsToNodeIdsRequest result = new TranslateBrowsePathsToNodeIdsRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        if (BrowsePaths!=null) {
            result.BrowsePaths = new BrowsePath[BrowsePaths.length];
            for (int i=0; i<BrowsePaths.length; i++)
                result.BrowsePaths[i] = BrowsePaths[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TranslateBrowsePathsToNodeIdsRequest other = (TranslateBrowsePathsToNodeIdsRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (BrowsePaths==null) {
            if (other.BrowsePaths != null) return false;
        } else if (!Arrays.equals(BrowsePaths, other.BrowsePaths)) return false;
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
                + ((BrowsePaths == null) ? 0 : Arrays.hashCode(BrowsePaths));
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
