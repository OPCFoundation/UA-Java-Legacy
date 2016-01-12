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
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.StatusCode;



public class StatusResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.StatusResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.StatusResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.StatusResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected DiagnosticInfo DiagnosticInfo;
    
    public StatusResult() {}
    
    public StatusResult(StatusCode StatusCode, DiagnosticInfo DiagnosticInfo)
    {
        this.StatusCode = StatusCode;
        this.DiagnosticInfo = DiagnosticInfo;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public DiagnosticInfo getDiagnosticInfo()
    {
        return DiagnosticInfo;
    }
    
    public void setDiagnosticInfo(DiagnosticInfo DiagnosticInfo)
    {
        this.DiagnosticInfo = DiagnosticInfo;
    }
    
    /**
      * Deep clone
      *
      * @return cloned StatusResult
      */
    public StatusResult clone()
    {
        StatusResult result = new StatusResult();
        result.StatusCode = StatusCode;
        result.DiagnosticInfo = DiagnosticInfo;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StatusResult other = (StatusResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (DiagnosticInfo==null) {
            if (other.DiagnosticInfo != null) return false;
        } else if (!DiagnosticInfo.equals(other.DiagnosticInfo)) return false;
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
                + ((DiagnosticInfo == null) ? 0 : DiagnosticInfo.hashCode());
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
		return "StatusResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
