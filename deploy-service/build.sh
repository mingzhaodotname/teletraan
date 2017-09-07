#!/bin/bash
set -ex

# First time: 
# mvn clean package -DskipTests

mvn package -DskipTests
