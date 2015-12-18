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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.core.SupportedProfile;



public class SoftwareCertificate extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SoftwareCertificate);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SoftwareCertificate_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SoftwareCertificate_Encoding_DefaultXml);
	
    protected String ProductName;
    protected String ProductUri;
    protected String VendorName;
    protected byte[] VendorProductCertificate;
    protected String SoftwareVersion;
    protected String BuildNumber;
    protected DateTime BuildDate;
    protected String IssuedBy;
    protected DateTime IssueDate;
    protected SupportedProfile[] SupportedProfiles;
    
    public SoftwareCertificate() {}
    
    public SoftwareCertificate(String ProductName, String ProductUri, String VendorName, byte[] VendorProductCertificate, String SoftwareVersion, String BuildNumber, DateTime BuildDate, String IssuedBy, DateTime IssueDate, SupportedProfile[] SupportedProfiles)
    {
        this.ProductName = ProductName;
        this.ProductUri = ProductUri;
        this.VendorName = VendorName;
        this.VendorProductCertificate = VendorProductCertificate;
        this.SoftwareVersion = SoftwareVersion;
        this.BuildNumber = BuildNumber;
        this.BuildDate = BuildDate;
        this.IssuedBy = IssuedBy;
        this.IssueDate = IssueDate;
        this.SupportedProfiles = SupportedProfiles;
    }
    
    public String getProductName()
    {
        return ProductName;
    }
    
    public void setProductName(String ProductName)
    {
        this.ProductName = ProductName;
    }
    
    public String getProductUri()
    {
        return ProductUri;
    }
    
    public void setProductUri(String ProductUri)
    {
        this.ProductUri = ProductUri;
    }
    
    public String getVendorName()
    {
        return VendorName;
    }
    
    public void setVendorName(String VendorName)
    {
        this.VendorName = VendorName;
    }
    
    public byte[] getVendorProductCertificate()
    {
        return VendorProductCertificate;
    }
    
    public void setVendorProductCertificate(byte[] VendorProductCertificate)
    {
        this.VendorProductCertificate = VendorProductCertificate;
    }
    
    public String getSoftwareVersion()
    {
        return SoftwareVersion;
    }
    
    public void setSoftwareVersion(String SoftwareVersion)
    {
        this.SoftwareVersion = SoftwareVersion;
    }
    
    public String getBuildNumber()
    {
        return BuildNumber;
    }
    
    public void setBuildNumber(String BuildNumber)
    {
        this.BuildNumber = BuildNumber;
    }
    
    public DateTime getBuildDate()
    {
        return BuildDate;
    }
    
    public void setBuildDate(DateTime BuildDate)
    {
        this.BuildDate = BuildDate;
    }
    
    public String getIssuedBy()
    {
        return IssuedBy;
    }
    
    public void setIssuedBy(String IssuedBy)
    {
        this.IssuedBy = IssuedBy;
    }
    
    public DateTime getIssueDate()
    {
        return IssueDate;
    }
    
    public void setIssueDate(DateTime IssueDate)
    {
        this.IssueDate = IssueDate;
    }
    
    public SupportedProfile[] getSupportedProfiles()
    {
        return SupportedProfiles;
    }
    
    public void setSupportedProfiles(SupportedProfile[] SupportedProfiles)
    {
        this.SupportedProfiles = SupportedProfiles;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SoftwareCertificate
      */
    public SoftwareCertificate clone()
    {
        SoftwareCertificate result = new SoftwareCertificate();
        result.ProductName = ProductName;
        result.ProductUri = ProductUri;
        result.VendorName = VendorName;
        result.VendorProductCertificate = VendorProductCertificate;
        result.SoftwareVersion = SoftwareVersion;
        result.BuildNumber = BuildNumber;
        result.BuildDate = BuildDate;
        result.IssuedBy = IssuedBy;
        result.IssueDate = IssueDate;
        if (SupportedProfiles!=null) {
            result.SupportedProfiles = new SupportedProfile[SupportedProfiles.length];
            for (int i=0; i<SupportedProfiles.length; i++)
                result.SupportedProfiles[i] = SupportedProfiles[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SoftwareCertificate other = (SoftwareCertificate) obj;
        if (ProductName==null) {
            if (other.ProductName != null) return false;
        } else if (!ProductName.equals(other.ProductName)) return false;
        if (ProductUri==null) {
            if (other.ProductUri != null) return false;
        } else if (!ProductUri.equals(other.ProductUri)) return false;
        if (VendorName==null) {
            if (other.VendorName != null) return false;
        } else if (!VendorName.equals(other.VendorName)) return false;
        if (VendorProductCertificate==null) {
            if (other.VendorProductCertificate != null) return false;
        } else if (!VendorProductCertificate.equals(other.VendorProductCertificate)) return false;
        if (SoftwareVersion==null) {
            if (other.SoftwareVersion != null) return false;
        } else if (!SoftwareVersion.equals(other.SoftwareVersion)) return false;
        if (BuildNumber==null) {
            if (other.BuildNumber != null) return false;
        } else if (!BuildNumber.equals(other.BuildNumber)) return false;
        if (BuildDate==null) {
            if (other.BuildDate != null) return false;
        } else if (!BuildDate.equals(other.BuildDate)) return false;
        if (IssuedBy==null) {
            if (other.IssuedBy != null) return false;
        } else if (!IssuedBy.equals(other.IssuedBy)) return false;
        if (IssueDate==null) {
            if (other.IssueDate != null) return false;
        } else if (!IssueDate.equals(other.IssueDate)) return false;
        if (SupportedProfiles==null) {
            if (other.SupportedProfiles != null) return false;
        } else if (!Arrays.equals(SupportedProfiles, other.SupportedProfiles)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ProductName == null) ? 0 : ProductName.hashCode());
        result = prime * result
                + ((ProductUri == null) ? 0 : ProductUri.hashCode());
        result = prime * result
                + ((VendorName == null) ? 0 : VendorName.hashCode());
        result = prime * result
                + ((VendorProductCertificate == null) ? 0 : VendorProductCertificate.hashCode());
        result = prime * result
                + ((SoftwareVersion == null) ? 0 : SoftwareVersion.hashCode());
        result = prime * result
                + ((BuildNumber == null) ? 0 : BuildNumber.hashCode());
        result = prime * result
                + ((BuildDate == null) ? 0 : BuildDate.hashCode());
        result = prime * result
                + ((IssuedBy == null) ? 0 : IssuedBy.hashCode());
        result = prime * result
                + ((IssueDate == null) ? 0 : IssueDate.hashCode());
        result = prime * result
                + ((SupportedProfiles == null) ? 0 : Arrays.hashCode(SupportedProfiles));
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
		return "SoftwareCertificate: "+ObjectUtils.printFieldsDeep(this);
	}

}
