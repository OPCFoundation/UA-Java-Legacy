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
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.BuildInfo;
import org.opcfoundation.ua.core.ServerState;
import org.opcfoundation.ua.utils.AbstractStructure;



public class ServerStatusDataType extends AbstractStructure {
	
	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers.ServerStatusDataType);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers.ServerStatusDataType_Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers.ServerStatusDataType_Encoding_DefaultXml);
	
    protected DateTime StartTime;
    protected DateTime CurrentTime;
    protected ServerState State;
    protected BuildInfo BuildInfo;
    protected UnsignedInteger SecondsTillShutdown;
    protected LocalizedText ShutdownReason;
    
    public ServerStatusDataType() {}
    
    public ServerStatusDataType(DateTime StartTime, DateTime CurrentTime, ServerState State, BuildInfo BuildInfo, UnsignedInteger SecondsTillShutdown, LocalizedText ShutdownReason)
    {
        this.StartTime = StartTime;
        this.CurrentTime = CurrentTime;
        this.State = State;
        this.BuildInfo = BuildInfo;
        this.SecondsTillShutdown = SecondsTillShutdown;
        this.ShutdownReason = ShutdownReason;
    }
    
    public DateTime getStartTime()
    {
        return StartTime;
    }
    
    public void setStartTime(DateTime StartTime)
    {
        this.StartTime = StartTime;
    }
    
    public DateTime getCurrentTime()
    {
        return CurrentTime;
    }
    
    public void setCurrentTime(DateTime CurrentTime)
    {
        this.CurrentTime = CurrentTime;
    }
    
    public ServerState getState()
    {
        return State;
    }
    
    public void setState(ServerState State)
    {
        this.State = State;
    }
    
    public BuildInfo getBuildInfo()
    {
        return BuildInfo;
    }
    
    public void setBuildInfo(BuildInfo BuildInfo)
    {
        this.BuildInfo = BuildInfo;
    }
    
    public UnsignedInteger getSecondsTillShutdown()
    {
        return SecondsTillShutdown;
    }
    
    public void setSecondsTillShutdown(UnsignedInteger SecondsTillShutdown)
    {
        this.SecondsTillShutdown = SecondsTillShutdown;
    }
    
    public LocalizedText getShutdownReason()
    {
        return ShutdownReason;
    }
    
    public void setShutdownReason(LocalizedText ShutdownReason)
    {
        this.ShutdownReason = ShutdownReason;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ServerStatusDataType
      */
    public ServerStatusDataType clone()
    {
        ServerStatusDataType result = (ServerStatusDataType) super.clone();
        result.StartTime = StartTime;
        result.CurrentTime = CurrentTime;
        result.State = State;
        result.BuildInfo = BuildInfo==null ? null : BuildInfo.clone();
        result.SecondsTillShutdown = SecondsTillShutdown;
        result.ShutdownReason = ShutdownReason;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ServerStatusDataType other = (ServerStatusDataType) obj;
        if (StartTime==null) {
            if (other.StartTime != null) return false;
        } else if (!StartTime.equals(other.StartTime)) return false;
        if (CurrentTime==null) {
            if (other.CurrentTime != null) return false;
        } else if (!CurrentTime.equals(other.CurrentTime)) return false;
        if (State==null) {
            if (other.State != null) return false;
        } else if (!State.equals(other.State)) return false;
        if (BuildInfo==null) {
            if (other.BuildInfo != null) return false;
        } else if (!BuildInfo.equals(other.BuildInfo)) return false;
        if (SecondsTillShutdown==null) {
            if (other.SecondsTillShutdown != null) return false;
        } else if (!SecondsTillShutdown.equals(other.SecondsTillShutdown)) return false;
        if (ShutdownReason==null) {
            if (other.ShutdownReason != null) return false;
        } else if (!ShutdownReason.equals(other.ShutdownReason)) return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((StartTime == null) ? 0 : StartTime.hashCode());
        result = prime * result
                + ((CurrentTime == null) ? 0 : CurrentTime.hashCode());
        result = prime * result
                + ((State == null) ? 0 : State.hashCode());
        result = prime * result
                + ((BuildInfo == null) ? 0 : BuildInfo.hashCode());
        result = prime * result
                + ((SecondsTillShutdown == null) ? 0 : SecondsTillShutdown.hashCode());
        result = prime * result
                + ((ShutdownReason == null) ? 0 : ShutdownReason.hashCode());
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
		return "ServerStatusDataType: "+ObjectUtils.printFieldsDeep(this);
	}

}
