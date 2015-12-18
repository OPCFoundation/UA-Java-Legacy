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
import org.opcfoundation.ua.core.ContentFilterElementResult;



public class ContentFilterResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ContentFilterResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ContentFilterResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ContentFilterResult_Encoding_DefaultXml);
	
    protected ContentFilterElementResult[] ElementResults;
    protected DiagnosticInfo[] ElementDiagnosticInfos;
    
    public ContentFilterResult() {}
    
    public ContentFilterResult(ContentFilterElementResult[] ElementResults, DiagnosticInfo[] ElementDiagnosticInfos)
    {
        this.ElementResults = ElementResults;
        this.ElementDiagnosticInfos = ElementDiagnosticInfos;
    }
    
    public ContentFilterElementResult[] getElementResults()
    {
        return ElementResults;
    }
    
    public void setElementResults(ContentFilterElementResult[] ElementResults)
    {
        this.ElementResults = ElementResults;
    }
    
    public DiagnosticInfo[] getElementDiagnosticInfos()
    {
        return ElementDiagnosticInfos;
    }
    
    public void setElementDiagnosticInfos(DiagnosticInfo[] ElementDiagnosticInfos)
    {
        this.ElementDiagnosticInfos = ElementDiagnosticInfos;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ContentFilterResult
      */
    public ContentFilterResult clone()
    {
        ContentFilterResult result = new ContentFilterResult();
        if (ElementResults!=null) {
            result.ElementResults = new ContentFilterElementResult[ElementResults.length];
            for (int i=0; i<ElementResults.length; i++)
                result.ElementResults[i] = ElementResults[i].clone();
        }
        result.ElementDiagnosticInfos = ElementDiagnosticInfos==null ? null : ElementDiagnosticInfos.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ContentFilterResult other = (ContentFilterResult) obj;
        if (ElementResults==null) {
            if (other.ElementResults != null) return false;
        } else if (!Arrays.equals(ElementResults, other.ElementResults)) return false;
        if (ElementDiagnosticInfos==null) {
            if (other.ElementDiagnosticInfos != null) return false;
        } else if (!Arrays.equals(ElementDiagnosticInfos, other.ElementDiagnosticInfos)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ElementResults == null) ? 0 : Arrays.hashCode(ElementResults));
        result = prime * result
                + ((ElementDiagnosticInfos == null) ? 0 : Arrays.hashCode(ElementDiagnosticInfos));
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
		return "ContentFilterResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
