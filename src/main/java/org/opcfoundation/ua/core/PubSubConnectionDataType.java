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
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.KeyValuePair;
import org.opcfoundation.ua.core.ReaderGroupDataType;
import org.opcfoundation.ua.core.WriterGroupDataType;
import org.opcfoundation.ua.utils.AbstractStructure;



public class PubSubConnectionDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.PubSubConnectionDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.PubSubConnectionDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.PubSubConnectionDataType_Encoding_DefaultXml);
	
    protected String Name;
    protected Boolean Enabled;
    protected Variant PublisherId;
    protected String TransportProfileUri;
    protected ExtensionObject Address;
    protected KeyValuePair[] ConnectionProperties;
    protected ExtensionObject TransportSettings;
    protected WriterGroupDataType[] WriterGroups;
    protected ReaderGroupDataType[] ReaderGroups;
    
    public PubSubConnectionDataType() {}
    
    public PubSubConnectionDataType(String Name, Boolean Enabled, Variant PublisherId, String TransportProfileUri, ExtensionObject Address, KeyValuePair[] ConnectionProperties, ExtensionObject TransportSettings, WriterGroupDataType[] WriterGroups, ReaderGroupDataType[] ReaderGroups)
    {
        this.Name = Name;
        this.Enabled = Enabled;
        this.PublisherId = PublisherId;
        this.TransportProfileUri = TransportProfileUri;
        this.Address = Address;
        this.ConnectionProperties = ConnectionProperties;
        this.TransportSettings = TransportSettings;
        this.WriterGroups = WriterGroups;
        this.ReaderGroups = ReaderGroups;
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
    
    public String getTransportProfileUri()
    {
        return TransportProfileUri;
    }
    
    public void setTransportProfileUri(String TransportProfileUri)
    {
        this.TransportProfileUri = TransportProfileUri;
    }
    
    public ExtensionObject getAddress()
    {
        return Address;
    }
    
    public void setAddress(ExtensionObject Address)
    {
        this.Address = Address;
    }
    
    public KeyValuePair[] getConnectionProperties()
    {
        return ConnectionProperties;
    }
    
    public void setConnectionProperties(KeyValuePair[] ConnectionProperties)
    {
        this.ConnectionProperties = ConnectionProperties;
    }
    
    public ExtensionObject getTransportSettings()
    {
        return TransportSettings;
    }
    
    public void setTransportSettings(ExtensionObject TransportSettings)
    {
        this.TransportSettings = TransportSettings;
    }
    
    public WriterGroupDataType[] getWriterGroups()
    {
        return WriterGroups;
    }
    
    public void setWriterGroups(WriterGroupDataType[] WriterGroups)
    {
        this.WriterGroups = WriterGroups;
    }
    
    public ReaderGroupDataType[] getReaderGroups()
    {
        return ReaderGroups;
    }
    
    public void setReaderGroups(ReaderGroupDataType[] ReaderGroups)
    {
        this.ReaderGroups = ReaderGroups;
    }
    
    /**
      * Deep clone
      *
      * @return cloned PubSubConnectionDataType
      */
    public PubSubConnectionDataType clone()
    {
        PubSubConnectionDataType result = (PubSubConnectionDataType) super.clone();
        result.Name = Name;
        result.Enabled = Enabled;
        result.PublisherId = PublisherId;
        result.TransportProfileUri = TransportProfileUri;
        result.Address = Address;
        if (ConnectionProperties!=null) {
            result.ConnectionProperties = new KeyValuePair[ConnectionProperties.length];
            for (int i=0; i<ConnectionProperties.length; i++)
                result.ConnectionProperties[i] = ConnectionProperties[i].clone();
        }
        result.TransportSettings = TransportSettings;
        if (WriterGroups!=null) {
            result.WriterGroups = new WriterGroupDataType[WriterGroups.length];
            for (int i=0; i<WriterGroups.length; i++)
                result.WriterGroups[i] = WriterGroups[i].clone();
        }
        if (ReaderGroups!=null) {
            result.ReaderGroups = new ReaderGroupDataType[ReaderGroups.length];
            for (int i=0; i<ReaderGroups.length; i++)
                result.ReaderGroups[i] = ReaderGroups[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PubSubConnectionDataType other = (PubSubConnectionDataType) obj;
        if (Name==null) {
            if (other.Name != null) return false;
        } else if (!Name.equals(other.Name)) return false;
        if (Enabled==null) {
            if (other.Enabled != null) return false;
        } else if (!Enabled.equals(other.Enabled)) return false;
        if (PublisherId==null) {
            if (other.PublisherId != null) return false;
        } else if (!PublisherId.equals(other.PublisherId)) return false;
        if (TransportProfileUri==null) {
            if (other.TransportProfileUri != null) return false;
        } else if (!TransportProfileUri.equals(other.TransportProfileUri)) return false;
        if (Address==null) {
            if (other.Address != null) return false;
        } else if (!Address.equals(other.Address)) return false;
        if (ConnectionProperties==null) {
            if (other.ConnectionProperties != null) return false;
        } else if (!Arrays.equals(ConnectionProperties, other.ConnectionProperties)) return false;
        if (TransportSettings==null) {
            if (other.TransportSettings != null) return false;
        } else if (!TransportSettings.equals(other.TransportSettings)) return false;
        if (WriterGroups==null) {
            if (other.WriterGroups != null) return false;
        } else if (!Arrays.equals(WriterGroups, other.WriterGroups)) return false;
        if (ReaderGroups==null) {
            if (other.ReaderGroups != null) return false;
        } else if (!Arrays.equals(ReaderGroups, other.ReaderGroups)) return false;
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
                + ((TransportProfileUri == null) ? 0 : TransportProfileUri.hashCode());
        result = prime * result
                + ((Address == null) ? 0 : Address.hashCode());
        result = prime * result
                + ((ConnectionProperties == null) ? 0 : Arrays.hashCode(ConnectionProperties));
        result = prime * result
                + ((TransportSettings == null) ? 0 : TransportSettings.hashCode());
        result = prime * result
                + ((WriterGroups == null) ? 0 : Arrays.hashCode(WriterGroups));
        result = prime * result
                + ((ReaderGroups == null) ? 0 : Arrays.hashCode(ReaderGroups));
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
		return "PubSubConnectionDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
