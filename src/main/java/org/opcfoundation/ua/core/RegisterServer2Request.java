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

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.core.RegisteredServer;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.utils.AbstractStructure;


public class RegisterServer2Request extends AbstractStructure implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.RegisterServer2Request);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.RegisterServer2Request_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.RegisterServer2Request_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected RegisteredServer Server;
    protected ExtensionObject[] DiscoveryConfiguration;
    
    public RegisterServer2Request() {}
    
    public RegisterServer2Request(RequestHeader RequestHeader, RegisteredServer Server, ExtensionObject[] DiscoveryConfiguration)
    {
        this.RequestHeader = RequestHeader;
        this.Server = Server;
        this.DiscoveryConfiguration = DiscoveryConfiguration;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public RegisteredServer getServer()
    {
        return Server;
    }
    
    public void setServer(RegisteredServer Server)
    {
        this.Server = Server;
    }
    
    public ExtensionObject[] getDiscoveryConfiguration()
    {
        return DiscoveryConfiguration;
    }
    
    public void setDiscoveryConfiguration(ExtensionObject[] DiscoveryConfiguration)
    {
        this.DiscoveryConfiguration = DiscoveryConfiguration;
    }
    
    /**
      * Deep clone
      *
      * @return cloned RegisterServer2Request
      */
    public RegisterServer2Request clone()
    {
        RegisterServer2Request result = (RegisterServer2Request) super.clone();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.Server = Server==null ? null : Server.clone();
        result.DiscoveryConfiguration = DiscoveryConfiguration==null ? null : DiscoveryConfiguration.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RegisterServer2Request other = (RegisterServer2Request) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (Server==null) {
            if (other.Server != null) return false;
        } else if (!Server.equals(other.Server)) return false;
        if (DiscoveryConfiguration==null) {
            if (other.DiscoveryConfiguration != null) return false;
        } else if (!Arrays.equals(DiscoveryConfiguration, other.DiscoveryConfiguration)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((RequestHeader == null) ? 0 : RequestHeader.hashCode());
        result = prime * result
                + ((Server == null) ? 0 : Server.hashCode());
        result = prime * result
                + ((DiscoveryConfiguration == null) ? 0 : Arrays.hashCode(DiscoveryConfiguration));
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
