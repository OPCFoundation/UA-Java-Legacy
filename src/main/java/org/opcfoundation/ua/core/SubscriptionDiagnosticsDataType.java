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
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.AbstractStructure;



public class SubscriptionDiagnosticsDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SubscriptionDiagnosticsDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SubscriptionDiagnosticsDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SubscriptionDiagnosticsDataType_Encoding_DefaultXml);
	
    protected NodeId SessionId;
    protected UnsignedInteger SubscriptionId;
    protected UnsignedByte Priority;
    protected Double PublishingInterval;
    protected UnsignedInteger MaxKeepAliveCount;
    protected UnsignedInteger MaxLifetimeCount;
    protected UnsignedInteger MaxNotificationsPerPublish;
    protected Boolean PublishingEnabled;
    protected UnsignedInteger ModifyCount;
    protected UnsignedInteger EnableCount;
    protected UnsignedInteger DisableCount;
    protected UnsignedInteger RepublishRequestCount;
    protected UnsignedInteger RepublishMessageRequestCount;
    protected UnsignedInteger RepublishMessageCount;
    protected UnsignedInteger TransferRequestCount;
    protected UnsignedInteger TransferredToAltClientCount;
    protected UnsignedInteger TransferredToSameClientCount;
    protected UnsignedInteger PublishRequestCount;
    protected UnsignedInteger DataChangeNotificationsCount;
    protected UnsignedInteger EventNotificationsCount;
    protected UnsignedInteger NotificationsCount;
    protected UnsignedInteger LatePublishRequestCount;
    protected UnsignedInteger CurrentKeepAliveCount;
    protected UnsignedInteger CurrentLifetimeCount;
    protected UnsignedInteger UnacknowledgedMessageCount;
    protected UnsignedInteger DiscardedMessageCount;
    protected UnsignedInteger MonitoredItemCount;
    protected UnsignedInteger DisabledMonitoredItemCount;
    protected UnsignedInteger MonitoringQueueOverflowCount;
    protected UnsignedInteger NextSequenceNumber;
    protected UnsignedInteger EventQueueOverFlowCount;
    
    public SubscriptionDiagnosticsDataType() {}
    
    public SubscriptionDiagnosticsDataType(NodeId SessionId, UnsignedInteger SubscriptionId, UnsignedByte Priority, Double PublishingInterval, UnsignedInteger MaxKeepAliveCount, UnsignedInteger MaxLifetimeCount, UnsignedInteger MaxNotificationsPerPublish, Boolean PublishingEnabled, UnsignedInteger ModifyCount, UnsignedInteger EnableCount, UnsignedInteger DisableCount, UnsignedInteger RepublishRequestCount, UnsignedInteger RepublishMessageRequestCount, UnsignedInteger RepublishMessageCount, UnsignedInteger TransferRequestCount, UnsignedInteger TransferredToAltClientCount, UnsignedInteger TransferredToSameClientCount, UnsignedInteger PublishRequestCount, UnsignedInteger DataChangeNotificationsCount, UnsignedInteger EventNotificationsCount, UnsignedInteger NotificationsCount, UnsignedInteger LatePublishRequestCount, UnsignedInteger CurrentKeepAliveCount, UnsignedInteger CurrentLifetimeCount, UnsignedInteger UnacknowledgedMessageCount, UnsignedInteger DiscardedMessageCount, UnsignedInteger MonitoredItemCount, UnsignedInteger DisabledMonitoredItemCount, UnsignedInteger MonitoringQueueOverflowCount, UnsignedInteger NextSequenceNumber, UnsignedInteger EventQueueOverFlowCount)
    {
        this.SessionId = SessionId;
        this.SubscriptionId = SubscriptionId;
        this.Priority = Priority;
        this.PublishingInterval = PublishingInterval;
        this.MaxKeepAliveCount = MaxKeepAliveCount;
        this.MaxLifetimeCount = MaxLifetimeCount;
        this.MaxNotificationsPerPublish = MaxNotificationsPerPublish;
        this.PublishingEnabled = PublishingEnabled;
        this.ModifyCount = ModifyCount;
        this.EnableCount = EnableCount;
        this.DisableCount = DisableCount;
        this.RepublishRequestCount = RepublishRequestCount;
        this.RepublishMessageRequestCount = RepublishMessageRequestCount;
        this.RepublishMessageCount = RepublishMessageCount;
        this.TransferRequestCount = TransferRequestCount;
        this.TransferredToAltClientCount = TransferredToAltClientCount;
        this.TransferredToSameClientCount = TransferredToSameClientCount;
        this.PublishRequestCount = PublishRequestCount;
        this.DataChangeNotificationsCount = DataChangeNotificationsCount;
        this.EventNotificationsCount = EventNotificationsCount;
        this.NotificationsCount = NotificationsCount;
        this.LatePublishRequestCount = LatePublishRequestCount;
        this.CurrentKeepAliveCount = CurrentKeepAliveCount;
        this.CurrentLifetimeCount = CurrentLifetimeCount;
        this.UnacknowledgedMessageCount = UnacknowledgedMessageCount;
        this.DiscardedMessageCount = DiscardedMessageCount;
        this.MonitoredItemCount = MonitoredItemCount;
        this.DisabledMonitoredItemCount = DisabledMonitoredItemCount;
        this.MonitoringQueueOverflowCount = MonitoringQueueOverflowCount;
        this.NextSequenceNumber = NextSequenceNumber;
        this.EventQueueOverFlowCount = EventQueueOverFlowCount;
    }
    
    public NodeId getSessionId()
    {
        return SessionId;
    }
    
    public void setSessionId(NodeId SessionId)
    {
        this.SessionId = SessionId;
    }
    
    public UnsignedInteger getSubscriptionId()
    {
        return SubscriptionId;
    }
    
    public void setSubscriptionId(UnsignedInteger SubscriptionId)
    {
        this.SubscriptionId = SubscriptionId;
    }
    
    public UnsignedByte getPriority()
    {
        return Priority;
    }
    
    public void setPriority(UnsignedByte Priority)
    {
        this.Priority = Priority;
    }
    
    public Double getPublishingInterval()
    {
        return PublishingInterval;
    }
    
    public void setPublishingInterval(Double PublishingInterval)
    {
        this.PublishingInterval = PublishingInterval;
    }
    
    public UnsignedInteger getMaxKeepAliveCount()
    {
        return MaxKeepAliveCount;
    }
    
    public void setMaxKeepAliveCount(UnsignedInteger MaxKeepAliveCount)
    {
        this.MaxKeepAliveCount = MaxKeepAliveCount;
    }
    
    public UnsignedInteger getMaxLifetimeCount()
    {
        return MaxLifetimeCount;
    }
    
    public void setMaxLifetimeCount(UnsignedInteger MaxLifetimeCount)
    {
        this.MaxLifetimeCount = MaxLifetimeCount;
    }
    
    public UnsignedInteger getMaxNotificationsPerPublish()
    {
        return MaxNotificationsPerPublish;
    }
    
    public void setMaxNotificationsPerPublish(UnsignedInteger MaxNotificationsPerPublish)
    {
        this.MaxNotificationsPerPublish = MaxNotificationsPerPublish;
    }
    
    public Boolean getPublishingEnabled()
    {
        return PublishingEnabled;
    }
    
    public void setPublishingEnabled(Boolean PublishingEnabled)
    {
        this.PublishingEnabled = PublishingEnabled;
    }
    
    public UnsignedInteger getModifyCount()
    {
        return ModifyCount;
    }
    
    public void setModifyCount(UnsignedInteger ModifyCount)
    {
        this.ModifyCount = ModifyCount;
    }
    
    public UnsignedInteger getEnableCount()
    {
        return EnableCount;
    }
    
    public void setEnableCount(UnsignedInteger EnableCount)
    {
        this.EnableCount = EnableCount;
    }
    
    public UnsignedInteger getDisableCount()
    {
        return DisableCount;
    }
    
    public void setDisableCount(UnsignedInteger DisableCount)
    {
        this.DisableCount = DisableCount;
    }
    
    public UnsignedInteger getRepublishRequestCount()
    {
        return RepublishRequestCount;
    }
    
    public void setRepublishRequestCount(UnsignedInteger RepublishRequestCount)
    {
        this.RepublishRequestCount = RepublishRequestCount;
    }
    
    public UnsignedInteger getRepublishMessageRequestCount()
    {
        return RepublishMessageRequestCount;
    }
    
    public void setRepublishMessageRequestCount(UnsignedInteger RepublishMessageRequestCount)
    {
        this.RepublishMessageRequestCount = RepublishMessageRequestCount;
    }
    
    public UnsignedInteger getRepublishMessageCount()
    {
        return RepublishMessageCount;
    }
    
    public void setRepublishMessageCount(UnsignedInteger RepublishMessageCount)
    {
        this.RepublishMessageCount = RepublishMessageCount;
    }
    
    public UnsignedInteger getTransferRequestCount()
    {
        return TransferRequestCount;
    }
    
    public void setTransferRequestCount(UnsignedInteger TransferRequestCount)
    {
        this.TransferRequestCount = TransferRequestCount;
    }
    
    public UnsignedInteger getTransferredToAltClientCount()
    {
        return TransferredToAltClientCount;
    }
    
    public void setTransferredToAltClientCount(UnsignedInteger TransferredToAltClientCount)
    {
        this.TransferredToAltClientCount = TransferredToAltClientCount;
    }
    
    public UnsignedInteger getTransferredToSameClientCount()
    {
        return TransferredToSameClientCount;
    }
    
    public void setTransferredToSameClientCount(UnsignedInteger TransferredToSameClientCount)
    {
        this.TransferredToSameClientCount = TransferredToSameClientCount;
    }
    
    public UnsignedInteger getPublishRequestCount()
    {
        return PublishRequestCount;
    }
    
    public void setPublishRequestCount(UnsignedInteger PublishRequestCount)
    {
        this.PublishRequestCount = PublishRequestCount;
    }
    
    public UnsignedInteger getDataChangeNotificationsCount()
    {
        return DataChangeNotificationsCount;
    }
    
    public void setDataChangeNotificationsCount(UnsignedInteger DataChangeNotificationsCount)
    {
        this.DataChangeNotificationsCount = DataChangeNotificationsCount;
    }
    
    public UnsignedInteger getEventNotificationsCount()
    {
        return EventNotificationsCount;
    }
    
    public void setEventNotificationsCount(UnsignedInteger EventNotificationsCount)
    {
        this.EventNotificationsCount = EventNotificationsCount;
    }
    
    public UnsignedInteger getNotificationsCount()
    {
        return NotificationsCount;
    }
    
    public void setNotificationsCount(UnsignedInteger NotificationsCount)
    {
        this.NotificationsCount = NotificationsCount;
    }
    
    public UnsignedInteger getLatePublishRequestCount()
    {
        return LatePublishRequestCount;
    }
    
    public void setLatePublishRequestCount(UnsignedInteger LatePublishRequestCount)
    {
        this.LatePublishRequestCount = LatePublishRequestCount;
    }
    
    public UnsignedInteger getCurrentKeepAliveCount()
    {
        return CurrentKeepAliveCount;
    }
    
    public void setCurrentKeepAliveCount(UnsignedInteger CurrentKeepAliveCount)
    {
        this.CurrentKeepAliveCount = CurrentKeepAliveCount;
    }
    
    public UnsignedInteger getCurrentLifetimeCount()
    {
        return CurrentLifetimeCount;
    }
    
    public void setCurrentLifetimeCount(UnsignedInteger CurrentLifetimeCount)
    {
        this.CurrentLifetimeCount = CurrentLifetimeCount;
    }
    
    public UnsignedInteger getUnacknowledgedMessageCount()
    {
        return UnacknowledgedMessageCount;
    }
    
    public void setUnacknowledgedMessageCount(UnsignedInteger UnacknowledgedMessageCount)
    {
        this.UnacknowledgedMessageCount = UnacknowledgedMessageCount;
    }
    
    public UnsignedInteger getDiscardedMessageCount()
    {
        return DiscardedMessageCount;
    }
    
    public void setDiscardedMessageCount(UnsignedInteger DiscardedMessageCount)
    {
        this.DiscardedMessageCount = DiscardedMessageCount;
    }
    
    public UnsignedInteger getMonitoredItemCount()
    {
        return MonitoredItemCount;
    }
    
    public void setMonitoredItemCount(UnsignedInteger MonitoredItemCount)
    {
        this.MonitoredItemCount = MonitoredItemCount;
    }
    
    public UnsignedInteger getDisabledMonitoredItemCount()
    {
        return DisabledMonitoredItemCount;
    }
    
    public void setDisabledMonitoredItemCount(UnsignedInteger DisabledMonitoredItemCount)
    {
        this.DisabledMonitoredItemCount = DisabledMonitoredItemCount;
    }
    
    public UnsignedInteger getMonitoringQueueOverflowCount()
    {
        return MonitoringQueueOverflowCount;
    }
    
    public void setMonitoringQueueOverflowCount(UnsignedInteger MonitoringQueueOverflowCount)
    {
        this.MonitoringQueueOverflowCount = MonitoringQueueOverflowCount;
    }
    
    public UnsignedInteger getNextSequenceNumber()
    {
        return NextSequenceNumber;
    }
    
    public void setNextSequenceNumber(UnsignedInteger NextSequenceNumber)
    {
        this.NextSequenceNumber = NextSequenceNumber;
    }
    
    public UnsignedInteger getEventQueueOverFlowCount()
    {
        return EventQueueOverFlowCount;
    }
    
    public void setEventQueueOverFlowCount(UnsignedInteger EventQueueOverFlowCount)
    {
        this.EventQueueOverFlowCount = EventQueueOverFlowCount;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SubscriptionDiagnosticsDataType
      */
    public SubscriptionDiagnosticsDataType clone()
    {
        SubscriptionDiagnosticsDataType result = (SubscriptionDiagnosticsDataType) super.clone();
        result.SessionId = SessionId;
        result.SubscriptionId = SubscriptionId;
        result.Priority = Priority;
        result.PublishingInterval = PublishingInterval;
        result.MaxKeepAliveCount = MaxKeepAliveCount;
        result.MaxLifetimeCount = MaxLifetimeCount;
        result.MaxNotificationsPerPublish = MaxNotificationsPerPublish;
        result.PublishingEnabled = PublishingEnabled;
        result.ModifyCount = ModifyCount;
        result.EnableCount = EnableCount;
        result.DisableCount = DisableCount;
        result.RepublishRequestCount = RepublishRequestCount;
        result.RepublishMessageRequestCount = RepublishMessageRequestCount;
        result.RepublishMessageCount = RepublishMessageCount;
        result.TransferRequestCount = TransferRequestCount;
        result.TransferredToAltClientCount = TransferredToAltClientCount;
        result.TransferredToSameClientCount = TransferredToSameClientCount;
        result.PublishRequestCount = PublishRequestCount;
        result.DataChangeNotificationsCount = DataChangeNotificationsCount;
        result.EventNotificationsCount = EventNotificationsCount;
        result.NotificationsCount = NotificationsCount;
        result.LatePublishRequestCount = LatePublishRequestCount;
        result.CurrentKeepAliveCount = CurrentKeepAliveCount;
        result.CurrentLifetimeCount = CurrentLifetimeCount;
        result.UnacknowledgedMessageCount = UnacknowledgedMessageCount;
        result.DiscardedMessageCount = DiscardedMessageCount;
        result.MonitoredItemCount = MonitoredItemCount;
        result.DisabledMonitoredItemCount = DisabledMonitoredItemCount;
        result.MonitoringQueueOverflowCount = MonitoringQueueOverflowCount;
        result.NextSequenceNumber = NextSequenceNumber;
        result.EventQueueOverFlowCount = EventQueueOverFlowCount;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SubscriptionDiagnosticsDataType other = (SubscriptionDiagnosticsDataType) obj;
        if (SessionId==null) {
            if (other.SessionId != null) return false;
        } else if (!SessionId.equals(other.SessionId)) return false;
        if (SubscriptionId==null) {
            if (other.SubscriptionId != null) return false;
        } else if (!SubscriptionId.equals(other.SubscriptionId)) return false;
        if (Priority==null) {
            if (other.Priority != null) return false;
        } else if (!Priority.equals(other.Priority)) return false;
        if (PublishingInterval==null) {
            if (other.PublishingInterval != null) return false;
        } else if (!PublishingInterval.equals(other.PublishingInterval)) return false;
        if (MaxKeepAliveCount==null) {
            if (other.MaxKeepAliveCount != null) return false;
        } else if (!MaxKeepAliveCount.equals(other.MaxKeepAliveCount)) return false;
        if (MaxLifetimeCount==null) {
            if (other.MaxLifetimeCount != null) return false;
        } else if (!MaxLifetimeCount.equals(other.MaxLifetimeCount)) return false;
        if (MaxNotificationsPerPublish==null) {
            if (other.MaxNotificationsPerPublish != null) return false;
        } else if (!MaxNotificationsPerPublish.equals(other.MaxNotificationsPerPublish)) return false;
        if (PublishingEnabled==null) {
            if (other.PublishingEnabled != null) return false;
        } else if (!PublishingEnabled.equals(other.PublishingEnabled)) return false;
        if (ModifyCount==null) {
            if (other.ModifyCount != null) return false;
        } else if (!ModifyCount.equals(other.ModifyCount)) return false;
        if (EnableCount==null) {
            if (other.EnableCount != null) return false;
        } else if (!EnableCount.equals(other.EnableCount)) return false;
        if (DisableCount==null) {
            if (other.DisableCount != null) return false;
        } else if (!DisableCount.equals(other.DisableCount)) return false;
        if (RepublishRequestCount==null) {
            if (other.RepublishRequestCount != null) return false;
        } else if (!RepublishRequestCount.equals(other.RepublishRequestCount)) return false;
        if (RepublishMessageRequestCount==null) {
            if (other.RepublishMessageRequestCount != null) return false;
        } else if (!RepublishMessageRequestCount.equals(other.RepublishMessageRequestCount)) return false;
        if (RepublishMessageCount==null) {
            if (other.RepublishMessageCount != null) return false;
        } else if (!RepublishMessageCount.equals(other.RepublishMessageCount)) return false;
        if (TransferRequestCount==null) {
            if (other.TransferRequestCount != null) return false;
        } else if (!TransferRequestCount.equals(other.TransferRequestCount)) return false;
        if (TransferredToAltClientCount==null) {
            if (other.TransferredToAltClientCount != null) return false;
        } else if (!TransferredToAltClientCount.equals(other.TransferredToAltClientCount)) return false;
        if (TransferredToSameClientCount==null) {
            if (other.TransferredToSameClientCount != null) return false;
        } else if (!TransferredToSameClientCount.equals(other.TransferredToSameClientCount)) return false;
        if (PublishRequestCount==null) {
            if (other.PublishRequestCount != null) return false;
        } else if (!PublishRequestCount.equals(other.PublishRequestCount)) return false;
        if (DataChangeNotificationsCount==null) {
            if (other.DataChangeNotificationsCount != null) return false;
        } else if (!DataChangeNotificationsCount.equals(other.DataChangeNotificationsCount)) return false;
        if (EventNotificationsCount==null) {
            if (other.EventNotificationsCount != null) return false;
        } else if (!EventNotificationsCount.equals(other.EventNotificationsCount)) return false;
        if (NotificationsCount==null) {
            if (other.NotificationsCount != null) return false;
        } else if (!NotificationsCount.equals(other.NotificationsCount)) return false;
        if (LatePublishRequestCount==null) {
            if (other.LatePublishRequestCount != null) return false;
        } else if (!LatePublishRequestCount.equals(other.LatePublishRequestCount)) return false;
        if (CurrentKeepAliveCount==null) {
            if (other.CurrentKeepAliveCount != null) return false;
        } else if (!CurrentKeepAliveCount.equals(other.CurrentKeepAliveCount)) return false;
        if (CurrentLifetimeCount==null) {
            if (other.CurrentLifetimeCount != null) return false;
        } else if (!CurrentLifetimeCount.equals(other.CurrentLifetimeCount)) return false;
        if (UnacknowledgedMessageCount==null) {
            if (other.UnacknowledgedMessageCount != null) return false;
        } else if (!UnacknowledgedMessageCount.equals(other.UnacknowledgedMessageCount)) return false;
        if (DiscardedMessageCount==null) {
            if (other.DiscardedMessageCount != null) return false;
        } else if (!DiscardedMessageCount.equals(other.DiscardedMessageCount)) return false;
        if (MonitoredItemCount==null) {
            if (other.MonitoredItemCount != null) return false;
        } else if (!MonitoredItemCount.equals(other.MonitoredItemCount)) return false;
        if (DisabledMonitoredItemCount==null) {
            if (other.DisabledMonitoredItemCount != null) return false;
        } else if (!DisabledMonitoredItemCount.equals(other.DisabledMonitoredItemCount)) return false;
        if (MonitoringQueueOverflowCount==null) {
            if (other.MonitoringQueueOverflowCount != null) return false;
        } else if (!MonitoringQueueOverflowCount.equals(other.MonitoringQueueOverflowCount)) return false;
        if (NextSequenceNumber==null) {
            if (other.NextSequenceNumber != null) return false;
        } else if (!NextSequenceNumber.equals(other.NextSequenceNumber)) return false;
        if (EventQueueOverFlowCount==null) {
            if (other.EventQueueOverFlowCount != null) return false;
        } else if (!EventQueueOverFlowCount.equals(other.EventQueueOverFlowCount)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((SessionId == null) ? 0 : SessionId.hashCode());
        result = prime * result
                + ((SubscriptionId == null) ? 0 : SubscriptionId.hashCode());
        result = prime * result
                + ((Priority == null) ? 0 : Priority.hashCode());
        result = prime * result
                + ((PublishingInterval == null) ? 0 : PublishingInterval.hashCode());
        result = prime * result
                + ((MaxKeepAliveCount == null) ? 0 : MaxKeepAliveCount.hashCode());
        result = prime * result
                + ((MaxLifetimeCount == null) ? 0 : MaxLifetimeCount.hashCode());
        result = prime * result
                + ((MaxNotificationsPerPublish == null) ? 0 : MaxNotificationsPerPublish.hashCode());
        result = prime * result
                + ((PublishingEnabled == null) ? 0 : PublishingEnabled.hashCode());
        result = prime * result
                + ((ModifyCount == null) ? 0 : ModifyCount.hashCode());
        result = prime * result
                + ((EnableCount == null) ? 0 : EnableCount.hashCode());
        result = prime * result
                + ((DisableCount == null) ? 0 : DisableCount.hashCode());
        result = prime * result
                + ((RepublishRequestCount == null) ? 0 : RepublishRequestCount.hashCode());
        result = prime * result
                + ((RepublishMessageRequestCount == null) ? 0 : RepublishMessageRequestCount.hashCode());
        result = prime * result
                + ((RepublishMessageCount == null) ? 0 : RepublishMessageCount.hashCode());
        result = prime * result
                + ((TransferRequestCount == null) ? 0 : TransferRequestCount.hashCode());
        result = prime * result
                + ((TransferredToAltClientCount == null) ? 0 : TransferredToAltClientCount.hashCode());
        result = prime * result
                + ((TransferredToSameClientCount == null) ? 0 : TransferredToSameClientCount.hashCode());
        result = prime * result
                + ((PublishRequestCount == null) ? 0 : PublishRequestCount.hashCode());
        result = prime * result
                + ((DataChangeNotificationsCount == null) ? 0 : DataChangeNotificationsCount.hashCode());
        result = prime * result
                + ((EventNotificationsCount == null) ? 0 : EventNotificationsCount.hashCode());
        result = prime * result
                + ((NotificationsCount == null) ? 0 : NotificationsCount.hashCode());
        result = prime * result
                + ((LatePublishRequestCount == null) ? 0 : LatePublishRequestCount.hashCode());
        result = prime * result
                + ((CurrentKeepAliveCount == null) ? 0 : CurrentKeepAliveCount.hashCode());
        result = prime * result
                + ((CurrentLifetimeCount == null) ? 0 : CurrentLifetimeCount.hashCode());
        result = prime * result
                + ((UnacknowledgedMessageCount == null) ? 0 : UnacknowledgedMessageCount.hashCode());
        result = prime * result
                + ((DiscardedMessageCount == null) ? 0 : DiscardedMessageCount.hashCode());
        result = prime * result
                + ((MonitoredItemCount == null) ? 0 : MonitoredItemCount.hashCode());
        result = prime * result
                + ((DisabledMonitoredItemCount == null) ? 0 : DisabledMonitoredItemCount.hashCode());
        result = prime * result
                + ((MonitoringQueueOverflowCount == null) ? 0 : MonitoringQueueOverflowCount.hashCode());
        result = prime * result
                + ((NextSequenceNumber == null) ? 0 : NextSequenceNumber.hashCode());
        result = prime * result
                + ((EventQueueOverFlowCount == null) ? 0 : EventQueueOverFlowCount.hashCode());
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
		return "SubscriptionDiagnosticsDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
