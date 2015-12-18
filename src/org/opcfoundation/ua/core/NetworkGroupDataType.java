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
import org.opcfoundation.ua.core.EndpointUrlListDataType;



public class NetworkGroupDataType extends Object implements Structure, Cloneable {
	
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
        NetworkGroupDataType result = new NetworkGroupDataType();
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
