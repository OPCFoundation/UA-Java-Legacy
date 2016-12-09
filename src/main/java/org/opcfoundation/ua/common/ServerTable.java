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


/**
 * Table for keeping the ServerUris, which are exposed by the server via the property Server.ServerArray.
 */
public class ServerTable extends UriTable {
	/**
	 * <p>createFromArray.</p>
	 *
	 * @param serverArray an array of {@link java.lang.String} objects.
	 * @return a {@link org.opcfoundation.ua.common.ServerTable} object.
	 */
	public static ServerTable createFromArray(String[] serverArray) {
		ServerTable result = new ServerTable();
		result.addAll(serverArray);
		return result;
	}
	
}
