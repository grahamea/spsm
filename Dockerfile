FROM adoptopenjdk:11

#VOLUME /tmp
#ARG JAVA_OPTS
#ENV JAVA_OPTS=$JAVA_OPTS
#COPY spsm.jar spsm.jar
#EXPOSE 3000

#ENTRYPOINT exec java $JAVA_OPTS -jar spsm.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar spsm.jar

CMD ["java", "-version"]