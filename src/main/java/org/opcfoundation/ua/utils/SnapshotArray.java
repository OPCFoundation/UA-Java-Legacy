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

/*
 * 12.6.2007
 */
package org.opcfoundation.ua.utils;

import java.lang.reflect.Array;

/**
 * SnapshotArray is synchronized list that provides copy-on-write-arraylist
 * of its contents.
 *
 * @author Toni Kalajainen
 * @param <T>
 */
public class SnapshotArray<T> {
    
    /**
     * Array of listeners
     */
    private volatile T [] array;
    
    /** 
     * The class of T
     */
    private final Class<T> componentType;
           
    /**
     * Construct new Listener List
     *
     * @param componentType the class of the listener type
     */
    public SnapshotArray(Class<T> componentType)
    {
        this.componentType = componentType;
        array = createArray(0);
    }
    
    /**
     * Get a snapshot of the contents. This method exposes an internal state
     * which must not be modified.
     *
     * @return an array.
     */
    public T[] getArray()
    {
        return array;
    }
    
    /**
     * <p>add.</p>
     *
     * @param item a T object.
     */
    public synchronized void add(T item)
    {
        int oldLength = array.length;
        int newLength = oldLength + 1;
        T newArray[] = createArray(newLength);
        System.arraycopy(array, 0, newArray, 0, oldLength);
        newArray[oldLength] = item;
        array = newArray;
    }
    
    /**
     * Removes the first occurance of the item.
     * If the item is added multiple times, then it must be removed
     * as many times.
     *
     * @param item an item
     * @return true if the item was removed from the list
     */
    public synchronized boolean remove(T item)
    {
        int pos = getPos(item);
        if (pos<0) return false;
        
        int oldLength = array.length;
        int newLength = oldLength -1;
        T newArray[] = createArray(newLength);
        
        // Copy beginning
        if (pos>0)
            System.arraycopy(array, 0, newArray, 0, pos);
        
        // Copy ending
        if (pos<newLength)
            System.arraycopy(array, pos+1, newArray, pos, newLength-pos);
        
        array = newArray;
        return true;
    }        
    
    private synchronized int getPos(T listener)
    {
        for (int i=0; i<array.length; i++)
            if (array[i] == listener)
                return i;
        return -1;
    }
    
    /**
     * <p>size.</p>
     *
     * @return a int.
     */
    public int size()
    {
        return array.length;
    }
    
    /**
     * <p>isEmpty.</p>
     *
     * @return a boolean.
     */
    public boolean isEmpty()
    {
        return array.length == 0;
    }
    
    /**
     * <p>clear.</p>
     */
    public void clear()
    {
        array = createArray(0);
    }
    
    @SuppressWarnings("unchecked")
    private T[] createArray(int size)
    {
        return (T[]) Array.newInstance(componentType, size);
    }
    
}
