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

import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.SessionChannel;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.BrowseDescription;
import org.opcfoundation.ua.core.BrowseDirection;
import org.opcfoundation.ua.core.BrowseResponse;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.examples.certs.ExampleKeys;
import org.opcfoundation.ua.transport.security.KeyPair;

public class DiscoveryExample {

  public static void main(String[] args) throws ServiceResultException {

    ////////////// CLIENT //////////////
    // Load Client's Application Instance Certificate from file
    KeyPair myClientApplicationInstanceCertificate = ExampleKeys.getCert("Cert");
    // Create Client
    Client myClient = Client.createClientApplication(myClientApplicationInstanceCertificate);
    KeyPair myHttpsCertificate = ExampleKeys.getHttpsCert("Client");
    myClient.getApplication().getHttpsSettings().setKeyPair(myHttpsCertificate);
    //////////////////////////////////////


    //////// DISCOVER ENDPOINTS ////////
    String uri = "opc.tcp://localhost:8666/UAExample"; // ServerExample1
    // uri = "opc.tcp://localhost:62541/";
    // uri = "https://localhost:62541/Quickstarts/DataAccessServer";
    // uri = "https://localhost:62542/Quickstarts/DataAccessServer"
    EndpointDescription[] endpoints = myClient.discoverEndpoints(uri, "");
    for (EndpointDescription ed : endpoints) {
      System.out.println(ed);
    }
    //////////////////////////////////////


    ///////// DISCOVER SERVERS /////////

    // NOTE: Use an URI for an actual server. The ServerExample1 does not support the Discovery
    // Service
    uri = "opc.tcp://localhost:62541/Quickstarts/DataAccessServer";
    // Discover server applications, when you have
    ApplicationDescription[] servers = myClient.discoverApplications(uri);

    // Choose one application
    ApplicationDescription server = servers[0];
    // Connect the application
    SessionChannel mySessionChannel = myClient.createSessionChannel(server);
    try {
      // Activate session
      mySessionChannel.activate();
      // mySessionChannel.activate("username", "password");
      //////////////////////////////////////

      ///////////// EXECUTE //////////////
      BrowseDescription browse = new BrowseDescription();
      browse.setNodeId(Identifiers.RootFolder);
      browse.setBrowseDirection(BrowseDirection.Forward);
      browse.setIncludeSubtypes(true);
      BrowseResponse res3 = mySessionChannel.Browse(null, null, null, browse);
      System.out.println(res3);
      //////////////////////////////////////


      ///////////// SHUTDOWN /////////////
      // close the session and the channel from the server
      mySessionChannel.close();
      //////////////////////////////////////

    } finally {
      mySessionChannel.dispose();
    }

  }

}
