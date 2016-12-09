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
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.utils.AbstractStructure;



public class AggregateConfiguration extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.AggregateConfiguration);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.AggregateConfiguration_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.AggregateConfiguration_Encoding_DefaultXml);
	
    protected Boolean UseServerCapabilitiesDefaults;
    protected Boolean TreatUncertainAsBad;
    protected UnsignedByte PercentDataBad;
    protected UnsignedByte PercentDataGood;
    protected Boolean UseSlopedExtrapolation;
    
    public AggregateConfiguration() {}
    
    public AggregateConfiguration(Boolean UseServerCapabilitiesDefaults, Boolean TreatUncertainAsBad, UnsignedByte PercentDataBad, UnsignedByte PercentDataGood, Boolean UseSlopedExtrapolation)
    {
        this.UseServerCapabilitiesDefaults = UseServerCapabilitiesDefaults;
        this.TreatUncertainAsBad = TreatUncertainAsBad;
        this.PercentDataBad = PercentDataBad;
        this.PercentDataGood = PercentDataGood;
        this.UseSlopedExtrapolation = UseSlopedExtrapolation;
    }
    
    public Boolean getUseServerCapabilitiesDefaults()
    {
        return UseServerCapabilitiesDefaults;
    }
    
    public void setUseServerCapabilitiesDefaults(Boolean UseServerCapabilitiesDefaults)
    {
        this.UseServerCapabilitiesDefaults = UseServerCapabilitiesDefaults;
    }
    
    public Boolean getTreatUncertainAsBad()
    {
        return TreatUncertainAsBad;
    }
    
    public void setTreatUncertainAsBad(Boolean TreatUncertainAsBad)
    {
        this.TreatUncertainAsBad = TreatUncertainAsBad;
    }
    
    public UnsignedByte getPercentDataBad()
    {
        return PercentDataBad;
    }
    
    public void setPercentDataBad(UnsignedByte PercentDataBad)
    {
        this.PercentDataBad = PercentDataBad;
    }
    
    public UnsignedByte getPercentDataGood()
    {
        return PercentDataGood;
    }
    
    public void setPercentDataGood(UnsignedByte PercentDataGood)
    {
        this.PercentDataGood = PercentDataGood;
    }
    
    public Boolean getUseSlopedExtrapolation()
    {
        return UseSlopedExtrapolation;
    }
    
    public void setUseSlopedExtrapolation(Boolean UseSlopedExtrapolation)
    {
        this.UseSlopedExtrapolation = UseSlopedExtrapolation;
    }
    
    /**
      * Deep clone
      *
      * @return cloned AggregateConfiguration
      */
    public AggregateConfiguration clone()
    {
        AggregateConfiguration result = (AggregateConfiguration) super.clone();
        result.UseServerCapabilitiesDefaults = UseServerCapabilitiesDefaults;
        result.TreatUncertainAsBad = TreatUncertainAsBad;
        result.PercentDataBad = PercentDataBad;
        result.PercentDataGood = PercentDataGood;
        result.UseSlopedExtrapolation = UseSlopedExtrapolation;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AggregateConfiguration other = (AggregateConfiguration) obj;
        if (UseServerCapabilitiesDefaults==null) {
            if (other.UseServerCapabilitiesDefaults != null) return false;
        } else if (!UseServerCapabilitiesDefaults.equals(other.UseServerCapabilitiesDefaults)) return false;
        if (TreatUncertainAsBad==null) {
            if (other.TreatUncertainAsBad != null) return false;
        } else if (!TreatUncertainAsBad.equals(other.TreatUncertainAsBad)) return false;
        if (PercentDataBad==null) {
            if (other.PercentDataBad != null) return false;
        } else if (!PercentDataBad.equals(other.PercentDataBad)) return false;
        if (PercentDataGood==null) {
            if (other.PercentDataGood != null) return false;
        } else if (!PercentDataGood.equals(other.PercentDataGood)) return false;
        if (UseSlopedExtrapolation==null) {
            if (other.UseSlopedExtrapolation != null) return false;
        } else if (!UseSlopedExtrapolation.equals(other.UseSlopedExtrapolation)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((UseServerCapabilitiesDefaults == null) ? 0 : UseServerCapabilitiesDefaults.hashCode());
        result = prime * result
                + ((TreatUncertainAsBad == null) ? 0 : TreatUncertainAsBad.hashCode());
        result = prime * result
                + ((PercentDataBad == null) ? 0 : PercentDataBad.hashCode());
        result = prime * result
                + ((PercentDataGood == null) ? 0 : PercentDataGood.hashCode());
        result = prime * result
                + ((UseSlopedExtrapolation == null) ? 0 : UseSlopedExtrapolation.hashCode());
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
		return "AggregateConfiguration: "+ObjectUtils.printFieldsDeep(this);
	}

}
