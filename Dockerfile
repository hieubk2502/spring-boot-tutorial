## Stage 1: Build Jar
#
#FROM maven:3.9.8-amazoncorretto-17 AS build
#
#WORKDIR /app
#
#COPY .env .
#
#COPY pom.xml .
#
#COPY src ./src
#
#RUN mvn package -DskipTests -P dev
#
##Stage 2: build image and run
#FROM openjdk:17
#
#WORKDIR /app
#
#COPY --from=build /app/target/*.jar api-service.jar
#
#ENTRYPOINT ["java","-jar","api-service.jar"]
#
#EXPOSE 80

FROM openjdk:17

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} api-service.jar

ENTRYPOINT ["java","-jar","api-service.jar"]

EXPOSE 80
