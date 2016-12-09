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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.ServiceCounterDataType;
import org.opcfoundation.ua.utils.AbstractStructure;



public class SessionDiagnosticsDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.SessionDiagnosticsDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.SessionDiagnosticsDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.SessionDiagnosticsDataType_Encoding_DefaultXml);
	
    protected NodeId SessionId;
    protected String SessionName;
    protected ApplicationDescription ClientDescription;
    protected String ServerUri;
    protected String EndpointUrl;
    protected String[] LocaleIds;
    protected Double ActualSessionTimeout;
    protected UnsignedInteger MaxResponseMessageSize;
    protected DateTime ClientConnectionTime;
    protected DateTime ClientLastContactTime;
    protected UnsignedInteger CurrentSubscriptionsCount;
    protected UnsignedInteger CurrentMonitoredItemsCount;
    protected UnsignedInteger CurrentPublishRequestsInQueue;
    protected ServiceCounterDataType TotalRequestCount;
    protected UnsignedInteger UnauthorizedRequestCount;
    protected ServiceCounterDataType ReadCount;
    protected ServiceCounterDataType HistoryReadCount;
    protected ServiceCounterDataType WriteCount;
    protected ServiceCounterDataType HistoryUpdateCount;
    protected ServiceCounterDataType CallCount;
    protected ServiceCounterDataType CreateMonitoredItemsCount;
    protected ServiceCounterDataType ModifyMonitoredItemsCount;
    protected ServiceCounterDataType SetMonitoringModeCount;
    protected ServiceCounterDataType SetTriggeringCount;
    protected ServiceCounterDataType DeleteMonitoredItemsCount;
    protected ServiceCounterDataType CreateSubscriptionCount;
    protected ServiceCounterDataType ModifySubscriptionCount;
    protected ServiceCounterDataType SetPublishingModeCount;
    protected ServiceCounterDataType PublishCount;
    protected ServiceCounterDataType RepublishCount;
    protected ServiceCounterDataType TransferSubscriptionsCount;
    protected ServiceCounterDataType DeleteSubscriptionsCount;
    protected ServiceCounterDataType AddNodesCount;
    protected ServiceCounterDataType AddReferencesCount;
    protected ServiceCounterDataType DeleteNodesCount;
    protected ServiceCounterDataType DeleteReferencesCount;
    protected ServiceCounterDataType BrowseCount;
    protected ServiceCounterDataType BrowseNextCount;
    protected ServiceCounterDataType TranslateBrowsePathsToNodeIdsCount;
    protected ServiceCounterDataType QueryFirstCount;
    protected ServiceCounterDataType QueryNextCount;
    protected ServiceCounterDataType RegisterNodesCount;
    protected ServiceCounterDataType UnregisterNodesCount;
    
    public SessionDiagnosticsDataType() {}
    
    public SessionDiagnosticsDataType(NodeId SessionId, String SessionName, ApplicationDescription ClientDescription, String ServerUri, String EndpointUrl, String[] LocaleIds, Double ActualSessionTimeout, UnsignedInteger MaxResponseMessageSize, DateTime ClientConnectionTime, DateTime ClientLastContactTime, UnsignedInteger CurrentSubscriptionsCount, UnsignedInteger CurrentMonitoredItemsCount, UnsignedInteger CurrentPublishRequestsInQueue, ServiceCounterDataType TotalRequestCount, UnsignedInteger UnauthorizedRequestCount, ServiceCounterDataType ReadCount, ServiceCounterDataType HistoryReadCount, ServiceCounterDataType WriteCount, ServiceCounterDataType HistoryUpdateCount, ServiceCounterDataType CallCount, ServiceCounterDataType CreateMonitoredItemsCount, ServiceCounterDataType ModifyMonitoredItemsCount, ServiceCounterDataType SetMonitoringModeCount, ServiceCounterDataType SetTriggeringCount, ServiceCounterDataType DeleteMonitoredItemsCount, ServiceCounterDataType CreateSubscriptionCount, ServiceCounterDataType ModifySubscriptionCount, ServiceCounterDataType SetPublishingModeCount, ServiceCounterDataType PublishCount, ServiceCounterDataType RepublishCount, ServiceCounterDataType TransferSubscriptionsCount, ServiceCounterDataType DeleteSubscriptionsCount, ServiceCounterDataType AddNodesCount, ServiceCounterDataType AddReferencesCount, ServiceCounterDataType DeleteNodesCount, ServiceCounterDataType DeleteReferencesCount, ServiceCounterDataType BrowseCount, ServiceCounterDataType BrowseNextCount, ServiceCounterDataType TranslateBrowsePathsToNodeIdsCount, ServiceCounterDataType QueryFirstCount, ServiceCounterDataType QueryNextCount, ServiceCounterDataType RegisterNodesCount, ServiceCounterDataType UnregisterNodesCount)
    {
        this.SessionId = SessionId;
        this.SessionName = SessionName;
        this.ClientDescription = ClientDescription;
        this.ServerUri = ServerUri;
        this.EndpointUrl = EndpointUrl;
        this.LocaleIds = LocaleIds;
        this.ActualSessionTimeout = ActualSessionTimeout;
        this.MaxResponseMessageSize = MaxResponseMessageSize;
        this.ClientConnectionTime = ClientConnectionTime;
        this.ClientLastContactTime = ClientLastContactTime;
        this.CurrentSubscriptionsCount = CurrentSubscriptionsCount;
        this.CurrentMonitoredItemsCount = CurrentMonitoredItemsCount;
        this.CurrentPublishRequestsInQueue = CurrentPublishRequestsInQueue;
        this.TotalRequestCount = TotalRequestCount;
        this.UnauthorizedRequestCount = UnauthorizedRequestCount;
        this.ReadCount = ReadCount;
        this.HistoryReadCount = HistoryReadCount;
        this.WriteCount = WriteCount;
        this.HistoryUpdateCount = HistoryUpdateCount;
        this.CallCount = CallCount;
        this.CreateMonitoredItemsCount = CreateMonitoredItemsCount;
        this.ModifyMonitoredItemsCount = ModifyMonitoredItemsCount;
        this.SetMonitoringModeCount = SetMonitoringModeCount;
        this.SetTriggeringCount = SetTriggeringCount;
        this.DeleteMonitoredItemsCount = DeleteMonitoredItemsCount;
        this.CreateSubscriptionCount = CreateSubscriptionCount;
        this.ModifySubscriptionCount = ModifySubscriptionCount;
        this.SetPublishingModeCount = SetPublishingModeCount;
        this.PublishCount = PublishCount;
        this.RepublishCount = RepublishCount;
        this.TransferSubscriptionsCount = TransferSubscriptionsCount;
        this.DeleteSubscriptionsCount = DeleteSubscriptionsCount;
        this.AddNodesCount = AddNodesCount;
        this.AddReferencesCount = AddReferencesCount;
        this.DeleteNodesCount = DeleteNodesCount;
        this.DeleteReferencesCount = DeleteReferencesCount;
        this.BrowseCount = BrowseCount;
        this.BrowseNextCount = BrowseNextCount;
        this.TranslateBrowsePathsToNodeIdsCount = TranslateBrowsePathsToNodeIdsCount;
        this.QueryFirstCount = QueryFirstCount;
        this.QueryNextCount = QueryNextCount;
        this.RegisterNodesCount = RegisterNodesCount;
        this.UnregisterNodesCount = UnregisterNodesCount;
    }
    
    public NodeId getSessionId()
    {
        return SessionId;
    }
    
    public void setSessionId(NodeId SessionId)
    {
        this.SessionId = SessionId;
    }
    
    public String getSessionName()
    {
        return SessionName;
    }
    
    public void setSessionName(String SessionName)
    {
        this.SessionName = SessionName;
    }
    
    public ApplicationDescription getClientDescription()
    {
        return ClientDescription;
    }
    
    public void setClientDescription(ApplicationDescription ClientDescription)
    {
        this.ClientDescription = ClientDescription;
    }
    
    public String getServerUri()
    {
        return ServerUri;
    }
    
    public void setServerUri(String ServerUri)
    {
        this.ServerUri = ServerUri;
    }
    
    public String getEndpointUrl()
    {
        return EndpointUrl;
    }
    
    public void setEndpointUrl(String EndpointUrl)
    {
        this.EndpointUrl = EndpointUrl;
    }
    
    public String[] getLocaleIds()
    {
        return LocaleIds;
    }
    
    public void setLocaleIds(String[] LocaleIds)
    {
        this.LocaleIds = LocaleIds;
    }
    
    public Double getActualSessionTimeout()
    {
        return ActualSessionTimeout;
    }
    
    public void setActualSessionTimeout(Double ActualSessionTimeout)
    {
        this.ActualSessionTimeout = ActualSessionTimeout;
    }
    
    public UnsignedInteger getMaxResponseMessageSize()
    {
        return MaxResponseMessageSize;
    }
    
    public void setMaxResponseMessageSize(UnsignedInteger MaxResponseMessageSize)
    {
        this.MaxResponseMessageSize = MaxResponseMessageSize;
    }
    
    public DateTime getClientConnectionTime()
    {
        return ClientConnectionTime;
    }
    
    public void setClientConnectionTime(DateTime ClientConnectionTime)
    {
        this.ClientConnectionTime = ClientConnectionTime;
    }
    
    public DateTime getClientLastContactTime()
    {
        return ClientLastContactTime;
    }
    
    public void setClientLastContactTime(DateTime ClientLastContactTime)
    {
        this.ClientLastContactTime = ClientLastContactTime;
    }
    
    public UnsignedInteger getCurrentSubscriptionsCount()
    {
        return CurrentSubscriptionsCount;
    }
    
    public void setCurrentSubscriptionsCount(UnsignedInteger CurrentSubscriptionsCount)
    {
        this.CurrentSubscriptionsCount = CurrentSubscriptionsCount;
    }
    
    public UnsignedInteger getCurrentMonitoredItemsCount()
    {
        return CurrentMonitoredItemsCount;
    }
    
    public void setCurrentMonitoredItemsCount(UnsignedInteger CurrentMonitoredItemsCount)
    {
        this.CurrentMonitoredItemsCount = CurrentMonitoredItemsCount;
    }
    
    public UnsignedInteger getCurrentPublishRequestsInQueue()
    {
        return CurrentPublishRequestsInQueue;
    }
    
    public void setCurrentPublishRequestsInQueue(UnsignedInteger CurrentPublishRequestsInQueue)
    {
        this.CurrentPublishRequestsInQueue = CurrentPublishRequestsInQueue;
    }
    
    public ServiceCounterDataType getTotalRequestCount()
    {
        return TotalRequestCount;
    }
    
    public void setTotalRequestCount(ServiceCounterDataType TotalRequestCount)
    {
        this.TotalRequestCount = TotalRequestCount;
    }
    
    public UnsignedInteger getUnauthorizedRequestCount()
    {
        return UnauthorizedRequestCount;
    }
    
    public void setUnauthorizedRequestCount(UnsignedInteger UnauthorizedRequestCount)
    {
        this.UnauthorizedRequestCount = UnauthorizedRequestCount;
    }
    
    public ServiceCounterDataType getReadCount()
    {
        return ReadCount;
    }
    
    public void setReadCount(ServiceCounterDataType ReadCount)
    {
        this.ReadCount = ReadCount;
    }
    
    public ServiceCounterDataType getHistoryReadCount()
    {
        return HistoryReadCount;
    }
    
    public void setHistoryReadCount(ServiceCounterDataType HistoryReadCount)
    {
        this.HistoryReadCount = HistoryReadCount;
    }
    
    public ServiceCounterDataType getWriteCount()
    {
        return WriteCount;
    }
    
    public void setWriteCount(ServiceCounterDataType WriteCount)
    {
        this.WriteCount = WriteCount;
    }
    
    public ServiceCounterDataType getHistoryUpdateCount()
    {
        return HistoryUpdateCount;
    }
    
    public void setHistoryUpdateCount(ServiceCounterDataType HistoryUpdateCount)
    {
        this.HistoryUpdateCount = HistoryUpdateCount;
    }
    
    public ServiceCounterDataType getCallCount()
    {
        return CallCount;
    }
    
    public void setCallCount(ServiceCounterDataType CallCount)
    {
        this.CallCount = CallCount;
    }
    
    public ServiceCounterDataType getCreateMonitoredItemsCount()
    {
        return CreateMonitoredItemsCount;
    }
    
    public void setCreateMonitoredItemsCount(ServiceCounterDataType CreateMonitoredItemsCount)
    {
        this.CreateMonitoredItemsCount = CreateMonitoredItemsCount;
    }
    
    public ServiceCounterDataType getModifyMonitoredItemsCount()
    {
        return ModifyMonitoredItemsCount;
    }
    
    public void setModifyMonitoredItemsCount(ServiceCounterDataType ModifyMonitoredItemsCount)
    {
        this.ModifyMonitoredItemsCount = ModifyMonitoredItemsCount;
    }
    
    public ServiceCounterDataType getSetMonitoringModeCount()
    {
        return SetMonitoringModeCount;
    }
    
    public void setSetMonitoringModeCount(ServiceCounterDataType SetMonitoringModeCount)
    {
        this.SetMonitoringModeCount = SetMonitoringModeCount;
    }
    
    public ServiceCounterDataType getSetTriggeringCount()
    {
        return SetTriggeringCount;
    }
    
    public void setSetTriggeringCount(ServiceCounterDataType SetTriggeringCount)
    {
        this.SetTriggeringCount = SetTriggeringCount;
    }
    
    public ServiceCounterDataType getDeleteMonitoredItemsCount()
    {
        return DeleteMonitoredItemsCount;
    }
    
    public void setDeleteMonitoredItemsCount(ServiceCounterDataType DeleteMonitoredItemsCount)
    {
        this.DeleteMonitoredItemsCount = DeleteMonitoredItemsCount;
    }
    
    public ServiceCounterDataType getCreateSubscriptionCount()
    {
        return CreateSubscriptionCount;
    }
    
    public void setCreateSubscriptionCount(ServiceCounterDataType CreateSubscriptionCount)
    {
        this.CreateSubscriptionCount = CreateSubscriptionCount;
    }
    
    public ServiceCounterDataType getModifySubscriptionCount()
    {
        return ModifySubscriptionCount;
    }
    
    public void setModifySubscriptionCount(ServiceCounterDataType ModifySubscriptionCount)
    {
        this.ModifySubscriptionCount = ModifySubscriptionCount;
    }
    
    public ServiceCounterDataType getSetPublishingModeCount()
    {
        return SetPublishingModeCount;
    }
    
    public void setSetPublishingModeCount(ServiceCounterDataType SetPublishingModeCount)
    {
        this.SetPublishingModeCount = SetPublishingModeCount;
    }
    
    public ServiceCounterDataType getPublishCount()
    {
        return PublishCount;
    }
    
    public void setPublishCount(ServiceCounterDataType PublishCount)
    {
        this.PublishCount = PublishCount;
    }
    
    public ServiceCounterDataType getRepublishCount()
    {
        return RepublishCount;
    }
    
    public void setRepublishCount(ServiceCounterDataType RepublishCount)
    {
        this.RepublishCount = RepublishCount;
    }
    
    public ServiceCounterDataType getTransferSubscriptionsCount()
    {
        return TransferSubscriptionsCount;
    }
    
    public void setTransferSubscriptionsCount(ServiceCounterDataType TransferSubscriptionsCount)
    {
        this.TransferSubscriptionsCount = TransferSubscriptionsCount;
    }
    
    public ServiceCounterDataType getDeleteSubscriptionsCount()
    {
        return DeleteSubscriptionsCount;
    }
    
    public void setDeleteSubscriptionsCount(ServiceCounterDataType DeleteSubscriptionsCount)
    {
        this.DeleteSubscriptionsCount = DeleteSubscriptionsCount;
    }
    
    public ServiceCounterDataType getAddNodesCount()
    {
        return AddNodesCount;
    }
    
    public void setAddNodesCount(ServiceCounterDataType AddNodesCount)
    {
        this.AddNodesCount = AddNodesCount;
    }
    
    public ServiceCounterDataType getAddReferencesCount()
    {
        return AddReferencesCount;
    }
    
    public void setAddReferencesCount(ServiceCounterDataType AddReferencesCount)
    {
        this.AddReferencesCount = AddReferencesCount;
    }
    
    public ServiceCounterDataType getDeleteNodesCount()
    {
        return DeleteNodesCount;
    }
    
    public void setDeleteNodesCount(ServiceCounterDataType DeleteNodesCount)
    {
        this.DeleteNodesCount = DeleteNodesCount;
    }
    
    public ServiceCounterDataType getDeleteReferencesCount()
    {
        return DeleteReferencesCount;
    }
    
    public void setDeleteReferencesCount(ServiceCounterDataType DeleteReferencesCount)
    {
        this.DeleteReferencesCount = DeleteReferencesCount;
    }
    
    public ServiceCounterDataType getBrowseCount()
    {
        return BrowseCount;
    }
    
    public void setBrowseCount(ServiceCounterDataType BrowseCount)
    {
        this.BrowseCount = BrowseCount;
    }
    
    public ServiceCounterDataType getBrowseNextCount()
    {
        return BrowseNextCount;
    }
    
    public void setBrowseNextCount(ServiceCounterDataType BrowseNextCount)
    {
        this.BrowseNextCount = BrowseNextCount;
    }
    
    public ServiceCounterDataType getTranslateBrowsePathsToNodeIdsCount()
    {
        return TranslateBrowsePathsToNodeIdsCount;
    }
    
    public void setTranslateBrowsePathsToNodeIdsCount(ServiceCounterDataType TranslateBrowsePathsToNodeIdsCount)
    {
        this.TranslateBrowsePathsToNodeIdsCount = TranslateBrowsePathsToNodeIdsCount;
    }
    
    public ServiceCounterDataType getQueryFirstCount()
    {
        return QueryFirstCount;
    }
    
    public void setQueryFirstCount(ServiceCounterDataType QueryFirstCount)
    {
        this.QueryFirstCount = QueryFirstCount;
    }
    
    public ServiceCounterDataType getQueryNextCount()
    {
        return QueryNextCount;
    }
    
    public void setQueryNextCount(ServiceCounterDataType QueryNextCount)
    {
        this.QueryNextCount = QueryNextCount;
    }
    
    public ServiceCounterDataType getRegisterNodesCount()
    {
        return RegisterNodesCount;
    }
    
    public void setRegisterNodesCount(ServiceCounterDataType RegisterNodesCount)
    {
        this.RegisterNodesCount = RegisterNodesCount;
    }
    
    public ServiceCounterDataType getUnregisterNodesCount()
    {
        return UnregisterNodesCount;
    }
    
    public void setUnregisterNodesCount(ServiceCounterDataType UnregisterNodesCount)
    {
        this.UnregisterNodesCount = UnregisterNodesCount;
    }
    
    /**
      * Deep clone
      *
      * @return cloned SessionDiagnosticsDataType
      */
    public SessionDiagnosticsDataType clone()
    {
        SessionDiagnosticsDataType result = (SessionDiagnosticsDataType) super.clone();
        result.SessionId = SessionId;
        result.SessionName = SessionName;
        result.ClientDescription = ClientDescription==null ? null : ClientDescription.clone();
        result.ServerUri = ServerUri;
        result.EndpointUrl = EndpointUrl;
        result.LocaleIds = LocaleIds==null ? null : LocaleIds.clone();
        result.ActualSessionTimeout = ActualSessionTimeout;
        result.MaxResponseMessageSize = MaxResponseMessageSize;
        result.ClientConnectionTime = ClientConnectionTime;
        result.ClientLastContactTime = ClientLastContactTime;
        result.CurrentSubscriptionsCount = CurrentSubscriptionsCount;
        result.CurrentMonitoredItemsCount = CurrentMonitoredItemsCount;
        result.CurrentPublishRequestsInQueue = CurrentPublishRequestsInQueue;
        result.TotalRequestCount = TotalRequestCount==null ? null : TotalRequestCount.clone();
        result.UnauthorizedRequestCount = UnauthorizedRequestCount;
        result.ReadCount = ReadCount==null ? null : ReadCount.clone();
        result.HistoryReadCount = HistoryReadCount==null ? null : HistoryReadCount.clone();
        result.WriteCount = WriteCount==null ? null : WriteCount.clone();
        result.HistoryUpdateCount = HistoryUpdateCount==null ? null : HistoryUpdateCount.clone();
        result.CallCount = CallCount==null ? null : CallCount.clone();
        result.CreateMonitoredItemsCount = CreateMonitoredItemsCount==null ? null : CreateMonitoredItemsCount.clone();
        result.ModifyMonitoredItemsCount = ModifyMonitoredItemsCount==null ? null : ModifyMonitoredItemsCount.clone();
        result.SetMonitoringModeCount = SetMonitoringModeCount==null ? null : SetMonitoringModeCount.clone();
        result.SetTriggeringCount = SetTriggeringCount==null ? null : SetTriggeringCount.clone();
        result.DeleteMonitoredItemsCount = DeleteMonitoredItemsCount==null ? null : DeleteMonitoredItemsCount.clone();
        result.CreateSubscriptionCount = CreateSubscriptionCount==null ? null : CreateSubscriptionCount.clone();
        result.ModifySubscriptionCount = ModifySubscriptionCount==null ? null : ModifySubscriptionCount.clone();
        result.SetPublishingModeCount = SetPublishingModeCount==null ? null : SetPublishingModeCount.clone();
        result.PublishCount = PublishCount==null ? null : PublishCount.clone();
        result.RepublishCount = RepublishCount==null ? null : RepublishCount.clone();
        result.TransferSubscriptionsCount = TransferSubscriptionsCount==null ? null : TransferSubscriptionsCount.clone();
        result.DeleteSubscriptionsCount = DeleteSubscriptionsCount==null ? null : DeleteSubscriptionsCount.clone();
        result.AddNodesCount = AddNodesCount==null ? null : AddNodesCount.clone();
        result.AddReferencesCount = AddReferencesCount==null ? null : AddReferencesCount.clone();
        result.DeleteNodesCount = DeleteNodesCount==null ? null : DeleteNodesCount.clone();
        result.DeleteReferencesCount = DeleteReferencesCount==null ? null : DeleteReferencesCount.clone();
        result.BrowseCount = BrowseCount==null ? null : BrowseCount.clone();
        result.BrowseNextCount = BrowseNextCount==null ? null : BrowseNextCount.clone();
        result.TranslateBrowsePathsToNodeIdsCount = TranslateBrowsePathsToNodeIdsCount==null ? null : TranslateBrowsePathsToNodeIdsCount.clone();
        result.QueryFirstCount = QueryFirstCount==null ? null : QueryFirstCount.clone();
        result.QueryNextCount = QueryNextCount==null ? null : QueryNextCount.clone();
        result.RegisterNodesCount = RegisterNodesCount==null ? null : RegisterNodesCount.clone();
        result.UnregisterNodesCount = UnregisterNodesCount==null ? null : UnregisterNodesCount.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SessionDiagnosticsDataType other = (SessionDiagnosticsDataType) obj;
        if (SessionId==null) {
            if (other.SessionId != null) return false;
        } else if (!SessionId.equals(other.SessionId)) return false;
        if (SessionName==null) {
            if (other.SessionName != null) return false;
        } else if (!SessionName.equals(other.SessionName)) return false;
        if (ClientDescription==null) {
            if (other.ClientDescription != null) return false;
        } else if (!ClientDescription.equals(other.ClientDescription)) return false;
        if (ServerUri==null) {
            if (other.ServerUri != null) return false;
        } else if (!ServerUri.equals(other.ServerUri)) return false;
        if (EndpointUrl==null) {
            if (other.EndpointUrl != null) return false;
        } else if (!EndpointUrl.equals(other.EndpointUrl)) return false;
        if (LocaleIds==null) {
            if (other.LocaleIds != null) return false;
        } else if (!Arrays.equals(LocaleIds, other.LocaleIds)) return false;
        if (ActualSessionTimeout==null) {
            if (other.ActualSessionTimeout != null) return false;
        } else if (!ActualSessionTimeout.equals(other.ActualSessionTimeout)) return false;
        if (MaxResponseMessageSize==null) {
            if (other.MaxResponseMessageSize != null) return false;
        } else if (!MaxResponseMessageSize.equals(other.MaxResponseMessageSize)) return false;
        if (ClientConnectionTime==null) {
            if (other.ClientConnectionTime != null) return false;
        } else if (!ClientConnectionTime.equals(other.ClientConnectionTime)) return false;
        if (ClientLastContactTime==null) {
            if (other.ClientLastContactTime != null) return false;
        } else if (!ClientLastContactTime.equals(other.ClientLastContactTime)) return false;
        if (CurrentSubscriptionsCount==null) {
            if (other.CurrentSubscriptionsCount != null) return false;
        } else if (!CurrentSubscriptionsCount.equals(other.CurrentSubscriptionsCount)) return false;
        if (CurrentMonitoredItemsCount==null) {
            if (other.CurrentMonitoredItemsCount != null) return false;
        } else if (!CurrentMonitoredItemsCount.equals(other.CurrentMonitoredItemsCount)) return false;
        if (CurrentPublishRequestsInQueue==null) {
            if (other.CurrentPublishRequestsInQueue != null) return false;
        } else if (!CurrentPublishRequestsInQueue.equals(other.CurrentPublishRequestsInQueue)) return false;
        if (TotalRequestCount==null) {
            if (other.TotalRequestCount != null) return false;
        } else if (!TotalRequestCount.equals(other.TotalRequestCount)) return false;
        if (UnauthorizedRequestCount==null) {
            if (other.UnauthorizedRequestCount != null) return false;
        } else if (!UnauthorizedRequestCount.equals(other.UnauthorizedRequestCount)) return false;
        if (ReadCount==null) {
            if (other.ReadCount != null) return false;
        } else if (!ReadCount.equals(other.ReadCount)) return false;
        if (HistoryReadCount==null) {
            if (other.HistoryReadCount != null) return false;
        } else if (!HistoryReadCount.equals(other.HistoryReadCount)) return false;
        if (WriteCount==null) {
            if (other.WriteCount != null) return false;
        } else if (!WriteCount.equals(other.WriteCount)) return false;
        if (HistoryUpdateCount==null) {
            if (other.HistoryUpdateCount != null) return false;
        } else if (!HistoryUpdateCount.equals(other.HistoryUpdateCount)) return false;
        if (CallCount==null) {
            if (other.CallCount != null) return false;
        } else if (!CallCount.equals(other.CallCount)) return false;
        if (CreateMonitoredItemsCount==null) {
            if (other.CreateMonitoredItemsCount != null) return false;
        } else if (!CreateMonitoredItemsCount.equals(other.CreateMonitoredItemsCount)) return false;
        if (ModifyMonitoredItemsCount==null) {
            if (other.ModifyMonitoredItemsCount != null) return false;
        } else if (!ModifyMonitoredItemsCount.equals(other.ModifyMonitoredItemsCount)) return false;
        if (SetMonitoringModeCount==null) {
            if (other.SetMonitoringModeCount != null) return false;
        } else if (!SetMonitoringModeCount.equals(other.SetMonitoringModeCount)) return false;
        if (SetTriggeringCount==null) {
            if (other.SetTriggeringCount != null) return false;
        } else if (!SetTriggeringCount.equals(other.SetTriggeringCount)) return false;
        if (DeleteMonitoredItemsCount==null) {
            if (other.DeleteMonitoredItemsCount != null) return false;
        } else if (!DeleteMonitoredItemsCount.equals(other.DeleteMonitoredItemsCount)) return false;
        if (CreateSubscriptionCount==null) {
            if (other.CreateSubscriptionCount != null) return false;
        } else if (!CreateSubscriptionCount.equals(other.CreateSubscriptionCount)) return false;
        if (ModifySubscriptionCount==null) {
            if (other.ModifySubscriptionCount != null) return false;
        } else if (!ModifySubscriptionCount.equals(other.ModifySubscriptionCount)) return false;
        if (SetPublishingModeCount==null) {
            if (other.SetPublishingModeCount != null) return false;
        } else if (!SetPublishingModeCount.equals(other.SetPublishingModeCount)) return false;
        if (PublishCount==null) {
            if (other.PublishCount != null) return false;
        } else if (!PublishCount.equals(other.PublishCount)) return false;
        if (RepublishCount==null) {
            if (other.RepublishCount != null) return false;
        } else if (!RepublishCount.equals(other.RepublishCount)) return false;
        if (TransferSubscriptionsCount==null) {
            if (other.TransferSubscriptionsCount != null) return false;
        } else if (!TransferSubscriptionsCount.equals(other.TransferSubscriptionsCount)) return false;
        if (DeleteSubscriptionsCount==null) {
            if (other.DeleteSubscriptionsCount != null) return false;
        } else if (!DeleteSubscriptionsCount.equals(other.DeleteSubscriptionsCount)) return false;
        if (AddNodesCount==null) {
            if (other.AddNodesCount != null) return false;
        } else if (!AddNodesCount.equals(other.AddNodesCount)) return false;
        if (AddReferencesCount==null) {
            if (other.AddReferencesCount != null) return false;
        } else if (!AddReferencesCount.equals(other.AddReferencesCount)) return false;
        if (DeleteNodesCount==null) {
            if (other.DeleteNodesCount != null) return false;
        } else if (!DeleteNodesCount.equals(other.DeleteNodesCount)) return false;
        if (DeleteReferencesCount==null) {
            if (other.DeleteReferencesCount != null) return false;
        } else if (!DeleteReferencesCount.equals(other.DeleteReferencesCount)) return false;
        if (BrowseCount==null) {
            if (other.BrowseCount != null) return false;
        } else if (!BrowseCount.equals(other.BrowseCount)) return false;
        if (BrowseNextCount==null) {
            if (other.BrowseNextCount != null) return false;
        } else if (!BrowseNextCount.equals(other.BrowseNextCount)) return false;
        if (TranslateBrowsePathsToNodeIdsCount==null) {
            if (other.TranslateBrowsePathsToNodeIdsCount != null) return false;
        } else if (!TranslateBrowsePathsToNodeIdsCount.equals(other.TranslateBrowsePathsToNodeIdsCount)) return false;
        if (QueryFirstCount==null) {
            if (other.QueryFirstCount != null) return false;
        } else if (!QueryFirstCount.equals(other.QueryFirstCount)) return false;
        if (QueryNextCount==null) {
            if (other.QueryNextCount != null) return false;
        } else if (!QueryNextCount.equals(other.QueryNextCount)) return false;
        if (RegisterNodesCount==null) {
            if (other.RegisterNodesCount != null) return false;
        } else if (!RegisterNodesCount.equals(other.RegisterNodesCount)) return false;
        if (UnregisterNodesCount==null) {
            if (other.UnregisterNodesCount != null) return false;
        } else if (!UnregisterNodesCount.equals(other.UnregisterNodesCount)) return false;
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
                + ((SessionName == null) ? 0 : SessionName.hashCode());
        result = prime * result
                + ((ClientDescription == null) ? 0 : ClientDescription.hashCode());
        result = prime * result
                + ((ServerUri == null) ? 0 : ServerUri.hashCode());
        result = prime * result
                + ((EndpointUrl == null) ? 0 : EndpointUrl.hashCode());
        result = prime * result
                + ((LocaleIds == null) ? 0 : Arrays.hashCode(LocaleIds));
        result = prime * result
                + ((ActualSessionTimeout == null) ? 0 : ActualSessionTimeout.hashCode());
        result = prime * result
                + ((MaxResponseMessageSize == null) ? 0 : MaxResponseMessageSize.hashCode());
        result = prime * result
                + ((ClientConnectionTime == null) ? 0 : ClientConnectionTime.hashCode());
        result = prime * result
                + ((ClientLastContactTime == null) ? 0 : ClientLastContactTime.hashCode());
        result = prime * result
                + ((CurrentSubscriptionsCount == null) ? 0 : CurrentSubscriptionsCount.hashCode());
        result = prime * result
                + ((CurrentMonitoredItemsCount == null) ? 0 : CurrentMonitoredItemsCount.hashCode());
        result = prime * result
                + ((CurrentPublishRequestsInQueue == null) ? 0 : CurrentPublishRequestsInQueue.hashCode());
        result = prime * result
                + ((TotalRequestCount == null) ? 0 : TotalRequestCount.hashCode());
        result = prime * result
                + ((UnauthorizedRequestCount == null) ? 0 : UnauthorizedRequestCount.hashCode());
        result = prime * result
                + ((ReadCount == null) ? 0 : ReadCount.hashCode());
        result = prime * result
                + ((HistoryReadCount == null) ? 0 : HistoryReadCount.hashCode());
        result = prime * result
                + ((WriteCount == null) ? 0 : WriteCount.hashCode());
        result = prime * result
                + ((HistoryUpdateCount == null) ? 0 : HistoryUpdateCount.hashCode());
        result = prime * result
                + ((CallCount == null) ? 0 : CallCount.hashCode());
        result = prime * result
                + ((CreateMonitoredItemsCount == null) ? 0 : CreateMonitoredItemsCount.hashCode());
        result = prime * result
                + ((ModifyMonitoredItemsCount == null) ? 0 : ModifyMonitoredItemsCount.hashCode());
        result = prime * result
                + ((SetMonitoringModeCount == null) ? 0 : SetMonitoringModeCount.hashCode());
        result = prime * result
                + ((SetTriggeringCount == null) ? 0 : SetTriggeringCount.hashCode());
        result = prime * result
                + ((DeleteMonitoredItemsCount == null) ? 0 : DeleteMonitoredItemsCount.hashCode());
        result = prime * result
                + ((CreateSubscriptionCount == null) ? 0 : CreateSubscriptionCount.hashCode());
        result = prime * result
                + ((ModifySubscriptionCount == null) ? 0 : ModifySubscriptionCount.hashCode());
        result = prime * result
                + ((SetPublishingModeCount == null) ? 0 : SetPublishingModeCount.hashCode());
        result = prime * result
                + ((PublishCount == null) ? 0 : PublishCount.hashCode());
        result = prime * result
                + ((RepublishCount == null) ? 0 : RepublishCount.hashCode());
        result = prime * result
                + ((TransferSubscriptionsCount == null) ? 0 : TransferSubscriptionsCount.hashCode());
        result = prime * result
                + ((DeleteSubscriptionsCount == null) ? 0 : DeleteSubscriptionsCount.hashCode());
        result = prime * result
                + ((AddNodesCount == null) ? 0 : AddNodesCount.hashCode());
        result = prime * result
                + ((AddReferencesCount == null) ? 0 : AddReferencesCount.hashCode());
        result = prime * result
                + ((DeleteNodesCount == null) ? 0 : DeleteNodesCount.hashCode());
        result = prime * result
                + ((DeleteReferencesCount == null) ? 0 : DeleteReferencesCount.hashCode());
        result = prime * result
                + ((BrowseCount == null) ? 0 : BrowseCount.hashCode());
        result = prime * result
                + ((BrowseNextCount == null) ? 0 : BrowseNextCount.hashCode());
        result = prime * result
                + ((TranslateBrowsePathsToNodeIdsCount == null) ? 0 : TranslateBrowsePathsToNodeIdsCount.hashCode());
        result = prime * result
                + ((QueryFirstCount == null) ? 0 : QueryFirstCount.hashCode());
        result = prime * result
                + ((QueryNextCount == null) ? 0 : QueryNextCount.hashCode());
        result = prime * result
                + ((RegisterNodesCount == null) ? 0 : RegisterNodesCount.hashCode());
        result = prime * result
                + ((UnregisterNodesCount == null) ? 0 : UnregisterNodesCount.hashCode());
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
		return "SessionDiagnosticsDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
