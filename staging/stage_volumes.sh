#!/bin/bash
#
# setup_volumes.sh
# set up the required local persistent volumes for mounting in the spsm containers
# add a lib directory for files we may neeed to add (e.g. database jars) 
#
# set up primary
mkdir -p volumes/haprimary/projects/lib
# set up backup 
mkdir -p volumes/habackup/projects/lib

# pre populate from SolacePubSubMonitor

cp -r SolacePubSubMonitor/projects volumes/haprimary
cp -r SolacePubSubMonitor/projects volumes/habackup

# set permisions so that container user can update when mounted

chmod -R 777 volumes
