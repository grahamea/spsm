FROM adoptopenjdk:11

#
# Add Tini to handle signal processing 
#
ENV TINI_VERSION v0.19.0
ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini
RUN chmod +x /tini

### Default to UTF-8 file.encoding
ENV LANG en_US.utf8


### add ifconfig as part of net tools so keying works

### Add Missing packages in 1 RUN command so that only one layer is created
RUN  \
### Add your package needs here
    INSTALL_PKGS="net-tools" && \
    apt-get update && \
    apt-get install -y ${INSTALL_PKGS} && \
    apt-get clean && \ 
    rm -rf /var/lib/apt/lists/*



### Copy RTViewDataServer	
### Setup user for build execution and application runtime
ENV APP_ROOT=/rtv/SolacePubSubMonitor \
    USER_NAME=default \
    USER_UID=10001 
ENV PROJECT_DIR=${APP_ROOT}/projects/rtview-server	
ENV APP_HOME=${APP_ROOT}  PATH=$PATH:${APP_ROOT}/bin

#
# Copy local unpacked RTViewDataServer into container
#
COPY SolacePubSubMonitor/ ${APP_ROOT}/


#
# Add our own container shell binaries
#
RUN mkdir -p ${APP_HOME}
COPY src/bin/*.sh ${APP_ROOT}/bin/

WORKDIR ${APP_ROOT}/bin

# export potentially persistent volumes
VOLUME ${PROJECT_DIR}

#ENTRYPOINT ["run.sh"]

# Use Tini as the entrypoint so that it handle signals for $PID 1 and sub processes
ENTRYPOINT ["/tini", "-v", "--", "/rtv/SolacePubSubMonitor/bin/run.sh"]

# using entrypoint means we can add option command line args using command
#CMD ["-verbose"]