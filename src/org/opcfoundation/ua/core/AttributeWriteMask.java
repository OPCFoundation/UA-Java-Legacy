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



public enum AttributeWriteMask implements Enumeration {

    None(0),
    AccessLevel(1),
    ArrayDimensions(2),
    BrowseName(4),
    ContainsNoLoops(8),
    DataType(16),
    Description(32),
    DisplayName(64),
    EventNotifier(128),
    Executable(256),
    Historizing(512),
    InverseName(1024),
    IsAbstract(2048),
    MinimumSamplingInterval(4096),
    NodeClass(8192),
    NodeId(16384),
    Symmetric(32768),
    UserAccessLevel(65536),
    UserExecutable(131072),
    UserWriteMask(262144),
    ValueRank(524288),
    WriteMask(1048576),
    ValueForVariableType(2097152);
	

	public static final NodeId ID = Identifiers.AttributeWriteMask;
	public static EnumSet<AttributeWriteMask> NONE = EnumSet.noneOf( AttributeWriteMask.class );
	public static EnumSet<AttributeWriteMask> ALL = EnumSet.allOf( AttributeWriteMask.class );
	
	private final int value;
	AttributeWriteMask(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, AttributeWriteMask> map;
	static {
		map = new HashMap<Integer, AttributeWriteMask>();
		for (AttributeWriteMask i : AttributeWriteMask.values()) 
			map.put(i.value, i);        
	}

	public static AttributeWriteMask valueOf(int value)
	{
		return map.get(value);
	}
	
	public static AttributeWriteMask valueOf(Integer value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static AttributeWriteMask valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static AttributeWriteMask[] valueOf(int[] value)
	{
		AttributeWriteMask[] result = new AttributeWriteMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static AttributeWriteMask[] valueOf(Integer[] value)
	{
		AttributeWriteMask[] result = new AttributeWriteMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static AttributeWriteMask[] valueOf(UnsignedInteger[] value)
	{
		AttributeWriteMask[] result = new AttributeWriteMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static UnsignedInteger getMask(AttributeWriteMask...list) 
	{
		int result = 0;
		for (AttributeWriteMask c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static UnsignedInteger getMask(Collection<AttributeWriteMask> list) 
	{
		int result = 0;
		for (AttributeWriteMask c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static EnumSet<AttributeWriteMask> getSet(UnsignedInteger mask)
	{
		return getSet(mask.intValue());
	}
	
	public static EnumSet<AttributeWriteMask> getSet(int mask)
	{
		List<AttributeWriteMask> res = new ArrayList<AttributeWriteMask>();
		for (AttributeWriteMask l : AttributeWriteMask.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		return EnumSet.copyOf(res);
	}	
	
}
