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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.opcfoundation.ua.builtintypes.BuiltinsMap;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.Enumeration;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.UnsignedLong;
import org.opcfoundation.ua.builtintypes.UnsignedShort;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.builtintypes.XmlElement;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.IdType;
import org.opcfoundation.ua.encoding.EncodeType;
import org.opcfoundation.ua.encoding.EncoderContext;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.IEncoder;
import org.opcfoundation.ua.encoding.utils.EncodeableDesc;
import org.opcfoundation.ua.utils.MultiDimensionArrayUtils;

/**
 * Calculates length in bytes of an encodeable if it were encoded with binary encoder.
 */
public class EncoderCalc implements IEncoder {
		
	EncoderContext ctx; 
	int length;

	/**
	 * <p>Constructor for EncoderCalc.</p>
	 */
	public EncoderCalc() 
	{		
	}
	
	/**
	 * <p>Getter for the field <code>length</code>.</p>
	 *
	 * @return a int.
	 */
	public int getLength()
	{
		return length;
	}
	
	/**
	 * <p>reset.</p>
	 */
	public void reset()
	{
		length = 0;
	}
	
	/**
	 * <p>getAndReset.</p>
	 *
	 * @return a int.
	 */
	public int getAndReset()
	{
		int result = length;
		length = 0;
		return result;
	}

	/**
	 * <p>getEncoderContext.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public EncoderContext getEncoderContext() {
		return ctx;
	}

	/**
	 * <p>setEncoderContext.</p>
	 *
	 * @param ctx a {@link org.opcfoundation.ua.encoding.EncoderContext} object.
	 */
	public void setEncoderContext(EncoderContext ctx) {
		this.ctx = ctx;
	}
	
	/** {@inheritDoc} */
	public void putBoolean(String fieldName, Boolean v)
	{
		length++;
	}

	/**
	 * <p>putBooleanArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Boolean} objects.
	 */
	public void putBooleanArray(String fieldName, Boolean[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + v.length;
	}	
	
	/**
	 * <p>putBooleanArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putBooleanArray(String fieldName, Collection<Boolean> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + v.size();
	}	
	
	/**
	 * <p>putSByte.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.lang.Byte} object.
	 */
	public void putSByte(String fieldName, Byte v)
	{
		length += 1;
	}
	
	/** {@inheritDoc} */
	public void putSByte(String fieldName, byte v)
	{
		length += 1;
	}

	/**
	 * <p>putSByte.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a int.
	 */
	public void putSByte(String fieldName, int v)
	{
		length += 1;
	}

	/**
	 * <p>putSByteArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Byte} objects.
	 */
	public void putSByteArray(String fieldName, Byte[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + v.length;
	}
	
	/** {@inheritDoc} */
	public void putSByteArray(String fieldName, Collection<Byte> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + v.size();
	}
	
	/** {@inheritDoc} */
	public void putByte(String fieldName, UnsignedByte v)
	{
		length += 1;
	}
	
	/**
	 * <p>putByteArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.UnsignedByte} objects.
	 */
	public void putByteArray(String fieldName, UnsignedByte[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + v.length;
	}
	
	/** {@inheritDoc} */
	public void putByteArray(String fieldName, Collection<UnsignedByte> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + v.size();
	}
	
	/** {@inheritDoc} */
	public void putInt16(String fieldName, Short v)
	{
		length += 2;
	}

	/**
	 * <p>putInt16.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a short.
	 */
	public void putInt16(String fieldName, short v)
	{
		length += 2;
	}
	
	/**
	 * <p>putInt16Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Short} objects.
	 */
	public void putInt16Array(String fieldName, Short[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 2*v.length;
	}	
	
	/** {@inheritDoc} */
	public void putInt16Array(String fieldName, Collection<Short> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 2*v.size();
	}	
	
	/** {@inheritDoc} */
	public void putUInt16(String fieldName, UnsignedShort v)
	{
		length += 2;
	}
	
	/**
	 * <p>putUInt16Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.UnsignedShort} objects.
	 */
	public void putUInt16Array(String fieldName, UnsignedShort[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 2*v.length;
	}
	
	/**
	 * <p>putUInt16Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putUInt16Array(String fieldName, Collection<UnsignedShort> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 2*v.size();
	}
	
	/**
	 * <p>putInt32.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.lang.Integer} object.
	 */
	public void putInt32(String fieldName, Integer v)
	{
		length += 4;
	}
	
	/** {@inheritDoc} */
	public void putInt32(String fieldName, int v)
	{
		length += 4;
	}
	
	/**
	 * <p>putInt32Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of int.
	 */
	public void putInt32Array(String fieldName, int[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 4*v.length;
	}
	/** {@inheritDoc} */
	public void putInt32Array(String fieldName, Collection<Integer> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 4*v.size();
	}	
	
	/**
	 * <p>putInt32Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Integer} objects.
	 */
	public void putInt32Array(String fieldName, Integer[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 4*v.length;
	}
		
	/** {@inheritDoc} */
	public void putUInt32(String fieldName, UnsignedInteger v)
	{
		length += 4;
	}
	
	/**
	 * <p>putUInt32Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} objects.
	 */
	public void putUInt32Array(String fieldName, UnsignedInteger[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 4*v.length;
	}	
	
	/** {@inheritDoc} */
	public void putUInt32Array(String fieldName, Collection<UnsignedInteger> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 4*v.size();
	}	
	
	/**
	 * <p>putInt64.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.lang.Long} object.
	 */
	public void putInt64(String fieldName, Long v)
	{
		length += 8;
	}
	
	/** {@inheritDoc} */
	public void putInt64(String fieldName, long v)
	{
		length += 8;
	}	
	
	/**
	 * <p>putInt64Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Long} objects.
	 */
	public void putInt64Array(String fieldName, Long[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 8*v.length;
	}
	
	/**
	 * <p>putInt64Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putInt64Array(String fieldName, Collection<Long> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 8*v.size();
	}
	
	/** {@inheritDoc} */
	public void putUInt64(String fieldName, UnsignedLong v)
	{
		length += 8;
	}
		
	/**
	 * <p>putUInt64Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.UnsignedLong} objects.
	 */
	public void putUInt64Array(String fieldName, UnsignedLong[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 8*v.length;
	}	
	
	/**
	 * <p>putUInt64Array.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putUInt64Array(String fieldName, Collection<UnsignedLong> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 8*v.size();
	}	
	
	/**
	 * <p>putFloat.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.lang.Float} object.
	 */
	public void putFloat(String fieldName, Float v)
	{
		length += 4;
	}
	
	/** {@inheritDoc} */
	public void putFloat(String fieldName, float v)
	{
		length += 4;
	}
	
	/**
	 * <p>putFloatArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Float} objects.
	 */
	public void putFloatArray(String fieldName, Float[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 4*v.length;
	}
	
	/**
	 * <p>putFloatArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putFloatArray(String fieldName, Collection<Float> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 4*v.size();
	}		
		
	/** {@inheritDoc} */
	public void putDouble(String fieldName, Double v)
	{
		length += 8;
	}
	
	/**
	 * <p>putDouble.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a double.
	 */
	public void putDouble(String fieldName, double v)
	{
		length += 8;
	}
	
	/**
	 * <p>putDoubleArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.Double} objects.
	 */
	public void putDoubleArray(String fieldName, Double[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 8*v.length;
	}	
	
	/**
	 * <p>putDoubleArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putDoubleArray(String fieldName, Collection<Double> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 8*v.size();
	}	
	
	/** {@inheritDoc} */
	public void putString(String fieldName, String v)
	{
		if (v==null) {
			length += 4;
		} else {
			length += 4 + v.getBytes(BinaryEncoder.UTF8).length;
		}
	}
	
	/**
	 * <p>putStringArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.lang.String} objects.
	 */
	public void putStringArray(String fieldName, String[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (int i=0; i<v.length; i++)
			putString(null, v[i]);
	}		
	
	/**
	 * <p>putStringArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putStringArray(String fieldName, Collection<String> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (String o : v)
			putString(null, o);
	}		
	
	/** {@inheritDoc} */
	public void putDateTime(String fieldName, DateTime v)
	{
		length += 8;
	}

	/**
	 * <p>putDateTimeArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.DateTime} objects.
	 */
	public void putDateTimeArray(String fieldName, DateTime[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 8*v.length;
	}			
	
	/** {@inheritDoc} */
	public void putDateTimeArray(String fieldName, Collection<DateTime> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 8*v.size();
	}			
	
	/** {@inheritDoc} */
	public void putGuid(String fieldName, UUID v)
	{
		length += 16;
	}
	
	/**
	 * <p>putGuidArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link java.util.UUID} objects.
	 */
	public void putGuidArray(String fieldName, UUID[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 16*v.length;
	}			
	
	/** {@inheritDoc} */
	public void putGuidArray(String fieldName, Collection<UUID> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 16*v.size();
	}			
	
	/**
     * <p>putByteString.</p>
     *
     * @param fieldName a {@link java.lang.String} object.
     * @param v an array of byte.
     */
    private void putByteString(String fieldName, byte[] v)
    {
        if (v==null) 
            length += 4;
        else {
            length += 4 + v.length;
        }       
    }
	
	/**
	 * <p>putByteString.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of byte.
	 */
	public void putByteString(String fieldName, ByteString v)
	{
	  putByteString(fieldName, ByteString.asByteArray(v));
	}
	
	/**
	 * <p>putByteStringArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of byte.
	 */
	public void putByteStringArray(String fieldName, ByteString[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (int i=0; i<v.length; i++)
			putByteString(null, v[i]);
	}				
	
	/** {@inheritDoc} */
	public void putByteStringArray(String fieldName, Collection<ByteString> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (ByteString o : v)
			putByteString(null, o);
	}				
	
	/** {@inheritDoc} */
	public void putXmlElement(String fieldName, XmlElement v)
	{
		if (v==null) 
			length += 4;
		else 
			putByteString(null, v.getData());
	}
	
	/**
	 * <p>putXmlElementArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.XmlElement} objects.
	 */
	public void putXmlElementArray(String fieldName, XmlElement[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (int i=0; i<v.length; i++)
			putXmlElement(null, v[i]);
	}	
	
	/** {@inheritDoc} */
	public void putXmlElementArray(String fieldName, Collection<XmlElement> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (XmlElement o : v)
			putXmlElement(null, o);
	}	
	
	/** {@inheritDoc} */
	public void putNodeId(String fieldName, NodeId v)
	{
		if (v==null) v = NodeId.NULL; 
		
		Object o = v.getValue();
		if (v.getIdType() == IdType.Numeric) {
			UnsignedInteger i = (UnsignedInteger) o;
			if (i.compareTo(UnsignedByte.MAX_VALUE)<=0 && v.getNamespaceIndex()==0)
			{
				length += 2;
			} else 
			if (i.compareTo(UnsignedShort.MAX_VALUE)<=0 && v.getNamespaceIndex()<256)
			{
				length += 4;
			} else {
				length += 7;
			}
		} else
			
		if (v.getIdType() == IdType.String) {
			length += 3;
			putString(null, (String)v.getValue());
		} else
			
		if (v.getIdType() == IdType.Opaque) {
			length += 3;
			putByteString(null, (ByteString) v.getValue());
		} else 
			
		if (v.getIdType() == IdType.Guid) {
			length += 19;
		}		
	}
	
	/**
	 * <p>putNodeIdArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.NodeId} objects.
	 */
	public void putNodeIdArray(String fieldName, NodeId[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (int i=0; i<v.length; i++)
			putNodeId(null, v[i]);
	}		

	/**
	 * <p>putNodeIdArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putNodeIdArray(String fieldName, Collection<NodeId> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (NodeId o : v)
			putNodeId(null, o);
	}		
	
	/** {@inheritDoc} */
	public void putExpandedNodeId(String fieldName, ExpandedNodeId v)
	{
		if (v==null) v = ExpandedNodeId.NULL;
		
//		byte upperBits = 0;
//		if (v.getNamespaceUri()!=null) upperBits |= 0x80;
//		if (v.getServerIndex()!=null) upperBits |= 0x40;
		
		Object o = v.getValue();
		if (v.getIdType() == IdType.Numeric) {
			UnsignedInteger i = (UnsignedInteger) o;
			if (i.compareTo(UnsignedByte.MAX_VALUE)<=0 && v.getNamespaceIndex()==0)
			{
				length += 2;
			} else 
			if (i.compareTo(UnsignedShort.MAX_VALUE)<=0 && v.getNamespaceIndex()<256)
			{
				length += 4;
			} else {
				length += 7;
			}
		}
			
		if (v.getIdType() == IdType.String) {
			length += 3;
			putString(null, (String)v.getValue());
		} else
			
		if (v.getIdType() == IdType.Opaque) {
			length += 3;
			putByteString(null, (ByteString) v.getValue());
		} else 
			
		if (v.getIdType() == IdType.Guid) {
			length += 3;
			putGuid(null,  (UUID) v.getValue() );
		}		
		
		if (v.getNamespaceUri()!=null) {
			putString(null, v.getNamespaceUri());
		}
		if (v.getServerIndex()!=null) {
			length += 4;
		}
	}
	
	/**
	 * <p>putExpandedNodeIdArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.ExpandedNodeId} objects.
	 */
	public void putExpandedNodeIdArray(String fieldName, ExpandedNodeId[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (int i=0; i<v.length; i++)
			putExpandedNodeId(null, v[i]);
	}		

	/**
	 * <p>putExpandedNodeIdArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putExpandedNodeIdArray(String fieldName, Collection<ExpandedNodeId> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (ExpandedNodeId o : v)
			putExpandedNodeId(null, o);
	}		
	
	/** {@inheritDoc} */
	public void putStatusCode(String fieldName, StatusCode v)
	{
		length += 4;
	}
	
	/**
	 * <p>putStatusCodeArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.StatusCode} objects.
	 */
	public void putStatusCodeArray(String fieldName, StatusCode[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 4*v.length;
	}		
	
	/**
	 * <p>putStatusCodeArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putStatusCodeArray(String fieldName, Collection<StatusCode> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4 + 4*v.size();
	}		
	
	/** {@inheritDoc} */
	public void putQualifiedName(String fieldName, QualifiedName v)
	{
		if (v==null) {
			length += 2;
			putString(null, null);
			return;
		}
		length += 2;
		putString(null, v.getName());
	}
	
	/**
	 * <p>putQualifiedNameArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.QualifiedName} objects.
	 */
	public void putQualifiedNameArray(String fieldName, QualifiedName[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (int i=0; i<v.length; i++)
			putQualifiedName(null, v[i]);
	}		
	
	/**
	 * <p>putQualifiedNameArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 */
	public void putQualifiedNameArray(String fieldName, Collection<QualifiedName> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (QualifiedName o : v)
			putQualifiedName(null, o);
	}		
	
	/** {@inheritDoc} */
	public void putLocalizedText(String fieldName, LocalizedText v)
	{
		if (v==null) {
			length += 1;
			return;
		}
		String locale = v.getLocaleId();
		String text = v.getText();
		length += 1;
		if (locale!=null) putString(null, locale);
		if (text!=null) putString(null, text);
	}
	
	/**
	 * <p>putLocalizedTextArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.LocalizedText} objects.
	 */
	public void putLocalizedTextArray(String fieldName, LocalizedText[] v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (int i=0; i<v.length; i++)
			putLocalizedText(null, v[i]);
	}	
	
	/** {@inheritDoc} */
	public void putLocalizedTextArray(String fieldName, Collection<LocalizedText> v)
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (LocalizedText o : v)
			putLocalizedText(null, o);
	}	
	
	/** {@inheritDoc} */
	public void putStructure(String fieldName, Structure v) throws EncodingException
	{
		if (v==null) {
			putNodeId(null, NodeId.ZERO); // Will throw error.
			length += 1;
			length += 4;
			return;
		}
		try {
			putNodeId(null, ctx.getNamespaceTable().toNodeId(v.getBinaryEncodeId()));
		} catch (ServiceResultException e) {
			throw new EncodingException("Could not get BinaryEncodeId for given Structure", e);
		}
		length += 1;
		length += 4;
		putEncodeable(fieldName, v);
	}

	/**
	 * <p>putStructureArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.Structure} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putStructureArray(String fieldName, Structure[] v) throws EncodingException
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (Structure o : v)
			putStructure(null, o);
	}

	/**
	 * <p>putStructureArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putStructureArray(String fieldName, Collection<Structure> v) throws EncodingException
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (Structure o : v)
			putStructure(null, o);
	}
	
	/** {@inheritDoc} */
	public void putExtensionObject(String fieldName, ExtensionObject v)
	throws EncodingException
	{
		if (v==null) {
			putNodeId(null, null); // Will throw error. 
			length += 1;
			return;
		}
		
	      //support lazy encoding
        if(!v.isEncoded()){
          putExtensionObject(fieldName, ExtensionObject.binaryEncode((Structure) v.getObject(), ctx));
          return;
        }
		
		putNodeId(null, ctx.toNodeId(v.getTypeId()));
		Object o = v.getObject();
		if (o==null) {
			length += 1;
		} else if (o instanceof byte[]) {
			length += 1;
			putByteString(null, (byte[])o);
		} else if (o instanceof XmlElement) {
			length += 1;
			putXmlElement(null, (XmlElement)o);
		} else {
			throw new EncodingException("Unexpected object "+o);
		}
	}
	
	/**
	 * <p>putExtensionObjectArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.ExtensionObject} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putExtensionObjectArray(String fieldName, ExtensionObject[] v) throws EncodingException
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (int i=0; i<v.length; i++)
			putExtensionObject(null, v[i]);
	}		

	/** {@inheritDoc} */
	public void putExtensionObjectArray(String fieldName, Collection<ExtensionObject> v) throws EncodingException
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (ExtensionObject o : v)
			putExtensionObject(null, o);
	}		
	
	/** {@inheritDoc} */
	public void putDataValue(String fieldName, DataValue v) throws EncodingException
	{
		if (v==null) {
			putSByte(null, 0);
			return;
		}
		int mask = 0;
		if (v.getValue()!=null) mask |= 1;		
		if (v.getStatusCode()!=null && !v.getStatusCode().equals(StatusCode.GOOD)) mask |= 2;
		if (v.getSourceTimestamp()!=null && !v.getSourceTimestamp().equals(DateTime.MIN_VALUE)) mask |= 4; 
		if (v.getServerTimestamp()!=null && !v.getServerTimestamp().equals(DateTime.MIN_VALUE)) mask |= 8;		
		if (v.getSourcePicoseconds()!=null && !v.getSourcePicoseconds().equals(UnsignedShort.MIN_VALUE)) mask |= 0x10;
		if (v.getServerPicoseconds()!=null && !v.getServerPicoseconds().equals(UnsignedShort.MIN_VALUE)) mask |= 0x20;
		
		length += 1;
		if ((mask & 1) == 1) putVariant(null, v.getValue());
		if ((mask & 2) == 2) putStatusCode(null, v.getStatusCode());
		if ((mask & 4) == 4) putDateTime(null, v.getSourceTimestamp());
		if ((mask & 8) == 8) putDateTime(null, v.getServerTimestamp());
		if ((mask & 0x10) == 0x10) putUInt16(null, v.getSourcePicoseconds());
		if ((mask & 0x20) == 0x20) putUInt16(null, v.getServerPicoseconds());
	}
	
	/**
	 * <p>putDataValueArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.DataValue} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putDataValueArray(String fieldName, DataValue[] v) throws EncodingException
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (int i=0; i<v.length; i++)
			putDataValue(null, v[i]);
	}		
	
	/** {@inheritDoc} */
	public void putDataValueArray(String fieldName, Collection<DataValue> v) throws EncodingException
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (DataValue o : v)
			putDataValue(null, o);
	}		
	
	/** {@inheritDoc} */
	public void putVariant(String fieldName, Variant v) throws EncodingException
	{
		if (v==null) {
			length += 1;
			return;
		}
		
		Object o = v.getValue();
		if (o==null) {
			length += 1;
			return;
		}		

		Class<?> compositeClass			= v.getCompositeClass();
		int builtinType;
		if (Structure.class.isAssignableFrom(compositeClass)
				|| Enumeration.class.isAssignableFrom(compositeClass))
			builtinType = 22;
		else
			builtinType = BuiltinsMap.ID_MAP.get(compositeClass);
		
		// Scalar
		if (!v.isArray()) {
			putSByte(null, builtinType);
			putScalar(null, builtinType, o);
			return;
		} 
		
		// Array
		int dim = v.getDimension();
		if (dim==1) {
			putSByte(null,  (builtinType | 0x80));
			putArray(null, builtinType, o);
			return;
		}
		
		// Multi-dimension array
		int dims[] = v.getArrayDimensions();
		int len = MultiDimensionArrayUtils.getLength(dims);
		int scalarLen = guessBuiltinSize(builtinType);
		Iterator<Object> i = MultiDimensionArrayUtils.arrayIterator(v.getValue(), v.getArrayDimensions());
		try {
			putSByte(null,  (builtinType | 0xC0));
			length += 4;
			if (scalarLen > 0) { 
				length += scalarLen * len;
			} else 
			{
				while (i.hasNext()) 
					putScalar(null, builtinType, i.next());
			}
			putInt32Array(null, dims);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new EncodingException("The inner dimensions of a multi-dimension must be consistent", e);
		}
	}	
	
	/**
	 * <p>putVariantArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v an array of {@link org.opcfoundation.ua.builtintypes.Variant} objects.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putVariantArray(String fieldName, Variant[] v) throws EncodingException
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (int i=0; i<v.length; i++)
			putVariant(null, v[i]);
	}		
	
	/**
	 * <p>putVariantArray.</p>
	 *
	 * @param fieldName a {@link java.lang.String} object.
	 * @param v a {@link java.util.Collection} object.
	 * @throws org.opcfoundation.ua.encoding.EncodingException if any.
	 */
	public void putVariantArray(String fieldName, Collection<Variant> v) throws EncodingException
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (Variant o : v)
			putVariant(null, o);
	}		
	
	/** {@inheritDoc} */
	public void putDiagnosticInfo(String fieldName, DiagnosticInfo v)
	{
		if (v==null) {
			putSByte(null, 0);
			return;
		}

		int mask = 0;
		if (v.getSymbolicId()!=null) mask |= 1;
		if (v.getNamespaceUriStr()!=null) mask |= 2;
		if (v.getLocalizedTextStr()!=null) mask |= 4;
		if (v.getLocaleStr()!=null) mask |= 8;
		if (v.getAdditionalInfo()!=null) mask |= 0x10;
		if (v.getInnerStatusCode()!=null) mask |= 0x20;
		if (v.getInnerDiagnosticInfo()!=null) mask |= 0x40;		
				
		putSByte(null, mask);
		if ((mask & 1) == 1) 
			length += 4;
		
		if ((mask & 2) == 2) 
			length += 4;
		
		if ((mask & 4) == 4) 
			length += 4;
		
		if ((mask & 8) == 8) 
			length += 4;
		
		if ((mask & 0x10) == 0x10) 
			putString(null, v.getAdditionalInfo());			
		
		if ((mask & 0x20) == 0x20) 
			putStatusCode(null, v.getInnerStatusCode());
		
		if ((mask & 0x40) == 0x40) 
			putDiagnosticInfo(null, v.getInnerDiagnosticInfo());	
	}	

	/** {@inheritDoc} */
	@Override
	public void putDiagnosticInfoArray(String fieldName, DiagnosticInfo[] v) 
	throws EncodingException 
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (DiagnosticInfo o : v)
			putDiagnosticInfo(null, o);
	}

	/** {@inheritDoc} */
	@Override
	public void putDiagnosticInfoArray(String fieldName, Collection<DiagnosticInfo> v)
	throws EncodingException 
	{
		if (v==null) {
			length += 4;
			return;
		}
		
		length += 4;
		for (DiagnosticInfo o : v)
			putDiagnosticInfo(null, o);
	}
	
	
	/** {@inheritDoc} */
	public void putEnumerationArray(String fieldName, Object array)
	{
		if (array==null) {
			length += 4;
			return;
		}
		int len = Array.getLength(array);
		length += 4;
		for (int i=0; i<len; i++)
			putEnumeration(null, (Enumeration)Array.get(array, i));
	}
	
	/** {@inheritDoc} */
	public void putEnumeration(String fieldName, Enumeration v)
	{
		//if (v==null)
			//throw new EncodingException("Cannot encode null value");
		length += 4;
	}

	/** {@inheritDoc} */
	public void putObject(String fieldName, Object o) throws EncodingException
	{
		if (o==null) throw new EncodingException("Cannot encode null value");
		Class<?> c = o.getClass();
		putObject(null, c, o);
	}
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public void putObject(String fieldName, Class<?> c, Object o) throws EncodingException
	{
		Integer bt = BuiltinsMap.ID_MAP.get(c);
		boolean array = c.isArray();
		if (bt!=null) {
			if (array) 
				putArray(null, bt, o);
			else
				putScalar(null, bt, o);
			return;
		} 
		
		if (!array && Enumeration.class.isAssignableFrom(c)) {
			putEnumeration(null, (Enumeration)o);
			return;
		}

		if (array && Enumeration.class.isAssignableFrom(c.getComponentType())) {
			putEnumerationArray(null, o);
			return;
		}
		
		Class<?> scalarClass = array ? c.getComponentType() : c; 
		if (array)
			putEncodeableArray(null, (Class<? extends IEncodeable>) scalarClass, o);
		else
			ctx.encodeableSerializer.calcEncodeable((Class<? extends IEncodeable>) scalarClass, (IEncodeable)o, this);
	}
	
	/**
	 * Guess builtin type
	 * @param builtinType
	 * @return -1 unknown, other builting scalar size
	 */
	int guessBuiltinSize(int builtinType)
	{
		switch (builtinType) {
		case 1: return 1; 
		case 2: return 1; 
		case 3: return 1; 
		case 4: return 2; 
		case 5: return 2; 
		case 6: return 4; 
		case 7: return 4; 
		case 8: return 8; 
		case 9: return 8; 
		case 10: return 4; 
		case 11: return 8; 
		case 13: return 8; 
		case 14: return 16;
		case 19: return 4;
		default: return -1; 
		}		
	}
	
	/** {@inheritDoc} */
	public void putScalar(String fieldName, int builtinType, Object o) throws EncodingException
	{
		switch (builtinType) {
		case 1: putBoolean(null, (Boolean) o); break;
		case 2: putSByte(null, (Byte) o); break;
		case 3: putByte(null, (UnsignedByte) o); break;
		case 4: putInt16(null, (Short) o); break;
		case 5: putUInt16(null, (UnsignedShort) o); break;
		case 6: putInt32(null, (Integer) o); break;
		case 7: putUInt32(null, (UnsignedInteger) o); break;
		case 8: putInt64(null, (Long) o); break;
		case 9: putUInt64(null, (UnsignedLong) o); break;
		case 10: putFloat(null, (Float) o); break;
		case 11: putDouble(null, (Double) o); break;
		case 12: putString(null, (String) o); break;
		case 13: putDateTime(null, (DateTime) o); break;
		case 14: putGuid(null, (UUID) o); break;
		case 15: putByteString(null, (ByteString) o); break;
		case 16: putXmlElement(null, (XmlElement) o); break;
		case 17: putNodeId(null, (NodeId) o); break;
		case 18: putExpandedNodeId(null, (ExpandedNodeId) o); break;
		case 19: putStatusCode(null, (StatusCode) o); break;
		case 20: putQualifiedName(null, (QualifiedName) o); break;
		case 21: putLocalizedText(null, (LocalizedText) o); break;
		case 22: {
			if (o instanceof Structure) 
				putStructure(null, (Structure) o); 
			else if (o instanceof Enumeration)
				putEnumeration(null, (Enumeration) o);
			else
				putExtensionObject(null, (ExtensionObject) o);
			break;
		}
		case 23: putDataValue(null, (DataValue) o); break;
		case 24: putVariant(null, (Variant) o); break;
		case 25: putDiagnosticInfo(null, (DiagnosticInfo) o); break;
		default: throw new EncodingException("cannot encode builtin type "+builtinType); 
		}
	}
	
	/** {@inheritDoc} */
	public void putArray(String fieldName, int builtinType, Object o) throws EncodingException
	{
		switch (builtinType) {
		case 1: putBooleanArray(null, (Boolean[]) o); break;
		case 2: putSByteArray(null, (Byte[]) o); break;
		case 3: putByteArray(null, (UnsignedByte[]) o); break;
		case 4: putInt16Array(null, (Short[]) o); break;
		case 5: putUInt16Array(null, (UnsignedShort[]) o); break;
		case 6: putInt32Array(null, (Integer[]) o); break;
		case 7: putUInt32Array(null, (UnsignedInteger[]) o); break;
		case 8: putInt64Array(null, (Long[]) o); break;
		case 9: putUInt64Array(null, (UnsignedLong[]) o); break;
		case 10: putFloatArray(null, (Float[]) o); break;
		case 11: putDoubleArray(null, (Double[]) o); break;
		case 12: putStringArray(null, (String[]) o); break;
		case 13: putDateTimeArray(null, (DateTime[]) o); break;
		case 14: putGuidArray(null, (UUID[]) o); break;
		case 15: putByteStringArray(null, (ByteString[]) o); break;
		case 16: putXmlElementArray(null, (XmlElement[]) o); break;
		case 17: putNodeIdArray(null, (NodeId[]) o); break;
		case 18: putExpandedNodeIdArray(null, (ExpandedNodeId[]) o); break;
		case 19: putStatusCodeArray(null, (StatusCode[]) o); break;
		case 20: putQualifiedNameArray(null, (QualifiedName[]) o); break;
		case 21: putLocalizedTextArray(null, (LocalizedText[]) o); break;
		case 22: {
			if (o instanceof ExtensionObject[]) 
				putExtensionObjectArray(null, (ExtensionObject[]) o); 
			else
				putStructureArray(null, (Structure[]) o); 
			break;
		}
		case 23: putDataValueArray(null, (DataValue[]) o); break;
		case 24: putVariantArray(null, (Variant[]) o); break;
		case 25: putDiagnosticInfoArray(null, (DiagnosticInfo[]) o); break;
		default: throw new EncodingException("cannot encode builtin type "+builtinType); 
		}
	}	
	
	/** {@inheritDoc} */
	public void putEncodeableArray(String fieldName, Class<? extends IEncodeable> clazz, Object array) throws ArrayIndexOutOfBoundsException, EncodingException, IllegalArgumentException
	{
		if (array==null) {
			length += 4;
			return;
		}
		int len = Array.getLength(array);
		length += 4;
		for (int i=0; i<len; i++) 
			ctx.encodeableSerializer.calcEncodeable(clazz, (IEncodeable)Array.get(array, i), this);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Encodes stucture without header
	 */
	@SuppressWarnings("unchecked")
	public void putEncodeable(String fieldName, IEncodeable s) throws EncodingException
	{		
		Class<IEncodeable> clazz			= (Class<IEncodeable>) s.getClass();
		ctx.encodeableSerializer.calcEncodeable(clazz, s, this);		
	}

	/**
	 * {@inheritDoc}
	 *
	 * Encodes stucture without header
	 */
	public void putEncodeable(String fieldName, Class<? extends IEncodeable> clazz, IEncodeable s) throws EncodingException
	{		
		ctx.encodeableSerializer.calcEncodeable(clazz, s, this);		
	}
	
	/**
	 * Encodes stucture without header
	 * @param s
	 * @throws EncodingException 
	 */
	void putEncodeable(String fieldName, IEncodeable s, EncodeableDesc si) throws EncodingException
	{		
		try {
			for (EncodeableDesc.FieldInfo fi : si.fields)
			{
				Field f					= fi.field;
				Object value			= s==null ? null : f.get(s);
				putObject(null, fi.type, value);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}	

	/**
	 * {@inheritDoc}
	 *
	 * Encodes structures including header (typeId, encoding type and length)
	 */
//	@SuppressWarnings("unchecked")
//	public void putStructure(IEncodeable s)
//	{
//		Class<IEncodeable> clazz = (Class<IEncodeable>) s.getClass();
//		if (!encodeableSerializer.getEncodeSet().contains(clazz))
//			throw new EncodingException("Cannot decode "+clazz);
//
//		length += 5;
//		encodeableSerializer.putMessage(s, encoder)
//		putNodeId(si.binaryId);
//		putEncodeable(s, si);
//	}
	@SuppressWarnings("unchecked")
	public void putMessage(IEncodeable s) throws EncodingException
	{
		Class<IEncodeable> clazz = (Class<IEncodeable>) s.getClass();
		try {
			putNodeId(null, ctx.getEncodeableNodeId(clazz, EncodeType.Binary));
		} catch (ServiceResultException e) {
			throw new EncodingException("Could not get BinaryEncodeId for the class", e);
		}
		ctx.encodeableSerializer.calcEncodeable(clazz, s, this);
	}

	/** {@inheritDoc} */
	@Override
	public void put(String fieldName, Object o) throws EncodingException {
		EncoderUtils.put(this, fieldName, o);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return length+"";
	}

	/** {@inheritDoc} */
	@Override
	public void put(String fieldName, Object o, Class<?> clazz) throws EncodingException {
		EncoderUtils.put(this, fieldName, o, clazz);
		
	}

	
}
