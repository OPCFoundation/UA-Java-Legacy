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

package org.opcfoundation.ua.common;


import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;

/**
 * The table of name space URIs for a server. The table enables mapping between
 * name space indexes and URIs.
 *
 * Use {@link #add} to add entries to the table. Use {@link #getIndex} to find the
 * index of an URI or {@link #getUri} to find the Uri of an index.
 */
public class NamespaceTable extends UriTable {

	/** Constant <code>OPCUA_NAMESPACE="http://opcfoundation.org/UA/"</code> */
	public static String OPCUA_NAMESPACE = "http://opcfoundation.org/UA/";
	private static NamespaceTable defaultInstance;

	/**
	 * <p>createFromArray.</p>
	 *
	 * @param namespaceArray an array of {@link java.lang.String} objects.
	 * @return a {@link org.opcfoundation.ua.common.NamespaceTable} object.
	 */
	public static NamespaceTable createFromArray(String[] namespaceArray) {
		NamespaceTable result = new NamespaceTable();
		result.addAll(namespaceArray);
		return result;
	}

	/**
	 * <p>Constructor for NamespaceTable.</p>
	 */
	public NamespaceTable() {
		add(0, OPCUA_NAMESPACE);
	}

	/**
	 * Compare 2 ExpandedNodeId objects. This method is intended for cases
	 * where one ExpandedNodeId is defined with NamespaceUri and another
	 * is defined with NamespaceIndex.
	 *
	 * @param n1 first
	 * @param n2 second
	 * @return true if they are equal
	 */
	public boolean nodeIdEquals(ExpandedNodeId n1, ExpandedNodeId n2) {
		if(ExpandedNodeId.isNull(n1) && ExpandedNodeId.isNull(n2)){
			return true;
		}
		if(ExpandedNodeId.isNull(n1) || ExpandedNodeId.isNull(n2)){
			return false; //other is now null
		}
		if (!n1.getValue().equals(n2.getValue()))
			return false;
		int i1 = n1.getNamespaceUri() == null ? n1.getNamespaceIndex() : getIndex(n1.getNamespaceUri());
		int i2 = n2.getNamespaceUri() == null ? n2.getNamespaceIndex() : getIndex(n2.getNamespaceUri());
		return i1 == i2;
	}
	
	/**
	 * Compare 1 ExpandedNodeId and 1 NodeId. This method is intended for cases
	 * where the ExpandedNodeId is defined with NamespaceUri and a comparison
	 * to NodeId which has NamespaceIndex is wanted.
	 *
	 * @param n1 first
	 * @param n2 second
	 * @return true if they are equal
	 */
	public boolean nodeIdEquals(NodeId n1, ExpandedNodeId n2) {
		if(NodeId.isNull(n1) && ExpandedNodeId.isNull(n2)){
			return true;
		}
		if(NodeId.isNull(n1) || ExpandedNodeId.isNull(n2)){
			return false; //other is now null
		}
		if (!n1.getValue().equals(n2.getValue()))
			return false;
		int i1 = n1.getNamespaceIndex();
		int i2 = n2.getNamespaceUri() == null ? n2.getNamespaceIndex() : getIndex(n2.getNamespaceUri());
		return i1 == i2;
	}

	/**
	 * Convert the nodeId to an ExpandedNodeId using the namespaceUris of the
	 * table
	 *
	 * @param nodeId
	 *            the node ID
	 * @return The respective ExpandedNodeId
	 */
	public ExpandedNodeId toExpandedNodeId(NodeId nodeId) {
		return new ExpandedNodeId(null, getUri(nodeId.getNamespaceIndex()), nodeId.getValue());
	}

	/**
	 * Convert the expandedNodeId to a NodeId using the name space indexes of the
	 * table
	 *
	 * @param expandedNodeId
	 *            the expanded node ID
	 * @return The respective NodeId
	 * @throws org.opcfoundation.ua.common.ServiceResultException
	 *             if there is no entry for the namespaceUri used in the
	 *             expandedNodeId
	 */
	public NodeId toNodeId(ExpandedNodeId expandedNodeId)
			throws ServiceResultException {
		// TODO: serverIndex==0 is valid reference to the local server, so it
		// should be accepted as well // jaro
		if (ExpandedNodeId.isNull(expandedNodeId))
			return NodeId.NULL;
		if (!expandedNodeId.isLocal())
			throw new ServiceResultException(
					"Cannot convert ExpandedNodeId with server index to NodeId");
		String uri = expandedNodeId.getNamespaceUri();
		if (uri == null)
			return NodeId.get(expandedNodeId.getIdType(), expandedNodeId
					.getNamespaceIndex(), expandedNodeId.getValue());
		int index = this.getIndex(uri);
		if (index < 0)
			throw new ServiceResultException(
					"Index for uri \""+uri+"\" not found in NamespaceTable");
		return NodeId.get(expandedNodeId.getIdType(), index, expandedNodeId
				.getValue());
	}

	/**
	 * Check if the node IDs refer to the same name space. Compares the NamespaceIndex of the IDs.
	 *
	 * @param nodeId1 a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 * @param nodeId2 a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 * @return true if the nodes are in the same name space
	 */
	public boolean namespaceEquals(NodeId nodeId1, NodeId nodeId2) {
		return nodeId1.getNamespaceIndex() == nodeId2.getNamespaceIndex();
	}

	/**
	 * Check if the node IDs refer to the same name space. The expandedNodeId is
	 * checked for the NamespaceUri or Index depending on which is used.
	 *
	 * @param nodeId a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 * @param expandedNodeId a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @return true if the nodes are in the same name space
	 */
	public boolean namespaceEquals(NodeId nodeId, ExpandedNodeId expandedNodeId) {
		int expandedNamespaceIndex = expandedNodeId.getNamespaceUri() != null ? getIndex(expandedNodeId
				.getNamespaceUri()) : expandedNodeId.getNamespaceIndex();
		return nodeId.getNamespaceIndex() == expandedNamespaceIndex;
	}

	/**
	 * Check if the node IDs refer to the same name space. The expandedNodeIds are
	 * checked for the NamespaceUri or Index depending on which is used.
	 *
	 * @param expandedNodeId1 a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @param expandedNodeId2 a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @return true if the nodes are in the same name space
	 */
	public boolean namespaceEquals(ExpandedNodeId expandedNodeId1, ExpandedNodeId expandedNodeId2) {
		int expandedNamespaceIndex1 = expandedNodeId1.getNamespaceUri() != null ? getIndex(expandedNodeId1
				.getNamespaceUri()) : expandedNodeId1.getNamespaceIndex();
		int expandedNamespaceIndex2 = expandedNodeId2.getNamespaceUri() != null ? getIndex(expandedNodeId2
				.getNamespaceUri()) : expandedNodeId2.getNamespaceIndex();
		return expandedNamespaceIndex1 == expandedNamespaceIndex2;
	}

	/**
	 * <p>Getter for the field <code>defaultInstance</code>.</p>
	 *
	 * @return a default instance which can be used when no other namespace
	 *         table is available. DO NOT use, if you have a valid application
	 *         context with an initialized namespace table available.
	 */
	public static NamespaceTable getDefaultInstance() {
		if (defaultInstance == null)
			defaultInstance = NamespaceTable.createFromArray(new String[]{NamespaceTable.OPCUA_NAMESPACE});
		return defaultInstance;
	}

}
