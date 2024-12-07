FROM maven:3.8.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY ../pom.xml .
COPY ../src ./src

EXPOSE 8080

RUN mvn clean compile assembly:single

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
