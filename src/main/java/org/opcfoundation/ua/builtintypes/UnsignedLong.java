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

import java.math.BigInteger;

import org.opcfoundation.ua.core.Identifiers;

/**
 * Unsigned Long represents an integer number value
 * between 0 .. and 0xFFFFFFFFFFFFFFFF.
 * <p>
 * There is a static instance for values between 0..1023 which can be accessed using
 * static methods {@link UnsignedLong#valueOf(long)} or {@link UnsignedLong#getFromBits(long)}
 * <p>
 * This class is immutable - once it has been constructed its value cannot be changed.
 * <p>
 * To use int as backend use {@link UnsignedLong#toLongBits()} and {@link UnsignedLong#getFromBits(long)}.
 */
public final class UnsignedLong extends Number implements Comparable<Number> {
		
    private static final UnsignedLong CACHE[] = new UnsignedLong[1024];
	
	private static final long serialVersionUID = 1L;
	/** Constant <code>ID</code> */
	public static final NodeId ID = Identifiers.UInt64;
	
	/** Constant <code>SIZE=64</code> */
	public static final int SIZE = 64;
	private static final long L_MAX_VALUE = Long.MAX_VALUE;
	private static final long L_HI_BIT = Long.MIN_VALUE;
	
	private static final BigInteger BI_L_MAX_VALUE = new BigInteger(Long.toString(L_MAX_VALUE));
	private static final BigInteger BI_MAX_VALUE = new BigInteger("2").pow(SIZE).add(new BigInteger("-1"));
	private static final BigInteger BI_MIN_VALUE = new BigInteger("0");
	private static final BigInteger BI_MID_VALUE = new BigInteger("2").pow(SIZE-1);
	private static final double D_MID_VALUE = BI_MID_VALUE.doubleValue(); 
	private static final float F_MID_VALUE = BI_MID_VALUE.floatValue(); 
	/** Constant <code>MAX_VALUE</code> */
	public static final UnsignedLong MAX_VALUE = new UnsignedLong(BI_MAX_VALUE); 
	/** Constant <code>MIN_VALUE</code> */
	public static final UnsignedLong MIN_VALUE = new UnsignedLong(BI_MIN_VALUE);

	/** Constant <code>ZERO</code> */
	public static final UnsignedLong ZERO = MIN_VALUE;
	/** Constant <code>ONE</code> */
	public static final UnsignedLong ONE = new UnsignedLong(1);

	private long value;

	static {
		CACHE[0] = ZERO;
		CACHE[1] = ONE;
    	for (int i=2; i<CACHE.length; i++)
    		CACHE[i] = new UnsignedLong(i);
    }
	
	/**
	 * Create unsigned long from 64 bits
	 *
	 * @param bits a long.
	 * @return new or cached instance
	 */
	public static UnsignedLong getFromBits(long bits)
	{
		if (bits>=0 && bits<CACHE.length)
			return CACHE[(int)bits];		
		UnsignedLong result = new UnsignedLong(0);
		result.value = bits;
		return result;
	}

	/**
	 * Get cached or create new instance
	 *
	 * @param value a long.
	 * @return new or cached instance
	 */
	public static UnsignedLong valueOf(long value)
	{
		if (value>=0 && value<CACHE.length)
			return CACHE[(int)value];		
		return new UnsignedLong(value);
	}
	
	/**
	 * <p>Constructor for UnsignedLong.</p>
	 *
	 * @param value a {@link java.math.BigInteger} object.
	 * @throws java.lang.IllegalArgumentException if any.
	 */
	public UnsignedLong(BigInteger value) throws IllegalArgumentException {
		if (value.compareTo(BI_MIN_VALUE)<0) throw new IllegalArgumentException("Value underflow");
		if (value.compareTo(BI_MAX_VALUE)>0) throw new IllegalArgumentException("Value overflow");
		
		if (value.compareTo(BI_L_MAX_VALUE)<=0) 
		{
			this.value = value.longValue();
		} else {
			this.value = value.subtract(BI_MID_VALUE).longValue() | L_HI_BIT;	
		}	
	}

	/**
	 * <p>Constructor for UnsignedLong.</p>
	 *
	 * @param value a int.
	 */
	public UnsignedLong(int value) {
		if (value < 0)
			throw new IllegalArgumentException("Value underflow");
		this.value = value;
	}

	/**
	 * Construct UnsignedLong from long. If long is negative, its upper bit is
	 * intrepreted as 0x8000000000000000.
	 *
	 * @param value a long.
	 */
	public UnsignedLong(long value) {
		if (value < 0)
			throw new IllegalArgumentException("Value underflow");
		this.value = value;
	}

	/**
	 * <p>Constructor for UnsignedLong.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public UnsignedLong(String value) {
		BigInteger bi = new BigInteger(value);
		if (bi.compareTo(BI_MIN_VALUE)<0) throw new IllegalArgumentException("Value underflow");
		if (bi.compareTo(BI_MAX_VALUE)>0) throw new IllegalArgumentException("Value overflow");
		
		if (bi.compareTo(BI_L_MAX_VALUE)<0) 
		{
			this.value = bi.longValue();
		} else {
			this.value = bi.subtract(BI_MID_VALUE).longValue() | L_HI_BIT;	
		}		
	}

	/**
	 * <p>bigIntegerValue.</p>
	 *
	 * @return a {@link java.math.BigInteger} object.
	 */
	public BigInteger bigIntegerValue() {
		// negative value
		if ((value & L_HI_BIT) == L_HI_BIT)		
			return BigInteger.valueOf(value & L_MAX_VALUE).add(BI_MID_VALUE);		
		return BigInteger.valueOf(value);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleValue() {
		if ((value & L_HI_BIT) == L_HI_BIT)		
			return (double)(value & L_MAX_VALUE) + D_MID_VALUE;
		return (double)value;
	}

	/** {@inheritDoc} */
	@Override
	public float floatValue() {
		if ((value & L_HI_BIT) == L_HI_BIT)		
			return (float)(value & L_MAX_VALUE) + F_MID_VALUE;
		return (float)value;
	}

	/** {@inheritDoc} */
	@Override
	public int intValue() {
		if ((value & L_HI_BIT) == L_HI_BIT)		
			return (int)(value & Integer.MAX_VALUE) | Integer.MIN_VALUE;
		return (int) value;
	}

	/** {@inheritDoc} */
	@Override
	public long longValue() {
		return value;
	}

	/**
	 * <p>compareTo.</p>
	 *
	 * @param o a {@link java.lang.Number} object.
	 * @return a int.
	 */
	public int compareTo(Number o) {
		//if just one has high bit
		if (((value & L_HI_BIT) == L_HI_BIT) ^ ((o.longValue() & L_HI_BIT) == L_HI_BIT)) {
			if ((value & L_HI_BIT) == L_HI_BIT) { return 1; } else { return -1; }
		} else {
			//else, a comparison needs to be done
			long x = longValue();
			long y = o.longValue();
		    return (x < y) ? -1 : ((x == y) ? 0 : 1);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj.getClass().equals(UnsignedLong.class))) return false;
		UnsignedLong other = (UnsignedLong) obj;
		return value == other.value;	
	}
	
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
	    return (int)value | (int)(value>>32);
	}	
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
		if ((value & L_HI_BIT) == L_HI_BIT)
			return bigIntegerValue().toString();
		return Long.toString(value);
    }
    
    /**
     * <p>toLongBits.</p>
     *
     * @return a long.
     */
    public long toLongBits() {
    	return value;
    }

	/**
	 * Parse an UnsignedLong value from a string
	 *
	 * @param s the string to parse, assumed to contain a positive Long value
	 * @return the respective UnsignedInteger
	 */
	public static UnsignedLong parseUnsignedLong(String s) {
		return new UnsignedLong(new BigInteger(s));
	}
	
    /**
     * Parses the string argument as an unsigned long similar to {@link Integer#parseInt(String, int)}
     *
	 * @param s the   string to parse, assumed to contain a positive Long value
     * @param radix   the radix to be used while parsing <code>s</code>.
	 * @return the respective UnsignedLong
	 * @throws java.lang.NumberFormatException if the string cannot be parsed into an integer value
	 * @throws java.lang.IllegalArgumentException if the parsed value does not fit in the range of UnsignedInteger
	 */
	public static UnsignedLong parseUnsignedLong(String s, int radix) throws NumberFormatException, IllegalArgumentException{
		return new UnsignedLong(new BigInteger(s, radix));
	}
    
	/**
	 * Increase the value by one. Note that this object is not changed, but a new one is created.
	 *
	 * @return a new UnsignedLong, increased by 1 from this one.
	 */
	public UnsignedLong inc() {
		return valueOf(getValue()+1);
	}
	
	/**
	 * Decrease the value by one. Note that this object is not changed, but a new one is created.
	 *
	 * @return a new UnsignedLong, decreased by 1 from this one.
	 * @throws java.lang.IllegalArgumentException if the value was 0 before the call
	 */
	public UnsignedLong dec() {
		return valueOf(getValue()-1);
	}
	
	/**
	 * Add a value. Note that this object is not changed, but a new one is created.
	 *
	 * @param increment the value to add to the current value
	 * @return a new UnsignedLong, increased by increment from this one.
	 */
	public UnsignedLong add(long increment) {
		long v = getValue()+increment;
		if (increment > 0 && (getValue() < 0 || v < getValue()))
			return new UnsignedLong(bigIntegerValue().add(BigInteger.valueOf(increment)));
		return valueOf(v);
	}
	
	/**
	 * Add a value. Note that this object is not changed, but a new one is created.
	 *
	 * @param increment the value to add to the current value
	 * @return a new UnsignedLong, increased by increment from this one.
	 */
	public UnsignedLong add(UnsignedLong increment) {
		long v = getValue()+increment.getValue();
		if (increment.getValue() > 0 && v < getValue())
			return new UnsignedLong(BigInteger.valueOf(getValue()).add(BigInteger.valueOf(increment.getValue())));
		return valueOf(v);
	}
	
	/**
	 * Subtract a value from this value. Note that this object is not changed, but a new one is created.
	 *
	 * @param decrement the value to subtract from the current value
	 * @return a new UnsignedLong, decreased by decrement from this one.
	 * @throws java.lang.IllegalArgumentException if the decrement is bigger than the current value
	 */
	public UnsignedLong subtract(long decrement) {
		if (getValue() >= 0 && getValue() > decrement)
			return valueOf(getValue()-decrement);
		BigInteger bi = bigIntegerValue();
		bi = bi.subtract(BigInteger.valueOf(decrement));
		return new UnsignedLong(bi);
	}
	/**
	 * Subtract a value from this value. Note that this object is not changed, but a new one is created.
	 *
	 * @param decrement the value to subtract from the current value
	 * @return a new UnsignedLong, decreased by decrement from this one.
	 * @throws java.lang.IllegalArgumentException if the decrement is bigger than the current value
	 */
	public UnsignedLong subtract(UnsignedLong decrement) {
		if (getValue() < 0 || decrement.getValue() < 0)
			return new UnsignedLong(bigIntegerValue().subtract(decrement.bigIntegerValue()));
		return valueOf(getValue()-decrement.getValue());
	}

	private long getValue() {
		return value;
	}
	}
