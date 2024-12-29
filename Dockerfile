FROM openjdk:18-jdk-slim
COPY target/your-app-name.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]