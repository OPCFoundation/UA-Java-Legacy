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
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.GregorianCalendar;

import javax.crypto.Mac;

import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.transport.security.SecurityConfiguration;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.CryptoUtil;

/**
 * Security Token of a tcp connection
 */
public class SecurityToken {

	private static final Charset UTF8 = Charset.forName("utf-8");
	
	private SecurityConfiguration securityConfiguration;	
	private int tokenId;
	private int secureChannelId;
	private long creationTime;
	private long lifetime;
	
	private ByteString localNonce;
	private ByteString remoteNonce;
	private byte[] localSigningKey;
    private byte[] localEncryptingKey;
    private byte[] localInitializationVector;
    private byte[] remoteSigningKey;
    private byte[] remoteEncryptingKey;
    private byte[] remoteInitializationVector;
    
	/**
	 * Create new security token.
	 *
	 * @param securityProfile a {@link org.opcfoundation.ua.transport.security.SecurityConfiguration} object.
	 * @param secureChannelId a int.
	 * @param tokenId a int.
	 * @param creationTime a long.
	 * @param lifetime a long.
	 * @param serverNonce an array of byte.
	 * @param clientNonce an array of byte.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public SecurityToken(SecurityConfiguration securityProfile, 
			int secureChannelId, int tokenId, 
			long creationTime, long lifetime,
			ByteString serverNonce, ByteString clientNonce) 
	throws ServiceResultException
	{
		if (securityProfile==null)
			throw new IllegalArgumentException("null arg");
		this.secureChannelId = secureChannelId;
		this.securityConfiguration = securityProfile;
		this.tokenId = tokenId;
		this.lifetime = lifetime;
		this.creationTime = creationTime;
		
		this.localNonce = serverNonce;
		this.remoteNonce = clientNonce;
		
        boolean isNone = securityProfile.getMessageSecurityMode() == MessageSecurityMode.None;
       	//Calculate and set keys       
		int sks = getSecurityPolicy().getSignatureKeySize();
		int eks = getSecurityPolicy().getEncryptionKeySize();
		int ebs = getSecurityPolicy().getEncryptionBlockSize();
		localSigningKey = isNone? null : PSHA(getRemoteNonce(), null, getLocalNonce(), 0, sks);
		localEncryptingKey = isNone? null : PSHA(getRemoteNonce(), null, getLocalNonce(), sks, eks);
		localInitializationVector = isNone? null : PSHA(getRemoteNonce(), null, getLocalNonce(), sks + eks, ebs);
		remoteSigningKey = isNone? null : PSHA(getLocalNonce(), null, getRemoteNonce(), 0, sks);
		remoteEncryptingKey = isNone? null : PSHA(getLocalNonce(), null, getRemoteNonce(), sks, eks);
		remoteInitializationVector = isNone? null : PSHA(getLocalNonce(), null, getRemoteNonce(), sks + eks, ebs);
	}	
	
	/**
     * Generates a Pseudo random sequence of bits using the P_SHA1 or P_SHA256 alhorithm.
     * 
     * This function conforms with C#-implementation, so that keys returned 
     * from this function matches the C#-implementation keys.
     * 
     * @param byteString
     * @param label
     * @param byteString2
     * @param offset
     * @param length
     * @return the pseudo random bytes
     */
	private byte[] PSHA(ByteString byteString, String label, ByteString byteString2,
			int offset, int length) throws ServiceResultException {
        //test parameters
    	if (byteString == null) throw new IllegalArgumentException("ArgumentNullException: secret");
        if (offset < 0)     throw new IllegalArgumentException("ArgumentOutOfRangeException: offset");
        if (length < 0)     throw new IllegalArgumentException("ArgumentOutOfRangeException: offset");

        // convert label to UTF-8 byte sequence.
        byte[] seed = label != null && !label.isEmpty() ? label.getBytes(UTF8) : null;

        // append data to label.
        if (byteString2 != null && byteString2.getLength() > 0){
            if (seed != null){
            	ByteBuffer buf = ByteBuffer.allocate(seed.length+byteString2.getLength());
            	buf.put(seed);
            	buf.put(byteString2.getValue());
            	buf.rewind();
                seed = buf.array();
            }
            else
            {
                seed = byteString2.getValue();
            }
        }

        // check for a valid seed.
        if (seed == null)
        {
           throw new ServiceResultException(StatusCodes.Bad_UnexpectedError, "The PSHA algorithm requires a non-null seed.");
        }

        // create the hmac.
        SecurityPolicy policy = securityConfiguration.getSecurityPolicy();
		Mac hmac = CryptoUtil.createMac(policy.getKeyDerivationAlgorithm(), byteString.getValue());
        //update data to mac and compute it
        hmac.update(seed);
        byte[] keySeed = hmac.doFinal();
       
        byte[] prfSeed = new byte[hmac.getMacLength() + seed.length];
        
        //Copy keyseed to prfseed from starting point to keyseed.lenght
        System.arraycopy(keySeed, 0, prfSeed, 0, keySeed.length);
        //Copy seed to prfseed, put it after keyseed
        System.arraycopy(seed, 0, prfSeed, keySeed.length, seed.length);
        
                    
        // create buffer with requested size.
        byte[] output = new byte[length];

        int position = 0;

        
        do {
        	//because Mac.doFinal reseted hmac, we must update it again
        	hmac.update(prfSeed);
        	//compute always new hash from prfseed
        	byte[] hash = hmac.doFinal();

            if (offset < hash.length)
            {
                for (int ii = offset; position < length && ii < hash.length; ii++)
                {
                    output[position++] = hash[ii];
                }
            }

            if (offset > hash.length)
            {
                offset -= hash.length;
            }
            else
            {
                offset = 0;
            }

            //calculate hmac from keySeed
            hmac.update(keySeed);
            keySeed = hmac.doFinal();
            System.arraycopy(keySeed, 0, prfSeed, 0, keySeed.length);
            
        }
        while (position < length);

        // return random data.
        return output;
    }

	/**
	 * Return security token validity. Security token is still valid if it has expired
	 * up to 25% after its lifetime. (See Part 6, 5.5.2.1/3)
	 *
	 * @return true if less than 125% of tokens life time has elapsed.
	 */
	public boolean isValid()
	{
		return System.currentTimeMillis() < creationTime + lifetime + (lifetime / 4);
	}
	
	/**
	 * Return security token time to renew status.
	 * True if 75% of security tokens life-time has elapsed.
	 *
	 * @return true if 75% of tokens life-time has passed
	 */
	public boolean isTimeToRenew()
	{
		return creationTime + (lifetime * 3) / 4 < System.currentTimeMillis();
	}
	
	/**
	 * Return security tokens expired status.
	 * Token is expired if its 100% of its life time has elapsed. Note, the token
	 * is valid for use until 125% of its life time has passed.
	 *
	 * @return true if 100% of security tokens life time has elapsed.
	 */
	public boolean isExpired()
	{
		return System.currentTimeMillis() >= creationTime + lifetime;
	}

	/**
	 * <p>getSecurityPolicy.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.SecurityPolicy} object.
	 */
	public SecurityPolicy getSecurityPolicy() {
		return securityConfiguration.getSecurityPolicy();
	}
	
	/**
	 * <p>Getter for the field <code>securityConfiguration</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.transport.security.SecurityConfiguration} object.
	 */
	public SecurityConfiguration getSecurityConfiguration() {
		return securityConfiguration;
	}
	
	/**
	 * <p>getMessageSecurityMode.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.MessageSecurityMode} object.
	 */
	public MessageSecurityMode getMessageSecurityMode() {
		return securityConfiguration.getMessageSecurityMode();
	}
	
	/**
	 * <p>Getter for the field <code>localSigningKey</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getLocalSigningKey() {
		return localSigningKey;
	}

	/**
	 * <p>Setter for the field <code>localSigningKey</code>.</p>
	 *
	 * @param localSigningKey an array of byte.
	 */
	public void setLocalSigningKey(byte[] localSigningKey) {
		this.localSigningKey = localSigningKey;
	}

	/**
	 * <p>Getter for the field <code>localEncryptingKey</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getLocalEncryptingKey() {
		return localEncryptingKey;
	}

	/**
	 * <p>Setter for the field <code>localEncryptingKey</code>.</p>
	 *
	 * @param localEncryptingKey an array of byte.
	 */
	public void setLocalEncryptingKey(byte[] localEncryptingKey) {
		this.localEncryptingKey = localEncryptingKey;
	}

	/**
	 * <p>Getter for the field <code>localInitializationVector</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getLocalInitializationVector() {
		return localInitializationVector;
	}

	/**
	 * <p>Setter for the field <code>localInitializationVector</code>.</p>
	 *
	 * @param localInitializationVector an array of byte.
	 */
	public void setLocalInitializationVector(byte[] localInitializationVector) {
		this.localInitializationVector = localInitializationVector;
	}

	/**
	 * <p>Getter for the field <code>remoteSigningKey</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getRemoteSigningKey() {
		return remoteSigningKey;
	}

	/**
	 * <p>Setter for the field <code>remoteSigningKey</code>.</p>
	 *
	 * @param remoteSigningKey an array of byte.
	 */
	public void setRemoteSigningKey(byte[] remoteSigningKey) {
		this.remoteSigningKey = remoteSigningKey;
	}

	/**
	 * <p>Getter for the field <code>remoteEncryptingKey</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getRemoteEncryptingKey() {
		return remoteEncryptingKey;
	}

	/**
	 * <p>Setter for the field <code>remoteEncryptingKey</code>.</p>
	 *
	 * @param remoteEncryptingKey an array of byte.
	 */
	public void setRemoteEncryptingKey(byte[] remoteEncryptingKey) {
		this.remoteEncryptingKey = remoteEncryptingKey;
	}

	/**
	 * <p>Getter for the field <code>remoteInitializationVector</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public byte[] getRemoteInitializationVector() {
		return remoteInitializationVector;
	}

	/**
	 * <p>Setter for the field <code>remoteInitializationVector</code>.</p>
	 *
	 * @param remoteInitializationVector an array of byte.
	 */
	public void setRemoteInitializationVector(byte[] remoteInitializationVector) {
		this.remoteInitializationVector = remoteInitializationVector;
	}

	/**
	 * Crate new remoteHmac
	 *
	 * @return hmac
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public Mac createRemoteHmac() throws ServiceResultException 
	{
		return createHmac(getRemoteSigningKey());
	}
	
	/**
	 * Create new localHmac
	 *
	 * @return hmac
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public Mac createLocalHmac() throws ServiceResultException 
	{
		return createHmac(getLocalSigningKey());
	}

	/**
	 * <p>createHmac.</p>
	 *
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 * @param secret an array of byte.
	 * @return a {@link javax.crypto.Mac} object.
	 */
	protected Mac createHmac(byte[] secret) throws ServiceResultException {
		SecurityPolicy policy = securityConfiguration.getSecurityPolicy();
		return CryptoUtil.createMac(policy.getSymmetricSignatureAlgorithm(), secret);
	}
	

	/**
	 * <p>Getter for the field <code>localNonce</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public ByteString getLocalNonce() {
		return localNonce;
	}

	/**
	 * <p>Getter for the field <code>remoteNonce</code>.</p>
	 *
	 * @return an array of byte.
	 */
	public ByteString getRemoteNonce() {
		return remoteNonce;
	}

	/**
	 * <p>Getter for the field <code>secureChannelId</code>.</p>
	 *
	 * @return a int.
	 */
	public int getSecureChannelId() {
		return secureChannelId;
	}

	/**
	 * <p>Getter for the field <code>tokenId</code>.</p>
	 *
	 * @return a int.
	 */
	public int getTokenId() {
		return tokenId;
	}
	
	/**
	 * <p>Getter for the field <code>creationTime</code>.</p>
	 *
	 * @return a long.
	 */
	public long getCreationTime()
	{
		return creationTime;
	}
	
	/**
	 * <p>getLifeTime.</p>
	 *
	 * @return a long.
	 */
	public long getLifeTime()
	{
		return lifetime;
	}
	
	/**
	 * <p>getRenewTime.</p>
	 *
	 * @return a long.
	 */
	public long getRenewTime()
	{
		return creationTime + ((lifetime *3)/4);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(creationTime);
		return "SecurityToken(Id="+tokenId+", secureChannelId="+secureChannelId+", creationTime="+DateFormat.getDateTimeInstance().format(cal.getTime())+", lifetime="+lifetime+")";
	}
	
}
