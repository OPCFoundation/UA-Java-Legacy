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

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.ObjectUtils;



public class UserTokenPolicy extends Object implements Structure, Cloneable {
	
	public static final UserTokenPolicy ANONYMOUS = new UserTokenPolicy("anonymous", UserTokenType.Anonymous, null, null, null);
	public static final UserTokenPolicy SECURE_USERNAME_PASSWORD = new UserTokenPolicy("username_basic128", UserTokenType.UserName, null, null, SecurityPolicy.BASIC128RSA15.getPolicyUri());
	public static final UserTokenPolicy SECURE_USERNAME_PASSWORD_BASIC256 = new UserTokenPolicy("username_basic256", UserTokenType.UserName, null, null, SecurityPolicy.BASIC256.getPolicyUri());
	public static final UserTokenPolicy SECURE_CERTIFICATE = new UserTokenPolicy("certificate_basic128", UserTokenType.Certificate, null, null, SecurityPolicy.BASIC128RSA15.getPolicyUri());
	public static final UserTokenPolicy SECURE_CERTIFICATE_BASIC256 = new UserTokenPolicy("certificate_basic256", UserTokenType.Certificate, null, null, SecurityPolicy.BASIC256.getPolicyUri());

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.UserTokenPolicy);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.UserTokenPolicy_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.UserTokenPolicy_Encoding_DefaultXml);
	
    protected String PolicyId;
    protected UserTokenType TokenType;
    protected String IssuedTokenType;
    protected String IssuerEndpointUrl;
    protected String SecurityPolicyUri;
    
    public UserTokenPolicy() {}
    
    public UserTokenPolicy(String PolicyId, UserTokenType TokenType, String IssuedTokenType, String IssuerEndpointUrl, String SecurityPolicyUri)
    {
        this.PolicyId = PolicyId;
        this.TokenType = TokenType;
        this.IssuedTokenType = IssuedTokenType;
        this.IssuerEndpointUrl = IssuerEndpointUrl;
        this.SecurityPolicyUri = SecurityPolicyUri;
    }
    
    public String getPolicyId()
    {
        return PolicyId;
    }
    
    public void setPolicyId(String PolicyId)
    {
        this.PolicyId = PolicyId;
    }
    
    public UserTokenType getTokenType()
    {
        return TokenType;
    }
    
    public void setTokenType(UserTokenType TokenType)
    {
        this.TokenType = TokenType;
    }
    
    public String getIssuedTokenType()
    {
        return IssuedTokenType;
    }
    
    public void setIssuedTokenType(String IssuedTokenType)
    {
        this.IssuedTokenType = IssuedTokenType;
    }
    
    public String getIssuerEndpointUrl()
    {
        return IssuerEndpointUrl;
    }
    
    public void setIssuerEndpointUrl(String IssuerEndpointUrl)
    {
        this.IssuerEndpointUrl = IssuerEndpointUrl;
    }
    
    public String getSecurityPolicyUri()
    {
        return SecurityPolicyUri;
    }
    
    public void setSecurityPolicyUri(String SecurityPolicyUri)
    {
        this.SecurityPolicyUri = SecurityPolicyUri;
    }
    
    /**
      * Deep clone
      *
      * @return cloned UserTokenPolicy
      */
    public UserTokenPolicy clone()
    {
        UserTokenPolicy result = new UserTokenPolicy();
        result.PolicyId = PolicyId;
        result.TokenType = TokenType;
        result.IssuedTokenType = IssuedTokenType;
        result.IssuerEndpointUrl = IssuerEndpointUrl;
        result.SecurityPolicyUri = SecurityPolicyUri;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UserTokenPolicy other = (UserTokenPolicy) obj;
        if (PolicyId==null) {
            if (other.PolicyId != null) return false;
        } else if (!PolicyId.equals(other.PolicyId)) return false;
        if (TokenType==null) {
            if (other.TokenType != null) return false;
        } else if (!TokenType.equals(other.TokenType)) return false;
        if (IssuedTokenType==null) {
            if (other.IssuedTokenType != null) return false;
        } else if (!IssuedTokenType.equals(other.IssuedTokenType)) return false;
        if (IssuerEndpointUrl==null) {
            if (other.IssuerEndpointUrl != null) return false;
        } else if (!IssuerEndpointUrl.equals(other.IssuerEndpointUrl)) return false;
        if (SecurityPolicyUri==null) {
            if (other.SecurityPolicyUri != null) return false;
        } else if (!SecurityPolicyUri.equals(other.SecurityPolicyUri)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((PolicyId == null) ? 0 : PolicyId.hashCode());
        result = prime * result
                + ((TokenType == null) ? 0 : TokenType.hashCode());
        result = prime * result
                + ((IssuedTokenType == null) ? 0 : IssuedTokenType.hashCode());
        result = prime * result
                + ((IssuerEndpointUrl == null) ? 0 : IssuerEndpointUrl.hashCode());
        result = prime * result
                + ((SecurityPolicyUri == null) ? 0 : SecurityPolicyUri.hashCode());
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
		return "UserTokenPolicy: "+ObjectUtils.printFieldsDeep(this);
	}

}
