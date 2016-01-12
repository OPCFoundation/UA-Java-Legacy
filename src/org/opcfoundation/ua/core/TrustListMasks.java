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



public enum TrustListMasks implements Enumeration {

    None(0),
    TrustedCertificates(1),
    TrustedCrls(2),
    IssuerCertificates(4),
    IssuerCrls(8),
    All(15);
	

	public static final NodeId ID = Identifiers.TrustListMasks;
	public static EnumSet<TrustListMasks> NONE = EnumSet.noneOf( TrustListMasks.class );
	public static EnumSet<TrustListMasks> ALL = EnumSet.allOf( TrustListMasks.class );
	
	private final int value;
	TrustListMasks(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, TrustListMasks> map;
	static {
		map = new HashMap<Integer, TrustListMasks>();
		for (TrustListMasks i : TrustListMasks.values()) 
			map.put(i.value, i);        
	}

	public static TrustListMasks valueOf(int value)
	{
		return map.get(value);
	}
	
	public static TrustListMasks valueOf(Integer value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static TrustListMasks valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static TrustListMasks[] valueOf(int[] value)
	{
		TrustListMasks[] result = new TrustListMasks[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static TrustListMasks[] valueOf(Integer[] value)
	{
		TrustListMasks[] result = new TrustListMasks[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static TrustListMasks[] valueOf(UnsignedInteger[] value)
	{
		TrustListMasks[] result = new TrustListMasks[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static UnsignedInteger getMask(TrustListMasks...list) 
	{
		int result = 0;
		for (TrustListMasks c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static UnsignedInteger getMask(Collection<TrustListMasks> list) 
	{
		int result = 0;
		for (TrustListMasks c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static EnumSet<TrustListMasks> getSet(UnsignedInteger mask)
	{
		return getSet(mask.intValue());
	}
	
	public static EnumSet<TrustListMasks> getSet(int mask)
	{
		List<TrustListMasks> res = new ArrayList<TrustListMasks>();
		for (TrustListMasks l : TrustListMasks.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		return EnumSet.copyOf(res);
	}	
	
}
