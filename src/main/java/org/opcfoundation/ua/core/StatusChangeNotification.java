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
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.core.NotificationData;



public class StatusChangeNotification extends NotificationData {
	
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
        StatusChangeNotification result = (StatusChangeNotification) super.clone();
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
