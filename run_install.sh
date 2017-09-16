#!/bin/bash -ex
#
# This script takes a standard ubuntu box and installs all the software needed to run teletraan locally.

echo "Install pre-requisites..."

pip install virtualenv
sudo apt install openjdk-8-jdk-headless
sudo apt install maven
sudo apt-get install  mysql-server-5.7
