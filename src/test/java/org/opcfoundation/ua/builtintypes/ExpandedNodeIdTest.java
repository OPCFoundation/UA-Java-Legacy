package org.opcfoundation.ua.builtintypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.opcfoundation.ua.common.NamespaceTable;

public class ExpandedNodeIdTest {

	@Test
	public void nullTest() {
		ExpandedNodeId nullIdx = new ExpandedNodeId(null, 0, UnsignedInteger.valueOf(0));
		assertTrue(nullIdx.isNullNodeId());
		ExpandedNodeId nullUri = new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, UnsignedInteger.valueOf(0));
		assertTrue(nullUri.isNullNodeId());
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