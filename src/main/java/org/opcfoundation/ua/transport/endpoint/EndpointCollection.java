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

package org.opcfoundation.ua.transport.endpoint;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.utils.EndpointUtil;

/**
 * <p>EndpointCollection class.</p>
 *
 * @deprecated Use EndpointBindingCollection
 */
public class EndpointCollection  {

	/** Endpoints  (Uri -&gt; Endpoint) */
	protected Map<Endpoint, Server> endpoints = new HashMap<Endpoint, Server>();
	
	/**
	 * <p>Constructor for EndpointCollection.</p>
	 */
	public EndpointCollection() {
	}
	
	/**
	 * <p>add.</p>
	 *
	 * @param endpoint a {@link org.opcfoundation.ua.transport.Endpoint} object.
	 * @param server a {@link org.opcfoundation.ua.application.Server} object.
	 */
	public synchronized void add(Endpoint endpoint, Server server) {
		if (endpoint==null || server==null) throw new IllegalArgumentException("null arg");
		for (Endpoint ep : endpoints.keySet())
			if (ep.getEndpointUrl().equals(endpoint.getEndpointUrl()))
				throw new RuntimeException("Collection already contains endpoint");
		endpoints.put(endpoint, server);
	}
	
	/**
	 * <p>remove.</p>
	 *
	 * @param endpoint a {@link org.opcfoundation.ua.transport.Endpoint} object.
	 * @return a {@link org.opcfoundation.ua.application.Server} object.
	 */
	public synchronized Server remove(Endpoint endpoint) {
		return endpoints.remove(endpoint);
	}
	
	/**
	 * <p>contains.</p>
	 *
	 * @param endpoint a {@link org.opcfoundation.ua.transport.Endpoint} object.
	 * @return a boolean.
	 */
	public synchronized boolean contains(Endpoint endpoint) {
		return endpoints.containsKey(endpoint);
	}
	
	/**
	 * <p>contains.</p>
	 *
	 * @param server a {@link org.opcfoundation.ua.application.Server} object.
	 * @return a boolean.
	 */
	public synchronized boolean contains(Server server) {
		return endpoints.containsValue(server);
	}
	
	/**
	 * <p>getServer.</p>
	 *
	 * @param endpoint a {@link org.opcfoundation.ua.transport.Endpoint} object.
	 * @return a {@link org.opcfoundation.ua.application.Server} object.
	 */
	public synchronized Server getServer(Endpoint endpoint) {
		return endpoints.get(endpoint);
	}
	
	/**
	 * Get all endpoints
	 *
	 * @return endpoints
	 */
	public synchronized Endpoint[] getEndpoints() {
		return endpoints.keySet().toArray(new Endpoint[endpoints.size()]);
	}
	
	/**
	 * Get endpoints by server
	 *
	 * @return endpoints
	 * @param server a {@link org.opcfoundation.ua.application.Server} object.
	 */
	public synchronized Endpoint[] getEndpoints(Server server) {
		List<Endpoint> result = new ArrayList<Endpoint>();
		for (Entry<Endpoint, Server> e : endpoints.entrySet())
			if (e.getValue()==server)
				result.add(e.getKey());
		return result.toArray(new Endpoint[result.size()]);
	}
	
	/**
	 * <p>getServers.</p>
	 *
	 * @return an array of {@link org.opcfoundation.ua.application.Server} objects.
	 */
	public synchronized Server[] getServers() {
		Set<Server> l = new HashSet<Server>();
		for (Server s: endpoints.values())
			l.add(s);
		return l.toArray(new Server[0]);
	}
	
	/**
	 * <p>get.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param mode a {@link org.opcfoundation.ua.transport.security.SecurityMode} object.
	 * @return a {@link org.opcfoundation.ua.transport.Endpoint} object.
	 */
	public synchronized Endpoint get(String url, SecurityMode mode)
	{
		for (Endpoint ep : endpoints.keySet())
			if (ep.supportsSecurityMode(mode) && url.equalsIgnoreCase(ep.getEndpointUrl()))
				return ep;
		return null;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.transport.Endpoint} object.
	 */
	public synchronized Endpoint get(String url)
	{
		// It seems that the casing of the host name part of the URL can vary,
		// so we must use a case-ignoring check here
		for (Endpoint ep : endpoints.keySet())
			if (url == null || url.equalsIgnoreCase(ep.getEndpointUrl()))
				return ep;
		return null;
	}

	/**
	 * <p>size.</p>
	 *
	 * @return a int.
	 */
	public synchronized int size() 
	{
		return endpoints.size();
	}

	/**
	 * <p>getDefault.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.transport.Endpoint} object.
	 */
	public Endpoint getDefault(String url) {
		if (url == null)
			throw new NullPointerException("url must be defined");
		try {
			URI requestedUri = new URI(url);
			for (Endpoint ep : endpoints.keySet())
				 {
					try {
						URI uri = new URI(ep.getEndpointUrl());
						if (EndpointUtil.urlEqualsHostIgnoreCase(uri, requestedUri))
							return ep;
					} catch (URISyntaxException e) {
					}
				}
		} catch (URISyntaxException e1) {
		}
		return null;
	}
	
}
