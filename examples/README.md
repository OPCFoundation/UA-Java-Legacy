# OPC Foundation UA JAVA - samples

This subproject contains samples for using the Stack.

List of samples:
* BigCertificateExample is the main example, which can be used to test UA Binary and HTTPS protocols. The application will instantiate a very basic server and connects a client to the server.
* ServerExample1 is a basic test for starting a server
* SampleClient is a simple test client application, which connects to a server, browses the address space and reads a value from status.
* ClientExample* are a basic tests for connecting to various servers.
* NanoServer is a an example of a minimal working UA server. This server is intended to conform to Nano Embedded Device Server Profile.

## Using
Import this Maven project to your IDE. Assuming your IDE can match the main stack project and this example one, you can start the samples within the IDE directly (works on eclipse). Assuming not or if you want to build the samples using maven, you need to first build the Stack and install/deploy it to your local/internal repository. 

Alternatively to use from command line: ```mvn clean install``` the main project and then ```mvn clean package``` this project. Bat/sh scripts used for starting the samples are generated to 'target/assemblies/bin'.


## Certificates

The sample applications create several certificates:

* OPC UA application instance certificate (self-signed) and private key, which are stored in <AppName>.der & .pem
* CA root certificate, which is used to sign the HTTPS certificate, stored in SampleCA.der & .pem
* HTTPS certificate (signed with SampleCA), stored in <AppName>_https.der & .pem
The certificates are stored in the root of the project directory (but are .gitignored).

The Java sample applications do not validate the certificates, so they accept secure connections with any application that has a certificate. Other applications may validate the certificates, and you need to follow their instructions on how to accept other application or HTTPS certificates.

See the org.opcfoundation.ua.examples.certs.Examples.java for details on how the certificates and private keys are saved and loaded from files.

Also see the section "Certificate validation" on how to define your own validators.


## Certificate key size

The default key size for new certificates is 1024 bits. You may extend this to 2048 or 4096 with CertificateUtils.setKeySize() before creating the certificates.

Note that the different security policies have limitations, regarding to the certificate keys size:
* Basic128Rsa16 and Basic256 require a certificate of 1024 or 2048 bits
* Basic256Sha256 requires a certificate of 2048 or 4096 bits  

In order to enable all security protocols, you must define a key size of 2048 bits or enable two application instance certificates. 
You can use Application.addApplicationInstanceCertificate() to add new certificates as necessary. The stack will automatically use the 
correct certificate depending on the security policy used for communications. 

## HTTPS certificates and .NET client applications
The client applications built with the OPC Foundation .NET stack require that the server's HTTPS certificate is signed by a trusted CA certificate. Therefore, the samples create a SampleCA certificate, which they use to sign the HTTPS certificate. In order to define a certificate (e.g. the SampleCA) as Trusted Root Certificate, you must install that into the respective Windows certificate store. 

The following instructions define how to do that:

http://technet.microsoft.com/en-us/library/cc754841.aspx#BKMK_addlocal

Or you can just 
* double click the .der file to open it in Windows,
* click "Install Certificate...",
* when asked for the certificate store, select "Place all certificates in the following store" and
* "Browse..." for the "Trusted Root Certificate Authorities"

## Certificate validation
The application object is initialized to accept all certificates with the following definitions:
``` 
getOpctcpSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
getHttpsSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
```
You can replace the validators with your own implementation (of CertificateValidator) by assigning them to these properties of
your Application instance.

## Common Problems - OPC UA Java Stack

When running the sample server (ServerExample1) and sample clients (ClientExample1 and SampleClient) you 
might encounter problems which prevent a successful connection. We have documented below common causes 
for connectivity problems, which include: 

- EndpointUrl mismatch
- Java Security Policies



### EndpointUrl Mismatch
	Summary:    The EndpointUrl used by the Client must match the Server's EndpointUrl.
	Resolution: 1. Launch the sample Server (ServerExample1)
				2. Find the EndpointUrl in the output console (e.g. opc.tcp://MyHost:8666/UAExample)
				3. Modify the "Run Configuration" for the Client (ClientExample1 or SampleClient): 
					a. In Eclipse, open Run -> Run Configurations...
					b. Expand "Java Application" in the left-navigation and choose a Client project.
					c. Click the "Arguments" tab
					d. Paste the Server's EndpointUrl (copied in step #2).
					e. Click "Apply" and then "Run".
				4. The Client should now connect successfully to the UA Server.




### Java Security Policies
	Summary:	You see an error (in red) that says "Illegal key size".
	Resolution:	1. Your Java runtime probably needs to update the Java Cryptography Extension (JCE) policy files.
				   (JRE 6: http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html)
				2. Extract the JCE download contents: local_policy.jar and US_export_policy.jar 
				3. Copy the 2 files to your Java security directory (backup your original files first): 
				   (JRE 6: C:\Program Files\Java\jre6\lib\security)
				4. Recompile and re-run your Java sample applications, Server and Client!

