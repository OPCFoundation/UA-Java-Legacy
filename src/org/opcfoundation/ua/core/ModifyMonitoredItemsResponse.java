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

import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.core.MonitoredItemModifyResult;
import org.opcfoundation.ua.core.ResponseHeader;


public class ModifyMonitoredItemsResponse extends Object implements ServiceResponse {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ModifyMonitoredItemsResponse);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ModifyMonitoredItemsResponse_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ModifyMonitoredItemsResponse_Encoding_DefaultXml);
	
    protected ResponseHeader ResponseHeader;
    protected MonitoredItemModifyResult[] Results;
    protected DiagnosticInfo[] DiagnosticInfos;
    
    public ModifyMonitoredItemsResponse() {}
    
    public ModifyMonitoredItemsResponse(ResponseHeader ResponseHeader, MonitoredItemModifyResult[] Results, DiagnosticInfo[] DiagnosticInfos)
    {
        this.ResponseHeader = ResponseHeader;
        this.Results = Results;
        this.DiagnosticInfos = DiagnosticInfos;
    }
    
    public ResponseHeader getResponseHeader()
    {
        return ResponseHeader;
    }
    
    public void setResponseHeader(ResponseHeader ResponseHeader)
    {
        this.ResponseHeader = ResponseHeader;
    }
    
    public MonitoredItemModifyResult[] getResults()
    {
        return Results;
    }
    
    public void setResults(MonitoredItemModifyResult[] Results)
    {
        this.Results = Results;
    }
    
    public DiagnosticInfo[] getDiagnosticInfos()
    {
        return DiagnosticInfos;
    }
    
    public void setDiagnosticInfos(DiagnosticInfo[] DiagnosticInfos)
    {
        this.DiagnosticInfos = DiagnosticInfos;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ModifyMonitoredItemsResponse
      */
    public ModifyMonitoredItemsResponse clone()
    {
        ModifyMonitoredItemsResponse result = new ModifyMonitoredItemsResponse();
        result.ResponseHeader = ResponseHeader==null ? null : ResponseHeader.clone();
        if (Results!=null) {
            result.Results = new MonitoredItemModifyResult[Results.length];
            for (int i=0; i<Results.length; i++)
                result.Results[i] = Results[i].clone();
        }
        result.DiagnosticInfos = DiagnosticInfos==null ? null : DiagnosticInfos.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ModifyMonitoredItemsResponse other = (ModifyMonitoredItemsResponse) obj;
        if (ResponseHeader==null) {
            if (other.ResponseHeader != null) return false;
        } else if (!ResponseHeader.equals(other.ResponseHeader)) return false;
        if (Results==null) {
            if (other.Results != null) return false;
        } else if (!Arrays.equals(Results, other.Results)) return false;
        if (DiagnosticInfos==null) {
            if (other.DiagnosticInfos != null) return false;
        } else if (!Arrays.equals(DiagnosticInfos, other.DiagnosticInfos)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ResponseHeader == null) ? 0 : ResponseHeader.hashCode());
        result = prime * result
                + ((Results == null) ? 0 : Arrays.hashCode(Results));
        result = prime * result
                + ((DiagnosticInfos == null) ? 0 : Arrays.hashCode(DiagnosticInfos));
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
		return ObjectUtils.printFieldsDeep(this);
	}
	
}
