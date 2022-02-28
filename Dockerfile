FROM java:8
VOLUME /tmp
COPY target/message-gateway-1.0.0-SNAPSHOT.jar msg-gateway.jar

# ENV JAVA_OPTS = ""

EXPOSE 8080
ENTRYPOINT java -jar msg-gateway.jar