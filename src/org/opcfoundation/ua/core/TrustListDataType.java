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
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class TrustListDataType extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.TrustListDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.TrustListDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.TrustListDataType_Encoding_DefaultXml);
	
    protected UnsignedInteger SpecifiedLists;
    protected byte[][] TrustedCertificates;
    protected byte[][] TrustedCrls;
    protected byte[][] IssuerCertificates;
    protected byte[][] IssuerCrls;
    
    public TrustListDataType() {}
    
    public TrustListDataType(UnsignedInteger SpecifiedLists, byte[][] TrustedCertificates, byte[][] TrustedCrls, byte[][] IssuerCertificates, byte[][] IssuerCrls)
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
    
    public byte[][] getTrustedCertificates()
    {
        return TrustedCertificates;
    }
    
    public void setTrustedCertificates(byte[][] TrustedCertificates)
    {
        this.TrustedCertificates = TrustedCertificates;
    }
    
    public byte[][] getTrustedCrls()
    {
        return TrustedCrls;
    }
    
    public void setTrustedCrls(byte[][] TrustedCrls)
    {
        this.TrustedCrls = TrustedCrls;
    }
    
    public byte[][] getIssuerCertificates()
    {
        return IssuerCertificates;
    }
    
    public void setIssuerCertificates(byte[][] IssuerCertificates)
    {
        this.IssuerCertificates = IssuerCertificates;
    }
    
    public byte[][] getIssuerCrls()
    {
        return IssuerCrls;
    }
    
    public void setIssuerCrls(byte[][] IssuerCrls)
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
        TrustListDataType result = new TrustListDataType();
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
