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
import org.opcfoundation.ua.core.IdentityCriteriaType;
import org.opcfoundation.ua.utils.AbstractStructure;



public class IdentityMappingRuleType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.IdentityMappingRuleType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.IdentityMappingRuleType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.IdentityMappingRuleType_Encoding_DefaultXml);
	
    protected IdentityCriteriaType CriteriaType;
    protected String Criteria;
    
    public IdentityMappingRuleType() {}
    
    public IdentityMappingRuleType(IdentityCriteriaType CriteriaType, String Criteria)
    {
        this.CriteriaType = CriteriaType;
        this.Criteria = Criteria;
    }
    
    public IdentityCriteriaType getCriteriaType()
    {
        return CriteriaType;
    }
    
    public void setCriteriaType(IdentityCriteriaType CriteriaType)
    {
        this.CriteriaType = CriteriaType;
    }
    
    public String getCriteria()
    {
        return Criteria;
    }
    
    public void setCriteria(String Criteria)
    {
        this.Criteria = Criteria;
    }
    
    /**
      * Deep clone
      *
      * @return cloned IdentityMappingRuleType
      */
    public IdentityMappingRuleType clone()
    {
        IdentityMappingRuleType result = (IdentityMappingRuleType) super.clone();
        result.CriteriaType = CriteriaType;
        result.Criteria = Criteria;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        IdentityMappingRuleType other = (IdentityMappingRuleType) obj;
        if (CriteriaType==null) {
            if (other.CriteriaType != null) return false;
        } else if (!CriteriaType.equals(other.CriteriaType)) return false;
        if (Criteria==null) {
            if (other.Criteria != null) return false;
        } else if (!Criteria.equals(other.Criteria)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((CriteriaType == null) ? 0 : CriteriaType.hashCode());
        result = prime * result
                + ((Criteria == null) ? 0 : Criteria.hashCode());
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
		return "IdentityMappingRuleType: "+ObjectUtils.printFieldsDeep(this);
	}

}
