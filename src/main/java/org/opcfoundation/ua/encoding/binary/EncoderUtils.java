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

import org.opcfoundation.ua.builtintypes.ByteString;
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

/**
 * <p>EncoderUtils class.</p>
 *
 */
public class EncoderUtils {
	/**
	 * <p>put.</p>
	 *
	 * @param encoder a {@link org.opcfoundation.ua.encoding.IEncoder} object.
	 * @param fieldName a {@link java.lang.String} object.
	 * @param o a {@link java.lang.Object} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public static void put(IEncoder encoder, String fieldName, Object o) throws EncodingException {
		if(o == null){
			throw new EncodingException("Cannot encode null object without Class information");
		}
		
		put(encoder, fieldName, o, o.getClass());		
	}
	

	/**
	 * <p>put.</p>
	 *
	 * @param encoder a {@link org.opcfoundation.ua.encoding.IEncoder} object.
	 * @param fieldName a {@link java.lang.String} object.
	 * @param o a {@link java.lang.Object} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	@SuppressWarnings("unchecked")
	public static void put(IEncoder encoder, String fieldName, Object o, Class<?> clazz) throws EncodingException {
		if (Boolean.class.isAssignableFrom(clazz)) {
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
		else if (ByteString.class.isAssignableFrom(clazz)) {
			encoder.putByteString(fieldName, (ByteString) o);
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
		else if (ByteString[].class.isAssignableFrom(clazz)) {
			encoder.putByteStringArray(fieldName, (ByteString[]) o);
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
			encoder.putEncodeableArray(fieldName, (Class<? extends IEncodeable>) clazz.getComponentType(), (Structure[]) o);
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
			encoder.putEnumerationArray(fieldName, (Enumeration[])o);
		}
		else throw new EncodingException("Cannot encode "+o);
	}	
}
