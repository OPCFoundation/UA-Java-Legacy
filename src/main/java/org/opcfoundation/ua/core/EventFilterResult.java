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

import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.core.ContentFilterResult;
import org.opcfoundation.ua.core.MonitoringFilterResult;



public class EventFilterResult extends MonitoringFilterResult {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.EventFilterResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.EventFilterResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.EventFilterResult_Encoding_DefaultXml);
	
    protected StatusCode[] SelectClauseResults;
    protected DiagnosticInfo[] SelectClauseDiagnosticInfos;
    protected ContentFilterResult WhereClauseResult;
    
    public EventFilterResult() {}
    
    public EventFilterResult(StatusCode[] SelectClauseResults, DiagnosticInfo[] SelectClauseDiagnosticInfos, ContentFilterResult WhereClauseResult)
    {
        this.SelectClauseResults = SelectClauseResults;
        this.SelectClauseDiagnosticInfos = SelectClauseDiagnosticInfos;
        this.WhereClauseResult = WhereClauseResult;
    }
    
    public StatusCode[] getSelectClauseResults()
    {
        return SelectClauseResults;
    }
    
    public void setSelectClauseResults(StatusCode[] SelectClauseResults)
    {
        this.SelectClauseResults = SelectClauseResults;
    }
    
    public DiagnosticInfo[] getSelectClauseDiagnosticInfos()
    {
        return SelectClauseDiagnosticInfos;
    }
    
    public void setSelectClauseDiagnosticInfos(DiagnosticInfo[] SelectClauseDiagnosticInfos)
    {
        this.SelectClauseDiagnosticInfos = SelectClauseDiagnosticInfos;
    }
    
    public ContentFilterResult getWhereClauseResult()
    {
        return WhereClauseResult;
    }
    
    public void setWhereClauseResult(ContentFilterResult WhereClauseResult)
    {
        this.WhereClauseResult = WhereClauseResult;
    }
    
    /**
      * Deep clone
      *
      * @return cloned EventFilterResult
      */
    public EventFilterResult clone()
    {
        EventFilterResult result = (EventFilterResult) super.clone();
        result.SelectClauseResults = SelectClauseResults==null ? null : SelectClauseResults.clone();
        result.SelectClauseDiagnosticInfos = SelectClauseDiagnosticInfos==null ? null : SelectClauseDiagnosticInfos.clone();
        result.WhereClauseResult = WhereClauseResult==null ? null : WhereClauseResult.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EventFilterResult other = (EventFilterResult) obj;
        if (SelectClauseResults==null) {
            if (other.SelectClauseResults != null) return false;
        } else if (!Arrays.equals(SelectClauseResults, other.SelectClauseResults)) return false;
        if (SelectClauseDiagnosticInfos==null) {
            if (other.SelectClauseDiagnosticInfos != null) return false;
        } else if (!Arrays.equals(SelectClauseDiagnosticInfos, other.SelectClauseDiagnosticInfos)) return false;
        if (WhereClauseResult==null) {
            if (other.WhereClauseResult != null) return false;
        } else if (!WhereClauseResult.equals(other.WhereClauseResult)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((SelectClauseResults == null) ? 0 : Arrays.hashCode(SelectClauseResults));
        result = prime * result
                + ((SelectClauseDiagnosticInfos == null) ? 0 : Arrays.hashCode(SelectClauseDiagnosticInfos));
        result = prime * result
                + ((WhereClauseResult == null) ? 0 : WhereClauseResult.hashCode());
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
		return "EventFilterResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
