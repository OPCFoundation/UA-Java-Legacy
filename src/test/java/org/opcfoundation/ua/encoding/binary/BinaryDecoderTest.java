package org.opcfoundation.ua.encoding.binary;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.encoding.EncoderContext;

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
	
}
