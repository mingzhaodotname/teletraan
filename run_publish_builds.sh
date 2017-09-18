#!/bin/bash -ex
#
# This script takes a standard ubuntu box and installs all the software needed to run teletraan locally.

./run_checks.sh
export TELETRAAN_ROOT=~/teletraan/

echo "Starting deploy service"
source $TELETRAAN_ROOT/deploy-board/venv/bin/activate

cd $TELETRAAN_ROOT/deploy-sentinel-packages
make publish

cd $TELETRAAN_ROOT/deploy-sentinel-packages-hellojq
make publish
