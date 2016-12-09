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
import java.util.UUID;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedLong;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.builtintypes.XmlElement;
import org.opcfoundation.ua.core.EnumeratedTestType;
import org.opcfoundation.ua.utils.AbstractStructure;



public class ScalarTestType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ScalarTestType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ScalarTestType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ScalarTestType_Encoding_DefaultXml);
	
    protected Boolean Boolean;
    protected Byte SByte;
    protected UnsignedByte Byte;
    protected Short Int16;
    protected UnsignedShort UInt16;
    protected Integer Int32;
    protected UnsignedInteger UInt32;
    protected Long Int64;
    protected UnsignedLong UInt64;
    protected Float Float;
    protected Double Double;
    protected String String;
    protected DateTime DateTime;
    protected UUID Guid;
    protected byte[] ByteString;
    protected XmlElement XmlElement;
    protected NodeId NodeId;
    protected ExpandedNodeId ExpandedNodeId;
    protected StatusCode StatusCode;
    protected DiagnosticInfo DiagnosticInfo;
    protected QualifiedName QualifiedName;
    protected LocalizedText LocalizedText;
    protected ExtensionObject ExtensionObject;
    protected DataValue DataValue;
    protected EnumeratedTestType EnumeratedValue;
    
    public ScalarTestType() {}
    
    public ScalarTestType(Boolean Boolean, Byte SByte, UnsignedByte Byte, Short Int16, UnsignedShort UInt16, Integer Int32, UnsignedInteger UInt32, Long Int64, UnsignedLong UInt64, Float Float, Double Double, String String, DateTime DateTime, UUID Guid, byte[] ByteString, XmlElement XmlElement, NodeId NodeId, ExpandedNodeId ExpandedNodeId, StatusCode StatusCode, DiagnosticInfo DiagnosticInfo, QualifiedName QualifiedName, LocalizedText LocalizedText, ExtensionObject ExtensionObject, DataValue DataValue, EnumeratedTestType EnumeratedValue)
    {
        this.Boolean = Boolean;
        this.SByte = SByte;
        this.Byte = Byte;
        this.Int16 = Int16;
        this.UInt16 = UInt16;
        this.Int32 = Int32;
        this.UInt32 = UInt32;
        this.Int64 = Int64;
        this.UInt64 = UInt64;
        this.Float = Float;
        this.Double = Double;
        this.String = String;
        this.DateTime = DateTime;
        this.Guid = Guid;
        this.ByteString = ByteString;
        this.XmlElement = XmlElement;
        this.NodeId = NodeId;
        this.ExpandedNodeId = ExpandedNodeId;
        this.StatusCode = StatusCode;
        this.DiagnosticInfo = DiagnosticInfo;
        this.QualifiedName = QualifiedName;
        this.LocalizedText = LocalizedText;
        this.ExtensionObject = ExtensionObject;
        this.DataValue = DataValue;
        this.EnumeratedValue = EnumeratedValue;
    }
    
    public Boolean getBoolean()
    {
        return Boolean;
    }
    
    public void setBoolean(Boolean Boolean)
    {
        this.Boolean = Boolean;
    }
    
    public Byte getSByte()
    {
        return SByte;
    }
    
    public void setSByte(Byte SByte)
    {
        this.SByte = SByte;
    }
    
    public UnsignedByte getByte()
    {
        return Byte;
    }
    
    public void setByte(UnsignedByte Byte)
    {
        this.Byte = Byte;
    }
    
    public Short getInt16()
    {
        return Int16;
    }
    
    public void setInt16(Short Int16)
    {
        this.Int16 = Int16;
    }
    
    public UnsignedShort getUInt16()
    {
        return UInt16;
    }
    
    public void setUInt16(UnsignedShort UInt16)
    {
        this.UInt16 = UInt16;
    }
    
    public Integer getInt32()
    {
        return Int32;
    }
    
    public void setInt32(Integer Int32)
    {
        this.Int32 = Int32;
    }
    
    public UnsignedInteger getUInt32()
    {
        return UInt32;
    }
    
    public void setUInt32(UnsignedInteger UInt32)
    {
        this.UInt32 = UInt32;
    }
    
    public Long getInt64()
    {
        return Int64;
    }
    
    public void setInt64(Long Int64)
    {
        this.Int64 = Int64;
    }
    
    public UnsignedLong getUInt64()
    {
        return UInt64;
    }
    
    public void setUInt64(UnsignedLong UInt64)
    {
        this.UInt64 = UInt64;
    }
    
    public Float getFloat()
    {
        return Float;
    }
    
    public void setFloat(Float Float)
    {
        this.Float = Float;
    }
    
    public Double getDouble()
    {
        return Double;
    }
    
    public void setDouble(Double Double)
    {
        this.Double = Double;
    }
    
    public String getString()
    {
        return String;
    }
    
    public void setString(String String)
    {
        this.String = String;
    }
    
    public DateTime getDateTime()
    {
        return DateTime;
    }
    
    public void setDateTime(DateTime DateTime)
    {
        this.DateTime = DateTime;
    }
    
    public UUID getGuid()
    {
        return Guid;
    }
    
    public void setGuid(UUID Guid)
    {
        this.Guid = Guid;
    }
    
    public byte[] getByteString()
    {
        return ByteString;
    }
    
    public void setByteString(byte[] ByteString)
    {
        this.ByteString = ByteString;
    }
    
    public XmlElement getXmlElement()
    {
        return XmlElement;
    }
    
    public void setXmlElement(XmlElement XmlElement)
    {
        this.XmlElement = XmlElement;
    }
    
    public NodeId getNodeId()
    {
        return NodeId;
    }
    
    public void setNodeId(NodeId NodeId)
    {
        this.NodeId = NodeId;
    }
    
    public ExpandedNodeId getExpandedNodeId()
    {
        return ExpandedNodeId;
    }
    
    public void setExpandedNodeId(ExpandedNodeId ExpandedNodeId)
    {
        this.ExpandedNodeId = ExpandedNodeId;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public DiagnosticInfo getDiagnosticInfo()
    {
        return DiagnosticInfo;
    }
    
    public void setDiagnosticInfo(DiagnosticInfo DiagnosticInfo)
    {
        this.DiagnosticInfo = DiagnosticInfo;
    }
    
    public QualifiedName getQualifiedName()
    {
        return QualifiedName;
    }
    
    public void setQualifiedName(QualifiedName QualifiedName)
    {
        this.QualifiedName = QualifiedName;
    }
    
    public LocalizedText getLocalizedText()
    {
        return LocalizedText;
    }
    
    public void setLocalizedText(LocalizedText LocalizedText)
    {
        this.LocalizedText = LocalizedText;
    }
    
    public ExtensionObject getExtensionObject()
    {
        return ExtensionObject;
    }
    
    public void setExtensionObject(ExtensionObject ExtensionObject)
    {
        this.ExtensionObject = ExtensionObject;
    }
    
    public DataValue getDataValue()
    {
        return DataValue;
    }
    
    public void setDataValue(DataValue DataValue)
    {
        this.DataValue = DataValue;
    }
    
    public EnumeratedTestType getEnumeratedValue()
    {
        return EnumeratedValue;
    }
    
    public void setEnumeratedValue(EnumeratedTestType EnumeratedValue)
    {
        this.EnumeratedValue = EnumeratedValue;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ScalarTestType
      */
    public ScalarTestType clone()
    {
        ScalarTestType result = (ScalarTestType) super.clone();
        result.Boolean = Boolean;
        result.SByte = SByte;
        result.Byte = Byte;
        result.Int16 = Int16;
        result.UInt16 = UInt16;
        result.Int32 = Int32;
        result.UInt32 = UInt32;
        result.Int64 = Int64;
        result.UInt64 = UInt64;
        result.Float = Float;
        result.Double = Double;
        result.String = String;
        result.DateTime = DateTime;
        result.Guid = Guid;
        result.ByteString = ByteString;
        result.XmlElement = XmlElement;
        result.NodeId = NodeId;
        result.ExpandedNodeId = ExpandedNodeId;
        result.StatusCode = StatusCode;
        result.DiagnosticInfo = DiagnosticInfo;
        result.QualifiedName = QualifiedName;
        result.LocalizedText = LocalizedText;
        result.ExtensionObject = ExtensionObject;
        result.DataValue = DataValue;
        result.EnumeratedValue = EnumeratedValue;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ScalarTestType other = (ScalarTestType) obj;
        if (Boolean==null) {
            if (other.Boolean != null) return false;
        } else if (!Boolean.equals(other.Boolean)) return false;
        if (SByte==null) {
            if (other.SByte != null) return false;
        } else if (!SByte.equals(other.SByte)) return false;
        if (Byte==null) {
            if (other.Byte != null) return false;
        } else if (!Byte.equals(other.Byte)) return false;
        if (Int16==null) {
            if (other.Int16 != null) return false;
        } else if (!Int16.equals(other.Int16)) return false;
        if (UInt16==null) {
            if (other.UInt16 != null) return false;
        } else if (!UInt16.equals(other.UInt16)) return false;
        if (Int32==null) {
            if (other.Int32 != null) return false;
        } else if (!Int32.equals(other.Int32)) return false;
        if (UInt32==null) {
            if (other.UInt32 != null) return false;
        } else if (!UInt32.equals(other.UInt32)) return false;
        if (Int64==null) {
            if (other.Int64 != null) return false;
        } else if (!Int64.equals(other.Int64)) return false;
        if (UInt64==null) {
            if (other.UInt64 != null) return false;
        } else if (!UInt64.equals(other.UInt64)) return false;
        if (Float==null) {
            if (other.Float != null) return false;
        } else if (!Float.equals(other.Float)) return false;
        if (Double==null) {
            if (other.Double != null) return false;
        } else if (!Double.equals(other.Double)) return false;
        if (String==null) {
            if (other.String != null) return false;
        } else if (!String.equals(other.String)) return false;
        if (DateTime==null) {
            if (other.DateTime != null) return false;
        } else if (!DateTime.equals(other.DateTime)) return false;
        if (Guid==null) {
            if (other.Guid != null) return false;
        } else if (!Guid.equals(other.Guid)) return false;
        if (ByteString==null) {
            if (other.ByteString != null) return false;
        } else if (!ByteString.equals(other.ByteString)) return false;
        if (XmlElement==null) {
            if (other.XmlElement != null) return false;
        } else if (!XmlElement.equals(other.XmlElement)) return false;
        if (NodeId==null) {
            if (other.NodeId != null) return false;
        } else if (!NodeId.equals(other.NodeId)) return false;
        if (ExpandedNodeId==null) {
            if (other.ExpandedNodeId != null) return false;
        } else if (!ExpandedNodeId.equals(other.ExpandedNodeId)) return false;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (DiagnosticInfo==null) {
            if (other.DiagnosticInfo != null) return false;
        } else if (!DiagnosticInfo.equals(other.DiagnosticInfo)) return false;
        if (QualifiedName==null) {
            if (other.QualifiedName != null) return false;
        } else if (!QualifiedName.equals(other.QualifiedName)) return false;
        if (LocalizedText==null) {
            if (other.LocalizedText != null) return false;
        } else if (!LocalizedText.equals(other.LocalizedText)) return false;
        if (ExtensionObject==null) {
            if (other.ExtensionObject != null) return false;
        } else if (!ExtensionObject.equals(other.ExtensionObject)) return false;
        if (DataValue==null) {
            if (other.DataValue != null) return false;
        } else if (!DataValue.equals(other.DataValue)) return false;
        if (EnumeratedValue==null) {
            if (other.EnumeratedValue != null) return false;
        } else if (!EnumeratedValue.equals(other.EnumeratedValue)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Boolean == null) ? 0 : Boolean.hashCode());
        result = prime * result
                + ((SByte == null) ? 0 : SByte.hashCode());
        result = prime * result
                + ((Byte == null) ? 0 : Byte.hashCode());
        result = prime * result
                + ((Int16 == null) ? 0 : Int16.hashCode());
        result = prime * result
                + ((UInt16 == null) ? 0 : UInt16.hashCode());
        result = prime * result
                + ((Int32 == null) ? 0 : Int32.hashCode());
        result = prime * result
                + ((UInt32 == null) ? 0 : UInt32.hashCode());
        result = prime * result
                + ((Int64 == null) ? 0 : Int64.hashCode());
        result = prime * result
                + ((UInt64 == null) ? 0 : UInt64.hashCode());
        result = prime * result
                + ((Float == null) ? 0 : Float.hashCode());
        result = prime * result
                + ((Double == null) ? 0 : Double.hashCode());
        result = prime * result
                + ((String == null) ? 0 : String.hashCode());
        result = prime * result
                + ((DateTime == null) ? 0 : DateTime.hashCode());
        result = prime * result
                + ((Guid == null) ? 0 : Guid.hashCode());
        result = prime * result
                + ((ByteString == null) ? 0 : ByteString.hashCode());
        result = prime * result
                + ((XmlElement == null) ? 0 : XmlElement.hashCode());
        result = prime * result
                + ((NodeId == null) ? 0 : NodeId.hashCode());
        result = prime * result
                + ((ExpandedNodeId == null) ? 0 : ExpandedNodeId.hashCode());
        result = prime * result
                + ((StatusCode == null) ? 0 : StatusCode.hashCode());
        result = prime * result
                + ((DiagnosticInfo == null) ? 0 : DiagnosticInfo.hashCode());
        result = prime * result
                + ((QualifiedName == null) ? 0 : QualifiedName.hashCode());
        result = prime * result
                + ((LocalizedText == null) ? 0 : LocalizedText.hashCode());
        result = prime * result
                + ((ExtensionObject == null) ? 0 : ExtensionObject.hashCode());
        result = prime * result
                + ((DataValue == null) ? 0 : DataValue.hashCode());
        result = prime * result
                + ((EnumeratedValue == null) ? 0 : EnumeratedValue.hashCode());
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
		return "ScalarTestType: "+ObjectUtils.printFieldsDeep(this);
	}

}
