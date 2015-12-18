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
import java.util.UUID;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedLong;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.builtintypes.XmlElement;
import org.opcfoundation.ua.core.EnumeratedTestType;



public class ArrayTestType extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ArrayTestType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ArrayTestType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ArrayTestType_Encoding_DefaultXml);
	
    protected Boolean[] Booleans;
    protected Byte[] SBytes;
    protected Short[] Int16s;
    protected UnsignedShort[] UInt16s;
    protected Integer[] Int32s;
    protected UnsignedInteger[] UInt32s;
    protected Long[] Int64s;
    protected UnsignedLong[] UInt64s;
    protected Float[] Floats;
    protected Double[] Doubles;
    protected String[] Strings;
    protected DateTime[] DateTimes;
    protected UUID[] Guids;
    protected byte[][] ByteStrings;
    protected XmlElement[] XmlElements;
    protected NodeId[] NodeIds;
    protected ExpandedNodeId[] ExpandedNodeIds;
    protected StatusCode[] StatusCodes;
    protected DiagnosticInfo[] DiagnosticInfos;
    protected QualifiedName[] QualifiedNames;
    protected LocalizedText[] LocalizedTexts;
    protected ExtensionObject[] ExtensionObjects;
    protected DataValue[] DataValues;
    protected Variant[] Variants;
    protected EnumeratedTestType[] EnumeratedValues;
    
    public ArrayTestType() {}
    
    public ArrayTestType(Boolean[] Booleans, Byte[] SBytes, Short[] Int16s, UnsignedShort[] UInt16s, Integer[] Int32s, UnsignedInteger[] UInt32s, Long[] Int64s, UnsignedLong[] UInt64s, Float[] Floats, Double[] Doubles, String[] Strings, DateTime[] DateTimes, UUID[] Guids, byte[][] ByteStrings, XmlElement[] XmlElements, NodeId[] NodeIds, ExpandedNodeId[] ExpandedNodeIds, StatusCode[] StatusCodes, DiagnosticInfo[] DiagnosticInfos, QualifiedName[] QualifiedNames, LocalizedText[] LocalizedTexts, ExtensionObject[] ExtensionObjects, DataValue[] DataValues, Variant[] Variants, EnumeratedTestType[] EnumeratedValues)
    {
        this.Booleans = Booleans;
        this.SBytes = SBytes;
        this.Int16s = Int16s;
        this.UInt16s = UInt16s;
        this.Int32s = Int32s;
        this.UInt32s = UInt32s;
        this.Int64s = Int64s;
        this.UInt64s = UInt64s;
        this.Floats = Floats;
        this.Doubles = Doubles;
        this.Strings = Strings;
        this.DateTimes = DateTimes;
        this.Guids = Guids;
        this.ByteStrings = ByteStrings;
        this.XmlElements = XmlElements;
        this.NodeIds = NodeIds;
        this.ExpandedNodeIds = ExpandedNodeIds;
        this.StatusCodes = StatusCodes;
        this.DiagnosticInfos = DiagnosticInfos;
        this.QualifiedNames = QualifiedNames;
        this.LocalizedTexts = LocalizedTexts;
        this.ExtensionObjects = ExtensionObjects;
        this.DataValues = DataValues;
        this.Variants = Variants;
        this.EnumeratedValues = EnumeratedValues;
    }
    
    public Boolean[] getBooleans()
    {
        return Booleans;
    }
    
    public void setBooleans(Boolean[] Booleans)
    {
        this.Booleans = Booleans;
    }
    
    public Byte[] getSBytes()
    {
        return SBytes;
    }
    
    public void setSBytes(Byte[] SBytes)
    {
        this.SBytes = SBytes;
    }
    
    public Short[] getInt16s()
    {
        return Int16s;
    }
    
    public void setInt16s(Short[] Int16s)
    {
        this.Int16s = Int16s;
    }
    
    public UnsignedShort[] getUInt16s()
    {
        return UInt16s;
    }
    
    public void setUInt16s(UnsignedShort[] UInt16s)
    {
        this.UInt16s = UInt16s;
    }
    
    public Integer[] getInt32s()
    {
        return Int32s;
    }
    
    public void setInt32s(Integer[] Int32s)
    {
        this.Int32s = Int32s;
    }
    
    public UnsignedInteger[] getUInt32s()
    {
        return UInt32s;
    }
    
    public void setUInt32s(UnsignedInteger[] UInt32s)
    {
        this.UInt32s = UInt32s;
    }
    
    public Long[] getInt64s()
    {
        return Int64s;
    }
    
    public void setInt64s(Long[] Int64s)
    {
        this.Int64s = Int64s;
    }
    
    public UnsignedLong[] getUInt64s()
    {
        return UInt64s;
    }
    
    public void setUInt64s(UnsignedLong[] UInt64s)
    {
        this.UInt64s = UInt64s;
    }
    
    public Float[] getFloats()
    {
        return Floats;
    }
    
    public void setFloats(Float[] Floats)
    {
        this.Floats = Floats;
    }
    
    public Double[] getDoubles()
    {
        return Doubles;
    }
    
    public void setDoubles(Double[] Doubles)
    {
        this.Doubles = Doubles;
    }
    
    public String[] getStrings()
    {
        return Strings;
    }
    
    public void setStrings(String[] Strings)
    {
        this.Strings = Strings;
    }
    
    public DateTime[] getDateTimes()
    {
        return DateTimes;
    }
    
    public void setDateTimes(DateTime[] DateTimes)
    {
        this.DateTimes = DateTimes;
    }
    
    public UUID[] getGuids()
    {
        return Guids;
    }
    
    public void setGuids(UUID[] Guids)
    {
        this.Guids = Guids;
    }
    
    public byte[][] getByteStrings()
    {
        return ByteStrings;
    }
    
    public void setByteStrings(byte[][] ByteStrings)
    {
        this.ByteStrings = ByteStrings;
    }
    
    public XmlElement[] getXmlElements()
    {
        return XmlElements;
    }
    
    public void setXmlElements(XmlElement[] XmlElements)
    {
        this.XmlElements = XmlElements;
    }
    
    public NodeId[] getNodeIds()
    {
        return NodeIds;
    }
    
    public void setNodeIds(NodeId[] NodeIds)
    {
        this.NodeIds = NodeIds;
    }
    
    public ExpandedNodeId[] getExpandedNodeIds()
    {
        return ExpandedNodeIds;
    }
    
    public void setExpandedNodeIds(ExpandedNodeId[] ExpandedNodeIds)
    {
        this.ExpandedNodeIds = ExpandedNodeIds;
    }
    
    public StatusCode[] getStatusCodes()
    {
        return StatusCodes;
    }
    
    public void setStatusCodes(StatusCode[] StatusCodes)
    {
        this.StatusCodes = StatusCodes;
    }
    
    public DiagnosticInfo[] getDiagnosticInfos()
    {
        return DiagnosticInfos;
    }
    
    public void setDiagnosticInfos(DiagnosticInfo[] DiagnosticInfos)
    {
        this.DiagnosticInfos = DiagnosticInfos;
    }
    
    public QualifiedName[] getQualifiedNames()
    {
        return QualifiedNames;
    }
    
    public void setQualifiedNames(QualifiedName[] QualifiedNames)
    {
        this.QualifiedNames = QualifiedNames;
    }
    
    public LocalizedText[] getLocalizedTexts()
    {
        return LocalizedTexts;
    }
    
    public void setLocalizedTexts(LocalizedText[] LocalizedTexts)
    {
        this.LocalizedTexts = LocalizedTexts;
    }
    
    public ExtensionObject[] getExtensionObjects()
    {
        return ExtensionObjects;
    }
    
    public void setExtensionObjects(ExtensionObject[] ExtensionObjects)
    {
        this.ExtensionObjects = ExtensionObjects;
    }
    
    public DataValue[] getDataValues()
    {
        return DataValues;
    }
    
    public void setDataValues(DataValue[] DataValues)
    {
        this.DataValues = DataValues;
    }
    
    public Variant[] getVariants()
    {
        return Variants;
    }
    
    public void setVariants(Variant[] Variants)
    {
        this.Variants = Variants;
    }
    
    public EnumeratedTestType[] getEnumeratedValues()
    {
        return EnumeratedValues;
    }
    
    public void setEnumeratedValues(EnumeratedTestType[] EnumeratedValues)
    {
        this.EnumeratedValues = EnumeratedValues;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ArrayTestType
      */
    public ArrayTestType clone()
    {
        ArrayTestType result = new ArrayTestType();
        result.Booleans = Booleans==null ? null : Booleans.clone();
        result.SBytes = SBytes==null ? null : SBytes.clone();
        result.Int16s = Int16s==null ? null : Int16s.clone();
        result.UInt16s = UInt16s==null ? null : UInt16s.clone();
        result.Int32s = Int32s==null ? null : Int32s.clone();
        result.UInt32s = UInt32s==null ? null : UInt32s.clone();
        result.Int64s = Int64s==null ? null : Int64s.clone();
        result.UInt64s = UInt64s==null ? null : UInt64s.clone();
        result.Floats = Floats==null ? null : Floats.clone();
        result.Doubles = Doubles==null ? null : Doubles.clone();
        result.Strings = Strings==null ? null : Strings.clone();
        result.DateTimes = DateTimes==null ? null : DateTimes.clone();
        result.Guids = Guids==null ? null : Guids.clone();
        result.ByteStrings = ByteStrings==null ? null : ByteStrings.clone();
        result.XmlElements = XmlElements==null ? null : XmlElements.clone();
        result.NodeIds = NodeIds==null ? null : NodeIds.clone();
        result.ExpandedNodeIds = ExpandedNodeIds==null ? null : ExpandedNodeIds.clone();
        result.StatusCodes = StatusCodes==null ? null : StatusCodes.clone();
        result.DiagnosticInfos = DiagnosticInfos==null ? null : DiagnosticInfos.clone();
        result.QualifiedNames = QualifiedNames==null ? null : QualifiedNames.clone();
        result.LocalizedTexts = LocalizedTexts==null ? null : LocalizedTexts.clone();
        result.ExtensionObjects = ExtensionObjects==null ? null : ExtensionObjects.clone();
        result.DataValues = DataValues==null ? null : DataValues.clone();
        result.Variants = Variants==null ? null : Variants.clone();
        result.EnumeratedValues = EnumeratedValues==null ? null : EnumeratedValues.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ArrayTestType other = (ArrayTestType) obj;
        if (Booleans==null) {
            if (other.Booleans != null) return false;
        } else if (!Arrays.equals(Booleans, other.Booleans)) return false;
        if (SBytes==null) {
            if (other.SBytes != null) return false;
        } else if (!Arrays.equals(SBytes, other.SBytes)) return false;
        if (Int16s==null) {
            if (other.Int16s != null) return false;
        } else if (!Arrays.equals(Int16s, other.Int16s)) return false;
        if (UInt16s==null) {
            if (other.UInt16s != null) return false;
        } else if (!Arrays.equals(UInt16s, other.UInt16s)) return false;
        if (Int32s==null) {
            if (other.Int32s != null) return false;
        } else if (!Arrays.equals(Int32s, other.Int32s)) return false;
        if (UInt32s==null) {
            if (other.UInt32s != null) return false;
        } else if (!Arrays.equals(UInt32s, other.UInt32s)) return false;
        if (Int64s==null) {
            if (other.Int64s != null) return false;
        } else if (!Arrays.equals(Int64s, other.Int64s)) return false;
        if (UInt64s==null) {
            if (other.UInt64s != null) return false;
        } else if (!Arrays.equals(UInt64s, other.UInt64s)) return false;
        if (Floats==null) {
            if (other.Floats != null) return false;
        } else if (!Arrays.equals(Floats, other.Floats)) return false;
        if (Doubles==null) {
            if (other.Doubles != null) return false;
        } else if (!Arrays.equals(Doubles, other.Doubles)) return false;
        if (Strings==null) {
            if (other.Strings != null) return false;
        } else if (!Arrays.equals(Strings, other.Strings)) return false;
        if (DateTimes==null) {
            if (other.DateTimes != null) return false;
        } else if (!Arrays.equals(DateTimes, other.DateTimes)) return false;
        if (Guids==null) {
            if (other.Guids != null) return false;
        } else if (!Arrays.equals(Guids, other.Guids)) return false;
        if (ByteStrings==null) {
            if (other.ByteStrings != null) return false;
        } else if (!Arrays.equals(ByteStrings, other.ByteStrings)) return false;
        if (XmlElements==null) {
            if (other.XmlElements != null) return false;
        } else if (!Arrays.equals(XmlElements, other.XmlElements)) return false;
        if (NodeIds==null) {
            if (other.NodeIds != null) return false;
        } else if (!Arrays.equals(NodeIds, other.NodeIds)) return false;
        if (ExpandedNodeIds==null) {
            if (other.ExpandedNodeIds != null) return false;
        } else if (!Arrays.equals(ExpandedNodeIds, other.ExpandedNodeIds)) return false;
        if (StatusCodes==null) {
            if (other.StatusCodes != null) return false;
        } else if (!Arrays.equals(StatusCodes, other.StatusCodes)) return false;
        if (DiagnosticInfos==null) {
            if (other.DiagnosticInfos != null) return false;
        } else if (!Arrays.equals(DiagnosticInfos, other.DiagnosticInfos)) return false;
        if (QualifiedNames==null) {
            if (other.QualifiedNames != null) return false;
        } else if (!Arrays.equals(QualifiedNames, other.QualifiedNames)) return false;
        if (LocalizedTexts==null) {
            if (other.LocalizedTexts != null) return false;
        } else if (!Arrays.equals(LocalizedTexts, other.LocalizedTexts)) return false;
        if (ExtensionObjects==null) {
            if (other.ExtensionObjects != null) return false;
        } else if (!Arrays.equals(ExtensionObjects, other.ExtensionObjects)) return false;
        if (DataValues==null) {
            if (other.DataValues != null) return false;
        } else if (!Arrays.equals(DataValues, other.DataValues)) return false;
        if (Variants==null) {
            if (other.Variants != null) return false;
        } else if (!Arrays.equals(Variants, other.Variants)) return false;
        if (EnumeratedValues==null) {
            if (other.EnumeratedValues != null) return false;
        } else if (!Arrays.equals(EnumeratedValues, other.EnumeratedValues)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Booleans == null) ? 0 : Arrays.hashCode(Booleans));
        result = prime * result
                + ((SBytes == null) ? 0 : Arrays.hashCode(SBytes));
        result = prime * result
                + ((Int16s == null) ? 0 : Arrays.hashCode(Int16s));
        result = prime * result
                + ((UInt16s == null) ? 0 : Arrays.hashCode(UInt16s));
        result = prime * result
                + ((Int32s == null) ? 0 : Arrays.hashCode(Int32s));
        result = prime * result
                + ((UInt32s == null) ? 0 : Arrays.hashCode(UInt32s));
        result = prime * result
                + ((Int64s == null) ? 0 : Arrays.hashCode(Int64s));
        result = prime * result
                + ((UInt64s == null) ? 0 : Arrays.hashCode(UInt64s));
        result = prime * result
                + ((Floats == null) ? 0 : Arrays.hashCode(Floats));
        result = prime * result
                + ((Doubles == null) ? 0 : Arrays.hashCode(Doubles));
        result = prime * result
                + ((Strings == null) ? 0 : Arrays.hashCode(Strings));
        result = prime * result
                + ((DateTimes == null) ? 0 : Arrays.hashCode(DateTimes));
        result = prime * result
                + ((Guids == null) ? 0 : Arrays.hashCode(Guids));
        result = prime * result
                + ((ByteStrings == null) ? 0 : Arrays.hashCode(ByteStrings));
        result = prime * result
                + ((XmlElements == null) ? 0 : Arrays.hashCode(XmlElements));
        result = prime * result
                + ((NodeIds == null) ? 0 : Arrays.hashCode(NodeIds));
        result = prime * result
                + ((ExpandedNodeIds == null) ? 0 : Arrays.hashCode(ExpandedNodeIds));
        result = prime * result
                + ((StatusCodes == null) ? 0 : Arrays.hashCode(StatusCodes));
        result = prime * result
                + ((DiagnosticInfos == null) ? 0 : Arrays.hashCode(DiagnosticInfos));
        result = prime * result
                + ((QualifiedNames == null) ? 0 : Arrays.hashCode(QualifiedNames));
        result = prime * result
                + ((LocalizedTexts == null) ? 0 : Arrays.hashCode(LocalizedTexts));
        result = prime * result
                + ((ExtensionObjects == null) ? 0 : Arrays.hashCode(ExtensionObjects));
        result = prime * result
                + ((DataValues == null) ? 0 : Arrays.hashCode(DataValues));
        result = prime * result
                + ((Variants == null) ? 0 : Arrays.hashCode(Variants));
        result = prime * result
                + ((EnumeratedValues == null) ? 0 : Arrays.hashCode(EnumeratedValues));
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
		return "ArrayTestType: "+ObjectUtils.printFieldsDeep(this);
	}

}
