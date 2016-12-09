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

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.encoding.IEncodeable;

/**
 * This class discovers builtin encodeable nodeIds and
 * corresponding classes.
 */
public class EncodeableDiscovery {

	private static Map<NodeId, Class<IEncodeable>> DEFAULT;
	
	/**
	 * Return default NodeId -&gt; Class mapping.
	 *
	 * @return default encodeable table
	 */
	public static synchronized Map<NodeId, Class<IEncodeable>> getDefault() 
	{
		if (DEFAULT!=null) return DEFAULT;
		
		Map<NodeId, Class<IEncodeable>> map = new HashMap<NodeId, Class<IEncodeable>>();
		try {
			discoverDefaultEncodeables(map);			
		} catch (RuntimeException e) {
			// Unrecoverable problem as installation is faulty if this occurs
			throw new Error(e);
		}
		DEFAULT = Collections.unmodifiableMap(map);
		return DEFAULT;
	}
	
	/**
	 * Discover default encodeables. Encodeables are discovered by inspecting
	 * all fields from Identifiers class using reflection.
	 *
	 * @param map encodeable table to fill with builtin encodeables
	 */
	@SuppressWarnings("unchecked")
	public static void discoverDefaultEncodeables(Map<NodeId, Class<IEncodeable>> map) 
	{
		// Discover builtin classes
		Class<?> clazz = Identifiers.class;
		ClassLoader cl = clazz.getClassLoader();
		int index = clazz.getCanonicalName().lastIndexOf(".");
		String prefix = clazz.getCanonicalName().substring(0, index);
		for (Field f : clazz.getFields())
		{
			f.setAccessible(true);
			try {
				String className = prefix+"."+f.getName();
				Class<IEncodeable> c = (Class<IEncodeable>) cl.loadClass(className);
				if (!IEncodeable.class.isAssignableFrom(c)) continue;
				for (Field cf : c.getFields()) {
					cf.setAccessible(true);
					if (!cf.getType().equals(NodeId.class)) continue;
					NodeId nodeId;
					try {
						nodeId = (NodeId) cf.get(null);
					} catch (IllegalArgumentException e) {
						throw new RuntimeException("Failed to load default identifiers", e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException("Failed to load default identifiers", e);
					}
					if (nodeId==null)
						throw new RuntimeException("Failed to load default identifiers");
					map.put(nodeId, c);
				}
			} catch (ClassNotFoundException e) {
				continue;
			}
		}
	}

	
}
