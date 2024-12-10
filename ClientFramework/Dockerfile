FROM maven:3.9.8-eclipse-temurin-21

COPY src /app/src

COPY pom.xml /app

WORKDIR /app
EXPOSE 8080
CMD mvn test