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

package org.opcfoundation.ua.transport;

import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedByte;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ActivateSessionRequest;
import org.opcfoundation.ua.core.ActivateSessionResponse;
import org.opcfoundation.ua.core.AddNodesItem;
import org.opcfoundation.ua.core.AddNodesRequest;
import org.opcfoundation.ua.core.AddNodesResponse;
import org.opcfoundation.ua.core.AddReferencesItem;
import org.opcfoundation.ua.core.AddReferencesRequest;
import org.opcfoundation.ua.core.AddReferencesResponse;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.BrowseDescription;
import org.opcfoundation.ua.core.BrowseNextRequest;
import org.opcfoundation.ua.core.BrowseNextResponse;
import org.opcfoundation.ua.core.BrowsePath;
import org.opcfoundation.ua.core.BrowseRequest;
import org.opcfoundation.ua.core.BrowseResponse;
import org.opcfoundation.ua.core.CallMethodRequest;
import org.opcfoundation.ua.core.CallRequest;
import org.opcfoundation.ua.core.CallResponse;
import org.opcfoundation.ua.core.CancelRequest;
import org.opcfoundation.ua.core.CancelResponse;
import org.opcfoundation.ua.core.CloseSecureChannelRequest;
import org.opcfoundation.ua.core.CloseSecureChannelResponse;
import org.opcfoundation.ua.core.CloseSessionRequest;
import org.opcfoundation.ua.core.CloseSessionResponse;
import org.opcfoundation.ua.core.ContentFilter;
import org.opcfoundation.ua.core.CreateMonitoredItemsRequest;
import org.opcfoundation.ua.core.CreateMonitoredItemsResponse;
import org.opcfoundation.ua.core.CreateSessionRequest;
import org.opcfoundation.ua.core.CreateSessionResponse;
import org.opcfoundation.ua.core.CreateSubscriptionRequest;
import org.opcfoundation.ua.core.CreateSubscriptionResponse;
import org.opcfoundation.ua.core.DeleteMonitoredItemsRequest;
import org.opcfoundation.ua.core.DeleteMonitoredItemsResponse;
import org.opcfoundation.ua.core.DeleteNodesItem;
import org.opcfoundation.ua.core.DeleteNodesRequest;
import org.opcfoundation.ua.core.DeleteNodesResponse;
import org.opcfoundation.ua.core.DeleteReferencesItem;
import org.opcfoundation.ua.core.DeleteReferencesRequest;
import org.opcfoundation.ua.core.DeleteReferencesResponse;
import org.opcfoundation.ua.core.DeleteSubscriptionsRequest;
import org.opcfoundation.ua.core.DeleteSubscriptionsResponse;
import org.opcfoundation.ua.core.FindServersOnNetworkRequest;
import org.opcfoundation.ua.core.FindServersOnNetworkResponse;
import org.opcfoundation.ua.core.FindServersRequest;
import org.opcfoundation.ua.core.FindServersResponse;
import org.opcfoundation.ua.core.GetEndpointsRequest;
import org.opcfoundation.ua.core.GetEndpointsResponse;
import org.opcfoundation.ua.core.HistoryReadRequest;
import org.opcfoundation.ua.core.HistoryReadResponse;
import org.opcfoundation.ua.core.HistoryReadValueId;
import org.opcfoundation.ua.core.HistoryUpdateRequest;
import org.opcfoundation.ua.core.HistoryUpdateResponse;
import org.opcfoundation.ua.core.MessageSecurityMode;
import org.opcfoundation.ua.core.ModifyMonitoredItemsRequest;
import org.opcfoundation.ua.core.ModifyMonitoredItemsResponse;
import org.opcfoundation.ua.core.ModifySubscriptionRequest;
import org.opcfoundation.ua.core.ModifySubscriptionResponse;
import org.opcfoundation.ua.core.MonitoredItemCreateRequest;
import org.opcfoundation.ua.core.MonitoredItemModifyRequest;
import org.opcfoundation.ua.core.MonitoringMode;
import org.opcfoundation.ua.core.NodeTypeDescription;
import org.opcfoundation.ua.core.OpenSecureChannelRequest;
import org.opcfoundation.ua.core.OpenSecureChannelResponse;
import org.opcfoundation.ua.core.PublishRequest;
import org.opcfoundation.ua.core.PublishResponse;
import org.opcfoundation.ua.core.QueryFirstRequest;
import org.opcfoundation.ua.core.QueryFirstResponse;
import org.opcfoundation.ua.core.QueryNextRequest;
import org.opcfoundation.ua.core.QueryNextResponse;
import org.opcfoundation.ua.core.ReadRequest;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.RegisterNodesRequest;
import org.opcfoundation.ua.core.RegisterNodesResponse;
import org.opcfoundation.ua.core.RegisterServer2Request;
import org.opcfoundation.ua.core.RegisterServer2Response;
import org.opcfoundation.ua.core.RegisterServerRequest;
import org.opcfoundation.ua.core.RegisterServerResponse;
import org.opcfoundation.ua.core.RegisteredServer;
import org.opcfoundation.ua.core.RepublishRequest;
import org.opcfoundation.ua.core.RepublishResponse;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.SecurityTokenRequestType;
import org.opcfoundation.ua.core.SetMonitoringModeRequest;
import org.opcfoundation.ua.core.SetMonitoringModeResponse;
import org.opcfoundation.ua.core.SetPublishingModeRequest;
import org.opcfoundation.ua.core.SetPublishingModeResponse;
import org.opcfoundation.ua.core.SetTriggeringRequest;
import org.opcfoundation.ua.core.SetTriggeringResponse;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.SignedSoftwareCertificate;
import org.opcfoundation.ua.core.SubscriptionAcknowledgement;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.core.TransferSubscriptionsRequest;
import org.opcfoundation.ua.core.TransferSubscriptionsResponse;
import org.opcfoundation.ua.core.TranslateBrowsePathsToNodeIdsRequest;
import org.opcfoundation.ua.core.TranslateBrowsePathsToNodeIdsResponse;
import org.opcfoundation.ua.core.UnregisterNodesRequest;
import org.opcfoundation.ua.core.UnregisterNodesResponse;
import org.opcfoundation.ua.core.ViewDescription;
import org.opcfoundation.ua.core.WriteRequest;
import org.opcfoundation.ua.core.WriteResponse;
import org.opcfoundation.ua.core.WriteValue;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.ChannelService;


/**
 * This is a autogenerated class, do not modify this class directly. 
 * @see ServiceChannel 
 */
public class ChannelService {

	/** The back-end channel object */
	protected RequestChannel channel;
	
	public ChannelService(RequestChannel clientChannel)
	{
		if (clientChannel==null)
			throw new IllegalArgumentException("null arg");
		this.channel = clientChannel;
	}
	
	public ChannelService()
	{
	}
	
	protected void setRequestChannel(RequestChannel channel)
	{
		if (channel==null)
			throw new IllegalArgumentException("null arg");
		this.channel = channel;		
	}	
	
    /**
     * Synchronous FindServers service request. 
     * 
     * @param RequestHeader
     * @param EndpointUrl
     * @param LocaleIds
     * @param ServerUris
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public FindServersResponse FindServers(RequestHeader RequestHeader, String EndpointUrl, String[] LocaleIds, String... ServerUris)
    throws ServiceFaultException, ServiceResultException {
    	FindServersRequest req = new FindServersRequest(RequestHeader, EndpointUrl, LocaleIds, ServerUris);
    	return (FindServersResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous FindServers service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public FindServersResponse FindServers(FindServersRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (FindServersResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous FindServers service request. 
     * 
     * @param RequestHeader
     * @param EndpointUrl
     * @param LocaleIds
     * @param ServerUris
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult FindServersAsync(RequestHeader RequestHeader, String EndpointUrl, String[] LocaleIds, String... ServerUris)
    {
    	FindServersRequest req = new FindServersRequest(RequestHeader, EndpointUrl, LocaleIds, ServerUris);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous FindServers service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult FindServersAsync(FindServersRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous FindServersOnNetwork service request. 
     * 
     * @param RequestHeader
     * @param StartingRecordId
     * @param MaxRecordsToReturn
     * @param ServerCapabilityFilter
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public FindServersOnNetworkResponse FindServersOnNetwork(RequestHeader RequestHeader, UnsignedInteger StartingRecordId, UnsignedInteger MaxRecordsToReturn, String... ServerCapabilityFilter)
    throws ServiceFaultException, ServiceResultException {
    	FindServersOnNetworkRequest req = new FindServersOnNetworkRequest(RequestHeader, StartingRecordId, MaxRecordsToReturn, ServerCapabilityFilter);
    	return (FindServersOnNetworkResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous FindServersOnNetwork service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public FindServersOnNetworkResponse FindServersOnNetwork(FindServersOnNetworkRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (FindServersOnNetworkResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous FindServersOnNetwork service request. 
     * 
     * @param RequestHeader
     * @param StartingRecordId
     * @param MaxRecordsToReturn
     * @param ServerCapabilityFilter
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult FindServersOnNetworkAsync(RequestHeader RequestHeader, UnsignedInteger StartingRecordId, UnsignedInteger MaxRecordsToReturn, String... ServerCapabilityFilter)
    {
    	FindServersOnNetworkRequest req = new FindServersOnNetworkRequest(RequestHeader, StartingRecordId, MaxRecordsToReturn, ServerCapabilityFilter);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous FindServersOnNetwork service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult FindServersOnNetworkAsync(FindServersOnNetworkRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous GetEndpoints service request. 
     * 
     * @param RequestHeader
     * @param EndpointUrl
     * @param LocaleIds
     * @param ProfileUris
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public GetEndpointsResponse GetEndpoints(RequestHeader RequestHeader, String EndpointUrl, String[] LocaleIds, String... ProfileUris)
    throws ServiceFaultException, ServiceResultException {
    	GetEndpointsRequest req = new GetEndpointsRequest(RequestHeader, EndpointUrl, LocaleIds, ProfileUris);
    	return (GetEndpointsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous GetEndpoints service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public GetEndpointsResponse GetEndpoints(GetEndpointsRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (GetEndpointsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous GetEndpoints service request. 
     * 
     * @param RequestHeader
     * @param EndpointUrl
     * @param LocaleIds
     * @param ProfileUris
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult GetEndpointsAsync(RequestHeader RequestHeader, String EndpointUrl, String[] LocaleIds, String... ProfileUris)
    {
    	GetEndpointsRequest req = new GetEndpointsRequest(RequestHeader, EndpointUrl, LocaleIds, ProfileUris);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous GetEndpoints service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult GetEndpointsAsync(GetEndpointsRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous RegisterServer service request. 
     * 
     * @param RequestHeader
     * @param Server
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public RegisterServerResponse RegisterServer(RequestHeader RequestHeader, RegisteredServer Server)
    throws ServiceFaultException, ServiceResultException {
    	RegisterServerRequest req = new RegisterServerRequest(RequestHeader, Server);
    	return (RegisterServerResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous RegisterServer service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public RegisterServerResponse RegisterServer(RegisterServerRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (RegisterServerResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous RegisterServer service request. 
     * 
     * @param RequestHeader
     * @param Server
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult RegisterServerAsync(RequestHeader RequestHeader, RegisteredServer Server)
    {
    	RegisterServerRequest req = new RegisterServerRequest(RequestHeader, Server);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous RegisterServer service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult RegisterServerAsync(RegisterServerRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous RegisterServer2 service request. 
     * 
     * @param RequestHeader
     * @param Server
     * @param DiscoveryConfiguration
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public RegisterServer2Response RegisterServer2(RequestHeader RequestHeader, RegisteredServer Server, ExtensionObject... DiscoveryConfiguration)
    throws ServiceFaultException, ServiceResultException {
    	RegisterServer2Request req = new RegisterServer2Request(RequestHeader, Server, DiscoveryConfiguration);
    	return (RegisterServer2Response) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous RegisterServer2 service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public RegisterServer2Response RegisterServer2(RegisterServer2Request req)
    throws ServiceFaultException, ServiceResultException {
    	return (RegisterServer2Response) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous RegisterServer2 service request. 
     * 
     * @param RequestHeader
     * @param Server
     * @param DiscoveryConfiguration
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult RegisterServer2Async(RequestHeader RequestHeader, RegisteredServer Server, ExtensionObject... DiscoveryConfiguration)
    {
    	RegisterServer2Request req = new RegisterServer2Request(RequestHeader, Server, DiscoveryConfiguration);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous RegisterServer2 service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult RegisterServer2Async(RegisterServer2Request req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous OpenSecureChannel service request. 
     * 
     * @param RequestHeader
     * @param ClientProtocolVersion
     * @param RequestType
     * @param SecurityMode
     * @param ClientNonce
     * @param RequestedLifetime
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public OpenSecureChannelResponse OpenSecureChannel(RequestHeader RequestHeader, UnsignedInteger ClientProtocolVersion, SecurityTokenRequestType RequestType, MessageSecurityMode SecurityMode, ByteString ClientNonce, UnsignedInteger RequestedLifetime)
    throws ServiceFaultException, ServiceResultException {
    	OpenSecureChannelRequest req = new OpenSecureChannelRequest(RequestHeader, ClientProtocolVersion, RequestType, SecurityMode, ClientNonce, RequestedLifetime);
    	return (OpenSecureChannelResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous OpenSecureChannel service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public OpenSecureChannelResponse OpenSecureChannel(OpenSecureChannelRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (OpenSecureChannelResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous OpenSecureChannel service request. 
     * 
     * @param RequestHeader
     * @param ClientProtocolVersion
     * @param RequestType
     * @param SecurityMode
     * @param ClientNonce
     * @param RequestedLifetime
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult OpenSecureChannelAsync(RequestHeader RequestHeader, UnsignedInteger ClientProtocolVersion, SecurityTokenRequestType RequestType, MessageSecurityMode SecurityMode, ByteString ClientNonce, UnsignedInteger RequestedLifetime)
    {
    	OpenSecureChannelRequest req = new OpenSecureChannelRequest(RequestHeader, ClientProtocolVersion, RequestType, SecurityMode, ClientNonce, RequestedLifetime);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous OpenSecureChannel service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult OpenSecureChannelAsync(OpenSecureChannelRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous CloseSecureChannel service request. 
     * 
     * @param RequestHeader
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CloseSecureChannelResponse CloseSecureChannel(RequestHeader RequestHeader)
    throws ServiceFaultException, ServiceResultException {
    	CloseSecureChannelRequest req = new CloseSecureChannelRequest(RequestHeader);
    	return (CloseSecureChannelResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous CloseSecureChannel service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CloseSecureChannelResponse CloseSecureChannel(CloseSecureChannelRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (CloseSecureChannelResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous CloseSecureChannel service request. 
     * 
     * @param RequestHeader
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CloseSecureChannelAsync(RequestHeader RequestHeader)
    {
    	CloseSecureChannelRequest req = new CloseSecureChannelRequest(RequestHeader);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous CloseSecureChannel service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CloseSecureChannelAsync(CloseSecureChannelRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous CreateSession service request. 
     * 
     * @param RequestHeader
     * @param ClientDescription
     * @param ServerUri
     * @param EndpointUrl
     * @param SessionName
     * @param ClientNonce
     * @param ClientCertificate
     * @param RequestedSessionTimeout
     * @param MaxResponseMessageSize
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CreateSessionResponse CreateSession(RequestHeader RequestHeader, ApplicationDescription ClientDescription, String ServerUri, String EndpointUrl, String SessionName, ByteString ClientNonce, ByteString ClientCertificate, Double RequestedSessionTimeout, UnsignedInteger MaxResponseMessageSize)
    throws ServiceFaultException, ServiceResultException {
    	CreateSessionRequest req = new CreateSessionRequest(RequestHeader, ClientDescription, ServerUri, EndpointUrl, SessionName, ClientNonce, ClientCertificate, RequestedSessionTimeout, MaxResponseMessageSize);
    	return (CreateSessionResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous CreateSession service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CreateSessionResponse CreateSession(CreateSessionRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (CreateSessionResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous CreateSession service request. 
     * 
     * @param RequestHeader
     * @param ClientDescription
     * @param ServerUri
     * @param EndpointUrl
     * @param SessionName
     * @param ClientNonce
     * @param ClientCertificate
     * @param RequestedSessionTimeout
     * @param MaxResponseMessageSize
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CreateSessionAsync(RequestHeader RequestHeader, ApplicationDescription ClientDescription, String ServerUri, String EndpointUrl, String SessionName, ByteString ClientNonce, ByteString ClientCertificate, Double RequestedSessionTimeout, UnsignedInteger MaxResponseMessageSize)
    {
    	CreateSessionRequest req = new CreateSessionRequest(RequestHeader, ClientDescription, ServerUri, EndpointUrl, SessionName, ClientNonce, ClientCertificate, RequestedSessionTimeout, MaxResponseMessageSize);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous CreateSession service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CreateSessionAsync(CreateSessionRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous ActivateSession service request. 
     * 
     * @param RequestHeader
     * @param ClientSignature
     * @param ClientSoftwareCertificates
     * @param LocaleIds
     * @param UserIdentityToken
     * @param UserTokenSignature
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public ActivateSessionResponse ActivateSession(RequestHeader RequestHeader, SignatureData ClientSignature, SignedSoftwareCertificate[] ClientSoftwareCertificates, String[] LocaleIds, ExtensionObject UserIdentityToken, SignatureData UserTokenSignature)
    throws ServiceFaultException, ServiceResultException {
    	ActivateSessionRequest req = new ActivateSessionRequest(RequestHeader, ClientSignature, ClientSoftwareCertificates, LocaleIds, UserIdentityToken, UserTokenSignature);
    	return (ActivateSessionResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous ActivateSession service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public ActivateSessionResponse ActivateSession(ActivateSessionRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (ActivateSessionResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous ActivateSession service request. 
     * 
     * @param RequestHeader
     * @param ClientSignature
     * @param ClientSoftwareCertificates
     * @param LocaleIds
     * @param UserIdentityToken
     * @param UserTokenSignature
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult ActivateSessionAsync(RequestHeader RequestHeader, SignatureData ClientSignature, SignedSoftwareCertificate[] ClientSoftwareCertificates, String[] LocaleIds, ExtensionObject UserIdentityToken, SignatureData UserTokenSignature)
    {
    	ActivateSessionRequest req = new ActivateSessionRequest(RequestHeader, ClientSignature, ClientSoftwareCertificates, LocaleIds, UserIdentityToken, UserTokenSignature);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous ActivateSession service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult ActivateSessionAsync(ActivateSessionRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous CloseSession service request. 
     * 
     * @param RequestHeader
     * @param DeleteSubscriptions
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CloseSessionResponse CloseSession(RequestHeader RequestHeader, Boolean DeleteSubscriptions)
    throws ServiceFaultException, ServiceResultException {
    	CloseSessionRequest req = new CloseSessionRequest(RequestHeader, DeleteSubscriptions);
    	return (CloseSessionResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous CloseSession service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CloseSessionResponse CloseSession(CloseSessionRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (CloseSessionResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous CloseSession service request. 
     * 
     * @param RequestHeader
     * @param DeleteSubscriptions
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CloseSessionAsync(RequestHeader RequestHeader, Boolean DeleteSubscriptions)
    {
    	CloseSessionRequest req = new CloseSessionRequest(RequestHeader, DeleteSubscriptions);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous CloseSession service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CloseSessionAsync(CloseSessionRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous Cancel service request. 
     * 
     * @param RequestHeader
     * @param RequestHandle
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CancelResponse Cancel(RequestHeader RequestHeader, UnsignedInteger RequestHandle)
    throws ServiceFaultException, ServiceResultException {
    	CancelRequest req = new CancelRequest(RequestHeader, RequestHandle);
    	return (CancelResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous Cancel service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CancelResponse Cancel(CancelRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (CancelResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous Cancel service request. 
     * 
     * @param RequestHeader
     * @param RequestHandle
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CancelAsync(RequestHeader RequestHeader, UnsignedInteger RequestHandle)
    {
    	CancelRequest req = new CancelRequest(RequestHeader, RequestHandle);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous Cancel service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CancelAsync(CancelRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous AddNodes service request. 
     * 
     * @param RequestHeader
     * @param NodesToAdd
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AddNodesResponse AddNodes(RequestHeader RequestHeader, AddNodesItem... NodesToAdd)
    throws ServiceFaultException, ServiceResultException {
    	AddNodesRequest req = new AddNodesRequest(RequestHeader, NodesToAdd);
    	return (AddNodesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous AddNodes service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AddNodesResponse AddNodes(AddNodesRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (AddNodesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous AddNodes service request. 
     * 
     * @param RequestHeader
     * @param NodesToAdd
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult AddNodesAsync(RequestHeader RequestHeader, AddNodesItem... NodesToAdd)
    {
    	AddNodesRequest req = new AddNodesRequest(RequestHeader, NodesToAdd);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous AddNodes service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult AddNodesAsync(AddNodesRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous AddReferences service request. 
     * 
     * @param RequestHeader
     * @param ReferencesToAdd
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AddReferencesResponse AddReferences(RequestHeader RequestHeader, AddReferencesItem... ReferencesToAdd)
    throws ServiceFaultException, ServiceResultException {
    	AddReferencesRequest req = new AddReferencesRequest(RequestHeader, ReferencesToAdd);
    	return (AddReferencesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous AddReferences service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AddReferencesResponse AddReferences(AddReferencesRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (AddReferencesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous AddReferences service request. 
     * 
     * @param RequestHeader
     * @param ReferencesToAdd
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult AddReferencesAsync(RequestHeader RequestHeader, AddReferencesItem... ReferencesToAdd)
    {
    	AddReferencesRequest req = new AddReferencesRequest(RequestHeader, ReferencesToAdd);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous AddReferences service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult AddReferencesAsync(AddReferencesRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous DeleteNodes service request. 
     * 
     * @param RequestHeader
     * @param NodesToDelete
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public DeleteNodesResponse DeleteNodes(RequestHeader RequestHeader, DeleteNodesItem... NodesToDelete)
    throws ServiceFaultException, ServiceResultException {
    	DeleteNodesRequest req = new DeleteNodesRequest(RequestHeader, NodesToDelete);
    	return (DeleteNodesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous DeleteNodes service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public DeleteNodesResponse DeleteNodes(DeleteNodesRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (DeleteNodesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous DeleteNodes service request. 
     * 
     * @param RequestHeader
     * @param NodesToDelete
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult DeleteNodesAsync(RequestHeader RequestHeader, DeleteNodesItem... NodesToDelete)
    {
    	DeleteNodesRequest req = new DeleteNodesRequest(RequestHeader, NodesToDelete);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous DeleteNodes service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult DeleteNodesAsync(DeleteNodesRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous DeleteReferences service request. 
     * 
     * @param RequestHeader
     * @param ReferencesToDelete
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public DeleteReferencesResponse DeleteReferences(RequestHeader RequestHeader, DeleteReferencesItem... ReferencesToDelete)
    throws ServiceFaultException, ServiceResultException {
    	DeleteReferencesRequest req = new DeleteReferencesRequest(RequestHeader, ReferencesToDelete);
    	return (DeleteReferencesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous DeleteReferences service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public DeleteReferencesResponse DeleteReferences(DeleteReferencesRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (DeleteReferencesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous DeleteReferences service request. 
     * 
     * @param RequestHeader
     * @param ReferencesToDelete
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult DeleteReferencesAsync(RequestHeader RequestHeader, DeleteReferencesItem... ReferencesToDelete)
    {
    	DeleteReferencesRequest req = new DeleteReferencesRequest(RequestHeader, ReferencesToDelete);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous DeleteReferences service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult DeleteReferencesAsync(DeleteReferencesRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous Browse service request. 
     * 
     * @param RequestHeader
     * @param View
     * @param RequestedMaxReferencesPerNode
     * @param NodesToBrowse
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public BrowseResponse Browse(RequestHeader RequestHeader, ViewDescription View, UnsignedInteger RequestedMaxReferencesPerNode, BrowseDescription... NodesToBrowse)
    throws ServiceFaultException, ServiceResultException {
    	BrowseRequest req = new BrowseRequest(RequestHeader, View, RequestedMaxReferencesPerNode, NodesToBrowse);
    	return (BrowseResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous Browse service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public BrowseResponse Browse(BrowseRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (BrowseResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous Browse service request. 
     * 
     * @param RequestHeader
     * @param View
     * @param RequestedMaxReferencesPerNode
     * @param NodesToBrowse
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult BrowseAsync(RequestHeader RequestHeader, ViewDescription View, UnsignedInteger RequestedMaxReferencesPerNode, BrowseDescription... NodesToBrowse)
    {
    	BrowseRequest req = new BrowseRequest(RequestHeader, View, RequestedMaxReferencesPerNode, NodesToBrowse);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous Browse service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult BrowseAsync(BrowseRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous BrowseNext service request. 
     * 
     * @param RequestHeader
     * @param ReleaseContinuationPoints
     * @param ContinuationPoints
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public BrowseNextResponse BrowseNext(RequestHeader RequestHeader, Boolean ReleaseContinuationPoints, ByteString... ContinuationPoints)
    throws ServiceFaultException, ServiceResultException {
    	BrowseNextRequest req = new BrowseNextRequest(RequestHeader, ReleaseContinuationPoints, ContinuationPoints);
    	return (BrowseNextResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous BrowseNext service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public BrowseNextResponse BrowseNext(BrowseNextRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (BrowseNextResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous BrowseNext service request. 
     * 
     * @param RequestHeader
     * @param ReleaseContinuationPoints
     * @param ContinuationPoints
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult BrowseNextAsync(RequestHeader RequestHeader, Boolean ReleaseContinuationPoints, ByteString... ContinuationPoints)
    {
    	BrowseNextRequest req = new BrowseNextRequest(RequestHeader, ReleaseContinuationPoints, ContinuationPoints);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous BrowseNext service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult BrowseNextAsync(BrowseNextRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous TranslateBrowsePathsToNodeIds service request. 
     * 
     * @param RequestHeader
     * @param BrowsePaths
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public TranslateBrowsePathsToNodeIdsResponse TranslateBrowsePathsToNodeIds(RequestHeader RequestHeader, BrowsePath... BrowsePaths)
    throws ServiceFaultException, ServiceResultException {
    	TranslateBrowsePathsToNodeIdsRequest req = new TranslateBrowsePathsToNodeIdsRequest(RequestHeader, BrowsePaths);
    	return (TranslateBrowsePathsToNodeIdsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous TranslateBrowsePathsToNodeIds service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public TranslateBrowsePathsToNodeIdsResponse TranslateBrowsePathsToNodeIds(TranslateBrowsePathsToNodeIdsRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (TranslateBrowsePathsToNodeIdsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous TranslateBrowsePathsToNodeIds service request. 
     * 
     * @param RequestHeader
     * @param BrowsePaths
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult TranslateBrowsePathsToNodeIdsAsync(RequestHeader RequestHeader, BrowsePath... BrowsePaths)
    {
    	TranslateBrowsePathsToNodeIdsRequest req = new TranslateBrowsePathsToNodeIdsRequest(RequestHeader, BrowsePaths);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous TranslateBrowsePathsToNodeIds service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult TranslateBrowsePathsToNodeIdsAsync(TranslateBrowsePathsToNodeIdsRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous RegisterNodes service request. 
     * 
     * @param RequestHeader
     * @param NodesToRegister
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public RegisterNodesResponse RegisterNodes(RequestHeader RequestHeader, NodeId... NodesToRegister)
    throws ServiceFaultException, ServiceResultException {
    	RegisterNodesRequest req = new RegisterNodesRequest(RequestHeader, NodesToRegister);
    	return (RegisterNodesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous RegisterNodes service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public RegisterNodesResponse RegisterNodes(RegisterNodesRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (RegisterNodesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous RegisterNodes service request. 
     * 
     * @param RequestHeader
     * @param NodesToRegister
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult RegisterNodesAsync(RequestHeader RequestHeader, NodeId... NodesToRegister)
    {
    	RegisterNodesRequest req = new RegisterNodesRequest(RequestHeader, NodesToRegister);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous RegisterNodes service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult RegisterNodesAsync(RegisterNodesRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous UnregisterNodes service request. 
     * 
     * @param RequestHeader
     * @param NodesToUnregister
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public UnregisterNodesResponse UnregisterNodes(RequestHeader RequestHeader, NodeId... NodesToUnregister)
    throws ServiceFaultException, ServiceResultException {
    	UnregisterNodesRequest req = new UnregisterNodesRequest(RequestHeader, NodesToUnregister);
    	return (UnregisterNodesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous UnregisterNodes service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public UnregisterNodesResponse UnregisterNodes(UnregisterNodesRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (UnregisterNodesResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous UnregisterNodes service request. 
     * 
     * @param RequestHeader
     * @param NodesToUnregister
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult UnregisterNodesAsync(RequestHeader RequestHeader, NodeId... NodesToUnregister)
    {
    	UnregisterNodesRequest req = new UnregisterNodesRequest(RequestHeader, NodesToUnregister);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous UnregisterNodes service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult UnregisterNodesAsync(UnregisterNodesRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous QueryFirst service request. 
     * 
     * @param RequestHeader
     * @param View
     * @param NodeTypes
     * @param Filter
     * @param MaxDataSetsToReturn
     * @param MaxReferencesToReturn
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public QueryFirstResponse QueryFirst(RequestHeader RequestHeader, ViewDescription View, NodeTypeDescription[] NodeTypes, ContentFilter Filter, UnsignedInteger MaxDataSetsToReturn, UnsignedInteger MaxReferencesToReturn)
    throws ServiceFaultException, ServiceResultException {
    	QueryFirstRequest req = new QueryFirstRequest(RequestHeader, View, NodeTypes, Filter, MaxDataSetsToReturn, MaxReferencesToReturn);
    	return (QueryFirstResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous QueryFirst service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public QueryFirstResponse QueryFirst(QueryFirstRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (QueryFirstResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous QueryFirst service request. 
     * 
     * @param RequestHeader
     * @param View
     * @param NodeTypes
     * @param Filter
     * @param MaxDataSetsToReturn
     * @param MaxReferencesToReturn
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult QueryFirstAsync(RequestHeader RequestHeader, ViewDescription View, NodeTypeDescription[] NodeTypes, ContentFilter Filter, UnsignedInteger MaxDataSetsToReturn, UnsignedInteger MaxReferencesToReturn)
    {
    	QueryFirstRequest req = new QueryFirstRequest(RequestHeader, View, NodeTypes, Filter, MaxDataSetsToReturn, MaxReferencesToReturn);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous QueryFirst service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult QueryFirstAsync(QueryFirstRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous QueryNext service request. 
     * 
     * @param RequestHeader
     * @param ReleaseContinuationPoint
     * @param ContinuationPoint
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public QueryNextResponse QueryNext(RequestHeader RequestHeader, Boolean ReleaseContinuationPoint, ByteString ContinuationPoint)
    throws ServiceFaultException, ServiceResultException {
    	QueryNextRequest req = new QueryNextRequest(RequestHeader, ReleaseContinuationPoint, ContinuationPoint);
    	return (QueryNextResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous QueryNext service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public QueryNextResponse QueryNext(QueryNextRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (QueryNextResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous QueryNext service request. 
     * 
     * @param RequestHeader
     * @param ReleaseContinuationPoint
     * @param ContinuationPoint
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult QueryNextAsync(RequestHeader RequestHeader, Boolean ReleaseContinuationPoint, ByteString ContinuationPoint)
    {
    	QueryNextRequest req = new QueryNextRequest(RequestHeader, ReleaseContinuationPoint, ContinuationPoint);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous QueryNext service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult QueryNextAsync(QueryNextRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous Read service request. 
     * 
     * @param RequestHeader
     * @param MaxAge
     * @param TimestampsToReturn
     * @param NodesToRead
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public ReadResponse Read(RequestHeader RequestHeader, Double MaxAge, TimestampsToReturn TimestampsToReturn, ReadValueId... NodesToRead)
    throws ServiceFaultException, ServiceResultException {
    	ReadRequest req = new ReadRequest(RequestHeader, MaxAge, TimestampsToReturn, NodesToRead);
    	return (ReadResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous Read service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public ReadResponse Read(ReadRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (ReadResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous Read service request. 
     * 
     * @param RequestHeader
     * @param MaxAge
     * @param TimestampsToReturn
     * @param NodesToRead
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult ReadAsync(RequestHeader RequestHeader, Double MaxAge, TimestampsToReturn TimestampsToReturn, ReadValueId... NodesToRead)
    {
    	ReadRequest req = new ReadRequest(RequestHeader, MaxAge, TimestampsToReturn, NodesToRead);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous Read service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult ReadAsync(ReadRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous HistoryRead service request. 
     * 
     * @param RequestHeader
     * @param HistoryReadDetails
     * @param TimestampsToReturn
     * @param ReleaseContinuationPoints
     * @param NodesToRead
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public HistoryReadResponse HistoryRead(RequestHeader RequestHeader, ExtensionObject HistoryReadDetails, TimestampsToReturn TimestampsToReturn, Boolean ReleaseContinuationPoints, HistoryReadValueId... NodesToRead)
    throws ServiceFaultException, ServiceResultException {
    	HistoryReadRequest req = new HistoryReadRequest(RequestHeader, HistoryReadDetails, TimestampsToReturn, ReleaseContinuationPoints, NodesToRead);
    	return (HistoryReadResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous HistoryRead service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public HistoryReadResponse HistoryRead(HistoryReadRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (HistoryReadResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous HistoryRead service request. 
     * 
     * @param RequestHeader
     * @param HistoryReadDetails
     * @param TimestampsToReturn
     * @param ReleaseContinuationPoints
     * @param NodesToRead
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult HistoryReadAsync(RequestHeader RequestHeader, ExtensionObject HistoryReadDetails, TimestampsToReturn TimestampsToReturn, Boolean ReleaseContinuationPoints, HistoryReadValueId... NodesToRead)
    {
    	HistoryReadRequest req = new HistoryReadRequest(RequestHeader, HistoryReadDetails, TimestampsToReturn, ReleaseContinuationPoints, NodesToRead);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous HistoryRead service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult HistoryReadAsync(HistoryReadRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous Write service request. 
     * 
     * @param RequestHeader
     * @param NodesToWrite
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public WriteResponse Write(RequestHeader RequestHeader, WriteValue... NodesToWrite)
    throws ServiceFaultException, ServiceResultException {
    	WriteRequest req = new WriteRequest(RequestHeader, NodesToWrite);
    	return (WriteResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous Write service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public WriteResponse Write(WriteRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (WriteResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous Write service request. 
     * 
     * @param RequestHeader
     * @param NodesToWrite
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult WriteAsync(RequestHeader RequestHeader, WriteValue... NodesToWrite)
    {
    	WriteRequest req = new WriteRequest(RequestHeader, NodesToWrite);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous Write service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult WriteAsync(WriteRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous HistoryUpdate service request. 
     * 
     * @param RequestHeader
     * @param HistoryUpdateDetails
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public HistoryUpdateResponse HistoryUpdate(RequestHeader RequestHeader, ExtensionObject... HistoryUpdateDetails)
    throws ServiceFaultException, ServiceResultException {
    	HistoryUpdateRequest req = new HistoryUpdateRequest(RequestHeader, HistoryUpdateDetails);
    	return (HistoryUpdateResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous HistoryUpdate service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public HistoryUpdateResponse HistoryUpdate(HistoryUpdateRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (HistoryUpdateResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous HistoryUpdate service request. 
     * 
     * @param RequestHeader
     * @param HistoryUpdateDetails
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult HistoryUpdateAsync(RequestHeader RequestHeader, ExtensionObject... HistoryUpdateDetails)
    {
    	HistoryUpdateRequest req = new HistoryUpdateRequest(RequestHeader, HistoryUpdateDetails);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous HistoryUpdate service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult HistoryUpdateAsync(HistoryUpdateRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous Call service request. 
     * 
     * @param RequestHeader
     * @param MethodsToCall
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CallResponse Call(RequestHeader RequestHeader, CallMethodRequest... MethodsToCall)
    throws ServiceFaultException, ServiceResultException {
    	CallRequest req = new CallRequest(RequestHeader, MethodsToCall);
    	return (CallResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous Call service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CallResponse Call(CallRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (CallResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous Call service request. 
     * 
     * @param RequestHeader
     * @param MethodsToCall
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CallAsync(RequestHeader RequestHeader, CallMethodRequest... MethodsToCall)
    {
    	CallRequest req = new CallRequest(RequestHeader, MethodsToCall);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous Call service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CallAsync(CallRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous CreateMonitoredItems service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param TimestampsToReturn
     * @param ItemsToCreate
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CreateMonitoredItemsResponse CreateMonitoredItems(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, TimestampsToReturn TimestampsToReturn, MonitoredItemCreateRequest... ItemsToCreate)
    throws ServiceFaultException, ServiceResultException {
    	CreateMonitoredItemsRequest req = new CreateMonitoredItemsRequest(RequestHeader, SubscriptionId, TimestampsToReturn, ItemsToCreate);
    	return (CreateMonitoredItemsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous CreateMonitoredItems service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CreateMonitoredItemsResponse CreateMonitoredItems(CreateMonitoredItemsRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (CreateMonitoredItemsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous CreateMonitoredItems service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param TimestampsToReturn
     * @param ItemsToCreate
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CreateMonitoredItemsAsync(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, TimestampsToReturn TimestampsToReturn, MonitoredItemCreateRequest... ItemsToCreate)
    {
    	CreateMonitoredItemsRequest req = new CreateMonitoredItemsRequest(RequestHeader, SubscriptionId, TimestampsToReturn, ItemsToCreate);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous CreateMonitoredItems service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CreateMonitoredItemsAsync(CreateMonitoredItemsRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous ModifyMonitoredItems service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param TimestampsToReturn
     * @param ItemsToModify
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public ModifyMonitoredItemsResponse ModifyMonitoredItems(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, TimestampsToReturn TimestampsToReturn, MonitoredItemModifyRequest... ItemsToModify)
    throws ServiceFaultException, ServiceResultException {
    	ModifyMonitoredItemsRequest req = new ModifyMonitoredItemsRequest(RequestHeader, SubscriptionId, TimestampsToReturn, ItemsToModify);
    	return (ModifyMonitoredItemsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous ModifyMonitoredItems service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public ModifyMonitoredItemsResponse ModifyMonitoredItems(ModifyMonitoredItemsRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (ModifyMonitoredItemsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous ModifyMonitoredItems service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param TimestampsToReturn
     * @param ItemsToModify
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult ModifyMonitoredItemsAsync(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, TimestampsToReturn TimestampsToReturn, MonitoredItemModifyRequest... ItemsToModify)
    {
    	ModifyMonitoredItemsRequest req = new ModifyMonitoredItemsRequest(RequestHeader, SubscriptionId, TimestampsToReturn, ItemsToModify);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous ModifyMonitoredItems service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult ModifyMonitoredItemsAsync(ModifyMonitoredItemsRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous SetMonitoringMode service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param MonitoringMode
     * @param MonitoredItemIds
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public SetMonitoringModeResponse SetMonitoringMode(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, MonitoringMode MonitoringMode, UnsignedInteger... MonitoredItemIds)
    throws ServiceFaultException, ServiceResultException {
    	SetMonitoringModeRequest req = new SetMonitoringModeRequest(RequestHeader, SubscriptionId, MonitoringMode, MonitoredItemIds);
    	return (SetMonitoringModeResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous SetMonitoringMode service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public SetMonitoringModeResponse SetMonitoringMode(SetMonitoringModeRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (SetMonitoringModeResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous SetMonitoringMode service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param MonitoringMode
     * @param MonitoredItemIds
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult SetMonitoringModeAsync(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, MonitoringMode MonitoringMode, UnsignedInteger... MonitoredItemIds)
    {
    	SetMonitoringModeRequest req = new SetMonitoringModeRequest(RequestHeader, SubscriptionId, MonitoringMode, MonitoredItemIds);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous SetMonitoringMode service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult SetMonitoringModeAsync(SetMonitoringModeRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous SetTriggering service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param TriggeringItemId
     * @param LinksToAdd
     * @param LinksToRemove
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public SetTriggeringResponse SetTriggering(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, UnsignedInteger TriggeringItemId, UnsignedInteger[] LinksToAdd, UnsignedInteger... LinksToRemove)
    throws ServiceFaultException, ServiceResultException {
    	SetTriggeringRequest req = new SetTriggeringRequest(RequestHeader, SubscriptionId, TriggeringItemId, LinksToAdd, LinksToRemove);
    	return (SetTriggeringResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous SetTriggering service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public SetTriggeringResponse SetTriggering(SetTriggeringRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (SetTriggeringResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous SetTriggering service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param TriggeringItemId
     * @param LinksToAdd
     * @param LinksToRemove
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult SetTriggeringAsync(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, UnsignedInteger TriggeringItemId, UnsignedInteger[] LinksToAdd, UnsignedInteger... LinksToRemove)
    {
    	SetTriggeringRequest req = new SetTriggeringRequest(RequestHeader, SubscriptionId, TriggeringItemId, LinksToAdd, LinksToRemove);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous SetTriggering service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult SetTriggeringAsync(SetTriggeringRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous DeleteMonitoredItems service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param MonitoredItemIds
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public DeleteMonitoredItemsResponse DeleteMonitoredItems(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, UnsignedInteger... MonitoredItemIds)
    throws ServiceFaultException, ServiceResultException {
    	DeleteMonitoredItemsRequest req = new DeleteMonitoredItemsRequest(RequestHeader, SubscriptionId, MonitoredItemIds);
    	return (DeleteMonitoredItemsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous DeleteMonitoredItems service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public DeleteMonitoredItemsResponse DeleteMonitoredItems(DeleteMonitoredItemsRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (DeleteMonitoredItemsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous DeleteMonitoredItems service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param MonitoredItemIds
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult DeleteMonitoredItemsAsync(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, UnsignedInteger... MonitoredItemIds)
    {
    	DeleteMonitoredItemsRequest req = new DeleteMonitoredItemsRequest(RequestHeader, SubscriptionId, MonitoredItemIds);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous DeleteMonitoredItems service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult DeleteMonitoredItemsAsync(DeleteMonitoredItemsRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous CreateSubscription service request. 
     * 
     * @param RequestHeader
     * @param RequestedPublishingInterval
     * @param RequestedLifetimeCount
     * @param RequestedMaxKeepAliveCount
     * @param MaxNotificationsPerPublish
     * @param PublishingEnabled
     * @param Priority
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CreateSubscriptionResponse CreateSubscription(RequestHeader RequestHeader, Double RequestedPublishingInterval, UnsignedInteger RequestedLifetimeCount, UnsignedInteger RequestedMaxKeepAliveCount, UnsignedInteger MaxNotificationsPerPublish, Boolean PublishingEnabled, UnsignedByte Priority)
    throws ServiceFaultException, ServiceResultException {
    	CreateSubscriptionRequest req = new CreateSubscriptionRequest(RequestHeader, RequestedPublishingInterval, RequestedLifetimeCount, RequestedMaxKeepAliveCount, MaxNotificationsPerPublish, PublishingEnabled, Priority);
    	return (CreateSubscriptionResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous CreateSubscription service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public CreateSubscriptionResponse CreateSubscription(CreateSubscriptionRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (CreateSubscriptionResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous CreateSubscription service request. 
     * 
     * @param RequestHeader
     * @param RequestedPublishingInterval
     * @param RequestedLifetimeCount
     * @param RequestedMaxKeepAliveCount
     * @param MaxNotificationsPerPublish
     * @param PublishingEnabled
     * @param Priority
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CreateSubscriptionAsync(RequestHeader RequestHeader, Double RequestedPublishingInterval, UnsignedInteger RequestedLifetimeCount, UnsignedInteger RequestedMaxKeepAliveCount, UnsignedInteger MaxNotificationsPerPublish, Boolean PublishingEnabled, UnsignedByte Priority)
    {
    	CreateSubscriptionRequest req = new CreateSubscriptionRequest(RequestHeader, RequestedPublishingInterval, RequestedLifetimeCount, RequestedMaxKeepAliveCount, MaxNotificationsPerPublish, PublishingEnabled, Priority);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous CreateSubscription service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult CreateSubscriptionAsync(CreateSubscriptionRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous ModifySubscription service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param RequestedPublishingInterval
     * @param RequestedLifetimeCount
     * @param RequestedMaxKeepAliveCount
     * @param MaxNotificationsPerPublish
     * @param Priority
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public ModifySubscriptionResponse ModifySubscription(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, Double RequestedPublishingInterval, UnsignedInteger RequestedLifetimeCount, UnsignedInteger RequestedMaxKeepAliveCount, UnsignedInteger MaxNotificationsPerPublish, UnsignedByte Priority)
    throws ServiceFaultException, ServiceResultException {
    	ModifySubscriptionRequest req = new ModifySubscriptionRequest(RequestHeader, SubscriptionId, RequestedPublishingInterval, RequestedLifetimeCount, RequestedMaxKeepAliveCount, MaxNotificationsPerPublish, Priority);
    	return (ModifySubscriptionResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous ModifySubscription service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public ModifySubscriptionResponse ModifySubscription(ModifySubscriptionRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (ModifySubscriptionResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous ModifySubscription service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param RequestedPublishingInterval
     * @param RequestedLifetimeCount
     * @param RequestedMaxKeepAliveCount
     * @param MaxNotificationsPerPublish
     * @param Priority
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult ModifySubscriptionAsync(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, Double RequestedPublishingInterval, UnsignedInteger RequestedLifetimeCount, UnsignedInteger RequestedMaxKeepAliveCount, UnsignedInteger MaxNotificationsPerPublish, UnsignedByte Priority)
    {
    	ModifySubscriptionRequest req = new ModifySubscriptionRequest(RequestHeader, SubscriptionId, RequestedPublishingInterval, RequestedLifetimeCount, RequestedMaxKeepAliveCount, MaxNotificationsPerPublish, Priority);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous ModifySubscription service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult ModifySubscriptionAsync(ModifySubscriptionRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous SetPublishingMode service request. 
     * 
     * @param RequestHeader
     * @param PublishingEnabled
     * @param SubscriptionIds
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public SetPublishingModeResponse SetPublishingMode(RequestHeader RequestHeader, Boolean PublishingEnabled, UnsignedInteger... SubscriptionIds)
    throws ServiceFaultException, ServiceResultException {
    	SetPublishingModeRequest req = new SetPublishingModeRequest(RequestHeader, PublishingEnabled, SubscriptionIds);
    	return (SetPublishingModeResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous SetPublishingMode service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public SetPublishingModeResponse SetPublishingMode(SetPublishingModeRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (SetPublishingModeResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous SetPublishingMode service request. 
     * 
     * @param RequestHeader
     * @param PublishingEnabled
     * @param SubscriptionIds
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult SetPublishingModeAsync(RequestHeader RequestHeader, Boolean PublishingEnabled, UnsignedInteger... SubscriptionIds)
    {
    	SetPublishingModeRequest req = new SetPublishingModeRequest(RequestHeader, PublishingEnabled, SubscriptionIds);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous SetPublishingMode service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult SetPublishingModeAsync(SetPublishingModeRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous Publish service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionAcknowledgements
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public PublishResponse Publish(RequestHeader RequestHeader, SubscriptionAcknowledgement... SubscriptionAcknowledgements)
    throws ServiceFaultException, ServiceResultException {
    	PublishRequest req = new PublishRequest(RequestHeader, SubscriptionAcknowledgements);
    	return (PublishResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous Publish service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public PublishResponse Publish(PublishRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (PublishResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous Publish service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionAcknowledgements
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult PublishAsync(RequestHeader RequestHeader, SubscriptionAcknowledgement... SubscriptionAcknowledgements)
    {
    	PublishRequest req = new PublishRequest(RequestHeader, SubscriptionAcknowledgements);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous Publish service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult PublishAsync(PublishRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous Republish service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param RetransmitSequenceNumber
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public RepublishResponse Republish(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, UnsignedInteger RetransmitSequenceNumber)
    throws ServiceFaultException, ServiceResultException {
    	RepublishRequest req = new RepublishRequest(RequestHeader, SubscriptionId, RetransmitSequenceNumber);
    	return (RepublishResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous Republish service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public RepublishResponse Republish(RepublishRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (RepublishResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous Republish service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionId
     * @param RetransmitSequenceNumber
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult RepublishAsync(RequestHeader RequestHeader, UnsignedInteger SubscriptionId, UnsignedInteger RetransmitSequenceNumber)
    {
    	RepublishRequest req = new RepublishRequest(RequestHeader, SubscriptionId, RetransmitSequenceNumber);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous Republish service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult RepublishAsync(RepublishRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous TransferSubscriptions service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionIds
     * @param SendInitialValues
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public TransferSubscriptionsResponse TransferSubscriptions(RequestHeader RequestHeader, UnsignedInteger[] SubscriptionIds, Boolean SendInitialValues)
    throws ServiceFaultException, ServiceResultException {
    	TransferSubscriptionsRequest req = new TransferSubscriptionsRequest(RequestHeader, SubscriptionIds, SendInitialValues);
    	return (TransferSubscriptionsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous TransferSubscriptions service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public TransferSubscriptionsResponse TransferSubscriptions(TransferSubscriptionsRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (TransferSubscriptionsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous TransferSubscriptions service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionIds
     * @param SendInitialValues
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult TransferSubscriptionsAsync(RequestHeader RequestHeader, UnsignedInteger[] SubscriptionIds, Boolean SendInitialValues)
    {
    	TransferSubscriptionsRequest req = new TransferSubscriptionsRequest(RequestHeader, SubscriptionIds, SendInitialValues);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous TransferSubscriptions service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult TransferSubscriptionsAsync(TransferSubscriptionsRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Synchronous DeleteSubscriptions service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionIds
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public DeleteSubscriptionsResponse DeleteSubscriptions(RequestHeader RequestHeader, UnsignedInteger... SubscriptionIds)
    throws ServiceFaultException, ServiceResultException {
    	DeleteSubscriptionsRequest req = new DeleteSubscriptionsRequest(RequestHeader, SubscriptionIds);
    	return (DeleteSubscriptionsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Synchronous DeleteSubscriptions service request. 
     * 
     * @param req the request
     * @return the response
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public DeleteSubscriptionsResponse DeleteSubscriptions(DeleteSubscriptionsRequest req)
    throws ServiceFaultException, ServiceResultException {
    	return (DeleteSubscriptionsResponse) channel.serviceRequest( req );
    }
    
    /**
     * Asynchronous DeleteSubscriptions service request. 
     * 
     * @param RequestHeader
     * @param SubscriptionIds
     * @return monitorable asyncronous result object
     * @throws ServiceFaultException on error while executing the operation
     * @throws ServiceResultException on communication error
     */
    public AsyncResult DeleteSubscriptionsAsync(RequestHeader RequestHeader, UnsignedInteger... SubscriptionIds)
    {
    	DeleteSubscriptionsRequest req = new DeleteSubscriptionsRequest(RequestHeader, SubscriptionIds);
    	return channel.serviceRequestAsync( req );
    }
    
    /**
     * Asynchronous DeleteSubscriptions service request. 
     * 
     * @param req the request
     * @param listener listener that receives either an error or the result
     * @return monitorable asyncronous result object
     * @throws ServiceResultException on communication error
     */
    public AsyncResult DeleteSubscriptionsAsync(DeleteSubscriptionsRequest req)
    {
    	return channel.serviceRequestAsync( req );
    }
    
 

}
