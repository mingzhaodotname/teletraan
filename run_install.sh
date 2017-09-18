#!/bin/bash -ex
#
# This script takes a standard ubuntu box and installs all the software needed to run teletraan locally.

echo "Install pre-requisites..."

sudo apt install -y python-pip
sudo pip install --upgrade pip
sudo pip install virtualenv
sudo apt install -y openjdk-8-jdk-headless
sudo apt install -y maven
sudo apt-get install  -y mysql-server-5.7
sudo apt install -y curl
