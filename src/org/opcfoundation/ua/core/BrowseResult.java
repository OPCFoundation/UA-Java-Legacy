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
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.core.ReferenceDescription;



public class BrowseResult extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.BrowseResult);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.BrowseResult_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.BrowseResult_Encoding_DefaultXml);
	
    protected StatusCode StatusCode;
    protected byte[] ContinuationPoint;
    protected ReferenceDescription[] References;
    
    public BrowseResult() {}
    
    public BrowseResult(StatusCode StatusCode, byte[] ContinuationPoint, ReferenceDescription[] References)
    {
        this.StatusCode = StatusCode;
        this.ContinuationPoint = ContinuationPoint;
        this.References = References;
    }
    
    public StatusCode getStatusCode()
    {
        return StatusCode;
    }
    
    public void setStatusCode(StatusCode StatusCode)
    {
        this.StatusCode = StatusCode;
    }
    
    public byte[] getContinuationPoint()
    {
        return ContinuationPoint;
    }
    
    public void setContinuationPoint(byte[] ContinuationPoint)
    {
        this.ContinuationPoint = ContinuationPoint;
    }
    
    public ReferenceDescription[] getReferences()
    {
        return References;
    }
    
    public void setReferences(ReferenceDescription[] References)
    {
        this.References = References;
    }
    
    /**
      * Deep clone
      *
      * @return cloned BrowseResult
      */
    public BrowseResult clone()
    {
        BrowseResult result = new BrowseResult();
        result.StatusCode = StatusCode;
        result.ContinuationPoint = ContinuationPoint;
        if (References!=null) {
            result.References = new ReferenceDescription[References.length];
            for (int i=0; i<References.length; i++)
                result.References[i] = References[i].clone();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BrowseResult other = (BrowseResult) obj;
        if (StatusCode==null) {
            if (other.StatusCode != null) return false;
        } else if (!StatusCode.equals(other.StatusCode)) return false;
        if (ContinuationPoint==null) {
            if (other.ContinuationPoint != null) return false;
        } else if (!ContinuationPoint.equals(other.ContinuationPoint)) return false;
        if (References==null) {
            if (other.References != null) return false;
        } else if (!Arrays.equals(References, other.References)) return false;
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
                + ((ContinuationPoint == null) ? 0 : ContinuationPoint.hashCode());
        result = prime * result
                + ((References == null) ? 0 : Arrays.hashCode(References));
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
		return "BrowseResult: "+ObjectUtils.printFieldsDeep(this);
	}

}
