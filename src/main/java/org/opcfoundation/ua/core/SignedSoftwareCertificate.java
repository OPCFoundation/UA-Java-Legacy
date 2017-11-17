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
import org.opcfoundation.ua.utils.AbstractStructure;



public class SignedSoftwareCertificate extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SignedSoftwareCertificate);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SignedSoftwareCertificate_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SignedSoftwareCertificate_Encoding_DefaultXml);
	
    protected ByteString CertificateData;
    protected ByteString Signature;
    
    public SignedSoftwareCertificate() {}
    
    public SignedSoftwareCertificate(ByteString CertificateData, ByteString Signature)
    {
        this.CertificateData = CertificateData;
        this.Signature = Signature;
    }
    
    public ByteString getCertificateData()
    {
        return CertificateData;
    }
    
    public void setCertificateData(ByteString CertificateData)
    {
        this.CertificateData = CertificateData;
    }
    
    public ByteString getSignature()
    {
        return Signature;
    }
    
    public void setSignature(ByteString Signature)
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
        SignedSoftwareCertificate result = (SignedSoftwareCertificate) super.clone();
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
