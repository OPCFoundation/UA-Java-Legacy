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

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.CompositeTestType;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.utils.AbstractStructure;


public class TestStackExRequest extends AbstractStructure implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.TestStackExRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.TestStackExRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.TestStackExRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected UnsignedInteger TestId;
    protected Integer Iteration;
    protected CompositeTestType Input;
    
    public TestStackExRequest() {}
    
    public TestStackExRequest(RequestHeader RequestHeader, UnsignedInteger TestId, Integer Iteration, CompositeTestType Input)
    {
        this.RequestHeader = RequestHeader;
        this.TestId = TestId;
        this.Iteration = Iteration;
        this.Input = Input;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public UnsignedInteger getTestId()
    {
        return TestId;
    }
    
    public void setTestId(UnsignedInteger TestId)
    {
        this.TestId = TestId;
    }
    
    public Integer getIteration()
    {
        return Iteration;
    }
    
    public void setIteration(Integer Iteration)
    {
        this.Iteration = Iteration;
    }
    
    public CompositeTestType getInput()
    {
        return Input;
    }
    
    public void setInput(CompositeTestType Input)
    {
        this.Input = Input;
    }
    
    /**
      * Deep clone
      *
      * @return cloned TestStackExRequest
      */
    public TestStackExRequest clone()
    {
        TestStackExRequest result = (TestStackExRequest) super.clone();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.TestId = TestId;
        result.Iteration = Iteration;
        result.Input = Input==null ? null : Input.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TestStackExRequest other = (TestStackExRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (TestId==null) {
            if (other.TestId != null) return false;
        } else if (!TestId.equals(other.TestId)) return false;
        if (Iteration==null) {
            if (other.Iteration != null) return false;
        } else if (!Iteration.equals(other.Iteration)) return false;
        if (Input==null) {
            if (other.Input != null) return false;
        } else if (!Input.equals(other.Input)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((RequestHeader == null) ? 0 : RequestHeader.hashCode());
        result = prime * result
                + ((TestId == null) ? 0 : TestId.hashCode());
        result = prime * result
                + ((Iteration == null) ? 0 : Iteration.hashCode());
        result = prime * result
                + ((Input == null) ? 0 : Input.hashCode());
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
