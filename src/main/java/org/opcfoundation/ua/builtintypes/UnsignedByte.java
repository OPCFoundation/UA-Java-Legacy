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

package org.opcfoundation.ua.builtintypes;

import org.opcfoundation.ua.core.Identifiers;


/**
 * <p>UnsignedByte class.</p>
 *
 */
public final class UnsignedByte extends Number implements Comparable<UnsignedByte> {
		
	/** Constant <code>ID</code> */
	public static final NodeId ID = Identifiers.Byte;	
	
	private static final long serialVersionUID = 4691302796477290208L;

	private final int value;

	private static final UnsignedByte CACHE[] = new UnsignedByte[256];	

	/** Constant <code>L_MAX_VALUE=0xFFL</code> */
	public static final long L_MAX_VALUE = 0xFFL;
    /** Constant <code>L_MIN_VALUE=0L</code> */
    public static final long L_MIN_VALUE = 0L;
	/** Constant <code>MAX_VALUE</code> */
	public static final UnsignedByte MAX_VALUE;
	/** Constant <code>MIN_VALUE</code> */
	public static final UnsignedByte MIN_VALUE;

	/** Constant <code>ZERO</code> */
	public static final UnsignedByte ZERO = new UnsignedByte(0);
	/** Constant <code>ONE</code> */
	public static final UnsignedByte ONE = new UnsignedByte(1);

	static {
		CACHE[0] = ZERO;
		CACHE[1] = ONE;
	    for (int i=2; i<CACHE.length; i++)
	        CACHE[i] = new UnsignedByte(i);
	    MIN_VALUE = CACHE[0];
	    MAX_VALUE = CACHE[255];
	}

	/**
	 * <p>max.</p>
	 *
	 * @param v0 a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
	 * @param v1 a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
	 */
	public static UnsignedByte max(UnsignedByte v0, UnsignedByte v1)
    {    	
    	return v0.intValue()<v1.intValue() ? v1 : v0;
    }

    /**
     * <p>min.</p>
     *
     * @param v0 a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
     * @param v1 a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
     */
    public static UnsignedByte min(UnsignedByte v0, UnsignedByte v1)
    {    	
    	return v0.intValue()<v1.intValue() ? v0 : v1;
    }
    
    /**
     * <p>valueOf.</p>
     *
     * @param value a int.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
     */
    public static UnsignedByte valueOf(int value) {
        assertValueInRange(value);
        return CACHE[value];
    }
    
	/**
	 * <p>getFromBits.</p>
	 *
	 * @param value a byte.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
	 */
	public static UnsignedByte getFromBits(byte value)
	{
		return CACHE[value & 0xff];
	}
    
	/**
	 * <p>Constructor for UnsignedByte.</p>
	 */
	public UnsignedByte() {
		this.value = 0;
	}
	
	/**
	 * <p>Constructor for UnsignedByte.</p>
	 *
	 * @param value a int.
	 * @throws java.lang.IllegalArgumentException if any.
	 */
	public UnsignedByte(int value) throws IllegalArgumentException {
        assertValueInRange(value);      
		this.value = value;
	}

	/**
	 * <p>Constructor for UnsignedByte.</p>
	 *
	 * @param value a long.
	 * @throws java.lang.IllegalArgumentException if any.
	 */
	public UnsignedByte(long value) throws IllegalArgumentException {
        assertValueInRange(value);      
		this.value = (int) value;
	}

	/**
	 * <p>Constructor for UnsignedByte.</p>
	 *
	 * @param value a byte.
	 * @throws java.lang.IllegalArgumentException if any.
	 */
	public UnsignedByte(byte value) throws IllegalArgumentException {
        assertValueInRange(value);      
		this.value = value;
	}

	/**
	 * <p>Constructor for UnsignedByte.</p>
	 *
	 * @param valueString a {@link java.lang.String} object.
	 * @throws java.lang.IllegalArgumentException if any.
	 */
	public UnsignedByte(String valueString) throws IllegalArgumentException {
		int _value = Short.parseShort(valueString);
		if ((_value < UnsignedByte.MIN_VALUE.getValue())
				|| (_value > UnsignedByte.MAX_VALUE.getValue())) {
			throw new IllegalArgumentException("Value out of bounds!");
		}
		this.value = _value;
		assertValueInRange(value);		
	}

    /**
     * <p>assertValueInRange.</p>
     *
     * @param value a int.
     */
    public static void assertValueInRange(int value)
    {
        if (value < 0)
            throw new IllegalArgumentException("Data value underflow!");
        if (value > 255)
            throw new IllegalArgumentException("Data value overflow!");
    }

    /**
     * <p>assertValueInRange.</p>
     *
     * @param value a long.
     */
    public static void assertValueInRange(long value)
    {
        if (value < 0)
            throw new IllegalArgumentException("Data value underflow!");
        if (value > 255)
            throw new IllegalArgumentException("Data value overflow!");
    }

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a int.
	 */
	public int getValue() {
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public double doubleValue() {
		return (double) value;
	}

	/** {@inheritDoc} */
	@Override
	public float floatValue() {
		return (float) value;
	}

	/** {@inheritDoc} */
	@Override
	public int intValue() {
		return (int) value;
	}

	/** {@inheritDoc} */
	@Override
	public long longValue() {
		return (long) value;
	}
	
	/** {@inheritDoc} */
	@Override
	public byte byteValue() {
		return (byte) (value & 0xff);
	}

    /**
     * <p>toByteBits.</p>
     *
     * @return a byte.
     */
    public byte toByteBits() {
		return (byte) (value & 0xFF); 
    }
	
	/**
	 * <p>compareTo.</p>
	 *
	 * @param o a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
	 * @return a int.
	 */
	public int compareTo(UnsignedByte o) {
		return this.value - o.getValue();
	}
	
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
	    return value;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj.getClass().equals(UnsignedByte.class))) return false;
		UnsignedByte other = (UnsignedByte) obj;
		return value == other.value;	
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
	    return Integer.toString(value);
	}

	/**
	 * Parse an UnsignedByte value from a string
	 *
	 * @param s the string to parse, assumed to contain a positive Integer value
	 * @return the respective UnsignedInteger
	 */
	public static UnsignedByte parseUnsignedByte(String s) {
		return valueOf(Integer.parseInt(s));
	}
	
    /**
     * Parses the string argument as an unsigned byte similar to {@link Integer#parseInt(String, int)}
     *
	 * @param s the   string to parse, assumed to contain a positive Integer value
     * @param radix   the radix to be used while parsing <code>s</code>.
	 * @return the respective UnsignedInteger
	 */
	public static UnsignedByte parseUnsignedByte(String s, int radix) {
		return valueOf(Integer.parseInt(s, radix));
	}

	/**
	 * Increase the value by one. Note that this object is not changed, but a new one is created.
	 *
	 * @return a new UnsignedByte, increased by 1 from this one.
	 */
	public UnsignedByte inc() {
		return valueOf(getValue()+1);
	}
	
	/**
	 * Decrease the value by one. Note that this object is not changed, but a new one is created.
	 *
	 * @return a new UnsignedByte, decreased by 1 from this one.
	 * @throws java.lang.IllegalArgumentException if the value was 0 before the call
	 */
	public UnsignedByte dec() {
		return valueOf(getValue()-1);
	}
	
	/**
	 * Add a value. Note that this object is not changed, but a new one is created.
	 *
	 * @param increment the value to add to the current value
	 * @return a new UnsignedByte, increased by increment from this one.
	 */
	public UnsignedByte add(int increment) {
		return valueOf(getValue()+increment);
	}
	
	/**
	 * Add a value. Note that this object is not changed, but a new one is created.
	 *
	 * @param increment the value to add to the current value
	 * @return a new UnsignedByte, increased by increment from this one.
	 */
	public UnsignedByte add(UnsignedByte increment) {
		return valueOf(getValue()+increment.getValue());
	}
	
	/**
	 * Subtract a value from this value. Note that this object is not changed, but a new one is created.
	 *
	 * @param decrement the value to subtract from the current value
	 * @return a new UnsignedByte, decreased by decrement from this one.
	 * @throws java.lang.IllegalArgumentException if the decrement is bigger than the current value
	 */
	public UnsignedByte subtract(int decrement) {
		return valueOf(getValue()-decrement);
	}
	/**
	 * Subtract a value from this value. Note that this object is not changed, but a new one is created.
	 *
	 * @param decrement the value to subtract from the current value
	 * @return a new UnsignedByte, decreased by decrement from this one.
	 * @throws java.lang.IllegalArgumentException if the decrement is bigger than the current value
	 */
	public UnsignedByte subtract(UnsignedByte decrement) {
		return valueOf(getValue()-decrement.getValue());
	}	
}
