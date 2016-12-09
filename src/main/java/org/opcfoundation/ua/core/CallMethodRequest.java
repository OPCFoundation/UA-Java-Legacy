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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.utils.AbstractStructure;



public class CallMethodRequest extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.CallMethodRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.CallMethodRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.CallMethodRequest_Encoding_DefaultXml);
	
    protected NodeId ObjectId;
    protected NodeId MethodId;
    protected Variant[] InputArguments;
    
    public CallMethodRequest() {}
    
    public CallMethodRequest(NodeId ObjectId, NodeId MethodId, Variant[] InputArguments)
    {
        this.ObjectId = ObjectId;
        this.MethodId = MethodId;
        this.InputArguments = InputArguments;
    }
    
    public NodeId getObjectId()
    {
        return ObjectId;
    }
    
    public void setObjectId(NodeId ObjectId)
    {
        this.ObjectId = ObjectId;
    }
    
    public NodeId getMethodId()
    {
        return MethodId;
    }
    
    public void setMethodId(NodeId MethodId)
    {
        this.MethodId = MethodId;
    }
    
    public Variant[] getInputArguments()
    {
        return InputArguments;
    }
    
    public void setInputArguments(Variant[] InputArguments)
    {
        this.InputArguments = InputArguments;
    }
    
    /**
      * Deep clone
      *
      * @return cloned CallMethodRequest
      */
    public CallMethodRequest clone()
    {
        CallMethodRequest result = (CallMethodRequest) super.clone();
        result.ObjectId = ObjectId;
        result.MethodId = MethodId;
        result.InputArguments = InputArguments==null ? null : InputArguments.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CallMethodRequest other = (CallMethodRequest) obj;
        if (ObjectId==null) {
            if (other.ObjectId != null) return false;
        } else if (!ObjectId.equals(other.ObjectId)) return false;
        if (MethodId==null) {
            if (other.MethodId != null) return false;
        } else if (!MethodId.equals(other.MethodId)) return false;
        if (InputArguments==null) {
            if (other.InputArguments != null) return false;
        } else if (!Arrays.equals(InputArguments, other.InputArguments)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ObjectId == null) ? 0 : ObjectId.hashCode());
        result = prime * result
                + ((MethodId == null) ? 0 : MethodId.hashCode());
        result = prime * result
                + ((InputArguments == null) ? 0 : Arrays.hashCode(InputArguments));
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
		return "CallMethodRequest: "+ObjectUtils.printFieldsDeep(this);
	}

}
