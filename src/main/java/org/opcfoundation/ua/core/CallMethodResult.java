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
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.utils.AbstractStructure;



public class CallMethodResult extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.CallMethodResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.CallMethodResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.CallMethodResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected StatusCode[] InputArgumentResults;
    protected DiagnosticInfo[] InputArgumentDiagnosticInfos;
    protected Variant[] OutputArguments;
    
    public CallMethodResult() {}
    
    public CallMethodResult(StatusCode StatusCode, StatusCode[] InputArgumentResults, DiagnosticInfo[] InputArgumentDiagnosticInfos, Variant[] OutputArguments)
    {
        this.StatusCode = StatusCode;
        this.InputArgumentResults = InputArgumentResults;
        this.InputArgumentDiagnosticInfos = InputArgumentDiagnosticInfos;
        this.OutputArguments = OutputArguments;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public StatusCode[] getInputArgumentResults()
    {
        return InputArgumentResults;
    }
    
    public void setInputArgumentResults(StatusCode[] InputArgumentResults)
    {
        this.InputArgumentResults = InputArgumentResults;
    }
    
    public DiagnosticInfo[] getInputArgumentDiagnosticInfos()
    {
        return InputArgumentDiagnosticInfos;
    }
    
    public void setInputArgumentDiagnosticInfos(DiagnosticInfo[] InputArgumentDiagnosticInfos)
    {
        this.InputArgumentDiagnosticInfos = InputArgumentDiagnosticInfos;
    }
    
    public Variant[] getOutputArguments()
    {
        return OutputArguments;
    }
    
    public void setOutputArguments(Variant[] OutputArguments)
    {
        this.OutputArguments = OutputArguments;
    }
    
    /**
      * Deep clone
      *
      * @return cloned CallMethodResult
      */
    public CallMethodResult clone()
    {
        CallMethodResult result = (CallMethodResult) super.clone();
        result.StatusCode = StatusCode;
        result.InputArgumentResults = InputArgumentResults==null ? null : InputArgumentResults.clone();
        result.InputArgumentDiagnosticInfos = InputArgumentDiagnosticInfos==null ? null : InputArgumentDiagnosticInfos.clone();
        result.OutputArguments = OutputArguments==null ? null : OutputArguments.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CallMethodResult other = (CallMethodResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (InputArgumentResults==null) {
            if (other.InputArgumentResults != null) return false;
        } else if (!Arrays.equals(InputArgumentResults, other.InputArgumentResults)) return false;
        if (InputArgumentDiagnosticInfos==null) {
            if (other.InputArgumentDiagnosticInfos != null) return false;
        } else if (!Arrays.equals(InputArgumentDiagnosticInfos, other.InputArgumentDiagnosticInfos)) return false;
        if (OutputArguments==null) {
            if (other.OutputArguments != null) return false;
        } else if (!Arrays.equals(OutputArguments, other.OutputArguments)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((StatusCode == null) ? 0 : StatusCode.hashCode());
        result = prime * result
                + ((InputArgumentResults == null) ? 0 : Arrays.hashCode(InputArgumentResults));
        result = prime * result
                + ((InputArgumentDiagnosticInfos == null) ? 0 : Arrays.hashCode(InputArgumentDiagnosticInfos));
        result = prime * result
                + ((OutputArguments == null) ? 0 : Arrays.hashCode(OutputArguments));
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
		return "CallMethodResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
