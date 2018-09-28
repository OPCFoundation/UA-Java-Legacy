package org.opcfoundation.ua.encoding.binary;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

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
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		BinaryEncoder enc = new BinaryEncoder(buf);
		enc.putDataValue(null, dv);
		
		BinaryDecoder dec = new BinaryDecoder(buf.toByteArray());
		final DataValue dv2 = dec.getDataValue(null);
		
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
	
	@Test
	public void decimalEncodingWithinVariant() throws Exception {
		long value = 1518632738243L; //random number
		short scale = 4;
		BigDecimal bd = BigDecimal.valueOf(value, scale);
		Variant v = new Variant(bd);
		byte[] data = binaryEncode(v);
		
		//Verify via decoding
		BinaryDecoder dec = new BinaryDecoder(data);
		dec.setEncoderContext(EncoderContext.getDefaultInstance());
		Variant actual = dec.get(null, Variant.class);
		assertEquals(v, actual);
	}
	
	@Test
	public void decimalArrayEncoding() throws Exception {
		long value = 1518632738243L;
		ArrayList<BigDecimal> data = new ArrayList<BigDecimal>();
		for(short i=0;i<10;i++) {
			data.add(BigDecimal.valueOf(value, i));
		}
		BigDecimal[] datain = data.toArray(new BigDecimal[0]);
		byte[] dataout = binaryEncode(datain);
		
		//validate by decoding
		BinaryDecoder dec = new BinaryDecoder(dataout);
		dec.setEncoderContext(EncoderContext.getDefaultInstance());
		BigDecimal[] decoded = dec.get(null, BigDecimal[].class);
		assertArrayEquals(data.toArray(), decoded);
	}
	
	@Test
	public void decimalArrayWithinVariantEncoding() throws Exception {
		long value = 1518632738243L;
		ArrayList<BigDecimal> data = new ArrayList<BigDecimal>();
		for(short i=0;i<10;i++) {
			data.add(BigDecimal.valueOf(value, i));
		}
		BigDecimal[] decimals = data.toArray(new BigDecimal[0]);
		Variant expected = new Variant(decimals);
		
		byte[] dataout = binaryEncode(expected);
		
		//validate by decoding
		BinaryDecoder dec = new BinaryDecoder(dataout);
		dec.setEncoderContext(EncoderContext.getDefaultInstance());
		Variant actual = dec.get(null, Variant.class);
		assertEquals(expected, actual);
	}
	
	private byte[] binaryEncode(Object o) throws Exception{
		ByteArrayOutputStream r = new ByteArrayOutputStream();
		BinaryEncoder enc = new BinaryEncoder(r);
		enc.setEncoderContext(EncoderContext.getDefaultInstance());
		enc.put(null, o);
		return r.toByteArray();
	}
	
}
