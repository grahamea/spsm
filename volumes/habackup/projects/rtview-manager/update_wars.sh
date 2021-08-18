#!/bin/bash

# Edit these values or pass them in on the command line.

APPNAME=rtview-manager
HOST=localhost
PORTPREFIX=30

# Edit these values for High Availability. 
# Set HA_HOST to the hostname of the backup data server.  

HA_HOST=

if [ "$1" == "?" ] || [ "$1" == "help" ]; then 
	echo "Usage: update_wars.sh [appname [host [portprefix]]]"
	echo "Defaults: $APPNAME $HOST $PORTPREFIX"
	exit 0
fi

SECURE=
if [ "$1" == "-secure" ]; then
	SECURE=$1
	shift
fi

if [ "$1" != "" ]; then APPNAME=$1; fi
if [ "$2" != "" ]; then HOST=$2; fi
if [ "$3" != "" ]; then PORTPREFIX=$3; fi

make_rtvagent_war.sh -appname:${APPNAME} -host:${HOST} -port:${PORTPREFIX}72 -package:rtvmgr -ha_host:${HA_HOST}
make_rtvdata_war.sh -appname:${APPNAME} -host:${HOST} -port:${PORTPREFIX}78 -package:rtvmgr -ha_host:${HA_HOST}
make_rtvquery_war.sh -appname:${APPNAME} -host:${HOST} -port:${PORTPREFIX}78 -package:rtvmgr -ha_host:${HA_HOST} ${SECURE}
make_rtvadmin_war.sh -appname:${APPNAME} -host:${HOST} -port:${PORTPREFIX}78 -package:rtvmgr -ha_host:${HA_HOST}
make_rtvpost_war.sh -appname:${APPNAME} -host:${HOST} -port:${PORTPREFIX}75 -package:rtvmgr
make_ui_war.sh -appname:${APPNAME} -package:rtvmgr
