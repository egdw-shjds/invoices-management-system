FROM openjdk:23

# Set working directory inside container
WORKDIR /app

# Copy the JAR file into the container
COPY target/invoices-management-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
