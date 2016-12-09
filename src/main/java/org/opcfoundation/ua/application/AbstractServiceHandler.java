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

package org.opcfoundation.ua.application;

import java.util.Collection;

import org.opcfoundation.ua.encoding.IEncodeable;

/**
 * Abstract implementation for a service handler that can server only
 * one type of service request;
 */
public abstract class AbstractServiceHandler implements ServiceHandler {

	Class<? extends IEncodeable> clazz;
	
	/**
	 * <p>Constructor for AbstractServiceHandler.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 */
	public AbstractServiceHandler(Class<? extends IEncodeable> clazz)
	{
		if (clazz==null)
			throw new IllegalArgumentException("null");
		this.clazz = clazz;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean supportsService(Class<? extends IEncodeable> clazz) {
		return clazz.equals(this.clazz);
	}

	/** {@inheritDoc} */
	@Override
	public void getSupportedServices(Collection<Class<? extends IEncodeable>> result) {
		result.add(clazz);
	}

}
