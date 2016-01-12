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
import org.opcfoundation.ua.core.DeleteReferencesItem;
import org.opcfoundation.ua.core.RequestHeader;


public class DeleteReferencesRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DeleteReferencesRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DeleteReferencesRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DeleteReferencesRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected DeleteReferencesItem[] ReferencesToDelete;
    
    public DeleteReferencesRequest() {}
    
    public DeleteReferencesRequest(RequestHeader RequestHeader, DeleteReferencesItem[] ReferencesToDelete)
    {
        this.RequestHeader = RequestHeader;
        this.ReferencesToDelete = ReferencesToDelete;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public DeleteReferencesItem[] getReferencesToDelete()
    {
        return ReferencesToDelete;
    }
    
    public void setReferencesToDelete(DeleteReferencesItem[] ReferencesToDelete)
    {
        this.ReferencesToDelete = ReferencesToDelete;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DeleteReferencesRequest
      */
    public DeleteReferencesRequest clone()
    {
        DeleteReferencesRequest result = new DeleteReferencesRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        if (ReferencesToDelete!=null) {
            result.ReferencesToDelete = new DeleteReferencesItem[ReferencesToDelete.length];
            for (int i=0; i<ReferencesToDelete.length; i++)
                result.ReferencesToDelete[i] = ReferencesToDelete[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DeleteReferencesRequest other = (DeleteReferencesRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (ReferencesToDelete==null) {
            if (other.ReferencesToDelete != null) return false;
        } else if (!Arrays.equals(ReferencesToDelete, other.ReferencesToDelete)) return false;
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
                + ((ReferencesToDelete == null) ? 0 : Arrays.hashCode(ReferencesToDelete));
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
