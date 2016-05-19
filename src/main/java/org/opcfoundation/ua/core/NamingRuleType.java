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
