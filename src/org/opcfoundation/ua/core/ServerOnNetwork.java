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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class ServerOnNetwork extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ServerOnNetwork);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ServerOnNetwork_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ServerOnNetwork_Encoding_DefaultXml);
	
    protected UnsignedInteger RecordId;
    protected String ServerName;
    protected String DiscoveryUrl;
    protected String[] ServerCapabilities;
    
    public ServerOnNetwork() {}
    
    public ServerOnNetwork(UnsignedInteger RecordId, String ServerName, String DiscoveryUrl, String[] ServerCapabilities)
    {
        this.RecordId = RecordId;
        this.ServerName = ServerName;
        this.DiscoveryUrl = DiscoveryUrl;
        this.ServerCapabilities = ServerCapabilities;
    }
    
    public UnsignedInteger getRecordId()
    {
        return RecordId;
    }
    
    public void setRecordId(UnsignedInteger RecordId)
    {
        this.RecordId = RecordId;
    }
    
    public String getServerName()
    {
        return ServerName;
    }
    
    public void setServerName(String ServerName)
    {
        this.ServerName = ServerName;
    }
    
    public String getDiscoveryUrl()
    {
        return DiscoveryUrl;
    }
    
    public void setDiscoveryUrl(String DiscoveryUrl)
    {
        this.DiscoveryUrl = DiscoveryUrl;
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
      * @return cloned ServerOnNetwork
      */
    public ServerOnNetwork clone()
    {
        ServerOnNetwork result = new ServerOnNetwork();
        result.RecordId = RecordId;
        result.ServerName = ServerName;
        result.DiscoveryUrl = DiscoveryUrl;
        result.ServerCapabilities = ServerCapabilities==null ? null : ServerCapabilities.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ServerOnNetwork other = (ServerOnNetwork) obj;
        if (RecordId==null) {
            if (other.RecordId != null) return false;
        } else if (!RecordId.equals(other.RecordId)) return false;
        if (ServerName==null) {
            if (other.ServerName != null) return false;
        } else if (!ServerName.equals(other.ServerName)) return false;
        if (DiscoveryUrl==null) {
            if (other.DiscoveryUrl != null) return false;
        } else if (!DiscoveryUrl.equals(other.DiscoveryUrl)) return false;
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
                + ((RecordId == null) ? 0 : RecordId.hashCode());
        result = prime * result
                + ((ServerName == null) ? 0 : ServerName.hashCode());
        result = prime * result
                + ((DiscoveryUrl == null) ? 0 : DiscoveryUrl.hashCode());
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
		return "ServerOnNetwork: "+ObjectUtils.printFieldsDeep(this);
	}

}
