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
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.core.BrowsePathTarget;



public class BrowsePathResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.BrowsePathResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.BrowsePathResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.BrowsePathResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected BrowsePathTarget[] Targets;
    
    public BrowsePathResult() {}
    
    public BrowsePathResult(StatusCode StatusCode, BrowsePathTarget[] Targets)
    {
        this.StatusCode = StatusCode;
        this.Targets = Targets;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public BrowsePathTarget[] getTargets()
    {
        return Targets;
    }
    
    public void setTargets(BrowsePathTarget[] Targets)
    {
        this.Targets = Targets;
    }
    
    /**
      * Deep clone
      *
      * @return cloned BrowsePathResult
      */
    public BrowsePathResult clone()
    {
        BrowsePathResult result = new BrowsePathResult();
        result.StatusCode = StatusCode;
        if (Targets!=null) {
            result.Targets = new BrowsePathTarget[Targets.length];
            for (int i=0; i<Targets.length; i++)
                result.Targets[i] = Targets[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BrowsePathResult other = (BrowsePathResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (Targets==null) {
            if (other.Targets != null) return false;
        } else if (!Arrays.equals(Targets, other.Targets)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((StatusCode == null) ? 0 : StatusCode.hashCode());
        result = prime * result
                + ((Targets == null) ? 0 : Arrays.hashCode(Targets));
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
		return "BrowsePathResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
