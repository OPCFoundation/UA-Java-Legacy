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

package org.opcfoundation.ua.encoding.utils;

import java.util.Collection;

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.common.NamespaceTable;
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncodeType;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IDecoder;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.IEncoder;
import org.opcfoundation.ua.encoding.binary.IEncodeableSerializer;

/**
 * Base for a simple serializer that can serialize only one type of class. Extending this class via non-code-generating ways should be avoided.
 */
public abstract class AbstractSerializer implements IEncodeableSerializer {

	/**
	 * Fixes standard namespace identifiers to have the uri for mapping purposes. 
	 * If given other namespace index identifiers, throw {@link IllegalArgumentException}.
	 * Additionally throw if given non-local ids.
	 * If given null, return null (as some encodings might be optional).
	 */
	private static ExpandedNodeId fixAndValidateId(ExpandedNodeId id) {
		if(id == null) {
			return null;
		}
		
		if(!id.isLocal()) {
			throw new IllegalArgumentException("Only ExpandedNodeIds that refer the local server are allowed as parameters");
		}
		
		//Check if the uri already exists
		if(id.getNamespaceUri() != null && !id.getNamespaceUri().isEmpty()) {
			return id;
		}
		//if we reach this point the uri is null or empty
		if(id.getNamespaceIndex() == 0) {
			//Standard namespace, fix the uri
			return new ExpandedNodeId(NamespaceTable.OPCUA_NAMESPACE, id.getValue());			
		}
		
		//Otherwise the given parameter only contains unknown index.
		throw new IllegalArgumentException("Only ExpandedNodeIds that contain the URI are allowed as parameter");
	}
	
	Class<? extends IEncodeable> clazz;

	ExpandedNodeId nodeId;
	ExpandedNodeId binaryId;
	ExpandedNodeId xmlId;
	ExpandedNodeId jsonId;
	
	/**
	 * Creates new {@link AbstractSerializer}. This will call {@link #AbstractSerializer(Class, ExpandedNodeId, ExpandedNodeId, ExpandedNodeId)} with type nodeId null.
	 * 
	 * @param clazz Structure to serialize
	 * @param binaryId binary id for the serialization, shall not be null
	 * @param xmlId xml id for the serialization, can be null
	 */
	public AbstractSerializer(Class<? extends IEncodeable> clazz, ExpandedNodeId binaryId, ExpandedNodeId xmlId){
		this(clazz, binaryId, xmlId, null);
	}
	
	/**
	 * Creates new {@link AbstractSerializer}. This will call {@link #AbstractSerializer(Class, ExpandedNodeId, ExpandedNodeId, ExpandedNodeId, ExpandedNodeId)} with jsonId  null.
	 * 
	 * @param clazz Structure to serialize
	 * @param binaryId binary id for the serialization, shall not be null
	 * @param xmlId xml id for the serialization, can be null
	 * @param nodeId node id of the DataType node of the Structure, can be null
	 */
	public AbstractSerializer(Class<? extends IEncodeable> clazz, ExpandedNodeId binaryId, ExpandedNodeId xmlId, ExpandedNodeId nodeId){
		this(clazz, binaryId, xmlId, nodeId, null);
	}
	
	/**
	 * Creates new {@link AbstractSerializer}.
	 * 
	 * @param clazz Structure to serialize
	 * @param binaryId binary id for the serialization, shall not be null
	 * @param xmlId xml id for the serialization, can be null
	 * @param nodeId node id of the DataType node of the Structure, can be null
	 * @param jsonId json id for the serializationm can be null
	 */
	public AbstractSerializer(Class<? extends IEncodeable> clazz, ExpandedNodeId binaryId, ExpandedNodeId xmlId, ExpandedNodeId nodeId, ExpandedNodeId jsonId){
		if (clazz==null) {
			throw new IllegalArgumentException("Given parameters cannot be null");
		}
		this.clazz = clazz;
		
		// GH#12, ensure 0-namespaces have the URI here
		this.binaryId = fixAndValidateId(binaryId);
		this.xmlId = fixAndValidateId(xmlId);
		this.nodeId = fixAndValidateId(nodeId);
		this.jsonId = fixAndValidateId(jsonId);
	}
	
	/**
	 * @deprecated overriding this method is no longer necessary, is not called.
	 */
	@Deprecated
	public void calcEncodeable(IEncodeable encodeable, IEncoder calculator)
	throws EncodingException{
		
	}

	/**
	 * <p>putEncodeable.</p>
	 *
	 * @param encodeable a {@link org.opcfoundation.ua.encoding.IEncodeable} object.
	 * @param encoder a {@link org.opcfoundation.ua.encoding.IEncoder} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public abstract void putEncodeable(IEncodeable encodeable, IEncoder encoder) 
	throws EncodingException;
	
	/**
	 * <p>getEncodeable.</p>
	 *
	 * @param decoder a {@link org.opcfoundation.ua.encoding.IDecoder} object.
	 * @return a {@link org.opcfoundation.ua.encoding.IEncodeable} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public abstract IEncodeable getEncodeable(IDecoder decoder) 
	throws DecodingException;
	
	

	/**
	 * @deprecated see text of {@link IEncodeableSerializer#calcEncodeable(Class, IEncodeable, IEncoder)}
	 */
	@Deprecated
	@Override
	public void calcEncodeable(Class<? extends IEncodeable> clazz,
			IEncodeable encodeable, IEncoder calculator)
			throws EncodingException {
		if (!clazz.equals(this.clazz))
			throw new EncodingException("Cannot encode "+clazz);
		calcEncodeable(encodeable, calculator);
	}


	/** {@inheritDoc} */
	@Override
	public void putEncodeable(Class<? extends IEncodeable> clazz,
			IEncodeable encodeable, IEncoder encoder) throws EncodingException
			 {
		if (!clazz.equals(this.clazz))
			throw new EncodingException("Cannot encode "+clazz);
		putEncodeable(encodeable, encoder);
	}	
	/** {@inheritDoc} */
	@Override
	public Class<? extends IEncodeable> getClass(ExpandedNodeId id) {
		return (id.equals(binaryId) || id.equals(xmlId) || id.equals(jsonId) || (nodeId!= null && id.equals(nodeId))) ? clazz : null; 
	}

	/** {@inheritDoc} */
	@Override
	public ExpandedNodeId getNodeId(Class<? extends IEncodeable> clazz, EncodeType type) {
		if (type==null) {
			return nodeId; //XXX not pretty, but will do for the moment
		}
		if (type==EncodeType.Binary) {
			return binaryId;
		}
		if (type==EncodeType.Xml) {
			return xmlId;
		}
		if(type==EncodeType.Json) {
			return jsonId;
		}
		return null; 
	}
	
	/** {@inheritDoc} */
	@Override
	public IEncodeable getEncodeable(Class<? extends IEncodeable> clazz,
			IDecoder decoder) throws DecodingException {
		if (!clazz.equals(this.clazz))
			throw new DecodingException("Cannot decode "+clazz);
		return getEncodeable(decoder);
	}

	/** {@inheritDoc} */
	@Override
	public void getSupportedClasses(Collection<Class<? extends IEncodeable>> result) {
		result.add(clazz);
	}

	/** {@inheritDoc} */
	@Override
	public void getSupportedNodeIds(Collection<ExpandedNodeId> result) {
		if (binaryId!=null) {
			result.add(binaryId);
		}
		if (xmlId!=null) {
			result.add(xmlId);
		}
		if(jsonId!=null) {
			result.add(jsonId);
		}
	}


}
