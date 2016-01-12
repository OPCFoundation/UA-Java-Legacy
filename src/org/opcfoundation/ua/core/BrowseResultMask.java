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



public enum BrowseResultMask implements Enumeration {

    None(0),
    ReferenceTypeId(1),
    IsForward(2),
    NodeClass(4),
    BrowseName(8),
    DisplayName(16),
    TypeDefinition(32),
    All(63),
    ReferenceTypeInfo(3),
    TargetInfo(60);
	

	public static final NodeId ID = Identifiers.BrowseResultMask;
	public static EnumSet<BrowseResultMask> NONE = EnumSet.noneOf( BrowseResultMask.class );
	public static EnumSet<BrowseResultMask> ALL = EnumSet.allOf( BrowseResultMask.class );
	
	private final int value;
	BrowseResultMask(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, BrowseResultMask> map;
	static {
		map = new HashMap<Integer, BrowseResultMask>();
		for (BrowseResultMask i : BrowseResultMask.values()) 
			map.put(i.value, i);        
	}

	public static BrowseResultMask valueOf(int value)
	{
		return map.get(value);
	}
	
	public static BrowseResultMask valueOf(Integer value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static BrowseResultMask valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static BrowseResultMask[] valueOf(int[] value)
	{
		BrowseResultMask[] result = new BrowseResultMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static BrowseResultMask[] valueOf(Integer[] value)
	{
		BrowseResultMask[] result = new BrowseResultMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static BrowseResultMask[] valueOf(UnsignedInteger[] value)
	{
		BrowseResultMask[] result = new BrowseResultMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static UnsignedInteger getMask(BrowseResultMask...list) 
	{
		int result = 0;
		for (BrowseResultMask c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static UnsignedInteger getMask(Collection<BrowseResultMask> list) 
	{
		int result = 0;
		for (BrowseResultMask c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static EnumSet<BrowseResultMask> getSet(UnsignedInteger mask)
	{
		return getSet(mask.intValue());
	}
	
	public static EnumSet<BrowseResultMask> getSet(int mask)
	{
		List<BrowseResultMask> res = new ArrayList<BrowseResultMask>();
		for (BrowseResultMask l : BrowseResultMask.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		return EnumSet.copyOf(res);
	}	
	
}
