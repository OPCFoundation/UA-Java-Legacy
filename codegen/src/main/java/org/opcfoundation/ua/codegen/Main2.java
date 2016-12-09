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

import static org.opcfoundation.ua.codegen.JavaUtils.fixWrapper;
import static org.opcfoundation.ua.codegen.JavaUtils.getClassName;
import static org.opcfoundation.ua.codegen.JavaUtils.getPackageName;
import static org.opcfoundation.ua.codegen.JavaUtils.isPrimitive;
import static org.opcfoundation.ua.codegen.JavaUtils.isPrimitiveWrapper;
import static org.opcfoundation.ua.codegen.JavaUtils.toFile;
import static org.opcfoundation.ua.codegen.JavaUtils.toFullClassName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.opcfoundation.ua.codegen.DictionaryTypes.TypeDictionary.Constant;
import org.opcfoundation.ua.codegen.DictionaryTypes2.AbstractDictionary.FieldType;
import org.opcfoundation.ua.codegen.DictionaryTypes2.ModelDesign.DataType;
import org.opcfoundation.ua.codegen.EngineeringUnitsUtil.EUnit;
import org.opcfoundation.ua.codegen.IdentifiersUtil.Identifier;
import org.xml.sax.SAXException;

public class Main2 {

	public static final File DEST = new File("../src/main/java");
	
	public static void main(String[] args) 
	throws Throwable {

		// Read Definitions
		DictionaryTypes2.ModelDesign dom = new DictionaryTypes2.ModelDesign();
		dom.readFromNode( DOMUtils.getNode("src/main/resources/codegen_data/data/UA Defined Types.xml", "ModelDesign") );
		dom.readFromNode( DOMUtils.getNode("src/main/resources/codegen_data/data/ext-UA Defined Types.xml", "ModelDesign") );
		
		List<IdentifiersUtil.Identifier> identifiers = new ArrayList<IdentifiersUtil.Identifier>();
		IdentifiersUtil.readIdentifiers(new File("src/main/resources/codegen_data/data/StandardTypes.csv"), identifiers);

		// Read template overrides
		Map<String, Template> overrides = OverrideUtil.getOverrides( OverrideUtil.OVERRIDES_FOLDER );
		
		// Build Enumerations
		buildEnumerations( dom, overrides );
		
		// Build Structures
		buildStructures( dom, overrides );
		
		// Build EncodeableSerializer.java
		buildSerializer( dom );
		
		// Build ChannelService.java 
		buildChannelService( dom );
		
		// Build Identifiers.java
		buildIdentifiers("org.opcfoundation.ua.core.Identifiers", identifiers);
		
		// Build Standard Engineering Units
		List<EUnit> eunits = EngineeringUnitsUtil.readUnits(new File("src/main/resources/codegen_data/data/UNECE_rec20_Rev10e_2014_utf16.txt"));
		buildEngineeringUnits("org.opcfoundation.ua.core.StandardEngineeringUnits", eunits);
		
		// Build *ServiceSetHandler.java
		List<IdentifiersUtil.Identifier> serviceSets = new ArrayList<IdentifiersUtil.Identifier>();
		IdentifiersUtil.readIdentifiers(new File("src/main/resources/codegen_data/data/ServiceSets.csv"), serviceSets);			
		ServiceSetUtil.Build(DEST, serviceSets);		

		// Build StatusCodes.java
		buildStatusCodes();
		
		// Build Attributes.java
		DictionaryTypes.TypeDictionary attributes = new DictionaryTypes.TypeDictionary();
		attributes.readFromNode( DOMUtils.getNode("src/main/resources/codegen_data/data/UA Attributes.xml", "opc:TypeDictionary") );
		attributes.TargetNamespace = "http://opcfoundation.org/UA/Attributes";
		if (attributes.Name.equals("StatusCodes")) attributes.Name = "Attributes";
		buildAttributes(attributes);		
	}
	
	public static void buildEngineeringUnits(String fullClassName, List<EUnit> eunits) throws Exception{
		Template euTemplate = Template.load("src/main/resources/codegen_data/templates/StandardEngineeringUnitsTemplate.java");
		String className = JavaUtils.getClassName(fullClassName);
		
		File file = JavaUtils.toFile(DEST, fullClassName);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> content = new ArrayList<String>();
		List<String> imports = new ArrayList<String>();
		
		map.put(Template.KEY_PACKAGE_NAME, getPackageName(fullClassName));
		map.put(Template.KEY_CLASSNAME, className);
		map.put(Template.KEY_CONTENT, content);
		map.put(Template.KEY_IMPORTS, imports);
		
		//TODO maybe sort based on some logic
		for(EUnit unit : eunits){
			content.add("public static final EUInformation "+unit.javaName+" = init("
					+"\""+unit.commonCode+"\", "
					+unit.id
					+", \""+unit.displayName+ "\", \""+ unit.description+"\");");
		}
		
		euTemplate.buildToFile(map, file);
	}
		
	public static void buildIdentifiers(
			String fullClassName,
			List<IdentifiersUtil.Identifier> identifiers)			
	throws IOException
	{
		Template identifiersTemplate = Template.load("src/main/resources/codegen_data/templates/IdentifiersTemplate.java");		
		String className = getClassName(fullClassName);
		File file = toFile(DEST, fullClassName);
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> content = new ArrayList<String>();
		List<String> imports = new ArrayList<String>();
		
		map.put(Template.KEY_PACKAGE_NAME, getPackageName(fullClassName));
		map.put(Template.KEY_CLASSNAME, className);
		map.put(Template.KEY_CONTENT, content);
		map.put(Template.KEY_IMPORTS, imports);		
		
		// Sort by type
		TreeMap<String, List<IdentifiersUtil.Identifier>> sorted = new TreeMap<String, List<IdentifiersUtil.Identifier>>();
		for (IdentifiersUtil.Identifier id : identifiers)
		{
			List<IdentifiersUtil.Identifier> typeList = sorted.get(id.type);
			if (typeList==null) {
				typeList = new ArrayList<IdentifiersUtil.Identifier>();
				sorted.put(id.type, typeList);
			}
			typeList.add(id);
		}
		
		// sort lists, sort by name		
		for (List<IdentifiersUtil.Identifier> list : sorted.values())		
			Collections.sort(list, IdentifiersUtil.NAME_COMPARATOR);
		
		
		for (String type : sorted.keySet())
		{
			content.add("// "+type);
			for (IdentifiersUtil.Identifier id : sorted.get(type))
			{
				content.add("public static final NodeId "+id.name+" = init("+id.id+");");
			}
			content.add("");
		}
		
		identifiersTemplate.buildToFile(map, file);		
	}	
	
	public static void buildChannelService(DictionaryTypes2.ModelDesign dom ) 
	throws IOException
	{
		Collection<DictionaryTypes2.ModelDesign.DataType[]> services = pickServices( dom );
		Template channelServiceTemplate = Template.load("src/main/resources/codegen_data/templates/ChannelServiceTemplate.java"); 
		String fullClassName = "org.opcfoundation.ua.transport.ChannelService";
		String className = getClassName(fullClassName);
		File file = toFile(DEST, fullClassName);
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> content = new ArrayList<String>();
		List<String> imports = new ArrayList<String>();
		
		map.put(Template.KEY_PACKAGE_NAME, getPackageName(fullClassName));
		map.put(Template.KEY_CLASSNAME, className);
		map.put(Template.KEY_CONTENT, content);
		map.put(Template.KEY_IMPORTS, imports);		

		// Imports
		for (DictionaryTypes2.ModelDesign.DataType[] service : services)
		{
			DictionaryTypes2.ModelDesign.DataType request = service[0];
			DictionaryTypes2.ModelDesign.DataType response = service[1];
			
			imports.add( toFullClassName( request.SymbolicName )  );			
			imports.add( toFullClassName( response.SymbolicName )  );			
		}
		imports.add("org.opcfoundation.ua.common.ServiceResultException");
		imports.add("org.opcfoundation.ua.common.ServiceFaultException");
		imports.add("org.opcfoundation.ua.transport.ChannelService");
		imports.add("org.opcfoundation.ua.transport.AsyncResult");
//		imports.add("org.opcfoundation.ua.transport.SecureChannel");
		
		for (DictionaryTypes2.ModelDesign.DataType[] service : services)
		{
			DictionaryTypes2.ModelDesign.DataType request = service[0];
			DictionaryTypes2.ModelDesign.DataType response = service[1];
			
			List<String[]> fields = getAllFields(dom, request, imports, true);
			
			String serviceName = request.Name.substring( 0, request.Name.length()-"Request".length() );
			String requestName = request.Name;
			String responseName = response.Name;

			// Convenient
			String args1 = "";
			String args2 = "";
			for (String[] field : fields)
			{
				boolean lastField = field == fields.get(fields.size()-1);
				if (!args1.isEmpty()) args1 += ", ";
				if (!args2.isEmpty()) args2 += ", ";
				
				String type = field[1];
				String name = field[0];
				
				if (lastField && type.endsWith("[]") && !type.equals("byte[]")) type = type.substring(0, type.length()-2)+"...";
				
				args1 += type + " " + name;
				args2 += name;
			}
			
			content.add("/**");
			content.add(" * Synchronous "+serviceName+" service request. ");
			content.add(" * ");
			for (String[] field : fields)
			{
				content.add(" * @param "+field[0]);				
			}
			content.add(" * @return the response");
			content.add(" * @throws ServiceFaultException on error while executing the operation");
			content.add(" * @throws ServiceResultException on communication error");
			content.add(" */");
			content.add("public "+responseName+" "+serviceName+"("+args1+")");
			content.add("throws ServiceFaultException, ServiceResultException {");		
			content.add("\t"+requestName+" req = new "+requestName+"("+args2+");");
			content.add("\treturn ("+responseName+") channel.serviceRequest( req );");
			content.add("}");			
			content.add("");
			
			// Sync calls
			content.add("/**");
			content.add(" * Synchronous "+serviceName+" service request. ");
			content.add(" * ");
			content.add(" * @param req the request");				
			content.add(" * @return the response");
			content.add(" * @throws ServiceFaultException on error while executing the operation");
			content.add(" * @throws ServiceResultException on communication error");
			content.add(" */");
			content.add("public "+responseName+" "+serviceName+"("+requestName+" req)");
			content.add("throws ServiceFaultException, ServiceResultException {");		
			content.add("\treturn ("+responseName+") channel.serviceRequest( req );");
			content.add("}");			
			content.add("");
			
			// Async calls
			content.add("/**");
			content.add(" * Asynchronous "+serviceName+" service request. ");
			content.add(" * ");
			for (String[] field : fields)
			{
				content.add(" * @param "+field[0]);				
			}
			content.add(" * @return monitorable asyncronous result object");
			content.add(" * @throws ServiceFaultException on error while executing the operation");
			content.add(" * @throws ServiceResultException on communication error");
			content.add(" */");
			content.add("public AsyncResult "+serviceName+"Async("+args1+")");
			content.add("{");		
			content.add("\t"+requestName+" req = new "+requestName+"("+args2+");");
			content.add("\treturn channel.serviceRequestAsync( req );");
			content.add("}");
			content.add("");
						
			content.add("/**");
			content.add(" * Asynchronous "+serviceName+" service request. ");
			content.add(" * ");
			content.add(" * @param req the request");				
			content.add(" * @param listener listener that receives either an error or the result");				
			content.add(" * @return monitorable asyncronous result object");
			content.add(" * @throws ServiceResultException on communication error");
			content.add(" */");			content.add("public AsyncResult "+serviceName+"Async("+requestName+" req)");
			content.add("{");		
			content.add("\treturn channel.serviceRequestAsync( req );");
			content.add("}");
			content.add("");

			
		}
		
		channelServiceTemplate.buildToFile(map, file);				
	}	
	
	/**
	 * 
	 * @param dom
	 * @param dt
	 * @param imports required imports
	 * @param includeInherited include fields defined in super types
	 * @return ordered list of fields (Name, Type) of dt 
	 */
	static List<String[]> getAllFields(DictionaryTypes2.ModelDesign dom, DictionaryTypes2.ModelDesign.DataType dt, Collection<String> imports, boolean includeInherited)
	{
		Collection<FieldType> fieldTypes = includeInherited ? dt.getAllFields() : dt.getFields();
		ArrayList<String[]> result = new ArrayList<String[]>();
		for (FieldType f : fieldTypes)
		{
			DictionaryTypes2.ModelDesign.DataType ft = dom.getDataType( f.getDataType() );
			ft = fixWrapper(ft);
			boolean isArray = f.ValueRank!=null;
			String dataTypeURL = ft.SymbolicName;
			if (dataTypeURL.equals("http://opcfoundation.org/UA/BaseDataType"))
				dataTypeURL = "http://opcfoundation.org/UA/Variant";
			String dataTypeFullClassName = toFullClassName(dataTypeURL);
			String dataTypeClassName = getClassName(dataTypeFullClassName);
			if (isArray) dataTypeClassName += "[]";
			if (dataTypeFullClassName.contains("."))
				imports.add(dataTypeFullClassName);
			
			// a local/super field
			String name = f.Name;
			String type = dataTypeClassName;
			result.add( new String[] {name, type} );
		}			
		return result;
	}
	
	static Collection<DictionaryTypes2.ModelDesign.DataType[]> pickServices(DictionaryTypes2.ModelDesign dom) 
	{
		List<DictionaryTypes2.ModelDesign.DataType[]> result = new ArrayList<DictionaryTypes2.ModelDesign.DataType[]>();
		for (DictionaryTypes2.ModelDesign.DataType dt : dom.dataTypes)
		{
			if (dt.getField("RequestHeader")==null) continue;
			
			DictionaryTypes2.ModelDesign.DataType request = dt;
			DictionaryTypes2.ModelDesign.DataType response = dom.getDataType(request.SymbolicName.replace("Request", "Response"));
			
			DictionaryTypes2.ModelDesign.DataType[] service = new DictionaryTypes2.ModelDesign.DataType[] {request, response};
			
			result.add( service );
		}
		return result;
	}	

	

	public static void buildEnumeration(DictionaryTypes2.ModelDesign.DataType enumi, Map<String, Template> overrides) 
	throws IOException 
	{
		String url = enumi.SymbolicName;
		String fullClassName = toFullClassName(url);
		String className = getClassName(fullClassName);
		File file = toFile(DEST, fullClassName);
		
		boolean isPacked = true; // if false constructs with explicit value 
		int workingCount = 0;
		List<FieldType> list = enumi.getAllFields();
		for (int i=0; i<list.size(); i++) {
			Integer v = list.get(i).Identifier;
			if (v!=null) {
				workingCount++;
				isPacked&=v==i;
			}
		}
		
		Template packedEnumTemplate = Template.load("src/main/resources/codegen_data/templates/PackedEnumTemplate.java");
		Template sparseEnumTemplate = Template.load("src/main/resources/codegen_data/templates/SparseEnumTemplate.java");
		Template t = isPacked ? packedEnumTemplate : sparseEnumTemplate; 		
		if (overrides.containsKey(file.getName())) t = overrides.get(file.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		
		String firstItem = null;
		List<String> content = new ArrayList<String>();
		for (int i=0; i<list.size(); i++)
		{
			FieldType item = list.get(i);
			String line = item.Name;			
			if (!isPacked) line += "("+item.Identifier+")";
			line += i<workingCount-1 ? "," : ";";
			
			if (item.Identifier==null) {
				line = "//"+line;
				System.err.println("WARNING: Could not produce "+fullClassName+"."+item.Name);
			} else if (firstItem==null) firstItem = item.Name;
			
			content.add(line);
		}
		
		map.put(Template.KEY_PACKAGE_NAME, getPackageName(fullClassName));
		map.put(Template.KEY_CLASSNAME, className);
		map.put(Template.KEY_URL, url);
		map.put(Template.KEY_CONTENT, content);
		
		t.buildToFile(map, file);
	}
	
	static void buildEnumerations(DictionaryTypes2.ModelDesign dom, Map<String, Template> overrides)
	throws IOException 
	{
		for (DictionaryTypes2.ModelDesign.DataType dt : dom.dataTypes)			
			if (isEnumeration(dt))
				buildEnumeration(dt, overrides);
	}
	
	static boolean isEnumeration(DictionaryTypes2.ModelDesign.DataType dt)
	{		
		DictionaryTypes2.ModelDesign.DataType superType = dt.getSuperType();
		if (superType==null) return false;
		if (superType.SymbolicName==null) return false;
		return superType.SymbolicName.equals("http://opcfoundation.org/UA/Enumeration");
	}

	public static void buildStructure(DictionaryTypes2.ModelDesign dom, DictionaryTypes2.ModelDesign.DataType dt, Map<String, Template> overrides, Collection<DataType> structures) 
	throws IOException 
	{
		boolean isRequest = dt.getField("RequestHeader") != null;
		boolean isResponse = dt.getField("ResponseHeader") != null;
		
		Template template;
		if (isRequest) template = Template.load("src/main/resources/codegen_data/templates/RequestTemplate.java"); else
		if (isResponse) template = Template.load("src/main/resources/codegen_data/templates/ResponseTemplate.java"); else
			template = Template.load("src/main/resources/codegen_data/templates/ComplexTypeTemplate.java");
			
		Collection<FieldType> allFieldTypes = dt.getAllFields();
		Collection<FieldType> fieldTypes = dt.getFields();
		
		String url = dt.SymbolicName;
		String fullClassName = toFullClassName(url);
		String className = getClassName(fullClassName);
		
		File file = toFile(DEST, fullClassName);
		
		if (overrides.containsKey(file.getName())) 
			template = overrides.get(file.getName());
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> content = new ArrayList<String>();
		List<String> imports = new ArrayList<String>();
		
		map.put(Template.KEY_PACKAGE_NAME, getPackageName(fullClassName));
		map.put(Template.KEY_CLASSNAME, className);
		map.put(Template.KEY_URL, url);
		map.put(Template.KEY_CONTENT, content);
		map.put(Template.KEY_IMPORTS, imports);
				
		DictionaryTypes2.ModelDesign.DataType superType = dt.getSuperType();
		superType = fixWrapper(superType);
		String superTypeClassName = toFullClassName(superType.SymbolicName);
		if (superTypeClassName.equals("org.opcfoundation.ua.builtintypes.ExtensionObject") |
			superTypeClassName.equals("org.opcfoundation.ua.builtintypes.Structure") | 
			superTypeClassName.equals("org.opcfoundation.ua.builtintypes.IEncodeable")) { 
			superTypeClassName = "org.opcfoundation.ua.utils.AbstractStructure";
			imports.add(superTypeClassName);
		} else {
			imports.add(superTypeClassName);
		}
		map.put(Template.KEY_SUPERTYPE, getClassName(superTypeClassName));
				
		// Local Fields - non inherited
		List<String> allNames = new ArrayList<String>();
		List<String> allTypes = new ArrayList<String>();
		List<String> types = new ArrayList<String>();
		List<String> names = new ArrayList<String>();		
		List<DataType> datatypes = new ArrayList<DataType>();
		
		// Super&Local fields
		for (FieldType f : allFieldTypes)
		{
			DictionaryTypes2.ModelDesign.DataType ft = dom.getDataType( f.getDataType() );
			ft = fixWrapper(ft);
			boolean isArray = f.ValueRank!=null;
			String dataTypeURL = ft.SymbolicName;
			if (dataTypeURL.equals("http://opcfoundation.org/UA/BaseDataType"))
				dataTypeURL = "http://opcfoundation.org/UA/Variant";
			String dataTypeFullClassName = toFullClassName(dataTypeURL);
			String dataTypeClassName = getClassName(dataTypeFullClassName);
			if (isArray) dataTypeClassName += "[]";
			if (dataTypeFullClassName.contains("."))
				imports.add(dataTypeFullClassName);
			
			// a local/super field
			allNames.add(f.Name);
			allTypes.add(dataTypeClassName);
			
			// Local field (not super)
			if (fieldTypes.contains(f)) {
				names.add(f.Name);
				types.add(dataTypeClassName);
				datatypes.add(ft);
				content.add("protected " + dataTypeClassName+" "+f.Name+";");			
			}
		}
		
		content.add("");
		
		// Add Constructors
		content.add("public "+className+"() {}");
		content.add("");
		StringBuilder argsStr = new StringBuilder();
		StringBuilder superStr = new StringBuilder();
		for (int i=0; i<allNames.size(); i++)
		{
			String name = allNames.get(i);
			String type = allTypes.get(i);
			if (argsStr.length()>0) argsStr.append(", ");
			argsStr.append(type+" "+name);
			if (!names.contains(name)) {
				if (superStr.length()>0) superStr.append(", ");
				superStr.append(name);
			}
		}
		if (argsStr.length()>0) {
			content.add("public "+className+"("+argsStr+")");
			content.add("{");
			if (superStr.length()>0)
				content.add("    super("+superStr+");");
			for (int i=0; i<names.size(); i++)
			{
				String name = names.get(i);
				content.add("    this."+name+" = "+name+";");
			}
			content.add("}");
		}
		content.add("");
		
		// Add GettersSetters
		for (int i=0; i<names.size(); i++)
		{
			String name = names.get(i);
			String type = types.get(i);
			content.add("public "+type+" get"+name+"()");
			content.add("{");
			content.add("    return "+name+";");
			content.add("}");
			content.add("");
			content.add("public void set"+name+"("+type+" "+name+")");
			content.add("{");
			content.add("    this."+name+" = "+name+";");
			content.add("}");
			content.add("");
		}
		
		// Put Clone
		content.add("/**");
		content.add("  * Deep clone");
		content.add("  *");
		content.add("  * @return cloned "+className);
		content.add("  */");
		content.add("public "+className+" clone()");
		content.add("{");
		content.add("    "+className+" result = ("+className+") super.clone();");
		for (FieldType f : allFieldTypes)
		{
			DictionaryTypes2.ModelDesign.DataType ft = dom.getDataType( f.getDataType() );
			ft = fixWrapper(ft);
			boolean isArray = f.ValueRank!=null;
			boolean isStructure = structures.contains(ft);
			
			String dataTypeURL = ft.SymbolicName;
			if (dataTypeURL.equals("http://opcfoundation.org/UA/BaseDataType"))
				dataTypeURL = "http://opcfoundation.org/UA/Variant";
			String dataTypeFullClassName = toFullClassName(dataTypeURL);
			String dataTypeClassName = getClassName(dataTypeFullClassName);
			
//			String type = isArray ? dataTypeClassName+"[]" : dataTypeClassName; 
			String name = f.Name;
						
			if (isArray) {
				if (isStructure) {
					content.add("    if ("+name+"!=null) {");
					content.add("        result."+name+" = new "+dataTypeClassName+"["+name+".length];");
					content.add("        for (int i=0; i<"+name+".length; i++)");
					content.add("            result."+name+"[i] = "+name+"[i].clone();");
					content.add("    }");
//					content.add("    result."+name+" = ("+type+") MultiDimensionArrayUtils.deepClone("+name+");");
//					imports.add("org.opcfoundation.ua.utils.MultiDimensionArrayUtils");
				} else {
					content.add("    result."+name+" = "+name+"==null ? null : "+name+".clone();");
				}
			} else {
				if (isStructure) {
					content.add("    result."+name+" = "+name+"==null ? null : "+name+".clone();");
				} else {
					content.add("    result."+name+" = "+name+";");
				}
			}
		}
		content.add("    return result;");
		content.add("}");
		content.add("");
		
		// Add equals
		content.add("@Override");
		content.add("public boolean equals(Object obj)");
		content.add("{");
		content.add("    if (this == obj) return true;");
		content.add("    if (obj == null) return false;");
		content.add("    if (getClass() != obj.getClass()) return false;");
		content.add("    " + className + " other = (" + className+ ") obj;");
		for (FieldType f : allFieldTypes)
		{
			DictionaryTypes2.ModelDesign.DataType ft = dom.getDataType( f.getDataType() );
			ft = fixWrapper(ft);
			boolean isArray = f.ValueRank!=null;
			
//			String dataTypeURL = ft.SymbolicName;
//			if (dataTypeURL.equals("http://opcfoundation.org/UA/BaseDataType"))
//				dataTypeURL = "http://opcfoundation.org/UA/Variant";
//			String dataTypeFullClassName = toFullClassName(dataTypeURL);
//			String dataTypeClassName = getClassName(dataTypeFullClassName);
			
//			String type = isArray ? dataTypeClassName+"[]" : dataTypeClassName; 
			String name = f.Name;
						
			if (isArray) {
				content.add("    if ("+name+"==null) {");
				content.add("        if (other."+name+" != null) return false;");
				content.add("    } else if (!Arrays.equals("+name+", other."+name+")) return false;");
				if (!imports.contains("java.util.Arrays"))
					imports.add("java.util.Arrays");
			} else {
				content.add("    if ("+name+"==null) {");
				content.add("        if (other."+name+" != null) return false;");
				content.add("    } else if (!"+name+".equals(other."+name+")) return false;");
			}
		}
		content.add("    return true;");
		content.add("}");
		content.add("");
	
		// Add hashCode
		content.add("@Override");
		content.add("public int hashCode()");
		content.add("{");
		content.add("    final int prime = 31;");
		content.add("    int result = 1;");
		for (FieldType f : allFieldTypes)
		{
			DictionaryTypes2.ModelDesign.DataType ft = dom.getDataType( f.getDataType() );
			ft = fixWrapper(ft);
			boolean isArray = f.ValueRank!=null;
			
//			String dataTypeURL = ft.SymbolicName;
//			if (dataTypeURL.equals("http://opcfoundation.org/UA/BaseDataType"))
//				dataTypeURL = "http://opcfoundation.org/UA/Variant";
//			String dataTypeFullClassName = toFullClassName(dataTypeURL);
//			String dataTypeClassName = getClassName(dataTypeFullClassName);
			
//			String type = isArray ? dataTypeClassName+"[]" : dataTypeClassName; 
			String name = f.Name;
						
			if (isArray) {
				content.add("    result = prime * result");
				content.add("            + (("+name+" == null) ? 0 : Arrays.hashCode("+name+"));");
			} else {
				content.add("    result = prime * result");
				content.add("            + (("+name+" == null) ? 0 : "+name+".hashCode());");
			}
		}
		content.add("    return result;");
		content.add("}");
		content.add("");
			
		// Already in template
		while (imports.remove("org.opcfoundation.ua.builtintypes.ExpandedNodeId")) {}
		template.buildToFile(map, file);		
	}
	
	static void buildStructures(DictionaryTypes2.ModelDesign dom, Map<String, Template> overrides) 
	throws IOException 
	{
		Collection<DataType> structures = pickStructures(dom);
		for (DictionaryTypes2.ModelDesign.DataType dt : structures) 
			buildStructure(dom, dt, overrides, structures);
	}
	
	static Collection<DictionaryTypes2.ModelDesign.DataType> pickStructures(DictionaryTypes2.ModelDesign dom) 
	{
		Collection<DictionaryTypes2.ModelDesign.DataType> result = new ArrayList<DictionaryTypes2.ModelDesign.DataType>();
		DictionaryTypes2.ModelDesign.DataType enumeration = dom.getDataType("http://opcfoundation.org/UA/Enumeration");
		for (DictionaryTypes2.ModelDesign.DataType dt : dom.dataTypes) {
			String className = toFullClassName( dt.SymbolicName );
			if (!className.startsWith("org.opcfoundation.ua.core")) continue;
			if (isPrimitive(dt)) continue;
			if (isPrimitiveWrapper(dt)) continue;
			if (dt.getSuperType()==enumeration) continue;
			result.add(dt);
		}		
		return result;
	}	
	
	public static void buildStatusCodes()
	throws IOException, SAXException
	{
		DictionaryTypes.TypeDictionary statusCodes = new DictionaryTypes.TypeDictionary();
		statusCodes.readFromNode( DOMUtils.getNode("src/main/resources/codegen_data/data/UA Status Codes.xml", "opc:TypeDictionary") );
		
		List<IdentifiersUtil.Identifier> identifiers = new ArrayList<IdentifiersUtil.Identifier>();
		IdentifiersUtil.readIdentifiers(new File("src/main/resources/codegen_data/data/UA Status Codes.csv"), identifiers);
		
		Template javaTemplate = Template.load("src/main/resources/codegen_data/templates/Template.java");
		String url = "http://opcfoundation.org/UA/StatusCodes";
		String fullClassName = toFullClassName(url);
		String className = getClassName(fullClassName);
		File file = toFile(DEST, fullClassName);
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> content = new ArrayList<String>();
		List<String> imports = new ArrayList<String>();
		map.put(Template.KEY_PACKAGE_NAME, getPackageName(fullClassName));
		map.put(Template.KEY_CLASSNAME, className);
		map.put(Template.KEY_URL, url);
		map.put(Template.KEY_CONTENT, content);
		map.put(Template.KEY_IMPORTS, imports);		

		imports.add("org.opcfoundation.ua.builtintypes.UnsignedInteger");
		imports.add("org.opcfoundation.ua.utils.Description");
				
		for (Identifier id : identifiers)
		{
			String name = id.name;
			long value = ((long)id.id)<<16;
			if (name.startsWith("Bad")) value |= 0x80000000L;
			if (name.startsWith("Uncertain")) value |= 0x40000000L;
			Constant document = statusCodes.getConstantByName(name);
			
			if (document!=null) 
				content.add("@Description(\""+document.Documentation+"\")");			
			content.add("public final static UnsignedInteger "+name+" = new UnsignedInteger("+String.format("0x%08X", value)+"L);");
			content.add("");
		}
		
		javaTemplate.buildToFile(map, file);
	}
	
	public static void buildAttributes(DictionaryTypes.TypeDictionary dt) throws IOException
	{
		Template javaTemplate = Template.load("src/main/resources/codegen_data/templates/Template.java");
		String url = dt.TargetNamespace;
		String fullClassName = toFullClassName(url);
		String className = getClassName(fullClassName);
		File file = toFile(DEST, fullClassName);
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> content = new ArrayList<String>();
		Set<String> imports = new HashSet<String>();
		map.put(Template.KEY_PACKAGE_NAME, getPackageName(fullClassName));
		map.put(Template.KEY_CLASSNAME, className);
		map.put(Template.KEY_URL, url);
		map.put(Template.KEY_CONTENT, content);
		map.put(Template.KEY_IMPORTS, imports);	

		boolean hasDescriptions = false;
		
		int index = 1;
		for (DictionaryTypes.TypeDictionary.Constant c : dt.constants)
		{				
			if (c.Documentation!=null) {				
				hasDescriptions |= true;
				content.add("@Description(\""+c.Documentation+"\")");
			}
			if (c.Value==null) {
				imports.add("org.opcfoundation.ua.builtintypes.UnsignedInteger");
				content.add("public final static UnsignedInteger "+c.Name+" = UnsignedInteger.valueOf("+index+");");
			} else {
				content.add("public final static String "+c.Name+" = \""+c.Value+"\";");
			}
			content.add("");
			index++;
		}
		
		if (hasDescriptions)
			imports.add("org.opcfoundation.ua.utils.Description");
		
		javaTemplate.buildToFile(map, file);
	}

	public static void buildSerializer(DictionaryTypes2.ModelDesign dom)
	throws IOException
	{
		Template serializerTemplate = Template.load("src/main/resources/codegen_data/templates/SerializerTemplate.java");
		String fullClassName = "org.opcfoundation.ua.core.EncodeableSerializer";
		String className = getClassName(fullClassName);
		File file = toFile(DEST, fullClassName);
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> content = new ArrayList<String>();
		List<String> imports = new ArrayList<String>();
		
		
		map.put(Template.KEY_PACKAGE_NAME, getPackageName(fullClassName));
		map.put(Template.KEY_CLASSNAME, className);
		map.put(Template.KEY_CONTENT, content);
		map.put(Template.KEY_IMPORTS, imports);		

		for (DictionaryTypes2.ModelDesign.DataType t : pickStructures(dom))
		{
			content.add("\t// "+t.Name);
			content.add("\taddSerializer(");
			content.add("\t\tnew AbstractSerializer("+t.Name+".class, "+t.Name+".BINARY, "+t.Name+".XML, "+t.Name+".ID) {");

			// Calculator 
			content.add("\t\t\tpublic void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {");
			if (!t.getAllFields().isEmpty())
				content.add("\t\t\t\t"+t.Name+" obj = ("+t.Name+") encodeable;");			
			for (FieldType f : t.getAllFields()) {	
				DictionaryTypes2.ModelDesign.DataType fieldType = dom.getDataType( f.getDataType() ); 
				fieldType = fixWrapper(fieldType);
				boolean isArray = f.ValueRank!=null;
				boolean isEnum = isEnumeration(fieldType);
				
				String fieldTypeFullClassName = toFullClassName(fieldType.SymbolicName);
//				imports.add(fieldTypeFullClassName);
				String dataTypeClassName = getClassName(fieldTypeFullClassName);
				Integer builtinsId = BuiltinsMap.ID_MAP.get(fieldType.SymbolicName);
				String builtinsName = BuiltinsMap.NAME_MAP.get(fieldType.SymbolicName);

				if (builtinsId!=null) {
					if (isArray)
//						content.add("\t\t\t\tcalculator.put"+builtinsName+"Array(null, ("+dataTypeClassName+"[]) ((obj==null)?null:obj.get"+f.Name+"()) );");
						content.add("\t\t\t\tcalculator.put"+builtinsName+"Array(null, ((obj==null)?null:obj.get"+f.Name+"()) );");
					else {
						if (builtinsId<=11)
							content.add("\t\t\t\tcalculator.put"+builtinsName+"(null, null /*obj.get"+f.Name+"()*/);");
						else
							content.add("\t\t\t\tcalculator.put"+builtinsName+"(null,  (obj==null)?null:obj.get"+f.Name+"() );");
					}
				} else if (isEnum) {
					if (isArray)
						content.add("\t\t\t\tcalculator.putEnumerationArray(null,  (obj==null)?null:obj.get"+f.Name+"() );");
					else
						content.add("\t\t\t\tcalculator.putEnumeration(null, null /*obj.get"+f.Name+"()*/);");
				} else {						
					if (isArray)
						content.add("\t\t\t\tcalculator.putEncodeableArray(null, "+dataTypeClassName+".class, (obj==null)?null:obj.get"+f.Name+"());");
					else
						content.add("\t\t\t\tcalculator.putEncodeable(null, "+dataTypeClassName+".class, (obj==null)?null:obj.get"+f.Name+"());");
				}
			}
			content.add("\t\t\t}");

			
			// Write
			content.add("\t\t\tpublic void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {");
			if (!t.getAllFields().isEmpty())
				content.add("\t\t\t\t"+t.Name+" obj = ("+t.Name+") encodeable;");
			for (FieldType f : t.getAllFields()) {				
				DictionaryTypes2.ModelDesign.DataType fieldType = dom.getDataType( f.getDataType() ); 
				String fieldTypeName = fieldType.SymbolicName;
//				if (fieldTypeName.equals("http://opcfoundation.org/UA/ExtensionObject")) 
//					fieldTypeName = "http://opcfoundation.org/UA/Structure";
				fieldType = fixWrapper(fieldType);
				boolean isArray = f.ValueRank!=null;
				boolean isEnum = isEnumeration(fieldType);
				
				String fieldTypeFullClassName = toFullClassName(fieldTypeName);
//				imports.add(fieldTypeFullClassName);
				String dataTypeClassName = getClassName(fieldTypeFullClassName);
				Integer builtinsId = BuiltinsMap.ID_MAP.get(fieldType.SymbolicName);
				String builtinsName = BuiltinsMap.NAME_MAP.get(fieldType.SymbolicName);
				String fieldName = f.Name;

				if (builtinsId!=null) {
					if (isArray)
						content.add("\t\t\t\tencoder.put"+builtinsName+"Array(\""+fieldName+"\", (obj==null)?null:obj.get"+f.Name+"() );");
					else
						content.add("\t\t\t\tencoder.put"+builtinsName+"(\""+fieldName+"\",  (obj==null)?null:obj.get"+f.Name+"() );");						
				} else if (isEnum) {
					if (isArray)
						content.add("\t\t\t\tencoder.putEnumerationArray(\""+fieldName+"\",  (obj==null)?null:obj.get"+f.Name+"() );");
					else
						content.add("\t\t\t\tencoder.putEnumeration(\""+fieldName+"\",  (obj==null)?null:obj.get"+f.Name+"() );");
				} else {					
					if (isArray)
						content.add("\t\t\t\tencoder.putEncodeableArray(\""+fieldName+"\", "+dataTypeClassName+".class, (obj==null)?null:obj.get"+f.Name+"());");
					else
						content.add("\t\t\t\tencoder.putEncodeable(\""+fieldName+"\", "+dataTypeClassName+".class, (obj==null)?null:obj.get"+f.Name+"());");
				}
			}
			content.add("\t\t\t}");
			
			// Read
			content.add("\t\t\tpublic IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {");
			content.add("\t\t\t\t"+t.Name+" result = new "+t.Name+"();");
			for (FieldType f : t.getAllFields()) {				
				DictionaryTypes2.ModelDesign.DataType fieldType = dom.getDataType( f.getDataType() ); 
				fieldType = fixWrapper(fieldType);
				boolean isArray = f.ValueRank!=null;
				boolean isEnum = isEnumeration(fieldType);
				
				String fieldTypeFullClassName = toFullClassName(fieldType.SymbolicName);
//				imports.add(fieldTypeFullClassName);
				String dataTypeClassName = getClassName(fieldTypeFullClassName);
				Integer builtinsId = BuiltinsMap.ID_MAP.get(fieldType.SymbolicName);
				String builtinsName = BuiltinsMap.NAME_MAP.get(fieldType.SymbolicName);
				String fieldName = f.Name;

				if (builtinsId!=null) {
					if (isArray)
						content.add("\t\t\t\tresult.set"+f.Name+"( decoder.get"+builtinsName+"Array(\""+fieldName+"\") );");
					else
						content.add("\t\t\t\tresult.set"+f.Name+"( decoder.get"+builtinsName+"(\""+fieldName+"\") );");						
				} else if (isEnum) {
					if (isArray)
						content.add("\t\t\t\tresult.set"+f.Name+"( decoder.getEnumerationArray(\""+fieldName+"\", "+dataTypeClassName+".class) );");
					else
						content.add("\t\t\t\tresult.set"+f.Name+"( decoder.getEnumeration(\""+fieldName+"\", "+dataTypeClassName+".class) );");
				} else {					
					if (isArray)
						content.add("\t\t\t\tresult.set"+f.Name+"( decoder.getEncodeableArray(\""+fieldName+"\", "+dataTypeClassName+".class) );");
					else
						content.add("\t\t\t\tresult.set"+f.Name+"( decoder.getEncodeable(\""+fieldName+"\", "+dataTypeClassName+".class) );");
				}
			}
			if (t.Name.equals("ResponseHeader"))
				content.add("\t\t\t\tDecoderUtils.fixResponseHeader(result);");
			content.add("\t\t\t\treturn result;");
			content.add("\t\t\t}");
			
			content.add("\t\t});");
			content.add("");
		}
		
		/*
		addSerializer( 
		    new AbstractSerializer(CreateMonitoredItemsRequest.class, CreateMonitoredItemsRequest.ID) {
		    	public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
		    		
		    	}
		    	public IEncodeable getEncodeable(IDecoder decoder)
		    	throws DecodingException, IOException {
		    		return null;
		    	}
		    	public void putEncodeable(IEncodeable encodeable, IEncoder encoder)
		    	throws EncodingException, IOException {
		    		
		    	}
		    }
		);		
 */
		
		serializerTemplate.buildToFile(map, file);		
	}
	
	
}
