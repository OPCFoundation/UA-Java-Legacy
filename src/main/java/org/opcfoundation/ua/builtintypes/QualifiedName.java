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

package org.opcfoundation.ua.builtintypes;

import org.opcfoundation.ua.core.Identifiers;


/**
 * A name qualified by a namespace.
 *
 */
public final class QualifiedName {
	
	public static final NodeId ID = Identifiers.QualifiedName;
	public static final QualifiedName NULL = new QualifiedName(UnsignedShort.valueOf(0), null); 
	public static final QualifiedName DEFAULT_BINARY_ENCODING = new QualifiedName("Default Binary"); 
	public static final QualifiedName DEFAULT_XML_ENCODING = new QualifiedName("Default XML");
	private int namespaceIndex;
	private String name;

	/**
	 * Initializes the object with default values.
	 * @param namespaceIndex namespace index
	 * @param name name part
	 */
	public QualifiedName(UnsignedShort namespaceIndex, String name)
	{		
//		if (name == null)  //changed 21.4. TODO
//			throw new IllegalArgumentException("name argument must not be null");
		// Part 3: 8.3, table 20
		// doesn't apply according to Randy, application layer must enforce this
//		if (name!=null){
//			if (name.length()>512)
//				throw new IllegalArgumentException("The length of name is restricted to 512 characters");
//		}
			
		this.namespaceIndex = namespaceIndex.intValue();
		this.name           = name;
	}

	/**
	 * Initializes the object with default values.
	 * Convenience method. 
	 * 
	 * @param namespaceIndex int bits of an unsigned short
	 * @param name name part
	 */
	public QualifiedName(int namespaceIndex, String name)
	{
//		if (name == null)
//			throw new IllegalArgumentException("name argument must not be null");
		if (namespaceIndex<UnsignedShort.MIN_VALUE.intValue() || namespaceIndex>UnsignedShort.MAX_VALUE.intValue())
			throw new IllegalArgumentException("namespace index out of bounds");
		// Part 3: 8.3, table 20
		// doesn't apply according to Randy, application layer must enforce this
//		if (name.length()>512)
//			throw new IllegalArgumentException("The length of name is restricted to 512 characters");
		
		this.namespaceIndex = namespaceIndex;
		this.name           = name;
	}	
		
	//TODO Added by Mikko 3.11.2008, this was needed in Session
	/** 
	 * Initializes the object with a name.
	 * In this convenience method the namespaceIndex is 0.
	 *
	 * @param name name
	 * 
	 */
	public QualifiedName(String name)
	{
		if (name == null)
			throw new IllegalArgumentException("name argument must not be null");
		// Part 3: 8.3, table 20
		// doesn't apply according to Randy, application layer must enforce this
//		if (name.length()>512)
//			throw new IllegalArgumentException("The length of name is restricted to 512 characters");
		namespaceIndex = 0;
		this.name           = name;
	}

	public String toString() {
		if (namespaceIndex > 0) 
			return namespaceIndex+":"+name;	
		return name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the namespaceIndex
	 */
	public int getNamespaceIndex() {
		return namespaceIndex;
	}
	
	/**
	 * Returns true if the value is null.
	 * @param value the qualified name
	 * @return true if value is null or equal to NULL
	 */
	public static boolean isNull(QualifiedName value) {
		return value==null || value.equals(NULL);
	}
	
	/**
	 * Return true if the value is null, or name part is empty string
	 * @param value the qualified name
	 * @return true if isNull(value) is true or the name part is empty string
	 */
	public static boolean isNullOrEmpty(QualifiedName value){
		if(isNull(value)){
			return true;
		} else if("".equals(value.name)){
			return true;
		} else {
			return false;
		}		
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + namespaceIndex;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return isNull(this);
		if (getClass() != obj.getClass())
			return false;
		QualifiedName other = (QualifiedName) obj;
		if (namespaceIndex != other.namespaceIndex)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	/**
	 * Parse the QualifiedName from a string.
	 * <p>
	 * The string is supposed to be in format "[NameSpaceIndex]:[Name]". or just "[Name]"
	 * @param value the string
	 * @return the new QualifiedName
	 */
	public static QualifiedName parseQualifiedName(String value) {
		String[] parts = value.split(":");
		UnsignedShort namespaceIndex = UnsignedShort.ZERO;
		String name = value;
		if (parts.length > 1)
			try {
				namespaceIndex = UnsignedShort.parseUnsignedShort(parts[0]);
				name = value.substring(parts[0].length()+1);
			} catch (NumberFormatException e) {
			} catch (IllegalArgumentException e) {
			}
		return new QualifiedName(namespaceIndex, name);
	}

}
