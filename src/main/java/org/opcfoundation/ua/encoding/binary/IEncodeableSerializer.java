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

import java.util.Collection;

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.EncodeableSerializer;
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncodeType;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IDecoder;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.IEncoder;
import org.opcfoundation.ua.encoding.utils.SerializerComposition;

/**
 * IEncodeableSerializer serializes IEncodeable classes.
 *
 * There are two implementations; Reflection based {@link EncodeableReflectionSerializer}
 * and code-generate based {@link EncodeableSerializer}.
 *
 * @see SerializerComposition Implementation that unifies a set of serializers
 * @see EncodeableReflectionSerializer Reflection based implementation
 * @see EncodeableSerializer Code-generated implementation
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public interface IEncodeableSerializer {

	/**
	 * Get the set of node ids this serializes knows how to decode.
	 *
	 * @param result a collection to be filled with node ids
	 */
	void getSupportedNodeIds(Collection<ExpandedNodeId> result);
	
	/**
	 * Returns a set of classes this serializes knows how to encode
	 *
	 * @param result a {@link java.util.Collection} object.
	 */
	void getSupportedClasses(Collection<Class<? extends IEncodeable>> result);
	
	/**
	 * <p>getClass.</p>
	 *
	 * @param id a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @return a {@link java.lang.Class} object.
	 */
	Class<? extends IEncodeable> getClass(ExpandedNodeId id);

	/**
	 * <p>getNodeId.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param type a {@link org.opcfoundation.ua.encoding.EncodeType} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 */
	ExpandedNodeId getNodeId(Class<? extends IEncodeable> clazz, EncodeType type);
	
	/**
	 * Deserialize an object from a binary stream.
	 * Reading NodeId must be omited.
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param decoder a {@link org.opcfoundation.ua.encoding.IDecoder} object.
	 * @return deserialized object
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	IEncodeable getEncodeable(Class<? extends IEncodeable> clazz, IDecoder decoder)
	throws DecodingException;
	
	/**
	 * Serialize object to encoder.
	 * Serialization of NodeId is omited.
	 *
	 * @param encodeable a {@link org.opcfoundation.ua.encoding.IEncodeable} object.
	 * @param encoder a {@link org.opcfoundation.ua.encoding.IEncoder} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	void putEncodeable(Class<? extends IEncodeable> clazz, IEncodeable encodeable, IEncoder encoder)
	throws EncodingException;
	
	/**
	 * Calculate encodeable size in bytes.
	 * Omit NodeId.
	 *
	 * @param encodeable a {@link org.opcfoundation.ua.encoding.IEncodeable} object.
	 * @param calculator a {@link org.opcfoundation.ua.encoding.IEncoder} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	void calcEncodeable(Class<? extends IEncodeable> clazz, IEncodeable encodeable, IEncoder calculator)
	throws EncodingException;
	
}
