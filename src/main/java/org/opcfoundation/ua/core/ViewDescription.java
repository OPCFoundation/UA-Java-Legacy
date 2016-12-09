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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class ViewDescription extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ViewDescription);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ViewDescription_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ViewDescription_Encoding_DefaultXml);
	
    protected NodeId ViewId;
    protected DateTime Timestamp;
    protected UnsignedInteger ViewVersion;
    
    public ViewDescription() {}
    
    public ViewDescription(NodeId ViewId, DateTime Timestamp, UnsignedInteger ViewVersion)
    {
        this.ViewId = ViewId;
        this.Timestamp = Timestamp;
        this.ViewVersion = ViewVersion;
    }
    
    public NodeId getViewId()
    {
        return ViewId;
    }
    
    public void setViewId(NodeId ViewId)
    {
        this.ViewId = ViewId;
    }
    
    public DateTime getTimestamp()
    {
        return Timestamp;
    }
    
    public void setTimestamp(DateTime Timestamp)
    {
        this.Timestamp = Timestamp;
    }
    
    public UnsignedInteger getViewVersion()
    {
        return ViewVersion;
    }
    
    public void setViewVersion(UnsignedInteger ViewVersion)
    {
        this.ViewVersion = ViewVersion;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ViewDescription
      */
    public ViewDescription clone()
    {
        ViewDescription result = (ViewDescription) super.clone();
        result.ViewId = ViewId;
        result.Timestamp = Timestamp;
        result.ViewVersion = ViewVersion;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ViewDescription other = (ViewDescription) obj;
        if (ViewId==null) {
            if (other.ViewId != null) return false;
        } else if (!ViewId.equals(other.ViewId)) return false;
        if (Timestamp==null) {
            if (other.Timestamp != null) return false;
        } else if (!Timestamp.equals(other.Timestamp)) return false;
        if (ViewVersion==null) {
            if (other.ViewVersion != null) return false;
        } else if (!ViewVersion.equals(other.ViewVersion)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ViewId == null) ? 0 : ViewId.hashCode());
        result = prime * result
                + ((Timestamp == null) ? 0 : Timestamp.hashCode());
        result = prime * result
                + ((ViewVersion == null) ? 0 : ViewVersion.hashCode());
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
		return "ViewDescription: "+ObjectUtils.printFieldsDeep(this);
	}

}
