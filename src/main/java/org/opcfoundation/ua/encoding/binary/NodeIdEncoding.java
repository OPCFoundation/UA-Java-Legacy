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

package org.opcfoundation.ua.encoding.binary;

import org.opcfoundation.ua.core.IdType;

/**
 * NodeId binary encoding byte.
 */
public enum NodeIdEncoding {

	  TwoByte((byte) 0x00, IdType.Numeric), 
	  FourByte((byte) 0x01, IdType.Numeric), 
	  Numeric((byte) 0x02, IdType.Numeric), 
	  String((byte) 0x03, IdType.String), 
	  Guid((byte) 0x04, IdType.Guid), 
	  ByteString((byte) 0x05, IdType.String);

	  private final byte bits;
	  private final IdType identifierType;

	  private NodeIdEncoding(byte bits, IdType identifierType) {
	    this.bits = bits;
	    this.identifierType = identifierType;
	  }

	  /**
	   * <p>Getter for the field <code>bits</code>.</p>
	   *
	   * @return a byte.
	   */
	  public byte getBits() {
	    return bits;
	  }
	  
	  /**
	   * <p>toIdentifierType.</p>
	   *
	   * @return a {@link org.opcfoundation.ua.core.IdType} object.
	   */
	  public IdType toIdentifierType()
	  {
	      return identifierType; 
	  }

	  /**
	   * <p>getNodeIdEncoding.</p>
	   *
	   * @param bits a int.
	   * @return a {@link org.opcfoundation.ua.encoding.binary.NodeIdEncoding} object.
	   */
	  public static NodeIdEncoding getNodeIdEncoding(int bits) {
	    if (bits == TwoByte.getBits()) {
	      return TwoByte;
	    } else if (bits == FourByte.getBits()) {
	      return FourByte;
	    } else if (bits == Numeric.getBits()) {
	      return Numeric;
	    } else if (bits==String.getBits()) {
	    	return String;
	    } else if (bits == Guid.getBits()) {
	      return Guid;
	    } else if (bits == ByteString.getBits()) {
	      return ByteString;
	    } else
	      return null;
	  }

}
