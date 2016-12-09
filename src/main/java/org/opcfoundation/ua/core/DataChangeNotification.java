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
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.core.MonitoredItemNotification;
import org.opcfoundation.ua.core.NotificationData;



public class DataChangeNotification extends NotificationData {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DataChangeNotification);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DataChangeNotification_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DataChangeNotification_Encoding_DefaultXml);
	
    protected MonitoredItemNotification[] MonitoredItems;
    protected DiagnosticInfo[] DiagnosticInfos;
    
    public DataChangeNotification() {}
    
    public DataChangeNotification(MonitoredItemNotification[] MonitoredItems, DiagnosticInfo[] DiagnosticInfos)
    {
        this.MonitoredItems = MonitoredItems;
        this.DiagnosticInfos = DiagnosticInfos;
    }
    
    public MonitoredItemNotification[] getMonitoredItems()
    {
        return MonitoredItems;
    }
    
    public void setMonitoredItems(MonitoredItemNotification[] MonitoredItems)
    {
        this.MonitoredItems = MonitoredItems;
    }
    
    public DiagnosticInfo[] getDiagnosticInfos()
    {
        return DiagnosticInfos;
    }
    
    public void setDiagnosticInfos(DiagnosticInfo[] DiagnosticInfos)
    {
        this.DiagnosticInfos = DiagnosticInfos;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DataChangeNotification
      */
    public DataChangeNotification clone()
    {
        DataChangeNotification result = (DataChangeNotification) super.clone();
        if (MonitoredItems!=null) {
            result.MonitoredItems = new MonitoredItemNotification[MonitoredItems.length];
            for (int i=0; i<MonitoredItems.length; i++)
                result.MonitoredItems[i] = MonitoredItems[i].clone();
        }
        result.DiagnosticInfos = DiagnosticInfos==null ? null : DiagnosticInfos.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DataChangeNotification other = (DataChangeNotification) obj;
        if (MonitoredItems==null) {
            if (other.MonitoredItems != null) return false;
        } else if (!Arrays.equals(MonitoredItems, other.MonitoredItems)) return false;
        if (DiagnosticInfos==null) {
            if (other.DiagnosticInfos != null) return false;
        } else if (!Arrays.equals(DiagnosticInfos, other.DiagnosticInfos)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((MonitoredItems == null) ? 0 : Arrays.hashCode(MonitoredItems));
        result = prime * result
                + ((DiagnosticInfos == null) ? 0 : Arrays.hashCode(DiagnosticInfos));
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
		return "DataChangeNotification: "+ObjectUtils.printFieldsDeep(this);
	}

}
