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

package org.opcfoundation.ua.transport.tcp.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.Callable;

import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.binary.BinaryDecoder;
import org.opcfoundation.ua.transport.tcp.impl.TcpConnectionParameters;
import org.opcfoundation.ua.utils.bytebuffer.ByteBufferArrayReadable;

/**
 * This {@link Callable} class unserializes chunk plaintexts into a message.
 */
public class ChunksToMessage implements Callable<IEncodeable> {

	Class<? extends IEncodeable> expectedType;
	TcpConnectionParameters ctx;
	EncoderContext encoderCtx;
	ByteBuffer[] plaintexts;
	
	/**
	 * <p>Constructor for ChunksToMessage.</p>
	 *
	 * @param ctx a {@link org.opcfoundation.ua.transport.tcp.impl.TcpConnectionParameters} object.
	 * @param expectedType type or null (if message expected)
	 * @param plaintexts a {@link java.nio.ByteBuffer} object.
	 * @param encoderCtx a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public ChunksToMessage(TcpConnectionParameters ctx, EncoderContext encoderCtx, Class<? extends IEncodeable> expectedType, ByteBuffer...plaintexts)
	{
		this.expectedType = expectedType;
		this.plaintexts = plaintexts;
		this.ctx = ctx;
		this.encoderCtx = encoderCtx;
	}
	
	/** {@inheritDoc} */
	@Override
	public IEncodeable call() throws Exception {
		ByteBufferArrayReadable readable = new ByteBufferArrayReadable(plaintexts);
		readable.order(ByteOrder.LITTLE_ENDIAN);
		BinaryDecoder dec = new BinaryDecoder(readable);
		dec.setEncoderContext(encoderCtx);
		if (expectedType!=null)
			return dec.getEncodeable(null, expectedType);
		else
			return dec.getMessage();
	}

}
