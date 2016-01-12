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

import java.util.Arrays;

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.EndpointUtil;
import org.opcfoundation.ua.utils.ObjectUtils;


/**
 * Endpoint Description
 * 
 * @See {@link EndpointUtil} for utility methods
 */

public class EndpointDescription extends Object implements Structure, Cloneable {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.EndpointDescription);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.EndpointDescription_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.EndpointDescription_Encoding_DefaultXml);
	
    protected String EndpointUrl;
    protected ApplicationDescription Server;
    protected byte[] ServerCertificate;
    protected MessageSecurityMode SecurityMode;
    protected String SecurityPolicyUri;
    protected UserTokenPolicy[] UserIdentityTokens;
    protected String TransportProfileUri;
    protected UnsignedByte SecurityLevel;
    
    public EndpointDescription() {}
    
    public EndpointDescription(String EndpointUrl, ApplicationDescription Server, byte[] ServerCertificate, MessageSecurityMode SecurityMode, String SecurityPolicyUri, UserTokenPolicy[] UserIdentityTokens, String TransportProfileUri, UnsignedByte SecurityLevel)
    {
        this.EndpointUrl = EndpointUrl;
        this.Server = Server;
        this.ServerCertificate = ServerCertificate;
        this.SecurityMode = SecurityMode;
        this.SecurityPolicyUri = SecurityPolicyUri;
        this.UserIdentityTokens = UserIdentityTokens;
        this.TransportProfileUri = TransportProfileUri;
        this.SecurityLevel = SecurityLevel;
    }
    
    public String getEndpointUrl()
    {
        return EndpointUrl;
    }
    
    public void setEndpointUrl(String EndpointUrl)
    {
        this.EndpointUrl = EndpointUrl;
    }
    
    public ApplicationDescription getServer()
    {
        return Server;
    }
    
    public void setServer(ApplicationDescription Server)
    {
        this.Server = Server;
    }
    
    public byte[] getServerCertificate()
    {
        return ServerCertificate;
    }
    
    public void setServerCertificate(byte[] ServerCertificate)
    {
        this.ServerCertificate = ServerCertificate;
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
    
    public UserTokenPolicy[] getUserIdentityTokens()
    {
        return UserIdentityTokens;
    }
    
    public void setUserIdentityTokens(UserTokenPolicy[] UserIdentityTokens)
    {
        this.UserIdentityTokens = UserIdentityTokens;
    }
    
    public String getTransportProfileUri()
    {
        return TransportProfileUri;
    }
    
    public void setTransportProfileUri(String TransportProfileUri)
    {
        this.TransportProfileUri = TransportProfileUri;
    }
    
    public UnsignedByte getSecurityLevel()
    {
        return SecurityLevel;
    }
    
    public void setSecurityLevel(UnsignedByte SecurityLevel)
    {
        this.SecurityLevel = SecurityLevel;
    }
    
    /**
      * Deep clone
      *
      * @return cloned EndpointDescription
      */
    public EndpointDescription clone()
    {
        EndpointDescription result = new EndpointDescription();
        result.EndpointUrl = EndpointUrl;
        result.Server = Server==null ? null : Server.clone();
        result.ServerCertificate = ServerCertificate;
        result.SecurityMode = SecurityMode;
        result.SecurityPolicyUri = SecurityPolicyUri;
        if (UserIdentityTokens!=null) {
            result.UserIdentityTokens = new UserTokenPolicy[UserIdentityTokens.length];
            for (int i=0; i<UserIdentityTokens.length; i++)
                result.UserIdentityTokens[i] = UserIdentityTokens[i].clone();
        }
        result.TransportProfileUri = TransportProfileUri;
        result.SecurityLevel = SecurityLevel;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EndpointDescription other = (EndpointDescription) obj;
        if (EndpointUrl==null) {
            if (other.EndpointUrl != null) return false;
        } else if (!EndpointUrl.equals(other.EndpointUrl)) return false;
        if (Server==null) {
            if (other.Server != null) return false;
        } else if (!Server.equals(other.Server)) return false;
        if (ServerCertificate==null) {
            if (other.ServerCertificate != null) return false;
        } else if (!ServerCertificate.equals(other.ServerCertificate)) return false;
        if (SecurityMode==null) {
            if (other.SecurityMode != null) return false;
        } else if (!SecurityMode.equals(other.SecurityMode)) return false;
        if (SecurityPolicyUri==null) {
            if (other.SecurityPolicyUri != null) return false;
        } else if (!SecurityPolicyUri.equals(other.SecurityPolicyUri)) return false;
        if (UserIdentityTokens==null) {
            if (other.UserIdentityTokens != null) return false;
        } else if (!Arrays.equals(UserIdentityTokens, other.UserIdentityTokens)) return false;
        if (TransportProfileUri==null) {
            if (other.TransportProfileUri != null) return false;
        } else if (!TransportProfileUri.equals(other.TransportProfileUri)) return false;
        if (SecurityLevel==null) {
            if (other.SecurityLevel != null) return false;
        } else if (!SecurityLevel.equals(other.SecurityLevel)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((EndpointUrl == null) ? 0 : EndpointUrl.hashCode());
        result = prime * result
                + ((Server == null) ? 0 : Server.hashCode());
        result = prime * result
                + ((ServerCertificate == null) ? 0 : ServerCertificate.hashCode());
        result = prime * result
                + ((SecurityMode == null) ? 0 : SecurityMode.hashCode());
        result = prime * result
                + ((SecurityPolicyUri == null) ? 0 : SecurityPolicyUri.hashCode());
        result = prime * result
                + ((UserIdentityTokens == null) ? 0 : Arrays.hashCode(UserIdentityTokens));
        result = prime * result
                + ((TransportProfileUri == null) ? 0 : TransportProfileUri.hashCode());
        result = prime * result
                + ((SecurityLevel == null) ? 0 : SecurityLevel.hashCode());
        return result;
    }
    

	
	/**
	 * Tests whether the stack and the endpoint supports given token type.
	 * This verifies that the stack knows the encryption algorithms of the
	 * token type. 
	 *  
	 * @param endpoint
	 * @param type
	 * @return true, if token type is supported
	 */
	public boolean supportsUserTokenType(EndpointDescription endpoint, UserTokenType type)
	{
		return findUserTokenPolicy(type) != null;
	}

	/**
	 * Finds UserTokenPolicy of given type that this stack can encrypt
	 * 
	 * @param endpoint
	 * @param type
	 * @return user token policy or null 
	 */
	public UserTokenPolicy findUserTokenPolicy(UserTokenType type)
	{
		if (UserIdentityTokens==null) return null;
		for (UserTokenPolicy p : UserIdentityTokens)
		{
		
			// Ensure the stack knows the policy
			try {
				String securityPolicyUri = p.getSecurityPolicyUri();
				SecurityPolicy.getSecurityPolicy(securityPolicyUri);
			} catch (ServiceResultException e) {
				continue;
			}

			if (p.getTokenType() != type) continue;
		
			return p;
		}
		return null;
	}

    /**
     * Finds the user token policy with the specified id.
     * 
     * @return user token policy or null
     */
    public UserTokenPolicy findUserTokenPolicy(String policyId)
    {
		if (UserIdentityTokens==null) return null;
    	//TODO how to determine right policyId's? Now policyId == Token name
		for (UserTokenPolicy policy : UserIdentityTokens)
			if (policy != null) {
				final String p = policy.getPolicyId();
				if (p != null && p.equals(policyId))
					return policy;
			}
        return null;
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
        return "EndpointDescription: "+ObjectUtils.printFieldsDeep(this);
    }

	public boolean needsCertificate() {
		return getSecurityMode().hasSigning() ||
			EndpointUtil.containsSecureUserTokenPolicy(getUserIdentityTokens());
	}
    
}
