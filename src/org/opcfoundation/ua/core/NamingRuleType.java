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



public enum NamingRuleType implements Enumeration {

    Mandatory(1),
    Optional(2),
    Constraint(3);
	

	public static final NodeId ID = Identifiers.NamingRuleType;
	public static EnumSet<NamingRuleType> NONE = EnumSet.noneOf( NamingRuleType.class );
	public static EnumSet<NamingRuleType> ALL = EnumSet.allOf( NamingRuleType.class );
	
	private final int value;
	NamingRuleType(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, NamingRuleType> map;
	static {
		map = new HashMap<Integer, NamingRuleType>();
		for (NamingRuleType i : NamingRuleType.values()) 
			map.put(i.value, i);        
	}

	public static NamingRuleType valueOf(int value)
	{
		return map.get(value);
	}
	
	public static NamingRuleType valueOf(Integer value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static NamingRuleType valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static NamingRuleType[] valueOf(int[] value)
	{
		NamingRuleType[] result = new NamingRuleType[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static NamingRuleType[] valueOf(Integer[] value)
	{
		NamingRuleType[] result = new NamingRuleType[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static NamingRuleType[] valueOf(UnsignedInteger[] value)
	{
		NamingRuleType[] result = new NamingRuleType[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static UnsignedInteger getMask(NamingRuleType...list) 
	{
		int result = 0;
		for (NamingRuleType c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static UnsignedInteger getMask(Collection<NamingRuleType> list) 
	{
		int result = 0;
		for (NamingRuleType c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static EnumSet<NamingRuleType> getSet(UnsignedInteger mask)
	{
		return getSet(mask.intValue());
	}
	
	public static EnumSet<NamingRuleType> getSet(int mask)
	{
		List<NamingRuleType> res = new ArrayList<NamingRuleType>();
		for (NamingRuleType l : NamingRuleType.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		return EnumSet.copyOf(res);
	}	
	
}
