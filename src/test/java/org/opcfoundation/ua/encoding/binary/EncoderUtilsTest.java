package org.opcfoundation.ua.encoding.binary;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Ignore;
import org.junit.Test;
import org.opcfoundation.ua.core.NodeAttributes;
import org.opcfoundation.ua.core.ObjectAttributes;
import org.opcfoundation.ua.core.ServerState;
import org.opcfoundation.ua.core.ServerStatusDataType;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IEncoder;

public class EncoderUtilsTest {
	
	@Test(expected=EncodingException.class)
	public void testNull() throws Exception {
		IEncoder encoder = mock(IEncoder.class);
		Boolean test = null;
		
		EncoderUtils.put(encoder, "Test", test);
	}
	
	@Test
	@Ignore
	public void testStructureWithoutClass() throws Exception {
		/*
		 * Because of refactorings to EncoderUtils, this cannot be tested as
		 * it does delegate to the version taking the class parameter.
		 */
		
		IEncoder encoder = mock(IEncoder.class);
		ServerStatusDataType test = new ServerStatusDataType();
		
		EncoderUtils.put(encoder, "Test", test);
		
		verify(encoder).putEncodeable("Test", test);
	}
	
	@Test
	public void testStructureWithClass() throws Exception {
		IEncoder encoder = mock(IEncoder.class);
		ServerStatusDataType test = new ServerStatusDataType();
		
		EncoderUtils.put(encoder, "Test", test, ServerStatusDataType.class);
		
		verify(encoder).putEncodeable("Test", ServerStatusDataType.class, test);
	}
	
	@Test
	public void testStructureArrayWithClass() throws Exception {
		IEncoder encoder = mock(IEncoder.class);
		ServerStatusDataType[] test = new ServerStatusDataType[0];
		
		EncoderUtils.put(encoder, "Test", test, ServerStatusDataType[].class);

		verify(encoder).putEncodeableArray("Test", ServerStatusDataType.class, test);
	}
	
	@Test
	public void testEnumerationArrayWithClass() throws Exception {
		IEncoder encoder = mock(IEncoder.class);
		ServerState[] test = new ServerState[0];
		
		EncoderUtils.put(encoder, "Test", test, ServerState[].class);

		verify(encoder).putEnumerationArray("Test", test);
	}
	
	@Test
	public void testCorrectStructureTypeSerialization() throws Exception {
		IEncoder encoder = mock(IEncoder.class);
		ObjectAttributes test = new ObjectAttributes();
		
		//If EncoderUtils is asked to put a Structure by .class, 
		//then it should delegate with that class and not the class
		//of the object given
		EncoderUtils.put(encoder, "Test", test, NodeAttributes.class);
		verify(encoder).putEncodeable("Test", NodeAttributes.class, test);
	}
	
	@Test
	public void testBoolean() throws Exception {
		IEncoder encoder = mock(IEncoder.class);
		Boolean test = Boolean.TRUE;
		
		EncoderUtils.put(encoder, "Test", test);

		verify(encoder).putBoolean("Test", test);
	}
	
	@Test
	public void testBooleanClass() throws Exception {
		IEncoder encoder = mock(IEncoder.class);
		Boolean test = Boolean.TRUE;
		
		EncoderUtils.put(encoder, "Test", test, Boolean.class);

		verify(encoder).putBoolean("Test", test);
	}
	
	@Test
	public void reverseNullElement() throws Exception {
		byte[] actual = EncoderUtils.reverse(null);
		assertNull(actual);
	}
	
	@Test
	public void reverse0Element() throws Exception {
		byte[] d = new byte[] {};
		byte[] expected = new  byte[] {};
		byte[] actual = EncoderUtils.reverse(d);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void reverse1Element() throws Exception {
		byte[] d = new byte[] {1};
		byte[] expected = new  byte[] {1};
		byte[] actual = EncoderUtils.reverse(d);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void reverse2Element() throws Exception {
		byte[] d = new byte[] {1,2};
		byte[] expected = new  byte[] {2,1};
		byte[] actual = EncoderUtils.reverse(d);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void reverse3Element() throws Exception {
		byte[] d = new byte[] {1,2,3};
		byte[] expected = new  byte[] {3,2,1};
		byte[] actual = EncoderUtils.reverse(d);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void reverse4Element() throws Exception {
		byte[] d = new byte[] {1,2,3,4};
		byte[] expected = new  byte[] {4,3,2,1};
		byte[] actual = EncoderUtils.reverse(d);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void concat() throws Exception {
		byte[] f = new byte[] {1,2};
		byte[] s = new byte[] {3,4};
		byte[] expected = new byte[] {1,2,3,4};
		byte[] actual = EncoderUtils.concat(f, s);
		assertArrayEquals(expected, actual);
	}
	
}
