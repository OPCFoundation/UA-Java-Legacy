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

import org.opcfoundation.ua.encoding.IEncodeable;

/**
 * Super interface for all complex type serializable objects
 */
public interface Structure extends IEncodeable, Cloneable {

	/**
	 * The NodeId for the actual DataType node defining this Structure type. Should never be null. 
	 * Shall always contain the NamespaceUri within the {@link ExpandedNodeId}.
	 */
	ExpandedNodeId getTypeId();
	
	/**
	 * The NodeId for the "Default XML" encodings node of this Structure type. Can be null if not supported.
	 * Shall always contain the NamespaceUri within the {@link ExpandedNodeId}, if not null.
	 */
	ExpandedNodeId getXmlEncodeId();
	
	/**
	 * The NodeId for the "Default Binary" encodings node of this Structure type. Should never be null as the encoding is mandatory.
	 * Shall always contain the NamespaceUri within the {@link ExpandedNodeId}.
	 */
	ExpandedNodeId getBinaryEncodeId();
	
	/**
	 * The NodeId for the "Default JSON" encodings node of this Structure type. Can be null if not supported.
	 * Shall always contain the NamespaceUri within the {@link ExpandedNodeId}, if not null.
	 */
	ExpandedNodeId getJsonEncodeId();
	
	/**
	 * As every Structure is Cloneable, this method provides convinience method for
	 * calling .clone for an unknown Structure. Classes implementing Structure should change the signature
	 * to return the type of the implementing class. Returns a deep clone of this Structure object.
	 */
	Structure clone();
	
}
