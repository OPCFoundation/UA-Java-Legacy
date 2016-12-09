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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.utils.AbstractStructure;



public class BuildInfo extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.BuildInfo);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.BuildInfo_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.BuildInfo_Encoding_DefaultXml);
	
    protected String ProductUri;
    protected String ManufacturerName;
    protected String ProductName;
    protected String SoftwareVersion;
    protected String BuildNumber;
    protected DateTime BuildDate;
    
    public BuildInfo() {}
    
    public BuildInfo(String ProductUri, String ManufacturerName, String ProductName, String SoftwareVersion, String BuildNumber, DateTime BuildDate)
    {
        this.ProductUri = ProductUri;
        this.ManufacturerName = ManufacturerName;
        this.ProductName = ProductName;
        this.SoftwareVersion = SoftwareVersion;
        this.BuildNumber = BuildNumber;
        this.BuildDate = BuildDate;
    }
    
    public String getProductUri()
    {
        return ProductUri;
    }
    
    public void setProductUri(String ProductUri)
    {
        this.ProductUri = ProductUri;
    }
    
    public String getManufacturerName()
    {
        return ManufacturerName;
    }
    
    public void setManufacturerName(String ManufacturerName)
    {
        this.ManufacturerName = ManufacturerName;
    }
    
    public String getProductName()
    {
        return ProductName;
    }
    
    public void setProductName(String ProductName)
    {
        this.ProductName = ProductName;
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
    
    /**
      * Deep clone
      *
      * @return cloned BuildInfo
      */
    public BuildInfo clone()
    {
        BuildInfo result = (BuildInfo) super.clone();
        result.ProductUri = ProductUri;
        result.ManufacturerName = ManufacturerName;
        result.ProductName = ProductName;
        result.SoftwareVersion = SoftwareVersion;
        result.BuildNumber = BuildNumber;
        result.BuildDate = BuildDate;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BuildInfo other = (BuildInfo) obj;
        if (ProductUri==null) {
            if (other.ProductUri != null) return false;
        } else if (!ProductUri.equals(other.ProductUri)) return false;
        if (ManufacturerName==null) {
            if (other.ManufacturerName != null) return false;
        } else if (!ManufacturerName.equals(other.ManufacturerName)) return false;
        if (ProductName==null) {
            if (other.ProductName != null) return false;
        } else if (!ProductName.equals(other.ProductName)) return false;
        if (SoftwareVersion==null) {
            if (other.SoftwareVersion != null) return false;
        } else if (!SoftwareVersion.equals(other.SoftwareVersion)) return false;
        if (BuildNumber==null) {
            if (other.BuildNumber != null) return false;
        } else if (!BuildNumber.equals(other.BuildNumber)) return false;
        if (BuildDate==null) {
            if (other.BuildDate != null) return false;
        } else if (!BuildDate.equals(other.BuildDate)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ProductUri == null) ? 0 : ProductUri.hashCode());
        result = prime * result
                + ((ManufacturerName == null) ? 0 : ManufacturerName.hashCode());
        result = prime * result
                + ((ProductName == null) ? 0 : ProductName.hashCode());
        result = prime * result
                + ((SoftwareVersion == null) ? 0 : SoftwareVersion.hashCode());
        result = prime * result
                + ((BuildNumber == null) ? 0 : BuildNumber.hashCode());
        result = prime * result
                + ((BuildDate == null) ? 0 : BuildDate.hashCode());
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
		return "BuildInfo: "+ObjectUtils.printFieldsDeep(this);
	}

}
