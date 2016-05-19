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



public enum ModelChangeStructureVerbMask implements Enumeration {

    NodeAdded(1),
    NodeDeleted(2),
    ReferenceAdded(4),
    ReferenceDeleted(8),
    DataTypeChanged(16);
	

	public static final NodeId ID = Identifiers.ModelChangeStructureVerbMask;
	public static EnumSet<ModelChangeStructureVerbMask> NONE = EnumSet.noneOf( ModelChangeStructureVerbMask.class );
	public static EnumSet<ModelChangeStructureVerbMask> ALL = EnumSet.allOf( ModelChangeStructureVerbMask.class );
	
	private final int value;
	ModelChangeStructureVerbMask(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, ModelChangeStructureVerbMask> map;
	static {
		map = new HashMap<Integer, ModelChangeStructureVerbMask>();
		for (ModelChangeStructureVerbMask i : ModelChangeStructureVerbMask.values()) 
			map.put(i.value, i);        
	}

	public static ModelChangeStructureVerbMask valueOf(int value)
	{
		return map.get(value);
	}
	
	public static ModelChangeStructureVerbMask valueOf(Integer value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static ModelChangeStructureVerbMask valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static ModelChangeStructureVerbMask[] valueOf(int[] value)
	{
		ModelChangeStructureVerbMask[] result = new ModelChangeStructureVerbMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static ModelChangeStructureVerbMask[] valueOf(Integer[] value)
	{
		ModelChangeStructureVerbMask[] result = new ModelChangeStructureVerbMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static ModelChangeStructureVerbMask[] valueOf(UnsignedInteger[] value)
	{
		ModelChangeStructureVerbMask[] result = new ModelChangeStructureVerbMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static UnsignedInteger getMask(ModelChangeStructureVerbMask...list) 
	{
		int result = 0;
		for (ModelChangeStructureVerbMask c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static UnsignedInteger getMask(Collection<ModelChangeStructureVerbMask> list) 
	{
		int result = 0;
		for (ModelChangeStructureVerbMask c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static EnumSet<ModelChangeStructureVerbMask> getSet(UnsignedInteger mask)
	{
		return getSet(mask.intValue());
	}
	
	public static EnumSet<ModelChangeStructureVerbMask> getSet(int mask)
	{
		List<ModelChangeStructureVerbMask> res = new ArrayList<ModelChangeStructureVerbMask>();
		for (ModelChangeStructureVerbMask l : ModelChangeStructureVerbMask.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		return EnumSet.copyOf(res);
	}	
	
}
