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
import org.opcfoundation.ua.core.EndpointUrlListDataType;
import org.opcfoundation.ua.utils.AbstractStructure;



public class NetworkGroupDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.NetworkGroupDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.NetworkGroupDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.NetworkGroupDataType_Encoding_DefaultXml);
	
    protected String ServerUri;
    protected EndpointUrlListDataType[] NetworkPaths;
    
    public NetworkGroupDataType() {}
    
    public NetworkGroupDataType(String ServerUri, EndpointUrlListDataType[] NetworkPaths)
    {
        this.ServerUri = ServerUri;
        this.NetworkPaths = NetworkPaths;
    }
    
    public String getServerUri()
    {
        return ServerUri;
    }
    
    public void setServerUri(String ServerUri)
    {
        this.ServerUri = ServerUri;
    }
    
    public EndpointUrlListDataType[] getNetworkPaths()
    {
        return NetworkPaths;
    }
    
    public void setNetworkPaths(EndpointUrlListDataType[] NetworkPaths)
    {
        this.NetworkPaths = NetworkPaths;
    }
    
    /**
      * Deep clone
      *
      * @return cloned NetworkGroupDataType
      */
    public NetworkGroupDataType clone()
    {
        NetworkGroupDataType result = (NetworkGroupDataType) super.clone();
        result.ServerUri = ServerUri;
        if (NetworkPaths!=null) {
            result.NetworkPaths = new EndpointUrlListDataType[NetworkPaths.length];
            for (int i=0; i<NetworkPaths.length; i++)
                result.NetworkPaths[i] = NetworkPaths[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        NetworkGroupDataType other = (NetworkGroupDataType) obj;
        if (ServerUri==null) {
            if (other.ServerUri != null) return false;
        } else if (!ServerUri.equals(other.ServerUri)) return false;
        if (NetworkPaths==null) {
            if (other.NetworkPaths != null) return false;
        } else if (!Arrays.equals(NetworkPaths, other.NetworkPaths)) return false;
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
                + ((NetworkPaths == null) ? 0 : Arrays.hashCode(NetworkPaths));
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
		return "NetworkGroupDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
