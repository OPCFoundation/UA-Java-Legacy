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
import org.opcfoundation.ua.utils.ObjectUtils;



/**
 * <p>DataValue class.</p>
 *
 */
public class DataValue implements Cloneable {

	/** Constant <code>ID</code> */
	public static final NodeId ID = Identifiers.DataValue;
	
	Variant value;
    StatusCode statusCode;
    DateTime sourceTimestamp;
    UnsignedShort sourcePicoseconds;
    DateTime serverTimestamp;
    UnsignedShort serverPicoseconds;

	/**
	 * <p>Constructor for DataValue.</p>
	 */
	public DataValue() {
      this(StatusCode.GOOD);
    }
    
    /**
     * <p>Constructor for DataValue.</p>
     *
     * @param value a {@link org.opcfoundation.ua.builtintypes.Variant} object.
     * @param statusCode a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
     * @param sourceTimestamp a {@link org.opcfoundation.ua.builtintypes.DateTime} object.
     * @param sourcePicoseconds a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
     * @param serverTimestamp a {@link org.opcfoundation.ua.builtintypes.DateTime} object.
     * @param serverPicoseconds a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
     */
    public DataValue(Variant value, StatusCode statusCode, DateTime sourceTimestamp, UnsignedShort sourcePicoseconds, DateTime serverTimestamp, UnsignedShort serverPicoseconds) {
        super();
        this.statusCode = statusCode;
        this.sourceTimestamp = sourceTimestamp;
        this.serverTimestamp = serverTimestamp;
		this.sourcePicoseconds = sourcePicoseconds == null ? UnsignedShort.ZERO
				: sourcePicoseconds;
		this.serverPicoseconds = serverPicoseconds == null ? UnsignedShort.ZERO
				: serverPicoseconds;
        setValue(value);
    }

    /**
     * <p>Constructor for DataValue.</p>
     *
     * @param value a {@link org.opcfoundation.ua.builtintypes.Variant} object.
     * @param statusCode a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
     * @param sourceTimestamp a {@link org.opcfoundation.ua.builtintypes.DateTime} object.
     * @param serverTimestamp a {@link org.opcfoundation.ua.builtintypes.DateTime} object.
     */
    public DataValue(Variant value, StatusCode statusCode, DateTime sourceTimestamp, DateTime serverTimestamp) {
		this(value, statusCode, sourceTimestamp, null, serverTimestamp, null);
    }

    /**
     * <p>Constructor for DataValue.</p>
     *
     * @param statusCode a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
     */
    public DataValue(StatusCode statusCode) {
    	this(Variant.NULL, statusCode); 
    }

    /**
     * <p>Constructor for DataValue.</p>
     *
     * @param value a {@link org.opcfoundation.ua.builtintypes.Variant} object.
     * @param statusCode a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
     */
    public DataValue(Variant value, StatusCode statusCode) {
		this(value, statusCode, null, null, null, null);
	}

	/**
	 * <p>Constructor for DataValue.</p>
	 *
	 * @param variant a {@link org.opcfoundation.ua.builtintypes.Variant} object.
	 */
	public DataValue(Variant variant) {
		this(variant, StatusCode.GOOD);
	}

	/**
	 * <p>Getter for the field <code>serverTimestamp</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.DateTime} object.
	 */
	public DateTime getServerTimestamp() {
        return serverTimestamp;
    }

    /**
     * <p>Setter for the field <code>serverTimestamp</code>.</p>
     *
     * @param serverTimestamp a {@link org.opcfoundation.ua.builtintypes.DateTime} object.
     */
    public void setServerTimestamp(DateTime serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    /**
     * <p>Getter for the field <code>sourceTimestamp</code>.</p>
     *
     * @return a {@link org.opcfoundation.ua.builtintypes.DateTime} object.
     */
    public DateTime getSourceTimestamp() {
        return sourceTimestamp;
    }

    /**
     * <p>Setter for the field <code>sourceTimestamp</code>.</p>
     *
     * @param sourceTimestamp a {@link org.opcfoundation.ua.builtintypes.DateTime} object.
     */
    public void setSourceTimestamp(DateTime sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }

    /**
     * <p>Getter for the field <code>statusCode</code>.</p>
     *
     * @return a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
     */
    public StatusCode getStatusCode() {
        return statusCode;
    }

    /**
     * <p>Setter for the field <code>statusCode</code>.</p>
     *
     * @param statusCode a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
     */
    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

	/**
	 * <p>Setter for the field <code>statusCode</code>.</p>
	 *
	 * @param value a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public void setStatusCode(UnsignedInteger value) {
		setStatusCode(new StatusCode(value));
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.Variant} object.
	 */
	public Variant getValue() {
        return value;
    }
	
	/**
	 * Check if Value is null.
	 *
	 * @return true if Value is null or the Variant returned by it contains a null.
	 */
	public boolean isNull() {
		return value.getValue() == null;
	}

    /**
     * <p>Setter for the field <code>value</code>.</p>
     *
     * @param value a {@link org.opcfoundation.ua.builtintypes.Variant} object.
     */
    public void setValue(Variant value) {
    	if (value == null)
			this.value = Variant.NULL;
		else
			this.value = value;
    }
    
    /** {@inheritDoc} */
    @Override
    public int hashCode() {
    	return 
		ObjectUtils.hashCode(value) |
		ObjectUtils.hashCode(statusCode) |
		ObjectUtils.hashCode(sourceTimestamp) |
		ObjectUtils.hashCode(sourcePicoseconds) |
		ObjectUtils.hashCode(serverTimestamp) |
		ObjectUtils.hashCode(serverPicoseconds);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
    	if (!(obj instanceof DataValue)) return false;
    	DataValue o = (DataValue) obj;
    	return 
			ObjectUtils.objectEquals(o.value, value) &&
			ObjectUtils.objectEquals(o.statusCode, statusCode) &&
			ObjectUtils.objectEquals(o.sourceTimestamp, sourceTimestamp) &&
			ObjectUtils.objectEquals(o.serverTimestamp, serverTimestamp) &&
			ObjectUtils.objectEquals(o.sourcePicoseconds, sourcePicoseconds) &&
			ObjectUtils.objectEquals(o.serverPicoseconds, serverPicoseconds);		
    }
    
    /**
     * <p>Getter for the field <code>sourcePicoseconds</code>.</p>
     *
     * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
     */
    public UnsignedShort getSourcePicoseconds() {
		return sourcePicoseconds;
	}

	/**
	 * <p>Setter for the field <code>sourcePicoseconds</code>.</p>
	 *
	 * @param sourcePicoseconds a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
	 */
	public void setSourcePicoseconds(UnsignedShort sourcePicoseconds) {
		this.sourcePicoseconds = sourcePicoseconds;
	}

	/**
	 * <p>Getter for the field <code>serverPicoseconds</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
	 */
	public UnsignedShort getServerPicoseconds() {
		return serverPicoseconds;
	}

	/**
	 * <p>Setter for the field <code>serverPicoseconds</code>.</p>
	 *
	 * @param serverPicoseconds a {@link org.opcfoundation.ua.builtintypes.UnsignedShort} object.
	 */
	public void setServerPicoseconds(UnsignedShort serverPicoseconds) {
		this.serverPicoseconds = serverPicoseconds;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DataValue(");
		sb.append("value="+value);
		sb.append(", statusCode="+statusCode);
		sb.append(", sourceTimestamp="+sourceTimestamp);
		sb.append(", sourcePicoseconds="+sourcePicoseconds);
		sb.append(", serverTimestamp="+serverTimestamp);
		sb.append(", serverPicoseconds="+serverPicoseconds);
		sb.append(")");
		return sb.toString();
	}

	/** {@inheritDoc} */
	@Override
	public Object clone() {
		return new DataValue(getValue(),
				getStatusCode(), getSourceTimestamp(),
				getServerPicoseconds(),
				getServerTimestamp(),
				getServerPicoseconds());	}
	   
}
