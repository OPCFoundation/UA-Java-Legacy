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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.SecurityTokenRequestType;


public class OpenSecureChannelRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.OpenSecureChannelRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.OpenSecureChannelRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.OpenSecureChannelRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected UnsignedInteger ClientProtocolVersion;
    protected SecurityTokenRequestType RequestType;
    protected MessageSecurityMode SecurityMode;
    protected byte[] ClientNonce;
    protected UnsignedInteger RequestedLifetime;
    
    public OpenSecureChannelRequest() {}
    
    public OpenSecureChannelRequest(RequestHeader RequestHeader, UnsignedInteger ClientProtocolVersion, SecurityTokenRequestType RequestType, MessageSecurityMode SecurityMode, byte[] ClientNonce, UnsignedInteger RequestedLifetime)
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
    
    public byte[] getClientNonce()
    {
        return ClientNonce;
    }
    
    public void setClientNonce(byte[] ClientNonce)
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
        OpenSecureChannelRequest result = new OpenSecureChannelRequest();
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
