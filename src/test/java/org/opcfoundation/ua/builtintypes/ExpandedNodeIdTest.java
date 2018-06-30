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
	public void parseServerIndexWithtoutNamespaceComponent() {
		final int intValue = 0;
		final UnsignedInteger serverIndex = UnsignedInteger.valueOf(1);
		// parse without nsu and ns
		ExpandedNodeId namespaceIndex0 = new ExpandedNodeId(serverIndex, 0, intValue);
		String stringWithoutMiddlePart = "svr=" + serverIndex + ";i=" + intValue;
		ExpandedNodeId parsedWithoutMiddlePart = ExpandedNodeId.parseExpandedNodeId(stringWithoutMiddlePart);
		assertEquals(namespaceIndex0, parsedWithoutMiddlePart);
	}

	@Test
	public void parseServerIndexByStringComposition() {
		final int namespaceIndex = 1;
		final int intValue = 0;
		final UnsignedInteger serverIndex = UnsignedInteger.valueOf(1);
		ExpandedNodeId serverNsIndex = new ExpandedNodeId(serverIndex, namespaceIndex, intValue);
		// composition
		String stringComposition = "svr=" + serverIndex + ";" + "ns=" + namespaceIndex + ";i=" + intValue;
		ExpandedNodeId parsedComposition = ExpandedNodeId.parseExpandedNodeId(stringComposition);
		assertEquals(serverNsIndex, parsedComposition);
	}

	@Test
	public void parseServerIndexByToString() {
		ExpandedNodeId serverNsIndex = new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, 0);
		// toString
		ExpandedNodeId parsed = ExpandedNodeId.parseExpandedNodeId(serverNsIndex.toString());
		assertEquals(serverNsIndex, parsed);
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
	public void equalsSameServerIndex() {
		ExpandedNodeId pivotServerIndex1 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 0, 0);
		ExpandedNodeId serverIndex1 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 0, 0);
		assertEquals(pivotServerIndex1, serverIndex1);
	}

	@Test
	public void equalsDifferentServerIndex() {
		ExpandedNodeId pivotServerIndex1 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 0, 0);
		ExpandedNodeId serverIndex2 = new ExpandedNodeId(UnsignedInteger.valueOf(2), 0, 0);
		assertNotEquals(pivotServerIndex1, serverIndex2);
	}

	@Test
	public void equalsSameNamespaceIndex() {
		ExpandedNodeId nsIndex1a = new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, 0);
		ExpandedNodeId nsIndex1 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, 0);
		assertEquals(nsIndex1a, nsIndex1);
	}

	@Test
	public void equalsDifferentNamespaceIndex() {
		ExpandedNodeId nsIndex1 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, 0);
		ExpandedNodeId nsIndex2 = new ExpandedNodeId(UnsignedInteger.valueOf(1), 2, 0);
		assertNotEquals(nsIndex1, nsIndex2);
	}

	@Test
	public void equalsNamespaceIndexToURI() {
		ExpandedNodeId index = new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, 0);
		ExpandedNodeId uri = new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, 0);
		assertNotEquals(index, uri);
	}

	@Test
	public void equalsDifferentNamespaceURI() {
		ExpandedNodeId uriDI = new ExpandedNodeId("http://opcfoundation.org/UA/DI/", 0);
		ExpandedNodeId uriUA = new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, 0);
		assertNotEquals(uriUA, uriDI);

	}
}
