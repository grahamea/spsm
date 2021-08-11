#!/bin/bash

# java sanity check
java -version

# start the spsm 
# echo "pretend I'm a dataserver ... start_servers.sh" $*

# start the servers
/rtv/SolacePubSubMonitor/bin/start_servers.sh $*

# keep running 
tail -f /dev/null
# and as a side effect echo the dataserver.log on stdout 
# so the docker logs commands work as expected
tail -f /rtv/SolacePubSubMonitor/projects/rtview-server/logs/dataserver.log
