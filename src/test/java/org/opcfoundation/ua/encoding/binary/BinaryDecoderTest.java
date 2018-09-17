package org.opcfoundation.ua.encoding.binary;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.NamespaceTable;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.utils.CryptoUtil;

public class BinaryDecoderTest {
	
	@Test
	public void testExpandedNodeIdTwoByte() throws Exception{
		ExpandedNodeId data = new ExpandedNodeId(null, 0, 128);
		
		EncoderContext ctx = EncoderContext.getDefaultInstance();
		EncoderCalc calc = new EncoderCalc();
		calc.setEncoderContext(ctx);
		calc.putExpandedNodeId(null, data);
		int len = calc.length;
		
		byte[] buf = new byte[len];
		BinaryEncoder enc = new BinaryEncoder(buf);
		enc.setEncoderContext(ctx);
		enc.putExpandedNodeId(null, data);
		
		BinaryDecoder sut = new BinaryDecoder(buf);
		sut.setEncoderContext(ctx);
		ExpandedNodeId actual = sut.getExpandedNodeId(null);
		assertEquals(data, actual);
		
	}
	
	@Test
	public void testExpandedNodeIdFourByte() throws Exception{
		ExpandedNodeId data = new ExpandedNodeId(null, 0, 33000);
		
		EncoderContext ctx = EncoderContext.getDefaultInstance();
		EncoderCalc calc = new EncoderCalc();
		calc.setEncoderContext(ctx);
		calc.putExpandedNodeId(null, data);
		int len = calc.length;
		
		byte[] buf = new byte[len];
		BinaryEncoder enc = new BinaryEncoder(buf);
		enc.setEncoderContext(ctx);
		enc.putExpandedNodeId(null, data);
		
		BinaryDecoder sut = new BinaryDecoder(buf);
		sut.setEncoderContext(ctx);
		ExpandedNodeId actual = sut.getExpandedNodeId(null);
		assertEquals(data, actual);

	}
	
	@Test
	public void testNodeIdTwoByte() throws Exception{
		NodeId data = new NodeId(0, 128);
		
		EncoderContext ctx = EncoderContext.getDefaultInstance();
		EncoderCalc calc = new EncoderCalc();
		calc.setEncoderContext(ctx);
		calc.putNodeId(null, data);
		int len = calc.length;
		
		byte[] buf = new byte[len];
		BinaryEncoder enc = new BinaryEncoder(buf);
		enc.setEncoderContext(ctx);
		enc.putNodeId(null, data);
		
		BinaryDecoder sut = new BinaryDecoder(buf);
		sut.setEncoderContext(ctx);
		NodeId actual = sut.getNodeId(null);
		assertEquals(data, actual);
		
	}
	
	@Test
	public void testNodeIdFourByte() throws Exception{
		NodeId data = new NodeId(0, 33000);
		
		EncoderContext ctx = EncoderContext.getDefaultInstance();
		EncoderCalc calc = new EncoderCalc();
		calc.setEncoderContext(ctx);
		calc.putNodeId(null, data);
		int len = calc.length;
		
		byte[] buf = new byte[len];
		BinaryEncoder enc = new BinaryEncoder(buf);
		enc.setEncoderContext(ctx);
		enc.putNodeId(null, data);
		
		BinaryDecoder sut = new BinaryDecoder(buf);
		sut.setEncoderContext(ctx);
		NodeId actual = sut.getNodeId(null);
		assertEquals(data, actual);

	}
	
	@Test
	public void decimalDecoding() throws Exception{
		long value = 1518632738243L; //random number
		short scale  = 4;
		
		//encoded form per spec
		//1.TypeId, NodeId, the NodeId for Decimal DataType node
		//2. Encoding, byte, always 1 as this fakes Structure with binary encodings
		//3. Lenght, Int32, "lenght of the Decimal", i.e. encoded body in fake Structures
		//4. Scale, Int16, included in lenght
		//5. Value, Byte array, size len - 2 bytes (of the scale)
		
		byte[] scalebytes = binaryEncode(scale);
		byte[] valuebytes = binaryEncode(value);
		System.out.println("valuebits:" + CryptoUtil.toHex(valuebytes));
		byte[] combinedbytes = EncoderUtils.concat(scalebytes, valuebytes);
		System.out.println("combined bytes len: "+combinedbytes.length);
		ExpandedNodeId id = new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, Identifiers.Decimal.getValue());
		ExtensionObject eo = new ExtensionObject(id, combinedbytes);
		byte[] completebytes = binaryEncode(eo);
		byte[] completedexpected = CryptoUtil.hexToBytes("0032010a0000000400c39d909561010000");
		assertArrayEquals(completedexpected, completebytes);
		
		//Decoding
		BinaryDecoder sut = new BinaryDecoder(completebytes);
		sut.setEncoderContext(EncoderContext.getDefaultInstance());
		BigDecimal bd = sut.get(null, BigDecimal.class);
		BigDecimal expected = BigDecimal.valueOf(value, scale);
		assertEquals(expected, bd);
	}
	
	@Test
	public void decimalWithinVariantDecoding() throws Exception {
		long value = 1518632738243L;
		short scale = 5;
		BigDecimal expected = BigDecimal.valueOf(value, scale);
		ExpandedNodeId id = new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, Identifiers.Decimal.getValue());
		ExtensionObject eo = new ExtensionObject(id, createDecimalAsEncodedBytes(value, scale));
		Variant veo = new Variant(eo);
		byte[] datainput = binaryEncode(veo);
		
		//Decoding
		BinaryDecoder sut = new BinaryDecoder(datainput);
		sut.setEncoderContext(EncoderContext.getDefaultInstance());
		Variant v = sut.get(null, Variant.class);
		BigDecimal actual = (BigDecimal) v.getValue();
		assertEquals(expected, actual);
	}
	
	@Test
	public void decimalArrayWithinVariantDecoding() throws Exception {
		long value = 1518632738243L;
		ExpandedNodeId id = new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, Identifiers.Decimal.getValue());
		ArrayList<ExtensionObject> eos = new ArrayList<ExtensionObject>();
		ArrayList<BigDecimal> expecteds = new ArrayList<BigDecimal>();
		for(short i=0;i<10;i++) {
			eos.add((new ExtensionObject(id, createDecimalAsEncodedBytes(value, i))));
			expecteds.add(BigDecimal.valueOf(value, i));
		}
		ExtensionObject[] eoarr = eos.toArray(new ExtensionObject[0]);
		byte[] datainput = binaryEncode(new Variant(eoarr));
		
		//Decoding
		BinaryDecoder sut = new BinaryDecoder(datainput);
		sut.setEncoderContext(EncoderContext.getDefaultInstance());
		Variant output = sut.get(null, Variant.class);
		Variant expected = new Variant(expecteds.toArray(new BigDecimal[0]));
		assertEquals(expected, output);
	}
	
	@Test
	public void decimalArrayDecoding() throws Exception {
		long value = 1518632738243L;
		ExpandedNodeId id = new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, Identifiers.Decimal.getValue());
		ArrayList<ExtensionObject> eos = new ArrayList<ExtensionObject>();
		ArrayList<BigDecimal> expecteds = new ArrayList<BigDecimal>();
		for(short i=0;i<10;i++) {
			eos.add(new ExtensionObject(id, createDecimalAsEncodedBytes(value, i)));
			expecteds.add(BigDecimal.valueOf(value, i));
		}
		ExtensionObject[] eosarr = eos.toArray(new ExtensionObject[0]);
		byte[] datainput = binaryEncode(eosarr);
		
		//Decoding
		BinaryDecoder sut = new BinaryDecoder(datainput);
		sut.setEncoderContext(EncoderContext.getDefaultInstance());
		BigDecimal[] output = sut.get(null, BigDecimal[].class);
		assertArrayEquals(expecteds.toArray(), output);
	}
	
	private byte[] createDecimalAsEncodedBytes(long valueraw, short scale) throws Exception{
		byte[] scalebytes = binaryEncode(scale);
		byte[] valuebytes = binaryEncode(valueraw);
		return EncoderUtils.concat(scalebytes, valuebytes);
	}

	
	private byte[] binaryEncode(Object o) throws Exception{
		EncoderCalc calc = new EncoderCalc();
		calc.setEncoderContext(EncoderContext.getDefaultInstance());
		calc.put(null, o);
		int len = calc.length;
		byte[] buf = new byte[len];
		BinaryEncoder enc = new BinaryEncoder(buf);
		enc.setEncoderContext(EncoderContext.getDefaultInstance());
		enc.put(null, o);
		return buf;
	}
	
}
