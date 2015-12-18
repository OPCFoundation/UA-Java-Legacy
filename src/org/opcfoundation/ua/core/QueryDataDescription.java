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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.RelativePath;



public class QueryDataDescription extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.QueryDataDescription);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.QueryDataDescription_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.QueryDataDescription_Encoding_DefaultXml);
	
    protected RelativePath RelativePath;
    protected UnsignedInteger AttributeId;
    protected String IndexRange;
    
    public QueryDataDescription() {}
    
    public QueryDataDescription(RelativePath RelativePath, UnsignedInteger AttributeId, String IndexRange)
    {
        this.RelativePath = RelativePath;
        this.AttributeId = AttributeId;
        this.IndexRange = IndexRange;
    }
    
    public RelativePath getRelativePath()
    {
        return RelativePath;
    }
    
    public void setRelativePath(RelativePath RelativePath)
    {
        this.RelativePath = RelativePath;
    }
    
    public UnsignedInteger getAttributeId()
    {
        return AttributeId;
    }
    
    public void setAttributeId(UnsignedInteger AttributeId)
    {
        this.AttributeId = AttributeId;
    }
    
    public String getIndexRange()
    {
        return IndexRange;
    }
    
    public void setIndexRange(String IndexRange)
    {
        this.IndexRange = IndexRange;
    }
    
    /**
      * Deep clone
      *
      * @return cloned QueryDataDescription
      */
    public QueryDataDescription clone()
    {
        QueryDataDescription result = new QueryDataDescription();
        result.RelativePath = RelativePath==null ? null : RelativePath.clone();
        result.AttributeId = AttributeId;
        result.IndexRange = IndexRange;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        QueryDataDescription other = (QueryDataDescription) obj;
        if (RelativePath==null) {
            if (other.RelativePath != null) return false;
        } else if (!RelativePath.equals(other.RelativePath)) return false;
        if (AttributeId==null) {
            if (other.AttributeId != null) return false;
        } else if (!AttributeId.equals(other.AttributeId)) return false;
        if (IndexRange==null) {
            if (other.IndexRange != null) return false;
        } else if (!IndexRange.equals(other.IndexRange)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((RelativePath == null) ? 0 : RelativePath.hashCode());
        result = prime * result
                + ((AttributeId == null) ? 0 : AttributeId.hashCode());
        result = prime * result
                + ((IndexRange == null) ? 0 : IndexRange.hashCode());
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
		return "QueryDataDescription: "+ObjectUtils.printFieldsDeep(this);
	}

}
