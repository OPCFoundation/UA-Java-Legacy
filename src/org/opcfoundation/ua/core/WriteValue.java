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

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.ServiceResult;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.NumericRange;



public class WriteValue extends Object implements Structure, Cloneable {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.WriteValue);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.WriteValue_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.WriteValue_Encoding_DefaultXml);
	

	NumericRange ParsedIndexRange = NumericRange.getEmpty();
	
    protected NodeId NodeId;
    protected UnsignedInteger AttributeId;
    protected String IndexRange;
    protected DataValue Value;
    
    public WriteValue() {}
    
    public WriteValue(NodeId NodeId, UnsignedInteger AttributeId, String IndexRange, DataValue Value)
    {
        this.NodeId = NodeId;
        this.AttributeId = AttributeId;
        this.IndexRange = IndexRange;
        this.Value = Value;
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
    
    public DataValue getValue()
    {
        return Value;
    }
    
    public void setValue(DataValue Value)
    {
        this.Value = Value;
    }
    
    /**
      * Deep clone
      *
      * @return cloned WriteValue
      */
    public WriteValue clone()
    {
        WriteValue result = new WriteValue();
        result.NodeId = NodeId;
        result.AttributeId = AttributeId;
        result.IndexRange = IndexRange;
        result.Value = Value;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        WriteValue other = (WriteValue) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (AttributeId==null) {
            if (other.AttributeId != null) return false;
        } else if (!AttributeId.equals(other.AttributeId)) return false;
        if (IndexRange==null) {
            if (other.IndexRange != null) return false;
        } else if (!IndexRange.equals(other.IndexRange)) return false;
        if (Value==null) {
            if (other.Value != null) return false;
        } else if (!Value.equals(other.Value)) return false;
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
                + ((Value == null) ? 0 : Value.hashCode());
        return result;
    }
    
 



	public NumericRange getParsedIndexRange(){
		return ParsedIndexRange;
	}

	public static ServiceResult validate(WriteValue value) {
		 // check for null structure.
	    if (value == null){
	        return new ServiceResult(StatusCodes.Bad_StructureMissing);
	    }

	    // null node ids are always invalid.
	    if (value.getNodeId() == null)
	    {
	        return new ServiceResult(StatusCodes.Bad_NodeIdInvalid);
	    }
	    
	    // must be a legimate attribute value.
	    if (!org.opcfoundation.ua.utils.AttributesUtil.isValid(value.AttributeId))
	    {
	        return new ServiceResult(StatusCodes.Bad_AttributeIdInvalid);
	    }

	    // initialize as empty.
	    value.ParsedIndexRange = NumericRange.getEmpty();

	    // parse the index range if specified.
	    if (!(value.IndexRange == null || value.IndexRange.isEmpty()))
	    {
	        try
	        {
	            value.ParsedIndexRange = NumericRange.parse(value.IndexRange);
	        }
	        catch (Exception e)
	        {
	        	return new ServiceResult(StatusCodes.Bad_IndexRangeInvalid, e);
	            
	        }
	        
	        // check that value provided is actually an array.
	       //TODO? Array array = value.Value.Value as Array;

	       /* if (array == null)
	        {
	            return StatusCodes.BadTypeMismatch;
	        }
	        
	        NumericRange range = value.ParsedIndexRange;

	        // check that the number of elements to write matches the index range.
	        if (range.getEnd() >= 0 && (range.getEnd() - range.getBegin() != array.Length))
	        {
	            return StatusCodes.Bad_IndexRangeInvalid;
	        }

	        // check for single element.
	        if (range.End < 0 && array.Length != 1)
	        {
	            return StatusCodes.Bad_IndexRangeInvalid;
	        }*/
	    }
	    else
	    {
	        value.ParsedIndexRange = NumericRange.getEmpty();
	    }

	    // passed basic validation.
	    return null;
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
}
