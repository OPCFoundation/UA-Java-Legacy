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

package org.opcfoundation.ua.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opcfoundation.ua.builtintypes.BuiltinsMap;

/**
 * <p>ReflectionUtils class.</p>
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ReflectionUtils {

	/**
	 * Returns all methods public, protected and private
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @return all methods
	 */
	public static Method[] getAllMethods(Class<?> clazz)
	{
		Set<Method> result = new HashSet<Method>();				
		_getAllMethods(clazz, result);		
		return result.toArray(new Method[result.size()]);
	}
	
	private static void _getAllMethods(Class<?> clazz, Collection<Method> result)
	{
		for (Method m : clazz.getDeclaredMethods())
			result.add(m);
	}

	/**
	 * <p>getAllFields.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @return an array of {@link java.lang.reflect.Field} objects.
	 */
	public static Field[] getAllFields(Class<?> clazz)
	{
		List<Field> result = new ArrayList<Field>();				
		_getAllFields(clazz, result);		
		return result.toArray(new Field[result.size()]);
	}
	
	private static void _getAllFields(Class<?> clazz, Collection<Field> result)
	{
		for (Field m : clazz.getDeclaredFields())
			result.add(m);
	}	

	/**
	 * Get array version of a class.
	 *
	 * E.g. Object -&gt; Object[]
	 *      Object[] -&gt; Object[][]
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @return array class
	 */
	public static Class<?> getRespectiveArrayClass(Class<?> clazz)
	{
		Integer bt = BuiltinsMap.ID_MAP.get(clazz);
		if (bt!=null) 
			return BuiltinsMap.ARRAY_LIST.get(bt);
		
		String name = "[L"+clazz.getCanonicalName()+";";
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new Error(e);
		}
	}	
	
	/**
	 * <p>getComponentClass.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @return a {@link java.lang.Class} object.
	 */
	public static Class<?> getComponentClass(Class<?> clazz)
	{
		if (!clazz.isArray()) return clazz;
		return clazz.getComponentType();
	}
	
	
}
