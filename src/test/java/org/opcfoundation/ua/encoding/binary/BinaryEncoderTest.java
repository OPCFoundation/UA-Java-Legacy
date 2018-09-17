package org.opcfoundation.ua.encoding.binary;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.utils.CryptoUtil;

public class BinaryEncoderTest {

	@Test
	public void encodeDecodeDataValue() throws Exception {
		//For GH#112
		//Test that encoded and then decoded DataValue are equal
		//NOTE this should be later refactored to check the raw binary form as well
		
		//Setting values that force encoding, i.e. not default values that are handed by the encoding mask
		DateTime dt = DateTime.fromMillis(100000); //some time that is not min or max
		UnsignedShort tenPicoSeconds = UnsignedShort.valueOf(10); //100 picoseconds, something that is not 0
		final DataValue dv = new DataValue(new Variant(Integer.valueOf(1)), StatusCode.BAD, dt, tenPicoSeconds, dt, tenPicoSeconds);
		EncoderCalc calc = new EncoderCalc();
		calc.putDataValue(null, dv);
		int len = calc.length;
		byte[] buf = new byte[len];
		BinaryEncoder enc = new BinaryEncoder(buf);
		enc.putDataValue(null, dv);
		
		//System.out.println(CryptoUtil.toHex(buf)); 
		
		BinaryDecoder dec = new BinaryDecoder(buf);
		final DataValue dv2 = dec.getDataValue(null);
		
		//System.out.println(dv);
		//System.out.println(dv2);
		assertEquals(dv, dv2);
	}
	
	@Test
	public void decimalEncoding() throws Exception {
		long value = 1518632738243L; //random number
		short scale = 4;
		BigDecimal bd = BigDecimal.valueOf(value, scale);
		byte[] data = binaryEncode(bd);
		
		// Java BigInteger only has enough bytes needed, it is less than 8 in this case vs. Int64
		byte[] expected = CryptoUtil.hexToBytes("003201080000000400c39d90956101");
		assertArrayEquals(expected, data);
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
