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



public enum EnumeratedTestType implements Enumeration {

    Red(1),
    Yellow(4),
    Green(5);
	

	public static final NodeId ID = Identifiers.EnumeratedTestType;
	public static EnumSet<EnumeratedTestType> NONE = EnumSet.noneOf( EnumeratedTestType.class );
	public static EnumSet<EnumeratedTestType> ALL = EnumSet.allOf( EnumeratedTestType.class );
	
	private final int value;
	EnumeratedTestType(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, EnumeratedTestType> map;
	static {
		map = new HashMap<Integer, EnumeratedTestType>();
		for (EnumeratedTestType i : EnumeratedTestType.values()) 
			map.put(i.value, i);        
	}

	public static EnumeratedTestType valueOf(int value)
	{
		return map.get(value);
	}
	
	public static EnumeratedTestType valueOf(Integer value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static EnumeratedTestType valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static EnumeratedTestType[] valueOf(int[] value)
	{
		EnumeratedTestType[] result = new EnumeratedTestType[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static EnumeratedTestType[] valueOf(Integer[] value)
	{
		EnumeratedTestType[] result = new EnumeratedTestType[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static EnumeratedTestType[] valueOf(UnsignedInteger[] value)
	{
		EnumeratedTestType[] result = new EnumeratedTestType[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static UnsignedInteger getMask(EnumeratedTestType...list) 
	{
		int result = 0;
		for (EnumeratedTestType c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static UnsignedInteger getMask(Collection<EnumeratedTestType> list) 
	{
		int result = 0;
		for (EnumeratedTestType c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static EnumSet<EnumeratedTestType> getSet(UnsignedInteger mask)
	{
		return getSet(mask.intValue());
	}
	
	public static EnumSet<EnumeratedTestType> getSet(int mask)
	{
		List<EnumeratedTestType> res = new ArrayList<EnumeratedTestType>();
		for (EnumeratedTestType l : EnumeratedTestType.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		return EnumSet.copyOf(res);
	}	
	
}
