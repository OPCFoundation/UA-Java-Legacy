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
import org.opcfoundation.ua.core.ComplianceLevel;



public class SupportedProfile extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SupportedProfile);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SupportedProfile_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SupportedProfile_Encoding_DefaultXml);
	
    protected String OrganizationUri;
    protected String ProfileId;
    protected String ComplianceTool;
    protected DateTime ComplianceDate;
    protected ComplianceLevel ComplianceLevel;
    protected String[] UnsupportedUnitIds;
    
    public SupportedProfile() {}
    
    public SupportedProfile(String OrganizationUri, String ProfileId, String ComplianceTool, DateTime ComplianceDate, ComplianceLevel ComplianceLevel, String[] UnsupportedUnitIds)
    {
        this.OrganizationUri = OrganizationUri;
        this.ProfileId = ProfileId;
        this.ComplianceTool = ComplianceTool;
        this.ComplianceDate = ComplianceDate;
        this.ComplianceLevel = ComplianceLevel;
        this.UnsupportedUnitIds = UnsupportedUnitIds;
    }
    
    public String getOrganizationUri()
    {
        return OrganizationUri;
    }
    
    public void setOrganizationUri(String OrganizationUri)
    {
        this.OrganizationUri = OrganizationUri;
    }
    
    public String getProfileId()
    {
        return ProfileId;
    }
    
    public void setProfileId(String ProfileId)
    {
        this.ProfileId = ProfileId;
    }
    
    public String getComplianceTool()
    {
        return ComplianceTool;
    }
    
    public void setComplianceTool(String ComplianceTool)
    {
        this.ComplianceTool = ComplianceTool;
    }
    
    public DateTime getComplianceDate()
    {
        return ComplianceDate;
    }
    
    public void setComplianceDate(DateTime ComplianceDate)
    {
        this.ComplianceDate = ComplianceDate;
    }
    
    public ComplianceLevel getComplianceLevel()
    {
        return ComplianceLevel;
    }
    
    public void setComplianceLevel(ComplianceLevel ComplianceLevel)
    {
        this.ComplianceLevel = ComplianceLevel;
    }
    
    public String[] getUnsupportedUnitIds()
    {
        return UnsupportedUnitIds;
    }
    
    public void setUnsupportedUnitIds(String[] UnsupportedUnitIds)
    {
        this.UnsupportedUnitIds = UnsupportedUnitIds;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SupportedProfile
      */
    public SupportedProfile clone()
    {
        SupportedProfile result = new SupportedProfile();
        result.OrganizationUri = OrganizationUri;
        result.ProfileId = ProfileId;
        result.ComplianceTool = ComplianceTool;
        result.ComplianceDate = ComplianceDate;
        result.ComplianceLevel = ComplianceLevel;
        result.UnsupportedUnitIds = UnsupportedUnitIds==null ? null : UnsupportedUnitIds.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SupportedProfile other = (SupportedProfile) obj;
        if (OrganizationUri==null) {
            if (other.OrganizationUri != null) return false;
        } else if (!OrganizationUri.equals(other.OrganizationUri)) return false;
        if (ProfileId==null) {
            if (other.ProfileId != null) return false;
        } else if (!ProfileId.equals(other.ProfileId)) return false;
        if (ComplianceTool==null) {
            if (other.ComplianceTool != null) return false;
        } else if (!ComplianceTool.equals(other.ComplianceTool)) return false;
        if (ComplianceDate==null) {
            if (other.ComplianceDate != null) return false;
        } else if (!ComplianceDate.equals(other.ComplianceDate)) return false;
        if (ComplianceLevel==null) {
            if (other.ComplianceLevel != null) return false;
        } else if (!ComplianceLevel.equals(other.ComplianceLevel)) return false;
        if (UnsupportedUnitIds==null) {
            if (other.UnsupportedUnitIds != null) return false;
        } else if (!Arrays.equals(UnsupportedUnitIds, other.UnsupportedUnitIds)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((OrganizationUri == null) ? 0 : OrganizationUri.hashCode());
        result = prime * result
                + ((ProfileId == null) ? 0 : ProfileId.hashCode());
        result = prime * result
                + ((ComplianceTool == null) ? 0 : ComplianceTool.hashCode());
        result = prime * result
                + ((ComplianceDate == null) ? 0 : ComplianceDate.hashCode());
        result = prime * result
                + ((ComplianceLevel == null) ? 0 : ComplianceLevel.hashCode());
        result = prime * result
                + ((UnsupportedUnitIds == null) ? 0 : Arrays.hashCode(UnsupportedUnitIds));
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
		return "SupportedProfile: "+ObjectUtils.printFieldsDeep(this);
	}

}
