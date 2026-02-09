# project-d: Core API Gateway (Java 21, Kotlin, multi-module Maven)
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app

# Copy all project files
COPY . .

# Limit Maven JVM memory to avoid OOM on small servers
ENV MAVEN_OPTS="-Xmx512m"

# Build app and required modules using reactor
RUN mvn -pl app -am -DskipTests -B package

# --- Runtime ---
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
