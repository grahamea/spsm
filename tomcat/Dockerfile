#
# Pick the base image for tomcat,  you choice using tags
# see https://hub.docker.com/_/tomcat
# it is suggested to use the same base java image as the spsm container
# to reuse / share image layers.
#
FROM tomcat:8.5-jdk11

# LABEL maintainer=”support@rtview.com”

# Add the missing webapps disabled by default for security
RUN cp -r webapps.dist/* webapps/

#
# copy our war files into webapps directory of the image
#
COPY  warfiles/*.war /usr/local/tomcat/webapps/


# expose the default port used by tomcat
EXPOSE 8080

# Run the apache tomcat webserver
CMD ["catalina.sh", "run"]