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

import java.util.concurrent.TimeUnit;

import org.opcfoundation.ua.common.ServiceResultException;

/**
 * Asynchronous result is a multi-thread object that operates as a container
 * for a result. The result is either an error or the result object.
 *
 * The result can be
 *   blocked ({@link #waitForResult()}),
 *   polled ({@link #getResult()} and {@link #getError()}), or
 *   listened to {@link #setListener(ResultListener)}.
 *
 * AsyncResult can be used from any thread and from multiple-thread.
 *
 * @see org.opcfoundation.ua.transport.impl.AsyncResultImpl
 */
public interface AsyncResult<T> {
	
	/**
	 * Set a listener. If the result is already available, the listener
	 * is invoked from the current thread.
	 *
	 * Otherwise, the notification is placed from the thread that sets
	 * the result. The listener implementation may not create any locks
	 * during the handling of the result, also it is good policy not do
	 * any long term operations in the listener implementation.
	 *
	 * @param listener (listener may not block) or null to remove listener
	 */
	void setListener(ResultListener<T> listener);
	
	/**
	 * Get result if available
	 *
	 * @return result or null if not complete
	 * @throws org.opcfoundation.ua.common.ServiceResultException if error
	 */
	T getResult() throws ServiceResultException;
	
	/**
	 * <p>getError.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.common.ServiceResultException} object.
	 */
	ServiceResultException getError(); 
	
	/**
	 * Get request status
	 *
	 * @return status
	 */
	AsyncResultStatus getStatus(); 
	
	/**
	 * Wait for result until result is available.
	 *
	 * Typically result becomes available in a default operation time out period. (120s)
	 *
	 * @return the result
	 * @throws org.opcfoundation.ua.common.ServiceResultException network error, e.g. IOException of MethodNotSupportedException
	 */
	T waitForResult() throws ServiceResultException;
	
	/**
	 * Wait for result or time out. On timeout ServiceResultException(Bad_Timeout) is thrown.
	 *
	 * @param timeout time out value
	 * @param unit time unit
	 * @return the result
	 * @throws org.opcfoundation.ua.common.ServiceResultException error during invocation
	 */
	T waitForResult(long timeout, TimeUnit unit) throws ServiceResultException;

	public static enum AsyncResultStatus {Waiting, Succeed, Failed}
	
}
