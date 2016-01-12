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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import junit.framework.TestCase;

import org.junit.Assert;
import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.application.TestStackService;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.TestStackRequest;
import org.opcfoundation.ua.core.TestStackResponse;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.SecureChannel;
import org.opcfoundation.ua.transport.security.BcCryptoProvider;
import org.opcfoundation.ua.transport.security.HttpsSecurityPolicy;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.StackUtils;

/**
 * Stress test & performance test using a loop-back server.
 * 
 */
public class TestStack extends TestCase {
	
	/** Timeout for a single test (Seconds) */
	final static int ITERATION_TIMEOUT = 300;
	
	String uri;
	int keySize;
	Server server;
	Endpoint endpoint;
	SecureChannel secureChannel;
	
	public void testOPC_TCP_BASIC128RSA15_SIGN_JCE() throws Exception {
		try {
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC128RSA15_SIGN, 2048);
			executeTest();
		} finally {
			_tearDown();
		}
	}

	public void testOPC_TCP_BASIC128RSA15_SIGN_ENCRYPT_JCE() throws Exception {
		try {
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC128RSA15_SIGN_ENCRYPT, 2048);
			executeTest();
		} finally {
			_tearDown();
		}
	}
	
	public void testOPC_TCP_BASIC256_SIGN_JCE() throws Exception {
		try {
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256_SIGN, 2048);
			executeTest();
		} finally {
			_tearDown();
		}
	}
	
	public void testOPC_TCP_BASIC256_SIGN_ENCRYPT_JCE() throws Exception {
		try {
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256_SIGN_ENCRYPT, 2048);
			executeTest();
		} finally {
			_tearDown();
		}
	}
	
	public void testOPC_TCP_BASIC256SHA256_SIGN_ENCRYPT_JCE() throws Exception {
		try {
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256SHA256_SIGN_ENCRYPT, 4096);
			executeTest();
		} finally {
			_tearDown();
		}	
	}
	
	public void testOPC_TCP_BASIC256SHA256_SIGN_JCE() throws Exception {
		try {
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256SHA256_SIGN, 4096);
			executeTest();
		} finally {
			_tearDown();
		}
	}	
	
	public void testHttps_TLS_1_0() throws Exception {
		try {
			_setupTest("https://localhost:6125/TestServer", HttpsSecurityPolicy.TLS_1_0, 2048);
			executeTest();
		} finally {
			_tearDown();
		}
	}	

	public void testHttps_TLS_1_1() throws Exception {
		try {
			_setupTest("https://localhost:6125/TestServer", HttpsSecurityPolicy.TLS_1_1, 2048);
			executeTest();
		} finally {
			_tearDown();
		}
	}	

	public void ignoreTestHttps_TLS_1_2() throws Exception {
		try {
			_setupTest("https://localhost:6125/TestServer", HttpsSecurityPolicy.TLS_1_2, 4096);
			executeTest();
		} finally {
			_tearDown();
		}
	}	
	
	public void testOPC_TCP_NONE() throws Exception {
		try {
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.NONE, 2048);
			executeTest();
		} finally {
			_tearDown();
		}
	}
	
	public void testOPC_TCP_BASIC128RSA15_SIGN_BC() throws Exception {
		try {
			CryptoUtil.setCryptoProvider(new BcCryptoProvider());
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC128RSA15_SIGN, 2048);
			executeTest();
		} finally {
			_tearDown();
		}
	}
	
	public void testOPC_TCP_BASIC128RSA15_SIGN_ENCRYPT_BC() throws Exception {
		try {
			CryptoUtil.setCryptoProvider(new BcCryptoProvider());
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC128RSA15_SIGN_ENCRYPT, 2048);
			executeTest();
		} finally {
			_tearDown();
		}
	}
	
	public void testOPC_TCP_BASIC256_SIGN_ENCRYPT_BC() throws Exception {
		try {
			CryptoUtil.setCryptoProvider(new BcCryptoProvider());
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256_SIGN_ENCRYPT, 2048, null);
			executeTest();
		} finally {
			_tearDown();
			CryptoUtil.setCryptoProvider(null);
		}
	}
	
	public void testOPC_TCP_BASIC256SHA256_SIGN_ENCRYPT_BC() throws Exception {
		try {
			CryptoUtil.setCryptoProvider(new BcCryptoProvider());
			_setupTest("opc.tcp://localhost:6125/TestServer", SecurityMode.BASIC256SHA256_SIGN_ENCRYPT, 4096, null);
			executeTest();
		} finally {
			_tearDown();
			CryptoUtil.setCryptoProvider(null);
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

	void _setupTest(String uri, HttpsSecurityPolicy httpsSecurityPolicy, int keysize) throws ServiceResultException
	{
		_setupTest(uri, SecurityMode.NONE, keysize, httpsSecurityPolicy);
	}

	void _setupTest(String uri, SecurityMode mode, int keysize) throws ServiceResultException
	{
		_setupTest(uri, mode, keysize, null);
	}

	void _setupTest(String uri, SecurityMode mode, int keysize, HttpsSecurityPolicy httpsSecurityPolicy) throws ServiceResultException
	{
		
		this.uri = uri;
		this.keySize = keysize;
		boolean https = uri.startsWith("https");
		
		// Create Server
		server = Server.createServerApplication();
		// Add a service to the server - TestStack echo
		server.addServiceHandler( new TestStackService() );
		
		// Add application instance certificate
		KeyPair myServerKeypair = UnitTestKeys.getKeyPair("server", keysize);
		server.getApplication().addApplicationInstanceCertificate( myServerKeypair );
		if ( https ) {
			KeyPair myServerHttpsKeypair = UnitTestKeys.getKeyPair("https_server", keysize);
			server.getApplication().getHttpsSettings().setKeyPair(myServerHttpsKeypair);
			server.getApplication().getHttpsSettings().setHttpsSecurityPolicies(new HttpsSecurityPolicy[] { httpsSecurityPolicy });
		}
		
		// Bind my server to my endpoint. This binds socket to port 6001 as well 
		server.bind( uri, uri, mode );
		
		// Create client
		KeyPair myClientKeys = UnitTestKeys.getKeyPair("client", keysize);
		Client client = Client.createClientApplication( myClientKeys );
		if ( https ) {
			KeyPair myClientHttpsKeys = UnitTestKeys.getKeyPair("https_client", keysize);
			client.getApplicationHttpsSettings().setKeyPair( myClientHttpsKeys );
			client.getApplication().getHttpsSettings().setHttpsSecurityPolicies(new HttpsSecurityPolicy[] { httpsSecurityPolicy });
			mode = SecurityMode.NONE;
		}
				
		secureChannel = client.createSecureChannel(uri, uri, mode, myServerKeypair.certificate);
	}

	void executeTest()
	throws ServiceResultException, InterruptedException, ServiceFaultException
	{
		int iterations = 2;
		int[] concurrentRequests = new int[] {      1000,     1000,      100,       10,      10};
		int[] msgSizes = new int[]           {         1,     1024,    10240,   102400, 1024000};

		System.out.println();
		
		for (int i=0; i<msgSizes.length; i++) {
			doTest(secureChannel, iterations, msgSizes[i], concurrentRequests[i]);
		}		
		StackUtils.shutdown();
	}
	
	private void doTest(SecureChannel c, int iterations, int msgSize, int concurrentRequests)
	throws ServiceResultException, InterruptedException, ServiceFaultException
	{
		System.out.println("Test case:");
		System.out.println(" * Uri = "+uri);
		System.out.println(" * Security Policy = "+c.getSecurityPolicy().getPolicyUri());
		System.out.println(" * Security Mode = "+c.getMessageSecurityMode());
		System.out.println(" * KeySize = "+keySize);
		System.out.println(" * Concurrent Requests = "+concurrentRequests);
		System.out.println(" * Message Size = "+msgSize);
		long bytes = msgSize * concurrentRequests;
		byte[] data = new byte[msgSize];
		fill(data);
		TestStackRequest testRequest = new TestStackRequest(null, null, null, new Variant(data));

		System.out.print(" * Iteration: ");
		ArrayList<Long> times = new ArrayList<Long>(); 
		for (int i=0; i<iterations; i++) {
			Runtime.getRuntime().gc();			
			AsyncResult<ServiceResponse> reqs[] = new AsyncResult[concurrentRequests];
			
			long time = System.currentTimeMillis();		
			for (int j=0; j<concurrentRequests; j++) {
				reqs[j] = c.serviceRequestAsync( testRequest );
			}
			
			if (!StackUtils.barrierWait(reqs, ITERATION_TIMEOUT)) 
				Assert.fail("Test failed, Timeouted ("+ITERATION_TIMEOUT+"s)");			
			
			// Barrier wait
			time = System.currentTimeMillis() - time;
			times.add(time);
			
			// Verify all requests
			for (AsyncResult req : reqs) {
				TestStackResponse res = (TestStackResponse) req.waitForResult();
				verify((byte[]) res.getOutput().getValue());
			}
			System.out.print(".");
		}
		System.out.println();
		
		Collections.sort(times);
		long medianTime = times.get(times.size()/2);
		System.out.println(" * Median time: "+medianTime+" ms");
		System.out.println(" * Round-trip transfer rate: "+ (bytes*1000 / medianTime)/(1024) + " KB/s" );
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
			Assert.assertEquals((byte) (r.nextInt(256) - 128), data[i]);
	}

}

