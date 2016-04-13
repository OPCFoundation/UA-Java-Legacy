package org.opcfoundation.ua.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;

/**
 * A cache for XXXFactory instances which are needed in XML processing.
 * Idea is that this is the common place to find a factory so it needs
 *  to be created only once (which is a time-consuming operation).
 * @author Bjakke
 *
 */
public class XMLFactoryCache {

	private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
	private static final SAXTransformerFactory SAX_TRANSFORMER_FACTORY = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
	private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
	private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();

	public static DocumentBuilderFactory getDocumentBuilderFactory(){
		return DOCUMENT_BUILDER_FACTORY;
	}

	public static SAXTransformerFactory getSAXTransformerFactory(){
		return SAX_TRANSFORMER_FACTORY;
	}

	public static TransformerFactory getTransformerFactory(){
		return TRANSFORMER_FACTORY;
	}

	public static XMLInputFactory getXMLInputFactory(){
		return XML_INPUT_FACTORY;
	}

}
