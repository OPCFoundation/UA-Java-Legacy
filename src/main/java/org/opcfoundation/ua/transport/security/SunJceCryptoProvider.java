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
package org.opcfoundation.ua.transport.security;

import java.io.IOException;
import java.security.Security;

import org.opcfoundation.ua.utils.CryptoUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <p>SunJceCryptoProvider class.</p>
 *
 */
public class SunJceCryptoProvider extends JceCryptoProvider implements CryptoProvider {

	/**
	 * <p>Constructor for SunJceCryptoProvider.</p>
	 */
	public SunJceCryptoProvider() {
		CryptoUtil.setSecurityProviderName("SunJCE");
		this.provider = Security.getProvider("SunJCE");
	}

	/** {@inheritDoc} */
	@Override
	public byte[] base64Decode(String string) {
		// Probably better, but not available on the default jars!
		// return javax.xml.bind.DatatypeConverter.parseBase64Binary(string);
		BASE64Decoder bd = new BASE64Decoder();
		try {
			return bd.decodeBuffer(string);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String base64Encode(byte[] bytes) {
		// See above: base64Decode
		// return javax.xml.bind.DatatypeConverter.printBase64Binary(bytes);
		BASE64Encoder bd = new BASE64Encoder();
		return bd.encode(bytes);
	}

}
