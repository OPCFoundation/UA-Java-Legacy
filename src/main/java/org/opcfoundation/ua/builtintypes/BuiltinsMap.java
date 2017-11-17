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

package org.opcfoundation.ua.builtintypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.BijectionMap;


/**
 *
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class BuiltinsMap {

	/** Builtins URL -&gt; ClassName map */
	public final static Map<String, Class<?>> MAP;	
	public final static Map<Class<?>, Integer> ID_MAP;
	public final static BijectionMap<NodeId, Class<?>> ID_CLASS_MAP;
	
	public final static ArrayList<Class<?>> SCALAR_LIST;
	public final static ArrayList<Class<?>> ARRAY_LIST;
	
	static {
		MAP = new HashMap<String, Class<?>>();		
		MAP.put("http://opcfoundation.org/UA/Boolean", Boolean.class);
		MAP.put("http://opcfoundation.org/UA/SByte", Byte.class);
		MAP.put("http://opcfoundation.org/UA/Byte", UnsignedByte.class);
		MAP.put("http://opcfoundation.org/UA/Int16", Short.class);
		MAP.put("http://opcfoundation.org/UA/UInt16", UnsignedShort.class);
		MAP.put("http://opcfoundation.org/UA/Int32", Integer.class);
		MAP.put("http://opcfoundation.org/UA/UInt32", UnsignedInteger.class);
		MAP.put("http://opcfoundation.org/UA/Int64", Long.class);
		MAP.put("http://opcfoundation.org/UA/UInt64", UnsignedLong.class);
		MAP.put("http://opcfoundation.org/UA/Float", Float.class);
		MAP.put("http://opcfoundation.org/UA/Double", Double.class);
		MAP.put("http://opcfoundation.org/UA/String", String.class);
		MAP.put("http://opcfoundation.org/UA/DateTime", DateTime.class);
		MAP.put("http://opcfoundation.org/UA/Guid", UUID.class);
		MAP.put("http://opcfoundation.org/UA/ByteString", ByteString.class);
		MAP.put("http://opcfoundation.org/UA/XmlElement", XmlElement.class);
		MAP.put("http://opcfoundation.org/UA/NodeId", NodeId.class);
		MAP.put("http://opcfoundation.org/UA/ExpandedNodeId", ExpandedNodeId.class);
		MAP.put("http://opcfoundation.org/UA/StatusCode", StatusCode.class);
		MAP.put("http://opcfoundation.org/UA/QualifiedName", QualifiedName.class);
		MAP.put("http://opcfoundation.org/UA/LocalizedText", LocalizedText.class);
		MAP.put("http://opcfoundation.org/UA/ExtensionObject", ExtensionObject.class);
		MAP.put("http://opcfoundation.org/UA/DataValue", DataValue.class);
		MAP.put("http://opcfoundation.org/UA/Variant", Variant.class);		
		MAP.put("http://opcfoundation.org/UA/DiagnosticInfo", DiagnosticInfo.class);
		
		ID_MAP = new HashMap<Class<?>, Integer>();		
		ID_MAP.put(Boolean.class, 1);
		ID_MAP.put(Byte.class, 2);
		ID_MAP.put(UnsignedByte.class, 3);
		ID_MAP.put(Short.class, 4);
		ID_MAP.put(UnsignedShort.class, 5);
		ID_MAP.put(Integer.class, 6);
		ID_MAP.put(UnsignedInteger.class, 7);
		ID_MAP.put(Long.class, 8);
		ID_MAP.put(UnsignedLong.class, 9);
		ID_MAP.put(Float.class, 10);
		ID_MAP.put(Double.class, 11);
		ID_MAP.put(String.class, 12);
		ID_MAP.put(DateTime.class, 13);
		ID_MAP.put(UUID.class, 14);
		ID_MAP.put(ByteString.class, 15);
		ID_MAP.put(XmlElement.class, 16);
		ID_MAP.put(NodeId.class, 17);
		ID_MAP.put(ExpandedNodeId.class, 18);
		ID_MAP.put(StatusCode.class, 19);
		ID_MAP.put(QualifiedName.class, 20);
		ID_MAP.put(LocalizedText.class, 21);
		ID_MAP.put(ExtensionObject.class, 22);
		ID_MAP.put(DataValue.class, 23);
		ID_MAP.put(Variant.class, 24);		
		ID_MAP.put(DiagnosticInfo.class, 25);
		
		ID_MAP.put(Boolean[].class, 1);
		ID_MAP.put(Byte[].class, 2);
		ID_MAP.put(UnsignedByte[].class, 3);
		ID_MAP.put(Short[].class, 4);
		ID_MAP.put(UnsignedShort[].class, 5);
		ID_MAP.put(Integer[].class, 6);
		ID_MAP.put(UnsignedInteger[].class, 7);
		ID_MAP.put(Long[].class, 8);
		ID_MAP.put(UnsignedLong[].class, 9);
		ID_MAP.put(Float[].class, 10);
		ID_MAP.put(Double[].class, 11);
		ID_MAP.put(String[].class, 12);
		ID_MAP.put(DateTime[].class, 13);
		ID_MAP.put(UUID[].class, 14);
		ID_MAP.put(ByteString[].class, 15);
		ID_MAP.put(XmlElement[].class, 16);
		ID_MAP.put(NodeId[].class, 17);
		ID_MAP.put(ExpandedNodeId[].class, 18);
		ID_MAP.put(StatusCode[].class, 19);
		ID_MAP.put(QualifiedName[].class, 20);
		ID_MAP.put(LocalizedText[].class, 21);
		ID_MAP.put(ExtensionObject[].class, 22);
		ID_MAP.put(DataValue[].class, 23);
		ID_MAP.put(Variant[].class, 24);		
		ID_MAP.put(DiagnosticInfo[].class, 25);	
		
		
		
		SCALAR_LIST = new ArrayList<Class<?>>();
		SCALAR_LIST.add(null);
		SCALAR_LIST.add(Boolean.class);
		SCALAR_LIST.add(Byte.class);
		SCALAR_LIST.add(UnsignedByte.class);
		SCALAR_LIST.add(Short.class);
		SCALAR_LIST.add(UnsignedShort.class);
		SCALAR_LIST.add(Integer.class);
		SCALAR_LIST.add(UnsignedInteger.class);
		SCALAR_LIST.add(Long.class);
		SCALAR_LIST.add(UnsignedLong.class);
		SCALAR_LIST.add(Float.class);
		SCALAR_LIST.add(Double.class);
		SCALAR_LIST.add(String.class);
		SCALAR_LIST.add(DateTime.class);
		SCALAR_LIST.add(UUID.class);
		SCALAR_LIST.add(ByteString.class);
		SCALAR_LIST.add(XmlElement.class);
		SCALAR_LIST.add(NodeId.class);
		SCALAR_LIST.add(ExpandedNodeId.class);
		SCALAR_LIST.add(StatusCode.class);
		SCALAR_LIST.add(QualifiedName.class);
		SCALAR_LIST.add(LocalizedText.class);
		SCALAR_LIST.add(ExtensionObject.class);
		SCALAR_LIST.add(DataValue.class);
		SCALAR_LIST.add(Variant.class);
		SCALAR_LIST.add(DiagnosticInfo.class);
		
		ARRAY_LIST = new ArrayList<Class<?>>();
		ARRAY_LIST.add(null);
		ARRAY_LIST.add(Boolean[].class);
		ARRAY_LIST.add(Byte[].class);
		ARRAY_LIST.add(UnsignedByte[].class);
		ARRAY_LIST.add(Short[].class);
		ARRAY_LIST.add(UnsignedShort[].class);
		ARRAY_LIST.add(Integer[].class);
		ARRAY_LIST.add(UnsignedInteger[].class);
		ARRAY_LIST.add(Long[].class);
		ARRAY_LIST.add(UnsignedLong[].class);
		ARRAY_LIST.add(Float[].class);
		ARRAY_LIST.add(Double[].class);
		ARRAY_LIST.add(String[].class);
		ARRAY_LIST.add(DateTime[].class);
		ARRAY_LIST.add(UUID[].class);
		ARRAY_LIST.add(ByteString[].class);
		ARRAY_LIST.add(XmlElement[].class);
		ARRAY_LIST.add(NodeId[].class);
		ARRAY_LIST.add(ExpandedNodeId[].class);
		ARRAY_LIST.add(StatusCode[].class);
		ARRAY_LIST.add(QualifiedName[].class);
		ARRAY_LIST.add(LocalizedText[].class);
		ARRAY_LIST.add(ExtensionObject[].class);
		ARRAY_LIST.add(DataValue[].class);
		ARRAY_LIST.add(Variant[].class);
		ARRAY_LIST.add(DiagnosticInfo[].class);
		
		ID_CLASS_MAP = new BijectionMap<NodeId, Class<?>>();		
		ID_CLASS_MAP.map(Identifiers.Boolean, Boolean.class);
		ID_CLASS_MAP.map(Identifiers.SByte, Byte.class);
		ID_CLASS_MAP.map(Identifiers.Byte, UnsignedByte.class);
		ID_CLASS_MAP.map(Identifiers.Int16, Short.class);
		ID_CLASS_MAP.map(Identifiers.UInt16, UnsignedShort.class);
		ID_CLASS_MAP.map(Identifiers.Int32, Integer.class);
		ID_CLASS_MAP.map(Identifiers.UInt32, UnsignedInteger.class);
		ID_CLASS_MAP.map(Identifiers.Int64, Long.class);
		ID_CLASS_MAP.map(Identifiers.UInt64, UnsignedLong.class);
		ID_CLASS_MAP.map(Identifiers.Float, Float.class);
		ID_CLASS_MAP.map(Identifiers.Double, Double.class);
		ID_CLASS_MAP.map(Identifiers.String, String.class);
		ID_CLASS_MAP.map(Identifiers.DateTime, DateTime.class);
		ID_CLASS_MAP.map(Identifiers.Guid, UUID.class);
		ID_CLASS_MAP.map(Identifiers.ByteString, ByteString.class);
		ID_CLASS_MAP.map(Identifiers.XmlElement, XmlElement.class);
		ID_CLASS_MAP.map(Identifiers.NodeId, NodeId.class);
		ID_CLASS_MAP.map(Identifiers.ExpandedNodeId, ExpandedNodeId.class);
		ID_CLASS_MAP.map(Identifiers.StatusCode, StatusCode.class);
		ID_CLASS_MAP.map(Identifiers.DiagnosticInfo, DiagnosticInfo.class);
		ID_CLASS_MAP.map(Identifiers.QualifiedName, QualifiedName.class);
		ID_CLASS_MAP.map(Identifiers.LocalizedText, LocalizedText.class);
		ID_CLASS_MAP.map(Identifiers.Structure, ExtensionObject.class);
		ID_CLASS_MAP.map(Identifiers.DataValue, DataValue.class);
		ID_CLASS_MAP.map(Identifiers.BaseDataType, Variant.class);		
		ID_CLASS_MAP.map(Identifiers.DiagnosticInfo, DiagnosticInfo.class);

	}
	
}
