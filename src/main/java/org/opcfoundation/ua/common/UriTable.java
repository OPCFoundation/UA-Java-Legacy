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

import java.util.Arrays;

import org.opcfoundation.ua.utils.BijectionMap;

/**
 * <p>UriTable class.</p>
 *
 */
public class UriTable {

	BijectionMap<Integer, String> indexUriMap = new BijectionMap<Integer, String>();

	/**
	 * <p>Constructor for UriTable.</p>
	 */
	public UriTable() {
		super();
	}

	/**
	 * <p>toArray.</p>
	 *
	 * @return an array of {@link java.lang.String} objects.
	 */
	public synchronized String[] toArray() {
		int len = 0;
		for (Integer i : indexUriMap.getLeftSet())
			if (i > len)
				len = i;
		len++;
		String result[] = new String[len];
		for (int i = 0; i < len; i++)
			result[i] = indexUriMap.getRight(i);
		return result;
	}

	/**
	 * Finds the URI with index in the table
	 *
	 * @param index
	 *            the index you are looking for
	 * @return the URI with the index or null, if there is no such
	 *         index
	 */
	public String getUri(int index) {
		return indexUriMap.getRight(index);
	}

	/**
	 * Finds the index of the namespace URI in the table
	 *
	 * @param namespaceUri
	 *            the URI of the namespace you are looking for
	 * @return the index of the URI or -1, if it is not in the table
	 */
	public int getIndex(String namespaceUri) {
		Integer i = indexUriMap.getLeft(namespaceUri);
		if (i == null)
			return -1;
		return i;
	}

	/**
	 * Add a new uri to the table. The uri will be added with a new index, unless it is in the table already, in which case the index is returned.
	 *
	 * @param uri
	 *            The URI.
	 * @return a int.
	 */
	public int add(String uri) {
		return add(-1, uri);
	}

	private int nextIndex() {
		int result = -1;
		for (int i : indexUriMap.getLeftSet())
			if (i > result)
				result = i;
		return result + 1;
	}

	/**
	 * Remove the entry for the specified index
	 *
	 * @param index a int.
	 */
	public void remove(int index) {
		indexUriMap.removeWithLeft(index);
	}

	/**
	 * Remove the entry for the specified uri
	 *
	 * @param uri a {@link java.lang.String} object.
	 */
	public void remove(String uri) {
		indexUriMap.removeWithRight(uri);
	}

	/**
	 * Add a new uri to the table.
	 *
	 * @param index
	 *            The new index (use -1 to automatically use the next unused
	 *            index)
	 * @param uri
	 *            The URI.
	 * @throws java.lang.IllegalArgumentException
	 *             if the index is already in use
	 * @return a int.
	 */
	public synchronized int add(int index, String uri) {
		// check if namespaceIndex already exists
		int i = getIndex(uri);
		if (i >= 0)
			return i;
		if (index < 0)
			index = nextIndex();
		else if (getUri(index) != null)
			throw new IllegalArgumentException(
					"namespaceTable already has namespaceIndex " + index);
		// in other case we are able to add new namespaceIndex with value
		indexUriMap.map(index, uri);
		return index;
	}

	/**
	 * <p>addAll.</p>
	 *
	 * @param namespaceArray an array of {@link java.lang.String} objects.
	 */
	public void addAll(String[] namespaceArray) {
		for (int i = 0; i < namespaceArray.length; i++)
			add(i, namespaceArray[i]);
	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
		return Arrays.toString(toArray());
	}

	/**
	 * <p>size.</p>
	 *
	 * @return a int.
	 */
	public int size() {
		return indexUriMap.size();
	}

}
