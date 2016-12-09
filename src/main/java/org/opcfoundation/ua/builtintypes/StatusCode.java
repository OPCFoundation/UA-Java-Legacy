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

import org.opcfoundation.ua.common.StatusCodeDescriptions;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.StatusCodes;


/**
 * A numeric identifier for a error or condition that is associated with a
 * value or an operation. StatusCode is immutable.
 *
 * TODO Add type-safe enums
 *
 * @see StatusCodes
 * @see StatusCodeDescriptions
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public final class StatusCode {	
	
	/** Constant <code>ID</code> */
	public static final NodeId ID = Identifiers.StatusCode;
	
	/** 
	 * Indicates whether the StatusCode represents a good, bad or uncertain 
	 * condition. 
	 */
	public static final int SEVERITY_MASK		= 0xC0000000;
	/** Constant <code>SEVERITY_GOOD=0x00000000</code> */
	public static final int SEVERITY_GOOD		= 0x00000000;
	/** Constant <code>SEVERITY_UNCERTAIN=0x40000000</code> */
	public static final int SEVERITY_UNCERTAIN	= 0x40000000;
	/** Constant <code>SEVERITY_BAD=0x80000000</code> */
	public static final int SEVERITY_BAD		= 0x80000000;		

	/** GOOD Result */
	public static final StatusCode GOOD = getFromBits(SEVERITY_GOOD);
	/** Constant <code>BAD</code> */
	public static final StatusCode BAD = getFromBits(SEVERITY_BAD);
		
	/** Reserved for future use. Shall always be zero. */
	public static final int RES1_MASK		= 0x30000000;
	
    /** 
     * The code is a numeric value assigned to represent different conditions. 
     * Each code has a symbolic name and a numeric value. All descriptions in 
     * the OPC UA specification refer to the symbolic name. Part 6 maps the 
     * symbolic names onto a numeric value.
     */
	public static final int SUBCODE_MASK	= 0x0FFF0000;
	
	/**
	 * Indicates that the structure of the associated data value has changed 
	 * since the last Notification. Clients should not process the data value 
	 * unless they re-read the metadata. Servers shall set this bit if the 
	 * DataTypeEncoding used for a Variable changes. Clause 7.23 describes how 
	 * the DataTypeEncoding is specified for a Variable.
	 * 
     * The bit is also set if the data type Attribute of the Variable changes. 
     * A Variable with data type BaseDataType does not require the bit to be 
     * set when the data type changes.
     * 
     * Servers shall also set this bit if the ArrayDimensions or the ValueRank 
     * Attribute or the EnumStrings Property of the DataType of the Variable 
     * changes.
     *
     * This bit is provided to warn Clients that parse complex data values that 
     * their parsing routines could fail because the serialized form of the data 
     * value has changed.
     * 
     * This bit has meaning only for StatusCodes returned as part of a data 
     * change Notification or the HistoryRead. StatusCodes used in other 
     * contexts shall always set this bit to zero.
	 */
	public static final int STRUCTURECHANGED_MASK = 0x00008000;
	
	/**
	 * Indicates that the semantics of the associated data value have changed. 
	 * Clients should not process the data value until they re-read the 
	 * metadata associated with the Variable.
	 *
	 * Servers should set this bit if the metadata has changed in way that 
	 * could cause application errors if the Client does not re-read the 
	 * metadata. For example, a change to the engineering units could create 
	 * problems if the Client uses the value to perform calculations.
	 * 
	 * Part 8 defines the conditions where a Server shall set this bit for a 
	 * DA Variable. Other specifications may define additional conditions. 
	 * A Server may define other conditions that cause this bit to be set.
	 *
	 * This bit has meaning only for StatusCodes returned as part of a data 
	 * change Notification or the HistoryRead. StatusCodes used in other 
	 * contexts shall always set this bit to zero.
	 */
	public static final int SEMANTICSCHANGED_MASK = 0x00004000;
	
	/** Reserved for future use. Shall always be zero. */
	public static final int RES2_MASK		= 0x00003000;
	
	/** The type of information contained in the info bits. */
	public static final int INFOTYPE_MASK	= 0x00000C00;
	
	/** The type of information is related to DataValue. */
	public static final int INFOTYPE_DATAVALUE	= 0x00000400;
	
	/** 
	 * Additional information bits that qualify the StatusCode. 
	 * The structure of these bits depends on the Info Type field.
	 */
	public static final int INFOBITS_MASK	= 0x000003FF;
	
	/** The limit bits associated with the data value. */
	public static final int LIMITBITS_MASK		= 0x00000300;
	
	/** The value is free to change. */
	public static final int LIMITBITS_NONE		= 0x00000000;
	
	/** The value is at the lower limit for the data source. */
	public static final int LIMITBITS_LOW		= 0x00000100;
	
	/** The value is at the higher limit for the data source. */
	public static final int LIMITBITS_HIGH		= 0x00000200;
	
	/** The value is constant and cannot change. */
	public static final int LIMITBITS_CONSTANT	= 0x00000300;	
	
	/** 
	 * If these bits are set, not every detected change has been returned 
	 * since the Server's queue buffer for the MonitoredItem reached 
	 * its limit and had to purge out data.
	 * <p>
	 * Consists of the {@link #INFOTYPE_DATAVALUE} and {@link #OVERFLOW_BIT}
	 */
	public static final int OVERFLOW_MASK	= 0x00000480;
	/** 
	 * If this bit is set, not every detected change has been returned 
	 * since the Server's queue buffer for the MonitoredItem reached 
	 * its limit and had to purge out data.
	 * <p>
	 * Must be used with the INFOTYPE_DATAVALUE.
	 * @see #OVERFLOW_MASK
	 */
	public static final int OVERFLOW_BIT	= 0x00000080;
	
	/** These bits are set only when reading historical data. They indicate 
	 * where the data value came from and provide information that affects 
	 * how the Client uses the data value. 
	 */
	public static final int HISTORIANBITS_MASK			= 0x0000001F;
	/** Constant <code>HISTORIANBITS_RAW=0x00000000</code> */
	public static final int HISTORIANBITS_RAW			= 0x00000000;
	/** Constant <code>HISTORIANBITS_CALCULATED=0x00000001</code> */
	public static final int HISTORIANBITS_CALCULATED	= 0x00000001;
	/** Constant <code>HISTORIANBITS_INTERPOLATED=0x00000002</code> */
	public static final int HISTORIANBITS_INTERPOLATED	= 0x00000002;
	/** Constant <code>HISTORIANBITS_RESERVED=0x00000003</code> */
	public static final int HISTORIANBITS_RESERVED		= 0x00000003;
	/** Constant <code>HISTORIANBITS_PARTIAL=0x00000004</code> */
	public static final int HISTORIANBITS_PARTIAL		= 0x00000004;
	/** Constant <code>HISTORIANBITS_EXTRADATA=0x00000008</code> */
	public static final int HISTORIANBITS_EXTRADATA		= 0x00000008;
	/** Constant <code>HISTORIANBITS_MULTIVALUE=0x00000010</code> */
	public static final int HISTORIANBITS_MULTIVALUE	= 0x00000010;
	
	private final int value;
	
	/**
	 * <p>getFromBits.</p>
	 *
	 * @param value a int.
	 * @return a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 */
	public static StatusCode getFromBits(int value)
	{
		return new StatusCode(value);
	}

	/**
	 * <p>Constructor for StatusCode.</p>
	 *
	 * @param value a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public StatusCode(UnsignedInteger value)
	{
		this.value = value.intValue();
	}
	
	/**
	 * @See Use getFromBits() 
	 * @param value
	 */
	private StatusCode(int value)
	{
		this.value = value;
	}
	
	/**
	 * <p>getValueAsIntBits.</p>
	 *
	 * @return a int.
	 */
	public int getValueAsIntBits() 
	{
		return value;
	}
	
	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public UnsignedInteger getValue()
	{
		return UnsignedInteger.getFromBits(value);
	}
	
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return value;
	}
	
    /** {@inheritDoc} */
    @Override
	public boolean equals(Object obj) {
	    if (!(obj instanceof StatusCode)) return false;
	    StatusCode other = (StatusCode) obj;
	    return value==other.value;
	}	
    
	/**
	 * <p>getDescription.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getDescription() {
		final String s = StatusCodeDescriptions.getStatusCodeDescription(value);
		if (s == null)
			return "";
		return s;
    }
	
    /**
     * <p>getName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
		if (value == GOOD.value)
			return "GOOD";
		if (value == BAD.value)
			return "BAD";
    	final String s = StatusCodeDescriptions.getStatusCode(value);
		if (s == null)
			return "";
		return s;
    }
    
    /** {@inheritDoc} */
    @Override
	public String toString() {
	    return 
	        String.format("%s (0x%08X) \"%s\"", 
	                getName(), value, 
	                getDescription());
	}
    
    // Redundant convenience methods
    
    /**
     * Tests if this status code is bad.
     *
     * @return true if bad
     */
    public boolean isBad() 
    {
        return (value & SEVERITY_MASK) == SEVERITY_BAD;
    }
    /**
     * Tests if this status code is good.
     *
     * @return true if good
     */
    public boolean isGood() 
    {        
        return (value & SEVERITY_MASK) == SEVERITY_GOOD;
    }
    /**
     * tests if a status code is not bad.
     *
     * @return true if not bad
     */
    public boolean isNotBad() 
    {
        return (value & SEVERITY_MASK) != SEVERITY_BAD;
    }
    /**
     * Tests if this status code is not good.
     *
     * @return true if not good
     */
    public boolean isNotGood() 
    {
        return (value & SEVERITY_MASK) != SEVERITY_GOOD;
    }
	
    /**
     * Tests if this status code is not uncertain.
     *
     * @return true if not uncertain
     */
    public boolean isNotUncertain() 
    {
    	return (value & SEVERITY_MASK) != SEVERITY_UNCERTAIN;
    }
    
    /**
     * Tests if this status code is not uncertain.
     *
     * @return true if not uncertain
     */
    public boolean isUncertain() 
    {
    	return (value & SEVERITY_MASK) == SEVERITY_UNCERTAIN;
    }
    
    /**
     * <p>isSemanticsChanged.</p>
     *
     * @return a boolean.
     */
    public boolean isSemanticsChanged()
    {
    	return (value & SEMANTICSCHANGED_MASK) != 0;
    }
    
    /**
     * <p>isStructureChanged.</p>
     *
     * @return a boolean.
     */
    public boolean isStructureChanged()
    {
    	return (value & STRUCTURECHANGED_MASK) != 0;
    }
    
	/**
	 * <p>isOverflow.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isOverflow()
	{
		return (value & OVERFLOW_MASK) != 0;
	}
	
	/**
	 * <p>getSeverity.</p>
	 *
	 * @return a int.
	 */
	public int getSeverity()
	{
		return value & SEVERITY_MASK;
	}
	
	/**
	 * <p>getSubcode.</p>
	 *
	 * @return a int.
	 */
	public int getSubcode()
	{
		return value & SUBCODE_MASK;
	}
		
	/**
	 * <p>getInfotype.</p>
	 *
	 * @return a int.
	 */
	public int getInfotype()
	{
		return value & INFOTYPE_MASK;
	}
	
	/**
	 * <p>getLimitBits.</p>
	 *
	 * @return a int.
	 */
	public int getLimitBits()
	{
		return value & LIMITBITS_MASK;
	}
	
	/**
	 * <p>getHistorianBits.</p>
	 *
	 * @return a int.
	 */
	public int getHistorianBits()
	{
		return value & HISTORIANBITS_MASK;
	}
	
	/**
	 * Matches argument against subcode and severity.
	 * This method can be used to compare statuscode to values in {@link StatusCodes}.
	 *
	 * @param statusCode a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @return true if subcode and severity matches
	 * @see StatusCode#equalsStatusCode(StatusCode)
	 */
	public boolean isStatusCode(UnsignedInteger statusCode)
	{
		int mask = SUBCODE_MASK | SEVERITY_MASK;
		return ( statusCode.intValue() & mask ) == ( value & mask ); 
	}

	/**
	 * Check if the status codes equal to severity and subcode, ignoring the lowest bits of the code.
	 *
	 * @param statusCode the StatusCode to compare this one to
	 * @return true if the codes are equal when masked with SEVERITY_MASK and SUBCODE_MASK
	 */
	public boolean equalsStatusCode(StatusCode statusCode) {
		return isStatusCode(statusCode.getValue());
	}
	
}
