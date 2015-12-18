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
import org.opcfoundation.ua.builtintypes.UnsignedByte;



public class AggregateConfiguration extends Object implements Structure, Cloneable {
	
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
        AggregateConfiguration result = new AggregateConfiguration();
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
