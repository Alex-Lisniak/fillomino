#FROM openjdk:17-jdk-alpine
#LABEL authors="Lisniak O.M."
#ARG JAR_FILE=target/*.jar
#COPY ./target
#ENTRYPOINT ["top", "-b"]
FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package -DskipTests=true

