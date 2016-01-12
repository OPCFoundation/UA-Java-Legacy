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
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.StatusCode;



public class HistoryReadResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.HistoryReadResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.HistoryReadResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.HistoryReadResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected byte[] ContinuationPoint;
    protected ExtensionObject HistoryData;
    
    public HistoryReadResult() {}
    
    public HistoryReadResult(StatusCode StatusCode, byte[] ContinuationPoint, ExtensionObject HistoryData)
    {
        this.StatusCode = StatusCode;
        this.ContinuationPoint = ContinuationPoint;
        this.HistoryData = HistoryData;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public byte[] getContinuationPoint()
    {
        return ContinuationPoint;
    }
    
    public void setContinuationPoint(byte[] ContinuationPoint)
    {
        this.ContinuationPoint = ContinuationPoint;
    }
    
    public ExtensionObject getHistoryData()
    {
        return HistoryData;
    }
    
    public void setHistoryData(ExtensionObject HistoryData)
    {
        this.HistoryData = HistoryData;
    }
    
    /**
      * Deep clone
      *
      * @return cloned HistoryReadResult
      */
    public HistoryReadResult clone()
    {
        HistoryReadResult result = new HistoryReadResult();
        result.StatusCode = StatusCode;
        result.ContinuationPoint = ContinuationPoint;
        result.HistoryData = HistoryData;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        HistoryReadResult other = (HistoryReadResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (ContinuationPoint==null) {
            if (other.ContinuationPoint != null) return false;
        } else if (!ContinuationPoint.equals(other.ContinuationPoint)) return false;
        if (HistoryData==null) {
            if (other.HistoryData != null) return false;
        } else if (!HistoryData.equals(other.HistoryData)) return false;
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
                + ((ContinuationPoint == null) ? 0 : ContinuationPoint.hashCode());
        result = prime * result
                + ((HistoryData == null) ? 0 : HistoryData.hashCode());
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
		return "HistoryReadResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
