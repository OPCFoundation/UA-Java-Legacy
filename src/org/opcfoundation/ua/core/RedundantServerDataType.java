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
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.core.ServerState;



public class RedundantServerDataType extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.RedundantServerDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.RedundantServerDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.RedundantServerDataType_Encoding_DefaultXml);
	
    protected String ServerId;
    protected UnsignedByte ServiceLevel;
    protected ServerState ServerState;
    
    public RedundantServerDataType() {}
    
    public RedundantServerDataType(String ServerId, UnsignedByte ServiceLevel, ServerState ServerState)
    {
        this.ServerId = ServerId;
        this.ServiceLevel = ServiceLevel;
        this.ServerState = ServerState;
    }
    
    public String getServerId()
    {
        return ServerId;
    }
    
    public void setServerId(String ServerId)
    {
        this.ServerId = ServerId;
    }
    
    public UnsignedByte getServiceLevel()
    {
        return ServiceLevel;
    }
    
    public void setServiceLevel(UnsignedByte ServiceLevel)
    {
        this.ServiceLevel = ServiceLevel;
    }
    
    public ServerState getServerState()
    {
        return ServerState;
    }
    
    public void setServerState(ServerState ServerState)
    {
        this.ServerState = ServerState;
    }
    
    /**
      * Deep clone
      *
      * @return cloned RedundantServerDataType
      */
    public RedundantServerDataType clone()
    {
        RedundantServerDataType result = new RedundantServerDataType();
        result.ServerId = ServerId;
        result.ServiceLevel = ServiceLevel;
        result.ServerState = ServerState;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RedundantServerDataType other = (RedundantServerDataType) obj;
        if (ServerId==null) {
            if (other.ServerId != null) return false;
        } else if (!ServerId.equals(other.ServerId)) return false;
        if (ServiceLevel==null) {
            if (other.ServiceLevel != null) return false;
        } else if (!ServiceLevel.equals(other.ServiceLevel)) return false;
        if (ServerState==null) {
            if (other.ServerState != null) return false;
        } else if (!ServerState.equals(other.ServerState)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ServerId == null) ? 0 : ServerId.hashCode());
        result = prime * result
                + ((ServiceLevel == null) ? 0 : ServiceLevel.hashCode());
        result = prime * result
                + ((ServerState == null) ? 0 : ServerState.hashCode());
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
		return "RedundantServerDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
