#!/bin/bash -e
#
# This script takes a standard ubuntu box and installs all the software needed to run teletraan locally.

./run_checks.sh
export TELETRAAN_ROOT=~/teletraan

echo "Starting deploy service"

read -p "Setup MySQL: mysql -u root -p, source $TELETRAAN_ROOT/deploy-service/common/src/main/resources/sql/deploy.sql;"

cd $TELETRAAN_ROOT/deploy-service
./update.sh
