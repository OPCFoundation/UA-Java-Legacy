package org.opcfoundation.ua.transport.tcp.io;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.transport.ReverseTransportChannelSettings;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.EndpointUtil;

public class TcpConnectionTest {

	/**
	 * Timeout for all tests in this class.
	 */
	@Rule
	public Timeout timeout = Timeout.seconds(10);
	
	
	@Test(expected=ServiceResultException.class)
	public void reverseHelloTimeout() throws Exception {
		TcpConnection sut = new TcpConnection();
		
		String url = "opc.tcp://"+EndpointUtil.getHostname()+":8888";
		ReverseTransportChannelSettings settings = new ReverseTransportChannelSettings();
		settings.setReverseHelloServerUri("urn:unknownserver:test");
		settings.setConfiguration(EndpointConfiguration.defaults());
		settings.getOpctcpSettings().setReverseHelloAcceptTimeout(2000);
		EndpointDescription serverEndpoint = new EndpointDescription();
		serverEndpoint.setSecurityMode(MessageSecurityMode.None);
		serverEndpoint.setSecurityPolicyUri(SecurityPolicy.NONE.getPolicyUri());
		serverEndpoint.setEndpointUrl("opc.tcp://"+EndpointUtil.getHostname()+":8999");
		settings.setDescription(serverEndpoint);
		EncoderContext ctx = EncoderContext.getDefaultInstance();
		sut.initialize(url, settings, ctx);
		
		//Should timeout in 2 seconds
		sut.open();
	}
	
}
