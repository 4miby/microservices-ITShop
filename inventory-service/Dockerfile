# Build stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy parent pom.xml first and install
COPY pom.xml /app/
COPY inventory-service/pom.xml /app/inventory-service/
COPY inventory-service/src /app/inventory-service/src

# Build project
RUN mvn install -N
WORKDIR /app/inventory-service
RUN mvn clean package -DskipTests -X

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy jar from build stage
COPY --from=build /app/inventory-service/target/*.jar app.jar

# Configure JVM
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0"

EXPOSE 8082
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]