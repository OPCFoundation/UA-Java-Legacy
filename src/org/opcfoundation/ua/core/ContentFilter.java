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
import org.opcfoundation.ua.core.ContentFilterElement;



public class ContentFilter extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ContentFilter);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ContentFilter_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ContentFilter_Encoding_DefaultXml);
	
    protected ContentFilterElement[] Elements;
    
    public ContentFilter() {}
    
    public ContentFilter(ContentFilterElement[] Elements)
    {
        this.Elements = Elements;
    }
    
    public ContentFilterElement[] getElements()
    {
        return Elements;
    }
    
    public void setElements(ContentFilterElement[] Elements)
    {
        this.Elements = Elements;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ContentFilter
      */
    public ContentFilter clone()
    {
        ContentFilter result = new ContentFilter();
        if (Elements!=null) {
            result.Elements = new ContentFilterElement[Elements.length];
            for (int i=0; i<Elements.length; i++)
                result.Elements[i] = Elements[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ContentFilter other = (ContentFilter) obj;
        if (Elements==null) {
            if (other.Elements != null) return false;
        } else if (!Arrays.equals(Elements, other.Elements)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Elements == null) ? 0 : Arrays.hashCode(Elements));
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
		return "ContentFilter: "+ObjectUtils.printFieldsDeep(this);
	}

}
