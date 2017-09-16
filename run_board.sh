#!/bin/bash -ex
#
# This script takes a standard ubuntu box and installs all the software needed to run teletraan locally.

./run_checks.sh
export TELETRAAN_ROOT=~/teletraan/

echo "Starting board"

cd $TELETRAAN_ROOT/deploy-board
virtualenv ./venv
source ./venv/bin/activate
pip install -r requirements.txt
# mkdir -p /tmp/deploy_board

./run.sh start

echo "Deploy board is running"
echo "Access http://localhost:8888 to try it out!"
