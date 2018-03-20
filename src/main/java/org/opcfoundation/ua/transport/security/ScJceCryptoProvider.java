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

import java.io.UnsupportedEncodingException;
import java.security.Security;

import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.StringUtils;
import org.spongycastle.util.encoders.Base64;

/**
 * <p>ScJceCryptoProvider class.</p>
 *
 */
public class ScJceCryptoProvider extends JceCryptoProvider implements CryptoProvider {

	/**
	 * <p>Constructor for ScJceCryptoProvider.</p>
	 */
	public ScJceCryptoProvider() {
		super();
		CryptoUtil.setSecurityProviderName("SC");
		this.provider = Security.getProvider("SC");
	}

	/** {@inheritDoc} */
	@Override
	public byte[] base64Decode(String string) {
		return Base64.decode(StringUtils.removeWhitespace(string));
	}

	/** {@inheritDoc} */
	@Override
	public String base64Encode(byte[] bytes) {
		try {
			return new String(Base64.encode(bytes), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
