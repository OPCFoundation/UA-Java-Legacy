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

import java.util.Collection;

import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityPolicy;

/**
 * Server side Secure channel.
 */
public interface ServerSecureChannel {
	
	/** Get Local Application Instance Certificate 
	 * 
	 * @return Local Application Instance Certificate 
	 * */
	KeyPair getLocalCertificate();
	
	/** Get Remote Application Instance Certificate 
	 * 
	 * @return Remote Application Instance Certificate 
	 * */
	Cert getRemoteCertificate();

	/**
	 * Get secure channel ID
	 * 
	 * @return secure channel id
	 */
	int getSecureChannelId();
	
	/**
	 * Get current socket connection if applicable for the binding type.
	 * 
	 * @return connection or null
	 */
	ServerConnection getConnection();

	/**
	 * Get message security mode.
	 * 
	 * @return security mode or null
	 */
	MessageSecurityMode getMessageSecurityMode();
	
	/**
	 * Get security policy
	 *  
	 * @return security policy or null if channel has not been initialized
	 */
	SecurityPolicy getSecurityPolicy();

	/**
	 * Return the URL of the connection. 
	 * This value is only available when the channel is in
	 * Open or Closing state, if not the return value is null.
	 * 
	 * @return connect URL or null
	 */
	String getConnectURL();	
	
	/**
	 * Is the secure channel open.
	 * 
	 * @return true if the channel is open
	 */
	boolean isOpen();
	
	/**
	 * Close the secure channel. This method does nothing if the channel is 
	 * already closed or has never been opened. <p>
	 * 
	 * This method sends CloseSecureChannelRequest to the server and 
	 * closes the socket connection. If sending of the message fails and thus
	 * the servers never receives notification about closed secure channel, then
	 * there is no resend attempt, instead the secure channel will eventually
	 * time out in the server. <p> 
	 * 
	 * All pending requests will fault with Bad_SecureChannelClosed <p>
	 */
	void close();
	
	/**
	 * Close the secure channel. This method does nothing if the channel is 
	 * already closed or has never been opened. <p>
	 * 
	 * This method sends CloseSecureChannelRequest to the server and 
	 * closes the socket connection. If sending of the message fails and thus
	 * the servers never receives notification about closed secure channel, then
	 * there is no resend attempt, instead the secure channel will eventually
	 * time out in the server. <p> 
	 * 
	 * All pending requests will fault with Bad_SecureChannelClosed <p>
	 * 
	 * @return asynchronous monitor object
	 */	
	AsyncResult<ServerSecureChannel> closeAsync();
	
	/**
	 * Close and dispose. The object becomes unusuable.
	 */
	void dispose();
	
	/**
	 * Get all unanswered service requests.
	 * 
	 * @param result container to fill with unanswered service requests
	 */
	void getPendingServiceRequests(Collection<EndpointServiceRequest<?, ?>> result);
	
	/**
	 * Get endpoint
	 * @return Endpoint
	 */
	Endpoint getEndpoint();
	
	/**
	 * Get Server
	 * @return server
	 */
	Server getServer();

	boolean needsCertificate();
	
}
