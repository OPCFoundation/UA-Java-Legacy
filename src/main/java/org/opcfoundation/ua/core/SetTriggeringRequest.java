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
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.utils.AbstractStructure;


public class SetTriggeringRequest extends AbstractStructure implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SetTriggeringRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SetTriggeringRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SetTriggeringRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected UnsignedInteger SubscriptionId;
    protected UnsignedInteger TriggeringItemId;
    protected UnsignedInteger[] LinksToAdd;
    protected UnsignedInteger[] LinksToRemove;
    
    public SetTriggeringRequest() {}
    
    public SetTriggeringRequest(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, UnsignedInteger TriggeringItemId, UnsignedInteger[] LinksToAdd, UnsignedInteger[] LinksToRemove)
    {
        this.RequestHeader = RequestHeader;
        this.SubscriptionId = SubscriptionId;
        this.TriggeringItemId = TriggeringItemId;
        this.LinksToAdd = LinksToAdd;
        this.LinksToRemove = LinksToRemove;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public UnsignedInteger getSubscriptionId()
    {
        return SubscriptionId;
    }
    
    public void setSubscriptionId(UnsignedInteger SubscriptionId)
    {
        this.SubscriptionId = SubscriptionId;
    }
    
    public UnsignedInteger getTriggeringItemId()
    {
        return TriggeringItemId;
    }
    
    public void setTriggeringItemId(UnsignedInteger TriggeringItemId)
    {
        this.TriggeringItemId = TriggeringItemId;
    }
    
    public UnsignedInteger[] getLinksToAdd()
    {
        return LinksToAdd;
    }
    
    public void setLinksToAdd(UnsignedInteger[] LinksToAdd)
    {
        this.LinksToAdd = LinksToAdd;
    }
    
    public UnsignedInteger[] getLinksToRemove()
    {
        return LinksToRemove;
    }
    
    public void setLinksToRemove(UnsignedInteger[] LinksToRemove)
    {
        this.LinksToRemove = LinksToRemove;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SetTriggeringRequest
      */
    public SetTriggeringRequest clone()
    {
        SetTriggeringRequest result = (SetTriggeringRequest) super.clone();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.SubscriptionId = SubscriptionId;
        result.TriggeringItemId = TriggeringItemId;
        result.LinksToAdd = LinksToAdd==null ? null : LinksToAdd.clone();
        result.LinksToRemove = LinksToRemove==null ? null : LinksToRemove.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SetTriggeringRequest other = (SetTriggeringRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (SubscriptionId==null) {
            if (other.SubscriptionId != null) return false;
        } else if (!SubscriptionId.equals(other.SubscriptionId)) return false;
        if (TriggeringItemId==null) {
            if (other.TriggeringItemId != null) return false;
        } else if (!TriggeringItemId.equals(other.TriggeringItemId)) return false;
        if (LinksToAdd==null) {
            if (other.LinksToAdd != null) return false;
        } else if (!Arrays.equals(LinksToAdd, other.LinksToAdd)) return false;
        if (LinksToRemove==null) {
            if (other.LinksToRemove != null) return false;
        } else if (!Arrays.equals(LinksToRemove, other.LinksToRemove)) return false;
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
                + ((SubscriptionId == null) ? 0 : SubscriptionId.hashCode());
        result = prime * result
                + ((TriggeringItemId == null) ? 0 : TriggeringItemId.hashCode());
        result = prime * result
                + ((LinksToAdd == null) ? 0 : Arrays.hashCode(LinksToAdd));
        result = prime * result
                + ((LinksToRemove == null) ? 0 : Arrays.hashCode(LinksToRemove));
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
