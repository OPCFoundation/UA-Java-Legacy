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
