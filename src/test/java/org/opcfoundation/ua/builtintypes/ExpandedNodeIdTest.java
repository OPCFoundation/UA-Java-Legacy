package org.opcfoundation.ua.builtintypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Assert;
import org.junit.Test;
import org.opcfoundation.ua.common.NamespaceTable;

public class ExpandedNodeIdTest {

	@Test
	public void nullTest() {
		ExpandedNodeId nullIdx = new ExpandedNodeId(null, 0, UnsignedInteger.valueOf(0));
		Assert.assertTrue(nullIdx.isNullNodeId());

		ExpandedNodeId nullUri = new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, UnsignedInteger.valueOf(0));
		Assert.assertTrue(nullUri.isNullNodeId());
	}

	@Test
	public void parseServerIndex() {
		final int namespaceIndex = 1;
		final int intValue = 0;
		final UnsignedInteger serverIndex = UnsignedInteger.valueOf(1);
		// toString
		ExpandedNodeId serverNsIndex = new ExpandedNodeId(serverIndex, namespaceIndex, intValue);
		ExpandedNodeId parsed = ExpandedNodeId.parseExpandedNodeId(serverNsIndex.toString());
		assertEquals(serverNsIndex, parsed);
		// string composed
		String stringComposition = "svr=" + serverIndex + ";" + "ns=" + namespaceIndex + ";i=" + intValue;
		ExpandedNodeId parsedComposition = ExpandedNodeId.parseExpandedNodeId(stringComposition);
		assertEquals(serverNsIndex, parsedComposition);
	}

	@Test
	public void parseNamespaceUri() {
		final String namespaceURI = NamespaceTable.OPCUA_NAMESPACE;
		final int intValue = 0;
		ExpandedNodeId serverUri = new ExpandedNodeId(namespaceURI, intValue);
		// string composed
		String stringComposition = "nsu=" + namespaceURI + ";i=" + intValue;
		ExpandedNodeId parsedComposition = ExpandedNodeId.parseExpandedNodeId(stringComposition);
		assertEquals(serverUri, parsedComposition);
	}

	@Test
	public void equalsServerIndexes() {
		ExpandedNodeId pivotServerIndex1 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 0, 0);
		//
		ExpandedNodeId serverIndex1 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 0, 0);
		assertEquals(pivotServerIndex1, serverIndex1);
		//
		ExpandedNodeId serverIndex2 = new ExpandedNodeId(UnsignedInteger.valueOf(2), 0, 0);
		assertNotEquals(pivotServerIndex1, serverIndex2);
	}

	@Test
	public void equalsNamespaceIndixes() {
		ExpandedNodeId pivotNsIndex1 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, 0);
		// equal namespaces
		ExpandedNodeId nsIndex1 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, 0);
		assertEquals(pivotNsIndex1, nsIndex1);
		// different ns
		ExpandedNodeId nsIndex2 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 2, 0);
		assertNotEquals(pivotNsIndex1, nsIndex2);
		// ns to uri
		ExpandedNodeId uri = new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, 0);
		assertNotEquals(pivotNsIndex1, uri);
	}
}
