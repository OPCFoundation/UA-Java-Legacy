package org.opcfoundation.ua.builtintypes;

import static org.junit.Assert.*;

import org.junit.Test;

public class ByteStringTest {

  @Test
  public void testByteStringConstructorValueCopy() throws Exception {
    //tests that the constructor of ByteString does copy the array
    byte[] changing = new byte[]{1,2,3,4,5,6,7,8,9};
    byte[] original = new byte[]{1,2,3,4,5,6,7,8,9};
    ByteString bs1 = ByteString.valueOf(changing);
    ByteString bs2 = ByteString.valueOf(original);
    
    //change the bs1 input data -- should not effect it
    changing[0] = 10;
    assertEquals(bs1, bs2);
  }
  
  @Test
  public void testByteStringEquals() throws Exception {
    byte[] b1 = new byte[]{1,2,3,4,5,6,7,8,9};
    byte[] b2 = new byte[]{1,2,3,4,5,6,7,8,9};
    
    byte[] b3 = new byte[]{2,2,3,4,5,6,7,8,9};//1->2
    ByteString bs1 = ByteString.valueOf(b1);
    ByteString bs2 = ByteString.valueOf(b2);
    ByteString bs3 = ByteString.valueOf(b3);
    
    assertEquals(bs1, bs2);    
    assertNotEquals(bs1, bs3);    
  }
  
  @Test
  public void testByteStringHashCode() throws Exception {
    byte[] b1 = new byte[]{1,2,3,4,5,6,7,8,9};
    byte[] b2 = new byte[]{1,2,3,4,5,6,7,8,9};
    
    ByteString bs1 = ByteString.valueOf(b1);
    ByteString bs2 = ByteString.valueOf(b2);
    
    //hashcodes should match
    assertEquals(bs1.hashCode(), bs2.hashCode());
  }
  
  @Test
  public void testByteStringReturnCopy() throws Exception {
    //tests that returned byte array is different java object
    
    byte[] b = new byte[]{1,2,3,4,5,6,7,8,9};
    ByteString bs = ByteString.valueOf(b);
    assertNotSame(bs.getValue(), b);
    
  }
  
  @Test
  public void testByteStringValueReturnIsCopy() throws Exception {
    // Ensures that the output value returned from ByteString is a copy
    // and not the internal state
    byte[] b1 = new byte[]{1,2,3,4,5,6,7,8,9};
    byte[] b2 = new byte[]{1,2,3,4,5,6,7,8,9};

    ByteString bs1 = ByteString.valueOf(b1);
    ByteString bs2 = ByteString.valueOf(b2);
    
    byte[] r = bs1.getValue();
    r[0] = 10;
    
    assertEquals(bs1, bs2);
    
  }

  @Test
  public void testToString() throws Exception {
    byte[] b1 = new byte[]{1,2,3,4,5,6,7,8,110};
    ByteString bs = ByteString.valueOf(b1);
    System.out.println(bs);
  }
  
  
}
