This is the OPC UA Java Stack Distribution.

  * Examples

There are a couple of example projects that demonstrate the use of the Java stack in this package.

BigCertificateExample is the main example, which can be used to test UA Binary
and HTTPS protocols. The application will instantiate a very basic server and 
connects a client to the server.

ServerExample1 is a basic test for starting a server

SampleClient is a simple test client application, which connects to a server, browses the address space and reads a value from status.
 
ClientExample* are a basic tests for connecting to various servers.

NanoServer is a an example of a minimal working UA server. This server is intended to conform to Nano Embedded Device Server Profile.

  * Building

There are a couple of makefiles: Makefile.examples.unix and Makefile.examples.win, which can be used to build the examples 
in Linux/OSX and Windows, respectively, if you have a running 'make' installed.

in Linux/OSX with 'make'

> make -f Makefile.examples.unix

or in Windows

> make -f Makefile.examples.win

NOTE: Check the paths to javac and ant, unless they are on the path. 

  * Running

The makefiles also create batch files, for the SampleClient and ServerExample1, to be able to run them from the command line.  

Linux/OSX:

> ./ServerExample1.sh
> ./SampleClient.sh opc.tcp://localhost:8666/UAExample  

Windows:

> ServerExample1.bat
> SampleClient.bat opc.tcp://localhost:8666/UAExample  

(You must run the server and client from a different shell/cmd window, since the server runs in the foreground of the shell.)

  * Certificates

The sample applications create several certificates:

- OPC UA application instance certificate (self-signed) and private key, which are stored in <AppName>.der & .pem
- CA root certificate, which is used to sign the HTTPS certificate, stored in SampleCA.der & .pem
- HTTPS certificate (signed with SampleCA), stored in <AppName>_https.der & .pem

The certificates are stored in the root of the project directory.

See the readme_certificates.txt for more information about building the trust relation between applications.

  * Logging
  
The sample applications are meant to use log4j-logging. In order to use logging, they need slf4j-api-*.jar-library, slf4j-log4j12-*.jar binding in addition to actual log4j-*.jar library at the build path.
More information about slf4j logging can be found from: http://slf4j.org/