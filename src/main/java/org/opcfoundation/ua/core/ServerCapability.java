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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public enum ServerCapability {
	
	DA("DA", "Provides current data."),
	HD("HD", "Provides historical data."),
	AC("AC", "Provides alarms and conditions that may require operator interaction."),
	HE("HE", "Provides historical alarms and events."),
	GDS("GDS", "Supports the Global Discovery Server information model."),
	LDS("LDS", "Only supports the Discovery Services. Cannot be used in combination with any other capability."),
	DI("DI", "Supports the Device Integration (DI) information model."),
	ADI("ADI", "Supports the Analyser Device Integration (ADI) information model."),
	FDI("FDI", "Supports the Field Device Integration (FDI) information model."),
	FDIC("FDIC", "Supports the Field Device Integration (FDI) Communication Server information model."),
	PLC("PLC", "Supports the PLCopen information model."),
	S95("S95", "Supports the ISA95 information model.");

	
	public static final String NOT_AVAILABLE = "NA";
	private final String identifier;
	private final String description;
	
	ServerCapability(String identifier, String description) {
		this.identifier = identifier;
		this.description = description;
	}
	
	private static final Map<String, ServerCapability> map;
	static {
		map = new HashMap<String, ServerCapability>();
		for (ServerCapability i : ServerCapability.values()) 
			map.put(i.identifier, i);        
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static String[] getIdentifiers(EnumSet<ServerCapability> list) {
		return getIdentifiers(list.toArray(new ServerCapability[list.size()]));
	}
	
	public static String[] getIdentifiers(ServerCapability... list) {
		if (list.length == 0)
			return new String[] { NOT_AVAILABLE };
		List<String> identifiers = new ArrayList<String>();
		for (ServerCapability i : list)
			identifiers.add(i.identifier);
		return identifiers.toArray(new String[identifiers.size()]);
	}
	
	public static EnumSet<ServerCapability> getSet(String... identifiers) {
		List<ServerCapability> serverCapabilities = new ArrayList<ServerCapability>();
		for (String i : identifiers) {
			ServerCapability serverCapability = valueOfId(i);
			if (serverCapability != null)
				serverCapabilities.add(serverCapability);
		}
		if (serverCapabilities.isEmpty())
			return EnumSet.noneOf(ServerCapability.class);
		else
			return EnumSet.copyOf(serverCapabilities);	
	}
	
	public static ServerCapability valueOfId(String identifier) {
		if (NOT_AVAILABLE.equals(identifier))
			return null;
		ServerCapability serverCapability = map.get(identifier);
		if (serverCapability == null)
			throw new IllegalArgumentException("The identifier is invalid: " + identifier);
		return serverCapability;
	}

	public static String toString(EnumSet<ServerCapability> capabilities) {
		return capabilities.toString();
	}
	
}
