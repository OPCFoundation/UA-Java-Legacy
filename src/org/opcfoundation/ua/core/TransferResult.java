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
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class TransferResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.TransferResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.TransferResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.TransferResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected UnsignedInteger[] AvailableSequenceNumbers;
    
    public TransferResult() {}
    
    public TransferResult(StatusCode StatusCode, UnsignedInteger[] AvailableSequenceNumbers)
    {
        this.StatusCode = StatusCode;
        this.AvailableSequenceNumbers = AvailableSequenceNumbers;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public UnsignedInteger[] getAvailableSequenceNumbers()
    {
        return AvailableSequenceNumbers;
    }
    
    public void setAvailableSequenceNumbers(UnsignedInteger[] AvailableSequenceNumbers)
    {
        this.AvailableSequenceNumbers = AvailableSequenceNumbers;
    }
    
    /**
      * Deep clone
      *
      * @return cloned TransferResult
      */
    public TransferResult clone()
    {
        TransferResult result = new TransferResult();
        result.StatusCode = StatusCode;
        result.AvailableSequenceNumbers = AvailableSequenceNumbers==null ? null : AvailableSequenceNumbers.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TransferResult other = (TransferResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (AvailableSequenceNumbers==null) {
            if (other.AvailableSequenceNumbers != null) return false;
        } else if (!Arrays.equals(AvailableSequenceNumbers, other.AvailableSequenceNumbers)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((StatusCode == null) ? 0 : StatusCode.hashCode());
        result = prime * result
                + ((AvailableSequenceNumbers == null) ? 0 : Arrays.hashCode(AvailableSequenceNumbers));
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
		return "TransferResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
