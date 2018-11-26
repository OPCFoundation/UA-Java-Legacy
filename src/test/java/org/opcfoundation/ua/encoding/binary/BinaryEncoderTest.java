package org.opcfoundation.ua.encoding.binary;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.NodeAttributes;
import org.opcfoundation.ua.core.ObjectAttributes;
import org.opcfoundation.ua.core.ServerState;
import org.opcfoundation.ua.core.ServerStatusDataType;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.EncodingException;
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
	public void unknownBuiltInTypeId() throws Exception {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		BinaryEncoder enc = new BinaryEncoder(buf);
		for(int i=26; i<64;i++) {
			try {
				enc.putScalar(null, i, "dataTypeDoesNotMatter");
				fail("Should have throw exception");
			}catch(EncodingException e) {
				// expected
			}
			try {
				enc.putArray(null, i, "dataTypeDoesNotMatter");
				fail("Should have throw exception");
			}catch (EncodingException e) {
				// expected
			}
		}
	}
	
	@Test
	public void objectClassEncoderPut() throws Exception {
		Integer number = 438975789;
		byte[] expected = binaryEncode(new Variant(number));
		
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		BinaryEncoder sut = new BinaryEncoder(buf);
		sut.put(null, number, Object.class);
		
		byte[] actual = buf.toByteArray();
		assertArrayEquals(expected, actual);
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
	public void verifyBooleanMultidim() throws Exception {
		BinaryEncoder enc = spy(new BinaryEncoder(new ByteArrayOutputStream()));
		enc.setEncoderContext(EncoderContext.getDefaultInstance());
		Boolean[][] test = new Boolean[2][2];
		for(int i = 0;i<2;i++) {
			for(int j = 0; j<2; j++) {
				test[i][j] = Boolean.TRUE;
			}
		}
		enc.put("Test", test);
		verify(enc, times(4)).putBoolean(null, Boolean.TRUE);
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
	
	@Test
	public void verifyPutStructureCallsPutEncodeable() throws Exception {
		BinaryEncoder enc = spy(new BinaryEncoder(new ByteArrayOutputStream()));
		enc.setEncoderContext(EncoderContext.getDefaultInstance());
		ServerStatusDataType test = new ServerStatusDataType();
		enc.put("Test", test, ServerStatusDataType.class);
		verify(enc).putEncodeable("Test", ServerStatusDataType.class, test);
	}
	
	@Test
	public void verifyStructureArray() throws Exception {
		BinaryEncoder enc = spy(new BinaryEncoder(new ByteArrayOutputStream()));
		enc.setEncoderContext(EncoderContext.getDefaultInstance());
		ServerStatusDataType[] test = new ServerStatusDataType[2];
		ServerStatusDataType t1 = new ServerStatusDataType();
		ServerStatusDataType t2 = new ServerStatusDataType();
		test[0] = t1;
		test[1] = t2;
		enc.put("Test", test, ServerStatusDataType[].class);
		ArgumentCaptor<ServerStatusDataType> cap = ArgumentCaptor.forClass(ServerStatusDataType.class);
		verify(enc, times(2)).putEncodeable(eq((String)null), eq(ServerStatusDataType.class), cap.capture());
		List<ServerStatusDataType> caps = cap.getAllValues();
		assertSame(t1, caps.get(0));
		assertSame(t2, caps.get(1));
	}
	
	@Test
	public void verifyEnumerationArrayWithClass() throws Exception {
		BinaryEncoder enc = spy(new BinaryEncoder(new ByteArrayOutputStream()));
		enc.setEncoderContext(EncoderContext.getDefaultInstance());
		ServerState[] test = new ServerState[2];
		test[0] = ServerState.Running;
		test[1] = ServerState.Shutdown;
		enc.put("Test", test, ServerState[].class);
		ArgumentCaptor<ServerState> cap = ArgumentCaptor.forClass(ServerState.class);
		verify(enc, times(2)).putEnumeration(eq((String)null), cap.capture());
		List<ServerState> caps = cap.getAllValues();
		assertSame(ServerState.Running, caps.get(0));
		assertSame(ServerState.Shutdown, caps.get(1));
	}
	
	@Test
	public void verifyCorrectStructureClassUsed() throws Exception {
		/*
		 * Point of this test is to ensure the given Class parameter
		 * is used when determining the Structure serializer,
		 * and not a potential subclass from the Object.
		 * 
		 */
		BinaryEncoder enc = spy(new BinaryEncoder(new ByteArrayOutputStream()));
		enc.setEncoderContext(EncoderContext.getDefaultInstance());
		ObjectAttributes test = new ObjectAttributes();
		enc.put("Test", test, NodeAttributes.class);
		verify(enc).putEncodeable("Test", NodeAttributes.class, test);
	}
	
	@Test
	public void verifyBooleanWithoutClass() throws Exception {
		BinaryEncoder enc = spy(new BinaryEncoder(new ByteArrayOutputStream()));
		Boolean test = Boolean.TRUE;
		enc.put("Test", test);
		verify(enc).putBoolean("Test", test);
	}
	
	@Test
	public void verifyBooleanWithtClass() throws Exception {
		BinaryEncoder enc = spy(new BinaryEncoder(new ByteArrayOutputStream()));
		Boolean test = Boolean.TRUE;
		enc.put("Test", test, Boolean.class);
		verify(enc).putBoolean("Test", test);
	}
	
	private byte[] binaryEncode(Object o) throws Exception{
		ByteArrayOutputStream r = new ByteArrayOutputStream();
		BinaryEncoder enc = new BinaryEncoder(r);
		enc.setEncoderContext(EncoderContext.getDefaultInstance());
		enc.put(null, o);
		return r.toByteArray();
	}
	
}
