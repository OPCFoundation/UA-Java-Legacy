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
import org.opcfoundation.ua.core.RelativePathElement;



public class RelativePath extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.RelativePath);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.RelativePath_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.RelativePath_Encoding_DefaultXml);
	
    protected RelativePathElement[] Elements;
    
    public RelativePath() {}
    
    public RelativePath(RelativePathElement[] Elements)
    {
        this.Elements = Elements;
    }
    
    public RelativePathElement[] getElements()
    {
        return Elements;
    }
    
    public void setElements(RelativePathElement[] Elements)
    {
        this.Elements = Elements;
    }
    
    /**
      * Deep clone
      *
      * @return cloned RelativePath
      */
    public RelativePath clone()
    {
        RelativePath result = new RelativePath();
        if (Elements!=null) {
            result.Elements = new RelativePathElement[Elements.length];
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
        RelativePath other = (RelativePath) obj;
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
		return "RelativePath: "+ObjectUtils.printFieldsDeep(this);
	}

}
