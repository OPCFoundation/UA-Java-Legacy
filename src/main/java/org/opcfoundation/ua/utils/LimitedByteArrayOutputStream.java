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
import java.io.OutputStream;

/**
 * A ByteArrayOutputStream that has a maximum size limit, 
 * after which it will throw {@link EncodingLimitsExceededIoException} if more is tried to be written.
 */
public class LimitedByteArrayOutputStream extends OutputStream{

	/**
	 * Creates a new limited {@link ByteArrayOutputStream} with the given limit
	 * and default capasity of {@link ByteArrayOutputStream}
	 * 
	 * @param maxBytes maximum number of bytes that can be written to the stream before it throws
	 * @return the new stream
	 */
	public static LimitedByteArrayOutputStream withSizeLimit(int maxBytes) {
		return new LimitedByteArrayOutputStream(maxBytes);
	}
	
	private final ByteArrayOutputStream delegate;
	private final int limit;

	//NOTE! private + factory methods to allow specifying initial capacity later
	private LimitedByteArrayOutputStream(int limit) {
		this.limit = limit;
		this.delegate = new ByteArrayOutputStream();
	}
	
	/**
	 * Behaves same as {@link ByteArrayOutputStream#write(int)}, but if the size of the underlying
	 * stream is at limit, will throw {@link EncodingLimitsExceededIoException} instead
	 */
	@Override
	public void write(int b) throws EncodingLimitsExceededIoException {
		if(delegate.size() >= limit) {
			throw new EncodingLimitsExceededIoException("Stream is at max capasity, "+limit+", cannot write more.");
		}else {
			delegate.write(b);
		}
	}
	
	/**
	 * Behaves same as {@link ByteArrayOutputStream#reset()}
	 */
	public void reset() {
		delegate.reset();
	}
	
	/**
	 * Behaves same as {@link ByteArrayOutputStream#toByteArray()}
	 */
	public byte[] toByteArray() {
		return delegate.toByteArray();
	}
	
}
