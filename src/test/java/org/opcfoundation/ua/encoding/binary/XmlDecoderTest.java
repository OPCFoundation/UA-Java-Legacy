package org.opcfoundation.ua.encoding.binary;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.builtintypes.XmlElement;
import org.opcfoundation.ua.common.NamespaceTable;
import org.opcfoundation.ua.common.ServerTable;
import org.opcfoundation.ua.core.Argument;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.xml.XmlDecoder;
import org.opcfoundation.ua.utils.StackUtils;

public class XmlDecoderTest {
	
	@Test
	public void nodeIdTransformWithinStructureFields() throws Exception {
		//For GH#146 issue
		/*
		 * XmlDecoder is able to transform NodeId's and others namespaceindexes
		 * if given different namespacetables via 1. the EncoderContext 2. via
		 * setNamespaceTable. This is useful if parsing from different context
		 * to another (e.g. NodeSet to address space) as doing after decoding
		 * for a deeply nested Structure can be cumbersome.
		 */

		// Test data, Argument Structure in a Method arguments definition
		// typically seen in NodeSet XMLs (escaped and without whitespace)
		String dataStr = "<Value xmlns=\"http://opcfoundation.org/UA/2011/03/UANodeSet.xsd\"><ListOfExtensionObject xmlns=\"http://opcfoundation.org/UA/2008/02/Types.xsd\"><ExtensionObject><TypeId><Identifier>i=297</Identifier></TypeId><Body><Argument><Name>Test</Name><DataType><Identifier>ns=1;i=1000</Identifier></DataType><ValueRank>-1</ValueRank><ArrayDimensions/><Description xmlns:p5=\"http://www.w3.org/2001/XMLSchema-instance\" p5:nil=\"true\"/></Argument></Body></ExtensionObject></ListOfExtensionObject></Value>";

		XmlElement data = new XmlElement(dataStr);

		String nsX = "X";
		String nsY = "Y";
		
		EncoderContext ctx = new EncoderContext(new NamespaceTable(),
				new ServerTable(), StackUtils.getDefaultSerializer());
		// 0 position in both is standard, fill context with X and Y
		// X will get index 1, Y will get 2
		ctx.getNamespaceTable().add(nsX);
		ctx.getNamespaceTable().add(nsY);
		assertEquals(0, ctx.getNamespaceTable()
				.getIndex(NamespaceTable.OPCUA_NAMESPACE));
		assertEquals(1, ctx.getNamespaceTable().getIndex(nsX));
		assertEquals(2, ctx.getNamespaceTable().getIndex(nsY));
		
		final XmlDecoder sut = new XmlDecoder(data, ctx);

		// Make a separate table for the decoder, fill only Y, it will get index
		// 1
		NamespaceTable nst = new NamespaceTable();
		nst.add(nsY);
		assertEquals(0, nst.getIndex(NamespaceTable.OPCUA_NAMESPACE));
		assertEquals(1, nst.getIndex(nsY));

		// Set the table with only Y to decoder
		sut.setNamespaceTable(nst);

		/*
		 * Parse the XML for the value. What should happen is that the DataType
		 * identifier inside the parsed Argument Structure is transformed from
		 * NodeId ns=1;i=1000 to ns=2;i=1000, since it will be read as nsIdx 1,
		 * which will map to "Y", which is then mapped to 2 from the other table
		 * "Y" mapping.
		 */

		final Variant v = sut.getVariant("");
		final Argument a = ((Argument[]) v.getValue())[0];

		assertEquals(new NodeId(2, 1000), a.getDataType());
		
		
		//If table not set to XmlDecoder, then should be as-is
		sut.setNamespaceTable(null);
		final XmlDecoder sut2 = new XmlDecoder(data, ctx);
		final Variant v2 = sut2.getVariant("");
		final Argument a2 = ((Argument[]) v2.getValue())[0];
		assertEquals(new NodeId(1, 1000), a2.getDataType());

	}
	
	
	@Test
	public void decimalDecoding() throws Exception {
		String data = "<ExtensionObject xmlns=\"http://opcfoundation.org/UA/2011/03/UANodeSet.xsd\">\r\n" + 
				"		<TypeId>\r\n" + 
				"			<Identifier>i=50</Identifier>\r\n" + 
				"		</TypeId>\r\n" + 
				"		<Body>\r\n" + 
				"			<Decimal xmlns=\"http://opcfoundation.org/UA/2008/02/Types.xsd\">\r\n" + 
				"				<Scale>5</Scale>\r\n" + 
				"				<Value>342742334224</Value>\r\n" + 
				"			</Decimal>\r\n" + 
				"		</Body>\r\n" + 
				"	</ExtensionObject>";
		
		XmlElement xml = new XmlElement(data);
		EncoderContext ctx = new EncoderContext(new NamespaceTable(),
				new ServerTable(), StackUtils.getDefaultSerializer());
		XmlDecoder sut = new XmlDecoder(xml, ctx);
		BigDecimal actual = sut.get("ExtensionObject", BigDecimal.class);
		BigDecimal expected =  BigDecimal.valueOf(342742334224L, 5);
		assertEquals(expected, actual);
	}
	
	@Test
	public void decimalArrayDecoding() throws Exception {
		String data = "	<ListOfExtensionObject xmlns=\"http://opcfoundation.org/UA/2008/02/Types.xsd\">"+ 
				"<ExtensionObject>\r\n" + 
				"		<TypeId>\r\n" + 
				"			<Identifier>i=50</Identifier>\r\n" + 
				"		</TypeId>\r\n" + 
				"		<Body>\r\n" + 
				"			<Decimal xmlns=\"http://opcfoundation.org/UA/2008/02/Types.xsd\">\r\n" + 
				"				<Scale>1</Scale>\r\n" + 
				"				<Value>342742334224</Value>\r\n" + 
				"			</Decimal>\r\n" + 
				"		</Body>\r\n" + 
				"	</ExtensionObject>\r\n" + 
				"<ExtensionObject>\r\n" + 
				"		<TypeId>\r\n" + 
				"			<Identifier>i=50</Identifier>\r\n" + 
				"		</TypeId>\r\n" + 
				"		<Body>\r\n" + 
				"			<Decimal xmlns=\"http://opcfoundation.org/UA/2008/02/Types.xsd\">\r\n" + 
				"				<Scale>2</Scale>\r\n" + 
				"				<Value>342742334224</Value>\r\n" + 
				"			</Decimal>\r\n" + 
				"		</Body>\r\n" + 
				"	</ExtensionObject>\r\n" + 
				"</ListOfExtensionObject>";
		
		XmlElement xml = new XmlElement(data);
		EncoderContext ctx = new EncoderContext(new NamespaceTable(),
				new ServerTable(), StackUtils.getDefaultSerializer());
		XmlDecoder sut = new XmlDecoder(xml, ctx);
		BigDecimal[] actual = sut.get("ListOfExtensionObject", BigDecimal[].class);
		BigDecimal[] expected = new BigDecimal[] {BigDecimal.valueOf(342742334224L, 1), BigDecimal.valueOf(342742334224L, 2)};
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void decimalInVariantDecoding() throws Exception {
		String data = "<Value xmlns=\"http://opcfoundation.org/UA/2011/03/UANodeSet.xsd\">\r\n" + 
				"	<ExtensionObject>\r\n" + 
				"		<TypeId>\r\n" + 
				"			<Identifier>i=50</Identifier>\r\n" + 
				"		</TypeId>\r\n" + 
				"		<Body>\r\n" + 
				"			<Decimal xmlns=\"http://opcfoundation.org/UA/2008/02/Types.xsd\">\r\n" + 
				"				<Scale>5</Scale>\r\n" + 
				"				<Value>342742334224</Value>\r\n" + 
				"			</Decimal>\r\n" + 
				"		</Body>\r\n" + 
				"	</ExtensionObject>\r\n" + 
				"</Value>";
		
		XmlElement xml = new XmlElement(data);
		EncoderContext ctx = new EncoderContext(new NamespaceTable(),
				new ServerTable(), StackUtils.getDefaultSerializer());
		XmlDecoder sut = new XmlDecoder(xml, ctx);
		Variant v = sut.get("", Variant.class);
		BigDecimal actual = (BigDecimal) v.getValue();
		BigDecimal expected = BigDecimal.valueOf(342742334224L, 5);
		assertEquals(expected, actual);
	}
	
	@Test
	public void decimalArrayInVariantDecoding() throws Exception {
		String data = "<Value xmlns=\"http://opcfoundation.org/UA/2011/03/UANodeSet.xsd\">\r\n" + 
				"	<ListOfExtensionObject xmlns=\"http://opcfoundation.org/UA/2008/02/Types.xsd\">"+ 
				"<ExtensionObject>\r\n" + 
				"		<TypeId>\r\n" + 
				"			<Identifier>i=50</Identifier>\r\n" + 
				"		</TypeId>\r\n" + 
				"		<Body>\r\n" + 
				"			<Decimal xmlns=\"http://opcfoundation.org/UA/2008/02/Types.xsd\">\r\n" + 
				"				<Scale>1</Scale>\r\n" + 
				"				<Value>342742334224</Value>\r\n" + 
				"			</Decimal>\r\n" + 
				"		</Body>\r\n" + 
				"	</ExtensionObject>\r\n" + 
				"<ExtensionObject>\r\n" + 
				"		<TypeId>\r\n" + 
				"			<Identifier>i=50</Identifier>\r\n" + 
				"		</TypeId>\r\n" + 
				"		<Body>\r\n" + 
				"			<Decimal xmlns=\"http://opcfoundation.org/UA/2008/02/Types.xsd\">\r\n" + 
				"				<Scale>2</Scale>\r\n" + 
				"				<Value>342742334224</Value>\r\n" + 
				"			</Decimal>\r\n" + 
				"		</Body>\r\n" + 
				"	</ExtensionObject>\r\n" + 
				"</ListOfExtensionObject>"+
				"</Value>";
		
		XmlElement xml = new XmlElement(data);
		EncoderContext ctx = new EncoderContext(new NamespaceTable(),
				new ServerTable(), StackUtils.getDefaultSerializer());
		XmlDecoder sut = new XmlDecoder(xml, ctx);
		Variant v = sut.get("", Variant.class);
		BigDecimal[] actual = (BigDecimal[]) v.getValue();
		BigDecimal[] expected = new BigDecimal[] {BigDecimal.valueOf(342742334224L, 1), BigDecimal.valueOf(342742334224L, 2)};
		assertArrayEquals(expected, actual);
	}
	
}
