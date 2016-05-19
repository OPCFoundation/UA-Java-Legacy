package org.opcfoundation.ua.builtintypes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
