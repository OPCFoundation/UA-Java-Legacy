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

package org.opcfoundation.ua.transport;

import java.util.EnumSet;

/**
 * Message write states.
 *
 * Initial states: Queued
 * Final states: Flushed, Error
 *
 * The only allowed state transition paths:
 *   Queued -&gt; Writing -&gt; Flushed
 *   Queued -&gt; Writing -&gt; Error
 *
 * @see AsyncWrite
 */
public enum WriteState {
	Ready,
	Queued,			// Message has been placed in write queue
	Writing,		// Message is being written
	Written,		// Message has been written. As always with internet, transmission is not quaranteed.
	Canceled,		// Message was canceled
	Error;			// Error by stack or abortion. (e.g. connection closed, aborted). See getException().
	

	public static final EnumSet<WriteState> FINAL_STATES = EnumSet.of(Written, Error, Canceled); 

	public static final EnumSet<WriteState> CANCELABLE_STATES = EnumSet.of(Ready, Queued); 
	
	public boolean isFinal(){
		return this==Error || this==Written || this==Canceled;
	}
}
