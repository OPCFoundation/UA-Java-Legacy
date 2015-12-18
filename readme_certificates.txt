This is the OPC UA Java Stack Distribution.

  * Certificates

The Java sample applications do not validate the certificates, so they accept secure connections with any application that has a certificate. 
Other applications may validate the certificates, and you need to follow their instructions on how to accept other application or HTTPS certificates.

See the org.opcfoundation.ua.examples.certs.Examples.java for details on how the certificates and private keys are saved and loaded from files.

Also see the section "Certificate validation" on how to define your own validators.

  * Certificate key size

The default key size for new certificates is 1024 bits. You may extend this to 2048 or 4096 with CertificateUtils.setKeySize() before 
creating the certificates.

Note that the different security policies have limitations, regarding to the certificate keys size:
- Basic128Rsa16 and Basic256 require a certificate of 1024 or 2048 bits
- Basic256Sha256 requires a certificate of 2048 or 4096 bits  

In order to enable all security protocols, you must define a key size of 2048 bits or enable two application instance certificates. 
You can use Application.addApplicationInstanceCertificate() to add new certificates as necessary. The stack will automatically use the 
correct certificate depending on the security policy used for communications. 

  * HTTPS certificates and .NET client applications

The client applications built with the OPC Foundation .NET stack require that the server's HTTPS certificate is signed by a trusted CA certificate. 
Therefore, the samples create a SampleCA certificate, which they use to sign the HTTPS certificate. In order to define a certificate (e.g. the 
SampleCA) as Trusted Root Certificate, you must install that into the respective Windows certificate store. 

The following instructions define how to do that:

http://technet.microsoft.com/en-us/library/cc754841.aspx#BKMK_addlocal

Or you can just 
- double click the .der file to open it in Windows,
- click "Install Certificate...",
- when asked for the certificate store, select "Place all certificates in the following store" and
- "Browse..." for the "Trusted Root Certificate Authorities"

  * Certificate validation
 
The application object is initialized to accept all certificates with the following definitions:
 
 		getOpctcpSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
		getHttpsSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
 
You can replace the validators with your own implementation (of CertificateValidator) by assigning them to these properties of
your Application instance.