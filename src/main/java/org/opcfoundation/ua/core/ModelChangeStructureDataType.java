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
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.utils.AbstractStructure;



public class ModelChangeStructureDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ModelChangeStructureDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ModelChangeStructureDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ModelChangeStructureDataType_Encoding_DefaultXml);
	
    protected NodeId Affected;
    protected NodeId AffectedType;
    protected UnsignedByte Verb;
    
    public ModelChangeStructureDataType() {}
    
    public ModelChangeStructureDataType(NodeId Affected, NodeId AffectedType, UnsignedByte Verb)
    {
        this.Affected = Affected;
        this.AffectedType = AffectedType;
        this.Verb = Verb;
    }
    
    public NodeId getAffected()
    {
        return Affected;
    }
    
    public void setAffected(NodeId Affected)
    {
        this.Affected = Affected;
    }
    
    public NodeId getAffectedType()
    {
        return AffectedType;
    }
    
    public void setAffectedType(NodeId AffectedType)
    {
        this.AffectedType = AffectedType;
    }
    
    public UnsignedByte getVerb()
    {
        return Verb;
    }
    
    public void setVerb(UnsignedByte Verb)
    {
        this.Verb = Verb;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ModelChangeStructureDataType
      */
    public ModelChangeStructureDataType clone()
    {
        ModelChangeStructureDataType result = (ModelChangeStructureDataType) super.clone();
        result.Affected = Affected;
        result.AffectedType = AffectedType;
        result.Verb = Verb;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ModelChangeStructureDataType other = (ModelChangeStructureDataType) obj;
        if (Affected==null) {
            if (other.Affected != null) return false;
        } else if (!Affected.equals(other.Affected)) return false;
        if (AffectedType==null) {
            if (other.AffectedType != null) return false;
        } else if (!AffectedType.equals(other.AffectedType)) return false;
        if (Verb==null) {
            if (other.Verb != null) return false;
        } else if (!Verb.equals(other.Verb)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Affected == null) ? 0 : Affected.hashCode());
        result = prime * result
                + ((AffectedType == null) ? 0 : AffectedType.hashCode());
        result = prime * result
                + ((Verb == null) ? 0 : Verb.hashCode());
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
		return "ModelChangeStructureDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
