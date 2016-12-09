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

package org.opcfoundation.ua.transport.tcp.impl;

import java.nio.ByteBuffer;

import org.opcfoundation.ua.common.RuntimeServiceResultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>ChunkSymmEncryptSigner class.</p>
 */
public class ChunkSymmEncryptSigner implements Runnable {

	/**
	 * Log4J Error logger. 
	 * Security failures are logged with INFO level.
	 * Security settings are logged with DEBUG level.
	 * Unexpected errors are logged with ERROR level. 
	 */
	static Logger logger = LoggerFactory.getLogger(ChunkSymmEncryptSigner.class);
	
	ByteBuffer chunk, body;
	SecurityToken token;
	
	/**
	 * <p>Constructor for ChunkSymmEncryptSigner.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @param body a {@link java.nio.ByteBuffer} object.
	 * @param token a {@link org.opcfoundation.ua.transport.tcp.impl.SecurityToken} object.
	 */
	public ChunkSymmEncryptSigner(ByteBuffer chunk, ByteBuffer body, SecurityToken token)
	{
		this.chunk = chunk;
		this.body = body;
		this.token = token;
	}
	
	/** {@inheritDoc} */
	@Override
	public void run() throws RuntimeServiceResultException 
	{
		SecurityPolicy policy = token.getSecurityPolicy();
		MessageSecurityMode msm = token.getMessageSecurityMode();
		try {

			int chunkSize = chunk.limit();
			int bodySize = body.limit();
			int sequenceHeader = 8;
			int messageHeaderSize = 8;
			int securityHeader = 8;

			// Chunk Size
//			int count = 0;
//			count += sequenceHeader;
//			count += bodySize;
//			count += policy.getSymmetricSignatureSize();

			// Sign
			int signatureSize = policy.getSymmetricSignatureSize();
			if ( msm == MessageSecurityMode.Sign || msm == MessageSecurityMode.SignAndEncrypt ) {
					
				// Message written so far will be signed
				int verifyLen = chunkSize - signatureSize;
				byte[] signature = new byte[ signatureSize ];
				
				sign(token, chunk.array(), verifyLen, signature);
				
				chunk.position(chunkSize - signatureSize);
				chunk.put(signature, 0, signatureSize);
				
				//isTraceEnabled checked because potentially time consuming CryptoUtil method gets evaluated otherwise every time.
				if (logger.isTraceEnabled()) {
					logger.trace("signature={}", CryptoUtil.toHex(signature));
				}
				
			}
			
			// Padding
			int padding = 0;
			if ( msm == MessageSecurityMode.SignAndEncrypt ) {
				chunk.position( chunkSize - signatureSize - 1 );
				padding = chunk.get() + 1;
			}

//			count += messageHeaderSize + securityHeader;

			// Write chunk size
			chunk.position(4);
			chunk.putInt(chunkSize);

			// Encrypt
			if ( msm == MessageSecurityMode.SignAndEncrypt ) {
				
				byte[] plaintext = new byte[ sequenceHeader + bodySize + padding + signatureSize ];
				chunk.position( messageHeaderSize + securityHeader );
				chunk.get(plaintext);
				
				// Run encrypt algorithm	
				encrypt(token, plaintext, 0, plaintext.length, chunk.array(), messageHeaderSize + securityHeader);
			}
			
		} catch (ServiceResultException e) {
			throw new RuntimeServiceResultException(e);
		}
	}
	
	private int encrypt(SecurityToken token, byte[] dataToEncrypt, int inputOffset, int inputLength, byte[] output, int outputOffset) 
			throws ServiceResultException
	{
		return CryptoUtil.getCryptoProvider().encryptSymm(token, dataToEncrypt, inputOffset, inputLength, output, outputOffset);
	}
	
	private void sign(SecurityToken token, byte[] input, int verifyLen, byte[] output)
			throws ServiceResultException
	{
		CryptoUtil.getCryptoProvider().signSymm(token, input, verifyLen, output);
	}

}
