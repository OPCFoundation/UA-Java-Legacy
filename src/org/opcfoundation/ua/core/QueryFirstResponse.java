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
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.core.ContentFilterResult;
import org.opcfoundation.ua.core.ParsingResult;
import org.opcfoundation.ua.core.QueryDataSet;
import org.opcfoundation.ua.core.ResponseHeader;


public class QueryFirstResponse extends Object implements ServiceResponse {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.QueryFirstResponse);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.QueryFirstResponse_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.QueryFirstResponse_Encoding_DefaultXml);
	
    protected ResponseHeader ResponseHeader;
    protected QueryDataSet[] QueryDataSets;
    protected byte[] ContinuationPoint;
    protected ParsingResult[] ParsingResults;
    protected DiagnosticInfo[] DiagnosticInfos;
    protected ContentFilterResult FilterResult;
    
    public QueryFirstResponse() {}
    
    public QueryFirstResponse(ResponseHeader ResponseHeader, QueryDataSet[] QueryDataSets, byte[] ContinuationPoint, ParsingResult[] ParsingResults, DiagnosticInfo[] DiagnosticInfos, ContentFilterResult FilterResult)
    {
        this.ResponseHeader = ResponseHeader;
        this.QueryDataSets = QueryDataSets;
        this.ContinuationPoint = ContinuationPoint;
        this.ParsingResults = ParsingResults;
        this.DiagnosticInfos = DiagnosticInfos;
        this.FilterResult = FilterResult;
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
    
    public byte[] getContinuationPoint()
    {
        return ContinuationPoint;
    }
    
    public void setContinuationPoint(byte[] ContinuationPoint)
    {
        this.ContinuationPoint = ContinuationPoint;
    }
    
    public ParsingResult[] getParsingResults()
    {
        return ParsingResults;
    }
    
    public void setParsingResults(ParsingResult[] ParsingResults)
    {
        this.ParsingResults = ParsingResults;
    }
    
    public DiagnosticInfo[] getDiagnosticInfos()
    {
        return DiagnosticInfos;
    }
    
    public void setDiagnosticInfos(DiagnosticInfo[] DiagnosticInfos)
    {
        this.DiagnosticInfos = DiagnosticInfos;
    }
    
    public ContentFilterResult getFilterResult()
    {
        return FilterResult;
    }
    
    public void setFilterResult(ContentFilterResult FilterResult)
    {
        this.FilterResult = FilterResult;
    }
    
    /**
      * Deep clone
      *
      * @return cloned QueryFirstResponse
      */
    public QueryFirstResponse clone()
    {
        QueryFirstResponse result = new QueryFirstResponse();
        result.ResponseHeader = ResponseHeader==null ? null : ResponseHeader.clone();
        if (QueryDataSets!=null) {
            result.QueryDataSets = new QueryDataSet[QueryDataSets.length];
            for (int i=0; i<QueryDataSets.length; i++)
                result.QueryDataSets[i] = QueryDataSets[i].clone();
        }
        result.ContinuationPoint = ContinuationPoint;
        if (ParsingResults!=null) {
            result.ParsingResults = new ParsingResult[ParsingResults.length];
            for (int i=0; i<ParsingResults.length; i++)
                result.ParsingResults[i] = ParsingResults[i].clone();
        }
        result.DiagnosticInfos = DiagnosticInfos==null ? null : DiagnosticInfos.clone();
        result.FilterResult = FilterResult==null ? null : FilterResult.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        QueryFirstResponse other = (QueryFirstResponse) obj;
        if (ResponseHeader==null) {
            if (other.ResponseHeader != null) return false;
        } else if (!ResponseHeader.equals(other.ResponseHeader)) return false;
        if (QueryDataSets==null) {
            if (other.QueryDataSets != null) return false;
        } else if (!Arrays.equals(QueryDataSets, other.QueryDataSets)) return false;
        if (ContinuationPoint==null) {
            if (other.ContinuationPoint != null) return false;
        } else if (!ContinuationPoint.equals(other.ContinuationPoint)) return false;
        if (ParsingResults==null) {
            if (other.ParsingResults != null) return false;
        } else if (!Arrays.equals(ParsingResults, other.ParsingResults)) return false;
        if (DiagnosticInfos==null) {
            if (other.DiagnosticInfos != null) return false;
        } else if (!Arrays.equals(DiagnosticInfos, other.DiagnosticInfos)) return false;
        if (FilterResult==null) {
            if (other.FilterResult != null) return false;
        } else if (!FilterResult.equals(other.FilterResult)) return false;
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
                + ((ContinuationPoint == null) ? 0 : ContinuationPoint.hashCode());
        result = prime * result
                + ((ParsingResults == null) ? 0 : Arrays.hashCode(ParsingResults));
        result = prime * result
                + ((DiagnosticInfos == null) ? 0 : Arrays.hashCode(DiagnosticInfos));
        result = prime * result
                + ((FilterResult == null) ? 0 : FilterResult.hashCode());
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
