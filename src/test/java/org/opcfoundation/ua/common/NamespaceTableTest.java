package org.opcfoundation.ua.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;

public class NamespaceTableTest {
	
	@Test
	public void testNodeIdEqualsOpaque() throws Exception {
		NamespaceTable testTable = new NamespaceTable();
		NodeId b = new NodeId(1, new byte[]  { 'a', '0' });
		ExpandedNodeId c = new ExpandedNodeId(null, 1, new byte[] { 'a', '0' });
		ExpandedNodeId d = new ExpandedNodeId(null, 1, new byte[] { 'a', '0' });
		ExpandedNodeId e = new ExpandedNodeId(null, 1, new byte[] { 'a', '0', '1' });
		
		assertEquals(true, testTable.nodeIdEquals(b, c));
		assertEquals(true, testTable.nodeIdEquals(d, c));
		
		assertEquals(false, testTable.nodeIdEquals(b, e));
		assertEquals(false, testTable.nodeIdEquals(d, e));
	}	
}
