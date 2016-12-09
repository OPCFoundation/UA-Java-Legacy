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
import org.opcfoundation.ua.core.ContentFilterElementResult;
import org.opcfoundation.ua.utils.AbstractStructure;



public class ContentFilterResult extends AbstractStructure {
	
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
        ContentFilterResult result = (ContentFilterResult) super.clone();
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
