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

import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;
import org.opcfoundation.ua.common.ServiceFaultException;


public interface NodeManagementServiceSetHandler {
    
    void onAddNodes(EndpointServiceRequest<AddNodesRequest, AddNodesResponse> req) throws ServiceFaultException;
    
    void onAddReferences(EndpointServiceRequest<AddReferencesRequest, AddReferencesResponse> req) throws ServiceFaultException;
    
    void onDeleteNodes(EndpointServiceRequest<DeleteNodesRequest, DeleteNodesResponse> req) throws ServiceFaultException;
    
    void onDeleteReferences(EndpointServiceRequest<DeleteReferencesRequest, DeleteReferencesResponse> req) throws ServiceFaultException;
    
    void onBrowse(EndpointServiceRequest<BrowseRequest, BrowseResponse> req) throws ServiceFaultException;
    
    void onBrowseNext(EndpointServiceRequest<BrowseNextRequest, BrowseNextResponse> req) throws ServiceFaultException;
    
    void onTranslateBrowsePathsToNodeIds(EndpointServiceRequest<TranslateBrowsePathsToNodeIdsRequest, TranslateBrowsePathsToNodeIdsResponse> req) throws ServiceFaultException;
    
    void onRegisterNodes(EndpointServiceRequest<RegisterNodesRequest, RegisterNodesResponse> req) throws ServiceFaultException;
    
    void onUnregisterNodes(EndpointServiceRequest<UnregisterNodesRequest, UnregisterNodesResponse> req) throws ServiceFaultException;
    
    void onQueryFirst(EndpointServiceRequest<QueryFirstRequest, QueryFirstResponse> req) throws ServiceFaultException;
    
    void onQueryNext(EndpointServiceRequest<QueryNextRequest, QueryNextResponse> req) throws ServiceFaultException;
 
}
