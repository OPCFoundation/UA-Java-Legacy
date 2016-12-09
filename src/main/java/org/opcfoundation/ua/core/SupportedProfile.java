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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.core.ComplianceLevel;
import org.opcfoundation.ua.utils.AbstractStructure;



public class SupportedProfile extends AbstractStructure {
	
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
        SupportedProfile result = (SupportedProfile) super.clone();
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
