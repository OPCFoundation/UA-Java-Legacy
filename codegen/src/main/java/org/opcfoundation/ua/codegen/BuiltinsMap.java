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

package org.opcfoundation.ua.codegen;


import java.util.HashMap;
import java.util.Map;


/**
 *
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class BuiltinsMap {

	/** Builtins URL -> ClassName map */
	public final static Map<String, String> MAP;
	
	public final static Map<String, Integer> ID_MAP;
	public final static Map<String, String> NAME_MAP;	
		
	static {
		String prefix = "org.opcfoundation.ua.builtintypes.";
		MAP = new HashMap<String, String>();		
		MAP.put("http://opcfoundation.org/UA/Boolean", "java.lang.Boolean");
		MAP.put("http://opcfoundation.org/UA/SByte", "java.lang.Byte");
		MAP.put("http://opcfoundation.org/UA/Byte", prefix+"UnsignedByte");
		MAP.put("http://opcfoundation.org/UA/Int16", "java.lang.Short");
		MAP.put("http://opcfoundation.org/UA/UInt16", prefix+"UnsignedShort");
		MAP.put("http://opcfoundation.org/UA/Int32", "java.lang.Integer");
		MAP.put("http://opcfoundation.org/UA/UInt32", prefix+"UnsignedInteger");
		MAP.put("http://opcfoundation.org/UA/Int64", "java.lang.Long");
		MAP.put("http://opcfoundation.org/UA/UInt64", prefix+"UnsignedLong");
		MAP.put("http://opcfoundation.org/UA/Float", "java.lang.Float");
		MAP.put("http://opcfoundation.org/UA/Double", "java.lang.Double");
		MAP.put("http://opcfoundation.org/UA/String", "java.lang.String");
		MAP.put("http://opcfoundation.org/UA/DateTime", prefix+"DateTime");
		MAP.put("http://opcfoundation.org/UA/Guid", "java.util.UUID");
		MAP.put("http://opcfoundation.org/UA/ByteString", prefix+ "ByteString");
		MAP.put("http://opcfoundation.org/UA/XmlElement", prefix+"XmlElement");
		MAP.put("http://opcfoundation.org/UA/NodeId", prefix+"NodeId");
		MAP.put("http://opcfoundation.org/UA/ExpandedNodeId", prefix+"ExpandedNodeId");
		MAP.put("http://opcfoundation.org/UA/StatusCode", prefix+"StatusCode");
		MAP.put("http://opcfoundation.org/UA/QualifiedName", prefix+"QualifiedName");
		MAP.put("http://opcfoundation.org/UA/LocalizedText", prefix+"LocalizedText");
		MAP.put("http://opcfoundation.org/UA/ExtensionObject", prefix+"ExtensionObject");
		MAP.put("http://opcfoundation.org/UA/Structure", prefix+"Structure");
		MAP.put("http://opcfoundation.org/UA/DataValue", prefix+"DataValue");
		MAP.put("http://opcfoundation.org/UA/Variant", prefix+"Variant");		
		MAP.put("http://opcfoundation.org/UA/BaseDataType", prefix+"Variant");		
		MAP.put("http://opcfoundation.org/UA/DiagnosticInfo", prefix+"DiagnosticInfo");
		
		ID_MAP = new HashMap<String, Integer>();		
		ID_MAP.put("http://opcfoundation.org/UA/Boolean", 1);
		ID_MAP.put("http://opcfoundation.org/UA/SByte", 2);
		ID_MAP.put("http://opcfoundation.org/UA/Byte", 3);
		ID_MAP.put("http://opcfoundation.org/UA/Int16", 4);
		ID_MAP.put("http://opcfoundation.org/UA/UInt16", 5);
		ID_MAP.put("http://opcfoundation.org/UA/Int32", 6);
		ID_MAP.put("http://opcfoundation.org/UA/UInt32", 7);
		ID_MAP.put("http://opcfoundation.org/UA/Int64", 8);
		ID_MAP.put("http://opcfoundation.org/UA/UInt64", 9);
		ID_MAP.put("http://opcfoundation.org/UA/Float", 10);
		ID_MAP.put("http://opcfoundation.org/UA/Double", 11);
		ID_MAP.put("http://opcfoundation.org/UA/String", 12);
		ID_MAP.put("http://opcfoundation.org/UA/DateTime", 13);
		ID_MAP.put("http://opcfoundation.org/UA/Guid", 14);
		ID_MAP.put("http://opcfoundation.org/UA/ByteString", 15);
		ID_MAP.put("http://opcfoundation.org/UA/XmlElement", 16);
		ID_MAP.put("http://opcfoundation.org/UA/NodeId", 17);
		ID_MAP.put("http://opcfoundation.org/UA/ExpandedNodeId", 18);
		ID_MAP.put("http://opcfoundation.org/UA/StatusCode", 19);
		ID_MAP.put("http://opcfoundation.org/UA/QualifiedName", 20);
		ID_MAP.put("http://opcfoundation.org/UA/LocalizedText", 21);
		ID_MAP.put("http://opcfoundation.org/UA/ExtensionObject", 22);
		ID_MAP.put("http://opcfoundation.org/UA/Structure", 22);
		ID_MAP.put("http://opcfoundation.org/UA/DataValue", 23);
		ID_MAP.put("http://opcfoundation.org/UA/Variant", 24);		
		ID_MAP.put("http://opcfoundation.org/UA/BaseDataType", 24);		
		ID_MAP.put("http://opcfoundation.org/UA/DiagnosticInfo", 25);		

		NAME_MAP = new HashMap<String, String>();		
		NAME_MAP.put("http://opcfoundation.org/UA/Boolean", "Boolean");
		NAME_MAP.put("http://opcfoundation.org/UA/SByte", "SByte");
		NAME_MAP.put("http://opcfoundation.org/UA/Byte", "Byte");
		NAME_MAP.put("http://opcfoundation.org/UA/Int16", "Int16");
		NAME_MAP.put("http://opcfoundation.org/UA/UInt16", "UInt16");
		NAME_MAP.put("http://opcfoundation.org/UA/Int32", "Int32");
		NAME_MAP.put("http://opcfoundation.org/UA/UInt32", "UInt32");
		NAME_MAP.put("http://opcfoundation.org/UA/Int64", "Int64");
		NAME_MAP.put("http://opcfoundation.org/UA/UInt64", "UInt64");
		NAME_MAP.put("http://opcfoundation.org/UA/Float", "Float");
		NAME_MAP.put("http://opcfoundation.org/UA/Double", "Double");
		NAME_MAP.put("http://opcfoundation.org/UA/String", "String");
		NAME_MAP.put("http://opcfoundation.org/UA/DateTime", "DateTime");
		NAME_MAP.put("http://opcfoundation.org/UA/Guid", "Guid");
		NAME_MAP.put("http://opcfoundation.org/UA/ByteString", "ByteString");
		NAME_MAP.put("http://opcfoundation.org/UA/XmlElement", "XmlElement");
		NAME_MAP.put("http://opcfoundation.org/UA/NodeId", "NodeId");
		NAME_MAP.put("http://opcfoundation.org/UA/ExpandedNodeId", "ExpandedNodeId");
		NAME_MAP.put("http://opcfoundation.org/UA/StatusCode", "StatusCode");
		NAME_MAP.put("http://opcfoundation.org/UA/QualifiedName", "QualifiedName");
		NAME_MAP.put("http://opcfoundation.org/UA/LocalizedText", "LocalizedText");
		NAME_MAP.put("http://opcfoundation.org/UA/ExtensionObject", "ExtensionObject");
		NAME_MAP.put("http://opcfoundation.org/UA/Structure", "ExtensionObject");
		NAME_MAP.put("http://opcfoundation.org/UA/DataValue", "DataValue");
		NAME_MAP.put("http://opcfoundation.org/UA/Variant", "Variant");		
		NAME_MAP.put("http://opcfoundation.org/UA/BaseDataType", "Variant");		
		NAME_MAP.put("http://opcfoundation.org/UA/DiagnosticInfo", "DiagnosticInfo");		
		
		
	}
	
}
