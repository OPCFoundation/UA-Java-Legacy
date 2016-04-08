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
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.IEncoder;

public class EncoderUtils {
	public static void put(IEncoder encoder, String fieldName, Object o) throws EncodingException {
		if (o instanceof Boolean) {
			encoder.putBoolean(fieldName, (Boolean) o);
		}
		else if (o instanceof Byte) {
			encoder.putSByte(fieldName, (Byte) o);
		}
		else if (o instanceof UnsignedByte) {
			encoder.putByte(fieldName, (UnsignedByte) o);
		}
		else if (o instanceof Short) {
			encoder.putInt16(fieldName, (Short) o);
		}
		else if (o instanceof UnsignedShort) {
			encoder.putUInt16(fieldName, (UnsignedShort) o);
		}
		else if (o instanceof Integer) {
			encoder.putInt32(fieldName, (Integer) o);
		}
		else if (o instanceof UnsignedInteger) {
			encoder.putUInt32(fieldName, (UnsignedInteger) o);
		}
		else if (o instanceof Long) {
			encoder.putInt64(fieldName, (Long) o);
		}
		else if (o instanceof UnsignedLong) {
			encoder.putUInt64(fieldName, (UnsignedLong) o);
		}
		else if (o instanceof Float) {
			encoder.putFloat(fieldName, (Float) o);
		}
		else if (o instanceof Double) {
			encoder.putDouble(fieldName, (Double) o);
		}
		else if (o instanceof String) {
			encoder.putString(fieldName, (String) o);
		}
		else if (o instanceof DateTime) {
			encoder.putDateTime(fieldName, (DateTime) o);
		}
		else if (o instanceof UUID) {
			encoder.putGuid(fieldName, (UUID) o);
		}
		else if (o instanceof byte[]) {
			encoder.putByteString(fieldName, (byte[]) o);
		}
		else if (o instanceof XmlElement) {
			encoder.putXmlElement(fieldName, (XmlElement) o);
		}
		else if (o instanceof NodeId) {
			encoder.putNodeId(fieldName, (NodeId) o);
		}
		else if (o instanceof ExpandedNodeId) {
			encoder.putExpandedNodeId(fieldName, (ExpandedNodeId) o);
		}
		else if (o instanceof StatusCode) {
			encoder.putStatusCode(fieldName, (StatusCode) o);
		}
		else if (o instanceof QualifiedName) {
			encoder.putQualifiedName(fieldName, (QualifiedName) o);
		}
		else if (o instanceof LocalizedText) {
			encoder.putLocalizedText(fieldName, (LocalizedText) o);
		}
		else if (o instanceof ExtensionObject) {
			encoder.putExtensionObject(fieldName, (ExtensionObject) o);
		}
		else if (o instanceof Structure) {
			encoder.putEncodeable(fieldName, (Structure) o);
		}
		else if (o instanceof DataValue) {
			encoder.putDataValue(fieldName, (DataValue) o);
		}
		else if (o instanceof Variant) {
			encoder.putVariant(fieldName, (Variant) o);
		}
		else if (o instanceof DiagnosticInfo) {
			encoder.putDiagnosticInfo(fieldName, (DiagnosticInfo) o);
		}
		else if (o instanceof Boolean[]) {
			encoder.putBooleanArray(fieldName, (Boolean[]) o);
		}
		else if (o instanceof Byte[]) {
			encoder.putSByteArray(fieldName, (Byte[]) o);
		}
		else if (o instanceof UnsignedByte[]) {
			encoder.putByteArray(fieldName, (UnsignedByte[]) o);
		}
		else if (o instanceof Short[]) {
			encoder.putInt16Array(fieldName, (Short[]) o);
		}
		else if (o instanceof UnsignedShort[]) {
			encoder.putUInt16Array(fieldName, (UnsignedShort[]) o);
		}
		else if (o instanceof Integer[]) {
			encoder.putInt32Array(fieldName, (Integer[]) o);
		}
		else if (o instanceof UnsignedInteger[]) {
			encoder.putUInt32Array(fieldName, (UnsignedInteger[]) o);
		}
		else if (o instanceof Long[]) {
			encoder.putInt64Array(fieldName, (Long[]) o);
		}
		else if (o instanceof UnsignedLong[]) {
			encoder.putUInt64Array(fieldName, (UnsignedLong[]) o);
		}
		else if (o instanceof Float[]) {
			encoder.putFloatArray(fieldName, (Float[]) o);
		}
		else if (o instanceof Double[]) {
			encoder.putDoubleArray(fieldName, (Double[]) o);
		}
		else if (o instanceof String[]) {
			encoder.putStringArray(fieldName, (String[]) o);
		}
		else if (o instanceof DateTime[]) {
			encoder.putDateTimeArray(fieldName, (DateTime[]) o);
		}
		else if (o instanceof UUID[]) {
			encoder.putGuidArray(fieldName, (UUID[]) o);
		}
		else if (o instanceof byte[][]) {
			encoder.putByteStringArray(fieldName, (byte[][]) o);
		}
		else if (o instanceof XmlElement[]) {
			encoder.putXmlElementArray(fieldName, (XmlElement[]) o);
		}
		else if (o instanceof NodeId[]) {
			encoder.putNodeIdArray(fieldName, (NodeId[]) o);
		}
		else if (o instanceof ExpandedNodeId[]) {
			encoder.putExpandedNodeIdArray(fieldName, (ExpandedNodeId[]) o);
		}
		else if (o instanceof StatusCode[]) {
			encoder.putStatusCodeArray(fieldName, (StatusCode[]) o);
		}
		else if (o instanceof QualifiedName[]) {
			encoder.putQualifiedNameArray(fieldName, (QualifiedName[]) o);
		}
		else if (o instanceof LocalizedText[]) {
			encoder.putLocalizedTextArray(fieldName, (LocalizedText[]) o);
		}
		else if (o instanceof ExtensionObject[]) {
			encoder.putExtensionObjectArray(fieldName, (ExtensionObject[]) o);
		}
		else if (o.getClass().getComponentType() != null && Structure.class.isAssignableFrom(o.getClass().getComponentType())) {
			Object[] array = (Object[]) o;
			Structure[] structArray = new Structure[array.length];
			for (int i = 0; i < array.length; i++)
				structArray[i] = (Structure) array[i];
			encoder.putStructureArray(fieldName, structArray);
		}
		else if (o instanceof DataValue[]) {
			encoder.putDataValueArray(fieldName, (DataValue[]) o);
		}
		else if (o instanceof Variant[]) {
			encoder.putVariantArray(fieldName, (Variant[]) o);
		}
		else if (o instanceof DiagnosticInfo[]) {
			encoder.putDiagnosticInfoArray(fieldName, (DiagnosticInfo[]) o);
		}
		else if (o instanceof Enumeration) {
			encoder.putEnumeration(fieldName, (Enumeration) o);
		}
		else if (o.getClass().getComponentType() != null && Enumeration.class.isAssignableFrom(o.getClass().getComponentType())) {
			encoder.putEnumerationArray(fieldName, o);
		}
		else throw new EncodingException("Cannot encode "+o);
	}
	

	@SuppressWarnings("unchecked")
	public static void put(IEncoder encoder, String fieldName, Object o, Class<?> clazz) throws EncodingException {
		if (o != null) {
			put(encoder, fieldName, o);
		}
		else if (Boolean.class.isAssignableFrom(clazz)) {
			encoder.putBoolean(fieldName, (Boolean) o);
		}
		else if (Byte.class.isAssignableFrom(clazz)) {
			encoder.putSByte(fieldName, (Byte) o);
		}
		else if (UnsignedByte.class.isAssignableFrom(clazz)) {
			encoder.putByte(fieldName, (UnsignedByte) o);
		}
		else if (Short.class.isAssignableFrom(clazz)) {
			encoder.putInt16(fieldName, (Short) o);
		}
		else if (UnsignedShort.class.isAssignableFrom(clazz)) {
			encoder.putUInt16(fieldName, (UnsignedShort) o);
		}
		else if (Integer.class.isAssignableFrom(clazz)) {
			encoder.putInt32(fieldName, (Integer) o);
		}
		else if (UnsignedInteger.class.isAssignableFrom(clazz)) {
			encoder.putUInt32(fieldName, (UnsignedInteger) o);
		}
		else if (Long.class.isAssignableFrom(clazz)) {
			encoder.putInt64(fieldName, (Long) o);
		}
		else if (UnsignedLong.class.isAssignableFrom(clazz)) {
			encoder.putUInt64(fieldName, (UnsignedLong) o);
		}
		else if (Float.class.isAssignableFrom(clazz)) {
			encoder.putFloat(fieldName, (Float) o);
		}
		else if (Double.class.isAssignableFrom(clazz)) {
			encoder.putDouble(fieldName, (Double) o);
		}
		else if (String.class.isAssignableFrom(clazz)) {
			encoder.putString(fieldName, (String) o);
		}
		else if (DateTime.class.isAssignableFrom(clazz)) {
			encoder.putDateTime(fieldName, (DateTime) o);
		}
		else if (UUID.class.isAssignableFrom(clazz)) {
			encoder.putGuid(fieldName, (UUID) o);
		}
		else if (byte[].class.isAssignableFrom(clazz)) {
			encoder.putByteString(fieldName, (byte[]) o);
		}
		else if (XmlElement.class.isAssignableFrom(clazz)) {
			encoder.putXmlElement(fieldName, (XmlElement) o);
		}
		else if (NodeId.class.isAssignableFrom(clazz)) {
			encoder.putNodeId(fieldName, (NodeId) o);
		}
		else if (ExpandedNodeId.class.isAssignableFrom(clazz)) {
			encoder.putExpandedNodeId(fieldName, (ExpandedNodeId) o);
		}
		else if (StatusCode.class.isAssignableFrom(clazz)) {
			encoder.putStatusCode(fieldName, (StatusCode) o);
		}
		else if (QualifiedName.class.isAssignableFrom(clazz)) {
			encoder.putQualifiedName(fieldName, (QualifiedName) o);
		}
		else if (LocalizedText.class.isAssignableFrom(clazz)) {
			encoder.putLocalizedText(fieldName, (LocalizedText) o);
		}
		else if (ExtensionObject.class.isAssignableFrom(clazz)) {
			encoder.putExtensionObject(fieldName, (ExtensionObject) o);
		}
		else if (Structure.class.isAssignableFrom(clazz)) {
			encoder.putEncodeable(fieldName, (Class<? extends IEncodeable>) clazz, (Structure) o);
		}
		else if (DataValue.class.isAssignableFrom(clazz)) {
			encoder.putDataValue(fieldName, (DataValue) o);
		}
		else if (Variant.class.isAssignableFrom(clazz)) {
			encoder.putVariant(fieldName, (Variant) o);
		}
		else if (DiagnosticInfo.class.isAssignableFrom(clazz)) {
			encoder.putDiagnosticInfo(fieldName, (DiagnosticInfo) o);
		}
		else if (Boolean[].class.isAssignableFrom(clazz)) {
			encoder.putBooleanArray(fieldName, (Boolean[]) o);
		}
		else if (Byte[].class.isAssignableFrom(clazz)) {
			encoder.putSByteArray(fieldName, (Byte[]) o);
		}
		else if (UnsignedByte[].class.isAssignableFrom(clazz)) {
			encoder.putByteArray(fieldName, (UnsignedByte[]) o);
		}
		else if (Short[].class.isAssignableFrom(clazz)) {
			encoder.putInt16Array(fieldName, (Short[]) o);
		}
		else if (UnsignedShort[].class.isAssignableFrom(clazz)) {
			encoder.putUInt16Array(fieldName, (UnsignedShort[]) o);
		}
		else if (Integer[].class.isAssignableFrom(clazz)) {
			encoder.putInt32Array(fieldName, (Integer[]) o);
		}
		else if (UnsignedInteger[].class.isAssignableFrom(clazz)) {
			encoder.putUInt32Array(fieldName, (UnsignedInteger[]) o);
		}
		else if (Long[].class.isAssignableFrom(clazz)) {
			encoder.putInt64Array(fieldName, (Long[]) o);
		}
		else if (UnsignedLong[].class.isAssignableFrom(clazz)) {
			encoder.putUInt64Array(fieldName, (UnsignedLong[]) o);
		}
		else if (Float[].class.isAssignableFrom(clazz)) {
			encoder.putFloatArray(fieldName, (Float[]) o);
		}
		else if (Double[].class.isAssignableFrom(clazz)) {
			encoder.putDoubleArray(fieldName, (Double[]) o);
		}
		else if (String[].class.isAssignableFrom(clazz)) {
			encoder.putStringArray(fieldName, (String[]) o);
		}
		else if (DateTime[].class.isAssignableFrom(clazz)) {
			encoder.putDateTimeArray(fieldName, (DateTime[]) o);
		}
		else if (UUID[].class.isAssignableFrom(clazz)) {
			encoder.putGuidArray(fieldName, (UUID[]) o);
		}
		else if (byte[][].class.isAssignableFrom(clazz)) {
			encoder.putByteStringArray(fieldName, (byte[][]) o);
		}
		else if (XmlElement[].class.isAssignableFrom(clazz)) {
			encoder.putXmlElementArray(fieldName, (XmlElement[]) o);
		}
		else if (NodeId[].class.isAssignableFrom(clazz)) {
			encoder.putNodeIdArray(fieldName, (NodeId[]) o);
		}
		else if (ExpandedNodeId[].class.isAssignableFrom(clazz)) {
			encoder.putExpandedNodeIdArray(fieldName, (ExpandedNodeId[]) o);
		}
		else if (StatusCode[].class.isAssignableFrom(clazz)) {
			encoder.putStatusCodeArray(fieldName, (StatusCode[]) o);
		}
		else if (QualifiedName[].class.isAssignableFrom(clazz)) {
			encoder.putQualifiedNameArray(fieldName, (QualifiedName[]) o);
		}
		else if (LocalizedText[].class.isAssignableFrom(clazz)) {
			encoder.putLocalizedTextArray(fieldName, (LocalizedText[]) o);
		}
		else if (ExtensionObject[].class.isAssignableFrom(clazz)) {
			encoder.putExtensionObjectArray(fieldName, (ExtensionObject[]) o);
		}
		else if (Structure[].class.isAssignableFrom(clazz)) {
			encoder.putStructureArray(fieldName, (Structure[]) null);
		}
		else if (DataValue[].class.isAssignableFrom(clazz)) {
			encoder.putDataValueArray(fieldName, (DataValue[]) o);
		}
		else if (Variant[].class.isAssignableFrom(clazz)) {
			encoder.putVariantArray(fieldName, (Variant[]) o);
		}
		else if (DiagnosticInfo[].class.isAssignableFrom(clazz)) {
			encoder.putDiagnosticInfoArray(fieldName, (DiagnosticInfo[]) o);
		}
		else if (Enumeration.class.isAssignableFrom(clazz)) {
			encoder.putEnumeration(fieldName, (Enumeration) o);
		}
		else if (Enumeration[].class.isAssignableFrom(clazz)) {
			encoder.putEnumerationArray(fieldName, null);
		}
		else throw new EncodingException("Cannot encode "+o);
	}
}
