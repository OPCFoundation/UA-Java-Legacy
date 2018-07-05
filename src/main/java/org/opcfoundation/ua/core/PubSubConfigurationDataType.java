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
import org.opcfoundation.ua.core.PubSubConnectionDataType;
import org.opcfoundation.ua.core.PublishedDataSetDataType;
import org.opcfoundation.ua.utils.AbstractStructure;



public class PubSubConfigurationDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.PubSubConfigurationDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.PubSubConfigurationDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.PubSubConfigurationDataType_Encoding_DefaultXml);
	
    protected PublishedDataSetDataType[] PublishedDataSets;
    protected PubSubConnectionDataType[] Connections;
    protected Boolean Enabled;
    
    public PubSubConfigurationDataType() {}
    
    public PubSubConfigurationDataType(PublishedDataSetDataType[] PublishedDataSets, PubSubConnectionDataType[] Connections, Boolean Enabled)
    {
        this.PublishedDataSets = PublishedDataSets;
        this.Connections = Connections;
        this.Enabled = Enabled;
    }
    
    public PublishedDataSetDataType[] getPublishedDataSets()
    {
        return PublishedDataSets;
    }
    
    public void setPublishedDataSets(PublishedDataSetDataType[] PublishedDataSets)
    {
        this.PublishedDataSets = PublishedDataSets;
    }
    
    public PubSubConnectionDataType[] getConnections()
    {
        return Connections;
    }
    
    public void setConnections(PubSubConnectionDataType[] Connections)
    {
        this.Connections = Connections;
    }
    
    public Boolean getEnabled()
    {
        return Enabled;
    }
    
    public void setEnabled(Boolean Enabled)
    {
        this.Enabled = Enabled;
    }
    
    /**
      * Deep clone
      *
      * @return cloned PubSubConfigurationDataType
      */
    public PubSubConfigurationDataType clone()
    {
        PubSubConfigurationDataType result = (PubSubConfigurationDataType) super.clone();
        if (PublishedDataSets!=null) {
            result.PublishedDataSets = new PublishedDataSetDataType[PublishedDataSets.length];
            for (int i=0; i<PublishedDataSets.length; i++)
                result.PublishedDataSets[i] = PublishedDataSets[i].clone();
        }
        if (Connections!=null) {
            result.Connections = new PubSubConnectionDataType[Connections.length];
            for (int i=0; i<Connections.length; i++)
                result.Connections[i] = Connections[i].clone();
        }
        result.Enabled = Enabled;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PubSubConfigurationDataType other = (PubSubConfigurationDataType) obj;
        if (PublishedDataSets==null) {
            if (other.PublishedDataSets != null) return false;
        } else if (!Arrays.equals(PublishedDataSets, other.PublishedDataSets)) return false;
        if (Connections==null) {
            if (other.Connections != null) return false;
        } else if (!Arrays.equals(Connections, other.Connections)) return false;
        if (Enabled==null) {
            if (other.Enabled != null) return false;
        } else if (!Enabled.equals(other.Enabled)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((PublishedDataSets == null) ? 0 : Arrays.hashCode(PublishedDataSets));
        result = prime * result
                + ((Connections == null) ? 0 : Arrays.hashCode(Connections));
        result = prime * result
                + ((Enabled == null) ? 0 : Enabled.hashCode());
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
		return "PubSubConfigurationDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
