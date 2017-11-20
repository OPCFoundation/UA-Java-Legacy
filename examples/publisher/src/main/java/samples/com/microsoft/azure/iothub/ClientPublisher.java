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

 
package samples.com.microsoft.azure.iothub;

import static org.opcfoundation.ua.utils.EndpointUtil.selectByMessageSecurityMode;
import static org.opcfoundation.ua.utils.EndpointUtil.selectByProtocol;
import static org.opcfoundation.ua.utils.EndpointUtil.selectBySecurityPolicy;
import static org.opcfoundation.ua.utils.EndpointUtil.sortBySecurityLevel;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.json.simple.JSONObject;
import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.SessionChannel;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.BrowseDescription;
import org.opcfoundation.ua.core.BrowseDirection;
import org.opcfoundation.ua.core.BrowseResponse;
import org.opcfoundation.ua.core.BrowseResultMask;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.NodeClass;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.transport.security.Cert;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.PrivKey;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.CertificateUtils;

import com.microsoft.azure.iothub.DeviceClient;
import com.microsoft.azure.iothub.IotHubClientProtocol;
import com.microsoft.azure.iothub.IotHubEventCallback;
import com.microsoft.azure.iothub.IotHubMessageResult;
import com.microsoft.azure.iothub.IotHubStatusCode;
import com.microsoft.azure.iothub.Message;


/**
 * In this example, a OPC client connects to a server, discovers endpoints, 
 * chooses one endpoint and connects to it
 * 
 * The data from a node is converted to JSON and sent to Azure IoTHub. 
 *
 * This sample requires Azure IotHub to publish data.
 *
 * Please follow instructions of the following 
 * Github project: http://www.github.com/Azure/azure-iot-sdks/java
 * to obtain a connection string for this sample.
 *
 * ======================================================================*/


public class ClientPublisher {

	/** Used as a counter in the message callback. */
	protected static class Counter
	{
		protected int num;

		public Counter(int num)
		{
			this.num = num;
		}

		public int get()
		{
			return this.num;
		}

		public void increment()
		{
			this.num++;
		}

		@Override
		public String toString()
		{
			return Integer.toString(this.num);
		}
	}

	private static final String PRIVKEY_PASSWORD = "Opc.Ua";

	/**
		* Load file certificate and private key from applicationName.der & .pfx - or create ones if they do not exist
		* @return the KeyPair composed of the certificate and private key
		* @throws ServiceResultException
		*/
	private static KeyPair getOPCCert(String applicationName)
	throws ServiceResultException
	{
		File certFile = new File(applicationName + ".der");
		File privKeyFile =  new File(applicationName+ ".pem");
		try {
			Cert myServerCertificate = Cert.load( certFile );
			PrivKey myServerPrivateKey = PrivKey.load(privKeyFile, PRIVKEY_PASSWORD);
			return new KeyPair(myServerCertificate, myServerPrivateKey); 
		} catch (CertificateException e) {
			throw new ServiceResultException( e );
		} catch (IOException e) {		
			try {
				String hostName = InetAddress.getLocalHost().getHostName();
				String applicationUri = "urn:"+hostName+":"+applicationName;
				KeyPair keys = CertificateUtils.createApplicationInstanceCertificate(applicationName, null, applicationUri, 3650, hostName);
				keys.getCertificate().save(certFile);
				PrivKey privKeySecure = keys.getPrivateKey();
				privKeySecure.save(privKeyFile, PRIVKEY_PASSWORD);
				return keys;
			} catch (Exception e1) {
				throw new ServiceResultException( e1 );
			}
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceResultException( e );
		} catch (Exception e) {
			throw new ServiceResultException( e );
		}
	}	

	protected static class MessageCallback
			implements com.microsoft.azure.iothub.MessageCallback
	{
		public IotHubMessageResult execute(Message msg,
				Object context)
		{
			Counter counter = (Counter) context;
			System.out.println(
					"Received message " + counter.toString()
							+ " with content: " + new String(msg.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET));

			int switchVal = counter.get() % 3;
			IotHubMessageResult res;
			switch (switchVal)
			{
				case 0:
					res = IotHubMessageResult.COMPLETE;
					break;
				case 1:
					res = IotHubMessageResult.ABANDON;
					break;
				case 2:
					res = IotHubMessageResult.REJECT;
					break;
				default:
					// should never happen.
					throw new IllegalStateException(
							"Invalid message result specified.");
			}

			System.out.println(
					"Responding to message " + counter.toString()
							+ " with " + res.name());

			counter.increment();

			return res;
		}
	}
	
	// Our MQTT doesn't support abandon/reject, so we will only display the messaged received
	// from IoTHub and return COMPLETE
	protected static class MessageCallbackMqtt implements com.microsoft.azure.iothub.MessageCallback
	{
		public IotHubMessageResult execute(Message msg, Object context)
		{
			Counter counter = (Counter) context;
			System.out.println(
					"Received message " + counter.toString()
							+ " with content: " + new String(msg.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET));

			counter.increment();

			return IotHubMessageResult.COMPLETE;
		}
	}

	protected static class EventCallback implements IotHubEventCallback{
		public void execute(IotHubStatusCode status, Object context){
			Integer i = (Integer) context;
			System.out.println("IoT Hub responded to message "+i.toString()
				+ " with status " + status.name());
		}
	}

	static void printHelp()
	{
		System.out.format(
			"Argument error.\n"
			+ " Call: ClientPublisher \n"
			+ "[OPC Server string] - OPC server,e.g. [opc.tcp://server:51210/UA/SampleServer]\n"
			+ "[Device connection string] - String containing Hostname, Device Id & Device Key in the following format: HostName=<iothub_host_name>;DeviceId=<device_id>;SharedAccessKey=<device_key>\n"
			+ "[Protocol] - (https | amqps | mqtt | amqps_ws).\n");
		System.exit(1);
	}
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Starting ClientPublisher sample");

		if (args.length < 2 || args.length > 3)
		{
			printHelp();
			return;
		}

		String opcUrl = args[0];
		String connString = args[1];
		IotHubClientProtocol protocol = IotHubClientProtocol.AMQPS;
		if (args.length > 2)
		{
			String protocolStr = args[2].toLowerCase();
			if (protocolStr.equals("https"))
			{
				protocol = IotHubClientProtocol.HTTPS;
			}
			else if (protocolStr.equals("amqps"))
			{
				protocol = IotHubClientProtocol.AMQPS;
			}
			else if (protocolStr.equals("mqtt"))
			{
				protocol = IotHubClientProtocol.MQTT;
			}
			else if (protocolStr.equals("amqps_ws"))
			{
				protocol = IotHubClientProtocol.AMQPS_WS;
			}
			else
			{
				System.out.format("Protocol: %s not found, using AMQPS instead\n", protocolStr);
			}
		}
		 
		System.out.format("OPC Server: %s\n", opcUrl);
		System.out.format("IoT Hub ConnectionString: %s\n", connString);
		System.out.format("Start communication using protocol %s.\n", protocol.name());

		//////////////  CLIENT  //////////////
		// Load Client's Application Instance Certificate from file
		KeyPair myClientApplicationInstanceCertificate = getOPCCert("ClientPublisher");
		// Create Client
		Client myClient = Client.createClientApplication( myClientApplicationInstanceCertificate );

		////////// DISCOVER ENDPOINT /////////
		// Discover server's endpoints, and choose one
		String publicHostname = InetAddress.getLocalHost().getHostName();
	  
		EndpointDescription[] endpoints = myClient.discoverEndpoints(opcUrl);
		// Filter out all but opc.tcp protocol endpoints
		if (opcUrl.startsWith("opc.tcp")) {
			endpoints = selectByProtocol(endpoints, "opc.tcp");
			// Filter out all but Signed & Encrypted endpoints
			endpoints = selectByMessageSecurityMode(endpoints, MessageSecurityMode.SignAndEncrypt);
			// Filter out all but Basic128 cryption endpoints
			endpoints = selectBySecurityPolicy(endpoints, SecurityPolicy.BASIC128RSA15);
			// Sort endpoints by security level. The lowest level at the
			// beginning, the highest at the end of the array
			endpoints = sortBySecurityLevel(endpoints);
		} 
		else
		{
			// we don't have a valid https cert for now
			System.out.format("HTTPS protocol not suported.\n");
			System.exit(0);
			endpoints = selectByProtocol(endpoints, "https");
		}   

		// Choose one endpoint
		EndpointDescription endpoint = endpoints[endpoints.length-1]; 

		// fix connection issue on localhost
		if (endpoint.getEndpointUrl().contains(publicHostname))
		{
			endpoint.setEndpointUrl(endpoint.getEndpointUrl().replace(publicHostname, "localhost"));
		}
		else
		{
			// fix .Net UA Server bug, patch localhost with hostname of server
			if (endpoint.getEndpointUrl().contains("localhost"))
			{
				URL opcURL = new URL(opcUrl.replace("opc.tcp", "http"));
				endpoint.setEndpointUrl(endpoint.getEndpointUrl().replace("localhost", opcURL.getHost()));
			}
		}

		////////////////////// AZURE IotHub ////////////////////////////
		// connect to Azure IotHub with connection string and protocol
		// see http://www.github.com/Azure/azure-iot-sdks/java
		//         
		DeviceClient client = new DeviceClient(connString, protocol);
		
		System.out.println("Successfully created an IoT Hub client.");

		if (protocol == IotHubClientProtocol.MQTT)
		{
			MessageCallbackMqtt callback = new MessageCallbackMqtt();
			Counter counter = new Counter(0);
			client.setMessageCallback(callback, counter);
		}
		else
		{
			MessageCallback callback = new MessageCallback();
			Counter counter = new Counter(0);
			client.setMessageCallback(callback, counter);
		}
		System.out.println("Successfully set message callback.");

		client.open();

		System.out.println("Opened connection to IoT Hub.");

		System.out.println("Beginning to receive messages...");


		////////////  SESSION  ////////////
		// Create Session
		SessionChannel mySession = myClient.createSessionChannel( endpoint );
		mySession.activate();

		System.out.println("Opened session to OPC Server.");
		
		/////////////  EXECUTE  //////////////		
		// Browse Root
		BrowseDescription browse = new BrowseDescription();
		browse.setNodeId( Identifiers.RootFolder );
		browse.setBrowseDirection( BrowseDirection.Forward );
		browse.setIncludeSubtypes( true );
		browse.setNodeClassMask( NodeClass.Object, NodeClass.Variable );
		browse.setResultMask( BrowseResultMask.All );
		BrowseResponse browseResponse = mySession.Browse( null, null, null, browse );
		System.out.println(browseResponse);

		/////////////  PUBLISH  /////////////
		// Poll OPC server and publish to Azure IotHub
	   
		int count = 0;
		while (count++ < 100)
		{
			// Read current time
			NodeId nodeId = Identifiers.Server_ServerStatus_CurrentTime;
			ReadResponse opcCurrentTime = mySession.Read(
				null, 
				500.0, 
				TimestampsToReturn.Both, 
				new ReadValueId(nodeId, Attributes.Value, null, null ) 
			);

			// convert node to JSON
			JSONObject obj = new JSONObject();
			obj.put("Id", nodeId.toString());
			obj.put("Uri", endpoints[0].getServer().getApplicationUri());
			
			JSONObject value = new JSONObject();
			value.put("Value", opcCurrentTime.getResults()[0].getValue().toString());
			value.put("SourceTimestamp", opcCurrentTime.getResults()[0].getSourceTimestamp().toString());
			value.put("ServerTimestamp", opcCurrentTime.getResults()[0].getServerTimestamp().toString());
			
			JSONObject monitoredItem = new JSONObject();
			monitoredItem.put("MonitoredItem", obj);
			monitoredItem.put("ClientHandle", 2);
			monitoredItem.put("Value", value);
			
			// publish JSON string to IotHub
			String msgStr = monitoredItem.toString();
			try
			{
				Message msg = new Message(msgStr);
				msg.setProperty("messageCount", Integer.toString(count));
								msg.setExpiryTime(5000);
				System.out.println(msgStr);
				EventCallback eventCallback = new EventCallback();
				client.sendEventAsync(msg, eventCallback, count);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			try {
				System.out.println(opcCurrentTime);
				System.out.println(msgStr);
				Thread.sleep(2000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		
		/////////////  SHUTDOWN  /////////////
		mySession.close();
		mySession.closeAsync();
		//////////////////////////////////////	
		
		client.close();

		//////////////////////////////////////	
		System.exit(0);
	}

}
