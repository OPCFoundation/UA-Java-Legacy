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

package org.opcfoundation.ua.utils.asyncsocket;

import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Asyncronous output stream of asynchronous socket.
 * There are two positions properties: Bytes written and bytes flushed.
 * <p>
 * Flushing of data can be monitored asyncronously with Alarm object.
 * e.g.
 *    byte[] data;
 *    long pos = os.getPosition();
 *    os.write(data);
 *    Alarm a = os.createAlarm(pos + data.length, flushListener);
 *
 * @see BufferMonitor
 * @see AsyncSocketImpl
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public abstract class AsyncOutputStream extends OutputStream {

	/**
	 * Write to stream
	 *
	 * @param src a {@link java.nio.ByteBuffer} object.
	 */
	public abstract void write(ByteBuffer src);
	
	/**
	 * Write to stream
	 *
	 * @param src a {@link java.nio.ByteBuffer} object.
	 * @param length a int.
	 */
	public abstract void write(ByteBuffer src, int length);
	
	/**
	 * Offers byte buffer to the output stream for write. The ownership of the byte buffer
	 * and its back-end will be taken over by the stream.
	 *
	 * @param buf buffer to offer
	 */
	public abstract void offer(ByteBuffer buf);

	/**
	 * Get the position of stream that has been flushed. This position lags behind getPosition() value
	 *
	 * @return bytes flushed from the stream
	 */
	public abstract long getFlushPosition();
	
	/**
	 * Get the position of the stream
	 *
	 * @return number of bytes written to the stream
	 */
	public abstract long getPosition();
	
	/**
	 * Get number of bytes remaining to be written
	 *
	 * @return the number of unflushed bytes
	 */
	public abstract long getUnflushedBytes();
	
	/**
	 * Create an object that monitors for flush position of the output stream.
	 *
	 * @param position position to trigger
	 * @param flushListener alarm listener
	 * @return alarm
	 */
	public abstract BufferMonitor createMonitor(long position, MonitorListener flushListener);	
	
}
