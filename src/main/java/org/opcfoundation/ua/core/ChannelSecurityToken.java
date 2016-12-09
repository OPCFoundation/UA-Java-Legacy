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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class ChannelSecurityToken extends AbstractStructure {
	
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
        ChannelSecurityToken result = (ChannelSecurityToken) super.clone();
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
