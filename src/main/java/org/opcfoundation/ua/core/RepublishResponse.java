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

import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.core.NotificationMessage;
import org.opcfoundation.ua.core.ResponseHeader;
import org.opcfoundation.ua.utils.AbstractStructure;


public class RepublishResponse extends AbstractStructure implements ServiceResponse {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.RepublishResponse);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.RepublishResponse_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.RepublishResponse_Encoding_DefaultXml);
	
    protected ResponseHeader ResponseHeader;
    protected NotificationMessage NotificationMessage;
    
    public RepublishResponse() {}
    
    public RepublishResponse(ResponseHeader ResponseHeader, NotificationMessage NotificationMessage)
    {
        this.ResponseHeader = ResponseHeader;
        this.NotificationMessage = NotificationMessage;
    }
    
    public ResponseHeader getResponseHeader()
    {
        return ResponseHeader;
    }
    
    public void setResponseHeader(ResponseHeader ResponseHeader)
    {
        this.ResponseHeader = ResponseHeader;
    }
    
    public NotificationMessage getNotificationMessage()
    {
        return NotificationMessage;
    }
    
    public void setNotificationMessage(NotificationMessage NotificationMessage)
    {
        this.NotificationMessage = NotificationMessage;
    }
    
    /**
      * Deep clone
      *
      * @return cloned RepublishResponse
      */
    public RepublishResponse clone()
    {
        RepublishResponse result = (RepublishResponse) super.clone();
        result.ResponseHeader = ResponseHeader==null ? null : ResponseHeader.clone();
        result.NotificationMessage = NotificationMessage==null ? null : NotificationMessage.clone();
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RepublishResponse other = (RepublishResponse) obj;
        if (ResponseHeader==null) {
            if (other.ResponseHeader != null) return false;
        } else if (!ResponseHeader.equals(other.ResponseHeader)) return false;
        if (NotificationMessage==null) {
            if (other.NotificationMessage != null) return false;
        } else if (!NotificationMessage.equals(other.NotificationMessage)) return false;
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
                + ((NotificationMessage == null) ? 0 : NotificationMessage.hashCode());
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
