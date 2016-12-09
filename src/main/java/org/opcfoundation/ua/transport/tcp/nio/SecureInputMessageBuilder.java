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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.binary.BinaryDecoder;
import org.opcfoundation.ua.transport.security.SecurityConfiguration;
import org.opcfoundation.ua.transport.tcp.impl.ChunkAsymmDecryptVerifier;
import org.opcfoundation.ua.transport.tcp.impl.ChunkSymmDecryptVerifier;
import org.opcfoundation.ua.transport.tcp.impl.ChunkUtils;
import org.opcfoundation.ua.transport.tcp.impl.SecurityToken;
import org.opcfoundation.ua.transport.tcp.impl.TcpConnectionParameters;
import org.opcfoundation.ua.transport.tcp.impl.TcpMessageType;
import org.opcfoundation.ua.utils.StackUtils;
import org.opcfoundation.ua.utils.bytebuffer.IncubationBuffer;
import org.opcfoundation.ua.utils.bytebuffer.InputStreamReadable;

/**
 * SecureInputMessageBuilder deciphers and decodes chunks into messages.
 * <p>
 * Message is decoded and chunks are deciphered and validated in background threads.
 * Deciphering is executed in StackUtils.getNonBlockerExecutor() which has one thread for each CPU core.
 * Decoding is executed in StackUtils.getBlockerExecutor() which creates new threads as needed.
 */
public class SecureInputMessageBuilder implements InputMessage {
	
	/** Message completition / error callback listener */
	MessageListener								listener;
	/** Token, {@link SecurityToken} if symmetric, {@link SecurityConfiguration} if asymmetric encryption */
	Object										token;
	/** Chunk assumptions from hand-shake */
	TcpConnectionParameters						ctx;
	/** Encoder Parameters */
	EncoderContext								encoderCtx;
	/** Stored error */
	Exception									error;
	/** Producer for decoder, consumer for chunk validator&decrypter */
	IncubationBuffer							chunkSink;
//	OrderedByteBufferInputStream chunkSink;
	/** Decode work */
	Runnable									messageDecoderRun;
	/** Chunks added counter */
	int											chunksAdded;
	/** The end result */
	IEncodeable									msg;
	Integer										requestId;
	Integer										securityChannelId;
	int											messageType;
	boolean										acceptsChunks = true; // set to false when final chunk is added  
	boolean										done; // set to true when the whole message is decoded or until an error occurs 
	String										securityPolicyUri;
	byte[]										senderCertificate;
	byte[]										receiverCertificateThumbPrint;
	List<Integer>								chunkSequenceNumbers = new ArrayList<Integer>(1);
	AtomicInteger								expectedSequenceNumber;
	static Logger 								log = LoggerFactory.getLogger(SecureInputMessageBuilder.class);

	public interface MessageListener {
		/**
		 * On message completed or error occured. 
		 * Use {@link InputMessage#getMessage()} to get the message, 
		 * if null get the error with {@link InputMessage#getError()}. 
		 * 
		 * @param sender 
		 */
		void onMessageComplete(InputMessage sender);
	}
	
	/**
	 * Create message builder. Message builder compiles inbound chunks into a message.
	 *
	 * @param token {@link SecurityToken} (symm) or {@link SecurityConfiguration} (asymm)
	 * @param listener a {@link org.opcfoundation.ua.transport.tcp.nio.SecureInputMessageBuilder.MessageListener} object.
	 * @param ctx a {@link org.opcfoundation.ua.transport.tcp.impl.TcpConnectionParameters} object.
	 * @param expectedSequenceNumber a {@link java.util.concurrent.atomic.AtomicInteger} object.
	 * @param encoderCtx a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public SecureInputMessageBuilder(Object token, MessageListener listener, TcpConnectionParameters ctx, EncoderContext encoderCtx, AtomicInteger expectedSequenceNumber)
	{
		assert(token!=null);
		this.listener = listener;
		this.token = token;
		this.ctx = ctx;
		this.encoderCtx = encoderCtx;
		
		this.expectedSequenceNumber = expectedSequenceNumber;
		log.debug("SecureInputMessageBuilder: expectedSequenceNumber={}", expectedSequenceNumber);
		// chunkSink is a byte input stream that is handed over to message decoder
		// New bytes become available as chunks are added by handleChunkRuns (see addChunk()). 
		chunkSink = new IncubationBuffer();		
//		chunkSink = new OrderedByteBufferInputStream();
		int maxRecvSize = ctx.maxRecvMessageSize==0 ? Integer.MAX_VALUE : ctx.maxRecvMessageSize;
		InputStreamReadable isr = new InputStreamReadable(chunkSink, maxRecvSize);
		isr.order(ByteOrder.LITTLE_ENDIAN);		
		final BinaryDecoder messageDecoder = new BinaryDecoder(isr);
		messageDecoder.setEncoderContext(encoderCtx);
		
		// Runnable that starts decoding the message. 
		// It is started in a thread right after the first chunk is added (addChunk())
		messageDecoderRun = new Runnable() {
			public void run() {				
				try {					
					// Decode the message using the chunk sink (set in dec)
					// Decoding proceeds as chunks are added to the chunk sink. 
					IEncodeable message = messageDecoder.getMessage();
					
					// assert sequence numbers are consecutive
					if (!(SecureInputMessageBuilder.this.token instanceof SecurityToken))
						for (int i=1; i<chunkSequenceNumbers.size(); i++)
							if (chunkSequenceNumbers.get(i) != chunkSequenceNumbers.get(i-1)-1) {
								String msg = "Sequence numbers of chunks are not consecutive";
								log.info(msg);
								setError(new ServiceResultException(StatusCodes.Bad_DecodingError, msg));
								return;
							}
					
					// Notify listener that message is ready
					setMessage( message );
				} catch (Exception e) {
					// Notify listener about an error
					setError(e);
				} catch (StackOverflowError e1) {
					// Payloads of high nesting levels may cause stack overflow. Structure, VariantArray and DiagnosticInfo at least may cause this.
					// JVM setting -Xss influences possible level of nesting. At least 100 levels of nesting must be supported, this should not be a problem with normal thread stack sizes. 
					// Inform receiving side that error has happened.
					setError(new ServiceResultException(StatusCodes.Bad_DecodingError, "Stack overflow: " + Arrays.toString(Arrays.copyOf(e1.getStackTrace(), 30)) + "..."));
				}
			}};
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("token="+token);
		sb.append(", secureChannelId="+securityChannelId);
		sb.append(", more="+moreChunksRequired());
		return sb.toString();
	}

	/**
	 * <p>addChunk.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public synchronized void addChunk(final ByteBuffer chunk) throws ServiceResultException
	{
		if (!acceptsChunks) throw new ServiceResultException(StatusCodes.Bad_UnexpectedError, "Final chunk added to message builder");
		final int chunkNumber = chunksAdded++;	
		chunkSequenceNumbers.add(null);
		int type = ChunkUtils.getMessageType(chunk);
		int messageType = type & TcpMessageType.MESSAGE_TYPE_MASK;
		int chunkType = type & TcpMessageType.CHUNK_TYPE_MASK;
		if (chunkType == TcpMessageType.FINAL) acceptsChunks = false;
		final Integer expectedSequenceNumber = this.expectedSequenceNumber!=null ? this.expectedSequenceNumber.getAndIncrement() : null;
		log.debug("addChunk: expectedSequenceNumber={}", expectedSequenceNumber);
		if (chunkType == TcpMessageType.ABORT) {
			setMessage(null);
		}
		
		if (chunkNumber==0) {
			this.messageType = messageType;
			this.securityChannelId = ChunkUtils.getSecureChannelId(chunk);
		}		
		
		chunkSink.incubate(chunk);
		Runnable handleChunkRun = new Runnable() {
			public void run() {
				if (hasError()) return;
				try {	
					log.debug("token: {}", token);
					if (token instanceof SecurityToken)
						new ChunkSymmDecryptVerifier(chunk, (SecurityToken)token).run();
					else if (token instanceof SecurityConfiguration) {
						ChunkAsymmDecryptVerifier asdf = new ChunkAsymmDecryptVerifier(chunk, (SecurityConfiguration)token);
						asdf.run();						
						securityPolicyUri = asdf.getSecurityPolicyUri();
						senderCertificate = asdf.getSenderCertificate();
						receiverCertificateThumbPrint = asdf.getReceiverCertificateThumbprint();
					}
					
					int pos = chunk.position();
					byte[] dada = new byte[chunk.remaining()];
					chunk.get(dada);
					chunk.position(pos);
					
					int plaintextStart = chunk.position();
					chunk.position(plaintextStart-8);
					int chunkSequenceNumber = chunk.getInt();
					chunkSequenceNumbers.set(chunkNumber, chunkSequenceNumber);
					if (expectedSequenceNumber!=null) {
						if (chunkSequenceNumber > expectedSequenceNumber) {
							// If we are ahead, wait a little while
							long t0 = System.currentTimeMillis();
							long TIMEOUT = 100; // ms
							while ((chunkSequenceNumber > expectedSequenceNumber)
									&& (System.currentTimeMillis() - t0 < TIMEOUT))
								Thread.sleep(1);
						}
						if(expectedSequenceNumber!=chunkSequenceNumber)
						{
							//log.warn("chunkSequenceNumber="+chunkSequenceNumber+", expectedSequenceNumber="+expectedSequenceNumber);
							throw new ServiceResultException(StatusCodes.Bad_UnexpectedError, "chunkSequenceNumber="+chunkSequenceNumber+", expectedSequenceNumber="+expectedSequenceNumber);
						}
					}
					int requestId = chunk.getInt();
					setRequestId( requestId );

					// verify secure channel id
					int secureChannelId = ChunkUtils.getSecureChannelId(chunk);
					if (secureChannelId!=SecureInputMessageBuilder.this.securityChannelId)
						throw new ServiceResultException(StatusCodes.Bad_UnexpectedError, "secureChannelId="+secureChannelId+", expected Id");
					
					chunk.position(plaintextStart);
					chunkSink.hatch(chunk);
//					chunkSink.offer(chunkNumber, chunk);
				} catch (Exception e) {
					log.info("addChunk: failed", e);
					chunkSink.forceClose();
					setError(e);
				}				
			}};
			
		// Validate chunk
		StackUtils.getNonBlockingWorkExecutor().execute(handleChunkRun);
		
		// Start decoding message
		if (chunkNumber==0)
			StackUtils.getBlockingWorkExecutor().execute(messageDecoderRun);		
	}	
	
	/**
	 * <p>fireComplete.</p>
	 */
	protected void fireComplete() {
		if (listener!=null) listener.onMessageComplete(this);
	}
	
	/**
	 * <p>Setter for the field <code>error</code>.</p>
	 *
	 * @param e a {@link java.lang.Exception} object.
	 */
	protected void setError(Exception e)
	{
		synchronized (this) {
			if (done) {
				log.info("setError[when done]", e);
				return;
			}
			done = true;
			this.error = e;
			chunkSink.forceClose();
		}
		fireComplete();
	}	
	
	/**
	 * <p>setMessage.</p>
	 *
	 * @param msg a {@link org.opcfoundation.ua.encoding.IEncodeable} object.
	 */
	protected void setMessage(IEncodeable msg)
	{
		synchronized (this) {
			if (done)
				return;
			chunkSink.close();
			done = true;
			this.msg = msg;
		}
		fireComplete();
	}
	
	private synchronized void setRequestId(int requestId) throws ServiceResultException 
	{
		if (this.requestId!=null && this.requestId!=requestId)
			throw new ServiceResultException(StatusCodes.Bad_UnexpectedError);
		this.requestId = requestId;
	}
	
	/**
	 * <p>Getter for the field <code>requestId</code>.</p>
	 *
	 * @return a int.
	 */
	public int getRequestId() {
		return requestId;
	}
	
	/**
	 * <p>isDone.</p>
	 *
	 * @return a boolean.
	 */
	public synchronized boolean isDone() {
		return done;
	}
	
	/**
	 * <p>moreChunksRequired.</p>
	 *
	 * @return a boolean.
	 */
	public synchronized boolean moreChunksRequired() {
		return acceptsChunks;
	}
	
	/**
	 * <p>close.</p>
	 */
	public void close() {
		if (done) return;
		done = true;
		chunkSink.forceClose();
	}
	
	/**
	 * <p>getMessage.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.encoding.IEncodeable} object.
	 */
	public IEncodeable getMessage() {
		return msg;
	}
	
	/**
	 * <p>Getter for the field <code>error</code>.</p>
	 *
	 * @return a {@link java.lang.Exception} object.
	 */
	public Exception getError() {
		return error;
	}
		
	/**
	 * <p>Getter for the field <code>messageType</code>.</p>
	 *
	 * @return a int.
	 */
	public int getMessageType() {
		return messageType;
	}
	
	/**
	 * <p>getSecureChannelId.</p>
	 *
	 * @return a int.
	 */
	public int getSecureChannelId() {
		return securityChannelId;
	}
	
	/**
	 * <p>Getter for the field <code>securityPolicyUri</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSecurityPolicyUri() {
		return securityPolicyUri;
	}

	/**
	 * <p>Getter for the field <code>senderCertificate</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getSenderCertificate() {
		return senderCertificate;
	}

	/**
	 * <p>getReceiverCertificateThumbprint.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getReceiverCertificateThumbprint() {
		return receiverCertificateThumbPrint;
	}	
	
	/**
	 * Return sequence number of each chunk
	 *
	 * @return list of sequnce numbers
	 */
	public List<Integer> getSequenceNumbers() {
		return chunkSequenceNumbers;
	}

	/** {@inheritDoc} */
	@Override
	public Object getToken() {
		return token;
	}

	/**
	 * <p>hasError.</p>
	 *
	 * @return a boolean.
	 */
	protected synchronized boolean hasError() {
		return error!=null;
	}
	
}
