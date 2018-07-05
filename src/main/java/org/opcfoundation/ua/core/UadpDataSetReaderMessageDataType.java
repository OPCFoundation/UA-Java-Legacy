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
import java.util.UUID;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.core.DataSetReaderMessageDataType;



public class UadpDataSetReaderMessageDataType extends DataSetReaderMessageDataType {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.UadpDataSetReaderMessageDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.UadpDataSetReaderMessageDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.UadpDataSetReaderMessageDataType_Encoding_DefaultXml);
	
    protected UnsignedInteger GroupVersion;
    protected UnsignedShort NetworkMessageNumber;
    protected UnsignedShort DataSetOffset;
    protected UUID DataSetClassId;
    protected UnsignedInteger NetworkMessageContentMask;
    protected UnsignedInteger DataSetMessageContentMask;
    protected Double PublishingInterval;
    protected Double ReceiveOffset;
    protected Double ProcessingOffset;
    
    public UadpDataSetReaderMessageDataType() {}
    
    public UadpDataSetReaderMessageDataType(UnsignedInteger GroupVersion, UnsignedShort NetworkMessageNumber, UnsignedShort DataSetOffset, UUID DataSetClassId, UnsignedInteger NetworkMessageContentMask, UnsignedInteger DataSetMessageContentMask, Double PublishingInterval, Double ReceiveOffset, Double ProcessingOffset)
    {
        this.GroupVersion = GroupVersion;
        this.NetworkMessageNumber = NetworkMessageNumber;
        this.DataSetOffset = DataSetOffset;
        this.DataSetClassId = DataSetClassId;
        this.NetworkMessageContentMask = NetworkMessageContentMask;
        this.DataSetMessageContentMask = DataSetMessageContentMask;
        this.PublishingInterval = PublishingInterval;
        this.ReceiveOffset = ReceiveOffset;
        this.ProcessingOffset = ProcessingOffset;
    }
    
    public UnsignedInteger getGroupVersion()
    {
        return GroupVersion;
    }
    
    public void setGroupVersion(UnsignedInteger GroupVersion)
    {
        this.GroupVersion = GroupVersion;
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
    
    public UUID getDataSetClassId()
    {
        return DataSetClassId;
    }
    
    public void setDataSetClassId(UUID DataSetClassId)
    {
        this.DataSetClassId = DataSetClassId;
    }
    
    public UnsignedInteger getNetworkMessageContentMask()
    {
        return NetworkMessageContentMask;
    }
    
    public void setNetworkMessageContentMask(UnsignedInteger NetworkMessageContentMask)
    {
        this.NetworkMessageContentMask = NetworkMessageContentMask;
    }
    
    public UnsignedInteger getDataSetMessageContentMask()
    {
        return DataSetMessageContentMask;
    }
    
    public void setDataSetMessageContentMask(UnsignedInteger DataSetMessageContentMask)
    {
        this.DataSetMessageContentMask = DataSetMessageContentMask;
    }
    
    public Double getPublishingInterval()
    {
        return PublishingInterval;
    }
    
    public void setPublishingInterval(Double PublishingInterval)
    {
        this.PublishingInterval = PublishingInterval;
    }
    
    public Double getReceiveOffset()
    {
        return ReceiveOffset;
    }
    
    public void setReceiveOffset(Double ReceiveOffset)
    {
        this.ReceiveOffset = ReceiveOffset;
    }
    
    public Double getProcessingOffset()
    {
        return ProcessingOffset;
    }
    
    public void setProcessingOffset(Double ProcessingOffset)
    {
        this.ProcessingOffset = ProcessingOffset;
    }
    
    /**
      * Deep clone
      *
      * @return cloned UadpDataSetReaderMessageDataType
      */
    public UadpDataSetReaderMessageDataType clone()
    {
        UadpDataSetReaderMessageDataType result = (UadpDataSetReaderMessageDataType) super.clone();
        result.GroupVersion = GroupVersion;
        result.NetworkMessageNumber = NetworkMessageNumber;
        result.DataSetOffset = DataSetOffset;
        result.DataSetClassId = DataSetClassId;
        result.NetworkMessageContentMask = NetworkMessageContentMask;
        result.DataSetMessageContentMask = DataSetMessageContentMask;
        result.PublishingInterval = PublishingInterval;
        result.ReceiveOffset = ReceiveOffset;
        result.ProcessingOffset = ProcessingOffset;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UadpDataSetReaderMessageDataType other = (UadpDataSetReaderMessageDataType) obj;
        if (GroupVersion==null) {
            if (other.GroupVersion != null) return false;
        } else if (!GroupVersion.equals(other.GroupVersion)) return false;
        if (NetworkMessageNumber==null) {
            if (other.NetworkMessageNumber != null) return false;
        } else if (!NetworkMessageNumber.equals(other.NetworkMessageNumber)) return false;
        if (DataSetOffset==null) {
            if (other.DataSetOffset != null) return false;
        } else if (!DataSetOffset.equals(other.DataSetOffset)) return false;
        if (DataSetClassId==null) {
            if (other.DataSetClassId != null) return false;
        } else if (!DataSetClassId.equals(other.DataSetClassId)) return false;
        if (NetworkMessageContentMask==null) {
            if (other.NetworkMessageContentMask != null) return false;
        } else if (!NetworkMessageContentMask.equals(other.NetworkMessageContentMask)) return false;
        if (DataSetMessageContentMask==null) {
            if (other.DataSetMessageContentMask != null) return false;
        } else if (!DataSetMessageContentMask.equals(other.DataSetMessageContentMask)) return false;
        if (PublishingInterval==null) {
            if (other.PublishingInterval != null) return false;
        } else if (!PublishingInterval.equals(other.PublishingInterval)) return false;
        if (ReceiveOffset==null) {
            if (other.ReceiveOffset != null) return false;
        } else if (!ReceiveOffset.equals(other.ReceiveOffset)) return false;
        if (ProcessingOffset==null) {
            if (other.ProcessingOffset != null) return false;
        } else if (!ProcessingOffset.equals(other.ProcessingOffset)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((GroupVersion == null) ? 0 : GroupVersion.hashCode());
        result = prime * result
                + ((NetworkMessageNumber == null) ? 0 : NetworkMessageNumber.hashCode());
        result = prime * result
                + ((DataSetOffset == null) ? 0 : DataSetOffset.hashCode());
        result = prime * result
                + ((DataSetClassId == null) ? 0 : DataSetClassId.hashCode());
        result = prime * result
                + ((NetworkMessageContentMask == null) ? 0 : NetworkMessageContentMask.hashCode());
        result = prime * result
                + ((DataSetMessageContentMask == null) ? 0 : DataSetMessageContentMask.hashCode());
        result = prime * result
                + ((PublishingInterval == null) ? 0 : PublishingInterval.hashCode());
        result = prime * result
                + ((ReceiveOffset == null) ? 0 : ReceiveOffset.hashCode());
        result = prime * result
                + ((ProcessingOffset == null) ? 0 : ProcessingOffset.hashCode());
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
		return "UadpDataSetReaderMessageDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
