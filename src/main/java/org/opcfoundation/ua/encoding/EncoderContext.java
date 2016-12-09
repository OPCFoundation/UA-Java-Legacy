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

package org.opcfoundation.ua.encoding;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.common.NamespaceTable;
import org.opcfoundation.ua.common.ServerTable;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.encoding.binary.IEncodeableSerializer;
import org.opcfoundation.ua.utils.StackUtils;

/**
 * <p>EncoderContext class.</p>
 */
public class EncoderContext {
	private static EncoderContext defaultInstance = new EncoderContext(NamespaceTable.getDefaultInstance(), null, StackUtils.getDefaultSerializer());

	/**
	 * <p>Getter for the field <code>defaultInstance</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public static EncoderContext getDefaultInstance() {
		return defaultInstance ;
	}
	
	/**
	 * <p>decode.</p>
	 *
	 * @param values an array of {@link org.opcfoundation.ua.builtintypes.ExtensionObject} objects.
	 * @return a {@link java.lang.Object} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Object decode(ExtensionObject[] values) throws DecodingException {
		return decode(values, null);
	}
	
	/**
	 * <p>decode.</p>
	 *
	 * @param values an array of {@link org.opcfoundation.ua.builtintypes.ExtensionObject} objects.
	 * @param namespaceTable a {@link org.opcfoundation.ua.common.NamespaceTable} object.
	 * @return a {@link java.lang.Object} object.
	 * @throws org.opcfoundation.ua.encoding.DecodingException if any.
	 */
	public Object decode(ExtensionObject[] values, NamespaceTable namespaceTable) throws DecodingException {
		Object value;
		int n = values.length;
		Structure[] newValue = new Structure[n];
		for (int i = 0; i < n; i++) {
			ExtensionObject obj = values[i];
			if (obj != null) newValue[i] = obj.decode(this, namespaceTable);
		}
		value = newValue;
		if (n > 0) {
			Class<? extends Structure> valueClass = null;
			for (int i = 0; i < n; i++)
				if (newValue[i] != null) {
					Class<? extends Structure> newClass = newValue[i]
							.getClass();
					if (valueClass == null)
						valueClass = newClass;
					else if (!newClass.isAssignableFrom(valueClass))
						if (valueClass.isAssignableFrom(newClass))
							valueClass = newClass;
						else {
							valueClass = null;
							break;
						}
				}
			if (valueClass != null)
				value = Arrays.copyOf(newValue, n, ((Structure[]) Array
						.newInstance(valueClass, 0)).getClass());
		}
		return value;
	}

	public NamespaceTable namespaceTable;
	public ServerTable serverTable;
	public IEncodeableSerializer encodeableSerializer;	

	public int maxMessageSize = 4*1024*1024*1024;
	
	// 0 = norestriction
    public int maxStringLength = 0; //UnsignedShort.MAX_VALUE.intValue();
    public int maxByteStringLength = 0; //UnsignedShort.MAX_VALUE.intValue() * 16;
    public int maxArrayLength = 0; //UnsignedShort.MAX_VALUE.intValue();

	/**
	 * <p>Constructor for EncoderContext.</p>
	 *
	 * @param namespaceTable a {@link org.opcfoundation.ua.common.NamespaceTable} object.
	 * @param serverTable a {@link org.opcfoundation.ua.common.ServerTable} object.
	 * @param encodeableSerializer a {@link org.opcfoundation.ua.encoding.binary.IEncodeableSerializer} object.
	 * @param maxMessageSize a int.
	 */
	public EncoderContext(NamespaceTable namespaceTable, 
			ServerTable serverTable,
			IEncodeableSerializer encodeableSerializer,
			int maxMessageSize) {
		this.encodeableSerializer = encodeableSerializer;
		this.namespaceTable = namespaceTable;
		this.serverTable = serverTable;
		this.maxMessageSize = maxMessageSize;
	}

	/**
	 * <p>Constructor for EncoderContext.</p>
	 *
	 * @param namespaceTable a {@link org.opcfoundation.ua.common.NamespaceTable} object.
	 * @param serverTable a {@link org.opcfoundation.ua.common.ServerTable} object.
	 * @param encodeableSerializer a {@link org.opcfoundation.ua.encoding.binary.IEncodeableSerializer} object.
	 */
	public EncoderContext(NamespaceTable namespaceTable,
	                      ServerTable serverTable,
	                      IEncodeableSerializer encodeableSerializer) {
		this.encodeableSerializer = encodeableSerializer;
		this.namespaceTable = namespaceTable;
		this.serverTable = serverTable;
	}

	/**
	 * <p>Getter for the field <code>maxMessageSize</code>.</p>
	 *
	 * @return a int.
	 */
	public int getMaxMessageSize() {
		return maxMessageSize;
	}
	
	/**
	 * <p>Setter for the field <code>maxMessageSize</code>.</p>
	 *
	 * @param encodeMessageMaxSize a int.
	 */
	public void setMaxMessageSize(int encodeMessageMaxSize) {
		this.maxMessageSize = encodeMessageMaxSize;
	}
	
	/**
	 * <p>Getter for the field <code>namespaceTable</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.common.NamespaceTable} object.
	 */
	public NamespaceTable getNamespaceTable() {
		return namespaceTable;
	}

	/**
	 * <p>Setter for the field <code>namespaceTable</code>.</p>
	 *
	 * @param namespaceTable a {@link org.opcfoundation.ua.common.NamespaceTable} object.
	 */
	public void setNamespaceTable(NamespaceTable namespaceTable) {
		this.namespaceTable = namespaceTable;
	}

	/**
	 * <p>getEncodeableNodeId.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param type a {@link org.opcfoundation.ua.encoding.EncodeType} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 * @throws org.opcfoundation.ua.common.ServiceResultException if any.
	 */
	public NodeId getEncodeableNodeId(Class<? extends IEncodeable> clazz, EncodeType type) throws ServiceResultException {
		return namespaceTable.toNodeId(encodeableSerializer.getNodeId(clazz, type));
	}

	/**
	 * <p>Getter for the field <code>serverTable</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.common.ServerTable} object.
	 */
	public ServerTable getServerTable() {
		return serverTable;
	}

	/**
	 * <p>Setter for the field <code>serverTable</code>.</p>
	 *
	 * @param serverTable a {@link org.opcfoundation.ua.common.ServerTable} object.
	 */
	public void setServerTable(ServerTable serverTable) {
		this.serverTable = serverTable;
	}

	/**
	 * <p>Getter for the field <code>encodeableSerializer</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.encoding.binary.IEncodeableSerializer} object.
	 */
	public IEncodeableSerializer getEncodeableSerializer() {
		return encodeableSerializer;
	}

	/**
	 * <p>getEncodeableClass.</p>
	 *
	 * @param id a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<? extends IEncodeable> getEncodeableClass(NodeId id) {
		return encodeableSerializer.getClass(namespaceTable.toExpandedNodeId(id));
	}

	/**
	 * <p>Setter for the field <code>encodeableSerializer</code>.</p>
	 *
	 * @param encodeableSerializer a {@link org.opcfoundation.ua.encoding.binary.IEncodeableSerializer} object.
	 */
	public void setEncodeableSerializer(IEncodeableSerializer encodeableSerializer) {
		this.encodeableSerializer = encodeableSerializer;
	}

	/**
	 * <p>toNodeId.</p>
	 *
	 * @param id a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public NodeId toNodeId(ExpandedNodeId id) throws EncodingException {
		try {
			return namespaceTable.toNodeId(id);
		} catch (ServiceResultException e) {
			throw new EncodingException("Could not get namespace index for given id");
		}
	}
	
	/**
	 * <p>Getter for the field <code>maxStringLength</code>.</p>
	 *
	 * @return a int.
	 */
	public int getMaxStringLength() {
		return maxStringLength;
	}

	/**
	 * <p>Setter for the field <code>maxStringLength</code>.</p>
	 *
	 * @param maxStringLength a int.
	 */
	public void setMaxStringLength(int maxStringLength) {
		this.maxStringLength = maxStringLength;
	}

	/**
	 * <p>Getter for the field <code>maxByteStringLength</code>.</p>
	 *
	 * @return a int.
	 */
	public int getMaxByteStringLength() {
		return maxByteStringLength;
	}

	/**
	 * <p>Setter for the field <code>maxByteStringLength</code>.</p>
	 *
	 * @param maxByteStringLength a int.
	 */
	public void setMaxByteStringLength(int maxByteStringLength) {
		this.maxByteStringLength = maxByteStringLength;
	}

	/**
	 * <p>Getter for the field <code>maxArrayLength</code>.</p>
	 *
	 * @return a int.
	 */
	public int getMaxArrayLength() {
		return maxArrayLength;
	}

	/**
	 * <p>Setter for the field <code>maxArrayLength</code>.</p>
	 *
	 * @param maxArrayLength a int.
	 */
	public void setMaxArrayLength(int maxArrayLength) {
		this.maxArrayLength = maxArrayLength;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("   namespaceTable = "+namespaceTable + "\n");		
		sb.append("   serverTable = "+serverTable + "\n");
		sb.append("   maxMessageSize = "+maxMessageSize + "\n");
		sb.append("   maxStringLength = "+maxStringLength + "\n");
		sb.append("   maxByteStringLength = "+maxByteStringLength + "\n");
		sb.append("   maxArrayLength = "+maxArrayLength + "\n");
		return sb.toString();
	}
}
