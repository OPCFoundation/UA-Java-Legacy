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

import java.util.Arrays;
import java.util.Locale;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.opcfoundation.ua.application.Application;
import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.SessionChannel;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.BrowseDescription;
import org.opcfoundation.ua.core.BrowseDirection;
import org.opcfoundation.ua.core.BrowseResponse;
import org.opcfoundation.ua.core.BrowseResultMask;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.NodeClass;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.examples.certs.ExampleKeys;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.HttpsSecurityPolicy;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.utils.CertificateUtils;

/**
 * Sample client creates a connection to OPC Server (1st arg), browses and reads a bit. 
 * e.g. opc.tcp://localhost:51210/
 * 
 * NOTE: Does not work against SeverExample1, since it does not support Browse
 */
public class SampleClient {


	public static final Locale ENGLISH = Locale.ENGLISH;
	public static final Locale ENGLISH_FINLAND = new Locale("en", "FI");
	public static final Locale ENGLISH_US = new Locale("en", "US");

	public static final Locale FINNISH = new Locale("fi");
	public static final Locale FINNISH_FINLAND = new Locale("fi", "FI");
	
	public static final Locale GERMAN = Locale.GERMAN;
	public static final Locale GERMAN_GERMANY = new Locale("de", "DE");
	
	public static void main(String[] args) 
	throws Exception {
		if (args.length==0) {
			System.out.println("Usage: SampleClient [server uri]");
			return;
		}
		String url = args[0];
		System.out.print("SampleClient: Connecting to "+url+" .. ");
		
		//////////////  CLIENT  //////////////
		// Create Client
		Application myApplication = new Application();
		Client myClient = new Client(myApplication);
		myApplication.addLocale( ENGLISH );
		myApplication.setApplicationName( new LocalizedText("Java Sample Client", Locale.ENGLISH) );
		myApplication.setProductUri( "urn:JavaSampleClient" );
		
		CertificateUtils.setKeySize(1024); // default = 1024
		KeyPair pair = ExampleKeys.getCert("SampleClient");
		myApplication.addApplicationInstanceCertificate( pair );		

		// The HTTPS SecurityPolicies are defined separate from the endpoint securities
		myApplication.getHttpsSettings().setHttpsSecurityPolicies(HttpsSecurityPolicy.ALL);

		// Peer verifier
		myApplication.getHttpsSettings().setHostnameVerifier( SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER );
		myApplication.getHttpsSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );

		// The certificate to use for HTTPS
		KeyPair myHttpsCertificate = ExampleKeys.getHttpsCert("SampleClient"); 
		myApplication.getHttpsSettings().setKeyPair( myHttpsCertificate );

		// Connect to the given uri
		SessionChannel mySession = myClient.createSessionChannel(url);
//		mySession.activate("username", "123");
		mySession.activate();
		//////////////////////////////////////		
		
		/////////////  EXECUTE  //////////////		
		// Browse Root
		BrowseDescription browse = new BrowseDescription();
		browse.setNodeId( Identifiers.RootFolder );
		browse.setBrowseDirection( BrowseDirection.Forward );
		browse.setIncludeSubtypes( true );
		browse.setNodeClassMask( NodeClass.Object, NodeClass.Variable );
		browse.setResultMask( BrowseResultMask.All );
		BrowseResponse res3 = mySession.Browse( null, null, null, browse );				
		System.out.println(res3);

		// Read Namespace Array
		ReadResponse res5 = mySession.Read(
			null, 
			null, 
			TimestampsToReturn.Neither, 				
			new ReadValueId(Identifiers.Server_NamespaceArray, Attributes.Value, null, null ) 
		);
		String[] namespaceArray = (String[]) res5.getResults()[0].getValue().getValue();
		System.out.println(Arrays.toString(namespaceArray));
		
		// Read a variable
		ReadResponse res4 = mySession.Read(
			null, 
			500.0, 
			TimestampsToReturn.Source, 
			new ReadValueId(new NodeId(6, 1710), Attributes.Value, null, null ) 
		);		
		System.out.println(res4);
		
		res4 = mySession.Read(
			null, 
			500.0, 
			TimestampsToReturn.Source, 
			new ReadValueId(new NodeId(6, 1710), Attributes.DataType, null, null ) 
		);		
		System.out.println(res4);
		
		
		/////////////  SHUTDOWN  /////////////
		mySession.close();
		mySession.closeAsync();
		//////////////////////////////////////	
		
	}
	
}
