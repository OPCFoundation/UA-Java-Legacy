/* Copyright (c) 1996-2015, OPC Foundation. All rights reserved.
   The source code in this file is covered under a dual-license scenario:
     - RCL: for OPC Foundation members in good-standing
     - GPL V2: everybody else
   RCL license terms accompanied with this source code. See http://opcfoundation.org/License/RCL/1.00/
   GNU General Public License as published by the Free Software Foundation;
   version 2 of the License are accompanied with this source code. See http://opcfoundation.org/License/GPLv2
   This source code is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
*/

package org.opcfoundation.ua.transport.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.opcfoundation.ua.application.Application;
import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.transport.CloseableObjectState;
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.EndpointBinding;
import org.opcfoundation.ua.transport.EndpointServer;
import org.opcfoundation.ua.transport.IConnectionListener;
import org.opcfoundation.ua.transport.ServerConnection;
import org.opcfoundation.ua.transport.UriUtil;
import org.opcfoundation.ua.transport.endpoint.EndpointBindingCollection;
import org.opcfoundation.ua.transport.impl.ConnectionCollection;
import org.opcfoundation.ua.transport.tcp.impl.ReverseHello;
import org.opcfoundation.ua.utils.AbstractState;
import org.opcfoundation.ua.utils.StackUtils;
import org.opcfoundation.ua.utils.asyncsocket.AsyncServerSocket;
import org.opcfoundation.ua.utils.asyncsocket.AsyncSocketImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EndpointOpcTcp hosts an endpoint for opc.tcp socket.
 *
 * Endpoint discovery is provided if endpoint url is unknown.
 *
 * @see Executors for creating executor instances
 */
public class OpcTcpServer extends AbstractState<CloseableObjectState, ServiceResultException> implements EndpointServer {

	/** Logger */
	static Logger logger = LoggerFactory.getLogger(OpcTcpServer.class);
	/** Application */
	Application application;
	/** Secure channel counter */
	AtomicInteger secureChannelCounter = new AtomicInteger();
	/** Endpoint bindings */
	EndpointBindingCollection endpointBindings = new EndpointBindingCollection(); 

	/** Service server used when client connects with "" url for endpoint discovery */
	public Server discoveryServer;
	public EndpointBinding discoveryEndpointBinding;
	
	private int receiveBufferSize = 0;

	private boolean initialized = false;
	
	/** Endpoint handles */
	Map<SocketAddress, SocketHandle> socketHandles = new HashMap<SocketAddress, SocketHandle>();
	
	/**
	 * <p>getEncoderContext.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public EncoderContext getEncoderContext() {
		return application.getEncoderContext();
	}

	/**
	 * <p>Getter for the field <code>receiveBufferSize</code>.</p>
	 *
	 * @return the receiveBufferSize to use for the connection socket of the server.
	 */
	public int getReceiveBufferSize() {
		return receiveBufferSize;
	}

	/**
	 * Define the receiveBufferSize to use for the connection socket of the server.
	 * <p>
	 * Default value: 0, which omits the parameter and the default value for the
	 * socket (depending on the operating system) is used.
	 *
	 * @param receiveBufferSize the new size in bytes
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 * @see "http://fasterdata.es.net/host-tuning/background/"
	 */
	public void setReceiveBufferSize(int receiveBufferSize) throws ServiceResultException {
		this.receiveBufferSize = receiveBufferSize;
		if (receiveBufferSize > 0) {
			for (SocketHandle sh : socketHandleSnapshot()) {
				try {
					AsyncServerSocket ass = sh.socket;
					if ( ass!=null ) {
						ass.socket().setReceiveBufferSize(receiveBufferSize);
					}
				} catch (SocketException e) {
					throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
				}
			}
		}
	}
	
	/** AsyncServerSocket Connect listener */
	org.opcfoundation.ua.utils.asyncsocket.AsyncServerSocket.ConnectListener connectListener = new org.opcfoundation.ua.utils.asyncsocket.AsyncServerSocket.ConnectListener() {		
		public void onConnected(AsyncServerSocket sender, AsyncSocketImpl newConnection) {
			logger.info("{}: {} connected", OpcTcpServer.this, newConnection.socket().getRemoteSocketAddress());
			final OpcTcpServerConnection conn = new OpcTcpServerConnection(OpcTcpServer.this, newConnection);
			connections.addConnection(conn);
			
			conn.addConnectionListener(new IConnectionListener() {
				@Override
				public void onClosed(ServiceResultException closeError) {						
						connections.removeConnection(conn);
				}
				@Override
				public void onOpen() {
				}});
			
	          // Check for connection count limits
	          // Per 1.05 Part 4 section 5.5.2, To protect against misbehaving Clients and denial of
	          // service attacks, the Server shall close the oldest unused SecureChannelthat has no
	          // Session assigned before reaching the maximum number of supported SecureChannels.


	          List<ServerConnection> conns = new ArrayList<ServerConnection>();
	          connections.getConnections(conns);

	          logger.trace("Checking maximum number of connections, limit: {}, current: {}", maxConnections, conns.size());

	          // at limit is enough per the 5.5.2
	          if (conns.size() >= maxConnections) {
	            int delta = maxConnections - conns.size() + 1;
	            logger.trace("We are at max or over limit, number of connections to purge if possible: {}", delta);
	            // TODO ordering based on timestamps of creation
	            int purged = 0;
	            for (ServerConnection tmp : conns) {
	              if (tmp instanceof OpcTcpServerConnection) {
	                // Every connection should be of this type in this OpcTcpServer)
	                OpcTcpServerConnection opcTcpTmp = (OpcTcpServerConnection) tmp;

	                // We are itself part of the connections as well. Skip ourselves here (that is
	                // handled at the end of this method). If we remove ourselves here, and would be the
	                // "final legit" connection (and then we would be at max capacity), we would be
	                // closed before we can ActivateSession.
	                if (conn == opcTcpTmp) {
	                  continue;
	                }

	                if (opcTcpTmp.isPurgeEligible()) {
	                  opcTcpTmp.close();
	                  purged++;
	                  if (purged >= delta) {
	                    break;
	                  }
	                }
	              }
	            }
	            logger.trace("We are at max or over limit, purged {} old connections", purged);
	          }
	          // It is possible that we were not able to purge anything, if we happen to be at max
	          // connections that all have activated sessions, in this case, close this connection
	          // instead.
	          List<ServerConnection> tmp = new ArrayList<ServerConnection>();
	          connections.getConnections(tmp);
	          /*
	           * But! This connection itself is within the connections, if we are exactly at max, that
	           * is still OK here. But if we are over (i.e. we already had at max and none cannot be
	           * removed), then remove this connection.
	           */
	          if (tmp.size() > maxConnections) {
	            logger.trace("We are at over limit, unable to purge enough old connections, closing this connection");
	            conn.close();
	          } else if (tmp.size() == maxConnections) {
	            logger.trace("We are exactly at maximum connections (including this connection). "
	                + "No older connections could be purged. Keeping this connection open.");
	          }
	    }};
	ConnectionCollection connections = new ConnectionCollection(this);	
	
	int maxConnections;
	int maxSecureChannelsPerConnection;	
	
	/**
	 * <p>Constructor for OpcTcpServer.</p>
	 *
	 * @param application a {@link org.opcfoundation.ua.application.Application} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public OpcTcpServer(Application application) throws ServiceResultException
	{
		super(CloseableObjectState.Closed, CloseableObjectState.Closed);
		this.application = application;
		try {
			ServerSocketChannel channel = ServerSocketChannel.open();
			channel.configureBlocking(false);
			
			// Create a service server for connections that query endpoints (url = "")
			discoveryServer = new Server( application );
			discoveryServer.setEndpointBindings( endpointBindings );
			discoveryEndpointBinding = new EndpointBinding(this, discoveryEndpoint, discoveryServer);	
		} catch (IOException e) {
			throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public EndpointHandle bind(SocketAddress socketAddress, EndpointBinding endpointBinding) throws ServiceResultException {
		if ( endpointBinding == null || socketAddress == null || endpointBinding.endpointServer!=this )
			throw new IllegalArgumentException();
		
		init();
		
		String scheme = UriUtil.getTransportProtocol( endpointBinding.endpointAddress.getEndpointUrl() );
		if ( !"opc.tcp".equals(scheme) ) throw new ServiceResultException(StatusCodes.Bad_UnexpectedError, "Cannot bind "+scheme+" to opc.tcp server");
		SocketHandle socketHandle = getOrCreateSocketHandle(socketAddress);
		
		if ( socketHandle.socket == null ) {
			try {
				socketHandle.setChannel(ServerSocketChannel.open());
				socketHandle.getChannel().configureBlocking(false);
			
				socketHandle.socket = new AsyncServerSocket(
						socketHandle.getChannel(),
						StackUtils.getNonBlockingWorkExecutor()//,
						//new AsyncSelector(Selector.open())
						,StackUtils.getSelector()
						);
				socketHandle.socket.bind(socketHandle.socketAddress, 0);
				socketHandle.socket.addListener(connectListener);
				logger.info("TCP/IP Socket bound to {}", socketAddress);
			} catch (IOException e) {
				logger.error("Failed to bind address "+ socketHandle.socketAddress, e);
				socketHandle.close();
				throw new ServiceResultException(StatusCodes.Bad_InternalError, e);
			}
			
		}
		
		OpcTcpEndpointHandle endpointHandle = socketHandle.getOrCreate(endpointBinding);
		return endpointHandle;
	}
	

	@Override
	public void bindReverse(final SocketAddress addressToConnect,
			final String endpointUrl) {
		if(addressToConnect == null || endpointUrl == null) {
			throw new IllegalArgumentException();
		}
		init();
		
		ReverseSocketHandle socketHandle = new ReverseSocketHandle(addressToConnect);
		if(socketHandle.socket == null) {
			try {
				socketHandle.setChannel(SocketChannel.open());
				socketHandle.getChannel().configureBlocking(false);
				
				socketHandle.socket = new AsyncSocketImpl(socketHandle.getChannel(), StackUtils.getNonBlockingWorkExecutor(), StackUtils.getSelector());

				ReverseHello rh = new ReverseHello();
				rh.setEndpointUrl(endpointUrl);
				rh.setServerUri(application.getApplicationDescription().getApplicationUri());
				final OpcTcpServerConnection conn = new OpcTcpServerConnection(OpcTcpServer.this, socketHandle.socket, rh); 
				connections.addConnection(conn);
				
				conn.addConnectionListener(new IConnectionListener() {
					@Override
					public void onClosed(ServiceResultException closeError) {						
						connections.removeConnection(conn);
							
						//1.04 Part 6 section 7.1.3 when closed, server must restart the process
						logger.debug("ReverseHello connection closed, rescheduling connection process");
						bindReverse(addressToConnect, endpointUrl);
					}
					@Override
					public void onOpen() {
					}});
				//async, do last, others listen on socket state.
				socketHandle.socket.connect(socketHandle.socketAddress);
			}catch(IOException e) {
				logger.error("Failed to create a ReverseSocketHandle", e);
				socketHandle.close();
			}
			
		}

	}
	

	/** {@inheritDoc} */
	@Override
	public List<SocketAddress> getBoundSocketAddresses() {
		ArrayList<SocketAddress> result = new ArrayList<SocketAddress>();
		for (SocketHandle sh : socketHandleSnapshot()) result.add( sh.socketAddress );
		return result;
	}

	/**
	 * <p>getBoundAddress.</p>
	 *
	 * @return a {@link java.net.SocketAddress} object.
	 */
	public SocketAddress getBoundAddress() {
		SocketHandle[] shs = socketHandleSnapshot();
		for (SocketHandle sh: shs) {
			if ( sh.socket!=null) return sh.socketAddress;
		}
		return null;
	}
	
	/**
	 * Disconnect all existing connections.
	 */
	public void disconnectAll()
	{
		List<ServerConnection> list = new ArrayList<ServerConnection>();
		getConnections(list);
		for (ServerConnection connection : list) {
			OpcTcpServerConnection c = (OpcTcpServerConnection) connection;
			c.close();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Closes server socket. Does not disconnect existing connections.
	 */
	@Override
	public synchronized OpcTcpServer close() {
		logger.info("{} closed", getBoundAddress());
		if (!getState().isClosed()) { 
			setState(CloseableObjectState.Closing);
		}
		
		try {
			for (SocketHandle sh : socketHandleSnapshot())
				sh.close();
		} finally {
			setState(CloseableObjectState.Closed);
		}
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public void addConnectionListener(org.opcfoundation.ua.transport.ConnectionMonitor.ConnectListener l) {
		connections.addConnectionListener(l);
	}

	/** {@inheritDoc} */
	@Override
	public void getConnections(Collection<ServerConnection> result) {
		connections.getConnections(result);
	}

	/** {@inheritDoc} */
	@Override
	public void removeConnectionListener(org.opcfoundation.ua.transport.ConnectionMonitor.ConnectListener l) {
		connections.removeConnectionListener(l);
	}	
	
	/** {@inheritDoc} */
	@Override
	public EndpointBindingCollection getEndpointBindings() {
		return endpointBindings;
	}	
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("OpcTcpServer");
		sb.append("(");
		for (SocketHandle sh : socketHandleSnapshot()) sb.append( sh.toString() );
		sb.append(")");
		return sb.toString();
	}

	synchronized SocketHandle getOrCreateSocketHandle(SocketAddress socketAddress)
	throws ServiceResultException
	{
		SocketHandle handle = socketHandles.get(socketAddress);
		if ( handle == null ) {
			handle = new SocketHandle(socketAddress);
			socketHandles.put(socketAddress, handle);
		}
		return handle;
	}
	
	/**
	 * <p>socketHandleSnapshot.</p>
	 *
	 * @return an array of {@link org.opcfoundation.ua.transport.tcp.nio.OpcTcpServer.SocketHandle} objects.
	 */
	public SocketHandle[] socketHandleSnapshot() {
		return socketHandles.values().toArray( new SocketHandle[ socketHandles.size()] );		
	}
	
	public static class ReverseSocketHandle{
		
		private SocketAddress socketAddress;
		private SocketChannel channel;
		private AsyncSocketImpl socket;
		
		public ReverseSocketHandle(SocketAddress socketAddress) {
			this.socketAddress = socketAddress;
		}

		public SocketChannel getChannel() {
			return channel;
		}

		public void setChannel(SocketChannel channel) {
			this.channel = channel;
		}

		public SocketAddress getSocketAddress() {
			return socketAddress;
		}
		
		public AsyncSocketImpl getSocket() {
			return socket;
		}

		public void setSocket(AsyncSocketImpl socket) {
			this.socket = socket;
		}

		void close() {
			SocketChannel c = channel;
			if(c != null) {
				try {
					c.close();
				} catch (IOException e) {
					logger.error("Failure in closing ReverseSockeHandle", e);
				}
			}
		}
	}

	public class SocketHandle {
		SocketAddress socketAddress;
		/** Server Socket */
		AsyncServerSocket socket;
		private ServerSocketChannel channel;
		int port;
		Map<Endpoint, OpcTcpEndpointHandle> endpoints = new HashMap<Endpoint, OpcTcpEndpointHandle>();
		SocketHandle(SocketAddress socketAddress)
		{
			this.socketAddress = socketAddress;
		}
		
		public synchronized OpcTcpEndpointHandle[] endpointHandleSnapshot() {
			return endpoints.values().toArray( new OpcTcpEndpointHandle[ endpoints.size()] );
		}
		
		synchronized void endpointHandleSnapshot(Collection<OpcTcpEndpointHandle> handles) {
			handles.addAll( endpoints.values() );
		}
		
		synchronized OpcTcpEndpointHandle getOrCreate(EndpointBinding endpointBinding) throws ServiceResultException {
			OpcTcpEndpointHandle handle = endpoints.get(endpointBinding.endpointAddress);
			if ( handle == null ) {
				handle = new OpcTcpEndpointHandle(this, endpointBinding);
				// Add endpointBinding to SocketHandle
				endpoints.put(endpointBinding.endpointAddress, handle);
				// Add endpointBinding to EndpointServer
				endpointBindings.add(endpointBinding);
				// Add endpointBinding to Server
				endpointBinding.serviceServer.getEndpointBindings().add(endpointBinding);
			} else {
				if ( !handle.endpointBinding.equals( endpointBinding ) ) {
					throw new ServiceResultException( StatusCodes.Bad_UnexpectedError, "Cannot bind an endpoint address to two different servers." );
				}
			}
			return handle;
		}
		int getPort() {
			return ((InetSocketAddress)socketAddress).getPort();
		}

		void close() {
			for ( OpcTcpEndpointHandle eph : endpoints.values() ) eph.close__();
			socketHandles.remove(socketAddress);
			if ( socket!=null ) {
				AsyncServerSocket ass = socket;
				socket = null;
				ass.close();				
			}
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append( "opc.tcp("+socketAddress+", ");
			for (OpcTcpEndpointHandle ep : endpoints.values()) {
				sb.append( ep.toString() );
			}
			sb.append( ")");
			return sb.toString();
		}

		/**
		 * @return the channel
		 */
		public ServerSocketChannel getChannel() {
			return channel;
		}

		/**
		 * @param channel the channel to set
		 */
		public void setChannel(ServerSocketChannel channel) {
			this.channel = channel;
		}

		public SocketAddress getSocketAddress() {
			return socketAddress;
		}
	}
	
	public class OpcTcpEndpointHandle implements EndpointHandle {
		EndpointBinding endpointBinding;
		SocketHandle socketHandle;
		OpcTcpEndpointHandle(SocketHandle socketHandle, EndpointBinding endpointBinding) {
			this.socketHandle = socketHandle;
			this.endpointBinding = endpointBinding;
		}
		@Override
		public SocketAddress socketAddress() {
			return socketHandle.socketAddress;
		}
		@Override
		public EndpointBinding endpointBinding() {
			return endpointBinding;
		}
		@Override
		public void close() {
			close_();
			close__();
		}
		void close_() {
			// Remove endpointBinding from SocketHandle
			socketHandle.endpoints.remove(endpointBinding.endpointAddress);
			// Close socket if this was the last
			if ( socketHandle.endpoints.isEmpty() ) {
				socketHandle.close();
			}
		}
		void close__() {
			// Close endpointHandler if this was the last
			int count = countEndpoints(endpointBinding.endpointAddress);
			if ( count == 0 ) {
				// Remove endpointBinding from EndpointServer
				endpointBindings.remove(endpointBinding);
				// Remove endpointBinding from Server
				endpointBinding.serviceServer.getEndpointBindings().remove(endpointBinding);
			}
		}
		@Override
		public String toString() {
			return "("+endpointBinding.endpointAddress.toString()+")";
		}
	}

	private void init() {
		if (!initialized) {
			int maxConnections = application.getOpctcpSettings().getMaxConnections();
			if (maxConnections <= 0) {
				throw new IllegalStateException("Maximum number of connections was not configured; must be greater than 0");
			}
			this.maxConnections = maxConnections;

			int maxSecureChannelsPerConnection = application.getOpctcpSettings().getMaxSecureChannelsPerConnection();
			if (maxSecureChannelsPerConnection <= 0) {
				throw new IllegalStateException(
		            "Maximum number of secure channels per connection was not configured; must be greater than 0");
			}
			this.maxSecureChannelsPerConnection = maxSecureChannelsPerConnection;
			initialized = true;
		}
	}
	  
	List<OpcTcpEndpointHandle> findEndpoints(String forUri) {
		List<OpcTcpEndpointHandle> result = new ArrayList<OpcTcpEndpointHandle>();
		for (SocketHandle sh : socketHandleSnapshot() ) sh.endpointHandleSnapshot(result);
		return result;
	}
	int countEndpoints(Endpoint endpointAddress) {
		int count = 0;
		for (SocketHandle sh : socketHandleSnapshot() ) {
			for (OpcTcpEndpointHandle eh : sh.endpointHandleSnapshot()) {
				if (eh.endpointBinding.endpointAddress.equals(endpointAddress)) count++;
			}
		}
		return count;
	}

}
