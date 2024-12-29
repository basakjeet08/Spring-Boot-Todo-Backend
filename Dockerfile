FROM openjdk:18-jdk-slim
ARG JAR_FILE=build/libs/todo-0.0.1-SNAPSHOT-plain.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]