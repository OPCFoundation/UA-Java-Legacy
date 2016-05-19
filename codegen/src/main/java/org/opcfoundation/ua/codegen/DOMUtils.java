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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class DOMUtils {

	/**
	 * Reads the node value of a single child node 
	 * @param n
	 * @param childName
	 * @return value or null
	 */
	public static String getChildStringValue(Node n, String childName)
	{
		Node c = getSingleChild(n, childName);
		if (c==null) return null;
		String value = c.getNodeValue();
		if (value!=null) return value; 
		value = c.getTextContent();
		return value.trim();
	}

	/**
	 * Reads a single attribute of a single child node
	 * @param n
	 * @param childName
	 * @param attribute
	 * @return value or null
	 */
	public static String getChildAttribute(Node n, String childName, String attribute)
	{
		Node c = getSingleChild(n, childName);
		return c.getAttributes().getNamedItem(attribute).getNodeValue();	
	}
	
	public static Integer getIntegerAttribute(Node n, String attributeName)
	{
		Node attrib = n.getAttributes().getNamedItem(attributeName);
		if ( attrib==null ) return null;
		String value = attrib.getNodeValue();
		assert(value!=null);
		return new Integer(value);
	}
	
	public static QName getQNameAttribute(Node n, String namespace, String attributeName)
	{
		Node attrib = n.getAttributes().getNamedItem(attributeName);
		if ( attrib==null ) return null;
		String value = attrib.getNodeValue();
		assert(value!=null);
		if (namespace==null)
			return new QName(value);
		else
			return new QName(namespace, value);
	}
	
	public static String getStringAttribute(Node n, String attributeName)
	{
		Node attrib = n.getAttributes().getNamedItem(attributeName);
		if ( attrib==null ) return null;
		return attrib.getNodeValue().trim();
	}	
	
	public static Boolean getBooleanAttribute(Node n, String attributeName)
	{
		Node attrib = n.getAttributes().getNamedItem(attributeName);
		if ( attrib==null ) return null;
		String value = attrib.getNodeValue();
		assert(value!=null);
		return new Boolean(value);
	}
	
	/**
	 * 
	 * @param n
	 * @param childName
	 * @return
	 */
	public static List<Node> getChildrenByName(Node n, String childName)
	{
		ArrayList<Node> result = new ArrayList<Node>();
		for (int i=0; i<n.getChildNodes().getLength(); i++)
		{
			Node c = n.getChildNodes().item(i);
			if (c.getNodeName().equals(childName)) {
				result.add(c);
			}
		}		
		return result;
	}
	
	/**
	 * Reads document from file
	 * @param fileName
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document readDocument(String fileName) 
	throws SAXException, IOException {
		DocumentBuilder docBuilder;
		Document result = null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// unexpected - configuration is assumed to be correct
			throw new Error(e);
		}
		File sourceFile = new File(fileName);
		result = docBuilder.parse(sourceFile);
		return result;
	}	

	public static Node getNode(String filename, String nodeName) throws SAXException, IOException
	{
		Document d = readDocument(filename);
		return getSingleChild(d, nodeName);
	}
	
	/**
	 * Get a single child by name
	 * @param n
	 * @param childName
	 * @return
	 */
	public static Node getSingleChild(Node n, String childName)
	{
		Node result = null;
		for (int i=0; i<n.getChildNodes().getLength(); i++)
		{
			Node c = n.getChildNodes().item(i);
			if (c.getNodeName().equals(childName))
			{
				assert(result==null);
				result = c;
			}
		}
		return result;		
	}
	
}
