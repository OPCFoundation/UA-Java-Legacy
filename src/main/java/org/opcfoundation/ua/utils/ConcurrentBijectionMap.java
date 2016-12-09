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

package org.opcfoundation.ua.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Bijection map is a 1:1 binding of 2-tuples.
 * For each binding one value has role "left" and the other one the role "right".
 *
 * @author Toni Kalajainen
 */
public class ConcurrentBijectionMap<L, R> {

	/** The keys of this map are lefts and values rights */
	private Map<L, R> tableLeft = new HashMap<L, R>();
	/** The keys of this map are rights and values lefts */
	private Map<R, L> tableRight = new HashMap<R, L>();
	
	/**
	 * <p>addAll.</p>
	 *
	 * @param map a {@link org.opcfoundation.ua.utils.ConcurrentBijectionMap} object.
	 */
	public synchronized void addAll(ConcurrentBijectionMap<L, R> map)
	{
		for (Entry<L, R> e : map.tableLeft.entrySet())
			map(e.getKey(), e.getValue());
	}
	
    /**
     * <p>retainAllLeft.</p>
     *
     * @param values a {@link java.util.Collection} object.
     * @return a boolean.
     */
    public synchronized boolean retainAllLeft(Collection<L> values)
    {
        boolean result = false;
        for (L lValue : values)
            if ( !tableLeft.containsKey(lValue) ) {
                removeWithLeft(lValue);
                result = true;
            }
        return result;
    }
    
    /**
     * <p>retainAllRight.</p>
     *
     * @param values a {@link java.util.Collection} object.
     * @return a boolean.
     */
    public synchronized boolean retainAllRight(Collection<R> values)
    {
        boolean result = false;
        for (R rValue : values)
            if ( !tableRight.containsKey(rValue) ) {
                removeWithRight(rValue);
                result = true;
            }
        return result;
    }
    
	/**
	 * <p>getEntries.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public synchronized Set<Entry<L, R>> getEntries()
	{
		return new HashSet<Entry<L, R>>(tableLeft.entrySet());
	}
	
	/**
	 * <p>containsLeft.</p>
	 *
	 * @param leftValue a L object.
	 * @return a boolean.
	 */
	public synchronized boolean containsLeft(L leftValue)
	{
		return tableLeft.containsKey(leftValue);
	}
	
	/**
	 * <p>containsRight.</p>
	 *
	 * @param rightValue a R object.
	 * @return a boolean.
	 */
	public synchronized boolean containsRight(R rightValue)
	{
		return tableRight.containsKey(rightValue);
	}
	
	/**
	 * Contains binding
	 *
	 * @param leftValue a L object.
	 * @param rightValue a R object.
	 * @return true if there is a mapping between left and right value
	 */
	public synchronized boolean contains(L leftValue, R rightValue)
	{
		if (leftValue==rightValue) return true;
		if (leftValue==null || rightValue==null) return false;
		R right = tableLeft.get(leftValue);
		if (right==rightValue) return true;
		return tableLeft.get(leftValue).equals(right);
	}
	
	/**
	 * Add value to the map
	 *
	 * @param leftValue a L object.
	 * @param rightValue a R object.
	 */
	public synchronized void map(L leftValue, R rightValue)
	{
        // Remove possible old mapping
        R oldRight = tableLeft.remove(leftValue);
        if (oldRight != null) {
            tableRight.remove(oldRight);
        } else {
            L oldLeft = tableRight.remove(rightValue);
            if (oldLeft != null) {
                tableLeft.remove(oldLeft);
            }
        }
        
		tableLeft.put(leftValue, rightValue);
		tableRight.put(rightValue, leftValue);
	}
	
	/**
	 * <p>isEmpty.</p>
	 *
	 * @return a boolean.
	 */
	public synchronized boolean isEmpty() {
	    return tableLeft.isEmpty();
	}
    
    /**
     * Get the number of mappings
     *
     * @return the number of mappings
     */
    public synchronized int size() 
    {
        return tableLeft.size();
    }
	
	/**
	 * Get left value with right key
	 *
	 * @param rightValue a R object.
	 * @return left value
	 */
	public synchronized L getLeft(R rightValue) {
		return tableRight.get(rightValue);		
	}

	/**
	 * Get right value with left key
	 *
	 * @param leftValue a L object.
	 * @return right vlaue
	 */
	public synchronized R getRight(L leftValue) {
		return tableLeft.get(leftValue);		
	}
	
	/**
	 * Remove a binding with left key
	 *
	 * @param leftValue a L object.
	 * @return old right value
	 */
	public synchronized R removeWithLeft(L leftValue) {
		R rightValue = tableLeft.remove(leftValue);
		if (rightValue!=null)
			tableRight.remove(rightValue);
		return rightValue;
	}

	/**
	 * Remove a binding with right key
	 *
	 * @param rightValue a R object.
	 * @return old left value
	 */
	public synchronized L removeWithRight(R rightValue) {
		L leftValue = tableRight.remove(rightValue);
		if (leftValue!=null)
			tableLeft.remove(leftValue);
		return leftValue;
	}
    
    /**
     * Get all left values
     *
     * @return all left values
     */
    public synchronized Set<L> getLeftSet() {
        return Collections.unmodifiableSet( tableLeft.keySet() ); 
    }
    
    /**
     * Get all right values.
     *
     * @return all right values
     */
    public synchronized Set<R> getRightSet() {
        return Collections.unmodifiableSet( tableRight.keySet() );
    }    
    
    /**
     * Clear all bindings
     */
    public synchronized void clear() {
        tableLeft.clear();
        tableRight.clear();
    }
    
    /** {@inheritDoc} */
    @Override
    public synchronized String toString() {
    	int count = 0;
    	StringBuilder sb = new StringBuilder();
    	sb.append("[");
    	for (Entry<L, R> e : tableLeft.entrySet())
    	{
    		if (count++>0) sb.append(", ");
    		sb.append(e.getKey().toString());
    		sb.append("=");
    		sb.append(e.getValue().toString());    		
    	}
    	sb.append("]");
    	return sb.toString();
    }
}
