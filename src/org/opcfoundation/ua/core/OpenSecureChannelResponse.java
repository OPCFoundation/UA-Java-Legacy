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

import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.ChannelSecurityToken;
import org.opcfoundation.ua.core.ResponseHeader;


public class OpenSecureChannelResponse extends Object implements ServiceResponse {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.OpenSecureChannelResponse);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.OpenSecureChannelResponse_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.OpenSecureChannelResponse_Encoding_DefaultXml);
	
    protected ResponseHeader ResponseHeader;
    protected UnsignedInteger ServerProtocolVersion;
    protected ChannelSecurityToken SecurityToken;
    protected byte[] ServerNonce;
    
    public OpenSecureChannelResponse() {}
    
    public OpenSecureChannelResponse(ResponseHeader ResponseHeader, UnsignedInteger ServerProtocolVersion, ChannelSecurityToken SecurityToken, byte[] ServerNonce)
    {
        this.ResponseHeader = ResponseHeader;
        this.ServerProtocolVersion = ServerProtocolVersion;
        this.SecurityToken = SecurityToken;
        this.ServerNonce = ServerNonce;
    }
    
    public ResponseHeader getResponseHeader()
    {
        return ResponseHeader;
    }
    
    public void setResponseHeader(ResponseHeader ResponseHeader)
    {
        this.ResponseHeader = ResponseHeader;
    }
    
    public UnsignedInteger getServerProtocolVersion()
    {
        return ServerProtocolVersion;
    }
    
    public void setServerProtocolVersion(UnsignedInteger ServerProtocolVersion)
    {
        this.ServerProtocolVersion = ServerProtocolVersion;
    }
    
    public ChannelSecurityToken getSecurityToken()
    {
        return SecurityToken;
    }
    
    public void setSecurityToken(ChannelSecurityToken SecurityToken)
    {
        this.SecurityToken = SecurityToken;
    }
    
    public byte[] getServerNonce()
    {
        return ServerNonce;
    }
    
    public void setServerNonce(byte[] ServerNonce)
    {
        this.ServerNonce = ServerNonce;
    }
    
    /**
      * Deep clone
      *
      * @return cloned OpenSecureChannelResponse
      */
    public OpenSecureChannelResponse clone()
    {
        OpenSecureChannelResponse result = new OpenSecureChannelResponse();
        result.ResponseHeader = ResponseHeader==null ? null : ResponseHeader.clone();
        result.ServerProtocolVersion = ServerProtocolVersion;
        result.SecurityToken = SecurityToken==null ? null : SecurityToken.clone();
        result.ServerNonce = ServerNonce;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        OpenSecureChannelResponse other = (OpenSecureChannelResponse) obj;
        if (ResponseHeader==null) {
            if (other.ResponseHeader != null) return false;
        } else if (!ResponseHeader.equals(other.ResponseHeader)) return false;
        if (ServerProtocolVersion==null) {
            if (other.ServerProtocolVersion != null) return false;
        } else if (!ServerProtocolVersion.equals(other.ServerProtocolVersion)) return false;
        if (SecurityToken==null) {
            if (other.SecurityToken != null) return false;
        } else if (!SecurityToken.equals(other.SecurityToken)) return false;
        if (ServerNonce==null) {
            if (other.ServerNonce != null) return false;
        } else if (!ServerNonce.equals(other.ServerNonce)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ResponseHeader == null) ? 0 : ResponseHeader.hashCode());
        result = prime * result
                + ((ServerProtocolVersion == null) ? 0 : ServerProtocolVersion.hashCode());
        result = prime * result
                + ((SecurityToken == null) ? 0 : SecurityToken.hashCode());
        result = prime * result
                + ((ServerNonce == null) ? 0 : ServerNonce.hashCode());
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
