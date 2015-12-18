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
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.HistoryUpdateDetails;
import org.opcfoundation.ua.core.PerformUpdateType;



public class UpdateStructureDataDetails extends HistoryUpdateDetails implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.UpdateStructureDataDetails);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.UpdateStructureDataDetails_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.UpdateStructureDataDetails_Encoding_DefaultXml);
	
    protected PerformUpdateType PerformInsertReplace;
    protected DataValue[] UpdateValues;
    
    public UpdateStructureDataDetails() {}
    
    public UpdateStructureDataDetails(NodeId NodeId, PerformUpdateType PerformInsertReplace, DataValue[] UpdateValues)
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
      * @return cloned UpdateStructureDataDetails
      */
    public UpdateStructureDataDetails clone()
    {
        UpdateStructureDataDetails result = new UpdateStructureDataDetails();
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
        UpdateStructureDataDetails other = (UpdateStructureDataDetails) obj;
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
		return "UpdateStructureDataDetails: "+ObjectUtils.printFieldsDeep(this);
	}

}
