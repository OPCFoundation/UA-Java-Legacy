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
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.utils.AbstractStructure;



public class GenericAttributeValue extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.GenericAttributeValue);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.GenericAttributeValue_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.GenericAttributeValue_Encoding_DefaultXml);
	
    protected UnsignedInteger AttributeId;
    protected Variant Value;
    
    public GenericAttributeValue() {}
    
    public GenericAttributeValue(UnsignedInteger AttributeId, Variant Value)
    {
        this.AttributeId = AttributeId;
        this.Value = Value;
    }
    
    public UnsignedInteger getAttributeId()
    {
        return AttributeId;
    }
    
    public void setAttributeId(UnsignedInteger AttributeId)
    {
        this.AttributeId = AttributeId;
    }
    
    public Variant getValue()
    {
        return Value;
    }
    
    public void setValue(Variant Value)
    {
        this.Value = Value;
    }
    
    /**
      * Deep clone
      *
      * @return cloned GenericAttributeValue
      */
    public GenericAttributeValue clone()
    {
        GenericAttributeValue result = (GenericAttributeValue) super.clone();
        result.AttributeId = AttributeId;
        result.Value = Value;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GenericAttributeValue other = (GenericAttributeValue) obj;
        if (AttributeId==null) {
            if (other.AttributeId != null) return false;
        } else if (!AttributeId.equals(other.AttributeId)) return false;
        if (Value==null) {
            if (other.Value != null) return false;
        } else if (!Value.equals(other.Value)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((AttributeId == null) ? 0 : AttributeId.hashCode());
        result = prime * result
                + ((Value == null) ? 0 : Value.hashCode());
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
		return "GenericAttributeValue: "+ObjectUtils.printFieldsDeep(this);
	}

}
