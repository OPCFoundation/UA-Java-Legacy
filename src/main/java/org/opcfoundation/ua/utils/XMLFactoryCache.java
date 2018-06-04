package org.opcfoundation.ua.utils;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A cache for XXXFactory instances which are needed in XML processing.
 * Idea is that this is the common place to find a factory so it needs
 *  to be created only once (which is a time-consuming operation).
 */
public class XMLFactoryCache {

	private static final Logger logger = LoggerFactory.getLogger(XMLFactoryCache.class);
	
	private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
	private static final SAXTransformerFactory SAX_TRANSFORMER_FACTORY = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
	private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
	private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();

	static{
		//Disable DTD processing
		
		XML_INPUT_FACTORY.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		XML_INPUT_FACTORY.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
		
		try {
			String dtd = (String) XMLConstants.class.getDeclaredField("ACCESS_EXTERNAL_DTD").get(null);
			String schema = (String) XMLConstants.class.getDeclaredField("ACCESS_EXTERNAL_SCHEMA").get(null);
			String stylesheet = (String) XMLConstants.class.getDeclaredField("ACCESS_EXTERNAL_STYLESHEET").get(null);
			
			TRANSFORMER_FACTORY.setAttribute(dtd, "");
			TRANSFORMER_FACTORY.setAttribute(stylesheet, "");
			
			SAX_TRANSFORMER_FACTORY.setAttribute(dtd, "");
			SAX_TRANSFORMER_FACTORY.setAttribute(stylesheet, "");
			
			DOCUMENT_BUILDER_FACTORY.setAttribute(dtd, "");
			DOCUMENT_BUILDER_FACTORY.setAttribute(schema, "");
			DOCUMENT_BUILDER_FACTORY.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			
			DOCUMENT_BUILDER_FACTORY.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			DOCUMENT_BUILDER_FACTORY.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			
			DOCUMENT_BUILDER_FACTORY.setFeature("http://xerces.apache.org/xerces-j/features.html#external-general-entities", false);
			DOCUMENT_BUILDER_FACTORY.setFeature("http://xerces.apache.org/xerces2-j/features.html#external-general-entities", false);
			DOCUMENT_BUILDER_FACTORY.setFeature("http://xml.org/sax/features/external-general-entities", false);
			
			DOCUMENT_BUILDER_FACTORY.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			DOCUMENT_BUILDER_FACTORY.setFeature("http://xerces.apache.org/xerces-j/features.html#external-parameter-entities", false);
			DOCUMENT_BUILDER_FACTORY.setFeature("http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities", false);
			
			DOCUMENT_BUILDER_FACTORY.setXIncludeAware(false);
			DOCUMENT_BUILDER_FACTORY.setExpandEntityReferences(false);
			
		} catch (Exception e) {
			logger.warn("Cannot initialize XML factories to ignore DTD processing, please update java to newer version");
		}

	}
	
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
