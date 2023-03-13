# How to run this locally

## Dependencies
The service can be started alone.

To be able to use all the endpoints, 
you need to run the __webuni-cloudnative-block4-db__ repository's service aka the DB application
(either in a JVM or in a container).

Take note, if you start both applications on the same host, you should start one of them on a different host.

## Configuration
The address of the DB application should be set with the configuration key __api.message.url__.

An example starting command with compilation and packaging is included with the __app-start__ script (both for PowerShell and Bash).
Must be run from the pom.xml's folder.

## How to call
Copy the contents of the __openapi.yaml__ file to [an OpenApi editor](https://editor-next.swagger.io/) 
and try the endpoints out.

Or use the [Postman](https://www.postman.com/downloads/) collection in this folder.
