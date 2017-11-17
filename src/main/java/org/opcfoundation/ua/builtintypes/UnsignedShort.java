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
 * Unsigned Short represents an integer number value
 * between 0 .. and 0xFFFF.
 * <p>
 * There is a static instance for values between 0..1023 which can be accessed
 * using static methods {@link UnsignedShort#valueOf(int)} or
 * {@link UnsignedShort#getFromBits(short)}
 * <p>
 * This class is immutable.
 * <p>
 * To create UnsignedShort from an integer value use
 * {@link UnsignedShort#toShortBits()} and
 * {@link UnsignedShort#getFromBits(short)}.
 */
public final class UnsignedShort extends Number implements Comparable<UnsignedShort> {
	
	private static final long serialVersionUID = 921127710458932841L;
	
	private static final UnsignedShort CACHE[] = new UnsignedShort[1024];

	/** Constant <code>ID</code> */
	public static final NodeId ID = Identifiers.UInt16;
	
	/** Constant <code>L_MAX_VALUE=0xFFFFL</code> */
	public static final long L_MAX_VALUE = 0xFFFFL;
    /** Constant <code>L_MIN_VALUE=0L</code> */
    public static final long L_MIN_VALUE = 0L;
	/** Constant <code>MAX_VALUE</code> */
	public static final UnsignedShort MAX_VALUE = new UnsignedShort(L_MAX_VALUE);
	/** Constant <code>MIN_VALUE</code> */
	public static final UnsignedShort MIN_VALUE = new UnsignedShort(L_MIN_VALUE);
	
	/** Constant <code>ZERO</code> */
	public static final UnsignedShort ZERO = MIN_VALUE;
	/** Constant <code>ONE</code> */
	public static final UnsignedShort ONE = new UnsignedShort(1);

    static {
		CACHE[0] = ZERO;
		CACHE[1] = ONE;
    	for (int i=2; i<CACHE.length; i++)
    		CACHE[i] = new UnsignedShort(i);
    }

    /**
     * <p>max.</p>
     *
     * @param v0 a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
     * @param v1 a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
     */
    public static UnsignedShort max(UnsignedShort v0, UnsignedShort v1)
    {    	
    	return v0.intValue()<v1.intValue() ? v1 : v0;
    }

    /**
     * <p>min.</p>
     *
     * @param v0 a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
     * @param v1 a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
     */
    public static UnsignedShort min(UnsignedShort v0, UnsignedShort v1)
    {    	
    	return v0.intValue()<v1.intValue() ? v0 : v1;
    }
    
	/**
	 * <p>getFromBits.</p>
	 *
	 * @param value a short.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
	 */
	public static UnsignedShort getFromBits(short value)
	{
		if (value>=0 && value<CACHE.length)
			return CACHE[value];
		UnsignedShort result = new UnsignedShort(0);
		result.value = value & 0xffff;
		return result;
	}
	
	/**
	 * <p>valueOf.</p>
	 *
	 * @param value a int.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
	 */
	public static UnsignedShort valueOf(int value)
	{
		if (value>=0 && value<CACHE.length)
			return CACHE[value];
		return new UnsignedShort(value);
	}    

	private int value;

	/**
	 * <p>Constructor for UnsignedShort.</p>
	 */
	public UnsignedShort() {
		this.value = 0;
	}

	/**
	 * <p>Constructor for UnsignedShort.</p>
	 *
	 * @param value a short.
	 */
	public UnsignedShort(short value) {
		if (value < 0)
			throw new IllegalArgumentException("Value underflow");
		this.value = value;
	}

	/**
	 * <p>Constructor for UnsignedShort.</p>
	 *
	 * @param value a int.
	 * @throws java.lang.IllegalArgumentException if any.
	 */
	public UnsignedShort(int value) throws IllegalArgumentException {
		if (value < 0 || value>= 0x10000)
			throw new IllegalArgumentException("Illegal value");
		this.value = value;
	}

	/**
	 * <p>Constructor for UnsignedShort.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @throws java.lang.IllegalArgumentException if any.
	 */
	public UnsignedShort(String value) throws IllegalArgumentException {
		int intvalue = Integer.parseInt(value);
		if (intvalue < 0 || intvalue>= 0x10000)
			throw new IllegalArgumentException("Illegal value");
		this.value = intvalue;
	}

	/**
	 * <p>Constructor for UnsignedShort.</p>
	 *
	 * @param value a {@link java.lang.Number} object.
	 */
	public UnsignedShort(Number value) {
		long lvalue = value.longValue();
		if (lvalue < 0 || lvalue>= 0x10000)
			throw new IllegalArgumentException("Illegal value");
		this.value = value.intValue();
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a int.
	 */
	public int getValue() {
		return this.value;
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
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public long longValue() {
		return (long) value;
	}
	
	/** {@inheritDoc} */
	@Override
	public short shortValue() {
		return (short) (value & 0xFFFF); 
	}

	/** {@inheritDoc} */
	@Override
	public byte byteValue() {
		return (byte) (value & 0xff);
	}

	/**
	 * <p>compareTo.</p>
	 *
	 * @param o a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
	 * @return a int.
	 */
	public int compareTo(UnsignedShort o) {
		return this.value - o.value;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return Integer.toString(value);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return value;
	}

    /**
     * <p>toShortBits.</p>
     *
     * @return a short.
     */
    public short toShortBits() {
		return (short) (value & 0xFFFF); 
    }
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj==this) return true;
		if (obj == null) return false;
		if (!(obj.getClass().equals(UnsignedShort.class)))
			return false;
		UnsignedShort other = (UnsignedShort) obj;
		return other.value == value;
	}

	/**
	 * Increase the value by one. Note that this object is not changed, but a new one is created.
	 *
	 * @return a new UnsignedShort, increased by 1 from this one.
	 */
	public UnsignedShort inc() {
		return valueOf(getValue()+1);
	}
	
	/**
	 * Decrease the value by one. Note that this object is not changed, but a new one is created.
	 *
	 * @return a new UnsignedShort, decreased by 1 from this one.
	 * @throws java.lang.IllegalArgumentException if the value was 0 before the call
	 */
	public UnsignedShort dec() {
		return valueOf(getValue()-1);
	}
	
	/**
	 * Add a value. Note that this object is not changed, but a new one is created.
	 *
	 * @param increment the value to add to the current value
	 * @return a new UnsignedShort, increased by increment from this one.
	 */
	public UnsignedShort add(int increment) {
		return valueOf(getValue()+increment);
	}
	
	/**
	 * Add a value. Note that this object is not changed, but a new one is created.
	 *
	 * @param increment the value to add to the current value
	 * @return a new UnsignedShort, increased by increment from this one.
	 */
	public UnsignedShort add(UnsignedShort increment) {
		return valueOf(getValue()+increment.getValue());
	}
	
	/**
	 * Subtract a value from this value. Note that this object is not changed, but a new one is created.
	 *
	 * @param decrement the value to subtract from the current value
	 * @return a new UnsignedShort, decreased by decrement from this one.
	 * @throws java.lang.IllegalArgumentException if the decrement is bigger than the current value
	 */
	public UnsignedShort subtract(int decrement) {
		return valueOf(getValue()-decrement);
	}
	/**
	 * Subtract a value from this value. Note that this object is not changed, but a new one is created.
	 *
	 * @param decrement the value to subtract from the current value
	 * @return a new UnsignedShort, decreased by decrement from this one.
	 * @throws java.lang.IllegalArgumentException if the decrement is bigger than the current value
	 */
	public UnsignedShort subtract(UnsignedShort decrement) {
		return valueOf(getValue()-decrement.getValue());
	}

	/**
	 * Parse an UnsignedShort value from a string
	 *
	 * @param s the string to parse, assumed to contain a positive Integer value
	 * @return the respective UnsignedShort
	 * @throws java.lang.NumberFormatException if the string cannot be parsed into an integer value
	 * @throws java.lang.IllegalArgumentException if the parsed value does not fit in the range of UnsignedShort
	 */
	public static UnsignedShort parseUnsignedShort(String s) throws NumberFormatException, IllegalArgumentException{
		return valueOf(Integer.parseInt(s));
	}
	
    /**
     * Parses the string argument as an unsigned byte similar to {@link Integer#parseInt(String, int)}
     *
	 * @param s the   string to parse, assumed to contain a positive Integer value
     * @param radix   the radix to be used while parsing <code>s</code>.
	 * @return the respective UnsignedShort
	 * @throws java.lang.NumberFormatException if the string cannot be parsed into an integer value
	 * @throws java.lang.IllegalArgumentException if the parsed value does not fit in the range of UnsignedShort
	 */
	public static UnsignedShort parseUnsignedShort(String s, int radix) throws NumberFormatException, IllegalArgumentException{
		return valueOf(Integer.parseInt(s, radix));
	}
}
