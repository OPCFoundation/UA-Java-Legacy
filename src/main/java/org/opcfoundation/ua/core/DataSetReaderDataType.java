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
import org.opcfoundation.ua.common.NamespaceTable;

import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.DataSetMetaDataType;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.KeyValuePair;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.utils.AbstractStructure;



public class DataSetReaderDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.DataSetReaderDataType.getValue());
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.DataSetReaderDataType_Encoding_DefaultBinary.getValue());
	public static final ExpandedNodeId XML = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.DataSetReaderDataType_Encoding_DefaultXml.getValue());
	
    protected String Name;
    protected Boolean Enabled;
    protected Variant PublisherId;
    protected UnsignedShort WriterGroupId;
    protected UnsignedShort DataSetWriterId;
    protected DataSetMetaDataType DataSetMetaData;
    protected UnsignedInteger DataSetFieldContentMask;
    protected Double MessageReceiveTimeout;
    protected UnsignedInteger KeyFrameCount;
    protected String HeaderLayoutUri;
    protected MessageSecurityMode SecurityMode;
    protected String SecurityGroupId;
    protected EndpointDescription[] SecurityKeyServices;
    protected KeyValuePair[] DataSetReaderProperties;
    protected ExtensionObject TransportSettings;
    protected ExtensionObject MessageSettings;
    protected ExtensionObject SubscribedDataSet;
    
    public DataSetReaderDataType() {}
    
    public DataSetReaderDataType(String Name, Boolean Enabled, Variant PublisherId, UnsignedShort WriterGroupId, UnsignedShort DataSetWriterId, DataSetMetaDataType DataSetMetaData, UnsignedInteger DataSetFieldContentMask, Double MessageReceiveTimeout, UnsignedInteger KeyFrameCount, String HeaderLayoutUri, MessageSecurityMode SecurityMode, String SecurityGroupId, EndpointDescription[] SecurityKeyServices, KeyValuePair[] DataSetReaderProperties, ExtensionObject TransportSettings, ExtensionObject MessageSettings, ExtensionObject SubscribedDataSet)
    {
        this.Name = Name;
        this.Enabled = Enabled;
        this.PublisherId = PublisherId;
        this.WriterGroupId = WriterGroupId;
        this.DataSetWriterId = DataSetWriterId;
        this.DataSetMetaData = DataSetMetaData;
        this.DataSetFieldContentMask = DataSetFieldContentMask;
        this.MessageReceiveTimeout = MessageReceiveTimeout;
        this.KeyFrameCount = KeyFrameCount;
        this.HeaderLayoutUri = HeaderLayoutUri;
        this.SecurityMode = SecurityMode;
        this.SecurityGroupId = SecurityGroupId;
        this.SecurityKeyServices = SecurityKeyServices;
        this.DataSetReaderProperties = DataSetReaderProperties;
        this.TransportSettings = TransportSettings;
        this.MessageSettings = MessageSettings;
        this.SubscribedDataSet = SubscribedDataSet;
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
    
    public Variant getPublisherId()
    {
        return PublisherId;
    }
    
    public void setPublisherId(Variant PublisherId)
    {
        this.PublisherId = PublisherId;
    }
    
    public UnsignedShort getWriterGroupId()
    {
        return WriterGroupId;
    }
    
    public void setWriterGroupId(UnsignedShort WriterGroupId)
    {
        this.WriterGroupId = WriterGroupId;
    }
    
    public UnsignedShort getDataSetWriterId()
    {
        return DataSetWriterId;
    }
    
    public void setDataSetWriterId(UnsignedShort DataSetWriterId)
    {
        this.DataSetWriterId = DataSetWriterId;
    }
    
    public DataSetMetaDataType getDataSetMetaData()
    {
        return DataSetMetaData;
    }
    
    public void setDataSetMetaData(DataSetMetaDataType DataSetMetaData)
    {
        this.DataSetMetaData = DataSetMetaData;
    }
    
    public UnsignedInteger getDataSetFieldContentMask()
    {
        return DataSetFieldContentMask;
    }
    
    public void setDataSetFieldContentMask(UnsignedInteger DataSetFieldContentMask)
    {
        this.DataSetFieldContentMask = DataSetFieldContentMask;
    }
    
    public Double getMessageReceiveTimeout()
    {
        return MessageReceiveTimeout;
    }
    
    public void setMessageReceiveTimeout(Double MessageReceiveTimeout)
    {
        this.MessageReceiveTimeout = MessageReceiveTimeout;
    }
    
    public UnsignedInteger getKeyFrameCount()
    {
        return KeyFrameCount;
    }
    
    public void setKeyFrameCount(UnsignedInteger KeyFrameCount)
    {
        this.KeyFrameCount = KeyFrameCount;
    }
    
    public String getHeaderLayoutUri()
    {
        return HeaderLayoutUri;
    }
    
    public void setHeaderLayoutUri(String HeaderLayoutUri)
    {
        this.HeaderLayoutUri = HeaderLayoutUri;
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
    
    public KeyValuePair[] getDataSetReaderProperties()
    {
        return DataSetReaderProperties;
    }
    
    public void setDataSetReaderProperties(KeyValuePair[] DataSetReaderProperties)
    {
        this.DataSetReaderProperties = DataSetReaderProperties;
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
    
    public ExtensionObject getSubscribedDataSet()
    {
        return SubscribedDataSet;
    }
    
    public void setSubscribedDataSet(ExtensionObject SubscribedDataSet)
    {
        this.SubscribedDataSet = SubscribedDataSet;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DataSetReaderDataType
      */
    public DataSetReaderDataType clone()
    {
        DataSetReaderDataType result = (DataSetReaderDataType) super.clone();
        result.Name = Name;
        result.Enabled = Enabled;
        result.PublisherId = PublisherId;
        result.WriterGroupId = WriterGroupId;
        result.DataSetWriterId = DataSetWriterId;
        result.DataSetMetaData = DataSetMetaData==null ? null : DataSetMetaData.clone();
        result.DataSetFieldContentMask = DataSetFieldContentMask;
        result.MessageReceiveTimeout = MessageReceiveTimeout;
        result.KeyFrameCount = KeyFrameCount;
        result.HeaderLayoutUri = HeaderLayoutUri;
        result.SecurityMode = SecurityMode;
        result.SecurityGroupId = SecurityGroupId;
        if (SecurityKeyServices!=null) {
            result.SecurityKeyServices = new EndpointDescription[SecurityKeyServices.length];
            for (int i=0; i<SecurityKeyServices.length; i++)
                result.SecurityKeyServices[i] = SecurityKeyServices[i].clone();
        }
        if (DataSetReaderProperties!=null) {
            result.DataSetReaderProperties = new KeyValuePair[DataSetReaderProperties.length];
            for (int i=0; i<DataSetReaderProperties.length; i++)
                result.DataSetReaderProperties[i] = DataSetReaderProperties[i].clone();
        }
        result.TransportSettings = TransportSettings;
        result.MessageSettings = MessageSettings;
        result.SubscribedDataSet = SubscribedDataSet;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DataSetReaderDataType other = (DataSetReaderDataType) obj;
        if (Name==null) {
            if (other.Name != null) return false;
        } else if (!Name.equals(other.Name)) return false;
        if (Enabled==null) {
            if (other.Enabled != null) return false;
        } else if (!Enabled.equals(other.Enabled)) return false;
        if (PublisherId==null) {
            if (other.PublisherId != null) return false;
        } else if (!PublisherId.equals(other.PublisherId)) return false;
        if (WriterGroupId==null) {
            if (other.WriterGroupId != null) return false;
        } else if (!WriterGroupId.equals(other.WriterGroupId)) return false;
        if (DataSetWriterId==null) {
            if (other.DataSetWriterId != null) return false;
        } else if (!DataSetWriterId.equals(other.DataSetWriterId)) return false;
        if (DataSetMetaData==null) {
            if (other.DataSetMetaData != null) return false;
        } else if (!DataSetMetaData.equals(other.DataSetMetaData)) return false;
        if (DataSetFieldContentMask==null) {
            if (other.DataSetFieldContentMask != null) return false;
        } else if (!DataSetFieldContentMask.equals(other.DataSetFieldContentMask)) return false;
        if (MessageReceiveTimeout==null) {
            if (other.MessageReceiveTimeout != null) return false;
        } else if (!MessageReceiveTimeout.equals(other.MessageReceiveTimeout)) return false;
        if (KeyFrameCount==null) {
            if (other.KeyFrameCount != null) return false;
        } else if (!KeyFrameCount.equals(other.KeyFrameCount)) return false;
        if (HeaderLayoutUri==null) {
            if (other.HeaderLayoutUri != null) return false;
        } else if (!HeaderLayoutUri.equals(other.HeaderLayoutUri)) return false;
        if (SecurityMode==null) {
            if (other.SecurityMode != null) return false;
        } else if (!SecurityMode.equals(other.SecurityMode)) return false;
        if (SecurityGroupId==null) {
            if (other.SecurityGroupId != null) return false;
        } else if (!SecurityGroupId.equals(other.SecurityGroupId)) return false;
        if (SecurityKeyServices==null) {
            if (other.SecurityKeyServices != null) return false;
        } else if (!Arrays.equals(SecurityKeyServices, other.SecurityKeyServices)) return false;
        if (DataSetReaderProperties==null) {
            if (other.DataSetReaderProperties != null) return false;
        } else if (!Arrays.equals(DataSetReaderProperties, other.DataSetReaderProperties)) return false;
        if (TransportSettings==null) {
            if (other.TransportSettings != null) return false;
        } else if (!TransportSettings.equals(other.TransportSettings)) return false;
        if (MessageSettings==null) {
            if (other.MessageSettings != null) return false;
        } else if (!MessageSettings.equals(other.MessageSettings)) return false;
        if (SubscribedDataSet==null) {
            if (other.SubscribedDataSet != null) return false;
        } else if (!SubscribedDataSet.equals(other.SubscribedDataSet)) return false;
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
                + ((PublisherId == null) ? 0 : PublisherId.hashCode());
        result = prime * result
                + ((WriterGroupId == null) ? 0 : WriterGroupId.hashCode());
        result = prime * result
                + ((DataSetWriterId == null) ? 0 : DataSetWriterId.hashCode());
        result = prime * result
                + ((DataSetMetaData == null) ? 0 : DataSetMetaData.hashCode());
        result = prime * result
                + ((DataSetFieldContentMask == null) ? 0 : DataSetFieldContentMask.hashCode());
        result = prime * result
                + ((MessageReceiveTimeout == null) ? 0 : MessageReceiveTimeout.hashCode());
        result = prime * result
                + ((KeyFrameCount == null) ? 0 : KeyFrameCount.hashCode());
        result = prime * result
                + ((HeaderLayoutUri == null) ? 0 : HeaderLayoutUri.hashCode());
        result = prime * result
                + ((SecurityMode == null) ? 0 : SecurityMode.hashCode());
        result = prime * result
                + ((SecurityGroupId == null) ? 0 : SecurityGroupId.hashCode());
        result = prime * result
                + ((SecurityKeyServices == null) ? 0 : Arrays.hashCode(SecurityKeyServices));
        result = prime * result
                + ((DataSetReaderProperties == null) ? 0 : Arrays.hashCode(DataSetReaderProperties));
        result = prime * result
                + ((TransportSettings == null) ? 0 : TransportSettings.hashCode());
        result = prime * result
                + ((MessageSettings == null) ? 0 : MessageSettings.hashCode());
        result = prime * result
                + ((SubscribedDataSet == null) ? 0 : SubscribedDataSet.hashCode());
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
		return "DataSetReaderDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
