#!/bin/bash
# stage_tomcat/.sh
#
# extract the changed files from the release and copy to the appropriate staged/tomcat directories
#
# define included tomcat release as SOURCE 
SOURCE="SolacePubSubMonitor/apache-tomcat-8.5.69-sl"
# define the tomcat staged directory as the TARGET
TARGET="staged/tomcat"


#
# Copy the modified configuration files 
#
cp $SOURCE/conf/server.xml $TARGET/conf
cp $SOURCE/conf/tomcat-users.xml $TARGET/conf
cp $SOURCE/conf/rtview-roles.txt $TARGET/conf

#
# Copy the additional lib files
#
cp $SOURCE/lib/rtview-jndirealm.jar $TARGET/lib
cp $SOURCE/lib/gmsjrtvhistorian.jar $TARGET/lib


#
# Copy the additional bin files
#
cp $SOURCE/bin/setenv.sh $TARGET/bin
cp $SOURCE/bin/setenv.bat $TARGET/bin

#
# Copy the README_RTVIEW.txt file
#
cp $SOURCE/README_RTVIEW.txt $TARGET
