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
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.core.MonitoredItemNotification;
import org.opcfoundation.ua.core.NotificationData;



public class DataChangeNotification extends NotificationData implements Structure, Cloneable {
	
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
        DataChangeNotification result = new DataChangeNotification();
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
