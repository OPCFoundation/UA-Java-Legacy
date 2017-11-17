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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opcfoundation.ua.builtintypes.BuiltinsMap;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.encoding.IEncodeable;

/**
 * Contains information about IEncodeable class
 *
 * @see EncodeableDescTable table of stub infos
 */
public final class EncodeableDesc {
	
	public final Class<? extends IEncodeable>	clazz;
	public final FieldInfo[]		fields;
	public final ExpandedNodeId				binaryId;
	public final ExpandedNodeId xmlId;
	public final ExpandedNodeId				id;
	public final String				url;
	public final int				length;
	public final Map<String, FieldInfo> fieldNameMap = new HashMap<String, FieldInfo>();	
	
	/**
	 * <p>Constructor for EncodeableDesc.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param fields an array of {@link org.opcfoundation.ua.encoding.utils.EncodeableDesc.FieldInfo} objects.
	 * @param id a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @param url a {@link java.lang.String} object.
	 * @param binaryId a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 * @param xmlId a {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} object.
	 */
	public EncodeableDesc(Class<? extends IEncodeable> clazz,		
			FieldInfo[] fields, ExpandedNodeId id, String url, ExpandedNodeId binaryId, ExpandedNodeId xmlId) {
		if (clazz == null || fields==null /*|| url==null  || id==null || xmlId==null || binaryId==null*/ )
			throw new IllegalArgumentException("null");
		this.binaryId = binaryId;
		this.clazz = clazz;
		this.fields = fields;
		this.id = id;
		this.url = url;
		this.xmlId = xmlId;
		this.length = fields.length;
		for (FieldInfo fi : fields)
			fieldNameMap.put(fi.field.getName(), fi);
	}

	static FieldInfo readFieldInfoFromClass(Field f)
	{
		f.setAccessible(true);
		Class<?> clazz			= f.getType();
		Integer builtinType		= BuiltinsMap.ID_MAP.get(clazz);
		int bt					= builtinType == null ? -1 : builtinType;
		boolean isArray			= clazz.isArray();
		return new FieldInfo(bt, f, isArray, clazz);
	}	
	
	/**
	 * <p>readFromClass.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param fields an array of {@link java.lang.reflect.Field} objects.
	 * @return a {@link org.opcfoundation.ua.encoding.utils.EncodeableDesc} object.
	 */
	public static EncodeableDesc readFromClass(Class<? extends IEncodeable> clazz, Field[] fields)
	{
		ExpandedNodeId binaryId = null;
		ExpandedNodeId id = null;
		ExpandedNodeId xmlId = null;
		String url = null;
		
		List<FieldInfo> fieldInfos = new ArrayList<EncodeableDesc.FieldInfo>(); 
		
		for (Field f : fields)
		{
			FieldInfo fi = readFieldInfoFromClass(f);
			fieldInfos.add( fi );
		}
		
		return new EncodeableDesc(
				clazz, 
				fieldInfos.toArray(new FieldInfo[fieldInfos.size()]),
				id,
				url,
				binaryId,
				xmlId);
	}	
	
	public static class FieldInfo {
		public final Field				field;
		public final Class<?>			type;
		public final int				builtinType;
		public final boolean			isArray;
		public FieldInfo(int builtinType, Field field, boolean isArray, Class<?> type) {
			this.builtinType = builtinType;
			this.field = field;
			this.isArray = isArray;
			this.type = type;
		}
	}
	
}
