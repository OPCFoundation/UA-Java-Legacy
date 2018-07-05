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
import java.util.Arrays;
import org.opcfoundation.ua.core.EnumDescription;
import org.opcfoundation.ua.core.SimpleTypeDescription;
import org.opcfoundation.ua.core.StructureDescription;
import org.opcfoundation.ua.utils.AbstractStructure;



public class DataTypeSchemaHeader extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DataTypeSchemaHeader);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DataTypeSchemaHeader_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DataTypeSchemaHeader_Encoding_DefaultXml);
	
    protected String[] Namespaces;
    protected StructureDescription[] StructureDataTypes;
    protected EnumDescription[] EnumDataTypes;
    protected SimpleTypeDescription[] SimpleDataTypes;
    
    public DataTypeSchemaHeader() {}
    
    public DataTypeSchemaHeader(String[] Namespaces, StructureDescription[] StructureDataTypes, EnumDescription[] EnumDataTypes, SimpleTypeDescription[] SimpleDataTypes)
    {
        this.Namespaces = Namespaces;
        this.StructureDataTypes = StructureDataTypes;
        this.EnumDataTypes = EnumDataTypes;
        this.SimpleDataTypes = SimpleDataTypes;
    }
    
    public String[] getNamespaces()
    {
        return Namespaces;
    }
    
    public void setNamespaces(String[] Namespaces)
    {
        this.Namespaces = Namespaces;
    }
    
    public StructureDescription[] getStructureDataTypes()
    {
        return StructureDataTypes;
    }
    
    public void setStructureDataTypes(StructureDescription[] StructureDataTypes)
    {
        this.StructureDataTypes = StructureDataTypes;
    }
    
    public EnumDescription[] getEnumDataTypes()
    {
        return EnumDataTypes;
    }
    
    public void setEnumDataTypes(EnumDescription[] EnumDataTypes)
    {
        this.EnumDataTypes = EnumDataTypes;
    }
    
    public SimpleTypeDescription[] getSimpleDataTypes()
    {
        return SimpleDataTypes;
    }
    
    public void setSimpleDataTypes(SimpleTypeDescription[] SimpleDataTypes)
    {
        this.SimpleDataTypes = SimpleDataTypes;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DataTypeSchemaHeader
      */
    public DataTypeSchemaHeader clone()
    {
        DataTypeSchemaHeader result = (DataTypeSchemaHeader) super.clone();
        result.Namespaces = Namespaces==null ? null : Namespaces.clone();
        if (StructureDataTypes!=null) {
            result.StructureDataTypes = new StructureDescription[StructureDataTypes.length];
            for (int i=0; i<StructureDataTypes.length; i++)
                result.StructureDataTypes[i] = StructureDataTypes[i].clone();
        }
        if (EnumDataTypes!=null) {
            result.EnumDataTypes = new EnumDescription[EnumDataTypes.length];
            for (int i=0; i<EnumDataTypes.length; i++)
                result.EnumDataTypes[i] = EnumDataTypes[i].clone();
        }
        if (SimpleDataTypes!=null) {
            result.SimpleDataTypes = new SimpleTypeDescription[SimpleDataTypes.length];
            for (int i=0; i<SimpleDataTypes.length; i++)
                result.SimpleDataTypes[i] = SimpleDataTypes[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DataTypeSchemaHeader other = (DataTypeSchemaHeader) obj;
        if (Namespaces==null) {
            if (other.Namespaces != null) return false;
        } else if (!Arrays.equals(Namespaces, other.Namespaces)) return false;
        if (StructureDataTypes==null) {
            if (other.StructureDataTypes != null) return false;
        } else if (!Arrays.equals(StructureDataTypes, other.StructureDataTypes)) return false;
        if (EnumDataTypes==null) {
            if (other.EnumDataTypes != null) return false;
        } else if (!Arrays.equals(EnumDataTypes, other.EnumDataTypes)) return false;
        if (SimpleDataTypes==null) {
            if (other.SimpleDataTypes != null) return false;
        } else if (!Arrays.equals(SimpleDataTypes, other.SimpleDataTypes)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Namespaces == null) ? 0 : Arrays.hashCode(Namespaces));
        result = prime * result
                + ((StructureDataTypes == null) ? 0 : Arrays.hashCode(StructureDataTypes));
        result = prime * result
                + ((EnumDataTypes == null) ? 0 : Arrays.hashCode(EnumDataTypes));
        result = prime * result
                + ((SimpleDataTypes == null) ? 0 : Arrays.hashCode(SimpleDataTypes));
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
		return "DataTypeSchemaHeader: "+ObjectUtils.printFieldsDeep(this);
	}

}
