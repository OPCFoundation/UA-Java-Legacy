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

import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.core.ContentFilterResult;
import org.opcfoundation.ua.core.ParsingResult;
import org.opcfoundation.ua.core.QueryDataSet;
import org.opcfoundation.ua.core.ResponseHeader;
import org.opcfoundation.ua.utils.AbstractStructure;


public class QueryFirstResponse extends AbstractStructure implements ServiceResponse {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.QueryFirstResponse);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.QueryFirstResponse_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.QueryFirstResponse_Encoding_DefaultXml);
	
    protected ResponseHeader ResponseHeader;
    protected QueryDataSet[] QueryDataSets;
    protected ByteString ContinuationPoint;
    protected ParsingResult[] ParsingResults;
    protected DiagnosticInfo[] DiagnosticInfos;
    protected ContentFilterResult FilterResult;
    
    public QueryFirstResponse() {}
    
    public QueryFirstResponse(ResponseHeader ResponseHeader, QueryDataSet[] QueryDataSets, ByteString ContinuationPoint, ParsingResult[] ParsingResults, DiagnosticInfo[] DiagnosticInfos, ContentFilterResult FilterResult)
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
    
    public ByteString getContinuationPoint()
    {
        return ContinuationPoint;
    }
    
    public void setContinuationPoint(ByteString ContinuationPoint)
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
        QueryFirstResponse result = (QueryFirstResponse) super.clone();
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
