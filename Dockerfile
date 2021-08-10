FROM adoptopenjdk:11

#VOLUME /tmp
#ARG JAVA_OPTS
#ENV JAVA_OPTS=$JAVA_OPTS
#COPY spsm.jar spsm.jar
#EXPOSE 3000

#ENTRYPOINT exec java $JAVA_OPTS -jar spsm.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar spsm.jar

### Copy RTViewDataServer	
### Setup user for build execution and application runtime
ENV APP_ROOT=/opt/SolacePubSubMonitor \
    USER_NAME=default \
    USER_UID=10001
ENV PROJECT_DIR=${APP_ROOT}/projects/rtview-server	
ENV APP_HOME=${APP_ROOT}  PATH=$PATH:${APP_ROOT}/bin

#
# Add binaries
#
RUN mkdir -p ${APP_HOME}
COPY src/bin/ ${APP_ROOT}/bin/


WORKDIR ${APP_ROOT}

ENTRYPOINT ["${APP_ROOT}/bin/run.sh" ]
# usinng entrypoimnt means we can add option command line args using command
CMD ["-verbose"]