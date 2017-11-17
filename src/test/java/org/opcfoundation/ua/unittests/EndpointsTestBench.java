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
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.SecureChannel;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.PrivKey;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.utils.EndpointUtil;

/**
 * Creates a test bench with an endpoint binded to each network interface. Idea is that same endpoint name is bound to different ip addresses. 
 * Also creates a client.
 * 
 * @see TestGetEndpoints for the actual test case
 */
public abstract class EndpointsTestBench extends TestCase {

	static String uri;
	
	Server server;
	Client client;
	Endpoint endpoint;
	SecureChannel secureChannel;
	SecureChannel unsecureChannel;
	
	
	public void setUp() throws Exception {
		
		// Create Server
		server = Server.createServerApplication();
		// Add a service to the server - TestStack echo
		// server.addServiceHandler( new TestStackService() );
		
		// Add application instance certificate
		Cert myServerCertificate = Cert.load( EndpointsTestBench.class.getResource( "ServerCert.der" ) );
		PrivKey myServerPrivateKey = PrivKey.loadFromKeyStore( EndpointsTestBench.class.getResource( "UAServerCert.pfx"), "Opc.Sample.Ua.Server");
		KeyPair serverApplicationInstanceCertificate = new KeyPair(myServerCertificate, myServerPrivateKey);
		server.getApplication().addApplicationInstanceCertificate( serverApplicationInstanceCertificate );
		
		//KeyPair myServerKeypair = UnitTestKeys.getKeyPair("server", 1024);
		//server.getApplication().addApplicationInstanceCertificate( myServerKeypair );
		
		KeyPair myServerHttpsKeypair = UnitTestKeys.getKeyPair("https_server", 1024);
		server.getApplication().getHttpsSettings().setKeyPair(myServerHttpsKeypair);
		//server.getApplication().getHttpsSettings().setHttpsSecurityPolicies(new HttpsSecurityPolicy[] { httpsSecurityPolicy });
		server.getApplication().getHttpsSettings().setHttpsSecurityPolicies(null);
		
		// Create different endpoints for different network interfaces
		String hostname = EndpointUtil.getHostname();
		String bindAddress, endpointAddress;
		for (String addr : EndpointUtil.getInetAddressNames()) {
			
			bindAddress = "opc.tcp://"+addr+":8666/UAExample";
			endpointAddress = "opc.tcp://"+hostname+":8666/UAExample";
			//TODO change these so that they work on all machines, not hardcoded!
			
			//
			if(bindAddress.contains("127.0.0.1")) {
				//bind SecurityMode None to loopback address
				server.bind(bindAddress, endpointAddress, SecurityMode.NONE);
				server.bind(bindAddress, endpointAddress, SecurityMode.BASIC128RSA15_SIGN_ENCRYPT);
				server.bind(bindAddress, endpointAddress, SecurityMode.BASIC128RSA15_SIGN);
				bindAddress = "https://"+addr+":8443/UAExample";
				endpointAddress = "https://"+hostname+":8443/UAExample";
				server.bind(bindAddress, endpointAddress, SecurityMode.NONE);
				
			}
			//
			else {
				server.bind(bindAddress, endpointAddress, SecurityMode.BASIC128RSA15_SIGN);
				server.bind(bindAddress, endpointAddress, SecurityMode.BASIC128RSA15_SIGN_ENCRYPT);
				server.bind(bindAddress, endpointAddress, SecurityMode.BASIC256_SIGN);
				server.bind(bindAddress, endpointAddress, SecurityMode.BASIC256_SIGN_ENCRYPT);

				bindAddress = "https://"+addr+":8443/UAExample";
				endpointAddress = "https://"+hostname+":8443/UAExample";
				server.bind(bindAddress, endpointAddress, SecurityMode.NONE);
			}
		}
		
		// Create client
		Cert myClientCertificate = Cert.load( EndpointsTestBench.class.getResource( "ClientCert.der" ) );
		PrivKey myClientPrivateKey = PrivKey.loadFromKeyStore( EndpointsTestBench.class.getResource( "ClientCert.pfx"), "Opc.Sample.Ua.Client");
		KeyPair clientApplicationInstanceCertificate = new KeyPair(myClientCertificate, myClientPrivateKey);
		
		client = Client.createClientApplication( clientApplicationInstanceCertificate );
		
		// Create client
		//KeyPair myClientKeys = UnitTestKeys.getKeyPair("client", 1024);
		//Client client = Client.createClientApplication( myClientKeys );
		
		KeyPair myClientHttpsKeys = UnitTestKeys.getKeyPair("https_client", 1024);
		client.getApplicationHttpsSettings().setKeyPair( myClientHttpsKeys );
		//client.getApplication().getHttpsSettings().setHttpsSecurityPolicies(new HttpsSecurityPolicy[] { SecurityMode.NONE });
		client.getApplication().getHttpsSettings().setHttpsSecurityPolicies(null);
	}
	
	@Override
	protected void tearDown() throws Exception {
		server.getApplication().close();
		server = null;
	}
	
		
}
