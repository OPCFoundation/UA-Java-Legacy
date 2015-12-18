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



public enum OpenFileMode implements Enumeration {

    Read(1),
    Write(2),
    EraseExisiting(4),
    Append(8);
	

	public static final NodeId ID = Identifiers.OpenFileMode;
	public static EnumSet<OpenFileMode> NONE = EnumSet.noneOf( OpenFileMode.class );
	public static EnumSet<OpenFileMode> ALL = EnumSet.allOf( OpenFileMode.class );
	
	private final int value;
	OpenFileMode(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, OpenFileMode> map;
	static {
		map = new HashMap<Integer, OpenFileMode>();
		for (OpenFileMode i : OpenFileMode.values()) 
			map.put(i.value, i);        
	}

	public static OpenFileMode valueOf(int value)
	{
		return map.get(value);
	}
	
	public static OpenFileMode valueOf(Integer value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static OpenFileMode valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static OpenFileMode[] valueOf(int[] value)
	{
		OpenFileMode[] result = new OpenFileMode[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static OpenFileMode[] valueOf(Integer[] value)
	{
		OpenFileMode[] result = new OpenFileMode[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static OpenFileMode[] valueOf(UnsignedInteger[] value)
	{
		OpenFileMode[] result = new OpenFileMode[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static UnsignedInteger getMask(OpenFileMode...list) 
	{
		int result = 0;
		for (OpenFileMode c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static UnsignedInteger getMask(Collection<OpenFileMode> list) 
	{
		int result = 0;
		for (OpenFileMode c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static EnumSet<OpenFileMode> getSet(UnsignedInteger mask)
	{
		return getSet(mask.intValue());
	}
	
	public static EnumSet<OpenFileMode> getSet(int mask)
	{
		List<OpenFileMode> res = new ArrayList<OpenFileMode>();
		for (OpenFileMode l : OpenFileMode.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		return EnumSet.copyOf(res);
	}	
	
}
