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



public class X509IdentityToken extends UserIdentityToken implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.X509IdentityToken);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.X509IdentityToken_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.X509IdentityToken_Encoding_DefaultXml);
	
    protected byte[] CertificateData;
    
    public X509IdentityToken() {}
    
    public X509IdentityToken(String PolicyId, byte[] CertificateData)
    {
        super(PolicyId);
        this.CertificateData = CertificateData;
    }
    
    public byte[] getCertificateData()
    {
        return CertificateData;
    }
    
    public void setCertificateData(byte[] CertificateData)
    {
        this.CertificateData = CertificateData;
    }
    
    /**
      * Deep clone
      *
      * @return cloned X509IdentityToken
      */
    public X509IdentityToken clone()
    {
        X509IdentityToken result = new X509IdentityToken();
        result.PolicyId = PolicyId;
        result.CertificateData = CertificateData;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        X509IdentityToken other = (X509IdentityToken) obj;
        if (PolicyId==null) {
            if (other.PolicyId != null) return false;
        } else if (!PolicyId.equals(other.PolicyId)) return false;
        if (CertificateData==null) {
            if (other.CertificateData != null) return false;
        } else if (!CertificateData.equals(other.CertificateData)) return false;
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
                + ((CertificateData == null) ? 0 : CertificateData.hashCode());
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
		return "X509IdentityToken: "+ObjectUtils.printFieldsDeep(this);
	}

}
