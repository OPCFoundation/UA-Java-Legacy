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
