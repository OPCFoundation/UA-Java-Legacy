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
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.core.ApplicationType;
import org.opcfoundation.ua.core.RegisteredServer;



public class RegisteredServer2 extends RegisteredServer {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.RegisteredServer2);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.RegisteredServer2_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.RegisteredServer2_Encoding_DefaultXml);
	
    protected String MdnsServerName;
    protected String[] ServerCapabilities;
    
    public RegisteredServer2() {}
    
    public RegisteredServer2(String ServerUri, String ProductUri, LocalizedText[] ServerNames, ApplicationType ServerType, String GatewayServerUri, String[] DiscoveryUrls, String SemaphoreFilePath, Boolean IsOnline, String MdnsServerName, String[] ServerCapabilities)
    {
        super(ServerUri, ProductUri, ServerNames, ServerType, GatewayServerUri, DiscoveryUrls, SemaphoreFilePath, IsOnline);
        this.MdnsServerName = MdnsServerName;
        this.ServerCapabilities = ServerCapabilities;
    }
    
    public String getMdnsServerName()
    {
        return MdnsServerName;
    }
    
    public void setMdnsServerName(String MdnsServerName)
    {
        this.MdnsServerName = MdnsServerName;
    }
    
    public String[] getServerCapabilities()
    {
        return ServerCapabilities;
    }
    
    public void setServerCapabilities(String[] ServerCapabilities)
    {
        this.ServerCapabilities = ServerCapabilities;
    }
    
    /**
      * Deep clone
      *
      * @return cloned RegisteredServer2
      */
    public RegisteredServer2 clone()
    {
        RegisteredServer2 result = (RegisteredServer2) super.clone();
        result.ServerUri = ServerUri;
        result.ProductUri = ProductUri;
        result.ServerNames = ServerNames==null ? null : ServerNames.clone();
        result.ServerType = ServerType;
        result.GatewayServerUri = GatewayServerUri;
        result.DiscoveryUrls = DiscoveryUrls==null ? null : DiscoveryUrls.clone();
        result.SemaphoreFilePath = SemaphoreFilePath;
        result.IsOnline = IsOnline;
        result.MdnsServerName = MdnsServerName;
        result.ServerCapabilities = ServerCapabilities==null ? null : ServerCapabilities.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RegisteredServer2 other = (RegisteredServer2) obj;
        if (ServerUri==null) {
            if (other.ServerUri != null) return false;
        } else if (!ServerUri.equals(other.ServerUri)) return false;
        if (ProductUri==null) {
            if (other.ProductUri != null) return false;
        } else if (!ProductUri.equals(other.ProductUri)) return false;
        if (ServerNames==null) {
            if (other.ServerNames != null) return false;
        } else if (!Arrays.equals(ServerNames, other.ServerNames)) return false;
        if (ServerType==null) {
            if (other.ServerType != null) return false;
        } else if (!ServerType.equals(other.ServerType)) return false;
        if (GatewayServerUri==null) {
            if (other.GatewayServerUri != null) return false;
        } else if (!GatewayServerUri.equals(other.GatewayServerUri)) return false;
        if (DiscoveryUrls==null) {
            if (other.DiscoveryUrls != null) return false;
        } else if (!Arrays.equals(DiscoveryUrls, other.DiscoveryUrls)) return false;
        if (SemaphoreFilePath==null) {
            if (other.SemaphoreFilePath != null) return false;
        } else if (!SemaphoreFilePath.equals(other.SemaphoreFilePath)) return false;
        if (IsOnline==null) {
            if (other.IsOnline != null) return false;
        } else if (!IsOnline.equals(other.IsOnline)) return false;
        if (MdnsServerName==null) {
            if (other.MdnsServerName != null) return false;
        } else if (!MdnsServerName.equals(other.MdnsServerName)) return false;
        if (ServerCapabilities==null) {
            if (other.ServerCapabilities != null) return false;
        } else if (!Arrays.equals(ServerCapabilities, other.ServerCapabilities)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ServerUri == null) ? 0 : ServerUri.hashCode());
        result = prime * result
                + ((ProductUri == null) ? 0 : ProductUri.hashCode());
        result = prime * result
                + ((ServerNames == null) ? 0 : Arrays.hashCode(ServerNames));
        result = prime * result
                + ((ServerType == null) ? 0 : ServerType.hashCode());
        result = prime * result
                + ((GatewayServerUri == null) ? 0 : GatewayServerUri.hashCode());
        result = prime * result
                + ((DiscoveryUrls == null) ? 0 : Arrays.hashCode(DiscoveryUrls));
        result = prime * result
                + ((SemaphoreFilePath == null) ? 0 : SemaphoreFilePath.hashCode());
        result = prime * result
                + ((IsOnline == null) ? 0 : IsOnline.hashCode());
        result = prime * result
                + ((MdnsServerName == null) ? 0 : MdnsServerName.hashCode());
        result = prime * result
                + ((ServerCapabilities == null) ? 0 : Arrays.hashCode(ServerCapabilities));
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
		return "RegisteredServer2: "+ObjectUtils.printFieldsDeep(this);
	}

}
