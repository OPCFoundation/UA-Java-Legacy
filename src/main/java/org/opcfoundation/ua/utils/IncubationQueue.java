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

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * IncubationQueue is ordered queue where objects are added in two phases;
 * (a) as incubating, they are given queuing number, and as (b) hatched they become
 * available to the consumer. Objects become consumable when they are hatch()ed.
 * The order of incubation is maintained. Objects become consumable in the order they
 * were incubate()ed. This class is synchronized and is multi-thread-safe.
 * <p>
 * Example:
 *
 *  IncubationQueue q = new IncubationQueue();
 *  q.incubate("a");
 *  q.incubate("b");
 *  q.incubate("c");
 *  q.hatch("b");
 *  q.removeNextHatchedIfAvailable(); // returns null
 *  q.hatch("a");
 *  q.removeNextHatched(); // returns "a"
 *  q.removeNextHatched(); // returns "b"
 *  q.removeNextHatchedIfAvailable(); // returns null
 *  q.hatch("c");
 *  q.removeNextHatched(); // returns "c"
 *
 * @author Toni Kalajainen (toni.kalajainen@iki.fi)
 */
public class IncubationQueue<T> {

	Map<T, T> hatchMap; // value=key, if null it is hatched
	LinkedList<T> orderList = new LinkedList<T>();
	
	/**
	 * Create new Incubation queue that compares with equals()/hashCode
	 */
	public IncubationQueue()
	{
		this(false);
	}
	
	/**
	 * Create new Incubation queue
	 *
	 * @param identityComparison if true objects are compared with ==, false compare with equals()/hashCode()
	 */
	public IncubationQueue(boolean identityComparison)
	{
		hatchMap = identityComparison ?
				new IdentityHashMap<T, T>() :
		 		new HashMap<T, T>(); 
	}
	
	
	/**
	 * Add object to the queue
	 *
	 * @param o object not null and not in queue
	 */
	public synchronized void incubate(T o) {
		if (o==null)
			throw new IllegalArgumentException("null arg");
		if (hatchMap.containsKey(o)) 
			throw new IllegalArgumentException(o+" is already incubating");
		hatchMap.put(o, o);
		orderList.addLast(o);
		notifyAll();
	}
	
	/**
	 * Hatch incubating object o.
	 * If all objects in the queue before o are hatched, all of them are returned
	 * in the order they were added to the queue.
	 *
	 * If there is an object still incubating before o, an empty list is returned.
	 *
	 * @param o object not null
	 * @throws java.lang.IllegalArgumentException if o was not incubating
	 * @return true if o was incubating
	 */
	public synchronized boolean hatch(T o)
	throws IllegalArgumentException
	{
		if (o==null)
			throw new IllegalArgumentException("null arg");
		if (!hatchMap.containsKey(o)) 		
			throw new IllegalArgumentException(o+" is not incubating");
		T key = hatchMap.get(o);
		hatchMap.put(key, null);
		notifyAll();		
		return key!=null;
	}

	/**
	 * Remove next hatched object if available
	 *
	 * @return next hatched object or null
	 */
	public synchronized T removeNextHatchedIfAvailable() 
	{
		T o = getNextHatchedIfAvailable();
		if (o==null) return null;
		orderList.removeFirst();
		hatchMap.remove(o);
		notifyAll();
		return o;
	}
		
	/**
	 * Remove next hatched object. Blocks until next is available.
	 *
	 * @return next hatched object
	 * @throws java.lang.InterruptedException if any.
	 */
	public synchronized T removeNextHatched() 
	throws InterruptedException
	{
		T o = getNextHatched();
		orderList.removeFirst();
		hatchMap.remove(o);
		notifyAll();
		return o;
	}
	
	/**
	 * Remove next hatched object. Blocks until next is available.
	 *
	 * @return next hatched object
	 */
	public synchronized T removeNextHatchedUninterruptibly()
	{
		while (true) {
			try {
				return removeNextHatched();
			} catch (InterruptedException e) {
			}
		}
	}
	
	
	/**
	 * Is next object hatched
	 *
	 * @return true if there is an object in queue and it is hatched
	 */
	public synchronized boolean nextIsHatched()
	{
		if (orderList.isEmpty()) return false;
		T o = orderList.getFirst();
		return hatchMap.get(o)==null;
	}

	/**
	 * Get next hatched object
	 *
	 * @return next hatched object or null
	 */
	public synchronized T getNextHatchedIfAvailable()
	{
		if (orderList.isEmpty()) return null;
		T key = orderList.getFirst();
		if (hatchMap.get(key)!=null) return null;
		return key;		
	}

	/**
	 * Get next hatched object, blocks if empty or unhatched
	 *
	 * @return next hatched object
	 * @throws java.lang.InterruptedException if any.
	 */
	public T getNextHatched() 
	throws InterruptedException
	{
		while (true) {
			T key = getNext();
			waitUntilIncubated(key);
			synchronized(this) {
				if (orderList.getFirst()==key) return key;
			}
		}
	}
	
	/**
	 * <p>getNext.</p>
	 *
	 * @return a T object.
	 * @throws java.lang.InterruptedException if any.
	 */
	public synchronized T getNext()
	throws InterruptedException
	{
		while(orderList.isEmpty()) wait();
		return orderList.getFirst();		
	}
	
	/**
	 * <p>isEmpty.</p>
	 *
	 * @return a boolean.
	 */
	public synchronized boolean isEmpty() {
		return hatchMap.isEmpty();
	}
	
	/**
	 * <p>clear.</p>
	 */
	public synchronized void clear() {
		orderList.clear();
		hatchMap.clear();
		notifyAll();
	}
	
	/**
	 * <p>size.</p>
	 *
	 * @return a int.
	 */
	public synchronized int size() {
		return hatchMap.size();
	}
	
	/**
	 * Non-thread safe iterator. Removing is not allowed.
	 *
	 * @return iterator
	 */
	public Iterator<T> iterator() {
		return orderList.iterator();
	}
	
	/**
	 * <p>contains.</p>
	 *
	 * @param o a T object.
	 * @return a boolean.
	 */
	public synchronized boolean contains(T o)
	{
		return hatchMap.containsKey(o);
	}
	
	/**
	 * <p>isHatched.</p>
	 *
	 * @param o a T object.
	 * @return a boolean.
	 */
	public synchronized boolean isHatched(T o)
	{
		if (!hatchMap.containsKey(o)) return false;
		return hatchMap.get(o) == null;
	}

	/**
	 * <p>isIncubating.</p>
	 *
	 * @param o a T object.
	 * @return a boolean.
	 */
	public synchronized boolean isIncubating(T o)
	{
		if (!hatchMap.containsKey(o)) return false;
		return hatchMap.get(o) != null;
	}
	
	/**
	 * <p>waitUntilIncubated.</p>
	 *
	 * @param o a T object.
	 * @throws java.lang.InterruptedException if any.
	 */
	public synchronized void waitUntilIncubated(T o) 
	throws InterruptedException
	{
		while (isIncubating(o)) wait();
	}
	
}
