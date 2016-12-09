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

import org.opcfoundation.ua.application.Server;

/**
 * Endpoint binding is a 3-way binding between
 *   the endpoint address (Endpoint),
 *   the listening socket (EndpointServer),
 *   and the service server (Server).
 */
public class EndpointBinding {

	/** The object that listens to the network socket */
	public final EndpointServer endpointServer;
	
	/** Endpoint address description */
	public final Endpoint endpointAddress;
	
	/** Service server */
	public final Server serviceServer;

	/**
	 * <p>Constructor for EndpointBinding.</p>
	 *
	 * @param endpointServer a {@link org.opcfoundation.ua.transport.EndpointServer} object.
	 * @param endpointAddress a {@link org.opcfoundation.ua.transport.Endpoint} object.
	 * @param serviceServer a {@link org.opcfoundation.ua.application.Server} object.
	 */
	public EndpointBinding(EndpointServer endpointServer, Endpoint endpointAddress, Server serviceServer) {
		if ( endpointServer==null || endpointAddress==null || serviceServer==null ) throw new IllegalArgumentException("null arg");
		this.endpointServer = endpointServer;
		this.endpointAddress = endpointAddress;
		this.serviceServer = serviceServer;
	}
	
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return endpointServer.hashCode() + 13*serviceServer.hashCode() + 7*endpointAddress.hashCode();
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EndpointBinding)) return false;
		EndpointBinding other = (EndpointBinding) obj;
		if (!other.endpointServer.equals(endpointServer)) return false;
		if (!other.endpointAddress.equals(endpointAddress)) return false;
		if (!other.serviceServer.equals(serviceServer)) return false;
		return true;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "(EndpointServer="+endpointServer+", EndpointAddress="+endpointAddress+", ServiceServer="+serviceServer+")";
	}
}
