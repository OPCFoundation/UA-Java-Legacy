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

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>EndpointServerCollection class.</p>
 *
 */
public class EndpointServerCollection {

	List<EndpointServer> servers = Collections.synchronizedList( new ArrayList<EndpointServer>() );

	/**
	 * <p>add.</p>
	 *
	 * @param endpointServer a {@link org.opcfoundation.ua.transport.EndpointServer} object.
	 */
	public void add(EndpointServer endpointServer)
	{
		servers.add( endpointServer );
	}
	
	/**
	 * <p>remove.</p>
	 *
	 * @param endpointServer a {@link org.opcfoundation.ua.transport.EndpointServer} object.
	 */
	public void remove(EndpointServer endpointServer)
	{
		servers.remove( endpointServer );
	}
	
	/**
	 * Get a snapshot of the endpoint server list
	 *
	 * @return a copy of the endpoint server list
	 */
	public List<EndpointServer> getList() {
		return new ArrayList<EndpointServer>( servers );
	}
	
	/**
	 * Get all endpoint bindings
	 *
	 * @return all endpoint bindings of all endpoint servers in this list
	 */
	public List<EndpointBinding> getEndpointBindings() {
		List<EndpointBinding> result = new ArrayList<EndpointBinding>();
		synchronized(servers) {
			for (EndpointServer es : servers) {
				result.addAll( es.getEndpointBindings().getAll() );
			}
		}
		return result;
	}
	
	/**
	 * Close all endpoint servers.
	 */
	public void closeAll()
	{
		for (EndpointServer server : getList() ) {
			server.close();
		}
	}
	
	/**
	 * Get the endpoint server that is bound at the given socket address.
	 *
	 * @param socketAddress a {@link java.net.SocketAddress} object.
	 * @return EndpointServer or null
	 */
	public EndpointServer getEndpointServer( SocketAddress socketAddress ) {
		for ( EndpointServer endpointServer : servers ) {
			for ( SocketAddress sa : endpointServer.getBoundSocketAddresses() ) {
				if ( sa.equals( socketAddress )) return endpointServer;
			}
		}
		return null;
	}
	
}
