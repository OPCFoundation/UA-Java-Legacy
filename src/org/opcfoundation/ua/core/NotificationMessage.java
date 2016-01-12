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
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class NotificationMessage extends Object implements Structure, Cloneable {
	
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
        NotificationMessage result = new NotificationMessage();
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
