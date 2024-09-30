#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
RUN mvn clean package

#
# Package stage
#
FROM openjdk:17-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]