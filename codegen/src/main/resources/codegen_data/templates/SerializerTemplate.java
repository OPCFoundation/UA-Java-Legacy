/* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.core;

import java.io.IOException;

import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IDecoder;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.IEncoder;
import org.opcfoundation.ua.encoding.binary.EncoderCalc;
import org.opcfoundation.ua.encoding.binary.DecoderUtils;
import org.opcfoundation.ua.encoding.binary.EncodeableReflectionSerializer;
import org.opcfoundation.ua.encoding.binary.IEncodeableSerializer;
import org.opcfoundation.ua.encoding.utils.AbstractSerializer;
import org.opcfoundation.ua.encoding.utils.SerializerComposition;
_imports_

/**
 * Code-generated encodeable serializer.
 * 
 * @see EncodeableReflectionSerializer Reflection based implementation
 * @see IEncodeableSerializer Serializer interface
 */
public class EncodeableSerializer extends SerializerComposition {

	private static EncodeableSerializer INSTANCE;
	
	/**
	 * Get singleton instance
	 * @return singleton instance
	 */
	public synchronized static EncodeableSerializer getInstance() 
	{
		return (INSTANCE!=null) ? INSTANCE : (INSTANCE = new EncodeableSerializer()); 
	}
	
	public EncodeableSerializer() {
		
		_Content_ 
		
	}
	

}
