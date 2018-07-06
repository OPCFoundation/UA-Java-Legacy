package org.opcfoundation.ua.encoding.xml;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.NamespaceTable;
import org.opcfoundation.ua.encoding.xml.helpers.XmlExpandedNodeId;

public class XmlExpandedNodeIdTest {

	@Test
	public void parseServerIndexWithtoutNamespaceComponent() throws UnsupportedEncodingException {
		final int intValue = 0;
		final UnsignedInteger serverIndex = UnsignedInteger.valueOf(1);
		// parse without nsu and ns
		ExpandedNodeId namespaceIndex0 = new ExpandedNodeId(serverIndex, 0, intValue);
		String stringWithoutMiddlePart = "svr=" + serverIndex + ";i=" + intValue;
		ExpandedNodeId parsedWithoutMiddlePart = XmlExpandedNodeId.parse(stringWithoutMiddlePart);
		assertEquals(namespaceIndex0, parsedWithoutMiddlePart);
	}

	@Test
	public void parseValueOnly() throws UnsupportedEncodingException {
		ExpandedNodeId parsed = XmlExpandedNodeId.parse("i=0");
		assertEquals(new ExpandedNodeId(UnsignedInteger.valueOf(0), 0, 0), parsed);
	}

	@Test
	public void parseServerIndexByStringComposition() throws UnsupportedEncodingException {
		final int namespaceIndex = 1;
		final int intValue = 0;
		final UnsignedInteger serverIndex = UnsignedInteger.valueOf(1);
		ExpandedNodeId serverNsIndex = new ExpandedNodeId(serverIndex, namespaceIndex, intValue);
		// composition
		String stringComposition = "svr=" + serverIndex + ";" + "ns=" + namespaceIndex + ";i=" + intValue;
		ExpandedNodeId parsedComposition = XmlExpandedNodeId.parse(stringComposition);
		assertEquals(serverNsIndex, parsedComposition);
	}

	@Test
	public void parseServerIndexByToString() throws UnsupportedEncodingException {
		ExpandedNodeId serverNsIndex = new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, 0);
		// toString
		ExpandedNodeId parsed = XmlExpandedNodeId.parse(XmlExpandedNodeId.composeString(serverNsIndex));
		assertEquals(serverNsIndex, parsed);
	}

	@Test
	public void parseNamespaceUri() throws UnsupportedEncodingException {
		final String namespaceURI = NamespaceTable.OPCUA_NAMESPACE;
		final int intValue = 0;
		ExpandedNodeId serverUri = new ExpandedNodeId(namespaceURI, intValue);
		// string composed
		String stringComposition = "nsu=" + namespaceURI + ";i=" + intValue;
		ExpandedNodeId parsedComposition = XmlExpandedNodeId.parse(stringComposition);
		assertEquals(serverUri, parsedComposition);
	}

	@Test
	public void parseNamespaceUriFromToString() throws UnsupportedEncodingException {
		ExpandedNodeId serverUri = new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, 0);
		// toString
		ExpandedNodeId parsed = XmlExpandedNodeId.parse(XmlExpandedNodeId.composeString(serverUri));
		assertEquals(serverUri, parsed);
	}

}
