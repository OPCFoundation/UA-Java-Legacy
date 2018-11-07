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

import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.core.DataSetWriterMessageDataType;



public class UadpDataSetWriterMessageDataType extends DataSetWriterMessageDataType {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.UadpDataSetWriterMessageDataType.getValue());
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.UadpDataSetWriterMessageDataType_Encoding_DefaultBinary.getValue());
	public static final ExpandedNodeId XML = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.UadpDataSetWriterMessageDataType_Encoding_DefaultXml.getValue());
	
    protected UnsignedInteger DataSetMessageContentMask;
    protected UnsignedShort ConfiguredSize;
    protected UnsignedShort NetworkMessageNumber;
    protected UnsignedShort DataSetOffset;
    
    public UadpDataSetWriterMessageDataType() {}
    
    public UadpDataSetWriterMessageDataType(UnsignedInteger DataSetMessageContentMask, UnsignedShort ConfiguredSize, UnsignedShort NetworkMessageNumber, UnsignedShort DataSetOffset)
    {
        this.DataSetMessageContentMask = DataSetMessageContentMask;
        this.ConfiguredSize = ConfiguredSize;
        this.NetworkMessageNumber = NetworkMessageNumber;
        this.DataSetOffset = DataSetOffset;
    }
    
    public UnsignedInteger getDataSetMessageContentMask()
    {
        return DataSetMessageContentMask;
    }
    
    public void setDataSetMessageContentMask(UnsignedInteger DataSetMessageContentMask)
    {
        this.DataSetMessageContentMask = DataSetMessageContentMask;
    }
    
    public UnsignedShort getConfiguredSize()
    {
        return ConfiguredSize;
    }
    
    public void setConfiguredSize(UnsignedShort ConfiguredSize)
    {
        this.ConfiguredSize = ConfiguredSize;
    }
    
    public UnsignedShort getNetworkMessageNumber()
    {
        return NetworkMessageNumber;
    }
    
    public void setNetworkMessageNumber(UnsignedShort NetworkMessageNumber)
    {
        this.NetworkMessageNumber = NetworkMessageNumber;
    }
    
    public UnsignedShort getDataSetOffset()
    {
        return DataSetOffset;
    }
    
    public void setDataSetOffset(UnsignedShort DataSetOffset)
    {
        this.DataSetOffset = DataSetOffset;
    }
    
    /**
      * Deep clone
      *
      * @return cloned UadpDataSetWriterMessageDataType
      */
    public UadpDataSetWriterMessageDataType clone()
    {
        UadpDataSetWriterMessageDataType result = (UadpDataSetWriterMessageDataType) super.clone();
        result.DataSetMessageContentMask = DataSetMessageContentMask;
        result.ConfiguredSize = ConfiguredSize;
        result.NetworkMessageNumber = NetworkMessageNumber;
        result.DataSetOffset = DataSetOffset;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UadpDataSetWriterMessageDataType other = (UadpDataSetWriterMessageDataType) obj;
        if (DataSetMessageContentMask==null) {
            if (other.DataSetMessageContentMask != null) return false;
        } else if (!DataSetMessageContentMask.equals(other.DataSetMessageContentMask)) return false;
        if (ConfiguredSize==null) {
            if (other.ConfiguredSize != null) return false;
        } else if (!ConfiguredSize.equals(other.ConfiguredSize)) return false;
        if (NetworkMessageNumber==null) {
            if (other.NetworkMessageNumber != null) return false;
        } else if (!NetworkMessageNumber.equals(other.NetworkMessageNumber)) return false;
        if (DataSetOffset==null) {
            if (other.DataSetOffset != null) return false;
        } else if (!DataSetOffset.equals(other.DataSetOffset)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((DataSetMessageContentMask == null) ? 0 : DataSetMessageContentMask.hashCode());
        result = prime * result
                + ((ConfiguredSize == null) ? 0 : ConfiguredSize.hashCode());
        result = prime * result
                + ((NetworkMessageNumber == null) ? 0 : NetworkMessageNumber.hashCode());
        result = prime * result
                + ((DataSetOffset == null) ? 0 : DataSetOffset.hashCode());
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
		return "UadpDataSetWriterMessageDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
