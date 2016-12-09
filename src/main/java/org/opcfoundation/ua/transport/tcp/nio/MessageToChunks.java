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

import org.opcfoundation.ua.common.RuntimeServiceResultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.EncoderMode;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.binary.BinaryEncoder;
import org.opcfoundation.ua.encoding.binary.EncoderCalc;
import org.opcfoundation.ua.transport.tcp.impl.ChunkFactory;
import org.opcfoundation.ua.transport.tcp.impl.TcpConnectionParameters;
import org.opcfoundation.ua.utils.bytebuffer.ByteBufferArrayWriteable;
import org.opcfoundation.ua.utils.bytebuffer.ByteQueue;

/**
 * Encodes messages to chunks.
 * Returns an array of plaintexts whose content is partially filled.
 * Chunk size, padding and plaintext is written, but footer and header is missing.
 * ByteOrder is Little Endian.
 * <p>
 * Encoder also asserts that message size and chunk count is with-in limits.
 */
public class MessageToChunks implements Callable<ByteBuffer[]> 
{
	MessageType type;
	IEncodeable msg;
	TcpConnectionParameters ctx;
	EncoderContext encoderCtx;
	ChunkFactory chunkFactory;

	/**
	 * <p>Constructor for MessageToChunks.</p>
	 *
	 * @param msg a {@link org.opcfoundation.ua.encoding.IEncodeable} object.
	 * @param ctx a {@link org.opcfoundation.ua.transport.tcp.impl.TcpConnectionParameters} object.
	 * @param encoderCtx a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 * @param chunkFactory a {@link org.opcfoundation.ua.transport.tcp.impl.ChunkFactory} object.
	 * @param type a {@link org.opcfoundation.ua.transport.tcp.nio.MessageType} object.
	 */
	public MessageToChunks(IEncodeable msg, TcpConnectionParameters ctx, EncoderContext encoderCtx, ChunkFactory chunkFactory, MessageType type)
	{
		if (msg==null || ctx==null || chunkFactory==null)
			throw new IllegalArgumentException("null arg");
		this.msg = msg;
		this.ctx = ctx;
		this.encoderCtx = encoderCtx;
		this.chunkFactory = chunkFactory;
		this.type = type;
	}
	
	/** {@inheritDoc} */
	@Override
	public ByteBuffer[] call() 
	throws RuntimeServiceResultException
	{		
	  try {
		EncoderCalc calc = new EncoderCalc();
		calc.setEncoderContext(encoderCtx);
		if (type == MessageType.Encodeable)
			calc.putEncodeable(null, msg);
		else
			calc.putMessage(msg);
		int len = calc.getLength();
		
		if (len>ctx.maxSendMessageSize && ctx.maxSendMessageSize!=0)
			throw new ServiceResultException(StatusCodes.Bad_TcpMessageTooLarge);
		
		ByteQueue bq = new ByteQueue();
		bq.order(ByteOrder.LITTLE_ENDIAN);
		bq.setWriteLimit(len);
		bq.setByteBufferFactory(chunkFactory);
		bq.setChunkSize(chunkFactory.maxPlaintextSize);
		
		ByteBufferArrayWriteable array = new ByteBufferArrayWriteable(bq);
		array.order(ByteOrder.LITTLE_ENDIAN);
		
		BinaryEncoder enc = new BinaryEncoder(array);
		enc.setEncoderContext(encoderCtx);
		enc.setEncoderMode(EncoderMode.NonStrict);
		
		if (type == MessageType.Message)
			enc.putMessage(msg);
		else
			enc.putEncodeable(null, msg);
		
		ByteBuffer[] plaintexts = bq.getChunks(len);
		
		if (plaintexts.length>ctx.maxRecvChunkCount && ctx.maxRecvChunkCount!=0)
			throw new ServiceResultException(StatusCodes.Bad_TcpMessageTooLarge);

		return plaintexts;
	  } catch (ServiceResultException e) {
		  throw new RuntimeServiceResultException(e);
	  }
	}
	
}
