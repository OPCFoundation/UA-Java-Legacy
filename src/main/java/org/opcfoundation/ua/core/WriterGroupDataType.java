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
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.core.DataSetWriterDataType;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.KeyValuePair;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.PubSubGroupDataType;



public class WriterGroupDataType extends PubSubGroupDataType {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.WriterGroupDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.WriterGroupDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.WriterGroupDataType_Encoding_DefaultXml);
	
    protected UnsignedShort WriterGroupId;
    protected Double PublishingInterval;
    protected Double KeepAliveTime;
    protected UnsignedByte Priority;
    protected String[] LocaleIds;
    protected ExtensionObject TransportSettings;
    protected ExtensionObject MessageSettings;
    protected DataSetWriterDataType[] DataSetWriters;
    
    public WriterGroupDataType() {}
    
    public WriterGroupDataType(String Name, Boolean Enabled, MessageSecurityMode SecurityMode, String SecurityGroupId, EndpointDescription[] SecurityKeyServices, UnsignedInteger MaxNetworkMessageSize, KeyValuePair[] GroupProperties, UnsignedShort WriterGroupId, Double PublishingInterval, Double KeepAliveTime, UnsignedByte Priority, String[] LocaleIds, ExtensionObject TransportSettings, ExtensionObject MessageSettings, DataSetWriterDataType[] DataSetWriters)
    {
        super(Name, Enabled, SecurityMode, SecurityGroupId, SecurityKeyServices, MaxNetworkMessageSize, GroupProperties);
        this.WriterGroupId = WriterGroupId;
        this.PublishingInterval = PublishingInterval;
        this.KeepAliveTime = KeepAliveTime;
        this.Priority = Priority;
        this.LocaleIds = LocaleIds;
        this.TransportSettings = TransportSettings;
        this.MessageSettings = MessageSettings;
        this.DataSetWriters = DataSetWriters;
    }
    
    public UnsignedShort getWriterGroupId()
    {
        return WriterGroupId;
    }
    
    public void setWriterGroupId(UnsignedShort WriterGroupId)
    {
        this.WriterGroupId = WriterGroupId;
    }
    
    public Double getPublishingInterval()
    {
        return PublishingInterval;
    }
    
    public void setPublishingInterval(Double PublishingInterval)
    {
        this.PublishingInterval = PublishingInterval;
    }
    
    public Double getKeepAliveTime()
    {
        return KeepAliveTime;
    }
    
    public void setKeepAliveTime(Double KeepAliveTime)
    {
        this.KeepAliveTime = KeepAliveTime;
    }
    
    public UnsignedByte getPriority()
    {
        return Priority;
    }
    
    public void setPriority(UnsignedByte Priority)
    {
        this.Priority = Priority;
    }
    
    public String[] getLocaleIds()
    {
        return LocaleIds;
    }
    
    public void setLocaleIds(String[] LocaleIds)
    {
        this.LocaleIds = LocaleIds;
    }
    
    public ExtensionObject getTransportSettings()
    {
        return TransportSettings;
    }
    
    public void setTransportSettings(ExtensionObject TransportSettings)
    {
        this.TransportSettings = TransportSettings;
    }
    
    public ExtensionObject getMessageSettings()
    {
        return MessageSettings;
    }
    
    public void setMessageSettings(ExtensionObject MessageSettings)
    {
        this.MessageSettings = MessageSettings;
    }
    
    public DataSetWriterDataType[] getDataSetWriters()
    {
        return DataSetWriters;
    }
    
    public void setDataSetWriters(DataSetWriterDataType[] DataSetWriters)
    {
        this.DataSetWriters = DataSetWriters;
    }
    
    /**
      * Deep clone
      *
      * @return cloned WriterGroupDataType
      */
    public WriterGroupDataType clone()
    {
        WriterGroupDataType result = (WriterGroupDataType) super.clone();
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
        result.WriterGroupId = WriterGroupId;
        result.PublishingInterval = PublishingInterval;
        result.KeepAliveTime = KeepAliveTime;
        result.Priority = Priority;
        result.LocaleIds = LocaleIds==null ? null : LocaleIds.clone();
        result.TransportSettings = TransportSettings;
        result.MessageSettings = MessageSettings;
        if (DataSetWriters!=null) {
            result.DataSetWriters = new DataSetWriterDataType[DataSetWriters.length];
            for (int i=0; i<DataSetWriters.length; i++)
                result.DataSetWriters[i] = DataSetWriters[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        WriterGroupDataType other = (WriterGroupDataType) obj;
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
        if (WriterGroupId==null) {
            if (other.WriterGroupId != null) return false;
        } else if (!WriterGroupId.equals(other.WriterGroupId)) return false;
        if (PublishingInterval==null) {
            if (other.PublishingInterval != null) return false;
        } else if (!PublishingInterval.equals(other.PublishingInterval)) return false;
        if (KeepAliveTime==null) {
            if (other.KeepAliveTime != null) return false;
        } else if (!KeepAliveTime.equals(other.KeepAliveTime)) return false;
        if (Priority==null) {
            if (other.Priority != null) return false;
        } else if (!Priority.equals(other.Priority)) return false;
        if (LocaleIds==null) {
            if (other.LocaleIds != null) return false;
        } else if (!Arrays.equals(LocaleIds, other.LocaleIds)) return false;
        if (TransportSettings==null) {
            if (other.TransportSettings != null) return false;
        } else if (!TransportSettings.equals(other.TransportSettings)) return false;
        if (MessageSettings==null) {
            if (other.MessageSettings != null) return false;
        } else if (!MessageSettings.equals(other.MessageSettings)) return false;
        if (DataSetWriters==null) {
            if (other.DataSetWriters != null) return false;
        } else if (!Arrays.equals(DataSetWriters, other.DataSetWriters)) return false;
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
        result = prime * result
                + ((WriterGroupId == null) ? 0 : WriterGroupId.hashCode());
        result = prime * result
                + ((PublishingInterval == null) ? 0 : PublishingInterval.hashCode());
        result = prime * result
                + ((KeepAliveTime == null) ? 0 : KeepAliveTime.hashCode());
        result = prime * result
                + ((Priority == null) ? 0 : Priority.hashCode());
        result = prime * result
                + ((LocaleIds == null) ? 0 : Arrays.hashCode(LocaleIds));
        result = prime * result
                + ((TransportSettings == null) ? 0 : TransportSettings.hashCode());
        result = prime * result
                + ((MessageSettings == null) ? 0 : MessageSettings.hashCode());
        result = prime * result
                + ((DataSetWriters == null) ? 0 : Arrays.hashCode(DataSetWriters));
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
		return "WriterGroupDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
