# Stage 1: Build the application
FROM gradle:8.0-jdk18 AS build
WORKDIR /app

# Copy the Gradle project files
COPY . .

# Build the project and create the JAR file
RUN ./gradlew bootJar

# Stage 2: Run the application
FROM openjdk:18-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]