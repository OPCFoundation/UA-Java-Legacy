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
import java.util.UUID;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.core.ConfigurationVersionDataType;
import org.opcfoundation.ua.core.DataTypeSchemaHeader;
import org.opcfoundation.ua.core.EnumDescription;
import org.opcfoundation.ua.core.FieldMetaData;
import org.opcfoundation.ua.core.SimpleTypeDescription;
import org.opcfoundation.ua.core.StructureDescription;



public class DataSetMetaDataType extends DataTypeSchemaHeader {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.DataSetMetaDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.DataSetMetaDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.DataSetMetaDataType_Encoding_DefaultXml);
	
    protected String Name;
    protected LocalizedText Description;
    protected FieldMetaData[] Fields;
    protected UUID DataSetClassId;
    protected ConfigurationVersionDataType ConfigurationVersion;
    
    public DataSetMetaDataType() {}
    
    public DataSetMetaDataType(String[] Namespaces, StructureDescription[] StructureDataTypes, EnumDescription[] EnumDataTypes, SimpleTypeDescription[] SimpleDataTypes, String Name, LocalizedText Description, FieldMetaData[] Fields, UUID DataSetClassId, ConfigurationVersionDataType ConfigurationVersion)
    {
        super(Namespaces, StructureDataTypes, EnumDataTypes, SimpleDataTypes);
        this.Name = Name;
        this.Description = Description;
        this.Fields = Fields;
        this.DataSetClassId = DataSetClassId;
        this.ConfigurationVersion = ConfigurationVersion;
    }
    
    public String getName()
    {
        return Name;
    }
    
    public void setName(String Name)
    {
        this.Name = Name;
    }
    
    public LocalizedText getDescription()
    {
        return Description;
    }
    
    public void setDescription(LocalizedText Description)
    {
        this.Description = Description;
    }
    
    public FieldMetaData[] getFields()
    {
        return Fields;
    }
    
    public void setFields(FieldMetaData[] Fields)
    {
        this.Fields = Fields;
    }
    
    public UUID getDataSetClassId()
    {
        return DataSetClassId;
    }
    
    public void setDataSetClassId(UUID DataSetClassId)
    {
        this.DataSetClassId = DataSetClassId;
    }
    
    public ConfigurationVersionDataType getConfigurationVersion()
    {
        return ConfigurationVersion;
    }
    
    public void setConfigurationVersion(ConfigurationVersionDataType ConfigurationVersion)
    {
        this.ConfigurationVersion = ConfigurationVersion;
    }
    
    /**
      * Deep clone
      *
      * @return cloned DataSetMetaDataType
      */
    public DataSetMetaDataType clone()
    {
        DataSetMetaDataType result = (DataSetMetaDataType) super.clone();
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
        result.Name = Name;
        result.Description = Description;
        if (Fields!=null) {
            result.Fields = new FieldMetaData[Fields.length];
            for (int i=0; i<Fields.length; i++)
                result.Fields[i] = Fields[i].clone();
        }
        result.DataSetClassId = DataSetClassId;
        result.ConfigurationVersion = ConfigurationVersion==null ? null : ConfigurationVersion.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DataSetMetaDataType other = (DataSetMetaDataType) obj;
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
        if (Name==null) {
            if (other.Name != null) return false;
        } else if (!Name.equals(other.Name)) return false;
        if (Description==null) {
            if (other.Description != null) return false;
        } else if (!Description.equals(other.Description)) return false;
        if (Fields==null) {
            if (other.Fields != null) return false;
        } else if (!Arrays.equals(Fields, other.Fields)) return false;
        if (DataSetClassId==null) {
            if (other.DataSetClassId != null) return false;
        } else if (!DataSetClassId.equals(other.DataSetClassId)) return false;
        if (ConfigurationVersion==null) {
            if (other.ConfigurationVersion != null) return false;
        } else if (!ConfigurationVersion.equals(other.ConfigurationVersion)) return false;
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
        result = prime * result
                + ((Name == null) ? 0 : Name.hashCode());
        result = prime * result
                + ((Description == null) ? 0 : Description.hashCode());
        result = prime * result
                + ((Fields == null) ? 0 : Arrays.hashCode(Fields));
        result = prime * result
                + ((DataSetClassId == null) ? 0 : DataSetClassId.hashCode());
        result = prime * result
                + ((ConfigurationVersion == null) ? 0 : ConfigurationVersion.hashCode());
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
		return "DataSetMetaDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
