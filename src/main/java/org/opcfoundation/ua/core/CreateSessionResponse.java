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

import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.ResponseHeader;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.SignedSoftwareCertificate;
import org.opcfoundation.ua.utils.AbstractStructure;


public class CreateSessionResponse extends AbstractStructure implements ServiceResponse {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.CreateSessionResponse);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.CreateSessionResponse_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.CreateSessionResponse_Encoding_DefaultXml);
	
    protected ResponseHeader ResponseHeader;
    protected NodeId SessionId;
    protected NodeId AuthenticationToken;
    protected Double RevisedSessionTimeout;
    protected ByteString ServerNonce;
    protected ByteString ServerCertificate;
    protected EndpointDescription[] ServerEndpoints;
    protected SignedSoftwareCertificate[] ServerSoftwareCertificates;
    protected SignatureData ServerSignature;
    protected UnsignedInteger MaxRequestMessageSize;
    
    public CreateSessionResponse() {}
    
    public CreateSessionResponse(ResponseHeader ResponseHeader, NodeId SessionId, NodeId AuthenticationToken, Double RevisedSessionTimeout, ByteString ServerNonce, ByteString ServerCertificate, EndpointDescription[] ServerEndpoints, SignedSoftwareCertificate[] ServerSoftwareCertificates, SignatureData ServerSignature, UnsignedInteger MaxRequestMessageSize)
    {
        this.ResponseHeader = ResponseHeader;
        this.SessionId = SessionId;
        this.AuthenticationToken = AuthenticationToken;
        this.RevisedSessionTimeout = RevisedSessionTimeout;
        this.ServerNonce = ServerNonce;
        this.ServerCertificate = ServerCertificate;
        this.ServerEndpoints = ServerEndpoints;
        this.ServerSoftwareCertificates = ServerSoftwareCertificates;
        this.ServerSignature = ServerSignature;
        this.MaxRequestMessageSize = MaxRequestMessageSize;
    }
    
    public ResponseHeader getResponseHeader()
    {
        return ResponseHeader;
    }
    
    public void setResponseHeader(ResponseHeader ResponseHeader)
    {
        this.ResponseHeader = ResponseHeader;
    }
    
    public NodeId getSessionId()
    {
        return SessionId;
    }
    
    public void setSessionId(NodeId SessionId)
    {
        this.SessionId = SessionId;
    }
    
    public NodeId getAuthenticationToken()
    {
        return AuthenticationToken;
    }
    
    public void setAuthenticationToken(NodeId AuthenticationToken)
    {
        this.AuthenticationToken = AuthenticationToken;
    }
    
    public Double getRevisedSessionTimeout()
    {
        return RevisedSessionTimeout;
    }
    
    public void setRevisedSessionTimeout(Double RevisedSessionTimeout)
    {
        this.RevisedSessionTimeout = RevisedSessionTimeout;
    }
    
    public ByteString getServerNonce()
    {
        return ServerNonce;
    }
    
    public void setServerNonce(ByteString ServerNonce)
    {
        this.ServerNonce = ServerNonce;
    }
    
    public ByteString getServerCertificate()
    {
        return ServerCertificate;
    }
    
    public void setServerCertificate(ByteString ServerCertificate)
    {
        this.ServerCertificate = ServerCertificate;
    }
    
    public EndpointDescription[] getServerEndpoints()
    {
        return ServerEndpoints;
    }
    
    public void setServerEndpoints(EndpointDescription[] ServerEndpoints)
    {
        this.ServerEndpoints = ServerEndpoints;
    }
    
    public SignedSoftwareCertificate[] getServerSoftwareCertificates()
    {
        return ServerSoftwareCertificates;
    }
    
    public void setServerSoftwareCertificates(SignedSoftwareCertificate[] ServerSoftwareCertificates)
    {
        this.ServerSoftwareCertificates = ServerSoftwareCertificates;
    }
    
    public SignatureData getServerSignature()
    {
        return ServerSignature;
    }
    
    public void setServerSignature(SignatureData ServerSignature)
    {
        this.ServerSignature = ServerSignature;
    }
    
    public UnsignedInteger getMaxRequestMessageSize()
    {
        return MaxRequestMessageSize;
    }
    
    public void setMaxRequestMessageSize(UnsignedInteger MaxRequestMessageSize)
    {
        this.MaxRequestMessageSize = MaxRequestMessageSize;
    }
    
    /**
      * Deep clone
      *
      * @return cloned CreateSessionResponse
      */
    public CreateSessionResponse clone()
    {
        CreateSessionResponse result = (CreateSessionResponse) super.clone();
        result.ResponseHeader = ResponseHeader==null ? null : ResponseHeader.clone();
        result.SessionId = SessionId;
        result.AuthenticationToken = AuthenticationToken;
        result.RevisedSessionTimeout = RevisedSessionTimeout;
        result.ServerNonce = ServerNonce;
        result.ServerCertificate = ServerCertificate;
        if (ServerEndpoints!=null) {
            result.ServerEndpoints = new EndpointDescription[ServerEndpoints.length];
            for (int i=0; i<ServerEndpoints.length; i++)
                result.ServerEndpoints[i] = ServerEndpoints[i].clone();
        }
        if (ServerSoftwareCertificates!=null) {
            result.ServerSoftwareCertificates = new SignedSoftwareCertificate[ServerSoftwareCertificates.length];
            for (int i=0; i<ServerSoftwareCertificates.length; i++)
                result.ServerSoftwareCertificates[i] = ServerSoftwareCertificates[i].clone();
        }
        result.ServerSignature = ServerSignature==null ? null : ServerSignature.clone();
        result.MaxRequestMessageSize = MaxRequestMessageSize;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CreateSessionResponse other = (CreateSessionResponse) obj;
        if (ResponseHeader==null) {
            if (other.ResponseHeader != null) return false;
        } else if (!ResponseHeader.equals(other.ResponseHeader)) return false;
        if (SessionId==null) {
            if (other.SessionId != null) return false;
        } else if (!SessionId.equals(other.SessionId)) return false;
        if (AuthenticationToken==null) {
            if (other.AuthenticationToken != null) return false;
        } else if (!AuthenticationToken.equals(other.AuthenticationToken)) return false;
        if (RevisedSessionTimeout==null) {
            if (other.RevisedSessionTimeout != null) return false;
        } else if (!RevisedSessionTimeout.equals(other.RevisedSessionTimeout)) return false;
        if (ServerNonce==null) {
            if (other.ServerNonce != null) return false;
        } else if (!ServerNonce.equals(other.ServerNonce)) return false;
        if (ServerCertificate==null) {
            if (other.ServerCertificate != null) return false;
        } else if (!ServerCertificate.equals(other.ServerCertificate)) return false;
        if (ServerEndpoints==null) {
            if (other.ServerEndpoints != null) return false;
        } else if (!Arrays.equals(ServerEndpoints, other.ServerEndpoints)) return false;
        if (ServerSoftwareCertificates==null) {
            if (other.ServerSoftwareCertificates != null) return false;
        } else if (!Arrays.equals(ServerSoftwareCertificates, other.ServerSoftwareCertificates)) return false;
        if (ServerSignature==null) {
            if (other.ServerSignature != null) return false;
        } else if (!ServerSignature.equals(other.ServerSignature)) return false;
        if (MaxRequestMessageSize==null) {
            if (other.MaxRequestMessageSize != null) return false;
        } else if (!MaxRequestMessageSize.equals(other.MaxRequestMessageSize)) return false;
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
                + ((SessionId == null) ? 0 : SessionId.hashCode());
        result = prime * result
                + ((AuthenticationToken == null) ? 0 : AuthenticationToken.hashCode());
        result = prime * result
                + ((RevisedSessionTimeout == null) ? 0 : RevisedSessionTimeout.hashCode());
        result = prime * result
                + ((ServerNonce == null) ? 0 : ServerNonce.hashCode());
        result = prime * result
                + ((ServerCertificate == null) ? 0 : ServerCertificate.hashCode());
        result = prime * result
                + ((ServerEndpoints == null) ? 0 : Arrays.hashCode(ServerEndpoints));
        result = prime * result
                + ((ServerSoftwareCertificates == null) ? 0 : Arrays.hashCode(ServerSoftwareCertificates));
        result = prime * result
                + ((ServerSignature == null) ? 0 : ServerSignature.hashCode());
        result = prime * result
                + ((MaxRequestMessageSize == null) ? 0 : MaxRequestMessageSize.hashCode());
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
