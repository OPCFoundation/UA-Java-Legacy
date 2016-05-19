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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * 
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class DictionaryTypes {
	
	public static class AbstractDictionary {
		public String Name;
		public String TargetNamespace;
		public Map<String, String> nameSpaceMap = new HashMap<String, String>();
		
		public abstract class AbstractType {
			public String Name;
			public QName Id;

			public void readFromNode(Node n) {
				// Read either Name or SymbolicName
				NamedNodeMap nm = n.getAttributes();
				assert(nm!=null);
				Node nameNode = nm.getNamedItem("Name");
				if (nameNode==null) nameNode = nm.getNamedItem("SymbolicName");
				assert(nameNode!=null);
				Name = nameNode.getNodeValue();
				Name = convertToQName( Name ).getLocalPart();
				
				Node idNode = nm.getNamedItem("SymbolicId");
				if (idNode!=null) {
					String IdStr = idNode.getNodeValue();
					Id = convertToQName( IdStr );
				}				
			}

			public QName getURI() {
				return new QName(AbstractDictionary.this.TargetNamespace, Name);
			}
			
			public String getURIAsString() {
				String ns = AbstractDictionary.this.TargetNamespace;
				if (ns.endsWith("/"))
					return AbstractDictionary.this.TargetNamespace+Name;
				return AbstractDictionary.this.TargetNamespace+"/"+Name;
			}

			@Override
			public int hashCode() {
				return getURI().hashCode();
			}

			@Override
			public boolean equals(Object obj) {
				if (!obj.getClass().equals(this.getClass()))
					return false;
				AbstractType other = (AbstractType) obj;
				return other.getURI().equals(getURI());
			}
			
			@Override
			public String toString() {
				return Name;
			}
		}

		public abstract class BaseType extends AbstractType {
			public Boolean AllowArray = false;
			public Boolean NotInAddressSpace = false;

			public void readFromNode(Node n) {
				super.readFromNode(n);
				NotInAddressSpace = DOMUtils.getBooleanAttribute(n,
						"NotInAddressSpace");
				AllowArray = DOMUtils.getBooleanAttribute(n, "AllowArray");
			}
			
			/** 
			 * Get super type 
			 * @return
			 */
			public String getBaseType() {
				return null;
			}
			
			public List<FieldType> getFields() {
				return null;
			}

			public List<FieldType> getAllFields() {
				return null;
			}

		}		
		
		QName convertToQName(String name)
		{
			if (name==null) return null;
			int separator = name.indexOf(':');
			if (separator >= 0) {
				String namespaceAbbreviation = name.substring(0, separator);
				String localPart = name.substring(separator + 1);
				String nsPart = AbstractDictionary.this.nameSpaceMap.get(namespaceAbbreviation);
				if (nsPart==null)
					System.err.println("Namespace abbreviation "+namespaceAbbreviation+" not found.");
				assert (nsPart != null);
				return new QName(nsPart, localPart);
			} else {
				return new QName(AbstractDictionary.this.TargetNamespace, name);
			}						
		}

		public class FieldType extends AbstractType {
			public QName DataType;
			public String ValueRank;
			public Integer ArrayLength;
			public String SingularName;
			public Boolean IsAttribute;
			public String DefaultValue;
			public Integer Identifier;

			@Override
			public void readFromNode(Node n) {
				super.readFromNode(n);
				DataType = convertToQName( DOMUtils.getStringAttribute(n, "DataType") );			
				Identifier = DOMUtils.getIntegerAttribute(n, "Identifier");
				ValueRank = DOMUtils.getStringAttribute(n, "ValueRank");
				ArrayLength = DOMUtils.getIntegerAttribute(n, "ArrayLength");
				SingularName = DOMUtils.getStringAttribute(n, "SingularName");
				IsAttribute = DOMUtils.getBooleanAttribute(n, "IsAttribute");
				DefaultValue = DOMUtils.getStringAttribute(n, "DefaultValue");
			}
			
			public String getDataType() {
				String ns = DataType.getNamespaceURI();
				if (ns.endsWith("/"))
					return ns+DataType.getLocalPart();
				return ns+"/"+DataType.getLocalPart();				
			}			
		}		
		public class ParameterType extends FieldType {}

		
		public void readFromNode(Node n) {
			TargetNamespace = DOMUtils.getStringAttribute(n, "TargetNamespace");

			String[] chunks = TargetNamespace.split("\\/");
			Name = chunks[chunks.length-1].trim();
			
			// Read name space imports
			NamedNodeMap attribs = n.getAttributes();
			for (int i = 0; i < attribs.getLength(); i++) {
				Node attrib = attribs.item(i);
				String ns = attrib.getNodeName();
				String nv = attrib.getNodeValue();
				if (ns.startsWith("xmlns:")) {
					nameSpaceMap.put(ns.substring(6), nv);
				}
			}			
		}
	}

	public static class TypeDictionary extends AbstractDictionary {

		public List<Constant> constants = new ArrayList<Constant>();
		public List<EnumeratedType> enumerationTypes = new ArrayList<EnumeratedType>();
		public List<TypeDeclaration> typeDeclarations = new ArrayList<TypeDeclaration>();
		public List<ComplexType> complexTypes = new ArrayList<ComplexType>();
		public List<ServiceType> serviceTypes = new ArrayList<ServiceType>();
		public List<BaseType> baseTypes = new ArrayList<BaseType>();

		public ComplexType getComplexType(String uri)
		{
			for (ComplexType ct : complexTypes) 
				if (ct.getURIAsString().equals(uri))
					return ct;
			
			return null;
		}
		
		public Constant getConstantByValue(String value)
		{
			for (Constant c : constants)
			{
				if (c.Value.equals(value))
					return c;
			}
			return null;
		}

		public Constant getConstantByName(String name)
		{
			for (Constant c : constants)
			{
				if (c.Name.equals(name))
					return c;
			}
			return null;
		}
		
		public void addConstant(String name, String value, String documentation)		
		{
			Constant c = new Constant();
			c.Name = name;
			c.Value = value;
			c.Documentation = documentation;
			constants.add(c);
		}

		public class Constant extends BaseType {
			public String Documentation;
			public String Value;

			public void readFromNode(Node n) {
				super.readFromNode(n);
				Documentation = DOMUtils.getChildStringValue(n, "opc:Documentation");
				Value = DOMUtils.getChildStringValue(n, "opc:Value");
			}
			
			@Override
			public String toString() {
				if (Value==null) return super.toString();
				return super.toString()+" = "+Value;
			}

		}

		public class EnumeratedType extends BaseType {
			public List<EnumerationItem> values = new ArrayList<EnumerationItem>();

			public class EnumerationItem extends AbstractType {
				public Integer Value;

				@Override
				public void readFromNode(Node n) {
					super.readFromNode(n);
					Value = DOMUtils.getIntegerAttribute(n, "Value");
				}
				
				@Override
				public String toString() {
					if (Value==null) return super.toString();
					return super.toString()+" = "+Value;
				}				
			}

			@Override
			public void readFromNode(Node n) {
				super.readFromNode(n);
				for (Node ei : DOMUtils.getChildrenByName(n, "opc:Value")) {
					EnumeratedType.EnumerationItem i = new EnumeratedType.EnumerationItem();
					i.readFromNode(ei);
					values.add(i);
				}
			}
			
			
		}

		public class TypeDeclaration extends BaseType {
			public QName SourceType;
			public String Documentation;

			public String getSourceType() {
				if (SourceType==null) return null;
				String ns = SourceType.getNamespaceURI();
				if (ns.endsWith("/"))
					return ns+SourceType.getLocalPart();
				return ns+"/"+SourceType.getLocalPart();				
			}
			
			@Override
			public void readFromNode(Node n) {				
				super.readFromNode(n);
				SourceType = convertToQName( DOMUtils.getStringAttribute(n, "SourceType") );
				Documentation = DOMUtils.getChildStringValue(n, "opc:Documentation");
			}
		}

		public class ComplexType extends BaseType {
			public QName BaseType;
			public List<FieldType> fields = new ArrayList<FieldType>();

			@Override
			public String getBaseType() {
				if (BaseType==null) return null;
				String ns = BaseType.getNamespaceURI();
				if (ns.endsWith("/"))
					return ns+BaseType.getLocalPart();
				return ns+"/"+BaseType.getLocalPart();				
			}
			
			public ComplexType getSuperType() {
				return getComplexType( getBaseType() );
			}
			
			@Override
			public void readFromNode(Node n) {
				super.readFromNode(n);
				BaseType = DOMUtils.getQNameAttribute(n, TypeDictionary.this.TargetNamespace, "BaseType");
				for (Node f : DOMUtils.getChildrenByName(n, "opc:Field")) {
					FieldType ft = new FieldType();
					ft.readFromNode(f);
					fields.add(ft);
				}
			}
			
			@Override
			public List<FieldType> getFields() {
				return fields;
			}

			@Override
			public List<FieldType> getAllFields() {
				List<FieldType> allFields = new ArrayList<FieldType>(); 
				List<DictionaryTypes.TypeDictionary.ComplexType> superTypes = new ArrayList<DictionaryTypes.TypeDictionary.ComplexType>(); 
				for (DictionaryTypes.TypeDictionary.ComplexType ct = this; ct!=null; ct = ct.getSuperType())
					superTypes.add(ct);
				Collections.reverse(superTypes);
				for (DictionaryTypes.TypeDictionary.ComplexType ct : superTypes)
					allFields.addAll(ct.getFields());
				return allFields;
			}
			
		}

		public class ServiceType extends BaseType {
			public Request Request;
			public Response Response;

			// Request or Response
			public class Message extends BaseType {
				public List<ParameterType> parameters = new ArrayList<ParameterType>();

				public void readFromNode(Node n) {
					for (Node f : DOMUtils
							.getChildrenByName(n, "opc:Parameter")) {
						ParameterType ft = new ParameterType();
						ft.readFromNode(f);
						parameters.add(ft);
					}
				}
				
				@Override
				public List<FieldType> getFields() {
					return new ArrayList<FieldType>(parameters);
				}
				
				@Override
				public List<FieldType> getAllFields() {
					return new ArrayList<FieldType>(parameters);
				}
			}

			public class Request extends Message {
			}

			public class Response extends Message {
			}

			@Override
			public void readFromNode(Node n) {
				super.readFromNode(n);
				Node rn = DOMUtils.getSingleChild(n, "opc:Request");
				if (rn != null) {
					Request = new Request();
					Request.readFromNode(rn);
					Request.Name = Name+"Request";
				}
				rn = DOMUtils.getSingleChild(n, "opc:Response");
				if (rn != null) {
					Response = new Response();
					Response.readFromNode(rn);
					Response.Name = Name+"Response";
				}
			}

		}

		/**
		 * Reads content from a XML DOM
		 * 
		 * @param d
		 */
		public void readFromNode(Node d) {
			super.readFromNode(d);
			// assert(d.getNodeName().equals("opc:TypeDictionary"));
			TargetNamespace = DOMUtils.getStringAttribute(d, "TargetNamespace");

			// Read name space imports
			NamedNodeMap attribs = d.getAttributes();
			for (int i = 0; i < attribs.getLength(); i++) {
				Node attrib = attribs.item(i);
				String ns = attrib.getNodeName();
				String nv = attrib.getNodeValue();
				if (ns.startsWith("xmlns:")) {
					nameSpaceMap.put(ns.substring(6), nv);
				}
			}

			// Read constants
			for (Node n : DOMUtils.getChildrenByName(d, "opc:Constant")) {
				Constant c = new Constant();
				c.readFromNode(n);
				constants.add(c);
			}

			// Type Declarations
			for (Node n : DOMUtils.getChildrenByName(d, "opc:TypeDeclaration")) {
				TypeDeclaration c = new TypeDeclaration();
				c.readFromNode(n);
				typeDeclarations.add(c);
			}
			
			// Read complex types
			for (Node n : DOMUtils.getChildrenByName(d, "opc:ComplexType")) {
				ComplexType c = new ComplexType();
				c.readFromNode(n);
				complexTypes.add(c);
				baseTypes.add(c);
			}
			
			// Read enumerations
			for (Node n : DOMUtils.getChildrenByName(d, "opc:EnumeratedType")) {
				EnumeratedType e = new EnumeratedType();
				e.readFromNode(n);
				enumerationTypes.add(e);
			}

			// Read Services
			for (Node n : DOMUtils.getChildrenByName(d, "opc:ServiceType")) {
				ServiceType e = new ServiceType();
				e.readFromNode(n);
				serviceTypes.add(e);
				baseTypes.add(e.Request);
				baseTypes.add(e.Response);
			}

		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(Name+" ("+TargetNamespace+")\n");
			if (!constants.isEmpty()) {
				sb.append("Constants:\n");
				for (AbstractType o : constants)
					sb.append("  "+o+"\n");
			}
			if (!enumerationTypes.isEmpty()) {
				sb.append("Enumerations:\n");
				for (AbstractType o : enumerationTypes)
					sb.append("  "+o+"\n");
			}
			if (!complexTypes.isEmpty()) {
				sb.append("Complex Types:\n");
				for (AbstractType o : complexTypes)
					sb.append("  "+o+"\n");
			}
			if (!serviceTypes.isEmpty()) {
				sb.append("Service Types:\n");
				for (AbstractType o : serviceTypes)
					sb.append("  "+o+"\n");
			}
			return sb.toString();			
		}

	}

	public static class ModelDesign extends AbstractDictionary {

		public List<DataType> dataTypes = new ArrayList<DataType>();
		

		public class DataType extends BaseType {
			public QName BaseType;
			public String SymbolicName;
			public List<FieldType> fields = new ArrayList<FieldType>();

			@Override
			public void readFromNode(Node n) {
				super.readFromNode(n);
				BaseType = convertToQName( DOMUtils.getStringAttribute(n, "BaseType") );
				for (Node fs : DOMUtils.getChildrenByName(n, "opc:Fields"))
					for (Node f : DOMUtils.getChildrenByName(fs, "opc:Field")) {
						FieldType ft = new FieldType();
						ft.readFromNode(f);
						fields.add(ft);
					}
			}
		}		
		/*
		public class ObjectType extends BaseType {
			public QName BaseType;
			public List<PropertyType> properties = new ArrayList<PropertyType>();
			public List<VariableType> variables = new ArrayList<VariableType>();
			public List<ObjectType> objects = new ArrayList<ObjectType>();
			
			@Override
			public void readFromNode(Node n) {
				super.readFromNode(n);
				BaseType = convertToQName( DOMUtils.getStringAttribute(n, "BaseType") );
				for (Node children : DOMUtils.getChildrenByName(n, "opc:Children"))
				{
					
				}
				
			}
		}*/
		
		/**
		 * Reads content from a XML DOM
		 * 
		 * @param d
		 */
		public void readFromNode(Node d) {
			super.readFromNode(d);
			assert(d.getNodeName().equals("ModelDesign"));

			// Read Data Types
			for (Node n : DOMUtils.getChildrenByName(d, "DataType")) {
				DataType e = new DataType();
				e.readFromNode(n);
				dataTypes.add(e);
			}
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(Name+" ("+TargetNamespace+")\n");
			if (!dataTypes.isEmpty()) {
				sb.append("Data Types:\n");
				for (AbstractType o : dataTypes)
					sb.append("  "+o+"\n");
			}
			return sb.toString();			
		}		
		
	}

}
