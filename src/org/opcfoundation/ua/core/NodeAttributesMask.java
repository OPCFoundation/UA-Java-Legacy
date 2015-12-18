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



public enum NodeAttributesMask implements Enumeration {

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
    Value(2097152),
    All(4194303),
    BaseNode(1335396),
    Object(1335524),
    ObjectTypeOrDataType(1337444),
    Variable(4026999),
    VariableType(3958902),
    Method(1466724),
    ReferenceType(1371236),
    View(1335532);
	

	public static final NodeId ID = Identifiers.NodeAttributesMask;
	public static EnumSet<NodeAttributesMask> NONE = EnumSet.noneOf( NodeAttributesMask.class );
	public static EnumSet<NodeAttributesMask> ALL = EnumSet.allOf( NodeAttributesMask.class );
	
	private final int value;
	NodeAttributesMask(int value) {
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	private static final Map<Integer, NodeAttributesMask> map;
	static {
		map = new HashMap<Integer, NodeAttributesMask>();
		for (NodeAttributesMask i : NodeAttributesMask.values()) 
			map.put(i.value, i);        
	}

	public static NodeAttributesMask valueOf(int value)
	{
		return map.get(value);
	}
	
	public static NodeAttributesMask valueOf(Integer value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static NodeAttributesMask valueOf(UnsignedInteger value)
	{
		return value == null ? null : valueOf(value.intValue());
	}

	public static NodeAttributesMask[] valueOf(int[] value)
	{
		NodeAttributesMask[] result = new NodeAttributesMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static NodeAttributesMask[] valueOf(Integer[] value)
	{
		NodeAttributesMask[] result = new NodeAttributesMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}
	
	public static NodeAttributesMask[] valueOf(UnsignedInteger[] value)
	{
		NodeAttributesMask[] result = new NodeAttributesMask[value.length];
		for(int i=0; i<value.length; i++)
		  result[i] = valueOf(value[i]);
		return result;
	}

	public static UnsignedInteger getMask(NodeAttributesMask...list) 
	{
		int result = 0;
		for (NodeAttributesMask c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static UnsignedInteger getMask(Collection<NodeAttributesMask> list) 
	{
		int result = 0;
		for (NodeAttributesMask c : list)
			result |= c.value;
		return UnsignedInteger.getFromBits(result);
	}	

	public static EnumSet<NodeAttributesMask> getSet(UnsignedInteger mask)
	{
		return getSet(mask.intValue());
	}
	
	public static EnumSet<NodeAttributesMask> getSet(int mask)
	{
		List<NodeAttributesMask> res = new ArrayList<NodeAttributesMask>();
		for (NodeAttributesMask l : NodeAttributesMask.values()) 
			if ( (mask & l.value) == l.value )
				res.add(l);
		return EnumSet.copyOf(res);
	}	
	
}
