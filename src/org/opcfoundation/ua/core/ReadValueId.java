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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class ReadValueId extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ReadValueId);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ReadValueId_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ReadValueId_Encoding_DefaultXml);
	
    protected NodeId NodeId;
    protected UnsignedInteger AttributeId;
    protected String IndexRange;
    protected QualifiedName DataEncoding;
    
    public ReadValueId() {}
    
    public ReadValueId(NodeId NodeId, UnsignedInteger AttributeId, String IndexRange, QualifiedName DataEncoding)
    {
        this.NodeId = NodeId;
        this.AttributeId = AttributeId;
        this.IndexRange = IndexRange;
        this.DataEncoding = DataEncoding;
    }
    
    public NodeId getNodeId()
    {
        return NodeId;
    }
    
    public void setNodeId(NodeId NodeId)
    {
        this.NodeId = NodeId;
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
    
    public QualifiedName getDataEncoding()
    {
        return DataEncoding;
    }
    
    public void setDataEncoding(QualifiedName DataEncoding)
    {
        this.DataEncoding = DataEncoding;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ReadValueId
      */
    public ReadValueId clone()
    {
        ReadValueId result = new ReadValueId();
        result.NodeId = NodeId;
        result.AttributeId = AttributeId;
        result.IndexRange = IndexRange;
        result.DataEncoding = DataEncoding;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ReadValueId other = (ReadValueId) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (AttributeId==null) {
            if (other.AttributeId != null) return false;
        } else if (!AttributeId.equals(other.AttributeId)) return false;
        if (IndexRange==null) {
            if (other.IndexRange != null) return false;
        } else if (!IndexRange.equals(other.IndexRange)) return false;
        if (DataEncoding==null) {
            if (other.DataEncoding != null) return false;
        } else if (!DataEncoding.equals(other.DataEncoding)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((NodeId == null) ? 0 : NodeId.hashCode());
        result = prime * result
                + ((AttributeId == null) ? 0 : AttributeId.hashCode());
        result = prime * result
                + ((IndexRange == null) ? 0 : IndexRange.hashCode());
        result = prime * result
                + ((DataEncoding == null) ? 0 : DataEncoding.hashCode());
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
		return "ReadValueId: "+ObjectUtils.printFieldsDeep(this);
	}

}
