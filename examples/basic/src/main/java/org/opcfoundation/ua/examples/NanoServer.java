/*
 * ======================================================================== Copyright (c) 2005-2015
 * The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 *
 * The complete license agreement can be found here: http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================
 */

package org.opcfoundation.ua.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.opcfoundation.ua.application.Application;
import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.builtintypes.ByteString;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.QualifiedName;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.AccessLevel;
import org.opcfoundation.ua.core.ActivateSessionRequest;
import org.opcfoundation.ua.core.ActivateSessionResponse;
import org.opcfoundation.ua.core.AddNodesRequest;
import org.opcfoundation.ua.core.AddNodesResponse;
import org.opcfoundation.ua.core.AddReferencesRequest;
import org.opcfoundation.ua.core.AddReferencesResponse;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.ApplicationType;
import org.opcfoundation.ua.core.AttributeServiceSetHandler;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.BrowseDescription;
import org.opcfoundation.ua.core.BrowseDirection;
import org.opcfoundation.ua.core.BrowseNextRequest;
import org.opcfoundation.ua.core.BrowseNextResponse;
import org.opcfoundation.ua.core.BrowsePath;
import org.opcfoundation.ua.core.BrowsePathResult;
import org.opcfoundation.ua.core.BrowsePathTarget;
import org.opcfoundation.ua.core.BrowseRequest;
import org.opcfoundation.ua.core.BrowseResponse;
import org.opcfoundation.ua.core.BrowseResult;
import org.opcfoundation.ua.core.CancelRequest;
import org.opcfoundation.ua.core.CancelResponse;
import org.opcfoundation.ua.core.CloseSessionRequest;
import org.opcfoundation.ua.core.CloseSessionResponse;
import org.opcfoundation.ua.core.CreateSessionRequest;
import org.opcfoundation.ua.core.CreateSessionResponse;
import org.opcfoundation.ua.core.DeleteNodesRequest;
import org.opcfoundation.ua.core.DeleteNodesResponse;
import org.opcfoundation.ua.core.DeleteReferencesRequest;
import org.opcfoundation.ua.core.DeleteReferencesResponse;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.FindServersRequest;
import org.opcfoundation.ua.core.FindServersResponse;
import org.opcfoundation.ua.core.HistoryReadRequest;
import org.opcfoundation.ua.core.HistoryReadResponse;
import org.opcfoundation.ua.core.HistoryUpdateRequest;
import org.opcfoundation.ua.core.HistoryUpdateResponse;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.NodeClass;
import org.opcfoundation.ua.core.NodeManagementServiceSetHandler;
import org.opcfoundation.ua.core.QueryFirstRequest;
import org.opcfoundation.ua.core.QueryFirstResponse;
import org.opcfoundation.ua.core.QueryNextRequest;
import org.opcfoundation.ua.core.QueryNextResponse;
import org.opcfoundation.ua.core.ReadRequest;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.ReferenceDescription;
import org.opcfoundation.ua.core.RegisterNodesRequest;
import org.opcfoundation.ua.core.RegisterNodesResponse;
import org.opcfoundation.ua.core.RelativePath;
import org.opcfoundation.ua.core.RelativePathElement;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.ResponseHeader;
import org.opcfoundation.ua.core.ServiceFault;
import org.opcfoundation.ua.core.SessionServiceSetHandler;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.core.TranslateBrowsePathsToNodeIdsRequest;
import org.opcfoundation.ua.core.TranslateBrowsePathsToNodeIdsResponse;
import org.opcfoundation.ua.core.UnregisterNodesRequest;
import org.opcfoundation.ua.core.UnregisterNodesResponse;
import org.opcfoundation.ua.core.UserIdentityToken;
import org.opcfoundation.ua.core.UserNameIdentityToken;
import org.opcfoundation.ua.core.UserTokenPolicy;
import org.opcfoundation.ua.core.WriteRequest;
import org.opcfoundation.ua.core.WriteResponse;
import org.opcfoundation.ua.core.WriteValue;
import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.examples.certs.ExampleKeys;
import org.opcfoundation.ua.transport.Endpoint;
import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;
import org.opcfoundation.ua.transport.security.BcCryptoProvider;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.HttpsSecurityPolicy;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityAlgorithm;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.transport.security.SecurityPolicy;
import org.opcfoundation.ua.utils.CertificateUtils;
import org.opcfoundation.ua.utils.CryptoUtil;
import org.opcfoundation.ua.utils.EndpointUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Server example. This server is intended to conform to Nano Embedded Device Server Profile.
 */
public class NanoServer {

  /**
   * Class to represent ContinuationPoint. NanoServer supports one continuation point at a time.
   */
  static class ContinuationPoint {
    private UnsignedInteger requestedMaxReferencesPerNode;

    private ReferenceDescription[] referenceDescriptions;

    private NodeId authenticationToken;

    private ByteString currentContinuationPoint;

    public ContinuationPoint(UnsignedInteger requestedMaxReferencesPerNode,
        ReferenceDescription[] referenceDescriptions, NodeId authenticationToken, ByteString currentContinuationPoint) {
      this.requestedMaxReferencesPerNode = requestedMaxReferencesPerNode;
      this.referenceDescriptions = referenceDescriptions;
      this.authenticationToken = authenticationToken;
      this.currentContinuationPoint = currentContinuationPoint;
    }

    /**
     * @return the authenticationToken
     */
    public NodeId getAuthenticationToken() {
      return authenticationToken;
    }

    /**
     * @return the currentContinuationPoint
     */
    public ByteString getCurrentContinuationPoint() {
      return currentContinuationPoint;
    }

    /**
     * @return those references that belong to next BrowseNext response
     * @param continuationPointRequested identify current continuation points
     */
    public BrowseResult getNextReferencesDescriptions(ByteString continuationPointRequested) {
      // ByteString continuationPointRequested may be used to identify
      // different continuation points
      ArrayList<ReferenceDescription> referenceDescriptionsToReturn = new ArrayList<ReferenceDescription>();
      ArrayList<ReferenceDescription> originalReferenceDescriptions =
          new ArrayList<ReferenceDescription>(Arrays.asList(referenceDescriptions));

      int length =
          Math.min(continuationPoint.getRequestedMaxReferencesPerNode().intValue(), referenceDescriptions.length);
      // return only certain amount of references
      referenceDescriptionsToReturn.addAll(originalReferenceDescriptions.subList(0, length));
      // Remove these references from this ContinuationPoint
      originalReferenceDescriptions.subList(0, length).clear();
      referenceDescriptions =
          originalReferenceDescriptions.toArray(new ReferenceDescription[originalReferenceDescriptions.size()]);
      // return referenceDescriptionsToReturn;
      if (referenceDescriptions.length > 0) {
        this.currentContinuationPoint =
            ByteString.valueOf(new byte[] {(byte) (continuationPointRequested.getValue()[0] + (byte) 1)});
        return new BrowseResult(StatusCode.GOOD, currentContinuationPoint,
            referenceDescriptionsToReturn.toArray(new ReferenceDescription[referenceDescriptionsToReturn.size()]));
      }
      // if no references are left, then do not return continuationPoint
      // anymore
      return new BrowseResult(StatusCode.GOOD, null,
          referenceDescriptionsToReturn.toArray(new ReferenceDescription[referenceDescriptionsToReturn.size()]));
    }

    public ReferenceDescription[] getReferenceDescriptions() {
      return this.referenceDescriptions;
    }

    public UnsignedInteger getRequestedMaxReferencesPerNode() {
      return this.requestedMaxReferencesPerNode;
    }

    /**
     * @param authenticationToken the authenticationToken to set
     */
    public void setAuthenticationToken(NodeId authenticationToken) {
      this.authenticationToken = authenticationToken;
    }

    /**
     * @param currentContinuationPoint the currentContinuationPoint to set
     */
    public void setCurrentContinuationPoint(ByteString currentContinuationPoint) {
      this.currentContinuationPoint = currentContinuationPoint;
    }

    public void setReferenceDescriptions(ReferenceDescription[] referenceDescriptions) {
      this.referenceDescriptions = referenceDescriptions;
    }

    public void setRequestedMaxReferencesPerNode(UnsignedInteger requestedMaxReferencesPerNode) {
      this.requestedMaxReferencesPerNode = requestedMaxReferencesPerNode;
    }
  }

  /**
   * This service handler contains only one method: onFindServers
   */
  static class FindServersServiceHandler {

    /**
     * FindServers Service
     * 
     * @param req EndpointServiceRequest
     * @throws ServiceFaultException
     */
    public void onFindServers(EndpointServiceRequest<FindServersRequest, FindServersResponse> req)
        throws ServiceFaultException {

      FindServersRequest request = req.getRequest();

      ApplicationDescription[] servers = new ApplicationDescription[1];

      Application application = nanoServer.getApplication();
      String applicationUri = application.getApplicationUri();
      String productUri = application.getProductUri();
      ApplicationDescription applicationDescription = application.getApplicationDescription();
      LocalizedText applicationName = applicationDescription.getApplicationName();
      ApplicationType applicationType = applicationDescription.getApplicationType();
      String gatewayServerUri = null;
      String discoveryProfileUri = null;
      String[] discoveryUrls = applicationDescription.getDiscoveryUrls();
      if (discoveryUrls == null) {
        // Specify default URLs for the DiscoveryServer if
        // getDiscoveryUrls() returned null.
        Endpoint[] discoveryEndpoints = nanoServer.getEndpoints();
        discoveryUrls = new String[discoveryEndpoints.length];
        for (int i = 0; i < discoveryEndpoints.length; i++) {
          discoveryUrls[i] = discoveryEndpoints[i].getEndpointUrl();
        }
      }
      servers[0] = new ApplicationDescription(applicationUri, productUri, applicationName, applicationType,
          gatewayServerUri, discoveryProfileUri, discoveryUrls);

      ResponseHeader header = new ResponseHeader(DateTime.currentTime(), request.getRequestHeader().getRequestHandle(),
          StatusCode.GOOD, null, null, null);

      FindServersResponse response = new FindServersResponse(header, servers);

      req.sendResponse(response);
    }
  }

  static class MyAttributeServiceHandler implements AttributeServiceSetHandler {

    @Override
    public void onHistoryRead(EndpointServiceRequest<HistoryReadRequest, HistoryReadResponse> req)
        throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    @Override
    public void onHistoryUpdate(EndpointServiceRequest<HistoryUpdateRequest, HistoryUpdateResponse> req)
        throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    /**
     * Handle read request.
     */
    @Override
    public void onRead(EndpointServiceRequest<ReadRequest, ReadResponse> req) throws ServiceFaultException {
      ReadRequest request = req.getRequest();
      ReadValueId[] nodesToRead = request.getNodesToRead();

      DataValue[] results = null;
      ReadResponse response = null;
      ResponseHeader h = checkRequestHeader(request.getRequestHeader());
      if (h.getServiceResult().isGood()) {

        if (request.getMaxAge() < 0) {
          h = new ResponseHeader(DateTime.currentTime(), request.getRequestHeader().getRequestHandle(),
              new StatusCode(StatusCodes.Bad_MaxAgeInvalid), null, null, null);
        } else if (request.getTimestampsToReturn() == null) {
          h = new ResponseHeader(DateTime.currentTime(), request.getRequestHeader().getRequestHandle(),
              new StatusCode(StatusCodes.Bad_TimestampsToReturnInvalid), null, null, null);
        } else if (nodesToRead != null) {
          // Do actual handling of NodesToRead

          results = new DataValue[nodesToRead.length];
          DateTime serverTimestamp = DateTime.currentTime();
          for (int i = 0; i < nodesToRead.length; i++) {
            results[i] = null;
            Map<UnsignedInteger, DataValue> attributeMap = onReadResultsMap.get(nodesToRead[i].getNodeId());

            if (attributeMap != null) {

              if (attributeMap.containsKey(nodesToRead[i].getAttributeId())) {
                results[i] = (DataValue) attributeMap.get(nodesToRead[i].getAttributeId()).clone();

                if (results[i] == null) {
                  results[i] = new DataValue(new StatusCode(StatusCodes.Bad_AttributeIdInvalid));
                } else if (new UnsignedInteger(13).equals(nodesToRead[i].getAttributeId())) {
                  // check maxAge
                  DateTime currentTimestamp = results[i].getServerTimestamp();
                  DateTime currentTime = DateTime.fromMillis(System.currentTimeMillis());
                  long age = currentTime.getTimeInMillis() - currentTimestamp.getTimeInMillis();
                  long maxAge = request.getMaxAge().longValue();
                  long diff = maxAge - age;
                  // If the server does not have a value that
                  // is within the maximum age, it shall
                  // attempt to read a new value from the data
                  // source.
                  // If maxAge is set to 0, the server shall
                  // attempt to read a new value from the data
                  // source.
                  if (diff <= 0) {
                    // read new value, simulated here by
                    // refreshing timestamp
                    results[i].setServerTimestamp(serverTimestamp);
                  }
                  /*
                   * This could also be checked here but it is now ignored: If maxAge is set to the
                   * max Int32 value or greater, the server shall attempt to get a cached value.
                   */

                  if (request.getTimestampsToReturn() != null) {
                    // check TimestampsToReturn
                    switch (request.getTimestampsToReturn()) {
                      case Source:
                        results[i].setSourceTimestamp(serverTimestamp);
                        results[i].setServerTimestamp(null);
                        break;
                      case Both:
                        results[i].setSourceTimestamp(serverTimestamp);
                        break;
                      case Neither:
                        results[i].setServerTimestamp(null);
                        break;
                      default:
                        // case Server
                        break;
                    }
                  }

                }
              } else {
                results[i] = new DataValue(new StatusCode(StatusCodes.Bad_AttributeIdInvalid));
              }
            } else {
              results[i] = new DataValue(new StatusCode(StatusCodes.Bad_NodeIdUnknown));
            }
          }
        } else {
          // NodesToRead is empty
          h = new ResponseHeader(DateTime.currentTime(), request.getRequestHeader().getRequestHandle(),
              new StatusCode(StatusCodes.Bad_NothingToDo), null, null, null);
        }
      }
      response = new ReadResponse(null, results, null);

      response.setResponseHeader(h);
      req.sendResponse(response);
    }

    /**
     * Handle write request.
     */
    @Override
    public void onWrite(EndpointServiceRequest<WriteRequest, WriteResponse> req) throws ServiceFaultException {

      WriteRequest request = req.getRequest();
      WriteValue[] nodesToWrite = request.getNodesToWrite();
      StatusCode[] results = null;
      StatusCode serviceResultCode = null;

      if (nodesToWrite != null) {
        // check here that Bad_TooManyOperations should not be set. No
        // limit for operations in this implementation.
        // Now set service result to GOOD always if nodesToWrite is not
        // null.
        serviceResultCode = StatusCode.GOOD;

        results = new StatusCode[nodesToWrite.length];
        for (int i = 0; i < nodesToWrite.length; i++) {
          // Get all attributes of the specified node
          Map<UnsignedInteger, DataValue> attributeMap = onReadResultsMap.get(nodesToWrite[i].getNodeId());

          if (attributeMap != null) {
            if (attributeMap.containsKey(nodesToWrite[i].getAttributeId())) {

              if (new UnsignedInteger(13).equals(nodesToWrite[i].getAttributeId())) {
                // Write value attribute
                // Check data type using nodes DataType
                // attribute
                // Validation is done with datatypeMap to enable
                // easy modification of valid data types
                NodeId datatype = (NodeId) attributeMap.get(Attributes.DataType).getValue().getValue();
                if (datatype == null) {
                  // Error: Current node does not have data
                  // type specified
                  results[i] = new StatusCode(StatusCodes.Bad_TypeMismatch);
                } else {
                  // Data type is defined for current node
                  // Get java class corresponding to this OPC
                  // UA data type
                  Class<?> targetDataType = datatypeMap.get(datatype);
                  if (targetDataType == null) {
                    // No java data type found for this ua
                    // type
                    results[i] = new StatusCode(StatusCodes.Bad_TypeMismatch);
                  } else {
                    // Compare data type of value attribute
                    // and value from write request
                    if (targetDataType.isAssignableFrom(nodesToWrite[i].getValue().getValue().getValue().getClass())) {
                      attributeMap.get(nodesToWrite[i].getAttributeId())
                          .setValue(nodesToWrite[i].getValue().getValue());
                      results[i] = StatusCode.GOOD;
                    } else {
                      // values do not match
                      results[i] = new StatusCode(StatusCodes.Bad_TypeMismatch);
                    }
                  }
                }
              } else {
                // Write no other attribute than value.
                // Correct data type should also be checked
                // here.
                attributeMap.get(nodesToWrite[i].getAttributeId()).setValue(nodesToWrite[i].getValue().getValue());
              }
            } else {
              results[i] = new StatusCode(StatusCodes.Bad_AttributeIdInvalid);
            }
          } else {
            results[i] = new StatusCode(StatusCodes.Bad_NodeIdInvalid);
          }
        }
      } else {
        // Empty nodesToWrite array
        serviceResultCode = new StatusCode(StatusCodes.Bad_NothingToDo);
      }
      WriteResponse response = new WriteResponse(null, results, null);
      // Set response header to pass ctt check_responseHeader_error.js
      ResponseHeader h = new ResponseHeader(DateTime.currentTime(), request.getRequestHeader().getRequestHandle(),
          serviceResultCode, null, null, null);
      response.setResponseHeader(h);

      req.sendResponse(response);
    }

  }

  static class MyNodeManagementServiceHandler implements NodeManagementServiceSetHandler {

    @Override
    public void onAddNodes(EndpointServiceRequest<AddNodesRequest, AddNodesResponse> req) throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    @Override
    public void onAddReferences(EndpointServiceRequest<AddReferencesRequest, AddReferencesResponse> req)
        throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    @Override
    public void onBrowse(EndpointServiceRequest<BrowseRequest, BrowseResponse> req) throws ServiceFaultException {
      BrowseRequest request = req.getRequest();

      List<BrowseResult> browseResults = new ArrayList<BrowseResult>();

      ResponseHeader h = checkRequestHeader(request.getRequestHeader());

      // If ViewId is not null, Bad_ViewIdUnknown is always returned.
      if (request.getView() != null) {
        if (!NodeId.isNull(request.getView().getViewId())) {
          h = new ResponseHeader(DateTime.currentTime(), request.getRequestHeader().getRequestHandle(),
              new StatusCode(StatusCodes.Bad_ViewIdUnknown), null, null, null);
        }
      }

      BrowseDescription[] nodesToBrowse = request.getNodesToBrowse();
      if (nodesToBrowse != null) {

        for (int i = 0; i < nodesToBrowse.length; i++) {
          BrowseResult nextBrowseResult = null;

          BrowseDescription currentNodeToBrowse = nodesToBrowse[i];
          NodeId nodeId = currentNodeToBrowse.getNodeId();
          if (onBrowseActions.containsKey(nodeId)) {
            nextBrowseResult = onBrowseActions.get(nodeId).clone();
            /*
             * Error handling
             */
          } else {
            // If NodeId is null, consider the syntax of the node id
            // not valid.
            if (NodeId.isNull(nodeId)) {
              browseResults.add(new BrowseResult(new StatusCode(StatusCodes.Bad_NodeIdInvalid), null, null));
            } else {
              // If NodeId is not found from browse actions
              // hashmap and is not null, then consider the NodeId
              // unknown
              browseResults.add(new BrowseResult(new StatusCode(StatusCodes.Bad_NodeIdUnknown), null, null));
            }
            continue;
          }
          NodeId referenceTypeId = currentNodeToBrowse.getReferenceTypeId();
          if ((!NodeId.isNull(referenceTypeId) && !onReadResultsMap.containsKey(referenceTypeId))
              || (!NodeId.isNull(referenceTypeId) && !isReferenceType(referenceTypeId))) {
            // We need to somehow decide whether the ReferenceTypeId
            // is invalid. If NodeId is not found from
            // onReadResultsMap, then the ReferenceType is not
            // valid.
            browseResults.add(new BrowseResult(new StatusCode(StatusCodes.Bad_ReferenceTypeIdInvalid), null, null));
            continue;
          }
          BrowseDirection browseDirection = currentNodeToBrowse.getBrowseDirection();
          if (browseDirection == null
              || (!browseDirection.equals(BrowseDirection.Both) && !browseDirection.equals(BrowseDirection.Forward)
                  && !browseDirection.equals(BrowseDirection.Inverse))) {
            browseResults.add(new BrowseResult(new StatusCode(StatusCodes.Bad_BrowseDirectionInvalid), null, null));
            continue;
          }

          // getBrowsePathTarget method contains code that is shared
          // by onBrowse- and onTranslateBrowsePathsToNodeIds methods.
          nextBrowseResult =
              getBrowsePathTarget(nodeId, referenceTypeId, browseDirection, currentNodeToBrowse.getIncludeSubtypes());

          /*
           * Responses are "filtered" by deleting unwanted references.
           */
          UnsignedInteger nodeClassMask = currentNodeToBrowse.getNodeClassMask();
          if (!(new UnsignedInteger(255).equals(nodeClassMask)) && !(new UnsignedInteger(0).equals(nodeClassMask))) {
            // Node Class Mask is not 255 or 0.
            /*
             * return references matching the node class as specified by the NodeClassMask Bit
             * NodeClass 0 Object 1 Variable 2 Method 3 ObjectType 4 VariableType 5 ReferenceType 6
             * DataType 7 View
             */
            ReferenceDescription[] referenceDescriptions = nextBrowseResult.getReferences();
            List<ReferenceDescription> newReferenceDescriptions = new ArrayList<ReferenceDescription>();

            if (referenceDescriptions != null) {
              for (int j = 0; j < referenceDescriptions.length; j++) {
                ReferenceDescription rd = referenceDescriptions[j];
                if ((rd.getNodeClass().getValue() & nodeClassMask.getValue()) != 0) {
                  newReferenceDescriptions.add(rd);
                }
              }
              nextBrowseResult.setReferences(
                  newReferenceDescriptions.toArray(new ReferenceDescription[newReferenceDescriptions.size()]));
            }
          }
          if (currentNodeToBrowse.getResultMask().intValue() != 63) {
            // Specifies the fields in the ReferenceDescription
            // structure that should be returned.
            // OPC UA part 4 - Services page 40, table 30.
            ReferenceDescription[] referenceDescriptions = nextBrowseResult.getReferences();
            if (referenceDescriptions != null) {
              for (ReferenceDescription rd : referenceDescriptions) {
                if ((1 & currentNodeToBrowse.getResultMask().intValue()) == 0) {
                  // ReferenceType to null
                  rd.setReferenceTypeId(null);
                }
                if ((2 & currentNodeToBrowse.getResultMask().intValue()) == 0) {
                  // IsForward to null
                  rd.setIsForward(null);
                }
                if ((4 & currentNodeToBrowse.getResultMask().intValue()) == 0) {
                  // NodeClass to null
                  rd.setNodeClass(null);
                }
                if ((8 & currentNodeToBrowse.getResultMask().intValue()) == 0) {
                  // BrowseName to null
                  rd.setBrowseName(null);
                }
                if ((16 & currentNodeToBrowse.getResultMask().intValue()) == 0) {
                  // DisplayName to null
                  rd.setDisplayName(null);
                }
                if ((32 & currentNodeToBrowse.getResultMask().intValue()) == 0) {
                  // TypeDefinition to null
                  rd.setTypeDefinition(null);
                }
              }
            }
          }
          if ((request.getRequestedMaxReferencesPerNode() != null)
              && (request.getRequestedMaxReferencesPerNode().intValue() != 0)) {

            if (nextBrowseResult.getReferences() != null) {

              int maxReferencesPerNode = request.getRequestedMaxReferencesPerNode().intValue();
              // Amount of results needs to be limited only if
              // RequestedMaxReferencesPerNode is smaller than
              // amount of references
              if (nextBrowseResult.getReferences().length > maxReferencesPerNode) {
                if (continuationPoint == null) {
                  ReferenceDescription[] referenceDescriptions = nextBrowseResult.getReferences();
                  List<ReferenceDescription> newReferenceDescriptions = new ArrayList<ReferenceDescription>();
                  List<ReferenceDescription> continuationPointReferenceDescriptions =
                      new ArrayList<ReferenceDescription>();

                  for (int j = 0; j < maxReferencesPerNode; j++) {
                    newReferenceDescriptions.add(referenceDescriptions[j]);
                  }
                  for (int j = maxReferencesPerNode; j < referenceDescriptions.length; j++) {
                    continuationPointReferenceDescriptions.add(referenceDescriptions[j]);
                  }
                  nextBrowseResult.setReferences(
                      newReferenceDescriptions.toArray(new ReferenceDescription[newReferenceDescriptions.size()]));

                  ByteString continuationPointIdentifier = ByteString.valueOf((byte) 1);
                  nextBrowseResult.setContinuationPoint(continuationPointIdentifier);
                  continuationPoint = new ContinuationPoint(request.getRequestedMaxReferencesPerNode(),
                      continuationPointReferenceDescriptions
                          .toArray(new ReferenceDescription[continuationPointReferenceDescriptions.size()]),
                      request.getRequestHeader().getAuthenticationToken(), continuationPointIdentifier);
                } else {
                  // There's only one continuation point
                  // available in this server. If it is not
                  // null, then no other continuation points
                  // are available.
                  nextBrowseResult = new BrowseResult(new StatusCode(StatusCodes.Bad_NoContinuationPoints), null, null);
                }
              }
            }
          }
          browseResults.add(nextBrowseResult);
        }
        // No errors to report in response header
        if (h == null) {
          h = new ResponseHeader(DateTime.currentTime(), request.getRequestHeader().getRequestHandle(), StatusCode.GOOD,
              null, null, null);
        }
      } else {
        // Bad_NothingToDo There was nothing to do because the client
        // passed a list of operations with no elements.
        h = new ResponseHeader(DateTime.currentTime(), request.getRequestHeader().getRequestHandle(),
            new StatusCode(StatusCodes.Bad_NothingToDo), null, null, null);
      }

      BrowseResponse response =
          new BrowseResponse(null, browseResults.toArray(new BrowseResult[browseResults.size()]), null);
      response.setResponseHeader(h);

      req.sendResponse(response);

    }

    @Override
    public void onBrowseNext(EndpointServiceRequest<BrowseNextRequest, BrowseNextResponse> req)
        throws ServiceFaultException {

      BrowseNextRequest request = req.getRequest();
      RequestHeader requestHeader = request.getRequestHeader();
      ByteString[] continuationPoints = request.getContinuationPoints();

      BrowseResult[] browseResults = new BrowseResult[continuationPoints.length];
      DiagnosticInfo[] diagnosticInfoResults = null;
      boolean continuationPointToNull = false;
      ResponseHeader h = checkRequestHeader(requestHeader);

      UnsignedInteger returnDiagnostics = requestHeader.getReturnDiagnostics();
      if (continuationPoints == null || continuationPoints.length == 0) {

        h = new ResponseHeader(DateTime.currentTime(), requestHeader.getRequestHandle(),
            new StatusCode(StatusCodes.Bad_NothingToDo), null, null, null);
        if (!returnDiagnostics.equals(new UnsignedInteger(0)) && returnDiagnostics != null) {
          diagnosticInfoResults = new DiagnosticInfo[1];
          diagnosticInfoResults[0] = new DiagnosticInfo("Some diagnostics.", null, null, null, null, 0, null);
          h.setStringTable(new String[] {"Some diagnostics available."});
        }
      } else if (continuationPoint != null && !request.getReleaseContinuationPoints()) {
        // Even though this implementation supports only one
        // continuation point, this structure should be expandable
        if (!returnDiagnostics.equals(new UnsignedInteger(0)) && returnDiagnostics != null) {
          diagnosticInfoResults = new DiagnosticInfo[continuationPoints.length];
        }
        int pos = 0;
        for (ByteString continuationPointRequested : continuationPoints) {
          if (!continuationPointRequested.equals(continuationPoint.getCurrentContinuationPoint())
              || !requestHeader.getAuthenticationToken().equals(continuationPoint.getAuthenticationToken())) {
            // Request contained old Continuation Point or
            // continuation point was not affiliated with this
            // session.
            browseResults[pos] = new BrowseResult(new StatusCode(StatusCodes.Bad_ContinuationPointInvalid), null, null);
          } else {
            // Actually get the next set of results
            browseResults[pos] = continuationPoint.getNextReferencesDescriptions(continuationPointRequested);
            if (browseResults[pos].getContinuationPoint() == null) {
              continuationPointToNull = true;
            }
          }
          if (!returnDiagnostics.equals(new UnsignedInteger(0)) && returnDiagnostics != null) {
            diagnosticInfoResults[pos] = new DiagnosticInfo("Some diagnostics.", null, null, null, null, 0, null);
            h.setStringTable(new String[] {""});
          }
          pos++;
        }
      } else if (continuationPoint == null) {
        // Client called browse next service even though no
        // continuationPoint exists.
        if ((returnDiagnostics != null) && (returnDiagnostics.intValue() != 0)) {
          diagnosticInfoResults = new DiagnosticInfo[continuationPoints.length];
        }

        for (int pos = 0; pos < continuationPoints.length; pos++) {
          browseResults[pos] = new BrowseResult(new StatusCode(StatusCodes.Bad_ContinuationPointInvalid), null, null);
          if ((returnDiagnostics != null) && (returnDiagnostics.intValue() != 0)) {
            diagnosticInfoResults[pos] =
                new DiagnosticInfo("No continuationPoint exists.", null, null, null, null, 0, null);
            h.setStringTable(new String[] {"No continuationPoint exists."});
          }
        }
      }
      if ((continuationPoint != null && request.getReleaseContinuationPoints()) || continuationPointToNull) {
        continuationPoint = null;
      }
      if ((returnDiagnostics != null) && (returnDiagnostics.intValue() != 0)) {
        h.setServiceDiagnostics(new DiagnosticInfo());
      }
      BrowseNextResponse browseNextResponse = new BrowseNextResponse(h, browseResults, diagnosticInfoResults);

      req.sendResponse(browseNextResponse);

    }

    @Override
    public void onDeleteNodes(EndpointServiceRequest<DeleteNodesRequest, DeleteNodesResponse> req)
        throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    @Override
    public void onDeleteReferences(EndpointServiceRequest<DeleteReferencesRequest, DeleteReferencesResponse> req)
        throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    @Override
    public void onQueryFirst(EndpointServiceRequest<QueryFirstRequest, QueryFirstResponse> req)
        throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    @Override
    public void onQueryNext(EndpointServiceRequest<QueryNextRequest, QueryNextResponse> req)
        throws ServiceFaultException {
      throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NotImplemented));
    }

    @Override
    public void onRegisterNodes(EndpointServiceRequest<RegisterNodesRequest, RegisterNodesResponse> req)
        throws ServiceFaultException {

      RegisterNodesRequest request = req.getRequest();

      NodeId[] registeredNodeIds = null;
      ResponseHeader h = checkRequestHeader(request.getRequestHeader());
      if (request.getNodesToRegister() == null || request.getNodesToRegister().length == 0) {
        // Bad_NothingToDo
        h.setServiceResult(new StatusCode(StatusCodes.Bad_NothingToDo));
        UnsignedInteger returnDiagnostics = request.getRequestHeader().getReturnDiagnostics();
        if ((returnDiagnostics != null) && (returnDiagnostics.intValue() != 0)) {
          // return service diagnostics
          h.setServiceDiagnostics(
              new DiagnosticInfo("Some diagnostics: Bad_NothingToDo.", null, null, null, null, 0, null));
          h.setStringTable(new String[] {"Some diagnostics: Bad_NothingToDo."});

        }
      } else {
        registeredNodeIds = new NodeId[request.getNodesToRegister().length];
        int pos = 0;
        for (NodeId nodeId : request.getNodesToRegister()) {
          // Do some custom checking on individual nodes, now only
          // pass requested nodes to response
          registeredNodeIds[pos] = nodeId;
          pos++;
        }
      }

      RegisterNodesResponse registerNodesResponse = new RegisterNodesResponse(h, registeredNodeIds);

      req.sendResponse(registerNodesResponse);
    }

    @Override
    public void onTranslateBrowsePathsToNodeIds(
        EndpointServiceRequest<TranslateBrowsePathsToNodeIdsRequest, TranslateBrowsePathsToNodeIdsResponse> req)
        throws ServiceFaultException {
      TranslateBrowsePathsToNodeIdsRequest request = req.getRequest();

      BrowsePathResult[] browsePathResults = null;
      DiagnosticInfo[] diagnosticInfos = null;
      // Check request header contents and return response header
      // accordingly.
      ResponseHeader h = checkRequestHeader(request.getRequestHeader());

      UnsignedInteger requestedReturnDiagnostics = request.getRequestHeader().getReturnDiagnostics();
      if (request.getBrowsePaths() == null || request.getBrowsePaths().length == 0) {
        // Bad_NothingToDo
        h.setServiceResult(new StatusCode(StatusCodes.Bad_NothingToDo));
        if (requestedReturnDiagnostics != null && !requestedReturnDiagnostics.equals(new UnsignedInteger(0))) {
          // return service diagnostics
          h.setServiceDiagnostics(new DiagnosticInfo("Diagnostics: Bad_NothingToDo.", null, null, null, null, 0, null));
          h.setStringTable(new String[] {"Diagnostics: Bad_NothingToDo."});

        }
      } else {
        // There is at least one browse path to browse
        int browsePathLength = request.getBrowsePaths().length;
        if ((requestedReturnDiagnostics != null) && (requestedReturnDiagnostics.intValue() != 0)) {
          diagnosticInfos = new DiagnosticInfo[browsePathLength]; // initialize
        }
        // diagnostics
        // if
        // they
        // are
        // requested.
        browsePathResults = new BrowsePathResult[browsePathLength];

        for (int i = 0; i < browsePathLength; i++) {
          BrowsePath bp = request.getBrowsePaths()[i];
          NodeId startingNode = bp.getStartingNode();

          // test is requested node is null
          if (NodeId.isNull(startingNode)) {
            browsePathResults[i] = new BrowsePathResult(new StatusCode(StatusCodes.Bad_NodeIdInvalid), null);
            // return diagnostics in the case of missing starting
            // node
            if ((requestedReturnDiagnostics != null) && (requestedReturnDiagnostics.intValue() != 0)) {
              // return service diagnostics
              h.setServiceDiagnostics(
                  new DiagnosticInfo("Diagnostics: Bad_NodeIdInvalid", null, null, null, null, 0, null));
              h.setStringTable(new String[] {"Diagnostics: Bad_NodeIdInvalid"});
              diagnosticInfos[i] = new DiagnosticInfo("DiagnosticInfo", null, null, null, 0, null, null);
            }
            continue;
          }
          // if node is not null, test if node is found on server at
          // all
          Map<UnsignedInteger, DataValue> attributeMap = onReadResultsMap.get(startingNode);
          if (attributeMap == null) {
            // Not found in read results
            browsePathResults[i] = new BrowsePathResult(new StatusCode(StatusCodes.Bad_NodeIdUnknown), null);
            // return diagnostics in the case of unknown starting
            // node
            if ((requestedReturnDiagnostics != null) && (requestedReturnDiagnostics.intValue() != 0)) {
              // return service diagnostics
              h.setServiceDiagnostics(
                  new DiagnosticInfo("Diagnostics: Bad_NodeIdUnknown", null, null, null, null, 0, null));
              h.setStringTable(new String[] {"Diagnostics: Bad_NodeIdUnknown"});
              diagnosticInfos[i] = new DiagnosticInfo("DiagnosticInfo", null, null, null, 0, null, null);
            }
            continue;
          }
          // test if client requested empty relative path
          RelativePath relativePath = bp.getRelativePath();
          if (relativePath == null || relativePath.getElements() == null || relativePath.getElements().length == 0) {
            // if relative path is empty then return Bad_NothingToDo
            browsePathResults[i] = new BrowsePathResult(new StatusCode(StatusCodes.Bad_NothingToDo), null);
            if ((requestedReturnDiagnostics != null) && (requestedReturnDiagnostics.intValue() != 0)) {
              // return service diagnostics
              h.setServiceDiagnostics(new DiagnosticInfo("Diagnostics: Bad_NothingToDo (relative path was empty).",
                  null, null, null, null, 0, null));
              h.setStringTable(new String[] {"Diagnostics: Bad_NothingToDo (relative path was empty)."});
              diagnosticInfos[i] = new DiagnosticInfo("DiagnosticInfo", null, null, null, 0, null, null);
            }
            continue;
          }

          RelativePathElement[] relativePathElements = relativePath.getElements();
          int relativePathElementsLength = relativePathElements.length;
          RelativePathElement lastElement = relativePathElements[relativePathElementsLength - 1];
          // Specification part 4 page 44: The last element in the
          // relativePath shall always have a targetName specified.
          if (QualifiedName.isNull(lastElement.getTargetName())) {
            browsePathResults[i] = new BrowsePathResult(new StatusCode(StatusCodes.Bad_BrowseNameInvalid), null);
            continue;
          }

          // After testing that values are valid, go through relative
          // path elements of current browse path and return correct
          // BrowsePathTarget.
          // In some cases also multiple valid BrowsePathTargets may
          // be found.
          ArrayList<BrowsePathTarget> targets = new ArrayList<BrowsePathTarget>();

          for (int j = 0; j < relativePathElementsLength; j++) {
            RelativePathElement rpe = relativePathElements[j];
            // Test if target name (browse name) is null. In that
            // case return Bad_BrowseNameInvalid. However the final
            // element may have an empty target name.
            if (QualifiedName.isNull(rpe.getTargetName()) && j < (relativePathElementsLength - 1)) {
              browsePathResults[i] = new BrowsePathResult(new StatusCode(StatusCodes.Bad_BrowseNameInvalid), null);
              break;
            }
            // relative path element defines the browse direction
            BrowseDirection browseDirection = BrowseDirection.Forward;
            if (rpe.getIsInverse()) {
              browseDirection = BrowseDirection.Inverse;
            }
            // Return all browse results from starting node with
            // certain reference type id and browse direction
            BrowseResult results =
                getBrowsePathTarget(startingNode, rpe.getReferenceTypeId(), browseDirection, rpe.getIncludeSubtypes());

            // Look through the browse results to find one with
            // correct target name
            ExpandedNodeId match = null;
            if (results.getReferences() != null) {
              for (ReferenceDescription rd : results.getReferences()) {
                QualifiedName browseName = rd.getBrowseName();
                if (browseName.equals(rpe.getTargetName())) {
                  // Found match with target name, this is
                  // starting node for next iteration
                  // Convert from expanded node id to node id:
                  Object value = rd.getNodeId().getValue();
                  int namespaceIndex = rd.getNodeId().getNamespaceIndex();
                  switch (rd.getNodeId().getIdType()) {
                    case Numeric:
                      startingNode = new NodeId(namespaceIndex, (UnsignedInteger) value);
                      break;
                    case String:
                      startingNode = new NodeId(namespaceIndex, (String) value);
                      break;
                    case Guid:
                      startingNode = new NodeId(namespaceIndex, (UUID) value);
                      break;
                    case Opaque:
                      startingNode = new NodeId(namespaceIndex, (byte[]) value);
                      break;
                  }

                  if (browseName.equals(lastElement.getTargetName())) {
                    // Return last target of multiple
                    // RelativePathElements
                    match = rd.getNodeId();
                    // Specification part 4, page 43: If a Node
                    // has multiple targets with the same
                    // BrowseName, the Server shall return a
                    // list of NodeIds
                    targets.add(new BrowsePathTarget(match, UnsignedInteger.MAX_VALUE));
                  }
                }
              }
            }
          }
          // Check whether valid targets have been found
          if (targets.size() == 0 && browsePathResults[i] == null) {
            // if not then return status code Bad_NoMatch
            browsePathResults[i] = new BrowsePathResult(new StatusCode(StatusCodes.Bad_NoMatch), null);
          } else if (targets.size() > 0 && browsePathResults[i] == null) {
            // otherwise everything is correct, return status code
            // GOOD
            browsePathResults[i] =
                new BrowsePathResult(StatusCode.GOOD, targets.toArray(new BrowsePathTarget[targets.size()]));
          }
          if ((requestedReturnDiagnostics != null) && (requestedReturnDiagnostics.intValue() != 0)) {
            // return service diagnostics
            h.setServiceDiagnostics(
                new DiagnosticInfo("Diagnostics: everything normal.", null, null, null, null, 0, null));
            h.setStringTable(new String[] {"Diagnostics: everything normal."});
            diagnosticInfos[i] = new DiagnosticInfo("DiagnosticInfo", null, null, null, 0, null, null);
          }
        }
      }

      TranslateBrowsePathsToNodeIdsResponse translateBrowsePathsToNodeIdsResponse =
          new TranslateBrowsePathsToNodeIdsResponse(h, browsePathResults, diagnosticInfos);
      req.sendResponse(translateBrowsePathsToNodeIdsResponse);

    }

    @Override
    public void onUnregisterNodes(EndpointServiceRequest<UnregisterNodesRequest, UnregisterNodesResponse> req)
        throws ServiceFaultException {

      UnregisterNodesRequest request = req.getRequest();

      ResponseHeader h = checkRequestHeader(request.getRequestHeader());
      NodeId[] nodesToUnregister = request.getNodesToUnregister();
      if (nodesToUnregister == null || nodesToUnregister.length == 0) {
        // Bad_NothingToDo
        h.setServiceResult(new StatusCode(StatusCodes.Bad_NothingToDo));
        UnsignedInteger returnDiagnostics = request.getRequestHeader().getReturnDiagnostics();
        if ((returnDiagnostics != null) && (returnDiagnostics.intValue() != 0)) {
          // return service diagnostics
          h.setServiceDiagnostics(
              new DiagnosticInfo("Some diagnostics: Bad_NothingToDo.", null, null, null, null, 0, null));
          h.setStringTable(new String[] {"Some diagnostics: Bad_NothingToDo."});

        }
      } /*
         * else { for(NodeId nodeId : request.getNodesToUnregister()) { ////Do some custom checking
         * on individual nodes, now all nodes are unregistered equally (do nothing)
         * 
         * } }
         */
      UnregisterNodesResponse unRegisterNodesResponse = new UnregisterNodesResponse(h);
      req.sendResponse(unRegisterNodesResponse);

    }

  }

  static class NanoServerExample extends Server implements SessionServiceSetHandler {

    @SuppressWarnings("serial")
    public NanoServerExample(Application application) throws Exception {
      super(application);
      addServiceHandler(this);

      // Add Client Application Instance Certificate validator - Accept
      // them all (for now)
      application.getOpctcpSettings().setCertificateValidator(CertificateValidator.ALLOW_ALL);
      application.getHttpsSettings().setCertificateValidator(CertificateValidator.ALLOW_ALL);

      // The HTTPS SecurityPolicies are defined separate from the endpoint
      // securities
      application.getHttpsSettings().setHttpsSecurityPolicies(HttpsSecurityPolicy.ALL);

      // Peer verifier
      application.getHttpsSettings().setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

      // Load Servers's Application Instance Certificate...
      KeyPair myServerApplicationInstanceCertificate = ExampleKeys.getCert("NanoServer");
      application.addApplicationInstanceCertificate(myServerApplicationInstanceCertificate);
      // ...and HTTPS certificate
      KeyPair myHttpsCertificate = ExampleKeys.getHttpsCert("NanoServer");
      application.getHttpsSettings().setKeyPair(myHttpsCertificate);

      // Add User Token Policies
      addUserTokenPolicy(UserTokenPolicy.ANONYMOUS);
      addUserTokenPolicy(UserTokenPolicy.SECURE_USERNAME_PASSWORD);

      // Create an endpoint for each network interface
      String hostname = EndpointUtil.getHostname();
      String bindAddress, endpointAddress;
      for (String addr : EndpointUtil.getInetAddressNames()) {
        bindAddress = "https://" + addr + ":8443";
        endpointAddress = "https://" + hostname + ":8443";
        logger.info("{} bound at {}", endpointAddress, bindAddress);
        // The HTTPS ports are using NONE OPC security
        bind(bindAddress, endpointAddress, SecurityMode.NONE);

        bindAddress = "opc.tcp://" + addr + ":8666";
        endpointAddress = "opc.tcp://" + hostname + ":8666";
        logger.info("{} bound at {}", endpointAddress, bindAddress);
        bind(bindAddress, endpointAddress, SecurityMode.ALL_101);
      }

      // Make ArrayList for authentication tokens
      validAuthenticationTokens = new ArrayList<NodeId>();
      sessions = new ArrayList<NodeId>();
      timeoutPeriods = new HashMap<NodeId, Long>();

      // Set continuationPoint to null at start-up
      continuationPoint = null;

      // *******************************************************************************
      // Put all browse references in one HashMap for better readability and performance
      // *******************************************************************************

      onBrowseActions = new HashMap<NodeId, BrowseResult>();
      // root node
      onBrowseActions.put(Identifiers.RootFolder,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  // Parameters: ReferenceTypeId, IsForward, NodeId, BrowseName, DisplayName,
                  // NodeClass, TypeDefinition
                  new ReferenceDescription(Identifiers.Organizes, true, new ExpandedNodeId(Identifiers.ObjectsFolder),
                      new QualifiedName("Objects"), new LocalizedText("Objects"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.Organizes, true, new ExpandedNodeId(Identifiers.TypesFolder),
                      new QualifiedName("Types"), new LocalizedText("Types"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.Organizes, true,
                      new ExpandedNodeId(Identifiers.ViewsFolder), new QualifiedName("Views"),
                      new LocalizedText("Views"), NodeClass.Object, new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.FolderType), new QualifiedName("FolderType"),
                      new LocalizedText("FolderType"), NodeClass.ObjectType, null)}));
      // types folder
      onBrowseActions.put(Identifiers.TypesFolder,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {new ReferenceDescription(Identifiers.Organizes, true,
                  new ExpandedNodeId(Identifiers.DataTypesFolder), new QualifiedName("DataTypes"),
                  new LocalizedText("DataTypes"), NodeClass.Object, new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.Organizes, true,
                      new ExpandedNodeId(Identifiers.ReferenceTypesFolder), new QualifiedName("ReferenceTypes"),
                      new LocalizedText("ReferenceTypes"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.FolderType), new QualifiedName("FolderType"),
                      new LocalizedText("FolderType"), NodeClass.ObjectType, null),
                  new ReferenceDescription(Identifiers.Organizes, false, new ExpandedNodeId(Identifiers.RootFolder),
                      new QualifiedName("Root"), new LocalizedText("Root"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.FolderType))}));
      // Views folder
      onBrowseActions.put(Identifiers.ViewsFolder,
          new BrowseResult(StatusCode.GOOD, null, new ReferenceDescription[] {
              new ReferenceDescription(Identifiers.HasTypeDefinition, true, new ExpandedNodeId(Identifiers.FolderType),
                  new QualifiedName("FolderType"), new LocalizedText("FolderType"), NodeClass.ObjectType, null),
              new ReferenceDescription(Identifiers.Organizes, false, new ExpandedNodeId(Identifiers.RootFolder),
                  new QualifiedName("Root"), new LocalizedText("Root"), NodeClass.Object,
                  new ExpandedNodeId(Identifiers.FolderType))}));
      // etc...
      onBrowseActions.put(Identifiers.DataTypesFolder,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.Organizes, true, new ExpandedNodeId(Identifiers.BaseDataType),
                      new QualifiedName("BaseDataType"), new LocalizedText("BaseDataType"), NodeClass.DataType, null),
                  new ReferenceDescription(Identifiers.Organizes, true,
                      new ExpandedNodeId(Identifiers.XmlSchema_TypeSystem), new QualifiedName("XML Schema"),
                      new LocalizedText("XML Schema"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.DataTypeSystemType)),
                  new ReferenceDescription(Identifiers.Organizes, true,
                      new ExpandedNodeId(Identifiers.OPCBinarySchema_TypeSystem), new QualifiedName("OPC Binary"),
                      new LocalizedText("OPC Binary"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.DataTypeSystemType)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.FolderType), new QualifiedName("FolderType"),
                      new LocalizedText("FolderType"), NodeClass.ObjectType, null),
                  new ReferenceDescription(Identifiers.Organizes, false, new ExpandedNodeId(Identifiers.TypesFolder),
                      new QualifiedName("Types"), new LocalizedText("Types"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.FolderType))}));
      onBrowseActions.put(Identifiers.ReferenceTypesFolder,
          new BrowseResult(StatusCode.GOOD, null, new ReferenceDescription[] {
              new ReferenceDescription(Identifiers.Organizes, true, new ExpandedNodeId(Identifiers.References),
                  new QualifiedName("References"), new LocalizedText("References"), NodeClass.ReferenceType, null),
              new ReferenceDescription(Identifiers.HasTypeDefinition, true, new ExpandedNodeId(Identifiers.FolderType),
                  new QualifiedName("FolderType"), new LocalizedText("FolderType"), NodeClass.ObjectType, null),
              new ReferenceDescription(Identifiers.Organizes, false, new ExpandedNodeId(Identifiers.TypesFolder),
                  new QualifiedName("Types"), new LocalizedText("Types"), NodeClass.Object,
                  new ExpandedNodeId(Identifiers.FolderType))}));
      onBrowseActions.put(Identifiers.References, new BrowseResult(StatusCode.GOOD, null,
          new ReferenceDescription[] {new ReferenceDescription(Identifiers.HasSubtype, true,
              new ExpandedNodeId(Identifiers.NonHierarchicalReferences), new QualifiedName("NonHierarchicalReferences"),
              new LocalizedText("NonHierarchicalReferences"), NodeClass.ReferenceType, null),
              new ReferenceDescription(Identifiers.HasSubtype, true,
                  new ExpandedNodeId(Identifiers.HierarchicalReferences), new QualifiedName("HierarchicalReferences"),
                  new LocalizedText("HierarchicalReferences"), NodeClass.ReferenceType, null),
              new ReferenceDescription(Identifiers.Organizes, false,
                  new ExpandedNodeId(Identifiers.ReferenceTypesFolder), new QualifiedName("ReferenceTypes"),
                  new LocalizedText("ReferenceTypes"), NodeClass.Object, new ExpandedNodeId(Identifiers.FolderType))}));

      onBrowseActions.put(Identifiers.NonHierarchicalReferences,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.HasSubtype, false, new ExpandedNodeId(Identifiers.References),
                      new QualifiedName("References"), new LocalizedText("References"), NodeClass.ReferenceType, null),
                  new ReferenceDescription(Identifiers.HasSubtype, true,
                      new ExpandedNodeId(Identifiers.HasTypeDefinition), new QualifiedName("HasTypeDefinition"),
                      new LocalizedText("HasTypeDefinition"), NodeClass.ReferenceType, null)

              }));

      onBrowseActions.put(Identifiers.HierarchicalReferences,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.HasSubtype, false, new ExpandedNodeId(Identifiers.References),
                      new QualifiedName("References"), new LocalizedText("References"), NodeClass.ReferenceType, null),
                  new ReferenceDescription(Identifiers.HasSubtype, true, new ExpandedNodeId(Identifiers.HasChild),
                      new QualifiedName("HasChild"), new LocalizedText("HasChild"), NodeClass.ReferenceType, null),
                  new ReferenceDescription(Identifiers.HasSubtype, true, new ExpandedNodeId(Identifiers.Organizes),
                      new QualifiedName("Organizes"), new LocalizedText("Organizes"), NodeClass.ReferenceType, null)

              }));

      onBrowseActions.put(Identifiers.BaseDataType,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.HasSubtype, true, new ExpandedNodeId(Identifiers.Boolean),
                      new QualifiedName("Boolean"), new LocalizedText("Boolean"), NodeClass.DataType, null)}));
      onBrowseActions.put(Identifiers.ObjectsFolder,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {new ReferenceDescription(Identifiers.Organizes, false,
                  new ExpandedNodeId(Identifiers.RootFolder), new QualifiedName("Root"), new LocalizedText("Root"),
                  NodeClass.Object, new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.FolderType), new QualifiedName("FolderType"),
                      new LocalizedText("FolderType"), NodeClass.ObjectType, null),
                  new ReferenceDescription(Identifiers.Organizes, true, new ExpandedNodeId(Identifiers.Server),
                      new QualifiedName("Server"), new LocalizedText("Server"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.ServerType)),
                  new ReferenceDescription(Identifiers.Organizes, true,
                      new ExpandedNodeId(new NodeId(complianceNamespaceIndex, "StaticData")),
                      new QualifiedName("StaticData"), new LocalizedText("StaticData"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.FolderType))}));
      onBrowseActions.put(Identifiers.Server,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {new ReferenceDescription(Identifiers.Organizes, false,
                  new ExpandedNodeId(Identifiers.ObjectsFolder), new QualifiedName("Objects"),
                  new LocalizedText("Objects"), NodeClass.Object, new ExpandedNodeId(Identifiers.ObjectsFolder)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.ServerType), new QualifiedName("ServerType"),
                      new LocalizedText("ServerType"), NodeClass.ObjectType,
                      new ExpandedNodeId(Identifiers.ServerType)),
                  new ReferenceDescription(Identifiers.HasComponent, true,
                      new ExpandedNodeId(Identifiers.Server_ServerStatus),
                      new QualifiedName("ServerStatus"), new LocalizedText("ServerStatus"), NodeClass.Variable,
                      new ExpandedNodeId(Identifiers.Server_ServerStatus)),
                  new ReferenceDescription(Identifiers.HasComponent, true,
                      new ExpandedNodeId(Identifiers.Server_ServerCapabilities),
                      new QualifiedName("ServerCapabilities"), new LocalizedText("ServerCapabilities"),
                      NodeClass.Object, new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.HasComponent, true,
                      new ExpandedNodeId(Identifiers.Server_ServerArray),
                      new QualifiedName("ServerArray"), new LocalizedText("ServerArray"), NodeClass.Variable,
                      new ExpandedNodeId(Identifiers.PropertyType)),
                  new ReferenceDescription(Identifiers.HasComponent, true,
                      new ExpandedNodeId(Identifiers.Server_NamespaceArray), new QualifiedName("NamespaceArray"),
                      new LocalizedText("NamespaceArray"), NodeClass.Variable,
                      new ExpandedNodeId(Identifiers.PropertyType))}));
      onBrowseActions.put(Identifiers.Server_ServerCapabilities,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {new ReferenceDescription(Identifiers.HasComponent, false,
                  new ExpandedNodeId(Identifiers.Server), new QualifiedName("Server"), new LocalizedText("Server"),
                  NodeClass.Object, new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.HasProperty, true,
                      new ExpandedNodeId(Identifiers.Server_ServerCapabilities_MaxBrowseContinuationPoints),
                      new QualifiedName("MaxBrowseContinuationPoints"),
                      new LocalizedText("MaxBrowseContinuationPoints"), NodeClass.Variable,
                      new ExpandedNodeId(Identifiers.UInt16))}));
      onBrowseActions.put(Identifiers.Server_ServerCapabilities_MaxBrowseContinuationPoints, new BrowseResult(
          StatusCode.GOOD, null,
          new ReferenceDescription[] {new ReferenceDescription(Identifiers.HasProperty, false,
              new ExpandedNodeId(Identifiers.Server_ServerCapabilities), new QualifiedName("ServerCapabilities"),
              new LocalizedText("ServerCapabilities"), NodeClass.Object, new ExpandedNodeId(Identifiers.FolderType)),
              new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                  new ExpandedNodeId(Identifiers.PropertyType), new QualifiedName("PropertyType"),
                  new LocalizedText("PropertyType"), NodeClass.DataType, null)}));

      onBrowseActions.put(Identifiers.Server_ServerArray,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.HasProperty, false, new ExpandedNodeId(Identifiers.Server),
                      new QualifiedName("Server"), new LocalizedText("Server"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.ServerType)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.PropertyType), new QualifiedName("PropertyType"),
                      new LocalizedText("PropertyType"), NodeClass.DataType, null)}));
      onBrowseActions.put(Identifiers.Server_NamespaceArray,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.HasProperty, false, new ExpandedNodeId(Identifiers.Server),
                      new QualifiedName("Server"), new LocalizedText("Server"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.ServerType)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.PropertyType), new QualifiedName("PropertyType"),
                      new LocalizedText("PropertyType"), NodeClass.DataType, null)}));
      onBrowseActions.put(Identifiers.FolderType,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {new ReferenceDescription(Identifiers.HasSubtype, false,
                  new ExpandedNodeId(Identifiers.BaseObjectType), new QualifiedName("BaseObjectType"),
                  new LocalizedText("BaseObjectType"), NodeClass.ObjectType, null)}));

      onBrowseActions.put(Identifiers.OPCBinarySchema_TypeSystem,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.Organizes, false,
                      new ExpandedNodeId(Identifiers.DataTypesFolder), new QualifiedName("DataTypes"),
                      new LocalizedText("DataTypes"), NodeClass.Object, new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.DataTypeSystemType), new QualifiedName("DataTypeSystemType"),
                      new LocalizedText("DataTypeSystemType"), NodeClass.ObjectType,
                      new ExpandedNodeId(Identifiers.DataTypeSystemType))}));
      onBrowseActions.put(Identifiers.XmlSchema_TypeSystem,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.Organizes, false,
                      new ExpandedNodeId(Identifiers.DataTypesFolder), new QualifiedName("DataTypes"),
                      new LocalizedText("DataTypes"), NodeClass.Object, new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.DataTypeSystemType), new QualifiedName("DataTypeSystemType"),
                      new LocalizedText("DataTypeSystemType"), NodeClass.ObjectType,
                      new ExpandedNodeId(Identifiers.DataTypeSystemType))}));
      onBrowseActions.put(Identifiers.DataTypeSystemType, new BrowseResult(StatusCode.GOOD, null, null));

      onBrowseActions.put(Identifiers.ServerType, new BrowseResult(StatusCode.GOOD, null, null));

      onBrowseActions.put(Identifiers.PropertyType,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.HasSubtype, false,
                      new ExpandedNodeId(Identifiers.BaseVariableType), new QualifiedName("BaseVariableType"),
                      new LocalizedText("BaseVariableType"), NodeClass.VariableType, null),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, false,
                      new ExpandedNodeId(Identifiers.Server_ServerCapabilities_MaxBrowseContinuationPoints),
                      new QualifiedName("MaxBrowseContinuationPoints"),
                      new LocalizedText("MaxBrowseContinuationPoints"), NodeClass.Variable, null),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, false,
                      new ExpandedNodeId(Identifiers.Server_ServerArray),
                      new QualifiedName("ServerArray"), new LocalizedText("ServerArray"), NodeClass.Variable,
                      new ExpandedNodeId(Identifiers.PropertyType)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, false,
                      new ExpandedNodeId(Identifiers.Server_NamespaceArray), new QualifiedName("NamespaceArray"),
                      new LocalizedText("NamespaceArray"), NodeClass.Variable,
                      new ExpandedNodeId(Identifiers.PropertyType))}));

      onBrowseActions.put(Identifiers.BaseDataVariableType,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.HasSubtype, false,
                      new ExpandedNodeId(Identifiers.BaseVariableType), new QualifiedName("BaseVariableType"),
                      new LocalizedText("BaseVariableType"), NodeClass.VariableType, null),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, false,
                      new ExpandedNodeId(Identifiers.Server_ServerStatus_State), new QualifiedName("State"),
                      new LocalizedText("State"), NodeClass.Variable, null)}));

      onBrowseActions
          .put(Identifiers.Server_ServerStatus,
              new BrowseResult(StatusCode.GOOD, null,
                  new ReferenceDescription[] {new ReferenceDescription(Identifiers.HasComponent,
                      false, new ExpandedNodeId(Identifiers.Server), new QualifiedName("Server"),
                      new LocalizedText("Server"), NodeClass.Variable, new ExpandedNodeId(Identifiers.Server)),
                      new ReferenceDescription(Identifiers.HasComponent, true,
                          new ExpandedNodeId(Identifiers.Server_ServerStatus_State), new QualifiedName("State"),
                          new LocalizedText("State"), NodeClass.Variable,
                          new ExpandedNodeId(Identifiers.ServerState))}));
      onBrowseActions.put(Identifiers.Server_ServerStatus_State,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  new ReferenceDescription(Identifiers.HasComponent, false,
                      new ExpandedNodeId(Identifiers.Server_ServerStatus), new QualifiedName("ServerStatus"),
                      new LocalizedText("ServerStatus"), NodeClass.Variable,
                      new ExpandedNodeId(Identifiers.ServerStatusDataType)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.BaseDataVariableType), new QualifiedName("BaseDataVariableType"),
                      new LocalizedText("BaseDataVariableType"), NodeClass.VariableType,
                      new ExpandedNodeId(Identifiers.BaseVariableType))}));

      onBrowseActions.put(Identifiers.Server_GetMonitoredItems, new BrowseResult(StatusCode.GOOD, null, null));

      onBrowseActions.put(Identifiers.BaseObjectType, new BrowseResult(StatusCode.GOOD, null, null));

      onBrowseActions.put(Identifiers.BaseVariableType,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  // Parameters: ReferenceTypeId, IsForward, NodeId, BrowseName, DisplayName,
                  // NodeClass, TypeDefinition
                  new ReferenceDescription(Identifiers.HasSubtype, true,
                      new ExpandedNodeId(Identifiers.BaseDataVariableType), new QualifiedName("BaseDataVariableType"),
                      new LocalizedText("BaseDataVariableType"), NodeClass.VariableType, null),
                  new ReferenceDescription(Identifiers.HasSubtype, true, new ExpandedNodeId(Identifiers.PropertyType),
                      new QualifiedName("PropertyType"), new LocalizedText("PropertyType"), NodeClass.VariableType,
                      null)}));

      onBrowseActions.put(Identifiers.HasChild,
          new BrowseResult(StatusCode.GOOD, null, new ReferenceDescription[] {
              // Parameters: ReferenceTypeId, IsForward, NodeId, BrowseName, DisplayName, NodeClass,
              // TypeDefinition
              new ReferenceDescription(Identifiers.HasSubtype, false,
                  new ExpandedNodeId(Identifiers.HierarchicalReferences), new QualifiedName("HierarchicalReferences"),
                  new LocalizedText("HierarchicalReferences"), NodeClass.ReferenceType, null),
              new ReferenceDescription(Identifiers.HasSubtype, true, new ExpandedNodeId(Identifiers.HasSubtype),
                  new QualifiedName("HasSubtype"), new LocalizedText("HasSubtype"), NodeClass.ReferenceType, null)}));
      onBrowseActions.put(Identifiers.HasSubtype,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  // Parameters: ReferenceTypeId, IsForward, NodeId, BrowseName, DisplayName,
                  // NodeClass, TypeDefinition
                  new ReferenceDescription(Identifiers.HasSubtype, false, new ExpandedNodeId(Identifiers.HasChild),
                      new QualifiedName("HasChild"), new LocalizedText("HasChild"), NodeClass.ReferenceType, null)}));

      onBrowseActions.put(Identifiers.Organizes,
          new BrowseResult(StatusCode.GOOD, null, new ReferenceDescription[] {
              // Parameters: ReferenceTypeId, IsForward, NodeId, BrowseName, DisplayName, NodeClass,
              // TypeDefinition
              new ReferenceDescription(Identifiers.HasSubtype, false,
                  new ExpandedNodeId(Identifiers.HierarchicalReferences), new QualifiedName("HierarchicalReferences"),
                  new LocalizedText("HierarchicalReferences"), NodeClass.ReferenceType, null)}));
      onBrowseActions.put(Identifiers.HasTypeDefinition,
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {
                  // Parameters: ReferenceTypeId, IsForward, NodeId, BrowseName, DisplayName,
                  // NodeClass, TypeDefinition
                  new ReferenceDescription(Identifiers.HasSubtype, false,
                      new ExpandedNodeId(Identifiers.NonHierarchicalReferences),
                      new QualifiedName("NonHierarchicalReferences"), new LocalizedText("NonHierarchicalReferences"),
                      NodeClass.ReferenceType, null)}));

      // Compliance namespace StaticData folder
      onBrowseActions.put(new NodeId(complianceNamespaceIndex, "StaticData"),
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {new ReferenceDescription(Identifiers.Organizes, false,
                  new ExpandedNodeId(Identifiers.ObjectsFolder), new QualifiedName("Objects"),
                  new LocalizedText("Objects"), NodeClass.Object, new ExpandedNodeId(Identifiers.ObjectsFolder)),
                  new ReferenceDescription(Identifiers.HasTypeDefinition, true,
                      new ExpandedNodeId(Identifiers.FolderType), new QualifiedName("FolderType"),
                      new LocalizedText("FolderType"), NodeClass.ObjectType,
                      new ExpandedNodeId(Identifiers.FolderType)),
                  new ReferenceDescription(Identifiers.Organizes, true,
                      new ExpandedNodeId(new NodeId(complianceNamespaceIndex, "StaticVariablesFolder")),
                      new QualifiedName("StaticVariables"), new LocalizedText("StaticVariables"), NodeClass.Object,
                      new ExpandedNodeId(Identifiers.FolderType))}));
      // Static Variables folder
      onBrowseActions.put(new NodeId(complianceNamespaceIndex, "StaticVariablesFolder"), new BrowseResult(
          StatusCode.GOOD, null,
          new ReferenceDescription[] {new ReferenceDescription(Identifiers.HasComponent, true,
              new ExpandedNodeId(new NodeId(complianceNamespaceIndex, "Boolean")), new QualifiedName("Boolean"),
              new LocalizedText("Boolean"), NodeClass.Variable, new ExpandedNodeId(Identifiers.BaseDataVariableType)),
              new ReferenceDescription(Identifiers.Organizes, false,
                  new ExpandedNodeId(new NodeId(complianceNamespaceIndex, "StaticData")),
                  new QualifiedName("StaticData"), new LocalizedText("StaticData"), NodeClass.Object,
                  new ExpandedNodeId(new NodeId(complianceNamespaceIndex, "StaticData"))),
              new ReferenceDescription(Identifiers.HasTypeDefinition, true, new ExpandedNodeId(Identifiers.FolderType),
                  new QualifiedName("FolderType"), new LocalizedText("FolderType"), NodeClass.ObjectType,
                  new ExpandedNodeId(Identifiers.FolderType))}));

      onBrowseActions.put(Identifiers.Boolean,
          new BrowseResult(StatusCode.GOOD, null, new ReferenceDescription[] {
              // Parameters: ReferenceTypeId, IsForward, NodeId, BrowseName, DisplayName, NodeClass,
              // TypeDefinition
              new ReferenceDescription(Identifiers.HasSubtype, false, new ExpandedNodeId(Identifiers.BaseDataType),
                  new QualifiedName("BaseDataType"), new LocalizedText("BaseDataType"), NodeClass.DataType, null)}));

      onBrowseActions.put(new NodeId(complianceNamespaceIndex, "Boolean"),
          new BrowseResult(StatusCode.GOOD, null,
              new ReferenceDescription[] {new ReferenceDescription(Identifiers.HasComponent, false,
                  new ExpandedNodeId(new NodeId(complianceNamespaceIndex, "StaticVariablesFolder")),
                  new QualifiedName("StaticVariables"), new LocalizedText("StaticVariables"), NodeClass.Object,
                  new ExpandedNodeId(Identifiers.FolderType))}));

      // *******************************************************************************
      // Put all read datavalues in one HashMap for better readability and performance
      // *******************************************************************************
      final DateTime serverTimeStamp = DateTime.currentTime();
      onReadResultsMap = new HashMap<NodeId, Map<UnsignedInteger, DataValue>>();

      onReadResultsMap.put(new NodeId(complianceNamespaceIndex, "Boolean"), new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId, new DataValue(new Variant(new NodeId(complianceNamespaceIndex, "Boolean")),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Variable), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName(complianceNamespaceIndex + ":Boolean")), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.DisplayName, new DataValue(new Variant(new LocalizedText("Boolean", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Value, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DataType,
              new DataValue(new Variant(Identifiers.Boolean), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.ValueRank, new DataValue(new Variant(-1), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.ArrayDimensions, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.AccessLevel,
              new DataValue(new Variant(new UnsignedInteger(3)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserAccessLevel,
              new DataValue(new Variant(new UnsignedInteger(3)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.MinimumSamplingInterval,
              new DataValue(new Variant(-1.0), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Historizing, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.RootFolder, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.RootFolder), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("Root")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName, new DataValue(new Variant(new LocalizedText("Root", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(new LocalizedText("The root of the server address space.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.EventNotifier,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.ObjectsFolder, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.ObjectsFolder), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("Objects")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName, new DataValue(new Variant(new LocalizedText("Objects", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(
                  new LocalizedText("The browse entry point when looking for objects in the server address space.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.EventNotifier, new DataValue(new Variant(0), StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.TypesFolder, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.TypesFolder), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("Types")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName, new DataValue(new Variant(new LocalizedText("Types", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(
                  new LocalizedText("The browse entry point when looking for types in the server address space.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.EventNotifier, new DataValue(new Variant(0), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.ViewsFolder, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.ViewsFolder), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("Views")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName, new DataValue(new Variant(new LocalizedText("Views", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(
                  new LocalizedText("The browse entry point when looking for views in the server address space.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.EventNotifier, new DataValue(new Variant(0), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.DataTypesFolder, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.DataTypesFolder), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("DataTypes")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("DataTypes", LocalizedText.NO_LOCALE)), StatusCode.GOOD, null,
                  serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(
                  new LocalizedText("The browse entry point when looking for data types in the server address space.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.EventNotifier, new DataValue(new Variant(0), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.ReferenceTypesFolder, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.ReferenceTypesFolder), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("ReferenceTypes")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("ReferenceTypes", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(new LocalizedText(
                  "The browse entry point when looking for reference types in the server address space.",
                  LocalizedText.NO_LOCALE)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.EventNotifier, new DataValue(new Variant(0), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.References, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.References), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("References")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("References", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(new LocalizedText("The abstract base type for all references.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(true), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.HierarchicalReferences, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.HierarchicalReferences), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName, new DataValue(new Variant(new QualifiedName("HierarchicalReferences")),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("HierarchicalReferences", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description, new DataValue(new Variant(
              new LocalizedText("The abstract base type for all hierarchical references.", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName,
              new DataValue(new Variant(new LocalizedText("HierarchicalReferences.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.NonHierarchicalReferences, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId, new DataValue(new Variant(Identifiers.NonHierarchicalReferences), StatusCode.GOOD,
              null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName, new DataValue(new Variant(new QualifiedName("NonHierarchicalReferences")),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("NonHierarchicalReferences", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(new LocalizedText("The abstract base type for all non-hierarchical references.",
                  LocalizedText.NO_LOCALE)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName,
              new DataValue(new Variant(new LocalizedText("NonHierarchicalReferences.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.FolderType, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.FolderType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ObjectType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("FolderType")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("FolderType", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(
                      new LocalizedText("The type for objects that organize other nodes.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.OPCBinarySchema_TypeSystem, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId, new DataValue(new Variant(Identifiers.OPCBinarySchema_TypeSystem), StatusCode.GOOD,
              null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("OPC Binary")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("OPC Binary", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(new LocalizedText(
                  "A type system which uses OPC binary schema to describe the encoding of data types.",
                  LocalizedText.NO_LOCALE)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.EventNotifier, new DataValue(new Variant(0), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.XmlSchema_TypeSystem, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.XmlSchema_TypeSystem), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("XML Schema")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("XML Schema", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(
                  new LocalizedText("A type system which uses XML schema to describe the encoding of data types.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.EventNotifier, new DataValue(new Variant(0), StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.DataTypeSystemType, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.DataTypeSystemType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ObjectType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName, new DataValue(new Variant(new QualifiedName("DataTypeSystemType")),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("DataTypeSystemType", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.PropertyType, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.PropertyType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.VariableType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("PropertyType")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("PropertyType", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(new LocalizedText("The type for variable that represents a property of another node.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.BaseDataVariableType, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.BaseDataVariableType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.VariableType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName, new DataValue(new Variant(new QualifiedName("BaseDataVariableType")),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("BaseDataVariableType", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(
                  new LocalizedText("The type for variable that represents a process value.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.Server, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.Server), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("Server")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName, new DataValue(new Variant(new LocalizedText("Server", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.EventNotifier,
              new DataValue(new Variant(new Byte((byte) 1)), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.ServerType, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.ServerType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ObjectType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("ServerType")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("ServerType", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(new LocalizedText("Specifies the current status and capabilities of the server.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.HasComponent, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.HasComponent), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("HasComponent")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("HasComponent", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(
                  new LocalizedText("The type for non-looping hierarchical reference from a node to its component.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName,
              new DataValue(new Variant(new LocalizedText("ComponentOf", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.Server_ServerStatus_CurrentTime, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.Value, new DataValue(new Variant(serverTimeStamp), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.Server_ServerCapabilities_LocaleIdArray,
          new HashMap<UnsignedInteger, DataValue>() {
            {
              put(Attributes.Value, new DataValue(new Variant(new String[1]), StatusCode.GOOD, null, serverTimeStamp));
            }
          });

      onReadResultsMap.put(Identifiers.Server_ServerStatus, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.Server_ServerStatus), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Variable), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("ServerStatus")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("ServerStatus", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(new LocalizedText("The current status of the server.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Value, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.Server_ServerStatus_State, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId, new DataValue(new Variant(Identifiers.Server_ServerStatus_State), StatusCode.GOOD,
              null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Variable), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("State")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName, new DataValue(new Variant(new LocalizedText("State", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description, new DataValue(new Variant(new LocalizedText("", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Value,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
        }
      });
      final String applicationURI = application.getApplicationUri();
      onReadResultsMap.put(Identifiers.Server_NamespaceArray, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.Server_NamespaceArray), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Variable), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("NamespaceArray")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("NamespaceArray", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(
                      new LocalizedText("The list of namespace URIs used by the server.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Value,
              new DataValue(new Variant(new String[] {"http://opcfoundation.org/UA/", applicationURI}), StatusCode.GOOD,
                  null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.Server_ServerArray, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.Server_ServerArray), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Variable), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("ServerArray")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("ServerArray", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(
                      new LocalizedText("The list of server URIs used by the server.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Value, new DataValue(new Variant(new String[] {applicationURI}), StatusCode.GOOD,
              serverTimeStamp, serverTimeStamp));
        }
      });
      onReadResultsMap.put(Identifiers.Server_ServerStatus_BuildInfo_ProductName,
          new HashMap<UnsignedInteger, DataValue>() {
            {
              put(Attributes.Value,
                  new DataValue(new Variant("SampleNanoServer"), StatusCode.GOOD, null, serverTimeStamp));
            }
          });
      onReadResultsMap.put(Identifiers.Server_ServerStatus_BuildInfo_ManufacturerName,
          new HashMap<UnsignedInteger, DataValue>() {
            {
              put(Attributes.Value, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
            }
          });
      onReadResultsMap.put(Identifiers.Server_ServerStatus_BuildInfo_SoftwareVersion,
          new HashMap<UnsignedInteger, DataValue>() {
            {
              put(Attributes.Value, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
            }
          });
      onReadResultsMap.put(Identifiers.Server_ServerStatus_BuildInfo_BuildDate,
          new HashMap<UnsignedInteger, DataValue>() {
            {
              put(Attributes.Value, new DataValue(new Variant(DateTime.parseDateTime("2014-12-30T00:00:00Z")),
                  StatusCode.GOOD, null, serverTimeStamp));
            }
          });

      onReadResultsMap.put(Identifiers.Server_ServerStatus_StartTime, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.Value, new DataValue(new Variant(serverTimeStamp), StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.Server_ServerStatus_SecondsTillShutdown,
          new HashMap<UnsignedInteger, DataValue>() {
            {
              put(Attributes.Value, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
            }
          });

      onReadResultsMap.put(Identifiers.Server_ServerStatus_ShutdownReason, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.Value, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.Server_ServerStatus_BuildInfo, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.Value, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.Server_ServerCapabilities, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId, new DataValue(new Variant(Identifiers.Server_ServerCapabilities), StatusCode.GOOD,
              null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName, new DataValue(new Variant(new QualifiedName("ServerCapabilities")),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("ServerCapabilities", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(
                  new LocalizedText("Describes the capabilities supported by the server.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.EventNotifier,
              new DataValue(new Variant(new Byte((byte) 0)), StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.Server_ServerCapabilities_MaxBrowseContinuationPoints,
          new HashMap<UnsignedInteger, DataValue>() {
            {
              put(Attributes.NodeId,
                  new DataValue(new Variant(Identifiers.Server_ServerCapabilities_MaxBrowseContinuationPoints),
                      StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.NodeClass,
                  new DataValue(new Variant(NodeClass.Variable), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.BrowseName, new DataValue(new Variant(new QualifiedName("MaxBrowseContinuationPoints")),
                  StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.DisplayName,
                  new DataValue(new Variant(new LocalizedText("MaxBrowseContinuationPoints", LocalizedText.NO_LOCALE)),
                      StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.Description,
                  new DataValue(new Variant(
                      new LocalizedText("The maximum number of continuation points for Browse operations per session.",
                          LocalizedText.NO_LOCALE)),
                      StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.WriteMask,
                  new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.UserWriteMask,
                  new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.Value,
                  new DataValue(new Variant(new UnsignedInteger(1)), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.DataType,
                  new DataValue(new Variant(Identifiers.UInt16), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.ValueRank, new DataValue(new Variant(-2), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.ArrayDimensions, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.AccessLevel,
                  new DataValue(new Variant(AccessLevel.CurrentRead), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.UserAccessLevel,
                  new DataValue(new Variant(AccessLevel.CurrentRead), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.MinimumSamplingInterval,
                  new DataValue(new Variant(0.0), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.Historizing, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
            }
          });

      onReadResultsMap.put(Identifiers.Server_ServerDiagnostics_EnabledFlag, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId, new DataValue(new Variant(Identifiers.Server_ServerDiagnostics_EnabledFlag),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.Variable), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("EnabledFlag")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("EnabledFlag", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(
                      new LocalizedText("If TRUE the diagnostics collection is enabled.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Value, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DataType,
              new DataValue(new Variant(Identifiers.Boolean), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.ValueRank, new DataValue(new Variant(-2), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.ArrayDimensions, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.AccessLevel,
              new DataValue(new Variant(AccessLevel.CurrentRead), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserAccessLevel,
              new DataValue(new Variant(AccessLevel.CurrentRead), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.MinimumSamplingInterval,
              new DataValue(new Variant(0.0), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Historizing, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
        }
      });

      onReadResultsMap.put(Identifiers.References, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.References), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("References")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("References", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(new LocalizedText("The abstract base type for all references.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(true), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName, new DataValue(null, StatusCode.GOOD, null, serverTimeStamp));

        }
      });

      onReadResultsMap.put(Identifiers.NonHierarchicalReferences, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId, new DataValue(new Variant(Identifiers.NonHierarchicalReferences), StatusCode.GOOD,
              null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName, new DataValue(new Variant(new QualifiedName("NonHierarchicalReferences")),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("NonHierarchicalReferences", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(new LocalizedText("The abstract base type for all non-hierarchical references.",
                  LocalizedText.NO_LOCALE)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName,
              new DataValue(new Variant(new LocalizedText("NonHierarchicalReferences", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));

        }
      });
      onReadResultsMap.put(Identifiers.HierarchicalReferences, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.HierarchicalReferences), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName, new DataValue(new Variant(new QualifiedName("HierarchicalReferences")),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("HierarchicalReferences", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description, new DataValue(new Variant(
              new LocalizedText("The abstract base type for all hierarchical references.", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName,
              new DataValue(new Variant(new LocalizedText("HierarchicalReferences", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));

        }
      });

      onReadResultsMap.put(Identifiers.HasSubtype, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.HasSubtype), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("HasSubtype")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("HasSubtype", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(new LocalizedText(
                  "The type for non-looping hierarchical references that are used to define sub types.",
                  LocalizedText.NO_LOCALE)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName,
              new DataValue(new Variant(new LocalizedText("HasSupertype", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));

        }
      });
      onReadResultsMap.put(Identifiers.HasProperty, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.HasProperty), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("HasProperty")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("HasProperty", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(
                  new LocalizedText("The type for non-looping hierarchical reference from a node to its property.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName,
              new DataValue(new Variant(new LocalizedText("PropertyOf", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));

        }
      });

      onReadResultsMap.put(Identifiers.HasChild, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.HasChild), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("HasChild")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName, new DataValue(new Variant(new LocalizedText("HasChild", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(new LocalizedText("The abstract base type for all non-looping hierarchical references.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName, new DataValue(new Variant(new LocalizedText("ChildOf", LocalizedText.NO_LOCALE)),
              StatusCode.GOOD, null, serverTimeStamp));

        }
      });

      onReadResultsMap.put(Identifiers.Organizes, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.Organizes), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName,
              new DataValue(new Variant(new QualifiedName("Organizes")), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("Organizes", LocalizedText.NO_LOCALE)), StatusCode.GOOD, null,
                  serverTimeStamp));
          put(Attributes.Description,
              new DataValue(
                  new Variant(new LocalizedText("The type for hierarchical references that are used to organize nodes.",
                      LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName,
              new DataValue(new Variant(new LocalizedText("OrganizedBy", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                  null, serverTimeStamp));

        }
      });

      onReadResultsMap.put(Identifiers.HasTypeDefinition, new HashMap<UnsignedInteger, DataValue>() {
        {
          put(Attributes.NodeId,
              new DataValue(new Variant(Identifiers.HasTypeDefinition), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.NodeClass,
              new DataValue(new Variant(NodeClass.ReferenceType), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.BrowseName, new DataValue(new Variant(new QualifiedName("HasTypeDefinition")), StatusCode.GOOD,
              null, serverTimeStamp));
          put(Attributes.DisplayName,
              new DataValue(new Variant(new LocalizedText("HasTypeDefinition", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Description,
              new DataValue(new Variant(new LocalizedText(
                  "The type for references from a instance node its type definition node.", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.WriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.UserWriteMask,
              new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.IsAbstract, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.Symmetric, new DataValue(new Variant(false), StatusCode.GOOD, null, serverTimeStamp));
          put(Attributes.InverseName,
              new DataValue(new Variant(new LocalizedText("TypeDefinitionOf", LocalizedText.NO_LOCALE)),
                  StatusCode.GOOD, null, serverTimeStamp));

        }
      });

      onReadResultsMap.put(new NodeId(complianceNamespaceIndex, "StaticData"),
          new HashMap<UnsignedInteger, DataValue>() {
            {
              put(Attributes.NodeId, new DataValue(new Variant(new NodeId(complianceNamespaceIndex, "StaticData")),
                  StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.NodeClass,
                  new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.BrowseName,
                  new DataValue(new Variant(new QualifiedName(complianceNamespaceIndex + ":StaticData")),
                      StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.DisplayName,
                  new DataValue(new Variant(new LocalizedText("StaticData", LocalizedText.NO_LOCALE)), StatusCode.GOOD,
                      null, serverTimeStamp));
              put(Attributes.Description,
                  new DataValue(new Variant(
                      new LocalizedText("The type for objects that organize other nodes.", LocalizedText.NO_LOCALE)),
                      StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.WriteMask,
                  new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.UserWriteMask,
                  new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.EventNotifier, new DataValue(new Variant(0), StatusCode.GOOD, null, serverTimeStamp));
            }
          });
      onReadResultsMap.put(new NodeId(complianceNamespaceIndex, "StaticVariablesFolder"),
          new HashMap<UnsignedInteger, DataValue>() {
            {
              put(Attributes.NodeId,
                  new DataValue(new Variant(new NodeId(complianceNamespaceIndex, "StaticVariablesFolder")),
                      StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.NodeClass,
                  new DataValue(new Variant(NodeClass.Object), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.BrowseName,
                  new DataValue(new Variant(new QualifiedName(complianceNamespaceIndex + ":StaticVariables")),
                      StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.DisplayName,
                  new DataValue(new Variant(new LocalizedText("StaticVariables", LocalizedText.NO_LOCALE)),
                      StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.Description,
                  new DataValue(new Variant(
                      new LocalizedText("The type for objects that organize other nodes.", LocalizedText.NO_LOCALE)),
                      StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.WriteMask,
                  new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.UserWriteMask,
                  new DataValue(new Variant(new UnsignedInteger(0)), StatusCode.GOOD, null, serverTimeStamp));
              put(Attributes.EventNotifier, new DataValue(new Variant(0), StatusCode.GOOD, null, serverTimeStamp));
            }
          });

      // *******************************************************************************
      // Put all data type mappings in one HashMap for better readability and performance
      // Only boolean supported at the moment
      // *******************************************************************************
      datatypeMap = new HashMap<NodeId, Class<?>>() {
        {
          put(Identifiers.Boolean, java.lang.Boolean.class);
        }
      };
      //////////////////////////////////////
    }

    @Override
    public void onActivateSession(EndpointServiceRequest<ActivateSessionRequest, ActivateSessionResponse> msgExchange)
        throws ServiceFaultException {

      ActivateSessionRequest request = msgExchange.getRequest();

      StatusCode statusCode = null;
      ActivateSessionResponse response = new ActivateSessionResponse();

      RequestHeader requestHeader = request.getRequestHeader();
      NodeId authenticationToken = requestHeader.getAuthenticationToken();
      if (!sessions.contains(authenticationToken)) {
        // This session is not valid
        statusCode = new StatusCode(StatusCodes.Bad_SessionClosed);
      }
      if (statusCode == null) {
        final ExtensionObject encodedToken = request.getUserIdentityToken();
        UserIdentityToken token = null;
        if (encodedToken != null) {
          try {
            token = encodedToken.decode(getEncoderContext());
            if (token.getTypeId().equals(new ExpandedNodeId(Identifiers.X509IdentityToken_Encoding_DefaultBinary)))
              statusCode = new StatusCode(StatusCodes.Bad_IdentityTokenInvalid);
          } catch (DecodingException e) {
            statusCode = new StatusCode(StatusCodes.Bad_IdentityTokenInvalid);
          }
        }

        if (timeoutPeriods != null && authenticationToken != null && timeoutPeriods.get(authenticationToken) != null) {

          Long timeToTimeout = timeoutPeriods.get(authenticationToken);

          Long now = System.currentTimeMillis();

          if (timeToTimeout < now) {
            statusCode = new StatusCode(StatusCodes.Bad_SessionClosed);
            validAuthenticationTokens.remove(authenticationToken);
            timeoutPeriods.remove(authenticationToken);

          }
        } else {
          statusCode = new StatusCode(StatusCodes.Bad_SessionIdInvalid);
        }
      }
      if (statusCode == null) {
        try {

          IEncodeable uit = request.getUserIdentityToken().decode(getEncoderContext());

          if (uit instanceof UserNameIdentityToken) {
            UserNameIdentityToken userNameIdentityToken = (UserNameIdentityToken) uit;
            String userName = userNameIdentityToken.getUserName();
            String policyId = userNameIdentityToken.getPolicyId();
            String encryptionAlgorithm = userNameIdentityToken.getEncryptionAlgorithm();

            if (userName == null) {
              statusCode = new StatusCode(StatusCodes.Bad_IdentityTokenInvalid);
            } else if (userName.equals("username")) {
              statusCode = new StatusCode(StatusCodes.Bad_UserAccessDenied);
            } else if (!userName.equals("user1") && !userName.equals("user2")) {
              statusCode = new StatusCode(StatusCodes.Bad_IdentityTokenRejected);
            }

            // Checking that policy id and encryption algorithm are
            // valid.
            // Add all supported policy ids and encryption
            // algorithms here.
            if (policyId == null || !policyId.equals("username_basic128") || encryptionAlgorithm == null
                || !encryptionAlgorithm.equals("http://www.w3.org/2001/04/xmlenc#rsa-1_5")) {
              statusCode = new StatusCode(StatusCodes.Bad_IdentityTokenInvalid);
            } else if (statusCode == null) {
              // user is user1 or user2, decrypt the password and
              // check password correctness

              PrivateKey pk = application.getApplicationInstanceCertificate().privateKey.getPrivateKey();
              ByteString dataToDecrypt = userNameIdentityToken.getPassword();
              // SunJceCryptoProvider needs buffer of at least 256
              // bytes
              byte[] output = new byte[256];
              int outputOffset = 0;

              CryptoUtil.getCryptoProvider().decryptAsymm(pk, SecurityAlgorithm.Rsa15, dataToDecrypt.getValue(), output,
                  outputOffset);

              int count = 11; // Hard-coded for now. CTT only uses
              // passwords that are 8
              // characters...
              String plaintextPassword = new String(output, 1, count).trim();

              // These usernames and passwords are defined in CTT
              // settings
              if ((userName.equals("user1") && !plaintextPassword.equals("p4ssword"))
                  || (userName.equals("user2") && !plaintextPassword.equals("passw0rd"))) {
                statusCode = new StatusCode(StatusCodes.Bad_UserAccessDenied);
              }

            }
          }

        } catch (DecodingException e) {
          // Auto-generated catch block
          e.printStackTrace();
        } catch (ServiceResultException e) {
          // Auto-generated catch block
          e.printStackTrace();
        }

      }
      response.setServerNonce(CryptoUtil.createNonce(32));

      if (statusCode == null) {
        statusCode = StatusCode.GOOD;
        validAuthenticationTokens.add(authenticationToken);
      }
      ResponseHeader h = new ResponseHeader(DateTime.currentTime(), requestHeader.getRequestHandle(), statusCode, null,
          getApplication().getLocaleIds(), null);
      response.setResponseHeader(h);

      msgExchange.sendResponse(response);
    }

    @Override
    public void onCancel(EndpointServiceRequest<CancelRequest, CancelResponse> msgExchange)
        throws ServiceFaultException {
      CancelResponse response = new CancelResponse();
      ResponseHeader h = new ResponseHeader(DateTime.currentTime(), msgExchange.getRequest().getRequestHandle(),
          new StatusCode(StatusCodes.Bad_NotSupported), null, getApplication().getLocaleIds(), null);
      response.setResponseHeader(h);

      msgExchange.sendResponse(response);
    }

    @Override
    public void onCloseSession(EndpointServiceRequest<CloseSessionRequest, CloseSessionResponse> msgExchange)
        throws ServiceFaultException {
      CloseSessionResponse res = new CloseSessionResponse();
      CloseSessionRequest req = msgExchange.getRequest();

      ResponseHeader h = checkRequestHeader(req.getRequestHeader());

      // take authentication token out of valid tokens
      validAuthenticationTokens.remove(req.getRequestHeader().getAuthenticationToken());
      sessions.remove(req.getRequestHeader().getAuthenticationToken());

      // Set continuation point to null, this also means that more than
      // one concurrent sessions cannot use continuation points
      continuationPoint = null;

      res.setResponseHeader(h);

      msgExchange.sendResponse(res);
    }

    @Override
    public void onCreateSession(EndpointServiceRequest<CreateSessionRequest, CreateSessionResponse> msgExchange)
        throws ServiceFaultException {
      CreateSessionRequest request = msgExchange.getRequest();
      CreateSessionResponse response = new CreateSessionResponse();

      StatusCode statusCode = null;
      byte[] token = new byte[32];
      byte[] nonce = new byte[32];
      Random r = new Random();
      r.nextBytes(nonce);
      r.nextBytes(token);

      // Check client nonce
      ByteString clientNonce = request.getClientNonce();
      if (clientNonce != null) {
        if (clientNonce.getLength() < 32) {
          statusCode = new StatusCode(StatusCodes.Bad_NonceInvalid);
        }
      }
      ByteString clientCertificate = request.getClientCertificate();
      if (clientCertificate != null) {
        String clientApplicationUri = request.getClientDescription().getApplicationUri();
        X509Certificate clientCertificateDecoded = null;
        String applicationUriOfDecoded = null;
        try {
          clientCertificateDecoded = CertificateUtils.decodeX509Certificate(clientCertificate.getValue());
          applicationUriOfDecoded = CertificateUtils.getApplicationUriOfCertificate(clientCertificateDecoded);
        } catch (CertificateException e) {
          e.printStackTrace();
        }

        if (!clientApplicationUri.equals(applicationUriOfDecoded)) {
          statusCode = new StatusCode(StatusCodes.Bad_CertificateUriInvalid);
        }
      }

      if (statusCode == null) {

        EndpointConfiguration endpointConfiguration = EndpointConfiguration.defaults();
        response.setMaxRequestMessageSize(UnsignedInteger.valueOf(
            Math.max(endpointConfiguration.getMaxMessageSize(), request.getMaxResponseMessageSize().longValue())));

        Double timeout = new Double(60 * 1000);
        if (!request.getRequestedSessionTimeout().equals(new Double(0))) {
          // set revised session timeout to 60 seconds or to lower
          // value if client requests
          timeout = Math.min(request.getRequestedSessionTimeout(), 60 * 1000);
        }
        response.setRevisedSessionTimeout(timeout);

        NodeId tokenId = new NodeId(0, token);
        response.setAuthenticationToken(tokenId);
        // Put authentication to memory in order to check validity of
        // incoming authentication tokens
        sessions.add(tokenId);
        Long time = System.currentTimeMillis();
        timeoutPeriods.put(tokenId, time + timeout.longValue());
      }

      KeyPair cert = getApplication().getApplicationInstanceCertificates()[0];
      response.setServerCertificate(ByteString.valueOf(cert.getCertificate().getEncoded()));
      response.setServerEndpoints(this.getEndpointDescriptions());
      response.setServerNonce(ByteString.valueOf(nonce));

      SecurityPolicy securityPolicy = msgExchange.getChannel().getSecurityPolicy();
      response.setServerSignature(
          getServerSignature(clientCertificate, clientNonce, securityPolicy, cert.getPrivateKey().getPrivateKey()));

      response.setServerSoftwareCertificates(getApplication().getSoftwareCertificates());
      response.setSessionId(new NodeId(0, "Session-" + UUID.randomUUID()));

      if (statusCode == null) {
        statusCode = StatusCode.GOOD;
      }
      // Set response header
      ResponseHeader h = new ResponseHeader(DateTime.currentTime(), request.getRequestHeader().getRequestHandle(),
          statusCode, null, getApplication().getLocaleIds(), null);
      response.setResponseHeader(h);

      msgExchange.sendResponse(response);
    }

    private SignatureData getServerSignature(ByteString clientCertificate, ByteString clientNonce,
        SecurityPolicy securityPolicy, final RSAPrivateKey privateKey) throws ServiceFaultException {
      if (clientCertificate != null) {
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
          s.write(clientCertificate.getValue());
        } catch (IOException e) {
          throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_SecurityChecksFailed));
        } catch (Exception e) {
          throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NonceInvalid));
        }
        try {
          s.write(clientNonce.getValue());
        } catch (IOException e) {
          throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NonceInvalid));
        } catch (Exception e) {
          throw new ServiceFaultException(ServiceFault.createServiceFault(StatusCodes.Bad_NonceInvalid));
        }
        try {
          SecurityAlgorithm algorithm = securityPolicy.getAsymmetricSignatureAlgorithm();
          if (algorithm == null) {
            algorithm = SecurityAlgorithm.RsaSha1;
          }
          return new SignatureData(algorithm.getUri(),
              ByteString.valueOf(CryptoUtil.getCryptoProvider().signAsymm(privateKey, algorithm, s.toByteArray())));
        } catch (ServiceResultException e) {
          throw new ServiceFaultException(e);
        }
      }
      return null;
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(NanoServer.class);
  private static int complianceNamespaceIndex = 1;
  static Map<NodeId, BrowseResult> onBrowseActions;
  static Map<NodeId, Map<UnsignedInteger, DataValue>> onReadResultsMap;
  static Map<NodeId, Class<?>> datatypeMap;

  static ArrayList<NodeId> sessions;

  static ArrayList<NodeId> validAuthenticationTokens;

  static Map<NodeId, Long> timeoutPeriods;

  static ContinuationPoint continuationPoint;

  static NanoServerExample nanoServer;

  /**
   * Check request header contents and return response header accordingly.
   *
   * @param requestHeader the request header to check.
   * @return ResponseHeader
   *
   */
  public static ResponseHeader checkRequestHeader(RequestHeader requestHeader) {
    // set responseheader to StatusCode.GOOD by default.
    ResponseHeader h =
        new ResponseHeader(DateTime.currentTime(), requestHeader.getRequestHandle(), StatusCode.GOOD, null, null, null);

    if (NodeId.isNull(requestHeader.getAuthenticationToken())
        || !validAuthenticationTokens.contains(requestHeader.getAuthenticationToken())) {
      // AuthenticationToken was null or invalid
      if (sessions.contains(requestHeader.getAuthenticationToken())) {
        // Session is created but not activated
        h = new ResponseHeader(DateTime.currentTime(), requestHeader.getRequestHandle(),
            new StatusCode(StatusCodes.Bad_SessionNotActivated), null, null, null);
        // This is an error condition: close this session
        sessions.remove(requestHeader.getAuthenticationToken());
      } else {
        h = new ResponseHeader(DateTime.currentTime(), requestHeader.getRequestHandle(),
            new StatusCode(StatusCodes.Bad_SessionIdInvalid), null, null, null);
      }

    } else if (requestHeader.getTimestamp().equals(new DateTime(0))) {
      // Timestamp is now only checked with value 0
      // TimeStamp was not valid
      h = new ResponseHeader(DateTime.currentTime(), requestHeader.getRequestHandle(),
          new StatusCode(StatusCodes.Bad_InvalidTimestamp), null, null, null);

    }
    return h;
  }

  /**
   * Return node by browsing from starting node and ending in target name.
   *
   * @param startingNode the request header to check.
   * @param referenceTypeId reference types to follow. See also includeSubtypes parameter.
   * @param browseDirection tells whether to check forward or inverse references.
   * @param includeSubtypes browse subtypes or not.
   * @return BrowseResult
   *
   */
  public static BrowseResult getBrowsePathTarget(NodeId startingNode, NodeId referenceTypeId,
      BrowseDirection browseDirection, Boolean includeSubtypes) {

    BrowseResult nextBrowseResult = onBrowseActions.get(startingNode).clone();

    if (browseDirection.equals(BrowseDirection.Inverse)) {
      // return only inverse references
      ReferenceDescription[] referenceDescriptions = nextBrowseResult.getReferences();
      List<ReferenceDescription> newReferenceDescriptions = new ArrayList<ReferenceDescription>();

      if (referenceDescriptions != null) {
        for (int j = 0; j < referenceDescriptions.length; j++) {
          if (!referenceDescriptions[j].getIsForward()) {
            newReferenceDescriptions.add(referenceDescriptions[j]);
          }
        }
        nextBrowseResult
            .setReferences(newReferenceDescriptions.toArray(new ReferenceDescription[newReferenceDescriptions.size()]));
      }
    } else if (browseDirection.equals(BrowseDirection.Forward)) {
      // return only forward references
      ReferenceDescription[] referenceDescriptions = nextBrowseResult.getReferences();
      List<ReferenceDescription> newReferenceDescriptions = new ArrayList<ReferenceDescription>();

      if (referenceDescriptions != null) {
        for (int j = 0; j < referenceDescriptions.length; j++) {
          if (referenceDescriptions[j].getIsForward()) {
            newReferenceDescriptions.add(referenceDescriptions[j]);
          }
        }
        nextBrowseResult
            .setReferences(newReferenceDescriptions.toArray(new ReferenceDescription[newReferenceDescriptions.size()]));
      }
    }
    // OPC UA standard part 3, page 56 illustrates hierarchy of reference
    // types
    // Current implementation only takes into account References,
    // HierarchicalReferences, NonHierarchicalReferences and HasChild. For
    // example HasEventSource -> HasNotifier relation won't work.
    // ReferenceTypeId=i=32 -> NonHierarchicalReferences
    if (Identifiers.NonHierarchicalReferences.equals(referenceTypeId)) {
      // NonHierarchicalReferences requested
      ReferenceDescription[] referenceDescriptions = nextBrowseResult.getReferences();
      List<ReferenceDescription> newReferenceDescriptions = new ArrayList<ReferenceDescription>();

      if (referenceDescriptions != null) {
        for (int j = 0; j < referenceDescriptions.length; j++) {
          ReferenceDescription rd = referenceDescriptions[j];
          if (includeSubtypes && (Identifiers.GeneratesEvent.equals(rd.getReferenceTypeId())
              || Identifiers.AlwaysGeneratesEvent.equals(rd.getReferenceTypeId())
              || Identifiers.HasEncoding.equals(rd.getReferenceTypeId())
              || Identifiers.HasModellingRule.equals(rd.getReferenceTypeId())
              || Identifiers.HasDescription.equals(rd.getReferenceTypeId())
              || Identifiers.HasTypeDefinition.equals(rd.getReferenceTypeId()))) {
            newReferenceDescriptions.add(rd);
          } else if (!includeSubtypes && Identifiers.NonHierarchicalReferences.equals(rd.getReferenceTypeId())) {
            // return only references of type
            // NonHierarchicalReferences (i=32), do not include sub
            // types
            newReferenceDescriptions.add(rd);
          }
        }
        nextBrowseResult
            .setReferences(newReferenceDescriptions.toArray(new ReferenceDescription[newReferenceDescriptions.size()]));
      }
    } else if (Identifiers.HierarchicalReferences.equals(referenceTypeId)) {
      // ReferenceTypeId=i=33
      ReferenceDescription[] referenceDescriptions = nextBrowseResult.getReferences();
      List<ReferenceDescription> newReferenceDescriptions = new ArrayList<ReferenceDescription>();

      if (referenceDescriptions != null) {
        for (int j = 0; j < referenceDescriptions.length; j++) {
          ReferenceDescription rd = referenceDescriptions[j];
          if (includeSubtypes && (Identifiers.HasComponent.equals(rd.getReferenceTypeId())
              || Identifiers.HasProperty.equals(rd.getReferenceTypeId())
              || Identifiers.HasOrderedComponent.equals(rd.getReferenceTypeId())
              || Identifiers.HasSubtype.equals(rd.getReferenceTypeId())
              || Identifiers.Organizes.equals(rd.getReferenceTypeId())
              || Identifiers.HasEventSource.equals(rd.getReferenceTypeId())
              || Identifiers.HasNotifier.equals(rd.getReferenceTypeId()))) {
            newReferenceDescriptions.add(rd);
          } else if (!includeSubtypes && Identifiers.HierarchicalReferences.equals(rd.getReferenceTypeId())) {
            // return only references of type HierarchicalReferences
            // (i=33), specified here separately
            newReferenceDescriptions.add(rd);
          }
        }
        nextBrowseResult
            .setReferences(newReferenceDescriptions.toArray(new ReferenceDescription[newReferenceDescriptions.size()]));
      }
    } else if (Identifiers.HasChild.equals(referenceTypeId)) {
      // ReferenceTypeId=i=34
      ReferenceDescription[] referenceDescriptions = nextBrowseResult.getReferences();
      List<ReferenceDescription> newReferenceDescriptions = new ArrayList<ReferenceDescription>();

      if (referenceDescriptions != null) {
        for (int j = 0; j < referenceDescriptions.length; j++) {
          ReferenceDescription rd = referenceDescriptions[j];
          if (includeSubtypes && (Identifiers.HasComponent.equals(rd.getReferenceTypeId())
              || Identifiers.HasProperty.equals(rd.getReferenceTypeId())
              || Identifiers.HasOrderedComponent.equals(rd.getReferenceTypeId())
              || Identifiers.HasSubtype.equals(rd.getReferenceTypeId())
              || Identifiers.Aggregates.equals(rd.getReferenceTypeId()))) {
            newReferenceDescriptions.add(rd);
          } else if (!includeSubtypes && Identifiers.HasChild.equals(rd.getReferenceTypeId())) {
            // return only references of type HierarchicalReferences
            // (i=33), specified here separately
            newReferenceDescriptions.add(rd);
          }
        }
        nextBrowseResult
            .setReferences(newReferenceDescriptions.toArray(new ReferenceDescription[newReferenceDescriptions.size()]));
      }
      // ReferenceTypeId=i=31 means that all references should be
      // returned. If some other ReferenceTypeId is specified, then only
      // return that references with that ReferenceTypeId.
    } else if (!(referenceTypeId.equals(Identifiers.References)) && !(NodeId.isNull(referenceTypeId))) {
      ReferenceDescription[] referenceDescriptions = nextBrowseResult.getReferences();
      List<ReferenceDescription> newReferenceDescriptions = new ArrayList<ReferenceDescription>();

      if (referenceDescriptions != null) {
        for (int j = 0; j < referenceDescriptions.length; j++) {
          ReferenceDescription rd = referenceDescriptions[j];
          if ((rd.getReferenceTypeId().equals(referenceTypeId))) {
            newReferenceDescriptions.add(rd);
          }
        }
        nextBrowseResult
            .setReferences(newReferenceDescriptions.toArray(new ReferenceDescription[newReferenceDescriptions.size()]));
      }
    }

    return nextBrowseResult;
  }

  /**
   * Convenience method wrapping different reference types.
   * 
   * @param referenceTypeId NodeId type to request.
   * @return boolean true if parameter NodeId is some reference type. Otherwise return false.
   */
  public static boolean isReferenceType(NodeId referenceTypeId) {

    if (referenceTypeId.equals(Identifiers.References) || referenceTypeId.equals(Identifiers.NonHierarchicalReferences)
        || referenceTypeId.equals(Identifiers.HierarchicalReferences)
        || referenceTypeId.equals(Identifiers.HasEventSource) || referenceTypeId.equals(Identifiers.HasNotifier)
        || referenceTypeId.equals(Identifiers.Organizes) || referenceTypeId.equals(Identifiers.HasChild)
        || referenceTypeId.equals(Identifiers.HasSubtype) || referenceTypeId.equals(Identifiers.Aggregates)
        || referenceTypeId.equals(Identifiers.HasProperty) || referenceTypeId.equals(Identifiers.HasComponent)
        || referenceTypeId.equals(Identifiers.HasOrderedComponent) || referenceTypeId.equals(Identifiers.GeneratesEvent)
        || referenceTypeId.equals(Identifiers.AlwaysGeneratesEvent) || referenceTypeId.equals(Identifiers.HasEncoding)
        || referenceTypeId.equals(Identifiers.HasModellingRule) || referenceTypeId.equals(Identifiers.HasDescription)
        || referenceTypeId.equals(Identifiers.HasTypeDefinition)) {
      return true;
    }
    return false;

  }

  public static void main(String[] args) throws Exception {
    // //////////// SERVER //////////////
    // Create UA Server Application
    // Create UA Service Server
    String applicationName = "NanoServer";
    Application myServerApplication = new Application();
    Locale myLocale = new Locale("en");
    LocalizedText myApplicationDescription = new LocalizedText(applicationName, myLocale);
    myServerApplication.setApplicationName(myApplicationDescription);
    myServerApplication.setProductUri("urn:opcfoundation.org:OPCUA:" + applicationName);
    // set custom application URI and not default randomUUID
    String publicHostname = "";
    try {
      publicHostname = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
    }
    myServerApplication.setApplicationUri("urn:" + publicHostname + ":NanoServer");

    nanoServer = new NanoServerExample(myServerApplication);

    nanoServer.addServiceHandler(new MyNodeManagementServiceHandler());
    nanoServer.addServiceHandler(new MyAttributeServiceHandler());
    nanoServer.addServiceHandler(new FindServersServiceHandler());

    CryptoUtil.setCryptoProvider(new BcCryptoProvider());
    // ////////////////////////////////////
    // Press enter to shutdown
    System.out.println("Press enter to shutdown");
    System.in.read();
    // ////////////////////////////////////

    // /////////// SHUTDOWN /////////////
    // Close the server by unbinding all endpoints
    nanoServer.getApplication().close();
    // ////////////////////////////////////

  }

}
