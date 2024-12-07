FROM maven:3.9.8-eclipse-temurin-21

COPY src /app/src

COPY pom.xml /app

WORKDIR /app
RUN sleep 25 # wait for server to start
EXPOSE 8080
EXPOSE 3306
CMD mvn clean install -U