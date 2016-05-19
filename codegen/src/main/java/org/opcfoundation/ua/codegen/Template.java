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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class Template {

	// Keys for optional string valued replacements
	public static final String KEY_DATE = "_date_";
	public static final String KEY_URL = "_url_";
	public static final String KEY_DESCRIPTION = "_description_";
	public static final String KEY_SUPERTYPE = "_SuperType_";
	
	// Keys for string valued replacements
	public static final String KEY_PACKAGE_NAME = "_PackageName_";
	public static final String KEY_CLASSNAME = "_ClassName_";
	
	// Keys for Collection<String> valued replacements
	public static final String KEY_IMPORTS = "_imports_";
	public static final String KEY_CONTENT = "_Content_";
	public static final String KEY_ADDITION = "_Additions_";

	String template;
	
	public static Template load(File file)
	{
		try {
			return new Template(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Template load(String file)
	{
		try {
			return new Template(new File(file));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Template(File template) 
	throws IOException
	{
		this.template = LoadFromFile(template);
	}

	public Template(String template) 
	throws IOException
	{
		this( new File(template) );
	}
	
	public void buildToFile(Map<String, Object> map, String output) 
	throws IOException 
	{
		buildToFile(map, new File(output));
	}
	
	public void buildToFile(Map<String, Object> map, File output) 
	throws IOException 
	{
		String content				= build(map);
		String filename				= output.getAbsolutePath();
		ensurePath(filename);
		System.out.println(output.getAbsolutePath());
		saveToFile(content.getBytes(), filename);		
	}
	
	@SuppressWarnings("unchecked")
	public String build(Map<String, Object> map) 
	throws IOException
	{		
		String result				= template;
		String url					= (String) map.get(KEY_URL);
		String description			= (String) map.get(KEY_DESCRIPTION);
		String dateStr				= map.containsKey(KEY_DATE) ? map.get(KEY_DATE).toString() : new Date().toString();
		String packageName			= map.get(KEY_PACKAGE_NAME).toString();
		String className			= map.get(KEY_CLASSNAME).toString();
		Collection<String> imports_	= (Collection<String>) map.get(KEY_IMPORTS);
		Collection<String> content	= (Collection<String>) map.get(KEY_CONTENT);
		String addition				= (String) map.get(KEY_ADDITION);
		String superTypeStr			= (String) map.get(KEY_SUPERTYPE);
		if (superTypeStr == null) superTypeStr = "Object";

		assert(packageName!=null);
		assert(className!=null);
		assert(content!=null);		

		// Remove overlappings and sort alphabeticly
		List<String> imports = new ArrayList<String>();
		if (imports_!=null) {
			HashSet<String> i = new HashSet<String>();
			i.addAll(imports_);
			for (String s : i)
			{
				if (!s.startsWith("java.lang"))
					imports.add(s);
			}
			Collections.sort(imports);
		}
		
		StringBuilder sb = new StringBuilder();
		for (String s : imports)		
			sb.append("import "+s+";\n");
		String importsStr = sb.toString();

		sb = new StringBuilder();
		for (String s : content) 
			for (String s2 : s.split("\n"))
				sb.append("    "+s2+"\n");		
		String contentStr = sb.toString();

		if (url!=null) {
			result = result.replaceAll(KEY_URL, url);
		} else {
			if (result.contains(KEY_URL)) result = cutLineWithSubString(result, KEY_URL);
		}

		if (description!=null) {
			result = result.replaceAll(KEY_DESCRIPTION, description);
		} else {
			if (result.contains(KEY_DESCRIPTION)) result = cutLineWithSubString(result, KEY_DESCRIPTION);
		}
		
		if (addition==null) addition="";
		
		result = result.replaceAll(KEY_DATE, dateStr);
		result = result.replaceAll(KEY_PACKAGE_NAME, packageName);
		result = result.replaceAll(KEY_CLASSNAME, className);
		result = result.replaceAll(KEY_IMPORTS, importsStr);
		result = result.replaceAll(KEY_CONTENT, contentStr);
		result = result.replaceAll(KEY_DATE, dateStr);
		result = result.replaceAll(KEY_SUPERTYPE, superTypeStr);
		result = result.replaceAll(KEY_ADDITION, addition);
		
		return result;
	}

	/**
	 * Extracts file's directory Excludes last / \
	 * 
	 * @param filename
	 *            the file
	 * @return the path
	 */
	public static String extractFileDir(String filename) {
		// construct directory name
		String splitted[] = filename.replaceAll("\\\\", "/").split("/");
		if (splitted.length > 1)
			return filename.substring(0, filename.length()
					- splitted[splitted.length - 1].length() - 1);
		return "";
	}
	
	public static void ensurePath(String filename) 
	{
	    // Create directories
	    String dir = extractFileDir(filename);
	    (new File(dir)).mkdirs();		
	}

	public static void saveToFile(byte data[], String filename)
	throws IOException
	{
		File file = new File(filename);
		if (file.exists()) file.delete();
	    FileOutputStream out = new FileOutputStream(file);
	    out.write(data);
	    out.flush();
	    out.close();
	}
	
	public static String LoadFromFile(File file)
	throws IOException
	{
	    byte[] buffer;
	    FileInputStream fin		= new FileInputStream(file);	    
		try {
			int	size = (int)file.length();
			buffer = new byte[size];
			
			int	pos	= 0;
			while (pos<size) 
				pos += fin.read(buffer, pos, size-pos);
		} finally {
			fin.close();
		}
	    return new String(buffer, "utf-8");
	}	

	private static String cutLineWithSubString(String text, String subString)
	{
		int pos = text.indexOf(subString);
		if (pos<0) return text;
		// Cut that line
		int from = getIndexOfPrevLF(text, pos);
		int to = getIndexOfNextLF(text, pos);
		String begin = from<=0 ? "" : text.substring(0, from+1);
		String ending = to<0 ? "" : text.substring(to+1);
		return begin+ending;
	}
	
	private static int getIndexOfPrevLF(String text, int pos)
	{
		while (pos>=0)
		{
			if (text.charAt(pos)=='\n' || text.charAt(pos)=='\r') return pos;
			pos--;
		}
		return -1;
	}

	private static int getIndexOfNextLF(String text, int pos)
	{
		while (pos>=0)
		{
			if (text.charAt(pos)=='\n' || text.charAt(pos)=='\r') return pos;
			pos++;
		}
		return -1;
	}
	
}
