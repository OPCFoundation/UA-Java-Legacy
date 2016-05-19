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
