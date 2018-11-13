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
		 * Following Structure types are not in the spec but are present in the files,
		 * but are not needed by this stack.
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
