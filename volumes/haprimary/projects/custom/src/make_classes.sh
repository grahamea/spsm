#!/bin/bash
# script to compile Java classes and add to rtvapm_custom.jar

echo ""
echo "... Compiling classes"
echo ""

CP=.:$RTVAPM_HOME/common/lib/gmsjrtvutils.jar:"$RTV_HOME"/lib/gmsjrtvhistorian.jar:"$RTV_HOME"/lib/gmsjmodels.jar:"$RTV_HOME"/lib/gmsjjmxds.jar:"$RTV_HOME"/lib/gmsjjmsadmds.jar:"$RTV_HOME"/lib/gmsjcacheds.jar:./myclasses.jar:"$RTV_USERPATH"

javac -classpath "$CP" com/sl/rtvapm/custom/*.java
if [ $? != 0 ]; then 
	echo "Error compiling classes"
	exit 1 
fi

JARFILE=../lib/rtvapm_custom.jar
JAROPTS=cf
if [ -f $JARFILE ]; then JAROPTS=uf; fi

echo ""
echo "... Generating $JARFILE"
echo ""

jar $JAROPTS $JARFILE com/sl/rtvapm/custom/*.class
if [ $? != 0 ]; then 
	echo "Error generating $JARFILE"
	exit 1 
else
	echo "make_classes Ok"
	exit 0
fi
