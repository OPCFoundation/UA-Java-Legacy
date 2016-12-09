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

package org.opcfoundation.ua.utils;

import java.util.HashMap;
import java.util.Map;

import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.NodeClass;

/**
 * <p>AttributesUtil class.</p>
 *
 */
public class AttributesUtil {

    //Copied from earlier project
    /**
     * <p>isValid.</p>
     *
     * @param attributeId a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     * @return a boolean.
     */
    public static boolean isValid(UnsignedInteger attributeId) {
		return (attributeId.compareTo(Attributes.NodeId) >= 0 && attributeId.compareTo(Attributes.UserExecutable) <= 0);
	}
    
    //TODO Mikko added some attributeIds to this function..please check that those are correct..the information has been taken from spec part 3 v. 1.01.13 from chapter 5.9
    /**
     * Tests if the attribute is valid for at least one of the node classes specified in the mask.
     *
     * @param nodeClass a {@link org.opcfoundation.ua.core.NodeClass} object.
     * @param attributeId a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
     * @return true if valid
     */
    public static boolean isValid(NodeClass nodeClass, UnsignedInteger attributeId) {
    	
    	int ordinalNodeClass = nodeClass.ordinal();
    	//NodeId,NodeClass,DisplayName&BrowseName are mandatory for all node types..Description,UserWriteMask&EriteMask are optional for all nodeTypes
    	if (attributeId.equals(Attributes.NodeId) || attributeId.equals(Attributes.NodeClass) || attributeId.equals(Attributes.BrowseName) || 
    			attributeId.equals(Attributes.DisplayName) || attributeId.equals(Attributes.Description) ||
    			attributeId.equals(Attributes.WriteMask) || attributeId.equals(Attributes.UserWriteMask)) {
    		return true;
		}
    	
    	if (attributeId.equals(Attributes.Value) || attributeId.equals(Attributes.DataType) || attributeId.equals(Attributes.ValueRank)) {
    		return (ordinalNodeClass & (org.opcfoundation.ua.core.NodeClass.VariableType.ordinal()| org.opcfoundation.ua.core.NodeClass.Variable.ordinal())) != 0;
			
		}
    	
    	if (attributeId.equals(Attributes.IsAbstract)) {
			return (ordinalNodeClass & (org.opcfoundation.ua.core.NodeClass.VariableType.ordinal() | 
					org.opcfoundation.ua.core.NodeClass.ObjectType.ordinal() | org.opcfoundation.ua.core.NodeClass.DataType.ordinal() |org.opcfoundation.ua.core.NodeClass.ReferenceType.ordinal())) != 0;
		}

    	if (attributeId.equals(Attributes.Symmetric) || attributeId.equals(Attributes.InverseName)) {
			return (ordinalNodeClass & org.opcfoundation.ua.core.NodeClass.ReferenceType.ordinal()) != 0;
		}

    	if (attributeId.equals(Attributes.ContainsNoLoops)) {
			return (ordinalNodeClass & org.opcfoundation.ua.core.NodeClass.View.ordinal()) != 0;
		}
    	
    	if (attributeId.equals(Attributes.EventNotifier)) {
			return (ordinalNodeClass & (org.opcfoundation.ua.core.NodeClass.Object.ordinal() | org.opcfoundation.ua.core.NodeClass.View.ordinal())) != 0;
		}
                
    	if (attributeId.equals(Attributes.AccessLevel) || attributeId.equals(Attributes.UserAccessLevel) || attributeId.equals(Attributes.MinimumSamplingInterval) || 
    			attributeId.equals(Attributes.Historizing)) {
			return (ordinalNodeClass & org.opcfoundation.ua.core.NodeClass.Variable.ordinal()) != 0;
		}
    	if( attributeId.equals(Attributes.ArrayDimensions)){
    		return (ordinalNodeClass & (org.opcfoundation.ua.core.NodeClass.Variable.ordinal() | 
					org.opcfoundation.ua.core.NodeClass.VariableType.ordinal())) != 0; 
    	}
                
    	if (attributeId.equals(Attributes.Executable) || attributeId.equals(Attributes.UserExecutable)) {
			return (ordinalNodeClass & org.opcfoundation.ua.core.NodeClass.Method.ordinal()) != 0;
		}

        return false;
	}

    
	/**
	 * <p>toString.</p>
	 *
	 * @param value a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String toString(UnsignedInteger value) {
		try {
		  return attributeNames.get(value);
		} catch (NullPointerException e) {
			return "<InvalidAttributeValue " + value + ">";
		}
	}
	
    final static Map<UnsignedInteger, String> attributeNames = new HashMap<UnsignedInteger, String>();
    static {
        
        attributeNames.put(Attributes.NodeId , "NodeId");
        attributeNames.put(Attributes.NodeClass , "NodeClass");
        attributeNames.put(Attributes.BrowseName , "BrowseName");
        attributeNames.put(Attributes.DisplayName , "DisplayName");
        attributeNames.put(Attributes.Description , "Description");
        attributeNames.put(Attributes.WriteMask , "WriteMask");
        attributeNames.put(Attributes.UserWriteMask , "UserWriteMask");
        attributeNames.put(Attributes.IsAbstract , "IsAbstract");
        attributeNames.put(Attributes.Symmetric , "Symmetric");
        attributeNames.put(Attributes.InverseName , "InverseName");
        attributeNames.put(Attributes.ContainsNoLoops , "ContainsNoLoops");
        attributeNames.put(Attributes.EventNotifier , "EventNotifier");
        attributeNames.put(Attributes.Value , "Value");
        attributeNames.put(Attributes.DataType , "DataType");
        attributeNames.put(Attributes.ValueRank , "ValueRank");
        attributeNames.put(Attributes.ArrayDimensions , "ArrayDimensions");
        attributeNames.put(Attributes.AccessLevel , "AccessLevel");
        attributeNames.put(Attributes.UserAccessLevel , "UserAccessLevel");
        attributeNames.put(Attributes.MinimumSamplingInterval , "MinimumSamplingInterval");
        attributeNames.put(Attributes.Historizing , "Historizing");
        attributeNames.put(Attributes.Executable , "Executable");
        attributeNames.put(Attributes.UserExecutable , "UserExecutable");
    }
}
