 /* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.unittests;
import junit.framework.TestCase;

import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.application.TestStackService;
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.SecureChannel;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.PrivKey;
import org.opcfoundation.ua.transport.security.SecurityMode;

/**
 * Creates a test bench with a loop-back connection to a local server.
 * There are is a client with 2 channels; secureChannel and unsecure channel.
 * 
 * @see TestStack for the actual test case
 */
public abstract class StackTestBench extends TestCase {

	static String uri;
	
	Server server;
	Endpoint endpoint;
	SecureChannel secureChannel;
	SecureChannel unsecureChannel;
	
	
	public void setUp() throws Exception {
		uri = "opc.tcp://localhost:6125/TestServer";
		
		// Create Server
		server = Server.createServerApplication();
		// Add a service to the server - TestStack echo
		server.addServiceHandler( new TestStackService() );
		
		// Add application instance certificate
		Cert myServerCertificate = Cert.load( StackTestBench.class.getResource( "ServerCert.der" ) );
		PrivKey myServerPrivateKey = PrivKey.loadFromKeyStore( StackTestBench.class.getResource( "UAServerCert.pfx"), "Opc.Sample.Ua.Server");
		KeyPair serverApplicationInstanceCertificate = new KeyPair(myServerCertificate, myServerPrivateKey);
		server.getApplication().addApplicationInstanceCertificate( serverApplicationInstanceCertificate );
		
		// Bind my server to my endpoint. This binds socket to port 6001 as well 
		server.bind( uri, uri, SecurityMode.ALL);		
		
		
		// Create client
		Cert myClientCertificate = Cert.load( StackTestBench.class.getResource( "ClientCert.der" ) );
		PrivKey myClientPrivateKey = PrivKey.loadFromKeyStore( StackTestBench.class.getResource( "ClientCert.pfx"), "Opc.Sample.Ua.Client");
		KeyPair clientApplicationInstanceCertificate = new KeyPair(myClientCertificate, myClientPrivateKey);
		
		Client client = Client.createClientApplication( clientApplicationInstanceCertificate );
		secureChannel = client.createSecureChannel(uri, uri, SecurityMode.BASIC128RSA15_SIGN_ENCRYPT, serverApplicationInstanceCertificate.getCertificate());
		unsecureChannel = client.createSecureChannel(uri, uri, SecurityMode.NONE, null);
	}
	
	@Override
	protected void tearDown() throws Exception {
		secureChannel.closeAsync();
		secureChannel = null;
		
		unsecureChannel.closeAsync();
		unsecureChannel = null;
				
		server.getApplication().close();
		server = null;
	}
	
		
}
