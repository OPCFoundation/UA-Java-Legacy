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

    byte get()
    throws IOException;

    void get(byte[] dst, int offset, int length)
    throws IOException;
    
    void get(byte[] dst)
    throws IOException;
    
    /**
     * Get buf fully 
     * 
     * @param buf
     * @throws IOException
     */
    void get(ByteBuffer buf)
    throws IOException;
    
    /**
     * Get fully length bytes
     * 
     * @param buf
     * @param length
     * @throws IOException
     */
    void get(ByteBuffer buf, int length)
    throws IOException;
    
    short getShort()
    throws IOException;
    
    int getInt()
    throws IOException;
    
    long getLong()
    throws IOException;
    
    float getFloat()
    throws IOException;
    
    double getDouble()
    throws IOException;
    
	long limit() throws IOException;
	
	ByteOrder order();

	void order(ByteOrder order);
    
	long position() throws IOException;
    
}
