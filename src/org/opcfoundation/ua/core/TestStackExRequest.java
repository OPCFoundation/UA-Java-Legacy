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

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.CompositeTestType;
import org.opcfoundation.ua.core.RequestHeader;


public class TestStackExRequest extends Object implements ServiceRequest {

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
        TestStackExRequest result = new TestStackExRequest();
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
