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

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Locale;

import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.SessionChannel;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.cert.CertificateCheck;
import org.opcfoundation.ua.cert.DefaultCertificateValidator;
import org.opcfoundation.ua.cert.DefaultCertificateValidatorListener;
import org.opcfoundation.ua.cert.PkiDirectoryCertificateStore;
import org.opcfoundation.ua.cert.ValidationResult;
import org.opcfoundation.ua.core.ApplicationDescription;
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
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.HttpsSecurityPolicy;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.utils.CertificateUtils;

/**
 * Sample client creates a connection to OPC UA Server (1st arg), browses and reads a boolean value.
 * It is configured to work against NanoServer example, using the address opc.tcp://localhost:8666/
 * 
 * NOTE: Does not work against SeverExample1, since it does not support Browse
 */
public class SampleClient {

  private static class MyValidationListener implements DefaultCertificateValidatorListener {

    @Override
    public ValidationResult onValidate(Cert certificate, ApplicationDescription applicationDescription,
        EnumSet<CertificateCheck> passedChecks) {
      System.out.println("Validating Server Certificate...");
      if (passedChecks.containsAll(CertificateCheck.COMPULSORY)) {
        System.out.println("Server Certificate is valid and trusted, accepting certificate!");
        return ValidationResult.AcceptPermanently;
      } else {
        System.out.println("Certificate Details: " + certificate.getCertificate().toString());
        System.out.println("Do you want to accept this certificate?\n" + " (A=Always, Y=Yes, this time, N=No)");
        while (true) {
          try {
            char c;
            c = Character.toLowerCase((char) System.in.read());
            if (c == 'a') {
              return ValidationResult.AcceptPermanently;
            }
            if (c == 'y') {
              return ValidationResult.AcceptOnce;
            }
            if (c == 'n') {
              return ValidationResult.Reject;
            }
          } catch (IOException e) {
            System.out.println("Error reading input! Not accepting certificate.");
            return ValidationResult.Reject;
          }
        }
      }
    }

  }

  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.out.println("Usage: SampleClient [server uri]");
      return;
    }
    String url = args[0];
    System.out.print("SampleClient: Connecting to " + url + " .. ");

    ////////////// CLIENT //////////////
    // Create Client

    // Set default key size for created certificates. The default value is also 2048,
    // but in some cases you may want to specify a different size.
    CertificateUtils.setKeySize(2048);

    // Try to load an application certificate with the specified application name.
    // In case it is not found, a new certificate is created.
    final KeyPair pair = ExampleKeys.getCert("SampleClient");

    // Create the client using information provided by the created certificate
    final Client myClient = Client.createClientApplication(pair);

    myClient.getApplication().addLocale(Locale.ENGLISH);
    myClient.getApplication().setApplicationName(new LocalizedText("Java Sample Client", Locale.ENGLISH));
    myClient.getApplication().setProductUri("urn:JavaSampleClient");

    // Create a certificate store for handling server certificates.
    // The constructor uses relative path "SampleClientPKI/CA" as the base directory, storing
    // rejected certificates in folder "rejected" and trusted certificates in folder "trusted".
    // To accept a server certificate, a rejected certificate needs to be moved from rejected to
    // trusted folder. This can be performed by moving the certificate manually, using method
    // addTrustedCertificate of PkiDirectoryCertificateStore or, as in this example, using a
    // custom implementation of DefaultCertificateValidatorListener.
    final PkiDirectoryCertificateStore myCertStore = new PkiDirectoryCertificateStore("SampleClientPKI/CA");

    // Create a default certificate validator for validating server certificates in the certificate
    // store.
    final DefaultCertificateValidator myValidator = new DefaultCertificateValidator(myCertStore);

    // Set MyValidationListener instance as the ValidatorListener. In case a certificate is not
    // automatically accepted, user can choose to reject or accept the certificate.
    final MyValidationListener myValidationListener = new MyValidationListener();
    myValidator.setValidationListener(myValidationListener);

    // Set myValidator as the validator for OpcTcp and Https
    myClient.getApplication().getOpctcpSettings().setCertificateValidator(myValidator);
    myClient.getApplication().getHttpsSettings().setCertificateValidator(myValidator);

    // The HTTPS SecurityPolicies are defined separate from the endpoint securities
    myClient.getApplication().getHttpsSettings().setHttpsSecurityPolicies(HttpsSecurityPolicy.ALL);

    // The certificate to use for HTTPS
    KeyPair myHttpsCertificate = ExampleKeys.getHttpsCert("SampleClient");
    myClient.getApplication().getHttpsSettings().setKeyPair(myHttpsCertificate);

    SessionChannel mySession = myClient.createSessionChannel(url);
    // mySession.activate("username", "123");
    mySession.activate();
    //////////////////////////////////////

    ///////////// EXECUTE //////////////
    // Browse Root
    BrowseDescription browse = new BrowseDescription();
    browse.setNodeId(Identifiers.RootFolder);
    browse.setBrowseDirection(BrowseDirection.Forward);
    browse.setIncludeSubtypes(true);
    browse.setNodeClassMask(NodeClass.Object, NodeClass.Variable);
    browse.setResultMask(BrowseResultMask.All);
    BrowseResponse res3 = mySession.Browse(null, null, null, browse);
    System.out.println(res3);

    // Read Namespace Array
    ReadResponse res5 = mySession.Read(null, null, TimestampsToReturn.Neither,
        new ReadValueId(Identifiers.Server_NamespaceArray, Attributes.Value, null, null));
    String[] namespaceArray = (String[]) res5.getResults()[0].getValue().getValue();
    System.out.println(Arrays.toString(namespaceArray));

    // Read a variable (Works with NanoServer example!)
    ReadResponse res4 = mySession.Read(null, 500.0, TimestampsToReturn.Source,
        new ReadValueId(new NodeId(1, "Boolean"), Attributes.Value, null, null));
    System.out.println(res4);

    ///////////// SHUTDOWN /////////////
    mySession.close();
    mySession.closeAsync();
    //////////////////////////////////////

  }

}
