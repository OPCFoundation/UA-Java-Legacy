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
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.NodeAttributes;



public class ObjectAttributes extends NodeAttributes implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ObjectAttributes);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ObjectAttributes_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ObjectAttributes_Encoding_DefaultXml);
	
    protected UnsignedByte EventNotifier;
    
    public ObjectAttributes() {}
    
    public ObjectAttributes(UnsignedInteger SpecifiedAttributes, LocalizedText DisplayName, LocalizedText Description, UnsignedInteger WriteMask, UnsignedInteger UserWriteMask, UnsignedByte EventNotifier)
    {
        super(SpecifiedAttributes, DisplayName, Description, WriteMask, UserWriteMask);
        this.EventNotifier = EventNotifier;
    }
    
    public UnsignedByte getEventNotifier()
    {
        return EventNotifier;
    }
    
    public void setEventNotifier(UnsignedByte EventNotifier)
    {
        this.EventNotifier = EventNotifier;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ObjectAttributes
      */
    public ObjectAttributes clone()
    {
        ObjectAttributes result = new ObjectAttributes();
        result.SpecifiedAttributes = SpecifiedAttributes;
        result.DisplayName = DisplayName;
        result.Description = Description;
        result.WriteMask = WriteMask;
        result.UserWriteMask = UserWriteMask;
        result.EventNotifier = EventNotifier;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ObjectAttributes other = (ObjectAttributes) obj;
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
        if (EventNotifier==null) {
            if (other.EventNotifier != null) return false;
        } else if (!EventNotifier.equals(other.EventNotifier)) return false;
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
                + ((EventNotifier == null) ? 0 : EventNotifier.hashCode());
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
		return "ObjectAttributes: "+ObjectUtils.printFieldsDeep(this);
	}

}
