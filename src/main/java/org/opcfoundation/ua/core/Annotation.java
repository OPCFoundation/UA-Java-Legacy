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
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.utils.AbstractStructure;



public class Annotation extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.Annotation);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.Annotation_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.Annotation_Encoding_DefaultXml);
	
    protected String Message;
    protected String UserName;
    protected DateTime AnnotationTime;
    
    public Annotation() {}
    
    public Annotation(String Message, String UserName, DateTime AnnotationTime)
    {
        this.Message = Message;
        this.UserName = UserName;
        this.AnnotationTime = AnnotationTime;
    }
    
    public String getMessage()
    {
        return Message;
    }
    
    public void setMessage(String Message)
    {
        this.Message = Message;
    }
    
    public String getUserName()
    {
        return UserName;
    }
    
    public void setUserName(String UserName)
    {
        this.UserName = UserName;
    }
    
    public DateTime getAnnotationTime()
    {
        return AnnotationTime;
    }
    
    public void setAnnotationTime(DateTime AnnotationTime)
    {
        this.AnnotationTime = AnnotationTime;
    }
    
    /**
      * Deep clone
      *
      * @return cloned Annotation
      */
    public Annotation clone()
    {
        Annotation result = (Annotation) super.clone();
        result.Message = Message;
        result.UserName = UserName;
        result.AnnotationTime = AnnotationTime;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Annotation other = (Annotation) obj;
        if (Message==null) {
            if (other.Message != null) return false;
        } else if (!Message.equals(other.Message)) return false;
        if (UserName==null) {
            if (other.UserName != null) return false;
        } else if (!UserName.equals(other.UserName)) return false;
        if (AnnotationTime==null) {
            if (other.AnnotationTime != null) return false;
        } else if (!AnnotationTime.equals(other.AnnotationTime)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((Message == null) ? 0 : Message.hashCode());
        result = prime * result
                + ((UserName == null) ? 0 : UserName.hashCode());
        result = prime * result
                + ((AnnotationTime == null) ? 0 : AnnotationTime.hashCode());
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
		return "Annotation: "+ObjectUtils.printFieldsDeep(this);
	}

}
