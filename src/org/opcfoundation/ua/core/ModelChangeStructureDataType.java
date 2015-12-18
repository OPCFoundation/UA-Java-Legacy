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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedByte;



public class ModelChangeStructureDataType extends Object implements Structure, Cloneable {
	
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
        ModelChangeStructureDataType result = new ModelChangeStructureDataType();
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
