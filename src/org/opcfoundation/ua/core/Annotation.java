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

import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.DateTime;



public class Annotation extends Object implements Structure, Cloneable {
	
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
        Annotation result = new Annotation();
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
