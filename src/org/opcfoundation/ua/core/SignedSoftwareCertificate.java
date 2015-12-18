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



public class SignedSoftwareCertificate extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SignedSoftwareCertificate);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SignedSoftwareCertificate_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SignedSoftwareCertificate_Encoding_DefaultXml);
	
    protected byte[] CertificateData;
    protected byte[] Signature;
    
    public SignedSoftwareCertificate() {}
    
    public SignedSoftwareCertificate(byte[] CertificateData, byte[] Signature)
    {
        this.CertificateData = CertificateData;
        this.Signature = Signature;
    }
    
    public byte[] getCertificateData()
    {
        return CertificateData;
    }
    
    public void setCertificateData(byte[] CertificateData)
    {
        this.CertificateData = CertificateData;
    }
    
    public byte[] getSignature()
    {
        return Signature;
    }
    
    public void setSignature(byte[] Signature)
    {
        this.Signature = Signature;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SignedSoftwareCertificate
      */
    public SignedSoftwareCertificate clone()
    {
        SignedSoftwareCertificate result = new SignedSoftwareCertificate();
        result.CertificateData = CertificateData;
        result.Signature = Signature;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SignedSoftwareCertificate other = (SignedSoftwareCertificate) obj;
        if (CertificateData==null) {
            if (other.CertificateData != null) return false;
        } else if (!CertificateData.equals(other.CertificateData)) return false;
        if (Signature==null) {
            if (other.Signature != null) return false;
        } else if (!Signature.equals(other.Signature)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((CertificateData == null) ? 0 : CertificateData.hashCode());
        result = prime * result
                + ((Signature == null) ? 0 : Signature.hashCode());
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
		return "SignedSoftwareCertificate: "+ObjectUtils.printFieldsDeep(this);
	}

}
