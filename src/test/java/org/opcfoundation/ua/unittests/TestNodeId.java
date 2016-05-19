/* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.unittests;
import java.util.UUID;

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;

import junit.framework.TestCase;


public class TestNodeId extends TestCase {

	public void testEquals() {
		NodeId a = new NodeId(0, "xyz");
		NodeId b = new NodeId(0, "xyz");
		assertEquals(a, b);
		assertNotSame(a, b);

		ExpandedNodeId c = new ExpandedNodeId(null, 0, "xyz");
		assertEquals(a, c);
		assertEquals(c, a);
		assertEquals(a.hashCode(), c.hashCode());
		assertEquals(c.hashCode(), a.hashCode());

		ExpandedNodeId d = new ExpandedNodeId(UnsignedInteger.valueOf(1), 0, "xyz");
		assertFalse(c.equals(d));
		assertFalse(a.equals(d));

		assertEquals( new NodeId(0, "x").toString(), "s=x" );
		assertEquals( new NodeId(0, new byte[5]).toString(), "b=AAAAAAA=" );
		assertEquals( new NodeId(0, UUID.fromString("0d4e10fa-b9e1-4842-bca6-887c7d8c31f0")).toString(), "g=0d4e10fa-b9e1-4842-bca6-887c7d8c31f0" );
		assertEquals( new NodeId(0, UnsignedInteger.valueOf(5)).toString(), "i=5" );

		assertEquals( new NodeId(1, "x").toString(), "ns=1;s=x" );
		assertEquals( new NodeId(1, new byte[5]).toString(), "ns=1;b=AAAAAAA=" );
		assertEquals( new NodeId(1, UUID.fromString("0d4e10fa-b9e1-4842-bca6-887c7d8c31f0")).toString(), "ns=1;g=0d4e10fa-b9e1-4842-bca6-887c7d8c31f0" );
		assertEquals( new NodeId(1, UnsignedInteger.valueOf(5)).toString(), "ns=1;i=5" );

		assertEquals( new NodeId(0, "x"), NodeId.parseNodeId("s=x") );
		assertEquals( new NodeId(0, new byte[5]), NodeId.parseNodeId("b=AAAAAAA=") );
		assertEquals( new NodeId(0, UUID.fromString("0d4e10fa-b9e1-4842-bca6-887c7d8c31f0")), NodeId.parseNodeId("g=0d4e10fa-b9e1-4842-bca6-887c7d8c31f0") );
		assertEquals( new NodeId(0, UnsignedInteger.valueOf(5)), NodeId.parseNodeId("i=5") );

		assertEquals( new NodeId(1, "x"), NodeId.parseNodeId("ns=1;s=x") );
		assertEquals( new NodeId(1, new byte[5]), NodeId.parseNodeId("ns=1;b=AAAAAAA=") );
		assertEquals( new NodeId(1, UUID.fromString("0d4e10fa-b9e1-4842-bca6-887c7d8c31f0")), NodeId.parseNodeId("ns=1;g=0d4e10fa-b9e1-4842-bca6-887c7d8c31f0") );
		assertEquals( new NodeId(1, UnsignedInteger.valueOf(5)), NodeId.parseNodeId("ns=1;i=5") );

		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(0), 0, "x").toString() );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(0), 0, new byte[5]).toString() );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(0), 0, UUID.randomUUID()).toString() );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(0), 0, UnsignedInteger.valueOf(5)).toString() );
		//
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(0), NamespaceTable.OPCUA_NAMESPACE, "x").toString() );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(0), NamespaceTable.OPCUA_NAMESPACE, new byte[5]).toString() );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(0), NamespaceTable.OPCUA_NAMESPACE, UUID.randomUUID()).toString() );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(0), NamespaceTable.OPCUA_NAMESPACE, UnsignedInteger.valueOf(5)).toString() );

		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, "x").toString(tbl) );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, new byte[] {1,2,3,4}).toString(tbl) );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, UUID.randomUUID()).toString(tbl) );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(1), 1, UnsignedInteger.valueOf(5)).toString(tbl) );
		//
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(1), 0, "x").toString(tbl) );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(1), 0, new byte[5]).toString(tbl) );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(1), 0, UUID.randomUUID()).toString(tbl) );
		//		System.out.println( new ExpandedNodeId(UnsignedInteger.valueOf(1), 0, UnsignedInteger.valueOf(5)).toString(tbl) );

	}

}
