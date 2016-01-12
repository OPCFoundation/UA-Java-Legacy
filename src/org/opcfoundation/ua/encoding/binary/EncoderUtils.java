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
package org.opcfoundation.ua.encoding.binary;

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
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IEncoder;

public class EncoderUtils {
	public static void put(IEncoder binaryEncoder, String fieldName, Object o) throws EncodingException {
		if (o instanceof Boolean) {
			binaryEncoder.putBoolean(fieldName, (Boolean) o);
		}
		else if (o instanceof Byte) {
			binaryEncoder.putSByte(fieldName, (Byte) o);
		}
		else if (o instanceof UnsignedByte) {
			binaryEncoder.putByte(fieldName, (UnsignedByte) o);
		}
		else if (o instanceof Short) {
			binaryEncoder.putInt16(fieldName, (Short) o);
		}
		else if (o instanceof UnsignedShort) {
			binaryEncoder.putUInt16(fieldName, (UnsignedShort) o);
		}
		else if (o instanceof Integer) {
			binaryEncoder.putInt32(fieldName, (Integer) o);
		}
		else if (o instanceof UnsignedInteger) {
			binaryEncoder.putUInt32(fieldName, (UnsignedInteger) o);
		}
		else if (o instanceof Long) {
			binaryEncoder.putInt64(fieldName, (Long) o);
		}
		else if (o instanceof UnsignedLong) {
			binaryEncoder.putUInt64(fieldName, (UnsignedLong) o);
		}
		else if (o instanceof Float) {
			binaryEncoder.putFloat(fieldName, (Float) o);
		}
		else if (o instanceof Double) {
			binaryEncoder.putDouble(fieldName, (Double) o);
		}
		else if (o instanceof String) {
			binaryEncoder.putString(fieldName, (String) o);
		}
		else if (o instanceof DateTime) {
			binaryEncoder.putDateTime(fieldName, (DateTime) o);
		}
		else if (o instanceof UUID) {
			binaryEncoder.putGuid(fieldName, (UUID) o);
		}
		else if (o instanceof byte[]) {
			binaryEncoder.putByteString(fieldName, (byte[]) o);
		}
		else if (o instanceof XmlElement) {
			binaryEncoder.putXmlElement(fieldName, (XmlElement) o);
		}
		else if (o instanceof NodeId) {
			binaryEncoder.putNodeId(fieldName, (NodeId) o);
		}
		else if (o instanceof ExpandedNodeId) {
			binaryEncoder.putExpandedNodeId(fieldName, (ExpandedNodeId) o);
		}
		else if (o instanceof StatusCode) {
			binaryEncoder.putStatusCode(fieldName, (StatusCode) o);
		}
		else if (o instanceof QualifiedName) {
			binaryEncoder.putQualifiedName(fieldName, (QualifiedName) o);
		}
		else if (o instanceof LocalizedText) {
			binaryEncoder.putLocalizedText(fieldName, (LocalizedText) o);
		}
		else if (o instanceof ExtensionObject) {
			binaryEncoder.putExtensionObject(fieldName, (ExtensionObject) o);
		}
		else if (o instanceof Structure) {
			binaryEncoder.putEncodeable(fieldName, (Structure) o);
		}
		else if (o instanceof DataValue) {
			binaryEncoder.putDataValue(fieldName, (DataValue) o);
		}
		else if (o instanceof Variant) {
			binaryEncoder.putVariant(fieldName, (Variant) o);
		}
		else if (o instanceof DiagnosticInfo) {
			binaryEncoder.putDiagnosticInfo(fieldName, (DiagnosticInfo) o);
		}
		else if (o instanceof Boolean[]) {
			binaryEncoder.putBooleanArray(fieldName, (Boolean[]) o);
		}
		else if (o instanceof Byte[]) {
			binaryEncoder.putSByteArray(fieldName, (Byte[]) o);
		}
		else if (o instanceof UnsignedByte[]) {
			binaryEncoder.putByteArray(fieldName, (UnsignedByte[]) o);
		}
		else if (o instanceof Short[]) {
			binaryEncoder.putInt16Array(fieldName, (Short[]) o);
		}
		else if (o instanceof UnsignedShort[]) {
			binaryEncoder.putUInt16Array(fieldName, (UnsignedShort[]) o);
		}
		else if (o instanceof Integer[]) {
			binaryEncoder.putInt32Array(fieldName, (Integer[]) o);
		}
		else if (o instanceof UnsignedInteger[]) {
			binaryEncoder.putUInt32Array(fieldName, (UnsignedInteger[]) o);
		}
		else if (o instanceof Long[]) {
			binaryEncoder.putInt64Array(fieldName, (Long[]) o);
		}
		else if (o instanceof UnsignedLong[]) {
			binaryEncoder.putUInt64Array(fieldName, (UnsignedLong[]) o);
		}
		else if (o instanceof Float[]) {
			binaryEncoder.putFloatArray(fieldName, (Float[]) o);
		}
		else if (o instanceof Double[]) {
			binaryEncoder.putDoubleArray(fieldName, (Double[]) o);
		}
		else if (o instanceof String[]) {
			binaryEncoder.putStringArray(fieldName, (String[]) o);
		}
		else if (o instanceof DateTime[]) {
			binaryEncoder.putDateTimeArray(fieldName, (DateTime[]) o);
		}
		else if (o instanceof UUID[]) {
			binaryEncoder.putGuidArray(fieldName, (UUID[]) o);
		}
		else if (o instanceof byte[][]) {
			binaryEncoder.putByteStringArray(fieldName, (byte[][]) o);
		}
		else if (o instanceof XmlElement[]) {
			binaryEncoder.putXmlElementArray(fieldName, (XmlElement[]) o);
		}
		else if (o instanceof NodeId[]) {
			binaryEncoder.putNodeIdArray(fieldName, (NodeId[]) o);
		}
		else if (o instanceof ExpandedNodeId[]) {
			binaryEncoder.putExpandedNodeIdArray(fieldName, (ExpandedNodeId[]) o);
		}
		else if (o instanceof StatusCode[]) {
			binaryEncoder.putStatusCodeArray(fieldName, (StatusCode[]) o);
		}
		else if (o instanceof QualifiedName[]) {
			binaryEncoder.putQualifiedNameArray(fieldName, (QualifiedName[]) o);
		}
		else if (o instanceof LocalizedText[]) {
			binaryEncoder.putLocalizedTextArray(fieldName, (LocalizedText[]) o);
		}
		else if (o instanceof ExtensionObject[]) {
			binaryEncoder.putExtensionObjectArray(fieldName, (ExtensionObject[]) o);
		}
		else if (o.getClass().getComponentType() != null && Structure.class.isAssignableFrom(o.getClass().getComponentType())) {
			Object[] array = (Object[]) o;
			Structure[] structArray = new Structure[array.length];
			for (int i = 0; i < array.length; i++)
				structArray[i] = (Structure) array[i];
			binaryEncoder.putStructureArray(fieldName, structArray);
		}
		else if (o instanceof DataValue[]) {
			binaryEncoder.putDataValueArray(fieldName, (DataValue[]) o);
		}
		else if (o instanceof Variant[]) {
			binaryEncoder.putVariantArray(fieldName, (Variant[]) o);
		}
		else if (o instanceof DiagnosticInfo[]) {
			binaryEncoder.putDiagnosticInfoArray(fieldName, (DiagnosticInfo[]) o);
		}
		else if (o instanceof Enumeration) {
			binaryEncoder.putEnumeration(fieldName, (Enumeration) o);
		}
		else if (o.getClass().getComponentType() != null && Enumeration.class.isAssignableFrom(o.getClass().getComponentType())) {
			binaryEncoder.putEnumerationArray(fieldName, o);
		}
		else throw new EncodingException("Cannot encode "+o);
	}
}
