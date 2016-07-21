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

package org.opcfoundation.ua.examples;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.opcfoundation.ua.application.Application;
import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.application.TestStackService;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.TestStackRequest;
import org.opcfoundation.ua.core.TestStackResponse;
import org.opcfoundation.ua.examples.certs.ExampleKeys;
import org.opcfoundation.ua.transport.ServiceChannel;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.EndpointUtil;

/**
 * This example creates both a server and a client. 
 * The server is bound to port 6001 and serves to TestStack requests (See {@link TestStackService}) with echo.
 * The client connects to the server and makes a simple "Hello World" request and receives 
 * corresponding "Hello World" resopnse.
 * 
 */
public class TestStackExample {

	public static void main(String[] args) throws Exception {
		
		//////////////  SERVER  //////////////
		// Create UA Server Application
		Application myServerApplication = new Application();
		myServerApplication.getOpctcpSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
		myServerApplication.getHttpsSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
		myServerApplication.getHttpsSettings().setHostnameVerifier( SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER );
		// Create UA Service Server
		Server myServer = new Server( myServerApplication );
		
		
		// Add a service to the server - TestStack echo
		myServer.addServiceHandler( new TestStackService() );
		
		// Load Servers's Application Instance Certificate from file
		KeyPair myServerApplicationInstanceCertificate = ExampleKeys.getKeyPair("server", 2048);
		KeyPair myServerHttpsKey = ExampleKeys.getKeyPair("https_server", 2048);
		// Add application instance certificate		
		myServerApplication.addApplicationInstanceCertificate( myServerApplicationInstanceCertificate );		
		myServerApplication.getHttpsSettings().setKeyPair( myServerHttpsKey );
		
		// Bind the endpoint for each network interface
		String hostname = EndpointUtil.getHostname();
		for (String addr : EndpointUtil.getInetAddressNames()) {
			String bindAddress     = "opc.tcp://"+addr+":6002/UAExample";
			String endpointAddress = "opc.tcp://"+hostname+":6002/UAExample";
			myServer.bind(bindAddress, endpointAddress, SecurityMode.ALL);
		}
		//////////////////////////////////////
		

		//////////////  CLIENT  //////////////
		// Load Client's Application Instance Certificate from file
		KeyPair myClientApplicationInstanceCertificate = ExampleKeys.getKeyPair("client", 2048);
		// Create Client
		Client myClient = Client.createClientApplication( myClientApplicationInstanceCertificate );		
		//////////////////////////////////////		
		
		
		/////////// DISCOVER ENDPOINT ////////
		// Discover server's endpoints, and choose one
		EndpointDescription[] endpoints = myClient.discoverEndpoints( "opc.tcp://"+hostname+":6002/" ); //51210=Sample Server
		// Filter out all but opc.tcp protocol endpoints
		endpoints = EndpointUtil.selectByProtocol(endpoints, "opc.tcp");
		// Filter out all but Signed & Encrypted endpoints
		endpoints = EndpointUtil.selectByMessageSecurityMode(endpoints, MessageSecurityMode.SignAndEncrypt);
		// Filter out all but Basic128 cryption endpoints
		endpoints = EndpointUtil.selectBySecurityPolicy(endpoints, SecurityPolicy.BASIC128RSA15);
		// Sort endpoints by security level. The lowest level at the beginning, the highest at the end of the array
		endpoints = EndpointUtil.sortBySecurityLevel(endpoints); 
		// Choose one endpoint
		EndpointDescription endpoint = endpoints[endpoints.length-1]; 
		//////////////////////////////////////		
		
				
		////////////  TEST-STACK  ////////////
		// Create Channel
		ServiceChannel myChannel = myClient.createServiceChannel( endpoint );
		// Create Test Request		
		TestStackRequest req = new TestStackRequest(null, null, null, new Variant( "Hello World" ));
		System.out.println("REQUEST: "+req);		
		// Invoke service
		TestStackResponse res = myChannel.TestStack(req);		
		// Print result
		System.out.println("RESPONSE: "+res);		
		//////////////////////////////////////		
		
		
		/////////////  SHUTDOWN  /////////////
		// Close channel
		myChannel.closeAsync();
		// Unbind endpoint. This also closes the socket 6001 as it has no more endpoints.
		myServer.getApplication().close();
		//////////////////////////////////////		
		
	}
	
}
