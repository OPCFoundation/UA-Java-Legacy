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
import org.opcfoundation.ua.encoding.binary.BinaryDecoder;
import org.opcfoundation.ua.encoding.xml.XmlDecoder;


/**
 * <p>IDecoder interface.</p>
 *
 * @see IEncoder
 * @see BinaryDecoder
 * @see XmlDecoder
 */
public interface IDecoder {
	
	/**
	 * <p>getBoolean.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link java.lang.Boolean} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Boolean getBoolean(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getBooleanArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.Boolean} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Boolean[] getBooleanArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getSByte.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link java.lang.Byte} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Byte getSByte(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getSByteArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.Byte} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Byte[] getSByteArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getByte.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public UnsignedByte getByte(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getByteArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.UnsignedByte} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public UnsignedByte[] getByteArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getInt16.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link java.lang.Short} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Short getInt16(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getInt16Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.Short} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Short[] getInt16Array(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getUInt16.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public UnsignedShort getUInt16(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getUInt16Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.UnsignedShort} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public UnsignedShort[] getUInt16Array(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getInt32.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link java.lang.Integer} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Integer getInt32(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getInt32Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.Integer} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Integer[] getInt32Array(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getInt32Array_.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of int.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public int[] getInt32Array_(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getUInt32.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public UnsignedInteger getUInt32(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getUInt32Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public UnsignedInteger[] getUInt32Array(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getInt64.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link java.lang.Long} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Long getInt64(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getInt64Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.Long} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Long[] getInt64Array(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getUInt64.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedLong} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public UnsignedLong getUInt64(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getUInt64Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.UnsignedLong} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public UnsignedLong[] getUInt64Array(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getFloat.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link java.lang.Float} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Float getFloat(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getFloatArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.Float} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Float[] getFloatArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getDouble.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link java.lang.Double} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Double getDouble(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getDoubleArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.Double} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Double[] getDoubleArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getString.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public String getString(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getStringArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.String} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public String[] getStringArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getDateTime.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.DateTime} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public DateTime getDateTime(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getDateTimeArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.DateTime} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public DateTime[] getDateTimeArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getGuid.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link java.util.UUID} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public UUID getGuid(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getGuidArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link java.util.UUID} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public UUID[] getGuidArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getByteString.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of byte.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public ByteString getByteString(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getByteStringArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of byte.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public ByteString[] getByteStringArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getXmlElement.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.XmlElement} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public XmlElement getXmlElement(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getXmlElementArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.XmlElement} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public XmlElement[] getXmlElementArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getNodeId.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public NodeId getNodeId(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getNodeIdArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.NodeId} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public NodeId[] getNodeIdArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getExpandedNodeId.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public ExpandedNodeId getExpandedNodeId(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getExpandedNodeIdArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public ExpandedNodeId[] getExpandedNodeIdArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getStatusCode.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public StatusCode getStatusCode(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getStatusCodeArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.StatusCode} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public StatusCode[] getStatusCodeArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getQualifiedName.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.QualifiedName} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public QualifiedName getQualifiedName(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getQualifiedNameArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.QualifiedName} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public QualifiedName[] getQualifiedNameArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getLocalizedText.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.LocalizedText} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public LocalizedText getLocalizedText(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getLocalizedTextArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.LocalizedText} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public LocalizedText[] getLocalizedTextArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getStructure.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.Structure} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Structure getStructure(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getStructureArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.Structure} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Structure[] getStructureArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getExtensionObject.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.ExtensionObject} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public ExtensionObject getExtensionObject(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getExtensionObjectArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.ExtensionObject} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public ExtensionObject[] getExtensionObjectArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getDataValue.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.DataValue} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public DataValue getDataValue(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getDataValueArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.DataValue} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public DataValue[] getDataValueArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getVariant.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.Variant} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Variant getVariant(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getVariantArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.Variant} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Variant[] getVariantArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getDiagnosticInfo.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.DiagnosticInfo} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public DiagnosticInfo getDiagnosticInfo(String fieldName)
    throws DecodingException;
	
	/**
	 * <p>getDiagnosticInfoArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @return an array of {@link org.opcfoundation.ua.builtintypes.DiagnosticInfo} objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public DiagnosticInfo[] getDiagnosticInfoArray(String fieldName)
    throws DecodingException;	
	
	/**
	 * <p>getEnumerationArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param enumerationClass a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return an array of T objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public <T extends Enumeration> T[] getEnumerationArray(String fieldName, Class<T> enumerationClass)
    throws DecodingException;	
	
	/**
	 * <p>getEnumeration.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param enumerationClass a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public <T extends Enumeration> T getEnumeration(String fieldName, Class<T> enumerationClass)
    throws DecodingException;	
	
	/**
	 * <p>getEncodeableArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param encodeableClass a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return an array of T objects.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public <T extends IEncodeable> T[] getEncodeableArray(String fieldName, Class<? extends T> encodeableClass)
    throws DecodingException;	
	
	/**
	 * <p>getEncodeable.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param encodeableClass a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public <T extends IEncodeable> T getEncodeable(String fieldName, Class<? extends T> encodeableClass)
    throws DecodingException;

	/**
	 * <p>get.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public <T> T get(String fieldName, Class<T> clazz)
	throws DecodingException;
	
	/**
	 * <p>getMessage.</p>
	 *
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public <T extends IEncodeable> T getMessage()
    throws DecodingException;	
	
	/**
	 * <p>getScalarObject.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param builtinTypeId a int.
	 * @return a {@link java.lang.Object} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Object getScalarObject(String fieldName, int builtinTypeId)
    throws DecodingException;	
	
	/**
	 * <p>getArrayObject.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param builtinTypeId a int.
	 * @return a {@link java.lang.Object} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Object getArrayObject(String fieldName, int builtinTypeId)
    throws DecodingException;	
	
}
