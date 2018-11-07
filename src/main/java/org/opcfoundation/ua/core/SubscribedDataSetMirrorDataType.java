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

import java.util.Arrays;
import org.opcfoundation.ua.core.RolePermissionType;
import org.opcfoundation.ua.core.SubscribedDataSetDataType;



public class SubscribedDataSetMirrorDataType extends SubscribedDataSetDataType {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.SubscribedDataSetMirrorDataType.getValue());
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.SubscribedDataSetMirrorDataType_Encoding_DefaultBinary.getValue());
	public static final ExpandedNodeId XML = new ExpandedNodeId(null, NamespaceTable.OPCUA_NAMESPACE, Identifiers.SubscribedDataSetMirrorDataType_Encoding_DefaultXml.getValue());
	
    protected String ParentNodeName;
    protected RolePermissionType[] RolePermissions;
    
    public SubscribedDataSetMirrorDataType() {}
    
    public SubscribedDataSetMirrorDataType(String ParentNodeName, RolePermissionType[] RolePermissions)
    {
        this.ParentNodeName = ParentNodeName;
        this.RolePermissions = RolePermissions;
    }
    
    public String getParentNodeName()
    {
        return ParentNodeName;
    }
    
    public void setParentNodeName(String ParentNodeName)
    {
        this.ParentNodeName = ParentNodeName;
    }
    
    public RolePermissionType[] getRolePermissions()
    {
        return RolePermissions;
    }
    
    public void setRolePermissions(RolePermissionType[] RolePermissions)
    {
        this.RolePermissions = RolePermissions;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SubscribedDataSetMirrorDataType
      */
    public SubscribedDataSetMirrorDataType clone()
    {
        SubscribedDataSetMirrorDataType result = (SubscribedDataSetMirrorDataType) super.clone();
        result.ParentNodeName = ParentNodeName;
        if (RolePermissions!=null) {
            result.RolePermissions = new RolePermissionType[RolePermissions.length];
            for (int i=0; i<RolePermissions.length; i++)
                result.RolePermissions[i] = RolePermissions[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SubscribedDataSetMirrorDataType other = (SubscribedDataSetMirrorDataType) obj;
        if (ParentNodeName==null) {
            if (other.ParentNodeName != null) return false;
        } else if (!ParentNodeName.equals(other.ParentNodeName)) return false;
        if (RolePermissions==null) {
            if (other.RolePermissions != null) return false;
        } else if (!Arrays.equals(RolePermissions, other.RolePermissions)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ParentNodeName == null) ? 0 : ParentNodeName.hashCode());
        result = prime * result
                + ((RolePermissions == null) ? 0 : Arrays.hashCode(RolePermissions));
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
		return "SubscribedDataSetMirrorDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
