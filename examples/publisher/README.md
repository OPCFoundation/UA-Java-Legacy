# OPC Foundation UA JAVA - Azure integration sample

Visit http://azure.com/iotdev to learn more about developing applications for Azure IoT.

This subproject contains a sample for publishing server nodes to Azure IoT.

* ClientPublisher connects as a client to a server and polls a node. The content of the node is converted to JSON and sent to IoTHub.

## Prerequisites

This sample uses the [Azure IoT device SDK for Java](https://github.com/Azure/azure-iot-sdks/blob/master/java/device/readme.md).

To get a device connection string [Setup a device on IoT Hub](https://github.com/Azure/azure-iot-sdks/blob/master/doc/setup_iothub.md). 

For more information on the Java integration in Azure IoT please follow [how-to-build-a-java-app-from-scratch](https://azure.microsoft.com/documentation/articles/iot-hub-java-java-getstarted/).

## Using
Import this Maven project to your IDE. Assuming your IDE can match the main stack project and this example, you can start the sample within the IDE directly (works on eclipse). Assuming not or if you want to build the samples using maven, you need to first build the Stack and install/deploy it to your local/internal repository. 

Alternatively to use from command line: ```mvn clean install``` this project. 

Command line arguments for ```ClientPublisher```:
* ```[OPC Server string]``` - OPC server,e.g. [opc.tcp://server:51210/UA/SampleServer]
* ```[Device connection string]``` - String containing Hostname, Device Id & Device Key in the following format: HostName=<iothub_host_name>;DeviceId=<device_id>;SharedAccessKey=<device_key>	
* ```[Protocol]``` - (https | amqps | mqtt | amqps_ws)

## Certificates

The sample application creates one certificate:

* OPC UA application instance certificate (self-signed) and private key, which are stored in ```<AppName>.der``` & ```.pem```

The certificates are stored in the root of the project directory (but are .gitignored).

The Java sample application does not validate the certificates, so it accepts secure connections with any application that has a certificate. Other applications may validate the certificates, and you need to follow their instructions on how to accept other application or HTTPS certificates.

## Common Problems - OPC UA Java Stack

Please refer to [examples](../basic/README.md).

