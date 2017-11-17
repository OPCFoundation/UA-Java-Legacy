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

/**
 * Unsigned Integer represents an integer number value
 * between 0 .. and 0xFFFFFFFF.
 * <p>
 * There is a static instance for values between 0..1023 which can be accessed using
 * static methods {@link UnsignedInteger#valueOf(long)} or {@link UnsignedInteger#getFromBits(int)}
 * <p>
 * This class is immutable - once it has been constructed its value cannot be
 * changed.
 * <p>
 * To use int as backend use {@link UnsignedInteger#toIntBits()} and {@link UnsignedInteger#getFromBits(int)}.
 */
public final class UnsignedInteger extends Number implements Comparable<Number> {

	// Initialization cycle
	//	public static final NodeId ID = Identifiers.UInt32;
	
    private static final UnsignedInteger CACHE[] = new UnsignedInteger[1024];
	/**
	 * <p>getFromBits.</p>
	 *
	 * @param value a int.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public static UnsignedInteger getFromBits(int value)
	{
		if (value>=0 && value<CACHE.length)
			return CACHE[value];
		UnsignedInteger result = new UnsignedInteger();
		result.value = value;
		return result;
	}
	
	/**
	 * <p>valueOf.</p>
	 *
	 * @param value a long.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public static UnsignedInteger valueOf(long value)
	{
		if (value>=0 && value<CACHE.length)
			return CACHE[(int)value];
		return new UnsignedInteger(value);
	}
    
    /**
     * <p>max.</p>
     *
     * @param v0 a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     * @param v1 a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     */
    public static UnsignedInteger max(UnsignedInteger v0, UnsignedInteger v1)
    {    	
    	return v0.longValue()<v1.longValue() ? v1 : v0;
    }

    /**
     * <p>min.</p>
     *
     * @param v0 a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     * @param v1 a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     */
    public static UnsignedInteger min(UnsignedInteger v0, UnsignedInteger v1)
    {    	
    	return v0.longValue()<v1.longValue() ? v0 : v1;
    }

    private static final long serialVersionUID = 8818590379317818155L;

    /** Constant <code>L_MAX_VALUE=0xFFFFFFFFL</code> */
    public static final long L_MAX_VALUE = 0xFFFFFFFFL;
    /** Constant <code>L_MIN_VALUE=0L</code> */
    public static final long L_MIN_VALUE = 0L;

    /** Constant <code>MAX_VALUE</code> */
    public static final UnsignedInteger MAX_VALUE = new UnsignedInteger(L_MAX_VALUE);
    /** Constant <code>MIN_VALUE</code> */
    public static final UnsignedInteger MIN_VALUE = new UnsignedInteger(L_MIN_VALUE);

	/** Constant <code>ZERO</code> */
	public static final UnsignedInteger ZERO = MIN_VALUE;
	/** Constant <code>ONE</code> */
	public static final UnsignedInteger ONE = new UnsignedInteger(1);

    private int value;

	static {
		CACHE[0] = ZERO;
		CACHE[1] = ONE;
	    for (int i=2; i<CACHE.length; i++)
	        CACHE[i] = new UnsignedInteger(i);
	}

	/**
	 * <p>Constructor for UnsignedInteger.</p>
	 */
	public UnsignedInteger() {
        this.value = 0;
    }

    /**
     * <p>Constructor for UnsignedInteger.</p>
     *
     * @param value a int.
     * @throws java.lang.IllegalArgumentException if any.
     */
    public UnsignedInteger(int value) throws IllegalArgumentException {
        if (value < 0)
            throw new IllegalArgumentException("Value underflow");
        this.value = value;
    }

    /**
     * <p>Constructor for UnsignedInteger.</p>
     *
     * @param value a long.
     * @throws java.lang.IllegalArgumentException if any.
     */
    public UnsignedInteger(long value) throws IllegalArgumentException {
        if ((value < 0) || (value > L_MAX_VALUE))
            throw new IllegalArgumentException("Value overflow");
        this.value = (int) value;
    }

    /**
     * <p>Constructor for UnsignedInteger.</p>
     *
     * @param value a {@link java.lang.String} object.
     * @throws java.lang.IllegalArgumentException if any.
     */
    public UnsignedInteger(String value) throws IllegalArgumentException {
        long longValue = Long.parseLong(value);
        if ((longValue < 0) || (longValue > L_MAX_VALUE))
            throw new IllegalArgumentException("Value overflow");
        this.value = (int) longValue;
    }

    /**
     * <p>Constructor for UnsignedInteger.</p>
     *
     * @param value a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     */
    public UnsignedInteger(UnsignedInteger value) {
    	this.value = value.value;
    }

    /**
     * <p>Constructor for UnsignedInteger.</p>
     *
     * @param value a {@link org.opcfoundation.ua.builtintypes.UnsignedByte} object.
     */
    public UnsignedInteger(UnsignedByte value) {
    	this.value = value.getValue();
    }

    /**
     * <p>Getter for the field <code>value</code>.</p>
     *
     * @return a long.
     */
    public long getValue() {
        return this.value & 0xffffffffL;
    }
    
	/** {@inheritDoc} */
	@Override
	public byte byteValue() {
		return (byte) (value & 0xff);
	}
	
    /** {@inheritDoc} */
    @Override
    public double doubleValue() {
        return (double) getValue();
    }

    /** {@inheritDoc} */
    @Override
    public float floatValue() {
        return (float) getValue();
    }

    /**
     * <p>toIntBits.</p>
     *
     * @return a int.
     */
    public int toIntBits() {
    	return value;
    }
    
    /** {@inheritDoc} */
    @Override
    public int intValue() {
        return value;
    }

    /** {@inheritDoc} */
    @Override
    public long longValue() {
        return this.value & 0xffffffffL;
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
		if (!(obj.getClass().equals(UnsignedInteger.class))) return false;
		UnsignedInteger other = (UnsignedInteger) obj;
		return value == other.value;
	}
        
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Long.toString(value & 0xFFFFFFFFL);
    }
    
	/**
	 * <p>and.</p>
	 *
	 * @param value a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public UnsignedInteger and(UnsignedInteger value)
    {
        return UnsignedInteger.getFromBits(value.value & this.value);
    }

    /**
     * <p>and.</p>
     *
     * @param value a int.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     */
    public UnsignedInteger and(int value)
    {
        return UnsignedInteger.getFromBits(this.value & value);
    }

    /**
     * <p>and.</p>
     *
     * @param value a long.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     */
    public UnsignedInteger and(long value)
    {
        return new UnsignedInteger(this.value & value);
    }

    /**
     * <p>or.</p>
     *
     * @param value a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     */
    public UnsignedInteger or(UnsignedInteger value)
    {
        return UnsignedInteger.getFromBits(value.value | this.value);
    }

    /**
     * <p>or.</p>
     *
     * @param value a int.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     */
    public UnsignedInteger or(int value)
    {
        return UnsignedInteger.getFromBits(this.value | value);
    }

    /**
     * <p>or.</p>
     *
     * @param value a long.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     */
    public UnsignedInteger or(long value)
    {
        return new UnsignedInteger(this.value | value);
    }

	/** {@inheritDoc} */
	@Override
	public int compareTo(Number o) {
		long x = longValue();
		long y = o.longValue();
	    return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}
	
    static {
		CACHE[0] = ZERO;
		CACHE[1] = ONE;
    	for (int i=2; i<CACHE.length; i++)
    		CACHE[i] = new UnsignedInteger(i);
    }

	/**
	 * Parse an UnsignedInteger value from a string
	 *
	 * @param s the string to parse, assumed to contain a positive Long value
	 * @return the respective UnsignedInteger
	 * @throws java.lang.NumberFormatException if the string cannot be parsed into an integer value
	 * @throws java.lang.IllegalArgumentException if the parsed value does not fit in the range of UnsignedInteger
	 */
	public static UnsignedInteger parseUnsignedInteger(String s) throws NumberFormatException, IllegalArgumentException{
		return valueOf(Long.parseLong(s));
	}

    /**
     * Parses the string argument as an unsigned integer similar to {@link Integer#parseInt(String, int)}
     *
	 * @param s the   string to parse, assumed to contain a positive Long value
     * @param radix   the radix to be used while parsing <code>s</code>.
	 * @return the respective UnsignedInteger
	 * @throws java.lang.NumberFormatException if the string cannot be parsed into an integer value
	 * @throws java.lang.IllegalArgumentException if the parsed value does not fit in the range of UnsignedInteger
	 */
	public static UnsignedInteger parseUnsignedInteger(String s, int radix) throws NumberFormatException, IllegalArgumentException{
		return valueOf(Long.parseLong(s, radix));
	}

	/**
	 * Increase the value by one. Note that this object is not changed, but a new one is created.
	 *
	 * @return a new UnsignedInteger, increased by 1 from this one.
	 */
	public UnsignedInteger inc() {
		return valueOf(getValue()+1);
	}
	
	/**
	 * Decrease the value by one. Note that this object is not changed, but a new one is created.
	 *
	 * @return a new UnsignedInteger, decreased by 1 from this one.
	 * @throws java.lang.IllegalArgumentException if the value was 0 before the call
	 */
	public UnsignedInteger dec() {
		return valueOf(getValue()-1);
	}
	
	/**
	 * Add a value. Note that this object is not changed, but a new one is created.
	 *
	 * @param increment the value to add to the current value
	 * @return a new UnsignedInteger, increased by increment from this one.
	 */
	public UnsignedInteger add(int increment) {
		return valueOf(getValue()+increment);
	}
	
	/**
	 * Add a value. Note that this object is not changed, but a new one is created.
	 *
	 * @param increment the value to add to the current value
	 * @return a new UnsignedInteger, increased by increment from this one.
	 */
	public UnsignedInteger add(long increment) {
		return valueOf(getValue()+increment);
	}

	/**
	 * Add a value. Note that this object is not changed, but a new one is created.
	 *
	 * @param increment the value to add to the current value
	 * @return a new UnsignedInteger, increased by increment from this one.
	 */
	public UnsignedInteger add(UnsignedInteger increment) {
		return valueOf(getValue()+increment.getValue());
	}
	
	/**
	 * Subtract a value from this value. Note that this object is not changed, but a new one is created.
	 *
	 * @param decrement the value to subtract from the current value
	 * @return a new UnsignedInteger, decreased by decrement from this one.
	 * @throws java.lang.IllegalArgumentException if the decrement is bigger than the current value
	 */
	public UnsignedInteger subtract(int decrement) {
		return valueOf(getValue()-decrement);
	}

	/**
	 * Subtract a value from this value. Note that this object is not changed, but a new one is created.
	 *
	 * @param decrement the value to subtract from the current value
	 * @return a new UnsignedInteger, decreased by decrement from this one.
	 * @throws java.lang.IllegalArgumentException if the decrement is bigger than the current value
	 */
	public UnsignedInteger subtract(long decrement) {
		return valueOf(getValue()-decrement);
	}

	/**
	 * Subtract a value from this value. Note that this object is not changed, but a new one is created.
	 *
	 * @param decrement the value to subtract from the current value
	 * @return a new UnsignedInteger, decreased by decrement from this one.
	 * @throws java.lang.IllegalArgumentException if the decrement is bigger than the current value
	 */
	public UnsignedInteger subtract(UnsignedInteger decrement) {
		return valueOf(getValue()-decrement.getValue());
	}
}
