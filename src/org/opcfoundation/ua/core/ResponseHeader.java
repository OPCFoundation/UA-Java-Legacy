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
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class ResponseHeader extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ResponseHeader);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ResponseHeader_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ResponseHeader_Encoding_DefaultXml);
	
    protected DateTime Timestamp;
    protected UnsignedInteger RequestHandle;
    protected StatusCode ServiceResult;
    protected DiagnosticInfo ServiceDiagnostics;
    protected String[] StringTable;
    protected ExtensionObject AdditionalHeader;
    
    public ResponseHeader() {}
    
    public ResponseHeader(DateTime Timestamp, UnsignedInteger RequestHandle, StatusCode ServiceResult, DiagnosticInfo ServiceDiagnostics, String[] StringTable, ExtensionObject AdditionalHeader)
    {
        this.Timestamp = Timestamp;
        this.RequestHandle = RequestHandle;
        this.ServiceResult = ServiceResult;
        this.ServiceDiagnostics = ServiceDiagnostics;
        this.StringTable = StringTable;
        this.AdditionalHeader = AdditionalHeader;
    }
    
    public DateTime getTimestamp()
    {
        return Timestamp;
    }
    
    public void setTimestamp(DateTime Timestamp)
    {
        this.Timestamp = Timestamp;
    }
    
    public UnsignedInteger getRequestHandle()
    {
        return RequestHandle;
    }
    
    public void setRequestHandle(UnsignedInteger RequestHandle)
    {
        this.RequestHandle = RequestHandle;
    }
    
    public StatusCode getServiceResult()
    {
        return ServiceResult;
    }
    
    public void setServiceResult(StatusCode ServiceResult)
    {
        this.ServiceResult = ServiceResult;
    }
    
    public DiagnosticInfo getServiceDiagnostics()
    {
        return ServiceDiagnostics;
    }
    
    public void setServiceDiagnostics(DiagnosticInfo ServiceDiagnostics)
    {
        this.ServiceDiagnostics = ServiceDiagnostics;
    }
    
    public String[] getStringTable()
    {
        return StringTable;
    }
    
    public void setStringTable(String[] StringTable)
    {
        this.StringTable = StringTable;
    }
    
    public ExtensionObject getAdditionalHeader()
    {
        return AdditionalHeader;
    }
    
    public void setAdditionalHeader(ExtensionObject AdditionalHeader)
    {
        this.AdditionalHeader = AdditionalHeader;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ResponseHeader
      */
    public ResponseHeader clone()
    {
        ResponseHeader result = new ResponseHeader();
        result.Timestamp = Timestamp;
        result.RequestHandle = RequestHandle;
        result.ServiceResult = ServiceResult;
        result.ServiceDiagnostics = ServiceDiagnostics;
        result.StringTable = StringTable==null ? null : StringTable.clone();
        result.AdditionalHeader = AdditionalHeader;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ResponseHeader other = (ResponseHeader) obj;
        if (Timestamp==null) {
            if (other.Timestamp != null) return false;
        } else if (!Timestamp.equals(other.Timestamp)) return false;
        if (RequestHandle==null) {
            if (other.RequestHandle != null) return false;
        } else if (!RequestHandle.equals(other.RequestHandle)) return false;
        if (ServiceResult==null) {
            if (other.ServiceResult != null) return false;
        } else if (!ServiceResult.equals(other.ServiceResult)) return false;
        if (ServiceDiagnostics==null) {
            if (other.ServiceDiagnostics != null) return false;
        } else if (!ServiceDiagnostics.equals(other.ServiceDiagnostics)) return false;
        if (StringTable==null) {
            if (other.StringTable != null) return false;
        } else if (!Arrays.equals(StringTable, other.StringTable)) return false;
        if (AdditionalHeader==null) {
            if (other.AdditionalHeader != null) return false;
        } else if (!AdditionalHeader.equals(other.AdditionalHeader)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Timestamp == null) ? 0 : Timestamp.hashCode());
        result = prime * result
                + ((RequestHandle == null) ? 0 : RequestHandle.hashCode());
        result = prime * result
                + ((ServiceResult == null) ? 0 : ServiceResult.hashCode());
        result = prime * result
                + ((ServiceDiagnostics == null) ? 0 : ServiceDiagnostics.hashCode());
        result = prime * result
                + ((StringTable == null) ? 0 : Arrays.hashCode(StringTable));
        result = prime * result
                + ((AdditionalHeader == null) ? 0 : AdditionalHeader.hashCode());
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
		return "ResponseHeader: "+ObjectUtils.printFieldsDeep(this);
	}

}
