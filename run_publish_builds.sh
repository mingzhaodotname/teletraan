#!/bin/bash
#
# This script takes a standard ubuntu box and installs all the software needed to run teletraan locally.

#./run_checks.sh
export TELETRAAN_ROOT=~/teletraan/

echo "Starting to publish packages to the package deployment system"
source $TELETRAAN_ROOT/deploy-board/venv/bin/activate

cd $TELETRAAN_ROOT/deploy-sentinel-packages
make -s publish

cd $TELETRAAN_ROOT/deploy-sentinel-packages-hellojq
make -s publish
