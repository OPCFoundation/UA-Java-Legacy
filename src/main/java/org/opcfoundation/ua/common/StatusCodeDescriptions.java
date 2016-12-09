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

package org.opcfoundation.ua.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.Description;

/**
 * Reads statuscode description annotations from generated StatusCode class
 * using reflection.
 *
 * @see StatusCode
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class StatusCodeDescriptions {

    private static Map<UnsignedInteger, String> ERROR_NAMES = null;
    private static Map<UnsignedInteger, String> ERROR_DESCRIPTIONS = null;
    private static Map<String, UnsignedInteger> ERROR_NAMES_REV = null;

    private static final int MASK = StatusCode.SEVERITY_MASK | StatusCode.SUBCODE_MASK;      
    
    private static synchronized void readDescriptions() {
    	if (ERROR_NAMES!=null) return;
    	ERROR_NAMES = new HashMap<UnsignedInteger, String>();
    	ERROR_DESCRIPTIONS = new HashMap<UnsignedInteger, String>();
    	ERROR_NAMES_REV = new HashMap<String, UnsignedInteger>();
    	try {
			Class<?> clazz = Class.forName("org.opcfoundation.ua.core.StatusCodes");			
			
			for (Field f : clazz.getFields()) {
				if (!f.getType().equals(UnsignedInteger.class)) continue;
				f.setAccessible(true);
				UnsignedInteger statusCode = (UnsignedInteger) f.get(null);
				int code = statusCode.intValue() & MASK;
				String name = f.getName();
				Description _summary = f.getAnnotation(Description.class);
				String summary = _summary==null?"":_summary.value();				
				statusCode = UnsignedInteger.getFromBits(code);				
				ERROR_DESCRIPTIONS.put(statusCode, summary);
				ERROR_NAMES.put(statusCode, name);
				ERROR_NAMES_REV.put(name, statusCode);
			}			
		} catch (Exception e) {
		}
    }
    
    /**
     * <p>getStatusCode.</p>
     *
     * @param statuscode a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getStatusCode(int statuscode)
    {
    	readDescriptions();
    	UnsignedInteger i = UnsignedInteger.getFromBits(statuscode & MASK);
    	return ERROR_NAMES.get(i);
    }

    /**
     * <p>getStatusCodeDescription.</p>
     *
     * @param statuscode a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getStatusCodeDescription(int statuscode)
    {
    	readDescriptions();
    	UnsignedInteger i = UnsignedInteger.getFromBits(statuscode & MASK);
    	return ERROR_DESCRIPTIONS.get(i);
    }

    /**
     * <p>getStatusCodeDescription.</p>
     *
     * @param statusCode a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getStatusCodeDescription(StatusCode statusCode)
    {
    	readDescriptions();
    	UnsignedInteger i = UnsignedInteger.getFromBits(statusCode.getValueAsIntBits());
    	return ERROR_DESCRIPTIONS.get(i);
    }

    /**
     * <p>getStatusCode.</p>
     *
     * @param description a {@link java.lang.String} object.
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     */
    public static UnsignedInteger getStatusCode(String description)
    {
    	readDescriptions();
    	return ERROR_NAMES_REV.get(description);
    }
    
}
