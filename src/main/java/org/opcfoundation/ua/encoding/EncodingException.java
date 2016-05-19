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
 *
 * 
 */
public class EncodingException extends ServiceResultException {

	private static final long serialVersionUID = 1L;

	public EncodingException() {
		super(new StatusCode(StatusCodes.Bad_EncodingError));
	}
	
	public EncodingException(Exception e) {
		super(new StatusCode(StatusCodes.Bad_EncodingError), e, e.getMessage());
	}
	
	public EncodingException(String message, Exception e) {
		super(new StatusCode(StatusCodes.Bad_EncodingError), e, message);
	}
	
	public EncodingException(String message) {
		super(new StatusCode(StatusCodes.Bad_EncodingError), message);
	}

	public EncodingException(int statusCode, String text) {
		super(statusCode, text);
		// TODO Auto-generated constructor stub
	}

	public EncodingException(int statusCode) {
		super(statusCode);
		// TODO Auto-generated constructor stub
	}

	public EncodingException(StatusCode statusCode, String text) {
		super(statusCode, text);
		// TODO Auto-generated constructor stub
	}

	public EncodingException(StatusCode statusCode, Throwable reason,
			String text) {
		super(statusCode, reason, text);
		// TODO Auto-generated constructor stub
	}

	public EncodingException(StatusCode statusCode) {
		super(statusCode);
		// TODO Auto-generated constructor stub
	}

	public EncodingException(Throwable reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	public EncodingException(UnsignedInteger statusCode, String text) {
		super(statusCode, text);
		// TODO Auto-generated constructor stub
	}

	public EncodingException(UnsignedInteger statusCode, Throwable reason) {
		super(statusCode, reason);
		// TODO Auto-generated constructor stub
	}

	public EncodingException(UnsignedInteger statusCode) {
		super(statusCode);
		// TODO Auto-generated constructor stub
	}
	
	
	
}
