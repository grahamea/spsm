#!/bin/bash

# java sanity check
java -version

# start the spsm 
echo "pretend I'm a dataserver ... start_servers.sh" $*

# keep running 
tail -f /dev/null

