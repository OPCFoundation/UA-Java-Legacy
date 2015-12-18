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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class ChannelSecurityToken extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ChannelSecurityToken);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ChannelSecurityToken_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ChannelSecurityToken_Encoding_DefaultXml);
	
    protected UnsignedInteger ChannelId;
    protected UnsignedInteger TokenId;
    protected DateTime CreatedAt;
    protected UnsignedInteger RevisedLifetime;
    
    public ChannelSecurityToken() {}
    
    public ChannelSecurityToken(UnsignedInteger ChannelId, UnsignedInteger TokenId, DateTime CreatedAt, UnsignedInteger RevisedLifetime)
    {
        this.ChannelId = ChannelId;
        this.TokenId = TokenId;
        this.CreatedAt = CreatedAt;
        this.RevisedLifetime = RevisedLifetime;
    }
    
    public UnsignedInteger getChannelId()
    {
        return ChannelId;
    }
    
    public void setChannelId(UnsignedInteger ChannelId)
    {
        this.ChannelId = ChannelId;
    }
    
    public UnsignedInteger getTokenId()
    {
        return TokenId;
    }
    
    public void setTokenId(UnsignedInteger TokenId)
    {
        this.TokenId = TokenId;
    }
    
    public DateTime getCreatedAt()
    {
        return CreatedAt;
    }
    
    public void setCreatedAt(DateTime CreatedAt)
    {
        this.CreatedAt = CreatedAt;
    }
    
    public UnsignedInteger getRevisedLifetime()
    {
        return RevisedLifetime;
    }
    
    public void setRevisedLifetime(UnsignedInteger RevisedLifetime)
    {
        this.RevisedLifetime = RevisedLifetime;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ChannelSecurityToken
      */
    public ChannelSecurityToken clone()
    {
        ChannelSecurityToken result = new ChannelSecurityToken();
        result.ChannelId = ChannelId;
        result.TokenId = TokenId;
        result.CreatedAt = CreatedAt;
        result.RevisedLifetime = RevisedLifetime;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ChannelSecurityToken other = (ChannelSecurityToken) obj;
        if (ChannelId==null) {
            if (other.ChannelId != null) return false;
        } else if (!ChannelId.equals(other.ChannelId)) return false;
        if (TokenId==null) {
            if (other.TokenId != null) return false;
        } else if (!TokenId.equals(other.TokenId)) return false;
        if (CreatedAt==null) {
            if (other.CreatedAt != null) return false;
        } else if (!CreatedAt.equals(other.CreatedAt)) return false;
        if (RevisedLifetime==null) {
            if (other.RevisedLifetime != null) return false;
        } else if (!RevisedLifetime.equals(other.RevisedLifetime)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ChannelId == null) ? 0 : ChannelId.hashCode());
        result = prime * result
                + ((TokenId == null) ? 0 : TokenId.hashCode());
        result = prime * result
                + ((CreatedAt == null) ? 0 : CreatedAt.hashCode());
        result = prime * result
                + ((RevisedLifetime == null) ? 0 : RevisedLifetime.hashCode());
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
		return "ChannelSecurityToken: "+ObjectUtils.printFieldsDeep(this);
	}

}
