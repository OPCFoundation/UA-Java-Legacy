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

import java.util.UUID;

import org.opcfoundation.ua.common.NamespaceTable;
import org.opcfoundation.ua.common.ServerTable;
import org.opcfoundation.ua.core.IdType;

/**
 * A reference to a node. The difference to NodeId and ExpandedNodeId is that
 * this class is reference by it-self with out lookup from namespace table and server table.
 *
 * (Untested)
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class NodeReference {

	/**
	 * Bind node id, namespace and server url
	 *
	 * @param nodeId a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 * @param namespaceTable a {@link org.opcfoundation.ua.common.NamespaceTable} object.
	 * @param serverTable a {@link org.opcfoundation.ua.common.ServerTable} object.
	 * @return a new node reference
	 */
	public static NodeReference createFromNodeId(NodeId nodeId, NamespaceTable namespaceTable, ServerTable serverTable)
	{
		return new NodeReference(serverTable.getUri(0), namespaceTable.getUri(nodeId.getNamespaceIndex()), nodeId.getValue());
	}

	/**
	 * Bind node id, namespace and server url
	 *
	 * @param nodeId a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @param namespaceTable a {@link org.opcfoundation.ua.common.NamespaceTable} object.
	 * @param serverTable a {@link org.opcfoundation.ua.common.ServerTable} object.
	 * @return a new node reference
	 */
	public static NodeReference createFromNodeId(ExpandedNodeId nodeId, NamespaceTable namespaceTable, ServerTable serverTable)
	{
		if (nodeId.getNamespaceUri()!=null)
			return new NodeReference(serverTable.getUri(nodeId.getServerIndex().intValue()), nodeId.getNamespaceUri(), nodeId.getValue());
		return new NodeReference(serverTable.getUri(nodeId.getServerIndex().intValue()), namespaceTable.getUri(nodeId.getNamespaceIndex()), nodeId.getValue());
	}


	/** Constant <code>OPCUA_NAMESPACE="http://opcfoundation.org/UA/"</code> */
	public static String OPCUA_NAMESPACE = "http://opcfoundation.org/UA/";
	
	IdType type;
	String namespaceUri, serverUri;
	Object value;
	int hashCode;
	
	/**
	 * Construct ExpandedNodeId using NamespaceIndex.
	 *
	 * @param serverUri server uri
	 * @param namespaceUri namespace uri
	 * @param value value (must be UnsignedInteger, String, UUID or ByteString)
	 */
	public NodeReference(String serverUri, String namespaceUri, Object value)
	{
		if (value==null || namespaceUri==null || serverUri==null) throw new IllegalArgumentException("argument is null");
		this.namespaceUri = namespaceUri;
		this.serverUri = serverUri;
		if (value instanceof Integer) value = UnsignedInteger.getFromBits((Integer)value);
		this.value = value;
		if (value instanceof UnsignedInteger) type = IdType.Numeric;
		else if (value instanceof String) type = IdType.String;
		else if (value instanceof UUID) type = IdType.Guid;
		else if (value instanceof ByteString) type = IdType.Opaque;
		else throw new IllegalArgumentException("value cannot be "+value.getClass().getName());
		
		hashCode += 3*value.hashCode();
		if (namespaceUri!=null) hashCode += 13*namespaceUri.hashCode();
		if (serverUri!=null) hashCode += 17*serverUri.hashCode();		
	}
	
	/**
	 * <p>getIdType.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.core.IdType} object.
	 */
	public IdType getIdType()
	{
		return type;
	}
	
	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * <p>Getter for the field <code>namespaceUri</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getNamespaceUri()
	{
		return namespaceUri;
	}
	
	/**
	 * <p>Getter for the field <code>serverUri</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getServerUri()
	{
		return serverUri;
	}
	
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return hashCode;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NodeReference)) return false;
		NodeReference other = (NodeReference) obj;
		if (!other.getNamespaceUri().equals( namespaceUri )) return false;
		if (!other.getServerUri().equals( serverUri )) return false;

		return other.getValue().equals(value);
		
	}
	
}
