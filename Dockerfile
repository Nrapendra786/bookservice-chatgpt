## ---- Build stage ----
#FROM maven:3.9.9-eclipse-temurin-21 AS builder
#
#WORKDIR /app
#
## Only copy pom.xml first to leverage Docker layer caching
#COPY pom.xml .
#RUN mvn -DskipTests dependency:go-offline
#
## Now copy sources
#COPY src ./src
#
## Build the application
#RUN mvn -B -DskipTests package
#
## ---- Runtime stage ----
#FROM eclipse-temurin:21-jre
#
#WORKDIR /app
#
## Copy fat jar from builder
#COPY --from=builder /app/target/*.jar app.jar
#
## Run as non-root
#USER 1000:1000
#
#EXPOSE 8080
#
#ENV JAVA_OPTS=""
#
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# syntax=docker/dockerfile:1

# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

ARG UID=10001
ARG GID=10001

# Create non-root user/group in ONE layer
RUN set -eux; \
    groupadd -r -g "${GID}" app; \
    useradd  -r -u "${UID}" -g app -d /app -s /usr/sbin/nologin app

# Copy jar with correct ownership (avoids extra chown layer)
COPY --from=builder --chown=app:app /app/target/*.jar /app/app.jar

# JVM options via standard env var (no shell needed)
ENV JAVA_TOOL_OPTIONS=""

# Spring Boot config via env vars (defaults can be overridden at runtime)
ENV SERVER_PORT=8081 \
    SPRING_PROFILES_ACTIVE=default

EXPOSE 8080

USER app:app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]






































