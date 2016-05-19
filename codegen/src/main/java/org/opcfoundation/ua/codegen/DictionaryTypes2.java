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
public class DictionaryTypes2 {
	
	public static class AbstractDictionary {
		public String Name;
		public String TargetNamespace;
		public Map<String, String> nameSpaceMap = new HashMap<String, String>();
		
		public abstract class AbstractType {
			public String Name;

			public void readFromNode(Node n) {
				// Read either Name or SymbolicName
				NamedNodeMap nm = n.getAttributes();
				assert(nm!=null);
				Node nameNode = nm.getNamedItem("Name");
				if (nameNode!=null) {
					Name = nameNode.getNodeValue();
				}

				nameNode = nm.getNamedItem("SymbolicName");
				if (nameNode!=null) {
					Name = toQName( nameNode.getNodeValue() ).getLocalPart();
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
			public QName SymbolicName;			
			public QName SymbolicId;
			public QName BaseType;

			public void readFromNode(Node n) {
				super.readFromNode(n);
				NotInAddressSpace = DOMUtils.getBooleanAttribute(n,
						"NotInAddressSpace");
				AllowArray = DOMUtils.getBooleanAttribute(n, "AllowArray");
				
				NamedNodeMap nm = n.getAttributes();
				Node idNode = nm.getNamedItem("SymbolicId");
				if (idNode!=null) 
					SymbolicId = toQName( idNode.getNodeValue());

				Node nameNode = nm.getNamedItem("SymbolicName");
				if (nameNode!=null) 
					SymbolicId = toQName( nameNode.getNodeValue() );

				Node baseTypeNode = nm.getNamedItem("BaseType");
				if (baseTypeNode!=null) 
					BaseType = toQName( baseTypeNode.getNodeValue() );
			}
			
			/** 
			 * Get super type 
			 * @return
			 */
			public QName getBaseType() {
				return BaseType;
			}
			
			public List<FieldType> getFields() {
				return null;
			}

			public List<FieldType> getAllFields() {
				return null;
			}

		}		
		
		QName toQName(String name)
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
		
		String toName(String name)
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
				return nsPart+localPart;
			} else {
				return AbstractDictionary.this.TargetNamespace+name;
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
				DataType = toQName( DOMUtils.getStringAttribute(n, "DataType") );			
				Identifier = DOMUtils.getIntegerAttribute(n, "Identifier");
				ValueRank = DOMUtils.getStringAttribute(n, "ValueRank");
				ArrayLength = DOMUtils.getIntegerAttribute(n, "ArrayLength");
				SingularName = DOMUtils.getStringAttribute(n, "SingularName");
				IsAttribute = DOMUtils.getBooleanAttribute(n, "IsAttribute");
				DefaultValue = DOMUtils.getStringAttribute(n, "DefaultValue");
			}
			
			public String getDataType() {
				if (DataType==null) return null;
				String ns = DataType.getNamespaceURI();
				if (ns.endsWith("/"))
					return ns+DataType.getLocalPart();
				return ns+"/"+DataType.getLocalPart();				
			}	
			
		}		

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

	public static class ModelDesign extends AbstractDictionary {

		public List<DataType> dataTypes = new ArrayList<DataType>();
		
		public DataType getDataType(String symbolicName)
		{
			for (DataType ct : dataTypes) 
				if (ct.SymbolicName.equals(symbolicName))
					return ct;			
			return null;
		}
		
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
		
		public class DataType extends BaseType {
			public String BaseType, SymbolicName, SymbolicId;
			public List<FieldType> fields = new ArrayList<FieldType>();

			@Override
			public void readFromNode(Node n) {
				super.readFromNode(n);
				BaseType = toName( DOMUtils.getStringAttribute(n, "BaseType") );
				SymbolicName = toName( DOMUtils.getStringAttribute(n, "SymbolicName") );
				SymbolicId = toName( DOMUtils.getStringAttribute(n, "SymbolicId") );
				for (Node fs : DOMUtils.getChildrenByName(n, "Fields")) {
					for (Node f : DOMUtils.getChildrenByName(fs, "Field")) {
						FieldType ft = new FieldType();
						ft.readFromNode(f);
						fields.add(ft);
					}
				}
			}
			
			public DataType getSuperType() {
				return getDataType( BaseType );
			}			
			
			@Override
			public List<FieldType> getFields() {
				return fields;
			}

			/**
			 * Get all fields including super fields
			 */
			@Override
			public List<FieldType> getAllFields() {
				List<FieldType> allFields = new ArrayList<FieldType>(); 
				List<DictionaryTypes2.ModelDesign.DataType> superTypes = new ArrayList<DictionaryTypes2.ModelDesign.DataType>(); 
				for (DictionaryTypes2.ModelDesign.DataType ct = this; ct!=null; ct = ct.getSuperType())
					superTypes.add(ct);
				Collections.reverse(superTypes);
				for (DictionaryTypes2.ModelDesign.DataType ct : superTypes)
					allFields.addAll(ct.getFields());
				return allFields;
			}
			
			public FieldType getField(String name)
			{
				for (FieldType ft : getAllFields()) 
					if (ft.Name.equals(name))
						return ft;			
				return null;
			}		
			
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
		
		
		
	}

}
