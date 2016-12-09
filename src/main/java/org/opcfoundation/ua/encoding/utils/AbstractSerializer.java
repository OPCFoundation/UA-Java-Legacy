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
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncodeType;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IDecoder;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.IEncoder;
import org.opcfoundation.ua.encoding.binary.IEncodeableSerializer;

/**
 * Simple serializer that can serialize only one type of class.
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public abstract class AbstractSerializer implements IEncodeableSerializer {

	Class<? extends IEncodeable> clazz;
	ExpandedNodeId binaryId, xmlId, nodeId;
	
	/**
	 * <p>Constructor for AbstractSerializer.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param binaryId a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @param xmlId a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 */
	public AbstractSerializer(Class<? extends IEncodeable> clazz, ExpandedNodeId binaryId, ExpandedNodeId xmlId){
		this(clazz, binaryId, xmlId, null);
	}
	
	/**
	 * <p>Constructor for AbstractSerializer.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param binaryId a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @param xmlId a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @param nodeId a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 */
	public AbstractSerializer(Class<? extends IEncodeable> clazz, ExpandedNodeId binaryId, ExpandedNodeId xmlId, ExpandedNodeId nodeId){
		if (clazz==null)
			throw new IllegalArgumentException("null arg");
		this.clazz = clazz;
		this.binaryId = binaryId;
		this.xmlId = xmlId;
		this.nodeId = nodeId;
	}
	
	/**
	 * <p>calcEncodeable.</p>
	 *
	 * @param encodeable a {@link org.opcfoundation.ua.encoding.IEncodeable} object.
	 * @param calculator a {@link org.opcfoundation.ua.encoding.IEncoder} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public abstract void calcEncodeable(IEncodeable encodeable, IEncoder calculator)
	throws EncodingException;

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
	
	
	/** {@inheritDoc} */
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
		return (id.equals(binaryId) || id.equals(xmlId) || (nodeId!= null && id.equals(nodeId))) ? clazz : null; 
	}

	/** {@inheritDoc} */
	@Override
	public ExpandedNodeId getNodeId(Class<? extends IEncodeable> clazz, EncodeType type) {
		if (type==null) return nodeId; //XXX not pretty, but will do for the moment
		if (type==EncodeType.Binary) return binaryId;
		if (type==EncodeType.Xml) return xmlId;
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
		if (binaryId!=null)
			result.add(binaryId);
		if (xmlId!=null)
			result.add(xmlId);
	}


}
