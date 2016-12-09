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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class NotificationMessage extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.NotificationMessage);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.NotificationMessage_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.NotificationMessage_Encoding_DefaultXml);
	
    protected UnsignedInteger SequenceNumber;
    protected DateTime PublishTime;
    protected ExtensionObject[] NotificationData;
    
    public NotificationMessage() {}
    
    public NotificationMessage(UnsignedInteger SequenceNumber, DateTime PublishTime, ExtensionObject[] NotificationData)
    {
        this.SequenceNumber = SequenceNumber;
        this.PublishTime = PublishTime;
        this.NotificationData = NotificationData;
    }
    
    public UnsignedInteger getSequenceNumber()
    {
        return SequenceNumber;
    }
    
    public void setSequenceNumber(UnsignedInteger SequenceNumber)
    {
        this.SequenceNumber = SequenceNumber;
    }
    
    public DateTime getPublishTime()
    {
        return PublishTime;
    }
    
    public void setPublishTime(DateTime PublishTime)
    {
        this.PublishTime = PublishTime;
    }
    
    public ExtensionObject[] getNotificationData()
    {
        return NotificationData;
    }
    
    public void setNotificationData(ExtensionObject[] NotificationData)
    {
        this.NotificationData = NotificationData;
    }
    
    /**
      * Deep clone
      *
      * @return cloned NotificationMessage
      */
    public NotificationMessage clone()
    {
        NotificationMessage result = (NotificationMessage) super.clone();
        result.SequenceNumber = SequenceNumber;
        result.PublishTime = PublishTime;
        result.NotificationData = NotificationData==null ? null : NotificationData.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        NotificationMessage other = (NotificationMessage) obj;
        if (SequenceNumber==null) {
            if (other.SequenceNumber != null) return false;
        } else if (!SequenceNumber.equals(other.SequenceNumber)) return false;
        if (PublishTime==null) {
            if (other.PublishTime != null) return false;
        } else if (!PublishTime.equals(other.PublishTime)) return false;
        if (NotificationData==null) {
            if (other.NotificationData != null) return false;
        } else if (!Arrays.equals(NotificationData, other.NotificationData)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((SequenceNumber == null) ? 0 : SequenceNumber.hashCode());
        result = prime * result
                + ((PublishTime == null) ? 0 : PublishTime.hashCode());
        result = prime * result
                + ((NotificationData == null) ? 0 : Arrays.hashCode(NotificationData));
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
		return "NotificationMessage: "+ObjectUtils.printFieldsDeep(this);
	}

}
