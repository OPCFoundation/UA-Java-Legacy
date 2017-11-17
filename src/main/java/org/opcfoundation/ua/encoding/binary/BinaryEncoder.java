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

package org.opcfoundation.ua.encoding.binary;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.ConnectException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.builtintypes.BuiltinsMap;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.Enumeration;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedLong;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.builtintypes.XmlElement;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.IdType;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.EncodeType;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.EncoderMode;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.IEncoder;
import org.opcfoundation.ua.utils.MultiDimensionArrayUtils;
import org.opcfoundation.ua.utils.bytebuffer.ByteBufferWriteable;
import org.opcfoundation.ua.utils.bytebuffer.IBinaryWriteable;
import org.opcfoundation.ua.utils.bytebuffer.OutputStreamWriteable;

/**
 * Encodes builtintypes, enumerations, structures and messages to
 * a byte buffer.
 *
 * <p>
 * Null valued arguments are encoded with default empty values
 * when encoder mode is NonStrict.
 *
 * @see EncoderCalc Calculates lengths of messages
 * @see IEncoder encoder interface
 * @see BinaryDecoder binary decoder
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class BinaryEncoder implements IEncoder {
	private static Logger logger = LoggerFactory.getLogger(BinaryEncoder.class);

	/** Constant <code>UTF8</code> */
	public static final Charset UTF8 = Charset.forName("utf-8");

	IBinaryWriteable out;
	EncoderContext ctx; 
	EncoderMode mode = EncoderMode.Strict;

	/**
	 * <p>Constructor for BinaryEncoder.</p>
	 *
	 * @param out a {@link org.opcfoundation.ua.utils.bytebuffer.IBinaryWriteable} object.
	 */
	public BinaryEncoder(IBinaryWriteable out)
	{
		setWriteable(out);
		//ctx = EncoderContext.getDefault();
	}
	
	/**
	 * <p>Constructor for BinaryEncoder.</p>
	 *
	 * @param os a {@link java.io.OutputStream} object.
	 */
	public BinaryEncoder(OutputStream os)
	{
		OutputStreamWriteable osr = new OutputStreamWriteable(os);
		osr.order(ByteOrder.LITTLE_ENDIAN);
		//ctx = EncoderContext.getDefault();
		setWriteable(osr);
	}
	
	/**
	 * <p>Constructor for BinaryEncoder.</p>
	 *
	 * @param buf a {@link java.nio.ByteBuffer} object.
	 */
	public BinaryEncoder(ByteBuffer buf)
	{
		ByteBufferWriteable wbb = new ByteBufferWriteable(buf); 
		//ctx = EncoderContext.getDefault();
		setWriteable(wbb);
	}
	
	/**
	 * <p>Constructor for BinaryEncoder.</p>
	 *
	 * @param buf an array of byte.
	 */
	public BinaryEncoder(byte[] buf)
	{
		ByteBuffer bb = ByteBuffer.wrap(buf);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		setWriteable( new ByteBufferWriteable(bb) ); 
		//ctx = EncoderContext.getDefault();
	}
	
	/**
	 * <p>Constructor for BinaryEncoder.</p>
	 *
	 * @param buf an array of byte.
	 * @param off a int.
	 * @param len a int.
	 */
	public BinaryEncoder(byte[] buf, int off, int len)
	{
		ByteBuffer bb = ByteBuffer.wrap(buf, off, len);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		setWriteable( new ByteBufferWriteable(bb) ); 
		//ctx = EncoderContext.getDefault();
	}	

	/**
	 * <p>getEncoderContext.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public EncoderContext getEncoderContext() {
		return ctx;
	}

	/**
	 * <p>setEncoderContext.</p>
	 *
	 * @param ctx a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public void setEncoderContext(EncoderContext ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * <p>setWriteable.</p>
	 *
	 * @param out a {@link org.opcfoundation.ua.utils.bytebuffer.IBinaryWriteable} object.
	 */
	public void setWriteable(IBinaryWriteable out)
	{
		if (out.order() != ByteOrder.LITTLE_ENDIAN)
			throw new IllegalArgumentException("Writeable must be in Little-Ending byte order");
		this.out = out;
	}

	/**
	 * <p>getWriteable.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.bytebuffer.IBinaryWriteable} object.
	 */
	public IBinaryWriteable getWriteable() 
	{
		return out;
	}
	
	/**
	 * <p>getOutput.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.bytebuffer.IBinaryWriteable} object.
	 */
	public IBinaryWriteable getOutput()
	{
		return this.out;
	}

	/**
	 * <p>getEncoderType.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.encoding.EncoderMode} object.
	 */
	public EncoderMode getEncoderType()
	{
		return mode;
	}

	/**
	 * Set encoding mode. If Strict, null values cannot be encoded.
	 * If Slack then nulls are encoded with empty default values.
	 *
	 * @param type encoder type
	 */
	public void setEncoderMode(EncoderMode type) {
		mode = type;
	}

	private void assertNullOk(Object v) throws EncodingException
	{
		if (v!=null) return;
		if (mode == EncoderMode.Strict)
			throw new EncodingException("Cannot encode null value");
	}
	
	
	/**
	 * Assert array length is within restrictions
	 * @param len
	 * @throws EncodingException
	 */
	private void assertArrayLength(int len)
	throws EncodingException
	{
		int maxLen = ctx.getMaxArrayLength();
		if (maxLen>0 && len>maxLen) { 
			final EncodingException encodingException = new EncodingException(StatusCodes.Bad_EncodingLimitsExceeded, "MaxArrayLength "+maxLen+" < "+len);
			logger.warn("assertArrayLength: failed", encodingException);
			throw encodingException;
		}
	}
	/**
	 * Assert string length is within restrictions
	 * @param len
	 * @throws EncodingException
	 */
	private void assertStringLength(int len)
	throws EncodingException
	{
		int maxLen = ctx.getMaxStringLength();
		if (maxLen>0 && len>maxLen) {
			final EncodingException encodingException = new EncodingException(StatusCodes.Bad_EncodingLimitsExceeded, "MaxStringLength "+maxLen+" < "+len);
			logger.warn("assertStringLength: failed", encodingException);
			throw encodingException;
		}
	}
	
	/**
	 * Assert bytestring length is within restrictions
	 * @param len
	 * @throws EncodingException
	 */
	private void assertByteStringLength(int len)
	throws EncodingException
	{
		int maxLen = ctx.getMaxByteStringLength();
		if (maxLen>0 && len>maxLen) {
			final EncodingException encodingException = new EncodingException(StatusCodes.Bad_EncodingLimitsExceeded, "MaxByteStringLength "+maxLen+" < "+len);
			logger.warn("assertByteStringLength: failed", encodingException);
			throw encodingException;
		}
	}		
	
	private static EncodingException toEncodingException(IOException e) 
	{
		if (e instanceof ClosedChannelException)
			return new EncodingException(StatusCodes.Bad_ConnectionClosed, e);
		if (e instanceof EOFException)
			return new EncodingException(StatusCodes.Bad_EndOfStream, e);
		if (e instanceof ConnectException)
			return new EncodingException(StatusCodes.Bad_ConnectionRejected, e);
		if (e instanceof SocketException)
			return new EncodingException(StatusCodes.Bad_CommunicationError, e);
		return new EncodingException(StatusCodes.Bad_UnexpectedError, e);		
	}	
	
	/** {@inheritDoc} */
	public void putBoolean(String fieldName, Boolean v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				putSByte(null, 0);
			} else {
				out.put( v  ?  (byte)1  : (byte)0 );		
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}

	/**
	 * <p>putBooleanArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Boolean} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putBooleanArray(String fieldName, Boolean[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (Boolean o : v)
				putBoolean(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/**
	 * <p>putBooleanArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putBooleanArray(String fieldName, Collection<Boolean> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (Boolean o : v)
				putBoolean(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/**
	 * <p>putSByte.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.lang.Byte} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putSByte(String fieldName, Byte v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				putSByte(null, 0);
			}
			else out.put( v );
		} catch (IOException e) {
			throw toEncodingException(e);
		}			
	}
	
	/** {@inheritDoc} */
	public void putSByte(String fieldName, byte v)
    throws EncodingException	
	{
		try {
			out.put( v );		
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}

	/**
	 * <p>putSByte.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a int.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putSByte(String fieldName, int v)
    throws EncodingException	
	{
		try {
			out.put( (byte) v );		
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}

	/**
	 * <p>putSByteArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Byte} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putSByteArray(String fieldName, Byte[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (Byte o : v)
				putSByte(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/** {@inheritDoc} */
	public void putSByteArray(String fieldName, Collection<Byte> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (Byte o : v)
				putSByte(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/** {@inheritDoc} */
	public void putByte(String fieldName, UnsignedByte v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				putSByte(null, 0);
			} else {
				out.put( v.toByteBits() );				
			}	
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putByteArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.UnsignedByte} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putByteArray(String fieldName, UnsignedByte[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (UnsignedByte o : v)
				putByte(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}

	/** {@inheritDoc} */
	public void putByteArray(String fieldName, Collection<UnsignedByte> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (UnsignedByte o : v)
				putByte(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/** {@inheritDoc} */
	public void putInt16(String fieldName, Short v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putShort((short)0);
			} else {
				out.putShort( v );				
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}

	/**
	 * <p>putInt16.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a short.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putInt16(String fieldName, short v)
    throws EncodingException	
	{
		try {
			out.putShort( v );				
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putInt16Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Short} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putInt16Array(String fieldName, Short[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (Short o : v)
				putInt16(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	

	/** {@inheritDoc} */
	public void putInt16Array(String fieldName, Collection<Short> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (Short o : v)
				putInt16(null, o);			
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/** {@inheritDoc} */
	public void putUInt16(String fieldName, UnsignedShort v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putShort((short)0);
			} else {
				out.putShort( v.toShortBits() );		
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putUInt16Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.UnsignedShort} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putUInt16Array(String fieldName, UnsignedShort[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (UnsignedShort o : v)
				putUInt16(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}

	/**
	 * <p>putUInt16Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putUInt16Array(String fieldName, Collection<UnsignedShort> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (UnsignedShort o : v)
				putUInt16(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putInt32.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.lang.Integer} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putInt32(String fieldName, Integer v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putInt((int)0);
			} else {
				out.putInt( v );						
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/** {@inheritDoc} */
	public void putInt32(String fieldName, int v)
    throws EncodingException	
	{
		try {
			out.putInt( v );						
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putInt32Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of int.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putInt32Array(String fieldName, int[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (int o : v)
				putInt32(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/** {@inheritDoc} */
	public void putInt32Array(String fieldName, Collection<Integer> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());		
			out.putInt(v.size());
			for (int o : v)
				putInt32(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
			
	}	
	
	/**
	 * <p>putInt32Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Integer} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putInt32Array(String fieldName, Integer[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (Integer o : v)
				putInt32(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}			
	}
		
	/** {@inheritDoc} */
	public void putUInt32(String fieldName, UnsignedInteger v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putInt((int)0);
			} else {
				out.putInt( v.toIntBits() );								
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putUInt32Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putUInt32Array(String fieldName, UnsignedInteger[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (UnsignedInteger o : v)
				putUInt32(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
			
	}	
	
	/** {@inheritDoc} */
	public void putUInt32Array(String fieldName, Collection<UnsignedInteger> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (UnsignedInteger o : v)
				putUInt32(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/**
	 * <p>putInt64.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.lang.Long} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putInt64(String fieldName, Long v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putLong((long)0);
			} else {
				out.putLong( v.longValue() );
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/** {@inheritDoc} */
	public void putInt64(String fieldName, long v)
    throws EncodingException	
	{
		try {
			out.putLong( v );
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/**
	 * <p>putInt64Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Long} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putInt64Array(String fieldName, Long[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (Long o : v)
				putInt64(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putInt64Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putInt64Array(String fieldName, Collection<Long> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (Long o : v)
				putInt64(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/** {@inheritDoc} */
	public void putUInt64(String fieldName, UnsignedLong v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putLong((long)0);
			} else {
				out.putLong( v.toLongBits() );
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
		
	/**
	 * <p>putUInt64Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.UnsignedLong} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putUInt64Array(String fieldName, UnsignedLong[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (UnsignedLong o : v)
				putUInt64(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/**
	 * <p>putUInt64Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putUInt64Array(String fieldName, Collection<UnsignedLong> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (UnsignedLong o : v)
				putUInt64(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/**
	 * <p>putFloat.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.lang.Float} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putFloat(String fieldName, Float v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putFloat((long)0);
			} else {
				out.putFloat( v.floatValue() );		
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}			
	}
	
	/** {@inheritDoc} */
	public void putFloat(String fieldName, float v)
    throws EncodingException	
	{
		try {
			out.putFloat( v );
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putFloatArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Float} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putFloatArray(String fieldName, Float[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (Float o : v)
				putFloat(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}			
	}		
	
	/**
	 * <p>putFloatArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putFloatArray(String fieldName, Collection<Float> v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (Float o : v)
				putFloat(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}			
	}		
	
	/** {@inheritDoc} */
	public void putDouble(String fieldName, Double v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putDouble((long)0);
			} else {
				out.putDouble( v.doubleValue() );		
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}			
	}
	
	/**
	 * <p>putDouble.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a double.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putDouble(String fieldName, double v)
    throws EncodingException	
	{
		try {
			out.putDouble( v );
		} catch (IOException e) {
			throw toEncodingException(e);
		}			
	}
	
	/**
	 * <p>putDoubleArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Double} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putDoubleArray(String fieldName, Double[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (Double o : v)
				putDouble(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	

	/**
	 * <p>putDoubleArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putDoubleArray(String fieldName, Collection<Double> v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (Double o : v)
				putDouble(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/** {@inheritDoc} */
	public void putString(String fieldName, String v)
    throws EncodingException	
    {
		try {
			if (v==null) {
				assertNullOk(v);
				out.putInt(-1);
			} else {
				assertStringLength(v.length());
				final byte[] bytes = v.getBytes(UTF8);
				out.putInt(bytes.length);
				out.put(bytes);
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}

	/**
	 * <p>putStringArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putStringArray(String fieldName, Collection<String> v)
	throws EncodingException
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (String s : v)
				putString(null, s);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putStringArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.String} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putStringArray(String fieldName, String[] v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (String s : v)
				putString(null, s);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/** {@inheritDoc} */
	public void putDateTime(String fieldName, DateTime v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putLong(0);
			} else {
				if (v.compareTo(DateTime.MAX_VALUE) >= 0) {
					out.putLong(Long.MAX_VALUE);
				} else if (v.compareTo(DateTime.MIN_VALUE) <= 0) {
					out.putLong(0);
				} else {
					out.putLong(v.getValue());
				}
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
}

	/**
	 * <p>putDateTimeArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.DateTime} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putDateTimeArray(String fieldName, DateTime[] v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (DateTime o : v)
				putDateTime(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}			
	
	/** {@inheritDoc} */
	public void putDateTimeArray(String fieldName, Collection<DateTime> v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (DateTime o : v)
				putDateTime(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}			
	
	/** {@inheritDoc} */
	public void putGuid(String fieldName, UUID v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putLong(0);
				out.putLong(0);
			} else {	
				long hi = v.getMostSignificantBits();
				long lo = v.getLeastSignificantBits();
				
				byte[] uuidBytes = new byte[16];

				for (int i = 0; i < 8; i++)
					uuidBytes[i] = (byte) (hi >>> 8 * (7 - i));
				for (int i = 8; i < 16; i++)
					uuidBytes[i] = (byte) (lo >>> 8 * (7 - i));

				out.put(uuidBytes[3]);
				out.put(uuidBytes[2]);
				out.put(uuidBytes[1]);
				out.put(uuidBytes[0]);
				out.put(uuidBytes[5]);
				out.put(uuidBytes[4]);
				out.put(uuidBytes[7]);
				out.put(uuidBytes[6]);
				for (int i = 8; i < 16; i++)
					out.put(uuidBytes[i]);
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putGuidArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.util.UUID} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putGuidArray(String fieldName, UUID[] v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (UUID o : v)
				putGuid(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}			

	/** {@inheritDoc} */
	public void putGuidArray(String fieldName, Collection<UUID> v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (UUID o : v)
				putGuid(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}			
	
	
	   /**
     * <p>putByteString.</p>
     *
     * @param fieldName a {@link java.lang.String} object.
     * @param v an array of byte.
     * @throws org.opcfoundation.ua.encoding.EncodingException if any.
     */
    private void putByteString(String fieldName, byte[] v)
    throws EncodingException    
    {       
        try {
            if (v==null) out.putInt(-1);
            else {
                assertByteStringLength(v.length);
                out.putInt(v.length);
                out.put(v);
            }
        } catch (IOException e) {
            throw toEncodingException(e);
        }
    }
	
	
	/**
	 * <p>putByteString.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of byte.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	@Override
	public void putByteString(String fieldName, ByteString v)
    throws EncodingException	
	{		
	  putByteString(fieldName, ByteString.asByteArray(v));
	}
	
	/**
	 * <p>putByteStringArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of byte.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	@Override
	public void putByteStringArray(String fieldName, ByteString[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);		
			out.putInt(v.length);
			for (ByteString o : v)
				putByteString(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}			
	}				
	
	/** {@inheritDoc} */
	public void putByteStringArray(String fieldName, Collection<ByteString> v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (ByteString o : v)
				putByteString(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}				
	
	/** {@inheritDoc} */
	public void putXmlElement(String fieldName, XmlElement v)
    throws EncodingException	
	{
		try {
			if (v==null) out.putInt(-1);
			else {
				putByteString(fieldName, v.getData());
				/*
				if ( v.hasBinaryData() ) {
					putByteString(fieldName, v.getData());
				} else {
					putString(fieldName, v.getValue());
				}*/
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putXmlElementArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.XmlElement} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putXmlElementArray(String fieldName, XmlElement[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (XmlElement o : v)
				putXmlElement(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}			
	}	
	
	/** {@inheritDoc} */
	public void putXmlElementArray(String fieldName, Collection<XmlElement> v)
    throws EncodingException	
	{
		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (XmlElement o : v)
				putXmlElement(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/** {@inheritDoc} */
	public void putNodeId(String fieldName, NodeId v)
    throws EncodingException	
	{
		try {
//		if(v==null) {
//			out.put(NodeIdEncoding.TwoByte.getBits());
//			out.put(new UnsignedInteger(0).byteValue());
//			return;
//		}
			if (v==null) v = NodeId.NULL; 
		
//		if (v==null) {
//			this.putUInt16(null,new UnsignedShort(0));
//			return;
//		}
		
			Object o = v.getValue();
			if (v.getIdType() == IdType.Numeric) {
				UnsignedInteger i = (UnsignedInteger) o;
				if (i.compareTo(UnsignedByte.MAX_VALUE)<=0 && v.getNamespaceIndex()==0)
				{
					out.put(NodeIdEncoding.TwoByte.getBits());
					out.put(i.byteValue());
				} else 
				if (i.compareTo(UnsignedShort.MAX_VALUE)<=0 && v.getNamespaceIndex()<256)
				{
					out.put(NodeIdEncoding.FourByte.getBits());
					putSByte(null, v.getNamespaceIndex());
					out.putShort(i.shortValue());
				} else {
					out.put(NodeIdEncoding.Numeric.getBits());
					out.putShort((short)v.getNamespaceIndex());
					out.putInt(i.intValue());
				}
			} else
			
			if (v.getIdType() == IdType.String) {
				out.put(NodeIdEncoding.String.getBits());
				out.putShort((short)v.getNamespaceIndex());
				putString(null, (String)v.getValue());
			} else
			if (v.getIdType() == IdType.Opaque) {
				out.put(NodeIdEncoding.ByteString.getBits());
				out.putShort((short)v.getNamespaceIndex());
				putByteString(null, (ByteString) v.getValue());
			} else 
		
			if (v.getIdType() == IdType.Guid) {
				UUID id = (UUID) v.getValue();
				out.put(NodeIdEncoding.Guid.getBits());
				out.putShort((short)v.getNamespaceIndex());
				putGuid(null, id);
			}		
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putNodeIdArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.NodeId} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putNodeIdArray(String fieldName, NodeId[] v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (NodeId o : v)
				putNodeId(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/**
	 * <p>putNodeIdArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putNodeIdArray(String fieldName, Collection<NodeId> v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (NodeId o : v)
				putNodeId(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/** {@inheritDoc} */
	public void putExpandedNodeId(String fieldName, ExpandedNodeId v)
    throws EncodingException	
	{
		try {
			if (v==null) v = ExpandedNodeId.NULL;
//		if (v==null) { //TODO does this work on the Decoder side, added 6.5.2009
//			putUInt16(null, new UnsignedShort(0));
//			return;
//		}
		
			byte upperBits = 0;
			if (v.getNamespaceUri()!=null) upperBits |= 0x80;
			if (v.getServerIndex()!=null) upperBits |= 0x40;
		
			Object o = v.getValue();
			if (v.getIdType() == IdType.Numeric) {
				UnsignedInteger i = (UnsignedInteger) o;
				if (i.compareTo(UnsignedByte.MAX_VALUE)<=0 && v.getNamespaceIndex()==0)
				{
					putSByte( null, (NodeIdEncoding.TwoByte.getBits() | upperBits));
					out.put(i.byteValue());
				} else 
				if (i.compareTo(UnsignedShort.MAX_VALUE)<=0 && v.getNamespaceIndex()<256)
				{
					putSByte( null, (NodeIdEncoding.FourByte.getBits() | upperBits));
					putSByte( null, v.getNamespaceIndex());
					out.putShort(i.shortValue());
				} else {
					putSByte( null, (NodeIdEncoding.Numeric.getBits() | upperBits));
					out.putShort((short)v.getNamespaceIndex());
					out.putInt(i.intValue());
				}
			}
			
			if (v.getIdType() == IdType.String) {
				putSByte( null, (NodeIdEncoding.String.getBits() | upperBits));
				out.putShort((short)v.getNamespaceIndex());
				putString(null, (String)v.getValue());
			} else
			
			if (v.getIdType() == IdType.Opaque) {
				putSByte( null, (NodeIdEncoding.ByteString.getBits() | upperBits));
				out.putShort((short)v.getNamespaceIndex());
				putByteString(null, (ByteString) v.getValue());
			} else 
			
			if (v.getIdType() == IdType.Guid) {
				putSByte( null, (NodeIdEncoding.Guid.getBits() | upperBits));
				out.putShort((short)v.getNamespaceIndex());
				putGuid( null, (UUID) v.getValue() );
			}		
		
			if (v.getNamespaceUri()!=null) {
				putString( null, v.getNamespaceUri());
			}
			if (v.getServerIndex()!=null) {
				out.putInt(v.getServerIndex().intValue());
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putExpandedNodeIdArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putExpandedNodeIdArray(String fieldName, ExpandedNodeId[] v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (ExpandedNodeId o : v)
				putExpandedNodeId(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/**
	 * <p>putExpandedNodeIdArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putExpandedNodeIdArray(String fieldName, Collection<ExpandedNodeId> v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (ExpandedNodeId o : v)
				putExpandedNodeId(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
			
	}		
	
	/** {@inheritDoc} */
	public void putStatusCode(String fieldName, StatusCode v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				assertNullOk(v);
				out.putInt(StatusCode.GOOD.getValueAsIntBits());
			} else {
				out.putInt(v.getValueAsIntBits());
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putStatusCodeArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.StatusCode} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putStatusCodeArray(String fieldName, StatusCode[] v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (StatusCode o : v)
				putStatusCode(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/**
	 * <p>putStatusCodeArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putStatusCodeArray(String fieldName, Collection<StatusCode> v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (StatusCode o : v)
				putStatusCode(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/** {@inheritDoc} */
	public void putQualifiedName(String fieldName, QualifiedName v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putShort((short)0);
				putString(null, null);
				return;
			}
			out.putShort((short)v.getNamespaceIndex());
			putString(null, v.getName());
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putQualifiedNameArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.QualifiedName} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putQualifiedNameArray(String fieldName, QualifiedName[] v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (QualifiedName o : v)
				putQualifiedName(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/**
	 * <p>putQualifiedNameArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putQualifiedNameArray(String fieldName, Collection<QualifiedName> v)
    throws EncodingException	
	{		
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (QualifiedName o : v)
				putQualifiedName(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/** {@inheritDoc} */
	public void putLocalizedText(String fieldName, LocalizedText v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				putSByte(null,  0);
				return;
			}
			String locale = v.getLocaleId();
			String text = v.getText();
			byte encodingMask = (byte) ((locale!=null ? 1 : 0) | (text!=null ? 2 : 0));
			out.put(encodingMask);
			if (locale!=null) putString(null, locale);
			if (text!=null) putString(null, text);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * <p>putLocalizedTextArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.LocalizedText} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putLocalizedTextArray(String fieldName, LocalizedText[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (LocalizedText o : v)
				putLocalizedText(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/** {@inheritDoc} */
	public void putLocalizedTextArray(String fieldName, Collection<LocalizedText> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (LocalizedText o : v)
				putLocalizedText(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	

	/** {@inheritDoc} */
	public void putStructure(String fieldName, Structure v)
    throws EncodingException	
	{
		if (v==null) {
			assertNullOk(v);
			putNodeId(null, NodeId.ZERO); // Will throw error.
			putSByte(null, 0);
			putInt32(null, -1);
			return;
		}

		try {
			putNodeId(null, ctx.getNamespaceTable().toNodeId(v.getBinaryEncodeId()));
		} catch (ServiceResultException e) {
			throw new EncodingException("Could not get BinaryEncodeId for given Structure", e);
		}
		putSByte(null, 1);
		
		EncoderCalc calc = new EncoderCalc();
		calc.setEncoderContext( getEncoderContext() );
		calc.putEncodeable(null, v);
		int len = calc.getLength();
		
		putInt32(null, len);
		putEncodeable(null, v);
	}

	/**
	 * <p>putStructureArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.Structure} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putStructureArray(String fieldName, Structure[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (Structure o : v)
				putStructure(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}

	/**
	 * <p>putStructureArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putStructureArray(String fieldName, Collection<Structure> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (Structure o : v)
				putStructure(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/** {@inheritDoc} */
	public void putExtensionObject(String fieldName, ExtensionObject v)
    throws EncodingException	
	{
		if (v==null) {
			assertNullOk(v);
			putNodeId(null, null);  
			putSByte(null, 0);
			return;
		}
		
		//support lazy encoding
		if(!v.isEncoded()){
		  putExtensionObject(fieldName, ExtensionObject.binaryEncode((Structure) v.getObject(), ctx));
		  return;
		}
		
		putNodeId(null, ctx.toNodeId(v.getTypeId()));
		Object o = v.getObject();		
		if (o==null) {
			putSByte(null, 0);
		} else if (v.getEncodeType() == EncodeType.Binary) {			
			putSByte(null, 1);
			putByteString(null, (byte[])o);
		} else if (v.getEncodeType() == EncodeType.Xml) {
			putSByte(null, 2);
			putXmlElement(null, (XmlElement)o);
		} else {
			// Unexpected state
			throw new EncodingException("Unexpected object "+v.getEncodeType());
		}
	}
	
	/**
	 * <p>putExtensionObjectArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.ExtensionObject} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putExtensionObjectArray(String fieldName, ExtensionObject[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (ExtensionObject o : v)
				putExtensionObject(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/** {@inheritDoc} */
	public void putExtensionObjectArray(String fieldName, Collection<ExtensionObject> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (ExtensionObject o : v)
				putExtensionObject(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/** {@inheritDoc} */
	public void putDataValue(String fieldName, DataValue v)
    throws EncodingException	
	{
		if (v==null) {
			putSByte(null, 0);
			return;
		}
		int mask = 0;
		if (v.getValue()!=null) mask |= 1;		
		if (v.getStatusCode()!=null && !v.getStatusCode().equals(StatusCode.GOOD)) mask |= 2;
		if (v.getSourceTimestamp()!=null && !v.getSourceTimestamp().equals(DateTime.MIN_VALUE)) mask |= 4; 
		if (v.getServerTimestamp()!=null && !v.getServerTimestamp().equals(DateTime.MIN_VALUE)) mask |= 8;		
		if (v.getSourcePicoseconds()!=null && !v.getSourcePicoseconds().equals(UnsignedShort.MIN_VALUE)) mask |= 0x10;
		if (v.getServerPicoseconds()!=null && !v.getServerPicoseconds().equals(UnsignedShort.MIN_VALUE)) mask |= 0x20;
		
		putSByte(null, mask);
		if ((mask & 1) == 1) putVariant(null, v.getValue());
		if ((mask & 2) == 2) putStatusCode(null, v.getStatusCode());
		if ((mask & 4) == 4) putDateTime(null, v.getSourceTimestamp());
		if ((mask & 8) == 8) putDateTime(null, v.getServerTimestamp());
		if ((mask & 0x10) == 0x10) putUInt16(null, v.getSourcePicoseconds());
		if ((mask & 0x20) == 0x20) putUInt16(null, v.getServerPicoseconds());
	}
	
	/**
	 * <p>putDataValueArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.DataValue} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putDataValueArray(String fieldName, DataValue[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (DataValue o : v)
				putDataValue(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/** {@inheritDoc} */
	public void putDataValueArray(String fieldName, Collection<DataValue> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (DataValue o : v)
				putDataValue(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		
	
	/** {@inheritDoc} */
	public void putVariant(String fieldName, Variant v)
    throws EncodingException	
	{
		if (v==null) {
			assertNullOk(v);
			putSByte(null, 0);  
			return;
		}
		
		Object o = v.getValue();
		if (o==null) {
			putSByte(null, 0);
			return;
		}		

		Class<?> compositeClass			= v.getCompositeClass();
		int builtinType;
		if (Structure.class.isAssignableFrom(compositeClass))
			builtinType = 22;
		else
			builtinType = BuiltinsMap.ID_MAP.get(compositeClass);
		
		// Scalar
		if (!v.isArray()) {
			putSByte(null, builtinType);
			putScalar(null, builtinType, o);
			return;
		} 
		
		// Array
		int dim = v.getDimension();
		if (dim==1) {
			putSByte( null, (builtinType | 0x80));
			putArray(null, builtinType, o);
			return;
		}
		
		// Multi-dimension array
		int dims[] = v.getArrayDimensions();
		int len = MultiDimensionArrayUtils.getLength(dims);
		Iterator<Object> i = MultiDimensionArrayUtils.arrayIterator(v.getValue(), v.getArrayDimensions());
		try {
			putSByte( null, (builtinType | 0xC0));
			out.putInt(len);
			while (i.hasNext()) 
				putScalar(null, builtinType, i.next());				
			putInt32Array(null, dims);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new EncodingException("The dimensions of inner array elements of a multi-dimension variable must be equal in length", e);
		} catch (IOException e) {
			throw toEncodingException(e);
		}		
	}	
	
	/**
	 * <p>putVariantArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.Variant} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putVariantArray(String fieldName, Variant[] v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (Variant o : v)
				putVariant(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		

	/**
	 * <p>putVariantArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putVariantArray(String fieldName, Collection<Variant> v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (Variant o : v)
				putVariant(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}		

	/**
	 * <p>putDiagnosticInfoArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.DiagnosticInfo} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putDiagnosticInfoArray(String fieldName, DiagnosticInfo[] v)
    throws EncodingException
    {
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.length);
			out.putInt(v.length);
			for (DiagnosticInfo o : v)
				putDiagnosticInfo(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
    }
	
	/** {@inheritDoc} */
	public void putDiagnosticInfoArray(String fieldName, Collection<DiagnosticInfo> v)
    throws EncodingException
    {
		try {
			if (v==null) {
				out.putInt(-1);
				return;
			}
		
			assertArrayLength(v.size());
			out.putInt(v.size());
			for (DiagnosticInfo o : v)
				putDiagnosticInfo(null, o);
		} catch (IOException e) {
			throw toEncodingException(e);
		}
    }
	
	/** {@inheritDoc} */
	public void putDiagnosticInfo(String fieldName, DiagnosticInfo v)
    throws EncodingException	
	{
		if (v==null) {
			putSByte(null, 0);
			return;
		}

		int mask = 0;
		if (v.getSymbolicId()!=null) mask |= 1;
		if (v.getNamespaceUriStr()!=null) mask |= 2;
		if (v.getLocalizedTextStr()!=null) mask |= 4;
		if (v.getLocaleStr()!=null) mask |= 8;
		if (v.getAdditionalInfo()!=null) mask |= 0x10;
		if (v.getInnerStatusCode()!=null) mask |= 0x20;
		if (v.getInnerDiagnosticInfo()!=null) mask |= 0x40;		
				
		try {
			putSByte(null, mask);
			if ((mask & 1) == 1) 
				out.putInt( v.getSymbolicId() );
		
			if ((mask & 2) == 2) 
				out.putInt( v.getNamespaceUri() );
		
			if ((mask & 4) == 4) 
				out.putInt(	v.getLocalizedText() );
		
			if ((mask & 8) == 8) 
				out.putInt(	v.getLocale() );
		
			if ((mask & 0x10) == 0x10) 
				putString(null, v.getAdditionalInfo());			
		
			if ((mask & 0x20) == 0x20) 
				putStatusCode(null, v.getInnerStatusCode());
		
			if ((mask & 0x40) == 0x40) 
				putDiagnosticInfo(null, v.getInnerDiagnosticInfo());
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}	
	
	/** {@inheritDoc} */
	public void putEnumerationArray(String fieldName, Object array)
    throws EncodingException	
	{
		try {
			if (array==null) {
				out.putInt(-1);
				return;
			}
			int length = Array.getLength(array);
			assertArrayLength(length);
			out.putInt(length);
			for (int i=0; i<length; i++)
				putEnumeration(null, (Enumeration)Array.get(array, i));
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/** {@inheritDoc} */
	public void putEnumeration(String fieldName, Enumeration v)
    throws EncodingException	
	{
		try {
			if (v==null) {
				out.putInt(0);
				//	throw new EncodingException("Cannot encode null value");
				return;
			}
			out.putInt(	v.getValue() );
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}

	/** {@inheritDoc} */
	public void putObject(String fieldName, Object o)
    throws EncodingException	
	{
		if (o==null) throw new EncodingException("Cannot encode null value");
		Class<?> c = o.getClass();
		putObject(null, c, o);
	}
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public void putObject(String fieldName, Class<?> c, Object o)
    throws EncodingException	
	{
		Integer bt = BuiltinsMap.ID_MAP.get(c);
		boolean array = c.isArray();
		if (bt!=null) {
			if (array) 
				putArray(null, bt, o);
			else
				putScalar(null, bt, o);
			return;
		} 
		
		if (!array && Enumeration.class.isAssignableFrom(c)) {
			putEnumeration(null, (Enumeration)o);
			return;
		}

		if (array && Enumeration.class.isAssignableFrom(c.getComponentType())) {
			putEnumerationArray(null, o);
			return;
		}
		
		Class<?> scalarClass = array ? c.getComponentType() : c; 
		if (array)
			putEncodeableArray(null, (Class<? extends IEncodeable>)scalarClass, o);
		else
			ctx.getEncodeableSerializer().putEncodeable((Class<? extends IEncodeable>) c, (IEncodeable)o, this);
		return;
	}
	
	/** {@inheritDoc} */
	public void putScalar(String fieldName, int builtinType, Object o)
    throws EncodingException	
	{
		switch (builtinType) {
		case 1: putBoolean(null, (Boolean) o); break;
		case 2: putSByte(null, (Byte) o); break;
		case 3: putByte(null, (UnsignedByte) o); break;
		case 4: putInt16(null, (Short) o); break;
		case 5: putUInt16(null, (UnsignedShort) o); break;
		case 6: putInt32(null, (Integer) o); break;
		case 7: putUInt32(null, (UnsignedInteger) o); break;
		case 8: putInt64(null, (Long) o); break;
		case 9: putUInt64(null, (UnsignedLong) o); break;
		case 10: putFloat(null, (Float) o); break;
		case 11: putDouble(null, (Double) o); break;
		case 12: putString(null, (String) o); break;
		case 13: putDateTime(null, (DateTime) o); break;
		case 14: putGuid(null, (UUID) o); break;
		case 15: putByteString(null, (ByteString) o); break;
		case 16: putXmlElement(null, (XmlElement) o); break;
		case 17: putNodeId(null, (NodeId) o); break;
		case 18: putExpandedNodeId(null, (ExpandedNodeId) o); break;
		case 19: putStatusCode(null, (StatusCode) o); break;
		case 20: putQualifiedName(null, (QualifiedName) o); break;
		case 21: putLocalizedText(null, (LocalizedText) o); break;
		case 22: {
			if (o instanceof Structure) 
				putStructure(null, (Structure) o); 
			else
				putExtensionObject(null, (ExtensionObject) o); 
			break;
		}		
		case 23: putDataValue(null, (DataValue) o); break;
		case 24: putVariant(null, (Variant) o); break;
		case 25: putDiagnosticInfo(null, (DiagnosticInfo) o); break;
		default: throw new EncodingException("cannot encode builtin type "+builtinType); 
		}
	}
	
	/** {@inheritDoc} */
	public void putArray(String fieldName, int builtinType, Object o)
    throws EncodingException	
	{
		switch (builtinType) {
		case 1: putBooleanArray(null, (Boolean[]) o); break;
		case 2: putSByteArray(null, (Byte[]) o); break;
		case 3: putByteArray(null, (UnsignedByte[]) o); break;
		case 4: putInt16Array(null, (Short[]) o); break;
		case 5: putUInt16Array(null, (UnsignedShort[]) o); break;
		case 6: putInt32Array(null, (Integer[]) o); break;
		case 7: putUInt32Array(null, (UnsignedInteger[]) o); break;
		case 8: putInt64Array(null, (Long[]) o); break;
		case 9: putUInt64Array(null, (UnsignedLong[]) o); break;
		case 10: putFloatArray(null, (Float[]) o); break;
		case 11: putDoubleArray(null, (Double[]) o); break;
		case 12: putStringArray(null, (String[]) o); break;
		case 13: putDateTimeArray(null, (DateTime[]) o); break;
		case 14: putGuidArray(null, (UUID[]) o); break;
		case 15: putByteStringArray(null, (ByteString[]) o); break;
		case 16: putXmlElementArray(null, (XmlElement[]) o); break;
		case 17: putNodeIdArray(null, (NodeId[]) o); break;
		case 18: putExpandedNodeIdArray(null, (ExpandedNodeId[]) o); break;
		case 19: putStatusCodeArray(null, (StatusCode[]) o); break;
		case 20: putQualifiedNameArray(null, (QualifiedName[]) o); break;
		case 21: putLocalizedTextArray(null, (LocalizedText[]) o); break;
		case 22: {
			if (o instanceof ExtensionObject[]) 
				putExtensionObjectArray(null, (ExtensionObject[]) o); 
			else if (o instanceof Structure[])
				putStructureArray(null, (Structure[]) o);
			else throw new EncodingException("cannot encode "+o);
			break;
		}		case 23: putDataValueArray(null, (DataValue[]) o); break;
		case 24: putVariantArray(null, (Variant[]) o); break;
		case 25: putDiagnosticInfoArray(null, (DiagnosticInfo[]) o); break;
		default: throw new EncodingException("cannot encode builtin type "+builtinType); 
		}
	}	
	
	/** {@inheritDoc} */
	public void putEncodeableArray(String fieldName, Class<? extends IEncodeable> clazz, Object array)
    throws EncodingException	
	{
		try {
			if (array==null) {
				out.putInt(-1);
				return;
			}
			int length = Array.getLength(array);
			assertArrayLength(length);
			out.putInt(length);
			for (int i=0; i<length; i++) {
				ctx.getEncodeableSerializer().putEncodeable(clazz, (IEncodeable)Array.get(array, i), this);
			}
		} catch (IOException e) {
			throw toEncodingException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Encodes stucture without header
	 */
	public void putEncodeable(String fieldName, IEncodeable s)
    throws EncodingException	
	{		
		Class<? extends IEncodeable> clazz  = s.getClass();
		ctx.getEncodeableSerializer().putEncodeable(clazz, s, this);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Encodes stucture without header
	 */
	public void putEncodeable(String fieldName, Class<? extends IEncodeable> clazz, IEncodeable s)
    throws EncodingException	
	{		
		ctx.getEncodeableSerializer().putEncodeable(clazz, s, this);
	}	

//	/**
//	 * Encodes structures including header (typeId, encoding type and length)
//	 * @param s
//	 */	
//	@SuppressWarnings("unchecked")
//	public void putStructure(IEncodeable s)
//    throws EncodingException	
//	{
//		Class<IEncodeable> clazz = (Class<IEncodeable>) s.getClass();
//		if (!encodeableSerializer.getEncodeSet().contains(s))
//			throw new EncodingException("Cannot Encode "+clazz);
//
//		EncoderCalc calc = new EncoderCalc();
//		calc.setEncodeableTable(encodeableTable);
//		calc.putEncodeable(s);
//		
//		putNodeId(si.binaryId);
//		//putSByte(1); // Binary encoding
//		putInt32(calc.getLength());
//		putEncodeable(s, si);
//	}
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public void putMessage(IEncodeable s)
    throws EncodingException	
	{
		Class<IEncodeable> clazz = (Class<IEncodeable>) s.getClass();
		try {
			putNodeId(null, ctx.getEncodeableNodeId(clazz, EncodeType.Binary));
		} catch (ServiceResultException e) {
			e.printStackTrace();
		}
		ctx.getEncodeableSerializer().putEncodeable(clazz, s, this);
	}

	/** {@inheritDoc} */
	@Override
	public void put(String fieldName, Object o) throws EncodingException {
		EncoderUtils.put(this, fieldName, o);
	}

	/** {@inheritDoc} */
	@Override
	public void put(String fieldName, Object o, Class<?> clazz) throws EncodingException {
		EncoderUtils.put(this, fieldName, o, clazz);
	}

	
}
