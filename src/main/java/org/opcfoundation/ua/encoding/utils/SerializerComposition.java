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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncodeType;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IDecoder;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.IEncoder;
import org.opcfoundation.ua.encoding.binary.IEncodeableSerializer;

/**
 * A collection of serializers for Structure types. Used by the encoders and decoders.
 */
public class SerializerComposition implements IEncodeableSerializer {

	Map<Class<? extends IEncodeable>, IEncodeableSerializer> serializers = new HashMap<Class<? extends IEncodeable>, IEncodeableSerializer>();
	Map<ExpandedNodeId, Class<? extends IEncodeable>> idToClass = new HashMap<ExpandedNodeId, Class<? extends IEncodeable>>();
	Map<Class<? extends IEncodeable>, ExpandedNodeId> classToBinId = new HashMap<Class<? extends IEncodeable>, ExpandedNodeId>();
	Map<Class<? extends IEncodeable>, ExpandedNodeId> classToXmlId = new HashMap<Class<? extends IEncodeable>, ExpandedNodeId>();
	Set<ExpandedNodeId> nodeIds = idToClass.keySet();
	Set<Class<? extends IEncodeable>> classes = classToBinId.keySet();

	/**
	 * <p>Constructor for SerializerComposition.</p>
	 */
	public SerializerComposition() {
	}
	
	/**
	 * <p>addSerializer.</p>
	 *
	 * @param serializer a {@link org.opcfoundation.ua.encoding.binary.IEncodeableSerializer} object.
	 */
	public void addSerializer(IEncodeableSerializer serializer)
	{
		List<Class<? extends IEncodeable>> classes = new ArrayList<Class<? extends IEncodeable>>();
		serializer.getSupportedClasses(classes);
		for (Class<? extends IEncodeable> clazz : classes)
		{
			assert(classToBinId.get(clazz)==null);
			ExpandedNodeId binId = serializer.getNodeId(clazz, EncodeType.Binary);
			classToBinId.put(clazz, binId);
			ExpandedNodeId xmlId = serializer.getNodeId(clazz, EncodeType.Xml);
			classToXmlId.put(clazz, xmlId);
			serializers.put(clazz, serializer);
			if (binId!=null) 
				idToClass.put(binId, clazz);			
			if (xmlId!=null) 
				idToClass.put(xmlId, clazz);		
			try{
				ExpandedNodeId nodeId = serializer.getNodeId(clazz, null);
				if(nodeId != null){
					idToClass.put(nodeId, clazz);
				}
			}catch(Exception e){
				/*
				 *  XXX Exceptions are not expected at this point, but since this 
				 *  depends on returning the type NodeId with null EncodeType 
				 *  (this is coded to AbstractSerializer) we catch just in 
				 *  case some serializer would throw NPE on null EncodeType.
				 */
			}
		}
	}

	/** {@inheritDoc} */
	public void putEncodeable(Class<? extends IEncodeable> clazz, IEncodeable encodeable, IEncoder encoder) throws EncodingException {
		IEncodeableSerializer s = serializers.get(clazz);
		if (s==null) throw new EncodingException("Cannot encode "+clazz);
		s.putEncodeable(clazz, encodeable, encoder);
	}
	
	/** {@inheritDoc} */
	public void calcEncodeable(Class<? extends IEncodeable> clazz, IEncodeable encodeable, IEncoder calculator)
	throws EncodingException {
		IEncodeableSerializer s = serializers.get(clazz);
		if (s==null) throw new EncodingException("Cannot encode "+clazz);
		s.calcEncodeable(clazz, encodeable, calculator);		
	}

	/** {@inheritDoc} */
	public IEncodeable getEncodeable(Class<? extends IEncodeable> clazz, IDecoder decoder) throws DecodingException {
		IEncodeableSerializer s = serializers.get(clazz);
		if (s==null) throw new DecodingException("Cannot decode "+clazz);
		return s.getEncodeable(clazz, decoder);
	}

	/** {@inheritDoc} */
	public Class<? extends IEncodeable> getClass(ExpandedNodeId id) {
		return idToClass.get(id);
	}
	
	/** {@inheritDoc} */
	public ExpandedNodeId getNodeId(Class<? extends IEncodeable> clazz, EncodeType type) {
		if (type == EncodeType.Binary)
			return classToBinId.get(clazz);
		if (type == EncodeType.Xml)
			return classToXmlId.get(clazz);
		return null;
	}

	/** {@inheritDoc} */
	public void getSupportedClasses(Collection<Class<? extends IEncodeable>> result) {
		result.addAll(classes);
	}

	/** {@inheritDoc} */
	public void getSupportedNodeIds(Collection<ExpandedNodeId> result) {
		result.addAll(nodeIds);
	}

}
