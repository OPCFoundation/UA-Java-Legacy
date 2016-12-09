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

import static org.opcfoundation.ua.core.StatusCodes.Bad_SecureChannelIdInvalid;
import static org.opcfoundation.ua.core.StatusCodes.Bad_SecurityChecksFailed;
import static org.opcfoundation.ua.core.StatusCodes.Bad_SecurityPolicyRejected;
import static org.opcfoundation.ua.core.StatusCodes.Bad_TcpInternalError;
import static org.opcfoundation.ua.core.StatusCodes.Bad_Timeout;
import static org.opcfoundation.ua.core.StatusCodes.Bad_UnexpectedError;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.CloseSecureChannelRequest;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.OpenSecureChannelRequest;
import org.opcfoundation.ua.core.SecurityTokenRequestType;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.EncoderMode;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.binary.BinaryEncoder;
import org.opcfoundation.ua.encoding.binary.EncoderCalc;
import org.opcfoundation.ua.transport.AsyncWrite;
import org.opcfoundation.ua.transport.CloseableObject;
import org.opcfoundation.ua.transport.CloseableObjectState;
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.EndpointBinding;
import org.opcfoundation.ua.transport.IConnectionListener;
import org.opcfoundation.ua.transport.ServerSecureChannel;
import org.opcfoundation.ua.transport.endpoint.AbstractServerSecureChannel;
import org.opcfoundation.ua.transport.endpoint.EndpointBindingCollection;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.transport.security.SecurityConfiguration;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.transport.tcp.impl.Acknowledge;
import org.opcfoundation.ua.transport.tcp.impl.ChunkAsymmEncryptSigner;
import org.opcfoundation.ua.transport.tcp.impl.ChunkFactory;
import org.opcfoundation.ua.transport.tcp.impl.ChunkFactory.AcknowledgeChunkFactory;
import org.opcfoundation.ua.transport.tcp.impl.ChunkFactory.ErrorMessageChunkFactory;
import org.opcfoundation.ua.transport.tcp.impl.ChunkSymmEncryptSigner;
import org.opcfoundation.ua.transport.tcp.impl.ChunkUtils;
import org.opcfoundation.ua.transport.tcp.impl.ErrorMessage;
import org.opcfoundation.ua.transport.tcp.impl.Hello;
import org.opcfoundation.ua.transport.tcp.impl.SecurityToken;
import org.opcfoundation.ua.transport.tcp.impl.TcpMessageType;
import org.opcfoundation.ua.transport.tcp.nio.Channel.ChannelListener;
import org.opcfoundation.ua.transport.tcp.nio.SecureInputMessageBuilder.MessageListener;
import org.opcfoundation.ua.utils.CertificateUtils;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.IStatefulObject;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.utils.StackUtils;
import org.opcfoundation.ua.utils.StateListener;
import org.opcfoundation.ua.utils.TimerUtil;
import org.opcfoundation.ua.utils.asyncsocket.AsyncInputStream;
import org.opcfoundation.ua.utils.asyncsocket.AsyncSocket;
import org.opcfoundation.ua.utils.asyncsocket.AsyncSocketImpl;
import org.opcfoundation.ua.utils.asyncsocket.BufferMonitorState;
import org.opcfoundation.ua.utils.asyncsocket.MonitorListener;
import org.opcfoundation.ua.utils.asyncsocket.SocketState;
import org.opcfoundation.ua.utils.bytebuffer.ByteBufferArrayWriteable2;
import org.opcfoundation.ua.utils.bytebuffer.ByteBufferArrayWriteable2.ChunkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>OpcTcpServerConnection class.</p>
 *
 * @author jaro
 */
public class OpcTcpServerConnection extends AbstractServerConnection {

	/** Logger */
	private final static Logger logger = LoggerFactory.getLogger(OpcTcpServerConnection.class);

	// Give client 10 minutes to handshake
	private static long handshakeTimeout = 10 * 60 * 1000; // 10 minutes
	/**
	 * <p>Getter for the field <code>handshakeTimeout</code>.</p>
	 *
	 * @return the handshakeTimeout
	 */
	public static long getHandshakeTimeout() {
		return handshakeTimeout;
	}
	/**
	 * Define the handshake timeout for new connections. If the client does not
	 * establish a connection during the timeout, the socket is released.
	 * A smaller value might be usable to avoid DNS attacks.
	 *
	 * @param handshakeTimeout
	 *            the handshakeTimeout to set. Default 600000 = 10 minutes.
	 */
	public static void setHandshakeTimeout(long handshakeTimeout) {
		OpcTcpServerConnection.handshakeTimeout = handshakeTimeout;
	}
	/** Agreed protocol version */
	int agreedProtocolVersion;
	/**  Request id - Pending Request mapping */
	Map<Integer, PendingRequest> pendingRequests  = new ConcurrentHashMap<Integer, PendingRequest>();

	/** An endpoint binding associated with this connection. Set after hello */
	EndpointBinding binding;

	/** Endpoint Server */
	OpcTcpServer endpointServer;

	/** Event based asynchronous socket */
	AsyncSocket s;


	/** Timer used for handshake timing */
	Timer timer = TimerUtil.getTimer();

	TimerTask timeoutTimer;
	Runnable timeout = new Runnable() {
		@Override
		public void run() {
			setError(Bad_Timeout);
		}};

		EncoderContext encoderCtx;
		EndpointConfiguration endpointConfiguration;

		/// ??? ///
		// Handles incoming data //
		MonitorListener inputListener =
				new MonitorListener() {
			@Override
			public void onStateTransition(IStatefulObject<BufferMonitorState, ?> sender,
					BufferMonitorState oldState, BufferMonitorState newState) {

				// Trigger is unreachable
				if (newState.isUnreachable()) {
					if (secureMessageBuilder!=null) {
						secureMessageBuilder.close();
						secureMessageBuilder = null;
					}
					return;
				}

				if (newState != BufferMonitorState.Triggered)
				{
					logger.error("Unexpected trigger state {}", newState);
					return;
				}

				// -- assert -- atleast 8 bytes are available --

				AsyncInputStream is = s.getInputStream();
				ByteBuffer hdr = is.peek(8);
				hdr.order(ByteOrder.LITTLE_ENDIAN);
				hdr.getInt();
				int chunkSize = hdr.getInt();

				if (chunkSize<12) {
					setError(StatusCodes.Bad_TcpInternalError);
					if (secureMessageBuilder!=null)
						secureMessageBuilder.close();
					return;
				}

				if (chunkSize > ctx.maxRecvChunkSize)
				{
					if (!hasError())
						try {
							sendError(new ErrorMessage(StatusCodes.Bad_CommunicationError, "Chunk size ("+chunkSize+") exceeded maximum ("+ctx.maxRecvChunkSize+")"));
						} catch (Exception e) {
						}
					setError(StatusCodes.Bad_TcpMessageTooLarge);
					if (secureMessageBuilder!=null)
						secureMessageBuilder.close();
					return;
				}

				if (is.available()>=chunkSize) {
					// Chunk is readable
					ByteBuffer chunk = is.read(chunkSize);
					chunk.rewind();
					try {
						try {
							handleChunk(chunk);
						} catch (RuntimeException e) {
							logger.warn("Error in handleChunk", e);
							throw StackUtils.toServiceResultException(e);
						}
					} catch (ServiceResultException se) {
						logger.info("Error in handleChunk", se);
						try {
							sendError(new ErrorMessage(se.getStatusCode(), se.getMessage()));
						} catch (ServiceResultException e1) {
							logger.warn("Could not send ErrorMessage:",e1);
						}
						setError(se);
					}
					// Wait for next chunk header
					is.createMonitor(is.getPosition()+8, this);

				} else {
					// Wake me up when the chunk is fully readable
					is.createMonitor(is.getPosition()+chunkSize, this);
				}
			}
		};
		/// ??? ///

		// Handle incoming messages, ran in BlockingWorkExecutor //
		MessageListener messageListener = new MessageListener() {
			@Override
			public void onMessageComplete(InputMessage sender) {
				IEncodeable msg = sender.getMessage();

				for (ChannelListener cl : channelListeners)
					if (cl.handleMessage(sender)) return;

				// Handler error
				if (msg==null)
					synchronized (this) {
						Exception error = sender.getError();
						if (error == null)
							return; // aborted;
						setError(StackUtils.toServiceResultException(error));
						return;
					}

				// Handle message
				try {
					if (sender.getMessageType() == TcpMessageType.MESSAGE) {
						handleSecureMessage(sender);
					} else if (sender.getMessageType() == TcpMessageType.CLOSE) {
						handleCloseSecureChannelRequest(sender);
					} else if (sender.getMessageType() == TcpMessageType.OPEN) {
						handleOpenSecureChannelRequest(sender);
					}
				} catch (ServiceResultException e) {
					// Handle message failed. Disconnect.
					//				logger.logger(Level.INFO, e.getMessage());
					try {
						sendError(new ErrorMessage(e.getStatusCode(), e.getMessage()));
					} catch (ServiceResultException e1) {
					}
					setError(e);
				}
			}};

			OpcTcpServerConnection(OpcTcpServer endpointServer, AsyncSocketImpl s) {
				super();
				this.endpointServer = endpointServer;
				this.s = s;

				this.encoderCtx = endpointServer.getEncoderContext();

				// Monitor the state of the socket, make changes reflect to the state of the UATcpConnection
				socketListener =
						new StateListener<SocketState>() {
					@Override
					public void onStateTransition(IStatefulObject<SocketState, ?> monitor, SocketState oldState, SocketState newState) {
						if (newState==SocketState.Error)
						{
							setError( StackUtils.toServiceResultException( OpcTcpServerConnection.this.s.getStateMonitor().getError() ) );
						}
						if (newState==SocketState.Closed)
						{
							close();
						}
					}
				};

				setState(CloseableObjectState.Opening);

				s.getStateMonitor().addStateListener(socketListener);
				s.getInputStream().createMonitor(8, inputListener);

				timeoutTimer = TimerUtil.schedule(
						timer, timeout,
						StackUtils.getBlockingWorkExecutor(),
						System.currentTimeMillis() + handshakeTimeout);
			}

			/** {@inheritDoc} */
			@Override
			public void addConnectionListener(IConnectionListener listener) {
				logger.debug("addConnectionListener: listener={}", listener);
				super.addConnectionListener(listener);

			}

			/** {@inheritDoc} */
			@Override
			public synchronized CloseableObject close() {
				try {
					setState(CloseableObjectState.Closing);
				} finally {
					try {
						s.close();
					} catch (IOException e) {
					}
					setState(CloseableObjectState.Closed);
				}
				return this;
			}

			/** {@inheritDoc} */
			@Override
			public SocketAddress getLocalAddress() {
				Socket socket = s.socket();
				if (socket==null) return null;
				return socket.getLocalSocketAddress();
			}

			/** {@inheritDoc} */
			@Override
			public SocketAddress getRemoteAddress() {
				Socket socket = s.socket();
				if (socket==null) return null;
				return socket.getRemoteSocketAddress();
			}

			/** {@inheritDoc} */
			@Override
			public void removeConnectionListener(IConnectionListener listener) {
				logger.debug("removeConnectionListener: listener={}", listener);
				super.removeConnectionListener(listener);
				cancelTimeoutTimer();
			}

			/**
			 * @param uri
			 * @return
			 */
			private String trimUrl(String uri) {
				if ( uri==null ) return null;
				// Also remove an optional '/' from the end, since it is not significant
				if (uri.endsWith("/"))
					uri = uri.substring(0, uri.length()-1);
				return uri;
			}

			/**
			 * <p>cancelTimeoutTimer.</p>
			 */
			protected void cancelTimeoutTimer() {
				// Cancel hand-shake time-out
				if (timeoutTimer!=null) {
					timeoutTimer.cancel();
					timeoutTimer = null;
					timeout = null;
				}
			}

			/**
			 * Flushes queued chunks (see startChunkSend())
			 *
			 * @param chunk chunk to send
			 */
			protected void endChunkSend(ByteBuffer chunk)
			{
				chunkIncubator.hatch(chunk);
				synchronized(this) {
					while (chunkIncubator.nextIsHatched()) {
						ByteBuffer c = chunkIncubator.removeNextHatchedIfAvailable();
						c.rewind();
						s.getOutputStream().offer(c);
					}
				}
			}

			/**
			 * <p>flush.</p>
			 *
			 * @param position a long.
			 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.BufferMonitorState} object.
			 * @throws java.lang.InterruptedException if any.
			 * @throws java.io.IOException if any.
			 */
			protected BufferMonitorState flush(long position)
					throws InterruptedException, IOException
			{
				return s.getOutputStream().createMonitor(position, null).waitForState(BufferMonitorState.FINAL_STATES);
			}

			/** {@inheritDoc} */
			@Override
			protected CertificateValidator getRemoteCertificateValidator() {
				return binding==null ? null: binding.serviceServer.getApplication().getOpctcpSettings().getCertificateValidator();
			}

			/**
			 * <p>handleAcknowledgeMessage.</p>
			 *
			 * @param a a {@link org.opcfoundation.ua.transport.tcp.impl.Acknowledge} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void handleAcknowledgeMessage(Acknowledge a) throws ServiceResultException {
				throw new ServiceResultException(Bad_UnexpectedError);
			}

			/**
			 * <p>handleAsymmChunk.</p>
			 *
			 * @param chunk a {@link java.nio.ByteBuffer} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void handleAsymmChunk(ByteBuffer chunk) throws ServiceResultException {
				chunk.rewind();
				if (secureMessageBuilder!=null && !secureMessageBuilder.moreChunksRequired()) secureMessageBuilder = null;

				// First chunk of the message
				if (secureMessageBuilder==null) {
					// Read remote certificate and thumbprint of the expected local certificate
					//			chunk.position(8);
					//			int secureChannelId = chunk.getInt();
					int secureChannelId = ChunkUtils.getSecureChannelId(chunk);
					OpcTcpServerSecureChannel secureChannel =(OpcTcpServerSecureChannel) secureChannels.get(secureChannelId);
					String securityPolicyUri = ChunkUtils.getString(chunk);
					SecurityPolicy securityPolicy = SecurityPolicy.getSecurityPolicy(securityPolicyUri);
					if (securityPolicy==null)
					{
						logger.warn("Security policy \"{}\" is not supported by the stack", securityPolicyUri);
						throw new ServiceResultException(Bad_SecurityPolicyRejected, "Security policy \""+securityPolicyUri+"\" is not supported by the stack");
					}

					//			if (!binding.endpointAddress.supportsSecurityPolicy(securityPolicy))
					//			{
					//				logger.warn("Security policy \""+securityPolicyUri+"\" is not supported by the endpoint");
					//				throw new ServiceResultException(Bad_SecurityPolicyRejected, "Security policy \""+securityPolicyUri+"\" is not supported by the endpoint");
					//			}

					byte[] encodedRemoteCertificate = ChunkUtils.getByteString(chunk);
					byte[] encodedLocalCertificateThumbprint = ChunkUtils.getByteString(chunk);
					logger.debug("secureChannelId={}", secureChannelId);
					logger.debug("secureChannel={}", secureChannel);
					logger.debug("securityPolicyUri={}", securityPolicyUri);
					logger.debug("securityPolicy={}", securityPolicy);
					logger.debug("encodedRemoteCertificate={}", encodedRemoteCertificate);
					logger.debug("encodedLocalCertificateThumbprint={}", encodedLocalCertificateThumbprint);


					KeyPair localCertificate = binding.serviceServer.getApplication().getApplicationInstanceCertificate(encodedLocalCertificateThumbprint);

					if (localCertificate==null && securityPolicy != SecurityPolicy.NONE) {
						logger.warn("Requested Application Instance Certificate is not found in the server");
						throw new ServiceResultException(Bad_SecurityChecksFailed, "Requested Application Instance Certificate is not found in the server");
					}

					// Decode remote certificate
					org.opcfoundation.ua.transport.security.Cert remoteCertificate;
					try {
						remoteCertificate =
								encodedRemoteCertificate==null ?
										null :
											new org.opcfoundation.ua.transport.security.Cert( CertificateUtils.decodeX509Certificate(encodedRemoteCertificate) );
					} catch (CertificateException e) {
						throw new ServiceResultException(Bad_SecurityChecksFailed); //see 1.02.2 errdata
					}

					// Validate Client's Certificate
					CertificateValidator validator = getRemoteCertificateValidator();
					if (validator != null) {
						StatusCode code = validator.validateCertificate(remoteCertificate);
						if (code != null && !code.isGood()) {
							logger.warn("Remote certificate not accepted: {}", code.toString());
							throw new ServiceResultException(code);
						}
					}

					// MessageMode is unknown at this time. It is fixed in UATcpServerSecureChannel.onOpenChannel
					MessageSecurityMode msm = securityPolicy == SecurityPolicy.NONE ? MessageSecurityMode.None : MessageSecurityMode.SignAndEncrypt;
					SecurityMode mode = new SecurityMode(securityPolicy, msm);
					securityConfiguration = new SecurityConfiguration(mode, localCertificate, remoteCertificate);

					AtomicInteger recvSequenceNumber = secureChannel==null ? null : secureChannel.recvSequenceNumber;

					secureMessageBuilder = new SecureInputMessageBuilder(securityConfiguration, messageListener, ctx, encoderCtx, recvSequenceNumber);
				}
				logger.debug("onAsymmSecureChunk: {}", chunk);
				secureMessageBuilder.addChunk(chunk);
			}

			/**
			 * <p>handleChunk.</p>
			 *
			 * @param chunk a {@link java.nio.ByteBuffer} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void handleChunk(ByteBuffer chunk) throws ServiceResultException {
				int type = ChunkUtils.getMessageType(chunk);
				int messageType = type & TcpMessageType.MESSAGE_TYPE_MASK;
				// Secure message
				if (messageType == TcpMessageType.MESSAGE) {
					handleSymmChunk(chunk);
				} else if (messageType == TcpMessageType.CLOSE) {
					handleCloseChunk(chunk);
				} else if (messageType == TcpMessageType.OPEN) {
					handleAsymmChunk(chunk);
				} else if (messageType == TcpMessageType.HELLO || messageType == TcpMessageType.ACKNOWLEDGE || messageType == TcpMessageType.ERROR) {
					handleRawChunk(chunk);
				} else {
					// Unknown chunk
					close();
				}
			}

			/**
			 * <p>handleCloseChunk.</p>
			 *
			 * @param chunk a {@link java.nio.ByteBuffer} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void handleCloseChunk(ByteBuffer chunk) throws ServiceResultException {
				close();
			}

			/**
			 * <p>handleCloseSecureChannelRequest.</p>
			 *
			 * @param mb a {@link org.opcfoundation.ua.transport.tcp.nio.InputMessage} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void handleCloseSecureChannelRequest(InputMessage mb) throws ServiceResultException {
				logger.debug("onCloseChannel");
				IEncodeable msg = mb.getMessage();
				if (!(msg instanceof CloseSecureChannelRequest))
					throw new ServiceResultException(Bad_UnexpectedError);

				CloseSecureChannelRequest req = (CloseSecureChannelRequest) msg;
				int secureChannelId = mb.getSecureChannelId();
				OpcTcpServerSecureChannel chan = (OpcTcpServerSecureChannel) secureChannels.get(secureChannelId);
				if (chan==null) throw new ServiceResultException( Bad_SecureChannelIdInvalid );
				chan.handleCloseSecureChannelRequest(mb, req);
			}

			/**
			 * <p>handleErrorMessage.</p>
			 *
			 * @param e a {@link org.opcfoundation.ua.transport.tcp.impl.ErrorMessage} object.
			 */
			protected void handleErrorMessage(ErrorMessage e) {
				logger.debug("onError: {}", e);
				setError(e.getError());
			}

			/**
			 * <p>handleHelloMessage.</p>
			 *
			 * @param h a {@link org.opcfoundation.ua.transport.tcp.impl.Hello} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void handleHelloMessage(Hello h) throws ServiceResultException {
				cancelTimeoutTimer();

				EndpointBindingCollection c = endpointServer.getEndpointBindings();
				if (c==null) throw new ServiceResultException(Bad_UnexpectedError);
				String url = trimUrl(h.getEndpointUrl());

				logger.debug("onHello: url={}", url);

				if ( url==null || url.equals("") ) {
					binding = endpointServer.discoveryEndpointBinding;
				} else {
					List<EndpointBinding> bindingsForUrl = c.get( url );
					if (bindingsForUrl.isEmpty()) {
						binding = c.getDefault(url);
						if(binding == null){
							throw new ServiceResultException(StatusCodes.Bad_TcpEndpointUrlInvalid);
						}
					} else {
						binding = bindingsForUrl.get(0);
					}
				}
				// Makes sense to check isDebugEnabled because Arrays.toString method probably takes longer than this check.
				if(logger.isDebugEnabled()){
					logger.debug(" endpoints={}", Arrays.toString(c.getEndpointAddresses().toArray(new Endpoint[0])));
					logger.debug(" endpoint=" + (binding==null?"binding is null":binding.endpointAddress));
				}

				if (getState()!=CloseableObjectState.Opening) throw new ServiceResultException(Bad_UnexpectedError);

				Acknowledge a = new Acknowledge();

				// Assert sane values from client
				if (h.getSendBufferSize().longValue()<8192)
					setError(new ServiceResultException(Bad_TcpInternalError, "Peer send buffer size <  8192"));
				if (h.getReceiveBufferSize().longValue()<8192)
					setError(new ServiceResultException(Bad_TcpInternalError, "Peer recv buffer size <  8192"));
				if (getError() != null)
					logger.warn("onHello: " + getError());

				// Determine communication protocol version
				agreedProtocolVersion = Math.min(StackUtils.TCP_PROTOCOL_VERSION, h.getProtocolVersion().intValue());
				a.setProtocolVersion( UnsignedInteger.getFromBits(agreedProtocolVersion) );

				// Message size
				if (h.getMaxMessageSize()!=null && h.getMaxMessageSize().intValue()!=0) {
					if (ctx.maxSendMessageSize==0)
						ctx.maxSendMessageSize = h.getMaxMessageSize().intValue();
					else
						ctx.maxSendMessageSize = Math.min(ctx.maxSendMessageSize, h.getMaxMessageSize().intValue());
				}

				if ( binding != null ) {
					int maxMsgSize = binding.endpointAddress.getEndpointConfiguration().getMaxMessageSize();
					ctx.maxSendMessageSize = ctx.maxSendMessageSize == 0 ? maxMsgSize : Math.min(ctx.maxSendMessageSize, maxMsgSize);
					ctx.maxRecvMessageSize = ctx.maxRecvMessageSize == 0 ? maxMsgSize : Math.min(ctx.maxRecvMessageSize, maxMsgSize);
					encoderCtx.maxMessageSize = maxMsgSize;
				} else {
					encoderCtx.maxMessageSize = ctx.maxSendMessageSize;
				}

				// Chunk count
				if (h.getMaxChunkCount().intValue()!=0)
					ctx.maxSendChunkCount = Math.min(ctx.maxSendChunkCount, h.getMaxChunkCount().intValue());
				a.setMaxChunkCount( UnsignedInteger.getFromBits( ctx.maxRecvChunkCount ) );

				// Chunk sizes
				ctx.maxSendChunkSize = Math.min(h.getReceiveBufferSize().intValue(), ctx.maxSendChunkSize);
				ctx.maxRecvChunkSize = Math.min(h.getSendBufferSize().intValue(), ctx.maxRecvChunkSize);
				a.setSendBufferSize(UnsignedInteger.getFromBits(ctx.maxSendChunkSize));
				a.setReceiveBufferSize(UnsignedInteger.getFromBits(ctx.maxRecvChunkSize));

				// Send buffer (chunk) size
				ctx.maxRecvChunkSize = Math.min(ctx.maxRecvChunkSize, h.getReceiveBufferSize().intValue());
				ctx.maxSendChunkSize = Math.min(ctx.maxSendChunkSize, h.getSendBufferSize().intValue());
				setState(CloseableObjectState.Opening);

				ctx.endpointUrl = h.getEndpointUrl();

				sendAcknowledge(a);
				setState(CloseableObjectState.Open);
			}

			/**
			 * <p>handleOpenSecureChannelRequest.</p>
			 *
			 * @param mb a {@link org.opcfoundation.ua.transport.tcp.nio.InputMessage} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void handleOpenSecureChannelRequest(InputMessage mb) throws ServiceResultException {
				IEncodeable msg = mb.getMessage();
				if (msg == null)
					synchronized (this) {
						Exception e = mb.getError();
						logger.warn("InputMessage has error", e);
						throw new ServiceResultException(Bad_UnexpectedError, e);
					}
				if (!(msg instanceof OpenSecureChannelRequest))
					throw new ServiceResultException(Bad_UnexpectedError);

				OpenSecureChannelRequest req		= (OpenSecureChannelRequest) msg;

				if (req.getRequestType() == SecurityTokenRequestType.Issue)
				{
					OpcTcpServerSecureChannel channel = new OpcTcpServerSecureChannel( this, endpointServer.secureChannelCounter.incrementAndGet() );
					logger.debug("handleOpenSecureChannelRequest: endpointServer={} SecureChannelId={}", endpointServer, channel.getSecureChannelId());
					channel.handleOpenChannel(mb, req);
				} else if (req.getRequestType() == SecurityTokenRequestType.Renew) {
					OpcTcpServerSecureChannel channel = (OpcTcpServerSecureChannel) secureChannels.get(mb.getSecureChannelId());
					if (channel==null)
						throw new ServiceResultException( Bad_SecureChannelIdInvalid );

					if (!ObjectUtils.objectEquals(req.getRequestType(), SecurityTokenRequestType.Renew)) throw new ServiceResultException(Bad_UnexpectedError);

					channel.handleRenewSecureChannelRequest(mb, req);
				}

				// Remove closed channels, should maybe be based on timestamps TODO
				Object[] channelArray = secureChannels.values().toArray();
				for (Object channel : channelArray)
					if(((AbstractServerSecureChannel)channel).getState().equals(CloseableObjectState.Closed))
						secureChannels.remove(((AbstractServerSecureChannel)channel).getSecureChannelId());

			}

			/**
			 * <p>handleRawChunk.</p>
			 *
			 * @param chunk a {@link java.nio.ByteBuffer} object.
			 */
			protected void handleRawChunk(ByteBuffer chunk) {
				int type = ChunkUtils.getMessageType(chunk);
				int messageType = type & TcpMessageType.MESSAGE_TYPE_MASK;
				int chunkType = type & TcpMessageType.CHUNK_TYPE_MASK;
				if (chunkType != TcpMessageType.FINAL) {
					close();
				}
				chunk.position(8);
				try {
					if (messageType == TcpMessageType.HELLO) {
						ChunksToMessage c2m = new ChunksToMessage(ctx, encoderCtx, Hello.class, chunk);
						handleHelloMessage( (Hello) c2m.call() );
					} else if (messageType == TcpMessageType.ACKNOWLEDGE) {
						ChunksToMessage c2m = new ChunksToMessage(ctx, encoderCtx, Acknowledge.class, chunk);
						handleAcknowledgeMessage( (Acknowledge) c2m.call() );
					} else if (messageType == TcpMessageType.ERROR) {
						ChunksToMessage c2m = new ChunksToMessage(ctx, encoderCtx, ErrorMessage.class, chunk);
						handleErrorMessage( (ErrorMessage) c2m.call() );
					}
				} catch (Exception e) {
					setError( StackUtils.toServiceResultException(e) );
				}
			}

			/**
			 * <p>handleSecureMessage.</p>
			 *
			 * @param mb a {@link org.opcfoundation.ua.transport.tcp.nio.InputMessage} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void handleSecureMessage(InputMessage mb) throws ServiceResultException {
				IEncodeable msg = mb.getMessage();
				int secureChannelId = mb.getSecureChannelId();
				if (logger.isDebugEnabled())
					logger.debug("handleSecureMessage: secureChannelId=" + secureChannelId + "msg=" + (msg == null ? "null" : msg.getClass().getSimpleName()));

				OpcTcpServerSecureChannel chan = (OpcTcpServerSecureChannel) secureChannels.get(secureChannelId);
				if (chan==null)
					throw new ServiceResultException( Bad_SecureChannelIdInvalid );

				if (msg instanceof OpenSecureChannelRequest) {
					OpenSecureChannelRequest req = (OpenSecureChannelRequest) msg;

					if (!ObjectUtils.objectEquals(req.getRequestType(), SecurityTokenRequestType.Renew)) throw new ServiceResultException(Bad_UnexpectedError);

					chan.handleRenewSecureChannelRequest(mb, req);
				} else if (msg instanceof CloseSecureChannelRequest) {
					chan.handleCloseSecureChannelRequest(mb, (CloseSecureChannelRequest)msg);
				} else {
					chan.handleSecureMessage(mb, msg);
				}
			}


			/**
			 * <p>handleSymmChunk.</p>
			 *
			 * @param chunk a {@link java.nio.ByteBuffer} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void handleSymmChunk(ByteBuffer chunk) throws ServiceResultException {
				int secureChannelId = ChunkUtils.getSecureChannelId(chunk);
				int tokenId = ChunkUtils.getTokenId(chunk);
				chunk.rewind();

				OpcTcpServerSecureChannel channel = (OpcTcpServerSecureChannel) secureChannels.get(secureChannelId);
				if (channel==null)
					throw new ServiceResultException(StatusCodes.Bad_TcpSecureChannelUnknown);
				if (channel.getState().equals(CloseableObjectState.Closed))
					throw new ServiceResultException(StatusCodes.Bad_SecureChannelIdInvalid);
				SecurityToken token = channel.getSecurityToken(tokenId);
				if (token==null)
					throw new ServiceResultException(StatusCodes.Bad_SecureChannelTokenUnknown);
				if (!token.isValid())
					token = channel.getLatestNonExpiredToken();
				if (token==null || !token.isValid()) {
					System.err.println("Token expired");
					throw new ServiceResultException(StatusCodes.Bad_SecureChannelClosed);
				}

				SecurityToken activeToken = channel.getActiveSecurityToken();
				if (token!=activeToken) {
					logger.debug("handleSymmChunk: activeToken={}, token={}", activeToken, token);
					if (activeToken.getCreationTime() < token.getCreationTime()) {
						channel.setActiveSecurityToken(token);
					}
				}
				logger.debug("handleSymmChunk: {}", secureMessageBuilder);
				if (secureMessageBuilder!=null && !secureMessageBuilder.moreChunksRequired()) secureMessageBuilder = null;
				if (secureMessageBuilder==null) {
					secureMessageBuilder = new SecureInputMessageBuilder(token/*channel*/, messageListener, ctx, encoderCtx, channel.recvSequenceNumber);
					logger.debug("handleSymmChunk: secureMessageBuilder={}", secureMessageBuilder);
					//				onSecureMessageBegin(secureMessageBuilder, chunk);
				}

				secureMessageBuilder.addChunk(chunk);
			}

			// Propagate connection closed/error to channels
			/** {@inheritDoc} */
			@Override
			protected synchronized void onStateTransition(CloseableObjectState oldState,
					CloseableObjectState newState) {
				super.onStateTransition(oldState, newState);
				logger.debug("onStateTransition: {}->{}", oldState, newState);

				if (newState == CloseableObjectState.Closing)
				{
					ServiceResultException err = getError();
					List<ServerSecureChannel> list = new ArrayList<ServerSecureChannel>();
					OpcTcpServerConnection.this.getSecureChannels(list);
					for (ServerSecureChannel c : list)
					{
						OpcTcpServerSecureChannel cc = (OpcTcpServerSecureChannel) c;
						if (cc != null && err!=null) cc.setError(err);
						c.close();
					}
				}
			}

			/**
			 * <p>sendAcknowledge.</p>
			 *
			 * @param a a {@link org.opcfoundation.ua.transport.tcp.impl.Acknowledge} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void sendAcknowledge(Acknowledge a)
					throws ServiceResultException
			{
				ChunkFactory rawChunkFactory = new AcknowledgeChunkFactory();
				MessageToChunks mc = new MessageToChunks(a, ctx, encoderCtx, rawChunkFactory, MessageType.Encodeable);
				final ByteBuffer[] plaintexts = mc.call();
				final ByteBuffer[] chunks = rawChunkFactory.expandToCompleteChunk(plaintexts);
				assert(chunks.length==1);

				// Set header and write
				chunks[0].putInt(TcpMessageType.ACKNOWLEDGE | TcpMessageType.FINAL);
				chunks[0].rewind();
				sendChunks(chunks);
			}

			/**
			 * {@inheritDoc}
			 *
			 * Send asymmetric secure message.
			 */
			@Override
			protected int sendAsymmSecureMessage(
					final AsyncWrite msg,
					final SecurityConfiguration securityConfiguration,
					int secureChannelId,
					int requestNumber,
					AtomicInteger sendSequenceNumber)
							throws ServiceResultException
			{
				synchronized(msg) {
					if (msg.isCanceled()) return -1;
					msg.setQueued();
				}
				ChunkFactory cf = new ChunkFactory.AsymmMsgChunkFactory(ctx.maxSendChunkSize, securityConfiguration);

				MessageToChunks mc = new MessageToChunks(msg.getMessage(), ctx, encoderCtx, cf, MessageType.Message);
				final ByteBuffer[] plaintexts = mc.call();
				final ByteBuffer[] chunks = cf.expandToCompleteChunk(plaintexts);
				synchronized(msg) {
					if (msg.isCanceled()) return -1;
					msg.setWriting();
				}
				SecurityPolicy policy = securityConfiguration.getSecurityPolicy();

				startChunkSend(chunks);
				for (int i=0; i<chunks.length; i++)
				{
					ByteBuffer chunk = chunks[i];
					ByteBuffer plaintext = plaintexts[i];
					boolean finalChunk = chunk == chunks[chunks.length-1];
					chunk.rewind();
					chunk.putInt( TcpMessageType.OPEN | (finalChunk ? TcpMessageType.FINAL : TcpMessageType.CONTINUE) );
					chunk.position(8);
					chunk.putInt(secureChannelId);

					// -- Security Header --
					// Policy URI
					byte[] data = policy.getEncodedPolicyUri();
					chunk.putInt(data.length);
					chunk.put(data);

					// Sender Certificate
					data = securityConfiguration.getEncodedLocalCertificate();
					chunk.putInt( data==null ? -1 : data.length);
					if (data!=null)	chunk.put(data);

					// Recv Certificate Thumbprint
					data = securityConfiguration.getEncodedRemoteCertificateThumbprint();
					chunk.putInt( data == null ? -1 : data.length);
					if (data!=null)	chunk.put(data);

					// -- Sequence header --
					chunk.putInt(sendSequenceNumber.getAndIncrement());
					chunk.putInt(requestNumber); // Request number

					new ChunkAsymmEncryptSigner(chunk, plaintext, securityConfiguration).run();
					chunk.rewind();
					endChunkSend(chunk);
				}
				msg.setWritten();
				return chunks.length;
			}

			/**
			 * Send chunks.
			 *
			 * @param chunks a {@link java.nio.ByteBuffer} object.
			 */
			protected synchronized void sendChunks(ByteBuffer...chunks)
			{
				startChunkSend(chunks);
				for (ByteBuffer chunk : chunks)
					endChunkSend(chunk);
			}

			/**
			 * <p>sendError.</p>
			 *
			 * @param e a {@link org.opcfoundation.ua.transport.tcp.impl.ErrorMessage} object.
			 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
			 */
			protected void sendError(ErrorMessage e)
					throws ServiceResultException
			{
				ChunkFactory rawChunkFactory = new ErrorMessageChunkFactory();
				MessageToChunks mc = new MessageToChunks(e, ctx, encoderCtx, rawChunkFactory, MessageType.Encodeable);
				final ByteBuffer[] plaintexts = mc.call();
				final ByteBuffer[] chunks = rawChunkFactory.expandToCompleteChunk(plaintexts);
				assert(chunks.length==1);

				// Set header and write
				chunks[0].putInt(TcpMessageType.ERROR | TcpMessageType.FINAL);
				chunks[0].rewind();
				sendChunks(chunks);
			}

			/**
			 * <p>sendHello.</p>
			 *
			 * @param h a {@link org.opcfoundation.ua.transport.tcp.impl.Hello} object.
			 */
			protected void sendHello(Hello h)
			{
				ctx.endpointUrl = h.getEndpointUrl();
				ChunkFactory rawChunkFactory = new ChunkFactory(ctx.maxSendChunkSize, 8, 0, 0, 0, 1, MessageSecurityMode.None, 0);
				//ChunkFactory rawChunkFactory = new HelloChunkFactory();
				MessageToChunks mc = new MessageToChunks(h, ctx, encoderCtx, rawChunkFactory, MessageType.Encodeable);
				final ByteBuffer[] plaintexts = mc.call();
				final ByteBuffer[] chunks = rawChunkFactory.expandToCompleteChunk(plaintexts);
				assert(chunks.length==1);

				// Set header and write
				chunks[0].putInt(TcpMessageType.HELLO | TcpMessageType.FINAL);
				chunks[0].rewind();
				sendChunks(chunks);
			}

			/**
			 * {@inheritDoc}
			 *
			 * Send symmetric secure message
			 */
			@Override
			protected void sendSecureMessage(
					final AsyncWrite msg,
					final SecurityToken token,
					final int requestId,
					final int messageType,
					final AtomicInteger sendSequenceNumber
					)
			{
				assert(token!=null);
				ByteBuffer chunks[], plaintexts[];
				boolean concurrent;
				try {
					synchronized(msg) {
						if (msg.isCanceled()) return;
						msg.setQueued();
					}
					//Probably more efficient to check isTraceEnabled before executing ObjectUtils.printFieldsDeep
					if (logger.isTraceEnabled())
						logger.trace("sendSecureMessage: " + ObjectUtils.printFieldsDeep(msg.getMessage()));
					EncoderCalc calc = new EncoderCalc();
					calc.setEncoderContext(encoderCtx);
					calc.putMessage(msg.getMessage());
					int len = calc.getLength();

					if (len>ctx.maxSendMessageSize && ctx.maxSendMessageSize!=0)
						throw new ServiceResultException(StatusCodes.Bad_TcpMessageTooLarge);

					SecurityPolicy policy = token.getSecurityPolicy();
					MessageSecurityMode mode = token.getMessageSecurityMode();
					SecurityAlgorithm symmEncryptAlgo = policy.getSymmetricEncryptionAlgorithm();
					SecurityAlgorithm symmSignAlgo = policy.getSymmetricSignatureAlgorithm();
					int cipherBlockSize = CryptoUtil.getCipherBlockSize(symmEncryptAlgo, null);
					int signatureSize = CryptoUtil.getSignatureSize(symmSignAlgo, null);
					int keySize = mode == MessageSecurityMode.SignAndEncrypt ? token.getRemoteEncryptingKey().length : 0;
					int paddingSize = mode == MessageSecurityMode.SignAndEncrypt ? keySize > 2048 ? 2 : 1 : 0;

					int maxPlaintextSize = ctx.maxSendChunkSize - 24 - paddingSize - signatureSize;
					maxPlaintextSize -= (maxPlaintextSize + paddingSize + signatureSize + 8) % cipherBlockSize;
					final int CORES = StackUtils.cores();

					int optimalPayloadSize = (len+CORES-1) / CORES;
					if (optimalPayloadSize > maxPlaintextSize)
						optimalPayloadSize = maxPlaintextSize;
					if (optimalPayloadSize < 4096)
						optimalPayloadSize = 4096;
					int optimalChunkSize = optimalPayloadSize + 24 + paddingSize + signatureSize;

					ChunkFactory cf = new ChunkFactory(
							optimalChunkSize,
							8,
							8,
							8,
							signatureSize,
							cipherBlockSize,
							mode,
							keySize);

					// Calculate chunk count
					final int count = (len + cf.maxPlaintextSize-1) / cf.maxPlaintextSize;
					if (count>ctx.maxSendChunkCount && ctx.maxSendChunkCount!=0)
						throw new ServiceResultException(StatusCodes.Bad_TcpMessageTooLarge);
					concurrent = (count > 1) && (CORES>0) && (mode != MessageSecurityMode.None);

					// Allocate chunks
					int bytesLeft = len;
					plaintexts = new ByteBuffer[count];
					chunks = new ByteBuffer[count];
					for (int i=0; i<count; i++) {
						plaintexts[i] = cf.allocate(bytesLeft);
						chunks[i] = cf.expandToCompleteChunk(plaintexts[i]);
						bytesLeft -= plaintexts[i].remaining();
					}
					assert(bytesLeft==0);

					// Start write
					synchronized(msg) {
						if (msg.isCanceled()) return;
						msg.setWriting();
					}
				} catch (ServiceResultException se) {
					msg.setError(se);
					return;
				}
				final ByteBuffer _chunks[] = chunks;
				final ByteBuffer _plaintexts[] = plaintexts;
				final int count = chunks.length;
				final boolean parallel = concurrent;

				int sequenceNumber = 0;
				synchronized(this) {
					sequenceNumber = sendSequenceNumber.getAndAdd(chunks.length);
					startChunkSend(chunks);
				}

				// Add chunk headers
				for (ByteBuffer chunk : chunks) {
					boolean finalChunk = chunk == chunks[chunks.length-1];
					chunk.rewind();
					chunk.putInt( messageType | (finalChunk ? TcpMessageType.FINAL : TcpMessageType.CONTINUE) );
					chunk.position(8);
					chunk.putInt(token.getSecureChannelId());

					// -- Security Header --
					chunk.putInt(token.getTokenId());

					// -- Sequence Header --
					chunk.putInt(sequenceNumber++);
					chunk.putInt(requestId);
				}

				// a Chunk-has-been-encoded handler
				final AtomicInteger chunksComplete = new AtomicInteger();
				ChunkListener completitionListener = new ChunkListener() {
					@Override
					public void onChunkComplete(ByteBuffer[] bufs, final int index) {
						Runnable action = new Runnable() {
							@Override
							public void run() {
								// Chunk contains message data, it needs to be encrypted and signed
								//							try {
								// Encrypt & sign
								new ChunkSymmEncryptSigner(_chunks[index], _plaintexts[index], token).run();
								_chunks[index].rewind();

								// Write chunk
								endChunkSend(_chunks[index]);

								// All chunks are completed
								if (chunksComplete.incrementAndGet()==count)
									msg.setWritten();

								//							} catch (ServiceResultException se) {
								//								msg.setError(se);
								//							}
							}};
							if (parallel && count>1) {
								StackUtils.getNonBlockingWorkExecutor().execute(action);
							} else {
								action.run();
							}
					}
				};

				// Create encoder
				ByteBufferArrayWriteable2 out = new ByteBufferArrayWriteable2(plaintexts, completitionListener);
				out.order(ByteOrder.LITTLE_ENDIAN);

				final BinaryEncoder enc = new BinaryEncoder(out);
				enc.setEncoderContext(encoderCtx);
				enc.setEncoderMode(EncoderMode.NonStrict);

				Runnable encoder = new Runnable() {
					@Override
					public void run() {
						try {
							enc.putMessage(msg.getMessage());
						} catch (ServiceResultException e) {
							msg.setError( StackUtils.toServiceResultException(e) );
						}
					}};
					StackUtils.getBlockingWorkExecutor().execute(encoder);
			}

			/** {@inheritDoc} */
			@Override
			protected synchronized void setError(ServiceResultException e) {
				if(!hasError()){
					try {
						sendError(new ErrorMessage(e.getStatusCode(), e.getMessage()));
					} catch (ServiceResultException e1) {
						logger.warn("Could not send error message",e1);
					}
					super.setError(e);
				}
			}

			/**
			 * Put chunks into send queue. Chunks will be given a sequence number
			 * but will be flushed in endChunkSend().
			 *
			 * @param chunks a {@link java.nio.ByteBuffer} object.
			 */
			protected void startChunkSend(ByteBuffer...chunks)
			{
				synchronized(chunkIncubator) {
					for (ByteBuffer chunk : chunks)
						chunkIncubator.incubate(chunk);
				}
			}

}
