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

package org.opcfoundation.ua.transport.tcp.impl;

import java.lang.reflect.Field;

import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.encoding.IEncodeable;

/**
 * ErrorMessage is a message used in TCP Handshake.
 */
public class ErrorMessage implements IEncodeable {

	UnsignedInteger Error;
	String Reason;
	
	static
	{
		try {
			fields = new Field[]{
					ErrorMessage.class.getDeclaredField("Error"),
			ErrorMessage.class.getDeclaredField("Reason"),
			};
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
	}
	
	private static Field[] fields;
	
	/**
	 * <p>Getter for the field <code>fields</code>.</p>
	 *
	 * @return the fields
	 */
	public static Field[] getFields() {
		return fields;
	}
	
	/**
	 * <p>Constructor for ErrorMessage.</p>
	 */
	public ErrorMessage() {}
	
	/**
	 * <p>Constructor for ErrorMessage.</p>
	 *
	 * @param error a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @param reason a {@link java.lang.String} object.
	 */
	public ErrorMessage(UnsignedInteger error, String reason) {
		this.Error = error;
		this.Reason = reason;
	}
	
	/**
	 * <p>Constructor for ErrorMessage.</p>
	 *
	 * @param code a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 * @param reason a {@link java.lang.String} object.
	 */
	public ErrorMessage(StatusCode code, String reason) {
		this.Error = code.getValue();
		this.Reason = reason;
	}
	
	/**
	 * <p>getError.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public UnsignedInteger getError() {
		return Error;
	}
	/**
	 * <p>setError.</p>
	 *
	 * @param error a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public void setError(UnsignedInteger error) {
		Error = error;
	}
	/**
	 * <p>getReason.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getReason() {
		return Reason;
	}
	/**
	 * <p>setReason.</p>
	 *
	 * @param reason a {@link java.lang.String} object.
	 */
	public void setReason(String reason) {
		Reason = reason;
	}		
	
}
