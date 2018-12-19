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

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.opcfoundation.ua.application.Application;
import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.application.Session;
import org.opcfoundation.ua.application.SessionChannel;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.DebugLogger;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.core.ActivateSessionRequest;
import org.opcfoundation.ua.core.ActivateSessionResponse;
import org.opcfoundation.ua.core.CallMethodRequest;
import org.opcfoundation.ua.core.CallMethodResult;
import org.opcfoundation.ua.core.CallRequest;
import org.opcfoundation.ua.core.CallResponse;
import org.opcfoundation.ua.core.CancelRequest;
import org.opcfoundation.ua.core.CancelResponse;
import org.opcfoundation.ua.core.CloseSessionRequest;
import org.opcfoundation.ua.core.CloseSessionResponse;
import org.opcfoundation.ua.core.CreateSessionRequest;
import org.opcfoundation.ua.core.CreateSessionResponse;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.GetEndpointsRequest;
import org.opcfoundation.ua.core.GetEndpointsResponse;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.MethodServiceSetHandler;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.SessionServiceSetHandler;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.core.UserTokenPolicy;
import org.opcfoundation.ua.examples.certs.ExampleKeys;
import org.opcfoundation.ua.transport.ChannelService;
import org.opcfoundation.ua.transport.EndpointServer;
import org.opcfoundation.ua.transport.SecureChannel;
import org.opcfoundation.ua.transport.TransportChannelSettings;
import org.opcfoundation.ua.transport.UriUtil;
import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.EndpointUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example showing how to setup ReverseHello connection (as described in 1.04
 * Part 6 section 7.1.3). Based on {@link ClientServerExample}.
 *
 */
class ReverseHelloClientServerExample {

	private static final Logger logger = LoggerFactory.getLogger(ReverseHelloClientServerExample.class);
	
	private static final NodeId LIST_SOLVERS = new NodeId(2, "ListSolvers");

	private static String serverEndpointUrl;

	private static class ReverseHelloServer extends Server
			implements
				MethodServiceSetHandler,
				SessionServiceSetHandler {

		public ReverseHelloServer(Application application) throws Exception {
			super(application);
			// Add method service set
			addServiceHandler(this);

			// Add Client Application Instance Certificate validator
			// This example allows all, a real application should use something else
			application.getOpctcpSettings()
					.setCertificateValidator(CertificateValidator.ALLOW_ALL);
			application.getHttpsSettings()
					.setCertificateValidator(CertificateValidator.ALLOW_ALL);

			// Load Servers's Application Instance Certificate...
			KeyPair myServerApplicationInstanceCertificate = ExampleKeys
					.getCert("ReverseHelloClientServerExample");
			application.addApplicationInstanceCertificate(
					myServerApplicationInstanceCertificate);

			// Add User Token Policies, this example only accepts the Anonymous
			addUserTokenPolicy(UserTokenPolicy.ANONYMOUS);

			// Create an endpoint for each network interface
			String hostname = EndpointUtil.getHostname();
			String bindAddress, endpointAddress;
			for (String addr : EndpointUtil.getInetAddressNames()) {
				bindAddress = "opc.tcp://" + addr + ":8666/UAExample";
				endpointAddress = "opc.tcp://" + hostname + ":8666/UAExample";
				serverEndpointUrl = endpointAddress;
				logger.info("{} bound at {}", endpointAddress, bindAddress);
				

				Set<SecurityPolicy> policies = new HashSet<SecurityPolicy>();
				policies.add(SecurityPolicy.NONE);
				policies.addAll(SecurityPolicy.ALL_SECURE_101);
				policies.addAll(SecurityPolicy.ALL_SECURE_102);
				policies.addAll(SecurityPolicy.ALL_SECURE_103);
				policies.addAll(SecurityPolicy.ALL_SECURE_104);
				bind(bindAddress, endpointAddress, SecurityMode
						.combinations(MessageSecurityMode.ALL, policies));
			}

		}

		@Override
		public void onCreateSession(
				EndpointServiceRequest<CreateSessionRequest, CreateSessionResponse> esr)
				throws ServiceFaultException {
			CreateSessionRequest req = esr.getRequest();
			CreateSessionResponse res = new CreateSessionResponse();
			byte[] token = new byte[32];
			Random r = new Random();
			r.nextBytes(token);
			SignatureData signatureData = new SignatureData();
			// signatureData.setAlgorithm(Algorithm)
			res.setAuthenticationToken(new NodeId(0, token));
			EndpointConfiguration endpointConfiguration = EndpointConfiguration
					.defaults();
			res.setMaxRequestMessageSize(UnsignedInteger
					.valueOf(Math.max(endpointConfiguration.getMaxMessageSize(),
							req.getMaxResponseMessageSize().longValue())));
			res.setRevisedSessionTimeout(
					Math.max(req.getRequestedSessionTimeout(), 60 * 1000));
			res.setServerCertificate(ByteString.valueOf(
					getApplication().getApplicationInstanceCertificates()[0]
							.getCertificate().encodedCertificate));
			res.setServerEndpoints(this.getEndpointDescriptions());
			res.setServerNonce(CryptoUtil.createNonce(32));
			res.setServerSignature(signatureData);
			res.setServerSoftwareCertificates(
					getApplication().getSoftwareCertificates());
			res.setSessionId(new NodeId(0, "Client"));
			esr.sendResponse(res);

		}

		@Override
		public void onActivateSession(
				EndpointServiceRequest<ActivateSessionRequest, ActivateSessionResponse> esr)
				throws ServiceFaultException {
			ActivateSessionResponse res = new ActivateSessionResponse();
			res.setServerNonce(CryptoUtil.createNonce(32));
			res.setResults(new StatusCode[]{StatusCode.GOOD});
			esr.sendResponse(res);

		}

		@Override
		public void onCloseSession(
				EndpointServiceRequest<CloseSessionRequest, CloseSessionResponse> esr)
				throws ServiceFaultException {
			CloseSessionResponse res = new CloseSessionResponse();
			esr.sendResponse(res);

		}

		@Override
		public void onCancel(
				EndpointServiceRequest<CancelRequest, CancelResponse> esr)
				throws ServiceFaultException {
		}

		@Override
		public void onCall(
				EndpointServiceRequest<CallRequest, CallResponse> esr)
				throws ServiceFaultException {

			CallResponse response = new CallResponse();
			CallMethodRequest[] reqs = esr.getRequest().getMethodsToCall();
			CallMethodResult[] results = new CallMethodResult[reqs.length];
			response.setResults(results);

			// Iterate all calls
			for (int i = 0; i < reqs.length; i++) {
				CallMethodRequest req = reqs[i];
				CallMethodResult result = results[i] = new CallMethodResult();

				NodeId methodId = req.getMethodId();
				if (LIST_SOLVERS.equals(methodId)) {
					String[] solvers = {"sovler1", "solver2", "solver3"};
					Variant solversInVariant = new Variant(solvers);
					result.setOutputArguments(new Variant[]{solversInVariant});
					result.setStatusCode(StatusCode.GOOD);
				}

				else {
					// Unknown method
					result.setStatusCode(
							new StatusCode(StatusCodes.Bad_MethodInvalid));
				}

			}

			esr.sendResponse(response);
		}

	}
	
	public static void main(String[] args) throws Exception {
		///// Server Application /////////////
		Application serverApplication = new Application();

		String hostname = EndpointUtil.getHostname();
		
		// In order for the ReverseHello to work, the client must know this value.
		serverApplication.setApplicationUri("urn:"+hostname+":reversehelloexampleserver");
		ReverseHelloServer myServer = new ReverseHelloServer(serverApplication);

		// Attach listener (debug logger) to each binding
		DebugLogger debugLogger = new DebugLogger(logger);
		for (EndpointServer b : myServer.getEndpointBindings()
				.getEndpointServers()) {
			b.addConnectionListener(debugLogger);
		}

		/*
		 * Client side "server" address to listen to Server-initiated
		 * connections. This must match the addressToConnect used in the
		 * myServer.bindReverse statement.
		 */
		String localUri = "opc.tcp://" + hostname + ":8888";
		
		// Makes reverse binding, after bindReverse returns,
		// the server will attempt to open connections to the
		// Given address (see 1.04 Part 6 section 7.1.3 for more info)
		myServer.bindReverse(localUri, serverEndpointUrl);

		////////////// CLIENT //////////////
		// Load Client's Application Instance Certificate from file
		KeyPair myClientApplicationInstanceCertificate = ExampleKeys
				.getCert("ClientServerExample");

		// Create Client
		Client myClient = Client.createClientApplication(
				myClientApplicationInstanceCertificate);
		myClient.setTimeout(1000); // timeout for normal service calls
		//////////////////////////////////////


		/*
		 * The server endpointUrl we would like to connect, if we were to
		 * connect normally and select endpoints etc. However in ReverseHello
		 * this is not used for connecting, but only to determine the endpoints
		 * after the connection is done by the server.
		 */
		String remoteUri = serverEndpointUrl; //"opc.tcp://" + hostname + ":8666/UAExample";


		
		SessionChannel myChannel = null;
		try {


			// Goal 1: Get a ReverseHello server -> client connection.
			// Goal 2: Make a GetEndpoints call to the server.
			// Goal 3: Select an Endpoint and make a Session.
			// Goal 4: Do something with the Session.

			/*
			 * Goal 1. We need a TransportChannelSettings object in order to
			 * configure ReverseHello.
			 * 
			 * We need and EndpointDescription suitable for making the initial
			 * GetEndpoints call. The GetEndpoints is called before making a
			 * Session and thus does not use security.
			 */
			EndpointDescription ed = new EndpointDescription();
			ed.setEndpointUrl(remoteUri);
			ed.setSecurityMode(MessageSecurityMode.None);
			ed.setSecurityPolicyUri(SecurityPolicy.NONE.getPolicyUri());

			TransportChannelSettings tcs = new TransportChannelSettings();
			tcs.setDescription(ed);

			//Call this to set a timeout on listening the server to open a connection
			//tcs.getOpctcpSettings().setReverseHelloAcceptTimeout(reverseHelloListenTimeout);
			
			/*
			 * 
			 * The given Server(Application)Uri that must appear in the ReverseHello Message the server sends.
			 * 
			 * NOTE! this call will block until the server has sent the ReverseHello.
			 */
			SecureChannel sc = myClient.createReverseSecureChannel(localUri, "urn:"+hostname+":reversehelloexampleserver", tcs);

			// Goal 2. Making the GetEndpoints call (see 1.04 Part 4 for more info).
			ChannelService cs = new ChannelService(sc);
			GetEndpointsRequest req = new GetEndpointsRequest(
					new RequestHeader(), "", new String[0], new String[0]);
			req.getRequestHeader().setTimeoutHint(
					UnsignedInteger.valueOf(myClient.getTimeout()));
			GetEndpointsResponse res = cs.GetEndpoints(req);

			// Goal 3. Selecting a suitable Endpoint and making a Session
			EndpointDescription[] endpoints = res.getEndpoints();
			endpoints = EndpointUtil.selectByProtocol(endpoints,
					UriUtil.getTransportProtocol(remoteUri));

			/*
			 * NOTE! at this point the SecureChannel is usually closed, as
			 * the selected Endpoint usually does not match the one used for the
			 * initial channel. Per the spec after a ReverseHello based channel has been closed, 
			 * the server must start making a new one by sending ReverseHello once again
			 */
			sc.close();
			tcs.setDescription(endpoints[0]);
			
			sc = myClient.createReverseSecureChannel(localUri, "urn:"+hostname+":reversehelloexampleserver", tcs);
			cs = new ChannelService(sc);
			
			
			// Create the Session
			Session mySession = myClient.createSession(sc);
			myChannel = new SessionChannel(myClient, mySession, sc);

			// Activate session
			myChannel.activate();
			//////////////////////////////////////

			// Goal 4. Doing something with the Server.
			///////////// EXECUTE //////////////
			CallRequest callRequest = new CallRequest();
			CallMethodRequest methodRequest = new CallMethodRequest();
			callRequest
					.setMethodsToCall(new CallMethodRequest[]{methodRequest});
			methodRequest.setMethodId(MyServerExample.LIST_SOLVERS);
			CallResponse callRes = myChannel.Call(callRequest);
			logger.info("Call response: {}", callRes);
			//////////////////////////////////////
			
		} finally {
			///////////// SHUTDOWN /////////////
			// Close client's channel
			if (myChannel != null) {
				myChannel.close();
			}
			// Close the server by unbinding all endpoints
			myServer.getApplication().close();
			//////////////////////////////////////
		}

	}

}
