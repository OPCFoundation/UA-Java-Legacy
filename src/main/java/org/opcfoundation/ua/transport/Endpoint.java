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

import java.net.URI;
import java.util.Arrays;

import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.ObjectUtils;

/**
 * Endpoint is a connection point to a server.
 * An endpoint is assigned to a {@link Server}.
 * Endpoint doesn't contain service logic, it is merely description of a connection point.
 * <p>
 * This class is hash-equals comparable.
 *
 */
public class Endpoint {

	/** Security Modes */
	SecurityMode[] modes;
	/** Endpoint Url */
	String endpointUrl;
	
	private int hash;
	
	EndpointConfiguration endpointConfiguration;
	
	/**
	 * Create a new endpoint.
	 *
	 * @param endpointUrl endpoint address
	 * @param modes security modes
	 */
	public Endpoint(URI endpointUrl, SecurityMode...modes) {		
		if (modes==null || endpointUrl==null)
			throw new IllegalArgumentException("null arg");
		for (SecurityMode m : modes) {
			if (m==null) throw new IllegalArgumentException("null arg");
			hash = 13*hash + m.hashCode();
		}
		this.endpointUrl = endpointUrl.toString();
		this.modes = modes;
		this.endpointConfiguration = EndpointConfiguration.defaults();
		hash = 13*hash + endpointUrl.hashCode();
	}
	
	/**
	 * Create new endpoint.
	 *
	 * @param endpointUrl endpoint address
	 * @param modes security modes
	 */
	public Endpoint(String endpointUrl, SecurityMode...modes) {		
		if (modes==null || endpointUrl==null)
			throw new IllegalArgumentException("null arg");
		for (SecurityMode m : modes) {
			if (m==null) throw new IllegalArgumentException("null arg");
			hash = 13*hash + m.hashCode();
		}
		this.endpointUrl = endpointUrl;
		this.modes = modes;
		this.endpointConfiguration = EndpointConfiguration.defaults();
		hash = 13*hash + endpointUrl.hashCode();
	}

	/**
	 * <p>Getter for the field <code>endpointUrl</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getEndpointUrl() {
		return endpointUrl;
	}

	/**
	 * <p>getSecurityModes.</p>
	 *
	 * @return an array of {@link org.opcfoundation.ua.transport.security.SecurityMode} objects.
	 */
	public SecurityMode[] getSecurityModes() {
		return modes;
	}
	
	/**
	 * <p>supportsSecurityMode.</p>
	 *
	 * @param mode a {@link org.opcfoundation.ua.transport.security.SecurityMode} object.
	 * @return a boolean.
	 */
	public boolean supportsSecurityMode(SecurityMode mode)
	{
		for (SecurityMode m : modes)
			if (m.equals(mode)) return true;
		return false;
	}
	
	/**
	 * <p>supportsSecurityPolicy.</p>
	 *
	 * @param policy a {@link org.opcfoundation.ua.transport.security.SecurityPolicy} object.
	 * @return a boolean.
	 */
	public boolean supportsSecurityPolicy(SecurityPolicy policy)
	{
		for (SecurityMode m : modes)
			if (m.getSecurityPolicy().equals(policy)) return true;
		return false;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return hash;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Endpoint)) return false;
		Endpoint other = (Endpoint) obj;
		if (!ObjectUtils.objectEquals(other.getEndpointUrl(), getEndpointUrl())) return false;
		if (!Arrays.deepEquals(modes, other.modes)) return false;
		return true;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return endpointUrl+" "+Arrays.toString(modes);
	}

	/**
	 * <p>Getter for the field <code>endpointConfiguration</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.EndpointConfiguration} object.
	 */
	public EndpointConfiguration getEndpointConfiguration() {
		return endpointConfiguration;
	}
	
	/**
	 * <p>Setter for the field <code>endpointConfiguration</code>.</p>
	 *
	 * @param endpointConfiguration a {@link org.opcfoundation.ua.core.EndpointConfiguration} object.
	 */
	public void setEndpointConfiguration( EndpointConfiguration endpointConfiguration ) {
		this.endpointConfiguration = endpointConfiguration; 
	}
	
}
