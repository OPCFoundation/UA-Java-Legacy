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



public enum HistoryUpdateType implements Enumeration {

    Insert(1),
    Replace(2),
    Update(3),
    Delete(4);
	

	public static final NodeId ID = Identifiers.HistoryUpdateType;
	public static EnumSet<HistoryUpdateType> NONE = EnumSet.noneOf( HistoryUpdateType.class );
	public static EnumSet<HistoryUpdateType> ALL = EnumSet.allOf( HistoryUpdateType.class );
	
	private final int value;
	HistoryUpdateType(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, HistoryUpdateType> map;
	static {
		map = new HashMap<Integer, HistoryUpdateType>();
		for (HistoryUpdateType i : HistoryUpdateType.values()) 
			map.put(i.value, i);        
	}

	public static HistoryUpdateType valueOf(int value)
	{
		return map.get(value);
	}
	
	public static HistoryUpdateType valueOf(Integer value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static HistoryUpdateType valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static HistoryUpdateType[] valueOf(int[] value)
	{
		HistoryUpdateType[] result = new HistoryUpdateType[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static HistoryUpdateType[] valueOf(Integer[] value)
	{
		HistoryUpdateType[] result = new HistoryUpdateType[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static HistoryUpdateType[] valueOf(UnsignedInteger[] value)
	{
		HistoryUpdateType[] result = new HistoryUpdateType[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static UnsignedInteger getMask(HistoryUpdateType...list) 
	{
		int result = 0;
		for (HistoryUpdateType c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static UnsignedInteger getMask(Collection<HistoryUpdateType> list) 
	{
		int result = 0;
		for (HistoryUpdateType c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static EnumSet<HistoryUpdateType> getSet(UnsignedInteger mask)
	{
		return getSet(mask.intValue());
	}
	
	public static EnumSet<HistoryUpdateType> getSet(int mask)
	{
		List<HistoryUpdateType> res = new ArrayList<HistoryUpdateType>();
		for (HistoryUpdateType l : HistoryUpdateType.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		return EnumSet.copyOf(res);
	}	
	
}
