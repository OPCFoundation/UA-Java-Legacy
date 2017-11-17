/*
 * ======================================================================== Copyright (c) 2005-2015
 * The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 *
 * The complete license agreement can be found here: http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================
 */

package org.opcfoundation.ua.examples;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.opcfoundation.ua.application.Application;
import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.transport.ServiceChannel;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.CertificateUtils;
import org.opcfoundation.ua.utils.EndpointUtil;


/**
 * This example creates a server and a client. GENERATES NEW APPLICATION INSTANCE CERTIFICATE, if it
 * does not exist. The server is bound to port 6001 and serves to TestStack requests (See
 * {@link TestStackService}). The client connects to the server and makes a simple "Hello World"
 * request.
 */
public class GenerateCertificateExample {

  public static void main(String[] args) throws Exception {

    // //Add provider for certificate generator
    // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    ////////////// SERVER //////////////
    // Create UA Server Application
    Application myServerApplication = new Application();
    // Create UA Service Server
    Server myServer = new Server(myServerApplication);

    // Check that certificates exists, if not -> create
    KeyPair myServerApplicationInstanceCertificate = null;
    // Create Application Instance Certificatge
    {
      String certificateCommonName = "UA Sample Server";
      System.out.println("Generating new Certificate for Server using CN: " + certificateCommonName);
      String applicationUri = myServerApplication.getApplicationUri();
      String organisation = "Sample Organisation";
      int validityTime = 365;
      String hostName = EndpointUtil.getHostname();
      myServerApplicationInstanceCertificate = CertificateUtils.createApplicationInstanceCertificate(
          certificateCommonName, organisation, applicationUri, validityTime, hostName);
    }
    // Add application instance certificate
    myServerApplication.addApplicationInstanceCertificate(myServerApplicationInstanceCertificate);
    myServerApplication.getOpctcpSettings().setCertificateValidator(CertificateValidator.ALLOW_ALL);
    myServerApplication.getHttpsSettings().setCertificateValidator(CertificateValidator.ALLOW_ALL);
    myServerApplication.getHttpsSettings().setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

    // Bind my server to my endpoint. This opens the port 6001 as well.
    String bindAddress = "opc.tcp://localhost:6001/UAExample";
    String endpointAddress = "opc.tcp://localhost:6001/UAExample";
    myServer.bind(bindAddress, endpointAddress, SecurityMode.ALL);
    //////////////////////////////////////


    ////////////// CLIENT //////////////
    Application myClientApplication = new Application();
    // Load Client's Application Instance Certificate from file
    KeyPair myClientApplicationInstanceCertificate = null;

    // Create Client Application Instance Certificate
    {
      String certificateCommonName = "UA Sample Client";
      System.out.println("Generating new Certificate for Client using CN: " + certificateCommonName);
      String applicationUri = myClientApplication.getApplicationUri();
      String organisation = "Sample Organisation";
      int validityTime = 365;
      myClientApplicationInstanceCertificate = CertificateUtils
          .createApplicationInstanceCertificate(certificateCommonName, organisation, applicationUri, validityTime);
    }


    // Create Client
    Client myClient = new Client(myClientApplication);
    myClientApplication.addApplicationInstanceCertificate(myClientApplicationInstanceCertificate);
    //////////////////////////////////////


    /////////// DISCOVER ENDPOINT ////////
    // Discover server's endpoints, and choose one
    EndpointDescription[] endpoints = myClient.discoverEndpoints("opc.tcp://localhost:8666/UAExample"); // 51210=Sample
                                                                                                        // Server
    // Filter out all but opc.tcp protocol endpoints
    endpoints = EndpointUtil.selectByProtocol(endpoints, "opc.tcp");
    // Filter out all but Signed & Encrypted endpoints
    endpoints = EndpointUtil.selectByMessageSecurityMode(endpoints, MessageSecurityMode.SignAndEncrypt);
    // Filter out all but Basic128 cryption endpoints
    endpoints = EndpointUtil.selectBySecurityPolicy(endpoints, SecurityPolicy.BASIC128RSA15);
    // Sort endpoints by security level. The lowest level at the beginning, the highest at the end
    // of the array
    endpoints = EndpointUtil.sortBySecurityLevel(endpoints);
    // Choose one endpoint
    EndpointDescription endpoint = endpoints[endpoints.length - 1];
    //////////////////////////////////////


    //////////// Open and close connection ////////////
    // Create Channel
    ServiceChannel myChannel = myClient.createServiceChannel(endpoint);
    System.out.println("Connected to server at '" + endpoint.getEndpointUrl() + "' with Security Mode '"
        + endpoint.getSecurityMode() + "' and Security Policy '" + endpoint.getSecurityPolicyUri() + "'");

    ///////////// SHUTDOWN /////////////
    // Close channel
    myChannel.closeAsync();
    // Unbind endpoint. This also closes the socket 6001 as it has no more endpoints.
    myServer.getApplication().close();
    //////////////////////////////////////

  }


}
