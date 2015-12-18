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



public class DataValue implements Cloneable {

	public static final NodeId ID = Identifiers.DataValue;
	
	Variant value;
    StatusCode statusCode;
    DateTime sourceTimestamp;
    UnsignedShort sourcePicoseconds;
    DateTime serverTimestamp;
    UnsignedShort serverPicoseconds;

	public DataValue() {
      this(StatusCode.GOOD);
    }
    
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

    public DataValue(Variant value, StatusCode statusCode, DateTime sourceTimestamp, DateTime serverTimestamp) {
		this(value, statusCode, sourceTimestamp, null, serverTimestamp, null);
    }

    public DataValue(StatusCode statusCode) {
    	this(Variant.NULL, statusCode); 
    }

    public DataValue(Variant value, StatusCode statusCode) {
		this(value, statusCode, null, null, null, null);
	}

	public DataValue(Variant variant) {
		this(variant, StatusCode.GOOD);
	}

	public DateTime getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(DateTime serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public DateTime getSourceTimestamp() {
        return sourceTimestamp;
    }

    public void setSourceTimestamp(DateTime sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

	public void setStatusCode(UnsignedInteger value) {
		setStatusCode(new StatusCode(value));
	}

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

    public void setValue(Variant value) {
    	if (value == null)
			this.value = Variant.NULL;
		else
			this.value = value;
    }
    
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
    
    public UnsignedShort getSourcePicoseconds() {
		return sourcePicoseconds;
	}

	public void setSourcePicoseconds(UnsignedShort sourcePicoseconds) {
		this.sourcePicoseconds = sourcePicoseconds;
	}

	public UnsignedShort getServerPicoseconds() {
		return serverPicoseconds;
	}

	public void setServerPicoseconds(UnsignedShort serverPicoseconds) {
		this.serverPicoseconds = serverPicoseconds;
	}
	
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

	@Override
	public Object clone() {
		return new DataValue(getValue(),
				getStatusCode(), getSourceTimestamp(),
				getServerPicoseconds(),
				getServerTimestamp(),
				getServerPicoseconds());	}
	   
}
