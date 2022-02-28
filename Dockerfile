FROM java:8
VOLUME /tmp
COPY target/message-gateway-1.0.0-SNAPSHOT.jar msg-gateway.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","msg-gateway.jar"]