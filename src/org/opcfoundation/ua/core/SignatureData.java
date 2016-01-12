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



public class SignatureData extends Object implements Structure, Cloneable {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SignatureData);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SignatureData_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SignatureData_Encoding_DefaultXml);
	
    protected String Algorithm;
    protected byte[] Signature;
    
    public SignatureData() {}
    
    public SignatureData(String Algorithm, byte[] Signature)
    {
        this.Algorithm = Algorithm;
        this.Signature = Signature;
    }
    
    public String getAlgorithm()
    {
        return Algorithm;
    }
    
    public void setAlgorithm(String Algorithm)
    {
        this.Algorithm = Algorithm;
    }
    
    public byte[] getSignature()
    {
        return Signature;
    }
    
    public void setSignature(byte[] Signature)
    {
        this.Signature = Signature;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SignatureData
      */
    public SignatureData clone()
    {
        SignatureData result = new SignatureData();
        result.Algorithm = Algorithm;
        result.Signature = Signature;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SignatureData other = (SignatureData) obj;
        if (Algorithm==null) {
            if (other.Algorithm != null) return false;
        } else if (!Algorithm.equals(other.Algorithm)) return false;
        if (Signature==null) {
            if (other.Signature != null) return false;
        } else if (!Signature.equals(other.Signature)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Algorithm == null) ? 0 : Algorithm.hashCode());
        result = prime * result
                + ((Signature == null) ? 0 : Signature.hashCode());
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
		return "SignatureData: "+ObjectUtils.printFieldsDeep(this);
	}

}
