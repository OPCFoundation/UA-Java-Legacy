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

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;

/**
 * <p>UriUtil class.</p>
 *
 */
public class UriUtil {
		
	/** Constant <code>OPC_TCP_PORT=6000</code> */
	public static final int OPC_TCP_PORT = 6000;
	/** Constant <code>HTTP_PORT=80</code> */
	public static final int HTTP_PORT = 80;
	/** Constant <code>HTTPS_PORT=443</code> */
	public static final int HTTPS_PORT = 443;
	
	/** Constant <code>OPC_TCP_DISCOVERY_PORT=4840</code> */
	public static final int OPC_TCP_DISCOVERY_PORT = 4840;
	/** Constant <code>HTTPS_DISCOVERY_PORT=4843</code> */
	public static final int HTTPS_DISCOVERY_PORT = 4843;
	/** Constant <code>HTTP_DISCOVERY_PORT=56201</code> */
	public static final int HTTP_DISCOVERY_PORT = 56201;  // Alternate to 80

	/** Constant <code>SCHEME_OPCTCP="opc.tcp"</code> */
	public final static String SCHEME_OPCTCP = "opc.tcp";
	/** Constant <code>SCHEME_HTTP="http"</code> */
	public final static String SCHEME_HTTP = "http";
	/** Constant <code>SCHEME_HTTPS="https"</code> */
	public final static String SCHEME_HTTPS = "https";
	
	/** Constant <code>PATTERN_HTTPS</code> */
	public static final Pattern PATTERN_HTTPS = Pattern.compile( "^https://([^/]+)(/.*)?$", Pattern.CASE_INSENSITIVE );
	/** Constant <code>PATTERN_HTTP</code> */
	public static final Pattern PATTERN_HTTP = Pattern.compile( "^http://([^/]+)(/.*)?$", Pattern.CASE_INSENSITIVE );
	/** Constant <code>PATTERN_OPCTCP</code> */
	public static final Pattern PATTERN_OPCTCP = Pattern.compile( "^opc.tcp://([^/]+)(/.*)?$", Pattern.CASE_INSENSITIVE );
	/** Constant <code>PATTERN_URI</code> */
	public static final Pattern PATTERN_URI = Pattern.compile( "^(\\S+)://([^/]+)(/.*)?$", Pattern.CASE_INSENSITIVE );

	public static enum MessageFormat {
		Binary,  // UA Secure Conversion, UASC, Opc.tcp
		Xml;    // SOAP, http, https		
	}
	
	/**
	 * Get message format associated with an endpoint
	 *
	 * @param endpointUri a {@link java.lang.String} object.
	 * @return transport protocol
	 * @throws org.opcfoundation.ua.common.ServiceResultException Bad_ServerUriInvalid if the protocol is unknown
	 */
	public static MessageFormat getMessageFormat(String endpointUri)
	throws ServiceResultException
	{
		if (PATTERN_OPCTCP.matcher(endpointUri).matches()) return MessageFormat.Binary;		
		if (PATTERN_HTTPS.matcher(endpointUri).matches()) return MessageFormat.Binary;
		if (PATTERN_HTTP.matcher(endpointUri).matches()) return MessageFormat.Binary;
		throw new ServiceResultException(StatusCodes.Bad_ServerUriInvalid);
	}
	
	/**
	 * Get the transport protocol of an endpoint
	 *
	 * @param endpointUri a {@link java.lang.String} object.
	 * @return transport protocol
	 */
	public static String getTransportProtocol(String endpointUri)
	{
		int pos = endpointUri.indexOf(':');
		if ( pos<1 ) return "";
		return endpointUri.substring(0, pos);
	}
	
	/**
	 * Convert uri to socket address
	 *
	 * @param endpointUri a {@link java.lang.String} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 * @return a {@link java.net.InetSocketAddress} object.
	 */
	public static InetSocketAddress getSocketAddress(String endpointUri)
	throws ServiceResultException
	{
		try {
			URI uri = new URI(endpointUri);
			if (uri.getScheme() == null)
				throw new ServiceResultException(StatusCodes.Bad_ServerUriInvalid, "Invalid endpointUri (no scheme): " + endpointUri);
			return getSocketAddress(uri);			
		} catch (URISyntaxException e) {
			throw new ServiceResultException(StatusCodes.Bad_ServerUriInvalid, e);
		} catch (IllegalArgumentException e) {
			try {
				// Do a custom parse, if the URI is not valid, possibly because it
				// does not conform to RFC 2396. This occurs, for example, if the host name
				// contains '_' characters, which are used by some Windows computers
				String[] parts = endpointUri.split("/+");
				String proto = parts[0].split(":")[0];
				String[] host_port = parts[1].split(":");
				String host = host_port[0];
				int port;
				try {
					port = Integer.parseInt(host_port[1]);
				} catch (NumberFormatException e1) {
					port = defaultPort(proto);
				} catch (ArrayIndexOutOfBoundsException e2) {
					port = defaultPort(proto);
				}
				return new InetSocketAddress(host, port);
			} catch (RuntimeException ex) {
				// Use the original exception as cause
				throw new ServiceResultException(
						StatusCodes.Bad_ServerUriInvalid, e);
			}
		}

	}
	
	/**
	 * <p>getSocketAddress.</p>
	 *
	 * @param endpointUri a {@link java.net.URI} object.
	 * @return a {@link java.net.InetSocketAddress} object.
	 */
	public static InetSocketAddress getSocketAddress(URI endpointUri)
	{
		String scheme = endpointUri.getScheme().toLowerCase(); 
		String host = endpointUri.getHost();
		int port = endpointUri.getPort();
		if (port==-1) port = defaultPort(scheme);
		return new InetSocketAddress(host, port);
	}
	
	/**
	 * Get the resource part of an URI
	 *
	 * @param uri a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getEndpointName(String uri) {
		Matcher m = PATTERN_URI.matcher( uri );
		if ( m.matches() ) return m.group(3);
		return null;
	}
    
    /**
     * <p>getPort.</p>
     *
     * @param endpointUrl a {@link java.lang.String} object.
     * @return a int.
     */
    public static int getPort(String endpointUrl) {
    	try {
			InetSocketAddress addr = UriUtil.getSocketAddress( endpointUrl );
			int port = addr.getPort(); 
			if ( port == -1 ) {
				String scheme = UriUtil.getTransportProtocol( endpointUrl );
				return UriUtil.defaultPort( scheme );
			}
	    	return port;
		} catch (ServiceResultException e) {
			return -1;
		}
    }	
    
	/**
	 * <p>defaultPort.</p>
	 *
	 * @param scheme a {@link java.lang.String} object.
	 * @return a int.
	 */
	public static int defaultPort(String scheme) {
		if (SCHEME_OPCTCP.equals(scheme))
			return OPC_TCP_PORT;
		if (SCHEME_HTTP.equals(scheme))
			return HTTP_PORT;
		if (SCHEME_HTTPS.equals(scheme))
			return HTTPS_PORT;
		throw new IllegalArgumentException("Unsupported protocol " + scheme);
	}	

}
