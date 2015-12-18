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
import org.opcfoundation.ua.core.HistoryData;
import org.opcfoundation.ua.core.ModificationInfo;



public class HistoryModifiedData extends HistoryData implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.HistoryModifiedData);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.HistoryModifiedData_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.HistoryModifiedData_Encoding_DefaultXml);
	
    protected ModificationInfo[] ModificationInfos;
    
    public HistoryModifiedData() {}
    
    public HistoryModifiedData(DataValue[] DataValues, ModificationInfo[] ModificationInfos)
    {
        super(DataValues);
        this.ModificationInfos = ModificationInfos;
    }
    
    public ModificationInfo[] getModificationInfos()
    {
        return ModificationInfos;
    }
    
    public void setModificationInfos(ModificationInfo[] ModificationInfos)
    {
        this.ModificationInfos = ModificationInfos;
    }
    
    /**
      * Deep clone
      *
      * @return cloned HistoryModifiedData
      */
    public HistoryModifiedData clone()
    {
        HistoryModifiedData result = new HistoryModifiedData();
        result.DataValues = DataValues==null ? null : DataValues.clone();
        if (ModificationInfos!=null) {
            result.ModificationInfos = new ModificationInfo[ModificationInfos.length];
            for (int i=0; i<ModificationInfos.length; i++)
                result.ModificationInfos[i] = ModificationInfos[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        HistoryModifiedData other = (HistoryModifiedData) obj;
        if (DataValues==null) {
            if (other.DataValues != null) return false;
        } else if (!Arrays.equals(DataValues, other.DataValues)) return false;
        if (ModificationInfos==null) {
            if (other.ModificationInfos != null) return false;
        } else if (!Arrays.equals(ModificationInfos, other.ModificationInfos)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((DataValues == null) ? 0 : Arrays.hashCode(DataValues));
        result = prime * result
                + ((ModificationInfos == null) ? 0 : Arrays.hashCode(ModificationInfos));
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
		return "HistoryModifiedData: "+ObjectUtils.printFieldsDeep(this);
	}

}
