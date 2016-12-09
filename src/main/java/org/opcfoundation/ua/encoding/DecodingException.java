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

package org.opcfoundation.ua.encoding;

import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;

/**
 * <p>DecodingException class.</p>
 */
public class DecodingException extends ServiceResultException {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for DecodingException.</p>
	 */
	public DecodingException() {
		super(StatusCodes.Bad_DecodingError);
	}
	
	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param e a {@link java.lang.Exception} object.
	 */
	public DecodingException(Exception e) {
		super(StatusCodes.Bad_DecodingError, e, e.getMessage());
	}
	
	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param e a {@link java.lang.Exception} object.
	 * @param message a {@link java.lang.String} object.
	 */
	public DecodingException(Exception e, String message) {
		super(StatusCodes.Bad_DecodingError, e, message);
	}
	
	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param reason a {@link java.lang.Throwable} object.
	 */
	public DecodingException(Throwable reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param statusCode a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @param reason a {@link java.lang.Throwable} object.
	 */
	public DecodingException(UnsignedInteger statusCode, Throwable reason) {
		super(statusCode, reason);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param e a {@link java.lang.Exception} object.
	 */
	public DecodingException(String message, Exception e) {
		super(StatusCodes.Bad_DecodingError, e, message);
	}

	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public DecodingException(String message) {
		super(StatusCodes.Bad_DecodingError, message);
	}

	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param statusCode a int.
	 * @param text a {@link java.lang.String} object.
	 */
	public DecodingException(int statusCode, String text) {
		super(statusCode, text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param statusCode a int.
	 */
	public DecodingException(int statusCode) {
		super(statusCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param statusCode a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 * @param text a {@link java.lang.String} object.
	 */
	public DecodingException(StatusCode statusCode, String text) {
		super(statusCode, text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param statusCode a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 * @param reason a {@link java.lang.Throwable} object.
	 * @param text a {@link java.lang.String} object.
	 */
	public DecodingException(StatusCode statusCode, Throwable reason,
			String text) {
		super(statusCode, reason, text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param statusCode a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 */
	public DecodingException(StatusCode statusCode) {
		super(statusCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param statusCode a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @param text a {@link java.lang.String} object.
	 */
	public DecodingException(UnsignedInteger statusCode, String text) {
		super(statusCode, text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>Constructor for DecodingException.</p>
	 *
	 * @param statusCode a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public DecodingException(UnsignedInteger statusCode) {
		super(statusCode);
		// TODO Auto-generated constructor stub
	}

	
	
}
