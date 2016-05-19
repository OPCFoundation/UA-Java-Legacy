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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class IdentifiersUtil {

	public static final Pattern IDENTIFIER_LINE_PATTERN = Pattern.compile("(\\S*),(\\d*)(,(\\S*))?");
	
	public static final Comparator<Identifier> NAME_COMPARATOR = 
		new Comparator<Identifier>() {
			@Override
			public int compare(Identifier o1, Identifier o2) {
				return o1.name.compareTo(o2.name);
			}};
	
	public static final Comparator<Identifier> TYPE_COMPARATOR = 
		new Comparator<Identifier>() {
			@Override
			public int compare(Identifier o1, Identifier o2) {
				return o1.type.compareTo(o2.type);
			}};
			
	public static final class Identifier {
		String name;
		int id;
		String type;
	}
	
	public static void readIdentifiers(File file, Collection<Identifier> identifiers)
	throws IOException
	{
		FileInputStream fis = new FileInputStream(file);
		try {
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			try {
				do {
					String line = br.readLine();
					if (line==null) break;
					if (line.isEmpty()) continue;
					line=line.trim();
					Matcher m = IDENTIFIER_LINE_PATTERN.matcher(line);
					if (!m.matches()) throw new RuntimeException("Unexpected \""+line+"\" in "+file);
					Identifier i = new Identifier();
					i.name = m.group(1);
					i.id = new Integer(m.group(2));
					i.type = m.group(4);
					identifiers.add(i);
				} while (true);
			} catch (IOException e) {
				
			} finally {
				br.close();
			}
		} finally {
			fis.close();
		}
	}

	public void writeIdentifiers(File file, Template template)
	{
		
	}
	
}
