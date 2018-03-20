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

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.opcfoundation.ua.core.Identifiers;

/**
 * OPC UA DateTime.
 * The OPC UA dateTime is defined as follows (OPCUA Part 3)
 *
 * 7.29 UtcTime
 * This primitive DataType is used to define Coordinated Universal Time (UTC) values.
 * All time values conveyed between servers and clients in OPC UA are UTC values.
 * Clients must provide any conversions between UTC and local time.
 * This DataType is represented as a 64-bit signed integer which represents the number
 * of 100 nanosecond intervals since January 1, 1601.
 * [UA Part 6] defines details about this DataType.
 *
 * @author Jouni.Aro@prosys.fi
 */
public class DateTime implements Serializable, Comparable<DateTime> {

	/** Constant <code>DEFAULT_STR_FORMAT="%TD %TT.%07d %TZ"</code> */
	public static String DEFAULT_STR_FORMAT = "%TD %TT.%07d %TZ";
	/** Constant <code>ISO_8601_STR_FORMAT="%TF %TT.%07d %TZ"</code> */
	public static String ISO_8601_STR_FORMAT = "%TF %TT.%07d %TZ";
	private static String strFormat = DEFAULT_STR_FORMAT;
	private static boolean useLocalTimeInToString = false;
	


	private static final long serialVersionUID = 2701845992071716850L;

	/** Constant <code>ID</code> */
	public static final NodeId ID = Identifiers.DateTime;
	
	// The correct one
//    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
	
	// Fallback logic in java uses GMT anyway.
	private static final TimeZone UTC = TimeZone.getTimeZone("GMT");
    
	/** Constant <code>OffsetToGregorianCalendarZero=116444736000000000L</code> */
	public static final long OffsetToGregorianCalendarZero = 116444736000000000L; // Diff between 1970/1/1 and 1601/1/1

	/** Constant <code>MIN_VALUE</code> */
	public static final DateTime MIN_VALUE = new DateTime(0);
	/** Constant <code>MAX_VALUE</code> */
	public static final DateTime MAX_VALUE = new DateTime(9999, Calendar.JANUARY, 1, 23, 59, 59, 0);
	private final long value;
	
	/**
	 * Constructs a new DateTime value, initializing it with the current UTC time.
	 */
	public DateTime() {
	    this(System.currentTimeMillis() * 10000 + OffsetToGregorianCalendarZero);
	}

	/**
	 * Constructs a new DateTime value, initializing it with an OPC UA time value with a resolution of 100 nanoseconds.
	 *
	 * @param value a long.
	 */
	public DateTime(long value) {
		this.value = value;
	}

	/**
	 * Constructs a new DateTime value, is initializing it with a Calendar value.
	 *
	 * @param value a {@link java.util.Calendar} object.
	 */
	public DateTime(Calendar value) {
		this.value = value.getTimeInMillis()*10000 + OffsetToGregorianCalendarZero;
	}

	/**
	 * Constructs a new DateTime value, initializing it with the given time value (in the defined timezone).
	 * <p>
	 * Creates a {@link GregorianCalendar} using the parameters year..second; sets the TimeZone and adds nanoSeconds.
	 * <p>
	 * <b>Note</b>: that month is 0-based, and the values correspond to the constants, Calendar.JANUARY, etc.
	 *
	 * @param year a int.
	 * @param month a int.
	 * @param day a int.
	 * @param hour a int.
	 * @param minute a int.
	 * @param second a int.
	 * @param nanosecond a int.
	 * @param timeZone a {@link java.util.TimeZone} object.
	 */
	public DateTime(int year, int month, int day, int hour, int minute, int second, int nanosecond, TimeZone timeZone) {
		Calendar c = new GregorianCalendar(year, month, day, hour, minute, second);
		c.setTimeZone(timeZone);
		this.value = (nanosecond / 100) + c.getTimeInMillis()*10000 + OffsetToGregorianCalendarZero;
	}

	/**
	 * Constructs a new DateTime value, initializing it with the given time value (in UTC timezone).
	 * <p>
	 * Creates a {@link GregorianCalendar} using the parameters year..second and adds nanoSeconds.
	 * <p>
	 * <b>Note</b>: that month is 0-based, and the values correspond to the constants, Calendar.JANUARY, etc.
	 *
	 * @param year a int.
	 * @param month a int.
	 * @param day a int.
	 * @param hour a int.
	 * @param minute a int.
	 * @param second a int.
	 * @param nanosecond a int.
	 */
	public DateTime(int year, int month, int day, int hour, int minute, int second, int nanosecond) {
		this(year, month, day, hour, minute, second, nanosecond, UTC);
	}

	/**
	 * Constructs a new DateTime value, initializing it with the given time value (in UTC timezone).
	 * <p>
	 * Creates a {@link GregorianCalendar} using the parameters year..second.
	 * <p>
	 * <b>Note</b>: that month is 0-based, and the values correspond to the constants, Calendar.JANUARY, etc.
	 *
	 * @param year a int.
	 * @param month a int.
	 * @param day a int.
	 * @param hour a int.
	 * @param minute a int.
	 * @param second a int.
	 */
	public DateTime(int year, int month, int day, int hour, int minute, int second) {
		this(year, month, day, hour, minute, second, 0, UTC);
	}

	/**
	 * Get 1/10 microseconds (1 unit = 100 nanoseconds) between
	 * the time and midnight, January 1, 1601 UTC.
	 *
	 * @return time value
	 */
	public long getValue() {
		return value;
	}
	
	/**
	 * Get milliseconds between
	 * the time and midnight, January 1, 1601 UTC.
	 *
	 * @return time in milliseconds since January 1, 1601 UTC midnight
	 */
	public long getMilliSeconds() {
		return value / 10000;
	}

	/**
	 * Get standard Java milliseconds between
	 * the time and midnight, January 1, 1970 UTC.
	 * This equals to the value returned by the respective
	 * Calendar instance, returned by getCalendar.
	 *
	 * @return milliseconds since January 1, 1970 UTC midnight
	 */
	public long getTimeInMillis() {
		return (value - OffsetToGregorianCalendarZero) / 10000;
	}
	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
		final GregorianCalendar c = useLocalTimeInToString ? getLocalCalendar() : getUtcCalendar();
		long nanos = value % 10000000; 
		return String.format(strFormat, c, c, nanos, c); // yyyy/mm/dd HH:MM:SS.LLL
	}
	
    /**
     * <p>getUtcCalendar.</p>
     *
     * @return the dateTime converted to a new GregorianCalendar in UTC TimeZone.
     */
    public GregorianCalendar getUtcCalendar() {
    	return getCalendar(UTC);
	}

    /**
     * <p>getLocalCalendar.</p>
     *
     * @return the dateTime converted to a new GregorianCalendar in local TimeZone.
     */
    public GregorianCalendar getLocalCalendar() {
    	return getCalendar(TimeZone.getDefault());
	}

    /**
     * Return the time as a calendar value.
     *
     * @param timezone The desired TimeZone for the calendar
     * @return a new GregorianCalendar instance, initialized to the value of the DateTime.
     */
    public GregorianCalendar getCalendar(TimeZone timezone) {
    	GregorianCalendar c = new GregorianCalendar(timezone);
    	c.setTimeInMillis(getTimeInMillis());
    	return c;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return (int) (value ^ (value >> 32));
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DateTime other = (DateTime) obj;
		if (value != other.value)
			return false;
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(DateTime o) {
		if (value < o.value)
			return -1;
		if (value > o.value)
			return 1;
		return 0;
	}

	/**
	 * <p>currentTime.</p>
	 *
	 * @return an instance of DateTime with the current time.
	 */
	public static DateTime currentTime() {
		return new DateTime();
	}

	/**
	 * Parse a DateTime value from a string. The valid string formats is the
	 * xs:dateTime format, as defined in [Part 6., 5.3.1.6]:
	 *
	 * <blockquote>All DateTime values shall be encoded as UTC times or with the
	 * time zone explicitly specified.
	 * <p>
	 * Correct:<br>
	 * 2002-10-10T00:00:00+05:00<br>
	 * 2002-10-09T19:00:00Z
	 * <p>
	 * Incorrect:<br>
	 * 2002-10-09T19:00:00 </blockquote>
	 *
	 * @param s the string to parse
	 * @return a new DateTime value, corresponding to the timestamp
	 * @throws java.text.ParseException if the DateTime cannot be parsed from s
	 */
	public static DateTime parseDateTime(String s) throws ParseException {
		// Cannot use SimpleDateFormat.parse, because it always gives a time in local timezone
		// or at least the Calendar interprets it so
		final int l = s.length();
		if (l < 10)
			throw new ParseException("Cannot parse DateTime from " + s + " expecting format 'yyyy-MM-dd'T'hh:mm:ssZ', for example '2011-04-13T11:47:12Z' for UTC timezone", 0);
		int year = Integer.parseInt(s.substring(0,4));
		int month = Integer.parseInt(s.substring(5,7));
		int day = Integer.parseInt(s.substring(8,10));
		int hour = (l < 13) ? 0 : Integer.parseInt(s.substring(11,13));
		int min = (l < 16) ? 0 : Integer.parseInt(s.substring(14,16));
		int sec = (l < 19) ? 0 : Integer.parseInt(s.substring(17,19));
		// Parse the timezone
		TimeZone timeZone = UTC;
		if (l > 19) {
			String tz = s.substring(19);
			if (tz.charAt(0) == '+' || tz.charAt(0) == '-')
				tz = "GMT" + tz;
			timeZone = TimeZone.getTimeZone(tz);
		}
		return new DateTime(year, month-1, day, hour, min, sec, 0, timeZone);
	}

	/**
	 * Create a new DateTime instance from standard Java milliseconds since the beginning of epoch, January 1, 1970 UTC.
	 *
	 * @param value a long.
	 * @return a {@link org.opcfoundation.ua.builtintypes.DateTime} object.
	 */
	public static DateTime fromMillis(long value) {
		return new DateTime(value * 10000 + OffsetToGregorianCalendarZero);
	}

	/**
	 * Get the format used for toString()
	 *
	 * @return the format
	 */
	public static String getStrFormat() {
		return strFormat;
	}
	
	/**
	 * Set the format used for toString()
	 *
	 * @param strFormat new format
	 */
	public static void setStrFormat(String strFormat) {
		DateTime.strFormat = strFormat;
	}

	/**
	 *  If this is true, then DateTime.toString should print local timestamps and Utc if this is false. Default is false.
	 *
	 * @return a boolean.
	 */
	public static boolean isUseLocalTimeInToString() {
		return useLocalTimeInToString;
	}

	/**
	 * If this is true, then DateTime.toString should print local timestamps and Utc if this is false. Default is false.
	 *
	 * @param useLocalTimeInToString a boolean.
	 */
	public static void setUseLocalTimeInToString(boolean useLocalTimeInToString) {
		DateTime.useLocalTimeInToString = useLocalTimeInToString;
	}
	
}
