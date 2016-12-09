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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class BrowsePathTarget extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.BrowsePathTarget);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.BrowsePathTarget_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.BrowsePathTarget_Encoding_DefaultXml);
	
    protected ExpandedNodeId TargetId;
    protected UnsignedInteger RemainingPathIndex;
    
    public BrowsePathTarget() {}
    
    public BrowsePathTarget(ExpandedNodeId TargetId, UnsignedInteger RemainingPathIndex)
    {
        this.TargetId = TargetId;
        this.RemainingPathIndex = RemainingPathIndex;
    }
    
    public ExpandedNodeId getTargetId()
    {
        return TargetId;
    }
    
    public void setTargetId(ExpandedNodeId TargetId)
    {
        this.TargetId = TargetId;
    }
    
    public UnsignedInteger getRemainingPathIndex()
    {
        return RemainingPathIndex;
    }
    
    public void setRemainingPathIndex(UnsignedInteger RemainingPathIndex)
    {
        this.RemainingPathIndex = RemainingPathIndex;
    }
    
    /**
      * Deep clone
      *
      * @return cloned BrowsePathTarget
      */
    public BrowsePathTarget clone()
    {
        BrowsePathTarget result = (BrowsePathTarget) super.clone();
        result.TargetId = TargetId;
        result.RemainingPathIndex = RemainingPathIndex;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BrowsePathTarget other = (BrowsePathTarget) obj;
        if (TargetId==null) {
            if (other.TargetId != null) return false;
        } else if (!TargetId.equals(other.TargetId)) return false;
        if (RemainingPathIndex==null) {
            if (other.RemainingPathIndex != null) return false;
        } else if (!RemainingPathIndex.equals(other.RemainingPathIndex)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((TargetId == null) ? 0 : TargetId.hashCode());
        result = prime * result
                + ((RemainingPathIndex == null) ? 0 : RemainingPathIndex.hashCode());
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
		return "BrowsePathTarget: "+ObjectUtils.printFieldsDeep(this);
	}

}
