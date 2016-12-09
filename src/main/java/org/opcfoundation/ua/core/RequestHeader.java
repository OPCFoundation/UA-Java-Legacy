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
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class RequestHeader extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.RequestHeader);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.RequestHeader_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.RequestHeader_Encoding_DefaultXml);
	
    protected NodeId AuthenticationToken;
    protected DateTime Timestamp;
    protected UnsignedInteger RequestHandle;
    protected UnsignedInteger ReturnDiagnostics;
    protected String AuditEntryId;
    protected UnsignedInteger TimeoutHint;
    protected ExtensionObject AdditionalHeader;
    
    public RequestHeader() {}
    
    public RequestHeader(NodeId AuthenticationToken, DateTime Timestamp, UnsignedInteger RequestHandle, UnsignedInteger ReturnDiagnostics, String AuditEntryId, UnsignedInteger TimeoutHint, ExtensionObject AdditionalHeader)
    {
        this.AuthenticationToken = AuthenticationToken;
        this.Timestamp = Timestamp;
        this.RequestHandle = RequestHandle;
        this.ReturnDiagnostics = ReturnDiagnostics;
        this.AuditEntryId = AuditEntryId;
        this.TimeoutHint = TimeoutHint;
        this.AdditionalHeader = AdditionalHeader;
    }
    
    public NodeId getAuthenticationToken()
    {
        return AuthenticationToken;
    }
    
    public void setAuthenticationToken(NodeId AuthenticationToken)
    {
        this.AuthenticationToken = AuthenticationToken;
    }
    
    public DateTime getTimestamp()
    {
        return Timestamp;
    }
    
    public void setTimestamp(DateTime Timestamp)
    {
        this.Timestamp = Timestamp;
    }
    
    public UnsignedInteger getRequestHandle()
    {
        return RequestHandle;
    }
    
    public void setRequestHandle(UnsignedInteger RequestHandle)
    {
        this.RequestHandle = RequestHandle;
    }
    
    public UnsignedInteger getReturnDiagnostics()
    {
        return ReturnDiagnostics;
    }
    
    public void setReturnDiagnostics(UnsignedInteger ReturnDiagnostics)
    {
        this.ReturnDiagnostics = ReturnDiagnostics;
    }
    
    public String getAuditEntryId()
    {
        return AuditEntryId;
    }
    
    public void setAuditEntryId(String AuditEntryId)
    {
        this.AuditEntryId = AuditEntryId;
    }
    
    public UnsignedInteger getTimeoutHint()
    {
        return TimeoutHint;
    }
    
    public void setTimeoutHint(UnsignedInteger TimeoutHint)
    {
        this.TimeoutHint = TimeoutHint;
    }
    
    public ExtensionObject getAdditionalHeader()
    {
        return AdditionalHeader;
    }
    
    public void setAdditionalHeader(ExtensionObject AdditionalHeader)
    {
        this.AdditionalHeader = AdditionalHeader;
    }
    
    /**
      * Deep clone
      *
      * @return cloned RequestHeader
      */
    public RequestHeader clone()
    {
        RequestHeader result = (RequestHeader) super.clone();
        result.AuthenticationToken = AuthenticationToken;
        result.Timestamp = Timestamp;
        result.RequestHandle = RequestHandle;
        result.ReturnDiagnostics = ReturnDiagnostics;
        result.AuditEntryId = AuditEntryId;
        result.TimeoutHint = TimeoutHint;
        result.AdditionalHeader = AdditionalHeader;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RequestHeader other = (RequestHeader) obj;
        if (AuthenticationToken==null) {
            if (other.AuthenticationToken != null) return false;
        } else if (!AuthenticationToken.equals(other.AuthenticationToken)) return false;
        if (Timestamp==null) {
            if (other.Timestamp != null) return false;
        } else if (!Timestamp.equals(other.Timestamp)) return false;
        if (RequestHandle==null) {
            if (other.RequestHandle != null) return false;
        } else if (!RequestHandle.equals(other.RequestHandle)) return false;
        if (ReturnDiagnostics==null) {
            if (other.ReturnDiagnostics != null) return false;
        } else if (!ReturnDiagnostics.equals(other.ReturnDiagnostics)) return false;
        if (AuditEntryId==null) {
            if (other.AuditEntryId != null) return false;
        } else if (!AuditEntryId.equals(other.AuditEntryId)) return false;
        if (TimeoutHint==null) {
            if (other.TimeoutHint != null) return false;
        } else if (!TimeoutHint.equals(other.TimeoutHint)) return false;
        if (AdditionalHeader==null) {
            if (other.AdditionalHeader != null) return false;
        } else if (!AdditionalHeader.equals(other.AdditionalHeader)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((AuthenticationToken == null) ? 0 : AuthenticationToken.hashCode());
        result = prime * result
                + ((Timestamp == null) ? 0 : Timestamp.hashCode());
        result = prime * result
                + ((RequestHandle == null) ? 0 : RequestHandle.hashCode());
        result = prime * result
                + ((ReturnDiagnostics == null) ? 0 : ReturnDiagnostics.hashCode());
        result = prime * result
                + ((AuditEntryId == null) ? 0 : AuditEntryId.hashCode());
        result = prime * result
                + ((TimeoutHint == null) ? 0 : TimeoutHint.hashCode());
        result = prime * result
                + ((AdditionalHeader == null) ? 0 : AdditionalHeader.hashCode());
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
		return "RequestHeader: "+ObjectUtils.printFieldsDeep(this);
	}

}
