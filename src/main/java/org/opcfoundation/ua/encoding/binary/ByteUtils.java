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
package org.opcfoundation.ua.encoding.binary;

import java.io.ByteArrayOutputStream;

/**
 * Internal helper for byte operations within this package.
 *
 */
class ByteUtils {

	static byte[] concat(byte[] first, byte[] second) {
		ByteArrayOutputStream r = new ByteArrayOutputStream();
		r.write(first, 0, first.length);
		r.write(second, 0, second.length);
		return r.toByteArray();
	}

	static byte[] reverse(byte[] data) {
		if(data == null || data.length == 0) {
			return data;
		}
		
		byte[] r = new byte[data.length];
		for(int i = 0;i<data.length;i++) {
			r[i] = data[data.length-1-i];
		}
		return r;
	}

}
