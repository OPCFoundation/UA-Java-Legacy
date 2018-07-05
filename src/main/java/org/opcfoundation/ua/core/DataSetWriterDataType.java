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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.core.KeyValuePair;
import org.opcfoundation.ua.utils.AbstractStructure;



public class DataSetWriterDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DataSetWriterDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DataSetWriterDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DataSetWriterDataType_Encoding_DefaultXml);
	
    protected String Name;
    protected Boolean Enabled;
    protected UnsignedShort DataSetWriterId;
    protected UnsignedInteger DataSetFieldContentMask;
    protected UnsignedInteger KeyFrameCount;
    protected String DataSetName;
    protected KeyValuePair[] DataSetWriterProperties;
    protected ExtensionObject TransportSettings;
    protected ExtensionObject MessageSettings;
    
    public DataSetWriterDataType() {}
    
    public DataSetWriterDataType(String Name, Boolean Enabled, UnsignedShort DataSetWriterId, UnsignedInteger DataSetFieldContentMask, UnsignedInteger KeyFrameCount, String DataSetName, KeyValuePair[] DataSetWriterProperties, ExtensionObject TransportSettings, ExtensionObject MessageSettings)
    {
        this.Name = Name;
        this.Enabled = Enabled;
        this.DataSetWriterId = DataSetWriterId;
        this.DataSetFieldContentMask = DataSetFieldContentMask;
        this.KeyFrameCount = KeyFrameCount;
        this.DataSetName = DataSetName;
        this.DataSetWriterProperties = DataSetWriterProperties;
        this.TransportSettings = TransportSettings;
        this.MessageSettings = MessageSettings;
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
    
    public UnsignedShort getDataSetWriterId()
    {
        return DataSetWriterId;
    }
    
    public void setDataSetWriterId(UnsignedShort DataSetWriterId)
    {
        this.DataSetWriterId = DataSetWriterId;
    }
    
    public UnsignedInteger getDataSetFieldContentMask()
    {
        return DataSetFieldContentMask;
    }
    
    public void setDataSetFieldContentMask(UnsignedInteger DataSetFieldContentMask)
    {
        this.DataSetFieldContentMask = DataSetFieldContentMask;
    }
    
    public UnsignedInteger getKeyFrameCount()
    {
        return KeyFrameCount;
    }
    
    public void setKeyFrameCount(UnsignedInteger KeyFrameCount)
    {
        this.KeyFrameCount = KeyFrameCount;
    }
    
    public String getDataSetName()
    {
        return DataSetName;
    }
    
    public void setDataSetName(String DataSetName)
    {
        this.DataSetName = DataSetName;
    }
    
    public KeyValuePair[] getDataSetWriterProperties()
    {
        return DataSetWriterProperties;
    }
    
    public void setDataSetWriterProperties(KeyValuePair[] DataSetWriterProperties)
    {
        this.DataSetWriterProperties = DataSetWriterProperties;
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
    
    /**
      * Deep clone
      *
      * @return cloned DataSetWriterDataType
      */
    public DataSetWriterDataType clone()
    {
        DataSetWriterDataType result = (DataSetWriterDataType) super.clone();
        result.Name = Name;
        result.Enabled = Enabled;
        result.DataSetWriterId = DataSetWriterId;
        result.DataSetFieldContentMask = DataSetFieldContentMask;
        result.KeyFrameCount = KeyFrameCount;
        result.DataSetName = DataSetName;
        if (DataSetWriterProperties!=null) {
            result.DataSetWriterProperties = new KeyValuePair[DataSetWriterProperties.length];
            for (int i=0; i<DataSetWriterProperties.length; i++)
                result.DataSetWriterProperties[i] = DataSetWriterProperties[i].clone();
        }
        result.TransportSettings = TransportSettings;
        result.MessageSettings = MessageSettings;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DataSetWriterDataType other = (DataSetWriterDataType) obj;
        if (Name==null) {
            if (other.Name != null) return false;
        } else if (!Name.equals(other.Name)) return false;
        if (Enabled==null) {
            if (other.Enabled != null) return false;
        } else if (!Enabled.equals(other.Enabled)) return false;
        if (DataSetWriterId==null) {
            if (other.DataSetWriterId != null) return false;
        } else if (!DataSetWriterId.equals(other.DataSetWriterId)) return false;
        if (DataSetFieldContentMask==null) {
            if (other.DataSetFieldContentMask != null) return false;
        } else if (!DataSetFieldContentMask.equals(other.DataSetFieldContentMask)) return false;
        if (KeyFrameCount==null) {
            if (other.KeyFrameCount != null) return false;
        } else if (!KeyFrameCount.equals(other.KeyFrameCount)) return false;
        if (DataSetName==null) {
            if (other.DataSetName != null) return false;
        } else if (!DataSetName.equals(other.DataSetName)) return false;
        if (DataSetWriterProperties==null) {
            if (other.DataSetWriterProperties != null) return false;
        } else if (!Arrays.equals(DataSetWriterProperties, other.DataSetWriterProperties)) return false;
        if (TransportSettings==null) {
            if (other.TransportSettings != null) return false;
        } else if (!TransportSettings.equals(other.TransportSettings)) return false;
        if (MessageSettings==null) {
            if (other.MessageSettings != null) return false;
        } else if (!MessageSettings.equals(other.MessageSettings)) return false;
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
                + ((DataSetWriterId == null) ? 0 : DataSetWriterId.hashCode());
        result = prime * result
                + ((DataSetFieldContentMask == null) ? 0 : DataSetFieldContentMask.hashCode());
        result = prime * result
                + ((KeyFrameCount == null) ? 0 : KeyFrameCount.hashCode());
        result = prime * result
                + ((DataSetName == null) ? 0 : DataSetName.hashCode());
        result = prime * result
                + ((DataSetWriterProperties == null) ? 0 : Arrays.hashCode(DataSetWriterProperties));
        result = prime * result
                + ((TransportSettings == null) ? 0 : TransportSettings.hashCode());
        result = prime * result
                + ((MessageSettings == null) ? 0 : MessageSettings.hashCode());
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
		return "DataSetWriterDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
