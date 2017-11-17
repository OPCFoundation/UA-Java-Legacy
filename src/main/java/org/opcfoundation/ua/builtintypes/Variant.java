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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.opcfoundation.ua.utils.MultiDimensionArrayUtils;
import org.opcfoundation.ua.utils.MultiDimensionArrayUtils.ArrayIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Variant wraps an arbitrary builtin variable, an array of builtin variables or
 * a multi-dimension array of builtin variable. Variant is equals-comparable.
 * <p>
 * An example: Variant v = new Variant( new UnsignedInteger[4][5][6] );
 * <p>
 * The value may be builtin primitive or a {@link Structure}.
 *  e.g. new Variant( new NotificationData() );
 *
 * Encoders write a structure as an {@link ExtensionObject}.
 */
public class Variant {
	
    private static Logger logger = LoggerFactory.getLogger(Variant.class);

	/** Constant <code>NULL</code> */
	public static final Variant NULL = new Variant(null);
	
	//utility to convert scalar / unknown dim Enumeration array to Integers
	private static Object enumsToInts(Object enums){
	  //this is called only by Variant constructor, which does not pass nulls to this
	  
	  Class<?> clazz = enums.getClass();
	  
	  // Most of the arguments are either scalar or 1-dim arrays,
	  // therefore special handling for those situations
	  
	  //scalar
	  if(Enumeration.class.isAssignableFrom(clazz)){
	    return ((Enumeration) enums).getValue(); //autobox to Integer
	  }
	  
	  //1-dim array
	  if(Enumeration[].class.isAssignableFrom(clazz)){
	    Enumeration[] arr = (Enumeration[]) enums;
	    Integer[] ints = new Integer[arr.length];
	    for(int i = 0; i < arr.length; i++){
	      if(arr[i] == null){
	        ints[i] = null;
	      }else{
	        ints[i] = arr[i].getValue();
	      }
	    }
	    return ints;
	  }
	  
	  //multi-dim array otherwise
	  int[] ad = calculateArrayDimensions(enums, false);
	  Enumeration[] enumMux = (Enumeration[])MultiDimensionArrayUtils.muxArray(enums, ad);
	  Integer[] intMux = new Integer[enumMux.length];
      for(int i = 0; i < enumMux.length; i++){
        if(enumMux[i] == null){
          intMux[i] = null;
        }else{
          intMux[i] = enumMux[i].getValue();
        }
      }
      return MultiDimensionArrayUtils.demuxArray(intMux, ad);
	}
	
	//utility to convert byte[] and arrays of them to ByteString(array)s
	private static Object byteArraysToByteStrings(Object byteArrays){
	  Class<?> clazz = byteArrays.getClass();
	  
	  //special handling for byte[] and byte[][] as they are most common
	  
	  //scalar
	  if(byte[].class.isAssignableFrom(clazz)){
	    return ByteString.valueOf((byte[])byteArrays);
	  }
	  
	  //1-dim
	  if(byte[][].class.isAssignableFrom(clazz)){
	    byte[][] arr = (byte[][]) byteArrays;
	    ByteString[] bss = new ByteString[arr.length];
	    for(int i=0; i < arr.length;i++){
	      bss[i] = ByteString.valueOf(arr[i]);
	    }
	    return bss;
	  }
	  
	  //multidim
	  int[] ad = calculateArrayDimensions(byteArrays, true);
	  ArrayIterator<byte[]> iter = MultiDimensionArrayUtils.arrayIterator(byteArrays, ad);
	  List<ByteString> tmp = new ArrayList<ByteString>();
	  while(iter.hasNext()){
	    tmp.add(ByteString.valueOf(iter.next()));
	  }
	  return MultiDimensionArrayUtils.demuxArray(tmp.toArray(), ad, ByteString.class);
	}
	
	final Object value;
	final Class<?> compositeClass;

	/**
	 * Create variant.
	 *
	 * @param value
	 *            scalar, array or multi-dimension array
	 */
	public Variant(final Object value) {
	  if(value == null){
	    this.value = null;
	    this.compositeClass = null;
	    return;
	  }
	  
	  // Find the non-array composite class of the value
	  final Class<?> composite = MultiDimensionArrayUtils.getComponentType(value.getClass());
	  
	  // GH#82, if the composite is an Enumeration, convert it to equivalent
	  // Integer array (Note! must be Integer and not int)
	  if(Enumeration.class.isAssignableFrom(composite)){
	    this.value = enumsToInts(value);
	    
	    //Enumerations are as UA Int32 which is Java Integer
	    this.compositeClass = Integer.class;
	    return;
	  }
	  
	  // GH#81, convert byte[] and arrays of them to ByteStrings
	  // for backwards compatibility
	  if(byte.class.isAssignableFrom(composite)){
	    //this should mean value is byte[] or some dim.array of them,
	    //as the Object in the constructor autoboxes byte -> Byte
	    
	    this.value = byteArraysToByteStrings(value);
	    this.compositeClass = ByteString.class;
	    return;
	  }
	  
	  //now the value should be as such that it can be used directly
	  // OR it is of incompatible type
	  assertValidClass(composite);
	  this.value = value;
	  this.compositeClass = composite;
	}

	void assertValidClass(Class<?> clazz) {
		if (clazz.equals(Boolean.class))
			return;
		if (clazz.equals(Byte.class))
			return;
		if (clazz.equals(UnsignedByte.class))
			return;
		if (clazz.equals(Short.class))
			return;
		if (clazz.equals(UnsignedShort.class))
			return;
		if (clazz.equals(Integer.class))
			return;
		if (clazz.equals(UnsignedInteger.class))
			return;
		if (clazz.equals(Long.class))
			return;
		if (clazz.equals(UnsignedLong.class))
			return;
		if (clazz.equals(Float.class))
			return;
		if (clazz.equals(Double.class))
			return;
		if (clazz.equals(String.class))
			return;
		if (clazz.equals(DateTime.class))
			return;
		if (clazz.equals(UUID.class))
			return;
		if (clazz.equals(XmlElement.class))
			return;
		if (clazz.equals(NodeId.class))
			return;
		if (clazz.equals(ExpandedNodeId.class))
			return;
		if (clazz.equals(StatusCode.class))
			return;
		if (clazz.equals(QualifiedName.class))
			return;
		if (clazz.equals(LocalizedText.class))
			return;
		if (clazz.equals(ExtensionObject.class))
			return;
		if (clazz.equals(DataValue.class))
			return;
		if (clazz.equals(DiagnosticInfo.class))
			return;
		if (clazz.equals(Variant.class))
			return;
	    if (ByteString.class.equals(clazz)){
	        return;
	    }
		if (Structure.class.isAssignableFrom(clazz))
			return;

		
		throw new IllegalArgumentException("Variant cannot be "
				+ clazz.getCanonicalName());
	}

	/**
	 * <p>isEmpty.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isEmpty() {
		return value == null;
	}

	/**
	 * <p>isArray.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isArray() {
		if (value == null)
			return false;
		return value.getClass().isArray();
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getValue() {
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return toString(false);
	}

	/**
	 * Convert the value to string, including the compositeClass.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toStringWithType() {
		return toString(true);
	}
	
	/**
	 * Convert the value to string, optionally including the compositeClass.
	 *
	 * @param includeCompositeClass whether to also add the name of the compositeClass
	 * @return the value as string
	 */
	public String toString(boolean includeCompositeClass) {
		if (value == null)
			return "(null)";
		String classStr = "";
		String valueStr;
		if (isArray()) {
			if (includeCompositeClass) {
				int[] d = getArrayDimensions();
				StringBuilder sb = new StringBuilder();
				sb.append(d[0]);
				for (int i = 1; i < getDimension(); i++)
					sb.append(",").append(d[i]);
				classStr = String.format("(%s[%s]) ", compositeClassToString(),
						sb.toString());
			}
			valueStr = MultiDimensionArrayUtils.toString(value);
		} else {
			if (includeCompositeClass)
				classStr = String.format("(%s) ", compositeClassToString());
			valueStr = MultiDimensionArrayUtils.toString(value); // works for scalars too (byte[] as ByteString is "scalar")
		}
		return classStr + valueStr;
	}

	/**
	 * <p>compositeClassToString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	protected String compositeClassToString() {
		String className = compositeClass.getSimpleName();
		return className;
	}
	/**
	 * The class type of the variant value. If the value is an array, the type
	 * may be an ExtensionObject, in which case the array elements must be
	 * decoded to find out the actual type of each.
	 *
	 * @return the class of Value
	 */
	public Class<?> getCompositeClass() {
		return compositeClass;
	}

	/**
	 * <p>getArrayDimensions.</p>
	 *
	 * @return an array of int.
	 */
	public int[] getArrayDimensions() {
		return calculateArrayDimensions(value, false);
	}

  private static int[] calculateArrayDimensions(Object value, boolean byteArray) {
    int dim = MultiDimensionArrayUtils.getDimension(value);
    if(byteArray){
      dim--;
    }
		int result[] = new int[dim];
		if (dim == 0)
			return result;

		Object o = value;
		for (int i = 0; i < dim; i++) {
			Object[] array = (Object[]) o;
			result[i] = array.length;
			if (array.length == 0)
				break;
			o = array[0];
		}

		return result;
  }

	/**
	 * <p>getDimension.</p>
	 *
	 * @return a int.
	 */
	public int getDimension() {
		return MultiDimensionArrayUtils.getDimension(value);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		if (value == null)
			return 0;
		if (!isArray())
			return value.hashCode();
		return Arrays.deepHashCode((Object[]) value);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Variant))
			return false;
		Variant o = (Variant) obj;
		if (value == null && o.value == null)
			return true;
		if (value == null && o.value != null)
			return false;
		if (value != null && o.value == null)
			return false;

		Class<?> c = value.getClass();
		if (!c.equals(o.value.getClass()))
			return false;
		if (!isArray())
			return value.equals(o.value);
		return Arrays.deepEquals((Object[]) value, (Object[]) o.value);
	}

	/**
	 * Convert the variant value to any class. If it cannot be converted returns defaultValue.
	 *
	 * @param clazz The class type to convert to.
	 * @param defaultValue A default value to return, if the conversion cannot be done
	 * @return Value as the requested class or defaultValue, if it cannot be converted.
	 * @param <T> a T object.
	 */
	public <T> T asClass(Class<T> clazz, T defaultValue) {
		if (value == null)
			return defaultValue;
		try {
			return clazz.cast(value);
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}
	
  /**
   * Converts the value to the given Enumeration objects. NOTE! this works only if the value of this
   * Variant is Integer or somedimensional array of them. NOTE! If there is no matching Enumeration
   * value for internal value Integers, null is used.
   * 
   * @param clazz enumeration class
   * @return equivalent scalar/X-dim array of Enumerations of given class or null if internal value
   *         is null
   * @throws ClassCastException if internal value is not Integer (or somedimensional array of
   *         them)
   */
	public <T extends Enum<T> & Enumeration> Object asEnum(Class<T> clazz){	  
	  if(value == null){
	    return null;
	  }
	  
	  // UA does not have a null Int32 encoding, therefore the following
	  // check should be enough
	  if(!Integer.class.equals(compositeClass)){
	    throw new ClassCastException("Variant.asEnum can only be called on non-null Variants that have compositeClass of Integer, was: "+toStringWithType());
	  }
	  
	  //build look-up-map
      final T[] vals = clazz.getEnumConstants();
      final Map<Integer, T> lookUp = new HashMap<Integer, T>();
      for (T val : vals) {
          lookUp.put(val.getValue(), val);
      }
	  
	  // Special handling, since most often scalar or 1-dim array, 
	  Class<?> objClass = value.getClass();
	  
	  //scalar
	  if(Integer.class.equals(objClass)){
	    Integer t = (Integer)value;
	    return lookUp.get(t); //may be null
	  }
	  
	  //1-dim
	  if(Integer[].class.equals(objClass)){
	    Integer[] ints = (Integer[]) value;
	    @SuppressWarnings("unchecked")
        T[] r = (T[]) Array.newInstance(clazz, ints.length);
        for (int i = 0; i < ints.length; i++) {
          r[i] = lookUp.get(ints[i]); //may be null
        }
        return r;
	  }
	  
	  //multidim
	   int[] ad = calculateArrayDimensions(value, false);
	   Integer[] intMux = (Integer[])MultiDimensionArrayUtils.muxArray(value, ad);
	   @SuppressWarnings("unchecked")
       T[] enumMux = (T[]) Array.newInstance(clazz, intMux.length);
	   for(int i = 0; i < enumMux.length; i++){
	     enumMux[i] = lookUp.get(intMux[i]); //may be null
	   }
	   return MultiDimensionArrayUtils.demuxArray(enumMux, ad);
	}

	/**
	 * Returns the value of the specified Variant as a <code>boolean</code>
	 *
	 * @return the Variant value as boolean, if it can be cast to such
	 * @throws java.lang.ClassCastException if the value cannot be cast to boolean
	 */
	public boolean booleanValue() {
		if (value instanceof Boolean)
			return (Boolean) value;
		if (isNumber())
			return longValue() != 0;
		if(isEmpty())
			throw new ClassCastException("Variant null cannot be cast to boolean");
		if (getCompositeClass().equals(String.class)) {
			// The following values are defined for ContentFilter conversions
			// Spec. Part 4, v1.01 p.113
			String s = ((String) getValue()).toLowerCase();
			if (s.equals("true") || s.equals("1"))
				return true;
			if (s.equals("false") || s.equals("0"))
				return false;
			throw new ClassCastException("Variant String cannot be cast to boolean: "+ s);
		}
		return asClass(Boolean.class, false);
	}

	/**
	 * Returns the value of the specified Variant as a <code>Number</code>
	 *
	 * @return the Variant value as Number, if it can be cast to such
	 * @throws java.lang.ClassCastException if the value cannot be cast to Number
	 */
	public Number toNumber() {
		if (value instanceof Boolean)
			return booleanValue() ? 1 : 0;
		if (isNumber())
			return (Number) value;
		throw new ClassCastException("Variant is not a Number; CompositeClass="
				+ compositeClass);
	}


	/**
	 * <p>isNumber.</p>
	 *
	 * @return true if the current value is an instance of Number or Boolean (which can be used as Integer)
	 */
	public boolean isNumber() {
		return value instanceof Number || value instanceof Boolean;
	}

	/**
	 * <p>isComparable.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isComparable() {
		return value instanceof Comparable<?>;
	}
	
    /**
     * Returns the value of the specified Variant as an <code>int</code>.
     * This may involve rounding or truncation.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type <code>int</code>.
     * @throws java.lang.ClassCastException if the value cannot be cast to Number
     */
    public int intValue() {
		return toNumber().intValue();
	}

    /**
     * Returns the value of the specified Variant as a <code>long</code>.
     * This may involve rounding or truncation.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type <code>long</code>.
     * @throws java.lang.ClassCastException if the value cannot be cast to Number
     */
    public long longValue() {
		return toNumber().longValue();
	}

    /**
     * Returns the value of the specified Variant as a <code>float</code>.
     * This may involve rounding.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type <code>float</code>.
     * @throws java.lang.ClassCastException if the value cannot be cast to Number
     */
    public float floatValue() {
		return toNumber().floatValue();
	}

    /**
     * Returns the value of the specified Variant as a <code>double</code>.
     * This may involve rounding.
     *
     * @return  the numeric value represented by this object after conversion
     *          to type <code>double</code>.
     * @throws java.lang.ClassCastException if the value cannot be cast to Number
     */
    public double doubleValue() {
		return toNumber().doubleValue();
	}

	/**
	 * Returns the value of the specified Variant as a <code>byte</code>.
	 * This may involve rounding or truncation.
	 *
	 * @return  the numeric value represented by this object after conversion
	 *          to type <code>byte</code>.
	 * @throws java.lang.ClassCastException if the value cannot be cast to Number
	 */
	public byte byteValue() {
		return toNumber().byteValue();
	}

	/**
	 * Returns the value of the specified Variant as a <code>short</code>.
	 * This may involve rounding or truncation.
	 *
	 * @return  the numeric value represented by this object after conversion
	 *          to type <code>short</code>.
	 * @throws java.lang.ClassCastException if the value cannot be cast to Number
	 */
	public short shortValue() {
		return toNumber().shortValue();
	}

	/**
	 * Compares the value of a variant with another variant. If both variants
	 * {@link #isNumber()}, compares the {@link #floatValue()} of each.
	 * <p>
	 * Otherwise, it will check if {@link #isComparable()}, in which case it
	 * will compare the values as {@link Comparable}s. If the values are of
	 * different class, it will try to cast the values to the same type.
	 *
	 * @param value2
	 *            the value to compare to
	 * @return true if the values of the variants are equal
	 * @throws java.lang.ClassCastException
	 *             if the values cannot be compared
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compareTo(Variant value2) {
		if (value2 == null)
			throw new NullPointerException("cannot compare to null");
		if (isEmpty() && value2.isEmpty())
			return 0;
		if (isEmpty())
			return -1;
		if (value2.isEmpty())
			return 1;
		// if both are numbers, compare as float
		if (isNumber() && value2.isNumber()) {
			float diff = floatValue() - value2.floatValue();
			return (int) Math.signum(diff);
		}
		if (isComparable()) {
			Object v2;
			if (getCompositeClass().equals(value2.getCompositeClass()))
				v2 = value2.getValue();
			else
				v2 = value2.asClass(getCompositeClass(), null);
			if (v2 == null)
				throw new ClassCastException(String.format(
						"Cannot compare the variants of types %s vs. %s",
						getCompositeClass(), value2.getCompositeClass()));
			return ((Comparable) getValue()).compareTo(v2);
		}
		if (value2.isComparable()) {
			Object v1;
			if (getCompositeClass().equals(value2.getCompositeClass()))
				v1 = getValue();
			else
				v1 = asClass(value2.getCompositeClass(), null);
			if (v1 == null)
				throw new ClassCastException(String.format(
						"Cannot compare the variants of types %s vs. %s",
						getCompositeClass(), value2.getCompositeClass()));
			return ((Comparable) value2.getValue()).compareTo(v1);
		}
		throw new ClassCastException(String.format(
				"Cannot compare the variants of types %s vs. %s",
				getCompositeClass(), value2.getCompositeClass()));
	}

	/**
	 * Compares the value of a variant with another variant. If the values are
	 * of the same class, {@link #equals(Object)} is called. If both variants
	 * {@link #isNumber()}, compares the {@link #floatValue()} of each.
	 *
	 * @param value2 the value to compare to
	 * @return true if the values of the variants are equal
	 */
	public boolean valueEquals(Variant value2) {
		if (value2 == null)
			return false;
		if (isEmpty() || value2.isEmpty())
			return false;
		// #0, if the classes are the same, use equals
		if (getCompositeClass().equals(value2.getCompositeClass()))
			return equals(value2);
		// #1, if both are numbers, compare as float
		if (isNumber() && value2.isNumber())
			return floatValue() == value2.floatValue();
		// #2 try to cast value2 to the class of this
		final Object castValue = value2.asClass(getCompositeClass(), null);
		if (castValue != null)
			return equals(new Variant(castValue));
		// #3 cast this to the class of value2
		return value2.equals(asClass(value2.getCompositeClass(), null));
	}

	
}
