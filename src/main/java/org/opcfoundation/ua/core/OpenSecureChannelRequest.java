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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.SecurityTokenRequestType;
import org.opcfoundation.ua.utils.AbstractStructure;


public class OpenSecureChannelRequest extends AbstractStructure implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.OpenSecureChannelRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.OpenSecureChannelRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.OpenSecureChannelRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected UnsignedInteger ClientProtocolVersion;
    protected SecurityTokenRequestType RequestType;
    protected MessageSecurityMode SecurityMode;
    protected ByteString ClientNonce;
    protected UnsignedInteger RequestedLifetime;
    
    public OpenSecureChannelRequest() {}
    
    public OpenSecureChannelRequest(RequestHeader RequestHeader, UnsignedInteger ClientProtocolVersion, SecurityTokenRequestType RequestType, MessageSecurityMode SecurityMode, ByteString ClientNonce, UnsignedInteger RequestedLifetime)
    {
        this.RequestHeader = RequestHeader;
        this.ClientProtocolVersion = ClientProtocolVersion;
        this.RequestType = RequestType;
        this.SecurityMode = SecurityMode;
        this.ClientNonce = ClientNonce;
        this.RequestedLifetime = RequestedLifetime;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public UnsignedInteger getClientProtocolVersion()
    {
        return ClientProtocolVersion;
    }
    
    public void setClientProtocolVersion(UnsignedInteger ClientProtocolVersion)
    {
        this.ClientProtocolVersion = ClientProtocolVersion;
    }
    
    public SecurityTokenRequestType getRequestType()
    {
        return RequestType;
    }
    
    public void setRequestType(SecurityTokenRequestType RequestType)
    {
        this.RequestType = RequestType;
    }
    
    public MessageSecurityMode getSecurityMode()
    {
        return SecurityMode;
    }
    
    public void setSecurityMode(MessageSecurityMode SecurityMode)
    {
        this.SecurityMode = SecurityMode;
    }
    
    public ByteString getClientNonce()
    {
        return ClientNonce;
    }
    
    public void setClientNonce(ByteString ClientNonce)
    {
        this.ClientNonce = ClientNonce;
    }
    
    public UnsignedInteger getRequestedLifetime()
    {
        return RequestedLifetime;
    }
    
    public void setRequestedLifetime(UnsignedInteger RequestedLifetime)
    {
        this.RequestedLifetime = RequestedLifetime;
    }
    
    /**
      * Deep clone
      *
      * @return cloned OpenSecureChannelRequest
      */
    public OpenSecureChannelRequest clone()
    {
        OpenSecureChannelRequest result = (OpenSecureChannelRequest) super.clone();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.ClientProtocolVersion = ClientProtocolVersion;
        result.RequestType = RequestType;
        result.SecurityMode = SecurityMode;
        result.ClientNonce = ClientNonce;
        result.RequestedLifetime = RequestedLifetime;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        OpenSecureChannelRequest other = (OpenSecureChannelRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (ClientProtocolVersion==null) {
            if (other.ClientProtocolVersion != null) return false;
        } else if (!ClientProtocolVersion.equals(other.ClientProtocolVersion)) return false;
        if (RequestType==null) {
            if (other.RequestType != null) return false;
        } else if (!RequestType.equals(other.RequestType)) return false;
        if (SecurityMode==null) {
            if (other.SecurityMode != null) return false;
        } else if (!SecurityMode.equals(other.SecurityMode)) return false;
        if (ClientNonce==null) {
            if (other.ClientNonce != null) return false;
        } else if (!ClientNonce.equals(other.ClientNonce)) return false;
        if (RequestedLifetime==null) {
            if (other.RequestedLifetime != null) return false;
        } else if (!RequestedLifetime.equals(other.RequestedLifetime)) return false;
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
                + ((ClientProtocolVersion == null) ? 0 : ClientProtocolVersion.hashCode());
        result = prime * result
                + ((RequestType == null) ? 0 : RequestType.hashCode());
        result = prime * result
                + ((SecurityMode == null) ? 0 : SecurityMode.hashCode());
        result = prime * result
                + ((ClientNonce == null) ? 0 : ClientNonce.hashCode());
        result = prime * result
                + ((RequestedLifetime == null) ? 0 : RequestedLifetime.hashCode());
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
