#!/bin/bash -ex
#
# This script takes a standard ubuntu box and installs all the software needed to run teletraan locally.

echo "Check pre-requisites..."
wget --version
virtualenv --version
python --version
java -version
javac -version
mvn --version
mysql --version

echo status: $?
echo -e "\n=== Congratulations - all pre-requisites are installed ==="
