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

package org.opcfoundation.ua.utils;

/**
 * Utility functions for manipulating string data.
 */
public class StringUtils {
	private static final String lineSeparator = System.getProperty("line.separator");

	/**
	 * @return the current system lineSeparator. Equals to 'System.getProperty("line.separator")'
	 */
	public static String lineSeparator() {
		return lineSeparator;
	}

	/**
	 * <p>Add line breaks to the string.</p>
	 * @param s the string to convert.
	 * @param lineLength the number of characters per line. Use 0 or negative to omit the line breaks.
	 * @return a {@link java.lang.String} with {@link #lineSeparator()} added after every lineLength characters. 
	 */
	public static String addLineBreaks(String s, int lineLength) {
		if (lineLength <= 0)
			return s;
		
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i + lineLength < s.length()) {
			sb.append(s.substring(i, i += lineLength));
			sb.append(lineSeparator);
		}
		sb.append(s.substring(i));
		return sb.toString();
	}

	/**
	 * <p>Remove line breaks from the string.</p>
	 * <p>Replaces all newline ("\n") and linefeed ("\r") characters in string with an empty string.
	 * @param string the string to convert
	 * @return the string without any line break characters.
	 */
	public static String removeLineBreaks(String string) {
	    return string.replaceAll("[\n\r]", "");
	}
	
	/**
	 * Returns a string which has all whitespace characters removed from the given string.
	 */
	public static String removeWhitespace(String string){
		return string.replaceAll("\\s", "");
	}
	
  /**
   * Make a null safe equals comparison of strings.
   * 
   * @param str1 first string
   * @param str2 second string
   * @return true, if strings are equal or if both are null
   */
  public static boolean equals(String str1, String str2) {
    return (str1 == null ? str2 == null : str1.equals(str2));
  }



}
