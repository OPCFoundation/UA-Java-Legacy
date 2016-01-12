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

import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.core.ContentFilterResult;
import org.opcfoundation.ua.core.MonitoringFilterResult;



public class EventFilterResult extends MonitoringFilterResult implements Structure, Cloneable {
	
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
        EventFilterResult result = new EventFilterResult();
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
