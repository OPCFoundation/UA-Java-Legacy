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
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.core.ServerState;
import org.opcfoundation.ua.utils.AbstractStructure;



public class RedundantServerDataType extends AbstractStructure {
	
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
        RedundantServerDataType result = (RedundantServerDataType) super.clone();
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
