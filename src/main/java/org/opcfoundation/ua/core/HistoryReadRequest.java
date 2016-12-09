/* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.core;

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.core.HistoryReadValueId;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.utils.AbstractStructure;


public class HistoryReadRequest extends AbstractStructure implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.HistoryReadRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.HistoryReadRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.HistoryReadRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected ExtensionObject HistoryReadDetails;
    protected TimestampsToReturn TimestampsToReturn;
    protected Boolean ReleaseContinuationPoints;
    protected HistoryReadValueId[] NodesToRead;
    
    public HistoryReadRequest() {}
    
    public HistoryReadRequest(RequestHeader RequestHeader, ExtensionObject HistoryReadDetails, TimestampsToReturn TimestampsToReturn, Boolean ReleaseContinuationPoints, HistoryReadValueId[] NodesToRead)
    {
        this.RequestHeader = RequestHeader;
        this.HistoryReadDetails = HistoryReadDetails;
        this.TimestampsToReturn = TimestampsToReturn;
        this.ReleaseContinuationPoints = ReleaseContinuationPoints;
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
    
    public ExtensionObject getHistoryReadDetails()
    {
        return HistoryReadDetails;
    }
    
    public void setHistoryReadDetails(ExtensionObject HistoryReadDetails)
    {
        this.HistoryReadDetails = HistoryReadDetails;
    }
    
    public TimestampsToReturn getTimestampsToReturn()
    {
        return TimestampsToReturn;
    }
    
    public void setTimestampsToReturn(TimestampsToReturn TimestampsToReturn)
    {
        this.TimestampsToReturn = TimestampsToReturn;
    }
    
    public Boolean getReleaseContinuationPoints()
    {
        return ReleaseContinuationPoints;
    }
    
    public void setReleaseContinuationPoints(Boolean ReleaseContinuationPoints)
    {
        this.ReleaseContinuationPoints = ReleaseContinuationPoints;
    }
    
    public HistoryReadValueId[] getNodesToRead()
    {
        return NodesToRead;
    }
    
    public void setNodesToRead(HistoryReadValueId[] NodesToRead)
    {
        this.NodesToRead = NodesToRead;
    }
    
    /**
      * Deep clone
      *
      * @return cloned HistoryReadRequest
      */
    public HistoryReadRequest clone()
    {
        HistoryReadRequest result = (HistoryReadRequest) super.clone();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.HistoryReadDetails = HistoryReadDetails;
        result.TimestampsToReturn = TimestampsToReturn;
        result.ReleaseContinuationPoints = ReleaseContinuationPoints;
        if (NodesToRead!=null) {
            result.NodesToRead = new HistoryReadValueId[NodesToRead.length];
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
        HistoryReadRequest other = (HistoryReadRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (HistoryReadDetails==null) {
            if (other.HistoryReadDetails != null) return false;
        } else if (!HistoryReadDetails.equals(other.HistoryReadDetails)) return false;
        if (TimestampsToReturn==null) {
            if (other.TimestampsToReturn != null) return false;
        } else if (!TimestampsToReturn.equals(other.TimestampsToReturn)) return false;
        if (ReleaseContinuationPoints==null) {
            if (other.ReleaseContinuationPoints != null) return false;
        } else if (!ReleaseContinuationPoints.equals(other.ReleaseContinuationPoints)) return false;
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
                + ((HistoryReadDetails == null) ? 0 : HistoryReadDetails.hashCode());
        result = prime * result
                + ((TimestampsToReturn == null) ? 0 : TimestampsToReturn.hashCode());
        result = prime * result
                + ((ReleaseContinuationPoints == null) ? 0 : ReleaseContinuationPoints.hashCode());
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
