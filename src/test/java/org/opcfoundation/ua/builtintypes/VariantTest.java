package org.opcfoundation.ua.builtintypes;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.opcfoundation.ua.core.ServerState;

public class VariantTest {
  
  @Test
  public void testEnumerations() throws Exception {
    Variant sut = new Variant(ServerState.Running);
    assertEquals(Integer.valueOf(ServerState.Running.getValue()), sut.getValue());
  }
  
  @Test
  public void testEnumerationsArray() throws Exception {
    ServerState[] data = new ServerState[]{ServerState.Running, ServerState.Running};
    Variant sut = new Variant(data);
    
    Integer[] expected = new Integer[]{ServerState.Running.getValue(), ServerState.Running.getValue()};
    Integer[] actual = (Integer[]) sut.getValue();
    
    assertArrayEquals(expected, actual);    
  }
  
  @Test
  public void testEnumerationsArrayWithNull() throws Exception {
    ServerState[] data = new ServerState[]{ServerState.Running, null};
    Variant sut = new Variant(data);
    
    Integer[] expected = new Integer[]{ServerState.Running.getValue(), null};
    Integer[] actual = (Integer[]) sut.getValue();
    
    assertArrayEquals(expected, actual);    
  }
  
  @Test
  public void testEnumerationsArray2D() throws Exception {
    ServerState[] d1 = new ServerState[]{ServerState.Running, ServerState.Running};
    ServerState[] d2 = new ServerState[]{ServerState.Running, ServerState.Failed};
    ServerState[][] data = new ServerState[][]{d1,d2};
    
    Variant sut = new Variant(data);
    
    Integer[] e1 = new Integer[]{ServerState.Running.getValue(), ServerState.Running.getValue()};
    Integer[] e2 = new Integer[]{ServerState.Running.getValue(), ServerState.Failed.getValue()};
    Integer[][] expected = new Integer[][]{e1,e2};
    
    Integer[][] actual = (Integer[][]) sut.getValue();
    assertTrue(Arrays.deepEquals(expected, actual));
  }
  
  @Test
  public void testEnumerationsArray2DWithNull() throws Exception {
    ServerState[] d1 = new ServerState[]{ServerState.Running, ServerState.Running};
    ServerState[] d2 = new ServerState[]{ServerState.Running, null};
    ServerState[][] data = new ServerState[][]{d1,d2};
    
    Variant sut = new Variant(data);
    
    Integer[] e1 = new Integer[]{ServerState.Running.getValue(), ServerState.Running.getValue()};
    Integer[] e2 = new Integer[]{ServerState.Running.getValue(), null};
    Integer[][] expected = new Integer[][]{e1,e2};

    Integer[][] actual = (Integer[][]) sut.getValue();
    assertTrue(Arrays.deepEquals(expected, actual));
  }
  
  @Test
  public void testByteArrayByteString() throws Exception {
    //tests that when given byte[] to Variant, equivalent ByteString is received
    byte[] ba = new byte[]{1,2,3,4,5,6,7,8,9};
    
    Variant sut = new Variant(ba);
    ByteString expected = ByteString.valueOf(ba);
    ByteString actual = (ByteString) sut.getValue();
    assertEquals(expected, actual);
  }
  
  @Test
  public void testByteArrayByteStringArray() throws Exception {
    //test given byte[][] to Variant equivalent ByteString[] is received
    byte[] ba1 = new byte[]{1,2,3,4,5,6,7,8,9};
    byte[] ba2 = new byte[]{1,2,3,4,5,6,7,8,9};
    byte[][] ba = new byte[][]{ba1,ba2};
    
    Variant sut = new Variant(ba);
    
    ByteString[] actual = (ByteString[]) sut.getValue();
    ByteString[] expected = new ByteString[]{ByteString.valueOf(ba1), ByteString.valueOf(ba2)};
    assertArrayEquals(expected, actual);
  }
  
  @Test
  public void testByteArrayByteStringArray2D() throws Exception {
    //test given byte[][][] to Variant equivalent ByteString[][] is received
    byte[] ba1 = new byte[]{1,2,3,4,5,6,7,8,9};
    byte[] ba2 = new byte[]{1,2,3,4,5,6,7,8,9};
    byte[][] barr1 = new byte[][]{ba1,ba2};
    byte[] ba3 = new byte[]{1,2,3,4,5,6,7,8,9};
    byte[] ba4 = new byte[]{1,2,3,4,5,6,7,8,9};
    byte[][] barr2 = new byte[][]{ba3,ba4};
    byte[][][] ba = new byte[][][]{barr1, barr2};
    
    Variant sut = new Variant(ba);
    
    ByteString[] e1 = new ByteString[]{ByteString.valueOf(ba1), ByteString.valueOf(ba2)};
    ByteString[] e2 = new ByteString[]{ByteString.valueOf(ba3), ByteString.valueOf(ba4)};
    ByteString[][] expected = new ByteString[][]{e1,e2};
    
    ByteString[][] actual = (ByteString[][]) sut.getValue();
    
    assertTrue(Arrays.deepEquals(expected, actual));
  }
  
  @Test
  public void testVariantAsEnumNull() throws Exception {
    Variant sut = new Variant(null);
    assertNull(sut.asEnum(ServerState.class));
  }
  
  @Test(expected= ClassCastException.class)
  public void testVariantAsEnumInvalidClass() throws Exception {
    Variant sut = new Variant(UnsignedInteger.valueOf(0));
    sut.asEnum(ServerState.class);
  }
  
  @Test
  public void testVariantAsEnumInvalidEnumValue() throws Exception {
    Variant sut = new Variant(10);
    assertNull(sut.asEnum(ServerState.class));
  }
  
  @Test
  public void testVariantAsEnumScalar() throws Exception {
    Variant sut = new Variant(ServerState.Running);
    assertEquals(ServerState.Running, sut.asEnum(ServerState.class));
  }
  
  @Test
  public void testVariantAsEnumArray() throws Exception {
    ServerState[] data = new ServerState[]{ServerState.Running, ServerState.Running};
    Variant sut = new Variant(data);
    
    ServerState[] actual = (ServerState[]) sut.asEnum(ServerState.class);
    assertArrayEquals(data, actual);    
  }
  
  @Test
  public void testVariantAsEnumArray2D() throws Exception {
    ServerState[] d1 = new ServerState[]{ServerState.Running, ServerState.Running};
    ServerState[] d2 = new ServerState[]{ServerState.Running, ServerState.Failed};
    ServerState[][] data = new ServerState[][]{d1,d2};
    
    Variant sut = new Variant(data);
    
    ServerState[][] actual = (ServerState[][]) sut.asEnum(ServerState.class);
    assertTrue(Arrays.deepEquals(data, actual));
  }
  
  @Test
  public void testVariantAsEnumArray2DWithNull() throws Exception {
    ServerState[] d1 = new ServerState[]{ServerState.Running, ServerState.Running};
    ServerState[] d2 = new ServerState[]{ServerState.Running, null};
    ServerState[][] data = new ServerState[][]{d1,d2};
    
    Variant sut = new Variant(data);
    
    ServerState[][] actual = (ServerState[][]) sut.asEnum(ServerState.class);
    assertTrue(Arrays.deepEquals(data, actual));
  }
  
}
