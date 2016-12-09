package org.opcfoundation.ua.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;

/**
 * A cache for XXXFactory instances which are needed in XML processing.
 * Idea is that this is the common place to find a factory so it needs
 *  to be created only once (which is a time-consuming operation).
 *
 * @author Bjakke
 */
public class XMLFactoryCache {

	private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
	private static final SAXTransformerFactory SAX_TRANSFORMER_FACTORY = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
	private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
	private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();

	/**
	 * <p>getDocumentBuilderFactory.</p>
	 *
	 * @return a {@link javax.xml.parsers.DocumentBuilderFactory} object.
	 */
	public static DocumentBuilderFactory getDocumentBuilderFactory(){
		return DOCUMENT_BUILDER_FACTORY;
	}

	/**
	 * <p>getSAXTransformerFactory.</p>
	 *
	 * @return a {@link javax.xml.transform.sax.SAXTransformerFactory} object.
	 */
	public static SAXTransformerFactory getSAXTransformerFactory(){
		return SAX_TRANSFORMER_FACTORY;
	}

	/**
	 * <p>getTransformerFactory.</p>
	 *
	 * @return a {@link javax.xml.transform.TransformerFactory} object.
	 */
	public static TransformerFactory getTransformerFactory(){
		return TRANSFORMER_FACTORY;
	}

	/**
	 * <p>getXMLInputFactory.</p>
	 *
	 * @return a {@link javax.xml.stream.XMLInputFactory} object.
	 */
	public static XMLInputFactory getXMLInputFactory(){
		return XML_INPUT_FACTORY;
	}

}
