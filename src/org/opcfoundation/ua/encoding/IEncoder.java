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

import java.util.Collection;
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
import org.opcfoundation.ua.encoding.binary.BinaryEncoder;
import org.opcfoundation.ua.encoding.xml.XmlEncoder;

/**
 * Abstract base implementation for encoders
 * 
 * @see XmlEncoder
 * @see BinaryEncoder
 * @see IDecoder
 */
public interface IEncoder {
	
	void putBoolean(String fieldName, Boolean v)
    throws EncodingException;	
	
	void putBooleanArray(String fieldName, Boolean[] v)
    throws EncodingException;	
	
	void putBooleanArray(String fieldName, Collection<Boolean> v)
    throws EncodingException;	
	
	void putSByte(String fieldName, Byte v)
    throws EncodingException;	
	
	void putSByte(String fieldName, byte v)
    throws EncodingException;	
	
	void putSByte(String fieldName, int v)
    throws EncodingException;
	
	void putSByteArray(String fieldName, Byte[] v)
    throws EncodingException;	
	
	void putSByteArray(String fieldName, Collection<Byte> v)
    throws EncodingException;	
	
	void putByte(String fieldName, UnsignedByte v)
    throws EncodingException;	
	
	void putByteArray(String fieldName, UnsignedByte[] v)
    throws EncodingException;	
	
	void putByteArray(String fieldName, Collection<UnsignedByte> v)
    throws EncodingException;	
	
	void putInt16(String fieldName, Short v)
    throws EncodingException;	
	
	void putInt16(String fieldName, short v)
    throws EncodingException;	
	
	void putInt16Array(String fieldName, Short[] v)
    throws EncodingException;	
	
	void putInt16Array(String fieldName, Collection<Short> v)
    throws EncodingException;	
	
	void putUInt16(String fieldName, UnsignedShort v)
    throws EncodingException;	
	
	void putUInt16Array(String fieldName, UnsignedShort[] v)
    throws EncodingException;	
	
	void putUInt16Array(String fieldName, Collection<UnsignedShort> v)
    throws EncodingException;	
	
	void putInt32(String fieldName, Integer v)
    throws EncodingException;	
	
	void putInt32(String fieldName, int v)
    throws EncodingException;	
	
	void putInt32Array(String fieldName, int[] v)
    throws EncodingException;	
	
	void putInt32Array(String fieldName, Collection<Integer> v)
    throws EncodingException;	
	
	void putInt32Array(String fieldName, Integer[] v)
    throws EncodingException;	

	void putUInt32(String fieldName, UnsignedInteger v)
    throws EncodingException;	
	
	void putUInt32Array(String fieldName, UnsignedInteger[] v)
    throws EncodingException;	
	
	void putUInt32Array(String fieldName, Collection<UnsignedInteger> v)
    throws EncodingException;	

	void putInt64(String fieldName, Long v)
    throws EncodingException;	
	
	void putInt64(String fieldName, long v)
    throws EncodingException;	
	
	void putInt64Array(String fieldName, Long[] v)
    throws EncodingException;	
	
	void putInt64Array(String fieldName, Collection<Long> v)
    throws EncodingException;	
	
	void putUInt64(String fieldName, UnsignedLong v)
    throws EncodingException;	
	
	void putUInt64Array(String fieldName, UnsignedLong[] v)
    throws EncodingException;	
	
	void putUInt64Array(String fieldName, Collection<UnsignedLong> v)
    throws EncodingException;	
	
	void putFloat(String fieldName, Float v)
    throws EncodingException;	
	
	void putFloat(String fieldName, float v)
    throws EncodingException;	
	
	void putFloatArray(String fieldName, Float[] v)
    throws EncodingException;	
	
	void putFloatArray(String fieldName, Collection<Float> v)
    throws EncodingException;	
	
	void putDouble(String fieldName, Double v)
    throws EncodingException;	
	
	void putDouble(String fieldName, double v)
    throws EncodingException;	
	
	void putDoubleArray(String fieldName, Double[] v)
    throws EncodingException;	
	
	void putDoubleArray(String fieldName, Collection<Double> v)
    throws EncodingException;	
	
	void putString(String fieldName, String v)
    throws EncodingException;	
	
	void putStringArray(String fieldName, Collection<String> v)
	throws EncodingException;
	
	void putStringArray(String fieldName, String[] v)
    throws EncodingException;	
	
	void putDateTime(String fieldName, DateTime v)
    throws EncodingException;	
	
	void putDateTimeArray(String fieldName, DateTime[] v)
    throws EncodingException;	
	
	void putDateTimeArray(String fieldName, Collection<DateTime> v)
    throws EncodingException;	
	
	void putGuid(String fieldName, UUID v)
    throws EncodingException;	
	
	void putGuidArray(String fieldName, UUID[] v)
    throws EncodingException;	
	
	void putGuidArray(String fieldName, Collection<UUID> v)
    throws EncodingException;	
	
	void putByteString(String fieldName, byte[] v)
    throws EncodingException;	
	
	void putByteStringArray(String fieldName, byte[][] v)
    throws EncodingException;	
	
	void putByteStringArray(String fieldName, Collection<byte[]> v)
    throws EncodingException;	
	
	void putXmlElement(String fieldName, XmlElement v)
    throws EncodingException;	
	
	void putXmlElementArray(String fieldName, XmlElement[] v)
    throws EncodingException;	
	
	void putXmlElementArray(String fieldName, Collection<XmlElement> v)
    throws EncodingException;	
	
	void putNodeId(String fieldName, NodeId v)
    throws EncodingException;	
	
	void putNodeIdArray(String fieldName, NodeId[] v)
    throws EncodingException;	
	
	void putNodeIdArray(String fieldName, Collection<NodeId> v)
    throws EncodingException;
	
	void putExpandedNodeId(String fieldName, ExpandedNodeId v)
    throws EncodingException;	
	
	void putExpandedNodeIdArray(String fieldName, ExpandedNodeId[] v)
    throws EncodingException;	
	
	void putExpandedNodeIdArray(String fieldName, Collection<ExpandedNodeId> v)
    throws EncodingException;	
	
	void putStatusCode(String fieldName, StatusCode v)
    throws EncodingException;	
	
	void putStatusCodeArray(String fieldName, StatusCode[] v)
    throws EncodingException;	
	
	void putStatusCodeArray(String fieldName, Collection<StatusCode> v)
    throws EncodingException;	
	
	void putQualifiedName(String fieldName, QualifiedName v)
    throws EncodingException;	
	
	void putQualifiedNameArray(String fieldName, QualifiedName[] v)
    throws EncodingException;	
	
	void putQualifiedNameArray(String fieldName, Collection<QualifiedName> v)
    throws EncodingException;	
	
	void putLocalizedText(String fieldName, LocalizedText v)
    throws EncodingException;	
	
	void putLocalizedTextArray(String fieldName, LocalizedText[] v)
    throws EncodingException;	
	
	void putLocalizedTextArray(String fieldName, Collection<LocalizedText> v)
    throws EncodingException;	
	
	void putStructure(String fieldName, Structure v)
    throws EncodingException;		

	void putStructureArray(String fieldName, Structure[] v)
    throws EncodingException;		

	void putStructureArray(String fieldName, Collection<Structure> v)
    throws EncodingException;		
	
	void putExtensionObject(String fieldName, ExtensionObject v)
    throws EncodingException;	
	
	void putExtensionObjectArray(String fieldName, ExtensionObject[] v)
    throws EncodingException;	
	
	void putExtensionObjectArray(String fieldName, Collection<ExtensionObject> v)
    throws EncodingException;	
	
	void putDataValue(String fieldName, DataValue v)
    throws EncodingException;	
	
	void putDataValueArray(String fieldName, DataValue[] v)
    throws EncodingException;	
	
	void putDataValueArray(String fieldName, Collection<DataValue> v)
    throws EncodingException;	
	
	void putVariant(String fieldName, Variant v)
    throws EncodingException;	
	
	void putVariantArray(String fieldName, Variant[] v)
    throws EncodingException;	
	
	void putVariantArray(String fieldName, Collection<Variant> v)
    throws EncodingException;	
	
	void putDiagnosticInfoArray(String fieldName, DiagnosticInfo[] v)
    throws EncodingException;
	
	void putDiagnosticInfoArray(String fieldName, Collection<DiagnosticInfo> v)
    throws EncodingException;
	
	void putDiagnosticInfo(String fieldName, DiagnosticInfo v)
    throws EncodingException;	
	
	void putEnumerationArray(String fieldName, Object array)
    throws EncodingException;	
	
	void putEnumeration(String fieldName, Enumeration v)
    throws EncodingException;	
	
	void putObject(String fieldName, Object o)
    throws EncodingException;	

	void putObject(String fieldName, Class<?> c, Object o)
    throws EncodingException;	
	
	void putScalar(String fieldName, int builtinType, Object o)
    throws EncodingException;	
	
	void putArray(String fieldName, int builtinType, Object o)
    throws EncodingException;	
	
	void putEncodeableArray(String fieldName, Class<? extends IEncodeable> clazz, Object array)
    throws EncodingException;	
	
	void putEncodeable(String fieldName, IEncodeable s)
    throws EncodingException;	
	
	void putEncodeable(String fieldName, Class<? extends IEncodeable> clazz, IEncodeable s)
    throws EncodingException;	
	
	void putMessage(IEncodeable s)
    throws EncodingException;

	void put(String fieldName, Object o)
	throws EncodingException;

	void put(String fieldName, Object o, Class<?> clazz)
	throws EncodingException;
}
