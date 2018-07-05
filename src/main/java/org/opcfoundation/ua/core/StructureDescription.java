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
import org.opcfoundation.ua.core.DataTypeDescription;
import org.opcfoundation.ua.core.StructureDefinition;



public class StructureDescription extends DataTypeDescription {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.StructureDescription);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.StructureDescription_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.StructureDescription_Encoding_DefaultXml);
	
    protected StructureDefinition StructureDefinition;
    
    public StructureDescription() {}
    
    public StructureDescription(NodeId DataTypeId, QualifiedName Name, StructureDefinition StructureDefinition)
    {
        super(DataTypeId, Name);
        this.StructureDefinition = StructureDefinition;
    }
    
    public StructureDefinition getStructureDefinition()
    {
        return StructureDefinition;
    }
    
    public void setStructureDefinition(StructureDefinition StructureDefinition)
    {
        this.StructureDefinition = StructureDefinition;
    }
    
    /**
      * Deep clone
      *
      * @return cloned StructureDescription
      */
    public StructureDescription clone()
    {
        StructureDescription result = (StructureDescription) super.clone();
        result.DataTypeId = DataTypeId;
        result.Name = Name;
        result.StructureDefinition = StructureDefinition==null ? null : StructureDefinition.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StructureDescription other = (StructureDescription) obj;
        if (DataTypeId==null) {
            if (other.DataTypeId != null) return false;
        } else if (!DataTypeId.equals(other.DataTypeId)) return false;
        if (Name==null) {
            if (other.Name != null) return false;
        } else if (!Name.equals(other.Name)) return false;
        if (StructureDefinition==null) {
            if (other.StructureDefinition != null) return false;
        } else if (!StructureDefinition.equals(other.StructureDefinition)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((DataTypeId == null) ? 0 : DataTypeId.hashCode());
        result = prime * result
                + ((Name == null) ? 0 : Name.hashCode());
        result = prime * result
                + ((StructureDefinition == null) ? 0 : StructureDefinition.hashCode());
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
		return "StructureDescription: "+ObjectUtils.printFieldsDeep(this);
	}

}
