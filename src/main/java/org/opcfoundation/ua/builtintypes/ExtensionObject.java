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

package org.opcfoundation.ua.builtintypes;

import java.util.Arrays;

import org.opcfoundation.ua.common.NamespaceTable;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncodeType;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.EncoderMode;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.binary.BinaryDecoder;
import org.opcfoundation.ua.encoding.binary.BinaryEncoder;
import org.opcfoundation.ua.encoding.binary.EncoderCalc;
import org.opcfoundation.ua.encoding.binary.IEncodeableSerializer;
import org.opcfoundation.ua.encoding.xml.XmlDecoder;
import org.opcfoundation.ua.utils.StackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Extension object contains a {@link Structure} which is either 
 * XML or binary encoded. 
 */
public class ExtensionObject {

	private static Logger logger = LoggerFactory.getLogger(ExtensionObject.class);
	
	/**
	 * Create extension object by encoding an encodeable to the defined encoding
	 * using the default serializer.
	 * 
	 * @param encodeable the objects to encode
	 * @param encodingType the requested encoding type either QualifiedName.DEFAULT_BINARY_ENCODING or QualifiedName.DEFAULT_XML_ENCODING
	 * @param serializer the serializer to use (default is {@link StackUtils#getDefaultSerializer()})
	 * @return the encodeable as an ExtensionObject
	 * @throws EncodingException if the encodingType is unsupported or the encoding fails
	 */
	public static ExtensionObject encode(
			Structure encodeable,
			QualifiedName encodingType, IEncodeableSerializer serializer, EncoderContext ctx) throws EncodingException {
		if (encodeable == null)
			return null;
		if (encodingType.equals(QualifiedName.DEFAULT_BINARY_ENCODING))
				return ExtensionObject.binaryEncode(encodeable, serializer, ctx);

		if (encodingType.equals(QualifiedName.DEFAULT_XML_ENCODING))
			return ExtensionObject.xmlEncode(encodeable, serializer);
		throw new EncodingException(StatusCodes.Bad_DataEncodingUnsupported);
	}
	

	/**
	 * Create extension object by encoding an encodeable to the defined encoding
	 * using {@link StackUtils#getDefaultSerializer() the default serializer}.
	 * 
	 * @param encodeable the objects to encode
	 * @param encodingType the requested encoding type either QualifiedName.DEFAULT_BINARY_ENCODING or QualifiedName.DEFAULT_XML_ENCODING
	 * @return the encodeable as an ExtensionObject
	 * @throws EncodingException if the encodingType is unsupported or the encoding fails
	 */
	public static ExtensionObject encode(
			Structure encodeable,
			QualifiedName encodingType, EncoderContext ctx) throws EncodingException {
		return encode(encodeable, encodingType, StackUtils.getDefaultSerializer(), ctx);
	}
	
	/**
	 * Create extension object by encoding an encodeable to a binary format 
	 * using the default serializer.
	 * 
	 * @param encodeable encodeable
	 * @return binary encoded encodeable
	 * @throws EncodingException on encoding problem
	 */
	public static ExtensionObject binaryEncode(Structure encodeable, EncoderContext ctx)
	throws EncodingException
	{
		return binaryEncode( encodeable, StackUtils.getDefaultSerializer(), ctx);
	}
	
	
	
	/**
	 * Create extension object by encoding an encodeable to a binary format
	 * 
	 * @param encodeable encodeable
	 * @param serializer serializer
	 * @return binary encoded encodeable
	 * @throws EncodingException on encoding problem
	 */
	public static ExtensionObject binaryEncode(Structure encodeable, IEncodeableSerializer serializer, EncoderContext ctx)
	throws EncodingException
	{
		ctx.setEncodeableSerializer(serializer);			
		EncoderCalc calc = new EncoderCalc();
		calc.setEncoderContext(ctx);
		serializer.calcEncodeable(encodeable.getClass(), encodeable, calc);
		byte[] data = new byte[calc.getLength()];
		BinaryEncoder enc = new BinaryEncoder(data);
		enc.setEncoderMode(EncoderMode.NonStrict);
		enc.setEncoderContext(ctx);
		enc.putEncodeable(null, encodeable);
		return new ExtensionObject(encodeable.getBinaryEncodeId(), data);
	}
	
	/**
	 * Create extension object by encoding an encodeable to xml format using
	 * the default serializer
	 * 
	 * @param encodeable encodeable
	 * @return xml encoded encodeable
	 * @throws EncodingException on encoding problem. Currently always, since the encoding is not supported.
	 */
	public static ExtensionObject xmlEncode(Structure encodeable)
	throws EncodingException
	{
		throw new EncodingException(StatusCodes.Bad_DataEncodingUnsupported);
	}	
	
	/**
	 * Create extension object by encoding an encodeable to xml format
	 * 
	 * @param encodeable encodeable
	 * @param serializer serializer
	 * @return xml encoded encodeable
	 * @throws EncodingException on encoding problem. Currently always, since the encoding is not supported.
	 */
	public static ExtensionObject xmlEncode(Structure encodeable, IEncodeableSerializer serializer)
	throws EncodingException
	{
		throw new EncodingException(StatusCodes.Bad_DataEncodingUnsupported);
	}		
	
	Object object;
	ExpandedNodeId typeId; // NodeId of a DataType
	Integer hash;
	EncodeType encodeType;

	public ExtensionObject(ExpandedNodeId typeId) {
		if (typeId==null)
			throw new IllegalArgumentException("typeId argument must not be null");
		this.typeId = typeId;
	}
	
	public ExtensionObject(ExpandedNodeId typeId, byte[] object) {
		if (typeId==null)
			throw new IllegalArgumentException("typeId argument must not be null");
		this.typeId = typeId;
		if (object!=null){
			this.object = object;
			this.encodeType = EncodeType.Binary;
		}else{
			//throw new IllegalArgumentException("object argument must not be null");
		}
	}

	public ExtensionObject(ExpandedNodeId typeId, XmlElement object) {
		if (typeId==null)
			throw new IllegalArgumentException("typeId argument must not be null");
		if (object==null){
			//throw new IllegalArgumentException("object argument must not be null");
			this.object = new XmlElement("");
		} else {
			this.object = object;
		}
		this.typeId = typeId;
//		this.object = object;
		this.encodeType = EncodeType.Xml;
		
//		if (typeId==null)
//			throw new IllegalArgumentException("typeId argument must not be null");
//		if (object==null)
//			throw new IllegalArgumentException("object argument must not be null");
//		this.typeId = typeId;
//		this.object = object;
//		this.encodeType = EncodeType.Xml;
	}
	
	public EncodeType getEncodeType() {
		return encodeType;
	}
	
	public Object getObject() {
		return object;
	}

	public ExpandedNodeId getTypeId() {
		return typeId;
	}
	
	/**
	 * Decode the extension object 
	 * 
	 * @param serializer serializer to use
	 * @param <T>
	 * @return decoded object
	 * @throws DecodingException
	 */
	@SuppressWarnings("unchecked")
	public <T extends IEncodeable> T decode(IEncodeableSerializer serializer, EncoderContext ctx,
			NamespaceTable namespaceTable)
	throws DecodingException {
		if (object==null)
		{
			Class<? extends IEncodeable> clazz = serializer.getClass(typeId);
			try {
				return (T) clazz.newInstance();
			} catch (InstantiationException e) {
				// Unexpected
				throw new DecodingException(e);
			} catch (IllegalAccessException e) {
				// Unexpected
				throw new DecodingException(e);
			}
		}
		
		if (object instanceof XmlElement) 
		{
			Class<? extends IEncodeable> clazz = serializer.getClass(typeId);
			logger.debug("decode: typeId={} class={}", typeId, clazz);
			if (clazz == null)
				throw new DecodingException("No serializer defined for class " + typeId);
			ctx.setEncodeableSerializer(serializer);
			XmlDecoder dec = new XmlDecoder((XmlElement) object, ctx);
			T result;
			try {
				dec.setNamespaceTable(namespaceTable);
				boolean inElement = dec.peek(clazz.getSimpleName());
				if (inElement)
					dec.getStartElement();
				result = (T) serializer.getEncodeable(clazz, dec);
				if (inElement)
					dec.getEndElement();
			} finally {
				// TODO Auto-generated catch block
				dec.close();
			}
			return result;
		}

		if (object instanceof byte[])
		{
			Class<? extends IEncodeable> clazz = serializer.getClass(typeId);
			ctx.setEncodeableSerializer(serializer);
			BinaryDecoder dec = new BinaryDecoder((byte[])object);
			dec.setEncoderContext(ctx);
			return (T) serializer.getEncodeable(clazz, dec);
		}

		throw new Error("unexpected");
	}
	
	/**
	 * Attempts to decode the extension object using the default serializer of the stack.
	 * 
	 * @param <T>
	 * @return decoded object
	 * @throws DecodingException
	 */
	public <T extends IEncodeable> T decode(EncoderContext ctx)
	throws DecodingException {
		return decode(ctx, null);
	}

	@SuppressWarnings("unchecked")
	public <T extends IEncodeable> T decode(EncoderContext ctx,
			NamespaceTable namespaceTable)
	throws DecodingException {
		return (T) decode(ctx.getEncodeableSerializer(), ctx, namespaceTable);
	}

	@Override
	public synchronized int hashCode() {
		if (hash==null) {
			if (object!=null && object instanceof byte[])
				hash = typeId.hashCode() + 13 * Arrays.hashCode((byte[])object); 
			else if (object!=null && object instanceof XmlElement)
				hash = typeId.hashCode() + 13 * ((XmlElement)object).hashCode();
			else 
				hash = typeId.hashCode();
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ExtensionObject))
			return false;
		ExtensionObject other = (ExtensionObject) obj;
		if (!other.typeId.equals(typeId)) return false;
		
		if (object==null) {
			return other.object==null;
		}
		
		if (object instanceof byte[]) {
			if (!(other.object instanceof byte[])) return false;
			return Arrays.equals((byte[])other.object, (byte[])object);
		}
		
		if (object instanceof XmlElement) {
			if (!(other.object instanceof XmlElement)) return false;
			return ((XmlElement)other.object).equals((XmlElement)object);
		}
		return false;
	}





	
}
