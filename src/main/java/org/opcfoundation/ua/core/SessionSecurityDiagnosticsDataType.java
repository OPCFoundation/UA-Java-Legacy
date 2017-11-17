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
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.utils.AbstractStructure;



public class SessionSecurityDiagnosticsDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SessionSecurityDiagnosticsDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SessionSecurityDiagnosticsDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SessionSecurityDiagnosticsDataType_Encoding_DefaultXml);
	
    protected NodeId SessionId;
    protected String ClientUserIdOfSession;
    protected String[] ClientUserIdHistory;
    protected String AuthenticationMechanism;
    protected String Encoding;
    protected String TransportProtocol;
    protected MessageSecurityMode SecurityMode;
    protected String SecurityPolicyUri;
    protected ByteString ClientCertificate;
    
    public SessionSecurityDiagnosticsDataType() {}
    
    public SessionSecurityDiagnosticsDataType(NodeId SessionId, String ClientUserIdOfSession, String[] ClientUserIdHistory, String AuthenticationMechanism, String Encoding, String TransportProtocol, MessageSecurityMode SecurityMode, String SecurityPolicyUri, ByteString ClientCertificate)
    {
        this.SessionId = SessionId;
        this.ClientUserIdOfSession = ClientUserIdOfSession;
        this.ClientUserIdHistory = ClientUserIdHistory;
        this.AuthenticationMechanism = AuthenticationMechanism;
        this.Encoding = Encoding;
        this.TransportProtocol = TransportProtocol;
        this.SecurityMode = SecurityMode;
        this.SecurityPolicyUri = SecurityPolicyUri;
        this.ClientCertificate = ClientCertificate;
    }
    
    public NodeId getSessionId()
    {
        return SessionId;
    }
    
    public void setSessionId(NodeId SessionId)
    {
        this.SessionId = SessionId;
    }
    
    public String getClientUserIdOfSession()
    {
        return ClientUserIdOfSession;
    }
    
    public void setClientUserIdOfSession(String ClientUserIdOfSession)
    {
        this.ClientUserIdOfSession = ClientUserIdOfSession;
    }
    
    public String[] getClientUserIdHistory()
    {
        return ClientUserIdHistory;
    }
    
    public void setClientUserIdHistory(String[] ClientUserIdHistory)
    {
        this.ClientUserIdHistory = ClientUserIdHistory;
    }
    
    public String getAuthenticationMechanism()
    {
        return AuthenticationMechanism;
    }
    
    public void setAuthenticationMechanism(String AuthenticationMechanism)
    {
        this.AuthenticationMechanism = AuthenticationMechanism;
    }
    
    public String getEncoding()
    {
        return Encoding;
    }
    
    public void setEncoding(String Encoding)
    {
        this.Encoding = Encoding;
    }
    
    public String getTransportProtocol()
    {
        return TransportProtocol;
    }
    
    public void setTransportProtocol(String TransportProtocol)
    {
        this.TransportProtocol = TransportProtocol;
    }
    
    public MessageSecurityMode getSecurityMode()
    {
        return SecurityMode;
    }
    
    public void setSecurityMode(MessageSecurityMode SecurityMode)
    {
        this.SecurityMode = SecurityMode;
    }
    
    public String getSecurityPolicyUri()
    {
        return SecurityPolicyUri;
    }
    
    public void setSecurityPolicyUri(String SecurityPolicyUri)
    {
        this.SecurityPolicyUri = SecurityPolicyUri;
    }
    
    public ByteString getClientCertificate()
    {
        return ClientCertificate;
    }
    
    public void setClientCertificate(ByteString ClientCertificate)
    {
        this.ClientCertificate = ClientCertificate;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SessionSecurityDiagnosticsDataType
      */
    public SessionSecurityDiagnosticsDataType clone()
    {
        SessionSecurityDiagnosticsDataType result = (SessionSecurityDiagnosticsDataType) super.clone();
        result.SessionId = SessionId;
        result.ClientUserIdOfSession = ClientUserIdOfSession;
        result.ClientUserIdHistory = ClientUserIdHistory==null ? null : ClientUserIdHistory.clone();
        result.AuthenticationMechanism = AuthenticationMechanism;
        result.Encoding = Encoding;
        result.TransportProtocol = TransportProtocol;
        result.SecurityMode = SecurityMode;
        result.SecurityPolicyUri = SecurityPolicyUri;
        result.ClientCertificate = ClientCertificate;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SessionSecurityDiagnosticsDataType other = (SessionSecurityDiagnosticsDataType) obj;
        if (SessionId==null) {
            if (other.SessionId != null) return false;
        } else if (!SessionId.equals(other.SessionId)) return false;
        if (ClientUserIdOfSession==null) {
            if (other.ClientUserIdOfSession != null) return false;
        } else if (!ClientUserIdOfSession.equals(other.ClientUserIdOfSession)) return false;
        if (ClientUserIdHistory==null) {
            if (other.ClientUserIdHistory != null) return false;
        } else if (!Arrays.equals(ClientUserIdHistory, other.ClientUserIdHistory)) return false;
        if (AuthenticationMechanism==null) {
            if (other.AuthenticationMechanism != null) return false;
        } else if (!AuthenticationMechanism.equals(other.AuthenticationMechanism)) return false;
        if (Encoding==null) {
            if (other.Encoding != null) return false;
        } else if (!Encoding.equals(other.Encoding)) return false;
        if (TransportProtocol==null) {
            if (other.TransportProtocol != null) return false;
        } else if (!TransportProtocol.equals(other.TransportProtocol)) return false;
        if (SecurityMode==null) {
            if (other.SecurityMode != null) return false;
        } else if (!SecurityMode.equals(other.SecurityMode)) return false;
        if (SecurityPolicyUri==null) {
            if (other.SecurityPolicyUri != null) return false;
        } else if (!SecurityPolicyUri.equals(other.SecurityPolicyUri)) return false;
        if (ClientCertificate==null) {
            if (other.ClientCertificate != null) return false;
        } else if (!ClientCertificate.equals(other.ClientCertificate)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((SessionId == null) ? 0 : SessionId.hashCode());
        result = prime * result
                + ((ClientUserIdOfSession == null) ? 0 : ClientUserIdOfSession.hashCode());
        result = prime * result
                + ((ClientUserIdHistory == null) ? 0 : Arrays.hashCode(ClientUserIdHistory));
        result = prime * result
                + ((AuthenticationMechanism == null) ? 0 : AuthenticationMechanism.hashCode());
        result = prime * result
                + ((Encoding == null) ? 0 : Encoding.hashCode());
        result = prime * result
                + ((TransportProtocol == null) ? 0 : TransportProtocol.hashCode());
        result = prime * result
                + ((SecurityMode == null) ? 0 : SecurityMode.hashCode());
        result = prime * result
                + ((SecurityPolicyUri == null) ? 0 : SecurityPolicyUri.hashCode());
        result = prime * result
                + ((ClientCertificate == null) ? 0 : ClientCertificate.hashCode());
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
		return "SessionSecurityDiagnosticsDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
