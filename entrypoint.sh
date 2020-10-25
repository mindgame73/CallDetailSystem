#!/bin/bash
set -e

#-----------Clean and repackage to executable jar file----------------
./mvnw clean package spring-boot:repackage

#-----------Copy executable JAR---------------------------------------
cp target/CDR-?.?.jar /opt/CDR.jar

#-----------Run jar file ---------------------------------------------
java -jar /opt/CDR.jar

exec "$@"
