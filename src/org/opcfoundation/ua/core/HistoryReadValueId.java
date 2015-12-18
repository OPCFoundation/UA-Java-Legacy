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

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.ServiceResult;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.utils.NumericRange;
import org.opcfoundation.ua.utils.ObjectUtils;



public class HistoryReadValueId extends Object implements Structure, Cloneable {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.HistoryReadValueId);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.HistoryReadValueId_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.HistoryReadValueId_Encoding_DefaultXml);
	NumericRange ParsedIndexRange;

    protected NodeId NodeId;
    protected String IndexRange;
    protected QualifiedName DataEncoding;
    protected byte[] ContinuationPoint;
    
    public HistoryReadValueId() {}
    
    public HistoryReadValueId(NodeId NodeId, String IndexRange, QualifiedName DataEncoding, byte[] ContinuationPoint)
    {
        this.NodeId = NodeId;
        this.IndexRange = IndexRange;
        this.DataEncoding = DataEncoding;
        this.ContinuationPoint = ContinuationPoint;
    }
    
    public NodeId getNodeId()
    {
        return NodeId;
    }
    
    public void setNodeId(NodeId NodeId)
    {
        this.NodeId = NodeId;
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
    
    public byte[] getContinuationPoint()
    {
        return ContinuationPoint;
    }
    
    public void setContinuationPoint(byte[] ContinuationPoint)
    {
        this.ContinuationPoint = ContinuationPoint;
    }
    
    /**
      * Deep clone
      *
      * @return cloned HistoryReadValueId
      */
    public HistoryReadValueId clone()
    {
        HistoryReadValueId result = new HistoryReadValueId();
        result.NodeId = NodeId;
        result.IndexRange = IndexRange;
        result.DataEncoding = DataEncoding;
        result.ContinuationPoint = ContinuationPoint;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        HistoryReadValueId other = (HistoryReadValueId) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (IndexRange==null) {
            if (other.IndexRange != null) return false;
        } else if (!IndexRange.equals(other.IndexRange)) return false;
        if (DataEncoding==null) {
            if (other.DataEncoding != null) return false;
        } else if (!DataEncoding.equals(other.DataEncoding)) return false;
        if (ContinuationPoint==null) {
            if (other.ContinuationPoint != null) return false;
        } else if (!ContinuationPoint.equals(other.ContinuationPoint)) return false;
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
                + ((IndexRange == null) ? 0 : IndexRange.hashCode());
        result = prime * result
                + ((DataEncoding == null) ? 0 : DataEncoding.hashCode());
        result = prime * result
                + ((ContinuationPoint == null) ? 0 : ContinuationPoint.hashCode());
        return result;
    }
    
 

	public static ServiceResult validate(HistoryReadValueId valueId){
		// Check for null structure.
		if (valueId == null) {
			return new ServiceResult(StatusCodes.Bad_StructureMissing);
		}

		// Null node ids are always invalid.
		if (valueId.getNodeId() == null) {
			return new ServiceResult(StatusCodes.Bad_NodeIdInvalid);
		}
	
		//init as empty
		NumericRange range = NumericRange.getEmpty();
		if(!(valueId.getIndexRange() == null || valueId.getIndexRange().isEmpty())){
			try{
				range = NumericRange.parse(valueId.getIndexRange());
				valueId.setParsedIndexRange(range);
				
			}
			catch (Exception e) {
				return new ServiceResult(StatusCodes.Bad_IndexRangeInvalid, e);
			}
		} else {
			valueId.setParsedIndexRange(NumericRange.getEmpty());
		}

		// passed basic validation
		return null;
	}


	public NumericRange getParsedIndexRange() {
		return ParsedIndexRange;
	}

	public void setParsedIndexRange(NumericRange parsedIndexRange) {
		ParsedIndexRange = parsedIndexRange;
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
		return "HistoryReadValueId: "+ObjectUtils.printFieldsDeep(this);
	}

}
