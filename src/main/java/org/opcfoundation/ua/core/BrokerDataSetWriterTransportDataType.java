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

import org.opcfoundation.ua.core.DataSetWriterTransportDataType;



public class BrokerDataSetWriterTransportDataType extends DataSetWriterTransportDataType {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.BrokerDataSetWriterTransportDataType.getValue());
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.BrokerDataSetWriterTransportDataType_Encoding_DefaultBinary.getValue());
	public static final ExpandedNodeId XML = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.BrokerDataSetWriterTransportDataType_Encoding_DefaultXml.getValue());
	
    protected String QueueName;
    protected String ResourceUri;
    protected String AuthenticationProfileUri;
    protected String MetaDataQueueName;
    protected Double MetaDataUpdateTime;
    
    public BrokerDataSetWriterTransportDataType() {}
    
    public BrokerDataSetWriterTransportDataType(String QueueName, String ResourceUri, String AuthenticationProfileUri, String MetaDataQueueName, Double MetaDataUpdateTime)
    {
        this.QueueName = QueueName;
        this.ResourceUri = ResourceUri;
        this.AuthenticationProfileUri = AuthenticationProfileUri;
        this.MetaDataQueueName = MetaDataQueueName;
        this.MetaDataUpdateTime = MetaDataUpdateTime;
    }
    
    public String getQueueName()
    {
        return QueueName;
    }
    
    public void setQueueName(String QueueName)
    {
        this.QueueName = QueueName;
    }
    
    public String getResourceUri()
    {
        return ResourceUri;
    }
    
    public void setResourceUri(String ResourceUri)
    {
        this.ResourceUri = ResourceUri;
    }
    
    public String getAuthenticationProfileUri()
    {
        return AuthenticationProfileUri;
    }
    
    public void setAuthenticationProfileUri(String AuthenticationProfileUri)
    {
        this.AuthenticationProfileUri = AuthenticationProfileUri;
    }
    
    public String getMetaDataQueueName()
    {
        return MetaDataQueueName;
    }
    
    public void setMetaDataQueueName(String MetaDataQueueName)
    {
        this.MetaDataQueueName = MetaDataQueueName;
    }
    
    public Double getMetaDataUpdateTime()
    {
        return MetaDataUpdateTime;
    }
    
    public void setMetaDataUpdateTime(Double MetaDataUpdateTime)
    {
        this.MetaDataUpdateTime = MetaDataUpdateTime;
    }
    
    /**
      * Deep clone
      *
      * @return cloned BrokerDataSetWriterTransportDataType
      */
    public BrokerDataSetWriterTransportDataType clone()
    {
        BrokerDataSetWriterTransportDataType result = (BrokerDataSetWriterTransportDataType) super.clone();
        result.QueueName = QueueName;
        result.ResourceUri = ResourceUri;
        result.AuthenticationProfileUri = AuthenticationProfileUri;
        result.MetaDataQueueName = MetaDataQueueName;
        result.MetaDataUpdateTime = MetaDataUpdateTime;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BrokerDataSetWriterTransportDataType other = (BrokerDataSetWriterTransportDataType) obj;
        if (QueueName==null) {
            if (other.QueueName != null) return false;
        } else if (!QueueName.equals(other.QueueName)) return false;
        if (ResourceUri==null) {
            if (other.ResourceUri != null) return false;
        } else if (!ResourceUri.equals(other.ResourceUri)) return false;
        if (AuthenticationProfileUri==null) {
            if (other.AuthenticationProfileUri != null) return false;
        } else if (!AuthenticationProfileUri.equals(other.AuthenticationProfileUri)) return false;
        if (MetaDataQueueName==null) {
            if (other.MetaDataQueueName != null) return false;
        } else if (!MetaDataQueueName.equals(other.MetaDataQueueName)) return false;
        if (MetaDataUpdateTime==null) {
            if (other.MetaDataUpdateTime != null) return false;
        } else if (!MetaDataUpdateTime.equals(other.MetaDataUpdateTime)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((QueueName == null) ? 0 : QueueName.hashCode());
        result = prime * result
                + ((ResourceUri == null) ? 0 : ResourceUri.hashCode());
        result = prime * result
                + ((AuthenticationProfileUri == null) ? 0 : AuthenticationProfileUri.hashCode());
        result = prime * result
                + ((MetaDataQueueName == null) ? 0 : MetaDataQueueName.hashCode());
        result = prime * result
                + ((MetaDataUpdateTime == null) ? 0 : MetaDataUpdateTime.hashCode());
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
		return "BrokerDataSetWriterTransportDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
