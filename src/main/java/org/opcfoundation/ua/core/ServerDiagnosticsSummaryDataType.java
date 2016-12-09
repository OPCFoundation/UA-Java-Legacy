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
import org.opcfoundation.ua.utils.AbstractStructure;



public class ServerDiagnosticsSummaryDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ServerDiagnosticsSummaryDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ServerDiagnosticsSummaryDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ServerDiagnosticsSummaryDataType_Encoding_DefaultXml);
	
    protected UnsignedInteger ServerViewCount;
    protected UnsignedInteger CurrentSessionCount;
    protected UnsignedInteger CumulatedSessionCount;
    protected UnsignedInteger SecurityRejectedSessionCount;
    protected UnsignedInteger RejectedSessionCount;
    protected UnsignedInteger SessionTimeoutCount;
    protected UnsignedInteger SessionAbortCount;
    protected UnsignedInteger CurrentSubscriptionCount;
    protected UnsignedInteger CumulatedSubscriptionCount;
    protected UnsignedInteger PublishingIntervalCount;
    protected UnsignedInteger SecurityRejectedRequestsCount;
    protected UnsignedInteger RejectedRequestsCount;
    
    public ServerDiagnosticsSummaryDataType() {}
    
    public ServerDiagnosticsSummaryDataType(UnsignedInteger ServerViewCount, UnsignedInteger CurrentSessionCount, UnsignedInteger CumulatedSessionCount, UnsignedInteger SecurityRejectedSessionCount, UnsignedInteger RejectedSessionCount, UnsignedInteger SessionTimeoutCount, UnsignedInteger SessionAbortCount, UnsignedInteger CurrentSubscriptionCount, UnsignedInteger CumulatedSubscriptionCount, UnsignedInteger PublishingIntervalCount, UnsignedInteger SecurityRejectedRequestsCount, UnsignedInteger RejectedRequestsCount)
    {
        this.ServerViewCount = ServerViewCount;
        this.CurrentSessionCount = CurrentSessionCount;
        this.CumulatedSessionCount = CumulatedSessionCount;
        this.SecurityRejectedSessionCount = SecurityRejectedSessionCount;
        this.RejectedSessionCount = RejectedSessionCount;
        this.SessionTimeoutCount = SessionTimeoutCount;
        this.SessionAbortCount = SessionAbortCount;
        this.CurrentSubscriptionCount = CurrentSubscriptionCount;
        this.CumulatedSubscriptionCount = CumulatedSubscriptionCount;
        this.PublishingIntervalCount = PublishingIntervalCount;
        this.SecurityRejectedRequestsCount = SecurityRejectedRequestsCount;
        this.RejectedRequestsCount = RejectedRequestsCount;
    }
    
    public UnsignedInteger getServerViewCount()
    {
        return ServerViewCount;
    }
    
    public void setServerViewCount(UnsignedInteger ServerViewCount)
    {
        this.ServerViewCount = ServerViewCount;
    }
    
    public UnsignedInteger getCurrentSessionCount()
    {
        return CurrentSessionCount;
    }
    
    public void setCurrentSessionCount(UnsignedInteger CurrentSessionCount)
    {
        this.CurrentSessionCount = CurrentSessionCount;
    }
    
    public UnsignedInteger getCumulatedSessionCount()
    {
        return CumulatedSessionCount;
    }
    
    public void setCumulatedSessionCount(UnsignedInteger CumulatedSessionCount)
    {
        this.CumulatedSessionCount = CumulatedSessionCount;
    }
    
    public UnsignedInteger getSecurityRejectedSessionCount()
    {
        return SecurityRejectedSessionCount;
    }
    
    public void setSecurityRejectedSessionCount(UnsignedInteger SecurityRejectedSessionCount)
    {
        this.SecurityRejectedSessionCount = SecurityRejectedSessionCount;
    }
    
    public UnsignedInteger getRejectedSessionCount()
    {
        return RejectedSessionCount;
    }
    
    public void setRejectedSessionCount(UnsignedInteger RejectedSessionCount)
    {
        this.RejectedSessionCount = RejectedSessionCount;
    }
    
    public UnsignedInteger getSessionTimeoutCount()
    {
        return SessionTimeoutCount;
    }
    
    public void setSessionTimeoutCount(UnsignedInteger SessionTimeoutCount)
    {
        this.SessionTimeoutCount = SessionTimeoutCount;
    }
    
    public UnsignedInteger getSessionAbortCount()
    {
        return SessionAbortCount;
    }
    
    public void setSessionAbortCount(UnsignedInteger SessionAbortCount)
    {
        this.SessionAbortCount = SessionAbortCount;
    }
    
    public UnsignedInteger getCurrentSubscriptionCount()
    {
        return CurrentSubscriptionCount;
    }
    
    public void setCurrentSubscriptionCount(UnsignedInteger CurrentSubscriptionCount)
    {
        this.CurrentSubscriptionCount = CurrentSubscriptionCount;
    }
    
    public UnsignedInteger getCumulatedSubscriptionCount()
    {
        return CumulatedSubscriptionCount;
    }
    
    public void setCumulatedSubscriptionCount(UnsignedInteger CumulatedSubscriptionCount)
    {
        this.CumulatedSubscriptionCount = CumulatedSubscriptionCount;
    }
    
    public UnsignedInteger getPublishingIntervalCount()
    {
        return PublishingIntervalCount;
    }
    
    public void setPublishingIntervalCount(UnsignedInteger PublishingIntervalCount)
    {
        this.PublishingIntervalCount = PublishingIntervalCount;
    }
    
    public UnsignedInteger getSecurityRejectedRequestsCount()
    {
        return SecurityRejectedRequestsCount;
    }
    
    public void setSecurityRejectedRequestsCount(UnsignedInteger SecurityRejectedRequestsCount)
    {
        this.SecurityRejectedRequestsCount = SecurityRejectedRequestsCount;
    }
    
    public UnsignedInteger getRejectedRequestsCount()
    {
        return RejectedRequestsCount;
    }
    
    public void setRejectedRequestsCount(UnsignedInteger RejectedRequestsCount)
    {
        this.RejectedRequestsCount = RejectedRequestsCount;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ServerDiagnosticsSummaryDataType
      */
    public ServerDiagnosticsSummaryDataType clone()
    {
        ServerDiagnosticsSummaryDataType result = (ServerDiagnosticsSummaryDataType) super.clone();
        result.ServerViewCount = ServerViewCount;
        result.CurrentSessionCount = CurrentSessionCount;
        result.CumulatedSessionCount = CumulatedSessionCount;
        result.SecurityRejectedSessionCount = SecurityRejectedSessionCount;
        result.RejectedSessionCount = RejectedSessionCount;
        result.SessionTimeoutCount = SessionTimeoutCount;
        result.SessionAbortCount = SessionAbortCount;
        result.CurrentSubscriptionCount = CurrentSubscriptionCount;
        result.CumulatedSubscriptionCount = CumulatedSubscriptionCount;
        result.PublishingIntervalCount = PublishingIntervalCount;
        result.SecurityRejectedRequestsCount = SecurityRejectedRequestsCount;
        result.RejectedRequestsCount = RejectedRequestsCount;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ServerDiagnosticsSummaryDataType other = (ServerDiagnosticsSummaryDataType) obj;
        if (ServerViewCount==null) {
            if (other.ServerViewCount != null) return false;
        } else if (!ServerViewCount.equals(other.ServerViewCount)) return false;
        if (CurrentSessionCount==null) {
            if (other.CurrentSessionCount != null) return false;
        } else if (!CurrentSessionCount.equals(other.CurrentSessionCount)) return false;
        if (CumulatedSessionCount==null) {
            if (other.CumulatedSessionCount != null) return false;
        } else if (!CumulatedSessionCount.equals(other.CumulatedSessionCount)) return false;
        if (SecurityRejectedSessionCount==null) {
            if (other.SecurityRejectedSessionCount != null) return false;
        } else if (!SecurityRejectedSessionCount.equals(other.SecurityRejectedSessionCount)) return false;
        if (RejectedSessionCount==null) {
            if (other.RejectedSessionCount != null) return false;
        } else if (!RejectedSessionCount.equals(other.RejectedSessionCount)) return false;
        if (SessionTimeoutCount==null) {
            if (other.SessionTimeoutCount != null) return false;
        } else if (!SessionTimeoutCount.equals(other.SessionTimeoutCount)) return false;
        if (SessionAbortCount==null) {
            if (other.SessionAbortCount != null) return false;
        } else if (!SessionAbortCount.equals(other.SessionAbortCount)) return false;
        if (CurrentSubscriptionCount==null) {
            if (other.CurrentSubscriptionCount != null) return false;
        } else if (!CurrentSubscriptionCount.equals(other.CurrentSubscriptionCount)) return false;
        if (CumulatedSubscriptionCount==null) {
            if (other.CumulatedSubscriptionCount != null) return false;
        } else if (!CumulatedSubscriptionCount.equals(other.CumulatedSubscriptionCount)) return false;
        if (PublishingIntervalCount==null) {
            if (other.PublishingIntervalCount != null) return false;
        } else if (!PublishingIntervalCount.equals(other.PublishingIntervalCount)) return false;
        if (SecurityRejectedRequestsCount==null) {
            if (other.SecurityRejectedRequestsCount != null) return false;
        } else if (!SecurityRejectedRequestsCount.equals(other.SecurityRejectedRequestsCount)) return false;
        if (RejectedRequestsCount==null) {
            if (other.RejectedRequestsCount != null) return false;
        } else if (!RejectedRequestsCount.equals(other.RejectedRequestsCount)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ServerViewCount == null) ? 0 : ServerViewCount.hashCode());
        result = prime * result
                + ((CurrentSessionCount == null) ? 0 : CurrentSessionCount.hashCode());
        result = prime * result
                + ((CumulatedSessionCount == null) ? 0 : CumulatedSessionCount.hashCode());
        result = prime * result
                + ((SecurityRejectedSessionCount == null) ? 0 : SecurityRejectedSessionCount.hashCode());
        result = prime * result
                + ((RejectedSessionCount == null) ? 0 : RejectedSessionCount.hashCode());
        result = prime * result
                + ((SessionTimeoutCount == null) ? 0 : SessionTimeoutCount.hashCode());
        result = prime * result
                + ((SessionAbortCount == null) ? 0 : SessionAbortCount.hashCode());
        result = prime * result
                + ((CurrentSubscriptionCount == null) ? 0 : CurrentSubscriptionCount.hashCode());
        result = prime * result
                + ((CumulatedSubscriptionCount == null) ? 0 : CumulatedSubscriptionCount.hashCode());
        result = prime * result
                + ((PublishingIntervalCount == null) ? 0 : PublishingIntervalCount.hashCode());
        result = prime * result
                + ((SecurityRejectedRequestsCount == null) ? 0 : SecurityRejectedRequestsCount.hashCode());
        result = prime * result
                + ((RejectedRequestsCount == null) ? 0 : RejectedRequestsCount.hashCode());
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
		return "ServerDiagnosticsSummaryDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
