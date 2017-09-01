#!/bin/bash -ex
#
# This script takes a standard ubuntu box and installs all the software needed to run teletraan locally.

echo "Download and running Teletraan services..."

RELEASE_VERSION=v1.0.1
DEPLOY_SERVICE_VERSION=fdd68c0
DEPLOY_BOARD_VERSION=fdd68c0
DEPLOY_SENTINEL_VERSION=fdd68c0
AGENT_VERSION=1.2.3

DOWNLOAD_PATH=https://github.com/pinterest/teletraan/releases/download/${RELEASE_VERSION}
DEPLOY_SERVICE_FILE=teletraan-service-${DEPLOY_SERVICE_VERSION}.tar.gz
DEPLOY_BOARD_FILE=deploy-board-${DEPLOY_BOARD_VERSION}.tar.gz
DEPLOY_SENTINEL_FILE=deploy-sentinel-${DEPLOY_SENTINEL_VERSION}.tar.gz 
DEPLOY_AGENT_FILE=deploy-agent-${AGENT_VERSION}.zip

QUICK_START_DIR=~/teletraan-quick-start
mkdir -p $QUICK_START_DIR
cd $QUICK_START_DIR
if [ ! -f $DEPLOY_SERVICE_FILE ]; then
  wget $DOWNLOAD_PATH/$DEPLOY_SERVICE_FILE
fi
if [ ! -f $DEPLOY_BOARD_FILE ]; then
  wget $DOWNLOAD_PATH/$DEPLOY_BOARD_FILE
fi
if [ ! -f $DEPLOY_SENTINEL_FILE ]; then
  wget $DOWNLOAD_PATH/$DEPLOY_SENTINEL_FILE
fi
if [ ! -f $DEPLOY_AGENT_FILE ]; then
  wget $DOWNLOAD_PATH/$DEPLOY_AGENT_FILE
fi

rm -fr ~/teletraan-demo
rm -fr /tmp/teletraan
rm -fr /tmp/deployd
rm -fr /tmp/deploy-board

cd ~
mkdir -p teletraan-demo

cd ~/teletraan-demo
mkdir -p deploy-service
cat $QUICK_START_DIR/$DEPLOY_SERVICE_FILE | tar zxf - -C deploy-service
echo "Teletraan server downloaded"
sed -i -e 's/type: mysql/#type: mysql/' ./deploy-service/bin/server.yaml
sed -i -e 's/host: localhost/#host: localhost/' ./deploy-service/bin/server.yaml
sed -i -e 's/port: 3306/#port: 3306/' ./deploy-service/bin/server.yaml
sed -i -e 's/userName: root/#userName: root/' ./deploy-service/bin/server.yaml
sed -i -e 's/password:/#password:/' ./deploy-service/bin/server.yaml
sed -i -e 's/pool: 10:50:20:5/#pool: 10:50:20:5/' ./deploy-service/bin/server.yaml
sed -i -e 's/#type: embedded/type: embedded/' ./deploy-service/bin/server.yaml
sed -i -e 's/#workDir: \/tmp\/teletraan\/db/workDir: \/tmp\/teletraan\/db/' ./deploy-service/bin/server.yaml
echo "Edited server.yaml to user embedded database"
./deploy-service/bin/run.sh start
echo "Teletraan server is running"

cd ~/teletraan-demo
virtualenv ./venv
source ./venv/bin/activate
mkdir -p deploy-board
cat $QUICK_START_DIR/$DEPLOY_BOARD_FILE | tar zxf - -C deploy-board --strip-components=1
echo "Deploy board downloaded"
cd deploy-board
pip install -r requirements.txt
mkdir -p /tmp/deploy_board
./run.sh start
echo "Deploy board is running"

cd ~/teletraan-demo
mkdir -p deploy-sentinel
cat $QUICK_START_DIR/$DEPLOY_SENTINEL_FILE | tar zxf - -C deploy-sentinel --strip-components=1
cd ~/teletraan-demo/deploy-sentinel
echo "Deploy sentinel downloaded"

cd ~/teletraan-demo
mkdir -p deploy-agent
cat $QUICK_START_DIR/$DEPLOY_AGENT_FILE > ./deploy-agent/deploy-agent-${AGENT_VERSION}.zip
echo "Deploy agent downloaded"
CURRENT_PATH=${PWD}
pip install deploy-agent==${AGENT_VERSION} --find-links=file://${CURRENT_PATH}/deploy-agent/deploy-agent-${AGENT_VERSION}.zip

echo "Successfully completed Teletraan installation. Access http://localhost:8888 to try it out!"
