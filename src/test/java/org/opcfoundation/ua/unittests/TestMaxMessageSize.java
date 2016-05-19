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

import java.util.Random;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.application.TestStackService;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.TestStackRequest;
import org.opcfoundation.ua.core.TestStackResponse;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.SecureChannel;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.HttpsSecurityPolicy;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityMode;

public class TestMaxMessageSize extends TestCase {
	
	/** Timeout for a single test (Seconds) */
	final static int ITERATION_TIMEOUT = 300;
	
	String uri;
	int keySize;
	Server server;
	Endpoint endpoint;
	SecureChannel secureChannel;
	
	public void test_Client_OPC_TCP_BASIC128RSA15_SIGN() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC128RSA15_SIGN, true, false);
		} finally {
			_tearDown();
		}
	}
	public void test_Server_OPC_TCP_BASIC128RSA15_SIGN() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC128RSA15_SIGN, false, true);
		} finally {
			_tearDown();
		}
	}

	public void test_Client_OPC_TCP_BASIC128RSA15_SIGN_ENCRYPT() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC128RSA15_SIGN_ENCRYPT, true, false);
		} finally {
			_tearDown();
		}
	}
	public void test_Server_OPC_TCP_BASIC128RSA15_SIGN_ENCRYPT() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC128RSA15_SIGN_ENCRYPT, false, true);
		} finally {
			_tearDown();
		}
	}
	
	public void test_Client_OPC_TCP_BASIC256_SIGN() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256_SIGN, true, false);
		} finally {
			_tearDown();
		}
	}
	public void test_Server_OPC_TCP_BASIC256_SIGN() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256_SIGN, false, true);
		} finally {
			_tearDown();
		}
	}
	
	public void test_Client_OPC_TCP_BASIC256_SIGN_ENCRYPT() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256_SIGN_ENCRYPT, true, false);
		} finally {
			_tearDown();
		}
	}
	public void test_Server_OPC_TCP_BASIC256_SIGN_ENCRYPT() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256_SIGN_ENCRYPT, false, true);
		} finally {
			_tearDown();
		}
	}
	
	public void test_Client_OPC_TCP_BASIC256SHA256_SIGN_ENCRYPT() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256SHA256_SIGN_ENCRYPT, true, false);
		} finally {
			_tearDown();
		}	
	}
	public void test_Server_OPC_TCP_BASIC256SHA256_SIGN_ENCRYPT() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256SHA256_SIGN_ENCRYPT, false, true);
		} finally {
			_tearDown();
		}	
	}

	public void test_Client_OPC_TCP_BASIC256SHA256_SIGN() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256SHA256_SIGN, true, false);
		} finally {
			_tearDown();
		}
	}	
	public void test_Server_OPC_TCP_BASIC256SHA256_SIGN() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256SHA256_SIGN, false, true);
		} finally {
			_tearDown();
		}
	}	
	
	public void test_Client_Https_TLS_1_0() throws Exception {
		try {
			executeTests("https://localhost:6125/TestServer", HttpsSecurityPolicy.TLS_1_0, true, false);
		} finally {
			_tearDown();
		}
	}	
	public void test_Server_Https_TLS_1_0() throws Exception {
		try {
			executeTests("https://localhost:6125/TestServer", HttpsSecurityPolicy.TLS_1_0, false, true);
		} finally {
			_tearDown();
		}
	}	

	public void test_Client_Https_TLS_1_1() throws Exception {
		try {
			executeTests("https://localhost:6125/TestServer", HttpsSecurityPolicy.TLS_1_1, true, false);
		} finally {
			_tearDown();
		}
	}	
	public void test_Server_Https_TLS_1_1() throws Exception {
		try {
			executeTests("https://localhost:6125/TestServer", HttpsSecurityPolicy.TLS_1_1, false, true);
		} finally {
			_tearDown();
		}
	}	
	
	public void test_Client_Https_TLS_1_2() throws Exception {
		try {
			executeTests("https://localhost:6125/TestServer", HttpsSecurityPolicy.TLS_1_2, true, false);
		} finally {
			_tearDown();
		}
	}	
	public void test_Server_Https_TLS_1_2() throws Exception {
		try {
			executeTests("https://localhost:6125/TestServer", HttpsSecurityPolicy.TLS_1_2, false, true);
		} finally {
			_tearDown();
		}
	}	
	
	public void test_Client_OPC_TCP_NONE() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.NONE, true, false);
		} finally {
			_tearDown();
		}
	}
	public void test_Server_OPC_TCP_NONE() throws Exception {
		try {
			executeTests("opc.tcp://localhost:6125/TestServer", SecurityMode.NONE, false, true);
		} finally {
			_tearDown();
		}
	}
		
	void _tearDown() throws Exception {
		if ( secureChannel != null ) {
			secureChannel.closeAsync();
			secureChannel = null;
		}
		
		if ( server != null ) {
			server.getApplication().close();
			server = null;
		}
	}

	void executeTests(String uri, HttpsSecurityPolicy httpsSecurityPolicy, boolean testClient, boolean testServer) throws Exception
	{
		executeTests(uri, SecurityMode.NONE, testClient, testServer, httpsSecurityPolicy);
	}
	void executeTests(String uri, SecurityMode mode, boolean testClient, boolean testServer) throws Exception
	{
		executeTests(uri, mode, testClient, testServer, null);
	}
	
	void executeTests(String uri, SecurityMode mode, boolean testClient, boolean testServer, HttpsSecurityPolicy httpsSecurityPolicy) throws Exception
	{
		int[] maxMessageSizes = new int[]          {  10000,    10000,     1000,   200000,  100000  };
		int[] msgSizes        = new int[]          {      1,     1024,    10240,   102400,  200000  };
		boolean[] expectedSuccess = new boolean[]  {   true,     true,    false,   true,    false   };

		System.out.println();
		for (int i=2; i<expectedSuccess.length; i++) {		
			
			// Test client
			try {
				int msgSize = msgSizes[i];
				int maxMessageSize = maxMessageSizes[i];
				
				this.uri = uri;
				this.keySize = 1024;
				
				// Create Server
				server = Server.createServerApplication();
				// Add a service to the server - TestStack echo
				server.addServiceHandler( new TestStackService() );
				
				// Add application instance certificate
				KeyPair myServerKeypair = UnitTestKeys.getKeyPair("server", this.keySize);
				KeyPair myServerHttpsKeypair = UnitTestKeys.getKeyPair("https_server", this.keySize);
				
				server.getApplication().addApplicationInstanceCertificate( myServerKeypair );
				server.getApplication().getHttpsSettings().setKeyPair(myServerHttpsKeypair);
				server.getApplication().getHttpsSettings().setHostnameVerifier( SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER );
				server.getApplication().getHttpsSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
				server.getApplication().getHttpsSettings().setHttpsSecurityPolicies(httpsSecurityPolicy);
								
				// Bind my server to my endpoint. This binds socket to port 6001 as well 
				Endpoint endpointAddress = new Endpoint(uri, mode);
				if ( testServer ) endpointAddress.getEndpointConfiguration().setMaxMessageSize( maxMessageSize );
				server.bind( uri, endpointAddress);
				
				// Create client
				KeyPair myClientKeys = UnitTestKeys.getKeyPair("client", this.keySize);
				KeyPair myClientHttpsKeys = UnitTestKeys.getKeyPair("https_client", this.keySize);
				
				Client client = Client.createClientApplication( myClientKeys );
				client.getApplication().getHttpsSettings().setKeyPair( myClientHttpsKeys );
				client.getApplication().getHttpsSettings().setHostnameVerifier( SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER );
				client.getApplication().getHttpsSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
				if ( testClient ) client.getEndpointConfiguration().setMaxMessageSize( maxMessageSize );
				
				secureChannel = client.createSecureChannel(uri, uri, mode, myServerKeypair.certificate);		
				
				executeTest(secureChannel, msgSize, maxMessageSize);
				if ( !expectedSuccess[i] ) {
					throw new Exception("Expected failure.");
				}
			} catch (ServiceResultException e) {
				if ( expectedSuccess[i] ) throw e; 
			} finally {
				_tearDown();
			}
			
			
		}
	}
	
	void executeTest(SecureChannel c, int msgSize, int maxMessageSize)
	throws ServiceResultException, InterruptedException, ServiceFaultException
	{
		System.out.println("Test case:");
		System.out.println(" * Uri = "+uri);
		System.out.println(" * Security Policy = "+c.getSecurityPolicy().getPolicyUri());
		System.out.println(" * Security Mode = "+c.getMessageSecurityMode());
		System.out.println(" * KeySize = "+keySize);
		System.out.println(" * Max Message Size= "+maxMessageSize);
		System.out.println(" * Message Size = "+msgSize);
		byte[] data = new byte[msgSize];
		fill(data);
		TestStackRequest testRequest = new TestStackRequest(null, null, null, new Variant(data));

		System.out.print(" * Iteration: ");
		Runtime.getRuntime().gc();			
		long time = System.currentTimeMillis();		
		AsyncResult req = c.serviceRequestAsync( testRequest );			
			
		req.waitForResult(ITERATION_TIMEOUT, TimeUnit.SECONDS);
			
		// Barrier wait
		time = System.currentTimeMillis() - time;
			
		// Verify all requests
		TestStackResponse res = (TestStackResponse) req.waitForResult();
		verify((byte[]) res.getOutput().getValue());
			
		System.out.print(".");
		
		System.out.println("End.");
		System.out.println();			
	}

	// Fill array with debug data
	public void fill(byte[] data)
	{
		// Create random generator with fixed seed
		Random r = new Random(data.length);
		for (int i=0; i<data.length; i++)
			data[i] = (byte) (r.nextInt(256) - 128);
	}
	
	// Verify array of debug data
	public void verify(byte[] data)
	{
		// Create random generator with the same seed
		Random r = new Random(data.length);
		for (int i=0; i<data.length; i++)
			assertEquals((byte) (r.nextInt(256) - 128), data[i]);
	}	
	
}

