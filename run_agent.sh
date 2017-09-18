#!/bin/bash -e
#
# This script takes a standard ubuntu box and installs all the software needed to run teletraan locally.

./run_checks.sh
export TELETRAAN_ROOT=~/teletraan

echo "setting up deploy agent"

cd $TELETRAAN_ROOT/deploy-agent
virtualenv ./venv
source ./venv/bin/activate
pip install python-daemon==2.0.6

./update.sh
# python setup.py build
# python setup.py install

