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
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.core.NotificationData;



public class StatusChangeNotification extends NotificationData implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.StatusChangeNotification);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.StatusChangeNotification_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.StatusChangeNotification_Encoding_DefaultXml);
	
    protected StatusCode Status;
    protected DiagnosticInfo DiagnosticInfo;
    
    public StatusChangeNotification() {}
    
    public StatusChangeNotification(StatusCode Status, DiagnosticInfo DiagnosticInfo)
    {
        this.Status = Status;
        this.DiagnosticInfo = DiagnosticInfo;
    }
    
    public StatusCode getStatus()
    {
        return Status;
    }
    
    public void setStatus(StatusCode Status)
    {
        this.Status = Status;
    }
    
    public DiagnosticInfo getDiagnosticInfo()
    {
        return DiagnosticInfo;
    }
    
    public void setDiagnosticInfo(DiagnosticInfo DiagnosticInfo)
    {
        this.DiagnosticInfo = DiagnosticInfo;
    }
    
    /**
      * Deep clone
      *
      * @return cloned StatusChangeNotification
      */
    public StatusChangeNotification clone()
    {
        StatusChangeNotification result = new StatusChangeNotification();
        result.Status = Status;
        result.DiagnosticInfo = DiagnosticInfo;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StatusChangeNotification other = (StatusChangeNotification) obj;
        if (Status==null) {
            if (other.Status != null) return false;
        } else if (!Status.equals(other.Status)) return false;
        if (DiagnosticInfo==null) {
            if (other.DiagnosticInfo != null) return false;
        } else if (!DiagnosticInfo.equals(other.DiagnosticInfo)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Status == null) ? 0 : Status.hashCode());
        result = prime * result
                + ((DiagnosticInfo == null) ? 0 : DiagnosticInfo.hashCode());
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
		return "StatusChangeNotification: "+ObjectUtils.printFieldsDeep(this);
	}

}
