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
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.HistoryUpdateDetails;
import org.opcfoundation.ua.core.PerformUpdateType;



public class UpdateDataDetails extends HistoryUpdateDetails {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.UpdateDataDetails);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.UpdateDataDetails_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.UpdateDataDetails_Encoding_DefaultXml);
	
    protected PerformUpdateType PerformInsertReplace;
    protected DataValue[] UpdateValues;
    
    public UpdateDataDetails() {}
    
    public UpdateDataDetails(NodeId NodeId, PerformUpdateType PerformInsertReplace, DataValue[] UpdateValues)
    {
        super(NodeId);
        this.PerformInsertReplace = PerformInsertReplace;
        this.UpdateValues = UpdateValues;
    }
    
    public PerformUpdateType getPerformInsertReplace()
    {
        return PerformInsertReplace;
    }
    
    public void setPerformInsertReplace(PerformUpdateType PerformInsertReplace)
    {
        this.PerformInsertReplace = PerformInsertReplace;
    }
    
    public DataValue[] getUpdateValues()
    {
        return UpdateValues;
    }
    
    public void setUpdateValues(DataValue[] UpdateValues)
    {
        this.UpdateValues = UpdateValues;
    }
    
    /**
      * Deep clone
      *
      * @return cloned UpdateDataDetails
      */
    public UpdateDataDetails clone()
    {
        UpdateDataDetails result = (UpdateDataDetails) super.clone();
        result.NodeId = NodeId;
        result.PerformInsertReplace = PerformInsertReplace;
        result.UpdateValues = UpdateValues==null ? null : UpdateValues.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UpdateDataDetails other = (UpdateDataDetails) obj;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (PerformInsertReplace==null) {
            if (other.PerformInsertReplace != null) return false;
        } else if (!PerformInsertReplace.equals(other.PerformInsertReplace)) return false;
        if (UpdateValues==null) {
            if (other.UpdateValues != null) return false;
        } else if (!Arrays.equals(UpdateValues, other.UpdateValues)) return false;
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
                + ((PerformInsertReplace == null) ? 0 : PerformInsertReplace.hashCode());
        result = prime * result
                + ((UpdateValues == null) ? 0 : Arrays.hashCode(UpdateValues));
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
		return "UpdateDataDetails: "+ObjectUtils.printFieldsDeep(this);
	}

}
