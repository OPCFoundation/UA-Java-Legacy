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

import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.utils.AbstractState;

/**
 *
 * 
 */
public class AsyncRead extends AbstractState<ReadState, ServiceResultException> {

	IEncodeable msg;
	
	public AsyncRead() {
		super(ReadState.Waiting, ReadState.Error);
	}
	
	public IEncodeable getMessage()
	{
		return msg;
	}
	
	public synchronized void setError(ServiceResultException e) {
		assert(!getState().isFinal());
		super.setError(e);
	}
		
	public synchronized void attemptSetError(ServiceResultException e) {
		if (getState().isFinal()) return;
		super.setError(e);
	}
		
	public synchronized void setComplete(IEncodeable msg) {
		assert(!getState().isFinal());
		this.msg = msg;
		setState(ReadState.Complete);		
	}	
	
}
