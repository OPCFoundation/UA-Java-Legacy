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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class ServiceSetUtil {


	public static void Build(File folder, Collection<IdentifiersUtil.Identifier> services)
			throws IOException
	{
		// Sort identifiers by service
		Map<String, List<String>> serviceSetMappings = new HashMap<String, List<String>>();
		for (IdentifiersUtil.Identifier i : services)
		{
			List<String> ss = serviceSetMappings.get(i.type);
			if (ss==null) {
				ss = new ArrayList<String>();
				serviceSetMappings.put(i.type, ss);
			}
			ss.add(i.name);
		}

		Template template = Template.load("src/main/resources/codegen_data/templates/ServiceSetTemplate.java");

		// Build service one by one
		for (String sset : serviceSetMappings.keySet())
		{
			File serviceFile = new File(folder, "org/opcfoundation/ua/core/"+sset+"Handler.java");
			Map<String, Object> map = new HashMap<String, Object>();
			List<String> imports = new ArrayList<String>();
			List<String> content = new ArrayList<String>();
			map.put(Template.KEY_PACKAGE_NAME, "org.opcfoundation.ua.core");
			map.put(Template.KEY_IMPORTS, imports);
			map.put(Template.KEY_CONTENT, content);
			map.put(Template.KEY_CLASSNAME, sset+"Handler");

			for (String service : serviceSetMappings.get(sset))
			{
				content.add("");
				content.add("void on"+service+"(EndpointServiceRequest<"+service+"Request, "+service+"Response> req) throws ServiceFaultException;");
			}
			template.buildToFile(map, serviceFile);
		}
	}



}
