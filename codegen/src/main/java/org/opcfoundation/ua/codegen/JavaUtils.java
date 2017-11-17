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

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JavaUtils {
	
	static Map<String, String> uriToClassNameMap = new HashMap<String, String>();
	
	static Set<String> primitives = new HashSet<String>();		
	
	static {
//		 Fills .ClassName field of each DataType instance.
//		 
//		 Rules:
//		   1) The package for builtin types is org.opcfoundation.ua.builtintypes.
//		   2) The package for DataTypes is org.opcfoundation.ua.core
		  
		String prefix = "org.opcfoundation.ua.builtintypes.";
		Map<String, String> map = uriToClassNameMap;
		map.put("http://opcfoundation.org/UA/Boolean", "java.lang.Boolean");
		map.put("http://opcfoundation.org/UA/SByte", "java.lang.Byte");
		map.put("http://opcfoundation.org/UA/Byte", prefix+"UnsignedByte");
		map.put("http://opcfoundation.org/UA/Int16", "java.lang.Short");
		map.put("http://opcfoundation.org/UA/UInt16", prefix+"UnsignedShort");
		map.put("http://opcfoundation.org/UA/Int32", "java.lang.Integer");
		map.put("http://opcfoundation.org/UA/UInt32", prefix+"UnsignedInteger");
		map.put("http://opcfoundation.org/UA/Int64", "java.lang.Long");
		map.put("http://opcfoundation.org/UA/UInt64", prefix+"UnsignedLong");
		map.put("http://opcfoundation.org/UA/Float", "java.lang.Float");
		map.put("http://opcfoundation.org/UA/Double", "java.lang.Double");
		map.put("http://opcfoundation.org/UA/String", "java.lang.String");
		map.put("http://opcfoundation.org/UA/DateTime", prefix+"DateTime");
		map.put("http://opcfoundation.org/UA/Guid", "java.util.UUID");
		map.put("http://opcfoundation.org/UA/ByteString", prefix+ "ByteString");
		map.put("http://opcfoundation.org/UA/XmlElement", prefix+"XmlElement");
		map.put("http://opcfoundation.org/UA/NodeId", prefix+"NodeId");
		map.put("http://opcfoundation.org/UA/ExpandedNodeId", prefix+"ExpandedNodeId");
		map.put("http://opcfoundation.org/UA/StatusCode", prefix+"StatusCode");
		map.put("http://opcfoundation.org/UA/QualifiedName", prefix+"QualifiedName");
		map.put("http://opcfoundation.org/UA/LocalizedText", prefix+"LocalizedText");
		map.put("http://opcfoundation.org/UA/ExtensionObject", prefix+"ExtensionObject");
		map.put("http://opcfoundation.org/UA/DataValue", prefix+"DataValue");
		map.put("http://opcfoundation.org/UA/Variant", prefix+"Variant");		
		map.put("http://opcfoundation.org/UA/DiagnosticInfo", prefix+"DiagnosticInfo");
		primitives.addAll(map.values());

		map.put("http://opcfoundation.org/UA/BaseDataType", prefix+"Object");
		map.put("http://opcfoundation.org/UA/Number", "java.lang.Number");
		map.put("http://opcfoundation.org/UA/Integer", "java.lang.Number");
		map.put("http://opcfoundation.org/UA/UInteger", "java.lang.Number");
		map.put("http://opcfoundation.org/UA/Enumeration", prefix+"Enumeration");
		map.put("http://opcfoundation.org/UA/Structure", prefix+"ExtensionObject");			
	}

	/**
	 * Fixes data type. If the argument is primitive wrapper the actual primitive type is returned 
	 * @param dt input data type
	 * @return data type or primitive type
	 */
	public static DictionaryTypes2.ModelDesign.DataType fixWrapper(DictionaryTypes2.ModelDesign.DataType dt)
	{
		if (dt==null) return null;
		
		return isPrimitiveWrapper(dt) ? dt.getSuperType() : dt;
	}
	
	/**
	 * Convert full class name to simple class name
	 * @param fullClassName
	 * @return class name
	 */
	public static String getClassName(String fullClassName)
	{
		int i = fullClassName.lastIndexOf(".");
		if (i<0) return fullClassName;
		return fullClassName.substring(i+1);
	}	
	public static String getPackageName(String fullClassName)
	{
		int i = fullClassName.lastIndexOf(".");
		if (i<0) return "";
		return fullClassName.substring(0, i);
	}	
	
	/**
	 * A data type is a primitive  
	 * 
	 * @param dt data type
	 * @return true if dt is primitive wrapper
	 */
	public static boolean isPrimitive(DictionaryTypes2.ModelDesign.DataType dt)
	{
		String className = toFullClassName(dt.SymbolicName);
		if (dt.Name.equals("Structure")) className = "java.lang.Object";
		return isPrimitive(className);
	}
	
	
	/**
	 * A data type is a primitive  
	 * 
	 * @param fullClassName
	 * @return true if dt is primitive wrapper
	 */
	public static boolean isPrimitive(String fullClassName)
	{
		return primitives.contains(fullClassName);
	}

	/**
	 * A data type is a primitive wrapper if it inherits primitive type and 
	 * adds no new fields 
	 * 
	 * @param dt data type
	 * @return true if dt is primitive wrapper
	 */
	public static boolean isPrimitiveWrapper(DictionaryTypes2.ModelDesign.DataType dt)
	{
		if (isPrimitive(dt)) return false;
		if (!dt.getFields().isEmpty()) return false;
		
		DictionaryTypes2.ModelDesign.DataType superType = dt.getSuperType();
		if (superType==null) return false;
		
		if (isPrimitive(superType)) return true;
		
		return isPrimitiveWrapper(superType);
	}	
	
	
	
	/**
	 * Converts file reference from file and ensures that the path exists
	 * 
	 * @param dstPath src folder 
	 * @param fullClassName full class name
	 * @return java file path
	 */
	public static File toFile(File dstPath, String fullClassName)
	{
		String packageName = getPackageName(fullClassName);
		String packagePath = packageName.replaceAll("\\.", "/");
		File path = new File( dstPath, packagePath );
		path.mkdirs();
		if (!path.exists()) throw new RuntimeException("Could not create "+path);
		String fileName = fullClassName.replaceAll("\\.", "/");
		File file = new File( dstPath, fileName+".java" );
		return file;
	}	
	
	/**
	 * Converts symbolic name (URI) to full java class name
	 * 
	 * @param symbolicName URI
	 * @return class name (with package & name)
	 */
	public static String toFullClassName(String symbolicName)
	{
		if (uriToClassNameMap.containsKey(symbolicName)) 
			return uriToClassNameMap.get(symbolicName);
		return symbolicName.replace("http://opcfoundation.org/UA/", "org.opcfoundation.ua.core.");
	}
		
}
