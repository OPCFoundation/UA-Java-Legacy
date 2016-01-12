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
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.RequestHeader;


public class CreateSessionRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.CreateSessionRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.CreateSessionRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.CreateSessionRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected ApplicationDescription ClientDescription;
    protected String ServerUri;
    protected String EndpointUrl;
    protected String SessionName;
    protected byte[] ClientNonce;
    protected byte[] ClientCertificate;
    protected Double RequestedSessionTimeout;
    protected UnsignedInteger MaxResponseMessageSize;
    
    public CreateSessionRequest() {}
    
    public CreateSessionRequest(RequestHeader RequestHeader, ApplicationDescription ClientDescription, String ServerUri, String EndpointUrl, String SessionName, byte[] ClientNonce, byte[] ClientCertificate, Double RequestedSessionTimeout, UnsignedInteger MaxResponseMessageSize)
    {
        this.RequestHeader = RequestHeader;
        this.ClientDescription = ClientDescription;
        this.ServerUri = ServerUri;
        this.EndpointUrl = EndpointUrl;
        this.SessionName = SessionName;
        this.ClientNonce = ClientNonce;
        this.ClientCertificate = ClientCertificate;
        this.RequestedSessionTimeout = RequestedSessionTimeout;
        this.MaxResponseMessageSize = MaxResponseMessageSize;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public ApplicationDescription getClientDescription()
    {
        return ClientDescription;
    }
    
    public void setClientDescription(ApplicationDescription ClientDescription)
    {
        this.ClientDescription = ClientDescription;
    }
    
    public String getServerUri()
    {
        return ServerUri;
    }
    
    public void setServerUri(String ServerUri)
    {
        this.ServerUri = ServerUri;
    }
    
    public String getEndpointUrl()
    {
        return EndpointUrl;
    }
    
    public void setEndpointUrl(String EndpointUrl)
    {
        this.EndpointUrl = EndpointUrl;
    }
    
    public String getSessionName()
    {
        return SessionName;
    }
    
    public void setSessionName(String SessionName)
    {
        this.SessionName = SessionName;
    }
    
    public byte[] getClientNonce()
    {
        return ClientNonce;
    }
    
    public void setClientNonce(byte[] ClientNonce)
    {
        this.ClientNonce = ClientNonce;
    }
    
    public byte[] getClientCertificate()
    {
        return ClientCertificate;
    }
    
    public void setClientCertificate(byte[] ClientCertificate)
    {
        this.ClientCertificate = ClientCertificate;
    }
    
    public Double getRequestedSessionTimeout()
    {
        return RequestedSessionTimeout;
    }
    
    public void setRequestedSessionTimeout(Double RequestedSessionTimeout)
    {
        this.RequestedSessionTimeout = RequestedSessionTimeout;
    }
    
    public UnsignedInteger getMaxResponseMessageSize()
    {
        return MaxResponseMessageSize;
    }
    
    public void setMaxResponseMessageSize(UnsignedInteger MaxResponseMessageSize)
    {
        this.MaxResponseMessageSize = MaxResponseMessageSize;
    }
    
    /**
      * Deep clone
      *
      * @return cloned CreateSessionRequest
      */
    public CreateSessionRequest clone()
    {
        CreateSessionRequest result = new CreateSessionRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.ClientDescription = ClientDescription==null ? null : ClientDescription.clone();
        result.ServerUri = ServerUri;
        result.EndpointUrl = EndpointUrl;
        result.SessionName = SessionName;
        result.ClientNonce = ClientNonce;
        result.ClientCertificate = ClientCertificate;
        result.RequestedSessionTimeout = RequestedSessionTimeout;
        result.MaxResponseMessageSize = MaxResponseMessageSize;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CreateSessionRequest other = (CreateSessionRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (ClientDescription==null) {
            if (other.ClientDescription != null) return false;
        } else if (!ClientDescription.equals(other.ClientDescription)) return false;
        if (ServerUri==null) {
            if (other.ServerUri != null) return false;
        } else if (!ServerUri.equals(other.ServerUri)) return false;
        if (EndpointUrl==null) {
            if (other.EndpointUrl != null) return false;
        } else if (!EndpointUrl.equals(other.EndpointUrl)) return false;
        if (SessionName==null) {
            if (other.SessionName != null) return false;
        } else if (!SessionName.equals(other.SessionName)) return false;
        if (ClientNonce==null) {
            if (other.ClientNonce != null) return false;
        } else if (!ClientNonce.equals(other.ClientNonce)) return false;
        if (ClientCertificate==null) {
            if (other.ClientCertificate != null) return false;
        } else if (!ClientCertificate.equals(other.ClientCertificate)) return false;
        if (RequestedSessionTimeout==null) {
            if (other.RequestedSessionTimeout != null) return false;
        } else if (!RequestedSessionTimeout.equals(other.RequestedSessionTimeout)) return false;
        if (MaxResponseMessageSize==null) {
            if (other.MaxResponseMessageSize != null) return false;
        } else if (!MaxResponseMessageSize.equals(other.MaxResponseMessageSize)) return false;
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
                + ((ClientDescription == null) ? 0 : ClientDescription.hashCode());
        result = prime * result
                + ((ServerUri == null) ? 0 : ServerUri.hashCode());
        result = prime * result
                + ((EndpointUrl == null) ? 0 : EndpointUrl.hashCode());
        result = prime * result
                + ((SessionName == null) ? 0 : SessionName.hashCode());
        result = prime * result
                + ((ClientNonce == null) ? 0 : ClientNonce.hashCode());
        result = prime * result
                + ((ClientCertificate == null) ? 0 : ClientCertificate.hashCode());
        result = prime * result
                + ((RequestedSessionTimeout == null) ? 0 : RequestedSessionTimeout.hashCode());
        result = prime * result
                + ((MaxResponseMessageSize == null) ? 0 : MaxResponseMessageSize.hashCode());
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
