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



public class ContentFilterElementResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ContentFilterElementResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ContentFilterElementResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ContentFilterElementResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected StatusCode[] OperandStatusCodes;
    protected DiagnosticInfo[] OperandDiagnosticInfos;
    
    public ContentFilterElementResult() {}
    
    public ContentFilterElementResult(StatusCode StatusCode, StatusCode[] OperandStatusCodes, DiagnosticInfo[] OperandDiagnosticInfos)
    {
        this.StatusCode = StatusCode;
        this.OperandStatusCodes = OperandStatusCodes;
        this.OperandDiagnosticInfos = OperandDiagnosticInfos;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public StatusCode[] getOperandStatusCodes()
    {
        return OperandStatusCodes;
    }
    
    public void setOperandStatusCodes(StatusCode[] OperandStatusCodes)
    {
        this.OperandStatusCodes = OperandStatusCodes;
    }
    
    public DiagnosticInfo[] getOperandDiagnosticInfos()
    {
        return OperandDiagnosticInfos;
    }
    
    public void setOperandDiagnosticInfos(DiagnosticInfo[] OperandDiagnosticInfos)
    {
        this.OperandDiagnosticInfos = OperandDiagnosticInfos;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ContentFilterElementResult
      */
    public ContentFilterElementResult clone()
    {
        ContentFilterElementResult result = new ContentFilterElementResult();
        result.StatusCode = StatusCode;
        result.OperandStatusCodes = OperandStatusCodes==null ? null : OperandStatusCodes.clone();
        result.OperandDiagnosticInfos = OperandDiagnosticInfos==null ? null : OperandDiagnosticInfos.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ContentFilterElementResult other = (ContentFilterElementResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (OperandStatusCodes==null) {
            if (other.OperandStatusCodes != null) return false;
        } else if (!Arrays.equals(OperandStatusCodes, other.OperandStatusCodes)) return false;
        if (OperandDiagnosticInfos==null) {
            if (other.OperandDiagnosticInfos != null) return false;
        } else if (!Arrays.equals(OperandDiagnosticInfos, other.OperandDiagnosticInfos)) return false;
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
                + ((OperandStatusCodes == null) ? 0 : Arrays.hashCode(OperandStatusCodes));
        result = prime * result
                + ((OperandDiagnosticInfos == null) ? 0 : Arrays.hashCode(OperandDiagnosticInfos));
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
		return "ContentFilterElementResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
