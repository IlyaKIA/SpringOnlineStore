FROM adoptopenjdk/openjdk11:alpine-jre  
ARG JAR_FILE=target/SpringData_HW7-0.0.1-SNAPSHOT.jar  
WORKDIR /opt/app  
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Xmx512m","-jar","app.jar"]