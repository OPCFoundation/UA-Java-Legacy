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

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncoderContext;

/**
 * Multi-dimension array utils.
 *
 * @see Array related
 * @see Arrays related
 * @see Arrays related
 * @see Collections related
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class MultiDimensionArrayUtils {

	
	/**
	 * Format object to string
	 *
	 * @param o a {@link java.lang.Object} object.
	 * @return string representation of the object
	 */
	public static String toString(Object o)
	{
		if (o==null) return "" + null;
		if (o instanceof ExtensionObject)
			try {
				o = ((ExtensionObject) o).decode(StackUtils.getDefaultSerializer(), EncoderContext.getDefaultInstance(), null);
			} catch (DecodingException e) {
				// ignore
			}
		if (!o.getClass().isArray()) return o.toString();
		int[] lens = getArrayLengths(o);
		if (lens.length==1) {
	        if (o instanceof ExtensionObject[]) {
	        	try {
					Object[] objArray = (Object[]) EncoderContext.getDefaultInstance().decode((ExtensionObject[])o);
		        	return Arrays.toString(objArray); 
				} catch (DecodingException e) {
					// ignore
					e.printStackTrace();
				}
	        }
	        if (o instanceof Object[]) return Arrays.toString((Object[])o); 
	        if (o instanceof double[]) return Arrays.toString((double[])o); 
	        if (o instanceof boolean[]) return Arrays.toString((boolean[])o); 
	        if (o instanceof byte[]) return Arrays.toString((byte[])o); 
	        if (o instanceof char[]) return Arrays.toString((char[])o); 
	        if (o instanceof float[]) return Arrays.toString((float[])o); 
	        if (o instanceof int[]) return Arrays.toString((int[])o); 
	        if (o instanceof long[]) return Arrays.toString((long[])o); 
	        if (o instanceof short[]) return Arrays.toString((short[])o);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i=0; i<lens[0]; i++)
			sb.append(toString(Array.get(o, i)));
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Clones the multi-dimension structure of an array but the not
	 * the individual elements. Element references remain the same is in the
	 * original.
	 *
	 * @param array a {@link java.lang.Object} object.
	 * @return cloned array
	 */
	public static Object clone(Object array)
	{
		Class<?> componentType = getComponentType(array.getClass());
		int dims[] = getArrayLengths(array);		
		Object[] elements = (Object[]) muxArray(array, dims, componentType);
		return demuxArray(elements, dims, componentType);
	}

	/**
	 * Deep clone structure and elements. Elements are cloned if they are cloneable,
	 * otherwise references are copied.
	 *
	 * @param array a {@link java.lang.Object} object.
	 * @return a {@link java.lang.Object} object.
	 */
	public static Object deepClone(Object array)
	{
		Class<?> componentType = getComponentType(array.getClass());
		int dims[] = getArrayLengths(array);
		Object[] elements = (Object[]) muxArray(array, dims, componentType);
		
		try {
			Object[] args = new Object[0];
			for (int i=0; i<elements.length; i++) 
			{
				Object element = elements[i];
				try {
					if (element==null) continue;
					Method clone = element.getClass().getMethod("clone");
					clone.setAccessible(true);
					elements[i] = clone.invoke(element, args);
				} catch (NoSuchMethodException e) {}
			}
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
		return demuxArray(elements, dims, componentType);
	}
	
	/**
	 * Print multi-dimension array
	 *
	 * @param v a {@link java.lang.Object} object.
	 * @param out output stream
	 */
	public static void printArrayDeep(Object v, PrintStream out)
	{
		ArrayIterator<Boolean> i = arrayIterator(v, getArrayLengths(v));
		while (i.hasNext())
			System.out.println(Arrays.toString(i.getIndices())+" = "+i.next());
	}	
	
	/**
	 * Get array length of each dimension of a multi-dimension array.
	 *
	 * @param value multi-dimension array
	 * @return lengths of each dimension
	 */
	public static int[] getArrayLengths(Object value)
	{			
		int dim = getDimension(value);
		int result[] = new int[ dim ];
		if (dim==0) return result;
		
		Object o = value;
		for (int i=0; i<dim; i++)			
		{
			result[i] = o == null ? 0 : Array.getLength(o);
			if (result[i]==0) break;
			o = Array.get(o, 0);
		}
		
		return result;
	}
	
	/**
	 * Get the number of dimensions in a multi-dimension array.
	 *
	 * @param value multi-dimension array
	 * @return the number of dimensions
	 */
	public static int getDimension(Object value)
	{			
		Class<?> clazz = value.getClass();
		String signature = clazz.getName();
		int dim = 0;
		for (; dim<signature.length(); dim++)		
			if (signature.charAt(dim)!='[') break;		
		return dim;
	}    	
	
	
	/**
	 * Deep create multi-level array
	 *
	 * @param componentType component type
	 * @param dims dimension lengths
	 * @return multi-level array
	 */
	public static Object[] createMultiDimArray(Class<?> componentType, int[] dims)
	{
		// Java 1.6.0
		return (Object[]) Array.newInstance(componentType, dims);
		
		/*
		// Older than 1.6.0
		if (dims==null || componentType==null || dims.length==0) throw new IllegalArgumentException();
		if (dims.length==1) 
			return Array.newInstance(componentType, dims[0]);
		
		int[][] dimArrays = new int[dims.length][];
		dimArrays[0] = dims;
		for (int j=1; j<dims.length; j++)
		{
			dimArrays[j] = new int[dims.length-j];
			System.arraycopy(dims, 1, dimArrays[j], 0, dims.length-j);
		}
		return _createMultiDimArray(componentType, dimArrays, 0);
		*/
	}
/*	
	private static Object[] _createMultiDimArray(Class<?> componentType, int[][] dimArrays, int lvl)
	{
		int[] dims = dimArrays[lvl];
		int len = dims[0];
		Object[] result = (Object[]) Array.newInstance(componentType, dims);
		if (lvl<dimArrays.length-1)
			for (int i=0; i<len; i++)
				result[i] = _createMultiDimArray(componentType, dimArrays, lvl+1);
		return result;
	}
*/	
		
	/**
	 * Returns the total number of elements in a multi-dimension array
	 *
	 * @param dims lengths of each dimension
	 * @return total number of elements
	 */
	public static int getLength(int[] dims)
	{
		int len = dims[0];
		for (int i=1; i<dims.length; i++)
			len *= dims[i];
		return len;		
	}

	/**
	 * Get the component type of an array class
	 *
	 * @param clazz (array) class
	 * @return component type
	 */
	public static Class<?> getComponentType(Class<?> clazz)
	{
		Class<?> result = clazz;
		while (result.isArray())
			result = result.getComponentType();
		return result;
	}
	
	/**
	 * Demux single-dimension array (x[]) to a multi-dimension array (x[][][])
	 *
	 * @param src single-dimension array
	 * @param dims an array of int.
	 * @return multi-dimension array
	 */
	public static Object demuxArray(Object src, int[] dims)
	{
		return demuxArray(src, dims, getComponentType(src.getClass()));
	}

	/**
	 * Demux single-dimension array (x[]) to a multi-dimension array (x[][][])
	 *
	 * @param src single-dimension array
	 * @param dims an array of int.
	 * @param componentType a {@link java.lang.Class} object.
	 * @return multi-dimension array
	 */
	public static Object demuxArray(Object src, int[] dims, Class<?> componentType)
	{
		Object dst = null;
		// TODO code to fix byte[][]
		if (componentType != byte.class) {
			dst = Array.newInstance(componentType, dims);
		} else {
			int[] dimsTemp = new int[dims.length + 1];
			System.arraycopy(dims, 0, dimsTemp, 0, dims.length);
			dimsTemp[dims.length] = 1;
			dst = Array.newInstance(componentType, dimsTemp);
		}
		_fillDemux(0, dims, src, 0, dst);
		return dst;
	}
	
	/**
	 * Demuxes single dimension array into a multi-dimension array
	 *
	 * @param src one dimension array (e.g. int[])
	 * @param dims length of each dimension
	 * @param dst multi-dimension array to be filled (use createMultiDimArray())
	 */
	public static void demuxArray(Object src, int[] dims, Object dst)
	{
		if (Array.getLength(src) != getLength(dims)) 
			throw new IllegalArgumentException("The length of src does not match the length of dst");	
		_fillDemux(0, dims, src, 0, dst);
	}
		
	private static int _fillDemux(int lvl, int[] dims, Object src, int srcIndex, Object dst)
	{
		// lower level
		if (lvl==dims.length-1) {
			int len = dims[dims.length-1]; 
			System.arraycopy(src, srcIndex, dst, 0, len);
			return srcIndex + len;
		}
		// higher level
		for (int i=0; i<dims[lvl]; i++)
			srcIndex = _fillDemux(lvl+1, dims, src, srcIndex, Array.get(dst, i));
		
		return srcIndex;
	}

	/**
	 * Multiplex multi-dimension array (x[][][]) to single-dimension array (x[])
	 *
	 * @param src multi-dimension array
	 * @return single-dimension array
	 */
	public static Object muxArray(Object src)
	{
		return muxArray(src, getArrayLengths(src), getComponentType(src.getClass()));
	}	
	
	/**
	 * Multiplex multi-dimension array (x[][][]) to single-dimension array (x[])
	 *
	 * @param src multi-dimension array
	 * @param dims an array of int.
	 * @return single-dimension array
	 */
	public static Object muxArray(Object src, int[] dims)
	{
		return muxArray(src, dims, getComponentType(src.getClass()));
	}	
	
	/**
	 * Multiplex multi-dimension array (x[][][]) to single-dimension array (x[])
	 *
	 * @param src multi-dimension array
	 * @param dims an array of int.
	 * @param componentType a {@link java.lang.Class} object.
	 * @return single-dimension array
	 */
	public static Object muxArray(Object src, int[] dims, Class<?> componentType)
	{
		int len = getLength(dims);
		Object dst = Array.newInstance(componentType, len);
		muxArray(src, dims, dst);
		return dst;
	}
	
	/**
	 * Multiplexes multi-dimension array into a single-dimension array
	 *
	 * @param src multi-dimension array
	 * @param dims dimensions
	 * @param dst single-dimension array
	 */
	public static void muxArray(Object src, int[] dims, Object dst)
	{
		int len = getLength(dims);
		if (Array.getLength(dst) != len) 
			throw new IllegalArgumentException("The length of src does not match the length of dst");
		
		_fillMux(0, dims, src, dst, 0);
	}
	
	private static int _fillMux(int lvl, int[] dims, Object src, Object dst, int dstIndex)
	{
		if (lvl == dims.length-1)
		{
			int len = dims[lvl];
			System.arraycopy(src, 0, dst, dstIndex, len);
			return dstIndex + len;
		}
		
		for (int i = 0; i < dims[lvl]; i++)
			dstIndex = _fillMux(lvl + 1, dims, Array.get(src, i), dst, dstIndex);

		return dstIndex;
	}
	
	/**
	 * <p>arrayIterator.</p>
	 *
	 * @param array a {@link java.lang.Object} object.
	 * @param dimLens an array of int.
	 * @param <R> a R object.
	 * @return a {@link org.opcfoundation.ua.utils.MultiDimensionArrayUtils.ArrayIterator} object.
	 */
	public static <R> ArrayIterator<R> arrayIterator(Object array, int[] dimLens)
	{
		return new ArrayIterator<R>(array, dimLens);
	}
	
	public static class ArrayIterator<T> implements Iterator<T>
	{
		Object v;
		int[] dims;
		int[] indices;
		Object[] arrays;
		int lastIndex, len;
		boolean hasNext = true;
		
		ArrayIterator(Object array, int[] dimLens)
		{
			this.v = array;
			this.dims = dimLens;
			this.indices = new int[dimLens.length];
			this.arrays = new Object[dimLens.length];
			lastIndex = dimLens.length-1;
//			int len = dimLens[0];
			for (int i=0; i<=lastIndex; i++)
				if (dimLens[i]==0) hasNext = false;
//			for (int i=1; i<=lastIndex; i++) 
//				len *= dimLens[i];
			arrays[0] = (Object[]) v;
			for (int i=1; i<dimLens.length; i++)
				arrays[i] = ((Object[])arrays[i-1])[0]; 			
		}
		
		public int[] getIndices()
		{
			return indices;			
		}
		
		@Override
		public boolean hasNext() {
			return hasNext;
		}

		private void _next() {
			int index = lastIndex;			
			while (++indices[index] >= dims[index])
			{
				indices[index] = 0;
				index--;
				if (index<0) {
					hasNext = false;
					return;
				}
				arrays[index+1] = ((Object[]) arrays[index])[indices[index]];
			}
			if (index<lastIndex)
				for (int i=index+1; i<=lastIndex; i++)
					arrays[i] = ((Object[])arrays[i-1])[indices[i-1]];			
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			if (!hasNext)
				throw new NoSuchElementException();
			
			T result = (T) Array.get(Array.get(arrays, lastIndex), indices[lastIndex]);
			_next();
			return result;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Cannot remove array element");
		}		
	}

}
