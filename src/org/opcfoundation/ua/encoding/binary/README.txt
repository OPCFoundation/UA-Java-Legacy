Binary Serialization
====================

Interfaces: 
  IEncoder, IDecoder
     Serialization or builtin types (primitives) and complex types
 
  IEncodeableSerializer
     Interface for encoding complex types (IEncodeable) 

Classes:

 BinaryEncoder & BinaryDecoder 
     Encoders builtin types and complex types when IEncodeableSerializer is given in EncoderContext
 
 EncodeableReflectionSerializer
     Encodes complex types using reflection to inspect the IEncodeable class
 
 EncodeableSerializer
     Code generated based encoder of IEncodeables 
 
