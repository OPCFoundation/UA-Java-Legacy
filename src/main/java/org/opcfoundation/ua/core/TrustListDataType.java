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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class TrustListDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.TrustListDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.TrustListDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.TrustListDataType_Encoding_DefaultXml);
	
    protected UnsignedInteger SpecifiedLists;
    protected ByteString[] TrustedCertificates;
    protected ByteString[] TrustedCrls;
    protected ByteString[] IssuerCertificates;
    protected ByteString[] IssuerCrls;
    
    public TrustListDataType() {}
    
    public TrustListDataType(UnsignedInteger SpecifiedLists, ByteString[] TrustedCertificates, ByteString[] TrustedCrls, ByteString[] IssuerCertificates, ByteString[] IssuerCrls)
    {
        this.SpecifiedLists = SpecifiedLists;
        this.TrustedCertificates = TrustedCertificates;
        this.TrustedCrls = TrustedCrls;
        this.IssuerCertificates = IssuerCertificates;
        this.IssuerCrls = IssuerCrls;
    }
    
    public UnsignedInteger getSpecifiedLists()
    {
        return SpecifiedLists;
    }
    
    public void setSpecifiedLists(UnsignedInteger SpecifiedLists)
    {
        this.SpecifiedLists = SpecifiedLists;
    }
    
    public ByteString[] getTrustedCertificates()
    {
        return TrustedCertificates;
    }
    
    public void setTrustedCertificates(ByteString[] TrustedCertificates)
    {
        this.TrustedCertificates = TrustedCertificates;
    }
    
    public ByteString[] getTrustedCrls()
    {
        return TrustedCrls;
    }
    
    public void setTrustedCrls(ByteString[] TrustedCrls)
    {
        this.TrustedCrls = TrustedCrls;
    }
    
    public ByteString[] getIssuerCertificates()
    {
        return IssuerCertificates;
    }
    
    public void setIssuerCertificates(ByteString[] IssuerCertificates)
    {
        this.IssuerCertificates = IssuerCertificates;
    }
    
    public ByteString[] getIssuerCrls()
    {
        return IssuerCrls;
    }
    
    public void setIssuerCrls(ByteString[] IssuerCrls)
    {
        this.IssuerCrls = IssuerCrls;
    }
    
    /**
      * Deep clone
      *
      * @return cloned TrustListDataType
      */
    public TrustListDataType clone()
    {
        TrustListDataType result = (TrustListDataType) super.clone();
        result.SpecifiedLists = SpecifiedLists;
        result.TrustedCertificates = TrustedCertificates==null ? null : TrustedCertificates.clone();
        result.TrustedCrls = TrustedCrls==null ? null : TrustedCrls.clone();
        result.IssuerCertificates = IssuerCertificates==null ? null : IssuerCertificates.clone();
        result.IssuerCrls = IssuerCrls==null ? null : IssuerCrls.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TrustListDataType other = (TrustListDataType) obj;
        if (SpecifiedLists==null) {
            if (other.SpecifiedLists != null) return false;
        } else if (!SpecifiedLists.equals(other.SpecifiedLists)) return false;
        if (TrustedCertificates==null) {
            if (other.TrustedCertificates != null) return false;
        } else if (!Arrays.equals(TrustedCertificates, other.TrustedCertificates)) return false;
        if (TrustedCrls==null) {
            if (other.TrustedCrls != null) return false;
        } else if (!Arrays.equals(TrustedCrls, other.TrustedCrls)) return false;
        if (IssuerCertificates==null) {
            if (other.IssuerCertificates != null) return false;
        } else if (!Arrays.equals(IssuerCertificates, other.IssuerCertificates)) return false;
        if (IssuerCrls==null) {
            if (other.IssuerCrls != null) return false;
        } else if (!Arrays.equals(IssuerCrls, other.IssuerCrls)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((SpecifiedLists == null) ? 0 : SpecifiedLists.hashCode());
        result = prime * result
                + ((TrustedCertificates == null) ? 0 : Arrays.hashCode(TrustedCertificates));
        result = prime * result
                + ((TrustedCrls == null) ? 0 : Arrays.hashCode(TrustedCrls));
        result = prime * result
                + ((IssuerCertificates == null) ? 0 : Arrays.hashCode(IssuerCertificates));
        result = prime * result
                + ((IssuerCrls == null) ? 0 : Arrays.hashCode(IssuerCrls));
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
		return "TrustListDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
