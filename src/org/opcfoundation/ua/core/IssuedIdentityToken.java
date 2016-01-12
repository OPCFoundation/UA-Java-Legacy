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
import org.opcfoundation.ua.core.UserIdentityToken;



public class IssuedIdentityToken extends UserIdentityToken implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.IssuedIdentityToken);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.IssuedIdentityToken_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.IssuedIdentityToken_Encoding_DefaultXml);
	
    protected byte[] TokenData;
    protected String EncryptionAlgorithm;
    
    public IssuedIdentityToken() {}
    
    public IssuedIdentityToken(String PolicyId, byte[] TokenData, String EncryptionAlgorithm)
    {
        super(PolicyId);
        this.TokenData = TokenData;
        this.EncryptionAlgorithm = EncryptionAlgorithm;
    }
    
    public byte[] getTokenData()
    {
        return TokenData;
    }
    
    public void setTokenData(byte[] TokenData)
    {
        this.TokenData = TokenData;
    }
    
    public String getEncryptionAlgorithm()
    {
        return EncryptionAlgorithm;
    }
    
    public void setEncryptionAlgorithm(String EncryptionAlgorithm)
    {
        this.EncryptionAlgorithm = EncryptionAlgorithm;
    }
    
    /**
      * Deep clone
      *
      * @return cloned IssuedIdentityToken
      */
    public IssuedIdentityToken clone()
    {
        IssuedIdentityToken result = new IssuedIdentityToken();
        result.PolicyId = PolicyId;
        result.TokenData = TokenData;
        result.EncryptionAlgorithm = EncryptionAlgorithm;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        IssuedIdentityToken other = (IssuedIdentityToken) obj;
        if (PolicyId==null) {
            if (other.PolicyId != null) return false;
        } else if (!PolicyId.equals(other.PolicyId)) return false;
        if (TokenData==null) {
            if (other.TokenData != null) return false;
        } else if (!TokenData.equals(other.TokenData)) return false;
        if (EncryptionAlgorithm==null) {
            if (other.EncryptionAlgorithm != null) return false;
        } else if (!EncryptionAlgorithm.equals(other.EncryptionAlgorithm)) return false;
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
                + ((TokenData == null) ? 0 : TokenData.hashCode());
        result = prime * result
                + ((EncryptionAlgorithm == null) ? 0 : EncryptionAlgorithm.hashCode());
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
		return "IssuedIdentityToken: "+ObjectUtils.printFieldsDeep(this);
	}

}
