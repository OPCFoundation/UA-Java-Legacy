This is the OPC UA Java Stack Distribution.

The stack requires Java SE 6 (JDK 1.6) or later.

  * Libraries

Necessary libraries for all applications are

- Security libraries

See below at "Security"

- HTTPS libraries

If HTTPS protocol support is required:

Apache HTTP Core: http*.jar
Apache Commons logging: commons-logging-*.jar

http://hc.apache.org/
http://commons.apache.org/proper/commons-logging/

- Logging libraries

Simple Logging Facade, SLF4J: slf4j-api*.jar

http://slf4j.org/

Plus either:

SLF4J Simple: slf4j-simple-*.jar

or both of these (or any other bridge & final logging library combination):

SLF4J-Log4j bridge: slf4j-log4j12-*.jar
Apache Log4J: log4j-1.2.*.jar

http://logging.apache.org/log4j/1.2/

(These additional SLF libraries are not in the default class path: each application may decide which combination to use) 


 * Licenses

Library licenses are in the license-directory. 

All Apache libraries use the Apache License 2.0. 
Bouncy Castle and Spongy Castle use the Bouncy Castle License. 

  * Security

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

  * Known issues

- TLS 1.2 policy required by OPC UA does not work (required ciphers not supported by JSSE) 
- HTTPS testing is not finished yet with the other stacks
- .NET Client requires that the server has a certificate signed by a trusted CA, if HTTPS is used.
  See the readme_certificates.txt for more 
- Apache HTTP Core http*.jar libraries deprecated lot of things when moving from version 4.2.x to 4.3.x.
  Problems with new HTTP libraries and certain message sizes came up in testing and backwards compatibility
  with previous HTTP libraries was conserved in this stack version. That's why we have included both version 
  4.2.x and 4.4.x in the lib - the latest ones may be used at own risk.

