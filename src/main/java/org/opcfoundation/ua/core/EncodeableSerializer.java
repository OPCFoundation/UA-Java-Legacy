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

import java.io.IOException;

import org.opcfoundation.ua.encoding.DecodingException;
import org.opcfoundation.ua.encoding.EncodingException;
import org.opcfoundation.ua.encoding.IDecoder;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.encoding.IEncoder;
import org.opcfoundation.ua.encoding.binary.EncoderCalc;
import org.opcfoundation.ua.encoding.binary.DecoderUtils;
import org.opcfoundation.ua.encoding.binary.EncodeableReflectionSerializer;
import org.opcfoundation.ua.encoding.binary.IEncodeableSerializer;
import org.opcfoundation.ua.encoding.utils.AbstractSerializer;
import org.opcfoundation.ua.encoding.utils.SerializerComposition;


/**
 * Code-generated encodeable serializer.
 * 
 * @see EncodeableReflectionSerializer Reflection based implementation
 * @see IEncodeableSerializer Serializer interface
 */
public class EncodeableSerializer extends SerializerComposition {

	private static EncodeableSerializer INSTANCE;
	
	/**
	 * Get singleton instance
	 * @return singleton instance
	 */
	public synchronized static EncodeableSerializer getInstance() 
	{
		return (INSTANCE!=null) ? INSTANCE : (INSTANCE = new EncodeableSerializer()); 
	}
	
	public EncodeableSerializer() {
		
    	// TrustListDataType
    	addSerializer(
    		new AbstractSerializer(TrustListDataType.class, TrustListDataType.BINARY, TrustListDataType.XML, TrustListDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				TrustListDataType obj = (TrustListDataType) encodeable;
    				calculator.putUInt32(null, null /*obj.getSpecifiedLists()*/);
    				calculator.putByteStringArray(null, ((obj==null)?null:obj.getTrustedCertificates()) );
    				calculator.putByteStringArray(null, ((obj==null)?null:obj.getTrustedCrls()) );
    				calculator.putByteStringArray(null, ((obj==null)?null:obj.getIssuerCertificates()) );
    				calculator.putByteStringArray(null, ((obj==null)?null:obj.getIssuerCrls()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				TrustListDataType obj = (TrustListDataType) encodeable;
    				encoder.putUInt32("SpecifiedLists",  (obj==null)?null:obj.getSpecifiedLists() );
    				encoder.putByteStringArray("TrustedCertificates", (obj==null)?null:obj.getTrustedCertificates() );
    				encoder.putByteStringArray("TrustedCrls", (obj==null)?null:obj.getTrustedCrls() );
    				encoder.putByteStringArray("IssuerCertificates", (obj==null)?null:obj.getIssuerCertificates() );
    				encoder.putByteStringArray("IssuerCrls", (obj==null)?null:obj.getIssuerCrls() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				TrustListDataType result = new TrustListDataType();
    				result.setSpecifiedLists( decoder.getUInt32("SpecifiedLists") );
    				result.setTrustedCertificates( decoder.getByteStringArray("TrustedCertificates") );
    				result.setTrustedCrls( decoder.getByteStringArray("TrustedCrls") );
    				result.setIssuerCertificates( decoder.getByteStringArray("IssuerCertificates") );
    				result.setIssuerCrls( decoder.getByteStringArray("IssuerCrls") );
    				return result;
    			}
    		});
    
    	// Node
    	addSerializer(
    		new AbstractSerializer(Node.class, Node.BINARY, Node.XML, Node.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				Node obj = (Node) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				Node obj = (Node) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				Node result = new Node();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				return result;
    			}
    		});
    
    	// InstanceNode
    	addSerializer(
    		new AbstractSerializer(InstanceNode.class, InstanceNode.BINARY, InstanceNode.XML, InstanceNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				InstanceNode obj = (InstanceNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				InstanceNode obj = (InstanceNode) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				InstanceNode result = new InstanceNode();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				return result;
    			}
    		});
    
    	// TypeNode
    	addSerializer(
    		new AbstractSerializer(TypeNode.class, TypeNode.BINARY, TypeNode.XML, TypeNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				TypeNode obj = (TypeNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				TypeNode obj = (TypeNode) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				TypeNode result = new TypeNode();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				return result;
    			}
    		});
    
    	// ObjectNode
    	addSerializer(
    		new AbstractSerializer(ObjectNode.class, ObjectNode.BINARY, ObjectNode.XML, ObjectNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ObjectNode obj = (ObjectNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				calculator.putByte(null, null /*obj.getEventNotifier()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ObjectNode obj = (ObjectNode) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				encoder.putByte("EventNotifier",  (obj==null)?null:obj.getEventNotifier() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ObjectNode result = new ObjectNode();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				result.setEventNotifier( decoder.getByte("EventNotifier") );
    				return result;
    			}
    		});
    
    	// ObjectTypeNode
    	addSerializer(
    		new AbstractSerializer(ObjectTypeNode.class, ObjectTypeNode.BINARY, ObjectTypeNode.XML, ObjectTypeNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ObjectTypeNode obj = (ObjectTypeNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				calculator.putBoolean(null, null /*obj.getIsAbstract()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ObjectTypeNode obj = (ObjectTypeNode) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				encoder.putBoolean("IsAbstract",  (obj==null)?null:obj.getIsAbstract() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ObjectTypeNode result = new ObjectTypeNode();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				result.setIsAbstract( decoder.getBoolean("IsAbstract") );
    				return result;
    			}
    		});
    
    	// VariableNode
    	addSerializer(
    		new AbstractSerializer(VariableNode.class, VariableNode.BINARY, VariableNode.XML, VariableNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				VariableNode obj = (VariableNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				calculator.putVariant(null,  (obj==null)?null:obj.getValue() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getDataType() );
    				calculator.putInt32(null, null /*obj.getValueRank()*/);
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getArrayDimensions()) );
    				calculator.putByte(null, null /*obj.getAccessLevel()*/);
    				calculator.putByte(null, null /*obj.getUserAccessLevel()*/);
    				calculator.putDouble(null, null /*obj.getMinimumSamplingInterval()*/);
    				calculator.putBoolean(null, null /*obj.getHistorizing()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				VariableNode obj = (VariableNode) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				encoder.putVariant("Value",  (obj==null)?null:obj.getValue() );
    				encoder.putNodeId("DataType",  (obj==null)?null:obj.getDataType() );
    				encoder.putInt32("ValueRank",  (obj==null)?null:obj.getValueRank() );
    				encoder.putUInt32Array("ArrayDimensions", (obj==null)?null:obj.getArrayDimensions() );
    				encoder.putByte("AccessLevel",  (obj==null)?null:obj.getAccessLevel() );
    				encoder.putByte("UserAccessLevel",  (obj==null)?null:obj.getUserAccessLevel() );
    				encoder.putDouble("MinimumSamplingInterval",  (obj==null)?null:obj.getMinimumSamplingInterval() );
    				encoder.putBoolean("Historizing",  (obj==null)?null:obj.getHistorizing() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				VariableNode result = new VariableNode();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				result.setValue( decoder.getVariant("Value") );
    				result.setDataType( decoder.getNodeId("DataType") );
    				result.setValueRank( decoder.getInt32("ValueRank") );
    				result.setArrayDimensions( decoder.getUInt32Array("ArrayDimensions") );
    				result.setAccessLevel( decoder.getByte("AccessLevel") );
    				result.setUserAccessLevel( decoder.getByte("UserAccessLevel") );
    				result.setMinimumSamplingInterval( decoder.getDouble("MinimumSamplingInterval") );
    				result.setHistorizing( decoder.getBoolean("Historizing") );
    				return result;
    			}
    		});
    
    	// VariableTypeNode
    	addSerializer(
    		new AbstractSerializer(VariableTypeNode.class, VariableTypeNode.BINARY, VariableTypeNode.XML, VariableTypeNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				VariableTypeNode obj = (VariableTypeNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				calculator.putVariant(null,  (obj==null)?null:obj.getValue() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getDataType() );
    				calculator.putInt32(null, null /*obj.getValueRank()*/);
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getArrayDimensions()) );
    				calculator.putBoolean(null, null /*obj.getIsAbstract()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				VariableTypeNode obj = (VariableTypeNode) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				encoder.putVariant("Value",  (obj==null)?null:obj.getValue() );
    				encoder.putNodeId("DataType",  (obj==null)?null:obj.getDataType() );
    				encoder.putInt32("ValueRank",  (obj==null)?null:obj.getValueRank() );
    				encoder.putUInt32Array("ArrayDimensions", (obj==null)?null:obj.getArrayDimensions() );
    				encoder.putBoolean("IsAbstract",  (obj==null)?null:obj.getIsAbstract() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				VariableTypeNode result = new VariableTypeNode();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				result.setValue( decoder.getVariant("Value") );
    				result.setDataType( decoder.getNodeId("DataType") );
    				result.setValueRank( decoder.getInt32("ValueRank") );
    				result.setArrayDimensions( decoder.getUInt32Array("ArrayDimensions") );
    				result.setIsAbstract( decoder.getBoolean("IsAbstract") );
    				return result;
    			}
    		});
    
    	// ReferenceTypeNode
    	addSerializer(
    		new AbstractSerializer(ReferenceTypeNode.class, ReferenceTypeNode.BINARY, ReferenceTypeNode.XML, ReferenceTypeNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReferenceTypeNode obj = (ReferenceTypeNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				calculator.putBoolean(null, null /*obj.getIsAbstract()*/);
    				calculator.putBoolean(null, null /*obj.getSymmetric()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getInverseName() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReferenceTypeNode obj = (ReferenceTypeNode) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				encoder.putBoolean("IsAbstract",  (obj==null)?null:obj.getIsAbstract() );
    				encoder.putBoolean("Symmetric",  (obj==null)?null:obj.getSymmetric() );
    				encoder.putLocalizedText("InverseName",  (obj==null)?null:obj.getInverseName() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReferenceTypeNode result = new ReferenceTypeNode();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				result.setIsAbstract( decoder.getBoolean("IsAbstract") );
    				result.setSymmetric( decoder.getBoolean("Symmetric") );
    				result.setInverseName( decoder.getLocalizedText("InverseName") );
    				return result;
    			}
    		});
    
    	// MethodNode
    	addSerializer(
    		new AbstractSerializer(MethodNode.class, MethodNode.BINARY, MethodNode.XML, MethodNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				MethodNode obj = (MethodNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				calculator.putBoolean(null, null /*obj.getExecutable()*/);
    				calculator.putBoolean(null, null /*obj.getUserExecutable()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				MethodNode obj = (MethodNode) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				encoder.putBoolean("Executable",  (obj==null)?null:obj.getExecutable() );
    				encoder.putBoolean("UserExecutable",  (obj==null)?null:obj.getUserExecutable() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MethodNode result = new MethodNode();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				result.setExecutable( decoder.getBoolean("Executable") );
    				result.setUserExecutable( decoder.getBoolean("UserExecutable") );
    				return result;
    			}
    		});
    
    	// ViewNode
    	addSerializer(
    		new AbstractSerializer(ViewNode.class, ViewNode.BINARY, ViewNode.XML, ViewNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ViewNode obj = (ViewNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				calculator.putBoolean(null, null /*obj.getContainsNoLoops()*/);
    				calculator.putByte(null, null /*obj.getEventNotifier()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ViewNode obj = (ViewNode) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				encoder.putBoolean("ContainsNoLoops",  (obj==null)?null:obj.getContainsNoLoops() );
    				encoder.putByte("EventNotifier",  (obj==null)?null:obj.getEventNotifier() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ViewNode result = new ViewNode();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				result.setContainsNoLoops( decoder.getBoolean("ContainsNoLoops") );
    				result.setEventNotifier( decoder.getByte("EventNotifier") );
    				return result;
    			}
    		});
    
    	// DataTypeNode
    	addSerializer(
    		new AbstractSerializer(DataTypeNode.class, DataTypeNode.BINARY, DataTypeNode.XML, DataTypeNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DataTypeNode obj = (DataTypeNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putEncodeableArray(null, ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				calculator.putBoolean(null, null /*obj.getIsAbstract()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DataTypeNode obj = (DataTypeNode) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putEncodeableArray("References", ReferenceNode.class, (obj==null)?null:obj.getReferences());
    				encoder.putBoolean("IsAbstract",  (obj==null)?null:obj.getIsAbstract() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DataTypeNode result = new DataTypeNode();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceNode.class) );
    				result.setIsAbstract( decoder.getBoolean("IsAbstract") );
    				return result;
    			}
    		});
    
    	// ReferenceNode
    	addSerializer(
    		new AbstractSerializer(ReferenceNode.class, ReferenceNode.BINARY, ReferenceNode.XML, ReferenceNode.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReferenceNode obj = (ReferenceNode) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getReferenceTypeId() );
    				calculator.putBoolean(null, null /*obj.getIsInverse()*/);
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getTargetId() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReferenceNode obj = (ReferenceNode) encodeable;
    				encoder.putNodeId("ReferenceTypeId",  (obj==null)?null:obj.getReferenceTypeId() );
    				encoder.putBoolean("IsInverse",  (obj==null)?null:obj.getIsInverse() );
    				encoder.putExpandedNodeId("TargetId",  (obj==null)?null:obj.getTargetId() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReferenceNode result = new ReferenceNode();
    				result.setReferenceTypeId( decoder.getNodeId("ReferenceTypeId") );
    				result.setIsInverse( decoder.getBoolean("IsInverse") );
    				result.setTargetId( decoder.getExpandedNodeId("TargetId") );
    				return result;
    			}
    		});
    
    	// Argument
    	addSerializer(
    		new AbstractSerializer(Argument.class, Argument.BINARY, Argument.XML, Argument.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				Argument obj = (Argument) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getName() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getDataType() );
    				calculator.putInt32(null, null /*obj.getValueRank()*/);
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getArrayDimensions()) );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				Argument obj = (Argument) encodeable;
    				encoder.putString("Name",  (obj==null)?null:obj.getName() );
    				encoder.putNodeId("DataType",  (obj==null)?null:obj.getDataType() );
    				encoder.putInt32("ValueRank",  (obj==null)?null:obj.getValueRank() );
    				encoder.putUInt32Array("ArrayDimensions", (obj==null)?null:obj.getArrayDimensions() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				Argument result = new Argument();
    				result.setName( decoder.getString("Name") );
    				result.setDataType( decoder.getNodeId("DataType") );
    				result.setValueRank( decoder.getInt32("ValueRank") );
    				result.setArrayDimensions( decoder.getUInt32Array("ArrayDimensions") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				return result;
    			}
    		});
    
    	// EnumValueType
    	addSerializer(
    		new AbstractSerializer(EnumValueType.class, EnumValueType.BINARY, EnumValueType.XML, EnumValueType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				EnumValueType obj = (EnumValueType) encodeable;
    				calculator.putInt64(null, null /*obj.getValue()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				EnumValueType obj = (EnumValueType) encodeable;
    				encoder.putInt64("Value",  (obj==null)?null:obj.getValue() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				EnumValueType result = new EnumValueType();
    				result.setValue( decoder.getInt64("Value") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				return result;
    			}
    		});
    
    	// OptionSet
    	addSerializer(
    		new AbstractSerializer(OptionSet.class, OptionSet.BINARY, OptionSet.XML, OptionSet.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				OptionSet obj = (OptionSet) encodeable;
    				calculator.putByteString(null,  (obj==null)?null:obj.getValue() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getValidBits() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				OptionSet obj = (OptionSet) encodeable;
    				encoder.putByteString("Value",  (obj==null)?null:obj.getValue() );
    				encoder.putByteString("ValidBits",  (obj==null)?null:obj.getValidBits() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				OptionSet result = new OptionSet();
    				result.setValue( decoder.getByteString("Value") );
    				result.setValidBits( decoder.getByteString("ValidBits") );
    				return result;
    			}
    		});
    
    	// Union
    	addSerializer(
    		new AbstractSerializer(Union.class, Union.BINARY, Union.XML, Union.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				Union result = new Union();
    				return result;
    			}
    		});
    
    	// TimeZoneDataType
    	addSerializer(
    		new AbstractSerializer(TimeZoneDataType.class, TimeZoneDataType.BINARY, TimeZoneDataType.XML, TimeZoneDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				TimeZoneDataType obj = (TimeZoneDataType) encodeable;
    				calculator.putInt16(null, null /*obj.getOffset()*/);
    				calculator.putBoolean(null, null /*obj.getDaylightSavingInOffset()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				TimeZoneDataType obj = (TimeZoneDataType) encodeable;
    				encoder.putInt16("Offset",  (obj==null)?null:obj.getOffset() );
    				encoder.putBoolean("DaylightSavingInOffset",  (obj==null)?null:obj.getDaylightSavingInOffset() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				TimeZoneDataType result = new TimeZoneDataType();
    				result.setOffset( decoder.getInt16("Offset") );
    				result.setDaylightSavingInOffset( decoder.getBoolean("DaylightSavingInOffset") );
    				return result;
    			}
    		});
    
    	// ApplicationDescription
    	addSerializer(
    		new AbstractSerializer(ApplicationDescription.class, ApplicationDescription.BINARY, ApplicationDescription.XML, ApplicationDescription.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ApplicationDescription obj = (ApplicationDescription) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getApplicationUri() );
    				calculator.putString(null,  (obj==null)?null:obj.getProductUri() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getApplicationName() );
    				calculator.putEnumeration(null, null /*obj.getApplicationType()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getGatewayServerUri() );
    				calculator.putString(null,  (obj==null)?null:obj.getDiscoveryProfileUri() );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getDiscoveryUrls()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ApplicationDescription obj = (ApplicationDescription) encodeable;
    				encoder.putString("ApplicationUri",  (obj==null)?null:obj.getApplicationUri() );
    				encoder.putString("ProductUri",  (obj==null)?null:obj.getProductUri() );
    				encoder.putLocalizedText("ApplicationName",  (obj==null)?null:obj.getApplicationName() );
    				encoder.putEnumeration("ApplicationType",  (obj==null)?null:obj.getApplicationType() );
    				encoder.putString("GatewayServerUri",  (obj==null)?null:obj.getGatewayServerUri() );
    				encoder.putString("DiscoveryProfileUri",  (obj==null)?null:obj.getDiscoveryProfileUri() );
    				encoder.putStringArray("DiscoveryUrls", (obj==null)?null:obj.getDiscoveryUrls() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ApplicationDescription result = new ApplicationDescription();
    				result.setApplicationUri( decoder.getString("ApplicationUri") );
    				result.setProductUri( decoder.getString("ProductUri") );
    				result.setApplicationName( decoder.getLocalizedText("ApplicationName") );
    				result.setApplicationType( decoder.getEnumeration("ApplicationType", ApplicationType.class) );
    				result.setGatewayServerUri( decoder.getString("GatewayServerUri") );
    				result.setDiscoveryProfileUri( decoder.getString("DiscoveryProfileUri") );
    				result.setDiscoveryUrls( decoder.getStringArray("DiscoveryUrls") );
    				return result;
    			}
    		});
    
    	// RequestHeader
    	addSerializer(
    		new AbstractSerializer(RequestHeader.class, RequestHeader.BINARY, RequestHeader.XML, RequestHeader.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RequestHeader obj = (RequestHeader) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getAuthenticationToken() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getTimestamp() );
    				calculator.putUInt32(null, null /*obj.getRequestHandle()*/);
    				calculator.putUInt32(null, null /*obj.getReturnDiagnostics()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getAuditEntryId() );
    				calculator.putUInt32(null, null /*obj.getTimeoutHint()*/);
    				calculator.putExtensionObject(null,  (obj==null)?null:obj.getAdditionalHeader() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RequestHeader obj = (RequestHeader) encodeable;
    				encoder.putNodeId("AuthenticationToken",  (obj==null)?null:obj.getAuthenticationToken() );
    				encoder.putDateTime("Timestamp",  (obj==null)?null:obj.getTimestamp() );
    				encoder.putUInt32("RequestHandle",  (obj==null)?null:obj.getRequestHandle() );
    				encoder.putUInt32("ReturnDiagnostics",  (obj==null)?null:obj.getReturnDiagnostics() );
    				encoder.putString("AuditEntryId",  (obj==null)?null:obj.getAuditEntryId() );
    				encoder.putUInt32("TimeoutHint",  (obj==null)?null:obj.getTimeoutHint() );
    				encoder.putExtensionObject("AdditionalHeader",  (obj==null)?null:obj.getAdditionalHeader() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RequestHeader result = new RequestHeader();
    				result.setAuthenticationToken( decoder.getNodeId("AuthenticationToken") );
    				result.setTimestamp( decoder.getDateTime("Timestamp") );
    				result.setRequestHandle( decoder.getUInt32("RequestHandle") );
    				result.setReturnDiagnostics( decoder.getUInt32("ReturnDiagnostics") );
    				result.setAuditEntryId( decoder.getString("AuditEntryId") );
    				result.setTimeoutHint( decoder.getUInt32("TimeoutHint") );
    				result.setAdditionalHeader( decoder.getExtensionObject("AdditionalHeader") );
    				return result;
    			}
    		});
    
    	// ResponseHeader
    	addSerializer(
    		new AbstractSerializer(ResponseHeader.class, ResponseHeader.BINARY, ResponseHeader.XML, ResponseHeader.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ResponseHeader obj = (ResponseHeader) encodeable;
    				calculator.putDateTime(null,  (obj==null)?null:obj.getTimestamp() );
    				calculator.putUInt32(null, null /*obj.getRequestHandle()*/);
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getServiceResult() );
    				calculator.putDiagnosticInfo(null,  (obj==null)?null:obj.getServiceDiagnostics() );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getStringTable()) );
    				calculator.putExtensionObject(null,  (obj==null)?null:obj.getAdditionalHeader() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ResponseHeader obj = (ResponseHeader) encodeable;
    				encoder.putDateTime("Timestamp",  (obj==null)?null:obj.getTimestamp() );
    				encoder.putUInt32("RequestHandle",  (obj==null)?null:obj.getRequestHandle() );
    				encoder.putStatusCode("ServiceResult",  (obj==null)?null:obj.getServiceResult() );
    				encoder.putDiagnosticInfo("ServiceDiagnostics",  (obj==null)?null:obj.getServiceDiagnostics() );
    				encoder.putStringArray("StringTable", (obj==null)?null:obj.getStringTable() );
    				encoder.putExtensionObject("AdditionalHeader",  (obj==null)?null:obj.getAdditionalHeader() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ResponseHeader result = new ResponseHeader();
    				result.setTimestamp( decoder.getDateTime("Timestamp") );
    				result.setRequestHandle( decoder.getUInt32("RequestHandle") );
    				result.setServiceResult( decoder.getStatusCode("ServiceResult") );
    				result.setServiceDiagnostics( decoder.getDiagnosticInfo("ServiceDiagnostics") );
    				result.setStringTable( decoder.getStringArray("StringTable") );
    				result.setAdditionalHeader( decoder.getExtensionObject("AdditionalHeader") );
    				DecoderUtils.fixResponseHeader(result);
    				return result;
    			}
    		});
    
    	// ServiceFault
    	addSerializer(
    		new AbstractSerializer(ServiceFault.class, ServiceFault.BINARY, ServiceFault.XML, ServiceFault.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ServiceFault obj = (ServiceFault) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ServiceFault obj = (ServiceFault) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ServiceFault result = new ServiceFault();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				return result;
    			}
    		});
    
    	// FindServersRequest
    	addSerializer(
    		new AbstractSerializer(FindServersRequest.class, FindServersRequest.BINARY, FindServersRequest.XML, FindServersRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				FindServersRequest obj = (FindServersRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putString(null,  (obj==null)?null:obj.getEndpointUrl() );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getLocaleIds()) );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getServerUris()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				FindServersRequest obj = (FindServersRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putString("EndpointUrl",  (obj==null)?null:obj.getEndpointUrl() );
    				encoder.putStringArray("LocaleIds", (obj==null)?null:obj.getLocaleIds() );
    				encoder.putStringArray("ServerUris", (obj==null)?null:obj.getServerUris() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				FindServersRequest result = new FindServersRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setEndpointUrl( decoder.getString("EndpointUrl") );
    				result.setLocaleIds( decoder.getStringArray("LocaleIds") );
    				result.setServerUris( decoder.getStringArray("ServerUris") );
    				return result;
    			}
    		});
    
    	// FindServersResponse
    	addSerializer(
    		new AbstractSerializer(FindServersResponse.class, FindServersResponse.BINARY, FindServersResponse.XML, FindServersResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				FindServersResponse obj = (FindServersResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, ApplicationDescription.class, (obj==null)?null:obj.getServers());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				FindServersResponse obj = (FindServersResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Servers", ApplicationDescription.class, (obj==null)?null:obj.getServers());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				FindServersResponse result = new FindServersResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setServers( decoder.getEncodeableArray("Servers", ApplicationDescription.class) );
    				return result;
    			}
    		});
    
    	// ServerOnNetwork
    	addSerializer(
    		new AbstractSerializer(ServerOnNetwork.class, ServerOnNetwork.BINARY, ServerOnNetwork.XML, ServerOnNetwork.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ServerOnNetwork obj = (ServerOnNetwork) encodeable;
    				calculator.putUInt32(null, null /*obj.getRecordId()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getServerName() );
    				calculator.putString(null,  (obj==null)?null:obj.getDiscoveryUrl() );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getServerCapabilities()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ServerOnNetwork obj = (ServerOnNetwork) encodeable;
    				encoder.putUInt32("RecordId",  (obj==null)?null:obj.getRecordId() );
    				encoder.putString("ServerName",  (obj==null)?null:obj.getServerName() );
    				encoder.putString("DiscoveryUrl",  (obj==null)?null:obj.getDiscoveryUrl() );
    				encoder.putStringArray("ServerCapabilities", (obj==null)?null:obj.getServerCapabilities() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ServerOnNetwork result = new ServerOnNetwork();
    				result.setRecordId( decoder.getUInt32("RecordId") );
    				result.setServerName( decoder.getString("ServerName") );
    				result.setDiscoveryUrl( decoder.getString("DiscoveryUrl") );
    				result.setServerCapabilities( decoder.getStringArray("ServerCapabilities") );
    				return result;
    			}
    		});
    
    	// FindServersOnNetworkRequest
    	addSerializer(
    		new AbstractSerializer(FindServersOnNetworkRequest.class, FindServersOnNetworkRequest.BINARY, FindServersOnNetworkRequest.XML, FindServersOnNetworkRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				FindServersOnNetworkRequest obj = (FindServersOnNetworkRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32(null, null /*obj.getStartingRecordId()*/);
    				calculator.putUInt32(null, null /*obj.getMaxRecordsToReturn()*/);
    				calculator.putStringArray(null, ((obj==null)?null:obj.getServerCapabilityFilter()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				FindServersOnNetworkRequest obj = (FindServersOnNetworkRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32("StartingRecordId",  (obj==null)?null:obj.getStartingRecordId() );
    				encoder.putUInt32("MaxRecordsToReturn",  (obj==null)?null:obj.getMaxRecordsToReturn() );
    				encoder.putStringArray("ServerCapabilityFilter", (obj==null)?null:obj.getServerCapabilityFilter() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				FindServersOnNetworkRequest result = new FindServersOnNetworkRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setStartingRecordId( decoder.getUInt32("StartingRecordId") );
    				result.setMaxRecordsToReturn( decoder.getUInt32("MaxRecordsToReturn") );
    				result.setServerCapabilityFilter( decoder.getStringArray("ServerCapabilityFilter") );
    				return result;
    			}
    		});
    
    	// FindServersOnNetworkResponse
    	addSerializer(
    		new AbstractSerializer(FindServersOnNetworkResponse.class, FindServersOnNetworkResponse.BINARY, FindServersOnNetworkResponse.XML, FindServersOnNetworkResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				FindServersOnNetworkResponse obj = (FindServersOnNetworkResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putDateTime(null,  (obj==null)?null:obj.getLastCounterResetTime() );
    				calculator.putEncodeableArray(null, ServerOnNetwork.class, (obj==null)?null:obj.getServers());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				FindServersOnNetworkResponse obj = (FindServersOnNetworkResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putDateTime("LastCounterResetTime",  (obj==null)?null:obj.getLastCounterResetTime() );
    				encoder.putEncodeableArray("Servers", ServerOnNetwork.class, (obj==null)?null:obj.getServers());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				FindServersOnNetworkResponse result = new FindServersOnNetworkResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setLastCounterResetTime( decoder.getDateTime("LastCounterResetTime") );
    				result.setServers( decoder.getEncodeableArray("Servers", ServerOnNetwork.class) );
    				return result;
    			}
    		});
    
    	// UserTokenPolicy
    	addSerializer(
    		new AbstractSerializer(UserTokenPolicy.class, UserTokenPolicy.BINARY, UserTokenPolicy.XML, UserTokenPolicy.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				UserTokenPolicy obj = (UserTokenPolicy) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getPolicyId() );
    				calculator.putEnumeration(null, null /*obj.getTokenType()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getIssuedTokenType() );
    				calculator.putString(null,  (obj==null)?null:obj.getIssuerEndpointUrl() );
    				calculator.putString(null,  (obj==null)?null:obj.getSecurityPolicyUri() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				UserTokenPolicy obj = (UserTokenPolicy) encodeable;
    				encoder.putString("PolicyId",  (obj==null)?null:obj.getPolicyId() );
    				encoder.putEnumeration("TokenType",  (obj==null)?null:obj.getTokenType() );
    				encoder.putString("IssuedTokenType",  (obj==null)?null:obj.getIssuedTokenType() );
    				encoder.putString("IssuerEndpointUrl",  (obj==null)?null:obj.getIssuerEndpointUrl() );
    				encoder.putString("SecurityPolicyUri",  (obj==null)?null:obj.getSecurityPolicyUri() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				UserTokenPolicy result = new UserTokenPolicy();
    				result.setPolicyId( decoder.getString("PolicyId") );
    				result.setTokenType( decoder.getEnumeration("TokenType", UserTokenType.class) );
    				result.setIssuedTokenType( decoder.getString("IssuedTokenType") );
    				result.setIssuerEndpointUrl( decoder.getString("IssuerEndpointUrl") );
    				result.setSecurityPolicyUri( decoder.getString("SecurityPolicyUri") );
    				return result;
    			}
    		});
    
    	// EndpointDescription
    	addSerializer(
    		new AbstractSerializer(EndpointDescription.class, EndpointDescription.BINARY, EndpointDescription.XML, EndpointDescription.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				EndpointDescription obj = (EndpointDescription) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getEndpointUrl() );
    				calculator.putEncodeable(null, ApplicationDescription.class, (obj==null)?null:obj.getServer());
    				calculator.putByteString(null,  (obj==null)?null:obj.getServerCertificate() );
    				calculator.putEnumeration(null, null /*obj.getSecurityMode()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getSecurityPolicyUri() );
    				calculator.putEncodeableArray(null, UserTokenPolicy.class, (obj==null)?null:obj.getUserIdentityTokens());
    				calculator.putString(null,  (obj==null)?null:obj.getTransportProfileUri() );
    				calculator.putByte(null, null /*obj.getSecurityLevel()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				EndpointDescription obj = (EndpointDescription) encodeable;
    				encoder.putString("EndpointUrl",  (obj==null)?null:obj.getEndpointUrl() );
    				encoder.putEncodeable("Server", ApplicationDescription.class, (obj==null)?null:obj.getServer());
    				encoder.putByteString("ServerCertificate",  (obj==null)?null:obj.getServerCertificate() );
    				encoder.putEnumeration("SecurityMode",  (obj==null)?null:obj.getSecurityMode() );
    				encoder.putString("SecurityPolicyUri",  (obj==null)?null:obj.getSecurityPolicyUri() );
    				encoder.putEncodeableArray("UserIdentityTokens", UserTokenPolicy.class, (obj==null)?null:obj.getUserIdentityTokens());
    				encoder.putString("TransportProfileUri",  (obj==null)?null:obj.getTransportProfileUri() );
    				encoder.putByte("SecurityLevel",  (obj==null)?null:obj.getSecurityLevel() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				EndpointDescription result = new EndpointDescription();
    				result.setEndpointUrl( decoder.getString("EndpointUrl") );
    				result.setServer( decoder.getEncodeable("Server", ApplicationDescription.class) );
    				result.setServerCertificate( decoder.getByteString("ServerCertificate") );
    				result.setSecurityMode( decoder.getEnumeration("SecurityMode", MessageSecurityMode.class) );
    				result.setSecurityPolicyUri( decoder.getString("SecurityPolicyUri") );
    				result.setUserIdentityTokens( decoder.getEncodeableArray("UserIdentityTokens", UserTokenPolicy.class) );
    				result.setTransportProfileUri( decoder.getString("TransportProfileUri") );
    				result.setSecurityLevel( decoder.getByte("SecurityLevel") );
    				return result;
    			}
    		});
    
    	// GetEndpointsRequest
    	addSerializer(
    		new AbstractSerializer(GetEndpointsRequest.class, GetEndpointsRequest.BINARY, GetEndpointsRequest.XML, GetEndpointsRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				GetEndpointsRequest obj = (GetEndpointsRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putString(null,  (obj==null)?null:obj.getEndpointUrl() );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getLocaleIds()) );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getProfileUris()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				GetEndpointsRequest obj = (GetEndpointsRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putString("EndpointUrl",  (obj==null)?null:obj.getEndpointUrl() );
    				encoder.putStringArray("LocaleIds", (obj==null)?null:obj.getLocaleIds() );
    				encoder.putStringArray("ProfileUris", (obj==null)?null:obj.getProfileUris() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				GetEndpointsRequest result = new GetEndpointsRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setEndpointUrl( decoder.getString("EndpointUrl") );
    				result.setLocaleIds( decoder.getStringArray("LocaleIds") );
    				result.setProfileUris( decoder.getStringArray("ProfileUris") );
    				return result;
    			}
    		});
    
    	// GetEndpointsResponse
    	addSerializer(
    		new AbstractSerializer(GetEndpointsResponse.class, GetEndpointsResponse.BINARY, GetEndpointsResponse.XML, GetEndpointsResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				GetEndpointsResponse obj = (GetEndpointsResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, EndpointDescription.class, (obj==null)?null:obj.getEndpoints());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				GetEndpointsResponse obj = (GetEndpointsResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Endpoints", EndpointDescription.class, (obj==null)?null:obj.getEndpoints());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				GetEndpointsResponse result = new GetEndpointsResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setEndpoints( decoder.getEncodeableArray("Endpoints", EndpointDescription.class) );
    				return result;
    			}
    		});
    
    	// RegisteredServer
    	addSerializer(
    		new AbstractSerializer(RegisteredServer.class, RegisteredServer.BINARY, RegisteredServer.XML, RegisteredServer.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RegisteredServer obj = (RegisteredServer) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getServerUri() );
    				calculator.putString(null,  (obj==null)?null:obj.getProductUri() );
    				calculator.putLocalizedTextArray(null, ((obj==null)?null:obj.getServerNames()) );
    				calculator.putEnumeration(null, null /*obj.getServerType()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getGatewayServerUri() );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getDiscoveryUrls()) );
    				calculator.putString(null,  (obj==null)?null:obj.getSemaphoreFilePath() );
    				calculator.putBoolean(null, null /*obj.getIsOnline()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RegisteredServer obj = (RegisteredServer) encodeable;
    				encoder.putString("ServerUri",  (obj==null)?null:obj.getServerUri() );
    				encoder.putString("ProductUri",  (obj==null)?null:obj.getProductUri() );
    				encoder.putLocalizedTextArray("ServerNames", (obj==null)?null:obj.getServerNames() );
    				encoder.putEnumeration("ServerType",  (obj==null)?null:obj.getServerType() );
    				encoder.putString("GatewayServerUri",  (obj==null)?null:obj.getGatewayServerUri() );
    				encoder.putStringArray("DiscoveryUrls", (obj==null)?null:obj.getDiscoveryUrls() );
    				encoder.putString("SemaphoreFilePath",  (obj==null)?null:obj.getSemaphoreFilePath() );
    				encoder.putBoolean("IsOnline",  (obj==null)?null:obj.getIsOnline() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RegisteredServer result = new RegisteredServer();
    				result.setServerUri( decoder.getString("ServerUri") );
    				result.setProductUri( decoder.getString("ProductUri") );
    				result.setServerNames( decoder.getLocalizedTextArray("ServerNames") );
    				result.setServerType( decoder.getEnumeration("ServerType", ApplicationType.class) );
    				result.setGatewayServerUri( decoder.getString("GatewayServerUri") );
    				result.setDiscoveryUrls( decoder.getStringArray("DiscoveryUrls") );
    				result.setSemaphoreFilePath( decoder.getString("SemaphoreFilePath") );
    				result.setIsOnline( decoder.getBoolean("IsOnline") );
    				return result;
    			}
    		});
    
    	// RegisterServerRequest
    	addSerializer(
    		new AbstractSerializer(RegisterServerRequest.class, RegisterServerRequest.BINARY, RegisterServerRequest.XML, RegisterServerRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RegisterServerRequest obj = (RegisterServerRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeable(null, RegisteredServer.class, (obj==null)?null:obj.getServer());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RegisterServerRequest obj = (RegisterServerRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeable("Server", RegisteredServer.class, (obj==null)?null:obj.getServer());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RegisterServerRequest result = new RegisterServerRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setServer( decoder.getEncodeable("Server", RegisteredServer.class) );
    				return result;
    			}
    		});
    
    	// RegisterServerResponse
    	addSerializer(
    		new AbstractSerializer(RegisterServerResponse.class, RegisterServerResponse.BINARY, RegisterServerResponse.XML, RegisterServerResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RegisterServerResponse obj = (RegisterServerResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RegisterServerResponse obj = (RegisterServerResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RegisterServerResponse result = new RegisterServerResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				return result;
    			}
    		});
    
    	// DiscoveryConfiguration
    	addSerializer(
    		new AbstractSerializer(DiscoveryConfiguration.class, DiscoveryConfiguration.BINARY, DiscoveryConfiguration.XML, DiscoveryConfiguration.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DiscoveryConfiguration result = new DiscoveryConfiguration();
    				return result;
    			}
    		});
    
    	// MdnsDiscoveryConfiguration
    	addSerializer(
    		new AbstractSerializer(MdnsDiscoveryConfiguration.class, MdnsDiscoveryConfiguration.BINARY, MdnsDiscoveryConfiguration.XML, MdnsDiscoveryConfiguration.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				MdnsDiscoveryConfiguration obj = (MdnsDiscoveryConfiguration) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getMdnsServerName() );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getServerCapabilities()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				MdnsDiscoveryConfiguration obj = (MdnsDiscoveryConfiguration) encodeable;
    				encoder.putString("MdnsServerName",  (obj==null)?null:obj.getMdnsServerName() );
    				encoder.putStringArray("ServerCapabilities", (obj==null)?null:obj.getServerCapabilities() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MdnsDiscoveryConfiguration result = new MdnsDiscoveryConfiguration();
    				result.setMdnsServerName( decoder.getString("MdnsServerName") );
    				result.setServerCapabilities( decoder.getStringArray("ServerCapabilities") );
    				return result;
    			}
    		});
    
    	// RegisterServer2Request
    	addSerializer(
    		new AbstractSerializer(RegisterServer2Request.class, RegisterServer2Request.BINARY, RegisterServer2Request.XML, RegisterServer2Request.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RegisterServer2Request obj = (RegisterServer2Request) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeable(null, RegisteredServer.class, (obj==null)?null:obj.getServer());
    				calculator.putExtensionObjectArray(null, ((obj==null)?null:obj.getDiscoveryConfiguration()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RegisterServer2Request obj = (RegisterServer2Request) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeable("Server", RegisteredServer.class, (obj==null)?null:obj.getServer());
    				encoder.putExtensionObjectArray("DiscoveryConfiguration", (obj==null)?null:obj.getDiscoveryConfiguration() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RegisterServer2Request result = new RegisterServer2Request();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setServer( decoder.getEncodeable("Server", RegisteredServer.class) );
    				result.setDiscoveryConfiguration( decoder.getExtensionObjectArray("DiscoveryConfiguration") );
    				return result;
    			}
    		});
    
    	// RegisterServer2Response
    	addSerializer(
    		new AbstractSerializer(RegisterServer2Response.class, RegisterServer2Response.BINARY, RegisterServer2Response.XML, RegisterServer2Response.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RegisterServer2Response obj = (RegisterServer2Response) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getConfigurationResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RegisterServer2Response obj = (RegisterServer2Response) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putStatusCodeArray("ConfigurationResults", (obj==null)?null:obj.getConfigurationResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RegisterServer2Response result = new RegisterServer2Response();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setConfigurationResults( decoder.getStatusCodeArray("ConfigurationResults") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// ChannelSecurityToken
    	addSerializer(
    		new AbstractSerializer(ChannelSecurityToken.class, ChannelSecurityToken.BINARY, ChannelSecurityToken.XML, ChannelSecurityToken.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ChannelSecurityToken obj = (ChannelSecurityToken) encodeable;
    				calculator.putUInt32(null, null /*obj.getChannelId()*/);
    				calculator.putUInt32(null, null /*obj.getTokenId()*/);
    				calculator.putDateTime(null,  (obj==null)?null:obj.getCreatedAt() );
    				calculator.putUInt32(null, null /*obj.getRevisedLifetime()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ChannelSecurityToken obj = (ChannelSecurityToken) encodeable;
    				encoder.putUInt32("ChannelId",  (obj==null)?null:obj.getChannelId() );
    				encoder.putUInt32("TokenId",  (obj==null)?null:obj.getTokenId() );
    				encoder.putDateTime("CreatedAt",  (obj==null)?null:obj.getCreatedAt() );
    				encoder.putUInt32("RevisedLifetime",  (obj==null)?null:obj.getRevisedLifetime() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ChannelSecurityToken result = new ChannelSecurityToken();
    				result.setChannelId( decoder.getUInt32("ChannelId") );
    				result.setTokenId( decoder.getUInt32("TokenId") );
    				result.setCreatedAt( decoder.getDateTime("CreatedAt") );
    				result.setRevisedLifetime( decoder.getUInt32("RevisedLifetime") );
    				return result;
    			}
    		});
    
    	// OpenSecureChannelRequest
    	addSerializer(
    		new AbstractSerializer(OpenSecureChannelRequest.class, OpenSecureChannelRequest.BINARY, OpenSecureChannelRequest.XML, OpenSecureChannelRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				OpenSecureChannelRequest obj = (OpenSecureChannelRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32(null, null /*obj.getClientProtocolVersion()*/);
    				calculator.putEnumeration(null, null /*obj.getRequestType()*/);
    				calculator.putEnumeration(null, null /*obj.getSecurityMode()*/);
    				calculator.putByteString(null,  (obj==null)?null:obj.getClientNonce() );
    				calculator.putUInt32(null, null /*obj.getRequestedLifetime()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				OpenSecureChannelRequest obj = (OpenSecureChannelRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32("ClientProtocolVersion",  (obj==null)?null:obj.getClientProtocolVersion() );
    				encoder.putEnumeration("RequestType",  (obj==null)?null:obj.getRequestType() );
    				encoder.putEnumeration("SecurityMode",  (obj==null)?null:obj.getSecurityMode() );
    				encoder.putByteString("ClientNonce",  (obj==null)?null:obj.getClientNonce() );
    				encoder.putUInt32("RequestedLifetime",  (obj==null)?null:obj.getRequestedLifetime() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				OpenSecureChannelRequest result = new OpenSecureChannelRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setClientProtocolVersion( decoder.getUInt32("ClientProtocolVersion") );
    				result.setRequestType( decoder.getEnumeration("RequestType", SecurityTokenRequestType.class) );
    				result.setSecurityMode( decoder.getEnumeration("SecurityMode", MessageSecurityMode.class) );
    				result.setClientNonce( decoder.getByteString("ClientNonce") );
    				result.setRequestedLifetime( decoder.getUInt32("RequestedLifetime") );
    				return result;
    			}
    		});
    
    	// OpenSecureChannelResponse
    	addSerializer(
    		new AbstractSerializer(OpenSecureChannelResponse.class, OpenSecureChannelResponse.BINARY, OpenSecureChannelResponse.XML, OpenSecureChannelResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				OpenSecureChannelResponse obj = (OpenSecureChannelResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putUInt32(null, null /*obj.getServerProtocolVersion()*/);
    				calculator.putEncodeable(null, ChannelSecurityToken.class, (obj==null)?null:obj.getSecurityToken());
    				calculator.putByteString(null,  (obj==null)?null:obj.getServerNonce() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				OpenSecureChannelResponse obj = (OpenSecureChannelResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putUInt32("ServerProtocolVersion",  (obj==null)?null:obj.getServerProtocolVersion() );
    				encoder.putEncodeable("SecurityToken", ChannelSecurityToken.class, (obj==null)?null:obj.getSecurityToken());
    				encoder.putByteString("ServerNonce",  (obj==null)?null:obj.getServerNonce() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				OpenSecureChannelResponse result = new OpenSecureChannelResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setServerProtocolVersion( decoder.getUInt32("ServerProtocolVersion") );
    				result.setSecurityToken( decoder.getEncodeable("SecurityToken", ChannelSecurityToken.class) );
    				result.setServerNonce( decoder.getByteString("ServerNonce") );
    				return result;
    			}
    		});
    
    	// CloseSecureChannelRequest
    	addSerializer(
    		new AbstractSerializer(CloseSecureChannelRequest.class, CloseSecureChannelRequest.BINARY, CloseSecureChannelRequest.XML, CloseSecureChannelRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CloseSecureChannelRequest obj = (CloseSecureChannelRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CloseSecureChannelRequest obj = (CloseSecureChannelRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CloseSecureChannelRequest result = new CloseSecureChannelRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				return result;
    			}
    		});
    
    	// CloseSecureChannelResponse
    	addSerializer(
    		new AbstractSerializer(CloseSecureChannelResponse.class, CloseSecureChannelResponse.BINARY, CloseSecureChannelResponse.XML, CloseSecureChannelResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CloseSecureChannelResponse obj = (CloseSecureChannelResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CloseSecureChannelResponse obj = (CloseSecureChannelResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CloseSecureChannelResponse result = new CloseSecureChannelResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				return result;
    			}
    		});
    
    	// SignedSoftwareCertificate
    	addSerializer(
    		new AbstractSerializer(SignedSoftwareCertificate.class, SignedSoftwareCertificate.BINARY, SignedSoftwareCertificate.XML, SignedSoftwareCertificate.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SignedSoftwareCertificate obj = (SignedSoftwareCertificate) encodeable;
    				calculator.putByteString(null,  (obj==null)?null:obj.getCertificateData() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getSignature() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SignedSoftwareCertificate obj = (SignedSoftwareCertificate) encodeable;
    				encoder.putByteString("CertificateData",  (obj==null)?null:obj.getCertificateData() );
    				encoder.putByteString("Signature",  (obj==null)?null:obj.getSignature() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SignedSoftwareCertificate result = new SignedSoftwareCertificate();
    				result.setCertificateData( decoder.getByteString("CertificateData") );
    				result.setSignature( decoder.getByteString("Signature") );
    				return result;
    			}
    		});
    
    	// SignatureData
    	addSerializer(
    		new AbstractSerializer(SignatureData.class, SignatureData.BINARY, SignatureData.XML, SignatureData.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SignatureData obj = (SignatureData) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getAlgorithm() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getSignature() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SignatureData obj = (SignatureData) encodeable;
    				encoder.putString("Algorithm",  (obj==null)?null:obj.getAlgorithm() );
    				encoder.putByteString("Signature",  (obj==null)?null:obj.getSignature() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SignatureData result = new SignatureData();
    				result.setAlgorithm( decoder.getString("Algorithm") );
    				result.setSignature( decoder.getByteString("Signature") );
    				return result;
    			}
    		});
    
    	// CreateSessionRequest
    	addSerializer(
    		new AbstractSerializer(CreateSessionRequest.class, CreateSessionRequest.BINARY, CreateSessionRequest.XML, CreateSessionRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CreateSessionRequest obj = (CreateSessionRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeable(null, ApplicationDescription.class, (obj==null)?null:obj.getClientDescription());
    				calculator.putString(null,  (obj==null)?null:obj.getServerUri() );
    				calculator.putString(null,  (obj==null)?null:obj.getEndpointUrl() );
    				calculator.putString(null,  (obj==null)?null:obj.getSessionName() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getClientNonce() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getClientCertificate() );
    				calculator.putDouble(null, null /*obj.getRequestedSessionTimeout()*/);
    				calculator.putUInt32(null, null /*obj.getMaxResponseMessageSize()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CreateSessionRequest obj = (CreateSessionRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeable("ClientDescription", ApplicationDescription.class, (obj==null)?null:obj.getClientDescription());
    				encoder.putString("ServerUri",  (obj==null)?null:obj.getServerUri() );
    				encoder.putString("EndpointUrl",  (obj==null)?null:obj.getEndpointUrl() );
    				encoder.putString("SessionName",  (obj==null)?null:obj.getSessionName() );
    				encoder.putByteString("ClientNonce",  (obj==null)?null:obj.getClientNonce() );
    				encoder.putByteString("ClientCertificate",  (obj==null)?null:obj.getClientCertificate() );
    				encoder.putDouble("RequestedSessionTimeout",  (obj==null)?null:obj.getRequestedSessionTimeout() );
    				encoder.putUInt32("MaxResponseMessageSize",  (obj==null)?null:obj.getMaxResponseMessageSize() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CreateSessionRequest result = new CreateSessionRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setClientDescription( decoder.getEncodeable("ClientDescription", ApplicationDescription.class) );
    				result.setServerUri( decoder.getString("ServerUri") );
    				result.setEndpointUrl( decoder.getString("EndpointUrl") );
    				result.setSessionName( decoder.getString("SessionName") );
    				result.setClientNonce( decoder.getByteString("ClientNonce") );
    				result.setClientCertificate( decoder.getByteString("ClientCertificate") );
    				result.setRequestedSessionTimeout( decoder.getDouble("RequestedSessionTimeout") );
    				result.setMaxResponseMessageSize( decoder.getUInt32("MaxResponseMessageSize") );
    				return result;
    			}
    		});
    
    	// CreateSessionResponse
    	addSerializer(
    		new AbstractSerializer(CreateSessionResponse.class, CreateSessionResponse.BINARY, CreateSessionResponse.XML, CreateSessionResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CreateSessionResponse obj = (CreateSessionResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putNodeId(null,  (obj==null)?null:obj.getSessionId() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getAuthenticationToken() );
    				calculator.putDouble(null, null /*obj.getRevisedSessionTimeout()*/);
    				calculator.putByteString(null,  (obj==null)?null:obj.getServerNonce() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getServerCertificate() );
    				calculator.putEncodeableArray(null, EndpointDescription.class, (obj==null)?null:obj.getServerEndpoints());
    				calculator.putEncodeableArray(null, SignedSoftwareCertificate.class, (obj==null)?null:obj.getServerSoftwareCertificates());
    				calculator.putEncodeable(null, SignatureData.class, (obj==null)?null:obj.getServerSignature());
    				calculator.putUInt32(null, null /*obj.getMaxRequestMessageSize()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CreateSessionResponse obj = (CreateSessionResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putNodeId("SessionId",  (obj==null)?null:obj.getSessionId() );
    				encoder.putNodeId("AuthenticationToken",  (obj==null)?null:obj.getAuthenticationToken() );
    				encoder.putDouble("RevisedSessionTimeout",  (obj==null)?null:obj.getRevisedSessionTimeout() );
    				encoder.putByteString("ServerNonce",  (obj==null)?null:obj.getServerNonce() );
    				encoder.putByteString("ServerCertificate",  (obj==null)?null:obj.getServerCertificate() );
    				encoder.putEncodeableArray("ServerEndpoints", EndpointDescription.class, (obj==null)?null:obj.getServerEndpoints());
    				encoder.putEncodeableArray("ServerSoftwareCertificates", SignedSoftwareCertificate.class, (obj==null)?null:obj.getServerSoftwareCertificates());
    				encoder.putEncodeable("ServerSignature", SignatureData.class, (obj==null)?null:obj.getServerSignature());
    				encoder.putUInt32("MaxRequestMessageSize",  (obj==null)?null:obj.getMaxRequestMessageSize() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CreateSessionResponse result = new CreateSessionResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setSessionId( decoder.getNodeId("SessionId") );
    				result.setAuthenticationToken( decoder.getNodeId("AuthenticationToken") );
    				result.setRevisedSessionTimeout( decoder.getDouble("RevisedSessionTimeout") );
    				result.setServerNonce( decoder.getByteString("ServerNonce") );
    				result.setServerCertificate( decoder.getByteString("ServerCertificate") );
    				result.setServerEndpoints( decoder.getEncodeableArray("ServerEndpoints", EndpointDescription.class) );
    				result.setServerSoftwareCertificates( decoder.getEncodeableArray("ServerSoftwareCertificates", SignedSoftwareCertificate.class) );
    				result.setServerSignature( decoder.getEncodeable("ServerSignature", SignatureData.class) );
    				result.setMaxRequestMessageSize( decoder.getUInt32("MaxRequestMessageSize") );
    				return result;
    			}
    		});
    
    	// UserIdentityToken
    	addSerializer(
    		new AbstractSerializer(UserIdentityToken.class, UserIdentityToken.BINARY, UserIdentityToken.XML, UserIdentityToken.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				UserIdentityToken obj = (UserIdentityToken) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getPolicyId() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				UserIdentityToken obj = (UserIdentityToken) encodeable;
    				encoder.putString("PolicyId",  (obj==null)?null:obj.getPolicyId() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				UserIdentityToken result = new UserIdentityToken();
    				result.setPolicyId( decoder.getString("PolicyId") );
    				return result;
    			}
    		});
    
    	// AnonymousIdentityToken
    	addSerializer(
    		new AbstractSerializer(AnonymousIdentityToken.class, AnonymousIdentityToken.BINARY, AnonymousIdentityToken.XML, AnonymousIdentityToken.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AnonymousIdentityToken obj = (AnonymousIdentityToken) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getPolicyId() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AnonymousIdentityToken obj = (AnonymousIdentityToken) encodeable;
    				encoder.putString("PolicyId",  (obj==null)?null:obj.getPolicyId() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AnonymousIdentityToken result = new AnonymousIdentityToken();
    				result.setPolicyId( decoder.getString("PolicyId") );
    				return result;
    			}
    		});
    
    	// UserNameIdentityToken
    	addSerializer(
    		new AbstractSerializer(UserNameIdentityToken.class, UserNameIdentityToken.BINARY, UserNameIdentityToken.XML, UserNameIdentityToken.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				UserNameIdentityToken obj = (UserNameIdentityToken) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getPolicyId() );
    				calculator.putString(null,  (obj==null)?null:obj.getUserName() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getPassword() );
    				calculator.putString(null,  (obj==null)?null:obj.getEncryptionAlgorithm() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				UserNameIdentityToken obj = (UserNameIdentityToken) encodeable;
    				encoder.putString("PolicyId",  (obj==null)?null:obj.getPolicyId() );
    				encoder.putString("UserName",  (obj==null)?null:obj.getUserName() );
    				encoder.putByteString("Password",  (obj==null)?null:obj.getPassword() );
    				encoder.putString("EncryptionAlgorithm",  (obj==null)?null:obj.getEncryptionAlgorithm() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				UserNameIdentityToken result = new UserNameIdentityToken();
    				result.setPolicyId( decoder.getString("PolicyId") );
    				result.setUserName( decoder.getString("UserName") );
    				result.setPassword( decoder.getByteString("Password") );
    				result.setEncryptionAlgorithm( decoder.getString("EncryptionAlgorithm") );
    				return result;
    			}
    		});
    
    	// X509IdentityToken
    	addSerializer(
    		new AbstractSerializer(X509IdentityToken.class, X509IdentityToken.BINARY, X509IdentityToken.XML, X509IdentityToken.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				X509IdentityToken obj = (X509IdentityToken) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getPolicyId() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getCertificateData() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				X509IdentityToken obj = (X509IdentityToken) encodeable;
    				encoder.putString("PolicyId",  (obj==null)?null:obj.getPolicyId() );
    				encoder.putByteString("CertificateData",  (obj==null)?null:obj.getCertificateData() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				X509IdentityToken result = new X509IdentityToken();
    				result.setPolicyId( decoder.getString("PolicyId") );
    				result.setCertificateData( decoder.getByteString("CertificateData") );
    				return result;
    			}
    		});
    
    	// IssuedIdentityToken
    	addSerializer(
    		new AbstractSerializer(IssuedIdentityToken.class, IssuedIdentityToken.BINARY, IssuedIdentityToken.XML, IssuedIdentityToken.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				IssuedIdentityToken obj = (IssuedIdentityToken) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getPolicyId() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getTokenData() );
    				calculator.putString(null,  (obj==null)?null:obj.getEncryptionAlgorithm() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				IssuedIdentityToken obj = (IssuedIdentityToken) encodeable;
    				encoder.putString("PolicyId",  (obj==null)?null:obj.getPolicyId() );
    				encoder.putByteString("TokenData",  (obj==null)?null:obj.getTokenData() );
    				encoder.putString("EncryptionAlgorithm",  (obj==null)?null:obj.getEncryptionAlgorithm() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				IssuedIdentityToken result = new IssuedIdentityToken();
    				result.setPolicyId( decoder.getString("PolicyId") );
    				result.setTokenData( decoder.getByteString("TokenData") );
    				result.setEncryptionAlgorithm( decoder.getString("EncryptionAlgorithm") );
    				return result;
    			}
    		});
    
    	// ActivateSessionRequest
    	addSerializer(
    		new AbstractSerializer(ActivateSessionRequest.class, ActivateSessionRequest.BINARY, ActivateSessionRequest.XML, ActivateSessionRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ActivateSessionRequest obj = (ActivateSessionRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeable(null, SignatureData.class, (obj==null)?null:obj.getClientSignature());
    				calculator.putEncodeableArray(null, SignedSoftwareCertificate.class, (obj==null)?null:obj.getClientSoftwareCertificates());
    				calculator.putStringArray(null, ((obj==null)?null:obj.getLocaleIds()) );
    				calculator.putExtensionObject(null,  (obj==null)?null:obj.getUserIdentityToken() );
    				calculator.putEncodeable(null, SignatureData.class, (obj==null)?null:obj.getUserTokenSignature());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ActivateSessionRequest obj = (ActivateSessionRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeable("ClientSignature", SignatureData.class, (obj==null)?null:obj.getClientSignature());
    				encoder.putEncodeableArray("ClientSoftwareCertificates", SignedSoftwareCertificate.class, (obj==null)?null:obj.getClientSoftwareCertificates());
    				encoder.putStringArray("LocaleIds", (obj==null)?null:obj.getLocaleIds() );
    				encoder.putExtensionObject("UserIdentityToken",  (obj==null)?null:obj.getUserIdentityToken() );
    				encoder.putEncodeable("UserTokenSignature", SignatureData.class, (obj==null)?null:obj.getUserTokenSignature());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ActivateSessionRequest result = new ActivateSessionRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setClientSignature( decoder.getEncodeable("ClientSignature", SignatureData.class) );
    				result.setClientSoftwareCertificates( decoder.getEncodeableArray("ClientSoftwareCertificates", SignedSoftwareCertificate.class) );
    				result.setLocaleIds( decoder.getStringArray("LocaleIds") );
    				result.setUserIdentityToken( decoder.getExtensionObject("UserIdentityToken") );
    				result.setUserTokenSignature( decoder.getEncodeable("UserTokenSignature", SignatureData.class) );
    				return result;
    			}
    		});
    
    	// ActivateSessionResponse
    	addSerializer(
    		new AbstractSerializer(ActivateSessionResponse.class, ActivateSessionResponse.BINARY, ActivateSessionResponse.XML, ActivateSessionResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ActivateSessionResponse obj = (ActivateSessionResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putByteString(null,  (obj==null)?null:obj.getServerNonce() );
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ActivateSessionResponse obj = (ActivateSessionResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putByteString("ServerNonce",  (obj==null)?null:obj.getServerNonce() );
    				encoder.putStatusCodeArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ActivateSessionResponse result = new ActivateSessionResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setServerNonce( decoder.getByteString("ServerNonce") );
    				result.setResults( decoder.getStatusCodeArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// CloseSessionRequest
    	addSerializer(
    		new AbstractSerializer(CloseSessionRequest.class, CloseSessionRequest.BINARY, CloseSessionRequest.XML, CloseSessionRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CloseSessionRequest obj = (CloseSessionRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putBoolean(null, null /*obj.getDeleteSubscriptions()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CloseSessionRequest obj = (CloseSessionRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putBoolean("DeleteSubscriptions",  (obj==null)?null:obj.getDeleteSubscriptions() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CloseSessionRequest result = new CloseSessionRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setDeleteSubscriptions( decoder.getBoolean("DeleteSubscriptions") );
    				return result;
    			}
    		});
    
    	// CloseSessionResponse
    	addSerializer(
    		new AbstractSerializer(CloseSessionResponse.class, CloseSessionResponse.BINARY, CloseSessionResponse.XML, CloseSessionResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CloseSessionResponse obj = (CloseSessionResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CloseSessionResponse obj = (CloseSessionResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CloseSessionResponse result = new CloseSessionResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				return result;
    			}
    		});
    
    	// CancelRequest
    	addSerializer(
    		new AbstractSerializer(CancelRequest.class, CancelRequest.BINARY, CancelRequest.XML, CancelRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CancelRequest obj = (CancelRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32(null, null /*obj.getRequestHandle()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CancelRequest obj = (CancelRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32("RequestHandle",  (obj==null)?null:obj.getRequestHandle() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CancelRequest result = new CancelRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setRequestHandle( decoder.getUInt32("RequestHandle") );
    				return result;
    			}
    		});
    
    	// CancelResponse
    	addSerializer(
    		new AbstractSerializer(CancelResponse.class, CancelResponse.BINARY, CancelResponse.XML, CancelResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CancelResponse obj = (CancelResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putUInt32(null, null /*obj.getCancelCount()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CancelResponse obj = (CancelResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putUInt32("CancelCount",  (obj==null)?null:obj.getCancelCount() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CancelResponse result = new CancelResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setCancelCount( decoder.getUInt32("CancelCount") );
    				return result;
    			}
    		});
    
    	// NodeAttributes
    	addSerializer(
    		new AbstractSerializer(NodeAttributes.class, NodeAttributes.BINARY, NodeAttributes.XML, NodeAttributes.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				NodeAttributes obj = (NodeAttributes) encodeable;
    				calculator.putUInt32(null, null /*obj.getSpecifiedAttributes()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				NodeAttributes obj = (NodeAttributes) encodeable;
    				encoder.putUInt32("SpecifiedAttributes",  (obj==null)?null:obj.getSpecifiedAttributes() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				NodeAttributes result = new NodeAttributes();
    				result.setSpecifiedAttributes( decoder.getUInt32("SpecifiedAttributes") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				return result;
    			}
    		});
    
    	// ObjectAttributes
    	addSerializer(
    		new AbstractSerializer(ObjectAttributes.class, ObjectAttributes.BINARY, ObjectAttributes.XML, ObjectAttributes.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ObjectAttributes obj = (ObjectAttributes) encodeable;
    				calculator.putUInt32(null, null /*obj.getSpecifiedAttributes()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putByte(null, null /*obj.getEventNotifier()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ObjectAttributes obj = (ObjectAttributes) encodeable;
    				encoder.putUInt32("SpecifiedAttributes",  (obj==null)?null:obj.getSpecifiedAttributes() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putByte("EventNotifier",  (obj==null)?null:obj.getEventNotifier() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ObjectAttributes result = new ObjectAttributes();
    				result.setSpecifiedAttributes( decoder.getUInt32("SpecifiedAttributes") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setEventNotifier( decoder.getByte("EventNotifier") );
    				return result;
    			}
    		});
    
    	// VariableAttributes
    	addSerializer(
    		new AbstractSerializer(VariableAttributes.class, VariableAttributes.BINARY, VariableAttributes.XML, VariableAttributes.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				VariableAttributes obj = (VariableAttributes) encodeable;
    				calculator.putUInt32(null, null /*obj.getSpecifiedAttributes()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putVariant(null,  (obj==null)?null:obj.getValue() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getDataType() );
    				calculator.putInt32(null, null /*obj.getValueRank()*/);
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getArrayDimensions()) );
    				calculator.putByte(null, null /*obj.getAccessLevel()*/);
    				calculator.putByte(null, null /*obj.getUserAccessLevel()*/);
    				calculator.putDouble(null, null /*obj.getMinimumSamplingInterval()*/);
    				calculator.putBoolean(null, null /*obj.getHistorizing()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				VariableAttributes obj = (VariableAttributes) encodeable;
    				encoder.putUInt32("SpecifiedAttributes",  (obj==null)?null:obj.getSpecifiedAttributes() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putVariant("Value",  (obj==null)?null:obj.getValue() );
    				encoder.putNodeId("DataType",  (obj==null)?null:obj.getDataType() );
    				encoder.putInt32("ValueRank",  (obj==null)?null:obj.getValueRank() );
    				encoder.putUInt32Array("ArrayDimensions", (obj==null)?null:obj.getArrayDimensions() );
    				encoder.putByte("AccessLevel",  (obj==null)?null:obj.getAccessLevel() );
    				encoder.putByte("UserAccessLevel",  (obj==null)?null:obj.getUserAccessLevel() );
    				encoder.putDouble("MinimumSamplingInterval",  (obj==null)?null:obj.getMinimumSamplingInterval() );
    				encoder.putBoolean("Historizing",  (obj==null)?null:obj.getHistorizing() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				VariableAttributes result = new VariableAttributes();
    				result.setSpecifiedAttributes( decoder.getUInt32("SpecifiedAttributes") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setValue( decoder.getVariant("Value") );
    				result.setDataType( decoder.getNodeId("DataType") );
    				result.setValueRank( decoder.getInt32("ValueRank") );
    				result.setArrayDimensions( decoder.getUInt32Array("ArrayDimensions") );
    				result.setAccessLevel( decoder.getByte("AccessLevel") );
    				result.setUserAccessLevel( decoder.getByte("UserAccessLevel") );
    				result.setMinimumSamplingInterval( decoder.getDouble("MinimumSamplingInterval") );
    				result.setHistorizing( decoder.getBoolean("Historizing") );
    				return result;
    			}
    		});
    
    	// MethodAttributes
    	addSerializer(
    		new AbstractSerializer(MethodAttributes.class, MethodAttributes.BINARY, MethodAttributes.XML, MethodAttributes.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				MethodAttributes obj = (MethodAttributes) encodeable;
    				calculator.putUInt32(null, null /*obj.getSpecifiedAttributes()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putBoolean(null, null /*obj.getExecutable()*/);
    				calculator.putBoolean(null, null /*obj.getUserExecutable()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				MethodAttributes obj = (MethodAttributes) encodeable;
    				encoder.putUInt32("SpecifiedAttributes",  (obj==null)?null:obj.getSpecifiedAttributes() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putBoolean("Executable",  (obj==null)?null:obj.getExecutable() );
    				encoder.putBoolean("UserExecutable",  (obj==null)?null:obj.getUserExecutable() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MethodAttributes result = new MethodAttributes();
    				result.setSpecifiedAttributes( decoder.getUInt32("SpecifiedAttributes") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setExecutable( decoder.getBoolean("Executable") );
    				result.setUserExecutable( decoder.getBoolean("UserExecutable") );
    				return result;
    			}
    		});
    
    	// ObjectTypeAttributes
    	addSerializer(
    		new AbstractSerializer(ObjectTypeAttributes.class, ObjectTypeAttributes.BINARY, ObjectTypeAttributes.XML, ObjectTypeAttributes.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ObjectTypeAttributes obj = (ObjectTypeAttributes) encodeable;
    				calculator.putUInt32(null, null /*obj.getSpecifiedAttributes()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putBoolean(null, null /*obj.getIsAbstract()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ObjectTypeAttributes obj = (ObjectTypeAttributes) encodeable;
    				encoder.putUInt32("SpecifiedAttributes",  (obj==null)?null:obj.getSpecifiedAttributes() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putBoolean("IsAbstract",  (obj==null)?null:obj.getIsAbstract() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ObjectTypeAttributes result = new ObjectTypeAttributes();
    				result.setSpecifiedAttributes( decoder.getUInt32("SpecifiedAttributes") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setIsAbstract( decoder.getBoolean("IsAbstract") );
    				return result;
    			}
    		});
    
    	// VariableTypeAttributes
    	addSerializer(
    		new AbstractSerializer(VariableTypeAttributes.class, VariableTypeAttributes.BINARY, VariableTypeAttributes.XML, VariableTypeAttributes.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				VariableTypeAttributes obj = (VariableTypeAttributes) encodeable;
    				calculator.putUInt32(null, null /*obj.getSpecifiedAttributes()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putVariant(null,  (obj==null)?null:obj.getValue() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getDataType() );
    				calculator.putInt32(null, null /*obj.getValueRank()*/);
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getArrayDimensions()) );
    				calculator.putBoolean(null, null /*obj.getIsAbstract()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				VariableTypeAttributes obj = (VariableTypeAttributes) encodeable;
    				encoder.putUInt32("SpecifiedAttributes",  (obj==null)?null:obj.getSpecifiedAttributes() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putVariant("Value",  (obj==null)?null:obj.getValue() );
    				encoder.putNodeId("DataType",  (obj==null)?null:obj.getDataType() );
    				encoder.putInt32("ValueRank",  (obj==null)?null:obj.getValueRank() );
    				encoder.putUInt32Array("ArrayDimensions", (obj==null)?null:obj.getArrayDimensions() );
    				encoder.putBoolean("IsAbstract",  (obj==null)?null:obj.getIsAbstract() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				VariableTypeAttributes result = new VariableTypeAttributes();
    				result.setSpecifiedAttributes( decoder.getUInt32("SpecifiedAttributes") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setValue( decoder.getVariant("Value") );
    				result.setDataType( decoder.getNodeId("DataType") );
    				result.setValueRank( decoder.getInt32("ValueRank") );
    				result.setArrayDimensions( decoder.getUInt32Array("ArrayDimensions") );
    				result.setIsAbstract( decoder.getBoolean("IsAbstract") );
    				return result;
    			}
    		});
    
    	// ReferenceTypeAttributes
    	addSerializer(
    		new AbstractSerializer(ReferenceTypeAttributes.class, ReferenceTypeAttributes.BINARY, ReferenceTypeAttributes.XML, ReferenceTypeAttributes.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReferenceTypeAttributes obj = (ReferenceTypeAttributes) encodeable;
    				calculator.putUInt32(null, null /*obj.getSpecifiedAttributes()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putBoolean(null, null /*obj.getIsAbstract()*/);
    				calculator.putBoolean(null, null /*obj.getSymmetric()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getInverseName() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReferenceTypeAttributes obj = (ReferenceTypeAttributes) encodeable;
    				encoder.putUInt32("SpecifiedAttributes",  (obj==null)?null:obj.getSpecifiedAttributes() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putBoolean("IsAbstract",  (obj==null)?null:obj.getIsAbstract() );
    				encoder.putBoolean("Symmetric",  (obj==null)?null:obj.getSymmetric() );
    				encoder.putLocalizedText("InverseName",  (obj==null)?null:obj.getInverseName() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReferenceTypeAttributes result = new ReferenceTypeAttributes();
    				result.setSpecifiedAttributes( decoder.getUInt32("SpecifiedAttributes") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setIsAbstract( decoder.getBoolean("IsAbstract") );
    				result.setSymmetric( decoder.getBoolean("Symmetric") );
    				result.setInverseName( decoder.getLocalizedText("InverseName") );
    				return result;
    			}
    		});
    
    	// DataTypeAttributes
    	addSerializer(
    		new AbstractSerializer(DataTypeAttributes.class, DataTypeAttributes.BINARY, DataTypeAttributes.XML, DataTypeAttributes.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DataTypeAttributes obj = (DataTypeAttributes) encodeable;
    				calculator.putUInt32(null, null /*obj.getSpecifiedAttributes()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putBoolean(null, null /*obj.getIsAbstract()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DataTypeAttributes obj = (DataTypeAttributes) encodeable;
    				encoder.putUInt32("SpecifiedAttributes",  (obj==null)?null:obj.getSpecifiedAttributes() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putBoolean("IsAbstract",  (obj==null)?null:obj.getIsAbstract() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DataTypeAttributes result = new DataTypeAttributes();
    				result.setSpecifiedAttributes( decoder.getUInt32("SpecifiedAttributes") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setIsAbstract( decoder.getBoolean("IsAbstract") );
    				return result;
    			}
    		});
    
    	// ViewAttributes
    	addSerializer(
    		new AbstractSerializer(ViewAttributes.class, ViewAttributes.BINARY, ViewAttributes.XML, ViewAttributes.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ViewAttributes obj = (ViewAttributes) encodeable;
    				calculator.putUInt32(null, null /*obj.getSpecifiedAttributes()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    				calculator.putUInt32(null, null /*obj.getWriteMask()*/);
    				calculator.putUInt32(null, null /*obj.getUserWriteMask()*/);
    				calculator.putBoolean(null, null /*obj.getContainsNoLoops()*/);
    				calculator.putByte(null, null /*obj.getEventNotifier()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ViewAttributes obj = (ViewAttributes) encodeable;
    				encoder.putUInt32("SpecifiedAttributes",  (obj==null)?null:obj.getSpecifiedAttributes() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    				encoder.putUInt32("WriteMask",  (obj==null)?null:obj.getWriteMask() );
    				encoder.putUInt32("UserWriteMask",  (obj==null)?null:obj.getUserWriteMask() );
    				encoder.putBoolean("ContainsNoLoops",  (obj==null)?null:obj.getContainsNoLoops() );
    				encoder.putByte("EventNotifier",  (obj==null)?null:obj.getEventNotifier() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ViewAttributes result = new ViewAttributes();
    				result.setSpecifiedAttributes( decoder.getUInt32("SpecifiedAttributes") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				result.setWriteMask( decoder.getUInt32("WriteMask") );
    				result.setUserWriteMask( decoder.getUInt32("UserWriteMask") );
    				result.setContainsNoLoops( decoder.getBoolean("ContainsNoLoops") );
    				result.setEventNotifier( decoder.getByte("EventNotifier") );
    				return result;
    			}
    		});
    
    	// AddNodesItem
    	addSerializer(
    		new AbstractSerializer(AddNodesItem.class, AddNodesItem.BINARY, AddNodesItem.XML, AddNodesItem.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AddNodesItem obj = (AddNodesItem) encodeable;
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getParentNodeId() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getReferenceTypeId() );
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getRequestedNewNodeId() );
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putExtensionObject(null,  (obj==null)?null:obj.getNodeAttributes() );
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getTypeDefinition() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AddNodesItem obj = (AddNodesItem) encodeable;
    				encoder.putExpandedNodeId("ParentNodeId",  (obj==null)?null:obj.getParentNodeId() );
    				encoder.putNodeId("ReferenceTypeId",  (obj==null)?null:obj.getReferenceTypeId() );
    				encoder.putExpandedNodeId("RequestedNewNodeId",  (obj==null)?null:obj.getRequestedNewNodeId() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putExtensionObject("NodeAttributes",  (obj==null)?null:obj.getNodeAttributes() );
    				encoder.putExpandedNodeId("TypeDefinition",  (obj==null)?null:obj.getTypeDefinition() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AddNodesItem result = new AddNodesItem();
    				result.setParentNodeId( decoder.getExpandedNodeId("ParentNodeId") );
    				result.setReferenceTypeId( decoder.getNodeId("ReferenceTypeId") );
    				result.setRequestedNewNodeId( decoder.getExpandedNodeId("RequestedNewNodeId") );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setNodeAttributes( decoder.getExtensionObject("NodeAttributes") );
    				result.setTypeDefinition( decoder.getExpandedNodeId("TypeDefinition") );
    				return result;
    			}
    		});
    
    	// AddNodesResult
    	addSerializer(
    		new AbstractSerializer(AddNodesResult.class, AddNodesResult.BINARY, AddNodesResult.XML, AddNodesResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AddNodesResult obj = (AddNodesResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getAddedNodeId() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AddNodesResult obj = (AddNodesResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putNodeId("AddedNodeId",  (obj==null)?null:obj.getAddedNodeId() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AddNodesResult result = new AddNodesResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setAddedNodeId( decoder.getNodeId("AddedNodeId") );
    				return result;
    			}
    		});
    
    	// AddNodesRequest
    	addSerializer(
    		new AbstractSerializer(AddNodesRequest.class, AddNodesRequest.BINARY, AddNodesRequest.XML, AddNodesRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AddNodesRequest obj = (AddNodesRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeableArray(null, AddNodesItem.class, (obj==null)?null:obj.getNodesToAdd());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AddNodesRequest obj = (AddNodesRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeableArray("NodesToAdd", AddNodesItem.class, (obj==null)?null:obj.getNodesToAdd());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AddNodesRequest result = new AddNodesRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setNodesToAdd( decoder.getEncodeableArray("NodesToAdd", AddNodesItem.class) );
    				return result;
    			}
    		});
    
    	// AddNodesResponse
    	addSerializer(
    		new AbstractSerializer(AddNodesResponse.class, AddNodesResponse.BINARY, AddNodesResponse.XML, AddNodesResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AddNodesResponse obj = (AddNodesResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, AddNodesResult.class, (obj==null)?null:obj.getResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AddNodesResponse obj = (AddNodesResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Results", AddNodesResult.class, (obj==null)?null:obj.getResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AddNodesResponse result = new AddNodesResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getEncodeableArray("Results", AddNodesResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// AddReferencesItem
    	addSerializer(
    		new AbstractSerializer(AddReferencesItem.class, AddReferencesItem.BINARY, AddReferencesItem.XML, AddReferencesItem.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AddReferencesItem obj = (AddReferencesItem) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getSourceNodeId() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getReferenceTypeId() );
    				calculator.putBoolean(null, null /*obj.getIsForward()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getTargetServerUri() );
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getTargetNodeId() );
    				calculator.putEnumeration(null, null /*obj.getTargetNodeClass()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AddReferencesItem obj = (AddReferencesItem) encodeable;
    				encoder.putNodeId("SourceNodeId",  (obj==null)?null:obj.getSourceNodeId() );
    				encoder.putNodeId("ReferenceTypeId",  (obj==null)?null:obj.getReferenceTypeId() );
    				encoder.putBoolean("IsForward",  (obj==null)?null:obj.getIsForward() );
    				encoder.putString("TargetServerUri",  (obj==null)?null:obj.getTargetServerUri() );
    				encoder.putExpandedNodeId("TargetNodeId",  (obj==null)?null:obj.getTargetNodeId() );
    				encoder.putEnumeration("TargetNodeClass",  (obj==null)?null:obj.getTargetNodeClass() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AddReferencesItem result = new AddReferencesItem();
    				result.setSourceNodeId( decoder.getNodeId("SourceNodeId") );
    				result.setReferenceTypeId( decoder.getNodeId("ReferenceTypeId") );
    				result.setIsForward( decoder.getBoolean("IsForward") );
    				result.setTargetServerUri( decoder.getString("TargetServerUri") );
    				result.setTargetNodeId( decoder.getExpandedNodeId("TargetNodeId") );
    				result.setTargetNodeClass( decoder.getEnumeration("TargetNodeClass", NodeClass.class) );
    				return result;
    			}
    		});
    
    	// AddReferencesRequest
    	addSerializer(
    		new AbstractSerializer(AddReferencesRequest.class, AddReferencesRequest.BINARY, AddReferencesRequest.XML, AddReferencesRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AddReferencesRequest obj = (AddReferencesRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeableArray(null, AddReferencesItem.class, (obj==null)?null:obj.getReferencesToAdd());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AddReferencesRequest obj = (AddReferencesRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeableArray("ReferencesToAdd", AddReferencesItem.class, (obj==null)?null:obj.getReferencesToAdd());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AddReferencesRequest result = new AddReferencesRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setReferencesToAdd( decoder.getEncodeableArray("ReferencesToAdd", AddReferencesItem.class) );
    				return result;
    			}
    		});
    
    	// AddReferencesResponse
    	addSerializer(
    		new AbstractSerializer(AddReferencesResponse.class, AddReferencesResponse.BINARY, AddReferencesResponse.XML, AddReferencesResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AddReferencesResponse obj = (AddReferencesResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AddReferencesResponse obj = (AddReferencesResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putStatusCodeArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AddReferencesResponse result = new AddReferencesResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getStatusCodeArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// DeleteNodesItem
    	addSerializer(
    		new AbstractSerializer(DeleteNodesItem.class, DeleteNodesItem.BINARY, DeleteNodesItem.XML, DeleteNodesItem.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteNodesItem obj = (DeleteNodesItem) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putBoolean(null, null /*obj.getDeleteTargetReferences()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteNodesItem obj = (DeleteNodesItem) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putBoolean("DeleteTargetReferences",  (obj==null)?null:obj.getDeleteTargetReferences() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteNodesItem result = new DeleteNodesItem();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setDeleteTargetReferences( decoder.getBoolean("DeleteTargetReferences") );
    				return result;
    			}
    		});
    
    	// DeleteNodesRequest
    	addSerializer(
    		new AbstractSerializer(DeleteNodesRequest.class, DeleteNodesRequest.BINARY, DeleteNodesRequest.XML, DeleteNodesRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteNodesRequest obj = (DeleteNodesRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeableArray(null, DeleteNodesItem.class, (obj==null)?null:obj.getNodesToDelete());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteNodesRequest obj = (DeleteNodesRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeableArray("NodesToDelete", DeleteNodesItem.class, (obj==null)?null:obj.getNodesToDelete());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteNodesRequest result = new DeleteNodesRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setNodesToDelete( decoder.getEncodeableArray("NodesToDelete", DeleteNodesItem.class) );
    				return result;
    			}
    		});
    
    	// DeleteNodesResponse
    	addSerializer(
    		new AbstractSerializer(DeleteNodesResponse.class, DeleteNodesResponse.BINARY, DeleteNodesResponse.XML, DeleteNodesResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteNodesResponse obj = (DeleteNodesResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteNodesResponse obj = (DeleteNodesResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putStatusCodeArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteNodesResponse result = new DeleteNodesResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getStatusCodeArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// DeleteReferencesItem
    	addSerializer(
    		new AbstractSerializer(DeleteReferencesItem.class, DeleteReferencesItem.BINARY, DeleteReferencesItem.XML, DeleteReferencesItem.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteReferencesItem obj = (DeleteReferencesItem) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getSourceNodeId() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getReferenceTypeId() );
    				calculator.putBoolean(null, null /*obj.getIsForward()*/);
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getTargetNodeId() );
    				calculator.putBoolean(null, null /*obj.getDeleteBidirectional()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteReferencesItem obj = (DeleteReferencesItem) encodeable;
    				encoder.putNodeId("SourceNodeId",  (obj==null)?null:obj.getSourceNodeId() );
    				encoder.putNodeId("ReferenceTypeId",  (obj==null)?null:obj.getReferenceTypeId() );
    				encoder.putBoolean("IsForward",  (obj==null)?null:obj.getIsForward() );
    				encoder.putExpandedNodeId("TargetNodeId",  (obj==null)?null:obj.getTargetNodeId() );
    				encoder.putBoolean("DeleteBidirectional",  (obj==null)?null:obj.getDeleteBidirectional() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteReferencesItem result = new DeleteReferencesItem();
    				result.setSourceNodeId( decoder.getNodeId("SourceNodeId") );
    				result.setReferenceTypeId( decoder.getNodeId("ReferenceTypeId") );
    				result.setIsForward( decoder.getBoolean("IsForward") );
    				result.setTargetNodeId( decoder.getExpandedNodeId("TargetNodeId") );
    				result.setDeleteBidirectional( decoder.getBoolean("DeleteBidirectional") );
    				return result;
    			}
    		});
    
    	// DeleteReferencesRequest
    	addSerializer(
    		new AbstractSerializer(DeleteReferencesRequest.class, DeleteReferencesRequest.BINARY, DeleteReferencesRequest.XML, DeleteReferencesRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteReferencesRequest obj = (DeleteReferencesRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeableArray(null, DeleteReferencesItem.class, (obj==null)?null:obj.getReferencesToDelete());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteReferencesRequest obj = (DeleteReferencesRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeableArray("ReferencesToDelete", DeleteReferencesItem.class, (obj==null)?null:obj.getReferencesToDelete());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteReferencesRequest result = new DeleteReferencesRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setReferencesToDelete( decoder.getEncodeableArray("ReferencesToDelete", DeleteReferencesItem.class) );
    				return result;
    			}
    		});
    
    	// DeleteReferencesResponse
    	addSerializer(
    		new AbstractSerializer(DeleteReferencesResponse.class, DeleteReferencesResponse.BINARY, DeleteReferencesResponse.XML, DeleteReferencesResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteReferencesResponse obj = (DeleteReferencesResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteReferencesResponse obj = (DeleteReferencesResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putStatusCodeArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteReferencesResponse result = new DeleteReferencesResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getStatusCodeArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// ViewDescription
    	addSerializer(
    		new AbstractSerializer(ViewDescription.class, ViewDescription.BINARY, ViewDescription.XML, ViewDescription.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ViewDescription obj = (ViewDescription) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getViewId() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getTimestamp() );
    				calculator.putUInt32(null, null /*obj.getViewVersion()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ViewDescription obj = (ViewDescription) encodeable;
    				encoder.putNodeId("ViewId",  (obj==null)?null:obj.getViewId() );
    				encoder.putDateTime("Timestamp",  (obj==null)?null:obj.getTimestamp() );
    				encoder.putUInt32("ViewVersion",  (obj==null)?null:obj.getViewVersion() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ViewDescription result = new ViewDescription();
    				result.setViewId( decoder.getNodeId("ViewId") );
    				result.setTimestamp( decoder.getDateTime("Timestamp") );
    				result.setViewVersion( decoder.getUInt32("ViewVersion") );
    				return result;
    			}
    		});
    
    	// BrowseDescription
    	addSerializer(
    		new AbstractSerializer(BrowseDescription.class, BrowseDescription.BINARY, BrowseDescription.XML, BrowseDescription.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				BrowseDescription obj = (BrowseDescription) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getBrowseDirection()*/);
    				calculator.putNodeId(null,  (obj==null)?null:obj.getReferenceTypeId() );
    				calculator.putBoolean(null, null /*obj.getIncludeSubtypes()*/);
    				calculator.putUInt32(null, null /*obj.getNodeClassMask()*/);
    				calculator.putUInt32(null, null /*obj.getResultMask()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				BrowseDescription obj = (BrowseDescription) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("BrowseDirection",  (obj==null)?null:obj.getBrowseDirection() );
    				encoder.putNodeId("ReferenceTypeId",  (obj==null)?null:obj.getReferenceTypeId() );
    				encoder.putBoolean("IncludeSubtypes",  (obj==null)?null:obj.getIncludeSubtypes() );
    				encoder.putUInt32("NodeClassMask",  (obj==null)?null:obj.getNodeClassMask() );
    				encoder.putUInt32("ResultMask",  (obj==null)?null:obj.getResultMask() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				BrowseDescription result = new BrowseDescription();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setBrowseDirection( decoder.getEnumeration("BrowseDirection", BrowseDirection.class) );
    				result.setReferenceTypeId( decoder.getNodeId("ReferenceTypeId") );
    				result.setIncludeSubtypes( decoder.getBoolean("IncludeSubtypes") );
    				result.setNodeClassMask( decoder.getUInt32("NodeClassMask") );
    				result.setResultMask( decoder.getUInt32("ResultMask") );
    				return result;
    			}
    		});
    
    	// ReferenceDescription
    	addSerializer(
    		new AbstractSerializer(ReferenceDescription.class, ReferenceDescription.BINARY, ReferenceDescription.XML, ReferenceDescription.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReferenceDescription obj = (ReferenceDescription) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getReferenceTypeId() );
    				calculator.putBoolean(null, null /*obj.getIsForward()*/);
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getBrowseName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putEnumeration(null, null /*obj.getNodeClass()*/);
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getTypeDefinition() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReferenceDescription obj = (ReferenceDescription) encodeable;
    				encoder.putNodeId("ReferenceTypeId",  (obj==null)?null:obj.getReferenceTypeId() );
    				encoder.putBoolean("IsForward",  (obj==null)?null:obj.getIsForward() );
    				encoder.putExpandedNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putQualifiedName("BrowseName",  (obj==null)?null:obj.getBrowseName() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putEnumeration("NodeClass",  (obj==null)?null:obj.getNodeClass() );
    				encoder.putExpandedNodeId("TypeDefinition",  (obj==null)?null:obj.getTypeDefinition() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReferenceDescription result = new ReferenceDescription();
    				result.setReferenceTypeId( decoder.getNodeId("ReferenceTypeId") );
    				result.setIsForward( decoder.getBoolean("IsForward") );
    				result.setNodeId( decoder.getExpandedNodeId("NodeId") );
    				result.setBrowseName( decoder.getQualifiedName("BrowseName") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setNodeClass( decoder.getEnumeration("NodeClass", NodeClass.class) );
    				result.setTypeDefinition( decoder.getExpandedNodeId("TypeDefinition") );
    				return result;
    			}
    		});
    
    	// BrowseResult
    	addSerializer(
    		new AbstractSerializer(BrowseResult.class, BrowseResult.BINARY, BrowseResult.XML, BrowseResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				BrowseResult obj = (BrowseResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getContinuationPoint() );
    				calculator.putEncodeableArray(null, ReferenceDescription.class, (obj==null)?null:obj.getReferences());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				BrowseResult obj = (BrowseResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putByteString("ContinuationPoint",  (obj==null)?null:obj.getContinuationPoint() );
    				encoder.putEncodeableArray("References", ReferenceDescription.class, (obj==null)?null:obj.getReferences());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				BrowseResult result = new BrowseResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setContinuationPoint( decoder.getByteString("ContinuationPoint") );
    				result.setReferences( decoder.getEncodeableArray("References", ReferenceDescription.class) );
    				return result;
    			}
    		});
    
    	// BrowseRequest
    	addSerializer(
    		new AbstractSerializer(BrowseRequest.class, BrowseRequest.BINARY, BrowseRequest.XML, BrowseRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				BrowseRequest obj = (BrowseRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeable(null, ViewDescription.class, (obj==null)?null:obj.getView());
    				calculator.putUInt32(null, null /*obj.getRequestedMaxReferencesPerNode()*/);
    				calculator.putEncodeableArray(null, BrowseDescription.class, (obj==null)?null:obj.getNodesToBrowse());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				BrowseRequest obj = (BrowseRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeable("View", ViewDescription.class, (obj==null)?null:obj.getView());
    				encoder.putUInt32("RequestedMaxReferencesPerNode",  (obj==null)?null:obj.getRequestedMaxReferencesPerNode() );
    				encoder.putEncodeableArray("NodesToBrowse", BrowseDescription.class, (obj==null)?null:obj.getNodesToBrowse());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				BrowseRequest result = new BrowseRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setView( decoder.getEncodeable("View", ViewDescription.class) );
    				result.setRequestedMaxReferencesPerNode( decoder.getUInt32("RequestedMaxReferencesPerNode") );
    				result.setNodesToBrowse( decoder.getEncodeableArray("NodesToBrowse", BrowseDescription.class) );
    				return result;
    			}
    		});
    
    	// BrowseResponse
    	addSerializer(
    		new AbstractSerializer(BrowseResponse.class, BrowseResponse.BINARY, BrowseResponse.XML, BrowseResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				BrowseResponse obj = (BrowseResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, BrowseResult.class, (obj==null)?null:obj.getResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				BrowseResponse obj = (BrowseResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Results", BrowseResult.class, (obj==null)?null:obj.getResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				BrowseResponse result = new BrowseResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getEncodeableArray("Results", BrowseResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// BrowseNextRequest
    	addSerializer(
    		new AbstractSerializer(BrowseNextRequest.class, BrowseNextRequest.BINARY, BrowseNextRequest.XML, BrowseNextRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				BrowseNextRequest obj = (BrowseNextRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putBoolean(null, null /*obj.getReleaseContinuationPoints()*/);
    				calculator.putByteStringArray(null, ((obj==null)?null:obj.getContinuationPoints()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				BrowseNextRequest obj = (BrowseNextRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putBoolean("ReleaseContinuationPoints",  (obj==null)?null:obj.getReleaseContinuationPoints() );
    				encoder.putByteStringArray("ContinuationPoints", (obj==null)?null:obj.getContinuationPoints() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				BrowseNextRequest result = new BrowseNextRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setReleaseContinuationPoints( decoder.getBoolean("ReleaseContinuationPoints") );
    				result.setContinuationPoints( decoder.getByteStringArray("ContinuationPoints") );
    				return result;
    			}
    		});
    
    	// BrowseNextResponse
    	addSerializer(
    		new AbstractSerializer(BrowseNextResponse.class, BrowseNextResponse.BINARY, BrowseNextResponse.XML, BrowseNextResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				BrowseNextResponse obj = (BrowseNextResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, BrowseResult.class, (obj==null)?null:obj.getResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				BrowseNextResponse obj = (BrowseNextResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Results", BrowseResult.class, (obj==null)?null:obj.getResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				BrowseNextResponse result = new BrowseNextResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getEncodeableArray("Results", BrowseResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// RelativePathElement
    	addSerializer(
    		new AbstractSerializer(RelativePathElement.class, RelativePathElement.BINARY, RelativePathElement.XML, RelativePathElement.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RelativePathElement obj = (RelativePathElement) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getReferenceTypeId() );
    				calculator.putBoolean(null, null /*obj.getIsInverse()*/);
    				calculator.putBoolean(null, null /*obj.getIncludeSubtypes()*/);
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getTargetName() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RelativePathElement obj = (RelativePathElement) encodeable;
    				encoder.putNodeId("ReferenceTypeId",  (obj==null)?null:obj.getReferenceTypeId() );
    				encoder.putBoolean("IsInverse",  (obj==null)?null:obj.getIsInverse() );
    				encoder.putBoolean("IncludeSubtypes",  (obj==null)?null:obj.getIncludeSubtypes() );
    				encoder.putQualifiedName("TargetName",  (obj==null)?null:obj.getTargetName() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RelativePathElement result = new RelativePathElement();
    				result.setReferenceTypeId( decoder.getNodeId("ReferenceTypeId") );
    				result.setIsInverse( decoder.getBoolean("IsInverse") );
    				result.setIncludeSubtypes( decoder.getBoolean("IncludeSubtypes") );
    				result.setTargetName( decoder.getQualifiedName("TargetName") );
    				return result;
    			}
    		});
    
    	// RelativePath
    	addSerializer(
    		new AbstractSerializer(RelativePath.class, RelativePath.BINARY, RelativePath.XML, RelativePath.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RelativePath obj = (RelativePath) encodeable;
    				calculator.putEncodeableArray(null, RelativePathElement.class, (obj==null)?null:obj.getElements());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RelativePath obj = (RelativePath) encodeable;
    				encoder.putEncodeableArray("Elements", RelativePathElement.class, (obj==null)?null:obj.getElements());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RelativePath result = new RelativePath();
    				result.setElements( decoder.getEncodeableArray("Elements", RelativePathElement.class) );
    				return result;
    			}
    		});
    
    	// BrowsePath
    	addSerializer(
    		new AbstractSerializer(BrowsePath.class, BrowsePath.BINARY, BrowsePath.XML, BrowsePath.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				BrowsePath obj = (BrowsePath) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getStartingNode() );
    				calculator.putEncodeable(null, RelativePath.class, (obj==null)?null:obj.getRelativePath());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				BrowsePath obj = (BrowsePath) encodeable;
    				encoder.putNodeId("StartingNode",  (obj==null)?null:obj.getStartingNode() );
    				encoder.putEncodeable("RelativePath", RelativePath.class, (obj==null)?null:obj.getRelativePath());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				BrowsePath result = new BrowsePath();
    				result.setStartingNode( decoder.getNodeId("StartingNode") );
    				result.setRelativePath( decoder.getEncodeable("RelativePath", RelativePath.class) );
    				return result;
    			}
    		});
    
    	// BrowsePathTarget
    	addSerializer(
    		new AbstractSerializer(BrowsePathTarget.class, BrowsePathTarget.BINARY, BrowsePathTarget.XML, BrowsePathTarget.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				BrowsePathTarget obj = (BrowsePathTarget) encodeable;
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getTargetId() );
    				calculator.putUInt32(null, null /*obj.getRemainingPathIndex()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				BrowsePathTarget obj = (BrowsePathTarget) encodeable;
    				encoder.putExpandedNodeId("TargetId",  (obj==null)?null:obj.getTargetId() );
    				encoder.putUInt32("RemainingPathIndex",  (obj==null)?null:obj.getRemainingPathIndex() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				BrowsePathTarget result = new BrowsePathTarget();
    				result.setTargetId( decoder.getExpandedNodeId("TargetId") );
    				result.setRemainingPathIndex( decoder.getUInt32("RemainingPathIndex") );
    				return result;
    			}
    		});
    
    	// BrowsePathResult
    	addSerializer(
    		new AbstractSerializer(BrowsePathResult.class, BrowsePathResult.BINARY, BrowsePathResult.XML, BrowsePathResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				BrowsePathResult obj = (BrowsePathResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putEncodeableArray(null, BrowsePathTarget.class, (obj==null)?null:obj.getTargets());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				BrowsePathResult obj = (BrowsePathResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putEncodeableArray("Targets", BrowsePathTarget.class, (obj==null)?null:obj.getTargets());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				BrowsePathResult result = new BrowsePathResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setTargets( decoder.getEncodeableArray("Targets", BrowsePathTarget.class) );
    				return result;
    			}
    		});
    
    	// TranslateBrowsePathsToNodeIdsRequest
    	addSerializer(
    		new AbstractSerializer(TranslateBrowsePathsToNodeIdsRequest.class, TranslateBrowsePathsToNodeIdsRequest.BINARY, TranslateBrowsePathsToNodeIdsRequest.XML, TranslateBrowsePathsToNodeIdsRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				TranslateBrowsePathsToNodeIdsRequest obj = (TranslateBrowsePathsToNodeIdsRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeableArray(null, BrowsePath.class, (obj==null)?null:obj.getBrowsePaths());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				TranslateBrowsePathsToNodeIdsRequest obj = (TranslateBrowsePathsToNodeIdsRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeableArray("BrowsePaths", BrowsePath.class, (obj==null)?null:obj.getBrowsePaths());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				TranslateBrowsePathsToNodeIdsRequest result = new TranslateBrowsePathsToNodeIdsRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setBrowsePaths( decoder.getEncodeableArray("BrowsePaths", BrowsePath.class) );
    				return result;
    			}
    		});
    
    	// TranslateBrowsePathsToNodeIdsResponse
    	addSerializer(
    		new AbstractSerializer(TranslateBrowsePathsToNodeIdsResponse.class, TranslateBrowsePathsToNodeIdsResponse.BINARY, TranslateBrowsePathsToNodeIdsResponse.XML, TranslateBrowsePathsToNodeIdsResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				TranslateBrowsePathsToNodeIdsResponse obj = (TranslateBrowsePathsToNodeIdsResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, BrowsePathResult.class, (obj==null)?null:obj.getResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				TranslateBrowsePathsToNodeIdsResponse obj = (TranslateBrowsePathsToNodeIdsResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Results", BrowsePathResult.class, (obj==null)?null:obj.getResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				TranslateBrowsePathsToNodeIdsResponse result = new TranslateBrowsePathsToNodeIdsResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getEncodeableArray("Results", BrowsePathResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// RegisterNodesRequest
    	addSerializer(
    		new AbstractSerializer(RegisterNodesRequest.class, RegisterNodesRequest.BINARY, RegisterNodesRequest.XML, RegisterNodesRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RegisterNodesRequest obj = (RegisterNodesRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putNodeIdArray(null, ((obj==null)?null:obj.getNodesToRegister()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RegisterNodesRequest obj = (RegisterNodesRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putNodeIdArray("NodesToRegister", (obj==null)?null:obj.getNodesToRegister() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RegisterNodesRequest result = new RegisterNodesRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setNodesToRegister( decoder.getNodeIdArray("NodesToRegister") );
    				return result;
    			}
    		});
    
    	// RegisterNodesResponse
    	addSerializer(
    		new AbstractSerializer(RegisterNodesResponse.class, RegisterNodesResponse.BINARY, RegisterNodesResponse.XML, RegisterNodesResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RegisterNodesResponse obj = (RegisterNodesResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putNodeIdArray(null, ((obj==null)?null:obj.getRegisteredNodeIds()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RegisterNodesResponse obj = (RegisterNodesResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putNodeIdArray("RegisteredNodeIds", (obj==null)?null:obj.getRegisteredNodeIds() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RegisterNodesResponse result = new RegisterNodesResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setRegisteredNodeIds( decoder.getNodeIdArray("RegisteredNodeIds") );
    				return result;
    			}
    		});
    
    	// UnregisterNodesRequest
    	addSerializer(
    		new AbstractSerializer(UnregisterNodesRequest.class, UnregisterNodesRequest.BINARY, UnregisterNodesRequest.XML, UnregisterNodesRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				UnregisterNodesRequest obj = (UnregisterNodesRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putNodeIdArray(null, ((obj==null)?null:obj.getNodesToUnregister()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				UnregisterNodesRequest obj = (UnregisterNodesRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putNodeIdArray("NodesToUnregister", (obj==null)?null:obj.getNodesToUnregister() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				UnregisterNodesRequest result = new UnregisterNodesRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setNodesToUnregister( decoder.getNodeIdArray("NodesToUnregister") );
    				return result;
    			}
    		});
    
    	// UnregisterNodesResponse
    	addSerializer(
    		new AbstractSerializer(UnregisterNodesResponse.class, UnregisterNodesResponse.BINARY, UnregisterNodesResponse.XML, UnregisterNodesResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				UnregisterNodesResponse obj = (UnregisterNodesResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				UnregisterNodesResponse obj = (UnregisterNodesResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				UnregisterNodesResponse result = new UnregisterNodesResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				return result;
    			}
    		});
    
    	// EndpointConfiguration
    	addSerializer(
    		new AbstractSerializer(EndpointConfiguration.class, EndpointConfiguration.BINARY, EndpointConfiguration.XML, EndpointConfiguration.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				EndpointConfiguration obj = (EndpointConfiguration) encodeable;
    				calculator.putInt32(null, null /*obj.getOperationTimeout()*/);
    				calculator.putBoolean(null, null /*obj.getUseBinaryEncoding()*/);
    				calculator.putInt32(null, null /*obj.getMaxStringLength()*/);
    				calculator.putInt32(null, null /*obj.getMaxByteStringLength()*/);
    				calculator.putInt32(null, null /*obj.getMaxArrayLength()*/);
    				calculator.putInt32(null, null /*obj.getMaxMessageSize()*/);
    				calculator.putInt32(null, null /*obj.getMaxBufferSize()*/);
    				calculator.putInt32(null, null /*obj.getChannelLifetime()*/);
    				calculator.putInt32(null, null /*obj.getSecurityTokenLifetime()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				EndpointConfiguration obj = (EndpointConfiguration) encodeable;
    				encoder.putInt32("OperationTimeout",  (obj==null)?null:obj.getOperationTimeout() );
    				encoder.putBoolean("UseBinaryEncoding",  (obj==null)?null:obj.getUseBinaryEncoding() );
    				encoder.putInt32("MaxStringLength",  (obj==null)?null:obj.getMaxStringLength() );
    				encoder.putInt32("MaxByteStringLength",  (obj==null)?null:obj.getMaxByteStringLength() );
    				encoder.putInt32("MaxArrayLength",  (obj==null)?null:obj.getMaxArrayLength() );
    				encoder.putInt32("MaxMessageSize",  (obj==null)?null:obj.getMaxMessageSize() );
    				encoder.putInt32("MaxBufferSize",  (obj==null)?null:obj.getMaxBufferSize() );
    				encoder.putInt32("ChannelLifetime",  (obj==null)?null:obj.getChannelLifetime() );
    				encoder.putInt32("SecurityTokenLifetime",  (obj==null)?null:obj.getSecurityTokenLifetime() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				EndpointConfiguration result = new EndpointConfiguration();
    				result.setOperationTimeout( decoder.getInt32("OperationTimeout") );
    				result.setUseBinaryEncoding( decoder.getBoolean("UseBinaryEncoding") );
    				result.setMaxStringLength( decoder.getInt32("MaxStringLength") );
    				result.setMaxByteStringLength( decoder.getInt32("MaxByteStringLength") );
    				result.setMaxArrayLength( decoder.getInt32("MaxArrayLength") );
    				result.setMaxMessageSize( decoder.getInt32("MaxMessageSize") );
    				result.setMaxBufferSize( decoder.getInt32("MaxBufferSize") );
    				result.setChannelLifetime( decoder.getInt32("ChannelLifetime") );
    				result.setSecurityTokenLifetime( decoder.getInt32("SecurityTokenLifetime") );
    				return result;
    			}
    		});
    
    	// QueryDataDescription
    	addSerializer(
    		new AbstractSerializer(QueryDataDescription.class, QueryDataDescription.BINARY, QueryDataDescription.XML, QueryDataDescription.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				QueryDataDescription obj = (QueryDataDescription) encodeable;
    				calculator.putEncodeable(null, RelativePath.class, (obj==null)?null:obj.getRelativePath());
    				calculator.putUInt32(null, null /*obj.getAttributeId()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getIndexRange() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				QueryDataDescription obj = (QueryDataDescription) encodeable;
    				encoder.putEncodeable("RelativePath", RelativePath.class, (obj==null)?null:obj.getRelativePath());
    				encoder.putUInt32("AttributeId",  (obj==null)?null:obj.getAttributeId() );
    				encoder.putString("IndexRange",  (obj==null)?null:obj.getIndexRange() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				QueryDataDescription result = new QueryDataDescription();
    				result.setRelativePath( decoder.getEncodeable("RelativePath", RelativePath.class) );
    				result.setAttributeId( decoder.getUInt32("AttributeId") );
    				result.setIndexRange( decoder.getString("IndexRange") );
    				return result;
    			}
    		});
    
    	// NodeTypeDescription
    	addSerializer(
    		new AbstractSerializer(NodeTypeDescription.class, NodeTypeDescription.BINARY, NodeTypeDescription.XML, NodeTypeDescription.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				NodeTypeDescription obj = (NodeTypeDescription) encodeable;
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getTypeDefinitionNode() );
    				calculator.putBoolean(null, null /*obj.getIncludeSubTypes()*/);
    				calculator.putEncodeableArray(null, QueryDataDescription.class, (obj==null)?null:obj.getDataToReturn());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				NodeTypeDescription obj = (NodeTypeDescription) encodeable;
    				encoder.putExpandedNodeId("TypeDefinitionNode",  (obj==null)?null:obj.getTypeDefinitionNode() );
    				encoder.putBoolean("IncludeSubTypes",  (obj==null)?null:obj.getIncludeSubTypes() );
    				encoder.putEncodeableArray("DataToReturn", QueryDataDescription.class, (obj==null)?null:obj.getDataToReturn());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				NodeTypeDescription result = new NodeTypeDescription();
    				result.setTypeDefinitionNode( decoder.getExpandedNodeId("TypeDefinitionNode") );
    				result.setIncludeSubTypes( decoder.getBoolean("IncludeSubTypes") );
    				result.setDataToReturn( decoder.getEncodeableArray("DataToReturn", QueryDataDescription.class) );
    				return result;
    			}
    		});
    
    	// QueryDataSet
    	addSerializer(
    		new AbstractSerializer(QueryDataSet.class, QueryDataSet.BINARY, QueryDataSet.XML, QueryDataSet.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				QueryDataSet obj = (QueryDataSet) encodeable;
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putExpandedNodeId(null,  (obj==null)?null:obj.getTypeDefinitionNode() );
    				calculator.putVariantArray(null, ((obj==null)?null:obj.getValues()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				QueryDataSet obj = (QueryDataSet) encodeable;
    				encoder.putExpandedNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putExpandedNodeId("TypeDefinitionNode",  (obj==null)?null:obj.getTypeDefinitionNode() );
    				encoder.putVariantArray("Values", (obj==null)?null:obj.getValues() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				QueryDataSet result = new QueryDataSet();
    				result.setNodeId( decoder.getExpandedNodeId("NodeId") );
    				result.setTypeDefinitionNode( decoder.getExpandedNodeId("TypeDefinitionNode") );
    				result.setValues( decoder.getVariantArray("Values") );
    				return result;
    			}
    		});
    
    	// NodeReference
    	addSerializer(
    		new AbstractSerializer(NodeReference.class, NodeReference.BINARY, NodeReference.XML, NodeReference.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				NodeReference obj = (NodeReference) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getReferenceTypeId() );
    				calculator.putBoolean(null, null /*obj.getIsForward()*/);
    				calculator.putNodeIdArray(null, ((obj==null)?null:obj.getReferencedNodeIds()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				NodeReference obj = (NodeReference) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putNodeId("ReferenceTypeId",  (obj==null)?null:obj.getReferenceTypeId() );
    				encoder.putBoolean("IsForward",  (obj==null)?null:obj.getIsForward() );
    				encoder.putNodeIdArray("ReferencedNodeIds", (obj==null)?null:obj.getReferencedNodeIds() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				NodeReference result = new NodeReference();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setReferenceTypeId( decoder.getNodeId("ReferenceTypeId") );
    				result.setIsForward( decoder.getBoolean("IsForward") );
    				result.setReferencedNodeIds( decoder.getNodeIdArray("ReferencedNodeIds") );
    				return result;
    			}
    		});
    
    	// ContentFilterElement
    	addSerializer(
    		new AbstractSerializer(ContentFilterElement.class, ContentFilterElement.BINARY, ContentFilterElement.XML, ContentFilterElement.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ContentFilterElement obj = (ContentFilterElement) encodeable;
    				calculator.putEnumeration(null, null /*obj.getFilterOperator()*/);
    				calculator.putExtensionObjectArray(null, ((obj==null)?null:obj.getFilterOperands()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ContentFilterElement obj = (ContentFilterElement) encodeable;
    				encoder.putEnumeration("FilterOperator",  (obj==null)?null:obj.getFilterOperator() );
    				encoder.putExtensionObjectArray("FilterOperands", (obj==null)?null:obj.getFilterOperands() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ContentFilterElement result = new ContentFilterElement();
    				result.setFilterOperator( decoder.getEnumeration("FilterOperator", FilterOperator.class) );
    				result.setFilterOperands( decoder.getExtensionObjectArray("FilterOperands") );
    				return result;
    			}
    		});
    
    	// ContentFilter
    	addSerializer(
    		new AbstractSerializer(ContentFilter.class, ContentFilter.BINARY, ContentFilter.XML, ContentFilter.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ContentFilter obj = (ContentFilter) encodeable;
    				calculator.putEncodeableArray(null, ContentFilterElement.class, (obj==null)?null:obj.getElements());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ContentFilter obj = (ContentFilter) encodeable;
    				encoder.putEncodeableArray("Elements", ContentFilterElement.class, (obj==null)?null:obj.getElements());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ContentFilter result = new ContentFilter();
    				result.setElements( decoder.getEncodeableArray("Elements", ContentFilterElement.class) );
    				return result;
    			}
    		});
    
    	// FilterOperand
    	addSerializer(
    		new AbstractSerializer(FilterOperand.class, FilterOperand.BINARY, FilterOperand.XML, FilterOperand.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				FilterOperand result = new FilterOperand();
    				return result;
    			}
    		});
    
    	// ElementOperand
    	addSerializer(
    		new AbstractSerializer(ElementOperand.class, ElementOperand.BINARY, ElementOperand.XML, ElementOperand.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ElementOperand obj = (ElementOperand) encodeable;
    				calculator.putUInt32(null, null /*obj.getIndex()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ElementOperand obj = (ElementOperand) encodeable;
    				encoder.putUInt32("Index",  (obj==null)?null:obj.getIndex() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ElementOperand result = new ElementOperand();
    				result.setIndex( decoder.getUInt32("Index") );
    				return result;
    			}
    		});
    
    	// LiteralOperand
    	addSerializer(
    		new AbstractSerializer(LiteralOperand.class, LiteralOperand.BINARY, LiteralOperand.XML, LiteralOperand.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				LiteralOperand obj = (LiteralOperand) encodeable;
    				calculator.putVariant(null,  (obj==null)?null:obj.getValue() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				LiteralOperand obj = (LiteralOperand) encodeable;
    				encoder.putVariant("Value",  (obj==null)?null:obj.getValue() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				LiteralOperand result = new LiteralOperand();
    				result.setValue( decoder.getVariant("Value") );
    				return result;
    			}
    		});
    
    	// AttributeOperand
    	addSerializer(
    		new AbstractSerializer(AttributeOperand.class, AttributeOperand.BINARY, AttributeOperand.XML, AttributeOperand.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AttributeOperand obj = (AttributeOperand) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putString(null,  (obj==null)?null:obj.getAlias() );
    				calculator.putEncodeable(null, RelativePath.class, (obj==null)?null:obj.getBrowsePath());
    				calculator.putUInt32(null, null /*obj.getAttributeId()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getIndexRange() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AttributeOperand obj = (AttributeOperand) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putString("Alias",  (obj==null)?null:obj.getAlias() );
    				encoder.putEncodeable("BrowsePath", RelativePath.class, (obj==null)?null:obj.getBrowsePath());
    				encoder.putUInt32("AttributeId",  (obj==null)?null:obj.getAttributeId() );
    				encoder.putString("IndexRange",  (obj==null)?null:obj.getIndexRange() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AttributeOperand result = new AttributeOperand();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setAlias( decoder.getString("Alias") );
    				result.setBrowsePath( decoder.getEncodeable("BrowsePath", RelativePath.class) );
    				result.setAttributeId( decoder.getUInt32("AttributeId") );
    				result.setIndexRange( decoder.getString("IndexRange") );
    				return result;
    			}
    		});
    
    	// SimpleAttributeOperand
    	addSerializer(
    		new AbstractSerializer(SimpleAttributeOperand.class, SimpleAttributeOperand.BINARY, SimpleAttributeOperand.XML, SimpleAttributeOperand.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SimpleAttributeOperand obj = (SimpleAttributeOperand) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getTypeDefinitionId() );
    				calculator.putQualifiedNameArray(null, ((obj==null)?null:obj.getBrowsePath()) );
    				calculator.putUInt32(null, null /*obj.getAttributeId()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getIndexRange() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SimpleAttributeOperand obj = (SimpleAttributeOperand) encodeable;
    				encoder.putNodeId("TypeDefinitionId",  (obj==null)?null:obj.getTypeDefinitionId() );
    				encoder.putQualifiedNameArray("BrowsePath", (obj==null)?null:obj.getBrowsePath() );
    				encoder.putUInt32("AttributeId",  (obj==null)?null:obj.getAttributeId() );
    				encoder.putString("IndexRange",  (obj==null)?null:obj.getIndexRange() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SimpleAttributeOperand result = new SimpleAttributeOperand();
    				result.setTypeDefinitionId( decoder.getNodeId("TypeDefinitionId") );
    				result.setBrowsePath( decoder.getQualifiedNameArray("BrowsePath") );
    				result.setAttributeId( decoder.getUInt32("AttributeId") );
    				result.setIndexRange( decoder.getString("IndexRange") );
    				return result;
    			}
    		});
    
    	// ContentFilterElementResult
    	addSerializer(
    		new AbstractSerializer(ContentFilterElementResult.class, ContentFilterElementResult.BINARY, ContentFilterElementResult.XML, ContentFilterElementResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ContentFilterElementResult obj = (ContentFilterElementResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getOperandStatusCodes()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getOperandDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ContentFilterElementResult obj = (ContentFilterElementResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putStatusCodeArray("OperandStatusCodes", (obj==null)?null:obj.getOperandStatusCodes() );
    				encoder.putDiagnosticInfoArray("OperandDiagnosticInfos", (obj==null)?null:obj.getOperandDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ContentFilterElementResult result = new ContentFilterElementResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setOperandStatusCodes( decoder.getStatusCodeArray("OperandStatusCodes") );
    				result.setOperandDiagnosticInfos( decoder.getDiagnosticInfoArray("OperandDiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// ContentFilterResult
    	addSerializer(
    		new AbstractSerializer(ContentFilterResult.class, ContentFilterResult.BINARY, ContentFilterResult.XML, ContentFilterResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ContentFilterResult obj = (ContentFilterResult) encodeable;
    				calculator.putEncodeableArray(null, ContentFilterElementResult.class, (obj==null)?null:obj.getElementResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getElementDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ContentFilterResult obj = (ContentFilterResult) encodeable;
    				encoder.putEncodeableArray("ElementResults", ContentFilterElementResult.class, (obj==null)?null:obj.getElementResults());
    				encoder.putDiagnosticInfoArray("ElementDiagnosticInfos", (obj==null)?null:obj.getElementDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ContentFilterResult result = new ContentFilterResult();
    				result.setElementResults( decoder.getEncodeableArray("ElementResults", ContentFilterElementResult.class) );
    				result.setElementDiagnosticInfos( decoder.getDiagnosticInfoArray("ElementDiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// ParsingResult
    	addSerializer(
    		new AbstractSerializer(ParsingResult.class, ParsingResult.BINARY, ParsingResult.XML, ParsingResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ParsingResult obj = (ParsingResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getDataStatusCodes()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDataDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ParsingResult obj = (ParsingResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putStatusCodeArray("DataStatusCodes", (obj==null)?null:obj.getDataStatusCodes() );
    				encoder.putDiagnosticInfoArray("DataDiagnosticInfos", (obj==null)?null:obj.getDataDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ParsingResult result = new ParsingResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setDataStatusCodes( decoder.getStatusCodeArray("DataStatusCodes") );
    				result.setDataDiagnosticInfos( decoder.getDiagnosticInfoArray("DataDiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// QueryFirstRequest
    	addSerializer(
    		new AbstractSerializer(QueryFirstRequest.class, QueryFirstRequest.BINARY, QueryFirstRequest.XML, QueryFirstRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				QueryFirstRequest obj = (QueryFirstRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeable(null, ViewDescription.class, (obj==null)?null:obj.getView());
    				calculator.putEncodeableArray(null, NodeTypeDescription.class, (obj==null)?null:obj.getNodeTypes());
    				calculator.putEncodeable(null, ContentFilter.class, (obj==null)?null:obj.getFilter());
    				calculator.putUInt32(null, null /*obj.getMaxDataSetsToReturn()*/);
    				calculator.putUInt32(null, null /*obj.getMaxReferencesToReturn()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				QueryFirstRequest obj = (QueryFirstRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeable("View", ViewDescription.class, (obj==null)?null:obj.getView());
    				encoder.putEncodeableArray("NodeTypes", NodeTypeDescription.class, (obj==null)?null:obj.getNodeTypes());
    				encoder.putEncodeable("Filter", ContentFilter.class, (obj==null)?null:obj.getFilter());
    				encoder.putUInt32("MaxDataSetsToReturn",  (obj==null)?null:obj.getMaxDataSetsToReturn() );
    				encoder.putUInt32("MaxReferencesToReturn",  (obj==null)?null:obj.getMaxReferencesToReturn() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				QueryFirstRequest result = new QueryFirstRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setView( decoder.getEncodeable("View", ViewDescription.class) );
    				result.setNodeTypes( decoder.getEncodeableArray("NodeTypes", NodeTypeDescription.class) );
    				result.setFilter( decoder.getEncodeable("Filter", ContentFilter.class) );
    				result.setMaxDataSetsToReturn( decoder.getUInt32("MaxDataSetsToReturn") );
    				result.setMaxReferencesToReturn( decoder.getUInt32("MaxReferencesToReturn") );
    				return result;
    			}
    		});
    
    	// QueryFirstResponse
    	addSerializer(
    		new AbstractSerializer(QueryFirstResponse.class, QueryFirstResponse.BINARY, QueryFirstResponse.XML, QueryFirstResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				QueryFirstResponse obj = (QueryFirstResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, QueryDataSet.class, (obj==null)?null:obj.getQueryDataSets());
    				calculator.putByteString(null,  (obj==null)?null:obj.getContinuationPoint() );
    				calculator.putEncodeableArray(null, ParsingResult.class, (obj==null)?null:obj.getParsingResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    				calculator.putEncodeable(null, ContentFilterResult.class, (obj==null)?null:obj.getFilterResult());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				QueryFirstResponse obj = (QueryFirstResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("QueryDataSets", QueryDataSet.class, (obj==null)?null:obj.getQueryDataSets());
    				encoder.putByteString("ContinuationPoint",  (obj==null)?null:obj.getContinuationPoint() );
    				encoder.putEncodeableArray("ParsingResults", ParsingResult.class, (obj==null)?null:obj.getParsingResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    				encoder.putEncodeable("FilterResult", ContentFilterResult.class, (obj==null)?null:obj.getFilterResult());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				QueryFirstResponse result = new QueryFirstResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setQueryDataSets( decoder.getEncodeableArray("QueryDataSets", QueryDataSet.class) );
    				result.setContinuationPoint( decoder.getByteString("ContinuationPoint") );
    				result.setParsingResults( decoder.getEncodeableArray("ParsingResults", ParsingResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				result.setFilterResult( decoder.getEncodeable("FilterResult", ContentFilterResult.class) );
    				return result;
    			}
    		});
    
    	// QueryNextRequest
    	addSerializer(
    		new AbstractSerializer(QueryNextRequest.class, QueryNextRequest.BINARY, QueryNextRequest.XML, QueryNextRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				QueryNextRequest obj = (QueryNextRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putBoolean(null, null /*obj.getReleaseContinuationPoint()*/);
    				calculator.putByteString(null,  (obj==null)?null:obj.getContinuationPoint() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				QueryNextRequest obj = (QueryNextRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putBoolean("ReleaseContinuationPoint",  (obj==null)?null:obj.getReleaseContinuationPoint() );
    				encoder.putByteString("ContinuationPoint",  (obj==null)?null:obj.getContinuationPoint() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				QueryNextRequest result = new QueryNextRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setReleaseContinuationPoint( decoder.getBoolean("ReleaseContinuationPoint") );
    				result.setContinuationPoint( decoder.getByteString("ContinuationPoint") );
    				return result;
    			}
    		});
    
    	// QueryNextResponse
    	addSerializer(
    		new AbstractSerializer(QueryNextResponse.class, QueryNextResponse.BINARY, QueryNextResponse.XML, QueryNextResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				QueryNextResponse obj = (QueryNextResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, QueryDataSet.class, (obj==null)?null:obj.getQueryDataSets());
    				calculator.putByteString(null,  (obj==null)?null:obj.getRevisedContinuationPoint() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				QueryNextResponse obj = (QueryNextResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("QueryDataSets", QueryDataSet.class, (obj==null)?null:obj.getQueryDataSets());
    				encoder.putByteString("RevisedContinuationPoint",  (obj==null)?null:obj.getRevisedContinuationPoint() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				QueryNextResponse result = new QueryNextResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setQueryDataSets( decoder.getEncodeableArray("QueryDataSets", QueryDataSet.class) );
    				result.setRevisedContinuationPoint( decoder.getByteString("RevisedContinuationPoint") );
    				return result;
    			}
    		});
    
    	// ReadValueId
    	addSerializer(
    		new AbstractSerializer(ReadValueId.class, ReadValueId.BINARY, ReadValueId.XML, ReadValueId.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReadValueId obj = (ReadValueId) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putUInt32(null, null /*obj.getAttributeId()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getIndexRange() );
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getDataEncoding() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReadValueId obj = (ReadValueId) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putUInt32("AttributeId",  (obj==null)?null:obj.getAttributeId() );
    				encoder.putString("IndexRange",  (obj==null)?null:obj.getIndexRange() );
    				encoder.putQualifiedName("DataEncoding",  (obj==null)?null:obj.getDataEncoding() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReadValueId result = new ReadValueId();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setAttributeId( decoder.getUInt32("AttributeId") );
    				result.setIndexRange( decoder.getString("IndexRange") );
    				result.setDataEncoding( decoder.getQualifiedName("DataEncoding") );
    				return result;
    			}
    		});
    
    	// ReadRequest
    	addSerializer(
    		new AbstractSerializer(ReadRequest.class, ReadRequest.BINARY, ReadRequest.XML, ReadRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReadRequest obj = (ReadRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putDouble(null, null /*obj.getMaxAge()*/);
    				calculator.putEnumeration(null, null /*obj.getTimestampsToReturn()*/);
    				calculator.putEncodeableArray(null, ReadValueId.class, (obj==null)?null:obj.getNodesToRead());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReadRequest obj = (ReadRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putDouble("MaxAge",  (obj==null)?null:obj.getMaxAge() );
    				encoder.putEnumeration("TimestampsToReturn",  (obj==null)?null:obj.getTimestampsToReturn() );
    				encoder.putEncodeableArray("NodesToRead", ReadValueId.class, (obj==null)?null:obj.getNodesToRead());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReadRequest result = new ReadRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setMaxAge( decoder.getDouble("MaxAge") );
    				result.setTimestampsToReturn( decoder.getEnumeration("TimestampsToReturn", TimestampsToReturn.class) );
    				result.setNodesToRead( decoder.getEncodeableArray("NodesToRead", ReadValueId.class) );
    				return result;
    			}
    		});
    
    	// ReadResponse
    	addSerializer(
    		new AbstractSerializer(ReadResponse.class, ReadResponse.BINARY, ReadResponse.XML, ReadResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReadResponse obj = (ReadResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putDataValueArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReadResponse obj = (ReadResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putDataValueArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReadResponse result = new ReadResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getDataValueArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// HistoryReadValueId
    	addSerializer(
    		new AbstractSerializer(HistoryReadValueId.class, HistoryReadValueId.BINARY, HistoryReadValueId.XML, HistoryReadValueId.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryReadValueId obj = (HistoryReadValueId) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putString(null,  (obj==null)?null:obj.getIndexRange() );
    				calculator.putQualifiedName(null,  (obj==null)?null:obj.getDataEncoding() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getContinuationPoint() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryReadValueId obj = (HistoryReadValueId) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putString("IndexRange",  (obj==null)?null:obj.getIndexRange() );
    				encoder.putQualifiedName("DataEncoding",  (obj==null)?null:obj.getDataEncoding() );
    				encoder.putByteString("ContinuationPoint",  (obj==null)?null:obj.getContinuationPoint() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryReadValueId result = new HistoryReadValueId();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setIndexRange( decoder.getString("IndexRange") );
    				result.setDataEncoding( decoder.getQualifiedName("DataEncoding") );
    				result.setContinuationPoint( decoder.getByteString("ContinuationPoint") );
    				return result;
    			}
    		});
    
    	// HistoryReadResult
    	addSerializer(
    		new AbstractSerializer(HistoryReadResult.class, HistoryReadResult.BINARY, HistoryReadResult.XML, HistoryReadResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryReadResult obj = (HistoryReadResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getContinuationPoint() );
    				calculator.putExtensionObject(null,  (obj==null)?null:obj.getHistoryData() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryReadResult obj = (HistoryReadResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putByteString("ContinuationPoint",  (obj==null)?null:obj.getContinuationPoint() );
    				encoder.putExtensionObject("HistoryData",  (obj==null)?null:obj.getHistoryData() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryReadResult result = new HistoryReadResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setContinuationPoint( decoder.getByteString("ContinuationPoint") );
    				result.setHistoryData( decoder.getExtensionObject("HistoryData") );
    				return result;
    			}
    		});
    
    	// HistoryReadDetails
    	addSerializer(
    		new AbstractSerializer(HistoryReadDetails.class, HistoryReadDetails.BINARY, HistoryReadDetails.XML, HistoryReadDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryReadDetails result = new HistoryReadDetails();
    				return result;
    			}
    		});
    
    	// ReadEventDetails
    	addSerializer(
    		new AbstractSerializer(ReadEventDetails.class, ReadEventDetails.BINARY, ReadEventDetails.XML, ReadEventDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReadEventDetails obj = (ReadEventDetails) encodeable;
    				calculator.putUInt32(null, null /*obj.getNumValuesPerNode()*/);
    				calculator.putDateTime(null,  (obj==null)?null:obj.getStartTime() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getEndTime() );
    				calculator.putEncodeable(null, EventFilter.class, (obj==null)?null:obj.getFilter());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReadEventDetails obj = (ReadEventDetails) encodeable;
    				encoder.putUInt32("NumValuesPerNode",  (obj==null)?null:obj.getNumValuesPerNode() );
    				encoder.putDateTime("StartTime",  (obj==null)?null:obj.getStartTime() );
    				encoder.putDateTime("EndTime",  (obj==null)?null:obj.getEndTime() );
    				encoder.putEncodeable("Filter", EventFilter.class, (obj==null)?null:obj.getFilter());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReadEventDetails result = new ReadEventDetails();
    				result.setNumValuesPerNode( decoder.getUInt32("NumValuesPerNode") );
    				result.setStartTime( decoder.getDateTime("StartTime") );
    				result.setEndTime( decoder.getDateTime("EndTime") );
    				result.setFilter( decoder.getEncodeable("Filter", EventFilter.class) );
    				return result;
    			}
    		});
    
    	// ReadRawModifiedDetails
    	addSerializer(
    		new AbstractSerializer(ReadRawModifiedDetails.class, ReadRawModifiedDetails.BINARY, ReadRawModifiedDetails.XML, ReadRawModifiedDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReadRawModifiedDetails obj = (ReadRawModifiedDetails) encodeable;
    				calculator.putBoolean(null, null /*obj.getIsReadModified()*/);
    				calculator.putDateTime(null,  (obj==null)?null:obj.getStartTime() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getEndTime() );
    				calculator.putUInt32(null, null /*obj.getNumValuesPerNode()*/);
    				calculator.putBoolean(null, null /*obj.getReturnBounds()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReadRawModifiedDetails obj = (ReadRawModifiedDetails) encodeable;
    				encoder.putBoolean("IsReadModified",  (obj==null)?null:obj.getIsReadModified() );
    				encoder.putDateTime("StartTime",  (obj==null)?null:obj.getStartTime() );
    				encoder.putDateTime("EndTime",  (obj==null)?null:obj.getEndTime() );
    				encoder.putUInt32("NumValuesPerNode",  (obj==null)?null:obj.getNumValuesPerNode() );
    				encoder.putBoolean("ReturnBounds",  (obj==null)?null:obj.getReturnBounds() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReadRawModifiedDetails result = new ReadRawModifiedDetails();
    				result.setIsReadModified( decoder.getBoolean("IsReadModified") );
    				result.setStartTime( decoder.getDateTime("StartTime") );
    				result.setEndTime( decoder.getDateTime("EndTime") );
    				result.setNumValuesPerNode( decoder.getUInt32("NumValuesPerNode") );
    				result.setReturnBounds( decoder.getBoolean("ReturnBounds") );
    				return result;
    			}
    		});
    
    	// ReadProcessedDetails
    	addSerializer(
    		new AbstractSerializer(ReadProcessedDetails.class, ReadProcessedDetails.BINARY, ReadProcessedDetails.XML, ReadProcessedDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReadProcessedDetails obj = (ReadProcessedDetails) encodeable;
    				calculator.putDateTime(null,  (obj==null)?null:obj.getStartTime() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getEndTime() );
    				calculator.putDouble(null, null /*obj.getProcessingInterval()*/);
    				calculator.putNodeIdArray(null, ((obj==null)?null:obj.getAggregateType()) );
    				calculator.putEncodeable(null, AggregateConfiguration.class, (obj==null)?null:obj.getAggregateConfiguration());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReadProcessedDetails obj = (ReadProcessedDetails) encodeable;
    				encoder.putDateTime("StartTime",  (obj==null)?null:obj.getStartTime() );
    				encoder.putDateTime("EndTime",  (obj==null)?null:obj.getEndTime() );
    				encoder.putDouble("ProcessingInterval",  (obj==null)?null:obj.getProcessingInterval() );
    				encoder.putNodeIdArray("AggregateType", (obj==null)?null:obj.getAggregateType() );
    				encoder.putEncodeable("AggregateConfiguration", AggregateConfiguration.class, (obj==null)?null:obj.getAggregateConfiguration());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReadProcessedDetails result = new ReadProcessedDetails();
    				result.setStartTime( decoder.getDateTime("StartTime") );
    				result.setEndTime( decoder.getDateTime("EndTime") );
    				result.setProcessingInterval( decoder.getDouble("ProcessingInterval") );
    				result.setAggregateType( decoder.getNodeIdArray("AggregateType") );
    				result.setAggregateConfiguration( decoder.getEncodeable("AggregateConfiguration", AggregateConfiguration.class) );
    				return result;
    			}
    		});
    
    	// ReadAtTimeDetails
    	addSerializer(
    		new AbstractSerializer(ReadAtTimeDetails.class, ReadAtTimeDetails.BINARY, ReadAtTimeDetails.XML, ReadAtTimeDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ReadAtTimeDetails obj = (ReadAtTimeDetails) encodeable;
    				calculator.putDateTimeArray(null, ((obj==null)?null:obj.getReqTimes()) );
    				calculator.putBoolean(null, null /*obj.getUseSimpleBounds()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ReadAtTimeDetails obj = (ReadAtTimeDetails) encodeable;
    				encoder.putDateTimeArray("ReqTimes", (obj==null)?null:obj.getReqTimes() );
    				encoder.putBoolean("UseSimpleBounds",  (obj==null)?null:obj.getUseSimpleBounds() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ReadAtTimeDetails result = new ReadAtTimeDetails();
    				result.setReqTimes( decoder.getDateTimeArray("ReqTimes") );
    				result.setUseSimpleBounds( decoder.getBoolean("UseSimpleBounds") );
    				return result;
    			}
    		});
    
    	// HistoryData
    	addSerializer(
    		new AbstractSerializer(HistoryData.class, HistoryData.BINARY, HistoryData.XML, HistoryData.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryData obj = (HistoryData) encodeable;
    				calculator.putDataValueArray(null, ((obj==null)?null:obj.getDataValues()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryData obj = (HistoryData) encodeable;
    				encoder.putDataValueArray("DataValues", (obj==null)?null:obj.getDataValues() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryData result = new HistoryData();
    				result.setDataValues( decoder.getDataValueArray("DataValues") );
    				return result;
    			}
    		});
    
    	// ModificationInfo
    	addSerializer(
    		new AbstractSerializer(ModificationInfo.class, ModificationInfo.BINARY, ModificationInfo.XML, ModificationInfo.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ModificationInfo obj = (ModificationInfo) encodeable;
    				calculator.putDateTime(null,  (obj==null)?null:obj.getModificationTime() );
    				calculator.putEnumeration(null, null /*obj.getUpdateType()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getUserName() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ModificationInfo obj = (ModificationInfo) encodeable;
    				encoder.putDateTime("ModificationTime",  (obj==null)?null:obj.getModificationTime() );
    				encoder.putEnumeration("UpdateType",  (obj==null)?null:obj.getUpdateType() );
    				encoder.putString("UserName",  (obj==null)?null:obj.getUserName() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ModificationInfo result = new ModificationInfo();
    				result.setModificationTime( decoder.getDateTime("ModificationTime") );
    				result.setUpdateType( decoder.getEnumeration("UpdateType", HistoryUpdateType.class) );
    				result.setUserName( decoder.getString("UserName") );
    				return result;
    			}
    		});
    
    	// HistoryModifiedData
    	addSerializer(
    		new AbstractSerializer(HistoryModifiedData.class, HistoryModifiedData.BINARY, HistoryModifiedData.XML, HistoryModifiedData.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryModifiedData obj = (HistoryModifiedData) encodeable;
    				calculator.putDataValueArray(null, ((obj==null)?null:obj.getDataValues()) );
    				calculator.putEncodeableArray(null, ModificationInfo.class, (obj==null)?null:obj.getModificationInfos());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryModifiedData obj = (HistoryModifiedData) encodeable;
    				encoder.putDataValueArray("DataValues", (obj==null)?null:obj.getDataValues() );
    				encoder.putEncodeableArray("ModificationInfos", ModificationInfo.class, (obj==null)?null:obj.getModificationInfos());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryModifiedData result = new HistoryModifiedData();
    				result.setDataValues( decoder.getDataValueArray("DataValues") );
    				result.setModificationInfos( decoder.getEncodeableArray("ModificationInfos", ModificationInfo.class) );
    				return result;
    			}
    		});
    
    	// HistoryEvent
    	addSerializer(
    		new AbstractSerializer(HistoryEvent.class, HistoryEvent.BINARY, HistoryEvent.XML, HistoryEvent.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryEvent obj = (HistoryEvent) encodeable;
    				calculator.putEncodeableArray(null, HistoryEventFieldList.class, (obj==null)?null:obj.getEvents());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryEvent obj = (HistoryEvent) encodeable;
    				encoder.putEncodeableArray("Events", HistoryEventFieldList.class, (obj==null)?null:obj.getEvents());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryEvent result = new HistoryEvent();
    				result.setEvents( decoder.getEncodeableArray("Events", HistoryEventFieldList.class) );
    				return result;
    			}
    		});
    
    	// HistoryReadRequest
    	addSerializer(
    		new AbstractSerializer(HistoryReadRequest.class, HistoryReadRequest.BINARY, HistoryReadRequest.XML, HistoryReadRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryReadRequest obj = (HistoryReadRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putExtensionObject(null,  (obj==null)?null:obj.getHistoryReadDetails() );
    				calculator.putEnumeration(null, null /*obj.getTimestampsToReturn()*/);
    				calculator.putBoolean(null, null /*obj.getReleaseContinuationPoints()*/);
    				calculator.putEncodeableArray(null, HistoryReadValueId.class, (obj==null)?null:obj.getNodesToRead());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryReadRequest obj = (HistoryReadRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putExtensionObject("HistoryReadDetails",  (obj==null)?null:obj.getHistoryReadDetails() );
    				encoder.putEnumeration("TimestampsToReturn",  (obj==null)?null:obj.getTimestampsToReturn() );
    				encoder.putBoolean("ReleaseContinuationPoints",  (obj==null)?null:obj.getReleaseContinuationPoints() );
    				encoder.putEncodeableArray("NodesToRead", HistoryReadValueId.class, (obj==null)?null:obj.getNodesToRead());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryReadRequest result = new HistoryReadRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setHistoryReadDetails( decoder.getExtensionObject("HistoryReadDetails") );
    				result.setTimestampsToReturn( decoder.getEnumeration("TimestampsToReturn", TimestampsToReturn.class) );
    				result.setReleaseContinuationPoints( decoder.getBoolean("ReleaseContinuationPoints") );
    				result.setNodesToRead( decoder.getEncodeableArray("NodesToRead", HistoryReadValueId.class) );
    				return result;
    			}
    		});
    
    	// HistoryReadResponse
    	addSerializer(
    		new AbstractSerializer(HistoryReadResponse.class, HistoryReadResponse.BINARY, HistoryReadResponse.XML, HistoryReadResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryReadResponse obj = (HistoryReadResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, HistoryReadResult.class, (obj==null)?null:obj.getResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryReadResponse obj = (HistoryReadResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Results", HistoryReadResult.class, (obj==null)?null:obj.getResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryReadResponse result = new HistoryReadResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getEncodeableArray("Results", HistoryReadResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// WriteValue
    	addSerializer(
    		new AbstractSerializer(WriteValue.class, WriteValue.BINARY, WriteValue.XML, WriteValue.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				WriteValue obj = (WriteValue) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putUInt32(null, null /*obj.getAttributeId()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getIndexRange() );
    				calculator.putDataValue(null,  (obj==null)?null:obj.getValue() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				WriteValue obj = (WriteValue) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putUInt32("AttributeId",  (obj==null)?null:obj.getAttributeId() );
    				encoder.putString("IndexRange",  (obj==null)?null:obj.getIndexRange() );
    				encoder.putDataValue("Value",  (obj==null)?null:obj.getValue() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				WriteValue result = new WriteValue();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setAttributeId( decoder.getUInt32("AttributeId") );
    				result.setIndexRange( decoder.getString("IndexRange") );
    				result.setValue( decoder.getDataValue("Value") );
    				return result;
    			}
    		});
    
    	// WriteRequest
    	addSerializer(
    		new AbstractSerializer(WriteRequest.class, WriteRequest.BINARY, WriteRequest.XML, WriteRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				WriteRequest obj = (WriteRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeableArray(null, WriteValue.class, (obj==null)?null:obj.getNodesToWrite());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				WriteRequest obj = (WriteRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeableArray("NodesToWrite", WriteValue.class, (obj==null)?null:obj.getNodesToWrite());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				WriteRequest result = new WriteRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setNodesToWrite( decoder.getEncodeableArray("NodesToWrite", WriteValue.class) );
    				return result;
    			}
    		});
    
    	// WriteResponse
    	addSerializer(
    		new AbstractSerializer(WriteResponse.class, WriteResponse.BINARY, WriteResponse.XML, WriteResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				WriteResponse obj = (WriteResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				WriteResponse obj = (WriteResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putStatusCodeArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				WriteResponse result = new WriteResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getStatusCodeArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// HistoryUpdateDetails
    	addSerializer(
    		new AbstractSerializer(HistoryUpdateDetails.class, HistoryUpdateDetails.BINARY, HistoryUpdateDetails.XML, HistoryUpdateDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryUpdateDetails obj = (HistoryUpdateDetails) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryUpdateDetails obj = (HistoryUpdateDetails) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryUpdateDetails result = new HistoryUpdateDetails();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				return result;
    			}
    		});
    
    	// UpdateDataDetails
    	addSerializer(
    		new AbstractSerializer(UpdateDataDetails.class, UpdateDataDetails.BINARY, UpdateDataDetails.XML, UpdateDataDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				UpdateDataDetails obj = (UpdateDataDetails) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getPerformInsertReplace()*/);
    				calculator.putDataValueArray(null, ((obj==null)?null:obj.getUpdateValues()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				UpdateDataDetails obj = (UpdateDataDetails) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("PerformInsertReplace",  (obj==null)?null:obj.getPerformInsertReplace() );
    				encoder.putDataValueArray("UpdateValues", (obj==null)?null:obj.getUpdateValues() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				UpdateDataDetails result = new UpdateDataDetails();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setPerformInsertReplace( decoder.getEnumeration("PerformInsertReplace", PerformUpdateType.class) );
    				result.setUpdateValues( decoder.getDataValueArray("UpdateValues") );
    				return result;
    			}
    		});
    
    	// UpdateStructureDataDetails
    	addSerializer(
    		new AbstractSerializer(UpdateStructureDataDetails.class, UpdateStructureDataDetails.BINARY, UpdateStructureDataDetails.XML, UpdateStructureDataDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				UpdateStructureDataDetails obj = (UpdateStructureDataDetails) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getPerformInsertReplace()*/);
    				calculator.putDataValueArray(null, ((obj==null)?null:obj.getUpdateValues()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				UpdateStructureDataDetails obj = (UpdateStructureDataDetails) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("PerformInsertReplace",  (obj==null)?null:obj.getPerformInsertReplace() );
    				encoder.putDataValueArray("UpdateValues", (obj==null)?null:obj.getUpdateValues() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				UpdateStructureDataDetails result = new UpdateStructureDataDetails();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setPerformInsertReplace( decoder.getEnumeration("PerformInsertReplace", PerformUpdateType.class) );
    				result.setUpdateValues( decoder.getDataValueArray("UpdateValues") );
    				return result;
    			}
    		});
    
    	// UpdateEventDetails
    	addSerializer(
    		new AbstractSerializer(UpdateEventDetails.class, UpdateEventDetails.BINARY, UpdateEventDetails.XML, UpdateEventDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				UpdateEventDetails obj = (UpdateEventDetails) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putEnumeration(null, null /*obj.getPerformInsertReplace()*/);
    				calculator.putEncodeable(null, EventFilter.class, (obj==null)?null:obj.getFilter());
    				calculator.putEncodeableArray(null, HistoryEventFieldList.class, (obj==null)?null:obj.getEventData());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				UpdateEventDetails obj = (UpdateEventDetails) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putEnumeration("PerformInsertReplace",  (obj==null)?null:obj.getPerformInsertReplace() );
    				encoder.putEncodeable("Filter", EventFilter.class, (obj==null)?null:obj.getFilter());
    				encoder.putEncodeableArray("EventData", HistoryEventFieldList.class, (obj==null)?null:obj.getEventData());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				UpdateEventDetails result = new UpdateEventDetails();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setPerformInsertReplace( decoder.getEnumeration("PerformInsertReplace", PerformUpdateType.class) );
    				result.setFilter( decoder.getEncodeable("Filter", EventFilter.class) );
    				result.setEventData( decoder.getEncodeableArray("EventData", HistoryEventFieldList.class) );
    				return result;
    			}
    		});
    
    	// DeleteRawModifiedDetails
    	addSerializer(
    		new AbstractSerializer(DeleteRawModifiedDetails.class, DeleteRawModifiedDetails.BINARY, DeleteRawModifiedDetails.XML, DeleteRawModifiedDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteRawModifiedDetails obj = (DeleteRawModifiedDetails) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putBoolean(null, null /*obj.getIsDeleteModified()*/);
    				calculator.putDateTime(null,  (obj==null)?null:obj.getStartTime() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getEndTime() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteRawModifiedDetails obj = (DeleteRawModifiedDetails) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putBoolean("IsDeleteModified",  (obj==null)?null:obj.getIsDeleteModified() );
    				encoder.putDateTime("StartTime",  (obj==null)?null:obj.getStartTime() );
    				encoder.putDateTime("EndTime",  (obj==null)?null:obj.getEndTime() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteRawModifiedDetails result = new DeleteRawModifiedDetails();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setIsDeleteModified( decoder.getBoolean("IsDeleteModified") );
    				result.setStartTime( decoder.getDateTime("StartTime") );
    				result.setEndTime( decoder.getDateTime("EndTime") );
    				return result;
    			}
    		});
    
    	// DeleteAtTimeDetails
    	addSerializer(
    		new AbstractSerializer(DeleteAtTimeDetails.class, DeleteAtTimeDetails.BINARY, DeleteAtTimeDetails.XML, DeleteAtTimeDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteAtTimeDetails obj = (DeleteAtTimeDetails) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putDateTimeArray(null, ((obj==null)?null:obj.getReqTimes()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteAtTimeDetails obj = (DeleteAtTimeDetails) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putDateTimeArray("ReqTimes", (obj==null)?null:obj.getReqTimes() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteAtTimeDetails result = new DeleteAtTimeDetails();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setReqTimes( decoder.getDateTimeArray("ReqTimes") );
    				return result;
    			}
    		});
    
    	// DeleteEventDetails
    	addSerializer(
    		new AbstractSerializer(DeleteEventDetails.class, DeleteEventDetails.BINARY, DeleteEventDetails.XML, DeleteEventDetails.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteEventDetails obj = (DeleteEventDetails) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getNodeId() );
    				calculator.putByteStringArray(null, ((obj==null)?null:obj.getEventIds()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteEventDetails obj = (DeleteEventDetails) encodeable;
    				encoder.putNodeId("NodeId",  (obj==null)?null:obj.getNodeId() );
    				encoder.putByteStringArray("EventIds", (obj==null)?null:obj.getEventIds() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteEventDetails result = new DeleteEventDetails();
    				result.setNodeId( decoder.getNodeId("NodeId") );
    				result.setEventIds( decoder.getByteStringArray("EventIds") );
    				return result;
    			}
    		});
    
    	// HistoryUpdateResult
    	addSerializer(
    		new AbstractSerializer(HistoryUpdateResult.class, HistoryUpdateResult.BINARY, HistoryUpdateResult.XML, HistoryUpdateResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryUpdateResult obj = (HistoryUpdateResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getOperationResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryUpdateResult obj = (HistoryUpdateResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putStatusCodeArray("OperationResults", (obj==null)?null:obj.getOperationResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryUpdateResult result = new HistoryUpdateResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setOperationResults( decoder.getStatusCodeArray("OperationResults") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// HistoryUpdateRequest
    	addSerializer(
    		new AbstractSerializer(HistoryUpdateRequest.class, HistoryUpdateRequest.BINARY, HistoryUpdateRequest.XML, HistoryUpdateRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryUpdateRequest obj = (HistoryUpdateRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putExtensionObjectArray(null, ((obj==null)?null:obj.getHistoryUpdateDetails()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryUpdateRequest obj = (HistoryUpdateRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putExtensionObjectArray("HistoryUpdateDetails", (obj==null)?null:obj.getHistoryUpdateDetails() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryUpdateRequest result = new HistoryUpdateRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setHistoryUpdateDetails( decoder.getExtensionObjectArray("HistoryUpdateDetails") );
    				return result;
    			}
    		});
    
    	// HistoryUpdateResponse
    	addSerializer(
    		new AbstractSerializer(HistoryUpdateResponse.class, HistoryUpdateResponse.BINARY, HistoryUpdateResponse.XML, HistoryUpdateResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryUpdateResponse obj = (HistoryUpdateResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, HistoryUpdateResult.class, (obj==null)?null:obj.getResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryUpdateResponse obj = (HistoryUpdateResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Results", HistoryUpdateResult.class, (obj==null)?null:obj.getResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryUpdateResponse result = new HistoryUpdateResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getEncodeableArray("Results", HistoryUpdateResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// CallMethodRequest
    	addSerializer(
    		new AbstractSerializer(CallMethodRequest.class, CallMethodRequest.BINARY, CallMethodRequest.XML, CallMethodRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CallMethodRequest obj = (CallMethodRequest) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getObjectId() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getMethodId() );
    				calculator.putVariantArray(null, ((obj==null)?null:obj.getInputArguments()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CallMethodRequest obj = (CallMethodRequest) encodeable;
    				encoder.putNodeId("ObjectId",  (obj==null)?null:obj.getObjectId() );
    				encoder.putNodeId("MethodId",  (obj==null)?null:obj.getMethodId() );
    				encoder.putVariantArray("InputArguments", (obj==null)?null:obj.getInputArguments() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CallMethodRequest result = new CallMethodRequest();
    				result.setObjectId( decoder.getNodeId("ObjectId") );
    				result.setMethodId( decoder.getNodeId("MethodId") );
    				result.setInputArguments( decoder.getVariantArray("InputArguments") );
    				return result;
    			}
    		});
    
    	// CallMethodResult
    	addSerializer(
    		new AbstractSerializer(CallMethodResult.class, CallMethodResult.BINARY, CallMethodResult.XML, CallMethodResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CallMethodResult obj = (CallMethodResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getInputArgumentResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getInputArgumentDiagnosticInfos()) );
    				calculator.putVariantArray(null, ((obj==null)?null:obj.getOutputArguments()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CallMethodResult obj = (CallMethodResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putStatusCodeArray("InputArgumentResults", (obj==null)?null:obj.getInputArgumentResults() );
    				encoder.putDiagnosticInfoArray("InputArgumentDiagnosticInfos", (obj==null)?null:obj.getInputArgumentDiagnosticInfos() );
    				encoder.putVariantArray("OutputArguments", (obj==null)?null:obj.getOutputArguments() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CallMethodResult result = new CallMethodResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setInputArgumentResults( decoder.getStatusCodeArray("InputArgumentResults") );
    				result.setInputArgumentDiagnosticInfos( decoder.getDiagnosticInfoArray("InputArgumentDiagnosticInfos") );
    				result.setOutputArguments( decoder.getVariantArray("OutputArguments") );
    				return result;
    			}
    		});
    
    	// CallRequest
    	addSerializer(
    		new AbstractSerializer(CallRequest.class, CallRequest.BINARY, CallRequest.XML, CallRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CallRequest obj = (CallRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeableArray(null, CallMethodRequest.class, (obj==null)?null:obj.getMethodsToCall());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CallRequest obj = (CallRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeableArray("MethodsToCall", CallMethodRequest.class, (obj==null)?null:obj.getMethodsToCall());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CallRequest result = new CallRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setMethodsToCall( decoder.getEncodeableArray("MethodsToCall", CallMethodRequest.class) );
    				return result;
    			}
    		});
    
    	// CallResponse
    	addSerializer(
    		new AbstractSerializer(CallResponse.class, CallResponse.BINARY, CallResponse.XML, CallResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CallResponse obj = (CallResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, CallMethodResult.class, (obj==null)?null:obj.getResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CallResponse obj = (CallResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Results", CallMethodResult.class, (obj==null)?null:obj.getResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CallResponse result = new CallResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getEncodeableArray("Results", CallMethodResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// MonitoringFilter
    	addSerializer(
    		new AbstractSerializer(MonitoringFilter.class, MonitoringFilter.BINARY, MonitoringFilter.XML, MonitoringFilter.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MonitoringFilter result = new MonitoringFilter();
    				return result;
    			}
    		});
    
    	// DataChangeFilter
    	addSerializer(
    		new AbstractSerializer(DataChangeFilter.class, DataChangeFilter.BINARY, DataChangeFilter.XML, DataChangeFilter.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DataChangeFilter obj = (DataChangeFilter) encodeable;
    				calculator.putEnumeration(null, null /*obj.getTrigger()*/);
    				calculator.putUInt32(null, null /*obj.getDeadbandType()*/);
    				calculator.putDouble(null, null /*obj.getDeadbandValue()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DataChangeFilter obj = (DataChangeFilter) encodeable;
    				encoder.putEnumeration("Trigger",  (obj==null)?null:obj.getTrigger() );
    				encoder.putUInt32("DeadbandType",  (obj==null)?null:obj.getDeadbandType() );
    				encoder.putDouble("DeadbandValue",  (obj==null)?null:obj.getDeadbandValue() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DataChangeFilter result = new DataChangeFilter();
    				result.setTrigger( decoder.getEnumeration("Trigger", DataChangeTrigger.class) );
    				result.setDeadbandType( decoder.getUInt32("DeadbandType") );
    				result.setDeadbandValue( decoder.getDouble("DeadbandValue") );
    				return result;
    			}
    		});
    
    	// EventFilter
    	addSerializer(
    		new AbstractSerializer(EventFilter.class, EventFilter.BINARY, EventFilter.XML, EventFilter.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				EventFilter obj = (EventFilter) encodeable;
    				calculator.putEncodeableArray(null, SimpleAttributeOperand.class, (obj==null)?null:obj.getSelectClauses());
    				calculator.putEncodeable(null, ContentFilter.class, (obj==null)?null:obj.getWhereClause());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				EventFilter obj = (EventFilter) encodeable;
    				encoder.putEncodeableArray("SelectClauses", SimpleAttributeOperand.class, (obj==null)?null:obj.getSelectClauses());
    				encoder.putEncodeable("WhereClause", ContentFilter.class, (obj==null)?null:obj.getWhereClause());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				EventFilter result = new EventFilter();
    				result.setSelectClauses( decoder.getEncodeableArray("SelectClauses", SimpleAttributeOperand.class) );
    				result.setWhereClause( decoder.getEncodeable("WhereClause", ContentFilter.class) );
    				return result;
    			}
    		});
    
    	// AggregateConfiguration
    	addSerializer(
    		new AbstractSerializer(AggregateConfiguration.class, AggregateConfiguration.BINARY, AggregateConfiguration.XML, AggregateConfiguration.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AggregateConfiguration obj = (AggregateConfiguration) encodeable;
    				calculator.putBoolean(null, null /*obj.getUseServerCapabilitiesDefaults()*/);
    				calculator.putBoolean(null, null /*obj.getTreatUncertainAsBad()*/);
    				calculator.putByte(null, null /*obj.getPercentDataBad()*/);
    				calculator.putByte(null, null /*obj.getPercentDataGood()*/);
    				calculator.putBoolean(null, null /*obj.getUseSlopedExtrapolation()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AggregateConfiguration obj = (AggregateConfiguration) encodeable;
    				encoder.putBoolean("UseServerCapabilitiesDefaults",  (obj==null)?null:obj.getUseServerCapabilitiesDefaults() );
    				encoder.putBoolean("TreatUncertainAsBad",  (obj==null)?null:obj.getTreatUncertainAsBad() );
    				encoder.putByte("PercentDataBad",  (obj==null)?null:obj.getPercentDataBad() );
    				encoder.putByte("PercentDataGood",  (obj==null)?null:obj.getPercentDataGood() );
    				encoder.putBoolean("UseSlopedExtrapolation",  (obj==null)?null:obj.getUseSlopedExtrapolation() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AggregateConfiguration result = new AggregateConfiguration();
    				result.setUseServerCapabilitiesDefaults( decoder.getBoolean("UseServerCapabilitiesDefaults") );
    				result.setTreatUncertainAsBad( decoder.getBoolean("TreatUncertainAsBad") );
    				result.setPercentDataBad( decoder.getByte("PercentDataBad") );
    				result.setPercentDataGood( decoder.getByte("PercentDataGood") );
    				result.setUseSlopedExtrapolation( decoder.getBoolean("UseSlopedExtrapolation") );
    				return result;
    			}
    		});
    
    	// AggregateFilter
    	addSerializer(
    		new AbstractSerializer(AggregateFilter.class, AggregateFilter.BINARY, AggregateFilter.XML, AggregateFilter.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AggregateFilter obj = (AggregateFilter) encodeable;
    				calculator.putDateTime(null,  (obj==null)?null:obj.getStartTime() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getAggregateType() );
    				calculator.putDouble(null, null /*obj.getProcessingInterval()*/);
    				calculator.putEncodeable(null, AggregateConfiguration.class, (obj==null)?null:obj.getAggregateConfiguration());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AggregateFilter obj = (AggregateFilter) encodeable;
    				encoder.putDateTime("StartTime",  (obj==null)?null:obj.getStartTime() );
    				encoder.putNodeId("AggregateType",  (obj==null)?null:obj.getAggregateType() );
    				encoder.putDouble("ProcessingInterval",  (obj==null)?null:obj.getProcessingInterval() );
    				encoder.putEncodeable("AggregateConfiguration", AggregateConfiguration.class, (obj==null)?null:obj.getAggregateConfiguration());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AggregateFilter result = new AggregateFilter();
    				result.setStartTime( decoder.getDateTime("StartTime") );
    				result.setAggregateType( decoder.getNodeId("AggregateType") );
    				result.setProcessingInterval( decoder.getDouble("ProcessingInterval") );
    				result.setAggregateConfiguration( decoder.getEncodeable("AggregateConfiguration", AggregateConfiguration.class) );
    				return result;
    			}
    		});
    
    	// MonitoringFilterResult
    	addSerializer(
    		new AbstractSerializer(MonitoringFilterResult.class, MonitoringFilterResult.BINARY, MonitoringFilterResult.XML, MonitoringFilterResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MonitoringFilterResult result = new MonitoringFilterResult();
    				return result;
    			}
    		});
    
    	// EventFilterResult
    	addSerializer(
    		new AbstractSerializer(EventFilterResult.class, EventFilterResult.BINARY, EventFilterResult.XML, EventFilterResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				EventFilterResult obj = (EventFilterResult) encodeable;
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getSelectClauseResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getSelectClauseDiagnosticInfos()) );
    				calculator.putEncodeable(null, ContentFilterResult.class, (obj==null)?null:obj.getWhereClauseResult());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				EventFilterResult obj = (EventFilterResult) encodeable;
    				encoder.putStatusCodeArray("SelectClauseResults", (obj==null)?null:obj.getSelectClauseResults() );
    				encoder.putDiagnosticInfoArray("SelectClauseDiagnosticInfos", (obj==null)?null:obj.getSelectClauseDiagnosticInfos() );
    				encoder.putEncodeable("WhereClauseResult", ContentFilterResult.class, (obj==null)?null:obj.getWhereClauseResult());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				EventFilterResult result = new EventFilterResult();
    				result.setSelectClauseResults( decoder.getStatusCodeArray("SelectClauseResults") );
    				result.setSelectClauseDiagnosticInfos( decoder.getDiagnosticInfoArray("SelectClauseDiagnosticInfos") );
    				result.setWhereClauseResult( decoder.getEncodeable("WhereClauseResult", ContentFilterResult.class) );
    				return result;
    			}
    		});
    
    	// AggregateFilterResult
    	addSerializer(
    		new AbstractSerializer(AggregateFilterResult.class, AggregateFilterResult.BINARY, AggregateFilterResult.XML, AggregateFilterResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AggregateFilterResult obj = (AggregateFilterResult) encodeable;
    				calculator.putDateTime(null,  (obj==null)?null:obj.getRevisedStartTime() );
    				calculator.putDouble(null, null /*obj.getRevisedProcessingInterval()*/);
    				calculator.putEncodeable(null, AggregateConfiguration.class, (obj==null)?null:obj.getRevisedAggregateConfiguration());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AggregateFilterResult obj = (AggregateFilterResult) encodeable;
    				encoder.putDateTime("RevisedStartTime",  (obj==null)?null:obj.getRevisedStartTime() );
    				encoder.putDouble("RevisedProcessingInterval",  (obj==null)?null:obj.getRevisedProcessingInterval() );
    				encoder.putEncodeable("RevisedAggregateConfiguration", AggregateConfiguration.class, (obj==null)?null:obj.getRevisedAggregateConfiguration());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AggregateFilterResult result = new AggregateFilterResult();
    				result.setRevisedStartTime( decoder.getDateTime("RevisedStartTime") );
    				result.setRevisedProcessingInterval( decoder.getDouble("RevisedProcessingInterval") );
    				result.setRevisedAggregateConfiguration( decoder.getEncodeable("RevisedAggregateConfiguration", AggregateConfiguration.class) );
    				return result;
    			}
    		});
    
    	// MonitoringParameters
    	addSerializer(
    		new AbstractSerializer(MonitoringParameters.class, MonitoringParameters.BINARY, MonitoringParameters.XML, MonitoringParameters.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				MonitoringParameters obj = (MonitoringParameters) encodeable;
    				calculator.putUInt32(null, null /*obj.getClientHandle()*/);
    				calculator.putDouble(null, null /*obj.getSamplingInterval()*/);
    				calculator.putExtensionObject(null,  (obj==null)?null:obj.getFilter() );
    				calculator.putUInt32(null, null /*obj.getQueueSize()*/);
    				calculator.putBoolean(null, null /*obj.getDiscardOldest()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				MonitoringParameters obj = (MonitoringParameters) encodeable;
    				encoder.putUInt32("ClientHandle",  (obj==null)?null:obj.getClientHandle() );
    				encoder.putDouble("SamplingInterval",  (obj==null)?null:obj.getSamplingInterval() );
    				encoder.putExtensionObject("Filter",  (obj==null)?null:obj.getFilter() );
    				encoder.putUInt32("QueueSize",  (obj==null)?null:obj.getQueueSize() );
    				encoder.putBoolean("DiscardOldest",  (obj==null)?null:obj.getDiscardOldest() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MonitoringParameters result = new MonitoringParameters();
    				result.setClientHandle( decoder.getUInt32("ClientHandle") );
    				result.setSamplingInterval( decoder.getDouble("SamplingInterval") );
    				result.setFilter( decoder.getExtensionObject("Filter") );
    				result.setQueueSize( decoder.getUInt32("QueueSize") );
    				result.setDiscardOldest( decoder.getBoolean("DiscardOldest") );
    				return result;
    			}
    		});
    
    	// MonitoredItemCreateRequest
    	addSerializer(
    		new AbstractSerializer(MonitoredItemCreateRequest.class, MonitoredItemCreateRequest.BINARY, MonitoredItemCreateRequest.XML, MonitoredItemCreateRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				MonitoredItemCreateRequest obj = (MonitoredItemCreateRequest) encodeable;
    				calculator.putEncodeable(null, ReadValueId.class, (obj==null)?null:obj.getItemToMonitor());
    				calculator.putEnumeration(null, null /*obj.getMonitoringMode()*/);
    				calculator.putEncodeable(null, MonitoringParameters.class, (obj==null)?null:obj.getRequestedParameters());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				MonitoredItemCreateRequest obj = (MonitoredItemCreateRequest) encodeable;
    				encoder.putEncodeable("ItemToMonitor", ReadValueId.class, (obj==null)?null:obj.getItemToMonitor());
    				encoder.putEnumeration("MonitoringMode",  (obj==null)?null:obj.getMonitoringMode() );
    				encoder.putEncodeable("RequestedParameters", MonitoringParameters.class, (obj==null)?null:obj.getRequestedParameters());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MonitoredItemCreateRequest result = new MonitoredItemCreateRequest();
    				result.setItemToMonitor( decoder.getEncodeable("ItemToMonitor", ReadValueId.class) );
    				result.setMonitoringMode( decoder.getEnumeration("MonitoringMode", MonitoringMode.class) );
    				result.setRequestedParameters( decoder.getEncodeable("RequestedParameters", MonitoringParameters.class) );
    				return result;
    			}
    		});
    
    	// MonitoredItemCreateResult
    	addSerializer(
    		new AbstractSerializer(MonitoredItemCreateResult.class, MonitoredItemCreateResult.BINARY, MonitoredItemCreateResult.XML, MonitoredItemCreateResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				MonitoredItemCreateResult obj = (MonitoredItemCreateResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putUInt32(null, null /*obj.getMonitoredItemId()*/);
    				calculator.putDouble(null, null /*obj.getRevisedSamplingInterval()*/);
    				calculator.putUInt32(null, null /*obj.getRevisedQueueSize()*/);
    				calculator.putExtensionObject(null,  (obj==null)?null:obj.getFilterResult() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				MonitoredItemCreateResult obj = (MonitoredItemCreateResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putUInt32("MonitoredItemId",  (obj==null)?null:obj.getMonitoredItemId() );
    				encoder.putDouble("RevisedSamplingInterval",  (obj==null)?null:obj.getRevisedSamplingInterval() );
    				encoder.putUInt32("RevisedQueueSize",  (obj==null)?null:obj.getRevisedQueueSize() );
    				encoder.putExtensionObject("FilterResult",  (obj==null)?null:obj.getFilterResult() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MonitoredItemCreateResult result = new MonitoredItemCreateResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setMonitoredItemId( decoder.getUInt32("MonitoredItemId") );
    				result.setRevisedSamplingInterval( decoder.getDouble("RevisedSamplingInterval") );
    				result.setRevisedQueueSize( decoder.getUInt32("RevisedQueueSize") );
    				result.setFilterResult( decoder.getExtensionObject("FilterResult") );
    				return result;
    			}
    		});
    
    	// CreateMonitoredItemsRequest
    	addSerializer(
    		new AbstractSerializer(CreateMonitoredItemsRequest.class, CreateMonitoredItemsRequest.BINARY, CreateMonitoredItemsRequest.XML, CreateMonitoredItemsRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CreateMonitoredItemsRequest obj = (CreateMonitoredItemsRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putEnumeration(null, null /*obj.getTimestampsToReturn()*/);
    				calculator.putEncodeableArray(null, MonitoredItemCreateRequest.class, (obj==null)?null:obj.getItemsToCreate());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CreateMonitoredItemsRequest obj = (CreateMonitoredItemsRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putEnumeration("TimestampsToReturn",  (obj==null)?null:obj.getTimestampsToReturn() );
    				encoder.putEncodeableArray("ItemsToCreate", MonitoredItemCreateRequest.class, (obj==null)?null:obj.getItemsToCreate());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CreateMonitoredItemsRequest result = new CreateMonitoredItemsRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setTimestampsToReturn( decoder.getEnumeration("TimestampsToReturn", TimestampsToReturn.class) );
    				result.setItemsToCreate( decoder.getEncodeableArray("ItemsToCreate", MonitoredItemCreateRequest.class) );
    				return result;
    			}
    		});
    
    	// CreateMonitoredItemsResponse
    	addSerializer(
    		new AbstractSerializer(CreateMonitoredItemsResponse.class, CreateMonitoredItemsResponse.BINARY, CreateMonitoredItemsResponse.XML, CreateMonitoredItemsResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CreateMonitoredItemsResponse obj = (CreateMonitoredItemsResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, MonitoredItemCreateResult.class, (obj==null)?null:obj.getResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CreateMonitoredItemsResponse obj = (CreateMonitoredItemsResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Results", MonitoredItemCreateResult.class, (obj==null)?null:obj.getResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CreateMonitoredItemsResponse result = new CreateMonitoredItemsResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getEncodeableArray("Results", MonitoredItemCreateResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// MonitoredItemModifyRequest
    	addSerializer(
    		new AbstractSerializer(MonitoredItemModifyRequest.class, MonitoredItemModifyRequest.BINARY, MonitoredItemModifyRequest.XML, MonitoredItemModifyRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				MonitoredItemModifyRequest obj = (MonitoredItemModifyRequest) encodeable;
    				calculator.putUInt32(null, null /*obj.getMonitoredItemId()*/);
    				calculator.putEncodeable(null, MonitoringParameters.class, (obj==null)?null:obj.getRequestedParameters());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				MonitoredItemModifyRequest obj = (MonitoredItemModifyRequest) encodeable;
    				encoder.putUInt32("MonitoredItemId",  (obj==null)?null:obj.getMonitoredItemId() );
    				encoder.putEncodeable("RequestedParameters", MonitoringParameters.class, (obj==null)?null:obj.getRequestedParameters());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MonitoredItemModifyRequest result = new MonitoredItemModifyRequest();
    				result.setMonitoredItemId( decoder.getUInt32("MonitoredItemId") );
    				result.setRequestedParameters( decoder.getEncodeable("RequestedParameters", MonitoringParameters.class) );
    				return result;
    			}
    		});
    
    	// MonitoredItemModifyResult
    	addSerializer(
    		new AbstractSerializer(MonitoredItemModifyResult.class, MonitoredItemModifyResult.BINARY, MonitoredItemModifyResult.XML, MonitoredItemModifyResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				MonitoredItemModifyResult obj = (MonitoredItemModifyResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putDouble(null, null /*obj.getRevisedSamplingInterval()*/);
    				calculator.putUInt32(null, null /*obj.getRevisedQueueSize()*/);
    				calculator.putExtensionObject(null,  (obj==null)?null:obj.getFilterResult() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				MonitoredItemModifyResult obj = (MonitoredItemModifyResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putDouble("RevisedSamplingInterval",  (obj==null)?null:obj.getRevisedSamplingInterval() );
    				encoder.putUInt32("RevisedQueueSize",  (obj==null)?null:obj.getRevisedQueueSize() );
    				encoder.putExtensionObject("FilterResult",  (obj==null)?null:obj.getFilterResult() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MonitoredItemModifyResult result = new MonitoredItemModifyResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setRevisedSamplingInterval( decoder.getDouble("RevisedSamplingInterval") );
    				result.setRevisedQueueSize( decoder.getUInt32("RevisedQueueSize") );
    				result.setFilterResult( decoder.getExtensionObject("FilterResult") );
    				return result;
    			}
    		});
    
    	// ModifyMonitoredItemsRequest
    	addSerializer(
    		new AbstractSerializer(ModifyMonitoredItemsRequest.class, ModifyMonitoredItemsRequest.BINARY, ModifyMonitoredItemsRequest.XML, ModifyMonitoredItemsRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ModifyMonitoredItemsRequest obj = (ModifyMonitoredItemsRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putEnumeration(null, null /*obj.getTimestampsToReturn()*/);
    				calculator.putEncodeableArray(null, MonitoredItemModifyRequest.class, (obj==null)?null:obj.getItemsToModify());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ModifyMonitoredItemsRequest obj = (ModifyMonitoredItemsRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putEnumeration("TimestampsToReturn",  (obj==null)?null:obj.getTimestampsToReturn() );
    				encoder.putEncodeableArray("ItemsToModify", MonitoredItemModifyRequest.class, (obj==null)?null:obj.getItemsToModify());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ModifyMonitoredItemsRequest result = new ModifyMonitoredItemsRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setTimestampsToReturn( decoder.getEnumeration("TimestampsToReturn", TimestampsToReturn.class) );
    				result.setItemsToModify( decoder.getEncodeableArray("ItemsToModify", MonitoredItemModifyRequest.class) );
    				return result;
    			}
    		});
    
    	// ModifyMonitoredItemsResponse
    	addSerializer(
    		new AbstractSerializer(ModifyMonitoredItemsResponse.class, ModifyMonitoredItemsResponse.BINARY, ModifyMonitoredItemsResponse.XML, ModifyMonitoredItemsResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ModifyMonitoredItemsResponse obj = (ModifyMonitoredItemsResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, MonitoredItemModifyResult.class, (obj==null)?null:obj.getResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ModifyMonitoredItemsResponse obj = (ModifyMonitoredItemsResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Results", MonitoredItemModifyResult.class, (obj==null)?null:obj.getResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ModifyMonitoredItemsResponse result = new ModifyMonitoredItemsResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getEncodeableArray("Results", MonitoredItemModifyResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// SetMonitoringModeRequest
    	addSerializer(
    		new AbstractSerializer(SetMonitoringModeRequest.class, SetMonitoringModeRequest.BINARY, SetMonitoringModeRequest.XML, SetMonitoringModeRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SetMonitoringModeRequest obj = (SetMonitoringModeRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putEnumeration(null, null /*obj.getMonitoringMode()*/);
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getMonitoredItemIds()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SetMonitoringModeRequest obj = (SetMonitoringModeRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putEnumeration("MonitoringMode",  (obj==null)?null:obj.getMonitoringMode() );
    				encoder.putUInt32Array("MonitoredItemIds", (obj==null)?null:obj.getMonitoredItemIds() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SetMonitoringModeRequest result = new SetMonitoringModeRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setMonitoringMode( decoder.getEnumeration("MonitoringMode", MonitoringMode.class) );
    				result.setMonitoredItemIds( decoder.getUInt32Array("MonitoredItemIds") );
    				return result;
    			}
    		});
    
    	// SetMonitoringModeResponse
    	addSerializer(
    		new AbstractSerializer(SetMonitoringModeResponse.class, SetMonitoringModeResponse.BINARY, SetMonitoringModeResponse.XML, SetMonitoringModeResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SetMonitoringModeResponse obj = (SetMonitoringModeResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SetMonitoringModeResponse obj = (SetMonitoringModeResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putStatusCodeArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SetMonitoringModeResponse result = new SetMonitoringModeResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getStatusCodeArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// SetTriggeringRequest
    	addSerializer(
    		new AbstractSerializer(SetTriggeringRequest.class, SetTriggeringRequest.BINARY, SetTriggeringRequest.XML, SetTriggeringRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SetTriggeringRequest obj = (SetTriggeringRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putUInt32(null, null /*obj.getTriggeringItemId()*/);
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getLinksToAdd()) );
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getLinksToRemove()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SetTriggeringRequest obj = (SetTriggeringRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putUInt32("TriggeringItemId",  (obj==null)?null:obj.getTriggeringItemId() );
    				encoder.putUInt32Array("LinksToAdd", (obj==null)?null:obj.getLinksToAdd() );
    				encoder.putUInt32Array("LinksToRemove", (obj==null)?null:obj.getLinksToRemove() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SetTriggeringRequest result = new SetTriggeringRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setTriggeringItemId( decoder.getUInt32("TriggeringItemId") );
    				result.setLinksToAdd( decoder.getUInt32Array("LinksToAdd") );
    				result.setLinksToRemove( decoder.getUInt32Array("LinksToRemove") );
    				return result;
    			}
    		});
    
    	// SetTriggeringResponse
    	addSerializer(
    		new AbstractSerializer(SetTriggeringResponse.class, SetTriggeringResponse.BINARY, SetTriggeringResponse.XML, SetTriggeringResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SetTriggeringResponse obj = (SetTriggeringResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getAddResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getAddDiagnosticInfos()) );
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getRemoveResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getRemoveDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SetTriggeringResponse obj = (SetTriggeringResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putStatusCodeArray("AddResults", (obj==null)?null:obj.getAddResults() );
    				encoder.putDiagnosticInfoArray("AddDiagnosticInfos", (obj==null)?null:obj.getAddDiagnosticInfos() );
    				encoder.putStatusCodeArray("RemoveResults", (obj==null)?null:obj.getRemoveResults() );
    				encoder.putDiagnosticInfoArray("RemoveDiagnosticInfos", (obj==null)?null:obj.getRemoveDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SetTriggeringResponse result = new SetTriggeringResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setAddResults( decoder.getStatusCodeArray("AddResults") );
    				result.setAddDiagnosticInfos( decoder.getDiagnosticInfoArray("AddDiagnosticInfos") );
    				result.setRemoveResults( decoder.getStatusCodeArray("RemoveResults") );
    				result.setRemoveDiagnosticInfos( decoder.getDiagnosticInfoArray("RemoveDiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// DeleteMonitoredItemsRequest
    	addSerializer(
    		new AbstractSerializer(DeleteMonitoredItemsRequest.class, DeleteMonitoredItemsRequest.BINARY, DeleteMonitoredItemsRequest.XML, DeleteMonitoredItemsRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteMonitoredItemsRequest obj = (DeleteMonitoredItemsRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getMonitoredItemIds()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteMonitoredItemsRequest obj = (DeleteMonitoredItemsRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putUInt32Array("MonitoredItemIds", (obj==null)?null:obj.getMonitoredItemIds() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteMonitoredItemsRequest result = new DeleteMonitoredItemsRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setMonitoredItemIds( decoder.getUInt32Array("MonitoredItemIds") );
    				return result;
    			}
    		});
    
    	// DeleteMonitoredItemsResponse
    	addSerializer(
    		new AbstractSerializer(DeleteMonitoredItemsResponse.class, DeleteMonitoredItemsResponse.BINARY, DeleteMonitoredItemsResponse.XML, DeleteMonitoredItemsResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteMonitoredItemsResponse obj = (DeleteMonitoredItemsResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteMonitoredItemsResponse obj = (DeleteMonitoredItemsResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putStatusCodeArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteMonitoredItemsResponse result = new DeleteMonitoredItemsResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getStatusCodeArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// CreateSubscriptionRequest
    	addSerializer(
    		new AbstractSerializer(CreateSubscriptionRequest.class, CreateSubscriptionRequest.BINARY, CreateSubscriptionRequest.XML, CreateSubscriptionRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CreateSubscriptionRequest obj = (CreateSubscriptionRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putDouble(null, null /*obj.getRequestedPublishingInterval()*/);
    				calculator.putUInt32(null, null /*obj.getRequestedLifetimeCount()*/);
    				calculator.putUInt32(null, null /*obj.getRequestedMaxKeepAliveCount()*/);
    				calculator.putUInt32(null, null /*obj.getMaxNotificationsPerPublish()*/);
    				calculator.putBoolean(null, null /*obj.getPublishingEnabled()*/);
    				calculator.putByte(null, null /*obj.getPriority()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CreateSubscriptionRequest obj = (CreateSubscriptionRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putDouble("RequestedPublishingInterval",  (obj==null)?null:obj.getRequestedPublishingInterval() );
    				encoder.putUInt32("RequestedLifetimeCount",  (obj==null)?null:obj.getRequestedLifetimeCount() );
    				encoder.putUInt32("RequestedMaxKeepAliveCount",  (obj==null)?null:obj.getRequestedMaxKeepAliveCount() );
    				encoder.putUInt32("MaxNotificationsPerPublish",  (obj==null)?null:obj.getMaxNotificationsPerPublish() );
    				encoder.putBoolean("PublishingEnabled",  (obj==null)?null:obj.getPublishingEnabled() );
    				encoder.putByte("Priority",  (obj==null)?null:obj.getPriority() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CreateSubscriptionRequest result = new CreateSubscriptionRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setRequestedPublishingInterval( decoder.getDouble("RequestedPublishingInterval") );
    				result.setRequestedLifetimeCount( decoder.getUInt32("RequestedLifetimeCount") );
    				result.setRequestedMaxKeepAliveCount( decoder.getUInt32("RequestedMaxKeepAliveCount") );
    				result.setMaxNotificationsPerPublish( decoder.getUInt32("MaxNotificationsPerPublish") );
    				result.setPublishingEnabled( decoder.getBoolean("PublishingEnabled") );
    				result.setPriority( decoder.getByte("Priority") );
    				return result;
    			}
    		});
    
    	// CreateSubscriptionResponse
    	addSerializer(
    		new AbstractSerializer(CreateSubscriptionResponse.class, CreateSubscriptionResponse.BINARY, CreateSubscriptionResponse.XML, CreateSubscriptionResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				CreateSubscriptionResponse obj = (CreateSubscriptionResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putDouble(null, null /*obj.getRevisedPublishingInterval()*/);
    				calculator.putUInt32(null, null /*obj.getRevisedLifetimeCount()*/);
    				calculator.putUInt32(null, null /*obj.getRevisedMaxKeepAliveCount()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				CreateSubscriptionResponse obj = (CreateSubscriptionResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putDouble("RevisedPublishingInterval",  (obj==null)?null:obj.getRevisedPublishingInterval() );
    				encoder.putUInt32("RevisedLifetimeCount",  (obj==null)?null:obj.getRevisedLifetimeCount() );
    				encoder.putUInt32("RevisedMaxKeepAliveCount",  (obj==null)?null:obj.getRevisedMaxKeepAliveCount() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				CreateSubscriptionResponse result = new CreateSubscriptionResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setRevisedPublishingInterval( decoder.getDouble("RevisedPublishingInterval") );
    				result.setRevisedLifetimeCount( decoder.getUInt32("RevisedLifetimeCount") );
    				result.setRevisedMaxKeepAliveCount( decoder.getUInt32("RevisedMaxKeepAliveCount") );
    				return result;
    			}
    		});
    
    	// ModifySubscriptionRequest
    	addSerializer(
    		new AbstractSerializer(ModifySubscriptionRequest.class, ModifySubscriptionRequest.BINARY, ModifySubscriptionRequest.XML, ModifySubscriptionRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ModifySubscriptionRequest obj = (ModifySubscriptionRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putDouble(null, null /*obj.getRequestedPublishingInterval()*/);
    				calculator.putUInt32(null, null /*obj.getRequestedLifetimeCount()*/);
    				calculator.putUInt32(null, null /*obj.getRequestedMaxKeepAliveCount()*/);
    				calculator.putUInt32(null, null /*obj.getMaxNotificationsPerPublish()*/);
    				calculator.putByte(null, null /*obj.getPriority()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ModifySubscriptionRequest obj = (ModifySubscriptionRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putDouble("RequestedPublishingInterval",  (obj==null)?null:obj.getRequestedPublishingInterval() );
    				encoder.putUInt32("RequestedLifetimeCount",  (obj==null)?null:obj.getRequestedLifetimeCount() );
    				encoder.putUInt32("RequestedMaxKeepAliveCount",  (obj==null)?null:obj.getRequestedMaxKeepAliveCount() );
    				encoder.putUInt32("MaxNotificationsPerPublish",  (obj==null)?null:obj.getMaxNotificationsPerPublish() );
    				encoder.putByte("Priority",  (obj==null)?null:obj.getPriority() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ModifySubscriptionRequest result = new ModifySubscriptionRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setRequestedPublishingInterval( decoder.getDouble("RequestedPublishingInterval") );
    				result.setRequestedLifetimeCount( decoder.getUInt32("RequestedLifetimeCount") );
    				result.setRequestedMaxKeepAliveCount( decoder.getUInt32("RequestedMaxKeepAliveCount") );
    				result.setMaxNotificationsPerPublish( decoder.getUInt32("MaxNotificationsPerPublish") );
    				result.setPriority( decoder.getByte("Priority") );
    				return result;
    			}
    		});
    
    	// ModifySubscriptionResponse
    	addSerializer(
    		new AbstractSerializer(ModifySubscriptionResponse.class, ModifySubscriptionResponse.BINARY, ModifySubscriptionResponse.XML, ModifySubscriptionResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ModifySubscriptionResponse obj = (ModifySubscriptionResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putDouble(null, null /*obj.getRevisedPublishingInterval()*/);
    				calculator.putUInt32(null, null /*obj.getRevisedLifetimeCount()*/);
    				calculator.putUInt32(null, null /*obj.getRevisedMaxKeepAliveCount()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ModifySubscriptionResponse obj = (ModifySubscriptionResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putDouble("RevisedPublishingInterval",  (obj==null)?null:obj.getRevisedPublishingInterval() );
    				encoder.putUInt32("RevisedLifetimeCount",  (obj==null)?null:obj.getRevisedLifetimeCount() );
    				encoder.putUInt32("RevisedMaxKeepAliveCount",  (obj==null)?null:obj.getRevisedMaxKeepAliveCount() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ModifySubscriptionResponse result = new ModifySubscriptionResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setRevisedPublishingInterval( decoder.getDouble("RevisedPublishingInterval") );
    				result.setRevisedLifetimeCount( decoder.getUInt32("RevisedLifetimeCount") );
    				result.setRevisedMaxKeepAliveCount( decoder.getUInt32("RevisedMaxKeepAliveCount") );
    				return result;
    			}
    		});
    
    	// SetPublishingModeRequest
    	addSerializer(
    		new AbstractSerializer(SetPublishingModeRequest.class, SetPublishingModeRequest.BINARY, SetPublishingModeRequest.XML, SetPublishingModeRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SetPublishingModeRequest obj = (SetPublishingModeRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putBoolean(null, null /*obj.getPublishingEnabled()*/);
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getSubscriptionIds()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SetPublishingModeRequest obj = (SetPublishingModeRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putBoolean("PublishingEnabled",  (obj==null)?null:obj.getPublishingEnabled() );
    				encoder.putUInt32Array("SubscriptionIds", (obj==null)?null:obj.getSubscriptionIds() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SetPublishingModeRequest result = new SetPublishingModeRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setPublishingEnabled( decoder.getBoolean("PublishingEnabled") );
    				result.setSubscriptionIds( decoder.getUInt32Array("SubscriptionIds") );
    				return result;
    			}
    		});
    
    	// SetPublishingModeResponse
    	addSerializer(
    		new AbstractSerializer(SetPublishingModeResponse.class, SetPublishingModeResponse.BINARY, SetPublishingModeResponse.XML, SetPublishingModeResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SetPublishingModeResponse obj = (SetPublishingModeResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SetPublishingModeResponse obj = (SetPublishingModeResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putStatusCodeArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SetPublishingModeResponse result = new SetPublishingModeResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getStatusCodeArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// NotificationMessage
    	addSerializer(
    		new AbstractSerializer(NotificationMessage.class, NotificationMessage.BINARY, NotificationMessage.XML, NotificationMessage.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				NotificationMessage obj = (NotificationMessage) encodeable;
    				calculator.putUInt32(null, null /*obj.getSequenceNumber()*/);
    				calculator.putDateTime(null,  (obj==null)?null:obj.getPublishTime() );
    				calculator.putExtensionObjectArray(null, ((obj==null)?null:obj.getNotificationData()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				NotificationMessage obj = (NotificationMessage) encodeable;
    				encoder.putUInt32("SequenceNumber",  (obj==null)?null:obj.getSequenceNumber() );
    				encoder.putDateTime("PublishTime",  (obj==null)?null:obj.getPublishTime() );
    				encoder.putExtensionObjectArray("NotificationData", (obj==null)?null:obj.getNotificationData() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				NotificationMessage result = new NotificationMessage();
    				result.setSequenceNumber( decoder.getUInt32("SequenceNumber") );
    				result.setPublishTime( decoder.getDateTime("PublishTime") );
    				result.setNotificationData( decoder.getExtensionObjectArray("NotificationData") );
    				return result;
    			}
    		});
    
    	// NotificationData
    	addSerializer(
    		new AbstractSerializer(NotificationData.class, NotificationData.BINARY, NotificationData.XML, NotificationData.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				NotificationData result = new NotificationData();
    				return result;
    			}
    		});
    
    	// DataChangeNotification
    	addSerializer(
    		new AbstractSerializer(DataChangeNotification.class, DataChangeNotification.BINARY, DataChangeNotification.XML, DataChangeNotification.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DataChangeNotification obj = (DataChangeNotification) encodeable;
    				calculator.putEncodeableArray(null, MonitoredItemNotification.class, (obj==null)?null:obj.getMonitoredItems());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DataChangeNotification obj = (DataChangeNotification) encodeable;
    				encoder.putEncodeableArray("MonitoredItems", MonitoredItemNotification.class, (obj==null)?null:obj.getMonitoredItems());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DataChangeNotification result = new DataChangeNotification();
    				result.setMonitoredItems( decoder.getEncodeableArray("MonitoredItems", MonitoredItemNotification.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// MonitoredItemNotification
    	addSerializer(
    		new AbstractSerializer(MonitoredItemNotification.class, MonitoredItemNotification.BINARY, MonitoredItemNotification.XML, MonitoredItemNotification.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				MonitoredItemNotification obj = (MonitoredItemNotification) encodeable;
    				calculator.putUInt32(null, null /*obj.getClientHandle()*/);
    				calculator.putDataValue(null,  (obj==null)?null:obj.getValue() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				MonitoredItemNotification obj = (MonitoredItemNotification) encodeable;
    				encoder.putUInt32("ClientHandle",  (obj==null)?null:obj.getClientHandle() );
    				encoder.putDataValue("Value",  (obj==null)?null:obj.getValue() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				MonitoredItemNotification result = new MonitoredItemNotification();
    				result.setClientHandle( decoder.getUInt32("ClientHandle") );
    				result.setValue( decoder.getDataValue("Value") );
    				return result;
    			}
    		});
    
    	// EventNotificationList
    	addSerializer(
    		new AbstractSerializer(EventNotificationList.class, EventNotificationList.BINARY, EventNotificationList.XML, EventNotificationList.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				EventNotificationList obj = (EventNotificationList) encodeable;
    				calculator.putEncodeableArray(null, EventFieldList.class, (obj==null)?null:obj.getEvents());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				EventNotificationList obj = (EventNotificationList) encodeable;
    				encoder.putEncodeableArray("Events", EventFieldList.class, (obj==null)?null:obj.getEvents());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				EventNotificationList result = new EventNotificationList();
    				result.setEvents( decoder.getEncodeableArray("Events", EventFieldList.class) );
    				return result;
    			}
    		});
    
    	// EventFieldList
    	addSerializer(
    		new AbstractSerializer(EventFieldList.class, EventFieldList.BINARY, EventFieldList.XML, EventFieldList.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				EventFieldList obj = (EventFieldList) encodeable;
    				calculator.putUInt32(null, null /*obj.getClientHandle()*/);
    				calculator.putVariantArray(null, ((obj==null)?null:obj.getEventFields()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				EventFieldList obj = (EventFieldList) encodeable;
    				encoder.putUInt32("ClientHandle",  (obj==null)?null:obj.getClientHandle() );
    				encoder.putVariantArray("EventFields", (obj==null)?null:obj.getEventFields() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				EventFieldList result = new EventFieldList();
    				result.setClientHandle( decoder.getUInt32("ClientHandle") );
    				result.setEventFields( decoder.getVariantArray("EventFields") );
    				return result;
    			}
    		});
    
    	// HistoryEventFieldList
    	addSerializer(
    		new AbstractSerializer(HistoryEventFieldList.class, HistoryEventFieldList.BINARY, HistoryEventFieldList.XML, HistoryEventFieldList.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				HistoryEventFieldList obj = (HistoryEventFieldList) encodeable;
    				calculator.putVariantArray(null, ((obj==null)?null:obj.getEventFields()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				HistoryEventFieldList obj = (HistoryEventFieldList) encodeable;
    				encoder.putVariantArray("EventFields", (obj==null)?null:obj.getEventFields() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				HistoryEventFieldList result = new HistoryEventFieldList();
    				result.setEventFields( decoder.getVariantArray("EventFields") );
    				return result;
    			}
    		});
    
    	// StatusChangeNotification
    	addSerializer(
    		new AbstractSerializer(StatusChangeNotification.class, StatusChangeNotification.BINARY, StatusChangeNotification.XML, StatusChangeNotification.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				StatusChangeNotification obj = (StatusChangeNotification) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatus() );
    				calculator.putDiagnosticInfo(null,  (obj==null)?null:obj.getDiagnosticInfo() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				StatusChangeNotification obj = (StatusChangeNotification) encodeable;
    				encoder.putStatusCode("Status",  (obj==null)?null:obj.getStatus() );
    				encoder.putDiagnosticInfo("DiagnosticInfo",  (obj==null)?null:obj.getDiagnosticInfo() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				StatusChangeNotification result = new StatusChangeNotification();
    				result.setStatus( decoder.getStatusCode("Status") );
    				result.setDiagnosticInfo( decoder.getDiagnosticInfo("DiagnosticInfo") );
    				return result;
    			}
    		});
    
    	// SubscriptionAcknowledgement
    	addSerializer(
    		new AbstractSerializer(SubscriptionAcknowledgement.class, SubscriptionAcknowledgement.BINARY, SubscriptionAcknowledgement.XML, SubscriptionAcknowledgement.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SubscriptionAcknowledgement obj = (SubscriptionAcknowledgement) encodeable;
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putUInt32(null, null /*obj.getSequenceNumber()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SubscriptionAcknowledgement obj = (SubscriptionAcknowledgement) encodeable;
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putUInt32("SequenceNumber",  (obj==null)?null:obj.getSequenceNumber() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SubscriptionAcknowledgement result = new SubscriptionAcknowledgement();
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setSequenceNumber( decoder.getUInt32("SequenceNumber") );
    				return result;
    			}
    		});
    
    	// PublishRequest
    	addSerializer(
    		new AbstractSerializer(PublishRequest.class, PublishRequest.BINARY, PublishRequest.XML, PublishRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				PublishRequest obj = (PublishRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putEncodeableArray(null, SubscriptionAcknowledgement.class, (obj==null)?null:obj.getSubscriptionAcknowledgements());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				PublishRequest obj = (PublishRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putEncodeableArray("SubscriptionAcknowledgements", SubscriptionAcknowledgement.class, (obj==null)?null:obj.getSubscriptionAcknowledgements());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				PublishRequest result = new PublishRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setSubscriptionAcknowledgements( decoder.getEncodeableArray("SubscriptionAcknowledgements", SubscriptionAcknowledgement.class) );
    				return result;
    			}
    		});
    
    	// PublishResponse
    	addSerializer(
    		new AbstractSerializer(PublishResponse.class, PublishResponse.BINARY, PublishResponse.XML, PublishResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				PublishResponse obj = (PublishResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getAvailableSequenceNumbers()) );
    				calculator.putBoolean(null, null /*obj.getMoreNotifications()*/);
    				calculator.putEncodeable(null, NotificationMessage.class, (obj==null)?null:obj.getNotificationMessage());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				PublishResponse obj = (PublishResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putUInt32Array("AvailableSequenceNumbers", (obj==null)?null:obj.getAvailableSequenceNumbers() );
    				encoder.putBoolean("MoreNotifications",  (obj==null)?null:obj.getMoreNotifications() );
    				encoder.putEncodeable("NotificationMessage", NotificationMessage.class, (obj==null)?null:obj.getNotificationMessage());
    				encoder.putStatusCodeArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				PublishResponse result = new PublishResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setAvailableSequenceNumbers( decoder.getUInt32Array("AvailableSequenceNumbers") );
    				result.setMoreNotifications( decoder.getBoolean("MoreNotifications") );
    				result.setNotificationMessage( decoder.getEncodeable("NotificationMessage", NotificationMessage.class) );
    				result.setResults( decoder.getStatusCodeArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// RepublishRequest
    	addSerializer(
    		new AbstractSerializer(RepublishRequest.class, RepublishRequest.BINARY, RepublishRequest.XML, RepublishRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RepublishRequest obj = (RepublishRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putUInt32(null, null /*obj.getRetransmitSequenceNumber()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RepublishRequest obj = (RepublishRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putUInt32("RetransmitSequenceNumber",  (obj==null)?null:obj.getRetransmitSequenceNumber() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RepublishRequest result = new RepublishRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setRetransmitSequenceNumber( decoder.getUInt32("RetransmitSequenceNumber") );
    				return result;
    			}
    		});
    
    	// RepublishResponse
    	addSerializer(
    		new AbstractSerializer(RepublishResponse.class, RepublishResponse.BINARY, RepublishResponse.XML, RepublishResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RepublishResponse obj = (RepublishResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeable(null, NotificationMessage.class, (obj==null)?null:obj.getNotificationMessage());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RepublishResponse obj = (RepublishResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeable("NotificationMessage", NotificationMessage.class, (obj==null)?null:obj.getNotificationMessage());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RepublishResponse result = new RepublishResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setNotificationMessage( decoder.getEncodeable("NotificationMessage", NotificationMessage.class) );
    				return result;
    			}
    		});
    
    	// TransferResult
    	addSerializer(
    		new AbstractSerializer(TransferResult.class, TransferResult.BINARY, TransferResult.XML, TransferResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				TransferResult obj = (TransferResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getAvailableSequenceNumbers()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				TransferResult obj = (TransferResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putUInt32Array("AvailableSequenceNumbers", (obj==null)?null:obj.getAvailableSequenceNumbers() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				TransferResult result = new TransferResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setAvailableSequenceNumbers( decoder.getUInt32Array("AvailableSequenceNumbers") );
    				return result;
    			}
    		});
    
    	// TransferSubscriptionsRequest
    	addSerializer(
    		new AbstractSerializer(TransferSubscriptionsRequest.class, TransferSubscriptionsRequest.BINARY, TransferSubscriptionsRequest.XML, TransferSubscriptionsRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				TransferSubscriptionsRequest obj = (TransferSubscriptionsRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getSubscriptionIds()) );
    				calculator.putBoolean(null, null /*obj.getSendInitialValues()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				TransferSubscriptionsRequest obj = (TransferSubscriptionsRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32Array("SubscriptionIds", (obj==null)?null:obj.getSubscriptionIds() );
    				encoder.putBoolean("SendInitialValues",  (obj==null)?null:obj.getSendInitialValues() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				TransferSubscriptionsRequest result = new TransferSubscriptionsRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setSubscriptionIds( decoder.getUInt32Array("SubscriptionIds") );
    				result.setSendInitialValues( decoder.getBoolean("SendInitialValues") );
    				return result;
    			}
    		});
    
    	// TransferSubscriptionsResponse
    	addSerializer(
    		new AbstractSerializer(TransferSubscriptionsResponse.class, TransferSubscriptionsResponse.BINARY, TransferSubscriptionsResponse.XML, TransferSubscriptionsResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				TransferSubscriptionsResponse obj = (TransferSubscriptionsResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putEncodeableArray(null, TransferResult.class, (obj==null)?null:obj.getResults());
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				TransferSubscriptionsResponse obj = (TransferSubscriptionsResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putEncodeableArray("Results", TransferResult.class, (obj==null)?null:obj.getResults());
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				TransferSubscriptionsResponse result = new TransferSubscriptionsResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getEncodeableArray("Results", TransferResult.class) );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// DeleteSubscriptionsRequest
    	addSerializer(
    		new AbstractSerializer(DeleteSubscriptionsRequest.class, DeleteSubscriptionsRequest.BINARY, DeleteSubscriptionsRequest.XML, DeleteSubscriptionsRequest.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteSubscriptionsRequest obj = (DeleteSubscriptionsRequest) encodeable;
    				calculator.putEncodeable(null, RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				calculator.putUInt32Array(null, ((obj==null)?null:obj.getSubscriptionIds()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteSubscriptionsRequest obj = (DeleteSubscriptionsRequest) encodeable;
    				encoder.putEncodeable("RequestHeader", RequestHeader.class, (obj==null)?null:obj.getRequestHeader());
    				encoder.putUInt32Array("SubscriptionIds", (obj==null)?null:obj.getSubscriptionIds() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteSubscriptionsRequest result = new DeleteSubscriptionsRequest();
    				result.setRequestHeader( decoder.getEncodeable("RequestHeader", RequestHeader.class) );
    				result.setSubscriptionIds( decoder.getUInt32Array("SubscriptionIds") );
    				return result;
    			}
    		});
    
    	// DeleteSubscriptionsResponse
    	addSerializer(
    		new AbstractSerializer(DeleteSubscriptionsResponse.class, DeleteSubscriptionsResponse.BINARY, DeleteSubscriptionsResponse.XML, DeleteSubscriptionsResponse.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DeleteSubscriptionsResponse obj = (DeleteSubscriptionsResponse) encodeable;
    				calculator.putEncodeable(null, ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				calculator.putStatusCodeArray(null, ((obj==null)?null:obj.getResults()) );
    				calculator.putDiagnosticInfoArray(null, ((obj==null)?null:obj.getDiagnosticInfos()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DeleteSubscriptionsResponse obj = (DeleteSubscriptionsResponse) encodeable;
    				encoder.putEncodeable("ResponseHeader", ResponseHeader.class, (obj==null)?null:obj.getResponseHeader());
    				encoder.putStatusCodeArray("Results", (obj==null)?null:obj.getResults() );
    				encoder.putDiagnosticInfoArray("DiagnosticInfos", (obj==null)?null:obj.getDiagnosticInfos() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DeleteSubscriptionsResponse result = new DeleteSubscriptionsResponse();
    				result.setResponseHeader( decoder.getEncodeable("ResponseHeader", ResponseHeader.class) );
    				result.setResults( decoder.getStatusCodeArray("Results") );
    				result.setDiagnosticInfos( decoder.getDiagnosticInfoArray("DiagnosticInfos") );
    				return result;
    			}
    		});
    
    	// BuildInfo
    	addSerializer(
    		new AbstractSerializer(BuildInfo.class, BuildInfo.BINARY, BuildInfo.XML, BuildInfo.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				BuildInfo obj = (BuildInfo) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getProductUri() );
    				calculator.putString(null,  (obj==null)?null:obj.getManufacturerName() );
    				calculator.putString(null,  (obj==null)?null:obj.getProductName() );
    				calculator.putString(null,  (obj==null)?null:obj.getSoftwareVersion() );
    				calculator.putString(null,  (obj==null)?null:obj.getBuildNumber() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getBuildDate() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				BuildInfo obj = (BuildInfo) encodeable;
    				encoder.putString("ProductUri",  (obj==null)?null:obj.getProductUri() );
    				encoder.putString("ManufacturerName",  (obj==null)?null:obj.getManufacturerName() );
    				encoder.putString("ProductName",  (obj==null)?null:obj.getProductName() );
    				encoder.putString("SoftwareVersion",  (obj==null)?null:obj.getSoftwareVersion() );
    				encoder.putString("BuildNumber",  (obj==null)?null:obj.getBuildNumber() );
    				encoder.putDateTime("BuildDate",  (obj==null)?null:obj.getBuildDate() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				BuildInfo result = new BuildInfo();
    				result.setProductUri( decoder.getString("ProductUri") );
    				result.setManufacturerName( decoder.getString("ManufacturerName") );
    				result.setProductName( decoder.getString("ProductName") );
    				result.setSoftwareVersion( decoder.getString("SoftwareVersion") );
    				result.setBuildNumber( decoder.getString("BuildNumber") );
    				result.setBuildDate( decoder.getDateTime("BuildDate") );
    				return result;
    			}
    		});
    
    	// RedundantServerDataType
    	addSerializer(
    		new AbstractSerializer(RedundantServerDataType.class, RedundantServerDataType.BINARY, RedundantServerDataType.XML, RedundantServerDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				RedundantServerDataType obj = (RedundantServerDataType) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getServerId() );
    				calculator.putByte(null, null /*obj.getServiceLevel()*/);
    				calculator.putEnumeration(null, null /*obj.getServerState()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				RedundantServerDataType obj = (RedundantServerDataType) encodeable;
    				encoder.putString("ServerId",  (obj==null)?null:obj.getServerId() );
    				encoder.putByte("ServiceLevel",  (obj==null)?null:obj.getServiceLevel() );
    				encoder.putEnumeration("ServerState",  (obj==null)?null:obj.getServerState() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				RedundantServerDataType result = new RedundantServerDataType();
    				result.setServerId( decoder.getString("ServerId") );
    				result.setServiceLevel( decoder.getByte("ServiceLevel") );
    				result.setServerState( decoder.getEnumeration("ServerState", ServerState.class) );
    				return result;
    			}
    		});
    
    	// EndpointUrlListDataType
    	addSerializer(
    		new AbstractSerializer(EndpointUrlListDataType.class, EndpointUrlListDataType.BINARY, EndpointUrlListDataType.XML, EndpointUrlListDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				EndpointUrlListDataType obj = (EndpointUrlListDataType) encodeable;
    				calculator.putStringArray(null, ((obj==null)?null:obj.getEndpointUrlList()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				EndpointUrlListDataType obj = (EndpointUrlListDataType) encodeable;
    				encoder.putStringArray("EndpointUrlList", (obj==null)?null:obj.getEndpointUrlList() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				EndpointUrlListDataType result = new EndpointUrlListDataType();
    				result.setEndpointUrlList( decoder.getStringArray("EndpointUrlList") );
    				return result;
    			}
    		});
    
    	// NetworkGroupDataType
    	addSerializer(
    		new AbstractSerializer(NetworkGroupDataType.class, NetworkGroupDataType.BINARY, NetworkGroupDataType.XML, NetworkGroupDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				NetworkGroupDataType obj = (NetworkGroupDataType) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getServerUri() );
    				calculator.putEncodeableArray(null, EndpointUrlListDataType.class, (obj==null)?null:obj.getNetworkPaths());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				NetworkGroupDataType obj = (NetworkGroupDataType) encodeable;
    				encoder.putString("ServerUri",  (obj==null)?null:obj.getServerUri() );
    				encoder.putEncodeableArray("NetworkPaths", EndpointUrlListDataType.class, (obj==null)?null:obj.getNetworkPaths());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				NetworkGroupDataType result = new NetworkGroupDataType();
    				result.setServerUri( decoder.getString("ServerUri") );
    				result.setNetworkPaths( decoder.getEncodeableArray("NetworkPaths", EndpointUrlListDataType.class) );
    				return result;
    			}
    		});
    
    	// SamplingIntervalDiagnosticsDataType
    	addSerializer(
    		new AbstractSerializer(SamplingIntervalDiagnosticsDataType.class, SamplingIntervalDiagnosticsDataType.BINARY, SamplingIntervalDiagnosticsDataType.XML, SamplingIntervalDiagnosticsDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SamplingIntervalDiagnosticsDataType obj = (SamplingIntervalDiagnosticsDataType) encodeable;
    				calculator.putDouble(null, null /*obj.getSamplingInterval()*/);
    				calculator.putUInt32(null, null /*obj.getMonitoredItemCount()*/);
    				calculator.putUInt32(null, null /*obj.getMaxMonitoredItemCount()*/);
    				calculator.putUInt32(null, null /*obj.getDisabledMonitoredItemCount()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SamplingIntervalDiagnosticsDataType obj = (SamplingIntervalDiagnosticsDataType) encodeable;
    				encoder.putDouble("SamplingInterval",  (obj==null)?null:obj.getSamplingInterval() );
    				encoder.putUInt32("MonitoredItemCount",  (obj==null)?null:obj.getMonitoredItemCount() );
    				encoder.putUInt32("MaxMonitoredItemCount",  (obj==null)?null:obj.getMaxMonitoredItemCount() );
    				encoder.putUInt32("DisabledMonitoredItemCount",  (obj==null)?null:obj.getDisabledMonitoredItemCount() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SamplingIntervalDiagnosticsDataType result = new SamplingIntervalDiagnosticsDataType();
    				result.setSamplingInterval( decoder.getDouble("SamplingInterval") );
    				result.setMonitoredItemCount( decoder.getUInt32("MonitoredItemCount") );
    				result.setMaxMonitoredItemCount( decoder.getUInt32("MaxMonitoredItemCount") );
    				result.setDisabledMonitoredItemCount( decoder.getUInt32("DisabledMonitoredItemCount") );
    				return result;
    			}
    		});
    
    	// ServerDiagnosticsSummaryDataType
    	addSerializer(
    		new AbstractSerializer(ServerDiagnosticsSummaryDataType.class, ServerDiagnosticsSummaryDataType.BINARY, ServerDiagnosticsSummaryDataType.XML, ServerDiagnosticsSummaryDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ServerDiagnosticsSummaryDataType obj = (ServerDiagnosticsSummaryDataType) encodeable;
    				calculator.putUInt32(null, null /*obj.getServerViewCount()*/);
    				calculator.putUInt32(null, null /*obj.getCurrentSessionCount()*/);
    				calculator.putUInt32(null, null /*obj.getCumulatedSessionCount()*/);
    				calculator.putUInt32(null, null /*obj.getSecurityRejectedSessionCount()*/);
    				calculator.putUInt32(null, null /*obj.getRejectedSessionCount()*/);
    				calculator.putUInt32(null, null /*obj.getSessionTimeoutCount()*/);
    				calculator.putUInt32(null, null /*obj.getSessionAbortCount()*/);
    				calculator.putUInt32(null, null /*obj.getCurrentSubscriptionCount()*/);
    				calculator.putUInt32(null, null /*obj.getCumulatedSubscriptionCount()*/);
    				calculator.putUInt32(null, null /*obj.getPublishingIntervalCount()*/);
    				calculator.putUInt32(null, null /*obj.getSecurityRejectedRequestsCount()*/);
    				calculator.putUInt32(null, null /*obj.getRejectedRequestsCount()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ServerDiagnosticsSummaryDataType obj = (ServerDiagnosticsSummaryDataType) encodeable;
    				encoder.putUInt32("ServerViewCount",  (obj==null)?null:obj.getServerViewCount() );
    				encoder.putUInt32("CurrentSessionCount",  (obj==null)?null:obj.getCurrentSessionCount() );
    				encoder.putUInt32("CumulatedSessionCount",  (obj==null)?null:obj.getCumulatedSessionCount() );
    				encoder.putUInt32("SecurityRejectedSessionCount",  (obj==null)?null:obj.getSecurityRejectedSessionCount() );
    				encoder.putUInt32("RejectedSessionCount",  (obj==null)?null:obj.getRejectedSessionCount() );
    				encoder.putUInt32("SessionTimeoutCount",  (obj==null)?null:obj.getSessionTimeoutCount() );
    				encoder.putUInt32("SessionAbortCount",  (obj==null)?null:obj.getSessionAbortCount() );
    				encoder.putUInt32("CurrentSubscriptionCount",  (obj==null)?null:obj.getCurrentSubscriptionCount() );
    				encoder.putUInt32("CumulatedSubscriptionCount",  (obj==null)?null:obj.getCumulatedSubscriptionCount() );
    				encoder.putUInt32("PublishingIntervalCount",  (obj==null)?null:obj.getPublishingIntervalCount() );
    				encoder.putUInt32("SecurityRejectedRequestsCount",  (obj==null)?null:obj.getSecurityRejectedRequestsCount() );
    				encoder.putUInt32("RejectedRequestsCount",  (obj==null)?null:obj.getRejectedRequestsCount() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ServerDiagnosticsSummaryDataType result = new ServerDiagnosticsSummaryDataType();
    				result.setServerViewCount( decoder.getUInt32("ServerViewCount") );
    				result.setCurrentSessionCount( decoder.getUInt32("CurrentSessionCount") );
    				result.setCumulatedSessionCount( decoder.getUInt32("CumulatedSessionCount") );
    				result.setSecurityRejectedSessionCount( decoder.getUInt32("SecurityRejectedSessionCount") );
    				result.setRejectedSessionCount( decoder.getUInt32("RejectedSessionCount") );
    				result.setSessionTimeoutCount( decoder.getUInt32("SessionTimeoutCount") );
    				result.setSessionAbortCount( decoder.getUInt32("SessionAbortCount") );
    				result.setCurrentSubscriptionCount( decoder.getUInt32("CurrentSubscriptionCount") );
    				result.setCumulatedSubscriptionCount( decoder.getUInt32("CumulatedSubscriptionCount") );
    				result.setPublishingIntervalCount( decoder.getUInt32("PublishingIntervalCount") );
    				result.setSecurityRejectedRequestsCount( decoder.getUInt32("SecurityRejectedRequestsCount") );
    				result.setRejectedRequestsCount( decoder.getUInt32("RejectedRequestsCount") );
    				return result;
    			}
    		});
    
    	// ServerStatusDataType
    	addSerializer(
    		new AbstractSerializer(ServerStatusDataType.class, ServerStatusDataType.BINARY, ServerStatusDataType.XML, ServerStatusDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ServerStatusDataType obj = (ServerStatusDataType) encodeable;
    				calculator.putDateTime(null,  (obj==null)?null:obj.getStartTime() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getCurrentTime() );
    				calculator.putEnumeration(null, null /*obj.getState()*/);
    				calculator.putEncodeable(null, BuildInfo.class, (obj==null)?null:obj.getBuildInfo());
    				calculator.putUInt32(null, null /*obj.getSecondsTillShutdown()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getShutdownReason() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ServerStatusDataType obj = (ServerStatusDataType) encodeable;
    				encoder.putDateTime("StartTime",  (obj==null)?null:obj.getStartTime() );
    				encoder.putDateTime("CurrentTime",  (obj==null)?null:obj.getCurrentTime() );
    				encoder.putEnumeration("State",  (obj==null)?null:obj.getState() );
    				encoder.putEncodeable("BuildInfo", BuildInfo.class, (obj==null)?null:obj.getBuildInfo());
    				encoder.putUInt32("SecondsTillShutdown",  (obj==null)?null:obj.getSecondsTillShutdown() );
    				encoder.putLocalizedText("ShutdownReason",  (obj==null)?null:obj.getShutdownReason() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ServerStatusDataType result = new ServerStatusDataType();
    				result.setStartTime( decoder.getDateTime("StartTime") );
    				result.setCurrentTime( decoder.getDateTime("CurrentTime") );
    				result.setState( decoder.getEnumeration("State", ServerState.class) );
    				result.setBuildInfo( decoder.getEncodeable("BuildInfo", BuildInfo.class) );
    				result.setSecondsTillShutdown( decoder.getUInt32("SecondsTillShutdown") );
    				result.setShutdownReason( decoder.getLocalizedText("ShutdownReason") );
    				return result;
    			}
    		});
    
    	// SessionDiagnosticsDataType
    	addSerializer(
    		new AbstractSerializer(SessionDiagnosticsDataType.class, SessionDiagnosticsDataType.BINARY, SessionDiagnosticsDataType.XML, SessionDiagnosticsDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SessionDiagnosticsDataType obj = (SessionDiagnosticsDataType) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getSessionId() );
    				calculator.putString(null,  (obj==null)?null:obj.getSessionName() );
    				calculator.putEncodeable(null, ApplicationDescription.class, (obj==null)?null:obj.getClientDescription());
    				calculator.putString(null,  (obj==null)?null:obj.getServerUri() );
    				calculator.putString(null,  (obj==null)?null:obj.getEndpointUrl() );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getLocaleIds()) );
    				calculator.putDouble(null, null /*obj.getActualSessionTimeout()*/);
    				calculator.putUInt32(null, null /*obj.getMaxResponseMessageSize()*/);
    				calculator.putDateTime(null,  (obj==null)?null:obj.getClientConnectionTime() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getClientLastContactTime() );
    				calculator.putUInt32(null, null /*obj.getCurrentSubscriptionsCount()*/);
    				calculator.putUInt32(null, null /*obj.getCurrentMonitoredItemsCount()*/);
    				calculator.putUInt32(null, null /*obj.getCurrentPublishRequestsInQueue()*/);
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getTotalRequestCount());
    				calculator.putUInt32(null, null /*obj.getUnauthorizedRequestCount()*/);
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getReadCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getHistoryReadCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getWriteCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getHistoryUpdateCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getCallCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getCreateMonitoredItemsCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getModifyMonitoredItemsCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getSetMonitoringModeCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getSetTriggeringCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getDeleteMonitoredItemsCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getCreateSubscriptionCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getModifySubscriptionCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getSetPublishingModeCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getPublishCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getRepublishCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getTransferSubscriptionsCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getDeleteSubscriptionsCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getAddNodesCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getAddReferencesCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getDeleteNodesCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getDeleteReferencesCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getBrowseCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getBrowseNextCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getTranslateBrowsePathsToNodeIdsCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getQueryFirstCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getQueryNextCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getRegisterNodesCount());
    				calculator.putEncodeable(null, ServiceCounterDataType.class, (obj==null)?null:obj.getUnregisterNodesCount());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SessionDiagnosticsDataType obj = (SessionDiagnosticsDataType) encodeable;
    				encoder.putNodeId("SessionId",  (obj==null)?null:obj.getSessionId() );
    				encoder.putString("SessionName",  (obj==null)?null:obj.getSessionName() );
    				encoder.putEncodeable("ClientDescription", ApplicationDescription.class, (obj==null)?null:obj.getClientDescription());
    				encoder.putString("ServerUri",  (obj==null)?null:obj.getServerUri() );
    				encoder.putString("EndpointUrl",  (obj==null)?null:obj.getEndpointUrl() );
    				encoder.putStringArray("LocaleIds", (obj==null)?null:obj.getLocaleIds() );
    				encoder.putDouble("ActualSessionTimeout",  (obj==null)?null:obj.getActualSessionTimeout() );
    				encoder.putUInt32("MaxResponseMessageSize",  (obj==null)?null:obj.getMaxResponseMessageSize() );
    				encoder.putDateTime("ClientConnectionTime",  (obj==null)?null:obj.getClientConnectionTime() );
    				encoder.putDateTime("ClientLastContactTime",  (obj==null)?null:obj.getClientLastContactTime() );
    				encoder.putUInt32("CurrentSubscriptionsCount",  (obj==null)?null:obj.getCurrentSubscriptionsCount() );
    				encoder.putUInt32("CurrentMonitoredItemsCount",  (obj==null)?null:obj.getCurrentMonitoredItemsCount() );
    				encoder.putUInt32("CurrentPublishRequestsInQueue",  (obj==null)?null:obj.getCurrentPublishRequestsInQueue() );
    				encoder.putEncodeable("TotalRequestCount", ServiceCounterDataType.class, (obj==null)?null:obj.getTotalRequestCount());
    				encoder.putUInt32("UnauthorizedRequestCount",  (obj==null)?null:obj.getUnauthorizedRequestCount() );
    				encoder.putEncodeable("ReadCount", ServiceCounterDataType.class, (obj==null)?null:obj.getReadCount());
    				encoder.putEncodeable("HistoryReadCount", ServiceCounterDataType.class, (obj==null)?null:obj.getHistoryReadCount());
    				encoder.putEncodeable("WriteCount", ServiceCounterDataType.class, (obj==null)?null:obj.getWriteCount());
    				encoder.putEncodeable("HistoryUpdateCount", ServiceCounterDataType.class, (obj==null)?null:obj.getHistoryUpdateCount());
    				encoder.putEncodeable("CallCount", ServiceCounterDataType.class, (obj==null)?null:obj.getCallCount());
    				encoder.putEncodeable("CreateMonitoredItemsCount", ServiceCounterDataType.class, (obj==null)?null:obj.getCreateMonitoredItemsCount());
    				encoder.putEncodeable("ModifyMonitoredItemsCount", ServiceCounterDataType.class, (obj==null)?null:obj.getModifyMonitoredItemsCount());
    				encoder.putEncodeable("SetMonitoringModeCount", ServiceCounterDataType.class, (obj==null)?null:obj.getSetMonitoringModeCount());
    				encoder.putEncodeable("SetTriggeringCount", ServiceCounterDataType.class, (obj==null)?null:obj.getSetTriggeringCount());
    				encoder.putEncodeable("DeleteMonitoredItemsCount", ServiceCounterDataType.class, (obj==null)?null:obj.getDeleteMonitoredItemsCount());
    				encoder.putEncodeable("CreateSubscriptionCount", ServiceCounterDataType.class, (obj==null)?null:obj.getCreateSubscriptionCount());
    				encoder.putEncodeable("ModifySubscriptionCount", ServiceCounterDataType.class, (obj==null)?null:obj.getModifySubscriptionCount());
    				encoder.putEncodeable("SetPublishingModeCount", ServiceCounterDataType.class, (obj==null)?null:obj.getSetPublishingModeCount());
    				encoder.putEncodeable("PublishCount", ServiceCounterDataType.class, (obj==null)?null:obj.getPublishCount());
    				encoder.putEncodeable("RepublishCount", ServiceCounterDataType.class, (obj==null)?null:obj.getRepublishCount());
    				encoder.putEncodeable("TransferSubscriptionsCount", ServiceCounterDataType.class, (obj==null)?null:obj.getTransferSubscriptionsCount());
    				encoder.putEncodeable("DeleteSubscriptionsCount", ServiceCounterDataType.class, (obj==null)?null:obj.getDeleteSubscriptionsCount());
    				encoder.putEncodeable("AddNodesCount", ServiceCounterDataType.class, (obj==null)?null:obj.getAddNodesCount());
    				encoder.putEncodeable("AddReferencesCount", ServiceCounterDataType.class, (obj==null)?null:obj.getAddReferencesCount());
    				encoder.putEncodeable("DeleteNodesCount", ServiceCounterDataType.class, (obj==null)?null:obj.getDeleteNodesCount());
    				encoder.putEncodeable("DeleteReferencesCount", ServiceCounterDataType.class, (obj==null)?null:obj.getDeleteReferencesCount());
    				encoder.putEncodeable("BrowseCount", ServiceCounterDataType.class, (obj==null)?null:obj.getBrowseCount());
    				encoder.putEncodeable("BrowseNextCount", ServiceCounterDataType.class, (obj==null)?null:obj.getBrowseNextCount());
    				encoder.putEncodeable("TranslateBrowsePathsToNodeIdsCount", ServiceCounterDataType.class, (obj==null)?null:obj.getTranslateBrowsePathsToNodeIdsCount());
    				encoder.putEncodeable("QueryFirstCount", ServiceCounterDataType.class, (obj==null)?null:obj.getQueryFirstCount());
    				encoder.putEncodeable("QueryNextCount", ServiceCounterDataType.class, (obj==null)?null:obj.getQueryNextCount());
    				encoder.putEncodeable("RegisterNodesCount", ServiceCounterDataType.class, (obj==null)?null:obj.getRegisterNodesCount());
    				encoder.putEncodeable("UnregisterNodesCount", ServiceCounterDataType.class, (obj==null)?null:obj.getUnregisterNodesCount());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SessionDiagnosticsDataType result = new SessionDiagnosticsDataType();
    				result.setSessionId( decoder.getNodeId("SessionId") );
    				result.setSessionName( decoder.getString("SessionName") );
    				result.setClientDescription( decoder.getEncodeable("ClientDescription", ApplicationDescription.class) );
    				result.setServerUri( decoder.getString("ServerUri") );
    				result.setEndpointUrl( decoder.getString("EndpointUrl") );
    				result.setLocaleIds( decoder.getStringArray("LocaleIds") );
    				result.setActualSessionTimeout( decoder.getDouble("ActualSessionTimeout") );
    				result.setMaxResponseMessageSize( decoder.getUInt32("MaxResponseMessageSize") );
    				result.setClientConnectionTime( decoder.getDateTime("ClientConnectionTime") );
    				result.setClientLastContactTime( decoder.getDateTime("ClientLastContactTime") );
    				result.setCurrentSubscriptionsCount( decoder.getUInt32("CurrentSubscriptionsCount") );
    				result.setCurrentMonitoredItemsCount( decoder.getUInt32("CurrentMonitoredItemsCount") );
    				result.setCurrentPublishRequestsInQueue( decoder.getUInt32("CurrentPublishRequestsInQueue") );
    				result.setTotalRequestCount( decoder.getEncodeable("TotalRequestCount", ServiceCounterDataType.class) );
    				result.setUnauthorizedRequestCount( decoder.getUInt32("UnauthorizedRequestCount") );
    				result.setReadCount( decoder.getEncodeable("ReadCount", ServiceCounterDataType.class) );
    				result.setHistoryReadCount( decoder.getEncodeable("HistoryReadCount", ServiceCounterDataType.class) );
    				result.setWriteCount( decoder.getEncodeable("WriteCount", ServiceCounterDataType.class) );
    				result.setHistoryUpdateCount( decoder.getEncodeable("HistoryUpdateCount", ServiceCounterDataType.class) );
    				result.setCallCount( decoder.getEncodeable("CallCount", ServiceCounterDataType.class) );
    				result.setCreateMonitoredItemsCount( decoder.getEncodeable("CreateMonitoredItemsCount", ServiceCounterDataType.class) );
    				result.setModifyMonitoredItemsCount( decoder.getEncodeable("ModifyMonitoredItemsCount", ServiceCounterDataType.class) );
    				result.setSetMonitoringModeCount( decoder.getEncodeable("SetMonitoringModeCount", ServiceCounterDataType.class) );
    				result.setSetTriggeringCount( decoder.getEncodeable("SetTriggeringCount", ServiceCounterDataType.class) );
    				result.setDeleteMonitoredItemsCount( decoder.getEncodeable("DeleteMonitoredItemsCount", ServiceCounterDataType.class) );
    				result.setCreateSubscriptionCount( decoder.getEncodeable("CreateSubscriptionCount", ServiceCounterDataType.class) );
    				result.setModifySubscriptionCount( decoder.getEncodeable("ModifySubscriptionCount", ServiceCounterDataType.class) );
    				result.setSetPublishingModeCount( decoder.getEncodeable("SetPublishingModeCount", ServiceCounterDataType.class) );
    				result.setPublishCount( decoder.getEncodeable("PublishCount", ServiceCounterDataType.class) );
    				result.setRepublishCount( decoder.getEncodeable("RepublishCount", ServiceCounterDataType.class) );
    				result.setTransferSubscriptionsCount( decoder.getEncodeable("TransferSubscriptionsCount", ServiceCounterDataType.class) );
    				result.setDeleteSubscriptionsCount( decoder.getEncodeable("DeleteSubscriptionsCount", ServiceCounterDataType.class) );
    				result.setAddNodesCount( decoder.getEncodeable("AddNodesCount", ServiceCounterDataType.class) );
    				result.setAddReferencesCount( decoder.getEncodeable("AddReferencesCount", ServiceCounterDataType.class) );
    				result.setDeleteNodesCount( decoder.getEncodeable("DeleteNodesCount", ServiceCounterDataType.class) );
    				result.setDeleteReferencesCount( decoder.getEncodeable("DeleteReferencesCount", ServiceCounterDataType.class) );
    				result.setBrowseCount( decoder.getEncodeable("BrowseCount", ServiceCounterDataType.class) );
    				result.setBrowseNextCount( decoder.getEncodeable("BrowseNextCount", ServiceCounterDataType.class) );
    				result.setTranslateBrowsePathsToNodeIdsCount( decoder.getEncodeable("TranslateBrowsePathsToNodeIdsCount", ServiceCounterDataType.class) );
    				result.setQueryFirstCount( decoder.getEncodeable("QueryFirstCount", ServiceCounterDataType.class) );
    				result.setQueryNextCount( decoder.getEncodeable("QueryNextCount", ServiceCounterDataType.class) );
    				result.setRegisterNodesCount( decoder.getEncodeable("RegisterNodesCount", ServiceCounterDataType.class) );
    				result.setUnregisterNodesCount( decoder.getEncodeable("UnregisterNodesCount", ServiceCounterDataType.class) );
    				return result;
    			}
    		});
    
    	// SessionSecurityDiagnosticsDataType
    	addSerializer(
    		new AbstractSerializer(SessionSecurityDiagnosticsDataType.class, SessionSecurityDiagnosticsDataType.BINARY, SessionSecurityDiagnosticsDataType.XML, SessionSecurityDiagnosticsDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SessionSecurityDiagnosticsDataType obj = (SessionSecurityDiagnosticsDataType) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getSessionId() );
    				calculator.putString(null,  (obj==null)?null:obj.getClientUserIdOfSession() );
    				calculator.putStringArray(null, ((obj==null)?null:obj.getClientUserIdHistory()) );
    				calculator.putString(null,  (obj==null)?null:obj.getAuthenticationMechanism() );
    				calculator.putString(null,  (obj==null)?null:obj.getEncoding() );
    				calculator.putString(null,  (obj==null)?null:obj.getTransportProtocol() );
    				calculator.putEnumeration(null, null /*obj.getSecurityMode()*/);
    				calculator.putString(null,  (obj==null)?null:obj.getSecurityPolicyUri() );
    				calculator.putByteString(null,  (obj==null)?null:obj.getClientCertificate() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SessionSecurityDiagnosticsDataType obj = (SessionSecurityDiagnosticsDataType) encodeable;
    				encoder.putNodeId("SessionId",  (obj==null)?null:obj.getSessionId() );
    				encoder.putString("ClientUserIdOfSession",  (obj==null)?null:obj.getClientUserIdOfSession() );
    				encoder.putStringArray("ClientUserIdHistory", (obj==null)?null:obj.getClientUserIdHistory() );
    				encoder.putString("AuthenticationMechanism",  (obj==null)?null:obj.getAuthenticationMechanism() );
    				encoder.putString("Encoding",  (obj==null)?null:obj.getEncoding() );
    				encoder.putString("TransportProtocol",  (obj==null)?null:obj.getTransportProtocol() );
    				encoder.putEnumeration("SecurityMode",  (obj==null)?null:obj.getSecurityMode() );
    				encoder.putString("SecurityPolicyUri",  (obj==null)?null:obj.getSecurityPolicyUri() );
    				encoder.putByteString("ClientCertificate",  (obj==null)?null:obj.getClientCertificate() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SessionSecurityDiagnosticsDataType result = new SessionSecurityDiagnosticsDataType();
    				result.setSessionId( decoder.getNodeId("SessionId") );
    				result.setClientUserIdOfSession( decoder.getString("ClientUserIdOfSession") );
    				result.setClientUserIdHistory( decoder.getStringArray("ClientUserIdHistory") );
    				result.setAuthenticationMechanism( decoder.getString("AuthenticationMechanism") );
    				result.setEncoding( decoder.getString("Encoding") );
    				result.setTransportProtocol( decoder.getString("TransportProtocol") );
    				result.setSecurityMode( decoder.getEnumeration("SecurityMode", MessageSecurityMode.class) );
    				result.setSecurityPolicyUri( decoder.getString("SecurityPolicyUri") );
    				result.setClientCertificate( decoder.getByteString("ClientCertificate") );
    				return result;
    			}
    		});
    
    	// ServiceCounterDataType
    	addSerializer(
    		new AbstractSerializer(ServiceCounterDataType.class, ServiceCounterDataType.BINARY, ServiceCounterDataType.XML, ServiceCounterDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ServiceCounterDataType obj = (ServiceCounterDataType) encodeable;
    				calculator.putUInt32(null, null /*obj.getTotalCount()*/);
    				calculator.putUInt32(null, null /*obj.getErrorCount()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ServiceCounterDataType obj = (ServiceCounterDataType) encodeable;
    				encoder.putUInt32("TotalCount",  (obj==null)?null:obj.getTotalCount() );
    				encoder.putUInt32("ErrorCount",  (obj==null)?null:obj.getErrorCount() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ServiceCounterDataType result = new ServiceCounterDataType();
    				result.setTotalCount( decoder.getUInt32("TotalCount") );
    				result.setErrorCount( decoder.getUInt32("ErrorCount") );
    				return result;
    			}
    		});
    
    	// StatusResult
    	addSerializer(
    		new AbstractSerializer(StatusResult.class, StatusResult.BINARY, StatusResult.XML, StatusResult.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				StatusResult obj = (StatusResult) encodeable;
    				calculator.putStatusCode(null,  (obj==null)?null:obj.getStatusCode() );
    				calculator.putDiagnosticInfo(null,  (obj==null)?null:obj.getDiagnosticInfo() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				StatusResult obj = (StatusResult) encodeable;
    				encoder.putStatusCode("StatusCode",  (obj==null)?null:obj.getStatusCode() );
    				encoder.putDiagnosticInfo("DiagnosticInfo",  (obj==null)?null:obj.getDiagnosticInfo() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				StatusResult result = new StatusResult();
    				result.setStatusCode( decoder.getStatusCode("StatusCode") );
    				result.setDiagnosticInfo( decoder.getDiagnosticInfo("DiagnosticInfo") );
    				return result;
    			}
    		});
    
    	// SubscriptionDiagnosticsDataType
    	addSerializer(
    		new AbstractSerializer(SubscriptionDiagnosticsDataType.class, SubscriptionDiagnosticsDataType.BINARY, SubscriptionDiagnosticsDataType.XML, SubscriptionDiagnosticsDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SubscriptionDiagnosticsDataType obj = (SubscriptionDiagnosticsDataType) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getSessionId() );
    				calculator.putUInt32(null, null /*obj.getSubscriptionId()*/);
    				calculator.putByte(null, null /*obj.getPriority()*/);
    				calculator.putDouble(null, null /*obj.getPublishingInterval()*/);
    				calculator.putUInt32(null, null /*obj.getMaxKeepAliveCount()*/);
    				calculator.putUInt32(null, null /*obj.getMaxLifetimeCount()*/);
    				calculator.putUInt32(null, null /*obj.getMaxNotificationsPerPublish()*/);
    				calculator.putBoolean(null, null /*obj.getPublishingEnabled()*/);
    				calculator.putUInt32(null, null /*obj.getModifyCount()*/);
    				calculator.putUInt32(null, null /*obj.getEnableCount()*/);
    				calculator.putUInt32(null, null /*obj.getDisableCount()*/);
    				calculator.putUInt32(null, null /*obj.getRepublishRequestCount()*/);
    				calculator.putUInt32(null, null /*obj.getRepublishMessageRequestCount()*/);
    				calculator.putUInt32(null, null /*obj.getRepublishMessageCount()*/);
    				calculator.putUInt32(null, null /*obj.getTransferRequestCount()*/);
    				calculator.putUInt32(null, null /*obj.getTransferredToAltClientCount()*/);
    				calculator.putUInt32(null, null /*obj.getTransferredToSameClientCount()*/);
    				calculator.putUInt32(null, null /*obj.getPublishRequestCount()*/);
    				calculator.putUInt32(null, null /*obj.getDataChangeNotificationsCount()*/);
    				calculator.putUInt32(null, null /*obj.getEventNotificationsCount()*/);
    				calculator.putUInt32(null, null /*obj.getNotificationsCount()*/);
    				calculator.putUInt32(null, null /*obj.getLatePublishRequestCount()*/);
    				calculator.putUInt32(null, null /*obj.getCurrentKeepAliveCount()*/);
    				calculator.putUInt32(null, null /*obj.getCurrentLifetimeCount()*/);
    				calculator.putUInt32(null, null /*obj.getUnacknowledgedMessageCount()*/);
    				calculator.putUInt32(null, null /*obj.getDiscardedMessageCount()*/);
    				calculator.putUInt32(null, null /*obj.getMonitoredItemCount()*/);
    				calculator.putUInt32(null, null /*obj.getDisabledMonitoredItemCount()*/);
    				calculator.putUInt32(null, null /*obj.getMonitoringQueueOverflowCount()*/);
    				calculator.putUInt32(null, null /*obj.getNextSequenceNumber()*/);
    				calculator.putUInt32(null, null /*obj.getEventQueueOverFlowCount()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SubscriptionDiagnosticsDataType obj = (SubscriptionDiagnosticsDataType) encodeable;
    				encoder.putNodeId("SessionId",  (obj==null)?null:obj.getSessionId() );
    				encoder.putUInt32("SubscriptionId",  (obj==null)?null:obj.getSubscriptionId() );
    				encoder.putByte("Priority",  (obj==null)?null:obj.getPriority() );
    				encoder.putDouble("PublishingInterval",  (obj==null)?null:obj.getPublishingInterval() );
    				encoder.putUInt32("MaxKeepAliveCount",  (obj==null)?null:obj.getMaxKeepAliveCount() );
    				encoder.putUInt32("MaxLifetimeCount",  (obj==null)?null:obj.getMaxLifetimeCount() );
    				encoder.putUInt32("MaxNotificationsPerPublish",  (obj==null)?null:obj.getMaxNotificationsPerPublish() );
    				encoder.putBoolean("PublishingEnabled",  (obj==null)?null:obj.getPublishingEnabled() );
    				encoder.putUInt32("ModifyCount",  (obj==null)?null:obj.getModifyCount() );
    				encoder.putUInt32("EnableCount",  (obj==null)?null:obj.getEnableCount() );
    				encoder.putUInt32("DisableCount",  (obj==null)?null:obj.getDisableCount() );
    				encoder.putUInt32("RepublishRequestCount",  (obj==null)?null:obj.getRepublishRequestCount() );
    				encoder.putUInt32("RepublishMessageRequestCount",  (obj==null)?null:obj.getRepublishMessageRequestCount() );
    				encoder.putUInt32("RepublishMessageCount",  (obj==null)?null:obj.getRepublishMessageCount() );
    				encoder.putUInt32("TransferRequestCount",  (obj==null)?null:obj.getTransferRequestCount() );
    				encoder.putUInt32("TransferredToAltClientCount",  (obj==null)?null:obj.getTransferredToAltClientCount() );
    				encoder.putUInt32("TransferredToSameClientCount",  (obj==null)?null:obj.getTransferredToSameClientCount() );
    				encoder.putUInt32("PublishRequestCount",  (obj==null)?null:obj.getPublishRequestCount() );
    				encoder.putUInt32("DataChangeNotificationsCount",  (obj==null)?null:obj.getDataChangeNotificationsCount() );
    				encoder.putUInt32("EventNotificationsCount",  (obj==null)?null:obj.getEventNotificationsCount() );
    				encoder.putUInt32("NotificationsCount",  (obj==null)?null:obj.getNotificationsCount() );
    				encoder.putUInt32("LatePublishRequestCount",  (obj==null)?null:obj.getLatePublishRequestCount() );
    				encoder.putUInt32("CurrentKeepAliveCount",  (obj==null)?null:obj.getCurrentKeepAliveCount() );
    				encoder.putUInt32("CurrentLifetimeCount",  (obj==null)?null:obj.getCurrentLifetimeCount() );
    				encoder.putUInt32("UnacknowledgedMessageCount",  (obj==null)?null:obj.getUnacknowledgedMessageCount() );
    				encoder.putUInt32("DiscardedMessageCount",  (obj==null)?null:obj.getDiscardedMessageCount() );
    				encoder.putUInt32("MonitoredItemCount",  (obj==null)?null:obj.getMonitoredItemCount() );
    				encoder.putUInt32("DisabledMonitoredItemCount",  (obj==null)?null:obj.getDisabledMonitoredItemCount() );
    				encoder.putUInt32("MonitoringQueueOverflowCount",  (obj==null)?null:obj.getMonitoringQueueOverflowCount() );
    				encoder.putUInt32("NextSequenceNumber",  (obj==null)?null:obj.getNextSequenceNumber() );
    				encoder.putUInt32("EventQueueOverFlowCount",  (obj==null)?null:obj.getEventQueueOverFlowCount() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SubscriptionDiagnosticsDataType result = new SubscriptionDiagnosticsDataType();
    				result.setSessionId( decoder.getNodeId("SessionId") );
    				result.setSubscriptionId( decoder.getUInt32("SubscriptionId") );
    				result.setPriority( decoder.getByte("Priority") );
    				result.setPublishingInterval( decoder.getDouble("PublishingInterval") );
    				result.setMaxKeepAliveCount( decoder.getUInt32("MaxKeepAliveCount") );
    				result.setMaxLifetimeCount( decoder.getUInt32("MaxLifetimeCount") );
    				result.setMaxNotificationsPerPublish( decoder.getUInt32("MaxNotificationsPerPublish") );
    				result.setPublishingEnabled( decoder.getBoolean("PublishingEnabled") );
    				result.setModifyCount( decoder.getUInt32("ModifyCount") );
    				result.setEnableCount( decoder.getUInt32("EnableCount") );
    				result.setDisableCount( decoder.getUInt32("DisableCount") );
    				result.setRepublishRequestCount( decoder.getUInt32("RepublishRequestCount") );
    				result.setRepublishMessageRequestCount( decoder.getUInt32("RepublishMessageRequestCount") );
    				result.setRepublishMessageCount( decoder.getUInt32("RepublishMessageCount") );
    				result.setTransferRequestCount( decoder.getUInt32("TransferRequestCount") );
    				result.setTransferredToAltClientCount( decoder.getUInt32("TransferredToAltClientCount") );
    				result.setTransferredToSameClientCount( decoder.getUInt32("TransferredToSameClientCount") );
    				result.setPublishRequestCount( decoder.getUInt32("PublishRequestCount") );
    				result.setDataChangeNotificationsCount( decoder.getUInt32("DataChangeNotificationsCount") );
    				result.setEventNotificationsCount( decoder.getUInt32("EventNotificationsCount") );
    				result.setNotificationsCount( decoder.getUInt32("NotificationsCount") );
    				result.setLatePublishRequestCount( decoder.getUInt32("LatePublishRequestCount") );
    				result.setCurrentKeepAliveCount( decoder.getUInt32("CurrentKeepAliveCount") );
    				result.setCurrentLifetimeCount( decoder.getUInt32("CurrentLifetimeCount") );
    				result.setUnacknowledgedMessageCount( decoder.getUInt32("UnacknowledgedMessageCount") );
    				result.setDiscardedMessageCount( decoder.getUInt32("DiscardedMessageCount") );
    				result.setMonitoredItemCount( decoder.getUInt32("MonitoredItemCount") );
    				result.setDisabledMonitoredItemCount( decoder.getUInt32("DisabledMonitoredItemCount") );
    				result.setMonitoringQueueOverflowCount( decoder.getUInt32("MonitoringQueueOverflowCount") );
    				result.setNextSequenceNumber( decoder.getUInt32("NextSequenceNumber") );
    				result.setEventQueueOverFlowCount( decoder.getUInt32("EventQueueOverFlowCount") );
    				return result;
    			}
    		});
    
    	// ModelChangeStructureDataType
    	addSerializer(
    		new AbstractSerializer(ModelChangeStructureDataType.class, ModelChangeStructureDataType.BINARY, ModelChangeStructureDataType.XML, ModelChangeStructureDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ModelChangeStructureDataType obj = (ModelChangeStructureDataType) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getAffected() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getAffectedType() );
    				calculator.putByte(null, null /*obj.getVerb()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ModelChangeStructureDataType obj = (ModelChangeStructureDataType) encodeable;
    				encoder.putNodeId("Affected",  (obj==null)?null:obj.getAffected() );
    				encoder.putNodeId("AffectedType",  (obj==null)?null:obj.getAffectedType() );
    				encoder.putByte("Verb",  (obj==null)?null:obj.getVerb() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ModelChangeStructureDataType result = new ModelChangeStructureDataType();
    				result.setAffected( decoder.getNodeId("Affected") );
    				result.setAffectedType( decoder.getNodeId("AffectedType") );
    				result.setVerb( decoder.getByte("Verb") );
    				return result;
    			}
    		});
    
    	// SemanticChangeStructureDataType
    	addSerializer(
    		new AbstractSerializer(SemanticChangeStructureDataType.class, SemanticChangeStructureDataType.BINARY, SemanticChangeStructureDataType.XML, SemanticChangeStructureDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				SemanticChangeStructureDataType obj = (SemanticChangeStructureDataType) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getAffected() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getAffectedType() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				SemanticChangeStructureDataType obj = (SemanticChangeStructureDataType) encodeable;
    				encoder.putNodeId("Affected",  (obj==null)?null:obj.getAffected() );
    				encoder.putNodeId("AffectedType",  (obj==null)?null:obj.getAffectedType() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				SemanticChangeStructureDataType result = new SemanticChangeStructureDataType();
    				result.setAffected( decoder.getNodeId("Affected") );
    				result.setAffectedType( decoder.getNodeId("AffectedType") );
    				return result;
    			}
    		});
    
    	// Range
    	addSerializer(
    		new AbstractSerializer(Range.class, Range.BINARY, Range.XML, Range.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				Range obj = (Range) encodeable;
    				calculator.putDouble(null, null /*obj.getLow()*/);
    				calculator.putDouble(null, null /*obj.getHigh()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				Range obj = (Range) encodeable;
    				encoder.putDouble("Low",  (obj==null)?null:obj.getLow() );
    				encoder.putDouble("High",  (obj==null)?null:obj.getHigh() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				Range result = new Range();
    				result.setLow( decoder.getDouble("Low") );
    				result.setHigh( decoder.getDouble("High") );
    				return result;
    			}
    		});
    
    	// EUInformation
    	addSerializer(
    		new AbstractSerializer(EUInformation.class, EUInformation.BINARY, EUInformation.XML, EUInformation.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				EUInformation obj = (EUInformation) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getNamespaceUri() );
    				calculator.putInt32(null, null /*obj.getUnitId()*/);
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDisplayName() );
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getDescription() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				EUInformation obj = (EUInformation) encodeable;
    				encoder.putString("NamespaceUri",  (obj==null)?null:obj.getNamespaceUri() );
    				encoder.putInt32("UnitId",  (obj==null)?null:obj.getUnitId() );
    				encoder.putLocalizedText("DisplayName",  (obj==null)?null:obj.getDisplayName() );
    				encoder.putLocalizedText("Description",  (obj==null)?null:obj.getDescription() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				EUInformation result = new EUInformation();
    				result.setNamespaceUri( decoder.getString("NamespaceUri") );
    				result.setUnitId( decoder.getInt32("UnitId") );
    				result.setDisplayName( decoder.getLocalizedText("DisplayName") );
    				result.setDescription( decoder.getLocalizedText("Description") );
    				return result;
    			}
    		});
    
    	// ComplexNumberType
    	addSerializer(
    		new AbstractSerializer(ComplexNumberType.class, ComplexNumberType.BINARY, ComplexNumberType.XML, ComplexNumberType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ComplexNumberType obj = (ComplexNumberType) encodeable;
    				calculator.putFloat(null, null /*obj.getReal()*/);
    				calculator.putFloat(null, null /*obj.getImaginary()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ComplexNumberType obj = (ComplexNumberType) encodeable;
    				encoder.putFloat("Real",  (obj==null)?null:obj.getReal() );
    				encoder.putFloat("Imaginary",  (obj==null)?null:obj.getImaginary() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ComplexNumberType result = new ComplexNumberType();
    				result.setReal( decoder.getFloat("Real") );
    				result.setImaginary( decoder.getFloat("Imaginary") );
    				return result;
    			}
    		});
    
    	// DoubleComplexNumberType
    	addSerializer(
    		new AbstractSerializer(DoubleComplexNumberType.class, DoubleComplexNumberType.BINARY, DoubleComplexNumberType.XML, DoubleComplexNumberType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				DoubleComplexNumberType obj = (DoubleComplexNumberType) encodeable;
    				calculator.putDouble(null, null /*obj.getReal()*/);
    				calculator.putDouble(null, null /*obj.getImaginary()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				DoubleComplexNumberType obj = (DoubleComplexNumberType) encodeable;
    				encoder.putDouble("Real",  (obj==null)?null:obj.getReal() );
    				encoder.putDouble("Imaginary",  (obj==null)?null:obj.getImaginary() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				DoubleComplexNumberType result = new DoubleComplexNumberType();
    				result.setReal( decoder.getDouble("Real") );
    				result.setImaginary( decoder.getDouble("Imaginary") );
    				return result;
    			}
    		});
    
    	// AxisInformation
    	addSerializer(
    		new AbstractSerializer(AxisInformation.class, AxisInformation.BINARY, AxisInformation.XML, AxisInformation.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				AxisInformation obj = (AxisInformation) encodeable;
    				calculator.putEncodeable(null, EUInformation.class, (obj==null)?null:obj.getEngineeringUnits());
    				calculator.putEncodeable(null, Range.class, (obj==null)?null:obj.getEURange());
    				calculator.putLocalizedText(null,  (obj==null)?null:obj.getTitle() );
    				calculator.putEnumeration(null, null /*obj.getAxisScaleType()*/);
    				calculator.putDoubleArray(null, ((obj==null)?null:obj.getAxisSteps()) );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				AxisInformation obj = (AxisInformation) encodeable;
    				encoder.putEncodeable("EngineeringUnits", EUInformation.class, (obj==null)?null:obj.getEngineeringUnits());
    				encoder.putEncodeable("EURange", Range.class, (obj==null)?null:obj.getEURange());
    				encoder.putLocalizedText("Title",  (obj==null)?null:obj.getTitle() );
    				encoder.putEnumeration("AxisScaleType",  (obj==null)?null:obj.getAxisScaleType() );
    				encoder.putDoubleArray("AxisSteps", (obj==null)?null:obj.getAxisSteps() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				AxisInformation result = new AxisInformation();
    				result.setEngineeringUnits( decoder.getEncodeable("EngineeringUnits", EUInformation.class) );
    				result.setEURange( decoder.getEncodeable("EURange", Range.class) );
    				result.setTitle( decoder.getLocalizedText("Title") );
    				result.setAxisScaleType( decoder.getEnumeration("AxisScaleType", AxisScaleEnumeration.class) );
    				result.setAxisSteps( decoder.getDoubleArray("AxisSteps") );
    				return result;
    			}
    		});
    
    	// XVType
    	addSerializer(
    		new AbstractSerializer(XVType.class, XVType.BINARY, XVType.XML, XVType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				XVType obj = (XVType) encodeable;
    				calculator.putDouble(null, null /*obj.getX()*/);
    				calculator.putFloat(null, null /*obj.getValue()*/);
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				XVType obj = (XVType) encodeable;
    				encoder.putDouble("X",  (obj==null)?null:obj.getX() );
    				encoder.putFloat("Value",  (obj==null)?null:obj.getValue() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				XVType result = new XVType();
    				result.setX( decoder.getDouble("X") );
    				result.setValue( decoder.getFloat("Value") );
    				return result;
    			}
    		});
    
    	// ProgramDiagnosticDataType
    	addSerializer(
    		new AbstractSerializer(ProgramDiagnosticDataType.class, ProgramDiagnosticDataType.BINARY, ProgramDiagnosticDataType.XML, ProgramDiagnosticDataType.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				ProgramDiagnosticDataType obj = (ProgramDiagnosticDataType) encodeable;
    				calculator.putNodeId(null,  (obj==null)?null:obj.getCreateSessionId() );
    				calculator.putString(null,  (obj==null)?null:obj.getCreateClientName() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getInvocationCreationTime() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getLastTransitionTime() );
    				calculator.putString(null,  (obj==null)?null:obj.getLastMethodCall() );
    				calculator.putNodeId(null,  (obj==null)?null:obj.getLastMethodSessionId() );
    				calculator.putEncodeableArray(null, Argument.class, (obj==null)?null:obj.getLastMethodInputArguments());
    				calculator.putEncodeableArray(null, Argument.class, (obj==null)?null:obj.getLastMethodOutputArguments());
    				calculator.putDateTime(null,  (obj==null)?null:obj.getLastMethodCallTime() );
    				calculator.putEncodeable(null, StatusResult.class, (obj==null)?null:obj.getLastMethodReturnStatus());
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				ProgramDiagnosticDataType obj = (ProgramDiagnosticDataType) encodeable;
    				encoder.putNodeId("CreateSessionId",  (obj==null)?null:obj.getCreateSessionId() );
    				encoder.putString("CreateClientName",  (obj==null)?null:obj.getCreateClientName() );
    				encoder.putDateTime("InvocationCreationTime",  (obj==null)?null:obj.getInvocationCreationTime() );
    				encoder.putDateTime("LastTransitionTime",  (obj==null)?null:obj.getLastTransitionTime() );
    				encoder.putString("LastMethodCall",  (obj==null)?null:obj.getLastMethodCall() );
    				encoder.putNodeId("LastMethodSessionId",  (obj==null)?null:obj.getLastMethodSessionId() );
    				encoder.putEncodeableArray("LastMethodInputArguments", Argument.class, (obj==null)?null:obj.getLastMethodInputArguments());
    				encoder.putEncodeableArray("LastMethodOutputArguments", Argument.class, (obj==null)?null:obj.getLastMethodOutputArguments());
    				encoder.putDateTime("LastMethodCallTime",  (obj==null)?null:obj.getLastMethodCallTime() );
    				encoder.putEncodeable("LastMethodReturnStatus", StatusResult.class, (obj==null)?null:obj.getLastMethodReturnStatus());
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				ProgramDiagnosticDataType result = new ProgramDiagnosticDataType();
    				result.setCreateSessionId( decoder.getNodeId("CreateSessionId") );
    				result.setCreateClientName( decoder.getString("CreateClientName") );
    				result.setInvocationCreationTime( decoder.getDateTime("InvocationCreationTime") );
    				result.setLastTransitionTime( decoder.getDateTime("LastTransitionTime") );
    				result.setLastMethodCall( decoder.getString("LastMethodCall") );
    				result.setLastMethodSessionId( decoder.getNodeId("LastMethodSessionId") );
    				result.setLastMethodInputArguments( decoder.getEncodeableArray("LastMethodInputArguments", Argument.class) );
    				result.setLastMethodOutputArguments( decoder.getEncodeableArray("LastMethodOutputArguments", Argument.class) );
    				result.setLastMethodCallTime( decoder.getDateTime("LastMethodCallTime") );
    				result.setLastMethodReturnStatus( decoder.getEncodeable("LastMethodReturnStatus", StatusResult.class) );
    				return result;
    			}
    		});
    
    	// Annotation
    	addSerializer(
    		new AbstractSerializer(Annotation.class, Annotation.BINARY, Annotation.XML, Annotation.ID) {
    			public void calcEncodeable(IEncodeable encodeable, IEncoder calculator) throws EncodingException {
    				Annotation obj = (Annotation) encodeable;
    				calculator.putString(null,  (obj==null)?null:obj.getMessage() );
    				calculator.putString(null,  (obj==null)?null:obj.getUserName() );
    				calculator.putDateTime(null,  (obj==null)?null:obj.getAnnotationTime() );
    			}
    			public void putEncodeable(IEncodeable encodeable, IEncoder encoder) throws EncodingException {
    				Annotation obj = (Annotation) encodeable;
    				encoder.putString("Message",  (obj==null)?null:obj.getMessage() );
    				encoder.putString("UserName",  (obj==null)?null:obj.getUserName() );
    				encoder.putDateTime("AnnotationTime",  (obj==null)?null:obj.getAnnotationTime() );
    			}
    			public IEncodeable getEncodeable(IDecoder decoder) throws DecodingException {
    				Annotation result = new Annotation();
    				result.setMessage( decoder.getString("Message") );
    				result.setUserName( decoder.getString("UserName") );
    				result.setAnnotationTime( decoder.getDateTime("AnnotationTime") );
    				return result;
    			}
    		});
    
 
		
	}
	

}
