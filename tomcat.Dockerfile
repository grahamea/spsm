#
# Pick the base image for tomcat,  you choice using tags
# see https://hub.docker.com/_/tomcat
# it is suggested to use the same base java image as the spsm container
# to reuse / share image layers.
#
FROM tomcat:8.5.69-jdk11

# LABEL maintainer=”support@rtview.com”

# Add the missing webapps disabled by default for security
# but not the examples 
RUN \
    cp -r webapps.dist/* webapps/ && \
    rm -rf webapps/examples


#
# Copy the additional/modified files from the SolacePubSubMonitor
# Tomcat installation to this installation 
#
#ENV SL_TOMCAT SolacePubSubMonitor/apache-tomcat-8.5.69-sl

#Use the staged tomcat files instead
ENV SL_TOMCAT staged/tomcat

#
# Copy the modified configuration files 
#
# RUN cp ${SL_TOMCAT}/conf/* conf/
COPY ${SL_TOMCAT}/conf/* conf/

#
# Copy the additional lib files
#
COPY ${SL_TOMCAT}/lib/* lib/

#
# Copy the additional bin files
#
COPY ${SL_TOMCAT}/bin/* bin/

#
# Copy the README_RTVIEW.txt file
#
COPY ${SL_TOMCAT}/README_RTVIEW.txt README_RTVIEW.txt

#
# copy our war files into webapps directory of the image
#
COPY ${SL_TOMCAT}/warfiles/*.war /usr/local/tomcat/webapps/


# expose the default port used by tomcat
EXPOSE 8068

# Run the apache tomcat webserver
CMD ["catalina.sh", "run"]