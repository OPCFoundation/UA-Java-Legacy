# OPC Foundation UA JAVA - codegen
This 'sub' project handles the codegeneration part of the stack.

The main class to run is codegen/org.opcfoundation.ua.codegen.Main2.

The generator reads descriptions in src/resources/codegen_data/ and writes the output
source files to main stack project src/main/java/org.opcfoundation.ua.core.

The input data is located in these folders:
codegen_data/data/ contains data type descriptions and constants from ModelDesigner.
codegen_data/templates/ contains Java source code templates.
codegen_data/overrides/ contains exceptions to templates.

Note: In eclipse, remember to refresh (F5) the source tree after code generation. 