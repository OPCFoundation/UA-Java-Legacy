/* Copyright (c) 1996-2015, OPC Foundation. All rights reserved.
   The source code in this file is covered under a dual-license scenario:
     - RCL: for OPC Foundation members in good-standing
     - GPL V2: everybody else
   RCL license terms accompanied with this source code. See http://opcfoundation.org/License/RCL/1.00/
   GNU General Public License as published by the Free Software Foundation;
   version 2 of the License are accompanied with this source code. See http://opcfoundation.org/License/GPLv2
   This source code is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
*/

package org.opcfoundation.ua.builtintypes;

import java.util.Arrays;

import org.opcfoundation.ua.utils.CryptoUtil;

/**
 * This primitive DataType specifies a ByteString, similar as String but for bytes.
 * Class is final because it is an immutable value object.
 * 
 * Also the constructor is private to avoid situations where the given value would be null,
 * now the static factory methods returns null if given ByteString Null equivalent byte[].
 */
public final class ByteString implements Comparable<ByteString>{

  /**
   * A null-safe way to convert ByteString to byte[]. 
   * The given array is copied for value.
   * 
   * @param byteString a ByteString or null
   * @return byte[] or null if given null
   */
  public static byte[] asByteArray(ByteString byteString){
    return byteString == null ? null : byteString.getValue();
  }
  
  /**
   * Creates a ByteString from byte array.
   * 
   * @param byteArray the byte array
   * @return new ByteString or null if given null array
   */
  public static ByteString valueOf(byte... byteArray){
    if(byteArray == null){
      return null;
    }
    if(byteArray.length == 0){
      return EMPTY;
    }
    return new ByteString(byteArray);
  }
  
  /**
   * ByteString instance that models empty ByteString.
   */
  public static final ByteString EMPTY = new ByteString(new byte[0]);
  
  private final byte[] value;
  
  /**
   * Create new ByteString from an array of bytes. 
   * NOTE! the given array is copied.
   * 
   * @param value value, shall not be null
   */
  private ByteString(byte[] value) {
    this.value = Arrays.copyOf(value, value.length);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ByteString other = (ByteString) obj;
    if (!Arrays.equals(value, other.value))
      return false;
    return true;
  }

  /**
   * Get a copy of the value of this ByteString as byte array.
   * 
   * @return copy of the value as byte array. Not null.
   */
  public byte[] getValue(){
    return Arrays.copyOf(value, value.length);
  }
  
  /**
   * Return the length of this ByteString.
   * 
   * @return
   */
  public int getLength(){
    return value.length;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(value);
    return result;
  }

  @Override
  public String toString() {
    return CryptoUtil.toHex(value, 0);
  }

@Override
public int compareTo(ByteString o) {
	if(this.equals(o)){
		return 0;
	}
	//best effort, at least keeps ordering
	//Note that per Comparable docs, this should throw NPE if o is null
	return this.toString().compareTo(o.toString());
}
  
}
