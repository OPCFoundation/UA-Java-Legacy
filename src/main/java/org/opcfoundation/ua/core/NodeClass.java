/* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.core;

import java.util.Collection;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opcfoundation.ua.builtintypes.Enumeration;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.Identifiers;



/**
 * <p>NodeClass class.</p>
 *
 */
public enum NodeClass implements Enumeration {

    Object(1),
    Variable(2),
    Method(4),
    ObjectType(8),
    VariableType(16),
    ReferenceType(32),
    DataType(64),
    View(128);
	

	/** Constant <code>ID</code> */
	public static final NodeId ID = Identifiers.NodeClass;
	/** Constant <code>NONE</code> */
	public static EnumSet<NodeClass> NONE = EnumSet.noneOf( NodeClass.class );
	/** Constant <code>ALL</code> */
	public static EnumSet<NodeClass> ALL = EnumSet.allOf( NodeClass.class );
	
	private final int value;
	NodeClass(int value) {
		this.value = value;
	}
	
	/** {@inheritDoc} */
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, NodeClass> map;
	static {
		map = new HashMap<Integer, NodeClass>();
		for (NodeClass i : NodeClass.values()) 
			map.put(i.value, i);        
	}

	/**
	 * <p>valueOf.</p>
	 *
	 * @param value a int.
	 * @return a {@link org.opcfoundation.ua.core.NodeClass} object.
	 */
	public static NodeClass valueOf(int value)
	{
		return map.get(value);
	}
	
	/**
	 * <p>valueOf.</p>
	 *
	 * @param value a {@link java.lang.Integer} object.
	 * @return a {@link org.opcfoundation.ua.core.NodeClass} object.
	 */
	public static NodeClass valueOf(Integer value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	/**
	 * <p>valueOf.</p>
	 *
	 * @param value a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @return a {@link org.opcfoundation.ua.core.NodeClass} object.
	 */
	public static NodeClass valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	/**
	 * <p>valueOf.</p>
	 *
	 * @param value an array of int.
	 * @return an array of {@link org.opcfoundation.ua.core.NodeClass} objects.
	 */
	public static NodeClass[] valueOf(int[] value)
	{
		NodeClass[] result = new NodeClass[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	/**
	 * <p>valueOf.</p>
	 *
	 * @param value an array of {@link java.lang.Integer} objects.
	 * @return an array of {@link org.opcfoundation.ua.core.NodeClass} objects.
	 */
	public static NodeClass[] valueOf(Integer[] value)
	{
		NodeClass[] result = new NodeClass[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	/**
	 * <p>valueOf.</p>
	 *
	 * @param value an array of {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} objects.
	 * @return an array of {@link org.opcfoundation.ua.core.NodeClass} objects.
	 */
	public static NodeClass[] valueOf(UnsignedInteger[] value)
	{
		NodeClass[] result = new NodeClass[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	/**
	 * <p>getMask.</p>
	 *
	 * @param list a {@link org.opcfoundation.ua.core.NodeClass} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public static UnsignedInteger getMask(NodeClass...list) 
	{
		int result = 0;
		for (NodeClass c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	/**
	 * <p>getMask.</p>
	 *
	 * @param list a {@link java.util.Collection} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public static UnsignedInteger getMask(Collection<NodeClass> list) 
	{
		int result = 0;
		for (NodeClass c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	/**
	 * <p>getSet.</p>
	 *
	 * @param mask a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @return a {@link java.util.EnumSet} object.
	 */
	public static EnumSet<NodeClass> getSet(UnsignedInteger mask)
	{
		return getSet(mask.intValue());
	}
	
	/**
	 * <p>getSet.</p>
	 *
	 * @param mask a int.
	 * @return a {@link java.util.EnumSet} object.
	 */
	public static EnumSet<NodeClass> getSet(int mask)
	{
		List<NodeClass> res = new ArrayList<NodeClass>();
		for (NodeClass l : NodeClass.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		return EnumSet.copyOf(res);
	}	
	
}
