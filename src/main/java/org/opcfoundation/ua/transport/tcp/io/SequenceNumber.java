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

package org.opcfoundation.ua.transport.tcp.io;

import java.util.concurrent.atomic.AtomicInteger;

import org.opcfoundation.ua.utils.StackUtils;

/**
 * Secure Channel sequence number
 */
public class SequenceNumber {	
	
	/**
	 * Sequence number used for output 
	 */
	private AtomicInteger sendSequenceNumber = new AtomicInteger( 1 /*StackUtils.RANDOM.nextInt()*/ );
	
	/**
	 * Sequence number used for input
	 */
	private AtomicInteger recvSequenceNumber = null;	
	
	/**
	 * Check whether recv sequence number has been set.
	 *
	 * @return true if setRecvSequenceNumber has been called
	 */
	public boolean hasRecvSequenceNumber() {
		return recvSequenceNumber != null;
	}

	/**
	 * Get current recv sequence number
	 *
	 * @return recv number or null
	 */
	public Integer getRecvSequenceNumber() {
		return recvSequenceNumber == null ? null : recvSequenceNumber.get();
	}
	
	/**
	 * Get next recv sequence number
	 *
	 * @return recv number or null
	 */
	public Integer getNextRecvSequenceNumber() {
		return recvSequenceNumber == null ? null : recvSequenceNumber.incrementAndGet();
	}
	
	
	/**
	 * <p>Setter for the field <code>recvSequenceNumber</code>.</p>
	 *
	 * @param value a int.
	 */
	public void setRecvSequenceNumber(int value) {
		recvSequenceNumber = new AtomicInteger(value);
	}
	
	/**
	 * Tests whether value matches expected sequence number and sets a new value.
	 *
	 * If value has never been set before the test passes and new value is set.
	 *
	 * Test passes if the value is one larger than previous value or if previous value is
	 * 4294966271 or larger.
	 *
	 * @return true if value matches
	 * @param newValue a int.
	 */
	public boolean testAndSetRecvSequencenumber(int newValue) {
		if (recvSequenceNumber==null) {
			recvSequenceNumber = new AtomicInteger(newValue);
			return true;
		}
		int oldValue = recvSequenceNumber.get();
		boolean wrapAround = (oldValue & 0x00000000ffffffff) >= 4294966271L;
		boolean exactMatch = oldValue +1 == newValue;
		boolean wrapMatch = wrapAround & (newValue<1024);
		boolean match = exactMatch | wrapMatch;
		
		if (match) 
			recvSequenceNumber.set(newValue);
		
		return match;
	}
	
	/**
	 * Get the next send sequence number.
	 * Send sequence number wraps between 4294966271 and 4294967295 to a new
	 * value that is below 1024.
	 *
	 * @return send sequnce numner.
	 */
	public int getNextSendSequencenumber() {
		long oldValue = sendSequenceNumber.get() & 0x00000000ffffffff;
		boolean mustWrap = oldValue == 4294967295L;
		boolean canWrap = oldValue >= 4294966271L;
		boolean wraps = mustWrap || (canWrap && StackUtils.RANDOM.nextBoolean()); 
		long newValue = oldValue +1;
		
		if (wraps) newValue = StackUtils.RANDOM.nextInt(1024);
		sendSequenceNumber.set( (int) newValue );
		return (int) newValue;
	}
	
	/**
	 * <p>getCurrentSendSequenceNumber.</p>
	 *
	 * @return a int.
	 */
	public int getCurrentSendSequenceNumber() {
		return sendSequenceNumber.get();
	}
	
	/**
	 * <p>setCurrentSendSequenceNumber.</p>
	 *
	 * @param newValue a int.
	 */
	public void setCurrentSendSequenceNumber(int newValue) {
		sendSequenceNumber.set(newValue);
	}	
	
}
