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
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.StatusCode;



public class ParsingResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ParsingResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ParsingResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ParsingResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected StatusCode[] DataStatusCodes;
    protected DiagnosticInfo[] DataDiagnosticInfos;
    
    public ParsingResult() {}
    
    public ParsingResult(StatusCode StatusCode, StatusCode[] DataStatusCodes, DiagnosticInfo[] DataDiagnosticInfos)
    {
        this.StatusCode = StatusCode;
        this.DataStatusCodes = DataStatusCodes;
        this.DataDiagnosticInfos = DataDiagnosticInfos;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public StatusCode[] getDataStatusCodes()
    {
        return DataStatusCodes;
    }
    
    public void setDataStatusCodes(StatusCode[] DataStatusCodes)
    {
        this.DataStatusCodes = DataStatusCodes;
    }
    
    public DiagnosticInfo[] getDataDiagnosticInfos()
    {
        return DataDiagnosticInfos;
    }
    
    public void setDataDiagnosticInfos(DiagnosticInfo[] DataDiagnosticInfos)
    {
        this.DataDiagnosticInfos = DataDiagnosticInfos;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ParsingResult
      */
    public ParsingResult clone()
    {
        ParsingResult result = new ParsingResult();
        result.StatusCode = StatusCode;
        result.DataStatusCodes = DataStatusCodes==null ? null : DataStatusCodes.clone();
        result.DataDiagnosticInfos = DataDiagnosticInfos==null ? null : DataDiagnosticInfos.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ParsingResult other = (ParsingResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (DataStatusCodes==null) {
            if (other.DataStatusCodes != null) return false;
        } else if (!Arrays.equals(DataStatusCodes, other.DataStatusCodes)) return false;
        if (DataDiagnosticInfos==null) {
            if (other.DataDiagnosticInfos != null) return false;
        } else if (!Arrays.equals(DataDiagnosticInfos, other.DataDiagnosticInfos)) return false;
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
                + ((DataStatusCodes == null) ? 0 : Arrays.hashCode(DataStatusCodes));
        result = prime * result
                + ((DataDiagnosticInfos == null) ? 0 : Arrays.hashCode(DataDiagnosticInfos));
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
		return "ParsingResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
