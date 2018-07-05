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
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.core.DataSetMetaDataType;
import org.opcfoundation.ua.core.KeyValuePair;
import org.opcfoundation.ua.utils.AbstractStructure;



public class PublishedDataSetDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.PublishedDataSetDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.PublishedDataSetDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.PublishedDataSetDataType_Encoding_DefaultXml);
	
    protected String Name;
    protected String[] DataSetFolder;
    protected DataSetMetaDataType DataSetMetaData;
    protected KeyValuePair[] ExtensionFields;
    protected ExtensionObject DataSetSource;
    
    public PublishedDataSetDataType() {}
    
    public PublishedDataSetDataType(String Name, String[] DataSetFolder, DataSetMetaDataType DataSetMetaData, KeyValuePair[] ExtensionFields, ExtensionObject DataSetSource)
    {
        this.Name = Name;
        this.DataSetFolder = DataSetFolder;
        this.DataSetMetaData = DataSetMetaData;
        this.ExtensionFields = ExtensionFields;
        this.DataSetSource = DataSetSource;
    }
    
    public String getName()
    {
        return Name;
    }
    
    public void setName(String Name)
    {
        this.Name = Name;
    }
    
    public String[] getDataSetFolder()
    {
        return DataSetFolder;
    }
    
    public void setDataSetFolder(String[] DataSetFolder)
    {
        this.DataSetFolder = DataSetFolder;
    }
    
    public DataSetMetaDataType getDataSetMetaData()
    {
        return DataSetMetaData;
    }
    
    public void setDataSetMetaData(DataSetMetaDataType DataSetMetaData)
    {
        this.DataSetMetaData = DataSetMetaData;
    }
    
    public KeyValuePair[] getExtensionFields()
    {
        return ExtensionFields;
    }
    
    public void setExtensionFields(KeyValuePair[] ExtensionFields)
    {
        this.ExtensionFields = ExtensionFields;
    }
    
    public ExtensionObject getDataSetSource()
    {
        return DataSetSource;
    }
    
    public void setDataSetSource(ExtensionObject DataSetSource)
    {
        this.DataSetSource = DataSetSource;
    }
    
    /**
      * Deep clone
      *
      * @return cloned PublishedDataSetDataType
      */
    public PublishedDataSetDataType clone()
    {
        PublishedDataSetDataType result = (PublishedDataSetDataType) super.clone();
        result.Name = Name;
        result.DataSetFolder = DataSetFolder==null ? null : DataSetFolder.clone();
        result.DataSetMetaData = DataSetMetaData==null ? null : DataSetMetaData.clone();
        if (ExtensionFields!=null) {
            result.ExtensionFields = new KeyValuePair[ExtensionFields.length];
            for (int i=0; i<ExtensionFields.length; i++)
                result.ExtensionFields[i] = ExtensionFields[i].clone();
        }
        result.DataSetSource = DataSetSource;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PublishedDataSetDataType other = (PublishedDataSetDataType) obj;
        if (Name==null) {
            if (other.Name != null) return false;
        } else if (!Name.equals(other.Name)) return false;
        if (DataSetFolder==null) {
            if (other.DataSetFolder != null) return false;
        } else if (!Arrays.equals(DataSetFolder, other.DataSetFolder)) return false;
        if (DataSetMetaData==null) {
            if (other.DataSetMetaData != null) return false;
        } else if (!DataSetMetaData.equals(other.DataSetMetaData)) return false;
        if (ExtensionFields==null) {
            if (other.ExtensionFields != null) return false;
        } else if (!Arrays.equals(ExtensionFields, other.ExtensionFields)) return false;
        if (DataSetSource==null) {
            if (other.DataSetSource != null) return false;
        } else if (!DataSetSource.equals(other.DataSetSource)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Name == null) ? 0 : Name.hashCode());
        result = prime * result
                + ((DataSetFolder == null) ? 0 : Arrays.hashCode(DataSetFolder));
        result = prime * result
                + ((DataSetMetaData == null) ? 0 : DataSetMetaData.hashCode());
        result = prime * result
                + ((ExtensionFields == null) ? 0 : Arrays.hashCode(ExtensionFields));
        result = prime * result
                + ((DataSetSource == null) ? 0 : DataSetSource.hashCode());
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
		return "PublishedDataSetDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
