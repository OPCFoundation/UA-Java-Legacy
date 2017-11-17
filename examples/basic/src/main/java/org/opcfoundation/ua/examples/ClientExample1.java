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

import static org.opcfoundation.ua.utils.EndpointUtil.selectByMessageSecurityMode;
import static org.opcfoundation.ua.utils.EndpointUtil.selectByProtocol;
import static org.opcfoundation.ua.utils.EndpointUtil.sortBySecurityLevel;

import java.net.InetAddress;

import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.ReadRequest;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.examples.certs.ExampleKeys;
import org.opcfoundation.ua.transport.ServiceChannel;
import org.opcfoundation.ua.transport.security.KeyPair;

/**
 * In this example, a client connects to a server, discovers endpoints, chooses one endpoint and
 * connects to it, and makes a stack-test using a {@link ServiceChannel}.
 * 
 * Run this example with {@link ServerExample1}.
 */
public class ClientExample1 {

  public static void main(String[] args) throws Exception {

    ////////////// CLIENT //////////////
    // Load Client's Application Instance Certificate from file
    KeyPair myClientApplicationInstanceCertificate = ExampleKeys.getCert("Client");
    // Create Client
    Client myClient = Client.createClientApplication(myClientApplicationInstanceCertificate);
    KeyPair myHttpsCertificate = ExampleKeys.getHttpsCert("Client");
    myClient.getApplication().getHttpsSettings().setKeyPair(myHttpsCertificate);
    //////////////////////////////////////


    ////////// DISCOVER ENDPOINT /////////
    // Discover server's endpoints, and choose one
    String publicHostname = InetAddress.getLocalHost().getHostName();
    String url = "opc.tcp://" + publicHostname + ":8666/UAExample"; // ServerExample1
    // "https://"+publicHostname+":8443/UAExample"; // ServerExample1
    // "opc.tcp://"+publicHostname+":51210/"; // :51210=Sample Server
    // "https://"+publicHostname+":51212/SampleServer/"; // :51212=Sample Server
    // "opc.tcp://"+publicHostname+":62541/"; // :62541=DataAccess Server
    EndpointDescription[] endpoints = myClient.discoverEndpoints(url);
    // Filter out all but opc.tcp protocol endpoints
    if (url.startsWith("opc.tcp")) {
      endpoints = selectByProtocol(endpoints, "opc.tcp");
      // Filter out all but Signed & Encrypted endpoints
      endpoints = selectByMessageSecurityMode(endpoints, MessageSecurityMode.None);
      // Filter out all but Basic128 cryption endpoints
      // endpoints = selectBySecurityPolicy(endpoints, SecurityPolicy.BASIC128RSA15);
      // Sort endpoints by security level. The lowest level at the
      // beginning, the highest at the end of the array
      endpoints = sortBySecurityLevel(endpoints);
    } else {
      endpoints = selectByProtocol(endpoints, "https");
    }

    // Choose one endpoint
    EndpointDescription endpoint = endpoints[endpoints.length - 1];
    //////////////////////////////////////

    //////////// TEST-STACK ////////////
    // Create Channel
    ServiceChannel myChannel = myClient.createServiceChannel(endpoint);
    // Create Test Request
    ReadValueId[] nodesToRead = {new ReadValueId(Identifiers.RootFolder, Attributes.BrowseName, null, null)};
    ReadRequest req = new ReadRequest(null, 0.0, TimestampsToReturn.Both, nodesToRead);
    System.out.println("REQUEST: " + req);

    // Invoke service
    ReadResponse res = myChannel.Read(req);
    // Print result
    System.out.println("RESPONSE: " + res);
    //////////////////////////////////////


    ///////////// SHUTDOWN /////////////
    // Close channel
    myChannel.closeAsync();
    //////////////////////////////////////
    System.exit(0);
  }

}
