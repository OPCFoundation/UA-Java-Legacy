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

package org.opcfoundation.ua.utils.bytebuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * Writeable context
 *
 * @see OutputStreamWriteable
 * @see ByteBufferWriteable
 * @see ByteBufferArrayWriteable
 * @see ByteBufferArrayWriteable2
 * @see ByteBufferArrayWriteable2
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public interface IBinaryWriteable {

    /**
     * <p>put.</p>
     *
     * @param b a byte.
     * @throws java.io.IOException if any.
     */
    void put(byte b)
    throws IOException;
    
    /**
     * Put n bytes from the remaining of the byte array.
     * This operation moves the pointer in byte buffer.
     *
     * @param src a {@link java.nio.ByteBuffer} object.
     * @throws java.io.IOException if any.
     */
    void put(ByteBuffer src)
    throws IOException;
    
    /**
     * Put n bytes from the remaining of the byte buffer.
     * This operation moves the pointer in byte buffer.
     *
     * @param src a {@link java.nio.ByteBuffer} object.
     * @param length a int.
     * @throws java.io.IOException if any.
     */
    void put(ByteBuffer src, int length)
    throws IOException;
    
    /**
     * <p>put.</p>
     *
     * @param src an array of byte.
     * @param offset a int.
     * @param length a int.
     * @throws java.io.IOException if any.
     */
    void put(byte[] src, int offset, int length)
    throws IOException;
    
    /**
     * <p>put.</p>
     *
     * @param src an array of byte.
     * @throws java.io.IOException if any.
     */
    void put(byte[] src)
    throws IOException;
    
    /**
     * <p>putShort.</p>
     *
     * @param value a short.
     * @throws java.io.IOException if any.
     */
    void putShort(short value)
    throws IOException;
    
    /**
     * <p>putInt.</p>
     *
     * @param value a int.
     * @throws java.io.IOException if any.
     */
    void putInt(int value)
    throws IOException;
    
    /**
     * <p>putLong.</p>
     *
     * @param value a long.
     * @throws java.io.IOException if any.
     */
    void putLong(long value)
    throws IOException;
    
    /**
     * <p>putFloat.</p>
     *
     * @param value a float.
     * @throws java.io.IOException if any.
     */
    void putFloat(float value)
    throws IOException;
    
    /**
     * <p>putDouble.</p>
     *
     * @param value a double.
     * @throws java.io.IOException if any.
     */
    void putDouble(double value)
    throws IOException;
    	
	/**
	 * <p>order.</p>
	 *
	 * @return a {@link java.nio.ByteOrder} object.
	 */
	ByteOrder order();

	/**
	 * <p>order.</p>
	 *
	 * @param order a {@link java.nio.ByteOrder} object.
	 * @throws java.io.IOException if any.
	 */
	void order(ByteOrder order)
	throws IOException;

	/**
	 * <p>flush.</p>
	 *
	 * @throws java.io.IOException if any.
	 */
	void flush()
	throws IOException;
    
}
