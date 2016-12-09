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
 * Generic object states.
 * 
 * Initial states: Closed, Opening
 * Final states: Closed
 * 
 * State transition paths: 
 *   Closed
 *   Closed -&gt; Opening -&gt; Open -&gt; Closing -&gt; Closed 
 *   Opening -&gt; Open -&gt; Closing -&gt; Closed
 * 
 */
public enum CloseableObjectState {

	Closed,	
	Opening,
	Open,
	Closing;
	
	public final static EnumSet<CloseableObjectState> CLOSED_STATES = EnumSet.of(Closed, Opening); 
	public final static EnumSet<CloseableObjectState> OPEN_STATES = EnumSet.of(Open, Closing); 
	public final static EnumSet<CloseableObjectState> POST_OPENING_STATES = EnumSet.of(Open, Closing, Closed); 
	
	public boolean isOpen(){
		return this == Open || this == Closing;
	}
	
	public boolean isClosed(){
		return this == Closed || this == Opening;
	}
	
}
