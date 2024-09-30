FROM maven:3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests


# Run stage

FROM openjdk:17-jdk-slim

COPY --from=build /target/*.jar demo.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","demo.jar"]
