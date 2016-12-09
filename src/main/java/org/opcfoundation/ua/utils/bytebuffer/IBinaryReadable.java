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

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;



/**
 * IBinaryReadable is a readable stream.
 *
 * IBinaryReadable throws {@link EOFException} if end of stream is reached.
 *
 * @see ByteBufferReadable
 * @see InputStreamReadable
 * @see ByteBufferArrayReadable
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public interface IBinaryReadable {

    /**
     * <p>get.</p>
     *
     * @return a byte.
     * @throws java.io.IOException if any.
     */
    byte get()
    throws IOException;

    /**
     * <p>get.</p>
     *
     * @param dst an array of byte.
     * @param offset a int.
     * @param length a int.
     * @throws java.io.IOException if any.
     */
    void get(byte[] dst, int offset, int length)
    throws IOException;
    
    /**
     * <p>get.</p>
     *
     * @param dst an array of byte.
     * @throws java.io.IOException if any.
     */
    void get(byte[] dst)
    throws IOException;
    
    /**
     * Get buf fully
     *
     * @param buf a {@link java.nio.ByteBuffer} object.
     * @throws java.io.IOException if any.
     */
    void get(ByteBuffer buf)
    throws IOException;
    
    /**
     * Get fully length bytes
     *
     * @param buf a {@link java.nio.ByteBuffer} object.
     * @param length a int.
     * @throws java.io.IOException if any.
     */
    void get(ByteBuffer buf, int length)
    throws IOException;
    
    /**
     * <p>getShort.</p>
     *
     * @return a short.
     * @throws java.io.IOException if any.
     */
    short getShort()
    throws IOException;
    
    /**
     * <p>getInt.</p>
     *
     * @return a int.
     * @throws java.io.IOException if any.
     */
    int getInt()
    throws IOException;
    
    /**
     * <p>getLong.</p>
     *
     * @return a long.
     * @throws java.io.IOException if any.
     */
    long getLong()
    throws IOException;
    
    /**
     * <p>getFloat.</p>
     *
     * @return a float.
     * @throws java.io.IOException if any.
     */
    float getFloat()
    throws IOException;
    
    /**
     * <p>getDouble.</p>
     *
     * @return a double.
     * @throws java.io.IOException if any.
     */
    double getDouble()
    throws IOException;
    
	/**
	 * <p>limit.</p>
	 *
	 * @return a long.
	 * @throws java.io.IOException if any.
	 */
	long limit() throws IOException;
	
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
	 */
	void order(ByteOrder order);
    
	/**
	 * <p>position.</p>
	 *
	 * @return a long.
	 * @throws java.io.IOException if any.
	 */
	long position() throws IOException;
    
}
