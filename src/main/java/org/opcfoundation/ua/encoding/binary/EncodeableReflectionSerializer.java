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

package org.opcfoundation.ua.encoding.binary;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

import org.opcfoundation.ua.builtintypes.Enumeration;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncodeType;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IDecoder;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.IEncoder;
import org.opcfoundation.ua.encoding.utils.EncodeableDesc;
import org.opcfoundation.ua.encoding.utils.EncodeableDescTable;

/**
 * Serializes {@link IEncodeable}s using reflection.
 * This class can encode anything that implements IEncodeable.
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class EncodeableReflectionSerializer implements IEncodeableSerializer {
	
	EncodeableDescTable encodeableTable;
	Set<Class<? extends IEncodeable>> encodeSet;
	Set<ExpandedNodeId> decodeSet;
	
	/**
	 * <p>Constructor for EncodeableReflectionSerializer.</p>
	 *
	 * @param table a {@link org.opcfoundation.ua.encoding.utils.EncodeableDescTable} object.
	 */
	public EncodeableReflectionSerializer(EncodeableDescTable table)
	{
		this.encodeableTable = table;
		encodeSet = table.getClassMap().keySet();
		decodeSet = table.getBinIdMap().keySet();
	}
	
	/** {@inheritDoc} */
	@Override
	public void calcEncodeable(Class<? extends IEncodeable> clazz, IEncodeable encodeable, IEncoder calculator) 
	throws EncodingException 
	{		
		EncodeableDesc si = encodeableTable.get(clazz);
		if (si==null) throw new EncodingException("Cannot encode "+clazz);
		
		try {
			for (EncodeableDesc.FieldInfo fi : si.fields)
			{
				Field f					= fi.field;
				Object value			= encodeable==null ? null : f.get(encodeable);
				calculator.putObject(fi.field.getName(), fi.type, value);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public IEncodeable getEncodeable(Class<? extends IEncodeable> clazz, IDecoder decoder) throws DecodingException
	{
		EncodeableDesc info = encodeableTable.get(clazz);
		if (info==null) throw new DecodingException("Cannot decode "+clazz);
		try {
			IEncodeable result = info.clazz.newInstance();
			for (EncodeableDesc.FieldInfo fi : info.fields)
			{
				// Decode builtin type
				if (fi.builtinType>=0) {
					Object value = fi.isArray ? decoder.getArrayObject(fi.field.getName(), fi.builtinType) : decoder.getScalarObject(fi.field.getName(), fi.builtinType);
					fi.field.set(result, value);
					continue;
				} 
				
				// Decode encodeable 
				EncodeableDesc vi = encodeableTable.get((Class<? extends IEncodeable>) fi.type);
				if (vi!=null) {
					Object value = fi.isArray ? decoder.getEncodeableArray(fi.field.getName(), vi.clazz) : getEncodeable(vi.clazz, decoder);
					fi.field.set(result, value);
					continue;
				}
				
				// Decode enumeration
				if (!fi.isArray && Enumeration.class.isAssignableFrom( fi.type ))
				{
					Object value = decoder.getEnumeration(fi.field.getName(), (Class<Enumeration>) fi.type);
					fi.field.set(result, value);
					continue;					
				}
				
				// Decode enumeration array
				if (fi.isArray && Enumeration.class.isAssignableFrom( fi.type.getComponentType() ))
				{
					Object value = decoder.getEnumerationArray(fi.field.getName(), (Class<Enumeration>) fi.type.getComponentType());
					fi.field.set(result, value);
					continue;					
				}
 
				throw new DecodingException("Cannot decode "+fi.type);
			}
			// Fixes diagnostic infos to point string table of the message
			if (result instanceof ServiceResponse) {
				DecoderUtils.fixResponseHeader(  ((ServiceResponse)result).getResponseHeader() );
			}
			return result;			
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	

	/** {@inheritDoc} */
	@Override
	public void putEncodeable(Class<? extends IEncodeable> clazz,
			IEncodeable encodeable, IEncoder encoder) throws EncodingException
	{
		EncodeableDesc si = encodeableTable.get(clazz);	
		if (si==null) throw new EncodingException("Cannot encode "+clazz);
		
		try {
			for (EncodeableDesc.FieldInfo fi : si.fields)
			{
				Field f					= fi.field;
				Object value			= encodeable==null ? null : f.get(encodeable);
				encoder.putObject(fi.field.getName(), fi.type, value);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}	

	/** {@inheritDoc} */
	@Override
	public void getSupportedNodeIds(Collection<ExpandedNodeId> result) {
		result.addAll(decodeSet);
	}
	
	/** {@inheritDoc} */
	@Override
	public void getSupportedClasses(Collection<Class<? extends IEncodeable>> result)
	{
		result.addAll(encodeSet);
	}

	/** {@inheritDoc} */
	@Override
	public Class<? extends IEncodeable> getClass(ExpandedNodeId id) {
		EncodeableDesc info = encodeableTable.get(id);
		if (info == null) return null;
		return info.clazz;
	}
	
	/** {@inheritDoc} */
	@Override
	public ExpandedNodeId getNodeId(Class<? extends IEncodeable> clazz, EncodeType type) {
		EncodeableDesc info = encodeableTable.get(clazz);
		if (info == null) return null;
		if (type==EncodeType.Binary)
			return info.binaryId;
		if (type==EncodeType.Xml)
			return info.xmlId;
		return null;
	}

}
