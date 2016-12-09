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
import org.opcfoundation.ua.core.HistoryUpdateType;
import org.opcfoundation.ua.utils.AbstractStructure;



public class ModificationInfo extends AbstractStructure {
	
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
        ModificationInfo result = (ModificationInfo) super.clone();
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
