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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class SubscriptionAcknowledgement extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SubscriptionAcknowledgement);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SubscriptionAcknowledgement_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SubscriptionAcknowledgement_Encoding_DefaultXml);
	
    protected UnsignedInteger SubscriptionId;
    protected UnsignedInteger SequenceNumber;
    
    public SubscriptionAcknowledgement() {}
    
    public SubscriptionAcknowledgement(UnsignedInteger SubscriptionId, UnsignedInteger SequenceNumber)
    {
        this.SubscriptionId = SubscriptionId;
        this.SequenceNumber = SequenceNumber;
    }
    
    public UnsignedInteger getSubscriptionId()
    {
        return SubscriptionId;
    }
    
    public void setSubscriptionId(UnsignedInteger SubscriptionId)
    {
        this.SubscriptionId = SubscriptionId;
    }
    
    public UnsignedInteger getSequenceNumber()
    {
        return SequenceNumber;
    }
    
    public void setSequenceNumber(UnsignedInteger SequenceNumber)
    {
        this.SequenceNumber = SequenceNumber;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SubscriptionAcknowledgement
      */
    public SubscriptionAcknowledgement clone()
    {
        SubscriptionAcknowledgement result = new SubscriptionAcknowledgement();
        result.SubscriptionId = SubscriptionId;
        result.SequenceNumber = SequenceNumber;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SubscriptionAcknowledgement other = (SubscriptionAcknowledgement) obj;
        if (SubscriptionId==null) {
            if (other.SubscriptionId != null) return false;
        } else if (!SubscriptionId.equals(other.SubscriptionId)) return false;
        if (SequenceNumber==null) {
            if (other.SequenceNumber != null) return false;
        } else if (!SequenceNumber.equals(other.SequenceNumber)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((SubscriptionId == null) ? 0 : SubscriptionId.hashCode());
        result = prime * result
                + ((SequenceNumber == null) ? 0 : SequenceNumber.hashCode());
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
		return "SubscriptionAcknowledgement: "+ObjectUtils.printFieldsDeep(this);
	}

}
