# OPC Foundation UA JAVA

The OPC Foundation has formally released the Unified Architecture Java Stack and Sample Code to the community.

Please review official site page (http://opcfoundation.github.io/UA-Java/) for:
 * Overview
 * Licensing
 * Sample Applications overview

## Contributing

We strongly encourage community participation and contribution to this project. First, please fork the repository and commit your changes there. Once happy with your changes you can generate a 'pull request'.

You must agree to the contributor license agreement before we can accept your changes. The CLA and "I AGREE" button is automatically displayed when you perform the pull request. You can preview CLA [here](https://opcfoundation.org/license/cla/ContributorLicenseAgreementv1.0.pdf).

OPC UA, empowering the Industrial Internet of Things (IIOT) and Industrie 4.0.

## Runtime Dependencies
The Stack requires Java SE 6 or later to run (for building requirements, see under 'Building the Stack').

See pom.xml for details about dependency jar versions.

The following dependencies are mandatory
* org.slf4j, SLF4J logging facade

Additionally the following dependencies are optional
* org.bouncycastle, Bouncy Castle, for using bouncy castle crypto provider (see 'Security' section)
* com.madgag.spongycastle, Spongy Castle, for using spongycastle crypto provider (used in android, See 'Security' section)
* org.apache.httpcomponents, HTTPS support libs, for https transport protocol support

Library licenses are in the license-directory.
All Apache libraries use the Apache License 2.0. 
Bouncy Castle and Spongy Castle use the Bouncy Castle License. 


## Building the Stack
As of 1.03 the project is a maven project. Note that these instrucrtions are for building the stack so that it works in Java 6.

#### JDK requirements ####
You need a Java 6 JDK that has the sunjce_provider, e.g. Oracle or OpenJDK (because of optional support for the sun jce crypto provider). In addition, because newest maven versions require at least Java 7, you need to also install it (or later) too.

The reason for requiring a version 6 of the JDK is that, while some newer versions of JDK have support for compiling to older versios of Java it is not enough to just define the ```source``` and the ```target``` arguments to ```1.6```. In addition a ```bootstrapclasspath``` argument needs to be defined (and pointed to a Java 6 installation) in order to use correct versions of the standard java libraries. Another option is using the older JDK version to compile by using maven-toolchains-plugin, which this project uses. It should also make this more future proof in case future JDK versions drop support for target 1.6, with JDK 9 being the last to support it (see https://bugs.openjdk.java.net/browse/JDK-8028563)

Maven needs to know where your JDK 6 installation is. To setup this create a file 'toolchains.xml' in your .m2 folder (which exists in your home folder) with the following contents:
```xml
<toolchains>
  <toolchain>
	<type>jdk</type>
	<provides>
	  <version>1.6</version>
	  <vendor>sun</vendor>
	</provides>
	<configuration>
	  <jdkHome>ABSOLUTE PATH TO JAVA 6 JDK HOME</jdkHome>
	</configuration>
  </toolchain>
</toolchains>
```
If you have no need to have a Java 6 supported build artifact, then you can remove the maven-toolchains-plugin from the pom.xml before building the project.

#### Compiling ####

* Import the project in your favorite IDE
 * e.g. in Eclipse Import->Existing Maven
* Execute the 'package' phase.

Alternatively assuming you have Maven installed and in PATH, you can build from the command line:
```
mvn package
```
The resulting artifacts are in the 'target' folder
* opc-ua-stack-XXX.jar, the main jar
* opc-ua-stack-XXX-javadoc.jar, javadoc documentation
* opc-ua-stack-XXX-sources.jar, sources
* opc-ua-stack-XXX-project.zip, zip of this project (without build artifacts)
* opc-ua-stack-XXX-dependencies.zip, zip of the project dependencies
 

## Examples

The 'examples' folder has a separate maven project that contains the samples, it has a dependency to the main stack project. See the examples/README.md for more information.


## Notes for developers
The stack codegen is provided under the 'codegen' folder. See codegen/README.md for more information

### Package file structure description
See generated javadoc after building, or package-info.java files in each package

### Mappings between Java and OPC UA types
See the javadoc for package org.opcfoudnation.ua.builtintypes

### Security
Security libraries are used as they are available. If no extra libraries are available, 
the Sun JCE implementation will be used.  Testing has so far based on the Bouncy Castle 
library and it is therefore recommended in normal applications.

The current implementation, since version 1.02.337.0, is based on a flexible CryptoProvider
model. There are several implementations of this interface available in the stack and you 
can also implement your own, if you have a custom security framework that you need to use.

The stack will pick a default CryptoProvider automatically as it finds them from the class
path. Alternatively, you can define the provider that you wish to use by setting it with 
CryptoUtil.setCryptoProvider() or with CryptoUtil.setSecurityPoviderName(). 

In the same manner, custom certificate framework can be used by implementing interface CertificateProvider 
and setting it with CertificateUtils.setCertificateProvider().

Current CryptoProvider implementations:

BcCryptoProvider (default, if Bouncy Castle is in the class path: uses Bouncy Castle directly)
ScCryptoProvider (default in Android, if Spongy Castle is in the class path)
SunJceCryptoProvider (default if Bouncy Castle or Spongy Castle are not available)
BcJceCryptoProvider (uses Bouncy Castle via the JCE crypto framework)
ScJceCryptoProvider (uses Spongy Castle via the JCE crypto framework)

If any of the ...JceCryptoProvider is used, you will have to install the JCE Unlimited Strength 
Jurisdiction Policy Files, from Oracle (for Java 6, 7 or 8, respectively), to enable support for 
256 bit security policies:

JRE6: http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html
JRE7: http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
JRE8: http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

Android includes a limited version of Bouncy Castle and the standard Bouncy Castle cannot 
be installed there. It also does not include the Sun classes. However, the Spongy Castle
libraries will provide the same functionality as Bouncy Castle in Android, so these 
libraries should be used in Android, unless the application can do without security altogether.

Current CertificateProvider implementations:

BcCertificateProvider (default, if Bouncy Castle is in the class path)
ScCertificateProvider (default in Android, if Spongy Castle is in the class path)
SunJceCertificateProvider (default if Bouncy Castle or Spongy Castle are not available)


## Known issues
* TLS 1.2 policy required by OPC UA does not work (required ciphers not supported by JSSE) 
* HTTPS testing is not finished yet with the other stacks
* .NET Client requires that the server has a certificate signed by a trusted CA, if HTTPS is used. See 'examples/README.md'.


