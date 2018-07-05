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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.KeyValuePair;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.utils.AbstractStructure;



public class PubSubGroupDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.PubSubGroupDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.PubSubGroupDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.PubSubGroupDataType_Encoding_DefaultXml);
	
    protected String Name;
    protected Boolean Enabled;
    protected MessageSecurityMode SecurityMode;
    protected String SecurityGroupId;
    protected EndpointDescription[] SecurityKeyServices;
    protected UnsignedInteger MaxNetworkMessageSize;
    protected KeyValuePair[] GroupProperties;
    
    public PubSubGroupDataType() {}
    
    public PubSubGroupDataType(String Name, Boolean Enabled, MessageSecurityMode SecurityMode, String SecurityGroupId, EndpointDescription[] SecurityKeyServices, UnsignedInteger MaxNetworkMessageSize, KeyValuePair[] GroupProperties)
    {
        this.Name = Name;
        this.Enabled = Enabled;
        this.SecurityMode = SecurityMode;
        this.SecurityGroupId = SecurityGroupId;
        this.SecurityKeyServices = SecurityKeyServices;
        this.MaxNetworkMessageSize = MaxNetworkMessageSize;
        this.GroupProperties = GroupProperties;
    }
    
    public String getName()
    {
        return Name;
    }
    
    public void setName(String Name)
    {
        this.Name = Name;
    }
    
    public Boolean getEnabled()
    {
        return Enabled;
    }
    
    public void setEnabled(Boolean Enabled)
    {
        this.Enabled = Enabled;
    }
    
    public MessageSecurityMode getSecurityMode()
    {
        return SecurityMode;
    }
    
    public void setSecurityMode(MessageSecurityMode SecurityMode)
    {
        this.SecurityMode = SecurityMode;
    }
    
    public String getSecurityGroupId()
    {
        return SecurityGroupId;
    }
    
    public void setSecurityGroupId(String SecurityGroupId)
    {
        this.SecurityGroupId = SecurityGroupId;
    }
    
    public EndpointDescription[] getSecurityKeyServices()
    {
        return SecurityKeyServices;
    }
    
    public void setSecurityKeyServices(EndpointDescription[] SecurityKeyServices)
    {
        this.SecurityKeyServices = SecurityKeyServices;
    }
    
    public UnsignedInteger getMaxNetworkMessageSize()
    {
        return MaxNetworkMessageSize;
    }
    
    public void setMaxNetworkMessageSize(UnsignedInteger MaxNetworkMessageSize)
    {
        this.MaxNetworkMessageSize = MaxNetworkMessageSize;
    }
    
    public KeyValuePair[] getGroupProperties()
    {
        return GroupProperties;
    }
    
    public void setGroupProperties(KeyValuePair[] GroupProperties)
    {
        this.GroupProperties = GroupProperties;
    }
    
    /**
      * Deep clone
      *
      * @return cloned PubSubGroupDataType
      */
    public PubSubGroupDataType clone()
    {
        PubSubGroupDataType result = (PubSubGroupDataType) super.clone();
        result.Name = Name;
        result.Enabled = Enabled;
        result.SecurityMode = SecurityMode;
        result.SecurityGroupId = SecurityGroupId;
        if (SecurityKeyServices!=null) {
            result.SecurityKeyServices = new EndpointDescription[SecurityKeyServices.length];
            for (int i=0; i<SecurityKeyServices.length; i++)
                result.SecurityKeyServices[i] = SecurityKeyServices[i].clone();
        }
        result.MaxNetworkMessageSize = MaxNetworkMessageSize;
        if (GroupProperties!=null) {
            result.GroupProperties = new KeyValuePair[GroupProperties.length];
            for (int i=0; i<GroupProperties.length; i++)
                result.GroupProperties[i] = GroupProperties[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PubSubGroupDataType other = (PubSubGroupDataType) obj;
        if (Name==null) {
            if (other.Name != null) return false;
        } else if (!Name.equals(other.Name)) return false;
        if (Enabled==null) {
            if (other.Enabled != null) return false;
        } else if (!Enabled.equals(other.Enabled)) return false;
        if (SecurityMode==null) {
            if (other.SecurityMode != null) return false;
        } else if (!SecurityMode.equals(other.SecurityMode)) return false;
        if (SecurityGroupId==null) {
            if (other.SecurityGroupId != null) return false;
        } else if (!SecurityGroupId.equals(other.SecurityGroupId)) return false;
        if (SecurityKeyServices==null) {
            if (other.SecurityKeyServices != null) return false;
        } else if (!Arrays.equals(SecurityKeyServices, other.SecurityKeyServices)) return false;
        if (MaxNetworkMessageSize==null) {
            if (other.MaxNetworkMessageSize != null) return false;
        } else if (!MaxNetworkMessageSize.equals(other.MaxNetworkMessageSize)) return false;
        if (GroupProperties==null) {
            if (other.GroupProperties != null) return false;
        } else if (!Arrays.equals(GroupProperties, other.GroupProperties)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Name == null) ? 0 : Name.hashCode());
        result = prime * result
                + ((Enabled == null) ? 0 : Enabled.hashCode());
        result = prime * result
                + ((SecurityMode == null) ? 0 : SecurityMode.hashCode());
        result = prime * result
                + ((SecurityGroupId == null) ? 0 : SecurityGroupId.hashCode());
        result = prime * result
                + ((SecurityKeyServices == null) ? 0 : Arrays.hashCode(SecurityKeyServices));
        result = prime * result
                + ((MaxNetworkMessageSize == null) ? 0 : MaxNetworkMessageSize.hashCode());
        result = prime * result
                + ((GroupProperties == null) ? 0 : Arrays.hashCode(GroupProperties));
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
		return "PubSubGroupDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
