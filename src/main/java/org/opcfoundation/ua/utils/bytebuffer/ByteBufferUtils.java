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

import java.nio.ByteBuffer;

/**
 * <p>ByteBufferUtils class.</p>
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ByteBufferUtils {

	/**
	 * Copies as much as possible
	 *
	 * @param src a {@link java.nio.ByteBuffer} object.
	 * @param dst a {@link java.nio.ByteBuffer} object.
	 */
	public static void copyRemaining(ByteBuffer src, ByteBuffer dst)
	{
		int n = Math.min(src.remaining(), dst.remaining());
		copy(src, dst, n);
	}
	
	/**
	 * <p>copy.</p>
	 *
	 * @param src a {@link java.nio.ByteBuffer} object.
	 * @param dst a {@link java.nio.ByteBuffer} object.
	 * @param length a int.
	 */
	public static void copy(ByteBuffer src, ByteBuffer dst, int length)
	{
		int srcLimit = src.limit();
		int dstLimit = dst.limit();
		src.limit(src.position() + length);
		dst.limit(dst.position() + length);
		dst.put(src);
		src.limit(srcLimit);
		dst.limit(dstLimit);		
	}		
	
	/**
	 * Concatenate two arrays to one
	 *
	 * @param chunks an array of byte.
	 * @return concatenation of all chunks
	 */
	public static byte[] concatenate(byte[]...chunks)
	{
		int len = 0;
		for (byte[] chunk : chunks)
			len += chunk.length;
		byte result[] = new byte[len];
		int pos = 0;
		for (byte[] chunk : chunks)
		{
			System.arraycopy(chunk, 0, result, pos, chunk.length);
			pos += chunk.length;			
		}
		return result;
	}
	
	
}
