#!/bin/bash

java -Xmx512m -Dserver.port=8082 \
-Dapi.message.url=http://localhost:8081 \
-jar target/microsoft-meetup-backend-api-1.0.0-SNAPSHOT.jar
