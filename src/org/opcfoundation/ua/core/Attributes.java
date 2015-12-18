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

import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.utils.Description;



public class Attributes {
	
    @Description("The canonical identifier for the node.")
    public final static UnsignedInteger NodeId = UnsignedInteger.valueOf(1);
    
    @Description("The class of the node.")
    public final static UnsignedInteger NodeClass = UnsignedInteger.valueOf(2);
    
    @Description("A non-localized, human readable name for the node.")
    public final static UnsignedInteger BrowseName = UnsignedInteger.valueOf(3);
    
    @Description("A localized, human readable name for the node.")
    public final static UnsignedInteger DisplayName = UnsignedInteger.valueOf(4);
    
    @Description("A localized description for the node.")
    public final static UnsignedInteger Description = UnsignedInteger.valueOf(5);
    
    @Description("Indicates which attributes are writable.")
    public final static UnsignedInteger WriteMask = UnsignedInteger.valueOf(6);
    
    @Description("Indicates which attributes are writable by the current user.")
    public final static UnsignedInteger UserWriteMask = UnsignedInteger.valueOf(7);
    
    @Description("Indicates that a type node may not be instantiated.")
    public final static UnsignedInteger IsAbstract = UnsignedInteger.valueOf(8);
    
    @Description("Indicates that forward and inverse references have the same meaning.")
    public final static UnsignedInteger Symmetric = UnsignedInteger.valueOf(9);
    
    @Description("The browse name for an inverse reference.")
    public final static UnsignedInteger InverseName = UnsignedInteger.valueOf(10);
    
    @Description("Indicates that following forward references within a view will not cause a loop.")
    public final static UnsignedInteger ContainsNoLoops = UnsignedInteger.valueOf(11);
    
    @Description("Indicates that the node can be used to subscribe to events.")
    public final static UnsignedInteger EventNotifier = UnsignedInteger.valueOf(12);
    
    @Description("The value of a variable.")
    public final static UnsignedInteger Value = UnsignedInteger.valueOf(13);
    
    @Description("The node id of the data type for the variable value.")
    public final static UnsignedInteger DataType = UnsignedInteger.valueOf(14);
    
    @Description("The number of dimensions in the value.")
    public final static UnsignedInteger ValueRank = UnsignedInteger.valueOf(15);
    
    @Description("The length for each dimension of an array value.")
    public final static UnsignedInteger ArrayDimensions = UnsignedInteger.valueOf(16);
    
    @Description("How a variable may be accessed.")
    public final static UnsignedInteger AccessLevel = UnsignedInteger.valueOf(17);
    
    @Description("How a variable may be accessed after taking the user's access rights into account.")
    public final static UnsignedInteger UserAccessLevel = UnsignedInteger.valueOf(18);
    
    @Description("Specifies (in milliseconds) how fast the server can reasonably sample the value for changes.")
    public final static UnsignedInteger MinimumSamplingInterval = UnsignedInteger.valueOf(19);
    
    @Description("Specifies whether the server is actively collecting historical data for the variable.")
    public final static UnsignedInteger Historizing = UnsignedInteger.valueOf(20);
    
    @Description("Whether the method can be called.")
    public final static UnsignedInteger Executable = UnsignedInteger.valueOf(21);
    
    @Description("Whether the method can be called by the current user.")
    public final static UnsignedInteger UserExecutable = UnsignedInteger.valueOf(22);
    
 

}
