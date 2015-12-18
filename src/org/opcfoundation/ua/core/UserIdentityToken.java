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

import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;



public class UserIdentityToken extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.UserIdentityToken);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.UserIdentityToken_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.UserIdentityToken_Encoding_DefaultXml);
	
    protected String PolicyId;
    
    public UserIdentityToken() {}
    
    public UserIdentityToken(String PolicyId)
    {
        this.PolicyId = PolicyId;
    }
    
    public String getPolicyId()
    {
        return PolicyId;
    }
    
    public void setPolicyId(String PolicyId)
    {
        this.PolicyId = PolicyId;
    }
    
    /**
      * Deep clone
      *
      * @return cloned UserIdentityToken
      */
    public UserIdentityToken clone()
    {
        UserIdentityToken result = new UserIdentityToken();
        result.PolicyId = PolicyId;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UserIdentityToken other = (UserIdentityToken) obj;
        if (PolicyId==null) {
            if (other.PolicyId != null) return false;
        } else if (!PolicyId.equals(other.PolicyId)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((PolicyId == null) ? 0 : PolicyId.hashCode());
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
		return "UserIdentityToken: "+ObjectUtils.printFieldsDeep(this);
	}

}
