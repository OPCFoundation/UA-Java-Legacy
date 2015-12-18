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
import org.opcfoundation.ua.builtintypes.Variant;



public class CallMethodResult extends Object implements Structure, Cloneable {
	
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
        CallMethodResult result = new CallMethodResult();
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
