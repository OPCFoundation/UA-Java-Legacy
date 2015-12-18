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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Variant;



public class CallMethodRequest extends Object implements Structure, Cloneable {
	
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
        CallMethodRequest result = new CallMethodRequest();
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
