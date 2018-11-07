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
import org.opcfoundation.ua.common.NamespaceTable;

import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class SessionlessInvokeRequestType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.SessionlessInvokeRequestType.getValue());
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.SessionlessInvokeRequestType_Encoding_DefaultBinary.getValue());
	public static final ExpandedNodeId XML = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.SessionlessInvokeRequestType_Encoding_DefaultXml.getValue());
	
    protected UnsignedInteger[] UrisVersion;
    protected String[] NamespaceUris;
    protected String[] ServerUris;
    protected String[] LocaleIds;
    protected UnsignedInteger ServiceId;
    
    public SessionlessInvokeRequestType() {}
    
    public SessionlessInvokeRequestType(UnsignedInteger[] UrisVersion, String[] NamespaceUris, String[] ServerUris, String[] LocaleIds, UnsignedInteger ServiceId)
    {
        this.UrisVersion = UrisVersion;
        this.NamespaceUris = NamespaceUris;
        this.ServerUris = ServerUris;
        this.LocaleIds = LocaleIds;
        this.ServiceId = ServiceId;
    }
    
    public UnsignedInteger[] getUrisVersion()
    {
        return UrisVersion;
    }
    
    public void setUrisVersion(UnsignedInteger[] UrisVersion)
    {
        this.UrisVersion = UrisVersion;
    }
    
    public String[] getNamespaceUris()
    {
        return NamespaceUris;
    }
    
    public void setNamespaceUris(String[] NamespaceUris)
    {
        this.NamespaceUris = NamespaceUris;
    }
    
    public String[] getServerUris()
    {
        return ServerUris;
    }
    
    public void setServerUris(String[] ServerUris)
    {
        this.ServerUris = ServerUris;
    }
    
    public String[] getLocaleIds()
    {
        return LocaleIds;
    }
    
    public void setLocaleIds(String[] LocaleIds)
    {
        this.LocaleIds = LocaleIds;
    }
    
    public UnsignedInteger getServiceId()
    {
        return ServiceId;
    }
    
    public void setServiceId(UnsignedInteger ServiceId)
    {
        this.ServiceId = ServiceId;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SessionlessInvokeRequestType
      */
    public SessionlessInvokeRequestType clone()
    {
        SessionlessInvokeRequestType result = (SessionlessInvokeRequestType) super.clone();
        result.UrisVersion = UrisVersion==null ? null : UrisVersion.clone();
        result.NamespaceUris = NamespaceUris==null ? null : NamespaceUris.clone();
        result.ServerUris = ServerUris==null ? null : ServerUris.clone();
        result.LocaleIds = LocaleIds==null ? null : LocaleIds.clone();
        result.ServiceId = ServiceId;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SessionlessInvokeRequestType other = (SessionlessInvokeRequestType) obj;
        if (UrisVersion==null) {
            if (other.UrisVersion != null) return false;
        } else if (!Arrays.equals(UrisVersion, other.UrisVersion)) return false;
        if (NamespaceUris==null) {
            if (other.NamespaceUris != null) return false;
        } else if (!Arrays.equals(NamespaceUris, other.NamespaceUris)) return false;
        if (ServerUris==null) {
            if (other.ServerUris != null) return false;
        } else if (!Arrays.equals(ServerUris, other.ServerUris)) return false;
        if (LocaleIds==null) {
            if (other.LocaleIds != null) return false;
        } else if (!Arrays.equals(LocaleIds, other.LocaleIds)) return false;
        if (ServiceId==null) {
            if (other.ServiceId != null) return false;
        } else if (!ServiceId.equals(other.ServiceId)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((UrisVersion == null) ? 0 : Arrays.hashCode(UrisVersion));
        result = prime * result
                + ((NamespaceUris == null) ? 0 : Arrays.hashCode(NamespaceUris));
        result = prime * result
                + ((ServerUris == null) ? 0 : Arrays.hashCode(ServerUris));
        result = prime * result
                + ((LocaleIds == null) ? 0 : Arrays.hashCode(LocaleIds));
        result = prime * result
                + ((ServiceId == null) ? 0 : ServiceId.hashCode());
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
		return "SessionlessInvokeRequestType: "+ObjectUtils.printFieldsDeep(this);
	}

}
