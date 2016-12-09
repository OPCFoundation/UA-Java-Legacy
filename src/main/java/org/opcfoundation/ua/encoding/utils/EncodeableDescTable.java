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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.encoding.IEncodeable;

/**
 * Table containing descriptions of stub classes.
 *
 * @see EncodeableDesc
 */
public class EncodeableDescTable {
	
	private Map<Class<? extends IEncodeable>, EncodeableDesc> classMap = new HashMap<Class<? extends IEncodeable>, EncodeableDesc>();
	private Map<ExpandedNodeId, EncodeableDesc> idMap = new HashMap<ExpandedNodeId, EncodeableDesc>();
	private Map<ExpandedNodeId, EncodeableDesc> binIdMap = new HashMap<ExpandedNodeId, EncodeableDesc>();
	private Map<ExpandedNodeId, EncodeableDesc> xmlIdMap = new HashMap<ExpandedNodeId, EncodeableDesc>();
	private Map<Class<? extends IEncodeable>, EncodeableDesc> _classMap = Collections.unmodifiableMap(classMap);
	private Map<ExpandedNodeId, EncodeableDesc> _idMap = Collections.unmodifiableMap(idMap);
	private Map<ExpandedNodeId, EncodeableDesc> _binIdMap = Collections.unmodifiableMap(binIdMap);
	private Map<ExpandedNodeId, EncodeableDesc> _xmlIdMap = Collections.unmodifiableMap(xmlIdMap);
	
	/**
	 * <p>get.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @return a {@link org.opcfoundation.ua.encoding.utils.EncodeableDesc} object.
	 */
	public EncodeableDesc get(Class<?> clazz)
	{
		return classMap.get(clazz);
	}
	
	/**
	 * <p>get.</p>
	 *
	 * @param id a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @return a {@link org.opcfoundation.ua.encoding.utils.EncodeableDesc} object.
	 */
	public EncodeableDesc get(ExpandedNodeId id)
	{
		return idMap.get(id);
	}

	
	/**
	 * <p>addStructureInfo.</p>
	 *
	 * @param s a {@link org.opcfoundation.ua.encoding.utils.EncodeableDesc} object.
	 */
	public void addStructureInfo(EncodeableDesc s)
	{
		classMap.put(s.clazz, s);
		//classMap.put(getArrayClass(s.clazz), s);
		idMap.put(s.binaryId, s);
		idMap.put(s.xmlId, s);
		idMap.put(s.id, s);
		binIdMap.put(s.binaryId, s);
		xmlIdMap.put(s.binaryId, s);
	}
	
	/**
	 * <p>Getter for the field <code>idMap</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<ExpandedNodeId, EncodeableDesc> getIdMap()
	{
		return _idMap;		
	}
	
	/**
	 * <p>Getter for the field <code>binIdMap</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<ExpandedNodeId, EncodeableDesc> getBinIdMap()
	{
		return _binIdMap;
	}
	
	/**
	 * <p>Getter for the field <code>xmlIdMap</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<ExpandedNodeId, EncodeableDesc> getXmlIdMap()
	{
		return _xmlIdMap;
	}
	
	/**
	 * <p>Getter for the field <code>classMap</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<Class<? extends IEncodeable>, EncodeableDesc> getClassMap()
	{
		return _classMap;
	}
	
}
