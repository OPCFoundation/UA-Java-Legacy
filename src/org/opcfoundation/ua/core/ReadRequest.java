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

package org.opcfoundation.ua.core;

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.TimestampsToReturn;


public class ReadRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ReadRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ReadRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ReadRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected Double MaxAge;
    protected TimestampsToReturn TimestampsToReturn;
    protected ReadValueId[] NodesToRead;
    
    public ReadRequest() {}
    
    public ReadRequest(RequestHeader RequestHeader, Double MaxAge, TimestampsToReturn TimestampsToReturn, ReadValueId[] NodesToRead)
    {
        this.RequestHeader = RequestHeader;
        this.MaxAge = MaxAge;
        this.TimestampsToReturn = TimestampsToReturn;
        this.NodesToRead = NodesToRead;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public Double getMaxAge()
    {
        return MaxAge;
    }
    
    public void setMaxAge(Double MaxAge)
    {
        this.MaxAge = MaxAge;
    }
    
    public TimestampsToReturn getTimestampsToReturn()
    {
        return TimestampsToReturn;
    }
    
    public void setTimestampsToReturn(TimestampsToReturn TimestampsToReturn)
    {
        this.TimestampsToReturn = TimestampsToReturn;
    }
    
    public ReadValueId[] getNodesToRead()
    {
        return NodesToRead;
    }
    
    public void setNodesToRead(ReadValueId[] NodesToRead)
    {
        this.NodesToRead = NodesToRead;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ReadRequest
      */
    public ReadRequest clone()
    {
        ReadRequest result = new ReadRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.MaxAge = MaxAge;
        result.TimestampsToReturn = TimestampsToReturn;
        if (NodesToRead!=null) {
            result.NodesToRead = new ReadValueId[NodesToRead.length];
            for (int i=0; i<NodesToRead.length; i++)
                result.NodesToRead[i] = NodesToRead[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ReadRequest other = (ReadRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (MaxAge==null) {
            if (other.MaxAge != null) return false;
        } else if (!MaxAge.equals(other.MaxAge)) return false;
        if (TimestampsToReturn==null) {
            if (other.TimestampsToReturn != null) return false;
        } else if (!TimestampsToReturn.equals(other.TimestampsToReturn)) return false;
        if (NodesToRead==null) {
            if (other.NodesToRead != null) return false;
        } else if (!Arrays.equals(NodesToRead, other.NodesToRead)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((RequestHeader == null) ? 0 : RequestHeader.hashCode());
        result = prime * result
                + ((MaxAge == null) ? 0 : MaxAge.hashCode());
        result = prime * result
                + ((TimestampsToReturn == null) ? 0 : TimestampsToReturn.hashCode());
        result = prime * result
                + ((NodesToRead == null) ? 0 : Arrays.hashCode(NodesToRead));
        return result;
    }
    
 

	public ExpandedNodeId getTypeId() {
		return ID;
	}

	public ExpandedNodeId getXmlEncodeId() {
		return XML;
	}
	
	public ExpandedNodeId getBinaryEncodeId() {
		return BINARY;
	}
	
	public String toString() {
		return ObjectUtils.printFieldsDeep(this);
	}
	
}
