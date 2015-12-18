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

import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import java.util.Arrays;
import org.opcfoundation.ua.builtintypes.ExtensionObject;
import org.opcfoundation.ua.core.RequestHeader;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.SignedSoftwareCertificate;


public class ActivateSessionRequest extends Object implements ServiceRequest {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ActivateSessionRequest);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ActivateSessionRequest_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ActivateSessionRequest_Encoding_DefaultXml);
	
    protected RequestHeader RequestHeader;
    protected SignatureData ClientSignature;
    protected SignedSoftwareCertificate[] ClientSoftwareCertificates;
    protected String[] LocaleIds;
    protected ExtensionObject UserIdentityToken;
    protected SignatureData UserTokenSignature;
    
    public ActivateSessionRequest() {}
    
    public ActivateSessionRequest(RequestHeader RequestHeader, SignatureData ClientSignature, SignedSoftwareCertificate[] ClientSoftwareCertificates, String[] LocaleIds, ExtensionObject UserIdentityToken, SignatureData UserTokenSignature)
    {
        this.RequestHeader = RequestHeader;
        this.ClientSignature = ClientSignature;
        this.ClientSoftwareCertificates = ClientSoftwareCertificates;
        this.LocaleIds = LocaleIds;
        this.UserIdentityToken = UserIdentityToken;
        this.UserTokenSignature = UserTokenSignature;
    }
    
    public RequestHeader getRequestHeader()
    {
        return RequestHeader;
    }
    
    public void setRequestHeader(RequestHeader RequestHeader)
    {
        this.RequestHeader = RequestHeader;
    }
    
    public SignatureData getClientSignature()
    {
        return ClientSignature;
    }
    
    public void setClientSignature(SignatureData ClientSignature)
    {
        this.ClientSignature = ClientSignature;
    }
    
    public SignedSoftwareCertificate[] getClientSoftwareCertificates()
    {
        return ClientSoftwareCertificates;
    }
    
    public void setClientSoftwareCertificates(SignedSoftwareCertificate[] ClientSoftwareCertificates)
    {
        this.ClientSoftwareCertificates = ClientSoftwareCertificates;
    }
    
    public String[] getLocaleIds()
    {
        return LocaleIds;
    }
    
    public void setLocaleIds(String[] LocaleIds)
    {
        this.LocaleIds = LocaleIds;
    }
    
    public ExtensionObject getUserIdentityToken()
    {
        return UserIdentityToken;
    }
    
    public void setUserIdentityToken(ExtensionObject UserIdentityToken)
    {
        this.UserIdentityToken = UserIdentityToken;
    }
    
    public SignatureData getUserTokenSignature()
    {
        return UserTokenSignature;
    }
    
    public void setUserTokenSignature(SignatureData UserTokenSignature)
    {
        this.UserTokenSignature = UserTokenSignature;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ActivateSessionRequest
      */
    public ActivateSessionRequest clone()
    {
        ActivateSessionRequest result = new ActivateSessionRequest();
        result.RequestHeader = RequestHeader==null ? null : RequestHeader.clone();
        result.ClientSignature = ClientSignature==null ? null : ClientSignature.clone();
        if (ClientSoftwareCertificates!=null) {
            result.ClientSoftwareCertificates = new SignedSoftwareCertificate[ClientSoftwareCertificates.length];
            for (int i=0; i<ClientSoftwareCertificates.length; i++)
                result.ClientSoftwareCertificates[i] = ClientSoftwareCertificates[i].clone();
        }
        result.LocaleIds = LocaleIds==null ? null : LocaleIds.clone();
        result.UserIdentityToken = UserIdentityToken;
        result.UserTokenSignature = UserTokenSignature==null ? null : UserTokenSignature.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ActivateSessionRequest other = (ActivateSessionRequest) obj;
        if (RequestHeader==null) {
            if (other.RequestHeader != null) return false;
        } else if (!RequestHeader.equals(other.RequestHeader)) return false;
        if (ClientSignature==null) {
            if (other.ClientSignature != null) return false;
        } else if (!ClientSignature.equals(other.ClientSignature)) return false;
        if (ClientSoftwareCertificates==null) {
            if (other.ClientSoftwareCertificates != null) return false;
        } else if (!Arrays.equals(ClientSoftwareCertificates, other.ClientSoftwareCertificates)) return false;
        if (LocaleIds==null) {
            if (other.LocaleIds != null) return false;
        } else if (!Arrays.equals(LocaleIds, other.LocaleIds)) return false;
        if (UserIdentityToken==null) {
            if (other.UserIdentityToken != null) return false;
        } else if (!UserIdentityToken.equals(other.UserIdentityToken)) return false;
        if (UserTokenSignature==null) {
            if (other.UserTokenSignature != null) return false;
        } else if (!UserTokenSignature.equals(other.UserTokenSignature)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((RequestHeader == null) ? 0 : RequestHeader.hashCode());
        result = prime * result
                + ((ClientSignature == null) ? 0 : ClientSignature.hashCode());
        result = prime * result
                + ((ClientSoftwareCertificates == null) ? 0 : Arrays.hashCode(ClientSoftwareCertificates));
        result = prime * result
                + ((LocaleIds == null) ? 0 : Arrays.hashCode(LocaleIds));
        result = prime * result
                + ((UserIdentityToken == null) ? 0 : UserIdentityToken.hashCode());
        result = prime * result
                + ((UserTokenSignature == null) ? 0 : UserTokenSignature.hashCode());
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
