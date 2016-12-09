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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.StatusCode;

/**
 * Reads statuscode description annotations from generated StatusCode class
 * using reflection. Based on StatusCodeDescriptions.
 *
 * @see StatusCode
 * @author Otso Palonen (otso.palonen@prosys.fi)
 */
public class IdentifierDescriptions {

	private static Map<String, NodeId> NAME_MAP = null;

	private static synchronized void readDescriptions() {
		if (NAME_MAP != null)
			return;

		NAME_MAP = new HashMap<String, NodeId>();

		Class<?> clazz;
		try {
			clazz = Class.forName("org.opcfoundation.ua.core.Identifiers");
			for (Field f : clazz.getFields()) {
				if (!f.getType().equals(NodeId.class))
					continue;
				f.setAccessible(true);
				NodeId nodeId = (NodeId) f.get(null);
				String name = f.getName();
				NAME_MAP.put(name, nodeId);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <p>toNodeId.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.NodeId} object.
	 */
	public static NodeId toNodeId(String name) {
		readDescriptions();
		NodeId nodeId = NAME_MAP.get(name);
		if (nodeId == null)
			throw new IllegalArgumentException("NodeId not found: " + name);
		return nodeId;
	}

}
