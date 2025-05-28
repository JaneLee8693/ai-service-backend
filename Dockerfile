# Use a lightweight OpenJDK image
FROM bellsoft/liberica-openjdk-alpine:17

# Set working directory
WORKDIR /app

# Copy the JAR into the container
COPY target/ai-service-1.0.jar app.jar

# Expose port used by Spring Boot
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
