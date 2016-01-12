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

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.BrowseDescription;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.ViewDescription;


public class BrowseRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.BrowseRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.BrowseRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.BrowseRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected ViewDescription View;
    protected UnsignedInteger RequestedMaxReferencesPerNode;
    protected BrowseDescription[] NodesToBrowse;
    
    public BrowseRequest() {}
    
    public BrowseRequest(RequestHeader RequestHeader, ViewDescription View, UnsignedInteger RequestedMaxReferencesPerNode, BrowseDescription[] NodesToBrowse)
    {
        this.RequestHeader = RequestHeader;
        this.View = View;
        this.RequestedMaxReferencesPerNode = RequestedMaxReferencesPerNode;
        this.NodesToBrowse = NodesToBrowse;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public ViewDescription getView()
    {
        return View;
    }
    
    public void setView(ViewDescription View)
    {
        this.View = View;
    }
    
    public UnsignedInteger getRequestedMaxReferencesPerNode()
    {
        return RequestedMaxReferencesPerNode;
    }
    
    public void setRequestedMaxReferencesPerNode(UnsignedInteger RequestedMaxReferencesPerNode)
    {
        this.RequestedMaxReferencesPerNode = RequestedMaxReferencesPerNode;
    }
    
    public BrowseDescription[] getNodesToBrowse()
    {
        return NodesToBrowse;
    }
    
    public void setNodesToBrowse(BrowseDescription[] NodesToBrowse)
    {
        this.NodesToBrowse = NodesToBrowse;
    }
    
    /**
      * Deep clone
      *
      * @return cloned BrowseRequest
      */
    public BrowseRequest clone()
    {
        BrowseRequest result = new BrowseRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.View = View==null ? null : View.clone();
        result.RequestedMaxReferencesPerNode = RequestedMaxReferencesPerNode;
        if (NodesToBrowse!=null) {
            result.NodesToBrowse = new BrowseDescription[NodesToBrowse.length];
            for (int i=0; i<NodesToBrowse.length; i++)
                result.NodesToBrowse[i] = NodesToBrowse[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BrowseRequest other = (BrowseRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (View==null) {
            if (other.View != null) return false;
        } else if (!View.equals(other.View)) return false;
        if (RequestedMaxReferencesPerNode==null) {
            if (other.RequestedMaxReferencesPerNode != null) return false;
        } else if (!RequestedMaxReferencesPerNode.equals(other.RequestedMaxReferencesPerNode)) return false;
        if (NodesToBrowse==null) {
            if (other.NodesToBrowse != null) return false;
        } else if (!Arrays.equals(NodesToBrowse, other.NodesToBrowse)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((RequestHeader == null) ? 0 : RequestHeader.hashCode());
        result = prime * result
                + ((View == null) ? 0 : View.hashCode());
        result = prime * result
                + ((RequestedMaxReferencesPerNode == null) ? 0 : RequestedMaxReferencesPerNode.hashCode());
        result = prime * result
                + ((NodesToBrowse == null) ? 0 : Arrays.hashCode(NodesToBrowse));
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
		return ObjectUtils.printFieldsDeep(this);
	}
	
}
