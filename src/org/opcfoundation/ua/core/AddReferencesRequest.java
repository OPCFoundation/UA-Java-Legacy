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
import org.opcfoundation.ua.core.AddReferencesItem;
import org.opcfoundation.ua.core.RequestHeader;


public class AddReferencesRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.AddReferencesRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.AddReferencesRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.AddReferencesRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected AddReferencesItem[] ReferencesToAdd;
    
    public AddReferencesRequest() {}
    
    public AddReferencesRequest(RequestHeader RequestHeader, AddReferencesItem[] ReferencesToAdd)
    {
        this.RequestHeader = RequestHeader;
        this.ReferencesToAdd = ReferencesToAdd;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public AddReferencesItem[] getReferencesToAdd()
    {
        return ReferencesToAdd;
    }
    
    public void setReferencesToAdd(AddReferencesItem[] ReferencesToAdd)
    {
        this.ReferencesToAdd = ReferencesToAdd;
    }
    
    /**
      * Deep clone
      *
      * @return cloned AddReferencesRequest
      */
    public AddReferencesRequest clone()
    {
        AddReferencesRequest result = new AddReferencesRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        if (ReferencesToAdd!=null) {
            result.ReferencesToAdd = new AddReferencesItem[ReferencesToAdd.length];
            for (int i=0; i<ReferencesToAdd.length; i++)
                result.ReferencesToAdd[i] = ReferencesToAdd[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AddReferencesRequest other = (AddReferencesRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (ReferencesToAdd==null) {
            if (other.ReferencesToAdd != null) return false;
        } else if (!Arrays.equals(ReferencesToAdd, other.ReferencesToAdd)) return false;
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
                + ((ReferencesToAdd == null) ? 0 : Arrays.hashCode(ReferencesToAdd));
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
