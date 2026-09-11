FROM maven:3.9-eclipse-temurin-21 AS build
LABEL author="yefrosiniya.zinkovskaya"
WORKDIR /app
COPY pom.xml .
COPY insurance-service/pom.xml insurance-service/
COPY insurance-registry-emulator/pom.xml insurance-registry-emulator/
COPY insurance-registry-emulator/src insurance-registry-emulator/src
RUN mvn -pl insurance-registry-emulator -am -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/insurance-registry-emulator/target/*.jar app.jar
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
