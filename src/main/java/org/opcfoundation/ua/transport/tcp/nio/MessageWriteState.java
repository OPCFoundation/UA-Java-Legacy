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

import java.util.EnumSet;


/**
 * Message write states.
 * 
 * Initial states: Ready
 * Final states: Canceled, Error, Flushed
 * 
 * State transition paths: 
 * <p>
 *   Encoding -&gt; Encrypting -&gt; Writing -&gt; Flushed
 *   <p>
 *   Encoding -&gt; Encrypting -&gt; Writing -&gt; Error
 *   <p>
 *   Encoding -&gt; Encrypting -&gt; Writing -&gt; Canceled
 *   <p>
 *   Encoding -&gt; Encrypting -&gt; Error
 *   <p>
 *   Encoding -&gt; Encrypting -&gt; Canceled
 *   <p>
 *   Encoding -&gt; Error
 *   <p>
 *   Encoding -&gt; Canceled
 */
public enum MessageWriteState {

	Encoding,
	Encrypting,
	Writing,
	Flushed,
	Error,
	Canceled;
	
	public static final EnumSet<MessageWriteState> FINAL_STATES = EnumSet.of(Error, Canceled, Flushed);
	
	
}
