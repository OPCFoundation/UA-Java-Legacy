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

import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.core.QueryDataSet;
import org.opcfoundation.ua.core.ResponseHeader;


public class QueryNextResponse extends Object implements ServiceResponse {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.QueryNextResponse);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.QueryNextResponse_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.QueryNextResponse_Encoding_DefaultXml);
	
    protected ResponseHeader ResponseHeader;
    protected QueryDataSet[] QueryDataSets;
    protected byte[] RevisedContinuationPoint;
    
    public QueryNextResponse() {}
    
    public QueryNextResponse(ResponseHeader ResponseHeader, QueryDataSet[] QueryDataSets, byte[] RevisedContinuationPoint)
    {
        this.ResponseHeader = ResponseHeader;
        this.QueryDataSets = QueryDataSets;
        this.RevisedContinuationPoint = RevisedContinuationPoint;
    }
    
    public ResponseHeader getResponseHeader()
    {
        return ResponseHeader;
    }
    
    public void setResponseHeader(ResponseHeader ResponseHeader)
    {
        this.ResponseHeader = ResponseHeader;
    }
    
    public QueryDataSet[] getQueryDataSets()
    {
        return QueryDataSets;
    }
    
    public void setQueryDataSets(QueryDataSet[] QueryDataSets)
    {
        this.QueryDataSets = QueryDataSets;
    }
    
    public byte[] getRevisedContinuationPoint()
    {
        return RevisedContinuationPoint;
    }
    
    public void setRevisedContinuationPoint(byte[] RevisedContinuationPoint)
    {
        this.RevisedContinuationPoint = RevisedContinuationPoint;
    }
    
    /**
      * Deep clone
      *
      * @return cloned QueryNextResponse
      */
    public QueryNextResponse clone()
    {
        QueryNextResponse result = new QueryNextResponse();
        result.ResponseHeader = ResponseHeader==null ? null : ResponseHeader.clone();
        if (QueryDataSets!=null) {
            result.QueryDataSets = new QueryDataSet[QueryDataSets.length];
            for (int i=0; i<QueryDataSets.length; i++)
                result.QueryDataSets[i] = QueryDataSets[i].clone();
        }
        result.RevisedContinuationPoint = RevisedContinuationPoint;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        QueryNextResponse other = (QueryNextResponse) obj;
        if (ResponseHeader==null) {
            if (other.ResponseHeader != null) return false;
        } else if (!ResponseHeader.equals(other.ResponseHeader)) return false;
        if (QueryDataSets==null) {
            if (other.QueryDataSets != null) return false;
        } else if (!Arrays.equals(QueryDataSets, other.QueryDataSets)) return false;
        if (RevisedContinuationPoint==null) {
            if (other.RevisedContinuationPoint != null) return false;
        } else if (!RevisedContinuationPoint.equals(other.RevisedContinuationPoint)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ResponseHeader == null) ? 0 : ResponseHeader.hashCode());
        result = prime * result
                + ((QueryDataSets == null) ? 0 : Arrays.hashCode(QueryDataSets));
        result = prime * result
                + ((RevisedContinuationPoint == null) ? 0 : RevisedContinuationPoint.hashCode());
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
