package org.opcfoundation.ua.codegen;

import org.opcfoundation.ua.codegen.DictionaryTypes2.ModelDesign.DataType;
import org.opcfoundation.ua.codegen.IdentifiersUtil.Identifier;

import com.google.common.collect.ImmutableList;

/**
 * The resource files this generator uses might contain errors or parts not used by this stack. 
 * This util will fix certain parts loaded from the resource files.
 *
 */
public class ResourceFileFixerUtil {

	static ImmutableList<IdentifiersUtil.Identifier> fixIdentifiers(ImmutableList<IdentifiersUtil.Identifier> original){
		ImmutableList.Builder<Integer> ignoreB = ImmutableList.builder();
		
		//Following DecimalDataType definition was in the files, but spec does not have it like that
		//The correct one is the Decimal as is, and it does not have encoding ids.
		ignoreB.add(17861); //DecimalDataType
		ignoreB.add(17863); //DecimalDataType_Encoding_DefaultBinary
		ignoreB.add(15045); //DecimalDataType_Encoding_DefaultJson
		ignoreB.add(17862); //DecimalDataType_Encoding_DefaultXml
		
		//see below fixStructures, some of these apparently are draft ids
		ignoreB.add(16313); //AdditionalParametersType + encodings
		ignoreB.add(17537); //AdditionalParametersType
		ignoreB.add(17547); //AdditionalParametersType
		ignoreB.add(17541); //AdditionalParametersType
		ignoreB.add(17546); //EccEncryptedSecret
		
		ignoreB.add(17538); //OpcUa_BinarySchema_AdditionalParametersType
		ignoreB.add(17539); //OpcUa_BinarySchema_AdditionalParametersType_DataTypeVersion
		ignoreB.add(17540); //OpcUa_BinarySchema_AdditionalParametersType_DictionaryFragment
		
		ignoreB.add(17550); //OpcUa_BinarySchema_EphemeralKeyType
		ignoreB.add(17551); //OpcUa_BinarySchema_EphemeralKeyType_DataTypeVersion
		ignoreB.add(17552); //OpcUa_BinarySchema_EphemeralKeyType_DictionaryFragment
		
		ignoreB.add(17542); //OpcUa_XmlSchema_AdditionalParametersType
		ignoreB.add(17543); //OpcUa_XmlSchema_AdditionalParametersType_DataTypeVersion
		ignoreB.add(17544); //OpcUa_XmlSchema_AdditionalParametersType_DictionaryFragment
		
		ignoreB.add(17554); //OpcUa_XmlSchema_EphemeralKeyType
		ignoreB.add(17555); //OpcUa_XmlSchema_EphemeralKeyType_DataTypeVersion
		ignoreB.add(17556); //OpcUa_XmlSchema_EphemeralKeyType_DictionaryFragment
		
		ignoreB.add(17548); //EphemeralKeyType
		ignoreB.add(17549); //EphemeralKeyType_Encoding_DefaultBinary
		ignoreB.add(17557); //EphemeralKeyType_Encoding_DefaultJson
		ignoreB.add(17553); //EphemeralKeyType_Encoding_DefaultXml
		
		ImmutableList<Integer> ignoreList = ignoreB.build();
		
		ImmutableList.Builder<IdentifiersUtil.Identifier> r = ImmutableList.builder();
		for(Identifier id : original) {
			if(!ignoreList.contains(id.id)) {
				r.add(id);
			}else {
				System.out.println("Ignoring the following, see ResourceFileFixerUtil. Id: "+ id.id);
			}
		}
		return r.build();
	}
	
	static ImmutableList<DictionaryTypes2.ModelDesign.DataType> fixStructures(ImmutableList<DictionaryTypes2.ModelDesign.DataType> original){
		
		/*
		 * Following Structure types are not in the spec but are present in the files and/or
		 * are not needed by this stack.
		 * 
		 * Some of them have NoClassGeneration true in the files, 
		 * but also some files that we need are marked as such so it does not help.
		 */
		ImmutableList.Builder<String> ignoreB = ImmutableList.builder();
		
		//Not a true Structure in this stack, mapped to java BigDecimal
		ignoreB.add("Decimal");
		
		//Not defined in the spec and not needed
		ignoreB.add("DataTypeNode");
		ignoreB.add("DecimalDataType");
		ignoreB.add("InstanceNode");
		ignoreB.add("MethodNode");
		ignoreB.add("Node");
		ignoreB.add("ObjectNode");
		ignoreB.add("ObjectTypeNode");
		ignoreB.add("ReferenceTypeNode");
		ignoreB.add("TypeNode");
		ignoreB.add("VariableNode");
		ignoreB.add("VariableTypeNode");
		ignoreB.add("ViewNode");
		
		//These are not real Structures/use not the common encoding rules of Structures
		//Some of these are assumed to behave like Decimal and must be handled in application levels
		//or not part of the current spec
		ignoreB.add("RsaEncryptedSecret"); //fake ExtensionObject, like Decimal
		ignoreB.add("EphemeralKeyType"); //marked "Draft" in files
		ignoreB.add("EccEncryptedSecret"); //marked "Draft" in files
		ignoreB.add("AdditionalParametersType"); //marked "Draft" in files
		
		ImmutableList<String> ignoreList = ignoreB.build();
		
		ImmutableList.Builder<DictionaryTypes2.ModelDesign.DataType> r = ImmutableList.builder();
		for(DataType id : original) {
			if(!ignoreList.contains(id.Name)) {
				r.add(id);
			}else {
				System.out.println("Ignoring the following, see ResourceFileFixerUtil. Structure: "+ id.Name);
			}
		}
		
		return r.build();
	}
	
}
