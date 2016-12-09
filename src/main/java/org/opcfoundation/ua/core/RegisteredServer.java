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
import org.opcfoundation.ua.utils.AbstractStructure;



public class RegisteredServer extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.RegisteredServer);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.RegisteredServer_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.RegisteredServer_Encoding_DefaultXml);
	
    protected String ServerUri;
    protected String ProductUri;
    protected LocalizedText[] ServerNames;
    protected ApplicationType ServerType;
    protected String GatewayServerUri;
    protected String[] DiscoveryUrls;
    protected String SemaphoreFilePath;
    protected Boolean IsOnline;
    
    public RegisteredServer() {}
    
    public RegisteredServer(String ServerUri, String ProductUri, LocalizedText[] ServerNames, ApplicationType ServerType, String GatewayServerUri, String[] DiscoveryUrls, String SemaphoreFilePath, Boolean IsOnline)
    {
        this.ServerUri = ServerUri;
        this.ProductUri = ProductUri;
        this.ServerNames = ServerNames;
        this.ServerType = ServerType;
        this.GatewayServerUri = GatewayServerUri;
        this.DiscoveryUrls = DiscoveryUrls;
        this.SemaphoreFilePath = SemaphoreFilePath;
        this.IsOnline = IsOnline;
    }
    
    public String getServerUri()
    {
        return ServerUri;
    }
    
    public void setServerUri(String ServerUri)
    {
        this.ServerUri = ServerUri;
    }
    
    public String getProductUri()
    {
        return ProductUri;
    }
    
    public void setProductUri(String ProductUri)
    {
        this.ProductUri = ProductUri;
    }
    
    public LocalizedText[] getServerNames()
    {
        return ServerNames;
    }
    
    public void setServerNames(LocalizedText[] ServerNames)
    {
        this.ServerNames = ServerNames;
    }
    
    public ApplicationType getServerType()
    {
        return ServerType;
    }
    
    public void setServerType(ApplicationType ServerType)
    {
        this.ServerType = ServerType;
    }
    
    public String getGatewayServerUri()
    {
        return GatewayServerUri;
    }
    
    public void setGatewayServerUri(String GatewayServerUri)
    {
        this.GatewayServerUri = GatewayServerUri;
    }
    
    public String[] getDiscoveryUrls()
    {
        return DiscoveryUrls;
    }
    
    public void setDiscoveryUrls(String[] DiscoveryUrls)
    {
        this.DiscoveryUrls = DiscoveryUrls;
    }
    
    public String getSemaphoreFilePath()
    {
        return SemaphoreFilePath;
    }
    
    public void setSemaphoreFilePath(String SemaphoreFilePath)
    {
        this.SemaphoreFilePath = SemaphoreFilePath;
    }
    
    public Boolean getIsOnline()
    {
        return IsOnline;
    }
    
    public void setIsOnline(Boolean IsOnline)
    {
        this.IsOnline = IsOnline;
    }
    
    /**
      * Deep clone
      *
      * @return cloned RegisteredServer
      */
    public RegisteredServer clone()
    {
        RegisteredServer result = (RegisteredServer) super.clone();
        result.ServerUri = ServerUri;
        result.ProductUri = ProductUri;
        result.ServerNames = ServerNames==null ? null : ServerNames.clone();
        result.ServerType = ServerType;
        result.GatewayServerUri = GatewayServerUri;
        result.DiscoveryUrls = DiscoveryUrls==null ? null : DiscoveryUrls.clone();
        result.SemaphoreFilePath = SemaphoreFilePath;
        result.IsOnline = IsOnline;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RegisteredServer other = (RegisteredServer) obj;
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
		return "RegisteredServer: "+ObjectUtils.printFieldsDeep(this);
	}

}
