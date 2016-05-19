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
