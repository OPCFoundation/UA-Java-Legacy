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
import org.opcfoundation.ua.core.MonitoringMode;
import org.opcfoundation.ua.core.MonitoringParameters;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.utils.AbstractStructure;



public class MonitoredItemCreateRequest extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.MonitoredItemCreateRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.MonitoredItemCreateRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.MonitoredItemCreateRequest_Encoding_DefaultXml);
	
    protected ReadValueId ItemToMonitor;
    protected MonitoringMode MonitoringMode;
    protected MonitoringParameters RequestedParameters;
    
    public MonitoredItemCreateRequest() {}
    
    public MonitoredItemCreateRequest(ReadValueId ItemToMonitor, MonitoringMode MonitoringMode, MonitoringParameters RequestedParameters)
    {
        this.ItemToMonitor = ItemToMonitor;
        this.MonitoringMode = MonitoringMode;
        this.RequestedParameters = RequestedParameters;
    }
    
    public ReadValueId getItemToMonitor()
    {
        return ItemToMonitor;
    }
    
    public void setItemToMonitor(ReadValueId ItemToMonitor)
    {
        this.ItemToMonitor = ItemToMonitor;
    }
    
    public MonitoringMode getMonitoringMode()
    {
        return MonitoringMode;
    }
    
    public void setMonitoringMode(MonitoringMode MonitoringMode)
    {
        this.MonitoringMode = MonitoringMode;
    }
    
    public MonitoringParameters getRequestedParameters()
    {
        return RequestedParameters;
    }
    
    public void setRequestedParameters(MonitoringParameters RequestedParameters)
    {
        this.RequestedParameters = RequestedParameters;
    }
    
    /**
      * Deep clone
      *
      * @return cloned MonitoredItemCreateRequest
      */
    public MonitoredItemCreateRequest clone()
    {
        MonitoredItemCreateRequest result = (MonitoredItemCreateRequest) super.clone();
        result.ItemToMonitor = ItemToMonitor==null ? null : ItemToMonitor.clone();
        result.MonitoringMode = MonitoringMode;
        result.RequestedParameters = RequestedParameters==null ? null : RequestedParameters.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MonitoredItemCreateRequest other = (MonitoredItemCreateRequest) obj;
        if (ItemToMonitor==null) {
            if (other.ItemToMonitor != null) return false;
        } else if (!ItemToMonitor.equals(other.ItemToMonitor)) return false;
        if (MonitoringMode==null) {
            if (other.MonitoringMode != null) return false;
        } else if (!MonitoringMode.equals(other.MonitoringMode)) return false;
        if (RequestedParameters==null) {
            if (other.RequestedParameters != null) return false;
        } else if (!RequestedParameters.equals(other.RequestedParameters)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ItemToMonitor == null) ? 0 : ItemToMonitor.hashCode());
        result = prime * result
                + ((MonitoringMode == null) ? 0 : MonitoringMode.hashCode());
        result = prime * result
                + ((RequestedParameters == null) ? 0 : RequestedParameters.hashCode());
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
		return "MonitoredItemCreateRequest: "+ObjectUtils.printFieldsDeep(this);
	}

}
