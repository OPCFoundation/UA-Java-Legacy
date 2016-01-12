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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.NodeAttributes;



public class MethodAttributes extends NodeAttributes implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.MethodAttributes);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.MethodAttributes_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.MethodAttributes_Encoding_DefaultXml);
	
    protected Boolean Executable;
    protected Boolean UserExecutable;
    
    public MethodAttributes() {}
    
    public MethodAttributes(UnsignedInteger SpecifiedAttributes, LocalizedText DisplayName, LocalizedText Description, UnsignedInteger WriteMask, UnsignedInteger UserWriteMask, Boolean Executable, Boolean UserExecutable)
    {
        super(SpecifiedAttributes, DisplayName, Description, WriteMask, UserWriteMask);
        this.Executable = Executable;
        this.UserExecutable = UserExecutable;
    }
    
    public Boolean getExecutable()
    {
        return Executable;
    }
    
    public void setExecutable(Boolean Executable)
    {
        this.Executable = Executable;
    }
    
    public Boolean getUserExecutable()
    {
        return UserExecutable;
    }
    
    public void setUserExecutable(Boolean UserExecutable)
    {
        this.UserExecutable = UserExecutable;
    }
    
    /**
      * Deep clone
      *
      * @return cloned MethodAttributes
      */
    public MethodAttributes clone()
    {
        MethodAttributes result = new MethodAttributes();
        result.SpecifiedAttributes = SpecifiedAttributes;
        result.DisplayName = DisplayName;
        result.Description = Description;
        result.WriteMask = WriteMask;
        result.UserWriteMask = UserWriteMask;
        result.Executable = Executable;
        result.UserExecutable = UserExecutable;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MethodAttributes other = (MethodAttributes) obj;
        if (SpecifiedAttributes==null) {
            if (other.SpecifiedAttributes != null) return false;
        } else if (!SpecifiedAttributes.equals(other.SpecifiedAttributes)) return false;
        if (DisplayName==null) {
            if (other.DisplayName != null) return false;
        } else if (!DisplayName.equals(other.DisplayName)) return false;
        if (Description==null) {
            if (other.Description != null) return false;
        } else if (!Description.equals(other.Description)) return false;
        if (WriteMask==null) {
            if (other.WriteMask != null) return false;
        } else if (!WriteMask.equals(other.WriteMask)) return false;
        if (UserWriteMask==null) {
            if (other.UserWriteMask != null) return false;
        } else if (!UserWriteMask.equals(other.UserWriteMask)) return false;
        if (Executable==null) {
            if (other.Executable != null) return false;
        } else if (!Executable.equals(other.Executable)) return false;
        if (UserExecutable==null) {
            if (other.UserExecutable != null) return false;
        } else if (!UserExecutable.equals(other.UserExecutable)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((SpecifiedAttributes == null) ? 0 : SpecifiedAttributes.hashCode());
        result = prime * result
                + ((DisplayName == null) ? 0 : DisplayName.hashCode());
        result = prime * result
                + ((Description == null) ? 0 : Description.hashCode());
        result = prime * result
                + ((WriteMask == null) ? 0 : WriteMask.hashCode());
        result = prime * result
                + ((UserWriteMask == null) ? 0 : UserWriteMask.hashCode());
        result = prime * result
                + ((Executable == null) ? 0 : Executable.hashCode());
        result = prime * result
                + ((UserExecutable == null) ? 0 : UserExecutable.hashCode());
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
		return "MethodAttributes: "+ObjectUtils.printFieldsDeep(this);
	}

}
