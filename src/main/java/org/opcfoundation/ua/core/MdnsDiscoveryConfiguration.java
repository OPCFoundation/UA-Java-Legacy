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
import org.opcfoundation.ua.core.DiscoveryConfiguration;



public class MdnsDiscoveryConfiguration extends DiscoveryConfiguration {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.MdnsDiscoveryConfiguration);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.MdnsDiscoveryConfiguration_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.MdnsDiscoveryConfiguration_Encoding_DefaultXml);
	
    protected String MdnsServerName;
    protected String[] ServerCapabilities;
    
    public MdnsDiscoveryConfiguration() {}
    
    public MdnsDiscoveryConfiguration(String MdnsServerName, String[] ServerCapabilities)
    {
        this.MdnsServerName = MdnsServerName;
        this.ServerCapabilities = ServerCapabilities;
    }
    
    public String getMdnsServerName()
    {
        return MdnsServerName;
    }
    
    public void setMdnsServerName(String MdnsServerName)
    {
        this.MdnsServerName = MdnsServerName;
    }
    
    public String[] getServerCapabilities()
    {
        return ServerCapabilities;
    }
    
    public void setServerCapabilities(String[] ServerCapabilities)
    {
        this.ServerCapabilities = ServerCapabilities;
    }
    
    /**
      * Deep clone
      *
      * @return cloned MdnsDiscoveryConfiguration
      */
    public MdnsDiscoveryConfiguration clone()
    {
        MdnsDiscoveryConfiguration result = (MdnsDiscoveryConfiguration) super.clone();
        result.MdnsServerName = MdnsServerName;
        result.ServerCapabilities = ServerCapabilities==null ? null : ServerCapabilities.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MdnsDiscoveryConfiguration other = (MdnsDiscoveryConfiguration) obj;
        if (MdnsServerName==null) {
            if (other.MdnsServerName != null) return false;
        } else if (!MdnsServerName.equals(other.MdnsServerName)) return false;
        if (ServerCapabilities==null) {
            if (other.ServerCapabilities != null) return false;
        } else if (!Arrays.equals(ServerCapabilities, other.ServerCapabilities)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((MdnsServerName == null) ? 0 : MdnsServerName.hashCode());
        result = prime * result
                + ((ServerCapabilities == null) ? 0 : Arrays.hashCode(ServerCapabilities));
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
		return "MdnsDiscoveryConfiguration: "+ObjectUtils.printFieldsDeep(this);
	}

}
