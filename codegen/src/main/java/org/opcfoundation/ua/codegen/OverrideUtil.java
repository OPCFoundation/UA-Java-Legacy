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

package org.opcfoundation.ua.codegen;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * There is a folder "additions/" which contains files with content to be added on to the
 * code generated classes.
 *
 */
public class OverrideUtil {

	public static final File OVERRIDES_FOLDER = new File("src/main/resources/codegen_data/overrides/");

	/**
	 * Read all files from a folder. The files are UTF-8 encoded.
	 *
	 * @param folder
	 * @return map containing file name and contents of the files
	 * @throws IOException
	 */
	public static Map<String, Template> getOverrides(File folder)
			throws IOException
	{
		Map<String, Template> result = new HashMap<String, Template>();

		for (File file : folder.listFiles())
		{
			if (!file.isFile()) continue;
			try {
				String name = file.getName();
				Template content = Template.load(file);
				result.put(name, content);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}

		return result;
	}


}
