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

package org.opcfoundation.ua.utils;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * <p>StreamUtil class.</p>
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class StreamUtil {

	/**
	 * Reads entire resource from an input stream
	 *
	 * @param is a {@link java.io.InputStream} object.
	 * @return resource
	 * @throws java.io.IOException if any.
	 */
	public static byte[] readFully(InputStream is) throws IOException {
		byte[] buf = new byte[4096];		
		ByteArrayOutputStream os = new ByteArrayOutputStream(4096);
		for (;;) {
			int bytesRead = is.read(buf);
			if (bytesRead == -1)
				break;
			os.write(buf, 0, bytesRead);
		}

		return os.toByteArray();
	}
	
    /**
     * <p>copyStream.</p>
     *
     * @param is a {@link java.io.InputStream} object.
     * @param out a {@link java.io.OutputStream} object.
     * @throws java.io.IOException if any.
     */
    public static void copyStream(InputStream is, OutputStream out)
    throws IOException
    {
    	ReadableByteChannel ic = Channels.newChannel(is);
    	WritableByteChannel oc = Channels.newChannel(out);
    	try {    		
    	    ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
    	    while (ic.read(buffer) != -1) {
    	    	buffer.flip();
    	    	oc.write(buffer);
    	    	buffer.compact();
    	    }
    	    buffer.flip();
    	    while (buffer.hasRemaining()) {
    	    	oc.write(buffer);
    	    }
    	} finally {
    		ic.close();
    		oc.close();
    	}
    }

	/**
	 * <p>read.</p>
	 *
	 * @param is a {@link java.io.InputStream} object.
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 * @param bytes a int.
	 * @throws java.io.IOException if any.
	 */
	public static void read(InputStream is, ByteBuffer buf, int bytes)
	throws IOException
	{
		while (bytes>0 & buf.hasRemaining()) {
			int n = is.read(buf.array(), buf.position(), bytes);
			if (n < 0) throw new EOFException();
			buf.position( buf.position() + n );
			bytes -= n;
		}
	}
	
	/**
	 * <p>readFully.</p>
	 *
	 * @param is a {@link java.io.InputStream} object.
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 * @throws java.io.IOException if any.
	 */
	public static void readFully(InputStream is, ByteBuffer buf)
	throws IOException
	{
		while (buf.hasRemaining()) {			
			int n = is.read(buf.array(), buf.position(), buf.remaining());
			if (n < 0) throw new EOFException();
			buf.position( buf.position() + n );
		}
	}
	
	/**
	 * <p>readFully.</p>
	 *
	 * @param is a {@link java.io.InputStream} object.
	 * @param b an array of byte.
	 * @throws java.io.IOException if any.
	 */
	public static void readFully(InputStream is, byte[] b)
	throws IOException
	{
		readFully(is, b, 0, b.length);		
	}
	
	/**
	 * <p>readFully.</p>
	 *
	 * @param is a {@link java.io.InputStream} object.
	 * @param b an array of byte.
	 * @param off a int.
	 * @param len a int.
	 * @throws java.io.IOException if any.
	 */
	public static void readFully(InputStream is, byte[] b, int off, int len)
	throws IOException
	{
		while (len > 0) {
			int n = is.read(b, off, len);
			if (n < 0) throw new EOFException();
			off += n;
			len -= n;
		}
	}
}
