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
	 * @param nodeId
	 * @param namespaceTable
	 * @param serverTable
	 * @return a new node reference
	 */
	public static NodeReference createFromNodeId(NodeId nodeId, NamespaceTable namespaceTable, ServerTable serverTable)
	{
		return new NodeReference(serverTable.getUri(0), namespaceTable.getUri(nodeId.getNamespaceIndex()), nodeId.getValue());
	}

	/**
	 * Bind node id, namespace and server url
	 * 
	 * @param nodeId
	 * @param namespaceTable
	 * @param serverTable
	 * @return a new node reference
	 */
	public static NodeReference createFromNodeId(ExpandedNodeId nodeId, NamespaceTable namespaceTable, ServerTable serverTable)
	{
		if (nodeId.getNamespaceUri()!=null)
			return new NodeReference(serverTable.getUri(nodeId.getServerIndex().intValue()), nodeId.getNamespaceUri(), nodeId.getValue());
		return new NodeReference(serverTable.getUri(nodeId.getServerIndex().intValue()), namespaceTable.getUri(nodeId.getNamespaceIndex()), nodeId.getValue());
	}


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
	 * @param value value (must be UnsignedInteger, String, UUID or byte[])
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
		else if (value instanceof byte[]) type = IdType.Opaque;
		else throw new IllegalArgumentException("value cannot be "+value.getClass().getName());
		
		if (!(value instanceof byte[])) 
			hashCode += 3*value.hashCode();
		if (namespaceUri!=null) hashCode += 13*namespaceUri.hashCode();
		if (serverUri!=null) hashCode += 17*serverUri.hashCode();		
	}
	
	public IdType getIdType()
	{
		return type;
	}
	
	public Object getValue()
	{
		return value;
	}

	public String getNamespaceUri()
	{
		return namespaceUri;
	}
	
	public String getServerUri()
	{
		return serverUri;
	}
	
	@Override
	public int hashCode() {
		if (type==IdType.Opaque) 		
			return hashCode + Arrays.hashCode((byte[])value);
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NodeReference)) return false;
		NodeReference other = (NodeReference) obj;
		if (!other.getNamespaceUri().equals( namespaceUri )) return false;
		if (!other.getServerUri().equals( serverUri )) return false;
		if (other.type==IdType.Opaque) { 
			// Deep compare
			return Arrays.equals((byte[])value, (byte[])other.value);
		} else {
			return other.getValue().equals(value);
		}
	}
	
}
