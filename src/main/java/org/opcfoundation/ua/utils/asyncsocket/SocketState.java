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
 * Socket states.
 * 
 * Initial states: Ready
 * Final states: Error, Closed
 * 
 * State transitions: 
 *   Ready -&gt; Connecting -&gt; Connected -&gt; Closed
 *   Ready -&gt; Connecting -&gt; Connected -&gt; Error
 *   Ready -&gt; Connecting -&gt; Error
 *   Ready -&gt; Closed
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public enum SocketState {
	
	Ready,				// Initial state
	Connecting,			// Connecting
	Connected,			// Connected
	Error,				// Closed with Error, see getError()
	Closed;				// Closed and unusable
	
	public static final EnumSet<SocketState> OPEN_STATES = EnumSet.of(Ready, Connecting, Connected);
	public static final EnumSet<SocketState> ERROR_STATES = EnumSet.of(Error);
	public static final EnumSet<SocketState> INITIAL_STATES = EnumSet.of(Ready);
	public static final EnumSet<SocketState> NON_FINAL_STATES = EnumSet.of(Ready, Connecting, Connected);
	public static final EnumSet<SocketState> FINAL_STATES = EnumSet.of(Closed, Error);
	public static final EnumSet<SocketState> CONNECTED_TRANSITION_STATES = EnumSet.of(Closed, Error);
	public static final EnumSet<SocketState> CONNECTING_TRANSITION_STATES = EnumSet.of(Closed, Connected, Error);
	
	public boolean isFinal()
	{
		return FINAL_STATES.contains(this);
	}
	
}
