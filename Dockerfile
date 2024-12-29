# Use the Eclipse Temurin 18 base image for Java 18
FROM eclipse-temurin:18-jdk-alpine AS build

# Set the working directory in the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Build the application using Gradle (if using Gradle for building)
RUN ./gradlew bootJar

# Stage 2: Create the runtime image
FROM eclipse-temurin:18-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the generated JAR file from the build stage
COPY --from=build /app/build/libs/*.jar todo-0.0.1-SNAPSHOT.jar

# Expose the application port (default Spring Boot port is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "todo-0.0.1-SNAPSHOT.jar"]
