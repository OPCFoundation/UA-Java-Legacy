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
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.core.UserIdentityToken;



public class UserNameIdentityToken extends UserIdentityToken {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.UserNameIdentityToken);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.UserNameIdentityToken_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.UserNameIdentityToken_Encoding_DefaultXml);
	
    protected String UserName;
    protected ByteString Password;
    protected String EncryptionAlgorithm;
    
    public UserNameIdentityToken() {}
    
    public UserNameIdentityToken(String PolicyId, String UserName, ByteString Password, String EncryptionAlgorithm)
    {
        super(PolicyId);
        this.UserName = UserName;
        this.Password = Password;
        this.EncryptionAlgorithm = EncryptionAlgorithm;
    }
    
    public String getUserName()
    {
        return UserName;
    }
    
    public void setUserName(String UserName)
    {
        this.UserName = UserName;
    }
    
    public ByteString getPassword()
    {
        return Password;
    }
    
    public void setPassword(ByteString Password)
    {
        this.Password = Password;
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
      * @return cloned UserNameIdentityToken
      */
    public UserNameIdentityToken clone()
    {
        UserNameIdentityToken result = (UserNameIdentityToken) super.clone();
        result.PolicyId = PolicyId;
        result.UserName = UserName;
        result.Password = Password;
        result.EncryptionAlgorithm = EncryptionAlgorithm;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UserNameIdentityToken other = (UserNameIdentityToken) obj;
        if (PolicyId==null) {
            if (other.PolicyId != null) return false;
        } else if (!PolicyId.equals(other.PolicyId)) return false;
        if (UserName==null) {
            if (other.UserName != null) return false;
        } else if (!UserName.equals(other.UserName)) return false;
        if (Password==null) {
            if (other.Password != null) return false;
        } else if (!Password.equals(other.Password)) return false;
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
                + ((UserName == null) ? 0 : UserName.hashCode());
        result = prime * result
                + ((Password == null) ? 0 : Password.hashCode());
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
		return "UserNameIdentityToken: "+ObjectUtils.printFieldsDeep(this);
	}

}
