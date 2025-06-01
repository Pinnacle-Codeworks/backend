# Use a minimal base image with Java 17
FROM openjdk:17-jdk-slim

# Set work directory inside the container
WORKDIR /app

# Copy the JAR into the image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose port (if your Spring Boot app uses 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
