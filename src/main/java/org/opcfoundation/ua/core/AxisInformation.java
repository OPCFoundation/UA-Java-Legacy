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
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.core.AxisScaleEnumeration;
import org.opcfoundation.ua.core.EUInformation;
import org.opcfoundation.ua.core.Range;
import org.opcfoundation.ua.utils.AbstractStructure;



public class AxisInformation extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.AxisInformation);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.AxisInformation_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.AxisInformation_Encoding_DefaultXml);
	
    protected EUInformation EngineeringUnits;
    protected Range EURange;
    protected LocalizedText Title;
    protected AxisScaleEnumeration AxisScaleType;
    protected Double[] AxisSteps;
    
    public AxisInformation() {}
    
    public AxisInformation(EUInformation EngineeringUnits, Range EURange, LocalizedText Title, AxisScaleEnumeration AxisScaleType, Double[] AxisSteps)
    {
        this.EngineeringUnits = EngineeringUnits;
        this.EURange = EURange;
        this.Title = Title;
        this.AxisScaleType = AxisScaleType;
        this.AxisSteps = AxisSteps;
    }
    
    public EUInformation getEngineeringUnits()
    {
        return EngineeringUnits;
    }
    
    public void setEngineeringUnits(EUInformation EngineeringUnits)
    {
        this.EngineeringUnits = EngineeringUnits;
    }
    
    public Range getEURange()
    {
        return EURange;
    }
    
    public void setEURange(Range EURange)
    {
        this.EURange = EURange;
    }
    
    public LocalizedText getTitle()
    {
        return Title;
    }
    
    public void setTitle(LocalizedText Title)
    {
        this.Title = Title;
    }
    
    public AxisScaleEnumeration getAxisScaleType()
    {
        return AxisScaleType;
    }
    
    public void setAxisScaleType(AxisScaleEnumeration AxisScaleType)
    {
        this.AxisScaleType = AxisScaleType;
    }
    
    public Double[] getAxisSteps()
    {
        return AxisSteps;
    }
    
    public void setAxisSteps(Double[] AxisSteps)
    {
        this.AxisSteps = AxisSteps;
    }
    
    /**
      * Deep clone
      *
      * @return cloned AxisInformation
      */
    public AxisInformation clone()
    {
        AxisInformation result = (AxisInformation) super.clone();
        result.EngineeringUnits = EngineeringUnits==null ? null : EngineeringUnits.clone();
        result.EURange = EURange==null ? null : EURange.clone();
        result.Title = Title;
        result.AxisScaleType = AxisScaleType;
        result.AxisSteps = AxisSteps==null ? null : AxisSteps.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AxisInformation other = (AxisInformation) obj;
        if (EngineeringUnits==null) {
            if (other.EngineeringUnits != null) return false;
        } else if (!EngineeringUnits.equals(other.EngineeringUnits)) return false;
        if (EURange==null) {
            if (other.EURange != null) return false;
        } else if (!EURange.equals(other.EURange)) return false;
        if (Title==null) {
            if (other.Title != null) return false;
        } else if (!Title.equals(other.Title)) return false;
        if (AxisScaleType==null) {
            if (other.AxisScaleType != null) return false;
        } else if (!AxisScaleType.equals(other.AxisScaleType)) return false;
        if (AxisSteps==null) {
            if (other.AxisSteps != null) return false;
        } else if (!Arrays.equals(AxisSteps, other.AxisSteps)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((EngineeringUnits == null) ? 0 : EngineeringUnits.hashCode());
        result = prime * result
                + ((EURange == null) ? 0 : EURange.hashCode());
        result = prime * result
                + ((Title == null) ? 0 : Title.hashCode());
        result = prime * result
                + ((AxisScaleType == null) ? 0 : AxisScaleType.hashCode());
        result = prime * result
                + ((AxisSteps == null) ? 0 : Arrays.hashCode(AxisSteps));
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
		return "AxisInformation: "+ObjectUtils.printFieldsDeep(this);
	}

}
