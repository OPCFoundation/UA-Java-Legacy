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
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public interface IBinaryWriteable {

    void put(byte b)
    throws IOException;
    
    /**
     * Put n bytes from the remaining of the byte array. 
     * This operation moves the pointer in byte buffer.
     * 
     * @param src
     * @throws IOException
     */
    void put(ByteBuffer src)
    throws IOException;
    
    /**
     * Put n bytes from the remaining of the byte buffer. 
     * This operation moves the pointer in byte buffer.
     * 
     * @param src
     * @param length
     * @throws IOException
     */
    void put(ByteBuffer src, int length)
    throws IOException;
    
    void put(byte[] src, int offset, int length)
    throws IOException;
    
    void put(byte[] src)
    throws IOException;
    
    void putShort(short value)
    throws IOException;
    
    void putInt(int value)
    throws IOException;
    
    void putLong(long value)
    throws IOException;
    
    void putFloat(float value)
    throws IOException;
    
    void putDouble(double value)
    throws IOException;
    	
	ByteOrder order();

	void order(ByteOrder order)
	throws IOException;

	void flush()
	throws IOException;
    
}
