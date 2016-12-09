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
import java.security.Key;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.opcfoundation.ua.common.RuntimeServiceResultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.transport.security.CryptoProvider;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.transport.security.SecurityConfiguration;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>ChunkAsymmEncryptSigner class.</p>
 */
public class ChunkAsymmEncryptSigner implements Runnable {

	/**
	 * Log4J Error logger. 
	 * Security failures are logged with INFO level.
	 * Security settings are logged with DEBUG level.
	 * Unexpected errors are logged with ERROR level. 
	 */
	static Logger logger = LoggerFactory.getLogger(ChunkAsymmEncryptSigner.class);
	
	ByteBuffer chunk, plaintext;
	SecurityConfiguration profile;

	private int signatureSize;
	
	/**
	 * <p>Constructor for ChunkAsymmEncryptSigner.</p>
	 *
	 * @param chunk a {@link java.nio.ByteBuffer} object.
	 * @param body a {@link java.nio.ByteBuffer} object.
	 * @param profile a {@link org.opcfoundation.ua.transport.security.SecurityConfiguration} object.
	 */
	public ChunkAsymmEncryptSigner(ByteBuffer chunk, ByteBuffer body, SecurityConfiguration profile)
	{
		this.chunk = chunk;
		this.plaintext = body;
		this.profile = profile;
	}
	
	/** {@inheritDoc} */
	@Override
	public void run() 
	throws RuntimeServiceResultException
	{
		try {
			int plaintextSize = plaintext.limit();
			MessageSecurityMode msm = profile.getMessageSecurityMode();
			if ( msm == MessageSecurityMode.Sign ) msm = MessageSecurityMode.SignAndEncrypt;

			SecurityPolicy policy = profile.getSecurityPolicy();
			int sequenceHeader = 8;
			SecurityAlgorithm signatureAlgorithm = policy.getAsymmetricSignatureAlgorithm();
			signatureSize = msm.hasSigning() ? CryptoUtil.getSignatureSize(signatureAlgorithm, profile.getLocalPrivateKey()) : 0;
			
			//At this point padding has already added to the chunk, check AsymChunkfactory
			
			//Get bytes that need to be signed
			//Amount of bytes to sign depends on policy (mode) in use
			logger.debug("SecurityMode in asymm enc: {}", msm.getValue());

			// Padding size includes the total padding: PaddingSize and ExtraPaddingSize bytes + padding
			int paddingSize = 0;
			
			// Encrypting
			if ( msm == MessageSecurityMode.SignAndEncrypt ){
	        	// Because of the encypting, add the minimal +1 or +2 padding bytes to the total size 
	        	int keySize = profile.getRemoteCertificate2().getKeySize();
	        	logger.trace("keySize={}", keySize);
				paddingSize = getPaddingSize(keySize);
	        	logger.trace("padding={}", paddingSize);
			}
			
			// Signing
			if ( msm == MessageSecurityMode.Sign || msm == MessageSecurityMode.SignAndEncrypt ) {
				
				byte[] plaintextForSign = new byte[plaintext.arrayOffset()+plaintextSize+paddingSize];
	        
		        // Set position to beginning of the chunk
		        chunk.rewind();
		        chunk.get(plaintextForSign, 0, plaintextForSign.length);
		        
		        // Sign
		        byte[] signature = sign(plaintextForSign, profile.getLocalPrivateKey());
		        
		        // Write back to chunk
	        	chunk.put(signature);
			}
			
	        if (logger.isTraceEnabled())
	        	logger.trace("getPaddingSize: chunk={}", CryptoUtil.toHex(chunk.array(), 64));
	        
	        if ( msm == MessageSecurityMode.SignAndEncrypt ) {
		        // Encrypt everything but the messageHeader and securityHeader
		        // Plaintext tells where the body of this message begins...
		        // There is sequenceHeader (size = 8) before the body and we want to encrypt that too 
		        
		        //Get amount of padding, which depends on security policy (mode) in use
		        byte[] bytesToEncrypt = new byte[ sequenceHeader+plaintextSize+paddingSize+signatureSize ];
		        
		        //Encrypt
				chunk.position( plaintext.arrayOffset() - sequenceHeader );
				chunk.get(bytesToEncrypt, 0, bytesToEncrypt.length);
				encrypt(bytesToEncrypt, 
						profile.getRemoteCertificate().getPublicKey(),
						chunk.array(), 
						plaintext.arrayOffset() - sequenceHeader);
	        }
				
	        //set chunk's position to the starting point of plaintext
			chunk.position(plaintext.arrayOffset());
			
		} catch (ServiceResultException e) {
			throw new RuntimeServiceResultException(e);
		}
	}
	
	/**
	 * Returns the number of bytes in the padding including paddingSize and
	 * possible extraPaddingByte.
	 * 
	 * @param keySize
	 * @return
	 */
	private int getPaddingSize(int keySize){
		//int lastPaddingBytePosition = chunk.limit() - signatureSize -1;
		int lastPaddingBytePosition = chunk.limit() - 1;
		//isTraceEnabled checked because potentially time consuming CryptoUtil methods get evaluated otherwise every time.
		if (logger.isTraceEnabled())
		{
			logger.trace("getPaddingSize: chunk={}", CryptoUtil.toHex(chunk.array(), 64));
			logger.trace("getPaddingSize: plaintext={}", CryptoUtil.toHex(plaintext.array(), 64));
			logger.trace("getPaddingSize: plaintext.arrayOffset()={}", plaintext.arrayOffset());
			logger.trace("getPaddingSize: plaintext.limit()={}", plaintext.limit());
			logger.trace("getPaddingSize: lastPaddingBytePosition={}", lastPaddingBytePosition);
		}
		
		if ( keySize > 2048 ) {
			int extraPaddingByte = chunk.get(lastPaddingBytePosition) & 0xff;
			int paddingByte = chunk.get(lastPaddingBytePosition - 1) & 0xff;
			logger.trace("getPaddingSize: paddingByte={}", paddingByte);
			logger.trace("getPaddingSize: extraPaddingByte={}", extraPaddingByte);
			logger.trace("getPaddingSize: padding={}", (paddingByte | extraPaddingByte << 8));
			return ( ( paddingByte & 0xff ) | ( (extraPaddingByte & 0xff) << 8 ) ) + 2;
		} else {
			int paddingByte = chunk.get(lastPaddingBytePosition) & 0xff;
			return ( paddingByte & 0xff ) + 1;
		}
		
	}
	
	//This function checks how the encryption is done.
	private void encrypt(byte[] dataToEncrypt, PublicKey publicKey, byte[] output, int outputOffset) throws ServiceResultException{
		SecurityPolicy policy = profile.getSecurityPolicy();
		logger.debug("rsa_Encrypt: policy={}", policy);
		
		//RSAPublicKey serverPublic =  (RSAPublicKey) encryptingCertificate.getPublicKey();
		//Cipher cipher = null;
		
		int inputBlockSize = 1;
		Key key = profile.getRemoteCertificate().getPublicKey();
		inputBlockSize = CryptoUtil.getPlainTextBlockSize(policy.getAsymmetricEncryptionAlgorithm(), key);
		
		logger.debug("encrypt: inputBlockSize={}", inputBlockSize);
		
		//get RSAPublicKey from Certificate
		
		// verify that the input data has the correct block size
		if(dataToEncrypt.length % inputBlockSize !=0) {
			logger.error("Wrong block size in asym encryption: length={} inputBlockSize={}", dataToEncrypt.length, inputBlockSize);
			throw new ServiceResultException(StatusCodes.Bad_InternalError, "Error in asymmetric encrypt: Input data is not an even number of encryption blocks.");
		}

		CryptoProvider cryptoProvider = CryptoUtil.getCryptoProvider();
		cryptoProvider.encryptAsymm(publicKey, profile.getSecurityPolicy().getAsymmetricEncryptionAlgorithm(), dataToEncrypt, output, outputOffset);
		
		if (logger.isTraceEnabled()) {
			logger.trace("encrypt: dataToEncrypt={}", CryptoUtil.toHex(dataToEncrypt,64));
			logger.trace("encrypt: output={}", CryptoUtil.toHex(output,64));
		}
		
	}
	
	private byte[] sign(byte[] dataToSign, RSAPrivateKey senderPrivate) throws ServiceResultException{		
		SecurityPolicy policy = profile.getSecurityPolicy();
		//Default
		if(policy == SecurityPolicy.NONE) {
			return null;
		}
		byte[] signature = CryptoUtil.getCryptoProvider().signAsymm(senderPrivate, profile.getSecurityPolicy().getAsymmetricSignatureAlgorithm(), dataToSign);
		
		if (logger.isTraceEnabled()) {
			logger.trace("sign: dataToSign={}", CryptoUtil.toHex(dataToSign,64));
			logger.trace("sign: signature={}", CryptoUtil.toHex(signature,64));
		}
		
		return signature;
	}

}
