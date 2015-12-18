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

import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.NotificationMessage;
import org.opcfoundation.ua.core.ResponseHeader;


public class PublishResponse extends Object implements ServiceResponse {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.PublishResponse);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.PublishResponse_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.PublishResponse_Encoding_DefaultXml);
	
    protected ResponseHeader ResponseHeader;
    protected UnsignedInteger SubscriptionId;
    protected UnsignedInteger[] AvailableSequenceNumbers;
    protected Boolean MoreNotifications;
    protected NotificationMessage NotificationMessage;
    protected StatusCode[] Results;
    protected DiagnosticInfo[] DiagnosticInfos;
    
    public PublishResponse() {}
    
    public PublishResponse(ResponseHeader ResponseHeader, UnsignedInteger SubscriptionId, UnsignedInteger[] AvailableSequenceNumbers, Boolean MoreNotifications, NotificationMessage NotificationMessage, StatusCode[] Results, DiagnosticInfo[] DiagnosticInfos)
    {
        this.ResponseHeader = ResponseHeader;
        this.SubscriptionId = SubscriptionId;
        this.AvailableSequenceNumbers = AvailableSequenceNumbers;
        this.MoreNotifications = MoreNotifications;
        this.NotificationMessage = NotificationMessage;
        this.Results = Results;
        this.DiagnosticInfos = DiagnosticInfos;
    }
    
    public ResponseHeader getResponseHeader()
    {
        return ResponseHeader;
    }
    
    public void setResponseHeader(ResponseHeader ResponseHeader)
    {
        this.ResponseHeader = ResponseHeader;
    }
    
    public UnsignedInteger getSubscriptionId()
    {
        return SubscriptionId;
    }
    
    public void setSubscriptionId(UnsignedInteger SubscriptionId)
    {
        this.SubscriptionId = SubscriptionId;
    }
    
    public UnsignedInteger[] getAvailableSequenceNumbers()
    {
        return AvailableSequenceNumbers;
    }
    
    public void setAvailableSequenceNumbers(UnsignedInteger[] AvailableSequenceNumbers)
    {
        this.AvailableSequenceNumbers = AvailableSequenceNumbers;
    }
    
    public Boolean getMoreNotifications()
    {
        return MoreNotifications;
    }
    
    public void setMoreNotifications(Boolean MoreNotifications)
    {
        this.MoreNotifications = MoreNotifications;
    }
    
    public NotificationMessage getNotificationMessage()
    {
        return NotificationMessage;
    }
    
    public void setNotificationMessage(NotificationMessage NotificationMessage)
    {
        this.NotificationMessage = NotificationMessage;
    }
    
    public StatusCode[] getResults()
    {
        return Results;
    }
    
    public void setResults(StatusCode[] Results)
    {
        this.Results = Results;
    }
    
    public DiagnosticInfo[] getDiagnosticInfos()
    {
        return DiagnosticInfos;
    }
    
    public void setDiagnosticInfos(DiagnosticInfo[] DiagnosticInfos)
    {
        this.DiagnosticInfos = DiagnosticInfos;
    }
    
    /**
      * Deep clone
      *
      * @return cloned PublishResponse
      */
    public PublishResponse clone()
    {
        PublishResponse result = new PublishResponse();
        result.ResponseHeader = ResponseHeader==null ? null : ResponseHeader.clone();
        result.SubscriptionId = SubscriptionId;
        result.AvailableSequenceNumbers = AvailableSequenceNumbers==null ? null : AvailableSequenceNumbers.clone();
        result.MoreNotifications = MoreNotifications;
        result.NotificationMessage = NotificationMessage==null ? null : NotificationMessage.clone();
        result.Results = Results==null ? null : Results.clone();
        result.DiagnosticInfos = DiagnosticInfos==null ? null : DiagnosticInfos.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PublishResponse other = (PublishResponse) obj;
        if (ResponseHeader==null) {
            if (other.ResponseHeader != null) return false;
        } else if (!ResponseHeader.equals(other.ResponseHeader)) return false;
        if (SubscriptionId==null) {
            if (other.SubscriptionId != null) return false;
        } else if (!SubscriptionId.equals(other.SubscriptionId)) return false;
        if (AvailableSequenceNumbers==null) {
            if (other.AvailableSequenceNumbers != null) return false;
        } else if (!Arrays.equals(AvailableSequenceNumbers, other.AvailableSequenceNumbers)) return false;
        if (MoreNotifications==null) {
            if (other.MoreNotifications != null) return false;
        } else if (!MoreNotifications.equals(other.MoreNotifications)) return false;
        if (NotificationMessage==null) {
            if (other.NotificationMessage != null) return false;
        } else if (!NotificationMessage.equals(other.NotificationMessage)) return false;
        if (Results==null) {
            if (other.Results != null) return false;
        } else if (!Arrays.equals(Results, other.Results)) return false;
        if (DiagnosticInfos==null) {
            if (other.DiagnosticInfos != null) return false;
        } else if (!Arrays.equals(DiagnosticInfos, other.DiagnosticInfos)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((ResponseHeader == null) ? 0 : ResponseHeader.hashCode());
        result = prime * result
                + ((SubscriptionId == null) ? 0 : SubscriptionId.hashCode());
        result = prime * result
                + ((AvailableSequenceNumbers == null) ? 0 : Arrays.hashCode(AvailableSequenceNumbers));
        result = prime * result
                + ((MoreNotifications == null) ? 0 : MoreNotifications.hashCode());
        result = prime * result
                + ((NotificationMessage == null) ? 0 : NotificationMessage.hashCode());
        result = prime * result
                + ((Results == null) ? 0 : Arrays.hashCode(Results));
        result = prime * result
                + ((DiagnosticInfos == null) ? 0 : Arrays.hashCode(DiagnosticInfos));
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
