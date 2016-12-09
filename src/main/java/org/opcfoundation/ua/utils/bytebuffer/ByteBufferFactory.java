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
import java.nio.ByteOrder;

/**
 * <p>Abstract ByteBufferFactory class.</p>
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public abstract class ByteBufferFactory {

	/** Constant <code>DEFAULT_ENDIAN_HEAP_BYTEBUFFER_FACTORY</code> */
	public static final ByteBufferFactory DEFAULT_ENDIAN_HEAP_BYTEBUFFER_FACTORY = 
		new ByteBufferFactory() {
			@Override
			public ByteBuffer allocate(int capacity) {
				ByteBuffer result = ByteBuffer.allocate(capacity);
				result.order(ByteOrder.nativeOrder());
				return null;
			}};	
	
	/** Constant <code>LITTLE_ENDIAN_HEAP_BYTEBUFFER_FACTORY</code> */
	public static final ByteBufferFactory LITTLE_ENDIAN_HEAP_BYTEBUFFER_FACTORY = 
		new ByteBufferFactory() {
			@Override
			public ByteBuffer allocate(int capacity) {
				ByteBuffer result = ByteBuffer.allocate(capacity);
				result.order(ByteOrder.LITTLE_ENDIAN);
				return result;
			}};  
	/** Constant <code>BIG_ENDIAN_HEAP_BYTEBUFFER_FACTORY</code> */
	public static final ByteBufferFactory BIG_ENDIAN_HEAP_BYTEBUFFER_FACTORY = 
		new ByteBufferFactory() {
			@Override
			public ByteBuffer allocate(int capacity) {
				ByteBuffer result = ByteBuffer.allocate(capacity);
				result.order(ByteOrder.BIG_ENDIAN);
				return result;
			}};  
	
	/**
	 * <p>allocate.</p>
	 *
	 * @param capacity a int.
	 * @return a {@link java.nio.ByteBuffer} object.
	 */
	public abstract ByteBuffer allocate(int capacity);
	
}
