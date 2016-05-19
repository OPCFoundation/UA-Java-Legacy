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

package org.opcfoundation.ua.encoding;

import java.util.UUID;

import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.Enumeration;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedLong;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.builtintypes.XmlElement;
import org.opcfoundation.ua.encoding.binary.BinaryDecoder;
import org.opcfoundation.ua.encoding.xml.XmlDecoder;


/**
 *
 * 
 * @see IEncoder
 * @see BinaryDecoder
 * @see XmlDecoder
 */
public interface IDecoder {
	
	public Boolean getBoolean(String fieldName)
    throws DecodingException;	
	
	public Boolean[] getBooleanArray(String fieldName)
    throws DecodingException;	
	
	public Byte getSByte(String fieldName)
    throws DecodingException;	
	
	public Byte[] getSByteArray(String fieldName)
    throws DecodingException;	
	
	public UnsignedByte getByte(String fieldName)
    throws DecodingException;	
	
	public UnsignedByte[] getByteArray(String fieldName)
    throws DecodingException;	
	
	public Short getInt16(String fieldName)
    throws DecodingException;	
	
	public Short[] getInt16Array(String fieldName)
    throws DecodingException;	
	
	public UnsignedShort getUInt16(String fieldName)
    throws DecodingException;	
	
	public UnsignedShort[] getUInt16Array(String fieldName)
    throws DecodingException;	
	
	public Integer getInt32(String fieldName)
    throws DecodingException;	
	
	public Integer[] getInt32Array(String fieldName)
    throws DecodingException;	
	
	public int[] getInt32Array_(String fieldName)
    throws DecodingException;	
	
	public UnsignedInteger getUInt32(String fieldName)
    throws DecodingException;	
	
	public UnsignedInteger[] getUInt32Array(String fieldName)
    throws DecodingException;	
	
	public Long getInt64(String fieldName)
    throws DecodingException;	
	
	public Long[] getInt64Array(String fieldName)
    throws DecodingException;	
	
	public UnsignedLong getUInt64(String fieldName)
    throws DecodingException;	
	
	public UnsignedLong[] getUInt64Array(String fieldName)
    throws DecodingException;	
	
	public Float getFloat(String fieldName)
    throws DecodingException;	
	
	public Float[] getFloatArray(String fieldName)
    throws DecodingException;	
	
	public Double getDouble(String fieldName)
    throws DecodingException;	
	
	public Double[] getDoubleArray(String fieldName)
    throws DecodingException;	
	
	public String getString(String fieldName)
    throws DecodingException;	
	
	public String[] getStringArray(String fieldName)
    throws DecodingException;	
	
	public DateTime getDateTime(String fieldName)
    throws DecodingException;	
	
	public DateTime[] getDateTimeArray(String fieldName)
    throws DecodingException;	
	
	public UUID getGuid(String fieldName)
    throws DecodingException;	
	
	public UUID[] getGuidArray(String fieldName)
    throws DecodingException;	
	
	public byte[] getByteString(String fieldName)
    throws DecodingException;	
	
	public byte[][] getByteStringArray(String fieldName)
    throws DecodingException;	
	
	public XmlElement getXmlElement(String fieldName)
    throws DecodingException;	
	
	public XmlElement[] getXmlElementArray(String fieldName)
    throws DecodingException;	
	
	public NodeId getNodeId(String fieldName)
    throws DecodingException;	
	
	public NodeId[] getNodeIdArray(String fieldName)
    throws DecodingException;	
	
	public ExpandedNodeId getExpandedNodeId(String fieldName)
    throws DecodingException;	
	
	public ExpandedNodeId[] getExpandedNodeIdArray(String fieldName)
    throws DecodingException;	
	
	public StatusCode getStatusCode(String fieldName)
    throws DecodingException;	
	
	public StatusCode[] getStatusCodeArray(String fieldName)
    throws DecodingException;	
	
	public QualifiedName getQualifiedName(String fieldName)
    throws DecodingException;	
	
	public QualifiedName[] getQualifiedNameArray(String fieldName)
    throws DecodingException;	
	
	public LocalizedText getLocalizedText(String fieldName)
    throws DecodingException;	
	
	public LocalizedText[] getLocalizedTextArray(String fieldName)
    throws DecodingException;	
	
	public Structure getStructure(String fieldName)
    throws DecodingException;	
	
	public Structure[] getStructureArray(String fieldName)
    throws DecodingException;	
	
	public ExtensionObject getExtensionObject(String fieldName)
    throws DecodingException;	
	
	public ExtensionObject[] getExtensionObjectArray(String fieldName)
    throws DecodingException;	
	
	public DataValue getDataValue(String fieldName)
    throws DecodingException;	
	
	public DataValue[] getDataValueArray(String fieldName)
    throws DecodingException;	
	
	public Variant getVariant(String fieldName)
    throws DecodingException;	
	
	public Variant[] getVariantArray(String fieldName)
    throws DecodingException;	
	
	public DiagnosticInfo getDiagnosticInfo(String fieldName)
    throws DecodingException;
	
	public DiagnosticInfo[] getDiagnosticInfoArray(String fieldName)
    throws DecodingException;	
	
	public <T extends Enumeration> T[] getEnumerationArray(String fieldName, Class<T> enumerationClass)
    throws DecodingException;	
	
	public <T extends Enumeration> T getEnumeration(String fieldName, Class<T> enumerationClass)
    throws DecodingException;	
	
	public <T extends IEncodeable> T[] getEncodeableArray(String fieldName, Class<? extends T> encodeableClass)
    throws DecodingException;	
	
	public <T extends IEncodeable> T getEncodeable(String fieldName, Class<? extends T> encodeableClass)
    throws DecodingException;

	public <T> T get(String fieldName, Class<T> clazz)
	throws DecodingException;
	
	public <T extends IEncodeable> T getMessage()
    throws DecodingException;	
	
	public Object getScalarObject(String fieldName, int builtinTypeId)
    throws DecodingException;	
	
	public Object getArrayObject(String fieldName, int builtinTypeId)
    throws DecodingException;	
	
}
