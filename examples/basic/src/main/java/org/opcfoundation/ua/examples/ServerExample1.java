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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.opcfoundation.ua.application.Application;
import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ActivateSessionRequest;
import org.opcfoundation.ua.core.ActivateSessionResponse;
import org.opcfoundation.ua.core.AddNodesRequest;
import org.opcfoundation.ua.core.AddNodesResponse;
import org.opcfoundation.ua.core.AddReferencesRequest;
import org.opcfoundation.ua.core.AddReferencesResponse;
import org.opcfoundation.ua.core.AttributeServiceSetHandler;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.BrowseNextRequest;
import org.opcfoundation.ua.core.BrowseNextResponse;
import org.opcfoundation.ua.core.BrowseRequest;
import org.opcfoundation.ua.core.BrowseResponse;
import org.opcfoundation.ua.core.BrowseResult;
import org.opcfoundation.ua.core.CancelRequest;
import org.opcfoundation.ua.core.CancelResponse;
import org.opcfoundation.ua.core.CloseSessionRequest;
import org.opcfoundation.ua.core.CloseSessionResponse;
import org.opcfoundation.ua.core.CreateSessionRequest;
import org.opcfoundation.ua.core.CreateSessionResponse;
import org.opcfoundation.ua.core.DeleteNodesRequest;
import org.opcfoundation.ua.core.DeleteNodesResponse;
import org.opcfoundation.ua.core.DeleteReferencesRequest;
import org.opcfoundation.ua.core.DeleteReferencesResponse;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.HistoryReadRequest;
import org.opcfoundation.ua.core.HistoryReadResponse;
import org.opcfoundation.ua.core.HistoryUpdateRequest;
import org.opcfoundation.ua.core.HistoryUpdateResponse;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.NodeManagementServiceSetHandler;
import org.opcfoundation.ua.core.QueryFirstRequest;
import org.opcfoundation.ua.core.QueryFirstResponse;
import org.opcfoundation.ua.core.QueryNextRequest;
import org.opcfoundation.ua.core.QueryNextResponse;
import org.opcfoundation.ua.core.ReadRequest;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.RegisterNodesRequest;
import org.opcfoundation.ua.core.RegisterNodesResponse;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.ResponseHeader;
import org.opcfoundation.ua.core.ServiceFault;
import org.opcfoundation.ua.core.SessionServiceSetHandler;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.core.TranslateBrowsePathsToNodeIdsRequest;
import org.opcfoundation.ua.core.TranslateBrowsePathsToNodeIdsResponse;
import org.opcfoundation.ua.core.UnregisterNodesRequest;
import org.opcfoundation.ua.core.UnregisterNodesResponse;
import org.opcfoundation.ua.core.UserTokenPolicy;
import org.opcfoundation.ua.core.WriteRequest;
import org.opcfoundation.ua.core.WriteResponse;
import org.opcfoundation.ua.examples.certs.ExampleKeys;
import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.HttpsSecurityPolicy;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.EndpointUtil;


/**
 * Simple Server example. This server responds to stack test and endpoint discover service requests.
 * 
 */
public class ServerExample1 {


  static class MyAttributeServiceHandler implements AttributeServiceSetHandler {

    @Override
    public void onHistoryRead(EndpointServiceRequest<HistoryReadRequest, HistoryReadResponse> msgExchange)
        throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    @Override
    public void onHistoryUpdate(EndpointServiceRequest<HistoryUpdateRequest, HistoryUpdateResponse> msgExchange)
        throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    @Override
    public void onRead(EndpointServiceRequest<ReadRequest, ReadResponse> msgExchange) throws ServiceFaultException {
      ReadRequest request = msgExchange.getRequest();
      ReadValueId[] nodesToRead = request.getNodesToRead();

      DataValue[] results = new DataValue[nodesToRead.length];
      for (int i = 0; i < nodesToRead.length; i++) {
        if (Identifiers.RootFolder.equals(nodesToRead[i].getNodeId())) {
          if (Attributes.BrowseName.equals(nodesToRead[i].getAttributeId())) {
            results[i] = new DataValue(new Variant(new QualifiedName("Root")));
          } else if (Attributes.DisplayName.equals(nodesToRead[i].getAttributeId())) {
            results[i] = new DataValue(new Variant(new LocalizedText("Root", LocalizedText.NO_LOCALE)));
          } else {
            results[i] = new DataValue(new StatusCode(StatusCodes.Bad_AttributeIdInvalid));
          }
        } else {
          results[i] = new DataValue(new StatusCode(StatusCodes.Bad_NodeIdUnknown));
        }
      }
      ReadResponse response = new ReadResponse(null, results, null);
      ResponseHeader responseHeader = createResponseHeader(msgExchange.getRequest().getRequestHeader());
      response.setResponseHeader(responseHeader);
      msgExchange.sendResponse(response);
    }

    @Override
    public void onWrite(EndpointServiceRequest<WriteRequest, WriteResponse> msgExchange) throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

  };

  static class MyNodeManagementServiceHandler implements NodeManagementServiceSetHandler {

    @Override
    public void onAddNodes(EndpointServiceRequest<AddNodesRequest, AddNodesResponse> req) throws ServiceFaultException {

    }

    @Override
    public void onAddReferences(EndpointServiceRequest<AddReferencesRequest, AddReferencesResponse> req)
        throws ServiceFaultException {

    }

    @Override
    public void onBrowse(EndpointServiceRequest<BrowseRequest, BrowseResponse> req) throws ServiceFaultException {
      BrowseRequest request = req.getRequest();
      BrowseResult[] Results = new BrowseResult[request.getNodesToBrowse().length];
      for (int i = 0; i < request.getNodesToBrowse().length; i++) {
        StatusCode statusCode;
        if (Identifiers.RootFolder.equals(request.getNodesToBrowse()[i].getNodeId())) {
          statusCode = StatusCode.GOOD;
        } else {
          statusCode = new StatusCode(StatusCodes.Bad_NodeIdUnknown);
        }
        Results[i] = new BrowseResult(statusCode, null, null);
      }
      BrowseResponse response = new BrowseResponse(null, Results, null);
      req.sendResponse(response);

    }

    @Override
    public void onBrowseNext(EndpointServiceRequest<BrowseNextRequest, BrowseNextResponse> req)
        throws ServiceFaultException {

    }

    @Override
    public void onDeleteNodes(EndpointServiceRequest<DeleteNodesRequest, DeleteNodesResponse> req)
        throws ServiceFaultException {

    }

    @Override
    public void onDeleteReferences(EndpointServiceRequest<DeleteReferencesRequest, DeleteReferencesResponse> req)
        throws ServiceFaultException {

    }

    @Override
    public void onQueryFirst(EndpointServiceRequest<QueryFirstRequest, QueryFirstResponse> req)
        throws ServiceFaultException {

    }

    @Override
    public void onQueryNext(EndpointServiceRequest<QueryNextRequest, QueryNextResponse> req)
        throws ServiceFaultException {

    }

    @Override
    public void onRegisterNodes(EndpointServiceRequest<RegisterNodesRequest, RegisterNodesResponse> req)
        throws ServiceFaultException {

    }

    @Override
    public void onTranslateBrowsePathsToNodeIds(
        EndpointServiceRequest<TranslateBrowsePathsToNodeIdsRequest, TranslateBrowsePathsToNodeIdsResponse> req)
        throws ServiceFaultException {

    }

    @Override
    public void onUnregisterNodes(EndpointServiceRequest<UnregisterNodesRequest, UnregisterNodesResponse> req)
        throws ServiceFaultException {

    }

  }


  static class MyServerExample extends Server implements SessionServiceSetHandler {

    public MyServerExample(Application application) throws Exception {
      super(application);
      addServiceHandler(this);

      // Add Client Application Instance Certificate validator - Accept them all (for now)
      application.getOpctcpSettings().setCertificateValidator(CertificateValidator.ALLOW_ALL);
      application.getHttpsSettings().setCertificateValidator(CertificateValidator.ALLOW_ALL);

      // The HTTPS SecurityPolicies are defined separate from the endpoint securities
      application.getHttpsSettings().setHttpsSecurityPolicies(HttpsSecurityPolicy.ALL_104);

      // Peer verifier
      application.getHttpsSettings().setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

      // Optionally limit the number of connections - note that if you limit sessions, these should
      // be at least that big. By default, both values are 100

      // application.getOpctcpSettings().setMaxConnections(1);
      // application.getHttpsSettings().setMaxConnections(1);

      // Load Servers's Application Instance Certificate...
      KeyPair myServerApplicationInstanceCertificate = ExampleKeys.getCert("ServerExample1");
      application.addApplicationInstanceCertificate(myServerApplicationInstanceCertificate);
      // ...and HTTPS certificate
      KeyPair myHttpsCertificate = ExampleKeys.getHttpsCert("ServerExample1");
      application.getHttpsSettings().setKeyPair(myHttpsCertificate);

      // Add User Token Policies
      addUserTokenPolicy(UserTokenPolicy.ANONYMOUS);
      addUserTokenPolicy(UserTokenPolicy.SECURE_USERNAME_PASSWORD);

      // Create an endpoint for each network interface
      String hostname = EndpointUtil.getHostname();
      String bindAddress, endpointAddress;
      for (String addr : EndpointUtil.getInetAddressNames()) {
        bindAddress = "opc.https://" + addr + ":8443/UAExample";
        endpointAddress = "opc.https://" + hostname + ":8443/UAExample";
        System.out.println(endpointAddress + " bound at " + bindAddress);
        /*
         * Please read specification 1.04 Part 6 section 7.4.1, also sign modes can be used in
         * addition to none in HTTPS.
         */
        bind(bindAddress, endpointAddress, SecurityMode.NONE);

        bindAddress = "opc.tcp://" + addr + ":8666/UAExample";
        endpointAddress = "opc.tcp://" + hostname + ":8666/UAExample";
        System.out.println(endpointAddress + " bound at " + bindAddress);

        Set<SecurityPolicy> policies = new HashSet<SecurityPolicy>();
        policies.add(SecurityPolicy.NONE);
        policies.addAll(SecurityPolicy.ALL_SECURE_101);
        policies.addAll(SecurityPolicy.ALL_SECURE_102);
        policies.addAll(SecurityPolicy.ALL_SECURE_103);
        policies.addAll(SecurityPolicy.ALL_SECURE_104);
        bind(bindAddress, endpointAddress, SecurityMode.combinations(MessageSecurityMode.ALL, policies));
      }

      //////////////////////////////////////
    }

    @Override
    public void onActivateSession(EndpointServiceRequest<ActivateSessionRequest, ActivateSessionResponse> msgExchange)
        throws ServiceFaultException {
      ActivateSessionResponse response = new ActivateSessionResponse();
      ResponseHeader responseHeader = createResponseHeader(msgExchange.getRequest().getRequestHeader());
      response.setResponseHeader(responseHeader);
      response.setServerNonce(CryptoUtil.createNonce(32));
      msgExchange.sendResponse(response);
    }

    @Override
    public void onCancel(EndpointServiceRequest<CancelRequest, CancelResponse> msgExchange)
        throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    @Override
    public void onCloseSession(EndpointServiceRequest<CloseSessionRequest, CloseSessionResponse> msgExchange)
        throws ServiceFaultException {
      CloseSessionResponse response = new CloseSessionResponse();
      ResponseHeader responseHeader = createResponseHeader(msgExchange.getRequest().getRequestHeader());
      response.setResponseHeader(responseHeader);
      msgExchange.sendResponse(response);
    }

    @Override
    public void onCreateSession(EndpointServiceRequest<CreateSessionRequest, CreateSessionResponse> msgExchange)
        throws ServiceFaultException {
      CreateSessionRequest request = msgExchange.getRequest();
      CreateSessionResponse response = new CreateSessionResponse();
      ResponseHeader responseHeader = createResponseHeader(msgExchange.getRequest().getRequestHeader());
      response.setResponseHeader(responseHeader);

      byte[] token = new byte[32];
      byte[] nonce = new byte[32];
      Random r = new Random();
      r.nextBytes(nonce);
      r.nextBytes(token);
      response.setAuthenticationToken(new NodeId(0, token));
      EndpointConfiguration endpointConfiguration = EndpointConfiguration.defaults();
      response.setMaxRequestMessageSize(UnsignedInteger.valueOf(
          Math.max(endpointConfiguration.getMaxMessageSize(), request.getMaxResponseMessageSize().longValue())));
      response.setRevisedSessionTimeout(Math.max(request.getRequestedSessionTimeout(), 60 * 1000));
      KeyPair cert = getApplication().getApplicationInstanceCertificates()[0];
      response.setServerCertificate(ByteString.valueOf(cert.getCertificate().getEncoded()));
      response.setServerEndpoints(this.getEndpointDescriptions());
      response.setServerNonce(ByteString.valueOf(nonce));
      ByteString clientCertificate = request.getClientCertificate();
      ByteString clientNonce = request.getClientNonce();
      SecurityPolicy securityPolicy = msgExchange.getChannel().getSecurityPolicy();
      response.setServerSignature(
          getServerSignature(clientCertificate, clientNonce, securityPolicy, cert.getPrivateKey().getPrivateKey()));

      response.setServerSoftwareCertificates(getApplication().getSoftwareCertificates());
      response.setSessionId(new NodeId(0, "Session-" + UUID.randomUUID()));
      msgExchange.sendResponse(response);
    }

    private SignatureData getServerSignature(ByteString clientCertificate, ByteString clientNonce,
        SecurityPolicy securityPolicy, final RSAPrivateKey privateKey) throws ServiceFaultException {
      if (clientCertificate != null) {
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
          s.write(clientCertificate.getValue());
        } catch (IOException e) {
          throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_SecurityChecksFailed));
        } catch (Exception e) {
          throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NonceInvalid));
        }
        try {
          s.write(clientNonce.getValue());
        } catch (IOException e) {
          throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NonceInvalid));
        } catch (Exception e) {
          throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NonceInvalid));
        }
        try {
          SecurityAlgorithm algorithm = securityPolicy.getAsymmetricSignatureAlgorithm();
          return new SignatureData(algorithm.getUri(),
              ByteString.valueOf(CryptoUtil.getCryptoProvider().signAsymm(privateKey, algorithm, s.toByteArray())));

        } catch (ServiceResultException e) {
          throw new ServiceFaultException(e);
        }
      }
      return null;
    }
  }

  public static ResponseHeader createResponseHeader(RequestHeader requestHeader) {
    ResponseHeader responseHeader = new ResponseHeader();
    responseHeader.setServiceResult(StatusCode.GOOD);
    responseHeader.setRequestHandle(requestHeader.getRequestHandle());
    return responseHeader;
  }

  public static void main(String[] args) throws Exception {
    ////////////// SERVER //////////////
    // Create UA Server Application
    // Create UA Service Server
    Application myServerApplication = new Application();
    MyServerExample myServer = new MyServerExample(myServerApplication);

    myServer.addServiceHandler(new MyNodeManagementServiceHandler());
    myServer.addServiceHandler(new MyAttributeServiceHandler());

    //////////////////////////////////////
    // Press enter to shutdown
    System.out.println("Press enter to shutdown");
    System.in.read();
    //////////////////////////////////////


    ///////////// SHUTDOWN /////////////
    // Close the server by unbinding all endpoints
    myServer.getApplication().close();
    //////////////////////////////////////

  }

}
