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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.utils.AbstractStructure;



public class RelativePathElement extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.RelativePathElement);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.RelativePathElement_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.RelativePathElement_Encoding_DefaultXml);
	
    protected NodeId ReferenceTypeId;
    protected Boolean IsInverse;
    protected Boolean IncludeSubtypes;
    protected QualifiedName TargetName;
    
    public RelativePathElement() {}
    
    public RelativePathElement(NodeId ReferenceTypeId, Boolean IsInverse, Boolean IncludeSubtypes, QualifiedName TargetName)
    {
        this.ReferenceTypeId = ReferenceTypeId;
        this.IsInverse = IsInverse;
        this.IncludeSubtypes = IncludeSubtypes;
        this.TargetName = TargetName;
    }
    
    public NodeId getReferenceTypeId()
    {
        return ReferenceTypeId;
    }
    
    public void setReferenceTypeId(NodeId ReferenceTypeId)
    {
        this.ReferenceTypeId = ReferenceTypeId;
    }
    
    public Boolean getIsInverse()
    {
        return IsInverse;
    }
    
    public void setIsInverse(Boolean IsInverse)
    {
        this.IsInverse = IsInverse;
    }
    
    public Boolean getIncludeSubtypes()
    {
        return IncludeSubtypes;
    }
    
    public void setIncludeSubtypes(Boolean IncludeSubtypes)
    {
        this.IncludeSubtypes = IncludeSubtypes;
    }
    
    public QualifiedName getTargetName()
    {
        return TargetName;
    }
    
    public void setTargetName(QualifiedName TargetName)
    {
        this.TargetName = TargetName;
    }
    
    /**
      * Deep clone
      *
      * @return cloned RelativePathElement
      */
    public RelativePathElement clone()
    {
        RelativePathElement result = (RelativePathElement) super.clone();
        result.ReferenceTypeId = ReferenceTypeId;
        result.IsInverse = IsInverse;
        result.IncludeSubtypes = IncludeSubtypes;
        result.TargetName = TargetName;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RelativePathElement other = (RelativePathElement) obj;
        if (ReferenceTypeId==null) {
            if (other.ReferenceTypeId != null) return false;
        } else if (!ReferenceTypeId.equals(other.ReferenceTypeId)) return false;
        if (IsInverse==null) {
            if (other.IsInverse != null) return false;
        } else if (!IsInverse.equals(other.IsInverse)) return false;
        if (IncludeSubtypes==null) {
            if (other.IncludeSubtypes != null) return false;
        } else if (!IncludeSubtypes.equals(other.IncludeSubtypes)) return false;
        if (TargetName==null) {
            if (other.TargetName != null) return false;
        } else if (!TargetName.equals(other.TargetName)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ReferenceTypeId == null) ? 0 : ReferenceTypeId.hashCode());
        result = prime * result
                + ((IsInverse == null) ? 0 : IsInverse.hashCode());
        result = prime * result
                + ((IncludeSubtypes == null) ? 0 : IncludeSubtypes.hashCode());
        result = prime * result
                + ((TargetName == null) ? 0 : TargetName.hashCode());
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
		return "RelativePathElement: "+ObjectUtils.printFieldsDeep(this);
	}

}
