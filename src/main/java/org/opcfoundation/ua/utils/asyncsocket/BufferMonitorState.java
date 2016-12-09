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

import java.util.EnumSet;

/**
 * Server Socket states.
 * 
 * Initial states: Waiting
 * Final states: Triggered, Canceled, Error
 * 
 * State transitions:
 *   Waiting -&gt; Triggered  
 *   Waiting -&gt; Canceled
 *   Waiting -&gt; Closed
 *   Waiting -&gt; Error 
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public enum BufferMonitorState {
	
	Waiting,		// Alarm is waiting 
	Triggered,		// Alarm has been triggered
	Canceled,		// Canceled by user
	Closed,			// Stream was closed before trigger position could be reached
	Error;			// Error occured before trigger position could be reached

	public static final EnumSet<BufferMonitorState> FINAL_STATES = EnumSet.of(Triggered, Canceled, Error, Closed);
	public static final EnumSet<BufferMonitorState> UNREACHABLE = EnumSet.of(Error, Closed);
	
	public boolean isFinal() {
		return FINAL_STATES.contains(this);
	}
	
	/**
	 * Tests if the state is unreachable
	 * 
	 * @return if true the state is unreachable
	 */
	public boolean isUnreachable() {
		return this == Error || this == Closed;
	}
	
}
