package org.opcfoundation.ua.encoding.xml.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.CryptoUtil;

public class XmlExpandedNodeId {

	/**
	 * Latin alphabet No.1
	 */
	public static final String NSU_XML_ENCODING = "ISO8859-1";

	/**
	 * <p>
	 * parseExpandedNodeId.
	 * </p>
	 *
	 * @param s
	 *            a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @throws UnsupportedEncodingException
	 */
	public static ExpandedNodeId parse(String s) throws UnsupportedEncodingException {
		String[] parts = s.split(";");
		assertExpandedNodeIdParts(s, parts, 1);

		int svrIndex = 0;
		int nsIndex = 0;
		NodeId nodeIdValue = NodeId.parseNodeId(parts[parts.length - 1]);
		ExpandedNodeId returnable = null;
		for (int i = 0; i < parts.length - 1; i++) {
			String[] subParts = parts[i].split("=");
			assertExpandedNodeIdParts(s, subParts, 2);
			if (subParts[0].equalsIgnoreCase("svr"))
				svrIndex = Integer.parseInt(subParts[1]);
			else if (subParts[0].equalsIgnoreCase("ns")) {
				nsIndex = Integer.parseInt(subParts[1]);
				returnable = new ExpandedNodeId(UnsignedInteger.valueOf(svrIndex), nsIndex, nodeIdValue.getValue());
			} else if (subParts[0].equalsIgnoreCase("nsu")) {
				String ns = subParts[1];
				ns = URLDecoder.decode(ns, NSU_XML_ENCODING);
				returnable = new ExpandedNodeId(UnsignedInteger.valueOf(svrIndex), ns, nodeIdValue.getValue());
			} else
				throwExpandedNodeIdCastException(s);
		}
		// ns and nsu skipped
		if (returnable == null) {
			returnable = new ExpandedNodeId(UnsignedInteger.valueOf(svrIndex), nsIndex, nodeIdValue.getValue());
		}
		return returnable;
	}

	public static String composeString(ExpandedNodeId eNodeId) throws UnsupportedEncodingException {
		String svrPart = !eNodeId.isLocal() ? "svr=" + eNodeId.getServerIndex() + ";" : "";
		String nsPart = getNsPart(eNodeId);

		Object value = eNodeId.getValue();
		switch (eNodeId.getIdType()) {
		case Numeric:
			return svrPart + nsPart + "i=" + value;
		case Guid:
			return svrPart + nsPart + "g=" + value;
		case Opaque:
			if (value == null)
				return svrPart + nsPart + "b=null";
			return svrPart + nsPart + "b=" + new String(CryptoUtil.base64Encode(((ByteString) value).getValue()));
		case String:
			return svrPart + nsPart + "s=" + value;
		default:
			return "error";
		}
	}

	private static String getNsPart(ExpandedNodeId eNodeId) throws UnsupportedEncodingException {
		String namespaceUri = eNodeId.getNamespaceUri();
		int namespaceIndex = eNodeId.getNamespaceIndex();
		return namespaceUri != null ? "nsu=" + URLEncoder.encode(namespaceUri, "ISO8859-1") + ";"
				: namespaceIndex > 0 ? "ns=" + namespaceIndex + ";" : "";
	}

	/**
	 * @param s
	 * @param parts
	 * @param n
	 * @throws ClassCastException
	 */
	private static void assertExpandedNodeIdParts(String s, String[] parts, final int n) throws ClassCastException {
		if (parts.length < n)
			throwExpandedNodeIdCastException(s);
	}

	/**
	 * @param s
	 * @throws ClassCastException
	 */
	private static void throwExpandedNodeIdCastException(String s) throws ClassCastException {
		throw new ClassCastException("String is not a valid ExpandedNodeId: " + s);
	}

}
