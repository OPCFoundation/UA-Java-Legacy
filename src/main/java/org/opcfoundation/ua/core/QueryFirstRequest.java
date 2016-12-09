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

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.ContentFilter;
import org.opcfoundation.ua.core.NodeTypeDescription;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.ViewDescription;
import org.opcfoundation.ua.utils.AbstractStructure;


public class QueryFirstRequest extends AbstractStructure implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.QueryFirstRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.QueryFirstRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.QueryFirstRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected ViewDescription View;
    protected NodeTypeDescription[] NodeTypes;
    protected ContentFilter Filter;
    protected UnsignedInteger MaxDataSetsToReturn;
    protected UnsignedInteger MaxReferencesToReturn;
    
    public QueryFirstRequest() {}
    
    public QueryFirstRequest(RequestHeader RequestHeader, ViewDescription View, NodeTypeDescription[] NodeTypes, ContentFilter Filter, UnsignedInteger MaxDataSetsToReturn, UnsignedInteger MaxReferencesToReturn)
    {
        this.RequestHeader = RequestHeader;
        this.View = View;
        this.NodeTypes = NodeTypes;
        this.Filter = Filter;
        this.MaxDataSetsToReturn = MaxDataSetsToReturn;
        this.MaxReferencesToReturn = MaxReferencesToReturn;
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
    
    public NodeTypeDescription[] getNodeTypes()
    {
        return NodeTypes;
    }
    
    public void setNodeTypes(NodeTypeDescription[] NodeTypes)
    {
        this.NodeTypes = NodeTypes;
    }
    
    public ContentFilter getFilter()
    {
        return Filter;
    }
    
    public void setFilter(ContentFilter Filter)
    {
        this.Filter = Filter;
    }
    
    public UnsignedInteger getMaxDataSetsToReturn()
    {
        return MaxDataSetsToReturn;
    }
    
    public void setMaxDataSetsToReturn(UnsignedInteger MaxDataSetsToReturn)
    {
        this.MaxDataSetsToReturn = MaxDataSetsToReturn;
    }
    
    public UnsignedInteger getMaxReferencesToReturn()
    {
        return MaxReferencesToReturn;
    }
    
    public void setMaxReferencesToReturn(UnsignedInteger MaxReferencesToReturn)
    {
        this.MaxReferencesToReturn = MaxReferencesToReturn;
    }
    
    /**
      * Deep clone
      *
      * @return cloned QueryFirstRequest
      */
    public QueryFirstRequest clone()
    {
        QueryFirstRequest result = (QueryFirstRequest) super.clone();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.View = View==null ? null : View.clone();
        if (NodeTypes!=null) {
            result.NodeTypes = new NodeTypeDescription[NodeTypes.length];
            for (int i=0; i<NodeTypes.length; i++)
                result.NodeTypes[i] = NodeTypes[i].clone();
        }
        result.Filter = Filter==null ? null : Filter.clone();
        result.MaxDataSetsToReturn = MaxDataSetsToReturn;
        result.MaxReferencesToReturn = MaxReferencesToReturn;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        QueryFirstRequest other = (QueryFirstRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (View==null) {
            if (other.View != null) return false;
        } else if (!View.equals(other.View)) return false;
        if (NodeTypes==null) {
            if (other.NodeTypes != null) return false;
        } else if (!Arrays.equals(NodeTypes, other.NodeTypes)) return false;
        if (Filter==null) {
            if (other.Filter != null) return false;
        } else if (!Filter.equals(other.Filter)) return false;
        if (MaxDataSetsToReturn==null) {
            if (other.MaxDataSetsToReturn != null) return false;
        } else if (!MaxDataSetsToReturn.equals(other.MaxDataSetsToReturn)) return false;
        if (MaxReferencesToReturn==null) {
            if (other.MaxReferencesToReturn != null) return false;
        } else if (!MaxReferencesToReturn.equals(other.MaxReferencesToReturn)) return false;
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
                + ((NodeTypes == null) ? 0 : Arrays.hashCode(NodeTypes));
        result = prime * result
                + ((Filter == null) ? 0 : Filter.hashCode());
        result = prime * result
                + ((MaxDataSetsToReturn == null) ? 0 : MaxDataSetsToReturn.hashCode());
        result = prime * result
                + ((MaxReferencesToReturn == null) ? 0 : MaxReferencesToReturn.hashCode());
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
