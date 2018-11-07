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
import org.opcfoundation.ua.common.NamespaceTable;

import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.utils.AbstractStructure;



public class EndpointType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.EndpointType.getValue());
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.EndpointType_Encoding_DefaultBinary.getValue());
	public static final ExpandedNodeId XML = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.EndpointType_Encoding_DefaultXml.getValue());
	
    protected String EndpointUrl;
    protected MessageSecurityMode SecurityMode;
    protected String SecurityPolicyUri;
    protected String TransportProfileUri;
    
    public EndpointType() {}
    
    public EndpointType(String EndpointUrl, MessageSecurityMode SecurityMode, String SecurityPolicyUri, String TransportProfileUri)
    {
        this.EndpointUrl = EndpointUrl;
        this.SecurityMode = SecurityMode;
        this.SecurityPolicyUri = SecurityPolicyUri;
        this.TransportProfileUri = TransportProfileUri;
    }
    
    public String getEndpointUrl()
    {
        return EndpointUrl;
    }
    
    public void setEndpointUrl(String EndpointUrl)
    {
        this.EndpointUrl = EndpointUrl;
    }
    
    public MessageSecurityMode getSecurityMode()
    {
        return SecurityMode;
    }
    
    public void setSecurityMode(MessageSecurityMode SecurityMode)
    {
        this.SecurityMode = SecurityMode;
    }
    
    public String getSecurityPolicyUri()
    {
        return SecurityPolicyUri;
    }
    
    public void setSecurityPolicyUri(String SecurityPolicyUri)
    {
        this.SecurityPolicyUri = SecurityPolicyUri;
    }
    
    public String getTransportProfileUri()
    {
        return TransportProfileUri;
    }
    
    public void setTransportProfileUri(String TransportProfileUri)
    {
        this.TransportProfileUri = TransportProfileUri;
    }
    
    /**
      * Deep clone
      *
      * @return cloned EndpointType
      */
    public EndpointType clone()
    {
        EndpointType result = (EndpointType) super.clone();
        result.EndpointUrl = EndpointUrl;
        result.SecurityMode = SecurityMode;
        result.SecurityPolicyUri = SecurityPolicyUri;
        result.TransportProfileUri = TransportProfileUri;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EndpointType other = (EndpointType) obj;
        if (EndpointUrl==null) {
            if (other.EndpointUrl != null) return false;
        } else if (!EndpointUrl.equals(other.EndpointUrl)) return false;
        if (SecurityMode==null) {
            if (other.SecurityMode != null) return false;
        } else if (!SecurityMode.equals(other.SecurityMode)) return false;
        if (SecurityPolicyUri==null) {
            if (other.SecurityPolicyUri != null) return false;
        } else if (!SecurityPolicyUri.equals(other.SecurityPolicyUri)) return false;
        if (TransportProfileUri==null) {
            if (other.TransportProfileUri != null) return false;
        } else if (!TransportProfileUri.equals(other.TransportProfileUri)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((EndpointUrl == null) ? 0 : EndpointUrl.hashCode());
        result = prime * result
                + ((SecurityMode == null) ? 0 : SecurityMode.hashCode());
        result = prime * result
                + ((SecurityPolicyUri == null) ? 0 : SecurityPolicyUri.hashCode());
        result = prime * result
                + ((TransportProfileUri == null) ? 0 : TransportProfileUri.hashCode());
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
		return "EndpointType: "+ObjectUtils.printFieldsDeep(this);
	}

}
