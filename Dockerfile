# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application's jar file into the container
COPY build/libs/*.jar app.jar

# Expose the application port (e.g., 8080 for Spring Boot)
EXPOSE 10000

# Run the jar file when the container starts
CMD ["java", "-jar", "app.jar"]
