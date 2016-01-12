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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.core.HistoryUpdateType;



public class ModificationInfo extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ModificationInfo);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ModificationInfo_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ModificationInfo_Encoding_DefaultXml);
	
    protected DateTime ModificationTime;
    protected HistoryUpdateType UpdateType;
    protected String UserName;
    
    public ModificationInfo() {}
    
    public ModificationInfo(DateTime ModificationTime, HistoryUpdateType UpdateType, String UserName)
    {
        this.ModificationTime = ModificationTime;
        this.UpdateType = UpdateType;
        this.UserName = UserName;
    }
    
    public DateTime getModificationTime()
    {
        return ModificationTime;
    }
    
    public void setModificationTime(DateTime ModificationTime)
    {
        this.ModificationTime = ModificationTime;
    }
    
    public HistoryUpdateType getUpdateType()
    {
        return UpdateType;
    }
    
    public void setUpdateType(HistoryUpdateType UpdateType)
    {
        this.UpdateType = UpdateType;
    }
    
    public String getUserName()
    {
        return UserName;
    }
    
    public void setUserName(String UserName)
    {
        this.UserName = UserName;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ModificationInfo
      */
    public ModificationInfo clone()
    {
        ModificationInfo result = new ModificationInfo();
        result.ModificationTime = ModificationTime;
        result.UpdateType = UpdateType;
        result.UserName = UserName;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ModificationInfo other = (ModificationInfo) obj;
        if (ModificationTime==null) {
            if (other.ModificationTime != null) return false;
        } else if (!ModificationTime.equals(other.ModificationTime)) return false;
        if (UpdateType==null) {
            if (other.UpdateType != null) return false;
        } else if (!UpdateType.equals(other.UpdateType)) return false;
        if (UserName==null) {
            if (other.UserName != null) return false;
        } else if (!UserName.equals(other.UserName)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ModificationTime == null) ? 0 : ModificationTime.hashCode());
        result = prime * result
                + ((UpdateType == null) ? 0 : UpdateType.hashCode());
        result = prime * result
                + ((UserName == null) ? 0 : UserName.hashCode());
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
		return "ModificationInfo: "+ObjectUtils.printFieldsDeep(this);
	}

}
