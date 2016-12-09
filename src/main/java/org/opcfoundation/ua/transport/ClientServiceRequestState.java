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
 * <p>ClientServiceRequestState class.</p>
 */
public enum ClientServiceRequestState {

	Init,			// initial state
	Canceled,		// Canceled before request was sent
	Sent,			// request sent
	Complete,		// response ready
	Error;
	
	/** Constant <code>FINAL_STATES</code> */
	public final static EnumSet<ClientServiceRequestState> FINAL_STATES = EnumSet.of(Canceled, Complete, Error); 

	/**
	 * <p>isFinal.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isFinal()
	{
		return FINAL_STATES.contains(this);
	}
	
}
