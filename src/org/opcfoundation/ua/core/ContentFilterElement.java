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
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.core.FilterOperator;



public class ContentFilterElement extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ContentFilterElement);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ContentFilterElement_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ContentFilterElement_Encoding_DefaultXml);
	
    protected FilterOperator FilterOperator;
    protected ExtensionObject[] FilterOperands;
    
    public ContentFilterElement() {}
    
    public ContentFilterElement(FilterOperator FilterOperator, ExtensionObject[] FilterOperands)
    {
        this.FilterOperator = FilterOperator;
        this.FilterOperands = FilterOperands;
    }
    
    public FilterOperator getFilterOperator()
    {
        return FilterOperator;
    }
    
    public void setFilterOperator(FilterOperator FilterOperator)
    {
        this.FilterOperator = FilterOperator;
    }
    
    public ExtensionObject[] getFilterOperands()
    {
        return FilterOperands;
    }
    
    public void setFilterOperands(ExtensionObject[] FilterOperands)
    {
        this.FilterOperands = FilterOperands;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ContentFilterElement
      */
    public ContentFilterElement clone()
    {
        ContentFilterElement result = new ContentFilterElement();
        result.FilterOperator = FilterOperator;
        result.FilterOperands = FilterOperands==null ? null : FilterOperands.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ContentFilterElement other = (ContentFilterElement) obj;
        if (FilterOperator==null) {
            if (other.FilterOperator != null) return false;
        } else if (!FilterOperator.equals(other.FilterOperator)) return false;
        if (FilterOperands==null) {
            if (other.FilterOperands != null) return false;
        } else if (!Arrays.equals(FilterOperands, other.FilterOperands)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((FilterOperator == null) ? 0 : FilterOperator.hashCode());
        result = prime * result
                + ((FilterOperands == null) ? 0 : Arrays.hashCode(FilterOperands));
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
		return "ContentFilterElement: "+ObjectUtils.printFieldsDeep(this);
	}

}
