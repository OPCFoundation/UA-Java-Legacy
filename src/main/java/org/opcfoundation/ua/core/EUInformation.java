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
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.utils.AbstractStructure;



public class EUInformation extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.EUInformation);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.EUInformation_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.EUInformation_Encoding_DefaultXml);
	
    protected String NamespaceUri;
    protected Integer UnitId;
    protected LocalizedText DisplayName;
    protected LocalizedText Description;
    
    public EUInformation() {}
    
    public EUInformation(String NamespaceUri, Integer UnitId, LocalizedText DisplayName, LocalizedText Description)
    {
        this.NamespaceUri = NamespaceUri;
        this.UnitId = UnitId;
        this.DisplayName = DisplayName;
        this.Description = Description;
    }
    
    public String getNamespaceUri()
    {
        return NamespaceUri;
    }
    
    public void setNamespaceUri(String NamespaceUri)
    {
        this.NamespaceUri = NamespaceUri;
    }
    
    public Integer getUnitId()
    {
        return UnitId;
    }
    
    public void setUnitId(Integer UnitId)
    {
        this.UnitId = UnitId;
    }
    
    public LocalizedText getDisplayName()
    {
        return DisplayName;
    }
    
    public void setDisplayName(LocalizedText DisplayName)
    {
        this.DisplayName = DisplayName;
    }
    
    public LocalizedText getDescription()
    {
        return Description;
    }
    
    public void setDescription(LocalizedText Description)
    {
        this.Description = Description;
    }
    
    /**
      * Deep clone
      *
      * @return cloned EUInformation
      */
    public EUInformation clone()
    {
        EUInformation result = (EUInformation) super.clone();
        result.NamespaceUri = NamespaceUri;
        result.UnitId = UnitId;
        result.DisplayName = DisplayName;
        result.Description = Description;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EUInformation other = (EUInformation) obj;
        if (NamespaceUri==null) {
            if (other.NamespaceUri != null) return false;
        } else if (!NamespaceUri.equals(other.NamespaceUri)) return false;
        if (UnitId==null) {
            if (other.UnitId != null) return false;
        } else if (!UnitId.equals(other.UnitId)) return false;
        if (DisplayName==null) {
            if (other.DisplayName != null) return false;
        } else if (!DisplayName.equals(other.DisplayName)) return false;
        if (Description==null) {
            if (other.Description != null) return false;
        } else if (!Description.equals(other.Description)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((NamespaceUri == null) ? 0 : NamespaceUri.hashCode());
        result = prime * result
                + ((UnitId == null) ? 0 : UnitId.hashCode());
        result = prime * result
                + ((DisplayName == null) ? 0 : DisplayName.hashCode());
        result = prime * result
                + ((Description == null) ? 0 : Description.hashCode());
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
		return "EUInformation: "+ObjectUtils.printFieldsDeep(this);
	}

}
