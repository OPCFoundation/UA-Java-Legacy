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
 * Servcer Socket states.
 * 
 * Initial states: Ready
 * Final states: Closed, Error
 * 
 * State transitions:
 *   Ready -&gt; Bound -&gt; Closed  
 *   Ready -&gt; Bound -&gt; Error
 *   Ready -&gt; Closed  
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public enum ServerSocketState {
	
	Ready,		// Channel is open but not bound
	Bound,		// Server is bound and accepting connections 
	Error,		// Error occured. See getError(). 
	Closed;		// Channel has been closed and system resources have been released
	
	public static final EnumSet<ServerSocketState> OPEN_STATES = EnumSet.of(Ready, Bound);
	public static final EnumSet<ServerSocketState> ERROR_STATES = EnumSet.of(Error);
	public static final EnumSet<ServerSocketState> INITIAL_STATES = EnumSet.of(Ready);
	public static final EnumSet<ServerSocketState> FINAL_STATES = EnumSet.of(Closed, Error);
	public static final EnumSet<ServerSocketState> READY_TRANSITION_STATES = EnumSet.of(Bound, Closed);
	public static final EnumSet<ServerSocketState> BOUND_TRANSITION_STATES = EnumSet.of(Closed, Error);
		
}
