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
import org.opcfoundation.ua.builtintypes.LocalizedText;



public class EUInformation extends Object implements Structure, Cloneable {
	
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
        EUInformation result = new EUInformation();
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
