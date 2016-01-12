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
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.opcfoundation.ua.builtintypes.Enumeration;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.AccessLevel;



public enum AccessLevel implements Enumeration {

    CurrentRead(1),
    CurrentWrite(2),
    HistoryRead(4),
    HistoryWrite(8),
    SemanticChange(16);
	
	
	public static EnumSet<AccessLevel> NONE = EnumSet.noneOf( AccessLevel.class );
	public static EnumSet<AccessLevel> READONLY = EnumSet.of( CurrentRead );
	public static EnumSet<AccessLevel> READWRITE = EnumSet.of( CurrentRead, CurrentWrite );
	public static EnumSet<AccessLevel> WRITEONLY = EnumSet.of( CurrentWrite );
	public static EnumSet<AccessLevel> HISTORYREAD = EnumSet.of( HistoryRead );
	public static EnumSet<AccessLevel> HISTORYREADWRITE = EnumSet.of( HistoryRead, HistoryWrite );
	public static EnumSet<AccessLevel> ALL = EnumSet.allOf( AccessLevel.class );

	private final int value;
	AccessLevel(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, AccessLevel> map;
	static {
		map = new HashMap<Integer, AccessLevel>();
		for (AccessLevel i : AccessLevel.values()) 
			map.put(i.value, i);        
	}

	public static AccessLevel valueOf(int value)
	{
		return map.get(value);
	}
	
	public static AccessLevel valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static AccessLevel[] valueOf(int[] value)
	{
		AccessLevel[] result = new AccessLevel[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static AccessLevel[] valueOf(Integer[] value)
	{
		AccessLevel[] result = new AccessLevel[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static AccessLevel[] valueOf(UnsignedInteger[] value)
	{
		AccessLevel[] result = new AccessLevel[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static UnsignedByte getMask(AccessLevel...list) 
	{
		byte result = 0;
		for (AccessLevel c : list)
			result |= c.value;
		return UnsignedByte.getFromBits(result);
	}	

	public static UnsignedByte getMask(Collection<AccessLevel> list) 
	{
		byte result = 0;
		for (AccessLevel c : list)
			result |= c.value;
		return UnsignedByte.getFromBits(result);
	}	
	
	public static EnumSet<AccessLevel> getSet(int mask)
	{
		List<AccessLevel> res = new ArrayList<AccessLevel>();
		for (AccessLevel l : AccessLevel.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		if (res.isEmpty())
			return NONE;
		return EnumSet.copyOf(res);
	}	

	public static EnumSet<AccessLevel> getSet(UnsignedInteger mask)
	{
		if (mask == null)
			return NONE;
		return getSet(mask.intValue());
	}
	
	public static EnumSet<AccessLevel> getSet(UnsignedShort mask)
	{
		if (mask == null)
			return NONE;
		return getSet(mask.intValue());
	}

	public static EnumSet<AccessLevel> getSet(UnsignedByte mask)
	{
		if (mask == null)
			return NONE;
		return getSet(mask.intValue());
	}
}
